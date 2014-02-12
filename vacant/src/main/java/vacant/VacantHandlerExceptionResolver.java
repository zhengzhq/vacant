package vacant;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import vacant.util.AjaxResult;

public class VacantHandlerExceptionResolver extends
		SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		String requestURI = request.getRequestURI();
		if (requestURI.indexOf("/ajax/") > 0) {// JSON格式返回
			try {
				PrintWriter writer = response.getWriter();
				String json = new ObjectMapper().writeValueAsString(AjaxResult
						.fail("系统异常："+ex.getMessage()));
				writer.write(json);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ModelAndView();
		} else {
			// 如果不是异步请求
			return super.doResolveException(request, response, handler, ex);
		}
	}
}
