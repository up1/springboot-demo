package net.tumit.springbootdemo.mail;

import com.google.common.base.Charsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailSender {

	private static final boolean MULTIPART = true;

	private final JavaMailSender javaMailSender;

	public MailSending sendPlainText(
					final String from
					, final String to
					, final String subject
					, final String text) {
		return sendMail(from, to, subject, text, false);
	}

	private MailSending sendMail(
					final String from
					, final String to
					, final String subject
					, final String text
					, final boolean html) {

		final MailSending mailSending = new MailSending(from, to, subject, text);

		try {
			final MimeMessage message = javaMailSender.createMimeMessage();
			final MimeMessageHelper helper =
							new MimeMessageHelper(message, MULTIPART, Charsets.UTF_8.name());
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, html);
			javaMailSender.send(message);
			return mailSending.success();
		} catch (MessagingException e) {
			log.error("mail sending is error: exception=", e);
			return mailSending.error(e.getMessage());
		}
	}
}
