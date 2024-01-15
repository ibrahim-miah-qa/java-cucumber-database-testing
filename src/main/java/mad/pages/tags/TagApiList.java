package mad.pages.tags;

import light.automate.core.pages.Page;
import mad.mapping.PermissionCategory;
import mad.mapping.TagApi;
import mad.pages.permission.PermissionCategoryAddDialog;
import mad.pages.permission.PermissionCategoryList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class TagApiList extends Page {
	public void open() {
		browser.navigate("/tag-api");
	}
	
	public TagApiList() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(xpath = "//button[normalize-space()= 'Create New Tag API']")
	public WebElement newButton;
	
	public TagApiCreateDialog createNew() {
		waitForVisible(this.newButton);
		waitForClickable(this.newButton);
		this.newButton.click();
		wait(2);
		return new TagApiCreateDialog();
	}
	
	public TagApiList search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		searchBox.clear();
		searchBox.sendKeys(name);
		searchBox.sendKeys(Keys.ENTER);
		return this;
	}
	
	@FindBy(id = "search_tag_api")
	private WebElement searchBox;
	
	public TagApiList waitForResult() {
		By tableSelector = By.cssSelector("div.MuiDataGrid-row");
		fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableSelector));
		return this;
	}
	public List<TagApi> all() {
		By tableSelector = By.cssSelector("div.MuiDataGrid-row");
		//fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableSelector));
		List<WebElement> tableRow = browser.driver().findElements(tableSelector);
		return tableRow.stream().map(row->{
			List<WebElement> cells = row.findElements(By.cssSelector("div.MuiDataGrid-cell"));
			String name = cells.get(0).getText();
			String category = cells.get(1).getText();
			String createdAt = cells.get(2).getText();
			String status = cells.get(3).getText();
			
			List<WebElement> buttons = row.findElements(By.cssSelector("div.MuiDataGrid-cell:nth-child(5) button"));
			
			return new TagApi(
				name,
				category,
				status,
				createdAt,
				buttons.get(0)
			);
		}).collect(Collectors.toList());
	}
}
