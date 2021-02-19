package model;

import exception.NegativePriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Item food1;
    private Item food2;
    private Item food3;
    private Item food4;
    private Item drink1;
    private Item drink2;
    Order order;

    @BeforeEach
    public void setup() {
        order = new Order();
        try {
            food1 = new Item("Food1", 10.50, true);
            food2 = new Item("Food2", 11, true);
            food3 = new Item("Food3", 12, true);
            food4 = new Item("Food4", 13, true);
            drink1 = new Item("Drink1", 4, false);
            drink2 = new Item("Drink2", 5, false);
        } catch (NegativePriceException e) {
            fail("");
        }

    }

    @Test
    public void orderIsEmpty() {
        assertEquals(0, order.length());
        assertFalse(order.length() > 0);
    }

    @Test
    public void addItem() {
        assertEquals(0, order.length());
        order.addItem(food1);
        assertEquals(1, order.length());
    }

    @Test
    public void insertItem() {
        order.addItem(food1);
        order.addItem(food3);
        assertEquals(food3, order.getItem(2));
        order.insertItem(2, food2);
        assertEquals(food2, order.getItem(2));
        assertEquals(food3, order.getItem(3));
    }

    @Test
    public void removeItem() {
        order.addItem(food1);
        order.addItem(food2);
        order.addItem(food3);
        assertEquals(3, order.length());
        order.removeItem(2);
        assertEquals(2, order.length());
        assertEquals(food3, order.getItem(2));
        assertNotEquals(food2, order.getItem(2));
    }

    @Test
    public void length(){
        order.addItem(food1);
        order.addItem(food2);
        order.addItem(food3);

        assertEquals(3, order.length());
    }

    @Test
    public void getTotalPrice() {
        assertEquals(0.00, order.getTotalPrice());
        order.addItem(food1);
        order.addItem(food2);
        order.addItem(food3);
        assertEquals(food1.getPrice()
                            + food2.getPrice()
                            + food3.getPrice()
                            , order.getTotalPrice());
    }
}

