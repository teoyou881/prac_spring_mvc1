package prac_spring_mvc1.demo.web.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import prac_spring_mvc1.demo.domain.member.Member;

class SessionManagerTest {

	SessionManager sessionManager = new SessionManager ();

	@Test
	void sessionTest () {
		/* Can't use 'HttpServletRequest, HttpServletResponse' objects, because they are from jakarta.servlet.http package.
		 * So, I used 'MockHttpServletRequest, MockHttpServletResponse' objects from spring-test library.
		 * */

		// create session
		MockHttpServletResponse response = new MockHttpServletResponse ();
		Member member = new Member ();
		sessionManager.createSession (member, response);

		// save response cookie
		MockHttpServletRequest request = new MockHttpServletRequest ();
		request.setCookies (response.getCookies ());

		//find session
		Object result = sessionManager.getSession (request);
		Assertions.assertThat (result).isEqualTo (member);

		// expire session
		sessionManager.expire (request);
		Object expired = sessionManager.getSession (request);
		Assertions.assertThat (expired).isNull ();
	}


}