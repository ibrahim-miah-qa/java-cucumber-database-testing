package mad.pages.permission;

import light.automate.core.pages.Page;
import mad.components.common.DeleteConfirmDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PermissionCategoryDetails extends Page {
	public PermissionCategoryDetails() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(xpath = "//button[normalize-space()= 'Edit Details']")
	private WebElement editDetailsButton;
	
	@FindBy(xpath = "//button[normalize-space()= 'Delete']")
	private WebElement deleteButton;
	
	@FindBy(xpath = "//button[contains(text(), 'Go to back')]")
	private WebElement backButton;
	
	
	public PermissionCategoryEditDialog edit() {
		waitForVisible(editDetailsButton);
		waitForClickable(editDetailsButton);
		editDetailsButton.click();
		wait(3);
		return new PermissionCategoryEditDialog();
	}
	
	public DeleteConfirmDialog delete() {
		waitForVisible(deleteButton);
		waitForClickable(deleteButton);
		deleteButton.click();
		wait(3);
		return new DeleteConfirmDialog();
	}
	
	public void back() {
		waitForVisible(backButton);
		waitForClickable(backButton);
		backButton.click();
	}
}
