package prac_spring_mvc1.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//    springboot is using the default message source, so you don't need to set it up.
//    @Bean
//    public MessageSource messageSource() { ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("messages", "errors");
//        messageSource.setDefaultEncoding("utf-8");
//        return messageSource;
//    }

}
