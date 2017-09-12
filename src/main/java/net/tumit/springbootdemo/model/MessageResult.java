package net.tumit.springbootdemo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResult {
	private Boolean status;

	public static MessageResult error() {
		return MessageResult.builder().status(false).build();
	}

	public static MessageResult success() {
		return MessageResult.builder().status(true).build();
	}
}
