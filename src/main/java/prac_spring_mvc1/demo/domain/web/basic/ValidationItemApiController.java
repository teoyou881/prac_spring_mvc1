package prac_spring_mvc1.demo.domain.web.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prac_spring_mvc1.demo.domain.web.basic.form.ItemSaveForm;

@Slf4j
@RestController
@RequestMapping ("/validation/api/items")
public class ValidationItemApiController {

	@PostMapping ("/add")
	public Object addItem (@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
		log.info ("api controller call");
		if (bindingResult.hasErrors ()) {
			log.info ("error={}", bindingResult);
			return bindingResult.getAllErrors ();
		}

		return form;
	}

}
