package com.debatz.gifts.controller;

import com.debatz.gifts.Pair;
import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.dao.GiftDao;
import com.debatz.gifts.model.dao.UserDao;
import com.debatz.gifts.service.FileService;
import com.debatz.gifts.service.RemoteUploadService;
import com.debatz.gifts.service.SlugService;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ListController extends ControllerBase
{
    private static final Logger logger = LogManager.getLogger(ListController.class);

    //public static final String UPLOADS_DIRECTORY = "/home/mgadmin/uploads_old/";

    public static final String UPLOADS_DIRECTORY = "/Users/christophedebatz/Documents/workspace/MisterGifts/uploads/";

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
    public ModelAndView myListPageInsert(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "details", required = false) String details,
            @RequestParam(value = "picture", required = false) String picture,
            @RequestParam(value = "shoplink", required = false) List<String> shoplinks,
            @RequestParam(value = "viewers", required = false) List<String> viewers) {

        Gift owned = new Gift();
        String errors = null;

        if (picture != null) {
            Pair<String, String> uploadResult = uploadPicture(picture);
            owned.setPicture(uploadResult.getFirst());
            errors = uploadResult.getSecond();
        }

        if (errors == null) {
            try {
                User currentUser = userDao.findByUserName(sessionBean.getUsername());
                List<User> viewerUsers = userDao.getUsersByNames(viewers);

                int id = giftDao.getNextSequence();
                owned.setId(id);
                owned.setName(name.substring(0, 1).toUpperCase() + name.substring(1));
                owned.setSlug(SlugService.getSlug(id, name, brand));
                owned.setBrand(brand.toUpperCase());
                owned.setDetails(details.length() == 0 ? null : details.substring(0, 1).toUpperCase() + details.substring(1));
                Optional.ofNullable(shoplinks).ifPresent(owned::setShopLinks);
                owned.setOwner(currentUser);
                owned.setViewers(viewerUsers);
                owned.setCreationDate(new Date());
                owned.setModificationDate(new Date());
                currentUser.addOwnedGift(owned);

                userDao.update(currentUser);

            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                String e = ex.getMessage().replaceAll("\\n", "");
                e = e.replaceAll("\\r", "");
                errors = e;
            }
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/mylist?" + errors);

        return model;
    }

    /**
     *
     * @param picture
     * @return
     */
    private Pair<String, String> uploadPicture(String picture) {
        String pictureLocalPath = null;

        if (picture == null || picture.equals("")) {
            return new Pair<>(null, null);
        }

        else if (FileService.checkIfExists(picture)) {
            String pictureExt = FilenameUtils.getExtension(picture);
            pictureExt = pictureExt.startsWith("jpeg") ? "jpeg" : pictureExt.substring(0, 3);

            pictureLocalPath = UUID.randomUUID().toString().replace("-", "") + "." + pictureExt;
            String uploadLocalPath = UPLOADS_DIRECTORY + pictureLocalPath;

            try {
                RemoteUploadService.save(picture, uploadLocalPath);

            } catch (Exception ex) {
                logger.error(ex.getMessage());
                return new Pair<>(null, "upload");
            }
        }

        return new Pair<>(pictureLocalPath, null);
    }
}
