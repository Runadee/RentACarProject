import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {

        try {

            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/rentacar",
                    "postgres",
                    "9252"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
