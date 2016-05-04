package example.four;

import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testGet() {
        ServletRunner sr = new ServletRunner();
        sr.registerServlet( "myServlet", StatefulServlet.class.getName() );
        ServletUnitClient sc = sr.newClient();
        WebRequest request   = new PostMethodWebRequest( "http://test.meterware.com/myServlet" );
        request.setParameter( "color", "red" );
        WebResponse response = sc.getResponse( request );
        assertNotNull( "No response received", response );
        assertEquals( "content type", "text/plain", response.getContentType() );
        assertEquals( "requested resource", "You selected red", response.getText() );
//        assertEquals(testBean, new TestBean("a", 2, 1L));
    }

}
