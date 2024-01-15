package mad.pages.consumer;

import light.automate.core.pages.Page;
import mad.mapping.Consumer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class ConsumerListPage extends Page {
	public ConsumerListPage() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(name = "search_consumer")
	private WebElement searchBox;
	
	@FindBy(xpath = "//button[contains(text(), 'Add New Consumer')]")
	public WebElement newButton;
	public void open() {
		browser.navigate("/consumers");
	}
	
	public void search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		searchBox.clear();
		searchBox.sendKeys(name);
	}
	
	public CreateEditConsumerDialog create() {
		waitForVisible(newButton);
		waitForClickable(newButton);
		newButton.click();
		return new CreateEditConsumerDialog();
	}
	
	public List<Consumer> all() {
		By tableSelector = By.cssSelector("div.MuiDataGrid-row");
		fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableSelector));
		List<WebElement> tableRow = browser.driver().findElements(tableSelector);
		return tableRow.stream().map(row->{
			List<WebElement> cells = row.findElements(By.cssSelector("div.MuiDataGrid-cell"));
			String name = cells.get(0).getText();
			String email = cells.get(1).getText();
			String phone = cells.get(2).getText();
			String role = cells.get(3).getText();
			String createdAt = cells.get(4).getText();
			String status = cells.get(5).getText();
			
			List<WebElement> buttons = row.findElements(By.cssSelector("div.MuiDataGrid-cell:nth-child(7) button"));
			
			return new Consumer(
				name,
				email,
				phone,
				role,
				createdAt,
				status,
				buttons.get(0),
				buttons.get(1),
				buttons.get(2)
			);
		}).collect(Collectors.toList());
	}

}
