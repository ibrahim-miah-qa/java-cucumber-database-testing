package mad.pages.permission;

import light.automate.core.pages.Page;
import mad.components.common.ChangeStatusDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PermissionCategoryEditDialog extends Page {
	public PermissionCategoryEditDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(css = "input[name='permissionCategoryName']")
	private WebElement nameField;
	
	@FindBy(css = "input[name='permissionCategoryDesc']")
	private WebElement descriptionField;
	
	@FindBy(xpath = "//button[contains(text(), 'Save Changes')]")
	private WebElement saveButton;
	
	@FindBy(css = "input[name=isActive]")
	private WebElement activeToggle;
	
	@FindBy(css = "div.MuiDialog-container h2+button")
	private WebElement closeButton;
	
	public PermissionCategoryEditDialog writeName(String name) {
		waitForVisible(this.nameField);
		write(this.nameField, name);
		return this;
	}
	
	public PermissionCategoryEditDialog writeDescription(String description) {
		waitForVisible(this.descriptionField);
		write(this.descriptionField, description);
		return this;
	}
	
	public void save() {
		waitForClickable(saveButton);
		click(saveButton);
	}
	
	public ChangeStatusDialog changeStatus() {
		wait(3);
		//waitForVisible(activeToggle);
		//waitForClickable(activeToggle);
		activeToggle.click();
		return new ChangeStatusDialog();
	}
	
	public void close() {
		waitForClickable(closeButton);
		closeButton.click();
	}
}
