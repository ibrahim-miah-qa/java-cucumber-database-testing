package mad.mapping;

import mad.pages.tags.TagCategoryDetails;
import org.openqa.selenium.WebElement;

public class TagApiCategory {
	public final String name;
	public final String description;
	public final String status;
	public final String createdAt;
	
	private final WebElement view;
	
	public TagApiCategory(
		String name,
		String description,
		String status,
		String createdAt,
		WebElement viewButton
	) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.view = viewButton;
	}
	
	public TagCategoryDetails view() {
		view.click();
		return new TagCategoryDetails();
	}
}
