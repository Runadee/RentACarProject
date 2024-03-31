package view;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
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
    private User user;
    private DefaultTableModel tableModel_brand;
    private DefaultTableModel tableModel_model;
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;

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
        loadModelTable();
        loadModelComponent();

    }

    private void loadModelComponent() {
        tableRowSelect(this.table_model);

        // Pop-Up Menu New, Update, Delete Actions
        this.model_menu = new JPopupMenu();
        this.model_menu.add("New").addActionListener(e -> {

        });

        this.model_menu.add("Update").addActionListener(e -> {

        });
        this.model_menu.add("Delete").addActionListener(e -> {

        });
        this.table_model.setComponentPopupMenu(this.model_menu);
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
                }
            });
        });
        this.brand_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(table_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMessage("Succeed !");
                    loadBrandTable();
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

}
