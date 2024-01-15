package mad.pages.tags;

import light.automate.core.pages.Page;
import mad.components.common.DeleteConfirmDialog;
import mad.components.tags.EditTagsApiCategoryDialog;
import mad.pages.permission.PermissionCategoryEditDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TagCategoryDetails extends Page {
	public TagCategoryDetails() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(xpath = "//button[normalize-space()= 'Edit Details']")
	private WebElement editDetailsButton;
	
	@FindBy(xpath = "//button[normalize-space()= 'Delete']")
	private WebElement deleteButton;
	
	@FindBy(xpath = "//button[contains(text(), 'Go to back')]")
	private WebElement backButton;
	
	
	public EditTagsApiCategoryDialog edit() {
		waitForVisible(editDetailsButton);
		waitForClickable(editDetailsButton);
		editDetailsButton.click();
		wait(3);
		return new EditTagsApiCategoryDialog();
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
