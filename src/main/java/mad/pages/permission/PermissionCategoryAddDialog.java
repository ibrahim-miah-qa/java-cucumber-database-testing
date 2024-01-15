package mad.pages.permission;

import light.automate.core.pages.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PermissionCategoryAddDialog extends Page {
	public PermissionCategoryAddDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(css = "input[name='permissionCategoryName']")
	private WebElement nameField;
	
	@FindBy(css = "input[name='permissionCategoryDesc']")
	private WebElement descriptionField;
	
	@FindBy(xpath = "//button[contains(text(), 'Create Permission Category')]")
	private WebElement createButton;
	
	
	public PermissionCategoryAddDialog writeName(String name) {
		waitForVisible(this.nameField);
		write(this.nameField, name);
		return this;
	}
	
	public PermissionCategoryAddDialog writeDescription(String description) {
		waitForVisible(this.descriptionField);
		write(this.descriptionField, description);
		return this;
	}
	
	public void create() {
		waitForClickable(createButton);
		click(createButton);
	}
}
