package example.four;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@WebService
@Path("json")
public class JsonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TestBean createSimpleBean() {
        return new TestBean("a", 2, 1L);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TestBean roundTrip(TestBean tb) {
        return tb;
    }
}
