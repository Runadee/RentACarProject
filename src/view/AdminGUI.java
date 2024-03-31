package view;

import business.BrandManager;
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
        this.guiInitialize(1000,500);

        if (this.user == null) {
           dispose();
        }

        this.label_welcome.setText(" Welcome " + this.user.getUsername());

       loadBrandTable();

        this.table_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table_brand.rowAtPoint(e.getPoint());
                table_brand.setRowSelectionInterval(selected_row,selected_row);
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
            int selectBrandId = Integer.parseInt(table_brand.getValueAt(table_brand.getSelectedRow(), 0).toString());
            BrandGUI brandGUI = new BrandGUI(this.brandManager.getById(selectBrandId));
            brandGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.brandMenu.add("Delete").addActionListener(e -> {

        });

        this.table_brand.setComponentPopupMenu(this.brandMenu);
    }

    public void loadBrandTable() {
        Object[] column_brand = {"Brand ID" , "Brand Name"};

        ArrayList<Brand> brandList = this.brandManager.findAll();
        this.tableModel_brand.setColumnIdentifiers(column_brand);



        this.table_brand.setModel(this.tableModel_brand);

        this.table_brand.getTableHeader().setReorderingAllowed(false);
        this.table_brand.setEnabled(false);

        DefaultTableModel clearModel = (DefaultTableModel) this.table_brand.getModel();
        clearModel.setRowCount(0);
        for (Brand brand : brandList) {
            Object[] objects = {brand.getId(), brand.getName()};
            this.tableModel_brand.addRow(objects);
        }

    }

}
