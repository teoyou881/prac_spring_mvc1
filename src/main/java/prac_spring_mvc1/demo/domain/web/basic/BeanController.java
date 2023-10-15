package prac_spring_mvc1.demo.domain.web.basic;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prac_spring_mvc1.demo.domain.item.DeliveryCode;
import prac_spring_mvc1.demo.domain.item.Item;
import prac_spring_mvc1.demo.domain.item.ItemRepository;
import prac_spring_mvc1.demo.domain.item.ItemType;

@Controller
@Slf4j
@RequestMapping ("/basic/items")
@RequiredArgsConstructor
public class BeanController {

	private final ItemRepository itemRepository;

	@ModelAttribute ("regions") public Map<String, String> regions () {
		Map<String, String> regions = new LinkedHashMap<> ();
		regions.put ("SEOUL", "seoul");
		regions.put ("BUSAN", "busan");
		regions.put ("JEJU", "jeju");
		return regions;
	}

	@ModelAttribute ("itemTypes") public ItemType[] itemTypes () {
		// Passes the values in the enumeration to an array.
		return ItemType.values ();
	}

	@ModelAttribute ("deliveryCodes") public List<DeliveryCode> deliveryCodes () {
		List<DeliveryCode> deliveryCodes = new ArrayList<> ();
		deliveryCodes.add (new DeliveryCode ("FAST", "fast"));
		deliveryCodes.add (new DeliveryCode ("NORMAL", "default"));
		deliveryCodes.add (new DeliveryCode ("SLOW", "slow"));

		return deliveryCodes;
	}

	@GetMapping public String items (Model model, Locale locale) {
		List<Item> items = itemRepository.findAll ();
		model.addAttribute ("items", items);
		return "basic/items";
	}

	@GetMapping ("/{itemId}") public String item (@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById (itemId);
		model.addAttribute ("item", item);
		return "basic/item";
	}

	@GetMapping ("/add") public String addForm (Model model) {
		model.addAttribute ("item", new Item ());
		return "basic/addForm";
	}

	//    Validated is an annotation to run the validator.
//    When this annotation is attached, the WebDataBinder will find and run the validator you registered earlier.
//    However, if you register multiple validators, you need to distinguish which validator should be executed.
//    This is where supports() is used.
//    Here, supports(Item.class) is called, and since the result is true, validate() of ItemValidator is called.
//    Translated with www.DeepL.com/Translator (free version)
	@PostMapping ("/add")
	public String addItemV (
		//BindingResult The position of the bindingResult parameter must be after the @ModelAttribute Item item.
		@Validated Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (item.getPrice () != null && item.getQuantity () != null) {
			int resultPrice = item.getPrice () * item.getQuantity ();
			if (resultPrice < 10000) {
				bindingResult.reject ("totalPriceMin", new Object[]{10000, resultPrice}, null);
			}
		}

		if (bindingResult.hasErrors ()) {
			log.info ("errors={}", bindingResult);
			return "basic/addForm";
		}

		// success case below

		Item savedItem = itemRepository.save (item);
		/* itemid should be replaced with the name of the variable in the path.
		 * the thing like status which is not replaced with anything must be added as query parameter.*/
		redirectAttributes.addAttribute ("itemId", savedItem.getId ());
		redirectAttributes.addAttribute ("status", true);
		return "redirect:/basic/items/{itemId}";
		///basic/items/3?status=true
	}

	@GetMapping ("/{itemId}/edit") public String editForm (@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById (itemId);
		model.addAttribute ("item", item);
		return "basic/editForm";
	}

	@PostMapping ("/{itemId}/edit") public String edit (@PathVariable Long itemId, Item item) {
		Item findItem = itemRepository.findById (itemId);
		itemRepository.update (itemId, item);
		return "redirect:/basic/items/{itemId}";
	}

	//data for test
	@PostConstruct public void init () {
		itemRepository.save (new Item ("A", 1, 100));
		itemRepository.save (new Item ("B", 2, 200));
	}

}