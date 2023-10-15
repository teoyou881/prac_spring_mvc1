package prac_spring_mvc1.demo.web.login;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

	@NotEmpty
	private String loginId;
	@NotEmpty
	private String password;
}