package mad.pages.tags;

import light.automate.core.pages.Page;
import mad.components.common.ChangeStatusDialog;
import mad.pages.permission.PermissionCategoryEditDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TagApiCreateDialog extends Page {
	public TagApiCreateDialog() {
		PageFactory.initElements(browser.driver(), this);
		waitForVisible(closeButton);
	}
	
	
	@FindBy(id = "tagApiName")
	private WebElement nameField;
	
	@FindBy(id = "tagApiDesc")
	private WebElement descriptionField;
	
	@FindBy(id = "endPoint")
	private WebElement endpointField;
	
	@FindBy(id = "tags")
	private WebElement tagField;
	
	@FindBy(id = "tagsCategoryID")
	private WebElement tagCategoryField;
	
	@FindBy(xpath = "//button[normalize-space()='Create Tag Api']")
	private WebElement createButton;
	
//	@FindBy(css = "input[name=isActive]")
//	private WebElement activeToggle;
	
	@FindBy(css = "div.MuiDialog-container h2+button")
	private WebElement closeButton;
	
	@FindBy(xpath = "//span[normalize-space()='Endpoint is required.']")
	private WebElement endpointErrorMessage;
	
	@FindBy(xpath = "//p[normalize-space()='Tag Category is required.']")
	private WebElement categoryErrorMessage;
	
	@FindBy(xpath = "//span[normalize-space()='Tag Api name is required.']")
	private WebElement nameErrorMessage;
	
	public TagApiCreateDialog writeName(String name) {
		waitForVisible(this.nameField);
		waitForClickable(this.nameField);
		write(this.nameField, name);
		return this;
	}
	
	public TagApiCreateDialog writeDescription(String description) {
		waitForVisible(this.descriptionField);
		write(this.descriptionField, description);
		return this;
	}
	
	public TagApiCreateDialog writeEndpoint(String endpoint) {
		waitForVisible(this.endpointField);
		write(this.endpointField, endpoint);
		return this;
	}
	
	public TagApiCreateDialog selectCategory(String category) {
		write(this.tagCategoryField, category);
		browser
			.driver()
			.findElement(By.xpath(String.format("//li[normalize-space()=\"%s\"]", category)))
			.click();
		return this;
	}
	
	public void create() {
		waitForClickable(createButton);
		click(createButton);
	}
	
	public void close() {
		waitForClickable(this.closeButton);
		this.closeButton.click();
	}
	
	public TagApiCreateDialog writeTag(String... tags) {
		waitForVisible(tagField);
		for (String tag : tags) {
			tagField.sendKeys(tag);
			tagField.sendKeys(Keys.ENTER);
		}
		return this;
	}
	
	public boolean isEndpointMessageDisplayed() {
		return endpointErrorMessage.isDisplayed();
	}
	
	public boolean isNameMessageDisplayed() {
		return nameErrorMessage.isDisplayed();
	}
	
	public boolean isCategoryMessageDisplayed() {
		return categoryErrorMessage.isDisplayed();
	}
}
