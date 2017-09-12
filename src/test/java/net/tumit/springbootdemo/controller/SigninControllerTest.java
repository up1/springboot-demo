package net.tumit.springbootdemo.controller;

import net.tumit.springbootdemo.mail.MailSender;
import net.tumit.springbootdemo.model.MessageResult;
import net.tumit.springbootdemo.service.DBLogService;
import net.tumit.springbootdemo.service.DateTimeService;
import net.tumit.springbootdemo.service.TokenService;
import net.tumit.springbootdemo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SigninControllerTest {

	@InjectMocks
	private SigninController signinController;

	@Mock
	private HttpServletRequest request;

	@Mock
	private UserService userService;

	@Mock
	private DBLogService dbLogService;

	@Mock
	private MailSender mailSender;

	@Mock
	private DateTimeService dateTimeService;

	@Mock
	private TokenService tokenService;

	@Test
	public void validSignin_should_ok() throws Exception {

		// arrange
		String username = "my_user";
		String password = "my_pass";
		String authHeader = "x-auth-token";

		ReflectionTestUtils.setField(signinController, "authHeader", authHeader);

		List<String> mockAuthorizes = new ArrayList<>();
		mockAuthorizes.add("data_read");
		mockAuthorizes.add("data_insert");
		when(userService.getAuthorizes()).thenReturn(mockAuthorizes);
		
		String mockToken = "MOCK_TOKEN";
		when(tokenService.genToken(username, mockAuthorizes)).thenReturn(mockToken);

		MessageResult mockResult = MessageResult.success();
		when(userService.authenticate(username, password)).thenReturn(mockResult);

		when(request.getRemoteAddr()).thenReturn("127.0.0.1");

		Date now = Calendar.getInstance().getTime();
		when(dateTimeService.now()).thenReturn(now);

		// act
		ResponseEntity<MessageResult> result = signinController.signin(username, password);

		// assert
		ResponseEntity okResponseEntity =
				ResponseEntity.ok()
						.header("Access-Control-Expose-Headers", authHeader)
						.header(authHeader, mockToken)
						.body(mockResult);
		assertThat(result).isEqualTo(okResponseEntity);

	}
}
