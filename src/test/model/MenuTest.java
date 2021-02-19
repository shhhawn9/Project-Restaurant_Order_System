package model;

import exception.NegativePriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    private Item food1;
    private Item food2;
    private Item food3;
    private Item food4;
    private Item drink1;
    private Item drink2;
    Menu menu;

    @BeforeEach
    public void setup() {
        menu = new Menu();
        try {
            food1 = new Item("food1", 10.50, true);
            food2 = new Item("food2", 11, true);
            food3 = new Item("food3", 12, true);
            food4 = new Item("food4", 13, true);
            drink1 = new Item("drink1", 4, false);
            drink2 = new Item("drink2", 5, false);
        } catch (NegativePriceException e) {
            fail("");
        }

    }

    @Test
    public void menuIsEmpty() {
        assertEquals(0, menu.length());
        assertFalse(menu.length() > 0);
    }

    @Test
    public void addItem() {
        assertEquals(0, menu.length());
        menu.addItem(food1);
        assertEquals(1, menu.length());
    }

    @Test
    public void insertItem() {
        menu.addItem(food1);
        menu.addItem(food3);
        assertEquals(food3, menu.getItem(2));
        menu.insertItem(2, food2);
        assertEquals(food2, menu.getItem(2));
        assertEquals(food3, menu.getItem(3));
    }

    @Test
    public void removeItem() {
        menu.addItem(food1);
        menu.addItem(food2);
        menu.addItem(food3);
        assertEquals(3, menu.length());
        menu.removeItem(2);
        assertEquals(2, menu.length());
        assertEquals(food3, menu.getItem(2));
        assertNotEquals(food2, menu.getItem(2));
    }

    @Test
    public void modifyItem() {
        menu.addItem(food1);
        menu.addItem(food2);
        menu.addItem(food3);

        assertEquals("Food1", menu.getItem(1).getName());
        assertEquals(10.50, menu.getItem(1).getPrice());
        food1.setName("Food10");
        try {
            food1.setPrice(12.50);
        } catch (NegativePriceException e) {
            fail("");
        }
        food1.setIsDrink();
        menu.modifyItem(1, food1);
        assertEquals("Food10", menu.getItem(1).getName());
        assertEquals(12.50, menu.getItem(1).getPrice());
        assertTrue(menu.getItem(1).isDrink());
        assertFalse(menu.getItem(1).isFood());

        menu.modifyItem(2, food4);
        assertEquals(menu.getItem(2), food4);
    }

    @Test
    public void length() {
        menu.addItem(food1);
        menu.addItem(food2);
        menu.addItem(food3);

        assertEquals(3, menu.length());
    }

    @Test
    public void testToJson() {
        try {
            menu.addItem(food1);
            menu.addItem(food2);
            menu.addItem(food3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.write(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            menu = reader.read();
            assertEquals(3, menu.length());
            assertEquals("Food1", menu.getItem(1).getName());
            assertEquals(10.50, menu.getItem(1).getPrice());
            assertTrue(menu.getItem(1).isFood());
            assertEquals("Food3", menu.getItem(3).getName());
            assertEquals(12, menu.getItem(3).getPrice());
            assertTrue(menu.getItem(3).isFood());

        } catch (NegativePriceException e) {
            fail("Exception should not have been thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
