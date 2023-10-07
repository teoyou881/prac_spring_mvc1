package prac_spring_mvc1.demo.domain.web.basic;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @GetMapping("/{itemId}")
    public String item(
        @PathVariable
        Long itemId, Model model
    ) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }
    
    @PostMapping("/add")
    public String save() {
        return "basic/addForm";
    }
    
    //data for test
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("A", 1, 100));
        itemRepository.save(new Item("B", 2, 200));
    }
    
}
