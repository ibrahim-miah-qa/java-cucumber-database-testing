package mad.consumer;

import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import light.automate.listener.TestListener;
import mad.Mad;
import mad.components.common.ChangeStatusDialog;
import mad.mapping.Consumer;
import mad.pages.consumer.ConsumerListPage;
import mad.pages.consumer.CreateEditConsumerDialog;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Optional;

@Listeners(TestListener.class)
public class ConsumerListTest extends Mad {
	/**
	 * MAD-ADMIN-83
	 * */
	@Test(priority = 1, dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", enabled = false)
	public void add_consumer_with_name_email_and_phone_number(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		wait(3);
		
		CreateEditConsumerDialog createDialog = consumerListPage.create();
		
		createDialog
			.writeName("new consumer")
			.writeEmail("consumer@mail.com")
			.writeMobile("8938498560")
			.add();
		
		System.out.println( snackBar.readMessage());
		
		consumerListPage.search("new consumer");
		
		wait(3);
		
		boolean found = consumerListPage
			.all()
			.stream().anyMatch(c->c.name.equals("new consumer"));
		
		Assert.assertTrue(found, "consumer not found");
	}
	
	/** MAD-ADMIN-84 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_with_name_and_email(JsonData data) {}
	
	/** MAD-ADMIN-85 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_with_name_and_phone(JsonData data) {}
	
	/** MAD-ADMIN-86 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_without_name(JsonData data) {}
	
	/** MAD-ADMIN-87 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_without_phone(JsonData data) {}
	
	/** MAD-ADMIN-88 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_without_email(JsonData data) {}
	
	/** MAD-ADMIN-89 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_with_name_has_more_than_65_characters(JsonData data) {}
	
	/** MAD-ADMIN-90 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_with_special_character_in_name(JsonData data) {}
	
	/** MAD-ADMIN-91 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_with_invalid_email_address(JsonData data) {}
	
	/** MAD-ADMIN-92 */
	@Test(priority = 1, enabled = false)
	public void add_consumer_with_invalid_phone(JsonData data) {}
	
	/**
	 * MAD-ADMIN-93
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 11)
	public void search_consumer_by_name(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.name.equals(data.get("name")));
		
		Assert.assertTrue(found, "consumer not found");
	}
	
	
	/**
	 * MAD-ADMIN-94
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 12)
	public void search_consumer_by_email(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("email"));
		wait(2);
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.email.equals(data.get("email")));
		
		Assert.assertTrue(found, "consumer not found by email");
	}
	
	
	/**
	 * MAD-ADMIN-95
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 13)
	public void search_consumer_by_phone(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("phone"));
		wait(2);
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("phone")));
		
		Assert.assertTrue(found, "consumer not found by phone");
	}
	
	
	/**
	 * MAD-ADMIN-96
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 14)
	public void search_consumer_by_special_characters(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("search_text"));
		wait(2);
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("search_text")));
		
		Assert.assertTrue(found, "consumer not found by special character");
	}
	
	/**
	 * MAD-ADMIN-97
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 14)
	public void change_consumer_status_active_to_inactive(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.phone.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		ChangeStatusDialog changeStatusDialog = optionalConsumer.get().changeStatus();
		changeStatusDialog.confirm();
		Assert.assertEquals(snackBar.readMessage(), "consumer de-activated successfully");
	}

	/**
	 * MAD-ADMIN-98
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 15, enabled = false)
	public void change_consumer_status_inactive_to_active(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.phone.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		ChangeStatusDialog changeStatusDialog = optionalConsumer.get().changeStatus();
		changeStatusDialog.confirm();
		Assert.assertEquals(snackBar.readMessage(), "consumer de-activated successfully");
	}
	
	/**
	 * MAD-ADMIN-99
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 16, enabled = false)
	public void edit_consumer_change_name(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readName(), data.get("name"));
		editConsumerDialog.clearName();
		editConsumerDialog.writeName(data.get("new_name"));
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_name"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.name.equals(data.get("new_name")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-100
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 17, enabled = false)
	public void edit_consumer_change_email(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readEmail(), data.get("email"));
		editConsumerDialog.clearEmail();
		editConsumerDialog.writeEmail(data.get("new_email"));
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_email"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.email.equals(data.get("new_email")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-101
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 18, enabled = false)
	public void edit_consumer_change_phone(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("phone"));
		editConsumerDialog.clearEmail();
		editConsumerDialog.writeEmail(data.get("new_phone"));
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_phone"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_phone")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-102
	 */
	@Test(dataProviderClass = JsonDataProvider.class, dataProvider = "json_data", priority = 19)
	public void edit_consumer_change_name_with_special_character(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("new_name"));
		editConsumerDialog.clearEmail();
		editConsumerDialog.writeEmail(data.get("new_name"));
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_name"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_name")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-103
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 20)
	public void edit_consumer_change_to_invalid_email(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("new_name"));
		editConsumerDialog.clearEmail();
		editConsumerDialog.writeEmail(data.get("new_name"));
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_email"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_email")));
		
		Assert.assertFalse(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-104
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 21)
	public void edit_consumer_remove_phone(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("new_phone"));
		editConsumerDialog.clearMobile();
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_phone"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_phone")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-105
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 22)
	public void edit_consumer_remove_email(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("email"));
		editConsumerDialog.clearEmail();
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_phone"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_phone")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-106
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 23)
	public void edit_consumer_remove_name(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("name"));
		editConsumerDialog.clearName();
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_phone"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_phone")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-107
	 */
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, priority = 24)
	public void edit_consumer_remove_name_and_email(JsonData data) {
		ConsumerListPage consumerListPage = new ConsumerListPage();
		consumerListPage.open();
		
		consumerListPage.search(data.get("name"));
		wait(2);
		Optional<Consumer> optionalConsumer = consumerListPage
			.all()
			.stream()
			.filter(c->c.name.equals(data.get("name")))
			.findAny();
		
		Assert.assertTrue(optionalConsumer.isPresent(), "consumer not found by special character");
		
		CreateEditConsumerDialog editConsumerDialog = optionalConsumer.get().edit();
		Assert.assertEquals(editConsumerDialog.readMobile(), data.get("new_phone"));
		editConsumerDialog.clearName();
		editConsumerDialog.clearEmail();
		editConsumerDialog.save();
		
		Assert.assertEquals(snackBar.readMessage(), "sdfsd");
		
		consumerListPage.search(data.get("new_phone"));
		boolean found = consumerListPage
			.all()
			.stream()
			.anyMatch(c->c.phone.equals(data.get("new_phone")));
		
		Assert.assertTrue(found, "consumer not found by name after update");
	}
	
	/**
	 * MAD-ADMIN-108
	 */
	@Test(enabled = false)
	public void delete_consumer(JsonData data) {}
	
	/**
	 * MAD-ADMIN-109
	 */
	@Test(enabled = false)
	public void view_consumer_dashboard(JsonData data) {}
}
