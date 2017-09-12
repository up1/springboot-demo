package net.tumit.springbootdemo.service;

import java.util.Date;

public interface DBLogService {
	public void logSigninSuccess(final String username, final String remoteAddr, final Date now);
}
