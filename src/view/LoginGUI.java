package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel label_welcome;
    private JLabel label_welcome2;
    private JTextField field_username;
    private JPasswordField field_password;
    private JButton button_login;
    private JLabel label_username;
    private JLabel label_password;
    private final UserManager userManager;

    public LoginGUI() {
        this.userManager = new UserManager();

        this.add(container);
        this.guiInitialize(500,500);

        button_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.field_username) || Helper.isFieldEmpty(this.field_password)) {
                Helper.showMessage("Please fill in all fields !");
            } else {
                User loginUser = this.userManager.findByLogin(this.field_username.getText(), this.field_password.getText());
                if (loginUser == null) {
                    Helper.showMessage("User not found");
                } else {
                    AdminGUI adminGUI = new AdminGUI(loginUser);
                    dispose();
                }
            }


        });
    }


}
