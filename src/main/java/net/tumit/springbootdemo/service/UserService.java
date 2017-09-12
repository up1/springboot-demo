package net.tumit.springbootdemo.service;

import net.tumit.springbootdemo.model.MessageResult;

import java.util.List;

public interface UserService {
	public MessageResult authenticate(final String username, final String password);
	public List<String> getAuthorizes();
}
