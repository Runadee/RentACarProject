package view;

import entity.User;

import javax.swing.*;
import java.awt.*;

public class AdminGUI extends Layout {
    private JPanel container;
    private JLabel label_welcome;
    private User user;

    public AdminGUI(User user) {
        this.add(container);
        this.user = user;
        this.guiInitialize(1000,500);

        if (this.user == null) {
           dispose();
        }

        this.label_welcome.setText(" Welcome " + this.user.getUsername());
    }

}
