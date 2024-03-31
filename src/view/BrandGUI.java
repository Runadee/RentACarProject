package view;



import business.BrandManager;
import core.Helper;
import entity.Brand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrandGUI extends Layout {
    private JPanel container;
    private JLabel label_brand;
    private JLabel label_brand_name;
    private JTextField field_brand_name;
    private JButton button_brand_save;
    private Brand brand;
    private BrandManager brandManager;

    public BrandGUI(Brand brand) {
        this.brandManager = new BrandManager();
        this.brand = brand;
        this.add(container);
        this.guiInitialize(300,300);


        if (brand != null) {
            field_brand_name.setText(brand.getName());
        }
        button_brand_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.field_brand_name)) {
                Helper.showMessage("Fill in all field !");
            } else {
                boolean result;

                if (this.brand == null) {
                    result = this.brandManager.save(new Brand(field_brand_name.getText()));

                } else {
                    this.brand.setName(field_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                }

                if (result) {
                    Helper.showMessage("Transaction successful");
                    dispose();
                } else {
                    Helper.showMessage("Error");
                }
            }
        });
    }
}
