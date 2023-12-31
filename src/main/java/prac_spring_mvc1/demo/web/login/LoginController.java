package prac_spring_mvc1.demo.web.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prac_spring_mvc1.demo.domain.login.LoginService;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.web.SessionConst;
import prac_spring_mvc1.demo.web.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final SessionManager sessionManager;

	private static void expireCookie (HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie (cookieName, null);
		cookie.setMaxAge (0);
		response.addCookie (cookie);
	}

	@GetMapping ("/login") public String loginForm (@ModelAttribute ("loginForm") LoginForm form) {
		return "login/loginForm";
	}

	//	@PostMapping ("/login")
	public String loginV1 (@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
	                       HttpServletResponse response) {
		if (bindingResult.hasErrors ()) {
			return "login/loginForm";
		}
		Member loginMember = loginService.login (form.getLoginId (), form.getPassword ());

		log.info ("login? {}", loginMember);
		if (loginMember == null) {
			// global error
			bindingResult.reject ("loginFail", "Id or password is not matched");
			return "login/loginForm";
		}

		// If you don't give time info to the cookie, it will be a session cookie. (When browser is closed, it will be deleted.)
		Cookie memberIdCookie = new Cookie ("memberId", String.valueOf (loginMember.getId ()));
		response.addCookie (memberIdCookie);
		return "redirect:/";
	}


	//	@PostMapping ("/login")
	public String loginV2 (@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
	                       HttpServletResponse response) {
		if (bindingResult.hasErrors ()) {
			return "login/loginForm";
		}
		Member loginMember = loginService.login (form.getLoginId (), form.getPassword ());

		if (loginMember == null) {
			// global error
			bindingResult.reject ("loginFail", "Id or password is not matched");
			return "login/loginForm";
		}

		sessionManager.createSession (loginMember, response);
		return "redirect:/";
	}

	//	@PostMapping ("/login")
	public String loginV3 (@Valid @ModelAttribute LoginForm form,
	                       BindingResult bindingResult, HttpServletRequest request) {

		if (bindingResult.hasErrors ()) {return "login/loginForm";}

		Member loginMember = loginService.login (form.getLoginId (), form.getPassword ());

		if (loginMember == null) {
			// global error
			bindingResult.reject ("loginFail", "Id or password is not matched");
			return "login/loginForm";
		}

		// If there is a session, return it. If not, create a new session.
		HttpSession session = request.getSession ();
		// save login member info in session
		session.setAttribute (SessionConst.LOGIN_MEMBER, loginMember);

		return "redirect:/";
	}

	@PostMapping ("/login")
	public String loginV4 (@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
	                       HttpServletRequest request,
	                       @RequestParam (defaultValue = "/") String redirectURL) {

		if (bindingResult.hasErrors ()) {return "login/loginForm";}
		Member loginMember = loginService.login (form.getLoginId (), form.getPassword ());

		if (loginMember == null) {
			// global error
			bindingResult.reject ("loginFail", "Id or password is not matched");
			return "login/loginForm";
		}

		HttpSession session = request.getSession ();
		session.setAttribute (SessionConst.LOGIN_MEMBER, loginMember);

		return "redirect:" + redirectURL;
	}

	//	@PostMapping ("/logout")
	public String logoutV1 (HttpServletResponse response) {
		expireCookie (response, "memberId");
		return "redirect:/";
	}

	//	@PostMapping ("/logout")
	public String logoutV2 (HttpServletRequest request) {
		sessionManager.expire (request);
		return "redirect:/";
	}

	@PostMapping ("/logout")
	public String logoutV3 (HttpServletRequest request) {
		HttpSession session = request.getSession (false);
		if (session != null) {
			// delete session
			session.invalidate ();
		}
		return "redirect:/";
	}
}