package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



class MyPanel extends JPanel {

    private JTextField Ip;
    private JTextField Port;
    private JLabel jcomp2;
    private JLabel jcomp3;
    private JButton jcomp4;
    private JLabel jcomp5;
    private JLabel jcomp6;
    private Client client;
    private JPanel contentPane;

    public MyPanel(JPanel panel) {

        contentPane = panel;
        //construct components
        Ip = new JTextField (2);
        Port = new JTextField(1);
        jcomp2 = new JLabel ("Port Server :");
        jcomp5 = new JLabel ("Ip Server :");
        jcomp4 = new JButton ("openNewWindow");

        //adjust size and set layout
        setPreferredSize (new Dimension (315, 85));
        setLayout (null);

        //set component bounds (only needed by Absolute Positioning)
        Ip.setBounds (245, 50, 120, 25);
        jcomp2.setBounds (35, 70, 185, 50);
        jcomp5.setBounds (35, 30, 185, 50);
        Port.setBounds (245, 90, 60, 25);
        jcomp4.setLocation(0, 0);
        jcomp4.setSize(315, 25);
        jcomp4.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                //new Thread(client =  new Client(Ip.getText(), Integer.parseInt(Port.getText()))).start();
                cardLayout.next(contentPane);
                //client.connectToServer();
            }
        });

        //add components
        add (Port);
        add (Ip);
        add (jcomp2);
        add (jcomp4);
        add(jcomp5);
    }
}

class MyPanel2 extends JPanel {
    private JButton jcomp1;
    private JButton jcomp2;
    private JButton jcomp3;
    private JTextField jcomp4;

    public MyPanel2() {
        //construct components
        jcomp1 = new JButton ("test1");
        jcomp2 = new JButton ("test2");
        jcomp3 = new JButton ("test3");
        jcomp4 = new JTextField (5);

        //adjust size and set layout
        setPreferredSize (new Dimension (395, 156));
        setLayout (null);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (20, 45, 100, 25);
        jcomp2.setBounds (135, 60, 100, 25);
        jcomp3.setBounds (260, 35, 100, 25);
        jcomp4.setBounds (105, 115, 100, 25);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
    }
}