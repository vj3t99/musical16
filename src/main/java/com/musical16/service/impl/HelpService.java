package com.musical16.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musical16.config.TokenProvider;
import com.musical16.service.IHelpService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class HelpService implements IHelpService{

	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Override
	public String getName(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith("Bearer")) {
            authToken = header.replace("Bearer","");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                return "An error occurred while fetching Username from Token";
            } catch (ExpiredJwtException e) {
                return "The token has expired";
            } catch(SignatureException e){
                return "Authentication Failed. Username or Password not valid.";
            }
        } else {
            return "Couldn't find bearer string, header will be ignored";
        }
		return username;
	}
	
	@Override
	public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
