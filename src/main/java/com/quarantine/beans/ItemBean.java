package com.quarantine.beans;

import java.io.Serializable;

/**
 * Created by Karl Jean-Brice
 */
public class ItemBean implements Serializable{
    private String category;
    private String description;
    private String startDate;
    private String endDate;
    private String completed;
    private int frontMappingID;
    private int itemID;

    private static int itemCount = 0;




    public ItemBean(String category, String description, String startDate, String endDate, String completed) {
        this.category = category;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = completed;
        this.frontMappingID = itemCount++;
    }


    public ItemBean(){
        this("default", "default","default","default","default");
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public int getItemID(){
        return this.itemID;
    }

    public int getFrontMappingID(){
        return this.frontMappingID;
    }

    public void setItemID(int id){
        this.itemID = id;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }

    public String generateJSON() {

        String outputString = "{\"category\":\"" + category + "\","
                + "\"description\":\"" + description + "\","
                + "\"startDate\":\"" + startDate + "\",";

        outputString += "\"endDate\":\"" + endDate + "\","
                + "\"frontID\":\"" + this.frontMappingID + "\","
                +"\"completed\":\"" + completed + "\"";

        outputString += "}";

        return outputString;
    }

    /*
      private String category;
    private String description;
    private String startDate;
    private String endDate;
    private String completed;
     */
}
