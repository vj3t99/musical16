package com.musical16.service;

import javax.servlet.http.HttpServletRequest;

public interface IHelpService {

	public String getName(HttpServletRequest req);
	
	public String getSiteURL(HttpServletRequest request);
}
