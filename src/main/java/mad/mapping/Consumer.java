package mad.mapping;

import mad.components.common.ChangeStatusDialog;
import mad.components.common.DeleteConfirmDialog;
import mad.pages.consumer.CreateEditConsumerDialog;
import mad.pages.permission.CreateEditPermissionDialog;
import org.openqa.selenium.WebElement;

public class Consumer {
	public final String name, email, phone, role, createdAt, status;
	
	
	private final WebElement editButton, changeStatusButton, deleteButton;
	
	public Consumer(
		String name,
		String email,
		String phone,
		String role,
		String createdAt,
		String status,
		WebElement changeStatusButton,
		WebElement editButton,
		WebElement deleteButton
	) {
		this.name = name;
		this.phone = phone;
		this.role = role;
		this.email = email;
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
	
	public CreateEditConsumerDialog edit() {
		editButton.click();
		return new CreateEditConsumerDialog();
	}
}
