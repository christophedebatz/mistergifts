package com.debatz.gifts.controller;

import com.debatz.gifts.model.dao.GiftDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends ControllerBase
{
    @Autowired
    private GiftDao giftDao;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView defaultPage()
    {
        int[] stats = this.giftDao.getStatistics();

        ModelAndView model = new ModelAndView();

        if (stats.length == 2) {
            model.addObject("globalGiftsCount", stats[0]);
            model.addObject("directGiftsCount", stats[1]);
        }

        model.setViewName("bonjour");

        return model;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView aboutPage()
    {
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
}
