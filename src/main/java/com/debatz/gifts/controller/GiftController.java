package com.debatz.gifts.controller;

import com.debatz.gifts.bean.SessionBean;
import com.debatz.gifts.model.Gift;
import com.debatz.gifts.model.User;
import com.debatz.gifts.model.dao.GiftDao;
import com.debatz.gifts.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GiftController
{
    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

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
}
