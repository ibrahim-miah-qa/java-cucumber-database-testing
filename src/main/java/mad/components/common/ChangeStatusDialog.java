package mad.components.common;

import light.automate.core.pages.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangeStatusDialog extends Page {
	public ChangeStatusDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(css = "(//div[contains(@class,'MuiDialog-container')]//h2)[2]")
	private WebElement message;
	
	@FindBy(xpath = "(//h2[text()='Are you sure to change status?']/following-sibling::div//button)[1]")
	private WebElement cancelButton;
	
	@FindBy(xpath = "(//h2[text()='Are you sure to change status?']/following-sibling::div//button)[2]")
	private WebElement confirmButton;
	
	public void cancel() {
		waitForVisible(cancelButton);
		cancelButton.click();
	}
	
	public void confirm() {
		waitForVisible(confirmButton);
		waitForClickable(confirmButton);
		confirmButton.click();
	}
	
	public String message() {
		waitForVisible(message);
		return message.getText().trim();
	}
}
