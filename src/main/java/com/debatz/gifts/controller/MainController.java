package com.debatz.gifts.controller;

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
import com.debatz.gifts.service.SlugService;
 
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
	
	
	
	
	
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public ModelAndView adminHomePage() {
 
	  ModelAndView model = new ModelAndView();
	  model.setViewName("adminHome");
	  
	  return model;
	}
	
	
	
	
	
	@RequestMapping(value = "/family", method = RequestMethod.GET)
	public ModelAndView familyPage() 
	{		
		ModelAndView model = new ModelAndView();
		String currentUsername = this.sessionBean.getCurrentUser().getUsername();
		
		List<User> users = this.userDao.getUsers(currentUsername);
		model.addObject("hasGift", this.giftDao.giftsExist(currentUsername));
		model.addObject("users", users);
		
		model.setViewName("family");
		
		return model;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/family/booking", method = RequestMethod.POST)
	public ModelAndView familyBookingPage(
			@RequestParam(value="giftId", required=true) int giftId,
			@RequestParam(value="action", required=false) String action)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("booking");
		
		Gift giftToBook = this.giftDao.findGift(giftId);
		
		if (giftToBook != null) 
		{
			User currentUser = this.sessionBean.getCurrentUser();
			
			if (action != null && action.equalsIgnoreCase("completeBooking")) 
			{
				currentUser.addBookedGift(giftToBook);
				giftToBook.setBooker(currentUser);
				
				this.userDao.update(currentUser);
				this.giftDao.update(giftToBook);
				
				model.addObject("response", "booked");
			}
			
			else if (giftToBook.getBooker() != null && giftToBook.getBooker().getUsername().equals(currentUser.getUsername()))
			{
				List<Gift> currentBooking = currentUser.getBookedGifts();
				currentBooking.remove(giftToBook);
				giftToBook.setBooker(null);
				
				this.userDao.update(currentUser);
				this.giftDao.update(giftToBook);
				
				model.setViewName("redirect:/family");
			}
			
			model.addObject("user", currentUser);
			model.addObject("gift", giftToBook);
		}
		
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
	
	
	
	
	
	
	@RequestMapping(value = "/gift/{slug}", method = RequestMethod.GET)
	public ModelAndView myListGiftPage(@PathVariable(value="slug") final String slug) 
	{
		ModelAndView model = new ModelAndView();
		
		model.addObject("selectedGift", this.giftDao.findGift(slug));
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
			SlugService.getSlug(brand + " " + name),
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
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "denied", required = false) String denied,
			@RequestParam(value = "logout", required = false) String logout) {
 
	  ModelAndView model = new ModelAndView();
	  
	  if (error != null) {
		  model.addObject("error", "Invalid username and password!");
	  }
	  
	  if (denied != null) {
		  model.addObject("error", "You have not enough permission to access this area. ");
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