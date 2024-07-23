/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.omg.CORBA.DoubleSeqHelper;

/**
 *
 * @author mohom
 */
public class Invoice extends javax.swing.JFrame {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    double totBillCost = 0.00;
    double totBillValue = 0.00;
    double grossValue = 0.00;
    double netValue = 0.00;
    double finalDiscount = 0;

    String LogUser = null;

    public Invoice(String usr) {

        LogUser = usr;
        conn = DatabaseConnection.connection();

        initComponents();
        item();
        dt();
        times();
        customer();
        PayId();

        comboItemAutoComplete();
        comboCustomerAutoComplete();

        lblTotalValue.setText(String.valueOf(totBillValue));
        lblBillCost.setText(String.valueOf(totBillCost));
        lblGrossValue.setText(String.valueOf(grossValue));
        lblNetValue.setText(String.valueOf(netValue));
        lblCurrentUser.setText(usr);
    }

    private Invoice() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void bill() {
txtBill.setText("");
        String totalva = lblTotalValue.getText();
        String netva = lblNetValue.getText();
        String grosva = lblGrossValue.getText();
        String FiDiscount = String.valueOf(finalDiscount);
        String cash = txtCash.getText();
        String blnce = lblBalance.getText();

        String paymenttype = "Cash";
        if (chkBoxCard.isSelected()) {
            paymenttype = "Card";
        }
        String name = lblCusName.getText();
        String payId = lblPaymentId.getText();
        String cashier = lblCurrentUser.getText();
        String dt = lblDateToday.getText();

        DefaultTableModel tblModel = (DefaultTableModel) tblInvoice.getModel();
        int rowCount = tblModel.getRowCount();

        txtBill.setText(txtBill.getText() + "******************************************************************\n");
        txtBill.setText(txtBill.getText() + "                                              MLT (HOLDINGS PVT LTD)" + "         " + "\t" + dt + "\t" + "\n");
        txtBill.setText(txtBill.getText() + "******************************************************************\n");
        txtBill.setText(txtBill.getText() + "                                                  \n");
        txtBill.setText(txtBill.getText() + "      " + "Product" + "\t" + "\t" + "Price" + "\t" + "QTY" + "\t" + "Discount" + "\t" + "Value" + "\n");

        for (int i = 0; i < rowCount; i++) {
            String pName = (String) tblModel.getValueAt(i, 0);
            String pPrice = (String) tblModel.getValueAt(i, 1);
            String pQty = (String) tblModel.getValueAt(i, 2);
            String pDiscount = (String) tblModel.getValueAt(i, 3);
            String pValue = (String) tblModel.getValueAt(i, 4);

            txtBill.setText(txtBill.getText() + "  " + pName + "\t" + pPrice + "\t" + pQty + "\t" + pDiscount + "\t" + pValue + "\n");

        }
        txtBill.setText(txtBill.getText() + "----------------------------------------------------------------------------------------------------------\n");
        txtBill.setText(txtBill.getText() + "                                                  \n");
        txtBill.setText(txtBill.getText() + "  " + "Total Value:" + "\t" + "\t" + "\t" + "\t" + "\t" + totalva + "\n");
        txtBill.setText(txtBill.getText() + "  " + "Gross Value:" + "\t" + "\t" + "\t" + "\t" + "\t" + grosva + "\n");
        txtBill.setText(txtBill.getText() + "  " + "DisCount:" + "\t" + "\t" + "\t" + "\t" + "\t" + FiDiscount + "\n");
        txtBill.setText(txtBill.getText() + "  " + "Net Value:" + "\t" + "\t" + "\t" + "\t" + "\t" + netva + "\n");
        txtBill.setText(txtBill.getText() + "======================================================\n");
        txtBill.setText(txtBill.getText() + "  " + "Cash:" + "\t" + "\t" + cash + "\n");
        txtBill.setText(txtBill.getText() + "  " + "Balance:" + "\t" + "\t" + blnce + "\n");
        txtBill.setText(txtBill.getText() + "                                                  \n");
        txtBill.setText(txtBill.getText() + "  " + payId + "\t" + name + "\t" + paymenttype + "\n");
        txtBill.setText(txtBill.getText() + "  " + cashier + "\n");

        txtBill.setText(txtBill.getText() + "********************************************************************\n");
        txtBill.setText(txtBill.getText() + "                                                  THANK YOU COME AGAIN!!               \n");
        txtBill.setText(txtBill.getText() + "********************************************************************\n");

    }

