package net.tumit.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
import net.tumit.springbootdemo.model.MessageResult;
import net.tumit.springbootdemo.service.DBLogService;
import net.tumit.springbootdemo.service.DateTimeService;
import net.tumit.springbootdemo.service.TokenService;
import net.tumit.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public class SigninController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	private DBLogService dbLogService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private DateTimeService dateTimeService;

	@Value("${jwt.auth.header}")
	private String authHeader;

	@PostMapping(value = "/signin")
	public ResponseEntity<MessageResult> signin(
			@RequestParam(value = "username", defaultValue = "") final String username,
			@RequestParam(value = "password", defaultValue = "") final String password) {

		MessageResult signInResult = null;
		try {

			signInResult = userService.authenticate(username, password);

			if (signInResult.getStatus()) {

				dbLogService.logSigninSuccess(username, request.getRemoteAddr(), dateTimeService.now());

				List<String> authorizes = userService.getAuthorizes();

				return ResponseEntity.ok()
						.header("Access-Control-Expose-Headers", authHeader)
						.header(authHeader, tokenService.genToken(username, authorizes))
						.body(signInResult);
			}
		} catch (Exception e) {
			log.error("Exception: username=" + username, e);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(MessageResult.error());
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(signInResult);
	}

}
