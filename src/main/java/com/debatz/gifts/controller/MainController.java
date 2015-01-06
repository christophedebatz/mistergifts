package com.debatz.gifts.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.dao.GiftDao;
import com.debatz.gifts.model.dao.UserDao;
 
@Controller
public class MainController 
{
 
	@Autowired
    private SessionBean sessionBean;
	
	@Autowired
	private GiftDao giftDao;
	
	@Autowired
	private UserDao userDao;
	
	
	
	
	
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {
 
	  ModelAndView model = new ModelAndView();
	  model.setViewName("bonjour");
	  
	  return model;
	}
	
	
	
	
	
	@RequestMapping(value = "/family", method = RequestMethod.GET)
	public ModelAndView familyPage() 
	{		
		ModelAndView model = new ModelAndView();
		
		List<User> users = this.userDao.getUsers();
		model.addObject("users", users);
		model.setViewName("family");
		
		return model;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/family/booking", method = RequestMethod.POST)
	public ModelAndView familyBookingPage(@RequestParam(value="giftId", required=true) int giftId)
	{
		ModelAndView model = new ModelAndView();
		Gift giftToBook = this.giftDao.findGift(giftId);
		
		if (giftToBook != null) 
		{
			this.sessionBean.getCurrentUser().addBookedGift(giftToBook);
			User currentUser = this.sessionBean.getCurrentUser();
			this.userDao.update(currentUser);
			
			model.addObject("user", currentUser);
			model.addObject("gift", giftToBook);
		}
		
		model.setViewName("booking");
		
		return model;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public ModelAndView myListPage() 
	{		
		ModelAndView model = new ModelAndView();
		model.addObject("user", this.sessionBean.getCurrentUser());
		model.setViewName("myList");
		
		return model;
	}
	
	
	
	
	
	@RequestMapping(value = "/mylist/{giftId}", method = RequestMethod.GET)
	public ModelAndView myListGiftPage(@PathVariable(value="giftId") final int giftId) 
	{
		ModelAndView model = new ModelAndView();
		List<Gift> gifts = this.sessionBean.getCurrentUser().getOwnedGifts();
		Gift selectedGift = null;
		
		for (Gift gift : gifts) {
			if (gift.getId() == giftId) {
				selectedGift = gift;
				break;
			}
		}
		
		model.addObject("selectedGift", selectedGift);
		model.setViewName("giftDetails");
		
		return model;
	}
	
	
	
	
	
	@RequestMapping(value = "/mylist", method = RequestMethod.POST)
	public ModelAndView myListPageUpdate(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="brand", required=true) String brand,
			@RequestParam(value="details", required=false) String details,
			@RequestParam(value="shoplink", required=false) List<String> shoplinks) 
	{
		Gift gift = new Gift(
			name.substring(0, 1).toUpperCase() + name.substring(1), 
			brand.toUpperCase(), 
			details.substring(0, 1).toUpperCase() + details.substring(1),
			shoplinks, 
			this.sessionBean.getCurrentUser()
		);
		
		this.giftDao.save(gift); 
		
		User user = this.sessionBean.getCurrentUser();
		user.addOwnedGift(gift);
		this.userDao.update(user);
		
		this.sessionBean.setCurrentUser(user);
		
		ModelAndView model = new ModelAndView();
		model.addObject("user", user);
		model.setViewName("myList");
		
		return model;
	}
	
	
	
 
	
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView aboutPage() {
 
	  ModelAndView model = new ModelAndView();
	  model.setViewName("about");
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
 
	
	


	
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
 
	  ModelAndView model = new ModelAndView();
 
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
 
	  model.setViewName("login");
	  return model;
 
	}
 
}