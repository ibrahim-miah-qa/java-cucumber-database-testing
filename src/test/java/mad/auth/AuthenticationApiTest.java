package mad.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import light.automate.dataprovider.JsonData;
import light.automate.dataprovider.JsonDataProvider;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

import static org.testng.Assert.*;

public class AuthenticationApiTest {
	private final OkHttpClient client = new OkHttpClient();
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectReader mapReader = new ObjectMapper().readerFor(Map.class);
	
	private final String url = "http://stagingadminapi.talkiomobile.com/api/v1/auth/login";
	
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_invalid_media_types(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse(data.get("media_type"));
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.post(body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post with invalid media type: " + data.get("media_type"));
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		//System.out.println(responseBodyString);
		
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		
		/* validate response content type should not null and should be application/json */
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_without_email(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			//.addHeader("Content-Type", "application/json")
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without email " + data.get("body"));
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertTrue(responseBodyMap.toString().contains("Email is required."), "Email is Required message not found in error message");
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_without_password(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without email " + data.get("body"));
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertTrue(responseBodyMap.toString().contains("Password is required."), "Password is Required message not found in error message");
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_without_ip(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without email " + data.get("body"));
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertTrue(responseBodyMap.toString().contains("IP is required"), "IP is Required message not found in error message");
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_without_user_agent(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without email " + data.get("body"));
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertTrue(responseBodyMap.toString().contains("User Agent is required"), "User Agent is Required message not found in error message");
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_without_timezone(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 200, "Response code should be 400 when post without timezone");
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertEquals(responseBodyMap.get("message"), "OTP has been sent");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_without_location(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without timezone");
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertTrue(responseBodyMap.toString().contains("Location is required"), "Location is Required message not found in error message");
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_invalid_user_agent(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without timezone");
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		// todo
		assertTrue(responseBodyMap.toString().contains("Location is required"), "Location is Required message not found in error message");
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_invalid_email(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 400, "Response code should be 400 when post without timezone");
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		assertTrue(responseBodyMap.toString().contains("msg=Invalid value, path=email"), data.get("test"));
		assertEquals(responseBodyMap.get("message"), data.get("message"));
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_invalid_password(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		assertEquals(response.code(), 200, "Response code should be 400 when post without timezone");
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		System.out.println(responseBodyMap);
		assertEquals(responseBodyMap.get("message"), "Email or password is incorrect.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_invalid_ip(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		System.out.println(responseBodyMap);
		/* Response body should not null */
		assertNotNull(response.body());
		assertEquals(response.code(), 400, "Response code should be 400 when post without timezone");
		assertTrue(responseBodyMap.toString().contains("IP is required"), data.get("test"));
		assertEquals(responseBodyMap.get("message"), "Please enter all required fields.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_wrong_email(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		System.out.println(responseBodyMap);
		
		assertEquals(response.code(), 200, "Response code should be 200 when post without timezone");
		//assertTrue(responseBodyMap.toString().contains("IP is required"), data.get("test"));
		assertEquals(responseBodyMap.get("message"), "Email or password is incorrect.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_wrong_password(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		System.out.println(responseBodyMap);
		
		assertEquals(response.code(), 200, "Response code should be 200 when post without timezone");
		assertEquals(responseBodyMap.get("message"), "Email or password is incorrect.");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
	}
	
	@Test(dataProvider = "json_data", dataProviderClass = JsonDataProvider.class, enabled = true)
	public void login_with_valid_email_password(JsonData data) throws IOException {
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody body = RequestBody.create(mediaType, data.get("body"));
		
		Request request = new Request.Builder()
			.url(url)
			.method("POST", body)
			.build();
		
		Response response = client.newCall(request).execute();
		
		/* Response body should not null */
		assertNotNull(response.body());
		
		String responseBodyString = response.body().string();
		
		Map<String, String> responseBodyMap = mapReader.readValue(responseBodyString);
		
		JsonNode responseObject = objectMapper.readTree(responseBodyString);
		assertEquals(response.code(), 200, "Response code should be 400 when post without timezone");
		assertTrue(responseBodyMap.toString().contains("message=OTP has been sent"), "Opt has been sent message not found in body");
		assertEquals(responseBodyMap.get("message"), "OTP has been sent");
		
		Headers responseHeaders = response.headers();
		String contentType = responseHeaders.get("Content-Type");
		assertNotNull(contentType);
		assertTrue(contentType.toLowerCase().contains("application/json"), "Response Content-type not matched");
		
		JsonNode responseData = responseObject.get("data");
		
		assertEquals(responseData.get("otp").asText().length(), 6, "opt length should be 6");
		assertEquals(responseData.get("otpSentVia").asText(),"email", "opt should be sent by mail");
	}
}
