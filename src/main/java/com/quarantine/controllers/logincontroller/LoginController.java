package com.quarantine.controllers.logincontroller;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Karl Jean-Brice
 */
@Controller
public class LoginController {
    @RequestMapping(value="login.htm", method = RequestMethod.GET)
    public String LoginService (){
        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn()){
            System.out.println("");
            User user = userService.getCurrentUser();
            System.out.println(user.getEmail());
            System.out.println(user.getUserId());
            System.out.println("Already logged in: User Logged out");
            return "redirect:" + userService.createLogoutURL("/");

        }
        else{
            return "redirect:" + userService.createLoginURL("/");
        }
    }
}
