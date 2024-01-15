package mad.pages.permission;

import light.automate.core.pages.Page;
import mad.mapping.PermissionCategory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionCategoryList extends Page {
	public PermissionCategoryList() {
		PageFactory.initElements(browser.driver(), this);
	}
	@FindBy(xpath = "//button[contains(text(), 'Create New Category')]")
	public WebElement newButton;
	
	public PermissionCategoryAddDialog createNew() {
		waitForVisible(this.newButton);
		waitForClickable(this.newButton);
		//click(this.newButton);
		this.newButton.click();
		return new PermissionCategoryAddDialog();
	}
	
	public PermissionCategoryList search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		//write(searchBox, name);
		searchBox.clear();
		searchBox.sendKeys(name);
		searchBox.sendKeys(Keys.ENTER);
		return this;
	}
	
	@FindBy(css = "input[id='search_permission_category']")
	private WebElement searchBox;
	public void open() {
		browser.open("http://stagingadmin.talkiomobile.com/permission/category");
	}
	
	public List<PermissionCategory> all() {
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
			
			return new PermissionCategory(
				name,
				description,
				status,
				createdAt,
				buttons.get(0)
			);
		}).collect(Collectors.toList());
	}
}
