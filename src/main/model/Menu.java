package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


//Menu object that takes a list of items
public class Menu implements Writable {

    private List<Item> menu;

    //assign new list to the menu
    public Menu() {
        menu = new ArrayList<>();
    }

    //EFFECTS: Add a new item to the end of the list.
    public void addItem(Item item) {
        menu.add(item);
    }

    //REQUIRES: num must be within the range of the list or the index right after the last index on the list.
    //          num must be > 0. The num is +1 of the index on the arraylist for user to use.
    //MODIFIES: this
    //EFFECTS: Insert item to the designated index on the list, everything after that index move down to the list.
    public void insertItem(int index, Item item) {
        menu.add(index - 1, item);
    }

    //REQUIRES: num must be within the range of the list and must be > 0
    //          The num is +1 of the index on the arraylist for user to use.
    //MODIFIES: this
    //EFFECTS: Remove an item from designated index on the list. Everything after that index moves up to the list.
    public void removeItem(int num) {
        menu.remove(num - 1);
    }

    //REQUIRES: num must be within the range of the list.
    //          The num is +1 of the index on the arraylist for user to use.
    //MODIFIES: this
    //EFFECTS: Modify an item from the designated index on the list.
    public void modifyItem(int num, Item item) {
        menu.set(num - 1, item);
    }

    //REQUIRES: num must be > 0
    //EFFECTS: Get the item at the index.
    public Item getItem(int num) {
        return menu.get(num - 1);
    }

    //EFFECTS: Return the size of the menu.
    public int length() {
        return menu.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("menu", menu);
        return json;
    }
}
