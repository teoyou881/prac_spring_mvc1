package prac_spring_mvc1.demo.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.domain.member.MemberRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MemberRepository memberRepository;

	//	@GetMapping ("/")
	public String home () {
		return "basic/home";
	}

	@GetMapping ("/")
	public String homeLogin (@CookieValue (name = "memberId", required = false) Long memberId, Model model) {
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

}
