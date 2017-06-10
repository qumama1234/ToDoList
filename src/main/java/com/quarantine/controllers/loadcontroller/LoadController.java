package com.quarantine.controllers.loadcontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.quarantine.beans.ItemBean;
import com.quarantine.beans.ToDoListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Xiangbin Zeng and Karl Jean-Brice
 */


@Controller
public class LoadController {

    @RequestMapping(value = "loadlist.htm", method = RequestMethod.GET)
    public void processSaveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        UserService userService = UserServiceFactory.getUserService();
        //ToDoListBean todolist = (ToDoListBean) request.getSession().getAttribute("ACTIVE_LIST");

        ToDoListBean list = new ToDoListBean();
        String listname = request.getParameter("listName");
        String owner = request.getParameter("owner");
        if (listname == null || owner == null || owner.trim().length() == 0 || listname.trim().length() == 0) {
            out.println("FAILURE");
        } else {
            boolean data_retrieved = false;
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

            Query.Filter email_filter = new Query.FilterPredicate("Owner", Query.FilterOperator.EQUAL, owner.trim());
            Query.Filter listname_filter = new Query.FilterPredicate("Listname", Query.FilterOperator.EQUAL, listname.trim());
            Query.CompositeFilter email_listname_filter = Query.CompositeFilterOperator.and(email_filter, listname_filter);

            Query q = new Query("Item").setFilter(email_listname_filter);
            PreparedQuery pq = datastore.prepare(q);
            for (Entity result : pq.asIterable()) {
                list.setOwner((String) result.getProperty("Owner"));
                list.setListname((String) result.getProperty("Listname"));
                if (userService.isUserLoggedIn()) {
                    list.setEmail((String) result.getProperty("Email"));
                } else {
                    list.setEmail("anon");
                }
                list.setPrivate((Boolean) result.getProperty("isPrivate"));

                String test = (String)result.getProperty("Empty");
                if(((String)result.getProperty("Empty")).trim().equalsIgnoreCase("false")) {
                    ItemBean newItem = new ItemBean();
                    newItem.setCategory((String) result.getProperty("Category"));
                    newItem.setDescription((String) result.getProperty("Description"));
                    newItem.setStartDate((String) result.getProperty("StartDate"));
                    newItem.setEndDate((String) result.getProperty("EndDate"));
                    newItem.setCompleted((String) result.getProperty("Completed"));
                    newItem.setItemID(((Number) result.getProperty("PrimaryID")).intValue());

                    list.addItem(newItem);
                }
                if (data_retrieved == false) {
                    data_retrieved = true;
                }
            }

            if (data_retrieved) {
                request.getSession().setAttribute("ACTIVE_LIST", list);
                out.println("SUCCESS");
            } else {
                out.println("FAILURE");
            }
        }
    }
}
