package prac_spring_mvc1.demo.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prac_spring_mvc1.demo.web.filter.LogFilter;
import prac_spring_mvc1.demo.web.filter.LoginCheckFilter;
import prac_spring_mvc1.demo.web.interceptor.LogInterceptor;
import prac_spring_mvc1.demo.web.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Add Spring MVC lifecycle interceptors for pre- and post-processing of controller method invocations and
	 * resource handler requests. Interceptors can be registered to apply to all requests or be limited to a
	 * subset of URL patterns.
	 *
	 * @param registry
	 */
	@Override public void addInterceptors (InterceptorRegistry registry) {
		registry.addInterceptor (new LogInterceptor ())
		        .order (1)
		        .addPathPatterns ("/**")
		        .excludePathPatterns ("/css/**", "/*.ico", "/error");
		registry.addInterceptor (new LoginCheckInterceptor ())
		        .order (2)
		        .addPathPatterns ("/**")
		        .excludePathPatterns ("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
	}

	@Bean
	public FilterRegistrationBean logFilter () {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean (new LogFilter ());
		filterRegistrationBean.setOrder (1);
		filterRegistrationBean.addUrlPatterns ("/*");
		return filterRegistrationBean;
	}

	//	@Bean
	public FilterRegistrationBean loginCheckFilter () {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean (new LoginCheckFilter ());
		filterRegistrationBean.setOrder (2);
		filterRegistrationBean.addUrlPatterns ("/*");
		return filterRegistrationBean;
	}
}
