package net.tumit.springbootdemo.mail;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
public class MailSending {

	private String from;
	private String to;
	private String subject;
	private String text;
	private boolean success;
	private String errorMessage;

	public MailSending(
					final String from
					,final String to
					,final String subject
					,final String text) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public MailSending success() {
		this.success = true;
		return this;
	}

	public MailSending error(String errorMessage) {
			this.success = false;
			this.errorMessage = errorMessage;
			return this;
	}

	public boolean isError() {
		return success == false;
	}

}
