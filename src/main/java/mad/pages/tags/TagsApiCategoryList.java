package mad.pages.tags;

import light.automate.core.pages.Page;
import mad.components.tags.CreateEditTagsApiCategoryDialog;
import mad.mapping.RoleCategory;
import mad.mapping.TagApiCategory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TagsApiCategoryList extends Page {
	public TagsApiCategoryList() {
		PageFactory.initElements(browser.driver(), this);
	}
	
	@FindBy(xpath = "//button[contains(normalize-space(), 'Create New Category')]")
	public WebElement newButton;
	
	@FindBy(id = "search_tag_api_category")
	private WebElement searchBox;
	
	public void open() {
		browser.navigate("/tag-api/category");
	}
	
	
	
	public CreateEditTagsApiCategoryDialog create() {
		waitForVisible(newButton);
		waitForClickable(newButton);
		wait(2);
		newButton.click();
		
		
		return new CreateEditTagsApiCategoryDialog();
	}
	
	public void search(String name) {
		waitForVisible(searchBox);
		waitForClickable(searchBox);
		searchBox.clear();
		searchBox.sendKeys(name);
		searchBox.sendKeys(Keys.chord(Keys.ENTER));
		wait(2);
	}
	
	public List<TagApiCategory> all() {
		List<WebElement> tableRow = browser.driver().findElements(By.cssSelector("div.MuiDataGrid-row"));
		return tableRow.stream().map(row->{
			List<WebElement> cells = row.findElements(By.cssSelector("div.MuiDataGrid-cell"));
			String name = cells.get(0).getText();
			String description = cells.get(1).getText();
			String createdAt = cells.get(2).getText();
			String status = cells.get(3).getText();
			
			List<WebElement> buttons = row.findElements(By.cssSelector("div.MuiDataGrid-cell:nth-child(5) button"));
			
			return new TagApiCategory(
				name,
				description,
				status,
				createdAt,
				buttons.get(0)
			);
		}).collect(Collectors.toList());
	}
}
