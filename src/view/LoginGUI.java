package view;

import core.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel container;
    private JPanel w_top;
    private JLabel label_welcome;
    private JLabel label_welcome2;
    private JTextField field_username;
    private JPasswordField field_password;
    private JButton button_login;
    private JLabel label_username;
    private JLabel label_password;

    public LoginGUI() {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(500,500);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x,y);
        this.setVisible(true);

        button_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.field_username) || Helper.isFieldEmpty(this.field_password)) {
                Helper.showMessage("Please fill in all fields !");
            }


        });
    }


}
