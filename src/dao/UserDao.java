package dao;

import core.Database;
import entity.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {

    private final Connection connection ;

    public UserDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";

        try{
            ResultSet resultSet = this.connection.createStatement().executeQuery(sql);
            while(resultSet.next()) {
                userList.add(this.match(resultSet));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User findByLogin(String username, String password) {
        User object = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";

        try {
            PreparedStatement prepared = this.connection.prepareStatement(query);
            prepared.setString(1,username);
            prepared.setString(2,password);

            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next()) {
                object = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      return object;
    }

    public User match(ResultSet resultSet) throws SQLException {
        User object = new User();
        object.setId(resultSet.getInt("user_id"));
        object.setUsername(resultSet.getString("user_name"));
        object.setPassword(resultSet.getString("user_password"));
        object.setRole(resultSet.getString("user_role"));
        return object;
    }

}
