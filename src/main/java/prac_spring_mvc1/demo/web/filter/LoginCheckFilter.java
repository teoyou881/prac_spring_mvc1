package prac_spring_mvc1.demo.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import prac_spring_mvc1.demo.web.SessionConst;

@Slf4j
public class LoginCheckFilter implements Filter {

	private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

	@Override
	public void doFilter (ServletRequest request, ServletResponse response,
	                      FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI ();

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {
			log.info ("Start LoginCheckFilter{}", requestURI);

			if (!isLoginCheckPath (requestURI)) {
				log.info ("Run loginCheck logic{}", requestURI);
				HttpSession session = ((HttpServletRequest) request).getSession (false);
				if (session == null || session.getAttribute (SessionConst.LOGIN_MEMBER) == null) {
					log.info ("Unauthenticated user request{}", requestURI);
					httpResponse.sendRedirect ("/login?redirectURL=" + requestURI);
					return;
				}
			}
			chain.doFilter (request, response);
		} catch (Exception e) {
			throw e;
		} finally {
			log.info ("End LoginCheckFilter{}", requestURI);
		}
	}

	// Not check whitelist
	private boolean isLoginCheckPath (String requestURI) {
		return PatternMatchUtils.simpleMatch (whitelist, requestURI);
	}


}

