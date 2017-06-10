package com.quarantine.services;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.quarantine.beans.FileBean;
import com.quarantine.beans.FileBeanHelper;
import com.quarantine.beans.ItemBean;
import com.quarantine.beans.ToDoListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Karl Jean-Brice
 */

@Controller
public class ServiceManager {
    @RequestMapping(value = "checkuserstatus.htm", method = RequestMethod.GET)
    public void checkUserStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn()) {
            User user = userService.getCurrentUser();
            out.println("" + user.getEmail());

        } else {
            out.println("NOT_LOGGED_IN");
        }
    }

    @RequestMapping(value = "requestitems.htm", method = RequestMethod.GET)
    public void processItemRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        ToDoListBean list = (ToDoListBean) request.getSession().getAttribute("ACTIVE_LIST");
        System.out.println(list.generateJSON());
        out.println(list.generateJSON());


    }

    @RequestMapping(value = "requestfiles.htm", method = RequestMethod.GET)
    public void processFileRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query q = new Query("Item");
        PreparedQuery pq = datastore.prepare(q);
        FileBeanHelper fileHelper = new FileBeanHelper();

        for (Entity result : pq.asIterable()) {
            String owner = (String) result.getProperty("Owner");
            String listname = (String) result.getProperty("Listname");
            boolean privacy = (Boolean) result.getProperty("isPrivate");


            System.out.println(owner + " " + listname);
            boolean insert_flag = true;
            for (int i = 0; i < fileHelper.getFileList().size(); i++) {
                if (fileHelper.getFileList().get(i).getListname().equalsIgnoreCase(listname.trim())) {
                    insert_flag = false;
                }
            }

            if (insert_flag) {
                if (privacy == true) {
                    UserService userService = UserServiceFactory.getUserService();
                    if(userService.isUserLoggedIn()){
                     if(userService.getCurrentUser().getEmail().trim().equalsIgnoreCase(owner.trim())){
                         fileHelper.addFile(new FileBean(owner, listname));
                     }
                    }
                } else {
                    fileHelper.addFile(new FileBean(owner, listname));
                }
            }

        }

       /* ToDoListBean list = (TooListBean)request.getSession().getAttribute("ACTIVE_LIST");
        System.out.println(list.generateJSON());
        out.println(list.generateJSON());*/
        System.out.println(fileHelper.generateJSON());
        out.println(fileHelper.generateJSON());
    }

}
