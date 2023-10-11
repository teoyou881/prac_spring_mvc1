package prac_spring_mvc1.demo.domain.web.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
@Slf4j
public class ErrorValidator {
    
    public boolean existError(Errors errors) {
        if (errors.hasErrors()) {
            log.info("errors={}", errors);
            return true;
        }
        return false;
    }
}
