package com.quarantine.beans;

import java.io.Serializable;

/**
 * Created by Xiangbin Zeng
 */
public class FileBean implements Serializable {
    private String listname;
    private String ownername;

    public FileBean(String ownername,String listname) {
        this.ownername = ownername;
        this.listname = listname;
    }

    public FileBean(){
        this("default","default");
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }


    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "listname='" + listname + '\'' +
                ", ownername='" + ownername + '\'' +
                '}';
    }

    public String generateJSON() {

        String outputString = "{\"listname\":\"" + this.listname + "\","
                + "\"ownername\":\"" + this.ownername + "\"";

        outputString += "}";

        return outputString;
    }
}
