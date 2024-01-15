package mad.repository;

import mad.pages.permission.PermissionCategoryList;
import mad.pages.permission.PermissionCategoryAddDialog;

public class PermissionCategoryRepository {
	private final PermissionCategoryAddDialog addDialog = new PermissionCategoryAddDialog();
	private final PermissionCategoryList categoryList = new PermissionCategoryList();
	
	public void addPermissionCategory(String name, String description) {
	
	}
	public void deletePermissionCategory(String name) {}
}
