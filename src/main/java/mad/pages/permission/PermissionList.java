package mad.pages.permission;

import light.automate.core.pages.Page;
import mad.mapping.Permission;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionList extends Page {
	public PermissionList() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	public void open() {
		//browser.open("http://stagingadmin.talkiomobile.com/permission");
		browser.navigate("/permission");
	}
	
	@FindBy(xpath = "//button[contains(normalize-space(), 'Create New Permission')]")
	private WebElement createNewButton;
	
	@FindBy(css = "input[id='search_permission']")
	private WebElement searchBox;
	
	public void search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		searchBox.clear();
		searchBox.sendKeys(name);
	}
	
	public List<Permission> all() {
		By tableSelector = By.cssSelector("div.MuiDataGrid-row");
		fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableSelector));
		List<WebElement> tableRow = browser.driver().findElements(tableSelector);
		return tableRow.stream().map(row->{
			List<WebElement> cells = row.findElements(By.cssSelector("div.MuiDataGrid-cell"));
			String name = cells.get(0).getText();
			String description = cells.get(1).getText();
			String createdAt = cells.get(2).getText();
			String status = cells.get(3).getText();
			
			List<WebElement> buttons = row.findElements(By.cssSelector("div.MuiDataGrid-cell:nth-child(5) button"));
			
			return new Permission(
				name,
				description,
				status,
				createdAt,
				buttons.get(0),
				buttons.get(1),
				buttons.get(2)
			);
		}).collect(Collectors.toList());
	}
	
	public CreateEditPermissionDialog createNew() {
		waitForVisible(createNewButton);
		waitForClickable(createNewButton);
		createNewButton.click();
		return new CreateEditPermissionDialog();
	}
}
