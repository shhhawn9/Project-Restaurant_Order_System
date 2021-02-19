//package gui;
//
//import model.Order;
//import model.Menu;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class NewOrderPane extends JPanel {
//    private JComponent mainPanel;
//    private JTable displayMenu;
//    private JButton button1;
//    private JTable displayOrder;
//    private JButton addItemToOrder;
//    private JButton removeItemFromOrder;
//    private JLabel displayPrice;
//    private JPanel panel;
//    private JTable menuTable;
//    private JTable orderTable;
//
//    public NewOrderPane() {
//        mainPanel = new JPanel();
//        displayPanel();
//    }
//
//    public void displayPanel() {
//        JPanel panel = new JPanel();
//        JLabel title = new JLabel("New Order");
//        addToOrder = new JButton("Add to order");
//        addToOrder.addActionListener(this);
//        removeOrderItem = new JButton("Remove from order");
//        removeOrderItem.addActionListener(this);
//        confirm = new JButton("Confirm order");
//        confirm.addActionListener(this);
//        displayMenu();
//        displayOrder();
//        JList displayMenu = menuList;
//        JScrollPane displayMenuPane = new JScrollPane(displayMenu);
//        JList displayOrder = orderList;
//        JScrollPane displayOrderPane = new JScrollPane(displayOrder);
//        title.setHorizontalAlignment(JLabel.CENTER);
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        panel.setLayout(new GridLayout(7,1));
//        panel.add(title);
//        panel.add(displayMenuPane);
//        panel.add(addToOrder);
//        panel.add(displayOrderPane);
//        panel.add(removeOrderItem);
//        panel.add(totalPrice);
//        panel.add(confirm);
//    }
//
//    public void addRowToJTable() {
//        DefaultTableModel model = (DefaultTableModel) displayMenu.getModel();
//
//    }
//
//    private void displayMenu() {
//        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
//
//        Object[] rowData = {"Name", "Price"};
//        model.setColumnIdentifiers(rowData);
//        for (int i = 1; i <= menu.length(); i++) {
//            rowData[0] = menu.getItem(i).getName();
//            rowData[1] = menu.getItem(i).getPrice();
//            model.addRow(rowData);
//        }
//        menuTable.setModel(model);
//    }
//
//    private void displayOrder() {
//        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
//
//        Object[] rowData = {"Name", "Price"};
//        model.setColumnIdentifiers(rowData);
//        for (int i = 1; i <= order.length(); i++) {
//            rowData[0] = order.getItem(i).getName();
//            rowData[1] = order.getItem(i).getPrice();
//            model.addRow(rowData);
//        }
//        orderTable.setModel(model);
//    }
//}
