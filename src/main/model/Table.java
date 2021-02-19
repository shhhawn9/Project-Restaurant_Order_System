package model;

// 10 table maximum for the restaurant. Order will be assigned to a specific table.
public class Table {
    private static final int TABLE = 10;
    Order[] table;
    Order order;

    //EFFECTS: assign a new table list in the restaurant. 10 tables only
    public Table() {
        order = new Order();
        table = new Order[TABLE];
    }

    //EFFECTS: return the number of the next available table. Return 0 if there is none.
    public int nextTable() {
        int temp = -1;
        int i = 0;
        while (i < TABLE) {
            if (table[i] == null) {
                temp = i + 1;
                break;
            }
            i++;
        }
        return temp;
    }

    //REQUIRES: tableNum is greater than 0 less then 11.
    //MODIFIES: this
    //EFFECTS: Add order to a specific table if there isn't an order assigned.
    //         Return false if there is an order.
    public boolean addOrder(int tableNum, Order order) {
        if (table[tableNum - 1] == null) {
            table[tableNum - 1] = order;
            return true;
        } else {
            return false;
        }
    }

    public boolean setOrder(int tableNum, Order order) {
        if (table[tableNum - 1] != null) {
            this.order = order;
            return true;
        } else {
            return false;
        }

    }

    //REQUIRES: tableNum is greater than 0 less then 11.
    //EFFECTS: return the order of a specific table.
    public Order getOrder(int tableNum) {
        return table[tableNum - 1];
    }

    //REQUIRES: tableNum is greater than 0 less then 11.
    //MODIFIES: this
    //EFFECTS: change the order of that table to empty.
    public boolean finishOrder(int tableNum) {
        table[tableNum - 1] = null;
        return true;
    }

    //EFFECTS: return table amount.
    public int length() {
        return table.length;
    }



//    //EFFECTS: Add order to the first available table. If all tables are not available, return false.
//    public boolean addOrder(Order order) {
//        for (int i = 0; i < table.length; i++) {
//            if (table[i].length() != 0) {
//                table[i] = order;
//            }
//        }
//
//        return false;
//    }


}
