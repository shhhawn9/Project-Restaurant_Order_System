package model;

import java.util.ArrayList;
import java.util.List;

//a list for order and calculate final price

public class Order {
    private List<Item> order;

    //assign new list to the menu
    public Order() {
        order = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Add a new item to the end of the list.
    public void addItem(Item item) {
        order.add(item);
    }

    //REQUIRES: num must be within the range of the list or the index right after the last index on the list.
    //          num must be > 0. The num is +1 of the index on the arraylist for user to use.
    //MODIFIES: this
    //EFFECTS: Insert item to the designated index on the list, everything after that index move down to the list.
    public void insertItem(int num, Item item) {
        order.add(num - 1, item);
    }

    //REQUIRES: num must be within the range of the list and must be > 0
    //          The num is +1 of the index on the arraylist for user to use.
    //MODIFIES: this
    //EFFECTS: Remove an item from designated index on the list. Everything after that index moves up to the list.
    public void removeItem(int num) {
        order.remove(num - 1);
    }


    //REQUIRES: num must be within the range of the list and must be > 0
    //          The num is +1 of the index on the arraylist for user to use.
    //EFFECTS: Get the item at the index.
    public Item getItem(int num) {
        return order.get(num - 1);
    }

    //EFFECTS: Return the size of the menu.
    public int length() {
        return order.size();
    }

    //EFFECTS: Return the total price for this order.
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Item item : order) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    //    //REQUIRES: num must be within the range of the list.
//    //          The num is +1 of the index on the arraylist for user to use.
//    //EFFECTS: Modify an item from the designated index on the list.
//    public void modifyItem(int num, Item item) {
//        order.set(num - 1, item);
//    }

//    //EFFECTS: Get a list of all items on the menu.
//    public List<Item> getMenu() {
//        return menu;
//    }

}
