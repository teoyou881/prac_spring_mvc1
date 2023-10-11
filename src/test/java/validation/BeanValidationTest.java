package validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import prac_spring_mvc1.demo.domain.item.Item;

public class BeanValidationTest {

	@Test
	void beanvalidation () throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory ();
		Validator validator = factory.getValidator ();

		Item item = new Item ();
		item.setName (" ");
		item.setPrice (0);
		item.setQuantity (10000);

		// If there is no error, the list is empty.
		validator.validate (item).forEach (validation -> {
			System.out.println ("validation = " + validation);
			System.out.println ("validation.getMessage () = " + validation.getMessage ());
		});

	}
}
