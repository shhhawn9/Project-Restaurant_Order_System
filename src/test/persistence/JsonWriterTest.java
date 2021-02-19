package persistence;

import exception.NegativePriceException;
import model.Item;
import model.Menu;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Menu menu = new Menu();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Expected
        }
    }

    @Test
    void testWriterEmptyMenu() {
        try {
            Menu menu = new Menu();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMenu.json");
            writer.open();
            writer.write(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMenu.json");
            menu = reader.read();
            assertEquals(0, menu.length());
        } catch (NegativePriceException e) {
            fail("Exception should not have been thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMenu() {
        try {
            Menu menu = new Menu();
            try {
                menu.addItem(new Item("TestFood1", 10.9, true));
                menu.addItem(new Item("TestDrink1", 11.2, false));
            } catch (NegativePriceException e) {
                fail("");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.write(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            menu = reader.read();
            assertEquals(2, menu.length());
            checkItem("TestFood1", 10.9, true, menu.getItem(1));
            checkItem("TestDrink1", 11.2, false, menu.getItem(2));

        } catch (NegativePriceException e) {
            fail("Exception should not have been thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
