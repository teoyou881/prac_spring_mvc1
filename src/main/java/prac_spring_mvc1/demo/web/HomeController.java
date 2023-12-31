package prac_spring_mvc1.demo.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.domain.member.MemberRepository;
import prac_spring_mvc1.demo.web.argumentResolver.Login;
import prac_spring_mvc1.demo.web.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;

	//	@GetMapping ("/")
	public String home () {
		return "basic/home";
	}

	//	@GetMapping ("/")
	public String homeLoginV1 (@CookieValue (name = "memberId", required = false) Long memberId,
	                           Model model) {
		if (memberId == null) {
			return "basic/home";
		}
		// login
		Member loginMember = memberRepository.findById (memberId);
		if (loginMember == null) {
			return "basic/home";
		}
		model.addAttribute ("member", loginMember);
		return "members/loginHome";
	}

	//	@GetMapping ("/")
	public String homeLoginV2 (HttpServletRequest request, Model model) {

		Member member = (Member) sessionManager.getSession (request);

		if (member == null) {
			return "basic/home";
		}

		model.addAttribute ("member", member);
		return "members/loginHome";
	}

	//@GetMapping ("/")
	public String homeLoginV3 (HttpServletRequest request, Model model) {

		HttpSession session = request.getSession (false);
		if (session == null) {return "basic/home";}

		Member loginMember = (Member) session.getAttribute (SessionConst.LOGIN_MEMBER);

		// If there is no user data in the session, it is not logged in.
		if (loginMember == null) {return "basic/home";}

		model.addAttribute ("member", loginMember);
		return "members/loginHome";
	}

	//	@GetMapping ("/")
	public String homeLoginV3Spring (
		@SessionAttribute (name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
		Model model) {
		if (loginMember == null) {
			return "basic/home";
		}
		model.addAttribute ("member", loginMember);
		return "members/loginHome";
	}

	@GetMapping ("/")
	public String homeLoginV3SpringArgumentResolver (@Login Member loginMember, Model model) {
		if (loginMember == null) {
			return "basic/home";
		}
		model.addAttribute ("member", loginMember);
		return "members/loginHome";
	}
}

