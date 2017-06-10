package com.quarantine.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Karl Jean-Brice and Raymond Xue
 */
public class ToDoListBean implements Serializable {

    private String listname;
    private String email;
    private String owner;
    private String id;
    private ArrayList<ItemBean> items;
    private boolean isPrivate;


    public ToDoListBean() {
        this("default","default" ,"default", "default", false);
    }


    public ToDoListBean(String email, String listname , String id, String owner, boolean isPrivate) {
        this.listname = listname;
        this.email = email;

        this.id = id;
        this.isPrivate = isPrivate;
        this.owner = owner;
        this.items = new ArrayList<>();
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public void setItems(ArrayList<ItemBean> items) {
        this.items = items;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public ArrayList<ItemBean> getItems() {
        return items;
    }

    public void addItem(ItemBean item) {
        this.items.add(item);
    }


    public String getId() {
        return id;
    }

    public String generateId(){
        Random rand = new Random();

        int  n = rand.nextInt(10000) + 1;

        return Integer.toString(n);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ToDoListBean{" +
                "listname='" + listname + '\'' +
                ", email='" + email + '\'' +
                ", owner='" + owner + '\'' +
                ", id='" + id + '\'' +
                ", items=" + items +
                ", private=" + isPrivate +
                '}';
    }

    public String generateJSON() {
        String outputString = "{\"email\":\"" + email + "\","
                + "\"owner\":\"" + owner + "\","
                + "\"listname\":\"" + listname + "\","
                + "\"id\":\"" + id + "\","
                + "\"private\":\"" + isPrivate + "\","
                + "\"Items\":[";

        if (items.isEmpty()) {
            outputString = "{\"email\":\"" + email + "\","
                    + "\"owner\":\"" + owner + "\","
                    + "\"listname\":\"" + listname + "\","
                    + "\"id\":\"" + id + "\","
                    + "\"private\":\"" + isPrivate + "\"}";
            return outputString;
        }

        for (int i = 0; i < items.size(); i++) {
            if (i == items.size() - 1) {
                outputString += items.get(i).generateJSON() + "]";
            } else {
                outputString += items.get(i).generateJSON() + ",";
            }
        }

        outputString += "}";

        return outputString;
    }
}
