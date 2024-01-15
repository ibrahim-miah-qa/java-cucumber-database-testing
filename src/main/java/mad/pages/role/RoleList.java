package mad.pages.role;

import light.automate.core.pages.Page;
import mad.mapping.Permission;
import mad.mapping.Role;
import mad.mapping.RoleCategory;
import mad.pages.permission.CreateEditPermissionDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class RoleList extends Page {
	public RoleList() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	public void open() {
		browser.open("http://stagingadmin.talkiomobile.com/roles");
	}
	
	@FindBy(xpath = "//button[contains(text(), 'Create New Role')]")
	private WebElement createNewButton;
	
	@FindBy(css = "input[id='search_role']")
	private WebElement searchBox;
	
	public void search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		searchBox.clear();
		searchBox.sendKeys(name);
	}
	
	public List<Role> all() {
		List<WebElement> tableRow = browser.driver().findElements(By.cssSelector("div.MuiDataGrid-row"));
		return tableRow.stream().map(row->{
			List<WebElement> cells = row.findElements(By.cssSelector("div.MuiDataGrid-cell"));
			String name = cells.get(0).getText().trim();
			String description = cells.get(1).getText().trim();
			String createdAt = cells.get(2).getText().trim();
			String status = cells.get(3).getText().trim();
			
			List<WebElement> buttons = row.findElements(By.cssSelector("div.MuiDataGrid-cell:nth-child(5) button"));
			
			return new Role(
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
	
	public CreateEditRoleDialog createNew() {
		waitForVisible(createNewButton);
		waitForClickable(createNewButton);
		createNewButton.click();
		return new CreateEditRoleDialog();
	}
}
