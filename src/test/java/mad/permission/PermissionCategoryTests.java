package mad.permission;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import mad.Mad;
import mad.components.common.ChangeStatusDialog;
import mad.components.common.DeleteConfirmDialog;
import mad.mapping.PermissionCategory;
import mad.pages.permission.PermissionCategoryAddDialog;
import mad.pages.permission.PermissionCategoryDetails;
import mad.pages.permission.PermissionCategoryEditDialog;
import mad.pages.permission.PermissionCategoryList;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

@Listeners
public class PermissionCategoryTests extends Mad {
	
	/**
	 * case id : TMAD-ADMIN-20
	 */
	@Test(priority = 0, dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void create_permission_category_with_valid_name_when_name_has_less_then_65_characters(JsonData data) {
		PermissionCategoryList permissionCategoryList = new PermissionCategoryList();
		
		// move to the permission category page
		permissionCategoryList.open();

		wait(4);
		
		// add new permission category <category>, <description> and <message>
		PermissionCategoryAddDialog addDialog = permissionCategoryList.createNew();
		addDialog.writeName(data.get("name"));
		addDialog.writeDescription(data.get("description"));
		addDialog.create();
		
		String message = snackBar.readMessage();
		//snackBar.close();
		Assert.assertEquals(message, "Permission Category created successfully.", "Snack bar message not matched for create permission category");
		
		// search permission category <category>
		permissionCategoryList.search(data.get("name"));
		
		wait(2);
		
		//validate permission category <category> and <description>
		 List<PermissionCategory> allCategories = permissionCategoryList
			.all();
		
		boolean found = allCategories.stream()
			.map(c-> c.name.equals(data.get("name")))
			.findAny()
			.isPresent();
		
		Assert.assertTrue(found,"Permission category not found after created");
	}
	
	/**
	 * case id : TMAD-ADMIN-24
	 */
	@Test(priority = 1, enabled = true)
	public void search_permission_category_by_name() {
		String name = "govt-permissions";
		String description = "category for status";
		
		PermissionCategoryList permissionCategoryList = new PermissionCategoryList();
		
		permissionCategoryList.open();
		
		// search permission category <category>
		permissionCategoryList.search(name);
		
		wait(2);
		
		//validate permission category <category> and <description>
		List<PermissionCategory> allCategories = permissionCategoryList
			.all();
		
		org.junit.Assert.assertEquals("Only one permission category should display after search", allCategories.size(), 1);
		
		boolean found = allCategories.stream()
			.map(c-> c.name.equals(name) && c.description.equals(description))
			.findAny()
			.isPresent();
		
		Assert.assertTrue(found,"Permission category not found after created");
	}
	
	
	/**
	 * case id : TMAD-ADMIN-26
	 */
	@Test(priority = 2, enabled = true)
	public void change_active_to_inactive_status_of_a_permission_category() {
		String name = "userpermissions";
		PermissionCategoryList permissionCategoryList = new PermissionCategoryList();
		
		permissionCategoryList.open();
		
		// search permission category <category>
		permissionCategoryList.search(name);
		
		wait(2);
		
		Optional<PermissionCategory> optionalPermissionCategory = permissionCategoryList
			.all()
			.stream()
			.filter(c->c.name.equals(name))
			.findFirst();
		
		Assert.assertTrue(optionalPermissionCategory.isPresent(), "Permission Category not found");
		
		PermissionCategoryDetails permissionCategoryDetails = optionalPermissionCategory
			.get()
			.view();
		wait(3);
		
		PermissionCategoryEditDialog editDialog = permissionCategoryDetails
			.edit();
		
		editDialog
			.changeStatus()
			.confirm();
		
		String message = snackBar.readMessage();
		Assert.assertEquals(message, "Permission Category deactivated successfully.", "Change status message not matched");
		
		editDialog.close();
		
		// back to permission category list page
		permissionCategoryDetails.back();
		
		permissionCategoryList.search(name);
		wait(2);
		
		boolean found = permissionCategoryList.all().stream().anyMatch(c->c.name.equals(name) && c.status.equals("In Active"));
		
		Assert.assertTrue(found, "Status not changed to In Active");
	}
	
	
	/**
	 * case id : TMAD-ADMIN-27
	 */
	@Test(priority = 3, enabled = true)
	public void change_inactive_to_active_status_of_a_permission_category() {
		String name = "userpermissions";
		PermissionCategoryList permissionCategoryList = new PermissionCategoryList();
		
		permissionCategoryList.open();
		
		// search permission category <category>
		permissionCategoryList.search(name);
		
		wait(2);
		
		Optional<PermissionCategory> optionalPermissionCategory = permissionCategoryList
			.all()
			.stream()
			.filter(c->c.name.equals(name))
			.findFirst();
		
		Assert.assertTrue(optionalPermissionCategory.isPresent(), "Permission Category not found");
		
		PermissionCategoryDetails details = optionalPermissionCategory.get().view();
		
		wait(3);
		PermissionCategoryEditDialog editDialog = details.edit();
		
		editDialog.changeStatus().confirm();
		
		String message = snackBar.readMessage();
		Assert.assertEquals(message, "Permission Category activated successfully.", "Change status message not matched");
		
		editDialog.close();
		details.back();
		
		permissionCategoryList.search(name);
		wait(2);
		
		boolean found = permissionCategoryList.all().stream().anyMatch(c->c.name.equals(name) && c.status.equals("Active"));
		
		Assert.assertTrue(found, "Status not changed to Active");
	}
	
	/**
	 * case id : TMAD-ADMIN-31
	 */
	@Test(priority = 4, enabled = true)
	public void delete_permission_category_when_no_permission_is_assigned_to_it() {
		String name = "categoryfordelete";
		PermissionCategoryList permissionCategoryList = new PermissionCategoryList();
		
		permissionCategoryList.open();
		
		permissionCategoryList.search(name);
		
		wait(2);
		
		Optional<PermissionCategory> optionalPermissionCategory = permissionCategoryList
			.all()
			.stream()
			.filter(c->c.name.equals(name))
			.findFirst();
		
		Assert.assertTrue(optionalPermissionCategory.isPresent(), "Permission Category not found");
		
		PermissionCategoryDetails details = optionalPermissionCategory.get().view();
		
		DeleteConfirmDialog confirmDialog = details.delete();
		
		confirmDialog.delete();
		
		String message = snackBar.readMessage();
		Assert.assertEquals(message, "Permission Category deleted successfully.", "Change status message not matched");
		
		permissionCategoryList.search(name);
		wait(2);
		
		boolean found = permissionCategoryList.all().stream().anyMatch(c->c.name.equals(name));
		
		Assert.assertFalse(found, "Category exists after deleted");
	}
}
