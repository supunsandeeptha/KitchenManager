package com.changed.supun.kitchenmanager;

public class KitchenItem {


    //variables of the kitchen item
    private String itemName;
    private double weight;
    private int availability;
    private int id;
    private String description;
    private double price;


    //constructor
    public KitchenItem(String itemName, double weight, int availability, int id, String description, double price) {

        this.itemName = itemName;
        this.weight = weight;
        this.availability = availability;
        this.id = id;
        this.description = description;
        this.price = price;
    }

    //empty constructor
    public KitchenItem() {

    }

    //getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
