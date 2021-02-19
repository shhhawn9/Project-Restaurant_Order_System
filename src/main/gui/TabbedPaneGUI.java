package gui;

import exception.NegativePriceException;
import model.*;
import model.Menu;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;


//Main JPanel GUI
public class TabbedPaneGUI extends JPanel implements ActionListener {
    private static final String JSON_SAVE = "./data/restaurant.json";
    private static final String BUTTON_SOUND = "./res/odd.wav";
    private Menu menu;
    private Order order;
    private Order savedOrder;
    private Table table;
    private ImageIcon addButtonIcon;
    private ImageIcon removeButtonIcon;
    private JButton addToOrder;
    private JButton removeOrderItem;
    private JButton check;
    private JButton confirm;
    private JButton clearTable;
    private JButton removeMenuItem;
    private JButton addToMenu;
    private JButton saveMenu;
    private JButton loadMenu;
    private JLabel totalPrice;
    private JLabel actionInfo;
    private JLabel orderPrice;
    private JLabel saveStatus;
    private JLabel title;
    private JLabel newItemName;
    private JLabel newItemPrice;
    private JList menuList;
    private JList orderList;
    private JList tableList;
    private JList checkOrderList;
    private JList modifyMenuList;
    private JTextField itemNameText;
    private JTextField itemPriceText;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String itemName;
    private double itemPrice;

