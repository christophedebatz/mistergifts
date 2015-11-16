package com.debatz.gifts.controller;

import com.debatz.gifts.service.ClientService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthorizationHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LogManager.getLogger("UserEvent");
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
    }

    protected void handle(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException {
        
        String ip = ClientService.getClientAddr(request);
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        
        logger.info("User " + username + " has just logged in himself with IP = " + username);
        
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
