package persistence;

import exception.NegativePriceException;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads menu from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Menu read() throws IOException, NegativePriceException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMenu(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses menu from JSON object and returns it
    private Menu parseMenu(JSONObject jsonObject) throws NegativePriceException {
//        String name = jsonObject.getString("name");
        Menu menu = new Menu();
        addItems(menu, jsonObject);
        return menu;
    }

    // MODIFIES: menu
    // EFFECTS: parses items from JSON object and adds them to menu
    private void addItems(Menu menu, JSONObject jsonObject) throws NegativePriceException {
        JSONArray jsonArray = jsonObject.getJSONArray("menu");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(menu, nextItem);
        }
    }

    // MODIFIES: menu
    // EFFECTS: parses item properties from JSON object and adds it to menu
    private void addItem(Menu menu, JSONObject jsonObject) throws NegativePriceException {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        boolean isFood = jsonObject.getBoolean("food");
        Item item = new Item(name, price, isFood);
        menu.addItem(item);


    }
}

    /*
    Citation
    WorkRoom demo
     */