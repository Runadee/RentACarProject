package business;

import core.Helper;
import dao.ModelDao;
import entity.Brand;
import entity.Model;

import java.util.ArrayList;
import java.util.Objects;

public class ModelManager {
    private final ModelDao modelDao = new ModelDao();

    public Model getById(int id) {
        return this.modelDao.getById(id);
    }

    public ArrayList<Model> findAll(){
        return this.modelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList) {
        ArrayList<Object[]> modelObjectList = new ArrayList<>();
        for (Model object : modelList ){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = object.getId();
            rowObject[i++] = object.getBrand().getName();
            rowObject[i++] = object.getName();
            rowObject[i++] = object.getType();
            rowObject[i++] = object.getYear();
            rowObject[i++] = object.getFuel();
            rowObject[i++] = object.getGear();
            modelObjectList.add(rowObject);
        }
        return modelObjectList;
    }

    public boolean save(Model model) {
        if (this.getById(model.getId()) != null) {
            Helper.showMessage("Error: Model with ID " + model.getId() + " already exists.");
            return false;
        }
        return this.modelDao.save(model);
    }

    public boolean update(Model model) {
        if (this.getById(model.getId()) == null) {
            Helper.showMessage(model.getId() + " This model not found !");
            return false;
        }
        return this.modelDao.update(model);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMessage(id + " This model not found !");
            return false;
        }
        return this.modelDao.delete(id);
    }


}
