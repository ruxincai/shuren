package example.four;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class JsonResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return Main.createAppConfig();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(Main.createMoxyJsonResolver());
    }

    @Test
    public void testGet() {
        WebTarget target = target("json");
        TestBean testBean = target.request(MediaType.APPLICATION_JSON_TYPE).get(TestBean.class);
        assertEquals(testBean, new TestBean("a", 2, 1L));
    }

    @Test
    public void roundTripTest() {
        final WebTarget target = target("json");
        final TestBean testBean = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(new TestBean("a", 2, 1L), MediaType.APPLICATION_JSON_TYPE), TestBean.class);

        assertEquals(testBean, new TestBean("a", 2, 1L));
    }

}
