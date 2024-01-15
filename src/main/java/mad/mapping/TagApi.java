package mad.mapping;

import mad.pages.permission.PermissionCategoryDetails;
import org.openqa.selenium.WebElement;

/**
 * Mapping to Permission Category UI Table Row
 */
public class TagApi {
	public final String name;
	public final String category;
	public final String status;
	public final String createdAt;
	
	private final WebElement viewButton;
	
	public TagApi(
		String name,
		String category,
		String status,
		String createdAt,
		WebElement viewButton
	) {
		this.name = name;
		this.category = category;
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
