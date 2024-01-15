package mad;

import light.automate.core.App;
import mad.components.common.LoginPage;
import mad.components.common.SnackBar;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Optional;

public class Mad extends App {
	
	protected SnackBar snackBar;
	
	Long timeStart = 0L;
	Long timeEnd = 0L;
	
	@Override
	@AfterMethod
	public void onTestEnd() throws Exception { }
	
	@Override
	@BeforeMethod
	public void onTestStart() throws Exception {
	}
	
	@Override
	@BeforeClass
	public void onTestClassStart() throws Exception {
	
	}
	
	@Override
	@AfterClass
	public void onTestClassEnd() throws Exception {
	
	}
	
	@BeforeSuite
	public void login() throws Exception {
		String url = configuration.browser().getProperty("url");
		browser.addDriverOption("--remote-allow-origins","*");
		browser.addDriverOption("--window-size", "1920,1080");
		
		timeStart = System.currentTimeMillis();
		this
			.initialize()
			.setRootUrl(url)
			.start();
		
		browser.driver().manage().timeouts().implicitlyWait(Duration.ofMillis(20));
		
		snackBar = new SnackBar();
		
		
		
		LocalStorage local = ((WebStorage) browser.driver()).getLocalStorage();

		local.setItem("signature", configuration.browser().getProperty("signature"));
		local.setItem("token", configuration.browser().getProperty("token"));
	}
	
	@AfterSuite
	public void clean() {
		this.close();
		timeEnd = System.currentTimeMillis();
		System.out.println("@@@ Total Execution Time : " + (timeEnd-timeStart)/1000 + " sec");
	}
}
