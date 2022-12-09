package ecom.udpm.vn.security.jwt;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final HandlerExceptionResolver resolver;

	public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
		log.error("Unauthorized error: {}", authException.getMessage());
		if(httpServletRequest.getAttribute("javax.servlet.error.exception") != null) {
			Throwable throwable = (Throwable) httpServletRequest.getAttribute("javax.servlet.error.exception");
			resolver.resolveException(httpServletRequest, httpServletResponse, null, (Exception) throwable);
		}

		if(!httpServletResponse.isCommitted()) {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		}
	}
}
