package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.payload.LoginRequest;
import ecom.udpm.vn.dto.response.Account.JwtAuthenticationResponse;
import ecom.udpm.vn.entity.Account;
import ecom.udpm.vn.exception.AccountException;
import ecom.udpm.vn.security.jwt.JwtTokenUtil;
import ecom.udpm.vn.security.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/")
@RestController
@CrossOrigin
@AllArgsConstructor
public class LoginController {
	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtUtils;

	private final ModelMapper modelMapper;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), loginRequest.getPassword())
			);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			Account account = modelMapper.map(userDetails.getAccount(), Account.class);
			String accessToken = jwtUtils.generateAccessToken(account);
			JwtAuthenticationResponse response = new JwtAuthenticationResponse(accessToken);
			return ResponseEntity.ok(response);

		} catch(BadCredentialsException ex) {
			throw new AccountException("Invalid Username or Password");
		}
	}
}
