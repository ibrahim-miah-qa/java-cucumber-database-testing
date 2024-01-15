package mad.pages.consumer;

import light.automate.core.pages.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateEditConsumerDialog extends Page {
	public CreateEditConsumerDialog() {
		PageFactory.initElements(browser.driver(), this);
	}
	@FindBy(id = "consumerName")
	private WebElement name;
	
	@FindBy(id = "consumerEmail")
	private WebElement email;
	
	@FindBy(id = "consumerMobile")
	private WebElement mobile;
	
	@FindBy(xpath = "//button[contains(text(), 'Save Changes')]")
	private WebElement saveChangesButton;
	
	@FindBy(css = "div.MuiDialog-container button:nth-child(1)")
	private WebElement addNewButton;
	
	@FindBy(css = "div.MuiDialog-container button:nth-child(2)")
	private WebElement closeButton;
	
	public CreateEditConsumerDialog writeName(String n) {
		waitForVisible(name);
		waitForClickable(name);
		name.sendKeys(n);
		return this;
	}
	public CreateEditConsumerDialog writeEmail(String e) {
		waitForVisible(email);
		waitForClickable(email);
		email.sendKeys(e);
		return this;
	}
	
	public CreateEditConsumerDialog writeMobile(String mob) {
		waitForVisible(mobile);
		waitForClickable(mobile);
		mobile.sendKeys(mob);
		return this;
	}
	
	public String readName() {
		waitForVisible(name);
		return mobile.getAttribute("value").trim();
	}
	public String readEmail() {
		waitForVisible(email);
		return email.getAttribute("value").trim();
	}
	
	public String readMobile() {
		waitForVisible(mobile);
		return mobile.getAttribute("value").trim();
	}
	
	public CreateEditConsumerDialog clearName() {
		name.clear();
		return this;
	}
	public CreateEditConsumerDialog clearEmail() {
		email.clear();
		return this;
	}
	
	public CreateEditConsumerDialog clearMobile() {
		mobile.clear();
		return this;
	}
	
	public void save() {
		waitForVisible(saveChangesButton);
		waitForClickable(saveChangesButton);
		saveChangesButton.click();
	}
	public void add() {
		waitForVisible(addNewButton);
		waitForClickable(addNewButton);
		addNewButton.click();
	}
	public void close() {
		waitForVisible(closeButton);
		waitForClickable(closeButton);
		closeButton.click();
	}
}
