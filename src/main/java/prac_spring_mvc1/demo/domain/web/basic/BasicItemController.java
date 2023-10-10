package prac_spring_mvc1.demo.domain.web.basic;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prac_spring_mvc1.demo.domain.item.DeliveryCode;
import prac_spring_mvc1.demo.domain.item.Item;
import prac_spring_mvc1.demo.domain.item.ItemRepository;
import prac_spring_mvc1.demo.domain.item.ItemType;

@Controller
@Slf4j
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    
    private final ItemRepository itemRepository;
    
    /*The @ModelAttribute can be applied to a separate method in the controller like this.
    This way, when that controller is requested, the value returned by regions will automatically be put into the model.
    Of course, you can also use it this way and put the data directly into the model in each controller method.
    * */
    @ModelAttribute("regions") public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "seoul");
        regions.put("BUSAN", "busan");
        regions.put("JEJU", "jeju");
        return regions;
    }
    
    @ModelAttribute("itemTypes") public ItemType[] itemTypes() {
        // Passes the values in the enumeration to an array.
        return ItemType.values();
    }
    
    @ModelAttribute("deliveryCodes") public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "fast"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "default"));
        deliveryCodes.add(new DeliveryCode("SLOW", "slow"));
        
        return deliveryCodes;
    }
    
    @GetMapping public String items(Model model, Locale locale) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    
    @GetMapping("/{itemId}") public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    
    @GetMapping("/add") public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }
    
    //    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price,
                            @RequestParam Integer quantity, Model model) {
        
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
        @ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }
    
    //    @PostMapping("/add")
    public String addItemV3(
        // Can omit @ModelAttribute
        Item item) {
        itemRepository.save(item);
        return "basic/item";
    }
    
    @PostMapping("/add") public String addItemV4(
        // Can omit @ModelAttribute
        Item item,
        // RedirectAttributes is used to pass data to the redirected URL.
        // It does all the basic URL encoding.
        RedirectAttributes redirectAttributes
        , Model model) {
        
        // save validation error data
        Map<String, String> errors = new HashMap<>();
        
        // validation logic
        if (!StringUtils.hasText(item.getName())) {
            errors.put("name", "Product name is required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "Price must be between 1000 and 1000000");
        }
        if (item.getQuantity() == null || item.getQuantity() < 1 || item.getQuantity() > 9999) {
            errors.put("quantity", "Quantity must be between 1 and 9999");
        }
        
        // for complicated rules
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError",
                           "Total price must be over 10000. Current price is " + resultPrice);
            }
        }
        
        // if there is an error, return to the form
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "basic/addForm";
        }
        
        // success case below
        
        Item savedItem = itemRepository.save(item);
        /* itemid should be replaced with the name of the variable in the path.
         * the thing like status which is not replaced with anything must be added as query parameter.*/
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
        ///basic/items/3?status=true
    }
    
    @GetMapping("/{itemId}/edit") public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    
    @PostMapping("/{itemId}/edit") public String edit(@PathVariable Long itemId, Item item) {
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
