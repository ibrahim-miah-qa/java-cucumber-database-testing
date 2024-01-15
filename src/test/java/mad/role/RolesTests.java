package mad.role;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import mad.Mad;
import mad.components.common.DeleteConfirmDialog;
import mad.mapping.Role;
import mad.pages.role.CreateEditRoleDialog;
import mad.pages.role.RoleList;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Optional;

@Listeners
public class RolesTests extends Mad {
	RoleList roleList;
	
	@BeforeMethod
	public void before() {
		roleList = new RoleList();
		roleList.open();
		wait(2);
	}
	/**
	 * MAD-ADMIN-57
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 0)
	public void create_role_with_role_name_category_and_permission(JsonData data) {
		CreateEditRoleDialog createEditRoleDialog = roleList.createNew();
		wait(2);
		createEditRoleDialog.writeName(data.get("name"));
		createEditRoleDialog.writeDescription(data.get("description"));
		createEditRoleDialog.selectCategory(data.get("categories").split(","));
		createEditRoleDialog.selectPermissions(data.get("permissions").split(","));
		createEditRoleDialog.writeTag(data.get("tags").split(","));
		createEditRoleDialog.create();
		
		Assert.assertEquals(snackBar.readMessage(), "Roles created successfully.");
		snackBar.close();
		wait(2);
		
		roleList.search(data.get("name"));
		wait(2);
		
		boolean isFound = roleList
			.all()
			.stream()
			.anyMatch(r->r.name.equals(data.get("name")));
		
		Assert.assertTrue(isFound, "Role is not created");
	}
	
	/**
	 * MAD-ADMIN-77
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 1)
	public void search_role_by_name(JsonData data) {
		roleList.search(data.get("name"));
		wait(3);
		
		boolean isFound = roleList
			.all()
			.stream()
			.anyMatch(r -> r.name.equals(data.get("name")));
		
		Assert.assertTrue(isFound, "Role is not found");
	}
	
	
	/**
	 *  MAD-ADMIN-70
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 2)
	public void edit_role_to_change_role_category(JsonData data) {
		roleList.search(data.get("name"));
		wait(3);
		
		Optional<Role> roleOptional = roleList
			.all()
			.stream()
			.filter(r->r.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(roleOptional.isPresent(), "Role is not found");
		
		CreateEditRoleDialog editRoleDialog = roleOptional.get().edit();
		wait(2);
		
		String oldName = editRoleDialog.readName();
		Assert.assertEquals(oldName, data.get("name"));
		editRoleDialog.readCategories().forEach(c->{
			Assert.assertTrue(data.get("old_category").contains(c), "Old Category not matched");
		});
		
		editRoleDialog.removeAllCategories();
		editRoleDialog.selectCategory(data.get("categories").split(","));
		editRoleDialog.saveChanges();
		
		Assert.assertEquals(snackBar.readMessage(), "Roles updated successfully.");
		snackBar.close();
		
		roleList.search(data.get("name"));
		roleOptional = roleList
			.all()
			.stream()
			.filter(r->r.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(roleOptional.isPresent(), "Role not found after updated");
		
		editRoleDialog = roleOptional.get().edit();
		wait(2);
		
		editRoleDialog
			.readCategories()
			.forEach(c->{
				Assert.assertTrue(data.get("categories").contains(c));
			});
		
		editRoleDialog.close();
	}
	
	/**
	 * MAD-ADMIN-66
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 3)
	public void edit_role_to_change_name(JsonData data) {
		roleList.search(data.get("name"));
		wait(3);
		
		Optional<Role> roleOptional = roleList
			.all()
			.stream()
			.filter(r->r.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(roleOptional.isPresent(), "Role is not found");
		
		CreateEditRoleDialog editRoleDialog = roleOptional.get().edit();
		wait(2);
		
		String oldName = editRoleDialog.readName();
		Assert.assertEquals(oldName, data.get("name"));
		
		editRoleDialog.clearName();
		editRoleDialog.writeName(data.get("new_name"));
		editRoleDialog.saveChanges();
		
		Assert.assertEquals(snackBar.readMessage(), "Roles updated successfully.");
		snackBar.close();
		
		roleList.search(data.get("new_name"));
		boolean isUpdated = roleList
			.all()
			.stream()
			.anyMatch(r->r.name.equals(data.get("new_name")));
		
		Assert.assertTrue(isUpdated, "Role name is not updated");
	}
	
	/**
	 * MAD-ADMIN-70
	 */
	@Test(priority = 4, dataProvider = "json_data", dataProviderClass = JsonDataProvider.class)
	public void edit_role_to_change_permission(JsonData data) {
		roleList.search(data.get("name"));
		wait(3);
		
		Optional<Role> roleOptional = roleList
			.all()
			.stream()
			.filter(r->r.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(roleOptional.isPresent(), "Role is not found");
		
		CreateEditRoleDialog editRoleDialog = roleOptional.get().edit();
		wait(2);
		
		String oldName = editRoleDialog.readName();
		Assert.assertEquals(oldName, data.get("name"));
		editRoleDialog.readPermissions().forEach(c->{
			Assert.assertTrue(data.get("old_permissions").contains(c), "Old Permission not matched");
		});
		
		editRoleDialog.removeAllPermission();
		editRoleDialog.selectPermissions(data.get("permissions").split(","));
		editRoleDialog.saveChanges();
		
		Assert.assertEquals(snackBar.readMessage(), "Roles updated successfully.");
		snackBar.close();
		
		roleList.search(data.get("name"));
		roleOptional = roleList
			.all()
			.stream()
			.filter(r->r.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(roleOptional.isPresent(), "Role not found after updated");
		
		editRoleDialog = roleOptional.get().edit();
		wait(2);
		
		editRoleDialog
			.readPermissions()
			.forEach(c->{
				Assert.assertTrue(data.get("permissions").contains(c));
			});
		
		editRoleDialog.close();
	}
	
	/**
	 * MAD-ADMIN-65
	 */
	@Test(priority = 5, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void delete_an_existing_role(JsonData data) {
		roleList.search(data.get("name"));
		wait(3);
		
		Optional<Role> roleOptional = roleList
			.all()
			.stream()
			.filter(r->r.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(roleOptional.isPresent(), "Role is not found");
		
		DeleteConfirmDialog deleteConfirmDialog = roleOptional.get().delete();
		deleteConfirmDialog.delete();
		
		Assert.assertEquals(snackBar.readMessage(), "Roles deleted successfully.");
		snackBar.close();
		
		roleList.search(data.get("name"));
		wait(2);
		
		boolean isDeleted = roleList
			.all()
			.stream()
			.anyMatch(r->r.name.equals(data.get("name")));
		
		Assert.assertFalse(isDeleted, "Role is not deleted");
	}
}
