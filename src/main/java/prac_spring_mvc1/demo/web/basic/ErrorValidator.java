package prac_spring_mvc1.demo.web.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@Slf4j
public class ErrorValidator implements Validator {

	public boolean existError (Errors errors) {
		if (errors.hasErrors ()) {
			log.info ("errors={}", errors);
			return true;
		}
		return false;
	}

	@Override public boolean supports (Class<?> clazz) {
		return false;
	}

	@Override public void validate (Object target, Errors errors) {

	}
}
