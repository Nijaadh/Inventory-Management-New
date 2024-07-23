/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import static Services.DashBoardForm.validateEmail;
import static Services.DashBoardForm.validateNIC;
import static Services.DashBoardForm.validatePhoneNumber;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author mohom
 */
public class Customer extends javax.swing.JFrame {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    String gender = "Male";
    String logedUser = null;

    int tblCount = 0;

    public Customer(String user) {
        initComponents();
        logedUser = user;
        lblCurrentUser.setText(user);
        conn = DatabaseConnection.connection();

        tblCountCustomer();
        showRecodsCustomer();
        cusId();
        dt();
        times();

        lblCusPurchused.setVisible(false);
        lblcusperch.setVisible(false);
        panelUpdateButtonsCus.setVisible(false);
    }

    private Customer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void tblCountCustomer() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM customer;";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tblCount = rs.getInt(1);
                String count = Integer.toString(tblCount);
                lblCusCount.setText(count);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void showRecodsCustomer() {
        try {

            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM cusfewdetails";
            ResultSet res = stmt.executeQuery(sqlSelect);
            tblCus.setModel(DbUtils.resultSetToTableModel(res));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void cusId() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select MAX(cusId) from customer");
            rs.next();
            rs.getString("MAX(cusId)");
            if (rs.getString("MAX(cusId)") == null) {
                cusLoyaltyNo.setText("C-0000001");
                System.out.println("cusId  null");
            } else {
                long id = Long.parseLong(rs.getString("MAX(cusId)").substring(2, rs.getString("MAX(cusId)").length()));
                id++;
                cusLoyaltyNo.setText("C-" + String.format("%07d", id));
                System.out.println("id is 00001");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
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

    public boolean txtValidation(String txt) {
        String input = txt;
        boolean result = false;
        String regex = "^[a-zA-Z\\s]*$"; // Regular expression to match only letters and spaces
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMidNaviUser1 = new javax.swing.JPanel();
        panelMiddleNavigationTopCus = new javax.swing.JPanel();
        panelSerachBarCus = new javax.swing.JPanel();
        txtSearch1 = new javax.swing.JTextField();
        btnSearch1 = new javax.swing.JLabel();
        panelUserAdd1 = new javax.swing.JPanel();
        btnAddUser1 = new javax.swing.JLabel();
        panelMiddleNavigationCenter1 = new javax.swing.JPanel();
        lblCusCount = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCus = new javax.swing.JTable();
        panelDelete1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnDelete1 = new javax.swing.JLabel();
        panelUpdate1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnUpdate1 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
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
        panelCustomerManagement = new javax.swing.JPanel();
        panelUserImage3 = new javax.swing.JPanel();
        lblCusImage = new javax.swing.JLabel();
        scrollPanelAdress3 = new javax.swing.JScrollPane();
        txtCusNote = new javax.swing.JTextArea();
        txtCusLastName = new javax.swing.JTextField();
        txtCusNic = new javax.swing.JTextField();
        lblCusGender = new javax.swing.JLabel();
        rdoCusMale = new javax.swing.JRadioButton();
        rdoCusFemale = new javax.swing.JRadioButton();
        lblDob1 = new javax.swing.JLabel();
        txtCusMail = new javax.swing.JTextField();
        lblCustomerAddress = new javax.swing.JLabel();
        lblCusMail = new javax.swing.JLabel();
        lblCusContactNo = new javax.swing.JLabel();
        lblCusNic = new javax.swing.JLabel();
        lblCusFirstName = new javax.swing.JLabel();
        jSeparator43 = new javax.swing.JSeparator();
        txtCusFirstName = new javax.swing.JTextField();
        panelButtons1 = new javax.swing.JPanel();
        panelRegisterButtonsCus = new javax.swing.JPanel();
        panelClear3 = new javax.swing.JPanel();
        btnClear1 = new javax.swing.JLabel();
        panelRegister3 = new javax.swing.JPanel();
        btnCusRegister = new javax.swing.JLabel();
        panelUpdateButtonsCus = new javax.swing.JPanel();
        panelRegister4 = new javax.swing.JPanel();
        btnCusUpdate = new javax.swing.JLabel();
        panelClear4 = new javax.swing.JPanel();
        btnClear3 = new javax.swing.JLabel();
        CalanderCusDob = new com.toedter.calendar.JDateChooser();
        lblcusperch = new javax.swing.JLabel();
        scrollPanelAdress4 = new javax.swing.JScrollPane();
        txtCusAddress = new javax.swing.JTextArea();
        txtCusContactNo1 = new javax.swing.JTextField();
        cusLoyaltyNo = new javax.swing.JLabel();
        lblCusLastName1 = new javax.swing.JLabel();
        lblCusLoyaltyId1 = new javax.swing.JLabel();
        lblCusPurchused = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelMidNaviUser1.setBackground(new java.awt.Color(71, 120, 197));

        panelMiddleNavigationTopCus.setBackground(new java.awt.Color(120, 168, 252));

        panelSerachBarCus.setBackground(new java.awt.Color(141, 182, 253));
        panelSerachBarCus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtSearch1.setBackground(new java.awt.Color(165, 197, 253));
        txtSearch1.setBorder(null);

        btnSearch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_search_24px.png"))); // NOI18N

        javax.swing.GroupLayout panelSerachBarCusLayout = new javax.swing.GroupLayout(panelSerachBarCus);
        panelSerachBarCus.setLayout(panelSerachBarCusLayout);
        panelSerachBarCusLayout.setHorizontalGroup(
            panelSerachBarCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSerachBarCusLayout.createSequentialGroup()
                .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch1))
        );
        panelSerachBarCusLayout.setVerticalGroup(
            panelSerachBarCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelUserAdd1.setBackground(new java.awt.Color(120, 168, 252));

        btnAddUser1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAddUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_add_user_male_32px_1.png"))); // NOI18N
        btnAddUser1.setToolTipText("Add Users");
        btnAddUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUser1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUserAdd1Layout = new javax.swing.GroupLayout(panelUserAdd1);
        panelUserAdd1.setLayout(panelUserAdd1Layout);
        panelUserAdd1Layout.setHorizontalGroup(
            panelUserAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelUserAdd1Layout.setVerticalGroup(
            panelUserAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddUser1)
        );

        javax.swing.GroupLayout panelMiddleNavigationTopCusLayout = new javax.swing.GroupLayout(panelMiddleNavigationTopCus);
        panelMiddleNavigationTopCus.setLayout(panelMiddleNavigationTopCusLayout);
        panelMiddleNavigationTopCusLayout.setHorizontalGroup(
            panelMiddleNavigationTopCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationTopCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelSerachBarCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelUserAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        panelMiddleNavigationTopCusLayout.setVerticalGroup(
            panelMiddleNavigationTopCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationTopCusLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelMiddleNavigationTopCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelUserAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelSerachBarCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMiddleNavigationCenter1.setBackground(new java.awt.Color(84, 127, 206));

        lblCusCount.setFont(new java.awt.Font("Segoe UI", 0, 72)); // NOI18N
        lblCusCount.setForeground(new java.awt.Color(255, 255, 255));
        lblCusCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCusCount.setText("0");

        tblCus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer ID", "Customer NIC", "Cus Name"
            }
        ));
        tblCus.setRowHeight(25);
        tblCus.setRowMargin(3);
        tblCus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCusMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblCusMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblCus);

        javax.swing.GroupLayout panelMiddleNavigationCenter1Layout = new javax.swing.GroupLayout(panelMiddleNavigationCenter1);
        panelMiddleNavigationCenter1.setLayout(panelMiddleNavigationCenter1Layout);
        panelMiddleNavigationCenter1Layout.setHorizontalGroup(
            panelMiddleNavigationCenter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationCenter1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(lblCusCount, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );
        panelMiddleNavigationCenter1Layout.setVerticalGroup(
            panelMiddleNavigationCenter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationCenter1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(lblCusCount)
                .addContainerGap())
        );

        panelDelete1.setBackground(new java.awt.Color(84, 108, 155));

        jPanel7.setBackground(new java.awt.Color(68, 194, 137));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        btnDelete1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete1.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_no_user_32px.png"))); // NOI18N
        btnDelete1.setText("Delete");
        btnDelete1.setToolTipText("Delete User");
        btnDelete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelete1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelDelete1Layout = new javax.swing.GroupLayout(panelDelete1);
        panelDelete1.setLayout(panelDelete1Layout);
        panelDelete1Layout.setHorizontalGroup(
            panelDelete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDelete1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnDelete1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDelete1Layout.setVerticalGroup(
            panelDelete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDelete1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnDelete1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelUpdate1.setBackground(new java.awt.Color(84, 108, 155));

        jPanel5.setBackground(new java.awt.Color(68, 194, 137));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        btnUpdate1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate1.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_registration_32px_1.png"))); // NOI18N
        btnUpdate1.setText("Update");
        btnUpdate1.setToolTipText("Update User Details");
        btnUpdate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdate1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUpdate1Layout = new javax.swing.GroupLayout(panelUpdate1);
        panelUpdate1.setLayout(panelUpdate1Layout);
        panelUpdate1Layout.setHorizontalGroup(
            panelUpdate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUpdate1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(btnUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelUpdate1Layout.setVerticalGroup(
            panelUpdate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUpdate1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jToggleButton1.setBackground(new java.awt.Color(84, 108, 155));
        jToggleButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_print_25px.png"))); // NOI18N
        jToggleButton1.setText("Print");
        jToggleButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMidNaviUser1Layout = new javax.swing.GroupLayout(panelMidNaviUser1);
        panelMidNaviUser1.setLayout(panelMidNaviUser1Layout);
        panelMidNaviUser1Layout.setHorizontalGroup(
            panelMidNaviUser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMidNaviUser1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMidNaviUser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelMidNaviUser1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelMidNaviUser1Layout.createSequentialGroup()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(panelMiddleNavigationTopCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMiddleNavigationCenter1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMidNaviUser1Layout.setVerticalGroup(
            panelMidNaviUser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMidNaviUser1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelMiddleNavigationTopCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMiddleNavigationCenter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(panelMidNaviUser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        panelTop.add(panelCurrentUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 20, -1, 30));

        jLabel12.setBackground(new java.awt.Color(71, 120, 197));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Date :");
        panelTop.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 120, 51));

        jLabel13.setBackground(new java.awt.Color(71, 120, 197));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("User Registration");
        panelTop.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, 60));

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

        panelTop.add(panelUserIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1470, 20, -1, 30));

        panelUserIcon1.setBackground(new java.awt.Color(71, 120, 197));

        lblUserIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_exit_32px.png"))); // NOI18N
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

        panelTop.add(panelUserIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1520, 20, -1, -1));

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
        lblUserIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_double_left_32px_1.png"))); // NOI18N
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

        panelTop.add(panelUserIcon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1570, 20, -1, -1));

        panelCustomerManagement.setBackground(new java.awt.Color(23, 35, 51));
        panelCustomerManagement.setForeground(new java.awt.Color(255, 255, 255));
        panelCustomerManagement.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUserImage3.setBackground(new java.awt.Color(23, 35, 51));
        panelUserImage3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        lblCusImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelUserImage3Layout = new javax.swing.GroupLayout(panelUserImage3);
        panelUserImage3.setLayout(panelUserImage3Layout);
        panelUserImage3Layout.setHorizontalGroup(
            panelUserImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCusImage, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );
        panelUserImage3Layout.setVerticalGroup(
            panelUserImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserImage3Layout.createSequentialGroup()
                .addComponent(lblCusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        panelCustomerManagement.add(panelUserImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        txtCusNote.setBackground(new java.awt.Color(23, 35, 51));
        txtCusNote.setColumns(20);
        txtCusNote.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtCusNote.setForeground(new java.awt.Color(255, 255, 255));
        txtCusNote.setRows(5);
        txtCusNote.setText("This is shoert note about that customer");
        txtCusNote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPanelAdress3.setViewportView(txtCusNote);

        panelCustomerManagement.add(scrollPanelAdress3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 480, 160));

        txtCusLastName.setBackground(new java.awt.Color(23, 35, 51));
        txtCusLastName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCusLastName.setForeground(new java.awt.Color(255, 255, 255));
        txtCusLastName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelCustomerManagement.add(txtCusLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 240, 320, 35));

        txtCusNic.setBackground(new java.awt.Color(23, 35, 51));
        txtCusNic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCusNic.setForeground(new java.awt.Color(255, 255, 255));
        txtCusNic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelCustomerManagement.add(txtCusNic, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, 290, 35));

        lblCusGender.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCusGender.setForeground(new java.awt.Color(255, 255, 255));
        lblCusGender.setText("Gender :");
        panelCustomerManagement.add(lblCusGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 310, -1, -1));

        rdoCusMale.setBackground(new java.awt.Color(23, 35, 51));
        rdoCusMale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoCusMale.setForeground(new java.awt.Color(255, 255, 255));
        rdoCusMale.setSelected(true);
        rdoCusMale.setText("Male");
        rdoCusMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCusMaleActionPerformed(evt);
            }
        });
        panelCustomerManagement.add(rdoCusMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 310, -1, -1));

        rdoCusFemale.setBackground(new java.awt.Color(23, 35, 51));
        rdoCusFemale.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoCusFemale.setForeground(new java.awt.Color(255, 255, 255));
        rdoCusFemale.setText("Female");
        rdoCusFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCusFemaleActionPerformed(evt);
            }
        });
        panelCustomerManagement.add(rdoCusFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 310, -1, -1));

        lblDob1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblDob1.setForeground(new java.awt.Color(255, 255, 255));
        lblDob1.setText("Date of Birth :");
        panelCustomerManagement.add(lblDob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 180, -1));

        txtCusMail.setBackground(new java.awt.Color(23, 35, 51));
        txtCusMail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCusMail.setForeground(new java.awt.Color(255, 255, 255));
        txtCusMail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelCustomerManagement.add(txtCusMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 292, 35));

        lblCustomerAddress.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCustomerAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomerAddress.setText("Current Adress :");
        panelCustomerManagement.add(lblCustomerAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, -1, -1));

        lblCusMail.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCusMail.setForeground(new java.awt.Color(255, 255, 255));
        lblCusMail.setText("Email :");
        panelCustomerManagement.add(lblCusMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 522, -1, -1));

        lblCusContactNo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCusContactNo.setForeground(new java.awt.Color(255, 255, 255));
        lblCusContactNo.setText("Contact Number :");
        panelCustomerManagement.add(lblCusContactNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        lblCusNic.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCusNic.setForeground(new java.awt.Color(255, 255, 255));
        lblCusNic.setText("NIC NO :");
        panelCustomerManagement.add(lblCusNic, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 150, -1));

        lblCusFirstName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCusFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblCusFirstName.setText("First Name :");
        panelCustomerManagement.add(lblCusFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 170, -1));

        jSeparator43.setForeground(new java.awt.Color(255, 255, 255));
        panelCustomerManagement.add(jSeparator43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 336, -1));

        txtCusFirstName.setBackground(new java.awt.Color(23, 35, 51));
        txtCusFirstName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCusFirstName.setForeground(new java.awt.Color(255, 255, 255));
        txtCusFirstName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelCustomerManagement.add(txtCusFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 290, 34));

        panelButtons1.setBackground(new java.awt.Color(23, 35, 51));
        panelButtons1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRegisterButtonsCus.setBackground(new java.awt.Color(23, 35, 51));
        panelRegisterButtonsCus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelClear3.setBackground(new java.awt.Color(23, 35, 51));
        panelClear3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnClear1.setBackground(new java.awt.Color(51, 51, 51));
        btnClear1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear1.setForeground(new java.awt.Color(255, 255, 255));
        btnClear1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear1.setText("Clear");
        btnClear1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClear3Layout = new javax.swing.GroupLayout(panelClear3);
        panelClear3.setLayout(panelClear3Layout);
        panelClear3Layout.setHorizontalGroup(
            panelClear3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelClear3Layout.setVerticalGroup(
            panelClear3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelRegisterButtonsCus.add(panelClear3, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 0, -1, -1));

        panelRegister3.setBackground(new java.awt.Color(23, 35, 51));
        panelRegister3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnCusRegister.setBackground(new java.awt.Color(51, 51, 51));
        btnCusRegister.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCusRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnCusRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCusRegister.setText("Register");
        btnCusRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCusRegisterMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRegister3Layout = new javax.swing.GroupLayout(panelRegister3);
        panelRegister3.setLayout(panelRegister3Layout);
        panelRegister3Layout.setHorizontalGroup(
            panelRegister3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCusRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelRegister3Layout.setVerticalGroup(
            panelRegister3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCusRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelRegisterButtonsCus.add(panelRegister3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelButtons1.add(panelRegisterButtonsCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, -1, -1));

        panelUpdateButtonsCus.setBackground(new java.awt.Color(23, 35, 51));
        panelUpdateButtonsCus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRegister4.setBackground(new java.awt.Color(23, 35, 51));
        panelRegister4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnCusUpdate.setBackground(new java.awt.Color(51, 51, 51));
        btnCusUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCusUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnCusUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCusUpdate.setText("Update");
        btnCusUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCusUpdateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCusUpdateMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout panelRegister4Layout = new javax.swing.GroupLayout(panelRegister4);
        panelRegister4.setLayout(panelRegister4Layout);
        panelRegister4Layout.setHorizontalGroup(
            panelRegister4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCusUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelRegister4Layout.setVerticalGroup(
            panelRegister4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCusUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelUpdateButtonsCus.add(panelRegister4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelClear4.setBackground(new java.awt.Color(23, 35, 51));
        panelClear4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnClear3.setBackground(new java.awt.Color(51, 51, 51));
        btnClear3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear3.setForeground(new java.awt.Color(255, 255, 255));
        btnClear3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear3.setText("Clear");
        btnClear3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClear4Layout = new javax.swing.GroupLayout(panelClear4);
        panelClear4.setLayout(panelClear4Layout);
        panelClear4Layout.setHorizontalGroup(
            panelClear4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelClear4Layout.setVerticalGroup(
            panelClear4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelUpdateButtonsCus.add(panelClear4, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 0, -1, -1));

        panelButtons1.add(panelUpdateButtonsCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        panelCustomerManagement.add(panelButtons1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 730, 620, 51));

        CalanderCusDob.setDateFormatString("yyyy,MM,dd");
        panelCustomerManagement.add(CalanderCusDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 290, 40));

        lblcusperch.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblcusperch.setForeground(new java.awt.Color(255, 255, 255));
        lblcusperch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblcusperch.setText("Purchursed");
        panelCustomerManagement.add(lblcusperch, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 560, -1, -1));

        txtCusAddress.setBackground(new java.awt.Color(23, 35, 51));
        txtCusAddress.setColumns(20);
        txtCusAddress.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtCusAddress.setForeground(new java.awt.Color(255, 255, 255));
        txtCusAddress.setRows(5);
        txtCusAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPanelAdress4.setViewportView(txtCusAddress);

        panelCustomerManagement.add(scrollPanelAdress4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 520, 150));

        txtCusContactNo1.setBackground(new java.awt.Color(23, 35, 51));
        txtCusContactNo1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCusContactNo1.setForeground(new java.awt.Color(255, 255, 255));
        txtCusContactNo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        panelCustomerManagement.add(txtCusContactNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, 290, 35));

        cusLoyaltyNo.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        cusLoyaltyNo.setForeground(new java.awt.Color(102, 255, 102));
        cusLoyaltyNo.setText("00");
        panelCustomerManagement.add(cusLoyaltyNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 320, 60));

        lblCusLastName1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCusLastName1.setForeground(new java.awt.Color(255, 255, 255));
        lblCusLastName1.setText("Last Name :");
        panelCustomerManagement.add(lblCusLastName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, -1, -1));

        lblCusLoyaltyId1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblCusLoyaltyId1.setForeground(new java.awt.Color(255, 255, 255));
        lblCusLoyaltyId1.setText("Loyalty ID : ");
        panelCustomerManagement.add(lblCusLoyaltyId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lblCusPurchused.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblCusPurchused.setForeground(new java.awt.Color(255, 102, 102));
        lblCusPurchused.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelCustomerManagement.add(lblCusPurchused, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 610, 410, 50));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCustomerManagement, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCustomerManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMidNaviUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMidNaviUser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdate1MouseClicked
        if (tblCus.getSelectedRowCount() < 0) {
            JOptionPane.showMessageDialog(null, "Select a customer before Update..");
        } else if (tblCus.getSelectedRowCount() > 1) {
            JOptionPane.showMessageDialog(null, "Select a single customer..");
        } else {
            lblCusPurchused.setVisible(false);
            lblcusperch.setVisible(false);

            panelUpdateButtonsCus.setVisible(true);
            txtCusFirstName.setEditable(true);
            txtCusLastName.setEditable(true);
            txtCusNic.setEditable(true);
            txtCusContactNo1.setEditable(true);
            txtCusMail.setEditable(true);
            txtCusAddress.setEditable(true);
            txtCusNote.setEditable(true);
            CalanderCusDob.setEnabled(true);
            rdoCusMale.setEnabled(true);
            rdoCusFemale.setEnabled(true);
            CalanderCusDob.setEnabled(true);
        }

    }//GEN-LAST:event_btnUpdate1MouseClicked

    private void btnAddUser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUser1MouseClicked

        DefaultTableModel tblMod = (DefaultTableModel) tblCus.getModel();
        tblCus.clearSelection();
        panelUpdateButtonsCus.setVisible(false);
        panelRegisterButtonsCus.setVisible(true);

        cusId();

        txtCusFirstName.setEditable(true);
        txtCusLastName.setEditable(true);
        txtCusNic.setEditable(true);
        txtCusContactNo1.setEditable(true);
        txtCusMail.setEditable(true);
        txtCusAddress.setEditable(true);
        txtCusNote.setEditable(true);
        CalanderCusDob.setEnabled(true);
        rdoCusMale.setEnabled(true);
        rdoCusMale.setSelected(true);
        rdoCusFemale.setEnabled(true);
        rdoCusFemale.setSelected(false);
        gender = "Male";
        CalanderCusDob.setEnabled(true);

        txtCusFirstName.setText("");
        txtCusLastName.setText("");
        txtCusNic.setText("");
        txtCusContactNo1.setText("");
        txtCusMail.setText("");
        txtCusAddress.setText("");
        txtCusNote.setText("");
        rdoCusFemale.setSelected(false);
        rdoCusMale.setSelected(true);
        CalanderCusDob.setDate(null);


    }//GEN-LAST:event_btnAddUser1MouseClicked

    private void btnClear3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear3MouseClicked

        txtCusFirstName.setText("");
        txtCusLastName.setText("");
        txtCusNic.setText("");
        txtCusContactNo1.setText("");
        txtCusMail.setText("");
        txtCusAddress.setText("");
        txtCusNote.setText("");
        rdoCusFemale.setSelected(false);
        rdoCusMale.setSelected(true);
        CalanderCusDob.setDate(null);


    }//GEN-LAST:event_btnClear3MouseClicked

    private void btnCusUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCusUpdateMouseClicked

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String cusId = cusLoyaltyNo.getText();
            String fName = txtCusFirstName.getText();
            String lName = txtCusLastName.getText();
            String nic = txtCusNic.getText();
            String sex = gender;
            String Cusdob = ((JTextField) CalanderCusDob.getDateEditor().getUiComponent()).getText();//dateFormat.format(CalanderDob.getDate());//txtDateOfBirth.getText();
            String contactNo = txtCusContactNo1.getText();
            String mail = txtCusMail.getText();
            String address = txtCusAddress.getText();
            String note = txtCusNote.getText();

            if (fName.isEmpty() || fName.length() < 3) {
                JOptionPane.showMessageDialog(null, "name should contain more than 3 letters");
            } else if (lName.isEmpty() || lName.length() < 3) {
                JOptionPane.showMessageDialog(null, "name should contain more than 3 letters");
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert a Valid NIC Number..");
            } else if (Cusdob.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter your date of birth...");
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address is missing...");
            } else if (contactNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "phone number not found");
            } else if (mail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a valid valid Email address...");
            } else if (gender.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a Gender");
            } else {

                stmt = conn.createStatement();

                String sql = "UPDATE customer SET cusFName ='" + fName + "' , cusLName='" + lName + "' , cusNic= '" + nic + "', cusGender='" + sex + "' , cusDob= '" + Cusdob + "', cusContactNo='" + contactNo + "' , cusEmail= '" + mail + "', cusAddress= '" + address + "', cusNote= '" + note + "' WHERE cusId='" + cusId + "';";
                String sql2 = "UPDATE cusfewdetails SET cusNic= '" + nic + "',cusName= '" + lName + "' WHERE cusId='" + cusId + "' ";
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql2);
                JOptionPane.showMessageDialog(null, "cUSTOMER Data uPDATED Successfully in to Customer Table...");

                showRecodsCustomer();
                tblCountCustomer();
                cusId();

                txtCusFirstName.setText("");
                txtCusLastName.setText("");
                txtCusNic.setText("");

                txtCusContactNo1.setText("");

                txtCusMail.setText("");
                txtCusAddress.setText("");
                txtCusNote.setText("");

                rdoCusFemale.setSelected(false);
                rdoCusMale.setSelected(true);

                CalanderCusDob.setDate(null);

                panelUpdateButtonsCus.setVisible(false);

                txtCusFirstName.setEditable(false);
                txtCusLastName.setEditable(false);
                txtCusNic.setEditable(false);
                txtCusContactNo1.setEditable(false);
                txtCusMail.setEditable(false);
                txtCusAddress.setEditable(false);
                txtCusNote.setEditable(false);
                CalanderCusDob.setEnabled(false);
                rdoCusFemale.setEnabled(false);
                rdoCusMale.setEnabled(false);
                CalanderCusDob.setEnabled(false);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_btnCusUpdateMouseClicked

    private void btnCusRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCusRegisterMouseClicked

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String cusId = cusLoyaltyNo.getText();
            String fName = txtCusFirstName.getText();
            String lName = txtCusLastName.getText();
            String nic = txtCusNic.getText();
            String sex = gender;
            String Cusdob = ((JTextField) CalanderCusDob.getDateEditor().getUiComponent()).getText();//dateFormat.format(CalanderDob.getDate());//txtDateOfBirth.getText();
            String contactNo = txtCusContactNo1.getText();
            String mail = txtCusMail.getText();
            String address = txtCusAddress.getText();
            String note = txtCusNote.getText();

            if (fName.isEmpty() || fName.length() < 3) {
                JOptionPane.showMessageDialog(null, "name should contain more than 3 letters");
            } else if (lName.isEmpty() || lName.length() < 3) {
                JOptionPane.showMessageDialog(null, "name should contain more than 3 letters");
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert a Valid NIC Number..");
            } else if (Cusdob.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter your date of birth...");
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address is missing...");
            } else if (contactNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "phone number not found");
            } else if (mail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a valid valid Email address...");
            } else if (gender.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a Gender");
            } else {

                boolean isFirstNameValid = txtValidation(fName);
                boolean isLastNameValid = txtValidation(lName);
                boolean isEmailValid = validateEmail(mail);
                boolean isContactNoValid = validatePhoneNumber(contactNo);

                boolean isNICValid = validateNIC(nic);

                if (!isFirstNameValid) {
                    JOptionPane.showMessageDialog(null, "First Name is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isLastNameValid) {
                    JOptionPane.showMessageDialog(null, "Last Name is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isEmailValid) {
                    JOptionPane.showMessageDialog(null, "Email is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isContactNoValid) {
                    JOptionPane.showMessageDialog(null, "Contact Number is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isNICValid) {
                    JOptionPane.showMessageDialog(null, "NIC is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else {

                    stmt = conn.createStatement();

                    //String sql = "INSERT INTO customer (cusId , cusFName, cusLName, cusNic, cusGender, cusDob, cusContactNo, cusEmail, cusAddress, cusNote) VALUES('" + cusId + "','" + fName + "','" + lName + "','" + nic + "','" + sex + "','" + Cusdob + "','" + contactNo + "','" + mail + "','" + address + "','" + note + "');";
                    //String sql2 = "INSERT INTO cusfewdetails (cusId,cusNic,cusName) VALUES ('" + cusId + "','" + nic + "','" + lName + "')";
                    //String sql = "UPDATE user SET uFirstName = '" + fName + "', uLastName = '" + lName + "', uNic = '" + nic + "', uGender = '" + sex + "', uDateOfBirth = '" + dob + "', uContactNo = '" + contactNo + "', uEmContactNo = '" + emContactNo + "', uEmail = '" + mail + "', uAddress = '" + address + "', uPassword = '" + password + "', uSecQuistion = '" + secQuestion + "', uSecAnswer = '" + secAnswer + "', userType = '" + userType + "' WHERE uUserName = '" + userName + "';";
                    //String sql2 = "UPDATE logindetails SET uLastName = '" + lName + "', userType = '" + userType + "' WHERE username = '" + userName + "';";
                    String sql = "INSERT INTO customer (cusId ,cusFName, cusLName, cusNic, cusGender, cusDob, cusContactNo, cusEmail, cusAddress, cusNote) VALUES ('"+cusId+"','" + fName + "', '" + lName + "', '" + nic + "', '" + sex + "', '" + Cusdob + "', '" + contactNo + "', '" + mail + "', '" + address + "', '" + note + "');";
                    String sql2 = "INSERT INTO cusfewdetails (cusId,cusNic, cusName) VALUES ('"+cusId+"','" + nic + "', '" + lName + "') ";

                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql2);
                    JOptionPane.showMessageDialog(null, "Data Inserted Successfully in to Customer Table...");

                    showRecodsCustomer();
                    tblCountCustomer();
                    cusId();

                    txtCusFirstName.setText("");
                    txtCusLastName.setText("");
                    txtCusNic.setText("");

                    txtCusContactNo1.setText("");

                    txtCusMail.setText("");
                    txtCusAddress.setText("");
                    txtCusNote.setText("");

                    rdoCusFemale.setSelected(false);
                    rdoCusMale.setSelected(true);

                    CalanderCusDob.setDate(null);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnCusRegisterMouseClicked

    private void btnClear1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear1MouseClicked

        txtCusFirstName.setText("");
        txtCusLastName.setText("");
        txtCusNic.setText("");
        txtCusContactNo1.setText("");
        txtCusMail.setText("");
        txtCusAddress.setText("");
        txtCusNote.setText("");
        rdoCusFemale.setSelected(false);
        rdoCusMale.setSelected(true);
        CalanderCusDob.setDate(null);

    }//GEN-LAST:event_btnClear1MouseClicked

    private void rdoCusFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCusFemaleActionPerformed
        if (rdoCusFemale.isSelected()) {
            rdoCusMale.setSelected(false);
            gender = "Female";
        }
    }//GEN-LAST:event_rdoCusFemaleActionPerformed

    private void rdoCusMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCusMaleActionPerformed
        if (rdoCusMale.isSelected()) {
            rdoCusFemale.setSelected(false);
            gender = "Male";
        }
    }//GEN-LAST:event_rdoCusMaleActionPerformed

    private void lblUserIcon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserIcon1MouseClicked

        int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Logout ?");

        if (yesOrNo == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            LoginForm login = new LoginForm();
            login.setVisible(true);
        } else if (yesOrNo == JOptionPane.NO_OPTION) {

        } else {

        }
    }//GEN-LAST:event_lblUserIcon1MouseClicked

    private void tblCusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCusMouseClicked

        panelRegisterButtonsCus.setVisible(false);
        panelUpdateButtonsCus.setVisible(false);
        lblCusPurchused.setVisible(true);
        lblcusperch.setVisible(true);

        try {
            int rowIndex = tblCus.getSelectedRow();
            int colmnIndex = 0;

            String selectedCusId = (String) tblCus.getModel().getValueAt(rowIndex, colmnIndex);

            stmt = conn.createStatement();

            String sql = "SELECT * FROM customer WHERE cusId = '" + selectedCusId + "';";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String dte = (rs.getString("cusDob"));
                ((JTextField) CalanderCusDob.getDateEditor().getUiComponent()).setText(dte);
                cusLoyaltyNo.setText((rs.getString("cusId")));
                txtCusFirstName.setText(rs.getString("cusFName"));
                txtCusLastName.setText(rs.getString("cusLName"));
                txtCusNic.setText(rs.getString("cusNic"));
                txtCusContactNo1.setText(rs.getString("cusContactNo"));
                txtCusMail.setText(rs.getString("cusEmail"));
                String p = rs.getString("cusPerchesed");

                lblCusPurchused.setText("Rs. " + p);
                txtCusAddress.setText(rs.getString("cusAddress"));
                txtCusNote.setText(rs.getString("cusNote"));

                gender = rs.getString("cusGender");

                if (gender.equals("Male")) {
                    rdoCusMale.setSelected(true);
                    rdoCusFemale.setSelected(false);

                }
                if (gender.equals("Female")) {
                    rdoCusFemale.setSelected(true);
                    rdoCusMale.setSelected(false);

                }

                txtCusFirstName.setEditable(false);
                txtCusLastName.setEditable(false);
                txtCusNic.setEditable(false);
                txtCusContactNo1.setEditable(false);
                txtCusMail.setEditable(false);
                txtCusAddress.setEditable(false);
                txtCusNote.setEditable(false);
                CalanderCusDob.setEnabled(false);
                if (gender.equals("Male")) {
                    rdoCusFemale.setEnabled(false);
                }

                if (gender.equals("Female")) {
                    rdoCusMale.setEnabled(false);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }


    }//GEN-LAST:event_tblCusMouseClicked

    private void tblCusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCusMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblCusMouseEntered

    private void btnDelete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelete1MouseClicked

        int selectedRowCount = tblCus.getSelectedRowCount();
        if (selectedRowCount < 0) {

            JOptionPane.showMessageDialog(null, "Select a user before Delete..");
        } else if (selectedRowCount > 1) {
            JOptionPane.showMessageDialog(null, "Select Single user to Delete..");
        } else {
            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Delete User ?");
            if (yesOrNo == JOptionPane.YES_OPTION) {
                try {
                    int selectedRow = tblCus.getSelectedRow();
                    int colmnIndex = 0;
                    String selectedCus = (String) tblCus.getModel().getValueAt(selectedRow, colmnIndex);
                    stmt = conn.createStatement();
                    String sql = "DELETE FROM customer WHERE cusId = '" + selectedCus + "';";
                    String sql1 = "DELETE FROM cusfewdetails WHERE cusId = '" + selectedCus + "';";
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql1);
                    JOptionPane.showMessageDialog(null, "Customer  Deletion Successfull...");

                    showRecodsCustomer();
                    tblCountCustomer();

                    txtCusFirstName.setText("");
                    txtCusLastName.setText("");
                    txtCusNic.setText("");

                    txtCusContactNo1.setText("");

                    txtCusMail.setText("");
                    txtCusAddress.setText("");
                    txtCusNote.setText("");

                    lblCusPurchused.setVisible(false);
                    lblcusperch.setVisible(false);

                    rdoCusFemale.setSelected(false);
                    rdoCusMale.setSelected(true);

                    CalanderCusDob.setDate(null);
                    txtCusFirstName.setEditable(false);
                    txtCusLastName.setEditable(false);
                    txtCusNic.setEditable(false);
                    txtCusContactNo1.setEditable(false);
                    txtCusMail.setEditable(false);
                    txtCusAddress.setEditable(false);
                    txtCusNote.setEditable(false);
                    CalanderCusDob.setEnabled(false);
                    rdoCusFemale.setEnabled(false);
                    rdoCusMale.setEnabled(false);

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(this, e);
                }
            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }
        }
    }//GEN-LAST:event_btnDelete1MouseClicked

    private void lblUserIcon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserIcon2MouseClicked
        DashBoardForm db = new DashBoardForm(logedUser);
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblUserIcon2MouseClicked

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed

        try {
            stmt = conn.createStatement();
            JasperDesign jdesign = JRXmlLoader.load("D:\\Project\\Inventory Management New\\src\\Services\\customerReport.jrxml");
            String sql = "SELECT * FROM customer";

            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(sql);
            jdesign.setQuery(updateQuery);
            JasperReport jReport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jReport, null, conn);
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void btnCusUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCusUpdateMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCusUpdateMouseEntered

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
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser CalanderCusDob;
    private javax.swing.JLabel btnAddUser1;
    private javax.swing.JLabel btnClear1;
    private javax.swing.JLabel btnClear3;
    private javax.swing.JLabel btnCusRegister;
    private javax.swing.JLabel btnCusUpdate;
    private javax.swing.JLabel btnDelete1;
    private javax.swing.JLabel btnSearch1;
    private javax.swing.JLabel btnUpdate1;
    private javax.swing.JLabel cusLoyaltyNo;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator43;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblCurrentUser;
    private javax.swing.JLabel lblCusContactNo;
    private javax.swing.JLabel lblCusCount;
    private javax.swing.JLabel lblCusFirstName;
    private javax.swing.JLabel lblCusGender;
    private javax.swing.JLabel lblCusImage;
    private javax.swing.JLabel lblCusLastName1;
    private javax.swing.JLabel lblCusLoyaltyId1;
    private javax.swing.JLabel lblCusMail;
    private javax.swing.JLabel lblCusNic;
    private javax.swing.JLabel lblCusPurchused;
    private javax.swing.JLabel lblCustomerAddress;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblDob1;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblUserIcon1;
    private javax.swing.JLabel lblUserIcon2;
    private javax.swing.JLabel lblcusperch;
    private javax.swing.JPanel panelButtons1;
    private javax.swing.JPanel panelClear3;
    private javax.swing.JPanel panelClear4;
    private javax.swing.JPanel panelCurrentUser;
    private javax.swing.JPanel panelCustomerManagement;
    private javax.swing.JPanel panelDelete1;
    private javax.swing.JPanel panelMidNaviUser1;
    private javax.swing.JPanel panelMiddleNavigationCenter1;
    private javax.swing.JPanel panelMiddleNavigationTopCus;
    private javax.swing.JPanel panelRegister3;
    private javax.swing.JPanel panelRegister4;
    private javax.swing.JPanel panelRegisterButtonsCus;
    private javax.swing.JPanel panelSerachBarCus;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPanel panelUpdate1;
    private javax.swing.JPanel panelUpdateButtonsCus;
    private javax.swing.JPanel panelUserAdd1;
    private javax.swing.JPanel panelUserIcon;
    private javax.swing.JPanel panelUserIcon1;
    private javax.swing.JPanel panelUserIcon2;
    private javax.swing.JPanel panelUserImage3;
    private javax.swing.JRadioButton rdoCusFemale;
    private javax.swing.JRadioButton rdoCusMale;
    private javax.swing.JScrollPane scrollPanelAdress3;
    private javax.swing.JScrollPane scrollPanelAdress4;
    private javax.swing.JTable tblCus;
    private javax.swing.JTextArea txtCusAddress;
    private javax.swing.JTextField txtCusContactNo1;
    private javax.swing.JTextField txtCusFirstName;
    private javax.swing.JTextField txtCusLastName;
    private javax.swing.JTextField txtCusMail;
    private javax.swing.JTextField txtCusNic;
    private javax.swing.JTextArea txtCusNote;
    private javax.swing.JTextField txtSearch1;
    // End of variables declaration//GEN-END:variables
}
