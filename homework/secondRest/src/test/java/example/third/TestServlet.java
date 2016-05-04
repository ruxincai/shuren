package example.third;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by ruxin on 5/2/2016.
 */
public class TestServlet extends TestCase {

    @Test
    public void test1() throws Exception {
        ServletRunner sr = new ServletRunner();
        sr.registerServlet( "myServlet", HelloServlet.class.getName() );
        ServletUnitClient sc = sr.newClient();
        GetMethodWebRequest request = new GetMethodWebRequest( "http://test.meterware.com/myServlet" );
//        request.setParameter( "color", "red" );
        WebResponse response = sc.getResponse( request );
        assertNotNull( "No response received", response );
        assertEquals( "content type", "text/plain", response.getContentType() );
    }
}
