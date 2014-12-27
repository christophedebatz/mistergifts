package com.debatz.gifts.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.dao.GiftDao;
 
@Controller
public class MainController 
{
 
	@Autowired
    private SessionBean sessionBean;
	
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {
 
	  ModelAndView model = new ModelAndView();
	  model.setViewName("bonjour");
	  
	  return model;
	}
	
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public ModelAndView myListPage() 
	{
		ModelAndView model = new ModelAndView();
		model.addObject("user", this.sessionBean.getCurrentUser());
		model.setViewName("mylist");
		
		return model;
	}
	
	@RequestMapping(value = "/mylist", method = RequestMethod.POST)
	public ModelAndView myListPageUpdate(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="details", required=false) String details,
			@RequestParam(value="shoplink", required=false) String shopLink) 
	{
		HashSet<String> shopLinks = new HashSet<String>();
		shopLinks.add(shopLink);
		
		Gift gift = new Gift(name, details, shopLinks, this.sessionBean.getCurrentUser());
		GiftDao giftDao = new GiftDao();
		giftDao.save(gift);
		
		User user = this.sessionBean.getCurrentUser();
		user.addOwnedGift(gift);
		this.sessionBean.setCurrentUser(user);
		
		ModelAndView model = new ModelAndView();
		model.addObject("user", this.sessionBean.getCurrentUser());
		model.setViewName("mylist");
		
		return model;
	}
 
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView aboutPage() {
 
	  ModelAndView model = new ModelAndView();
	  model.setViewName("about");
	  return model;
 
	}
	
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_ADMIN only!");
	  model.setViewName("admin");
	  return model;
 
	}
 
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
 
	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }
 
	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("login");
 
	  return model;
 
	}
 
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
 
	  ModelAndView model = new ModelAndView();
 
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
 
	  model.setViewName("403");
	  return model;
 
	}
 
}