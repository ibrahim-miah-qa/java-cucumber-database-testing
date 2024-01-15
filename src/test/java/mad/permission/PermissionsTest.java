package mad.permission;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import light.automate.listener.TestListener;
import mad.Mad;
import mad.components.common.ChangeStatusDialog;
import mad.components.common.LoginPage;
import mad.mapping.Permission;
import mad.pages.permission.CreateEditPermissionDialog;
import mad.pages.permission.PermissionList;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Listeners(TestListener.class)
public class PermissionsTest extends Mad {
	
	/**
	 * case id : TMAD-ADMIN-34
	 */
	@Test(priority = 1, dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void create_permission_with_name_category_and_api_action(JsonData data) {
		PermissionList permissionList = new PermissionList();
		permissionList.open();
		
		wait(2);
		CreateEditPermissionDialog createEditPermissionDialog = permissionList.createNew();
		
		createEditPermissionDialog.writeName(data.get("name"));
		createEditPermissionDialog.writeDescription(data.get("description"));
		createEditPermissionDialog.writeTag(data.get("tags").split(","));
		createEditPermissionDialog.selectCategory(data.get("categories").split(","));
		createEditPermissionDialog.selectAction(data.get("actions").split(","));
		createEditPermissionDialog.create();
		
		String message = snackBar.readMessage();
		//snackBar.close();
		
		Assert.assertEquals(message, "Permission created successfully.", "Create Permission snack bar message not matched");
		
		permissionList.search(data.get("name"));
		wait(3);
		
		boolean isCreated = permissionList.all()
			.stream()
			.anyMatch(p->p.name.equals(data.get("name")));
		
		Assert.assertTrue(isCreated, "Permission is not created");
	}
	
	/**
	 * case id : TMAD-ADMIN-52
	 * name : search by permission name
	 */
	@Test(priority = 2, enabled = false)
	public void search_by_permission_name() {
		String name = "government admin user permission";
		PermissionList permissionList = new PermissionList();
		
		permissionList.open();
		
		permissionList.search(name);
		
		wait(2);
		
		boolean found = permissionList
			.all()
			.stream().anyMatch(c->c.name.equals(name));
		
		Assert.assertTrue(found, "Permission not found");
	}
	
	/**
	 * case id : TMAD-ADMIN-43
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 3, enabled = false)
	public void edit_permission_to_update_name(JsonData data) {
		PermissionList permissionList = new PermissionList();
		permissionList.open();
		wait(2);
		
		permissionList.search(data.get("old_name"));
		wait(2);
		Optional<Permission> optionalPermission = permissionList.all().stream().filter(p-> p.name.equals(data.get("old_name"))).findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission not found!");
		
		CreateEditPermissionDialog createEditPermissionDialog = optionalPermission.get().edit();
		
		wait(3);
		
		String oldName = createEditPermissionDialog.readName();
		
		Assert.assertEquals(oldName, data.get("old_name"), "existing name not matched");
		
		createEditPermissionDialog.clearName();
		createEditPermissionDialog.writeName(data.get("name"));
		
		createEditPermissionDialog.saveChanges();
		
		String message = snackBar.readMessage();
		snackBar.close();
		
		Assert.assertEquals(message, "Permission updated successfully.", "Create Permission snack bar message not matched");
		
		permissionList.search(data.get("name"));
		wait(3);
		
		boolean isUpdated = permissionList.all()
			.stream()
			.anyMatch(p->p.name.equals(data.get("name")));
		
		Assert.assertTrue(isUpdated, "Permission is not updated");
	}
	
	
	/**
	 * case id : TMAD-ADMIN-44
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 4, enabled = false)
	public void edit_permission_to_update_category(JsonData data) {
		PermissionList permissionList = new PermissionList();
		permissionList.open();
		wait(2);
		
		permissionList.search(data.get("name"));
		wait(2);
		Optional<Permission> optionalPermission = permissionList.all().stream().filter(p-> p.name.equals(data.get("name"))).findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission not found!");
		
		CreateEditPermissionDialog createEditPermissionDialog = optionalPermission.get().edit();
		
		wait(3);
		
		String oldName = createEditPermissionDialog.readName();
		List<String> oldCategories = createEditPermissionDialog.readCategories();
		
		Arrays
			.stream(data.get("old_categories").split(","))
			.forEach(c->{
				Assert.assertTrue(oldCategories.contains(c), "Existing category not matched");
			});
		Assert.assertEquals(oldName, data.get("name"), "existing name not matched");
		
		
		// change category
		createEditPermissionDialog.removeAllCategories();
		wait(2);
		createEditPermissionDialog.selectCategory(data.get("categories"));
		
		createEditPermissionDialog.saveChanges();
		
		String message = snackBar.readMessage();
		snackBar.close();
		
		Assert.assertEquals(message, "Permission updated successfully.", "Create Permission snack bar message not matched");
		
		permissionList.search(data.get("name"));
		wait(2);
		
		optionalPermission = permissionList.all()
			.stream()
			.filter(p->p.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission is not found after update category");
		
		createEditPermissionDialog = optionalPermission.get().edit();
		
		wait(3);
		
		List<String> updatedCategories = createEditPermissionDialog.readCategories();
		Arrays
			.stream(data.get("categories").split(","))
			.forEach(c->{
				Assert.assertTrue(updatedCategories.contains(c), "Updated category not matched");
			});
	}
	
	/**
	 * case id : TMAD-ADMIN-45
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 5, enabled = false)
	public void edit_permission_to_update_api_action (JsonData data) {
		PermissionList permissionList = new PermissionList();
		permissionList.open();
		wait(2);
		
		permissionList.search(data.get("name"));
		wait(2);
		Optional<Permission> optionalPermission = permissionList
			.all()
			.stream()
			.filter(p-> p.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission not found!");
		
		CreateEditPermissionDialog createEditPermissionDialog = optionalPermission.get().edit();
		
		wait(3);
		
		String oldName = createEditPermissionDialog.readName();
		List<String> oldActions = createEditPermissionDialog.readActions();
		
		Arrays
			.stream(data.get("old_action").split(","))
			.forEach(c->{
				Assert.assertTrue(oldActions.contains(c), "Existing actions not matched");
			});
		Assert.assertEquals(oldName, data.get("name"), "existing name not matched");
		
		
		// change category
		createEditPermissionDialog.removeAllActions();
		wait(2);
		createEditPermissionDialog.selectAction(data.get("action"));
		
		createEditPermissionDialog.saveChanges();
		
		String message = snackBar.readMessage();
		snackBar.close();
		
		Assert.assertEquals(message, "Permission updated successfully.", "Create Permission snack bar message not matched");
		
		permissionList.search(data.get("name"));
		wait(3);
		
		optionalPermission = permissionList.all()
			.stream()
			.filter(p->p.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission is not found after update category");
		
		createEditPermissionDialog = optionalPermission.get().edit();
		
		wait(3);
		
		List<String> updatedCategories = createEditPermissionDialog.readActions();
		Arrays
			.stream(data.get("action").split(","))
			.forEach(c->{
				Assert.assertTrue(updatedCategories.contains(c), "Updated action not matched");
			});
	}
	
	/**
	 * case id : TMAD-ADMIN-51
	 */
	@Test(priority = 6, enabled = false)
	public void change_status_for_permission_to_inactive() {
		String name = "government readonly permission";
		PermissionList permissionList = new PermissionList();
		
		permissionList.open();
		
		permissionList.search(name);
		wait(3);
		
		Optional<Permission> optionalPermission = permissionList
			.all()
			.stream()
			.filter(c->c.name.equals(name))
			.findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission not found");
		
		ChangeStatusDialog changeStatusDialog = optionalPermission
			.get()
			.changeStatus();
		
		changeStatusDialog.confirm();;
		
		String message = snackBar.readMessage();
		snackBar.close();
		
		Assert.assertEquals(message, "Permission deactivated successfully", "Change status message of Permission not matched");
		
		permissionList.search(name);
		wait(3);
		
		boolean isActived = permissionList
			.all()
			.stream().anyMatch(c->c.name.equals(name) && c.status.equals("In Active"));
		
		Assert.assertTrue(isActived, "Permission Not found with status In Active");
	}
	
	/**
	 * case id : TMAD-ADMIN-50
	 */
	@Test(priority = 7, enabled = false)
	public void change_status_for_permission_to_active() {
		String name = "government readonly permission";
		PermissionList permissionList = new PermissionList();
		
		permissionList.open();
		
		permissionList.search(name);
		wait(3);
		
		Optional<Permission> optionalPermission = permissionList
			.all()
			.stream().filter(c->c.name.equals(name)).findAny();
		
		Assert.assertTrue(optionalPermission.isPresent(), "Permission not found");
		
		ChangeStatusDialog changeStatusDialog = optionalPermission
			.get()
			.changeStatus();
		
		changeStatusDialog.confirm();;
		
		String message = snackBar.readMessage();
		snackBar.close();
		
		Assert.assertEquals(message, "Permission activated successfully", "Change status message of Permission not matched");
		
		permissionList.search(name);
		wait(3);
		
		boolean isDeactived = permissionList
			.all()
			.stream().anyMatch(c->c.name.equals(name) && c.status.equals("Active"));
		
		Assert.assertTrue(isDeactived, "Permission Status not changed to Active");
	}
}
