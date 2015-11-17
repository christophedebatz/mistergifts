package com.debatz.gifts.controller;

import com.debatz.gifts.model.User;
import com.debatz.gifts.model.UserRole;
import com.debatz.gifts.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class AdminController extends ControllerBase
{
    @Autowired
    private UserDao userDao;

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
}
