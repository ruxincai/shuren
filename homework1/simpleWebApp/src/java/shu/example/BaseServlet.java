package shu.example;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.postgresql.ds.PGPoolingDataSource;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base implementation of {@link Servlet}.
 */
public abstract class BaseServlet implements Servlet {

/** The data source. */
protected static final PGPoolingDataSource DATA_SOURCE;
/** The authenticated tokens. */
protected static final Map<String, TokenData> TOKENS =
		new ConcurrentHashMap<>();
protected static final String TEXT_JSON = "text/json;charset=UTF-8";

static {
	DATA_SOURCE = new PGPoolingDataSource();
	DATA_SOURCE.setUser("postgres");
	DATA_SOURCE.setDatabaseName("homework");
}

@Override
public void init(ServletConfig servletConfig) throws ServletException {
}

@Override
public ServletConfig getServletConfig() {
	return null;
}

@Override
public String getServletInfo() {
	return null;
}

@Override
public void destroy() {
}

protected static class TokenData {

	private final long time;
	private final String address;

	public TokenData(@NotNull HttpServletRequest request) {
		this(request.getRemoteAddr(), System.currentTimeMillis());
	}

	private TokenData(@NotNull String address, long time) {
		this.address = address;
		this.time = time;
	}

	@Nullable
	public TokenData check(@NotNull HttpServletRequest request) {
		// The token is only valid for the same address
		if (!address.equals(request.getRemoteAddr())) {
			return null;
		}

		// The token expires 1 hour after the last access
		long now = System.currentTimeMillis();
		return now - time > 3600000L ? null : new TokenData(address, now);
	}
}

}
