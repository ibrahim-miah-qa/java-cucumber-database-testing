package mad.pages.role;

import light.automate.core.pages.Page;
import mad.components.role.CreateEditRoleCategoryDialog;
import mad.mapping.RoleCategory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class RoleCategoryList extends Page {
	@FindBy(xpath = "//button[contains(text(), 'Create New Category')]")
	public WebElement newButton;
	
	@FindBy(id = "search_role_category")
	private WebElement searchBox;
	
	public RoleCategoryList() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	public void open() {
		browser.open("http://stagingadmin.talkiomobile.com/roles/category");
	}
	
	public CreateEditRoleCategoryDialog createNew() {
		waitForVisible(newButton);
		waitForClickable(newButton);
		newButton.click();
		
		wait(2);
		
		return new CreateEditRoleCategoryDialog();
	}
	
	public void search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		searchBox.clear();
		searchBox.sendKeys(name);
		wait(2);
	}
	
	public List<RoleCategory> all() {
		List<WebElement> tableRow = browser.driver().findElements(By.cssSelector("div.MuiDataGrid-row"));
		return tableRow.stream().map(row->{
			List<WebElement> cells = row.findElements(By.cssSelector("div.MuiDataGrid-cell"));
			String name = cells.get(0).getText();
			String description = cells.get(1).getText();
			String createdAt = cells.get(2).getText();
			String status = cells.get(3).getText();
			
			List<WebElement> buttons = row.findElements(By.cssSelector("div.MuiDataGrid-cell:nth-child(5) button"));
			
			return new RoleCategory(
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
}
