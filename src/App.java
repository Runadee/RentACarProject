import business.UserManager;
import core.Database;
import view.AdminGUI;
import view.LoginGUI;

import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {

        // LoginGUI loginGUI = new LoginGUI();
         UserManager userManager = new UserManager();
         AdminGUI adminGUI = new AdminGUI(userManager.findByLogin("admin","1234"));
    }
}
