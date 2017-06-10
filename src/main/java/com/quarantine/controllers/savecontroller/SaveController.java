package com.quarantine.controllers.savecontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
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
 * Created by Raymond Xue and Karl Jean-Brice and Xiangbin Zeng
 */


@Controller
public class SaveController {
    @RequestMapping(value = "savelist.htm", method = RequestMethod.GET)
    public void processSaveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        UserService userService = UserServiceFactory.getUserService();
        ToDoListBean todolist = (ToDoListBean) request.getSession().getAttribute("ACTIVE_LIST");
        System.out.println("Active List output: " + todolist.toString());

        String listname = request.getParameter("listName");
        String privacy = request.getParameter("privacy");
        if (listname == null || listname.trim().length() == 0 || privacy == null || privacy.trim().length() == 0) {
                out.println("FAILURE");
        } else {
            //Sets the name of the list to be saved.
            todolist.setListname(listname.trim());
            if(privacy.trim().equalsIgnoreCase("true")){
                todolist.setPrivate(true);
            }else{
                todolist.setPrivate(false);
            }

            String id = todolist.generateId();
            todolist.setId(id);
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


            //Delete any previous list with the same name made by the current email address
            Filter email_filter = new FilterPredicate("Owner",FilterOperator.EQUAL,userService.getCurrentUser().getEmail());
            Filter listname_filter = new FilterPredicate("Listname",FilterOperator.EQUAL,listname.trim());
            CompositeFilter email_listname_filter = CompositeFilterOperator.and(email_filter,listname_filter);

            Query q = new Query("Item").setFilter(email_listname_filter);
            PreparedQuery pq = datastore.prepare(q);

            for (Entity result : pq.asIterable()) {
                String category = (String) result.getProperty("Category");
                String description = (String) result.getProperty("Description");
                String list_name = (String)result.getProperty("Listname");
                String owner_email = (String)result.getProperty("Email");

                System.out.println(category + " " + description + ", " + list_name + ", " + owner_email);
                datastore.delete(result.getKey());
            }

            if(todolist.getItems().size() == 0) {
                Entity item = new Entity("Item");
                item.setProperty("Empty","true");
                item.setProperty("isPrivate", todolist.isPrivate());
                item.setProperty("Listname", todolist.getListname());
                item.setProperty("Owner", todolist.getOwner());
                item.setProperty("Email", userService.getCurrentUser().getEmail());
                datastore.put(item);
            }else {
                //Save bean object to datastore
                for (int i = 0; i < todolist.getItems().size(); i++) {
                    Key itemKey;
                    itemKey = KeyFactory.createKey("PID", todolist.getItems().get(i).getItemID());
                    Entity item = new Entity("Item", itemKey);
                    item.setProperty("Category", todolist.getItems().get(i).getCategory());
                    item.setProperty("Description", todolist.getItems().get(i).getDescription());
                    item.setProperty("StartDate", todolist.getItems().get(i).getStartDate());
                    item.setProperty("EndDate", todolist.getItems().get(i).getEndDate());
                    item.setProperty("Completed", todolist.getItems().get(i).getCompleted());
                    item.setProperty("isPrivate", todolist.isPrivate());
                    item.setProperty("Listname", todolist.getListname());
                    item.setProperty("Owner", todolist.getOwner());
                    item.setProperty("FrontMappingID", todolist.getItems().get(i).getFrontMappingID());
                    item.setProperty("SpecialID", id);
                    item.setProperty("Email", userService.getCurrentUser().getEmail());
                    item.setProperty("PrimaryID", todolist.getItems().get(i).getItemID());
                    item.setProperty("Empty", "false");
                    datastore.put(item);
                }
            }
            out.println("SUCCESS");
        }


    }
}
