package com.changed.supun.kitchenmanager.Recipies;

public class JsonObjectModel {

    //variables to store the itemTitle and URL
    private String itemTitle;
    private String itemURL;


    //constructor
    public JsonObjectModel(String itemTitle,String itemURL ){
        this.itemTitle = itemTitle;
        this.itemURL = itemURL;
    }

    //getters and setters


    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }
}
