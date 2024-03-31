package business;

import core.Helper;
import dao.BrandDao;
import entity.Brand;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;

    public BrandManager() {
        this.brandDao = new BrandDao();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> brandRowList = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRowList.add(rowObject);
        }
        return brandRowList;
    }

    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    public boolean save(Brand brand) {
        if (brand.getId() != 0) {
            Helper.showMessage("This brand is available");
        }
        return this.brandDao.save(brand);
    }

    public Brand getById(int id) {
        return this.brandDao.getById(id);
    }

    public boolean update(Brand brand) {
        if (this.getById(brand.getId()) == null) {
            Helper.showMessage("Not Found !");
        }
        return this.brandDao.update(brand);
    }
}
