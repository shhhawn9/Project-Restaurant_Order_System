package model;

import exception.NegativePriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {
    private Table tableList;
    private Order order1;
    private Order order2;
    private Item food1;
    private Item food2;
    private Item food3;
    private Item food4;
    private Item drink1;
    private Item drink2;

    @BeforeEach
    public void setup() {
        tableList = new Table();
        order1 = new Order();
        order2 = new Order();
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
        order1.addItem(food1);
        order1.addItem(food2);
        order1.addItem(drink1);
        order2.addItem(food3);
        order2.addItem(food4);
        order2.addItem(drink2);
    }

    @Test
    void tableIsEmpty() {
        assertEquals(10, tableList.length());
        for(int i = 0; i < tableList.length(); i++) {
            assertNull(tableList.getOrder(i + 1));
        }
    }

    @Test
    void nextTable() {
        assertTrue(tableList.addOrder(1, order1));
        assertTrue(tableList.addOrder(4, order2));
        assertEquals(2, tableList.nextTable());
        assertNotEquals(4, tableList.nextTable());
    }

    @Test
    void fullTable() {
        for(int i = 0; i < tableList.length(); i++) {
            assertTrue(tableList.addOrder(i + 1, order1));
        }
        assertEquals(-1, tableList.nextTable());
    }

    @Test
    void addOrder() {
        int i = tableList.nextTable();
        assertTrue(tableList.addOrder(i, order1));
        assertEquals(order1, tableList.getOrder(i));
        assertFalse(tableList.addOrder(i, order2));
    }

    @Test
    void setOrder() {
        assertTrue(tableList.addOrder(1, order1));
        assertTrue(tableList.setOrder(1, order2));
        assertFalse(tableList.setOrder(2, order2));
    }

    @Test
    void getOrder() {
        assertTrue(tableList.addOrder(1, order1));
        assertEquals(order1, tableList.getOrder(1));
    }

    @Test
    void finishOrder() {
        assertTrue(tableList.addOrder(1, order1));
        assertEquals(order1, tableList.getOrder(1));
        assertTrue(tableList.finishOrder(1));
        assertNull(tableList.getOrder(1));
    }
}
