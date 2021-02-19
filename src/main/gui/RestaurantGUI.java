package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Run GUI
public class RestaurantGUI extends JFrame {
    private JFrame frame;
    private TabbedPaneGUI tabbedPaneGUI;

    public RestaurantGUI() {
        frame = new JFrame();
        tabbedPaneGUI = new TabbedPaneGUI();
        runRestaurant();
    }

    public void runRestaurant() {
        frame.setSize(500, 800);
        frame.add(tabbedPaneGUI, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("My Restaurant");
        frame.setVisible(true);
    }
}
