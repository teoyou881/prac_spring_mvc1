package prac_spring_mvc1.demo.domain.web.basic;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prac_spring_mvc1.demo.domain.item.Item;
import prac_spring_mvc1.demo.domain.item.ItemRepository;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    
    private final ItemRepository itemRepository;
    
    @GetMapping public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    
    @GetMapping("/{itemId}")
    public String item(
        @PathVariable
        Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }
    
    //    @PostMapping("/add")
    public String addItemV1(
        @RequestParam
        String itemName,
        @RequestParam
        int price,
        @RequestParam
        Integer quantity, Model model) {
        
        Item item = new Item(itemName, price, quantity);
        
        itemRepository.save(item);
        
        model.addAttribute("item", item);
        
        return "basic/item";
    }
    
    //    @PostMapping("/add")
    public String addItemV2(
        /* @ModelAttribute
         1. create an object and bind it to the passed parameters.
         2. do model.addAttribute(); automatically.
        ModelAttribute("hello") =>  model.add~~("heelo", item)
         If there is no name like ModelAttribute =>
          Only the first leading character of the class name that follows is changed to lowercase.
           @ModelAttribute HelloData item  -->  model.add~~("helloData", item)*/
        @ModelAttribute
        Item item
    ) {
        itemRepository.save(item);
        return "basic/item";
    }
    
    //    @PostMapping("/add")
    public String addItemV3(
        // Can omit @ModelAttribute
        Item item
    ) {
        itemRepository.save(item);
        return "basic/item";
    }
    
    @PostMapping("/add")
    public String addItemV4(
        // Can omit @ModelAttribute
        Item item
    ) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }
    
    @GetMapping("/{itemId}/edit")
    public String editForm(
        @PathVariable Long itemId, Model model
    ) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    
    @PostMapping("/{itemId}/edit")
    public String edit(
        @PathVariable Long itemId,
        Item item
    ) {
        Item findItem = itemRepository.findById(itemId);
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    
    //data for test
    @PostConstruct public void init() {
        itemRepository.save(new Item("A", 1, 100));
        itemRepository.save(new Item("B", 2, 200));
    }
    
}
