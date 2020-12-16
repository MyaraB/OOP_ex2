package src.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import javax.swing.*;

public class Label extends JFrame implements ActionListener {

    JTextField af;
    JLabel l;
    JButton b;


    public Label(){
af = new JTextField();
        af.setBounds(50,50,150,20);
        l= new JLabel();
        l.setBounds(50,100,250,20);
        l.setText(("Insert url to find the IP"));
        b= new JButton("find ip");
        b.setBounds(50,150,95,30);
        b.addActionListener(this);
        this.add(b);
        this.add(af);
        this.add(l);
        setSize(400,400);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String host = af.getText();
        try {
            String ip = java.net.InetAddress.getByName(host).getHostAddress();
            l.setText("IP of" + host + "is: " + ip);


        } catch (UnknownHostException unkownHostException) {
            unkownHostException.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Label();
    }
}
