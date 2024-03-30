package dao;

import core.Database;
import entity.Brand;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {

    private final Connection connection;

    public BrandDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";

        try{
            ResultSet resultSet = this.connection.createStatement().executeQuery(sql);
            while(resultSet.next()) {
                brandList.add(this.match(resultSet));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public boolean save(Brand brand) {
        String query = "INSERT INTO public.brand (brand_name) VALUES (?)";
        try {

            PreparedStatement prepared = this.connection.prepareStatement(query);
            prepared.setString(1,brand.getName());
            return prepared.executeUpdate() != -1;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public Brand match(ResultSet resultSet) throws SQLException {
        Brand object = new Brand();
        object.setId(resultSet.getInt("brand_id"));
        object.setName(resultSet.getString("brand_name"));

        return object;
    }
}
