package prac_spring_mvc1.demo.web.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import prac_spring_mvc1.demo.domain.member.Member;
import prac_spring_mvc1.demo.web.SessionConst;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * @param parameter the method parameter to check
	 * @return
	 */
	@Override public boolean supportsParameter (MethodParameter parameter) {
		log.info ("supportsParameter");
		boolean hasLoginAnnotation = parameter.hasParameterAnnotation (Login.class);
		boolean hasMemberType = Member.class.isAssignableFrom (parameter.getParameterType ());

		return hasLoginAnnotation && hasMemberType;

	}

	/**
	 * @param parameter     the method parameter to resolve. This parameter must have previously been passed
	 *                      to {@link #supportsParameter} which must have returned {@code true}.
	 * @param mavContainer  the ModelAndViewContainer for the current request
	 * @param webRequest    the current request
	 * @param binderFactory a factory for creating {@link WebDataBinder} instances
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object resolveArgument (MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		log.info ("resolveArgument");
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest ();
		HttpSession session = request.getSession (false);
		if (session == null) {
			return null;
		}

		return session.getAttribute (SessionConst.LOGIN_MEMBER);
	}
}
