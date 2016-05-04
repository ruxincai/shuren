package example.four;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class HomeController {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() {
        Map<String, String> model = new HashMap<>();
        model.put("hello", "Hello");
        model.put("world", "World");
        return new Viewable("/index", model);
    }
}
