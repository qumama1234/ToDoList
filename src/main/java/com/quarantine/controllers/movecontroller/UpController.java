package com.quarantine.controllers.movecontroller;

import com.quarantine.beans.ItemBean;
import com.quarantine.beans.ToDoListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Xiangbin Zeng
 */
@Controller
public class UpController {
    @RequestMapping(value = "moveup.htm", method = RequestMethod.GET)
    public void sortByStart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // Get the list
        ToDoListBean list;
        list = (ToDoListBean) request.getSession().getAttribute("ACTIVE_LIST");
        String frontMappingID = request.getParameter("frontID");

        if(frontMappingID.trim().length() == 0){
            out.println("FAILURE");
            return;
        }
        // Get the array of items
        ArrayList<ItemBean> items;
        items = list.getItems();

        int index = -1;
        for(int i = 0; i<items.size();i++){
            if(items.get(i).getFrontMappingID() == Integer.parseInt(frontMappingID)){
                index = i;
            }
        }

        if(index == -1){
            out.println("FAILURE");
            return;
        }

        // Check if the list is empty
        if(!items.isEmpty()) {
            // Check if the item is already at the top.
            if(index > 0) {
                // Swap the current item and the one above.
                Collections.swap(items, index,index - 1);
                list.setItems(items);
            }
        }

        request.getSession().setAttribute("ACTIVE_LIST",list);
        out.println("SUCCESS");
    }
}
