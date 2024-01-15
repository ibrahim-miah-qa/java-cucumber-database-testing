package mad.components.role;

import light.automate.core.pages.Page;
import mad.pages.permission.CreateEditPermissionDialog;
import mad.pages.permission.PermissionCategoryAddDialog;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// todo rename to CreateRoleCategoryDialog
public class CreateEditRoleCategoryDialog extends Page {
	public CreateEditRoleCategoryDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(id = "roleCategoryName")
	private WebElement nameField;
	
	@FindBy(id = "roleCategoryDesc")
	private WebElement descriptionField;
	
	@FindBy(xpath = "//button[contains(text(), 'Create Roles Category')]")
	private WebElement createButton;
	
	@FindBy(xpath = "//button[text()='Save Changes']")
	private WebElement saveChanges;
	
	
	public CreateEditRoleCategoryDialog writeName(String name) {
		waitForVisible(this.nameField);
		write(this.nameField, name);
		return this;
	}
	
	public CreateEditRoleCategoryDialog writeDescription(String description) {
		waitForVisible(this.descriptionField);
		write(this.descriptionField, description);
		return this;
	}
	
	public void create() {
		waitForClickable(createButton);
		click(createButton);
	}
	
	public String readName() {
		waitForVisible(nameField);
		return nameField.getAttribute("value");
	}
	
	public CreateEditRoleCategoryDialog clearName() {
		waitForVisible(nameField);
		nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		nameField.sendKeys(Keys.BACK_SPACE);
		return this;
	}
	
	public void saveChanges() {
		saveChanges.click();
	}
}
