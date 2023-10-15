package prac_spring_mvc1.demo.web.basic;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import prac_spring_mvc1.demo.domain.item.Item;

@Component
public class ItemValidator implements Validator {

	@Override public boolean supports (Class<?> clazz) {
		return Item.class.isAssignableFrom (clazz);
		// This method checks if the given class is assignable to the class
		// item == clazz || item's children class == clazz

	}

	@Override public void validate (Object target, Errors errors) {
		Item item = (Item) target;

		// validation logic
		if (!StringUtils.hasText (item.getName ())) {
			//  errors.addError(new FieldError("item", "name", item.getName(), false, new String[]{"required.item.name", "required.default"}, null, null));
			errors.rejectValue ("name", "required");
		}

		if (item.getPrice () == null || item.getPrice () < 1000 || item.getPrice () > 1000000) {
			errors.rejectValue ("price", "range", new Object[]{1000, 1000000}, null);
		}
		if (item.getQuantity () == null || item.getQuantity () < 1 || item.getQuantity () > 9999) {
			errors.rejectValue ("quantity", "max", new Object[]{9999}, null);
		}

		// for complicated rules
		if (item.getPrice () != null && item.getQuantity () != null) {
			int resultPrice = item.getPrice () * item.getQuantity ();
			if (resultPrice < 10000) {
				errors.reject ("totalPriceMin", new Object[]{10000, resultPrice}, null);
			}
		}
	}
}
