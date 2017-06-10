package com.quarantine.controllers.deletecontroller;

import com.google.appengine.api.datastore.*;
import com.quarantine.beans.ItemBean;
import com.quarantine.beans.ToDoListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Raymond Xue and Karl Jean-Brice
 */
@Controller
public class DeleteController {
    @RequestMapping(value = "deleteitem.htm", method = RequestMethod.GET)
    public void processDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        ToDoListBean listBean = (ToDoListBean) request.getSession().getAttribute("ACTIVE_LIST");

        //ASSUMIGN WE HAVE A WAY TO DETERMINE WHICH ONE IS SELECTED
        int item = -1;
        String res = "FAILURE";
        int itemIndex = -1;

        String frontID = request.getParameter("frontID");

        ItemBean removeitem = null;
        for(int i = 0; i < listBean.getItems().size(); i++){
            if(listBean.getItems().get(i).getFrontMappingID() == Integer.parseInt(frontID)){
                removeitem = listBean.getItems().get(i);
                item = removeitem.getItemID();
                itemIndex = i;
            }
        }

        if(removeitem != null){
            listBean.getItems().remove(itemIndex);
            res = "SUCCESS";
            request.getSession().setAttribute("ACTIVE_LIST",listBean);
        }

        out.println(res);

    }
}
