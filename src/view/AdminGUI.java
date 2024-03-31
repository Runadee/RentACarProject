package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminGUI extends Layout {
    private JPanel container;
    private JLabel label_welcome;
    private JTabbedPane tabbedPane1;
    private JButton button_logout;
    private JPanel panel_brand;
    private JScrollPane scroll_brand;
    private JTable table_brand;
    private User user;
    private DefaultTableModel tableModel_brand;
    private BrandManager brandManager;
    private JPopupMenu brandMenu;

    public AdminGUI(User user) {
        this.tableModel_brand = new DefaultTableModel();
        this.brandManager = new BrandManager();
        this.add(container);
        this.user = user;
        this.guiInitialize(1000, 500);

        if (this.user == null) {
            dispose();
        }
        this.label_welcome.setText(" Welcome " + this.user.getUsername());
        loadBrandTable();
        loadBrandComponent();
        this.table_brand.setComponentPopupMenu(this.brandMenu);
    }

    public void loadBrandTable() {
        Object[] column_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(column_brand.length);
        this.createTable(this.tableModel_brand, this.table_brand, column_brand, brandList);
    }

    public void loadBrandComponent() {
        this.table_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table_brand.rowAtPoint(e.getPoint());
                table_brand.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        // Pop-Up Menu New, Update, Delete Actions
        this.brandMenu = new JPopupMenu();
        this.brandMenu.add("New").addActionListener(e -> {
            BrandGUI brandGUI = new BrandGUI(null);
            brandGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });

        this.brandMenu.add("Update").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(table_brand,0);
            BrandGUI brandGUI = new BrandGUI(this.brandManager.getById(selectBrandId));
            brandGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.brandMenu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(table_brand,0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMessage("Succeed !");
                    loadBrandTable();
                } else {
                    Helper.showMessage("Error !");
                }
            }
        });
    }

}
