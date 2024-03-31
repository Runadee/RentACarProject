package dao;

import core.Database;
import entity.Brand;
import entity.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDao {
    private Connection connection;
    private final BrandDao brandDao = new BrandDao();

    public ModelDao() {
        this.connection = Database.getInstance();
    }

    public Model getById(int id) {
        Model object = null;
        String query = "SELECT * FROM public.model WHERE model_id = ?";

        try {
            PreparedStatement prepared = this.connection.prepareStatement(query);
            prepared.setInt(1,id);
            ResultSet resultSet = prepared.executeQuery();
            if (resultSet.next()) {
                object = this.match(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return object;
    }

    public ArrayList<Model> findAll() {
        ArrayList<Model> modelList = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM public.model ORDER BY model_id ASC ");
            while(resultSet.next()) {
                modelList.add(this.match(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return modelList;
    }

    public boolean save(Model model ) {
        String query = "INSERT INTO public.model " +
                "(" +
                "model_brand_id" +
                "model_name" +
                "model_type" +
                "model_year" +
                "model_fuel" +
                "model_gear" +
                ")" +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement prepared = this.connection.prepareStatement(query);
            prepared.setInt(1,model.getBrand_id());
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    public boolean delete(int model_id) {
        String query = "DELETE FROM public.model WHERE model_id = ?";
        try {
            PreparedStatement prepared = this.connection.prepareStatement(query);
            prepared.setInt(1,model_id);
            return prepared.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public ArrayList<Model> selectByQuery(String query) {
        ArrayList<Model> modelList = new ArrayList<>();
        try{
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                modelList.add(this.match(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return modelList;
    }


    public Model match(ResultSet resultSet) throws SQLException {
        Model model = new Model();
        model.setId(resultSet.getInt("model_id"));
        model.setName(resultSet.getString("model_name"));
        model.setFuel(Model.Fuel.valueOf(resultSet.getString("model_fuel")));
        model.setGear(Model.Gear.valueOf(resultSet.getString("model_gear")));
        model.setType(Model.Type.valueOf(resultSet.getString("model_type")));
        model.setYear(resultSet.getString("model_year"));
        model.setBrand(this.brandDao.getById(resultSet.getInt("model_brand_id")));
        model.setBrand_id(resultSet.getInt("model_brand_id"));
        return model;
    }
}