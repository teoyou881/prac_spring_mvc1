package prac_spring_mvc1.demo.web.login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import prac_spring_mvc1.demo.domain.login.LoginService;
import prac_spring_mvc1.demo.domain.member.Member;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@GetMapping ("/login") public String loginForm (@ModelAttribute ("loginForm") LoginForm form) {
		return "login/loginForm";
	}

	@PostMapping ("/login")
	public String login (@Valid @ModelAttribute LoginForm form, BindingResult bindingResult) {
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
//TODO
// login success

		return "redirect:/";
	}
}