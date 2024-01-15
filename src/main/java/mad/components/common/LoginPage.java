package mad.components.common;

import light.automate.core.pages.Page;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends Page {
	public LoginPage() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(id = "email")
	private WebElement emailField;
	
	@FindBy(id = "password")
	private WebElement passwordField;
	
	@FindBy(xpath = "//button[text()='Login']")
	private WebElement loginButton;
	
	@FindBy(css = "input")
	private List<WebElement> otpFields;
	
	@FindBy(xpath = "//button[text()='Verify OTP']")
	private WebElement verifyOtpButton;
	
	public LoginPage writeEmail(String email) {
		waitForVisible(emailField);
		waitForClickable(emailField);
		emailField.sendKeys(email);
		return this;
	}
	
	public LoginPage writePassword(String password) {
		waitForVisible(passwordField);
		waitForClickable(passwordField);
		passwordField.sendKeys(password);
		return this;
	}
	
	public LoginPage clickOnLoginButton() {
		waitForClickable(loginButton);
		loginButton.click();
		return this;
	}
	
	public LoginPage writeOtp(String otp) {
		fluentWait.until(ExpectedConditions.visibilityOfAllElements(otpFields));
		int index = 0;
		for (WebElement otpField : otpFields) {
			otpField.sendKeys(""+otp.charAt(index++));
		}
		
		return this;
	}
	
	public LoginPage verifyOtp() {
		waitForClickable(verifyOtpButton);
		verifyOtpButton.click();
		return this;
	}
	
	public void loginWithApi(String s, String s1) {
		OkHttpClient client = new OkHttpClient();
	}
}
