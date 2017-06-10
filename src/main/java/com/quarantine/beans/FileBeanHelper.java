package com.quarantine.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Xiangbin Zeng
 */
public class FileBeanHelper implements Serializable {
    private String current_user;
    private ArrayList<FileBean> fileList;

    public FileBeanHelper(String current_user){
        this.current_user = current_user;
        this.fileList = new ArrayList<>();
    }

    public FileBeanHelper(){
        this("default");
    }


    public String getCurrent_user() {
        return current_user;
    }

    public void addFile(FileBean newFile){
        fileList.add(newFile);
    }
    public ArrayList<FileBean> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<FileBean> fileList) {
        this.fileList = fileList;
    }

    public void setCurrent_user(String current_user) {
        this.current_user = current_user;
    }

    @Override
    public String toString() {
        return "FileBeanHelper{" +
                "current_user='" + current_user + '\'' +
                '}';
    }

    public String generateJSON(){
        String outputString = "{\"currentUser\":\"" + this.current_user + "\","
                + "\"Files\":[";

        if (fileList.isEmpty()) {
            outputString = "{\"currentUser\":\"" + current_user + "\"}";
            return outputString;
        }

        for (int i = 0; i < fileList.size(); i++) {
            if (i == fileList.size() - 1) {
                outputString += fileList.get(i).generateJSON() + "]";
            } else {
                outputString += fileList.get(i).generateJSON() + ",";
            }
        }

        outputString += "}";

        return outputString;
    }
}
