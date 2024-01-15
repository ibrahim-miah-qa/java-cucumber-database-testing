package mad.components.common;

import light.automate.core.pages.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SnackBar extends Page {
	public SnackBar() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(css = ".MuiAlert-message")
	private WebElement message;
	
	@FindBy(css = ".MuiAlert-action")
	private WebElement closeButton;
	
	public String readMessage() {
		waitForVisible(message);
        return message.getText().trim();
	}
	
	public void close() {
		waitForVisible(closeButton);
		waitForClickable(closeButton);
		closeButton.click();
	}
}