    public TabbedPaneGUI() {

        super(new GridLayout(1, 1));
        initiate();
        presetMenu();
        displayMenu();
        displayOrder();
        displayTable();
        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent panel1 = newOrderPane();
        tabbedPane.addTab("New Order", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = checkOrderPane();
        tabbedPane.addTab("Check Order", panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);

        JComponent panel3 = menuPane();
        tabbedPane.addTab("Modify Menu", panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_1);

        add(tabbedPane);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: initiate all global variable.
     */
    public void initiate() {
        menuList = new JList();
        orderList = new JList();
        tableList = new JList();
        checkOrderList = new JList();
        savedOrder = new Order();
        menu = new Menu();
        order = new Order();
        table = new Table();
        totalPrice = new JLabel("Total Price is $" + order.getTotalPrice());
        orderPrice = new JLabel("Total Price is $" + order.getTotalPrice());
        modifyMenuList = new JList();
        jsonWriter = new JsonWriter(JSON_SAVE);
        jsonReader = new JsonReader(JSON_SAVE);
        addButtonIcon = new ImageIcon("./res/plus32x32.png");
        removeButtonIcon = new ImageIcon("./res/minus32x32.png");
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: initiate all variable will be used on the new order pane.
     */
    private void newOrderPaneIni() {
        title = new JLabel("New Order");
        addToOrder = new JButton("Add to order", addButtonIcon);
        addToOrder.addActionListener(this);
        removeOrderItem = new JButton("Remove from order", removeButtonIcon);
        removeOrderItem.addActionListener(this);
        confirm = new JButton("Confirm order");
        confirm.addActionListener(this);
        displayMenu();
        displayOrder();
        title.setHorizontalAlignment(JLabel.CENTER);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: setup new order pane.
     */
    protected JComponent newOrderPane() {
        newOrderPaneIni();
        JPanel panel = new JPanel();
        JList displayMenu = menuList;
        JList displayOrder = orderList;
        JScrollPane displayMenuPane = new JScrollPane(displayMenu);
        JScrollPane displayOrderPane = new JScrollPane(displayOrder);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(0,1));
        panel.add(title);
        panel.add(displayMenuPane);
        panel.add(addToOrder);
        panel.add(displayOrderPane);
        panel.add(removeOrderItem);
        panel.add(totalPrice);
        panel.add(confirm);
        return panel;
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: initiate all variable will be used on the check order pane.
     */
    private void checkOrderPaneIni() {
        check = new JButton("Check order");
        check.addActionListener(this);
        clearTable = new JButton("Clear this table");
        clearTable.addActionListener(this);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: setup check order pane.
     */
    protected JComponent checkOrderPane() {
        checkOrderPaneIni();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(0, 1));
        JLabel title = new JLabel("Check Order");
        title.setHorizontalAlignment(JLabel.CENTER);
        JList displayTable = tableList;
        JScrollPane displayTablePane = new JScrollPane(displayTable);
        JList displayCheckOrder = checkOrderList;
        JScrollPane checkOrderPane = new JScrollPane(displayCheckOrder);
        displayTable();
        displayCheckOrder();
        panel.add(title);
        panel.add(displayTablePane);
        panel.add(check);
        panel.add(orderPrice);
        panel.add(checkOrderPane);
        panel.add(clearTable);
        return panel;
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: initiate all variable will be used on the menu pane.
     */
    private void menuPaneIni() {
        removeMenuItem = new JButton("Remove from menu", removeButtonIcon);
        removeMenuItem.addActionListener(this);
        actionInfo = new JLabel("Please select an item to add or remove");
        addToMenu = new JButton("Add to menu", addButtonIcon);
        addToMenu.addActionListener(this);
        saveMenu = new JButton("Save menu");
        saveMenu.addActionListener(this);
        loadMenu = new JButton("Load menu");
        loadMenu.addActionListener(this);
        itemNameText = new JTextField(20);
        itemPriceText = new JTextField(20);
        saveStatus = new JLabel("Save and load menu below");
        newItemName = new JLabel("New Item Name");
        newItemPrice = new JLabel("New Item Price");
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: setup menu pane.
     */
    protected JComponent menuPane() {
        menuPaneIni();
        JPanel panel = new JPanel();
        JLabel filler = new JLabel("Modify Menu");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(0, 1));
        panel.add(filler);
        displayModifyMenu();
        JList displayMenu = modifyMenuList;
        JScrollPane displayMenuPane = new JScrollPane(displayMenu);
        panel.add(displayMenuPane);
        panel.add(actionInfo);
        panel.add(removeMenuItem);
        panel.add(newItemName);
        panel.add(itemNameText);
        panel.add(newItemPrice);
        panel.add(itemPriceText);
        panel.add(addToMenu);
        panel.add(saveStatus);
        panel.add(saveMenu);
        panel.add(loadMenu);
        return panel;
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: add preset items to the menu.
     */
    public void presetMenu() {
        try {
            menu.addItem(new Item("Black Cod With Miso", 40.50, true));
            menu.addItem(new Item("Black Label Burger", 20.70, true));
            menu.addItem(new Item("Canlis Salad", 15.50, true));
            menu.addItem(new Item("Carnitas Burrito", 17.00, true));
        } catch (NegativePriceException e) {
            System.out.println("");
        }
//        menu.addItem(new Item("Soda", 3.50, false));
//        menu.addItem(new Item("Wine", 80.00, false));
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: display menu on the list.
     */
    private void displayMenu() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 1; i <= menu.length(); i++) {
            model.addElement(menu.getItem(i).getName() + " --- $" + menu.getItem(i).getPrice());
        }
        menuList.setModel(model);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: display current order on the list.
     */
    private void displayOrder() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 1; i <= order.length(); i++) {
            model.addElement(order.getItem(i).getName() + " --- $" + order.getItem(i).getPrice());
        }
        orderList.setModel(model);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: display the table on the list.
     */
    private void displayTable() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 1; i <= table.length(); i++) {
            model.addElement("Table " + i);
        }
        tableList.setModel(model);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: display the checked order from savedorder object on the list.
     */
    private void displayCheckOrder() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 1; i <= savedOrder.length(); i++) {
            model.addElement(savedOrder.getItem(i).getName() + " --- $" + savedOrder.getItem(i).getPrice());
        }
        checkOrderList.setModel(model);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: display the modify menu from menu object on the list.
     */
    private void displayModifyMenu() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 1; i <= menu.length(); i++) {
            model.addElement(menu.getItem(i).getName() + " --- $" + menu.getItem(i).getPrice());
        }
        modifyMenuList.setModel(model);
    }

    /*
     *  EFFECTS: execute button actions.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add to order")) {
            addToOrderAction();
        } else if (e.getActionCommand().equals("Remove from order") && order.length() != 0) {
            removeFromOrderAction();
        } else if (e.getActionCommand().equals("Confirm order")) {
            confirmOrderAction();
        } else if (e.getActionCommand().equals("Check order")) {
            checkOrderAction();
        } else if (e.getActionCommand().equals("Clear this table")) {
            clearTableAction();
        } else if (e.getActionCommand().equals("Remove from menu")) {
            removeMenuItemAction();
        } else if (e.getActionCommand().equals("Add to menu")) {
            addToMenuAction();
        } else if (e.getActionCommand().equals("Save menu")) {
            saveMenuAction();
        } else if (e.getActionCommand().equals("Load menu")) {
            loadMenuAction();

        }
    }

    /*
     *  EFFECTS: return the index of selected item on the menu list.
     */
    private int menuListSelectIndex() {
        int selected = menuList.getSelectedIndex();
        return selected;
    }

    /*
     *  EFFECTS: return the index of selected item on the order list.
     */
    private int orderListSelectIndex() {
        int selected = orderList.getSelectedIndex();
        return selected;
    }

    /*
     *  EFFECTS: return the index of selected item on the table list.
     */
    private int tableListSelectIndex() {
        int selected = tableList.getSelectedIndex();
        return selected;
    }

    /*
     *  EFFECTS: return the index of selected item on the modify menu list.
     */
    private int modifyMenuListSelectIndex() {
        int selected = modifyMenuList.getSelectedIndex();
        return selected;
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: add selected item from menu to order.
     */
    private void addToOrderAction() {
        order.addItem(menu.getItem(menuListSelectIndex() + 1));
        totalPrice.setText("Total Price is $" + String.format("%.2f", order.getTotalPrice()));
        displayOrder();
        playSound(BUTTON_SOUND);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: remove selected item from order.
     */
    private void removeFromOrderAction() {
        order.removeItem(orderListSelectIndex() + 1);
        totalPrice.setText("Total Price is $" + String.format("%.2f", order.getTotalPrice()));
        displayOrder();
        playSound(BUTTON_SOUND);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: Confirm order and add it to the next available table.
     */
    private void confirmOrderAction() {
        if (table.nextTable() != -1) {
            totalPrice.setText("Order has been assigned to Table " + table.nextTable());
            table.addOrder(table.nextTable(), order);
            order = new Order();
            displayOrder();
        } else {
            totalPrice.setText("All tables are full! Please finish order in the Check Order panel.");
        }
        playSound(BUTTON_SOUND);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: check the order on selected table.
     */
    private void checkOrderAction() {
        if (table.getOrder(tableListSelectIndex() + 1) != null) {
            savedOrder = table.getOrder(tableListSelectIndex() + 1);
            orderPrice.setText("Total Price is $" + String.format("%.2f", savedOrder.getTotalPrice()));
            displayCheckOrder();
        } else {
            savedOrder = new Order();
            displayCheckOrder();
            orderPrice.setText("This table is available.");
        }
        playSound(BUTTON_SOUND);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: remove all item from table and make it available for another order.
     */
    private void clearTableAction() {
        if (table.getOrder(tableListSelectIndex() + 1) != null) {
            table.finishOrder(tableListSelectIndex() + 1);
            orderPrice.setText("This table is available now.");
            savedOrder = new Order();
            displayCheckOrder();
        } else {
            orderPrice.setText("This table is already empty!");
        }
        playSound(BUTTON_SOUND);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: remove selected item from menu.
     */
    private void removeMenuItemAction() {
        menu.removeItem(modifyMenuListSelectIndex() + 1);
        displayModifyMenu();
        displayMenu();
        playSound(BUTTON_SOUND);
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: add name and price from text field to menu as an item object.
     *           refresh menu and modified menu. Clear text fields.
     */
    private void addToMenuAction() {
        itemPrice = Double.parseDouble(itemPriceText.getText());
        itemName = itemNameText.getText();
        try {
            menu.addItem(new Item(itemName, itemPrice, true));
        } catch (NegativePriceException e) {
            System.out.println("price must be larger than 0");
        }
        displayModifyMenu();
        displayMenu();
        itemPriceText.setText("");
        itemNameText.setText("");
        playSound(BUTTON_SOUND);
    }

    //EFFECTS: save current menu to file
    private void saveMenuAction() {
        try {
            jsonWriter.open();
            jsonWriter.write(menu);
            jsonWriter.close();
            System.out.println("All orders are being save to " + JSON_SAVE);
            saveStatus.setText("Menu saved to file " + JSON_SAVE);
            playSound(BUTTON_SOUND);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_SAVE);
        }
    }

    //EFFECTS: load previous menu from file
    private void loadMenuAction() {
        try {
            menu = jsonReader.read();
            System.out.println("Loaded all orders from " + JSON_SAVE);
            displayMenu();
            displayModifyMenu();
            saveStatus.setText("Menu loaded from file " + JSON_SAVE);
            playSound(BUTTON_SOUND);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_SAVE);
        } catch (NegativePriceException e) {
            System.out.println("Caught negative price from file: " + JSON_SAVE);
        }
    }

    // REQUIRES: soundFile must be a path to a wav file.
    // EFFECTS: play sound from soundFile.
    private void playSound(String soundFile) {
        File file = new File(soundFile);
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clip.start();
    }

}
