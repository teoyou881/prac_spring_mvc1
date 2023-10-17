package prac_spring_mvc1.demo.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prac_spring_mvc1.demo.web.filter.LogFilter;

@Configuration
public class WebConfig {

	@Bean
	public FilterRegistrationBean LogFilter () {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean (new LogFilter ());
		filterRegistrationBean.setOrder (1);
		filterRegistrationBean.addUrlPatterns ("/*");
		return filterRegistrationBean;
	}
}
