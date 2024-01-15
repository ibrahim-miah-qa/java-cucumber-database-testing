package mad.mapping;

import mad.components.common.ChangeStatusDialog;
import mad.components.common.DeleteConfirmDialog;
import mad.pages.role.CreateEditRoleDialog;
import org.openqa.selenium.WebElement;

public class Role {
	public final String name;
	public final String description;
	public final String status;
	public final String createdAt;
	
	private final WebElement editButton, changeStatusButton, deleteButton;
	
	public Role(
		String name,
		String description,
		String status,
		String createdAt,
		WebElement changeStatusButton,
		WebElement editButton,
		WebElement deleteButton
	) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.editButton = editButton;
		this.changeStatusButton = changeStatusButton;
		this.deleteButton = deleteButton;
	}
	
	public ChangeStatusDialog changeStatus() {
		changeStatusButton.click();
		return new ChangeStatusDialog();
	}
	
	public DeleteConfirmDialog delete() {
		deleteButton.click();
		return new DeleteConfirmDialog();
	}
	
	public CreateEditRoleDialog edit() {
		editButton.click();
		return new CreateEditRoleDialog();
	}
}
