package com.debatz.gifts.controller;

import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.dao.GiftDao;
import com.debatz.gifts.model.dao.UserDao;
import com.debatz.gifts.service.FileService;
import com.debatz.gifts.service.SlugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ListController
{
    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/grouplist", method = RequestMethod.GET)
    public ModelAndView groupPage() {
        ModelAndView model = new ModelAndView();
        String currentUsername = this.sessionBean.getUsername();

        List<User> users = this.userDao.getUsers(currentUsername);
        model.addObject("hasGift", this.giftDao.giftsExist(currentUsername));
        model.addObject("users", users);

        model.setViewName("group");

        return model;
    }

    @RequestMapping(value = "/grouplist/booking", method = RequestMethod.POST)
    public ModelAndView groupBookingPage(
            @RequestParam(value = "giftId", required = true) int giftId,
            @RequestParam(value = "action", required = false) String action) {
        ModelAndView model = new ModelAndView();
        model.setViewName("booking");

        Gift giftToBook = this.giftDao.find(giftId);

        if (giftToBook != null) {
            User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());

            if (action != null && action.equalsIgnoreCase("completeBooking")) {
                currentUser.addBookedGift(giftToBook);
                giftToBook.setBooker(currentUser);

                this.userDao.update(currentUser);
                this.giftDao.update(giftToBook);

                model.addObject("response", "booked");
            } else if (giftToBook.getBooker() != null && giftToBook.getBooker().getUsername().equals(currentUser.getUsername())) {
                List<Gift> currentBooking = currentUser.getBookedGifts();
                currentBooking.remove(giftToBook);
                giftToBook.setBooker(null);

                this.userDao.update(currentUser);
                this.giftDao.update(giftToBook);

                currentUser.setBookedGifts(currentBooking);

                model.setViewName("redirect:/grouplist");
            }

            model.addObject("user", currentUser);
            model.addObject("gift", giftToBook);
        }

        return model;
    }

    @RequestMapping(value = "/mylist", method = RequestMethod.GET)
    public ModelAndView myListPage() {
        ModelAndView model = new ModelAndView();
        User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());
        model.addObject("user", currentUser);
        model.setViewName("myList");

        return model;
    }

    @RequestMapping(value = "/mylist", method = RequestMethod.POST)
    public ModelAndView myListPageUpdate(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "brand", required = true) String brand,
            @RequestParam(value = "details", required = false) String details,
            @RequestParam(value = "picture", required = false) String picture,
            @RequestParam(value = "shoplink", required = false) List<String> shoplinks) {

        if (!FileService.checkIfExists(picture)) {
            picture = null;
        }

        User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());

        currentUser.addOwnedGift(
                new Gift(
                        name.substring(0, 1).toUpperCase() + name.substring(1),
                        SlugService.getSlug(this.giftDao.getNextSequence() + " " + brand + " " + name),
                        brand.toUpperCase(),
                        details.length() == 0 ? null : details.substring(0, 1).toUpperCase() + details.substring(1),
                        picture,
                        shoplinks,
                        currentUser
                )
        );

        this.userDao.update(currentUser);

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/mylist");

        return model;
    }
}
