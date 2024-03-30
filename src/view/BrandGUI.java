package view;



import entity.Brand;

import javax.swing.*;

public class BrandGUI extends Layout {
    private JPanel container;
    private JLabel label_brand;
    private JLabel label_brand_name;
    private JTextField field_brand_name;
    private JButton button_brand_save;
    private Brand brand;

    public BrandGUI(Brand brand) {
        this.add(container);
        this.guiInitialize(300,300);
        this.brand = brand;
    }
}
