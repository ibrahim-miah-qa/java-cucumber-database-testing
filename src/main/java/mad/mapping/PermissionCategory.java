package mad.mapping;

import mad.components.common.DeleteConfirmDialog;
import mad.pages.permission.PermissionCategoryDetails;
import org.openqa.selenium.WebElement;

/**
 * Mapping to Permission Category UI Table Row
 */
public class PermissionCategory {
	public final String name;
	public final String description;
	public final String status;
	public final String createdAt;
	
	private final WebElement viewButton;
	
	public PermissionCategory(
		String name,
		String description,
		String status,
		String createdAt,
		WebElement viewButton
	) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.viewButton = viewButton;
	}
	
//	public void changeStatus() {
//		changeStatusButton.click();
//	}
	
//	public DeleteConfirmDialog delete() {
//		deleteButton.click();
//		return new DeleteConfirmDialog();
//	}
	
	public PermissionCategoryDetails view() {
		viewButton.click();
		return new PermissionCategoryDetails();
	}
}
