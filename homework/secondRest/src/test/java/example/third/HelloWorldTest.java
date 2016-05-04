package example.third;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class HelloWorldTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /** Test direct method call. */
    @Test
    public void testGetTextMethod() {
        Assert.assertEquals("echo", new HelloWorld().getText("echo"));
    }

    /** Test to see that the message "{whatever}" is sent in the response. */
    @Test
    public void testGetText() {
        String responseMsg = target.path("rest").path("echo").request().get(String.class);
        Assert.assertEquals("echo", responseMsg);
    }
}
