package com.debatz.gifts.controller;

import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.dao.GiftDao;
import com.debatz.gifts.model.dao.UserDao;
import com.debatz.gifts.service.FileService;
import com.debatz.gifts.service.RemoteUploadService;
import com.debatz.gifts.service.SlugService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ListController extends ControllerBase
{
    private static final Logger logger = LogManager.getLogger(RemoteUploadService.class);

    private static final String UPLOADS_DIRECTORY = "/home/pi/uploads/mistergifts/";

    //private static final String UPLOADS_DIRECTORY = "/Users/christophedebatz/Documents/workspace/MisterGifts/uploads/";

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
            }
            else if (giftToBook.getBooker() != null && giftToBook.getBooker().getUsername().equals(currentUser.getUsername())) {
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
    public ModelAndView myListPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        final User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());
        List<User> users = this.userDao.getUsers().stream().filter(
                u -> !u.getUsername().equals(currentUser.getUsername())
        ).collect(Collectors.toList());

        model.addObject("user", currentUser);
        model.addObject("users", users);
        model.addObject("error", request.getParameter("error"));
        model.setViewName("myList");

        return model;
    }

    @RequestMapping(value = "/mylist", method = RequestMethod.POST)
    public ModelAndView myListPageUpdate(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "brand", required = true) String brand,
            @RequestParam(value = "details", required = false) String details,
            @RequestParam(value = "picture", required = false) String picture,
            @RequestParam(value = "shoplink", required = false) List<String> shoplinks,
            @RequestParam(value = "onlyViewer", required = false) String onlyViewer) {

        boolean hasError = true;
        String pictureLocalPath = null;

        if (picture == null || picture.equals("")) {
            hasError = false;
        }

        else if (FileService.checkIfExists(picture))
        {
            String[] pictureExtArray = picture.split("\\.");
            String pictureExt = pictureExtArray[pictureExtArray.length - 1];

            pictureLocalPath = UUID.randomUUID().toString() + "." + pictureExt;
            String uploadLocalPath = UPLOADS_DIRECTORY + pictureLocalPath;

            try {
                RemoteUploadService
                        .save(picture, uploadLocalPath);

                hasError = false;

            } catch (Exception ex) {
                logger.debug(ex.getMessage());
            }
        }

        if (!hasError)
        {
            try {
                User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());
                User onlyViewerUser = this.userDao.findByUserName(onlyViewer);

                Gift owned = new Gift();
                owned.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
                owned.setSlug(SlugService.getSlug(this.giftDao.getNextSequence() + " " + brand + " " + name));
                owned.setBrand(brand.toUpperCase());
                owned.setDetails(details.length() == 0 ? null : details.substring(0, 1).toUpperCase() + details.substring(1));
                owned.setPicture(pictureLocalPath);
                owned.setShopLinks(shoplinks);
                owned.setOwner(currentUser);
                owned.setOnlyViewer(onlyViewerUser);

                currentUser.addOwnedGift(owned);
                this.userDao.update(currentUser);
                hasError = false;

            } catch (Exception ex) {
                logger.debug(ex.getMessage(), ex);
            }
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/mylist" + (hasError ? "?error" : ""));

        return model;
    }
}
