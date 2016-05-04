package shu.example;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class APITest extends TestCase {

private APIServlet servlet;
private MockHttpServletRequest request;
private MockHttpServletResponse response;

public void setUp() {
	servlet = new APIServlet();
	request = new MockHttpServletRequest();
	request.setServerName("www.example.com");
	response = new MockHttpServletResponse();
}

public void testGetText() throws Exception {
	request.setPathInfo("/text/haha");
	request.setMethod("GET");

	servlet.service(request, response);
	String result = new String(response.getContentAsByteArray());
	assertEquals("{\"text\": \"haha\"}", result);
}

public void testGetSubmissions() throws Exception {
	request.setPathInfo("/submissions");
	request.setMethod("GET");

	servlet.service(request, response);
	String result = new String(response.getContentAsByteArray());
	Object object = new JSONParser(new StringReader(result)).parse();
	assertTrue("is list", object instanceof List);
	@SuppressWarnings("unchecked")
	List<Object> list = (List<Object>)object;
	if (!list.isEmpty()) {
		@SuppressWarnings("unchecked")
		Map<String, Object> submission =
				(Map<String, Object>)list.iterator().next();
		assertNotNull("missing id", submission.get("id"));
		assertNotNull("missing userName", submission.get("userName"));
		assertNotNull("missing city", submission.get("city"));
		assertNotNull("missing last_update", submission.get("last_update"));
		assertNotNull("missing responses", submission.get("responses"));
	}
}

public void testPostSubmissions() throws Exception {
	request.setPathInfo("/submissions");
	request.setMethod("POST");
	request.setContentType("text/json");
	request.setContent(("{\"name\":\"pavel\",\"city\":\"Toronto\"," +
			"\"text\":\"hello world\"}").getBytes());
	servlet.service(request, response);
	String result = new String(response.getContentAsByteArray());
	assertEquals("", result);
	assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
	String location = (String)response.getHeader("Location");
	int i = location.lastIndexOf('/');
	String id = location.substring(i + 1);
	assertNotNull(Integer.parseInt(id));
	//todo runs select to see if it is there
	//do assert

	//todo delete our record id
}

}
