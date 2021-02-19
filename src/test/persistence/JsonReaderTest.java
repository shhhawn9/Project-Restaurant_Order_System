package persistence;

import exception.NegativePriceException;
import model.Menu;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Menu menu = reader.read();
            fail("IOException expected");
        } catch (NegativePriceException e) {
            fail("NegativePriceException unexpected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMenu() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMenu.json");
        try {
            Menu menu = reader.read();
            assertEquals(0, menu.length());
        } catch (NegativePriceException e) {
            fail("NegativePriceException unexpected");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderDemoMenu() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMenu.json");
        try {
            Menu menu = reader.read();
            assertEquals(8, menu.length());
            checkItem("Black Cod With Miso", 40.5, true, menu.getItem(1));
            checkItem("Wine", 80, false, menu.getItem(7));
        } catch (NegativePriceException e) {
            fail("NegativePriceException unexpected");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderDemoMenuNegativePrice() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralMenuNegativePrice.json");
        try {
            Menu menu = reader.read();
            assertEquals(8, menu.length());
            checkItem("Black Cod With Miso", 40.5, true, menu.getItem(1));
            checkItem("Wine", 80, false, menu.getItem(7));
        } catch (NegativePriceException e) {
            //Expected
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
