package mad.role;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import mad.Mad;
import mad.components.common.ChangeStatusDialog;
import mad.components.common.DeleteConfirmDialog;
import mad.components.role.CreateEditRoleCategoryDialog;
import mad.mapping.RoleCategory;
import mad.pages.role.RoleCategoryList;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Optional;

@Listeners
public class RoleCategoryTest extends Mad {
	/**
	 * MAD-ADMIN-05
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 1)
	public void create_role_category_with_valid_name_when_name_has_less_then_65_characters(JsonData data) {
		RoleCategoryList roleCategoryList = new RoleCategoryList();
		
		roleCategoryList.open();
		wait(2);
		
		CreateEditRoleCategoryDialog createEditRoleDialog = roleCategoryList.createNew();
		
		createEditRoleDialog
			.writeName(data.get("name"))
			.writeDescription(data.get("description"))
			.create();
		
		String message = snackBar.readMessage();
		snackBar.close();
		
		Assert.assertEquals(message, "Role Category created successfully.", "Role Create permission snackbar message not message");
		
		roleCategoryList.search(data.get("name"));
		
		boolean isCreated = roleCategoryList
			.all()
			.stream()
			.anyMatch(c-> c.name.equals(data.get("name")));
		
		Assert.assertTrue(isCreated, "Role Category not created");
	}
	
	/**
	 * MAD-ADMIN-08
	 */
	@Test(priority = 2)
	public void search_role_category_by_name() {
		String name = "Read only";
		RoleCategoryList roleCategoryList = new RoleCategoryList();
		
		roleCategoryList.open();
		wait(2);
		
		roleCategoryList.search(name);
		
		boolean isCreated = roleCategoryList
			.all()
			.stream()
			.anyMatch(c-> c.name.equals(name));
		
		Assert.assertTrue(isCreated, "Role Category not found");
	}
	
	/**
	 * MAD-ADMIN-10
	 */
	@Test(priority = 3)
	public void change_active_to_inactive_status_of_a_role_category() {
		String name = "Writer";
		RoleCategoryList roleCategoryList = new RoleCategoryList();
		
		roleCategoryList.open();
		wait(2);
		
		roleCategoryList.search(name);
		
		Optional<RoleCategory> roleCategoryOptional = roleCategoryList
			.all()
			.stream()
			.filter(c-> c.name.equals(name))
			.findFirst();
		
		Assert.assertTrue(roleCategoryOptional.isPresent(), "Role Category not found");
		
		ChangeStatusDialog changeStatusDialog = roleCategoryOptional.get().changeStatus();
		changeStatusDialog.confirm();
		
		Assert.assertEquals(snackBar.readMessage(), "Category deactivated successfully");
		snackBar.close();
		wait(2);
		
		roleCategoryList.search(name);
		boolean isStatusChanged = roleCategoryList
			.all()
			.stream()
			.anyMatch(c-> c.name.equals(name) && c.status.equals("In Active"));
		
		Assert.assertTrue(isStatusChanged, "Role Category Status not matched");
	}
	
	/**
	 * MAD-ADMIN-81
	 */
	@Test(priority = 4)
	public void change_inactive_to_active_status_of_a_role_category() {
		String name = "Writer";
		RoleCategoryList roleCategoryList = new RoleCategoryList();
		
		roleCategoryList.open();
		wait(2);
		
		roleCategoryList.search(name);
		
		Optional<RoleCategory> roleCategoryOptional = roleCategoryList
			.all()
			.stream()
			.filter(c-> c.name.equals(name))
			.findFirst();
		
		Assert.assertTrue(roleCategoryOptional.isPresent(), "Role Category not found");
		
		ChangeStatusDialog changeStatusDialog = roleCategoryOptional.get().changeStatus();
		changeStatusDialog.confirm();
		Assert.assertEquals(snackBar.readMessage(), "Category activated successfully");
		snackBar.close();
		wait(2);
		
		roleCategoryList.search(name);
		boolean isStatusChanged = roleCategoryList
			.all()
			.stream()
			.anyMatch(c-> c.name.equals(name) && c.status.equals("Active"));
		
		Assert.assertTrue(isStatusChanged, "Role Category Status not matched");
	}
	
	/**
	 * MAD-ADMIN-11
 	 */
	@Test(priority = 5)
	public void update_role_category_name() {
		String oldName = "Writer";
		String name = "User Write permission";
		RoleCategoryList roleCategoryList = new RoleCategoryList();
		
		roleCategoryList.open();
		wait(2);
		
		roleCategoryList.search(oldName);
		
		Optional<RoleCategory> roleCategoryOptional = roleCategoryList
			.all()
			.stream()
			.filter(c-> c.name.equals(oldName))
			.findFirst();
		
		Assert.assertTrue(roleCategoryOptional.isPresent(), "Role Category not found");
		
		CreateEditRoleCategoryDialog createEditRoleDialog = roleCategoryOptional.get().edit();
		
		wait(2);
		
		String oldNameUI = createEditRoleDialog.readName();
		Assert.assertEquals(oldNameUI, oldName, "Existing name not matched");
		
		createEditRoleDialog
			.clearName()
			.writeName(name)
			.saveChanges();
		
		Assert.assertEquals(snackBar.readMessage(), "Role Category updated successfully.");
		snackBar.close();
		wait(2);
		
		roleCategoryList.search(name);
		wait(3);
		boolean isFound = roleCategoryList
			.all()
			.stream()
			.anyMatch(c-> c.name.equals(name) && c.status.equals("Active"));
		
		Assert.assertTrue(isFound, "Role Category name is not updated");
	}
	
	/**
	 * MAD-ADMIN-14
 	 */
	@Test(priority = 6)
	public void delete_role_category() {
		String name = "User Write permission";
		RoleCategoryList roleCategoryList = new RoleCategoryList();
		
		roleCategoryList.open();
		wait(2);
		
		roleCategoryList.search(name);
		
		Optional<RoleCategory> roleCategoryOptional = roleCategoryList
			.all()
			.stream()
			.filter(c-> c.name.equals(name))
			.findFirst();
		
		Assert.assertTrue(roleCategoryOptional.isPresent(), "Role Category not found");
		
		DeleteConfirmDialog deleteConfirmDialog = roleCategoryOptional.get().delete();
		
		deleteConfirmDialog.delete();
		
		Assert.assertEquals(snackBar.readMessage(), "Category deleted successfully.");
		snackBar.close();
		wait(2);
		
		roleCategoryList.search(name);
		wait(2);
		boolean isFound = roleCategoryList
			.all()
			.stream()
			.anyMatch(c-> c.name.equals(name));
		
		Assert.assertFalse(isFound, "Role Category should not present after deleted");
	}
}
