package prac_spring_mvc1.demo.domain.web.basic;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prac_spring_mvc1.demo.domain.item.Item;
import prac_spring_mvc1.demo.domain.item.ItemRepository;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    
    private final ItemRepository itemRepository;
    
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("A", 1, 100));
        itemRepository.save(new Item("B", 2, 200));
    }
    
}
