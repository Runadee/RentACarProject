package core;

import javax.swing.*;

public class Helper {

    public static boolean isFieldEmpty (JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.INFORMATION_MESSAGE);
    }


}
