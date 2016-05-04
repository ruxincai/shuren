package example.four;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Routing servlet.
 */
public class RoutingServlet extends BaseServlet {

private static final long TIMESTAMP = System.currentTimeMillis() / 1000 * 1000;
private static final String[] SCRIPTS = {
        "node_modules/es6-shim/es6-shim.min.js",
        "node_modules/systemjs/dist/system-polyfills.js",
        "node_modules/angular2/es6/dev/src/testing/shims_for_IE.js",
        "node_modules/angular2/bundles/angular2-polyfills.js",
        "node_modules/systemjs/dist/system-csp-production.src.js",
        "node_modules/rxjs/bundles/Rx.js",
        "node_modules/angular2/bundles/angular2.dev.js",
        "node_modules/angular2/bundles/router.dev.js",
        "bootstrap.js"
};
/**
 * The pattern for IE.
 */
private static final Pattern IE_PATTERN =
        Pattern.compile("Trident/([0-9]+)");

@Override
public void service(@NotNull ServletRequest servletRequest,
                    @NotNull ServletResponse servletResponse)
        throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    if (!"GET".equals(request.getMethod())) {
        response.setHeader("Allow", "GET");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return;
    }

    response.setHeader("Cache-Control", "max-age=86400");

    StringBuilder requestURL = new StringBuilder(80);
    requestURL.append(request.getScheme()).append("://")
            .append(request.getServerName());
    int port = request.getServerPort();
    //https vs http
    if (port != (request.isSecure() ? 443 : 80)) {
        requestURL.append(':').append(String.valueOf(port));
    }
    requestURL.append(request.getContextPath()).append('/');
    String baseURI = requestURL.toString();
    response.setHeader("Content-Security-Policy",
            "default-src 'self'; " +
                    "script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
                    "style-src 'self' 'unsafe-inline'; " +
                    "media-src 'none'; object-src 'none'; " +
                    "base-uri " + baseURI + "; " +
                    "frame-ancestors 'none'");

    long ifModified = request.getDateHeader("If-Modified-Since");
    if (ifModified == TIMESTAMP) {
        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        return;
    }

    String userAgent = request.getHeader("User-Agent");
    if (userAgent != null && IE_PATTERN.matcher(userAgent).find()) {
        response.addHeader("X-UA-Compatible", "IE=edge");
    }

    response.setDateHeader("Last-Modified", TIMESTAMP);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter writer = response.getWriter();
    writer.append(
            "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>FrontToBack Test</title>" +
                    "<base href=\"").append(request.getContextPath()).append("/\">" +
            "<meta name=\"viewport\" content=\"initial-scale=1, user-scalable=no\">" +
            "<link rel=\"stylesheet\" href=\"styles.css\">");
    for (String script : SCRIPTS) {
        writer.append("<script src=\"").append(script).append("\"></script>");
    }
    writer.append("</head>" +
            "<body><test-app>Loading...</test-app></body>" +
            "</html>");
}

}
