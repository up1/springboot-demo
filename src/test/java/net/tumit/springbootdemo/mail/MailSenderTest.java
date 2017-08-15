package net.tumit.springbootdemo.mail;

import com.google.common.base.Charsets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({MailSender.class})
@RunWith(PowerMockRunner.class)
public class MailSenderTest {

	private MailSender mailSender;
	private JavaMailSender javaMailSender;

	@Before
	public void setUp() {
		javaMailSender = mock(JavaMailSender.class);
		mailSender = new MailSender(javaMailSender);
	}

	@Test
	public void sendPlainText() throws Exception {

		// arrange
		String from = "sender@test.com";
		String to = "receiver@test.com";
		String subject = "My subject";
		String text = "My text";
		boolean multipart = true;
		boolean html = false;

		MailSending successMailSending = new MailSending(from, to, subject, text).success();

		MimeMessage message = mock(MimeMessage.class);
		when(javaMailSender.createMimeMessage()).thenReturn(message);

		MimeMessageHelper messageHelper = mock(MimeMessageHelper.class);
		whenNew(MimeMessageHelper.class)
						.withArguments(message, multipart, Charsets.UTF_8.name())
						.thenReturn(messageHelper);

		// act
		MailSending mailSending = mailSender.sendPlainText(from, to, subject, text);
		// assert
		verifyNew(MimeMessageHelper.class)
						.withArguments(message, multipart, Charsets.UTF_8.name());
		verify(messageHelper).setFrom(from);
		verify(messageHelper).setTo(to);
		verify(messageHelper).setSubject(subject);
		verify(messageHelper).setText(text, html);
		verify(javaMailSender).send(message);
		
		assertThat(mailSending).isEqualTo(successMailSending);
	}


}
