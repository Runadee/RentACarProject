package business;

import core.Helper;
import dao.BrandDao;
import entity.Brand;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;

    public BrandManager() {
        this.brandDao = new BrandDao();
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
}
