package vacant;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class VacantHandlerExceptionResolver extends
		AbstractHandlerExceptionResolver {

	public VacantHandlerExceptionResolver() {
		setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		try {
			String script = "<script type='text/javascript'>top.location.href='";
			script += request.getContextPath() + "/error/system_error";
			script += "'</script>";
			response.getWriter().write(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView();
	}
}
