package model;

import exception.NegativePriceException;

import java.util.*;
import java.lang.Object.*;

//Create Item object that takes name, price, and isFood
public class Item {
    private String name;
    private double price;
    private boolean isFood;

    //REQUIRES: name has a non-zero length, price can only has two decimal places
    //MODIFIES: this
    //EFFECTS: Assign new dish name and price to an item type.
    //         boolean true if food, false if drink
    public Item(String name, double price, boolean isFood) throws NegativePriceException {
        if (price < 0) {
            throw new NegativePriceException();
        }
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.price = price;
        this.isFood = isFood;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: return true or false of the item's availability.
//    public boolean itemAvailable() {
//        return true;
//    }

    //REQUIRES: price > 0
    //MODIFIES: this
    //EFFECTS: set new price to item
    public void setPrice(double price) throws NegativePriceException {
        if (price < 0) {
            throw new NegativePriceException();
        }
        this.price = price;
    }

    //EFFECTS: return price
    public double getPrice() {
        return price;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: set new name to item
    public void setName(String name) {
        this.name = name;
    }

    //EFFECTS: return item name
    public String getName() {
        return name;
    }

    //MODIFIES: isFood
    //EFFECTS: set isFood boolean to true, means it is a food item.
    public void setIsFood() {
        isFood = true;
    }

    //EFFECTS: return true if item is food
    public boolean isFood() {
        return isFood;
    }

    //MODIFIES: isFood
    //EFFECTS: set isFood boolean to false, means it is not a food item.
    public void setIsDrink() {
        isFood = false;
    }

    //EFFECTS: return false if item is drink
    public boolean isDrink() {
        return !isFood;
    }

}
