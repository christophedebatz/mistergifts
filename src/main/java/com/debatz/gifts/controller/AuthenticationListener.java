package com.debatz.gifts.controller;

import com.debatz.gifts.bean.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private SessionBean sessionBean;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();

        this.sessionBean.setUsername(userDetails.getUsername());
    }
}
