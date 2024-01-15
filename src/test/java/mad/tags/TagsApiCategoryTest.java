package mad.tags;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import light.automate.listener.TestListener;
import mad.Mad;
import mad.components.tags.CreateEditTagsApiCategoryDialog;
import mad.components.tags.EditTagsApiCategoryDialog;
import mad.mapping.TagApiCategory;
import mad.pages.tags.TagCategoryDetails;
import mad.pages.tags.TagsApiCategoryList;
import static org.junit.Assert.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

@Listeners(TestListener.class)
public class TagsApiCategoryTest extends Mad {
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 1)
	public void create_tag_api_category(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName(data.get("name"))
			.writeDescription(data.get("description"))
			.create();
		
		org.junit.Assert.assertEquals(data.get("message"), snackBar.readMessage());
		wait(3);
		categoryList.search(data.get("name"));
		
		List<TagApiCategory> allCategory = categoryList
			.all();
		
		org.junit.Assert.assertEquals(allCategory.size(), 1);
		
		org.junit.Assert.assertEquals(allCategory.get(0).name, data.get("name"));
		org.junit.Assert.assertEquals(allCategory.get(0).description, data.get("description"));
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 2)
	public void create_tag_api_category_with_an_existing_category_name(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName(data.get("name"))
			.writeDescription(data.get("description"))
			.create();
		
		assertEquals("This TAG Category name already exists.", snackBar.readMessage() );
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 3)
	public void change_active_to_inactive_status_of_a_tag_category(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		assertEquals(data.get("description"), allCategory.get(0).description);
		assertEquals(data.get("status"), allCategory.get(0).status);
		
		TagCategoryDetails details = allCategory.get(0).view();
		wait(1);
		
		EditTagsApiCategoryDialog editDialog = details.edit();
		editDialog.changeStatus().confirm();
		
		assertEquals(data.get("message"), snackBar.readMessage());
		wait(3);
		editDialog.close();
		
		details.back();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		allCategory = categoryList.all();
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("expected_status"), allCategory.get(0).status);
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 4)
	public void change_inactive_to_active_status_of_a_tag_category(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		assertEquals(data.get("description"), allCategory.get(0).description);
		assertEquals(data.get("status"), allCategory.get(0).status);
		
		TagCategoryDetails details = allCategory.get(0).view();
		wait(1);
		
		EditTagsApiCategoryDialog editDialog = details.edit();
		editDialog.changeStatus().confirm();
		
		assertEquals(data.get("message"), snackBar.readMessage());
		wait(3);
		editDialog.close();
		
		details.back();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		allCategory = categoryList.all();
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("expected_status"), allCategory.get(0).status);
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 5)
	public void update_tag_category_name_with_empty_value(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		
		TagCategoryDetails details = allCategory.get(0).view();
		wait(1);
		
		EditTagsApiCategoryDialog editDialog = details.edit();
		editDialog
			.clearName()
			.writeName("")
			.saveChanges();
		
		assertTrue(editDialog.isErrorMessageVisible());
		editDialog.close();
		details.back();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		allCategory = categoryList.all();
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		assertEquals(data.get("status"), allCategory.get(0).status);
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 6)
	public void update_tag_category_description(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search(data.get("name"));
		wait(2);
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		assertEquals(data.get("description"), allCategory.get(0).description);
		assertEquals(data.get("status"), allCategory.get(0).status);
		
		TagCategoryDetails details = allCategory.get(0).view();
		wait(1);
		
		EditTagsApiCategoryDialog editDialog = details.edit();
		editDialog
			.clearDescription()
			.writeDescription(data.get("new_description"))
			.saveChanges();
		
		assertEquals(data.get("message"), snackBar.readMessage());
		
		details.back();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		allCategory = categoryList.all();
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		assertEquals(data.get("new_description"), allCategory.get(0).description);
		assertEquals(data.get("status"), allCategory.get(0).status);
	}
	
	@Test(priority = 7)
	public void search_tag_api_category_by_name() {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search("tagapicategory govt user's");
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals("tagapicategory govt user's", allCategory.get(0).name);
		assertEquals("tag api category with dash and underscore", allCategory.get(0).description);
	}
	
	@Test(priority = 8)
	public void create_existing_tag_api_category() {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName("tag api category (govt user's)")
			.writeDescription("tag api category for govt user")
			.create();
		
		assertEquals("This TAG Category name already exists.", snackBar.readMessage() );
		createEditDialog.close();
		wait(2);
		categoryList.search("tag api category for govt user");
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals("tag api category for govt user", allCategory.get(0).name);
		assertEquals("tag api category description updated", allCategory.get(0).description);
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 9)
	public void update_tag_category_name(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("name"), allCategory.get(0).name);
		assertEquals(data.get("description"), allCategory.get(0).description);
		assertEquals(data.get("status"), allCategory.get(0).status);
		
		TagCategoryDetails details = allCategory.get(0).view();
		wait(1);
		
		EditTagsApiCategoryDialog editDialog = details.edit();
		editDialog
			.clearName()
			.writeName(data.get("new_name"))
			.saveChanges();
		
		assertEquals(data.get("message"), snackBar.readMessage());
		
		details.back();
		
		wait(3);
		categoryList.search(data.get("name"));
		
		allCategory = categoryList.all();
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("new_name"), allCategory.get(0).name);
		assertEquals(data.get("description"), allCategory.get(0).description);
		assertEquals(data.get("status"), allCategory.get(0).status);
	}
	
	@Test(enabled = true)
	public void create_tag_api_category_with_empty_category_name_and_description() {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName("")
			.writeDescription("")
			.create();
		
		assertTrue("Name empty error message should be displayed", createEditDialog.isErrorMessageVisible());
	}
	
	@Test(enabled = true)
	public void create_tag_api_with_empty_category_name() {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName("")
			.writeDescription("tag api category with empty name")
			.create();
		
		assertTrue("Name empty error message should be displayed", createEditDialog.isErrorMessageVisible());
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void create_tag_api_category_with_empty_category_description(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName(data.get("name"))
			.writeDescription("")
			.create();
		
		assertEquals(snackBar.readMessage(), data.get("message"));
		
		categoryList.search(data.get("name"));
		
		boolean found = categoryList
			.all()
			.stream()
			.anyMatch(c->c.name.equals(data.get("name")));
		
		assertTrue( "Tags Api category not found by name", found);
	}
	
	
	/*@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void create_tag_category_with_valid_name_when_name_has_more_then_65_characters() {}*/
	
	/**
	 * Create a tag category with a name that includes special characters   '-_(),
	 * Create a tag category with a name that includes special characters  *&^%$#@!{}|\/?;:[]+=~`
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void create_a_tag_category_with_a_name_that_includes_special_characters(JsonData data) {
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		CreateEditTagsApiCategoryDialog createEditDialog = categoryList.create();
		createEditDialog
			.writeName(data.get("name"))
			.writeDescription(data.get("description"))
			.create();
		
		assertEquals(data.get("message"), snackBar.readMessage() );
		wait(2);
		categoryList.search(data.get("name"));
		
		List<TagApiCategory> allCategory = categoryList.all();
		
		assertEquals(1, allCategory.size());
		
		assertEquals(data.get("e_name"), allCategory.get(0).name);
		assertEquals(data.get("description"), allCategory.get(0).description);
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = false)
	public void delete_tag_category_when_no_tag_is_assigned_to_it() {
		String name = "category with comma,and and&";
		
		TagsApiCategoryList categoryList = new TagsApiCategoryList();
		categoryList.open();
		
		wait(3);
		categoryList.search(name);
		
		List<TagApiCategory> all = categoryList
			.all();
			
		assertEquals(1, all.size());
		
		Optional<TagApiCategory> optionalTagApiCategory = categoryList
			.all()
			.stream()
			.filter(c->c.name.equals(name))
			.findAny();
		
		assertTrue( "Tags Api category not found by name", optionalTagApiCategory.isPresent());
		
		TagCategoryDetails details = optionalTagApiCategory
			.get()
			.view();
		
		details
			.delete()
			.delete();
		
		assertEquals("sdf", snackBar.readMessage());
		
		details.back();
		
		categoryList.search(name);
		
		boolean found = categoryList
			.all()
			.stream()
			.anyMatch(c->c.name.equals(name));
		
		assertFalse("category should not present after deleted", found);
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = false)
	public void delete_tag_category_when_tag_assigned() {}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = false, priority = 20)
	public void check_tag_category_pagination_and_per_page_item() {}
}
