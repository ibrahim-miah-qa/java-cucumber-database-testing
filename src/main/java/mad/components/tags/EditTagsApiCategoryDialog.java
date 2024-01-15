package mad.components.tags;

import light.automate.core.pages.Page;
import mad.components.common.ChangeStatusDialog;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditTagsApiCategoryDialog extends Page {
	public EditTagsApiCategoryDialog() {
		PageFactory.initElements(browser. driver(), this);
	}
	
	@FindBy(id = "tagCategoryName")
	private WebElement nameField;
	
	@FindBy(id = "tagCategoryDesc")
	private WebElement descriptionField;
	
	@FindBy(xpath = "//button[contains(text(), 'Create Tag Category')]")
	private WebElement createButton;
	
	@FindBy(xpath = "//button[text()='Save Changes']")
	private WebElement saveChanges;
	
	@FindBy(xpath = "//span[normalize-space()= 'Category name is required.']")
	private WebElement nameErrorMessage;
	
	@FindBy(css = "div.MuiDialog-container h2+button")
	private WebElement closeButton;
	
	@FindBy(css = "input[name=isActive]")
	private WebElement activeToggle;
	
	public EditTagsApiCategoryDialog writeName(String name) {
		waitForVisible(this.nameField);
		write(this.nameField, name);
		return this;
	}
	
	public EditTagsApiCategoryDialog writeDescription(String description) {
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
	
	public EditTagsApiCategoryDialog clearName() {
		waitForVisible(nameField);
		nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		nameField.sendKeys(Keys.BACK_SPACE);
		return this;
	}
	
	public EditTagsApiCategoryDialog clearDescription() {
		waitForVisible(descriptionField);
		descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		descriptionField.sendKeys(Keys.BACK_SPACE);
		return this;
	}
	
	public ChangeStatusDialog changeStatus() {
		wait(3);
		activeToggle.click();
		return new ChangeStatusDialog();
	}
	
	public void saveChanges() {
		saveChanges.click();
	}
	
	public boolean isErrorMessageVisible() {
		return nameErrorMessage.isDisplayed();
	}
	
	public void close() {
		waitForClickable(closeButton);
		closeButton.click();
	}
}
