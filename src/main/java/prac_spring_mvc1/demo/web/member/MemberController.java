package prac_spring_mvc1.demo.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.domain.member.MemberRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping ("/members")
public class MemberController {

	private final MemberRepository memberRepository;

	@GetMapping ("/add")
	public String addForm (@ModelAttribute Member member) {
		return "members/addMemberForm";
	}

	@PostMapping ("/add")
	public String save (@Validated @ModelAttribute Member member, BindingResult bindingResult) {
		if (bindingResult.hasErrors ()) {
			return "members/addMemberForm";
		}

		memberRepository.save (member);
		return "redirect:/";
	}
}
