package persistence;

import model.Menu;
import org.json.JSONObject;

import java.io.*;

public class JsonWriter {
    private static final int WHITESPACE = 10;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: open writer. Throws FileNotFoundException if the file cannot be open
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: write menu to file
    public void write(Menu menu) {
        JSONObject json = menu.toJson();
        saveToFile(json.toString(WHITESPACE));
    }

    //MODIFIES: this
    //EFFECTS: close writer.
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}

    /*
    Citation
    WorkRoom demo
     */