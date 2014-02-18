package vacant;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import vacant.constant.Global;
import vacant.domain.VacantUser;
import vacant.service.VacantUserService;

public class SecurityFilter implements Filter {

	protected Log log = LogFactory.getLog(getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI().replaceFirst(
				httpRequest.getContextPath(), "");

		if (requestURI.startsWith("/login") || requestURI.startsWith("/dict/get/")|| requestURI.equals("/organ/tree")
				|| requestURI.equals("/") || requestURI.endsWith(".js")
				|| requestURI.endsWith(".css") || requestURI.endsWith(".gif")
				|| requestURI.endsWith(".png") || requestURI.endsWith(".html")
				|| requestURI.endsWith(".json")
				|| requestURI.endsWith("validation")
				|| requestURI.startsWith("/error")) {
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

		if (requestURI.startsWith("/main") || requestURI.startsWith("/left")
				|| requestURI.startsWith("/top")
				|| requestURI.startsWith("/right")) {
			chain.doFilter(httpRequest, response);
			return;
		}
		VacantUser user = (VacantUser) session
				.getAttribute(Global.SESSION_ATTRIBUTE_USER);
		// Set<String> resourceUrlSet = user.getResourceUrlSet();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(httpRequest.getSession()
						.getServletContext());
		VacantUserService userService = webApplicationContext
				.getBean(VacantUserService.class);
		if (userService.isUserCanAccessResource(user.getId(), requestURI)) {
			chain.doFilter(httpRequest, response);
		} else {
			if (log.isErrorEnabled()) {
				log.error(String.format(
						"User %s don't have permission to visit \"%s\"",
						user.getLoginName(), requestURI));
			}
			String script = "<script type='text/javascript'>top.location.href='";
			script += httpRequest.getContextPath() + "/error/no_privilege";
			script += "'</script>";
			response.getWriter().write(script);
		}
	}

	public void destroy() {
	}

}
