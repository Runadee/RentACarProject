package core;

import javax.swing.*;

public class Helper {

    public static boolean isFieldEmpty (JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String string) {
        optionPaneTR();
        String message;
        if (string.equals("sure")) {
            message = "Are you sure you want to do this ?";
        } else {
            message = string;
        }
        return JOptionPane.showConfirmDialog(null,message,"Please confirm !", JOptionPane.YES_NO_OPTION) == 0;
    }


    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText" , "OK");
        UIManager.put("OptionPane.yesButtonText" , "Yes");
        UIManager.put("OptionPane.noButtonText" , "No");
    }

    public static boolean isFieldListEmpty(JTextField[] jTextFields) {
        for (JTextField field : jTextFields) {
            if (isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }
}
