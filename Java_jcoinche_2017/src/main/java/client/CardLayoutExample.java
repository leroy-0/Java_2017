package client;

import javax.swing.*;
import java.awt.*;

public class CardLayoutExample
{
    private JPanel contentPane;
    private MyPanel panel1;
    private MyPanel2 panel2;

    public void displayGUI()
    {
        JFrame frame = new JFrame("Card Layout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());
        panel1 = new MyPanel(contentPane);
        panel2 = new MyPanel2();
        contentPane.add(panel1, "Panel 1");
        contentPane.add(panel2, "Panel 2");
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}