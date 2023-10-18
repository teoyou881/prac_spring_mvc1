package prac_spring_mvc1.demo.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import prac_spring_mvc1.demo.web.SessionConst;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	/**
	 * @param request  current HTTP request
	 * @param response current HTTP response
	 * @param handler  chosen handler to execute, for type and/or instance evaluation
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI ();
		log.info ("start LoginCheckInterceptor {}", requestURI);
		HttpSession session = request.getSession ();
		if (session == null || session.getAttribute (SessionConst.LOGIN_MEMBER) == null) {
			log.info ("Unauthenticated user request");
			response.sendRedirect ("/login?redirectURL=" + requestURI);
			return false;
		}
		return true;
	}
}
