package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brand;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminGUI extends Layout {
    private JPanel container;
    private JLabel label_welcome;
    private JTabbedPane panel_model;
    private JButton button_logout;
    private JPanel panel_brand;
    private JScrollPane scroll_brand;
    private JTable table_brand;
    private JScrollPane scroll_model;
    private JTable table_model;
    private JComboBox<ComboItem> cmb_s_model_brand;
    private JComboBox<Model.Type> cmb_s_model_type;
    private JComboBox<Model.Fuel> cmb_s_model_fuel;
    private JComboBox<Model.Gear> cmb_s_model_gear;
    private JButton btn_search_model;
    private JButton button_cancel_model;
    private User user;
    private DefaultTableModel tableModel_brand;
    private DefaultTableModel tableModel_model;
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;
    private Object[] col_model;

    public AdminGUI(User user) {
        this.tableModel_brand = new DefaultTableModel();
        this.tableModel_model = new DefaultTableModel();
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.user = user;
        this.guiInitialize(1000, 500);

        if (this.user == null) {
            dispose();
        }
        this.label_welcome.setText(" Welcome " + this.user.getUsername());
        loadBrandTable();
        loadBrandComponent();

        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

    }

    private void loadModelComponent() {
        tableRowSelect(this.table_model);

        // Pop-Up Menu New, Update, Delete Actions
        this.model_menu = new JPopupMenu();
        this.model_menu.add("New").addActionListener(e -> {
            ModelGUI modelGUI = new ModelGUI(new Model());
            modelGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });

        });

        this.model_menu.add("Update").addActionListener(e -> {

            int selectModelId = this.getTableSelectedRow(table_model, 0);
            ModelGUI modelGUI = new ModelGUI(this.modelManager.getById(selectModelId));
            modelGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Delete").addActionListener(e -> {

            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(table_model, 0);
                if (this.modelManager.delete(selectModelId)) {
                    Helper.showMessage("Succeed !");
                    loadModelTable(null);
                } else {
                    Helper.showMessage("Error !");
                }
            }
        });
        this.table_model.setComponentPopupMenu(this.model_menu);

        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem()
            );


            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.button_cancel_model.addActionListener(e -> {
            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }

    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID", "Brand", "Name", "Type", "Year", "Fuel", "Gear"};
        if (modelList == null) {
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }
        createTable(tableModel_model, this.table_model, col_model, modelList);

    }

    public void loadBrandTable() {
        Object[] column_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(column_brand.length);
        this.createTable(this.tableModel_brand, this.table_brand, column_brand, brandList);
    }

    public void loadBrandComponent() {
        tableRowSelect(this.table_brand);
        // Pop-Up Menu New, Update, Delete Actions
        this.brand_menu = new JPopupMenu();
        this.brand_menu.add("New").addActionListener(e -> {
            BrandGUI brandGUI = new BrandGUI(null);
            brandGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });

        this.brand_menu.add("Update").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(table_brand, 0);
            BrandGUI brandGUI = new BrandGUI(this.brandManager.getById(selectBrandId));
            brandGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });
        });
        this.brand_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(table_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMessage("Succeed !");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                } else {
                    Helper.showMessage("Error !");
                }
            }
        });
        this.table_brand.setComponentPopupMenu(this.brand_menu);
    }

    public void loadModelTable() {
        Object[] column_model = {"Model ID", "Brand", "Name", "Type", "Year", "Fuel", "Gear"};
        ArrayList<Object[]> modelList = this.modelManager.getForTable(column_model.length, this.modelManager.findAll());
        createTable(this.tableModel_model, this.table_model, column_model, modelList);
    }

    public void loadModelFilter() {
        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();

    }

    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }

}
