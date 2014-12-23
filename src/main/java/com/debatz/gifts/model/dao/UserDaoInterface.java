package com.debatz.gifts.model.dao;

import com.debatz.gifts.model.User;


public interface UserDaoInterface 
{
	
	public User findByUserName(String username);
 
}