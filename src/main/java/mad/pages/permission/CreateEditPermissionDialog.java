package mad.pages.permission;

import light.automate.core.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CreateEditPermissionDialog extends Page {
	public CreateEditPermissionDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	
	@FindBy(css = "input#permissionName")
	private WebElement nameField;
	
	@FindBy(css = "input#permissionDesc")
	private WebElement descriptionField;
	
	@FindBy(css = "input#permissionTags")
	private WebElement tagField;
	
	
	@FindBy(css = "input#permissionCategoryID")
	private WebElement categoryField;
	
	@FindBy(css = "input#permissionCategoryID+div button")
	private WebElement clearAllButton;
	
	@FindBy(css = "input#permissionAPI+div button")
	private WebElement clearAllActionButton;
	
	@FindBy(css = "input#permissionAPI")
	private WebElement actionField;
	
	@FindBy(xpath = "//button[text()='Create Permission']")
	private WebElement createButton;
	
	@FindBy(xpath = "//button[text()='Save Changes']")
	private WebElement saveChanges;
	
	@FindBy(xpath = "//label[text()='Permission Categories']/following-sibling::div//span[contains(@class,'MuiChip-label')]")
	private List<WebElement> selectedCategories;
	
	@FindBy(xpath = "//label[text()='Permission APIs']/following-sibling::div//span[contains(@class,'MuiChip-label')]")
	private List<WebElement> selectedActions;
	
	
	public CreateEditPermissionDialog writeName(String name) {
		waitForVisible(nameField);
		nameField.sendKeys(name);
		return this;
	}
	public void clearName() {
		waitForVisible(nameField);
		nameField.click();
		nameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
	}
	
	public String readName() {
		waitForVisible(nameField);
		return nameField.getAttribute("value");
	}
	
	public void writeDescription(String description) {
		waitForVisible(descriptionField);
		descriptionField.sendKeys(description);
	}
	
	public void writeTag(String... tags) {
		waitForVisible(tagField);
		for (String tag : tags) {
			tagField.sendKeys(tag);
			tagField.sendKeys(Keys.ENTER);
		}
	}
	
	public void selectCategory(String... categories) {
		waitForVisible(categoryField);
		waitForClickable(categoryField);
		categoryField.click();
		for (String category : categories) {
			categoryField.sendKeys(category);
			browser
				.driver()
				.findElement(By.xpath(String.format("//li[normalize-space()='%s']", category)))
				.click();
		}
		categoryField.click();
	}
	
	public void selectAction(String... categories) {
		actionField.click();
		for (String action : categories) {
			actionField.sendKeys(action);
			browser
				.driver()
				.findElement(By.xpath(String.format("//li[normalize-space()='%s']", action)))
				.click();
		}
		actionField.click();
	}
	
	public void create() {
		createButton.click();
	}
	
	public void saveChanges() {
		saveChanges.click();
	}
	
	public List<String> readCategories() {
		return selectedCategories
			.stream()
			.map(c->c.getText().trim())
			.collect(Collectors.toList());
	}
	
	public void removeAllCategories() {
		categoryField.click();
		waitForVisible(clearAllButton);
		clearAllButton.click();
	}
	
	public List<String> readActions() {
		return selectedActions
			.stream()
			.map(c->c.getText().trim())
			.collect(Collectors.toList());
	}

	public void removeAllActions() {
		actionField.click();
		waitForVisible(clearAllActionButton);
		clearAllActionButton.click();
	}
	
}