    public void dt() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        String dd = sdf.format(d);
        lblDateToday.setText(dd);
    }
    Timer t;
    SimpleDateFormat st;

    public void times() {
        t = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date dt = new Date();
                st = new SimpleDateFormat("hh:mm:ss a");
                String tt = st.format(dt);
                lblTime.setText(tt);
            }

        });
        t.start();
    }

    public void PayId() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select MAX(paymentId) from payment");
            rs.next();
            rs.getString("MAX(paymentId)");
            if (rs.getString("MAX(paymentId)") == null) {
                lblPaymentId.setText("MLTP-0000001");
                System.out.println("paymentId  null");
            } else {
                long id = Long.parseLong(rs.getString("MAX(paymentId)").substring(5, rs.getString("MAX(paymentId)").length()));
                id++;
                lblPaymentId.setText("MLTP-" + String.format("%07d", id));
                System.out.println("id is 00001");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    
    public void comboItemAutoComplete() {
        AutoCompleteDecorator.decorate(comboItem);
    }

    public void comboCustomerAutoComplete() {
        AutoCompleteDecorator.decorate(comboCustomer);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTop = new javax.swing.JPanel();
        panelCurrentUser = new javax.swing.JPanel();
        lblCurrentUser = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelUserIcon = new javax.swing.JPanel();
        lblUserIcon = new javax.swing.JLabel();
        panelUserIcon1 = new javax.swing.JPanel();
        lblUserIcon1 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblDateToday = new javax.swing.JLabel();
        panelUserIcon2 = new javax.swing.JPanel();
        lblUserIcon2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        comboItem = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        btnADD = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtDis = new javax.swing.JTextField();
        rdoPercentItem = new javax.swing.JRadioButton();
        rdoRupeeItem = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        lblItmValue = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblDiscount = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtQRCode = new javax.swing.JTextField();
        btnQROK = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtFinalDis = new javax.swing.JTextField();
        rdoRupeeFinal = new javax.swing.JRadioButton();
        rdoPercentFinal = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        lblNetValue = new javax.swing.JLabel();
        chkBoxCard = new javax.swing.JCheckBox();
        lblBillCost = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblBalance = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblGrossValue = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblUnitCost = new javax.swing.JLabel();
        lblRetailPrice = new javax.swing.JLabel();
        lblShopAv = new javax.swing.JLabel();
        lblStore1Av = new javax.swing.JLabel();
        lblStore2Av = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblUnit = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        comboCustomer = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblCusName = new javax.swing.JLabel();
        lblCusNic = new javax.swing.JLabel();
        lblCusPrePurchesing = new javax.swing.JLabel();
        lblPaymentId = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBill = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelTop.setBackground(new java.awt.Color(71, 120, 197));
        panelTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCurrentUser.setBackground(new java.awt.Color(123, 156, 225));

        lblCurrentUser.setBackground(new java.awt.Color(123, 156, 225));
        lblCurrentUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCurrentUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCurrentUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCurrentUser.setText("Makuwa");
        lblCurrentUser.setToolTipText("Current User");

        javax.swing.GroupLayout panelCurrentUserLayout = new javax.swing.GroupLayout(panelCurrentUser);
        panelCurrentUser.setLayout(panelCurrentUserLayout);
        panelCurrentUserLayout.setHorizontalGroup(
            panelCurrentUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCurrentUserLayout.createSequentialGroup()
                .addComponent(lblCurrentUser, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCurrentUserLayout.setVerticalGroup(
            panelCurrentUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCurrentUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelTop.add(panelCurrentUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1610, 20, -1, 30));

        jLabel12.setBackground(new java.awt.Color(71, 120, 197));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Date :");
        panelTop.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 120, 51));

        jLabel13.setBackground(new java.awt.Color(71, 120, 197));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Invoce");
        panelTop.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 51));

        jLabel14.setBackground(new java.awt.Color(71, 120, 197));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Time :");
        panelTop.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 120, 51));

        panelUserIcon.setBackground(new java.awt.Color(71, 120, 197));

        lblUserIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/user.png"))); // NOI18N

        javax.swing.GroupLayout panelUserIconLayout = new javax.swing.GroupLayout(panelUserIcon);
        panelUserIcon.setLayout(panelUserIconLayout);
        panelUserIconLayout.setHorizontalGroup(
            panelUserIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserIconLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserIcon)
                .addContainerGap())
        );
        panelUserIconLayout.setVerticalGroup(
            panelUserIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        panelTop.add(panelUserIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1780, 20, -1, 30));

        panelUserIcon1.setBackground(new java.awt.Color(71, 120, 197));

        lblUserIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_double_left_32px_1.png"))); // NOI18N
        lblUserIcon1.setToolTipText("Exit");
        lblUserIcon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserIcon1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUserIcon1Layout = new javax.swing.GroupLayout(panelUserIcon1);
        panelUserIcon1.setLayout(panelUserIcon1Layout);
        panelUserIcon1Layout.setHorizontalGroup(
            panelUserIcon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserIcon1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserIcon1)
                .addContainerGap())
        );
        panelUserIcon1Layout.setVerticalGroup(
            panelUserIcon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserIcon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelTop.add(panelUserIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1880, 20, -1, -1));

        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setText("10:00:42 PM");
        panelTop.add(lblTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 200, 30));

        lblDateToday.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDateToday.setForeground(new java.awt.Color(255, 255, 255));
        lblDateToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDateToday.setText("2023-Jun-15");
        panelTop.add(lblDateToday, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 160, 30));

        panelUserIcon2.setBackground(new java.awt.Color(71, 120, 197));

        lblUserIcon2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_exit_32px.png"))); // NOI18N
        lblUserIcon2.setToolTipText("Exit");
        lblUserIcon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserIcon2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUserIcon2Layout = new javax.swing.GroupLayout(panelUserIcon2);
        panelUserIcon2.setLayout(panelUserIcon2Layout);
        panelUserIcon2Layout.setHorizontalGroup(
            panelUserIcon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserIcon2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserIcon2)
                .addContainerGap())
        );
        panelUserIcon2Layout.setVerticalGroup(
            panelUserIcon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserIcon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelTop.add(panelUserIcon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1830, 20, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Item Name :");

        comboItem.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        comboItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboItemActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Qnty :");

        txtQty.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        btnADD.setText("Add");
        btnADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADDActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Dis :");

        txtDis.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtDis.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtDisInputMethodTextChanged(evt);
            }
        });
        txtDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDisKeyReleased(evt);
            }
        });

        rdoPercentItem.setText("Percent");
        rdoPercentItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPercentItemActionPerformed(evt);
            }
        });

        rdoRupeeItem.setSelected(true);
        rdoRupeeItem.setText("Rupee");
        rdoRupeeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRupeeItemActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setText("Value :");

        lblItmValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblItmValue.setForeground(new java.awt.Color(255, 0, 0));
        lblItmValue.setText("Rs 0.00");

        jPanel5.setBackground(new java.awt.Color(41, 51, 80));

        jPanel6.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Select Item");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("Discount :");

        lblDiscount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDiscount.setForeground(new java.awt.Color(0, 153, 153));
        lblDiscount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiscount.setText("Rs 0.00");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setText("Qr Code:");

        txtQRCode.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtQRCode.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtQRCodeInputMethodTextChanged(evt);
            }
        });
        txtQRCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQRCodeKeyReleased(evt);
            }
        });

        btnQROK.setText("OK");
        btnQROK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQROKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comboItem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnADD, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblDiscount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblItmValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                        .addComponent(txtDis, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(rdoPercentItem)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(80, 80, 80)
                                            .addComponent(rdoRupeeItem))
                                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtQty)))
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnQROK, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQROK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDis, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoRupeeItem)
                    .addComponent(rdoPercentItem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblItmValue, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnADD, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Net value :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Cash :");

        txtCash.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtCash.setForeground(new java.awt.Color(0, 204, 204));
        txtCash.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCash.setText("0.00");
        txtCash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCashKeyReleased(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setText("Total Value :");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel25.setText("Dis :");

        txtFinalDis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtFinalDis.setForeground(new java.awt.Color(0, 153, 0));
        txtFinalDis.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFinalDisInputMethodTextChanged(evt);
            }
        });
        txtFinalDis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFinalDisKeyReleased(evt);
            }
        });

        rdoRupeeFinal.setText("Rupee");
        rdoRupeeFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRupeeFinalActionPerformed(evt);
            }
        });

        rdoPercentFinal.setSelected(true);
        rdoPercentFinal.setText("Percent");
        rdoPercentFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPercentFinalActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setText("Balance :");

        jPanel7.setBackground(new java.awt.Color(41, 51, 80));

        jPanel8.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Calculate the bill");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalValue.setText("0.00");

        lblNetValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNetValue.setForeground(new java.awt.Color(255, 51, 51));
        lblNetValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNetValue.setText("0.00");

        chkBoxCard.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chkBoxCard.setText("Card");

        lblBillCost.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblBillCost.setForeground(new java.awt.Color(0, 204, 102));
        lblBillCost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBillCost.setText("0.00");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel31.setText("Bill Cost:");

        lblBalance.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblBalance.setForeground(new java.awt.Color(0, 153, 102));
        lblBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBalance.setText("0.00");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel36.setText("Gross Value :");

        lblGrossValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblGrossValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGrossValue.setText("0.00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkBoxCard))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtFinalDis, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rdoPercentFinal)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rdoRupeeFinal))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNetValue, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBillCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGrossValue, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBillCost, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGrossValue, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFinalDis, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoRupeeFinal)
                    .addComponent(rdoPercentFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNetValue, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBoxCard))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBalance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Itm :");

        lblItemName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("Cost :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("Retail price:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setText("Shop Av :");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setText("Store 1 Av :");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setText("Store 2 Av :");

        lblUnitCost.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblUnitCost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblRetailPrice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblRetailPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblShopAv.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblShopAv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblStore1Av.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblStore1Av.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblStore2Av.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblStore2Av.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel9.setBackground(new java.awt.Color(41, 51, 80));

        jPanel10.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Item Details");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("Unit :");

        lblUnit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblUnit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(44, 44, 44)
                        .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(1, 1, 1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addGap(25, 25, 25)))))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(lblStore2Av, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblStore1Av, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblShopAv, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUnitCost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblUnit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(jLabel9))
                        .addGap(19, 19, 19))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblShopAv, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStore1Av, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStore2Av, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setText("Refresh");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel13.setBackground(new java.awt.Color(41, 51, 80));

        jPanel14.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Select the Customer");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        comboCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCustomerActionPerformed(evt);
            }
        });

        jButton5.setText("Payment");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Name:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Nic No:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Perchused:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setText("Payment ID:");

        lblCusName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCusName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblCusNic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCusNic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblCusPrePurchesing.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCusPrePurchesing.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPaymentId.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblPaymentId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(lblCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPaymentId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel28))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCusPrePurchesing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCusNic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCusNic, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCusPrePurchesing, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPaymentId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Customer Bill :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))))
        );

        tblInvoice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Unit Price", "Qty", "Discount", "Value"
            }
        ));
        tblInvoice.setRowHeight(35);
        tblInvoice.setRowMargin(2);
        tblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInvoice);

        txtBill.setColumns(20);
        txtBill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBill.setRows(5);
        jScrollPane2.setViewportView(txtBill);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, 1940, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1940, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void item() {

        //String company = (String) comboCompanyName.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory;";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String item = rs.getString("itemName");

                boolean exists = false;
                for (int i = 0; i < comboItem.getItemCount(); i++) {
                    if (item.equals(comboItem.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboItem.addItem(item);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void customer() {

        //String company = (String) comboCompanyName.getSelectedItem();
        

try {
    stmt = conn.createStatement();
    String sql = "SELECT * FROM customer;";
    rs = stmt.executeQuery(sql);

    while (rs.next()) {
        String cus = rs.getString("cusId");

        boolean exists = false;
        for (int i = 0; i < comboCustomer.getItemCount(); i++) {
            if (cus.equals(comboCustomer.getItemAt(i))) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            comboCustomer.addItem(cus);
        }
        
       
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
} 
    }


    private void lblUserIcon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserIcon1MouseClicked

        if (tblInvoice.getRowCount() > 0) {
            JOptionPane.showMessageDialog(null, "Remove Selected Items before Exit");
        } else {

            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Back? ?");

            if (yesOrNo == JOptionPane.YES_OPTION) {

                DashBoardForm ds = new DashBoardForm(LogUser);
                ds.setVisible(true);
                this.setVisible(false);
            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }

        }


    }//GEN-LAST:event_lblUserIcon1MouseClicked

    private void btnADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADDActionPerformed

        String itm = (String) comboItem.getSelectedItem();
        String unit = lblUnit.getText();
        int shopAV = Integer.parseInt(lblShopAv.getText());
        String itmUnit = null;

        int qty = 0;
        double disValue = 0;
        double totvalue = 0;
        double discount = 0;
        double value = 0.00;
        if (txtDis.getText().equals("")) {
            disValue = 0;

        } else {
            disValue = Double.parseDouble(txtDis.getText());
        }
        if (txtQty.getText().equals("")) {
            qty = 0;

        } else {
            qty = Integer.parseInt(txtQty.getText());
        }

        if (qty > shopAV) {
            JOptionPane.showMessageDialog(null, "Quantity is exceded than Item availability in shop..");
        } else if (qty <= 0) {
            JOptionPane.showMessageDialog(null, "Recheck the Quantity Level");
        } else {
            double retailPrice = Double.parseDouble(lblRetailPrice.getText());
            double unitcost = Double.parseDouble(lblUnitCost.getText());

            totvalue = retailPrice * qty;

            if (rdoPercentItem.isSelected()) {
                discount = totvalue * disValue / 100;

            } else if (rdoRupeeItem.isSelected()) {
                discount = disValue;

            }
            lblDiscount.setText("Rs " + String.valueOf(discount));

            //double value = totvalue - discount;
            value = (totvalue - discount);

            lblItmValue.setText("Rs " + String.valueOf(value));

            double cost = unitcost * qty;

            totBillCost = totBillCost + cost;
            totBillValue = totBillValue + totvalue;
            grossValue = grossValue + value;
            netValue = grossValue;
            lblBillCost.setText(String.valueOf(totBillCost));
            lblTotalValue.setText(String.valueOf(totBillValue));
            lblGrossValue.setText(String.valueOf(grossValue));
            lblNetValue.setText(String.valueOf(grossValue));

            int PreShopQty = 0;
            int pretotQty = 0;
            double preCost = 0;
            double preVal = 0;
            double Unitcost = 0;

            try {

                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM maininventory WHERE itemName='" + itm + "'";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {

                        PreShopQty = Integer.parseInt(rs.getString("shopAvailable"));
                        pretotQty = Integer.parseInt(rs.getString("totalQty"));
                        preCost = Double.parseDouble(rs.getString("totalCost"));
                        preVal = Double.parseDouble(rs.getString("totalValue"));
                        Unitcost = Double.parseDouble(rs.getString("unitCost"));

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cant get item details from main inventory " + e);
                }

                int nowShopQty = PreShopQty;
                int nowtotQty = pretotQty;
                double nowCost = preCost;
                double nowVal = preVal;
                double nowUnitCost = Unitcost;

                nowShopQty = nowShopQty - qty;
                nowtotQty = nowtotQty - qty;
                nowCost = nowCost - (Unitcost * qty);
                nowVal = nowVal - (retailPrice * qty);

                try {
                    stmt = conn.createStatement();
                    String sql = "UPDATE maininventory SET shopAvailable = '" + nowShopQty + "' WHERE itemName='" + itm + "';";
                    String sq2 = "UPDATE maininventory SET totalCost = '" + nowCost + "' WHERE itemName='" + itm + "';";
                    String sq3 = "UPDATE maininventory SET totalValue = '" + nowVal + "' WHERE itemName='" + itm + "';";
                    String sq4 = "UPDATE maininventory SET totalQty = '" + nowtotQty + "' WHERE itemName='" + itm + "';";

                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sq2);
                    stmt.executeUpdate(sq3);
                    stmt.executeUpdate(sq4);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cant update main inventory " + e);
                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "Cant connect with main inventory..." + e);
            }

            String price = lblRetailPrice.getText();
            String q = txtQty.getText();
            String d = String.valueOf(discount);
            String val = String.valueOf(value);
            String data[] = {itm, price, q, d, val};

            DefaultTableModel tblModel = (DefaultTableModel) tblInvoice.getModel();
            tblModel.addRow(data);
            JOptionPane.showMessageDialog(null, "item Picked Successfully...");

            comboItem.setSelectedItem(null);
            txtQty.setText("");
            txtDis.setText("");
            rdoPercentItem.setSelected(false);
            rdoRupeeItem.setSelected(true);
            lblItmValue.setText("Rs 0.00");
            lblDiscount.setText("0.00");

        }


    }//GEN-LAST:event_btnADDActionPerformed

    private void comboItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboItemActionPerformed
        String itm = (String) comboItem.getSelectedItem();
        lblItemName.setText(itm);

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemName='" + itm + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String unitcost = rs.getString("unitCost");
                String retailPrice = rs.getString("unitRetail");
                String shopAV = rs.getString("shopAvailable");
                String Store1AV = rs.getString("store1Available");
                String Store2AV = rs.getString("store2Available");
                String unit = rs.getString("unit");
                lblUnitCost.setText(unitcost);
                lblRetailPrice.setText(retailPrice);
                lblShopAv.setText(shopAV);
                lblStore1Av.setText(Store1AV);
                lblStore2Av.setText(Store2AV);
                lblUnit.setText(unit);
                item();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant get data from Main Inentory...");
        }

        String shop = lblShopAv.getText();

        if (shop.equals("0") || shop.isEmpty()) {
            btnADD.setEnabled(false);
        } else {
            btnADD.setEnabled(true);
        }


    }//GEN-LAST:event_comboItemActionPerformed

    private void rdoPercentItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPercentItemActionPerformed
        if (rdoPercentItem.isSelected()) {
            rdoRupeeItem.setSelected(false);
        }


    }//GEN-LAST:event_rdoPercentItemActionPerformed

    private void rdoRupeeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRupeeItemActionPerformed
        if (rdoRupeeItem.isSelected()) {
            rdoPercentItem.setSelected(false);
        }

    }//GEN-LAST:event_rdoRupeeItemActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        txtQty.setText("");
        txtDis.setText("");
        rdoRupeeItem.setSelected(true);
        lblItmValue.setText("Rs 0.00");
        lblDiscount.setText("0.00");
        totBillCost = 0.00;
        totBillValue = 0.00;
        grossValue = 0.00;
        lblBillCost.setText("0.00");
        lblTotalValue.setText("0.00");
        lblGrossValue.setText("");
        lblNetValue.setText("");
        txtFinalDis.setText("");
        rdoPercentFinal.setSelected(true);
        rdoRupeeFinal.setSelected(false);
        netValue = 0.00;
        lblNetValue.setText("0.00");
        txtCash.setText("0.00");
        chkBoxCard.setSelected(false);
        lblBalance.setText("0.00");
        comboCustomer.setSelectedIndex(0);


    }//GEN-LAST:event_jButton4ActionPerformed

    private void lblUserIcon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserIcon2MouseClicked

        if (tblInvoice.getRowCount() > 0) {
            JOptionPane.showMessageDialog(null, "Remove Selected Items before Exit");
        } else {
            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Back? ?");

            if (yesOrNo == JOptionPane.YES_OPTION) {

                LoginForm login = new LoginForm();
                login.setVisible(true);
                this.setVisible(false);
            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }
        }


    }//GEN-LAST:event_lblUserIcon2MouseClicked

    private void tblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseClicked

        
        int selectedRow = tblInvoice.getSelectedRow();
        String itm = (String) tblInvoice.getValueAt(selectedRow, 0);
        String Price = (String) tblInvoice.getValueAt(selectedRow, 1);
        String Qty = (String) tblInvoice.getValueAt(selectedRow, 1);
        String dis = (String) tblInvoice.getValueAt(selectedRow, 3);
        String value = (String) tblInvoice.getValueAt(selectedRow, 4);

        int qty = Integer.parseInt(Qty);
        double unitPrice = Double.parseDouble(Price);
        double disCount = Double.parseDouble(dis);
        double ammount = Double.parseDouble(value);

        comboItem.setSelectedItem(itm);
        rdoRupeeItem.setSelected(true);
        rdoPercentItem.setSelected(false);
        lblDiscount.setText(dis);
        txtDis.setText(dis);
        txtQty.setText(Qty);
        lblItmValue.setText(value);


    }//GEN-LAST:event_tblInvoiceMouseClicked

    private void txtDisInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtDisInputMethodTextChanged

        //String disValue = txtDis.getText();

    }//GEN-LAST:event_txtDisInputMethodTextChanged

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed

        int selectedRow = tblInvoice.getSelectedRow();
        String itm = (String) tblInvoice.getValueAt(selectedRow, 0);
        String Price = (String) tblInvoice.getValueAt(selectedRow, 1);
        String Quantity = (String) tblInvoice.getValueAt(selectedRow, 2);
        String dis = (String) tblInvoice.getValueAt(selectedRow, 3);
        String value = (String) tblInvoice.getValueAt(selectedRow, 4);

        int qty = Integer.parseInt(Quantity);
        double unitPrice = Double.parseDouble(Price);
        double disCount = Double.parseDouble(dis);
        double ammount = Double.parseDouble(value);

        int PreShopQty = 0;
        int pretotQty = 0;
        double preCost = 0;
        double preVal = 0;
        double Unitcost = 0;
        double retail = 0;

        int nowShopQty1 = PreShopQty;
        int nowtotQty1 = pretotQty;
        double nowCost1 = preCost;
        double nowVal1 = preVal;
        double nowUnitCost1 = Unitcost;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemName='" + itm + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                PreShopQty = Integer.parseInt(rs.getString("shopAvailable"));
                pretotQty = Integer.parseInt(rs.getString("totalQty"));
                preCost = Double.parseDouble(rs.getString("totalCost"));
                preVal = Double.parseDouble(rs.getString("totalValue"));
                Unitcost = Double.parseDouble(rs.getString("unitCost"));
                retail = Double.parseDouble(rs.getString("unitRetail"));

                nowShopQty1 = PreShopQty;
                nowtotQty1 = pretotQty;
                nowCost1 = preCost;
                nowVal1 = preVal;
                nowUnitCost1 = Unitcost;

                System.out.println(PreShopQty + "/" + pretotQty + "/" + preCost + " /" + preVal + " /" + Unitcost);
                //System.out.println(nowShopQty1 + "/" + nowtotQty1 + "/" + nowCost1 + " /" + nowVal1 + " /" + nowUnitCost1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant get data from main inventory to update.." + e);
        }

        int nowShopQty = PreShopQty;
        int nowtotQty = pretotQty;
        double nowCost = preCost;
        double nowVal = preVal;
        double nowUnitCost = Unitcost;

        nowShopQty = nowShopQty + qty;
        nowtotQty = nowtotQty + qty;
        nowCost = nowCost + (Unitcost * qty);
        nowVal = nowVal + (retail * qty);

        System.out.println(nowShopQty + "/" + nowtotQty + "/" + nowCost + " /" + nowVal);

        try {
            stmt = conn.createStatement();
            String sql = "UPDATE maininventory SET shopAvailable = '" + nowShopQty + "' WHERE itemName='" + itm + "';";
            String sq2 = "UPDATE maininventory SET totalCost = '" + nowCost + "' WHERE itemName='" + itm + "';";
            String sq3 = "UPDATE maininventory SET totalValue = '" + nowVal + "' WHERE itemName='" + itm + "';";
            String sq4 = "UPDATE maininventory SET totalQty = '" + nowtotQty + "' WHERE itemName='" + itm + "';";

            stmt.executeUpdate(sql);
            stmt.executeUpdate(sq2);
            stmt.executeUpdate(sq3);
            stmt.executeUpdate(sq4);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant reverse to inventory");

        }

        /*totBillCost = totBillCost - Unitcost * qty;
        lblBillCost.setText(String.valueOf(totBillCost));
        totBillValue = totBillValue - retail * qty;
        lblTotalValue.setText(String.valueOf(totBillValue));
        grossValue = grossValue - (retail * qty) - disCount;
        lblGrossValue.setText(String.valueOf(grossValue));*/
        totBillCost = totBillCost - Unitcost * qty;
        lblBillCost.setText(String.valueOf(totBillCost));

        totBillValue = totBillValue - (qty * unitPrice);
        lblTotalValue.setText(String.valueOf(totBillValue));

        grossValue = grossValue - ((qty * unitPrice) - disCount);
        lblGrossValue.setText(String.valueOf(grossValue));

        netValue = netValue - ((qty * unitPrice) - disCount);
        lblNetValue.setText(String.valueOf(netValue));

        //lblNetValue.setText(String.valueOf(grossValue));
        comboItem.setSelectedItem(null);
        txtQty.setText("");
        txtDis.setText("");
        rdoPercentItem.setSelected(false);
        rdoRupeeItem.setSelected(true);
        lblItmValue.setText("Rs 0.00");
        lblDiscount.setText("0.00");

        DefaultTableModel tblMod = (DefaultTableModel) tblInvoice.getModel();
        tblMod.removeRow(selectedRow);

        JOptionPane.showMessageDialog(null, "Item reverse to main inventory Successfully...");

        if(tblMod.getRowCount()==0){
            totBillCost=0.00;
            totBillValue=0.00;
            
        }
        

    }//GEN-LAST:event_btnRemoveActionPerformed

    private void rdoPercentFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPercentFinalActionPerformed
        if (rdoPercentFinal.isSelected()) {
            rdoRupeeFinal.setSelected(false);
        }


    }//GEN-LAST:event_rdoPercentFinalActionPerformed

    private void rdoRupeeFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRupeeFinalActionPerformed
        if (rdoRupeeFinal.isSelected()) {
            rdoPercentFinal.setSelected(false);
        }
    }//GEN-LAST:event_rdoRupeeFinalActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (txtCash.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the cash Ammount");
        } else if (tblInvoice.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Please select Items to pay");
        } 
        
        else {

            if (tblInvoice.getRowCount() > 0) {
                String paymentType = "Cash";
                double cash = Double.parseDouble(txtCash.getText());
                double netAmmount = Double.parseDouble(lblNetValue.getText());

                String payId = lblPaymentId.getText();
                String cusId = (String) comboCustomer.getSelectedItem();
                String billValue = lblTotalValue.getText();
                String billCost = lblBillCost.getText();
                String perch = lblCusPrePurchesing.getText();
                double p = Double.parseDouble(perch);
                double v = Double.parseDouble(lblTotalValue.getText());
                double c = Double.parseDouble(lblBillCost.getText());

                double newPurch = p + v;

                String dte = lblDateToday.getText();

                double balance = cash - netAmmount;
                lblBalance.setText(String.valueOf(balance));

                if (chkBoxCard.isSelected()) {
                    paymentType = "Card";
                } else {
                    paymentType = "Cash";
                }

                try {
                    stmt = conn.createStatement();
                    String sql = "INSERT INTO payment (paymentId,cusId,billValue,billCost,paymentMethod,Date) VALUES ('" + payId + "','" + cusId + "','" + billValue + "','" + billCost + "','" + paymentType + "','" + dte + "')";
                    String sql2 = "UPDATE customer SET cusPerchesed='" + newPurch + "' WHERE cusId='" + cusId + "'";
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql2);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cant Update payment Table And Customer Table...");
                }

                txtQty.setText("");
                txtDis.setText("");
                rdoRupeeItem.setSelected(true);
                lblItmValue.setText("Rs 0.00");
                lblDiscount.setText("0.00");
                totBillCost = 0.00;
                totBillValue = 0.00;
                grossValue = 0.00;
                lblBillCost.setText("0.00");
                lblTotalValue.setText("0.00");
                lblGrossValue.setText("");
                lblNetValue.setText("");
                txtFinalDis.setText("");
                rdoPercentFinal.setSelected(true);
                rdoRupeeFinal.setSelected(false);
                netValue = 0.00;
                lblNetValue.setText("0.00");
                txtCash.setText("0.00");
                chkBoxCard.setSelected(false);
                lblBalance.setText("0.00");

                DefaultTableModel tbl = (DefaultTableModel) tblInvoice.getModel();

                for (int i = 0; i <= tblInvoice.getRowCount(); i++) {
                    tbl.removeRow(i);
                }
                bill();
                JOptionPane.showMessageDialog(null,"payment sucessfull...");
                txtBill.setText("");
                PayId();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Items Before Payment..");
            }

        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtFinalDisInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFinalDisInputMethodTextChanged

        if (txtFinalDis.getText().equals("")) {
            netValue = Double.parseDouble(lblGrossValue.getText());
            lblNetValue.setText(String.valueOf(netValue));
        } else {

            double netVal = 0;
            double grossValue = Double.parseDouble(lblGrossValue.getText());
            double disValue = Double.parseDouble(txtFinalDis.getText());
            if (rdoPercentFinal.isSelected()) {
                netVal = grossValue - (grossValue * disValue / 100);
                netValue = netVal;
                lblNetValue.setText(String.valueOf(netValue));
            } else if (rdoRupeeFinal.isSelected()) {
                netVal = grossValue - disValue;
                netValue = netVal;
                lblNetValue.setText(String.valueOf(netValue));
            }

        }


    }//GEN-LAST:event_txtFinalDisInputMethodTextChanged

    private void txtFinalDisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFinalDisKeyReleased

        if (txtFinalDis.getText().equals("")) {
            netValue = Double.parseDouble(lblGrossValue.getText());
            lblNetValue.setText(String.valueOf(netValue));
        } else {

            double netVal = 0;
            double grossValue = Double.parseDouble(lblGrossValue.getText());
            double disValue = Double.parseDouble(txtFinalDis.getText());
            if (rdoPercentFinal.isSelected()) {
                netVal = grossValue - (grossValue * disValue / 100);
                finalDiscount = grossValue * disValue / 100;
                netValue = netVal;
                lblNetValue.setText(String.valueOf(netValue));
            } else if (rdoRupeeFinal.isSelected()) {
                netVal = grossValue - disValue;
                finalDiscount = grossValue - disValue;
                netValue = netVal;
                lblNetValue.setText(String.valueOf(netValue));
            }

        }


    }//GEN-LAST:event_txtFinalDisKeyReleased

    private void txtDisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDisKeyReleased

        String itm = (String) comboItem.getSelectedItem();
        String unit = lblUnit.getText();
        int shopAV = Integer.parseInt(lblShopAv.getText());
        String itmUnit = null;

        int qty = 0;
        double disValue = 0;
        double totvalue = 0;
        double discount = 0;
        double value = 0.00;
        if (txtDis.getText().equals("")) {
            disValue = 0;

        } else {
            disValue = Double.parseDouble(txtDis.getText());
        }
        if (txtQty.getText().equals("")) {
            qty = 0;

        } else {
            qty = Integer.parseInt(txtQty.getText());
        }

        if (qty > shopAV) {
            JOptionPane.showMessageDialog(null, "Quantity is exceded than Item availability in shop..");
        } else if (qty <= 0) {
            JOptionPane.showMessageDialog(null, "Recheck the Quantity Level");
        } else {
            double retailPrice = Double.parseDouble(lblRetailPrice.getText());
            double unitcost = Double.parseDouble(lblUnitCost.getText());

            totvalue = retailPrice * qty;

            if (rdoPercentItem.isSelected()) {
                discount = totvalue * disValue / 100;

            } else if (rdoRupeeItem.isSelected()) {
                discount = disValue;

            }
            lblDiscount.setText("Rs " + String.valueOf(discount));

            //double value = totvalue - discount;
            value = (totvalue - discount);

            lblItmValue.setText("Rs " + String.valueOf(value));

            /*double cost = unitcost * qty;

            totBillCost = totBillCost + cost;
            totBillValue = totBillValue + totvalue;
            grossValue = grossValue + value;
            netValue = grossValue;
            lblBillCost.setText(String.valueOf(totBillCost));
            lblTotalValue.setText(String.valueOf(totBillValue));
            lblGrossValue.setText(String.valueOf(grossValue));
            lblNetValue.setText(String.valueOf(grossValue));*/
        }

    }//GEN-LAST:event_txtDisKeyReleased

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased

        String itm = (String) comboItem.getSelectedItem();
        String unit = lblUnit.getText();
        int shopAV = Integer.parseInt(lblShopAv.getText());
        String itmUnit = null;

        int qty = 0;
        double disValue = 0;
        double totvalue = 0;
        double discount = 0;
        double value = 0.00;
        if (txtDis.getText().equals("")) {
            disValue = 0;

        } else {
            disValue = Double.parseDouble(txtDis.getText());
        }
        if (txtQty.getText().equals("")) {
            qty = 0;

        } else {
            qty = Integer.parseInt(txtQty.getText());
        }

        if (qty > shopAV) {
            JOptionPane.showMessageDialog(null, "Quantity is exceded than Item availability in shop..");
        } else if (qty <= 0) {
            JOptionPane.showMessageDialog(null, "Recheck the Quantity Level");
        } else {
            double retailPrice = Double.parseDouble(lblRetailPrice.getText());
            double unitcost = Double.parseDouble(lblUnitCost.getText());

            totvalue = retailPrice * qty;

            if (rdoPercentItem.isSelected()) {
                discount = totvalue * disValue / 100;

            } else if (rdoRupeeItem.isSelected()) {
                discount = disValue;

            }
            lblDiscount.setText("Rs " + String.valueOf(discount));

            //double value = totvalue - discount;
            value = (totvalue - discount);

            lblItmValue.setText("Rs " + String.valueOf(value));
        }
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtCashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashKeyReleased

        double cash = 0;
        double netAmmount = 0;
        String paymentType = "Cash";

        if (txtCash.getText().equals("")) {
            cash = 0;

        } else {
            cash = Double.parseDouble(txtCash.getText());

            cash = Double.parseDouble(txtCash.getText());
            netAmmount = Double.parseDouble(lblNetValue.getText());

            double balance = cash - netAmmount;
            lblBalance.setText(String.valueOf(balance));
        }


    }//GEN-LAST:event_txtCashKeyReleased

    private void comboCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCustomerActionPerformed

        String cusId = (String) comboCustomer.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM customer WHERE cusId='" + cusId + "'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String cusName = rs.getString("cusLName");
                String cusNic = rs.getString("cusNic");
                String cusPerchesed = rs.getString("cusPerchesed");

                lblCusName.setText(cusName);
                lblCusNic.setText(cusNic);
                lblCusPrePurchesing.setText(cusPerchesed);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant get customer details..");
        }

        customer();
    }//GEN-LAST:event_comboCustomerActionPerformed

    private void txtQRCodeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtQRCodeInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQRCodeInputMethodTextChanged

    private void txtQRCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQRCodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQRCodeKeyReleased

    private void btnQROKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQROKActionPerformed

        String Qr = txtQRCode.getText();
        Qr = "'Pdct-000000107062023072920230000000000000002'";
        String productID = Qr.substring(1, 13);
        String mfg = Qr.substring(13, 21);
        String exp = Qr.substring(21, 29);
        String expTID = Qr.substring(29, 45);

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemId ='" + productID + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String product = rs.getString("itemName");

                comboItem.setSelectedItem(product);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant get details from Inventory");
        }

    }//GEN-LAST:event_btnQROKActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        bill();

        String textFieldText = txtBill.getText(); // Replace with the value from your text field
        try {
            txtBill.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Invoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnADD;
    private javax.swing.JButton btnQROK;
    private javax.swing.JButton btnRemove;
    private javax.swing.JCheckBox chkBoxCard;
    private javax.swing.JComboBox<String> comboCustomer;
    private javax.swing.JComboBox<String> comboItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblBillCost;
    private javax.swing.JLabel lblCurrentUser;
    private javax.swing.JLabel lblCusName;
    private javax.swing.JLabel lblCusNic;
    private javax.swing.JLabel lblCusPrePurchesing;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblGrossValue;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblItmValue;
    private javax.swing.JLabel lblNetValue;
    private javax.swing.JLabel lblPaymentId;
    private javax.swing.JLabel lblRetailPrice;
    private javax.swing.JLabel lblShopAv;
    private javax.swing.JLabel lblStore1Av;
    private javax.swing.JLabel lblStore2Av;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JLabel lblUnit;
    private javax.swing.JLabel lblUnitCost;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblUserIcon1;
    private javax.swing.JLabel lblUserIcon2;
    private javax.swing.JPanel panelCurrentUser;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPanel panelUserIcon;
    private javax.swing.JPanel panelUserIcon1;
    private javax.swing.JPanel panelUserIcon2;
    private javax.swing.JRadioButton rdoPercentFinal;
    private javax.swing.JRadioButton rdoPercentItem;
    private javax.swing.JRadioButton rdoRupeeFinal;
    private javax.swing.JRadioButton rdoRupeeItem;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTextArea txtBill;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtDis;
    private javax.swing.JTextField txtFinalDis;
    private javax.swing.JTextField txtQRCode;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
}
