package example.four;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class MyApplication extends ResourceConfig {

    public MyApplication() {
        // Resources.
        packages("example.four");

        // MVC.
        register(JspMvcFeature.class);
        property(JspMvcFeature.TEMPLATES_BASE_PATH, "/jsp");
        register(Main.createMoxyJsonResolver());

        register(HomeController.class);

        // Tracing support.
        property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
    }
}
