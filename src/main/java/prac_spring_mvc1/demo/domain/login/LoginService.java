package prac_spring_mvc1.demo.domain.login;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;

	/**
	 * @return null ? --> login fail
	 */
	public Member login (String loginId, String password) {
		return memberRepository.findByLoginId (loginId)
		                       .filter (m -> m.getPassword ().equals (password))
		                       .orElse (null);
	}
}