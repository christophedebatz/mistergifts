package com.debatz.gifts.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.UserRole;
import com.debatz.gifts.model.dao.GiftDao;
import com.debatz.gifts.model.dao.UserDao;
import com.debatz.gifts.service.FileService;
import com.debatz.gifts.service.SlugService;

@Controller
public class MainController {

    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView defaultPage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("bonjour");

        return model;
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView adminHomePage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("adminHome");

        return model;
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.POST)
    public ModelAndView adminHomeAddUserPage(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "user_role", required = false) String role) {

        if (role == null) {
            role = "ROLE_USER";
        }

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        password = bCrypt.encode(password);

        User newUser = new User(username, password, true);
        UserRole newUserRole = new UserRole(newUser, role);
        newUser.setRoles(Arrays.asList(newUserRole));

        ModelAndView model = new ModelAndView();

        try {
            this.userDao.save(newUser);
        } catch (Exception ex) {
            model.addObject("error", "Error raised. " + ex.getMessage());
        }

        model.addObject("error", "none");
        model.setViewName("adminHome");

        return model;
    }

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

        Gift giftToBook = this.giftDao.findGift(giftId);

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

    @RequestMapping(value = "/gift/{slug}", method = RequestMethod.GET)
    public ModelAndView myListGiftPage(@PathVariable(value = "slug") final String slug) {
        ModelAndView model = new ModelAndView();

        model.addObject("selectedGift", this.giftDao.findGift(slug));
        model.setViewName("giftDetails");

        return model;
    }

    @RequestMapping(value = "/gift", method = RequestMethod.POST)
    public ModelAndView myListGiftPage(@RequestParam(value = "giftId", required = true) int giftId) throws Exception {
        Gift giftToRemove = this.giftDao.findGift(giftId);
        User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());

        if (giftToRemove != null && giftToRemove.getBooker() == null
                && currentUser.getUsername().equals(giftToRemove.getOwner().getUsername())) {
            List<Gift> userGifts = currentUser.getOwnedGifts();

            if (!userGifts.remove(giftToRemove)) {
                throw new Exception("Unable to remove gift in user gifts list.");
            }

            currentUser.setOwnedGifts(userGifts);
            this.giftDao.remove(giftToRemove);
            this.userDao.update(currentUser);
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/mylist");

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
}
