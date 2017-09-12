package net.tumit.springbootdemo.service;

import java.util.List;

public interface TokenService {
	public String genToken(final String username, final List<String> authorizes);
}
