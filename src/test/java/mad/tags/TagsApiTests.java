package mad.tags;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import light.automate.listener.TestListener;
import mad.Mad;
import mad.mapping.TagApi;
import mad.pages.tags.TagApiCreateDialog;
import mad.pages.tags.TagApiList;
import static org.junit.Assert.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Listeners(TestListener.class)
public class TagsApiTests extends Mad {
	
	@Test(priority = 1, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void create_tag_api_category_with_name_and_description(JsonData data) {
		TagApiList tagApiList = new TagApiList();
		
		tagApiList.open();
		
		wait(5);
		
		TagApiCreateDialog createDialog = tagApiList
			.createNew();
		
		createDialog
			.writeName(data.get("name"))
			.writeDescription(data.get("description"))
			.writeEndpoint(data.get("endpoint"))
			.writeTag(data.get("tags").split(","))
			.selectCategory(data.get("category"))
			.create();
		
		assertEquals(data.get("message"), snackBar.readMessage());
		
		tagApiList.search(data.get("name"));
		wait(2);
		List<TagApi> all = tagApiList
			.waitForResult()
			.all();
		
		assertEquals(1, all.size());
		
		Optional<TagApi> tagApiOptional = all
			.stream()
			.filter(a->a.name.equals(data.get("name")))
			.findAny();
		
		assertTrue("Tag Api should be present after created",tagApiOptional.isPresent());
		assertEquals(data.get("category"), tagApiOptional.get().category);
		assertEquals("Active", tagApiOptional.get().status);
	}
	
	@Test(priority = 2, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void create_tag_api_category_with_name_only(JsonData data) {
		TagApiList tagApiList = new TagApiList();
		
		tagApiList.open();
		
		wait(5);
		
		TagApiCreateDialog createDialog = tagApiList
			.createNew();
		
		createDialog
			.writeName(data.get("name"))
			.create();
		
		assertTrue("Endpoint error message should be displayed", createDialog.isEndpointMessageDisplayed());
		assertTrue("Category error message should be displayed", createDialog.isCategoryMessageDisplayed());
		createDialog.close();
		
		tagApiList.search(data.get("name"));
		wait(2);
		List<TagApi> all = tagApiList
			.all();
		
		assertEquals(0, all.size());
	}
	
	@Test(priority = 3, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void create_tag_api_category_with_endpoint_only(JsonData data) {
		TagApiList tagApiList = new TagApiList();
		
		tagApiList.open();
		
		wait(2);
		
		TagApiCreateDialog createDialog = tagApiList
			.createNew();
		
		createDialog
			.writeEndpoint(data.get("endpoint"))
			.create();
		
		assertTrue("Category error message should be displayed", createDialog.isCategoryMessageDisplayed());
		assertTrue("Name error message should be displayed", createDialog.isNameMessageDisplayed());
		createDialog.close();
		
		tagApiList.search(data.get("name"));
		wait(2);
		List<TagApi> all = tagApiList
			.all();
		
		assertEquals(0, all.size());
	}
	
	@Test(priority = 3, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void create_tag_api_category_with_category_only(JsonData data) {
		TagApiList tagApiList = new TagApiList();
		
		tagApiList.open();
		
		wait(3);
		
		TagApiCreateDialog createDialog = tagApiList
			.createNew();
		
		createDialog
			.selectCategory(data.get("category"))
			.create();
		
		assertTrue("Endpoint error message should be displayed", createDialog.isEndpointMessageDisplayed());
		assertTrue("Name error message should be displayed", createDialog.isNameMessageDisplayed());
		createDialog.close();
		
		tagApiList.search(data.get("name"));
		wait(2);
		List<TagApi> all = tagApiList
			.all();
		
		assertEquals(0, all.size());
	}
	
	@Test(priority = 4, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void create_tag_api_category_with_special_character_in_name(JsonData data) {}
	
	@Test(priority = 5, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void create_tag_api_category_with_more_than_65_character_in_name(JsonData data) {}
	
	@Test(priority = 6, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void edit_tag_api_category_name(JsonData data) {}
	
	@Test(priority = 7, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void edit_tag_api_category_description(JsonData data) {}
	
	@Test(priority = 8, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void edit_tag_api_category_name_with_special_characters(JsonData data) {}
	
	@Test(priority = 9, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void search_tag_api_category_by_name(JsonData data) {}
	
	@Test(priority = 10, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void search_tag_api_category_by_special_characters(JsonData data) {}
	
	@Test(priority = 11, enabled = false, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data")
	public void delete_tag_api_category(JsonData data) {}
}
