package prac_spring_mvc1.demo.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

	public static final String SESSION_COOKIE_NAME = "mySessionId";
	private Map<String, Object> sessionStore = new ConcurrentHashMap<> ();

	//	create a session
	public void createSession (Object value, HttpServletResponse response) {
		// craete session id, save value in session store
		String sessionId = UUID.randomUUID ().toString ();
		sessionStore.put (sessionId, value);

		// create cookie
		Cookie mySessionCookie = new Cookie (SESSION_COOKIE_NAME, sessionId);
		response.addCookie (mySessionCookie);
	}

	// find session
	public Object getSession (HttpServletRequest request) {
		Cookie sessionCookie = findCookie (request, SESSION_COOKIE_NAME);
		if (sessionCookie == null) {
			return null;
		}
		return sessionStore.get (sessionCookie.getValue ());
	}

	// expire session
	public void expire (HttpServletRequest request) {
		Cookie sessionCookie = findCookie (request, SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			sessionStore.remove (sessionCookie.getValue ());
		}
	}

	public Cookie findCookie (HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies ();
		if (cookies == null) {
			return null;
		}
		return Arrays.stream (cookies)
		             .filter (cookie -> cookie.getName ().equals (cookieName))
		             .findAny ().orElse (null);
	}

}
