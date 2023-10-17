package prac_spring_mvc1.demo.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SessionInfoController {

	@GetMapping ("/session-info")
	public String sessionInfo (HttpServletRequest request) {
		HttpSession session = request.getSession (false);
		if (session == null) {
			return "no session";
		}

		// print session info
		session.getAttributeNames ()
		       .asIterator ()
		       .forEachRemaining (
			       name -> log.info ("session name={}, value={}", name, session.getAttribute (name)));

		log.info ("sessionId={}", session.getId ());
		log.info ("maxInactiveInterval={}", session.getMaxInactiveInterval ());
		log.info ("creationTime={}", new Date (session.getCreationTime ()));
		log.info ("lastAccessedTime={}", new Date (session.getLastAccessedTime ()));
		log.info ("isNew={}", session.isNew ());

		return "sessionInfo";
	}
}
