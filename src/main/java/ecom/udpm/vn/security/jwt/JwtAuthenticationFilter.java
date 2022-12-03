package ecom.udpm.vn.security.jwt;

import ecom.udpm.vn.security.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private JwtTokenUtil jwtUtils;

	private UserDetailServiceImpl userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if(!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getAccessToken(request);
		if(!jwtUtils.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
	}

	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");

	}

	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return header.split(" ")[1].trim();
	}

	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(token);
		UsernamePasswordAuthenticationToken
				authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		authentication.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private UserDetails getUserDetails(String token) {
		String[] jwtSubject = jwtUtils.getSubject(token).split(",");
//		userDetails.setId((int) Long.parseLong(jwtSubject[0]));
//		userDetails.setUsername(jwtSubject[1]);

		return userDetailService.loadUserByUsername(jwtSubject[1]);
	}
}
