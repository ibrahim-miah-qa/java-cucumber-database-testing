package mad.components.common;

import light.automate.core.pages.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteConfirmDialog extends Page {
	public DeleteConfirmDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(css = "div.MuiDialog-container h2")
	private WebElement message;
	
	@FindBy(css = "div.MuiDialog-container button:nth-child(1)")
	private WebElement cancelButton;
	
	@FindBy(css = "div.MuiDialog-container button:nth-child(2)")
	private WebElement confirmButton;
	
	public String message() {
		waitForVisible(message);
		return message.getText().trim();
	}
	
	public void cancel() {
		waitForVisible(cancelButton);
		waitForClickable(cancelButton);
		cancelButton.click();
	}
	
	public void delete() {
		waitForVisible(confirmButton);
		waitForClickable(confirmButton);
		confirmButton.click();
	}
}
