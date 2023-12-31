package prac_spring_mvc1.demo.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI ();
		String uuid = UUID.randomUUID ().toString ();
		try {
			log.info ("Request [{}][{}]", uuid, requestURI);
			chain.doFilter (request, response);
		} catch (Exception e) {throw e;} finally {log.info ("Response [{}][{}]", uuid, requestURI);}
	}
}
