package vacant.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import vacant.web.AppConfig;

public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);
    @Autowired
    private AppConfig config;
    
	public void contextInitialized(ServletContextEvent sce) {
        if (log.isInfoEnabled()) {
            log.info("Servlet Context initialized...");
        }
        ServletContext context = sce.getServletContext();
        //设置当前WEB路径上下文
        String contextPath = context.getContextPath();
        context.setAttribute("contextPath", contextPath);

        config.loadDicts();
	}

	public void contextDestroyed(ServletContextEvent sce) {
        if (log.isInfoEnabled()) {
            log.info("Servlet Context destroyed...");
        }
	}

}
