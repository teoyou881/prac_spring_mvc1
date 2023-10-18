package prac_spring_mvc1.demo.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

	public static final String LOG_ID = "logId";
	public static final String REQUEST_URI = "requestURI";

	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestURI = request.getRequestURI ();
		String logId = UUID.randomUUID ().toString ();

		request.setAttribute (REQUEST_URI, requestURI);
		request.setAttribute (LOG_ID, logId);

		// @requestMapping: HandlerMethod
		// static resource: ResourceHttpRequestHandler
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
		}

		log.info ("REQUEST [{}][{}][{}]", logId, requestURI, handler);

		return true;
	}

	@Override
	public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info ("postHandle [{}]", modelAndView);
	}


	@Override
	public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		String requestURI = (String) request.getAttribute (REQUEST_URI);
		String logId = (String) request.getAttribute (LOG_ID);

		log.info ("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
		if (ex != null) {
			log.error ("afterCompletion error!!", ex);
		}
	}
}
