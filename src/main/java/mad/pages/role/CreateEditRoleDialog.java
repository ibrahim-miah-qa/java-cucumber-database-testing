package mad.pages.role;

import light.automate.core.pages.Page;
import mad.pages.permission.CreateEditPermissionDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CreateEditRoleDialog extends Page {
	public CreateEditRoleDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(id = "rolesName")
	private WebElement nameField;
	
	@FindBy(id = "rolesDesc")
	private WebElement descriptionField;
	
	@FindBy(id = "rolesTags")
	private WebElement tagField;
	
	
	@FindBy(id = "rolesCategoryID")
	private WebElement categoryField;
	
	
	@FindBy(id = "rolesPermissionID")
	private WebElement permissionField;
	
	@FindBy(css = "input#rolesPermissionID+div button")
	private WebElement clearAllPermissionButton;
	
	@FindBy(css = "input#rolesCategoryID+div button")
	private WebElement clearAllCategoryButton;
	
	
	
	@FindBy(xpath = "//button[text()='Create Role']")
	private WebElement createButton;
	
	@FindBy(css = "h2.MuiDialogTitle-root+button")
	private WebElement closeButton;
	
	@FindBy(xpath = "//button[text()='Save Changes']")
	private WebElement saveChanges;
	
	@FindBy(xpath = "//label[text()='Roles Categories']/following-sibling::div//span[contains(@class,'MuiChip-label')]")
	private List<WebElement> selectedCategories;
	
	@FindBy(xpath = "//label[text()='Roles Permissions']/following-sibling::div//span[contains(@class,'MuiChip-label')]")
	private List<WebElement> selectedPermission;
	
	
	public CreateEditRoleDialog writeName(String name) {
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
	
	public void selectPermissions(String... categories) {
		permissionField.click();
		for (String action : categories) {
			permissionField.sendKeys(action);
			browser
				.driver()
				.findElement(By.xpath(String.format("//li[normalize-space()='%s']", action)))
				.click();
		}
		permissionField.click();
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
		waitForVisible(clearAllCategoryButton);
		clearAllCategoryButton.click();
	}
	
	public void removeAllPermission() {
		permissionField.click();
		waitForVisible(clearAllPermissionButton);
		clearAllPermissionButton.click();
	}
	
	public void close() {
		closeButton.click();
	}
	
	public List<String> readPermissions() {
		return selectedPermission
			.stream()
			.map(c->c.getText().trim())
			.collect(Collectors.toList());
	}
}
