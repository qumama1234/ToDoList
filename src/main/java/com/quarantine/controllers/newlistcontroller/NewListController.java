package com.quarantine.controllers.newlistcontroller;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.quarantine.beans.ToDoListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Karl Jean-Brice
 */
@Controller
public class NewListController {
    @RequestMapping(value="newlist.htm", method = RequestMethod.GET)
    public void processNewListRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn()) {
            ToDoListBean newList = new ToDoListBean();
            newList.setOwner(userService.getCurrentUser().getEmail());
            request.getSession().setAttribute("ACTIVE_LIST", newList);
            out.println("NEWLIST:SUCCESS");
        }
        else{
            out.println("NEWLIST:FAILURE");
        }
    }
}

