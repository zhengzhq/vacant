package vacant;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import vacant.constant.Global;
import vacant.domain.VacantUser;

public class SecurityFilter implements Filter {

	protected Log log = LogFactory.getLog(getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String requestURI = httpRequest.getRequestURI().replaceFirst(
				httpRequest.getContextPath(), "");

		if (requestURI.startsWith("/login") || requestURI.startsWith("/main")
				|| requestURI.startsWith("/left")
				|| requestURI.startsWith("/top")
				|| requestURI.startsWith("/right") || requestURI.equals("/")
				|| requestURI.endsWith(".js") || requestURI.endsWith(".css")
				|| requestURI.endsWith(".gif") || requestURI.endsWith(".png")
				|| requestURI.endsWith(".html")) {
			chain.doFilter(httpRequest, response);
			return;
		}

		if (log.isDebugEnabled()) {
			log.debug(String.format(
					"URI \"%s\" will be managered by SecurityManager.",
					requestURI));
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			String loginURI = httpRequest.getContextPath() + "/login";
			String script = "<script type='text/javascript'>top.location.href='";
			script += loginURI;
			script += "'</script>";
			response.getWriter().write(script);
			return;
		}
		VacantUser user = (VacantUser) session
				.getAttribute(Global.SESSION_ATTRIBUTE_USER);
		Set<String> resourceUrlSet = user.getResourceUrlSet();
		if (!resourceUrlSet.contains(requestURI)) {
			if (log.isErrorEnabled()) {
				log.error(String.format(
						"User %s don't have permission to visit \"%s\"",
						user.getLoginName(), requestURI));
			}
			httpResponse.sendRedirect(httpRequest.getContextPath()
					+ "/no_privilege");
		} else {
			chain.doFilter(httpRequest, response);
		}

	}

	public void destroy() {
	}

}
