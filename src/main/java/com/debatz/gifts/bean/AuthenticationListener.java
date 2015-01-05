package com.debatz.gifts.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

import com.debatz.gifts.model.dao.UserDao;

public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent>
{

	@Autowired
	private SessionBean sessionBean;
	
	@Autowired
	private UserDao userDao;
	
	
	@Override
	public void onApplicationEvent(final AuthenticationSuccessEvent event) 
	{
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        
        this.sessionBean.setCurrentUser(
    		this.userDao.findByUserName(userDetails.getUsername())
		);
	}
}
