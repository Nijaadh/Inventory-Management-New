/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.lowagie.text.Row;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.Cell;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jxl.Sheet;
import net.proteanit.sql.DbUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author mohom
 */
public class ViewInventory extends javax.swing.JFrame {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    String logedUser = null;

    public ViewInventory(String user) {
        initComponents();
        conn = DatabaseConnection.connection();
        logedUser = user;
        showRecodsOrderItems();
    }

    public void exportToCSV(JTable table, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

            // Write table headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                csvPrinter.print(table.getColumnName(i));
            }
            csvPrinter.println();

            // Write table data
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int column = 0; column < table.getColumnCount(); column++) {
                    Object value = table.getValueAt(row, column);
                    if (value != null) {
                        csvPrinter.print(value.toString());
                    } else {
                        csvPrinter.print("");
                    }
                }
                csvPrinter.println();
            }

            System.out.println("CSV file exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ViewInventory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void showRecodsOrderItems() {
        try {

            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM maininventory";
            rs = stmt.executeQuery(sqlSelect);
            tblInventory.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelViewInventory = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        txtItemName = new javax.swing.JTextField();
        txtCompany = new javax.swing.JTextField();
        txtCat = new javax.swing.JTextField();
        jPanel65 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        lblComId = new javax.swing.JLabel();
        lblComName = new javax.swing.JLabel();
        lblItemId = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        lblCatagory = new javax.swing.JLabel();
        lblUnit = new javax.swing.JLabel();
        lblDiscount = new javax.swing.JLabel();
        lblUnitCost = new javax.swing.JLabel();
        lblRetailPrice = new javax.swing.JLabel();
        lblTotalCost = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        lblTotQty = new javax.swing.JLabel();
        lblShopMx = new javax.swing.JLabel();
        lblStore1Mx = new javax.swing.JLabel();
        txtShopAv = new javax.swing.JTextField();
        txtStore1Av = new javax.swing.JTextField();
        txtStore2Av = new javax.swing.JTextField();
        jPanel68 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        lblLastGrn = new javax.swing.JLabel();
        lblLastFreeIsuue = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        lblLastAdedQty = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        lblReOrdr = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventory = new javax.swing.JTable();
        btnPrint = new javax.swing.JLabel();
        btnUpdateButton = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(41, 51, 80));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MLT HOLDINGS PVT LTD MAIN INVENTORY");

        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_multiply_39px.png"))); // NOI18N
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblExitMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblExitMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(610, 610, 610)
                .addComponent(lblExit))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel1)
            .addComponent(lblExit)
        );

        jPanel64.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel32.setText("Company :");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel36.setText("Item Name :");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel44.setText("Catagory :");

        jPanel66.setBackground(new java.awt.Color(41, 51, 80));

        jPanel67.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        jLabel92.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Fiters");

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel66Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtItemName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemNameKeyReleased(evt);
            }
        });

        txtCompany.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompanyKeyReleased(evt);
            }
        });

        txtCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCatActionPerformed(evt);
            }
        });
        txtCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCatKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCompany))
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCat, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel65.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setText("Item Name        :");

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel63.setText("Company Name :");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel66.setText("Catagory           :");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel67.setText("Company ID      :");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel68.setText("Item ID              :");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel69.setText("Unit                   :");

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel70.setText("Discount    :");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel71.setText("Unit Cost    :");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel72.setText("Retail Price :");

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel73.setText("Total Cost   :");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel74.setText("Total Value :");

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel75.setText("Store1 Av :");

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel76.setText("Shop Av   :");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel77.setText("Store2 Av :");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel78.setText("Total Qty  :");

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel79.setText("Shop Mx :");

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel80.setText("Store1 Mx :");

        lblComId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblComId.setForeground(new java.awt.Color(51, 51, 255));
        lblComId.setText("Sup-00001");

        lblComName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblComName.setForeground(new java.awt.Color(51, 51, 255));
        lblComName.setText("Natures Secrets");

        lblItemId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblItemId.setForeground(new java.awt.Color(51, 51, 255));
        lblItemId.setText("Itm-00001");

        lblItemName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblItemName.setForeground(new java.awt.Color(51, 51, 255));
        lblItemName.setText("NS PAPAYA FW 100ml");

        lblCatagory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCatagory.setForeground(new java.awt.Color(51, 51, 255));
        lblCatagory.setText("FW");

        lblUnit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUnit.setForeground(new java.awt.Color(51, 51, 255));
        lblUnit.setText("100ml");

        lblDiscount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDiscount.setForeground(new java.awt.Color(0, 153, 153));
        lblDiscount.setText("15");

        lblUnitCost.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUnitCost.setForeground(new java.awt.Color(0, 153, 153));
        lblUnitCost.setText("332.65");

        lblRetailPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRetailPrice.setForeground(new java.awt.Color(0, 153, 153));
        lblRetailPrice.setText("345.00");

        lblTotalCost.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTotalCost.setForeground(new java.awt.Color(0, 153, 153));
        lblTotalCost.setText("12000");

        lblTotalValue.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTotalValue.setForeground(new java.awt.Color(0, 153, 153));
        lblTotalValue.setText("17000");

        lblTotQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTotQty.setForeground(new java.awt.Color(102, 0, 153));
        lblTotQty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotQty.setText("6");

        lblShopMx.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblShopMx.setForeground(new java.awt.Color(102, 0, 153));
        lblShopMx.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShopMx.setText("10");

        lblStore1Mx.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblStore1Mx.setForeground(new java.awt.Color(102, 0, 153));
        lblStore1Mx.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStore1Mx.setText("60");

        txtShopAv.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtShopAv.setForeground(new java.awt.Color(255, 51, 51));
        txtShopAv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtShopAv.setText("5");

        txtStore1Av.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtStore1Av.setForeground(new java.awt.Color(255, 51, 51));
        txtStore1Av.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStore1Av.setText("10");

        txtStore2Av.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtStore2Av.setForeground(new java.awt.Color(255, 51, 51));
        txtStore2Av.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStore2Av.setText("44");

        jPanel68.setBackground(new java.awt.Color(41, 51, 80));

        jPanel69.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        jLabel93.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("Item Full Details ");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(389, 389, 389)
                .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel93))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel82.setText("Last Grn No       :");

        lblLastGrn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLastGrn.setForeground(new java.awt.Color(51, 51, 255));
        lblLastGrn.setText("100ml");

        lblLastFreeIsuue.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLastFreeIsuue.setForeground(new java.awt.Color(0, 153, 153));
        lblLastFreeIsuue.setText("332.65");

        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel83.setText("Last Free Issue:");

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel84.setText("Last Added Qty    :");

        lblLastAdedQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLastAdedQty.setForeground(new java.awt.Color(0, 153, 153));
        lblLastAdedQty.setText("15");

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel85.setText("Re Order Leve :");

        lblReOrdr.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblReOrdr.setForeground(new java.awt.Color(102, 0, 153));
        lblReOrdr.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReOrdr.setText("60");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblComId, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblComName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblItemId, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCatagory, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel65Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblLastGrn, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(51, 51, 51)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRetailPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUnitCost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalCost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(lblLastFreeIsuue, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                        .addComponent(jLabel84)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(lblLastAdedQty, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel65Layout.createSequentialGroup()
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                                        .addComponent(txtShopAv, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addComponent(txtStore1Av, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtStore2Av, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotQty, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblShopMx, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblStore1Mx, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblReOrdr, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50))))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addComponent(txtShopAv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStore1Av, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStore2Av, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(jLabel78)
                            .addComponent(lblItemName)
                            .addComponent(lblTotQty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(jLabel79)
                            .addComponent(lblCatagory)
                            .addComponent(lblShopMx))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel69)
                                .addComponent(jLabel80)
                                .addComponent(lblStore1Mx))
                            .addComponent(lblUnit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel82)
                            .addComponent(lblLastGrn)))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel67)
                                .addComponent(jLabel76)
                                .addComponent(lblComId)
                                .addComponent(lblDiscount)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel63)
                                    .addComponent(jLabel75)
                                    .addComponent(lblComName))
                                .addGroup(jPanel65Layout.createSequentialGroup()
                                    .addComponent(lblLastAdedQty)
                                    .addGap(7, 7, 7)))
                            .addComponent(jLabel84))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel65Layout.createSequentialGroup()
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabel77))
                                .addGap(127, 127, 127)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblReOrdr)
                                    .addComponent(jLabel85)))
                            .addComponent(lblItemId)
                            .addGroup(jPanel65Layout.createSequentialGroup()
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel83)
                                    .addComponent(lblLastFreeIsuue))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel71)
                                    .addComponent(lblUnitCost))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel72)
                                    .addComponent(lblRetailPrice))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel73)
                                    .addComponent(lblTotalCost))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel74)
                                    .addComponent(lblTotalValue))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblInventory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ItemId", "Last Grn No", "Company", "Item Name", "Catagory", "Unit", "Last free Issue", "Discount", "last Aded Qty", "Unit Cost", "retail price", "total Cost", "Total value", "Total Qty", "Shop Av", "Store Av", "Store2"
            }
        ));
        tblInventory.setRowHeight(30);
        tblInventory.setRowMargin(5);
        tblInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInventoryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInventory);

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/Print button.png"))); // NOI18N
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        btnUpdateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/Update button.png"))); // NOI18N
        btnUpdateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelViewInventoryLayout = new javax.swing.GroupLayout(panelViewInventory);
        panelViewInventory.setLayout(panelViewInventoryLayout);
        panelViewInventoryLayout.setHorizontalGroup(
            panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewInventoryLayout.createSequentialGroup()
                .addGroup(panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViewInventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelViewInventoryLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(btnUpdateButton)
                                .addGap(68, 68, 68)
                                .addComponent(btnPrint)))
                        .addGap(29, 29, 29)
                        .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panelViewInventoryLayout.setVerticalGroup(
            panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViewInventoryLayout.createSequentialGroup()
                        .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrint)
                            .addComponent(btnUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panelViewInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelViewInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1721, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMousePressed

        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_multiply_39px_1.png"))); // NOI18N


    }//GEN-LAST:event_lblExitMousePressed

    private void lblExitMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseReleased

        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_multiply_39px.png"))); // NOI18N

    }//GEN-LAST:event_lblExitMouseReleased

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked

        this.dispose();
        DashBoardForm db = new DashBoardForm(logedUser);
        db.setVisible(true);

    }//GEN-LAST:event_lblExitMouseClicked

    private void tblInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInventoryMouseClicked

        DefaultTableModel tblModel = (DefaultTableModel) tblInventory.getModel();
        int selectedRowCount = tblInventory.getSelectedRowCount();
        if (selectedRowCount > 1) {
            JOptionPane.showMessageDialog(null, "Select a Single Row...");
        } else {
            int selectedRow = tblInventory.getSelectedRow();
            String itemId = (String) tblInventory.getValueAt(selectedRow, 0);
            String grnNo = (String) tblInventory.getValueAt(selectedRow, 1);
            String comapany = (String) tblInventory.getValueAt(selectedRow, 2);
            String itemName = (String) tblInventory.getValueAt(selectedRow, 3);
            String catagory = (String) tblInventory.getValueAt(selectedRow, 4);
            String unit = (String) tblInventory.getValueAt(selectedRow, 5);
            String freeIssue = (String) tblInventory.getValueAt(selectedRow, 6);
            String Discount = (String) tblInventory.getValueAt(selectedRow, 7);
            String lastAddedQty = (String) tblInventory.getValueAt(selectedRow, 8);
            String unitCost = (String) tblInventory.getValueAt(selectedRow, 9);
            String retail = (String) tblInventory.getValueAt(selectedRow, 10);
            String totalCost = (String) tblInventory.getValueAt(selectedRow, 11);
            String totalValue = (String) tblInventory.getValueAt(selectedRow, 12);
            String totalQty = (String) tblInventory.getValueAt(selectedRow, 13);
            String shopAv = (String) tblInventory.getValueAt(selectedRow, 14);
            String store1Av = (String) tblInventory.getValueAt(selectedRow, 15);
            String store2Av = (String) tblInventory.getValueAt(selectedRow, 16);

            lblItemId.setText(itemId);
            lblLastGrn.setText(grnNo);
            lblComName.setText(comapany);
            lblItemName.setText(itemName);
            lblCatagory.setText(catagory);
            lblUnit.setText(unit);
            lblLastFreeIsuue.setText(freeIssue);
            lblDiscount.setText(Discount);
            lblLastAdedQty.setText(lastAddedQty);
            lblUnitCost.setText(unitCost);
            lblRetailPrice.setText(retail);
            lblTotalCost.setText(totalCost);
            lblTotalValue.setText(totalValue);
            lblTotQty.setText(totalQty);
            txtShopAv.setText(shopAv);
            txtStore1Av.setText(store1Av);
            txtStore2Av.setText(store2Av);

            try {
                stmt = conn.createStatement();
                String sql = "SELECT * FROM items WHERE itemId='" + itemId + "'";
                rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    String comId = rs.getString("itemId");
                    String shopMx = rs.getString("shopMax");
                    String Store1Mx = rs.getString("storeMax");
                    String Store2Mx = rs.getString("itemId");
                    String reorder = rs.getString("reOrderLevel");

                    lblComId.setText(comId);
                    lblShopMx.setText(shopMx);
                    lblStore1Mx.setText(Store1Mx);

                    lblReOrdr.setText(reorder);

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cant get value From Items..");
            }

        }

    }//GEN-LAST:event_tblInventoryMouseClicked

    private void txtCatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCatKeyReleased

        //        try {
            //            if (txtCat.getText().isEmpty() && txtItemName.getText().isEmpty() && txtCompany.getText().isEmpty()) {
                //                showRecodsOrderItems();
                //            } else {
                //                String cat = txtCat.getText();
                //                String itm = txtItemName.getText();
                //                String com = txtCompany.getText();
                //
                //                if (cat.equals("")) {
                    //
                    //                    try {
                        //                        stmt = conn.createStatement();
                        //                        String sql = "SELECT * FROM maininventory ";
                        //                        rs = stmt.executeQuery(sql);
                        //                        if (rs.next()) {
                            //                            stmt = conn.createStatement();
                            //                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' AND  company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            //                            ResultSet res = stmt.executeQuery(sqlSelect);
                            //                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                            //                        } else {
                            //                            showRecodsOrderItems();
                            //                        }
                        //
                        //                    } catch (Exception e) {
                        //                        JOptionPane.showMessageDialog(this, e);
                        //                    }
                    //                } else if (itm.equals("")) {
                    //                    try {
                        //                        stmt = conn.createStatement();
                        //                        String sql = "SELECT * FROM maininventory ";
                        //                        rs = stmt.executeQuery(sql);
                        //                        if (rs.next()) {
                            //                            stmt = conn.createStatement();
                            //                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' AND company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            //                            ResultSet res = stmt.executeQuery(sqlSelect);
                            //                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                            //                        } else {
                            //                            showRecodsOrderItems();
                            //                        }
                        //
                        //                    } catch (Exception e) {
                        //                        JOptionPane.showMessageDialog(this, e);
                        //                    }
                    //
                    //                } else if (com.equals("")) {
                    //
                    //                    try {
                        //                        stmt = conn.createStatement();
                        //                        String sql = "SELECT * FROM maininventory ";
                        //                        rs = stmt.executeQuery(sql);
                        //                        if (rs.next()) {
                            //                            stmt = conn.createStatement();
                            //                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' AND itemName LIKE '%" + itm + "%'";   //'" + userName + "';";
                            //                            ResultSet res = stmt.executeQuery(sqlSelect);
                            //                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                            //                        } else {
                            //                            showRecodsOrderItems();
                            //                        }
                        //
                        //                    } catch (Exception e) {
                        //                        JOptionPane.showMessageDialog(this, e);
                        //                    }
                    //                }
                //
                //            }
            //        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Not found any Item on That Name");
            //        }
        try {
            if (txtCat.getText().isEmpty() && txtItemName.getText().isEmpty() && txtCompany.getText().isEmpty()) {
                showRecodsOrderItems();
            } else {

                String itm = txtItemName.getText();
                String com = txtCompany.getText();
                String cat = txtCat.getText();

                if (com.equals("") && itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (com.equals("") && !itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (com.equals("") && !itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && !itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND itemName LIKE '%" + itm + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && !itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND itemName LIKE '%" + itm + "%' AND AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any Item ...");
        }

        //String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' AND itemName LIKE '%" + itm + "%' AND  company LIKE '%" + com + "%' ";
    }//GEN-LAST:event_txtCatKeyReleased

    private void txtCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCatActionPerformed

    private void txtCompanyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyKeyReleased

        //        try {
            //            if (txtCompany.getText().isEmpty()) {
                //                showRecodsOrderItems();
                //            } else {
                //                String com = txtCompany.getText();
                //                try {
                    //                    stmt = conn.createStatement();
                    //                    String sql = "SELECT * FROM maininventory ";
                    //                    rs = stmt.executeQuery(sql);
                    //                    if (rs.next()) {
                        //                        stmt = conn.createStatement();
                        //                        String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%'";   //'" + userName + "';";
                        //                        ResultSet res = stmt.executeQuery(sqlSelect);
                        //                        tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        //                    } else {
                        //                        showRecodsOrderItems();
                        //                    }
                    //
                    //                } catch (Exception e) {
                    //                    JOptionPane.showMessageDialog(this, e);
                    //                }
                //            }
            //        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Not found any Company on that Name");
            //        }
        try {
            if (txtCat.getText().isEmpty() && txtItemName.getText().isEmpty() && txtCompany.getText().isEmpty()) {
                showRecodsOrderItems();
            } else {

                String itm = txtItemName.getText();
                String com = txtCompany.getText();
                String cat = txtCat.getText();

                if (com.equals("") && itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (com.equals("") && !itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (com.equals("") && !itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && !itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND itemName LIKE '%" + itm + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && !itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND itemName LIKE '%" + itm + "%' AND AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any Item ...");
        }
    }//GEN-LAST:event_txtCompanyKeyReleased

    private void txtItemNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemNameKeyReleased

        //        try {
            //            if (txtItemName.getText().isEmpty()) {
                //                showRecodsOrderItems();
                //            } else {
                //                String itm = txtItemName.getText();
                //                try {
                    //                    stmt = conn.createStatement();
                    //                    String sql = "SELECT * FROM maininventory ";
                    //                    rs = stmt.executeQuery(sql);
                    //                    if (rs.next()) {
                        //                        stmt = conn.createStatement();
                        //                        String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%'";   //'" + userName + "';";
                        //                        ResultSet res = stmt.executeQuery(sqlSelect);
                        //                        tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        //                    } else {
                        //                        showRecodsOrderItems();
                        //                    }
                    //
                    //                } catch (Exception e) {
                    //                    JOptionPane.showMessageDialog(this, e);
                    //                }
                //            }
            //        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Not found any Item on That Name");
            //        }
        //        try {
            //            if (txtCat.getText().isEmpty() && txtItemName.getText().isEmpty() && txtCompany.getText().isEmpty()) {
                //                showRecodsOrderItems();
                //            } else {
                //                String cat = txtCat.getText();
                //                String itm = txtItemName.getText();
                //                String com = txtCompany.getText();
                //
                //                if (cat.equals("")) {
                    //
                    //                    try {
                        //                        stmt = conn.createStatement();
                        //                        String sql = "SELECT * FROM maininventory ";
                        //                        rs = stmt.executeQuery(sql);
                        //                        if (rs.next()) {
                            //                            stmt = conn.createStatement();
                            //                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' AND  company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            //                            ResultSet res = stmt.executeQuery(sqlSelect);
                            //                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                            //                        } else {
                            //                            showRecodsOrderItems();
                            //                        }
                        //
                        //                    } catch (Exception e) {
                        //                        JOptionPane.showMessageDialog(this, e);
                        //                    }
                    //                } else if (itm.equals("")) {
                    //                    try {
                        //                        stmt = conn.createStatement();
                        //                        String sql = "SELECT * FROM maininventory ";
                        //                        rs = stmt.executeQuery(sql);
                        //                        if (rs.next()) {
                            //                            stmt = conn.createStatement();
                            //                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' AND company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            //                            ResultSet res = stmt.executeQuery(sqlSelect);
                            //                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                            //                        } else {
                            //                            showRecodsOrderItems();
                            //                        }
                        //
                        //                    } catch (Exception e) {
                        //                        JOptionPane.showMessageDialog(this, e);
                        //                    }
                    //
                    //                } else if (com.equals("")) {
                    //
                    //                    try {
                        //                        stmt = conn.createStatement();
                        //                        String sql = "SELECT * FROM maininventory ";
                        //                        rs = stmt.executeQuery(sql);
                        //                        if (rs.next()) {
                            //                            stmt = conn.createStatement();
                            //                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' AND itemName LIKE '%" + itm + "%'";   //'" + userName + "';";
                            //                            ResultSet res = stmt.executeQuery(sqlSelect);
                            //                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                            //                        } else {
                            //                            showRecodsOrderItems();
                            //                        }
                        //
                        //                    } catch (Exception e) {
                        //                        JOptionPane.showMessageDialog(this, e);
                        //                    }
                    //                }
                //
                //            }
            //        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(null, "Not found any Item on That Name");
            //        }
        try {
            if (txtCat.getText().isEmpty() && txtItemName.getText().isEmpty() && txtCompany.getText().isEmpty()) {
                showRecodsOrderItems();
            } else {

                String itm = txtItemName.getText();
                String com = txtCompany.getText();
                String cat = txtCat.getText();

                if (com.equals("") && itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE catagory LIKE '%" + cat + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (com.equals("") && !itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (com.equals("") && !itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE itemName LIKE '%" + itm + "%' AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' ";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && !itm.equals("") && cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND itemName LIKE '%" + itm + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                } else if (!com.equals("") && !itm.equals("") && !cat.equals("")) {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM maininventory ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM maininventory WHERE company LIKE '%" + com + "%' AND itemName LIKE '%" + itm + "%' AND AND catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tblInventory.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecodsOrderItems();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any Item ...");
        }
    }//GEN-LAST:event_txtItemNameKeyReleased

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        
        try {
            tblInventory.print();
        } catch (PrinterException ex) {
            Logger.getLogger(ViewInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnPrintMouseClicked

    private void btnUpdateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateButtonMouseClicked
        
        if (logedUser == "User") {

        }

        if (txtShopAv.getText().equals("") || txtStore1Av.getText().equals("") || txtStore2Av.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter proper values to Update Main Inventory....");
        } else {

            int shopAv = Integer.parseInt(txtShopAv.getText());
            int store1Av = Integer.parseInt(txtStore1Av.getText());
            int store2Av = Integer.parseInt(txtStore2Av.getText());
            int shopMx = Integer.parseInt(lblShopMx.getText());
            int store1Mx = Integer.parseInt(lblStore1Mx.getText());

            if (shopAv > shopMx || store1Av > store1Mx) {
                JOptionPane.showMessageDialog(null, "Maximum Level Exceded..");
            } else {
                int totQty = shopAv + store1Av + store2Av;

                double unitCost = Double.parseDouble(lblUnitCost.getText());
                double retail = Double.parseDouble(lblRetailPrice.getText());

                double newTOTcost = unitCost * totQty;
                double newTOTvalue = retail * totQty;

                try {
                    stmt = conn.createStatement();
                    String sql = "UPDATE maininventory SET totalValue='" + newTOTvalue + "', totalCost='" + newTOTcost + "', shopAvailable='" + shopAv + "', store1Available='" + store1Av + "', store2Available='" + store2Av + "'";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Data Updated Successfully...");
                    showRecodsOrderItems();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Can't update data..." + e);
                }

            }

        }

        
    }//GEN-LAST:event_btnUpdateButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewInventory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnPrint;
    private javax.swing.JLabel btnUpdateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCatagory;
    private javax.swing.JLabel lblComId;
    private javax.swing.JLabel lblComName;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblItemId;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblLastAdedQty;
    private javax.swing.JLabel lblLastFreeIsuue;
    private javax.swing.JLabel lblLastGrn;
    private javax.swing.JLabel lblReOrdr;
    private javax.swing.JLabel lblRetailPrice;
    private javax.swing.JLabel lblShopMx;
    private javax.swing.JLabel lblStore1Mx;
    private javax.swing.JLabel lblTotQty;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JLabel lblUnit;
    private javax.swing.JLabel lblUnitCost;
    private javax.swing.JPanel panelViewInventory;
    private javax.swing.JTable tblInventory;
    private javax.swing.JTextField txtCat;
    private javax.swing.JTextField txtCompany;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtShopAv;
    private javax.swing.JTextField txtStore1Av;
    private javax.swing.JTextField txtStore2Av;
    // End of variables declaration//GEN-END:variables
}
