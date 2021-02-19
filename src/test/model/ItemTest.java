package model;

import exception.NegativePriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item food1;
    private Item food2;
//    private Item food3;
//    private Item food4;
    private Item drink1;
//    private Item drink2;

    @BeforeEach
    public void setup(){
        try {
            food1 = new Item("Food1", 10.50, true);

//        food3 = new Item("food3", 12, true);
//        food4 = new Item("food4", 13, true);
            drink1 = new Item("Drink1", 4, false);
//        drink2 = new Item("drink2", 5, false);
        } catch (NegativePriceException e) {
            fail("");
        }
    }

    @Test
    void setupNewItemNegativePriceException() {
        try {
            food2 = new Item("food2", -2, true);
            fail("Didn't throw exception");
        } catch (NegativePriceException e) {
            //Excepted
        }

    }

    @Test
    public void testItem() {
        assertEquals("Food1", food1.getName());
        assertEquals(10.50, food1.getPrice());
        assertTrue(food1.isFood());
        assertFalse(food1.isDrink());

        assertFalse(drink1.isFood());
        assertTrue(drink1.isDrink());
    }

    @Test
    void setPrice() {
        assertEquals(10.50, food1.getPrice());
        try {
            food1.setPrice(20.50);
        } catch (NegativePriceException e) {
            fail("");
        }
        assertEquals(20.50, food1.getPrice());
    }

    @Test
    void setNegativePriceException() {
        assertEquals(10.50, food1.getPrice());
        try {
            food1.setPrice(-12);
            fail("Didn't throw exception");
        } catch (NegativePriceException e) {
            //Expected
        }
    }
    @Test
    void getPrice() {
        assertEquals(10.50, food1.getPrice());
        assertNotEquals(20.00, food1.getPrice());
    }

    @Test
    void setName() {
        assertEquals("Food1", food1.getName());
        food1.setName("Food10");
        assertEquals("Food10", food1.getName());
    }

    @Test
    void getName() {
        assertEquals("Food1", food1.getName());
        assertNotEquals("Food10", food1.getName());
    }

    @Test
    void setIsFood() {
        assertFalse(drink1.isFood());
        drink1.setIsFood();
        assertTrue(drink1.isFood());
    }

    @Test
    void isFood() {
        assertTrue(food1.isFood());
        assertFalse(food1.isDrink());
    }

    @Test
    void setIsDrink() {
        assertTrue(food1.isFood());
        food1.setIsDrink();
        assertTrue(food1.isDrink());
    }

    @Test
    void isDrink() {
        assertTrue(drink1.isDrink());
        assertFalse(drink1.isFood());
    }
}