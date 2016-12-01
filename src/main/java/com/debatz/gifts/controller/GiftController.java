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
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GiftController extends ControllerBase
{
    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/gift/{slug}", method = RequestMethod.GET)
    public ModelAndView myListGiftPage(@PathVariable(value = "slug") final String slug)
    {
        ModelAndView model = new ModelAndView();
        Gift gift = giftDao.findGift(slug);

        Map<String, String> links = new HashMap<>();
        gift.getShopLinks().stream().forEach(link -> {
            try {
                URI uri = new URI(link);
                String domain = uri.getHost();
                links.put(link, domain.startsWith("www.") ? domain.substring(4) : domain);
            } catch (URISyntaxException ignored) { }
        });

        model.addObject("links", links);
        model.addObject("selectedGift", gift);
        model.setViewName("giftDetails");

        return model;
    }

    @RequestMapping(value = "/gift/{giftId}", method = RequestMethod.POST)
    public ModelAndView removeGift(@PathVariable(value = "giftId") final Integer giftId) throws Exception
    {
        Gift giftToRemove = this.giftDao.find(giftId);
        User currentUser = this.userDao.findByUserName(this.sessionBean.getUsername());

        if (giftToRemove != null && giftToRemove.getBooker() == null
                && currentUser.getUsername().equals(giftToRemove.getOwner().getUsername())) {
            List<Gift> userGifts = currentUser.getOwnedGifts();
            userGifts.remove(giftToRemove);
            currentUser.setOwnedGifts(userGifts);
            giftDao.remove(giftToRemove.getId());
            userDao.update(currentUser);
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/mylist");

        return model;
    }

}
