package ui;

import exception.NegativePriceException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Restaurant {
    private Table table;
    private Menu menu;
    private Item item;
    private Scanner input;
    private static final String JSON_SAVE = "./data/restaurant.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the ordering system
    public Restaurant() {
        runRestaurant();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRestaurant() {
        boolean working = true;
        String command;
        table = new Table();
        jsonWriter = new JsonWriter(JSON_SAVE);
        jsonReader = new JsonReader(JSON_SAVE);
        run();
        while (working) {
            displayCommand();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                working = false;
                System.out.println("Thank you for today! You are clocked out now.");
            } else {
                processCommand(command);
            }
        }
    }

    //EFFECTS: initializes workflow.
    private void run() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        menu = new Menu();
        presetMenu();
    }

    //EFFECTS: displays command menu for user
    private void displayCommand() {
        System.out.println("\t--------------------------");
        System.out.println("\tMain Menu");
        System.out.println("\t--------------------------");
        System.out.println("\tn -> serve new customer");
        System.out.println("\tc -> check on going order");
        System.out.println("\tm -> modify menu");
        System.out.println("\ts -> save menu to file");
        System.out.println("\tl -> load menu from file");
        System.out.println("\tq -> close restaurant today");
        System.out.println("\t--------------------------");
    }

    //MODIFIES: this
    //EFFECTS: select function process
    private void processCommand(String command) {
        if (command.equals("n")) {
            newCustomer();
        } else if (command.equals("c")) {
            checkOrderCommand();
        } else if (command.equals("m")) {
            modifyMenu();
        } else if (command.equals("s")) {
            saveMenu();
        } else if (command.equals("l")) {
            loadMenu();
        } else {
            System.out.println("Invalid input, Please try again.");
        }
    }

    //MODIFIES: this
    //EFFECTS: Assign new order to a table if there is table available
    private void newCustomer() {
        System.out.println("New customer came in!");
        if (table.nextTable() != -1) {
            System.out.println("Table " + table.nextTable() + " is available!");
            System.out.println("The order will be assigned to table " + table.nextTable());
            displayMenu();
            takeOrder();
        } else {
            tableAvailability();
        }
    }

    //MODIFIES: this
    //EFFECTS: when tables are full, empty a table by input if needed
    private void tableAvailability() {
        while (true) {
            System.out.println("Seems all tables are full, is there any table finishing their order? (Enter y/n)");
            String answer = input.next().toLowerCase();
            if (answer.equals("y")) {
                System.out.println("Which one?");
                int finish = input.nextInt();
                table.finishOrder(finish);
                System.out.println("Table " + finish + " is now empty.");
                break;
            } else if (answer.equals("n")) {
                System.out.println("Return to main menu");
                break;
            } else {
                System.out.println("Invalid input, Please try again.");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: setup a preset menu for display
    private void presetMenu() {
        try {
            menu.addItem(new Item("Black Cod With Miso", 40.50, true));
            menu.addItem(new Item("Black Label Burger", 20.00, true));
            menu.addItem(new Item("Canlis Salad", 15.70, true));
            menu.addItem(new Item("Carnitas Burrito", 17.00, true));
            menu.addItem(new Item("Soda", 3.50, false));
            menu.addItem(new Item("Wine", 80.00, false));
        } catch (NegativePriceException e) {
            System.out.println("");
        }
    }

    //MODIFIES: nothing
    //EFFECTS: Display menu
    private void displayMenu() {
        System.out.println("\t----------------------------------------------");
        System.out.printf("\t%-22s %22s %n", "Food", "Price");
        System.out.println("\t----------------------------------------------");
        menuItems(true);
        System.out.println("\t----------------------------------------------");
        System.out.printf("\t%-22s %22s %n", "Drink", "Price");
        System.out.println("\t----------------------------------------------");
        menuItems(false);
        System.out.println("\t----------------------------------------------");
        System.out.printf("\t%-22s %n", "0. finish");
        System.out.println("\t----------------------------------------------");
    }

    //EFFECTS: Display Items by their food identity (true) or not.
    private void menuItems(boolean isFood) {
        for (int i = 1; i <= menu.length(); i++) {
            if (menu.getItem(i).isFood() == isFood) {
                System.out.format("\t%-22s %22s %n", i + ". " + menu.getItem(i).getName(),
                        String.format("%.2f", menu.getItem(i).getPrice()));
            }
        }
    }

    //MODIFIES: menu
    //EFFECTS: sort menu to put food first, drink second
    private void sortMenu() {
        List<Item> drink = new ArrayList<>();
        for (int i = 1; i <= menu.length(); i++) {
            if (menu.getItem(i).isDrink()) {
                drink.add(menu.getItem(i));
            }
        }
        for (int i = menu.length(); i > 0; i--) {
            if (menu.getItem(i).isDrink()) {
                menu.removeItem(i);
            }
        }
        for (int i = 0; i < drink.size(); i++) {
            menu.addItem(drink.get(i));
        }
    }

    // counter for food and drink
    // set different list

    //MODIFIES: order
    //EFFECTS: sort order to put food first, drink second
//    private void sortOrder(Order order) {
//
//    }

    //MODIFIES: this
    //EFFECTS: add item from menu to order and display total price for the entire order, assign the order to the table.
    private void takeOrder() {
        Order order = new Order();
        System.out.println("What would your customer like today?");
        System.out.println("(Enter 1-" + menu.length() + " or 0 to finish)");

        addOrder(order);
        displayOrder(order);
        confirmOrder(order);
    }

    //REQUIRES: this
    //MODIFIES: this
    //EFFECTS: add item to the order.
    private void addOrder(Order order) {
        String str;
        int num;
        Item temp;
        while (true) {
            str = input.next();
            try {
                num = Integer.parseInt(str);
                if (num > 0 && num <= menu.length()) {
                    temp = menu.getItem(num);
                    order.addItem(temp);
                    System.out.println(menu.getItem(num).getName() + " has been added to the order, anything else? ");
                    System.out.println("(Enter 1-" + menu.length() + " or 0 to finish)");
                } else if (num == 0) {
                    break;
                } else {
                    System.out.println("Invalid input, please enter 1-" + menu.length() + " or 0 to finish.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please enter 1-" + menu.length() + " or 0 to finish.");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: modify items from order if needed and confirm order
    private void confirmOrder(Order order) {
        String str;
        int temp;
        while (true) {
            confirmOrderMenu();
            str = input.next();
            if (str.equals("a")) {
                displayMenu();
                System.out.println("Select an item.");
                addOrder(order);
                displayOrder(order);
            } else if (str.equals("r")) {
                removeItemInOrder(order);
                displayOrder(order);
            } else if (str.equals("y")) {
                temp = table.nextTable();
                table.addOrder(temp, order);
                System.out.println("The order has been assigned to table " + temp);
                System.out.println("Return to main menu.");
                break;
            } else {
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    //MODIFIES: nothing
    //EFFECTS: display confirm order options
    private void confirmOrderMenu() {
        System.out.println();
        System.out.println("\tOrder Confirm? (y/n)");
        System.out.println("\ty -> Yes, proceed");
        System.out.println("\ta -> No, add an item");
        System.out.println("\tr -> No, remove an item");
    }


    //MODIFIES: this
    //EFFECTS: remove an item from current order.
    private void removeItemInOrder(Order order) {
        String str;
        int num;
        System.out.println("What would you like to remove? (Enter 1-" + order.length() + " or 0 to finish)");
        while (true) {
            str = input.next();
            try {
                num = Integer.parseInt(str);
                if (num > 0 && num <= order.length()) {
                    String name = order.getItem(num).getName();
                    order.removeItem(num);
                    displayOrder(order);
                    System.out.println(name
                            + " has been removed from the order, anything else? ");
                    System.out.println("(Enter 1-" + order.length() + " or 0 to finish)");
                } else if (num == 0) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again. ");
                System.out.println("(Enter 1-" + order.length() + " or 0 to finish)");
            }
        }
    }

    //REQUIRES: user input can only be int.
    //MODIFIES: this
    //EFFECTS: check order of select table and be able to finish order.
    private void checkOrderCommand() {
        String str;
        int num;
        while (true) {
            System.out.println("Which table would you like to check? ");
            System.out.println("(Enter 1-10, or 0 to quit)");
            str = input.next();
            try {
                num = Integer.parseInt(str);
                if (num > 0 && num <= table.length()) {
                    checkOrder(num);
                } else if (num == 0) {
                    System.out.println("Return to main menu");
                    break;
                } else {
                    System.out.println("Invalid input, please try again. ");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again. ");
            }
        }
    }

    //REQUIRES: user must input int
    //EFFECTS: If there is no order for the selected table, print table empty.
    //          If there is an order, print the order list.
    //          Ask if user want to end the order.
    //          If no, return to the table selection.
    //          If yes, finish order on selected table, return to the table selection.
    private void checkOrder(int num) {
        if (table.getOrder(num) == null) {
            System.out.println("This table is empty.");
        } else {
            System.out.println("Here is the order for table " + num + ": ");
            for (int i = 1; i <= table.getOrder(num).length(); i++) {
                System.out.format("\t%-22s %22s %n", i + ". " + table.getOrder(num).getItem(i).getName(),
                        String.format("%.2f", table.getOrder(num).getItem(i).getPrice()));
            }
            System.out.format("\t%-22s %22s %n", "   Total Price",
                    String.format("%.2f", table.getOrder(num).getTotalPrice()));
            finishOrderCommand(num);
        }
    }

    //REQUIRES: num > 0 && num < 10
    //MODIFIES: this
    //EFFECTS: process for finishing an order. Table will be emptied.
    private void finishOrderCommand(int num) {
        String str;
        while (true) {
            System.out.println("Is this order paid? (Enter y/n)");
            str = input.next().toLowerCase();
            if (str.equals("y")) {
                table.finishOrder(num);
                System.out.println("Table " + num + " is empty now.");
                break;
            } else if (!str.equals("n")) {
                System.out.println("Invalid input, please try again.");
            } else {
                break;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: display current order list
    private void displayOrder(Order order) {
        System.out.println("Here is the order for table " + table.nextTable() + ":");
        for (int i = 1; i <= order.length(); i++) {
            System.out.format("\t%-22s %22s %n", i + ". " + order.getItem(i).getName(),
                    String.format("%.2f", order.getItem(i).getPrice()));
        }
        System.out.format("\t%-22s %22s %n", "   Total Price",
                String.format("%.2f", order.getTotalPrice()));
    }

    //MODIFIES: this
    //EFFECTS: creating new item.
    private Item newItemProcess() {
        try {
            return item = new Item(itemName(), itemPrice(), itemIsFood());
        } catch (NegativePriceException e) {
            System.out.println("");
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: assign item a name
    private String itemName() {
        String str;
        System.out.println("What is the |NAME| for this new item?");

        str = input.next();
        return str;
    }

    //MODIFIES: this
    //EFFECTS: assign item a price
    private double itemPrice() {
        String dbl;
        double price;
        while (true) {
            System.out.println("What is the |PRICE| for this new item? (Enter xx.xx)");
            dbl = input.next();
            try {
                price = Double.parseDouble(dbl);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input, please try again. ");
            }
        }
        return price;
    }

    //MODIFIES: this
    //EFFECTS: assign item either food or drink
    private boolean itemIsFood() {
        String bool;
        while (true) {
            System.out.println("Is it |FOOD| ? (y/n)");
            bool = input.next();
            if (bool.equals("y")) {
                return true;
            } else if (bool.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input, please enter (y/n). ");
            }
        }
    }

    //EFFECTS: display modify menu and initiate modify process
    public void modifyMenu() {
        String str;

        while (true) {
            displayMenu();
            System.out.println("\t< Modify Menu");
            System.out.println("\t--------------------------");
            System.out.println("\ta -> add new item");
            System.out.println("\tr -> remove item");
//            System.out.println("\tn -> modify item name");
//            System.out.println("\tp -> modify item price");
            System.out.println("\tl -> replace item");
            System.out.println("\tq -> return to main menu");

            str = input.next();
            if (str.equals("q")) {
                break;
            } else {
                modifyMenuProcess(str);
            }
        }
    }


    //EFFECTS: modify menu selection process
    public void modifyMenuProcess(String commend) {
        if (commend.equals("a")) {
            menu.addItem(newItemProcess());
            if (item.isFood()) {
                sortMenu();
            }
        } else if (commend.equals("r")) {
            displayMenu();
            removeItemProcess();
        } else if (commend.equals("l")) {
            displayMenu();
            replaceItem();
            if (item.isFood()) {
                sortMenu();
            }
        } else {
            System.out.println("Invalid input, Please try again.");
        }
//        else if (commend.equals("n")) {
//            displayMenu();
//            modifyItemName();
//        } else if (commend.equals("p")) {
//            displayMenu();
//            modifyItemPrice();
//        }
    }

    //MODIFIES: this
    //EFFECTS: remove item from menu
    public void removeItemProcess() {
        String str;
        int num;
        System.out.println("What would you like to remove? (Enter 1-" + menu.length() + " or 0 to cancel)");
        while (true) {

            str = input.next();
            try {
                num = Integer.parseInt(str);
                if (num > 0 && num <= menu.length()) {
                    System.out.println(menu.getItem(num).getName() + " has been removed.");
                    menu.removeItem(num);
                    break;
                } else if (num == 0) {
                    break;
                } else {
                    System.out.println("Invalid input, Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, Please try again. (Enter 1-" + menu.length() + " or 0 to cancel)");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: replace item on the menu
    public void replaceItem() {
        String str;
        int num;
        System.out.println("Which item would you like to replace? (Enter 1-" + menu.length() + " or 0 to cancel)");
        while (true) {
            try {
                str = input.next();
                num = Integer.parseInt(str);
                if (num > 0 && num <= menu.length()) {
                    System.out.println("What would you want to change " + menu.getItem(num).getName() + " into?");
                    menu.modifyItem(num, newItemProcess());
                    break;
                } else if (num == 0) {
                    break;
                } else {
                    System.out.println("Invalid input, Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, Please try again. (Enter 1-" + menu.length() + " or 0 to cancel)");
            }
        }
    }

    //TODO: initiate new item process instead of only change the name for the item. Cause problem.
    //MODIFIES: this
    //EFFECTS: modify item's name on the menu
    public void modifyItemName() {
        String str;
        int num;
        System.out.println("Which item would you like to modify name? (Enter 1-" + menu.length() +  "or 0 to cancel)");
        while (true) {
            try {
                str = input.next();
                num = Integer.parseInt(str);
                if (num > 0 && num <= menu.length()) {
                    System.out.println("What name would you like to change for " + menu.getItem(num).getName() + "?");
                    str = input.next();
                    menu.getItem(num).setName(str);
                    break;
                } else if (num == 0) {
                    break;
                } else {
                    System.out.println("Invalid input, Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, Please try again. (Enter 1-" + menu.length() + " or 0 to cancel)");
            }
        }
    }

    //TODO: initiate new item process instead of only change the price for the item. Cause problem.
    //MODIFIES: this
    //EFFECTS: modify item's price on the menu
    public void modifyItemPrice() {
        String str;
        double price;
        int num;
        System.out.println("Modify price of which item? (Enter 1-" + menu.length() +  "or 0 to cancel)");
        while (true) {
            try {
                str = input.next();
                num = Integer.parseInt(str);
                if (num > 0 && num <= menu.length()) {
                    System.out.println("What price would you like to change for " + menu.getItem(num).getName() + "?");
                    str = input.next();
                    price = Double.parseDouble(str);
                    menu.getItem(num).setPrice(price);
                    break;
                } else if (num == 0) {
                    break;
                } else {
                    System.out.println("Invalid input, Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, Please try again. (Enter 1-" + menu.length() + " or 0 to cancel)");
            }
        }
    }

    //EFFECTS: save current menu to file
    private void saveMenu() {
        try {
            jsonWriter.open();
            jsonWriter.write(menu);
            jsonWriter.close();
            System.out.println("All orders are being save to " + JSON_SAVE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_SAVE);
        }
    }

    //EFFECTS: load previous menu from file
    private void loadMenu() {
        try {
            menu = jsonReader.read();
            System.out.println("Loaded all orders from " + JSON_SAVE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_SAVE);
        } catch (NegativePriceException e) {
            System.out.println("Negative Price found from file: " + JSON_SAVE);
        }
    }

}
