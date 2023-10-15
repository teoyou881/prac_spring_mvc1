package prac_spring_mvc1.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prac_spring_mvc1.demo.domain.item.Item;
import prac_spring_mvc1.demo.domain.item.ItemRepository;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;


	// add data for test
	@PostConstruct
	public void init () {
		itemRepository.save (new Item ("itemA", 10000, 10));
		itemRepository.save (new Item ("itemB", 20000, 30));

		Member member = new Member ();
		member.setLoginId ("test");
		member.setPassword ("123123");
		member.setName ("tester");

		memberRepository.save (member);
	}
}
