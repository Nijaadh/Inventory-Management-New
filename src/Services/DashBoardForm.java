package Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import static javafx.scene.paint.Color.color;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class DashBoardForm extends javax.swing.JFrame {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    String supGender = "Male";
    String gender = "Male";
    String userType = "User";
    String ps1;
    String ps2;
    String password;
    int tblCount;
    String cusGender = "Male";
    String LoginUser = null;

    public DashBoardForm(String userName) {
        super("DashBoardForm");
        initComponents();
        LoginUser = userName;
        conn = DatabaseConnection.connection();
        lblCurrentUser.setText(userName);
        supId();
        itemId();
        //cusId();
        dt();
        times();
        showRecodsItem();
        showRecodsGRN();
        grn();
        showrecodsReturn();
        Returnct();
        updateCompanyCombo();
        updateCatagoryCombo();
        updateUnitCombo();
        InventoryTotVals();
        InventoryTotCost();
        InventoryItemCount();
        InventoryTodayValue();
        tblCountCustomer();
        comboCompanyAutoComplete();
        comboCatagoryAutoComplete();
        comboUnitAutoComplete();
        comboCompanyNameAutoComplete();
        comboItemNameAutoComplete();
        comboReturnLocationAutoComplete();
        comboCompanyReturnAutoComplete();
        comboItemReturnAutoComplete();
        comboCatagoryReturnAutoComplete();

        comboUnit.setSelectedItem(null);
        comboCatagory.setSelectedItem(null);
        comboCompany.setSelectedItem(null);
        comboCompanyName.setSelectedItem(null);
        comboItemName.setSelectedItem(null);
        comboGRN.setSelectedItem(null);
        comboItemCat.setSelectedItem(null);
        lblSlectedCom.setText("");
        txtRetailPrice.setText(null);
        lblSupplierId.setText("");
        lblItemCode.setText(null);
        rdoPercent.setSelected(true);
        txtGRNvalue.setText("");
        lblUnitCost.setText("");
        lblNewUnitCost.setText("");
        lblTotalCost.setText("");
        lblTotalValue.setText("");
        lblShopMax.setText("");
        lblStore1Max.setText("");
        lblShopStock.setText("");
        lblStore1Stock.setText("");
        lblStore2Stock.setText("");
        lblTotalStock.setText("");
        lblUnit.setText("");
        comboCompanyReturn.setSelectedItem(null);
        comboItemReturn.setSelectedItem(null);
        comboCatagoryReturn.setSelectedItem(null);

        lblItmCode.setVisible(false);

        do {
            btnItemUpdate.setVisible(false);
            btnItemRemove.setVisible(false);
        } while (tableItem.getSelectedRow() > 0);

        do {
            btnOrdrItemRemove.setVisible(false);
        } while (tblViewOrder.getSelectedRow() > 0);

    }

    private DashBoardForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void comboCompanyAutoComplete() {
        AutoCompleteDecorator.decorate(comboCompany);
    }

    public void comboCatagoryAutoComplete() {
        AutoCompleteDecorator.decorate(comboCatagory);
    }

    public void comboUnitAutoComplete() {
        AutoCompleteDecorator.decorate(comboUnit);
    }

    public void comboCompanyNameAutoComplete() {
        AutoCompleteDecorator.decorate(comboCompanyName);
    }

    public void comboItemNameAutoComplete() {
        AutoCompleteDecorator.decorate(comboItemName);
    }

    public void comboGRNAutoComplete() {
        AutoCompleteDecorator.decorate(comboGRN);
    }

    public void comboReturnLocationAutoComplete() {
        AutoCompleteDecorator.decorate(comboReturnLocation);
    }

    public void comboCompanyReturnAutoComplete() {
        AutoCompleteDecorator.decorate(comboCompanyReturn);
    }

    public void comboItemReturnAutoComplete() {
        AutoCompleteDecorator.decorate(comboItemReturn);
    }

    public void comboCatagoryReturnAutoComplete() {
        AutoCompleteDecorator.decorate(comboCatagoryReturn);
    }

    public void showRecods() {
        try {
            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM logindetails";
            ResultSet res = stmt.executeQuery(sqlSelect);
            tableUser.setModel(DbUtils.resultSetToTableModel(res));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
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

    public static boolean validateEmail(String email) {
        // Regular expression pattern for email validation
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        // Regular expression pattern for phone number validation
        
        String number = phoneNumber;
        boolean rslt=false;
        
        if(number.length()!=10){
            rslt = false;
        }
        else{
            
            String phoneNumberPattern = "^(\\+\\d{1,3})?[-.\\s]?\\(?\\d{1,3}\\)?[-.\\s]?\\d{1,3}[-.\\s]?\\d{1,4}$";

        Pattern pattern = Pattern.compile(phoneNumberPattern);
        Matcher matcher = pattern.matcher(phoneNumber);

        rslt =  matcher.matches();
        }
       
            return rslt;
        
  
    }

    public static boolean validateNIC(String nicNumber) {
        if (nicNumber.matches("[0-9]+")) {
            return true;
        }

        return false;
    }

    // Additional validation rules...
    public void InventoryTotVals() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT SUM(totalValue) AS total FROM maininventory";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                double tot = rs.getDouble("total"); // Use "total" instead of "totalValue"              
                lblTotalInventoryValue.setText(String.valueOf(tot));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant get vale");
        }

    }

    public void InventoryTotCost() {
        try {
            stmt = conn.createStatement();
            String sql2 = "SELECT SUM(totalCost) AS cost FROM maininventory";
            rs = stmt.executeQuery(sql2);
            if (rs.next()) {
                double totCos = rs.getDouble("cost"); // Use "total" instead of "totalValue"               
                lblTotalInventoryCost.setText(String.valueOf(totCos));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant get vale");
        }
    }

    public void InventoryItemCount() {
        try {
            stmt = conn.createStatement();
            String sql2 = "SELECT COUNT(itemName) AS count FROM maininventory";
            rs = stmt.executeQuery(sql2);
            if (rs.next()) {
                int count = rs.getInt("count"); // Use "total" instead of "totalValue"               
                lblItemCount.setText(String.valueOf(count));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant get vale");
        }
    }

    public void InventoryTodayValue() {
        try {
            String dt = lblDateToday.getText();

            stmt = conn.createStatement();
            String sql2 = "SELECT SUM(billValue) AS value FROM payment WHERE Date='" + dt + "'";
            rs = stmt.executeQuery(sql2);
            if (rs.next()) {
                double value = rs.getDouble("value"); // Corrected column name to "value"               
                lblTodayValue.setText(String.valueOf(value));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot get value");
        }

    }

    public void showrecodsReturn() {

        try {
            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM returntable";
            ResultSet res = stmt.executeQuery(sqlSelect);
            tblReturns.setModel(DbUtils.resultSetToTableModel(res));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    public void returnCompany() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM company";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String companyName = rs.getString("companyName");
                boolean exists = false;
                for (int i = 0; i < comboCompanyReturn.getItemCount(); i++) {
                    if (companyName.equals(comboCompanyReturn.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboCompanyReturn.addItem(companyName);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void Returnct() {

        String company = (String) comboCompanyReturn.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items  ";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String cat = rs.getString("catagory");
                boolean exists = false;
                for (int i = 0; i < comboCatagoryReturn.getItemCount(); i++) {
                    if (cat.equals(comboCatagoryReturn.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboCatagoryReturn.addItem(cat); // Corrected line
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void ReturnItm() {

        String company = (String) comboCompanyReturn.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items  ";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String item = rs.getString("itemName");
                boolean exists = false;
                for (int i = 0; i < comboItemReturn.getItemCount(); i++) {
                    if (item.equals(comboItemReturn.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboItemReturn.addItem(item); // Corrected line
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void showRecodsCompany() {
        try {

            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM company";
            ResultSet res = stmt.executeQuery(sqlSelect);
            tableSupplier.setModel(DbUtils.resultSetToTableModel(res));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void grn() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM grn";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String grn = rs.getString("grnNo");
                boolean exists = false;
                for (int i = 0; i < comboGRN.getItemCount(); i++) {
                    if (grn.equals(comboGRN.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboGRN.addItem(grn); // Corrected line
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void grnValueLoad() {

        String grn = (String) comboGRN.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM grn WHERE grnNo  = '" + grn + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String val = rs.getString("value");
                txtGRNvalue.setText(val);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void grnNewValueLoad() {

        String grn = txtGrn.getText();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM grn WHERE grnNo  = '" + grn + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String val = rs.getString("value");
                txtGRNvalue.setText(val);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void showRecodsItem() {
        try {
            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM items";
            ResultSet res = stmt.executeQuery(sqlSelect);
            tableItem.setModel(DbUtils.resultSetToTableModel(res));
        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void showRecodsGRN() {
        String grn = null;
        String itmname = null;
        String retail = null;
        String unitcost = null;
        String count = null;
        String discount = null;
        String free = null;
        String mfg = null;
        String exp = null;
        String batchno = null;
        try {
            String itmCode = lblItemCode.getText();
            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM maininventory ";
            ResultSet res = stmt.executeQuery(sqlSelect);
            while (res.next()) {
                grn = res.getString("grnNo");
                itmname = res.getString("itemName");
                retail = res.getString("unitRetail");
                unitcost = res.getString("unitCost");
                count = res.getString("lastAddedQty");
                discount = res.getString("discount");
                free = res.getString("freeIssue");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        try {
            String itmCode = lblItemCode.getText();
            stmt = conn.createStatement();
            String sqlSelect = "SELECT * FROM expiredate ";
            ResultSet res = stmt.executeQuery(sqlSelect);
            while (res.next()) {
                mfg = res.getString("mfg");
                exp = res.getString("exp");
                batchno = res.getString("batchNo");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        String data[] = {grn, itmname, retail, unitcost, count, discount, free, mfg, exp, batchno};
        DefaultTableModel tblModel = (DefaultTableModel) tableInventoryItem.getModel();
        tblModel.addRow(data);
    }

    public void tblCount() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM user;";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tblCount = rs.getInt(1);
                String count = Integer.toString(tblCount);
                lblUserCount.setText(count);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void tblCountSupplier() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM company;";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tblCount = rs.getInt(1);
                //JOptionPane.showMessageDialog(null,tblCount);
                String count = Integer.toString(tblCount);
                lblCompanyCount.setText(count);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void tblCountCustomer() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM customer;";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tblCount = rs.getInt(1);
                //JOptionPane.showMessageDialog(null,tblCount);
                String count = Integer.toString(tblCount);
                lblCustomerCount.setText(count);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void updateCompanyCombo() {
        
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM company";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String companyName = rs.getString("companyName");
                boolean exists = false;
                for (int i = 0; i < comboCompany.getItemCount(); i++) {
                    if (companyName.equals(comboCompany.getItemAt(i)) || companyName.equals(comboCompanyName.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboCompany.addItem(companyName);
                    comboCompanyName.addItem(companyName);
                    comboCom.addItem(companyName);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    public void updateCatagoryCombo() {
//comboCatagory.removeAllItems();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM catagory";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String catagoryName = rs.getString("catagoryName");

                // Check if the item already exists in the combo box
                boolean exists = false;
                for (int i = 0; i < comboCatagory.getItemCount(); i++) {
                    if (catagoryName.equals(comboCatagory.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboCatagory.addItem(catagoryName);
                }
            }
        } catch (Exception e) {
        }
    }

    public void updateUnitCombo() {
       // comboUnit.removeAllItems();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM unit";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String unitName = rs.getString("unitName");
                boolean exists = false;
                for (int i = 0; i < comboUnit.getItemCount(); i++) {
                    if (unitName.equals(comboUnit.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboUnit.addItem(unitName);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //******************************************************************************************************
    public void item() {
        String company = (String) comboCompanyName.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items;";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String item = rs.getString("itemName");

                boolean exists = false;
                for (int i = 0; i < comboItemName.getItemCount(); i++) {
                    if (item.equals(comboItemName.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboItemName.addItem(item);
                    comboItm.addItem(item);// Corrected line
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void comboItemName() {
        String company = (String) comboCompanyName.getSelectedItem();
        comboItemName.removeAllItems();
        String cat = (String) comboItemCat.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE companyName = '" + company + "' && catagory = '" + cat + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String item = rs.getString("itemName");
                boolean exists = false;
                for (int i = 0; i < comboItemName.getItemCount(); i++) {
                    if (item.equals(comboItemName.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboItemName.addItem(item); // Corrected line
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

//**************************************************************************************************
    public void comboItemCat() {  // show all catagories 
        String company = (String) comboCompanyName.getSelectedItem();
        comboItemCat.removeAllItems();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE companyName = '" + company + "' ";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String cat = rs.getString("catagory");
                boolean exists = false;
                for (int i = 0; i < comboItemCat.getItemCount(); i++) {
                    if (cat.equals(comboItemCat.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboItemCat.addItem(cat); // Corrected line
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void cmbItem() {  // show all catagories 
        String company = (String) comboCompanyName.getSelectedItem();
        String ct = (String) comboItemCat.getSelectedItem();
        comboItemName.removeAllItems();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE companyName = '" + company + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String itm = rs.getString("itemName");
                boolean exists = false;
                for (int i = 0; i < comboItemName.getItemCount(); i++) {
                    if (itm.equals(comboItemName.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    comboItemName.addItem(itm); // Corrected line

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

//************************************************************************************************************    
    public void comboUnitType() {

        //comboUnitType.removeAllItems();
        String company = (String) comboCompanyName.getSelectedItem();
        String cat = (String) comboItemCat.getSelectedItem();
        String itmName = (String) comboItemName.getSelectedItem();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE companyName = '" + company + "' && catagory = '" + cat + "' && itemName='" + itmName + "' ";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String unit = rs.getString("unit");
                lblUnit.setText(unit);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    //************************************************RETURN ITEM COMBO SET METHODS*************************************************************//
    //**********************************************ITEM ID && SUP ID********************************************************//
    public void supId() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select MAX(companyId) from company");
            rs.next();
            rs.getString("MAX(companyId)");
            if (rs.getString("MAX(companyId)") == null) {
                lblSupId.setText("Sup-0000001");
                System.out.println("companyId null");
            } else {
                long id = Long.parseLong(rs.getString("MAX(companyId)").substring(4, rs.getString("MAX(companyId)").length()));
                id++;
                lblSupId.setText("Sup-" + String.format("%07d", id));
                System.out.println("id is 00001");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void itemId() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select MAX(itemId) from items");
            rs.next();
            rs.getString("MAX(itemId)");
            if (rs.getString("MAX(itemId)") == null) {
                lblProductId.setText("Pdct-0000001");
                System.out.println("item null");
            } else {
                long id = Long.parseLong(rs.getString("MAX(itemId)").substring(5, rs.getString("MAX(itemId)").length()));
                id++;
                lblProductId.setText("Pdct-" + String.format("%07d", id));
                System.out.println("id is 00001");

            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardForm.class
                    .getName()).log(Level.SEVERE, null, ex);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBackgroundImage = new javax.swing.JLabel();
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
        panelLeftNavigation = new javax.swing.JPanel();
        lblCompanyName = new javax.swing.JLabel();
        panelHome = new javax.swing.JPanel();
        panelHomeSelector = new javax.swing.JPanel();
        lblHome = new javax.swing.JLabel();
        panelUser = new javax.swing.JPanel();
        panelUserSelector = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        panelCustomer = new javax.swing.JPanel();
        panelCustomerSelector = new javax.swing.JPanel();
        lblCustomer = new javax.swing.JLabel();
        panelSupplier = new javax.swing.JPanel();
        panelSupplierSelector = new javax.swing.JPanel();
        lblSupplier = new javax.swing.JLabel();
        panelInventory = new javax.swing.JPanel();
        panelInventorySelector = new javax.swing.JPanel();
        lblInventory = new javax.swing.JLabel();
        panelInvoice = new javax.swing.JPanel();
        panelInvoiceSelector = new javax.swing.JPanel();
        lblInvoice = new javax.swing.JLabel();
        panelReturns = new javax.swing.JPanel();
        panelReturnSelector = new javax.swing.JPanel();
        lblReturn = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TabedPanelMain = new javax.swing.JTabbedPane();
        Home = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        lblTotalInventoryValue = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblTodayValue = new javax.swing.JLabel();
        lblTodayValue1 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jPanel86 = new javax.swing.JPanel();
        lblTotalInventoryCost = new javax.swing.JLabel();
        jPanel87 = new javax.swing.JPanel();
        jLabel130 = new javax.swing.JLabel();
        jPanel88 = new javax.swing.JPanel();
        lblItemCount = new javax.swing.JLabel();
        jPanel89 = new javax.swing.JPanel();
        jPanel90 = new javax.swing.JPanel();
        lblCustomerCount = new javax.swing.JLabel();
        lblTodayValue3 = new javax.swing.JLabel();
        Management = new javax.swing.JPanel();
        tabMiddleNavigation = new javax.swing.JTabbedPane();
        panelMidNaviUser = new javax.swing.JPanel();
        panelMiddleNavigationCenter = new javax.swing.JPanel();
        panelTable = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        lblUserCount = new javax.swing.JLabel();
        panelMiddleNavigationTop = new javax.swing.JPanel();
        panelSerachBar = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JLabel();
        panelUserAdd = new javax.swing.JPanel();
        btnAddUser = new javax.swing.JLabel();
        panelDelete = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JLabel();
        panelUpdate = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnUpdate = new javax.swing.JLabel();
        panelOrder = new javax.swing.JPanel();
        panelOrderSelector = new javax.swing.JPanel();
        lblOrder = new javax.swing.JLabel();
        panelMidNaviSupplier = new javax.swing.JPanel();
        panelMidNaviUser2 = new javax.swing.JPanel();
        panelMiddleNavigationTop1 = new javax.swing.JPanel();
        panelSerachBar1 = new javax.swing.JPanel();
        txtSearch2 = new javax.swing.JTextField();
        btnSearch2 = new javax.swing.JLabel();
        panelUserAdd2 = new javax.swing.JPanel();
        btnAddUser2 = new javax.swing.JLabel();
        panelMiddleNavigationCenter2 = new javax.swing.JPanel();
        panelTable2 = new javax.swing.JScrollPane();
        tableSupplier = new javax.swing.JTable();
        lblCompanyCount = new javax.swing.JLabel();
        panelDelete2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnDelete2 = new javax.swing.JLabel();
        panelUpdate2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnSupUpdate = new javax.swing.JLabel();
        tabRightNavigation = new javax.swing.JTabbedPane();
        panelRightNaviUser = new javax.swing.JPanel();
        panelUserManagement = new javax.swing.JPanel();
        panelUserImage1 = new javax.swing.JPanel();
        lblUserImage = new javax.swing.JLabel();
        scollPanelWarmMessage1 = new javax.swing.JScrollPane();
        txtWarmMessage = new javax.swing.JTextArea();
        scrollPanelAdress1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        panelQuestion1 = new javax.swing.JPanel();
        lblSecurityQuestion = new javax.swing.JLabel();
        comboSecQuestion = new javax.swing.JComboBox<>();
        txtAnswer = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        txtLastName = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        txtNic = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        lblGender = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        lblDob = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        txtContactNo = new javax.swing.JTextField();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        txtEmNumber = new javax.swing.JTextField();
        jSeparator20 = new javax.swing.JSeparator();
        txtEmail = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        lblAccountType = new javax.swing.JLabel();
        rdoPermitedUser = new javax.swing.JRadioButton();
        rdoUser = new javax.swing.JRadioButton();
        lblTerms = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblContactNo = new javax.swing.JLabel();
        lblNic = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblEmNumber = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtRePassword = new javax.swing.JPasswordField();
        lblRepassword = new javax.swing.JLabel();
        jSeparator22 = new javax.swing.JSeparator();
        jSeparator23 = new javax.swing.JSeparator();
        panelShowIcon3 = new javax.swing.JPanel();
        lblHide3 = new javax.swing.JLabel();
        lblshow3 = new javax.swing.JLabel();
        panelShowIcon4 = new javax.swing.JPanel();
        lblHide4 = new javax.swing.JLabel();
        lblshow4 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        panelButtons = new javax.swing.JPanel();
        panelRegisterButtons = new javax.swing.JPanel();
        panelClear1 = new javax.swing.JPanel();
        btnClear = new javax.swing.JLabel();
        panelRegister1 = new javax.swing.JPanel();
        btnRegister = new javax.swing.JLabel();
        panelUpdateButtons = new javax.swing.JPanel();
        panelRegister2 = new javax.swing.JPanel();
        panelUpdateOk = new javax.swing.JLabel();
        panelClear2 = new javax.swing.JPanel();
        btnClear2 = new javax.swing.JLabel();
        CalanderDob = new com.toedter.calendar.JDateChooser();
        panelRightNaviSupplier = new javax.swing.JPanel();
        lblBackgroundImage1 = new javax.swing.JLabel();
        panelSupllierManagemet = new javax.swing.JPanel();
        panelSupllierImage = new javax.swing.JPanel();
        lblSupplierImage = new javax.swing.JLabel();
        panelSupButtons = new javax.swing.JPanel();
        panelSupRegisterButtons = new javax.swing.JPanel();
        panelClear5 = new javax.swing.JPanel();
        btnClear4 = new javax.swing.JLabel();
        panelRegister5 = new javax.swing.JPanel();
        btnSupReg = new javax.swing.JLabel();
        panelSupUpdateButtons = new javax.swing.JPanel();
        panelRegister6 = new javax.swing.JPanel();
        btnSupUpdateOk = new javax.swing.JLabel();
        panelClear6 = new javax.swing.JPanel();
        btnClear5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        scollPanelWarmMessage5 = new javax.swing.JScrollPane();
        txtWarmMessage3 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        scollPanelWarmMessage2 = new javax.swing.JScrollPane();
        txtSupAddress = new javax.swing.JTextArea();
        lblUserName1 = new javax.swing.JLabel();
        txtSupMail = new javax.swing.JTextField();
        lblUserName5 = new javax.swing.JLabel();
        lblUserName4 = new javax.swing.JLabel();
        txtSupContactNo = new javax.swing.JTextField();
        txtSupName = new javax.swing.JTextField();
        lblUserName3 = new javax.swing.JLabel();
        lblUserName2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        lblUserName6 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        jSeparator24 = new javax.swing.JSeparator();
        lblSupId = new javax.swing.JLabel();
        lblUserName14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtRepMail = new javax.swing.JTextField();
        lblUserName7 = new javax.swing.JLabel();
        lblUserName8 = new javax.swing.JLabel();
        txtRepContactNo = new javax.swing.JTextField();
        txtRepFirstName = new javax.swing.JTextField();
        lblUserName9 = new javax.swing.JLabel();
        lblUserName10 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        lblUserName11 = new javax.swing.JLabel();
        txtRepNice = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        lblUserName12 = new javax.swing.JLabel();
        txtRepLastName = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        lblUserName13 = new javax.swing.JLabel();
        rdoSupMale = new javax.swing.JRadioButton();
        rdoSupFemale = new javax.swing.JRadioButton();
        Inventory = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnViewInventory = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        btnLblViewInventory = new javax.swing.JLabel();
        btnShift = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        btnLblShift = new javax.swing.JLabel();
        btnItemReg = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        btnLblItemReg = new javax.swing.JLabel();
        btnAddGrn = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        btnLblAddGrn = new javax.swing.JLabel();
        tabInventory = new javax.swing.JTabbedPane();
        panelReg = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        comboCatagory = new javax.swing.JComboBox<>();
        jPanel27 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        lblCatagorySettings = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        comboUnit = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        lblUnitSettings = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboCompany = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        lblSlectedCom = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lblProductId = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtReOrderLevel = new javax.swing.JTextField();
        txtShopMax = new javax.swing.JTextField();
        txtStore1Max = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        txtItemSearch = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        btItemAdd = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        btnItemUpdate = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        btnItemRemove = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableItem = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        comboItemFilter = new javax.swing.JComboBox<>();
        panalAddGRN = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        lblShopStock = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        lblStore1Stock = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblStore2Stock = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lblTotalStock = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        lblShopMax = new javax.swing.JLabel();
        lblStore1Max = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        lblSupplierId = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lblComName = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        lblItemCode = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        lblCat = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jSeparator25 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jLabel58 = new javax.swing.JLabel();
        lblTotalCost = new javax.swing.JLabel();
        lblTotalValue = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        lblNewUnitCost = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblUnitCost = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        lblUnit = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        comboCompanyName = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtFreeIssue = new javax.swing.JTextField();
        comboItemName = new javax.swing.JComboBox<>();
        jPanel45 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        comboItemCat = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        txtRetailPrice = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtCount = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtBatchNo = new javax.swing.JTextField();
        dteMfg = new com.toedter.calendar.JDateChooser();
        dateExp = new com.toedter.calendar.JDateChooser();
        rdoRupee = new javax.swing.JRadioButton();
        rdoPercent = new javax.swing.JRadioButton();
        jLabel56 = new javax.swing.JLabel();
        txtUnitCost = new javax.swing.JTextField();
        txtGRNvalue = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboGRN = new javax.swing.JComboBox<>();
        txtGrn = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        btnBarcode = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableInventoryItem = new javax.swing.JTable();
        btnGRNUpdate = new javax.swing.JButton();
        panelShift = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        lblShopAvailble = new javax.swing.JLabel();
        lblStore1Available = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblStore2Availble = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblTotLvel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableShift = new javax.swing.JTable();
        jPanel59 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        comboWhere = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        comboTo = new javax.swing.JComboBox<>();
        jPanel58 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        comboCom = new javax.swing.JComboBox<>();
        txtQty = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        comboItm = new javax.swing.JComboBox<>();
        jPanel60 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel63 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        lblShopMaxLevel = new javax.swing.JLabel();
        lblStore1MaxLevel = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        btnItemRemove1 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        btItemAdd3 = new javax.swing.JLabel();
        panelViewInventory = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel64 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
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
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel68 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Return = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblReturns = new javax.swing.JTable();
        jPanel70 = new javax.swing.JPanel();
        comboItemReturn = new javax.swing.JComboBox<>();
        jLabel94 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        txtReturnItemQty = new javax.swing.JTextField();
        jPanel72 = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        comboCompanyReturn = new javax.swing.JComboBox<>();
        jLabel105 = new javax.swing.JLabel();
        comboCatagoryReturn = new javax.swing.JComboBox<>();
        lblComIDReturn = new javax.swing.JLabel();
        lblItemCodeReturn = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        comboReturnLocation = new javax.swing.JComboBox<>();
        jLabel107 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        btnReturn = new javax.swing.JLabel();
        btnReturn1 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jPanel78 = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        txtReturnCompany = new javax.swing.JTextField();
        txtReturnSearch = new javax.swing.JTextField();
        txtreturnSerchCat = new javax.swing.JTextField();
        Order = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        comboItmName = new javax.swing.JComboBox<>();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        txtOrderQty = new javax.swing.JTextField();
        jPanel76 = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        comboCmNAme = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        comboCat = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        lblComID = new javax.swing.JLabel();
        lblItmCode = new javax.swing.JLabel();
        btnOrdrItemRemove = new javax.swing.JButton();
        jLabel123 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jPanel81 = new javax.swing.JPanel();
        jPanel82 = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel126 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox<>();
        jLabel127 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel71 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblPutOrder = new javax.swing.JTable();
        jPanel83 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblViewOrder = new javax.swing.JTable();

        lblBackgroundImage.setBackground(new java.awt.Color(51, 51, 51));
        lblBackgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/image-Person.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelTop.add(panelCurrentUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 20, -1, 30));

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
        jLabel13.setText("Dash Board");
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

        panelTop.add(panelUserIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1520, 20, -1, 30));

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

        panelTop.add(panelUserIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1560, 20, -1, -1));

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

        getContentPane().add(panelTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 1620, 70));

        panelLeftNavigation.setBackground(new java.awt.Color(23, 35, 51));

        lblCompanyName.setBackground(new java.awt.Color(74, 31, 61));
        lblCompanyName.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblCompanyName.setForeground(new java.awt.Color(255, 255, 255));
        lblCompanyName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCompanyName.setText("M L T Holdings");

        panelHome.setBackground(new java.awt.Color(41, 51, 80));

        panelHomeSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelHomeSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelHomeSelectorLayout = new javax.swing.GroupLayout(panelHomeSelector);
        panelHomeSelector.setLayout(panelHomeSelectorLayout);
        panelHomeSelectorLayout.setHorizontalGroup(
            panelHomeSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        panelHomeSelectorLayout.setVerticalGroup(
            panelHomeSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblHome.setBackground(new java.awt.Color(71, 120, 197));
        lblHome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHome.setText("Home");
        lblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelHomeLayout = new javax.swing.GroupLayout(panelHome);
        panelHome.setLayout(panelHomeLayout);
        panelHomeLayout.setHorizontalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addComponent(panelHomeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panelHomeLayout.setVerticalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHomeSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomeLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelUser.setBackground(new java.awt.Color(41, 51, 80));

        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelUserSelectorLayout = new javax.swing.GroupLayout(panelUserSelector);
        panelUserSelector.setLayout(panelUserSelectorLayout);
        panelUserSelectorLayout.setHorizontalGroup(
            panelUserSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        panelUserSelectorLayout.setVerticalGroup(
            panelUserSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblUser.setBackground(new java.awt.Color(71, 120, 197));
        lblUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("User Management");
        lblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUserLayout = new javax.swing.GroupLayout(panelUser);
        panelUser.setLayout(panelUserLayout);
        panelUserLayout.setHorizontalGroup(
            panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserLayout.createSequentialGroup()
                .addComponent(panelUserSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUserLayout.setVerticalGroup(
            panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUserSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCustomer.setBackground(new java.awt.Color(41, 51, 80));

        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelCustomerSelectorLayout = new javax.swing.GroupLayout(panelCustomerSelector);
        panelCustomerSelector.setLayout(panelCustomerSelectorLayout);
        panelCustomerSelectorLayout.setHorizontalGroup(
            panelCustomerSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        panelCustomerSelectorLayout.setVerticalGroup(
            panelCustomerSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblCustomer.setBackground(new java.awt.Color(71, 120, 197));
        lblCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCustomer.setText("Customer Management");
        lblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCustomerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelCustomerLayout = new javax.swing.GroupLayout(panelCustomer);
        panelCustomer.setLayout(panelCustomerLayout);
        panelCustomerLayout.setHorizontalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addComponent(panelCustomerSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCustomerLayout.setVerticalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCustomerSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelSupplier.setBackground(new java.awt.Color(41, 51, 80));

        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelSupplierSelectorLayout = new javax.swing.GroupLayout(panelSupplierSelector);
        panelSupplierSelector.setLayout(panelSupplierSelectorLayout);
        panelSupplierSelectorLayout.setHorizontalGroup(
            panelSupplierSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panelSupplierSelectorLayout.setVerticalGroup(
            panelSupplierSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblSupplier.setBackground(new java.awt.Color(71, 120, 197));
        lblSupplier.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSupplier.setText("Supplier Management");
        lblSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSupplierMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelSupplierLayout = new javax.swing.GroupLayout(panelSupplier);
        panelSupplier.setLayout(panelSupplierLayout);
        panelSupplierLayout.setHorizontalGroup(
            panelSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSupplierLayout.createSequentialGroup()
                .addComponent(panelSupplierSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSupplierLayout.setVerticalGroup(
            panelSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSupplierSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSupplierLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelInventory.setBackground(new java.awt.Color(41, 51, 80));

        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelInventorySelectorLayout = new javax.swing.GroupLayout(panelInventorySelector);
        panelInventorySelector.setLayout(panelInventorySelectorLayout);
        panelInventorySelectorLayout.setHorizontalGroup(
            panelInventorySelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        panelInventorySelectorLayout.setVerticalGroup(
            panelInventorySelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblInventory.setBackground(new java.awt.Color(71, 120, 197));
        lblInventory.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInventory.setText("Inventory Management");
        lblInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInventoryMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelInventoryLayout = new javax.swing.GroupLayout(panelInventory);
        panelInventory.setLayout(panelInventoryLayout);
        panelInventoryLayout.setHorizontalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventoryLayout.createSequentialGroup()
                .addComponent(panelInventorySelector, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInventoryLayout.setVerticalGroup(
            panelInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelInventorySelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInventoryLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelInvoice.setBackground(new java.awt.Color(41, 51, 80));

        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelInvoiceSelectorLayout = new javax.swing.GroupLayout(panelInvoiceSelector);
        panelInvoiceSelector.setLayout(panelInvoiceSelectorLayout);
        panelInvoiceSelectorLayout.setHorizontalGroup(
            panelInvoiceSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        panelInvoiceSelectorLayout.setVerticalGroup(
            panelInvoiceSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblInvoice.setBackground(new java.awt.Color(71, 120, 197));
        lblInvoice.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInvoice.setText("Make Invoice");
        lblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInvoiceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelInvoiceLayout = new javax.swing.GroupLayout(panelInvoice);
        panelInvoice.setLayout(panelInvoiceLayout);
        panelInvoiceLayout.setHorizontalGroup(
            panelInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInvoiceLayout.createSequentialGroup()
                .addComponent(panelInvoiceSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInvoiceLayout.setVerticalGroup(
            panelInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelInvoiceSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInvoiceLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelReturns.setBackground(new java.awt.Color(41, 51, 80));

        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelReturnSelectorLayout = new javax.swing.GroupLayout(panelReturnSelector);
        panelReturnSelector.setLayout(panelReturnSelectorLayout);
        panelReturnSelectorLayout.setHorizontalGroup(
            panelReturnSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        panelReturnSelectorLayout.setVerticalGroup(
            panelReturnSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblReturn.setBackground(new java.awt.Color(71, 120, 197));
        lblReturn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReturn.setText("Make Returns");
        lblReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReturnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelReturnsLayout = new javax.swing.GroupLayout(panelReturns);
        panelReturns.setLayout(panelReturnsLayout);
        panelReturnsLayout.setHorizontalGroup(
            panelReturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReturnsLayout.createSequentialGroup()
                .addComponent(panelReturnSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelReturnsLayout.setVerticalGroup(
            panelReturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelReturnSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReturnsLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_europe_124px.png"))); // NOI18N

        javax.swing.GroupLayout panelLeftNavigationLayout = new javax.swing.GroupLayout(panelLeftNavigation);
        panelLeftNavigation.setLayout(panelLeftNavigationLayout);
        panelLeftNavigationLayout.setHorizontalGroup(
            panelLeftNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelReturns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelLeftNavigationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelLeftNavigationLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLeftNavigationLayout.setVerticalGroup(
            panelLeftNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeftNavigationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCompanyName)
                .addGap(18, 18, 18)
                .addComponent(panelHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelReturns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        getContentPane().add(panelLeftNavigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 880));

        Home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel84.setBackground(new java.awt.Color(41, 51, 80));

        lblTotalInventoryValue.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblTotalInventoryValue.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalInventoryValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalInventoryValue.setText("0000");

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setText("Total Inventory Value :");

        jPanel75.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel84Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(233, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalInventoryValue, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38)
                .addComponent(lblTotalInventoryValue, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addComponent(jPanel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Home.add(jPanel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 40, -1, -1));

        jPanel5.setBackground(new java.awt.Color(41, 51, 80));

        jPanel7.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTodayValue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTodayValue.setForeground(new java.awt.Color(255, 255, 255));
        lblTodayValue.setText("000");

        lblTodayValue1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTodayValue1.setForeground(new java.awt.Color(255, 255, 255));
        lblTodayValue1.setText("Today revanue :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 141, Short.MAX_VALUE)
                        .addComponent(lblTodayValue, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblTodayValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTodayValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(lblTodayValue, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Home.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 676, -1, -1));

        jPanel85.setBackground(new java.awt.Color(41, 51, 80));

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Total Inventory Cost :");

        jPanel86.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblTotalInventoryCost.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblTotalInventoryCost.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalInventoryCost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalInventoryCost.setText("0000");

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel85Layout.createSequentialGroup()
                .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel85Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(lblTotalInventoryCost, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(lblTotalInventoryCost, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addComponent(jPanel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Home.add(jPanel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, -1, -1));

        jPanel87.setBackground(new java.awt.Color(41, 51, 80));

        jLabel130.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setText("Total Item Count :");

        jPanel88.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout jPanel88Layout = new javax.swing.GroupLayout(jPanel88);
        jPanel88.setLayout(jPanel88Layout);
        jPanel88Layout.setHorizontalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel88Layout.setVerticalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblItemCount.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblItemCount.setForeground(new java.awt.Color(255, 255, 255));
        lblItemCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblItemCount.setText("0000");

        javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel87Layout.createSequentialGroup()
                .addComponent(jPanel88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel87Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(lblItemCount, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel87Layout.createSequentialGroup()
                .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28)
                .addComponent(lblItemCount, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addComponent(jPanel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Home.add(jPanel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jPanel89.setBackground(new java.awt.Color(41, 51, 80));

        jPanel90.setBackground(new java.awt.Color(71, 120, 197));

        javax.swing.GroupLayout jPanel90Layout = new javax.swing.GroupLayout(jPanel90);
        jPanel90.setLayout(jPanel90Layout);
        jPanel90Layout.setHorizontalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        jPanel90Layout.setVerticalGroup(
            jPanel90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblCustomerCount.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCustomerCount.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomerCount.setText("000");

        lblTodayValue3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTodayValue3.setForeground(new java.awt.Color(255, 255, 255));
        lblTodayValue3.setText("Registerd Customer:");

        javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
        jPanel89.setLayout(jPanel89Layout);
        jPanel89Layout.setHorizontalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addComponent(jPanel90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel89Layout.createSequentialGroup()
                        .addGap(0, 141, Short.MAX_VALUE)
                        .addComponent(lblCustomerCount, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel89Layout.createSequentialGroup()
                        .addComponent(lblTodayValue3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel89Layout.setVerticalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTodayValue3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(lblCustomerCount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Home.add(jPanel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 561, -1, 90));

        TabedPanelMain.addTab("Home", Home);

        Management.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMidNaviUser.setBackground(new java.awt.Color(71, 120, 197));
        panelMidNaviUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMiddleNavigationCenter.setBackground(new java.awt.Color(84, 127, 206));

        tableUser.setBackground(new java.awt.Color(84, 127, 206));
        tableUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableUser.setForeground(new java.awt.Color(255, 255, 255));
        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", ""},
                {"", "", "", ""},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "User ID", "Name", "User Name", "User Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableUser.setGridColor(new java.awt.Color(84, 127, 206));
        tableUser.setRowHeight(30);
        tableUser.setRowMargin(2);
        tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUserMouseClicked(evt);
            }
        });
        panelTable.setViewportView(tableUser);

        lblUserCount.setFont(new java.awt.Font("Segoe UI", 0, 72)); // NOI18N
        lblUserCount.setForeground(new java.awt.Color(255, 255, 255));
        lblUserCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelMiddleNavigationCenterLayout = new javax.swing.GroupLayout(panelMiddleNavigationCenter);
        panelMiddleNavigationCenter.setLayout(panelMiddleNavigationCenterLayout);
        panelMiddleNavigationCenterLayout.setHorizontalGroup(
            panelMiddleNavigationCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationCenterLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(lblUserCount, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
            .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelMiddleNavigationCenterLayout.setVerticalGroup(
            panelMiddleNavigationCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationCenterLayout.createSequentialGroup()
                .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(lblUserCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelMidNaviUser.add(panelMiddleNavigationCenter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 142, -1, 430));

        panelMiddleNavigationTop.setBackground(new java.awt.Color(120, 168, 252));

        panelSerachBar.setBackground(new java.awt.Color(141, 182, 253));
        panelSerachBar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtSearch.setBackground(new java.awt.Color(165, 197, 253));
        txtSearch.setBorder(null);
        txtSearch.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtSearchInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_search_24px.png"))); // NOI18N
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelSerachBarLayout = new javax.swing.GroupLayout(panelSerachBar);
        panelSerachBar.setLayout(panelSerachBarLayout);
        panelSerachBarLayout.setHorizontalGroup(
            panelSerachBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSerachBarLayout.createSequentialGroup()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch))
        );
        panelSerachBarLayout.setVerticalGroup(
            panelSerachBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelUserAdd.setBackground(new java.awt.Color(120, 168, 252));

        btnAddUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_add_user_male_32px_1.png"))); // NOI18N
        btnAddUser.setToolTipText("Add Users");
        btnAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUserAddLayout = new javax.swing.GroupLayout(panelUserAdd);
        panelUserAdd.setLayout(panelUserAddLayout);
        panelUserAddLayout.setHorizontalGroup(
            panelUserAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelUserAddLayout.setVerticalGroup(
            panelUserAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddUser)
        );

        javax.swing.GroupLayout panelMiddleNavigationTopLayout = new javax.swing.GroupLayout(panelMiddleNavigationTop);
        panelMiddleNavigationTop.setLayout(panelMiddleNavigationTopLayout);
        panelMiddleNavigationTopLayout.setHorizontalGroup(
            panelMiddleNavigationTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationTopLayout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(panelSerachBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelUserAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        panelMiddleNavigationTopLayout.setVerticalGroup(
            panelMiddleNavigationTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationTopLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelMiddleNavigationTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelUserAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelSerachBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        panelMidNaviUser.add(panelMiddleNavigationTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 9, -1, 120));

        panelDelete.setBackground(new java.awt.Color(84, 108, 155));

        jPanel6.setBackground(new java.awt.Color(68, 194, 137));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_no_user_32px.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setToolTipText("Delete User");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelDeleteLayout = new javax.swing.GroupLayout(panelDelete);
        panelDelete.setLayout(panelDeleteLayout);
        panelDeleteLayout.setHorizontalGroup(
            panelDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDeleteLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDeleteLayout.setVerticalGroup(
            panelDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDeleteLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelMidNaviUser.add(panelDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 700, -1, -1));

        panelUpdate.setBackground(new java.awt.Color(84, 108, 155));

        jPanel4.setBackground(new java.awt.Color(68, 194, 137));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_registration_32px_1.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setToolTipText("Update User Details");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUpdateLayout = new javax.swing.GroupLayout(panelUpdate);
        panelUpdate.setLayout(panelUpdateLayout);
        panelUpdateLayout.setHorizontalGroup(
            panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUpdateLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelUpdateLayout.setVerticalGroup(
            panelUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelMidNaviUser.add(panelUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(211, 624, -1, -1));

        panelOrder.setBackground(new java.awt.Color(41, 51, 80));

        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setPreferredSize(new java.awt.Dimension(5, 72));

        javax.swing.GroupLayout panelOrderSelectorLayout = new javax.swing.GroupLayout(panelOrderSelector);
        panelOrderSelector.setLayout(panelOrderSelectorLayout);
        panelOrderSelectorLayout.setHorizontalGroup(
            panelOrderSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        panelOrderSelectorLayout.setVerticalGroup(
            panelOrderSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblOrder.setBackground(new java.awt.Color(71, 120, 197));
        lblOrder.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOrder.setText("Order Management");
        lblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOrderMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addComponent(panelOrderSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelOrderSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelMidNaviUser.add(panelOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 457, 356, -1));

        tabMiddleNavigation.addTab("tab2", panelMidNaviUser);

        panelMidNaviUser2.setBackground(new java.awt.Color(71, 120, 197));

        panelMiddleNavigationTop1.setBackground(new java.awt.Color(120, 168, 252));

        panelSerachBar1.setBackground(new java.awt.Color(141, 182, 253));
        panelSerachBar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        txtSearch2.setBackground(new java.awt.Color(165, 197, 253));
        txtSearch2.setBorder(null);

        btnSearch2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSearch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_search_24px.png"))); // NOI18N

        javax.swing.GroupLayout panelSerachBar1Layout = new javax.swing.GroupLayout(panelSerachBar1);
        panelSerachBar1.setLayout(panelSerachBar1Layout);
        panelSerachBar1Layout.setHorizontalGroup(
            panelSerachBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSerachBar1Layout.createSequentialGroup()
                .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch2))
        );
        panelSerachBar1Layout.setVerticalGroup(
            panelSerachBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelUserAdd2.setBackground(new java.awt.Color(120, 168, 252));

        btnAddUser2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAddUser2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_add_user_male_32px_1.png"))); // NOI18N
        btnAddUser2.setToolTipText("Add Users");
        btnAddUser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUser2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUserAdd2Layout = new javax.swing.GroupLayout(panelUserAdd2);
        panelUserAdd2.setLayout(panelUserAdd2Layout);
        panelUserAdd2Layout.setHorizontalGroup(
            panelUserAdd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelUserAdd2Layout.setVerticalGroup(
            panelUserAdd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddUser2)
        );

        javax.swing.GroupLayout panelMiddleNavigationTop1Layout = new javax.swing.GroupLayout(panelMiddleNavigationTop1);
        panelMiddleNavigationTop1.setLayout(panelMiddleNavigationTop1Layout);
        panelMiddleNavigationTop1Layout.setHorizontalGroup(
            panelMiddleNavigationTop1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationTop1Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(panelSerachBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelUserAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        panelMiddleNavigationTop1Layout.setVerticalGroup(
            panelMiddleNavigationTop1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationTop1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelMiddleNavigationTop1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelUserAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelSerachBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        panelMiddleNavigationCenter2.setBackground(new java.awt.Color(84, 127, 206));

        tableSupplier.setBackground(new java.awt.Color(84, 127, 206));
        tableSupplier.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableSupplier.setForeground(new java.awt.Color(255, 255, 255));
        tableSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", ""},
                {"", "", "", ""},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "User ID", "Name", "User Name", "User Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSupplier.setGridColor(new java.awt.Color(84, 127, 206));
        tableSupplier.setRowHeight(30);
        tableSupplier.setRowMargin(2);
        tableSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSupplierMouseClicked(evt);
            }
        });
        panelTable2.setViewportView(tableSupplier);

        lblCompanyCount.setFont(new java.awt.Font("Segoe UI", 0, 72)); // NOI18N
        lblCompanyCount.setForeground(new java.awt.Color(255, 255, 255));
        lblCompanyCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCompanyCount.setText("0");

        javax.swing.GroupLayout panelMiddleNavigationCenter2Layout = new javax.swing.GroupLayout(panelMiddleNavigationCenter2);
        panelMiddleNavigationCenter2.setLayout(panelMiddleNavigationCenter2Layout);
        panelMiddleNavigationCenter2Layout.setHorizontalGroup(
            panelMiddleNavigationCenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationCenter2Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(lblCompanyCount, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
            .addComponent(panelTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelMiddleNavigationCenter2Layout.setVerticalGroup(
            panelMiddleNavigationCenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMiddleNavigationCenter2Layout.createSequentialGroup()
                .addComponent(panelTable2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCompanyCount, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDelete2.setBackground(new java.awt.Color(84, 108, 155));

        jPanel8.setBackground(new java.awt.Color(68, 194, 137));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        btnDelete2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete2.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_no_user_32px.png"))); // NOI18N
        btnDelete2.setText("Delete");
        btnDelete2.setToolTipText("Delete User");
        btnDelete2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelete2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelDelete2Layout = new javax.swing.GroupLayout(panelDelete2);
        panelDelete2.setLayout(panelDelete2Layout);
        panelDelete2Layout.setHorizontalGroup(
            panelDelete2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDelete2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnDelete2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelDelete2Layout.setVerticalGroup(
            panelDelete2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDelete2Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnDelete2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelUpdate2.setBackground(new java.awt.Color(84, 108, 155));

        jPanel9.setBackground(new java.awt.Color(68, 194, 137));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        btnSupUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSupUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnSupUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_registration_32px_1.png"))); // NOI18N
        btnSupUpdate.setText("Update");
        btnSupUpdate.setToolTipText("Update User Details");
        btnSupUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupUpdateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelUpdate2Layout = new javax.swing.GroupLayout(panelUpdate2);
        panelUpdate2.setLayout(panelUpdate2Layout);
        panelUpdate2Layout.setHorizontalGroup(
            panelUpdate2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUpdate2Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(btnSupUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelUpdate2Layout.setVerticalGroup(
            panelUpdate2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSupUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout panelMidNaviUser2Layout = new javax.swing.GroupLayout(panelMidNaviUser2);
        panelMidNaviUser2.setLayout(panelMidNaviUser2Layout);
        panelMidNaviUser2Layout.setHorizontalGroup(
            panelMidNaviUser2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMiddleNavigationTop1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelMiddleNavigationCenter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMidNaviUser2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMidNaviUser2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelUpdate2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDelete2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelMidNaviUser2Layout.setVerticalGroup(
            panelMidNaviUser2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMidNaviUser2Layout.createSequentialGroup()
                .addComponent(panelMiddleNavigationTop1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelMiddleNavigationCenter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelUpdate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(panelDelete2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout panelMidNaviSupplierLayout = new javax.swing.GroupLayout(panelMidNaviSupplier);
        panelMidNaviSupplier.setLayout(panelMidNaviSupplierLayout);
        panelMidNaviSupplierLayout.setHorizontalGroup(
            panelMidNaviSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
            .addGroup(panelMidNaviSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMidNaviSupplierLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelMidNaviUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelMidNaviSupplierLayout.setVerticalGroup(
            panelMidNaviSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGroup(panelMidNaviSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMidNaviSupplierLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelMidNaviUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tabMiddleNavigation.addTab("tab4", panelMidNaviSupplier);

        Management.add(tabMiddleNavigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 900));

        panelRightNaviUser.setBackground(new java.awt.Color(51, 51, 51));
        panelRightNaviUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUserManagement.setBackground(new java.awt.Color(51, 51, 51));
        panelUserManagement.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUserImage1.setBackground(new java.awt.Color(51, 51, 51));
        panelUserImage1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        lblUserImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelUserImage1Layout = new javax.swing.GroupLayout(panelUserImage1);
        panelUserImage1.setLayout(panelUserImage1Layout);
        panelUserImage1Layout.setHorizontalGroup(
            panelUserImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserImage, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );
        panelUserImage1Layout.setVerticalGroup(
            panelUserImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserImage, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );

        panelUserManagement.add(panelUserImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(887, 31, -1, -1));

        txtWarmMessage.setEditable(false);
        txtWarmMessage.setBackground(new java.awt.Color(51, 51, 51));
        txtWarmMessage.setColumns(20);
        txtWarmMessage.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtWarmMessage.setForeground(new java.awt.Color(255, 255, 255));
        txtWarmMessage.setRows(5);
        txtWarmMessage.setText("* Welcome to MLT Holdings Registration!\n   We'are delighted that you have choosen to join our shop comunity. By creating an account,\n   You will unlock a convenient features tailored just for you.\n   Please take a moment to provide us with some details to get started.");
        txtWarmMessage.setBorder(null);
        scollPanelWarmMessage1.setViewportView(txtWarmMessage);

        panelUserManagement.add(scollPanelWarmMessage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 736, 135));

        txtAddress.setBackground(new java.awt.Color(51, 51, 51));
        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(255, 255, 255));
        txtAddress.setRows(5);
        txtAddress.setText("No. 00/000 StreatName\nHometown\nPostalArea");
        txtAddress.setBorder(null);
        scrollPanelAdress1.setViewportView(txtAddress);

        panelUserManagement.add(scrollPanelAdress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 641, 382, -1));

        panelQuestion1.setBackground(new java.awt.Color(51, 51, 51));
        panelQuestion1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        lblSecurityQuestion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSecurityQuestion.setForeground(new java.awt.Color(255, 255, 255));
        lblSecurityQuestion.setText("Security Question ?");

        comboSecQuestion.setBackground(new java.awt.Color(51, 51, 51));
        comboSecQuestion.setForeground(new java.awt.Color(74, 31, 61));
        comboSecQuestion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "what is your Faviorite Colour ?", "what is your first phone No ?", "who is your faviorite person ?" }));

        txtAnswer.setBackground(new java.awt.Color(51, 51, 51));
        txtAnswer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAnswer.setForeground(new java.awt.Color(255, 255, 255));
        txtAnswer.setText("Answer");
        txtAnswer.setBorder(null);

        jSeparator14.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelQuestion1Layout = new javax.swing.GroupLayout(panelQuestion1);
        panelQuestion1.setLayout(panelQuestion1Layout);
        panelQuestion1Layout.setHorizontalGroup(
            panelQuestion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuestion1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelQuestion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelQuestion1Layout.createSequentialGroup()
                        .addGroup(panelQuestion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSecurityQuestion)
                            .addComponent(comboSecQuestion, 0, 331, Short.MAX_VALUE)
                            .addComponent(txtAnswer))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator14))
                .addContainerGap())
        );
        panelQuestion1Layout.setVerticalGroup(
            panelQuestion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQuestion1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSecurityQuestion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboSecQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(txtAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        panelUserManagement.add(panelQuestion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 446, -1, -1));

        txtLastName.setBackground(new java.awt.Color(51, 51, 51));
        txtLastName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(255, 255, 255));
        txtLastName.setText("Nijaadh");
        txtLastName.setBorder(null);
        panelUserManagement.add(txtLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 265, 234, 35));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 313, 350, -1));
        panelUserManagement.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 313, 277, -1));

        txtNic.setBackground(new java.awt.Color(51, 51, 51));
        txtNic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNic.setForeground(new java.awt.Color(255, 255, 255));
        txtNic.setText("200021701711");
        txtNic.setBorder(null);
        panelUserManagement.add(txtNic, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 333, 232, 35));

        jSeparator16.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 377, 350, -1));

        lblGender.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGender.setForeground(new java.awt.Color(255, 255, 255));
        lblGender.setText("Gender :");
        panelUserManagement.add(lblGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(419, 357, -1, -1));

        rdoMale.setBackground(new java.awt.Color(51, 51, 51));
        rdoMale.setForeground(new java.awt.Color(255, 255, 255));
        rdoMale.setSelected(true);
        rdoMale.setText("Male");
        rdoMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMaleActionPerformed(evt);
            }
        });
        panelUserManagement.add(rdoMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 354, -1, -1));

        rdoFemale.setBackground(new java.awt.Color(51, 51, 51));
        rdoFemale.setForeground(new java.awt.Color(255, 255, 255));
        rdoFemale.setText("Female");
        rdoFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoFemaleActionPerformed(evt);
            }
        });
        panelUserManagement.add(rdoFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(611, 354, -1, -1));

        lblDob.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDob.setForeground(new java.awt.Color(255, 255, 255));
        lblDob.setText("Date of Birth :");
        panelUserManagement.add(lblDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 392, 102, -1));

        jSeparator17.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 435, 350, -1));

        txtContactNo.setBackground(new java.awt.Color(51, 51, 51));
        txtContactNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtContactNo.setForeground(new java.awt.Color(255, 255, 255));
        txtContactNo.setText("0774411765");
        txtContactNo.setBorder(null);
        panelUserManagement.add(txtContactNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 454, 237, 35));

        jSeparator18.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 496, 351, -1));

        jSeparator19.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 496, 277, 10));

        txtEmNumber.setBackground(new java.awt.Color(51, 51, 51));
        txtEmNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmNumber.setForeground(new java.awt.Color(255, 255, 255));
        txtEmNumber.setText("0766470486");
        txtEmNumber.setBorder(null);
        panelUserManagement.add(txtEmNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 446, 132, 35));

        jSeparator20.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 555, 349, -1));

        txtEmail.setBackground(new java.awt.Color(51, 51, 51));
        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setText("mohomednijaadh.net@gmail.com");
        txtEmail.setBorder(null);
        panelUserManagement.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 513, 292, 35));

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress.setText("Current Adress :");
        panelUserManagement.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 595, -1, -1));

        txtUserName.setBackground(new java.awt.Color(51, 51, 51));
        txtUserName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUserName.setForeground(new java.awt.Color(255, 255, 255));
        txtUserName.setText("Makuwa");
        txtUserName.setBorder(null);
        panelUserManagement.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(911, 257, 196, 35));

        jSeparator21.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 420, 336, -1));

        lblAccountType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAccountType.setForeground(new java.awt.Color(255, 255, 255));
        lblAccountType.setText("Account Type :");
        panelUserManagement.add(lblAccountType, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 653, -1, -1));

        rdoPermitedUser.setBackground(new java.awt.Color(51, 51, 51));
        rdoPermitedUser.setForeground(new java.awt.Color(255, 255, 255));
        rdoPermitedUser.setText("Permited User");
        rdoPermitedUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPermitedUserActionPerformed(evt);
            }
        });
        panelUserManagement.add(rdoPermitedUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 650, -1, -1));

        rdoUser.setBackground(new java.awt.Color(51, 51, 51));
        rdoUser.setForeground(new java.awt.Color(255, 255, 255));
        rdoUser.setSelected(true);
        rdoUser.setText("User");
        rdoUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoUserActionPerformed(evt);
            }
        });
        panelUserManagement.add(rdoUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 650, -1, -1));

        lblTerms.setBackground(new java.awt.Color(51, 51, 51));
        lblTerms.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTerms.setForeground(new java.awt.Color(255, 255, 255));
        lblTerms.setText("Terms and Conditions...");
        panelUserManagement.add(lblTerms, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 693, -1, -1));

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("Email :");
        panelUserManagement.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 522, -1, -1));

        lblContactNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblContactNo.setForeground(new java.awt.Color(255, 255, 255));
        lblContactNo.setText("Contact Number :");
        panelUserManagement.add(lblContactNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 463, -1, -1));

        lblNic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNic.setForeground(new java.awt.Color(255, 255, 255));
        lblNic.setText("NIC NO :");
        panelUserManagement.add(lblNic, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 342, -1, -1));

        lblFirstName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("First Name :");
        panelUserManagement.add(lblFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 278, -1, -1));

        lblLastName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name :");
        panelUserManagement.add(lblLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 274, -1, -1));

        lblEmNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblEmNumber.setText("Emegancy Number :");
        panelUserManagement.add(lblEmNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 455, -1, -1));

        lblUserName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName.setText("User Name :");
        panelUserManagement.add(lblUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 266, -1, -1));

        lblPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Enter Password :");
        panelUserManagement.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 327, -1, -1));

        txtPassword.setBackground(new java.awt.Color(51, 51, 51));
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setText("**********");
        txtPassword.setBorder(null);
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
        });
        panelUserManagement.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(914, 328, 166, -1));

        txtRePassword.setBackground(new java.awt.Color(51, 51, 51));
        txtRePassword.setForeground(new java.awt.Color(255, 255, 255));
        txtRePassword.setText("**********");
        txtRePassword.setBorder(null);
        txtRePassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRePasswordFocusGained(evt);
            }
        });
        panelUserManagement.add(txtRePassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(913, 387, 167, -1));

        lblRepassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRepassword.setForeground(new java.awt.Color(255, 255, 255));
        lblRepassword.setText("Re Enter Password :");
        panelUserManagement.add(lblRepassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 392, -1, -1));

        jSeparator22.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 357, 336, -1));

        jSeparator23.setForeground(new java.awt.Color(255, 255, 255));
        panelUserManagement.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 292, 336, -1));

        panelShowIcon3.setBackground(new java.awt.Color(51, 51, 51));
        panelShowIcon3.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        panelShowIcon3.setToolTipText("");
        panelShowIcon3.setPreferredSize(new java.awt.Dimension(16, 16));
        panelShowIcon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelShowIcon3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelShowIcon3MouseReleased(evt);
            }
        });

        lblHide3.setBackground(new java.awt.Color(51, 51, 51));
        lblHide3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHide3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/eye-crossed.png"))); // NOI18N

        lblshow3.setBackground(new java.awt.Color(51, 51, 51));
        lblshow3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblshow3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/eye.png"))); // NOI18N

        javax.swing.GroupLayout panelShowIcon3Layout = new javax.swing.GroupLayout(panelShowIcon3);
        panelShowIcon3.setLayout(panelShowIcon3Layout);
        panelShowIcon3Layout.setHorizontalGroup(
            panelShowIcon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShowIcon3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblshow3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelShowIcon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelShowIcon3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblHide3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelShowIcon3Layout.setVerticalGroup(
            panelShowIcon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblshow3, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
            .addGroup(panelShowIcon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelShowIcon3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblHide3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelUserManagement.add(panelShowIcon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1087, 327, 20, 20));

        panelShowIcon4.setBackground(new java.awt.Color(51, 51, 51));
        panelShowIcon4.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        panelShowIcon4.setToolTipText("");
        panelShowIcon4.setPreferredSize(new java.awt.Dimension(16, 16));
        panelShowIcon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelShowIcon4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelShowIcon4MouseReleased(evt);
            }
        });

        lblHide4.setBackground(new java.awt.Color(51, 51, 51));
        lblHide4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHide4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/eye-crossed.png"))); // NOI18N

        lblshow4.setBackground(new java.awt.Color(51, 51, 51));
        lblshow4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblshow4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/eye.png"))); // NOI18N

        javax.swing.GroupLayout panelShowIcon4Layout = new javax.swing.GroupLayout(panelShowIcon4);
        panelShowIcon4.setLayout(panelShowIcon4Layout);
        panelShowIcon4Layout.setHorizontalGroup(
            panelShowIcon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShowIcon4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblshow4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelShowIcon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelShowIcon4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblHide4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelShowIcon4Layout.setVerticalGroup(
            panelShowIcon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblshow4, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
            .addGroup(panelShowIcon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelShowIcon4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblHide4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelUserManagement.add(panelShowIcon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1087, 387, 20, 20));

        txtFirstName.setBackground(new java.awt.Color(51, 51, 51));
        txtFirstName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(255, 255, 255));
        txtFirstName.setText("Mohomed");
        txtFirstName.setBorder(null);
        panelUserManagement.add(txtFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 266, 180, 34));

        panelButtons.setBackground(new java.awt.Color(51, 51, 51));
        panelButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRegisterButtons.setBackground(new java.awt.Color(51, 51, 51));
        panelRegisterButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelClear1.setBackground(new java.awt.Color(51, 51, 51));
        panelClear1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnClear.setBackground(new java.awt.Color(51, 51, 51));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear.setText("Clear");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClear1Layout = new javax.swing.GroupLayout(panelClear1);
        panelClear1.setLayout(panelClear1Layout);
        panelClear1Layout.setHorizontalGroup(
            panelClear1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelClear1Layout.setVerticalGroup(
            panelClear1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelRegisterButtons.add(panelClear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 0, -1, -1));

        panelRegister1.setBackground(new java.awt.Color(51, 51, 51));
        panelRegister1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnRegister.setBackground(new java.awt.Color(51, 51, 51));
        btnRegister.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRegister.setText("Register");
        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegisterMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRegister1Layout = new javax.swing.GroupLayout(panelRegister1);
        panelRegister1.setLayout(panelRegister1Layout);
        panelRegister1Layout.setHorizontalGroup(
            panelRegister1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelRegister1Layout.setVerticalGroup(
            panelRegister1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelRegisterButtons.add(panelRegister1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelButtons.add(panelRegisterButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, -1, -1));

        panelUpdateButtons.setBackground(new java.awt.Color(51, 51, 51));
        panelUpdateButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRegister2.setBackground(new java.awt.Color(51, 51, 51));
        panelRegister2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        panelUpdateOk.setBackground(new java.awt.Color(51, 51, 51));
        panelUpdateOk.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        panelUpdateOk.setForeground(new java.awt.Color(255, 255, 255));
        panelUpdateOk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelUpdateOk.setText("Update");
        panelUpdateOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelUpdateOkMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRegister2Layout = new javax.swing.GroupLayout(panelRegister2);
        panelRegister2.setLayout(panelRegister2Layout);
        panelRegister2Layout.setHorizontalGroup(
            panelRegister2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegister2Layout.createSequentialGroup()
                .addComponent(panelUpdateOk, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelRegister2Layout.setVerticalGroup(
            panelRegister2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUpdateOk, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelUpdateButtons.add(panelRegister2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelClear2.setBackground(new java.awt.Color(51, 51, 51));
        panelClear2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnClear2.setBackground(new java.awt.Color(51, 51, 51));
        btnClear2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear2.setForeground(new java.awt.Color(255, 255, 255));
        btnClear2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear2.setText("Clear");
        btnClear2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClear2Layout = new javax.swing.GroupLayout(panelClear2);
        panelClear2.setLayout(panelClear2Layout);
        panelClear2Layout.setHorizontalGroup(
            panelClear2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        );
        panelClear2Layout.setVerticalGroup(
            panelClear2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelUpdateButtons.add(panelClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 0, 110, -1));

        panelButtons.add(panelUpdateButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, -1));

        panelUserManagement.add(panelButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 717, -1, 51));

        CalanderDob.setDateFormatString("yyyy,MM,dd");
        panelUserManagement.add(CalanderDob, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 210, 30));

        panelRightNaviUser.add(panelUserManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, -20, 1140, 790));

        tabRightNavigation.addTab("tab2", panelRightNaviUser);

        panelRightNaviSupplier.setBackground(new java.awt.Color(51, 51, 51));

        lblBackgroundImage1.setBackground(new java.awt.Color(51, 51, 51));
        lblBackgroundImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/image-Person.png"))); // NOI18N

        panelSupllierManagemet.setBackground(new java.awt.Color(51, 51, 51));
        panelSupllierManagemet.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSupllierImage.setBackground(new java.awt.Color(51, 51, 51));
        panelSupllierImage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        lblSupplierImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelSupllierImageLayout = new javax.swing.GroupLayout(panelSupllierImage);
        panelSupllierImage.setLayout(panelSupllierImageLayout);
        panelSupllierImageLayout.setHorizontalGroup(
            panelSupllierImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSupplierImage, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );
        panelSupllierImageLayout.setVerticalGroup(
            panelSupllierImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSupplierImage, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );

        panelSupllierManagemet.add(panelSupllierImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, 150, 160));

        panelSupButtons.setBackground(new java.awt.Color(51, 51, 51));
        panelSupButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSupRegisterButtons.setBackground(new java.awt.Color(51, 51, 51));
        panelSupRegisterButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelClear5.setBackground(new java.awt.Color(51, 51, 51));
        panelClear5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnClear4.setBackground(new java.awt.Color(51, 51, 51));
        btnClear4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear4.setForeground(new java.awt.Color(255, 255, 255));
        btnClear4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear4.setText("Clear");
        btnClear4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClear5Layout = new javax.swing.GroupLayout(panelClear5);
        panelClear5.setLayout(panelClear5Layout);
        panelClear5Layout.setHorizontalGroup(
            panelClear5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear4, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelClear5Layout.setVerticalGroup(
            panelClear5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelSupRegisterButtons.add(panelClear5, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 0, -1, -1));

        panelRegister5.setBackground(new java.awt.Color(51, 51, 51));
        panelRegister5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnSupReg.setBackground(new java.awt.Color(51, 51, 51));
        btnSupReg.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSupReg.setForeground(new java.awt.Color(255, 255, 255));
        btnSupReg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSupReg.setText("Register");
        btnSupReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupRegMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRegister5Layout = new javax.swing.GroupLayout(panelRegister5);
        panelRegister5.setLayout(panelRegister5Layout);
        panelRegister5Layout.setHorizontalGroup(
            panelRegister5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSupReg, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelRegister5Layout.setVerticalGroup(
            panelRegister5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSupReg, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelSupRegisterButtons.add(panelRegister5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelSupButtons.add(panelSupRegisterButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, -1, -1));

        panelSupUpdateButtons.setBackground(new java.awt.Color(51, 51, 51));
        panelSupUpdateButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRegister6.setBackground(new java.awt.Color(51, 51, 51));
        panelRegister6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnSupUpdateOk.setBackground(new java.awt.Color(51, 51, 51));
        btnSupUpdateOk.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSupUpdateOk.setForeground(new java.awt.Color(255, 255, 255));
        btnSupUpdateOk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSupUpdateOk.setText("Update");
        btnSupUpdateOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupUpdateOkMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRegister6Layout = new javax.swing.GroupLayout(panelRegister6);
        panelRegister6.setLayout(panelRegister6Layout);
        panelRegister6Layout.setHorizontalGroup(
            panelRegister6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSupUpdateOk, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelRegister6Layout.setVerticalGroup(
            panelRegister6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSupUpdateOk, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelSupUpdateButtons.add(panelRegister6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelClear6.setBackground(new java.awt.Color(51, 51, 51));
        panelClear6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        btnClear5.setBackground(new java.awt.Color(51, 51, 51));
        btnClear5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear5.setForeground(new java.awt.Color(255, 255, 255));
        btnClear5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear5.setText("Clear");
        btnClear5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelClear6Layout = new javax.swing.GroupLayout(panelClear6);
        panelClear6.setLayout(panelClear6Layout);
        panelClear6Layout.setHorizontalGroup(
            panelClear6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear5, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
        );
        panelClear6Layout.setVerticalGroup(
            panelClear6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClear5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        panelSupUpdateButtons.add(panelClear6, new org.netbeans.lib.awtextra.AbsoluteConstraints(156, 0, -1, -1));

        panelSupButtons.add(panelSupUpdateButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, -1));

        panelSupllierManagemet.add(panelSupButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 690, 640, 51));
        panelSupllierManagemet.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, -1, -1));

        txtWarmMessage3.setEditable(false);
        txtWarmMessage3.setBackground(new java.awt.Color(51, 51, 51));
        txtWarmMessage3.setColumns(20);
        txtWarmMessage3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtWarmMessage3.setForeground(new java.awt.Color(255, 255, 255));
        txtWarmMessage3.setRows(5);
        txtWarmMessage3.setText("* Welcome to MLT Holdings Registration!\n   We'are delighted that you have choosen to join our shop comunity. By creating an account,\n   You will unlock a convenient features tailored just for you.\n   Please take a moment to provide us with some details to get started.");
        txtWarmMessage3.setBorder(null);
        scollPanelWarmMessage5.setViewportView(txtWarmMessage3);

        panelSupllierManagemet.add(scollPanelWarmMessage5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 840, 150));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        txtSupAddress.setBackground(new java.awt.Color(51, 51, 51));
        txtSupAddress.setColumns(20);
        txtSupAddress.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        txtSupAddress.setForeground(new java.awt.Color(255, 255, 255));
        txtSupAddress.setRows(5);
        txtSupAddress.setBorder(null);
        scollPanelWarmMessage2.setViewportView(txtSupAddress);

        lblUserName1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName1.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName1.setText("Address");

        txtSupMail.setBackground(new java.awt.Color(51, 51, 51));
        txtSupMail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSupMail.setForeground(new java.awt.Color(255, 255, 255));
        txtSupMail.setBorder(null);

        lblUserName5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName5.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName5.setText("E mail :");

        lblUserName4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName4.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName4.setText("Contact Number :");

        txtSupContactNo.setBackground(new java.awt.Color(51, 51, 51));
        txtSupContactNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSupContactNo.setForeground(new java.awt.Color(255, 255, 255));
        txtSupContactNo.setBorder(null);

        txtSupName.setBackground(new java.awt.Color(51, 51, 51));
        txtSupName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSupName.setForeground(new java.awt.Color(255, 255, 255));
        txtSupName.setBorder(null);

        lblUserName3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName3.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName3.setText("Agency Name :");

        lblUserName2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUserName2.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName2.setText("Agancy Details");

        lblUserName6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName6.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName6.setText("Company Name :");

        txtCompanyName.setBackground(new java.awt.Color(51, 51, 51));
        txtCompanyName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCompanyName.setForeground(new java.awt.Color(255, 255, 255));
        txtCompanyName.setBorder(null);

        lblSupId.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSupId.setForeground(new java.awt.Color(255, 255, 255));
        lblSupId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblUserName14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUserName14.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName14.setText("Supplier ID :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserName1)
                            .addComponent(scollPanelWarmMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblUserName14)
                                .addGap(26, 26, 26)
                                .addComponent(lblSupId, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblUserName6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblUserName4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtSupContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblUserName5)
                                    .addGap(76, 76, 76)
                                    .addComponent(txtSupMail, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblUserName3)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtSupName, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(39, 39, 39))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 1, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblUserName2)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 320, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblSupId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblUserName14)))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblUserName3))
                    .addComponent(txtSupName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName6))
                .addGap(13, 13, 13)
                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName4)
                    .addComponent(txtSupContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblUserName5))
                    .addComponent(txtSupMail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scollPanelWarmMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 37, Short.MAX_VALUE)
                    .addComponent(lblUserName2)
                    .addGap(8, 8, 8)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 479, Short.MAX_VALUE)))
        );

        panelSupllierManagemet.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 500, 560));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        txtRepMail.setBackground(new java.awt.Color(51, 51, 51));
        txtRepMail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRepMail.setForeground(new java.awt.Color(255, 255, 255));
        txtRepMail.setBorder(null);

        lblUserName7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName7.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName7.setText("E mail :");

        lblUserName8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName8.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName8.setText("Contact Number :");

        txtRepContactNo.setBackground(new java.awt.Color(51, 51, 51));
        txtRepContactNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRepContactNo.setForeground(new java.awt.Color(255, 255, 255));
        txtRepContactNo.setText("0774411765");
        txtRepContactNo.setBorder(null);

        txtRepFirstName.setBackground(new java.awt.Color(51, 51, 51));
        txtRepFirstName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRepFirstName.setForeground(new java.awt.Color(255, 255, 255));
        txtRepFirstName.setBorder(null);

        lblUserName9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName9.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName9.setText("First Name :");

        lblUserName10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUserName10.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName10.setText("Reprantative Details");

        lblUserName11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName11.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName11.setText("Nic :");

        txtRepNice.setBackground(new java.awt.Color(51, 51, 51));
        txtRepNice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRepNice.setForeground(new java.awt.Color(255, 255, 255));
        txtRepNice.setBorder(null);

        lblUserName12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName12.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName12.setText("Last Name :");

        txtRepLastName.setBackground(new java.awt.Color(51, 51, 51));
        txtRepLastName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRepLastName.setForeground(new java.awt.Color(255, 255, 255));
        txtRepLastName.setBorder(null);

        lblUserName13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUserName13.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName13.setText("Gender :");

        rdoSupMale.setBackground(new java.awt.Color(51, 51, 51));
        rdoSupMale.setForeground(new java.awt.Color(255, 255, 255));
        rdoSupMale.setSelected(true);
        rdoSupMale.setText("Male");
        rdoSupMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSupMaleActionPerformed(evt);
            }
        });

        rdoSupFemale.setBackground(new java.awt.Color(51, 51, 51));
        rdoSupFemale.setForeground(new java.awt.Color(255, 255, 255));
        rdoSupFemale.setText("Female");
        rdoSupFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSupFemaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUserName10)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblUserName11)
                        .addGap(79, 79, 79)
                        .addComponent(txtRepNice, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblUserName7)
                        .addGap(78, 78, 78)
                        .addComponent(txtRepMail, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblUserName12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRepLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblUserName9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRepFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserName8)
                            .addComponent(lblUserName13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rdoSupMale)
                                .addGap(39, 39, 39)
                                .addComponent(rdoSupFemale))
                            .addComponent(txtRepContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblUserName10)
                .addGap(8, 8, 8)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblUserName9))
                    .addComponent(txtRepFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblUserName12))
                    .addComponent(txtRepLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblUserName11))
                    .addComponent(txtRepNice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName13)
                    .addComponent(rdoSupMale)
                    .addComponent(rdoSupFemale))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName8)
                    .addComponent(txtRepContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserName7)
                    .addComponent(txtRepMail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSupllierManagemet.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 580, 500));

        javax.swing.GroupLayout panelRightNaviSupplierLayout = new javax.swing.GroupLayout(panelRightNaviSupplier);
        panelRightNaviSupplier.setLayout(panelRightNaviSupplierLayout);
        panelRightNaviSupplierLayout.setHorizontalGroup(
            panelRightNaviSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRightNaviSupplierLayout.createSequentialGroup()
                .addComponent(panelSupllierManagemet, javax.swing.GroupLayout.PREFERRED_SIZE, 1175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBackgroundImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRightNaviSupplierLayout.setVerticalGroup(
            panelRightNaviSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRightNaviSupplierLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBackgroundImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelRightNaviSupplierLayout.createSequentialGroup()
                .addComponent(panelSupllierManagemet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 119, Short.MAX_VALUE))
        );

        tabRightNavigation.addTab("tab4", panelRightNaviSupplier);

        Management.add(tabRightNavigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(447, -5, 1180, 890));

        TabedPanelMain.addTab("Manage", Management);

        Inventory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnViewInventory.setBackground(new java.awt.Color(68, 194, 137));

        jPanel24.setBackground(new java.awt.Color(41, 51, 80));

        btnLblViewInventory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLblViewInventory.setForeground(new java.awt.Color(255, 255, 255));
        btnLblViewInventory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLblViewInventory.setText("View Inventory");
        btnLblViewInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLblViewInventoryMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblViewInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblViewInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnViewInventoryLayout = new javax.swing.GroupLayout(btnViewInventory);
        btnViewInventory.setLayout(btnViewInventoryLayout);
        btnViewInventoryLayout.setHorizontalGroup(
            btnViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnViewInventoryLayout.setVerticalGroup(
            btnViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnViewInventoryLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        btnShift.setBackground(new java.awt.Color(68, 194, 137));

        jPanel26.setBackground(new java.awt.Color(41, 51, 80));

        btnLblShift.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLblShift.setForeground(new java.awt.Color(255, 255, 255));
        btnLblShift.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLblShift.setText("Shift");
        btnLblShift.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLblShiftMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblShift, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblShift, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnShiftLayout = new javax.swing.GroupLayout(btnShift);
        btnShift.setLayout(btnShiftLayout);
        btnShiftLayout.setHorizontalGroup(
            btnShiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        btnShiftLayout.setVerticalGroup(
            btnShiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnShiftLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        btnItemReg.setBackground(new java.awt.Color(120, 168, 252));

        jPanel28.setBackground(new java.awt.Color(41, 51, 80));

        btnLblItemReg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLblItemReg.setForeground(new java.awt.Color(255, 255, 255));
        btnLblItemReg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLblItemReg.setText("Register Items");
        btnLblItemReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLblItemRegMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblItemReg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblItemReg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnItemRegLayout = new javax.swing.GroupLayout(btnItemReg);
        btnItemReg.setLayout(btnItemRegLayout);
        btnItemRegLayout.setHorizontalGroup(
            btnItemRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        btnItemRegLayout.setVerticalGroup(
            btnItemRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnItemRegLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        btnAddGrn.setBackground(new java.awt.Color(68, 194, 137));

        jPanel30.setBackground(new java.awt.Color(41, 51, 80));

        btnLblAddGrn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLblAddGrn.setForeground(new java.awt.Color(255, 255, 255));
        btnLblAddGrn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLblAddGrn.setText("Add GRN");
        btnLblAddGrn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLblAddGrnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblAddGrn, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLblAddGrn, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnAddGrnLayout = new javax.swing.GroupLayout(btnAddGrn);
        btnAddGrn.setLayout(btnAddGrnLayout);
        btnAddGrnLayout.setHorizontalGroup(
            btnAddGrnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnAddGrnLayout.setVerticalGroup(
            btnAddGrnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddGrnLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnItemReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddGrn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnShift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnViewInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddGrn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnItemReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShift, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewInventory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        Inventory.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel11.setBackground(new java.awt.Color(165, 197, 253));
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("Select Catagory :");

        comboCatagory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboCatagory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboCatagoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comboCatagoryMouseEntered(evt);
            }
        });
        comboCatagory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCatagoryActionPerformed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(23, 35, 51));

        jPanel29.setBackground(new java.awt.Color(23, 35, 51));

        lblCatagorySettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCatagorySettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/settings_icon_White.png"))); // NOI18N
        lblCatagorySettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCatagorySettingsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCatagorySettingsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblCatagorySettingsMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(lblCatagorySettings))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCatagorySettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Catagory");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCatagory, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboCatagory, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(165, 197, 253));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Select Unit        :");

        comboUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboUnit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboUnitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comboUnitMouseEntered(evt);
            }
        });
        comboUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUnitActionPerformed(evt);
            }
        });

        jPanel31.setBackground(new java.awt.Color(23, 35, 51));

        jPanel32.setBackground(new java.awt.Color(23, 35, 51));

        lblUnitSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUnitSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/settings_icon_White.png"))); // NOI18N
        lblUnitSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUnitSettingsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblUnitSettingsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblUnitSettingsMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(0, 33, Short.MAX_VALUE)
                .addComponent(lblUnitSettings))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUnitSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Unit");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(comboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(165, 197, 253));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Company        :");

        comboCompany.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboCompany.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboCompanyMouseClicked(evt);
            }
        });
        comboCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCompanyActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setText("Company ID    :");

        lblSlectedCom.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSlectedCom.setText("Sup-00001");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSlectedCom, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblSlectedCom))
                .addGap(24, 24, 24)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(165, 197, 253));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Product Name :");

        txtProductName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setText("Product ID      :");

        lblProductId.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblProductId.setText("Pdct-00001");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(165, 197, 253));
        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("Store 1 Max Stock Level :");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setText("Shop Max Stock Level    :");

        jLabel16.setBackground(new java.awt.Color(165, 197, 253));
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setText("Re Order Level             :");

        txtReOrderLevel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtShopMax.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtStore1Max.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtReOrderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtShopMax, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStore1Max, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtShopMax, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtStore1Max, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtReOrderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(68, 194, 137));

        txtItemSearch.setBackground(new java.awt.Color(68, 194, 137));
        txtItemSearch.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtItemSearchInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtItemSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemSearchActionPerformed(evt);
            }
        });
        txtItemSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemSearchKeyReleased(evt);
            }
        });

        jPanel20.setBackground(new java.awt.Color(68, 194, 137));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_search_24px_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(txtItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtItemSearch)
        );

        btItemAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btItemAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_add.png"))); // NOI18N
        btItemAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btItemAddMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btItemAdd)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btItemAdd)
        );

        btnItemUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnItemUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_update (1).png"))); // NOI18N
        btnItemUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnItemUpdateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnItemUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnItemUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnItemRemove.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnItemRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_remove (1).png"))); // NOI18N
        btnItemRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnItemRemoveMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnItemRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnItemRemove))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Item Id", "Item Name", "Company", "Catagory", "Unit", "shopMax", "nitMax", "ReOrderLevel"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableItem.setRowHeight(25);
        tableItem.setRowMargin(3);
        tableItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableItemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableItem);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_refresh_32px.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        comboItemFilter.setBackground(new java.awt.Color(0, 255, 204));
        comboItemFilter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboItemFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item_ID", "Item_Name", "Company", "Catagory", " ", " " }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 35, Short.MAX_VALUE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboItemFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(63, 63, 63)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboItemFilter))))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRegLayout = new javax.swing.GroupLayout(panelReg);
        panelReg.setLayout(panelRegLayout);
        panelRegLayout.setHorizontalGroup(
            panelRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRegLayout.setVerticalGroup(
            panelRegLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabInventory.addTab("Reg", panelReg);

        panalAddGRN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel37.setForeground(new java.awt.Color(255, 51, 51));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Shop in Stock   :");

        lblShopStock.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblShopStock.setForeground(new java.awt.Color(0, 153, 51));
        lblShopStock.setText("00");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Store 1 in Stock :");

        lblStore1Stock.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblStore1Stock.setForeground(new java.awt.Color(0, 0, 255));
        lblStore1Stock.setText("00");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setText("Store 2 in Stock :");

        lblStore2Stock.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblStore2Stock.setForeground(new java.awt.Color(51, 51, 255));
        lblStore2Stock.setText("00");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setText("Total Stock       :");

        lblTotalStock.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTotalStock.setForeground(new java.awt.Color(255, 51, 51));
        lblTotalStock.setText("00");

        jPanel41.setBackground(new java.awt.Color(41, 51, 80));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Stock Level");

        jPanel42.setBackground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setText("Store 1 Max :");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setText("Shop Max :");

        lblShopMax.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblShopMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblShopMax.setText("0");

        lblStore1Max.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblStore1Max.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStore1Max.setText("0");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStore1Max, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblShopMax, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStore2Stock)
                            .addComponent(lblStore1Stock)
                            .addComponent(lblShopStock)
                            .addComponent(lblTotalStock))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShopMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(lblStore1Max))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(lblShopStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(lblStore1Stock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(lblStore2Stock))
                .addGap(27, 27, 27)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(lblTotalStock))
                .addContainerGap())
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setText("Sup ID      :");

        lblSupplierId.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSupplierId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setText("Com Name :");

        lblComName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblComName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setText("Item code  :");

        lblItemCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblItemCode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel43.setText("Item Name :");

        lblItemName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblItemName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setText("Catagory    :");

        lblCat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jPanel43.setBackground(new java.awt.Color(41, 51, 80));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Item Details");

        jPanel44.setBackground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSeparator25.setForeground(new java.awt.Color(255, 51, 0));

        jSeparator26.setForeground(new java.awt.Color(255, 51, 0));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("Value               :");

        lblTotalCost.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTotalCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalCost.setText("0");

        lblTotalValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalValue.setText("0");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel59.setText("New Unit Cost    :");

        lblNewUnitCost.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNewUnitCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNewUnitCost.setText("0");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel60.setText("Unit Cost          :");

        lblUnitCost.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUnitCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnitCost.setText("0");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel61.setText("Unit          :");

        lblUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblUnit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("Total Cost         :");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblUnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addComponent(jLabel59)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNewUnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(51, 51, 51)
                                        .addComponent(lblTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel45))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCat, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(131, 131, 131))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblComName, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel38Layout.createSequentialGroup()
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSupplierId, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(lblSupplierId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(lblComName))
                .addGap(12, 12, 12)
                .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(lblItemCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(lblItemName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(lblCat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(lblUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator26, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(lblUnitCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(lblNewUnitCost))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(lblTotalCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(lblTotalValue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel36.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 51, 80), 2, true));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Company :");

        comboCompanyName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboCompanyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCompanyNameActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setText("Item :");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel30.setText("Free Issue :");

        txtFreeIssue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        comboItemName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboItemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboItemNameActionPerformed(evt);
            }
        });

        jPanel45.setBackground(new java.awt.Color(41, 51, 80));

        jPanel49.setBackground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel48Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        comboItemCat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comboItemCatFocusGained(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel38.setText("Retail Price:");

        txtRetailPrice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtRetailPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel53.setText("Discount :");

        txtDiscount.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountKeyReleased(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel55.setText("Count :");

        txtCount.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel49.setText("MFGD :");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel50.setText("EXPD :");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel51.setText("Batch No :");

        txtBatchNo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addGap(26, 26, 26)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dteMfg, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(dateExp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBatchNo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(dteMfg, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(dateExp, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBatchNo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)))
        );

        rdoRupee.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoRupee.setText("Rupee");
        rdoRupee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRupeeActionPerformed(evt);
            }
        });

        rdoPercent.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoPercent.setText("Percent");
        rdoPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPercentActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel56.setText("Unit Cost :");

        txtUnitCost.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        txtGRNvalue.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel62.setText("GRN Value :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("GRN No   :");

        comboGRN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboGRNActionPerformed(evt);
            }
        });

        txtGrn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtGrn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGrnKeyReleased(evt);
            }
        });

        jLabel131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/Add grn.png"))); // NOI18N
        jLabel131.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel131MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                                .addComponent(txtDiscount)
                                .addGap(18, 18, 18)
                                .addComponent(rdoPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoRupee, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFreeIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel55)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(24, 24, 24))
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboCompanyName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRetailPrice)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(comboGRN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboItemCat, 0, 95, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboItemName, 0, 270, Short.MAX_VALUE)
                                    .addComponent(txtGrn))))))
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel36Layout.createSequentialGroup()
                                        .addComponent(jLabel56)
                                        .addGap(43, 43, 43)))
                                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel36Layout.createSequentialGroup()
                                        .addComponent(txtUnitCost)
                                        .addGap(1, 1, 1))
                                    .addComponent(txtGRNvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(comboCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboItemCat, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(comboGRN, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtGrn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel56))
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFreeIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel53)
                                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdoRupee, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rdoPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGRNvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        btnBarcode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_generate-barcode.png"))); // NOI18N
        btnBarcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBarcodeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnBarcodeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnBarcodeMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBarcode)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panalAddGRN.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 415));

        tableInventoryItem.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tableInventoryItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GRN No", "Item Name", "Retail Price", "Unit Cost", "Count", "Discount", "Free Issue", "MFG", "EXP", "Batch No"
            }
        ));
        tableInventoryItem.setRowHeight(30);
        tableInventoryItem.setRowMargin(8);
        tableInventoryItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInventoryItemMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableInventoryItem);

        panalAddGRN.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 412, 1614, 350));

        btnGRNUpdate.setText("Update");
        btnGRNUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGRNUpdateActionPerformed(evt);
            }
        });
        panalAddGRN.add(btnGRNUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(824, 457, 180, 70));

        tabInventory.addTab("Add GRN", panalAddGRN);

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        jPanel52.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel28.setText("Shop Available    : ");

        lblShopAvailble.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblShopAvailble.setText("00");

        lblStore1Available.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblStore1Available.setText("00");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel34.setText("Store 1 Available : ");

        lblStore2Availble.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblStore2Availble.setText("00");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel40.setText("Store 2 Available :");

        jPanel56.setBackground(new java.awt.Color(41, 51, 80));

        jPanel57.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Stock Level");

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setText("Total Stock          :");

        lblTotLvel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotLvel.setText("00");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStore2Availble))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblShopAvailble))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                        .addGap(42, 42, 42)
                        .addComponent(lblStore1Available))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotLvel))))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblShopAvailble)
                    .addComponent(jLabel28))
                .addGap(7, 7, 7)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(lblStore1Available))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(lblStore2Availble))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(lblTotLvel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableShift.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tableShift.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Comapany Name", "Item Name", "From", "To", "Pre Availble (From)", "Now Available (From)", "Pre Availble (To)", "Now Available (To)", "Shift Qty"
            }
        ));
        tableShift.setRowHeight(25);
        tableShift.setRowMargin(6);
        tableShift.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableShiftMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableShift);

        jPanel59.setBackground(new java.awt.Color(41, 51, 80));

        jPanel55.setBackground(new java.awt.Color(68, 194, 137));

        jPanel46.setBackground(new java.awt.Color(165, 197, 253));

        comboWhere.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        comboWhere.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Shop", "Store 1", "Store 2" }));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setText("To");

        comboTo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        comboTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Shop", "Store 1", "Store 2" }));
        comboTo.setSelectedIndex(1);

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboWhere, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addGap(4, 4, 4)
                .addComponent(comboTo, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboWhere, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, 515, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel58.setBackground(new java.awt.Color(68, 194, 137));

        jPanel51.setBackground(new java.awt.Color(165, 197, 253));

        comboCom.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        comboCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboComActionPerformed(evt);
            }
        });

        txtQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel25.setText("Company :");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel26.setText("Qty :");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel27.setText("Item :");

        comboItm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        comboItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboItmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboCom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboItm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(comboCom, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboItm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(77, 77, 77))
        );

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel51, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel61.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel61.setForeground(new java.awt.Color(255, 51, 51));

        jPanel62.setBackground(new java.awt.Color(41, 51, 80));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Stock Level");

        jPanel63.setBackground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel64.setText("Store 1 Max :");

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel65.setText("Shop Max :");

        lblShopMaxLevel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblShopMaxLevel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblShopMaxLevel.setText("0");

        lblStore1MaxLevel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblStore1MaxLevel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStore1MaxLevel.setText("0");

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addGap(28, 28, 28)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStore1MaxLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShopMaxLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShopMaxLevel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(lblStore1MaxLevel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnItemRemove1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnItemRemove1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_remove (1).png"))); // NOI18N
        btnItemRemove1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnItemRemove1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnItemRemove1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnItemRemove1))
        );

        btItemAdd3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btItemAdd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_add.png"))); // NOI18N
        btItemAdd3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btItemAdd3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btItemAdd3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btItemAdd3, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel50Layout.createSequentialGroup()
                                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addGap(958, 958, 958)
                        .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(312, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                        .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelShiftLayout = new javax.swing.GroupLayout(panelShift);
        panelShift.setLayout(panelShiftLayout);
        panelShiftLayout.setHorizontalGroup(
            panelShiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShiftLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelShiftLayout.setVerticalGroup(
            panelShiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShiftLayout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabInventory.addTab("Shift", panelShift);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Company", "Item Name", "Cat", "Unit", "Discount", "Unit Cost", "Retail Price", "Total Cost", "Total Value", "Shop Av", "Store1 Av", "Store2 Av", "Total Qty", "Shop Max", "Store1 Max"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        jPanel64.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
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
        jLabel78.setText("Shop Mx  :");

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel79.setText("Store1 Mx :");

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel80.setText("Total Qty  :");

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(51, 51, 255));
        jLabel81.setText("Sup-00001");

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(51, 51, 255));
        jLabel82.setText("Natures Secrets");

        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(51, 51, 255));
        jLabel83.setText("Itm-00001");

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(51, 51, 255));
        jLabel84.setText("NS PAPAYA FW 100ml");

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(51, 51, 255));
        jLabel85.setText("FW");

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(51, 51, 255));
        jLabel86.setText("100ml");

        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 153, 153));
        jLabel87.setText("15");

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 153, 153));
        jLabel88.setText("332.65");

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 153, 153));
        jLabel89.setText("345.00");

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 153, 153));
        jLabel90.setText("12000");

        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 153, 153));
        jLabel91.setText("17000");

        jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(102, 0, 153));
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setText("6");

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(102, 0, 153));
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("10");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(102, 0, 153));
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("60");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 51, 51));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("5");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 51, 51));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("10");

        jTextField3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 51, 51));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("44");

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

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(69, 69, 69)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 31, Short.MAX_VALUE)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel91, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))))
            .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel67)
                                .addComponent(jLabel76)
                                .addComponent(jLabel81)
                                .addComponent(jLabel87)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(jLabel71)
                            .addComponent(jLabel75)
                            .addComponent(jLabel82)
                            .addComponent(jLabel88))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel68)
                                .addComponent(jLabel72)
                                .addComponent(jLabel77)
                                .addComponent(jLabel89))
                            .addComponent(jLabel83)))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel73)
                    .addComponent(jLabel78)
                    .addComponent(jLabel84)
                    .addComponent(jLabel90)
                    .addComponent(jLabel95))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jLabel74)
                    .addComponent(jLabel79)
                    .addComponent(jLabel85)
                    .addComponent(jLabel91)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel69)
                        .addComponent(jLabel80)
                        .addComponent(jLabel97))
                    .addComponent(jLabel86))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Update");

        javax.swing.GroupLayout panelViewInventoryLayout = new javax.swing.GroupLayout(panelViewInventory);
        panelViewInventory.setLayout(panelViewInventoryLayout);
        panelViewInventoryLayout.setHorizontalGroup(
            panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(panelViewInventoryLayout.createSequentialGroup()
                .addGroup(panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViewInventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelViewInventoryLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        panelViewInventoryLayout.setVerticalGroup(
            panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelViewInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViewInventoryLayout.createSequentialGroup()
                        .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
        );

        tabInventory.addTab("View Inventory", panelViewInventory);

        Inventory.add(tabInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1615, 740));

        TabedPanelMain.addTab("Inventory", Inventory);

        tblReturns.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tblReturns.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "Company ID", "Company Name", "Catagory", "Item ID", "Item Name", "Location", "lastAdedqty", "Qty"
            }
        ));
        tblReturns.setRowHeight(40);
        tblReturns.setRowMargin(5);
        jScrollPane6.setViewportView(tblReturns);

        jPanel70.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        comboItemReturn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboItemReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboItemReturnActionPerformed(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel94.setText("Company Name :");

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel98.setText("Item Name         :");

        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel99.setText("Qty                     :");

        txtReturnItemQty.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtReturnItemQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel72.setBackground(new java.awt.Color(41, 51, 80));

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 255, 255));
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setText("Select Item");

        jPanel73.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(308, 308, 308)
                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(466, Short.MAX_VALUE))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(jPanel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel103.setText("Company ID      :");

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel104.setText("Item ID              :");

        comboCompanyReturn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboCompanyReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCompanyReturnActionPerformed(evt);
            }
        });

        jLabel105.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel105.setText("Catagory            :");

        comboCatagoryReturn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        lblComIDReturn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblComIDReturn.setForeground(new java.awt.Color(51, 51, 255));
        lblComIDReturn.setText("Sup-00001");

        lblItemCodeReturn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblItemCodeReturn.setForeground(new java.awt.Color(51, 51, 255));
        lblItemCodeReturn.setText("Item-00001");

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel101.setText("Item Location    :");

        comboReturnLocation.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboReturnLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Shop", "Store 1", "Store 2" }));

        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel107.setText("Av Stock in Shop :");

        jLabel116.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel116.setText("Av Stock in Store 1 :");

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel121.setText("Av Stock in Store 2 :");

        jLabel122.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setText("00");

        jLabel128.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("00");

        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setText("00");

        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/returnBtn.png"))); // NOI18N
        btnReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReturnMouseClicked(evt);
            }
        });

        btnReturn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/Print button.png"))); // NOI18N
        btnReturn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReturn1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboReturnLocation, 0, 308, Short.MAX_VALUE)
                            .addComponent(comboItemReturn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboCompanyReturn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboCatagoryReturn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtReturnItemQty)))
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnReturn)
                        .addGap(49, 49, 49)
                        .addComponent(btnReturn1)))
                .addGap(34, 34, 34)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblItemCodeReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addComponent(jLabel103)
                        .addGap(8, 8, 8)
                        .addComponent(lblComIDReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel121, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboReturnLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel101))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboCompanyReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel94)
                            .addGroup(jPanel70Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboItemReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel98))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel105)
                                    .addComponent(comboCatagoryReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtReturnItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel99)))))
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel103)
                            .addComponent(lblComIDReturn))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel104)
                            .addComponent(lblItemCodeReturn))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel107)
                            .addComponent(jLabel122))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel116)
                            .addComponent(jLabel128))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel121)
                            .addComponent(jLabel129))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReturn1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel109.setText("Company Name :");

        jPanel77.setBackground(new java.awt.Color(41, 51, 80));

        jPanel78.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel110.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("Search Returns");

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel77Layout.createSequentialGroup()
                .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel111.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel111.setText("Catagory             :");

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel112.setText("Item Name          :");

        txtReturnCompany.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtReturnCompanyInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtReturnCompany.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReturnCompanyKeyReleased(evt);
            }
        });

        txtReturnSearch.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtReturnSearchInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtReturnSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtReturnSearchKeyReleased(evt);
            }
        });

        txtreturnSerchCat.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtreturnSerchCatInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtreturnSerchCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtreturnSerchCatKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtReturnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(txtReturnCompany, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(txtreturnSerchCat, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(83, 83, 83))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109)
                    .addComponent(txtReturnCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtreturnSerchCat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel112)
                    .addComponent(txtReturnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1594, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout ReturnLayout = new javax.swing.GroupLayout(Return);
        Return.setLayout(ReturnLayout);
        ReturnLayout.setHorizontalGroup(
            ReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReturnLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 1823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ReturnLayout.setVerticalGroup(
            ReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TabedPanelMain.addTab("Returns", Return);

        jPanel74.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        comboItmName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "papaya" }));

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel113.setText("Company Name :");

        jLabel114.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel114.setText("Item Name         :");

        jLabel115.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel115.setText("Qty                     :");

        txtOrderQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel76.setBackground(new java.awt.Color(41, 51, 80));

        jLabel117.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(255, 255, 255));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setText("Select Item");

        jPanel79.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addGap(346, 346, 346)
                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel118.setText("Company ID      :");

        jLabel119.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel119.setText("Item ID              :");

        comboCmNAme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "ns" }));

        jLabel120.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel120.setText("Catagory            :");

        comboCat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setText("Order");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        lblComID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblComID.setForeground(new java.awt.Color(51, 51, 255));
        lblComID.setText("Sup-00001");

        lblItmCode.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblItmCode.setForeground(new java.awt.Color(51, 51, 255));
        lblItmCode.setText("Item-00001");

        btnOrdrItemRemove.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnOrdrItemRemove.setText("Remove");
        btnOrdrItemRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdrItemRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel74Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboCat, javax.swing.GroupLayout.Alignment.TRAILING, 0, 237, Short.MAX_VALUE)
                    .addComponent(comboItmName, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboCmNAme, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOrderQty))
                .addGap(45, 45, 45)
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel118)
                            .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItmCode, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblComID, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOrdrItemRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel74Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addComponent(jLabel113)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel114)
                            .addComponent(comboItmName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel120)
                            .addComponent(comboCat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel115)
                            .addComponent(txtOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel118)
                            .addComponent(comboCmNAme, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblComID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel119)
                            .addComponent(lblItmCode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOrdrItemRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel80.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel124.setText("Company Name :");

        jPanel81.setBackground(new java.awt.Color(41, 51, 80));

        jPanel82.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel125.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(255, 255, 255));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("Search Returns");

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel81Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel126.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel126.setText("Catagory             :");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel127.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel127.setText("Item Name          :");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel80Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel124, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel80Layout.createSequentialGroup()
                        .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel126, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel127, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBox14, 0, 263, Short.MAX_VALUE)
                        .addComponent(jComboBox15, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(48, 48, 48))
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel124)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel126)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel127)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTabbedPane1.setName(""); // NOI18N

        tblPutOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company ID", "Company Name", "Catagory", "Item ID", "Item Name", "Qty"
            }
        ));
        jScrollPane7.setViewportView(tblPutOrder);

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1576, Short.MAX_VALUE)
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("view Inventory", jPanel71);

        tblViewOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company ID", "Company Name", "Catagory", "Item ID", "Item Name", "Qty"
            }
        ));
        tblViewOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblViewOrderMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblViewOrder);

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel83Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 57, Short.MAX_VALUE))
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("View Order", jPanel83);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout OrderLayout = new javax.swing.GroupLayout(Order);
        Order.setLayout(OrderLayout);
        OrderLayout.setHorizontalGroup(
            OrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 1823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OrderLayout.setVerticalGroup(
            OrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TabedPanelMain.addTab("Order", Order);

        getContentPane().add(TabedPanelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 1610, 810));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void lblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(120, 168, 252));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        TabedPanelMain.setSelectedIndex(1);
        tabMiddleNavigation.setSelectedIndex(0);
        tabRightNavigation.setSelectedIndex(0);
        panelUserManagement.setVisible(false);

        showRecods();
        tblCount();
    }//GEN-LAST:event_lblUserMouseClicked


    private void lblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(120, 168, 252));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        TabedPanelMain.setSelectedIndex(0);

    }//GEN-LAST:event_lblHomeMouseClicked


    private void lblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCustomerMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(120, 168, 252));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        Customer cus = new Customer(LoginUser);
        cus.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_lblCustomerMouseClicked


    private void lblSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSupplierMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(120, 168, 252));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        TabedPanelMain.setSelectedIndex(1);
        tabMiddleNavigation.setSelectedIndex(1);
        tabRightNavigation.setSelectedIndex(1);
        panelSupllierManagemet.setVisible(false);
        lblBackgroundImage.setVisible(true);

        supId();
        showRecodsCompany();
        tblCountSupplier();


    }//GEN-LAST:event_lblSupplierMouseClicked

    private void lblInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInventoryMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(120, 168, 252));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(120, 168, 252));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        TabedPanelMain.setSelectedIndex(2);
        tabInventory.setSelectedIndex(0);
        btnItemReg.setBackground(new Color(120, 168, 252));
        btnAddGrn.setBackground(new Color(68, 194, 137));
        btnShift.setBackground(new Color(68, 194, 137));

        updateCompanyCombo();
        comboCompanyAutoComplete();
    }//GEN-LAST:event_lblInventoryMouseClicked


    private void lblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInvoiceMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(120, 168, 252));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        Invoice invoice = new Invoice(LoginUser);
        invoice.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblInvoiceMouseClicked


    private void lblReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReturnMouseClicked

//================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(120, 168, 252));
        panelOrderSelector.setBackground(new java.awt.Color(68, 194, 137));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(120, 168, 252));
        lblOrder.setForeground(new java.awt.Color(255, 255, 255));

        TabedPanelMain.setSelectedIndex(3);
    }//GEN-LAST:event_lblReturnMouseClicked

    private void lblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOrderMouseClicked

        //================================Background Colour Changing====================//
        panelHomeSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelUserSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelCustomerSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelSupplierSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInventorySelector.setBackground(new java.awt.Color(68, 194, 137));
        panelInvoiceSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelReturnSelector.setBackground(new java.awt.Color(68, 194, 137));
        panelOrderSelector.setBackground(new java.awt.Color(120, 168, 252));

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomer.setForeground(new java.awt.Color(255, 255, 255));
        lblSupplier.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblReturn.setForeground(new java.awt.Color(255, 255, 255));
        lblOrder.setForeground(new java.awt.Color(120, 168, 252));

        //TabedPanelMain.setSelectedIndex(4);
        ViewOrderList vOList = new ViewOrderList(LoginUser);
        vOList.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblOrderMouseClicked

    private void btnRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegisterMouseClicked
        try {
            stmt = conn.createStatement();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dob = ((JTextField) CalanderDob.getDateEditor().getUiComponent()).getText();
            String fName = txtFirstName.getText();
            String lName = txtLastName.getText();
            String nic = txtNic.getText();
            String sex = gender;
            String contactNo = txtContactNo.getText();
            String emContactNo = txtEmNumber.getText();
            String mail = txtEmail.getText();
            String address = txtAddress.getText();
            String userName = txtUserName.getText();
            String ps1 = txtPassword.getText();
            password = ps1;
            String secQuestion = (String) comboSecQuestion.getSelectedItem();
            String secAnswer = txtAnswer.getText();
            String userType = this.userType;

            if (fName.isEmpty() || fName.length() < 3 || lName.isEmpty() || lName.length() < 3) {
                JOptionPane.showMessageDialog(null, "Invalid Name Found..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert a Valid NIC Number..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (dob.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter your date of birth...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address is missing...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (contactNo.isEmpty() || emContactNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number is incorrect", "error", JOptionPane.ERROR_MESSAGE);
            } else if (mail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a valid Email address...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (userName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a valid user name...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Set a strong password..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (secQuestion.isEmpty() || secAnswer.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Security Question or Answer is missing..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (userType.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a user Type", "error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isFirstNameValid = txtValidation(fName);
                boolean isLastNameValid = txtValidation(lName);
                boolean isEmailValid = validateEmail(mail);
                boolean isContactNoValid = validatePhoneNumber(contactNo);
                boolean isEmContactNoValid = validatePhoneNumber(emContactNo);
                boolean isNICValid = validateNIC(nic);

                if (!isFirstNameValid) {
                    JOptionPane.showMessageDialog(null, "First Name is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isLastNameValid) {
                    JOptionPane.showMessageDialog(null, "Last Name is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isEmailValid) {
                    JOptionPane.showMessageDialog(null, "Email is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isContactNoValid) {
                    JOptionPane.showMessageDialog(null, "Contact Number is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isEmContactNoValid) {
                    JOptionPane.showMessageDialog(null, "Emergency Contact Number is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isNICValid) {
                    JOptionPane.showMessageDialog(null, "NIC is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else {

                    String sql = "INSERT INTO user (uFirstName, uLastName, uNic, uGender, uDateOfBirth, uContactNo, uEmContactNo, uEmail, uAddress, uUserName, uPassword, uSecQuistion, uSecAnswer, userType) VALUES('" + fName + "','" + lName + "','" + nic + "','" + sex + "','" + dob + "','" + contactNo + "','" + emContactNo + "','" + mail + "','" + address + "','" + userName + "','" + password + "','" + secQuestion + "','" + secAnswer + "','" + userType + "');";
                    String sql2 = "INSERT INTO logindetails (uLastName,userName,userType) VALUES ('" + lName + "', '" + userName + "','" + userType + "');";
                   
                    
                    
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql2);
                    JOptionPane.showMessageDialog(null, "Data Inserted Successfully...");
                    lblFirstName.setForeground(Color.white);
                    lblLastName.setForeground(Color.white);
                    lblNic.setForeground(Color.white);
                    lblDob.setForeground(Color.white);
                    lblContactNo.setForeground(Color.white);
                    lblEmNumber.setForeground(Color.white);
                    lblEmail.setForeground(Color.white);
                    lblAddress.setForeground(Color.white);
                    lblUserName.setForeground(Color.white);
                    lblPassword.setForeground(Color.white);
                    lblRepassword.setForeground(Color.white);
                    lblSecurityQuestion.setForeground(Color.white);
                    lblAccountType.setForeground(Color.white);
                    lblGender.setForeground(Color.white);

                    showRecods();
                    tblCount();

                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtNic.setText("");
                    //txtDateOfBirth.setText("");
                    txtContactNo.setText("");
                    txtEmNumber.setText("");
                    txtEmail.setText("");
                    txtAddress.setText("");
                    txtUserName.setText("");
                    txtPassword.setText("");
                    txtRePassword.setText("");
                    txtAnswer.setText("");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }


    }//GEN-LAST:event_btnRegisterMouseClicked

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        txtPassword.setText("");
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtRePasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRePasswordFocusGained
        txtRePassword.setText("");
    }//GEN-LAST:event_txtRePasswordFocusGained

    private void panelShowIcon3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelShowIcon3MousePressed
        lblshow3.setVisible(true);
        lblHide3.setVisible(false);
        txtPassword.setEchoChar((char) 0);
    }//GEN-LAST:event_panelShowIcon3MousePressed

    private void panelShowIcon3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelShowIcon3MouseReleased
        lblshow3.setVisible(false);
        lblHide3.setVisible(true);
        txtPassword.setEchoChar('*');
    }//GEN-LAST:event_panelShowIcon3MouseReleased

    private void panelShowIcon4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelShowIcon4MousePressed
        lblshow4.setVisible(true);
        lblHide4.setVisible(false);
        txtRePassword.setEchoChar((char) 0);
    }//GEN-LAST:event_panelShowIcon4MousePressed

    private void panelShowIcon4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelShowIcon4MouseReleased
        lblshow4.setVisible(false);
        lblHide4.setVisible(true);
        txtRePassword.setEchoChar('*');
    }//GEN-LAST:event_panelShowIcon4MouseReleased

    private void rdoMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMaleActionPerformed
        if (rdoMale.isSelected()) {
            rdoFemale.setSelected(false);
            gender = rdoMale.getText();
        }
    }//GEN-LAST:event_rdoMaleActionPerformed

    private void rdoFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoFemaleActionPerformed
        if (rdoFemale.isSelected()) {
            rdoMale.setSelected(false);
            gender = rdoFemale.getText();
        }
    }//GEN-LAST:event_rdoFemaleActionPerformed

    private void rdoPermitedUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPermitedUserActionPerformed
        if (rdoPermitedUser.isSelected()) {
            rdoUser.setSelected(false);
            userType = "Permited User";
        }
    }//GEN-LAST:event_rdoPermitedUserActionPerformed

    private void rdoUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoUserActionPerformed
        if (rdoUser.isSelected()) {
            rdoPermitedUser.setSelected(false);
            userType = "User";
        }
    }//GEN-LAST:event_rdoUserActionPerformed

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked

        try {
            if (txtSearch.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "notext");
                showRecods();
            } else {
                String userName = txtSearch.getText();
                try {
                    stmt = conn.createStatement();
                    //WHERE userName = '" + userName + "';
                    String sql = "SELECT * FROM logindetails ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM logindetails WHERE userName LIKE '%" + userName + "%'";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tableUser.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        JOptionPane.showMessageDialog(null, "recode not found");
                        showRecods();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any user name");
        }
    }//GEN-LAST:event_btnSearchMouseClicked

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked

        tabRightNavigation.setSelectedIndex(0);
        panelUserManagement.setVisible(true);
        panelButtons.setVisible(false);
        panelRegisterButtons.setVisible(false);
        panelUpdateButtons.setVisible(false);
        txtUserName.setEnabled(true);

        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtNic.setEditable(false);
        //txtDateOfBirth.setEditable(false);
        txtContactNo.setEditable(false);
        txtEmNumber.setEditable(false);
        txtEmail.setEditable(false);
        txtAddress.setEditable(false);
        txtUserName.setEditable(false);
        txtPassword.setEditable(false);
        txtRePassword.setEditable(false);

        CalanderDob.setEnabled(false);
        rdoMale.setEnabled(false);
        rdoFemale.setEnabled(false);
        rdoPermitedUser.setEnabled(false);
        rdoUser.setEnabled(false);
        comboSecQuestion.setEnabled(false);
        txtAnswer.setEditable(false);
        txtPassword.setFocusable(false);
        txtRePassword.setFocusable(false);
        panelShowIcon3.hide();
        panelShowIcon4.hide();

        try {
            int rowIndex = tableUser.getSelectedRow();
            int colmnIndex = 2;
            String selectedUser = (String) tableUser.getModel().getValueAt(rowIndex, colmnIndex);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE uUserName = '" + selectedUser + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String dte = (rs.getString("uDateOfBirth"));
                ((JTextField) CalanderDob.getDateEditor().getUiComponent()).setText(dte);
                txtFirstName.setText(rs.getString("uFirstName"));
                txtLastName.setText(rs.getString("uLastName"));
                txtNic.setText(rs.getString("uNic"));
                //txtDateOfBirth.setText(rs.getString("uDateOfBirth"));
                txtContactNo.setText(rs.getString("uContactNo"));
                txtEmNumber.setText(rs.getString("uEmContactNo"));
                txtEmail.setText(rs.getString("uEmail"));
                txtAddress.setText(rs.getString("uAddress"));
                txtUserName.setText(rs.getString("uUserName"));
                txtPassword.setText(rs.getString("uPassword"));
                txtRePassword.setText(rs.getString("uPassword"));
                txtAnswer.setText(rs.getString("uSecAnswer"));
                gender = rs.getString("uGender");
                if (gender.equals("Male")) {
                    rdoMale.setSelected(true);
                    rdoFemale.setSelected(false);

                }
                if (gender.equals("Female")) {
                    rdoFemale.setSelected(true);
                    rdoMale.setSelected(false);

                }
                String userType = rs.getString("userType");
                if (userType.equals("Permited User")) {
                    rdoPermitedUser.setSelected(true);
                    rdoUser.setSelected(false);

                }
                if (userType.equals("User")) {
                    rdoUser.setSelected(true);
                    rdoPermitedUser.setSelected(false);

                }
                String secQuestion = rs.getString("uSecQuistion");
                if (secQuestion.equals("what is your Faviorite Colour ?")) {
                    comboSecQuestion.setSelectedIndex(0);
                } else if (secQuestion.equals("what is your first phone No ?")) {
                    comboSecQuestion.setSelectedIndex(1);
                } else {
                    comboSecQuestion.setSelectedIndex(2);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_tableUserMouseClicked

    private void btnAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUserMouseClicked

        String usr = lblCurrentUser.getText();

        String usrTpe;

        try {

            stmt = conn.createStatement();

            String sql = "SELECT userType FROM user WHERE uUserName = '" + usr + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                usrTpe = (rs.getString("userType"));
                //JOptionPane.showMessageDialog(null, usrTpe);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "User type not found");
        }

        panelUserManagement.setVisible(true);
        panelButtons.setVisible(true);
        panelUpdateButtons.setVisible(false);
        panelRegisterButtons.setVisible(true);
        panelShowIcon3.setVisible(true);
        panelShowIcon4.setVisible(true);
        txtUserName.setEnabled(true);
        txtPassword.setEnabled(true);
        txtRePassword.setEnabled(true);
        CalanderDob.setEnabled(true);
        txtFirstName.setText("");
        txtLastName.setText("");
        txtNic.setText("");
        txtContactNo.setText("");
        txtEmNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtRePassword.setText("");
        txtAnswer.setText("");

        txtFirstName.setEditable(true);
        txtLastName.setEditable(true);
        txtNic.setEditable(true);
        txtContactNo.setEditable(true);
        txtEmNumber.setEditable(true);
        txtEmail.setEditable(true);
        txtAddress.setEditable(true);
        txtUserName.setEditable(true);
        txtPassword.setEditable(true);
        txtRePassword.setEditable(true);
        txtAnswer.setEditable(true);
        rdoMale.setEnabled(true);
        rdoFemale.setEnabled(true);
        rdoUser.setEnabled(true);
        rdoPermitedUser.setEnabled(true);
        comboSecQuestion.setEnabled(true);

    }//GEN-LAST:event_btnAddUserMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked

        int rowIndex = tableUser.getSelectedRow();
        int SelectedCount = tableUser.getSelectedRowCount();
        if (SelectedCount < 0) {
            panelUserManagement.setVisible(false);
            JOptionPane.showMessageDialog(null, "Select a user before update..");

        } else if (SelectedCount > 1) {
            panelUserManagement.setVisible(false);
            JOptionPane.showMessageDialog(null, "Select a Single user before update..");
        } else {
            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Update User Details ?");

            if (yesOrNo == JOptionPane.YES_OPTION) {

                tabRightNavigation.setSelectedIndex(0);
                panelUserManagement.setVisible(true);
                panelButtons.setVisible(true);
                panelRegisterButtons.setVisible(false);
                panelUpdateButtons.setVisible(true);
                CalanderDob.setEnabled(true);
                txtFirstName.setEditable(true);
                txtLastName.setEditable(true);
                txtNic.setEditable(true);
                txtContactNo.setEditable(true);
                txtEmNumber.setEditable(true);
                txtEmail.setEditable(true);
                txtAddress.setEditable(true);
                txtUserName.setEditable(true);
                txtPassword.setEditable(true);
                txtRePassword.setEditable(true);
                rdoMale.setEnabled(true);
                rdoFemale.setEnabled(true);
                comboSecQuestion.setEnabled(true);
                txtAnswer.setEditable(true);
                rdoPermitedUser.setEnabled(true);
                rdoUser.setEnabled(true);
                txtPassword.setFocusable(true);
                txtRePassword.setFocusable(true);
                panelShowIcon3.setVisible(true);
                panelShowIcon4.setVisible(true);

            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }
        }

    }//GEN-LAST:event_btnUpdateMouseClicked

    private void panelUpdateOkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelUpdateOkMouseClicked

        try {
            stmt = conn.createStatement();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dob = ((JTextField) CalanderDob.getDateEditor().getUiComponent()).getText();
            String fName = txtFirstName.getText();
            String lName = txtLastName.getText();
            String nic = txtNic.getText();
            String sex = gender;
            String contactNo = txtContactNo.getText();
            String emContactNo = txtEmNumber.getText();
            String mail = txtEmail.getText();
            String address = txtAddress.getText();
            String userName = txtUserName.getText();
            String ps1 = txtPassword.getText();
            password = ps1;
            String secQuestion = (String) comboSecQuestion.getSelectedItem();
            String secAnswer = txtAnswer.getText();
            String userType = this.userType;

            if (fName.isEmpty() || fName.length() < 3 || lName.isEmpty() || lName.length() < 3) {
                JOptionPane.showMessageDialog(null, "Invalid Name Found..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insert a Valid NIC Number..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (dob.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter your date of birth...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address is missing...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (contactNo.isEmpty() || emContactNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number is incorrect", "error", JOptionPane.ERROR_MESSAGE);
            } else if (mail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a valid Email address...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (userName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a valid user name...", "error", JOptionPane.ERROR_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Set a strong password..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (secQuestion.isEmpty() || secAnswer.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Security Question or Answer is missing..", "error", JOptionPane.ERROR_MESSAGE);
            } else if (userType.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a user Type", "error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isFirstNameValid = txtValidation(fName);
                boolean isLastNameValid = txtValidation(lName);
                boolean isEmailValid = validateEmail(mail);
                boolean isContactNoValid = validatePhoneNumber(contactNo);
                boolean isEmContactNoValid = validatePhoneNumber(emContactNo);
                boolean isNICValid = validateNIC(nic);

                if (!isFirstNameValid) {
                    JOptionPane.showMessageDialog(null, "First Name is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isLastNameValid) {
                    JOptionPane.showMessageDialog(null, "Last Name is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isEmailValid) {
                    JOptionPane.showMessageDialog(null, "Email is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isContactNoValid) {
                    JOptionPane.showMessageDialog(null, "Contact Number is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isEmContactNoValid) {
                    JOptionPane.showMessageDialog(null, "Emergency Contact Number is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else if (!isNICValid) {
                    JOptionPane.showMessageDialog(null, "NIC is Not Valid...", "error", JOptionPane.ERROR_MESSAGE);
                } else {

                    String sql = "UPDATE user SET uFirstName = '" + fName + "', uLastName = '" + lName + "', uNic = '" + nic + "', uGender = '" + sex + "', uDateOfBirth = '" + dob + "', uContactNo = '" + contactNo + "', uEmContactNo = '" + emContactNo + "', uEmail = '" + mail + "', uAddress = '" + address + "', uPassword = '" + password + "', uSecQuistion = '" + secQuestion + "', uSecAnswer = '" + secAnswer + "', userType = '" + userType + "' WHERE uUserName = '" + userName + "';";
                    String sql2 = "UPDATE logindetails SET uLastName = '" + lName + "', userType = '" + userType + "' WHERE username = '" + userName + "';";
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql2);
                    JOptionPane.showMessageDialog(null, "Data Updated Successfully...");
                    showRecods();
                    tblCount();

            

                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtNic.setText("");
                    //txtDateOfBirth.setText("");
                    txtContactNo.setText("");
                    txtEmNumber.setText("");
                    txtEmail.setText("");
                    txtAddress.setText("");
                    txtUserName.setText("");
                    txtPassword.setText("");
                    txtRePassword.setText("");
                    txtAnswer.setText("");

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }


    }//GEN-LAST:event_panelUpdateOkMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked

        int rowIndex = tableUser.getSelectedRow();
        if (rowIndex < 0) {
            panelUserManagement.setVisible(false);
            JOptionPane.showMessageDialog(null, "Select a user before Delete..");
        } else {
            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Delete User ?");
            if (yesOrNo == JOptionPane.YES_OPTION) {
                try {
                    rowIndex = tableUser.getSelectedRow();
                    int colmnIndex = 2;
                    String selectedUser = (String) tableUser.getModel().getValueAt(rowIndex, colmnIndex);
                    stmt = conn.createStatement();
                    String sql = "DELETE FROM user WHERE uUserName = '" + selectedUser + "';";
                    String sql1 = "DELETE FROM logindetails WHERE userName = '" + selectedUser + "';";
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql1);
                    JOptionPane.showMessageDialog(null, "Deletion Successfull...");
                    showRecods();
                    tblCount();
                    panelUserManagement.setVisible(false);

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(this, e);
                }
            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }
        }
    }//GEN-LAST:event_btnDeleteMouseClicked

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

    private void txtSearchInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSearchInputMethodTextChanged

        try {
            if (txtSearch.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "notext");
                showRecods();
            } else {
                String userName = txtSearch.getText();
                try {

                    stmt = conn.createStatement();
                    //WHERE userName = '" + userName + "';
                    String sql = "SELECT * FROM logindetails ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM logindetails WHERE userName = '" + userName + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tableUser.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        JOptionPane.showMessageDialog(null, "recode not found");
                        showRecods();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any user name");
        }
    }//GEN-LAST:event_txtSearchInputMethodTextChanged

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

        try {
            if (txtSearch.getText().isEmpty()) {
                showRecods();
            } else {
                String userName = txtSearch.getText();

                boolean result = txtValidation(userName);
                if (result == false) {
                    JOptionPane.showMessageDialog(null, "Letters Only, Can not Enter symbols or Number.. ", "Eror", JOptionPane.ERROR_MESSAGE);
                } else {

                    try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM logindetails ";
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM logindetails WHERE userName LIKE '%" + userName + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tableUser.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            showRecods();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any user name");
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked

        txtFirstName.setText("");
        txtLastName.setText("");
        txtNic.setText("");
        txtContactNo.setText("");
        txtEmNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtRePassword.setText("");
        txtAnswer.setText("");
        txtFirstName.setEditable(true);
        txtLastName.setEditable(true);
        txtNic.setEditable(true);
        txtContactNo.setEditable(true);
        txtEmNumber.setEditable(true);
        txtEmail.setEditable(true);
        txtAddress.setEditable(true);
        txtUserName.setEditable(true);
        txtPassword.setEditable(true);
        txtRePassword.setEditable(true);
        txtAnswer.setEditable(true);
        rdoMale.setEnabled(true);
        rdoFemale.setEnabled(true);
        rdoUser.setEnabled(true);
        rdoPermitedUser.setEnabled(true);
        comboSecQuestion.setEnabled(true);
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnClear2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear2MouseClicked

        txtFirstName.setText("");
        txtLastName.setText("");
        txtNic.setText("");
        txtContactNo.setText("");
        txtEmNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtRePassword.setText("");
        txtAnswer.setText("");
        txtFirstName.setEditable(true);
        txtLastName.setEditable(true);
        txtNic.setEditable(true);
        txtContactNo.setEditable(true);
        txtEmNumber.setEditable(true);
        txtEmail.setEditable(true);
        txtAddress.setEditable(true);
        txtUserName.setEditable(true);
        txtPassword.setEditable(true);
        txtRePassword.setEditable(true);
        txtAnswer.setEditable(true);
        rdoMale.setEnabled(true);
        rdoFemale.setEnabled(true);
        rdoUser.setEnabled(true);
        rdoPermitedUser.setEnabled(true);
        comboSecQuestion.setEnabled(true);
    }//GEN-LAST:event_btnClear2MouseClicked

    private void btnAddUser2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUser2MouseClicked

        panelSupllierManagemet.setVisible(true);
        panelSupButtons.setVisible(true);
        panelSupUpdateButtons.setVisible(false);
        panelSupRegisterButtons.setVisible(true);

        txtSupName.setText("");
        txtCompanyName.setText("");
        txtSupContactNo.setText("");
        txtSupMail.setText("");
        txtSupAddress.setText("");
        txtRepFirstName.setText("");
        txtRepLastName.setText("");
        txtRepNice.setText("");
        txtRepContactNo.setText("");
        txtRepMail.setText("");

        rdoSupMale.setSelected(true);

        txtSupName.setEditable(true);
        txtCompanyName.setEditable(true);
        txtSupContactNo.setEditable(true);
        txtSupMail.setEditable(true);
        txtSupAddress.setEditable(true);
        txtRepFirstName.setEditable(true);
        txtRepLastName.setEditable(true);
        txtRepNice.setEditable(true);
        txtRepContactNo.setEditable(true);
        txtRepMail.setEditable(true);

        rdoSupMale.setEnabled(true);
        rdoSupFemale.setEnabled(true);

        supId();
    }//GEN-LAST:event_btnAddUser2MouseClicked

    private void tableSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSupplierMouseClicked

        tabRightNavigation.setSelectedIndex(1);
        panelSupllierManagemet.setVisible(true);

        try {
            int rowIndex = tableSupplier.getSelectedRow();
            int colmnIndex = 0;

            String selectedSupId = (String) tableSupplier.getModel().getValueAt(rowIndex, colmnIndex);

            stmt = conn.createStatement();

            String sql = "SELECT * FROM supplierfulldetails WHERE supId = '" + selectedSupId + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                txtSupName.setEditable(false);
                txtCompanyName.setEditable(false);
                txtSupContactNo.setEditable(false);
                txtSupMail.setEditable(false);
                txtSupAddress.setEditable(false);
                txtRepFirstName.setEditable(false);
                txtRepLastName.setEditable(false);
                txtRepNice.setEditable(false);
                txtRepContactNo.setEditable(false);
                txtRepMail.setEditable(false);

                if (supGender.equals("Male")) {
                    rdoSupFemale.setEnabled(false);
                }

                if (supGender.equals("Female")) {
                    rdoSupMale.setEnabled(false);
                }

                lblSupId.setText((rs.getString("supId")));
                txtSupName.setText(rs.getString("supName"));
                txtCompanyName.setText(rs.getString("companyName"));
                txtSupContactNo.setText(rs.getString("supContactNo"));
                txtSupMail.setText(rs.getString("supMail"));
                txtSupAddress.setText(rs.getString("supAddress"));

                txtRepFirstName.setText(rs.getString("repFirstName"));
                txtRepLastName.setText(rs.getString("repLastName"));
                txtRepNice.setText(rs.getString("repNic"));
                txtRepContactNo.setText(rs.getString("repContactNo"));
                txtRepMail.setText(rs.getString("repMail"));

                supGender = rs.getString("repGender");

                if (supGender.equals("Male")) {
                    rdoSupMale.setSelected(true);
                    rdoSupFemale.setSelected(false);
                    System.out.println("Sup male");
                }
                if (supGender.equals("Female")) {
                    rdoSupFemale.setSelected(true);
                    rdoSupMale.setSelected(false);
                    System.out.println("sup female");
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }


    }//GEN-LAST:event_tableSupplierMouseClicked

    private void btnDelete2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelete2MouseClicked

        int rowIndex = tableSupplier.getSelectedRow();

        if (rowIndex < 0) {
            panelSupllierManagemet.setVisible(false);
            JOptionPane.showMessageDialog(null, "Select a Supplier before Delete..");

        } else {

            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Delete this Supplier ?");

            if (yesOrNo == JOptionPane.YES_OPTION) {

                try {

                    rowIndex = tableSupplier.getSelectedRow();
                    int colmnIndex = 0;

                    String selectedSup = (String) tableSupplier.getModel().getValueAt(rowIndex, colmnIndex);

                    stmt = conn.createStatement();

                    String sql = "DELETE FROM company WHERE companyId = '" + selectedSup + "';";
                    String sql1 = "DELETE FROM supplierfulldetails WHERE supId = '" + selectedSup + "';";

                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql1);

                    JOptionPane.showMessageDialog(null, "Deletion Successfull...");
                    showRecodsCompany();
                    tblCountSupplier();
                    panelSupllierManagemet.setVisible(false);

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(this, e);
                }

            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }

        }

    }//GEN-LAST:event_btnDelete2MouseClicked

    private void btnSupUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupUpdateMouseClicked

        int rowIndex = tableSupplier.getSelectedRow();

        if (rowIndex < 0) {
            panelSupllierManagemet.setVisible(false);
            JOptionPane.showMessageDialog(null, "Select a Supplier before update..");

        } else {
            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Update Supplier Details ?");

            if (yesOrNo == JOptionPane.YES_OPTION) {

                tabRightNavigation.setSelectedIndex(2);
                panelSupllierManagemet.setVisible(true);
                panelSupButtons.setVisible(true);
                panelSupRegisterButtons.setVisible(false);
                panelSupUpdateButtons.setVisible(true);

                txtSupName.setEditable(true);
                txtCompanyName.setEditable(true);
                txtSupContactNo.setEditable(true);
                txtSupMail.setEditable(true);
                txtSupAddress.setEditable(true);
                txtRepFirstName.setEditable(true);
                txtRepLastName.setEditable(true);
                txtRepNice.setEditable(true);
                txtRepContactNo.setEditable(true);
                txtRepMail.setEditable(true);

                rdoSupMale.setEnabled(true);
                rdoSupFemale.setEnabled(true);

            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {
                // User closed the dialog or clicked the "X" button
                // Handle accordingly
            }
        }

    }//GEN-LAST:event_btnSupUpdateMouseClicked

    private void btnClear5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear5MouseClicked

    private void btnSupUpdateOkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupUpdateOkMouseClicked

        String supId = lblSupId.getText();
        String supName = txtSupName.getText();
        String comName = txtCompanyName.getText();
        String supContactNo = txtSupContactNo.getText();
        String supMail = txtSupMail.getText();
        String supAddress = txtSupAddress.getText();
        String repFirstName = txtRepFirstName.getText();
        String repLastName = txtRepLastName.getText();
        String repNic = txtRepNice.getText();
        String repSex = supGender;
        String repContact = txtRepContactNo.getText();
        String repMail = txtRepMail.getText();
        try {

            stmt = conn.createStatement();

            String sql = "UPDATE company SET companyName = '" + comName + "',supName = '" + supName + "' WHERE companyId = '" + supId + "';";

            String sql2 = "UPDATE supplierfulldetails SET companyName = '" + comName + "',supName = '" + supName + "',supContactNo = '" + supContactNo + "',supMail = '" + supMail + "',supAddress = '" + supAddress + "',repFirstName = '" + repFirstName + "',repLastName = '" + repLastName + "',repNic = '" + repNic + "',repGender = '" + repSex + "',repContactNo = '" + repContact + "',repMail =  '" + repMail + "' WHERE supId = '" + supId + "';";

            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);

            JOptionPane.showMessageDialog(null, "Data Updated Successfully...");
            showRecodsCompany();
            tblCountSupplier();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnSupUpdateOkMouseClicked

    private void btnSupRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupRegMouseClicked

        //*******validation must be here..........
        String supId = lblSupId.getText();
        String supName = txtSupName.getText();
        String comName = txtCompanyName.getText();
        String supContactNo = txtSupContactNo.getText();
        String supMail = txtSupMail.getText();
        String supAddress = txtSupAddress.getText();
        String repFirstName = txtRepFirstName.getText();
        String repLastName = txtRepLastName.getText();
        String repNic = txtRepNice.getText();
        String repSex = supGender;
        String repContact = txtRepContactNo.getText();
        String repMail = txtRepMail.getText();

        try {
            stmt = conn.createStatement();

            String sql = "INSERT INTO company (companyId,companyName,supName) VALUES('" + supId + "','" + comName + "','" + supName + "');";

            String sql2 = "INSERT INTO supplierfulldetails (supId,companyName,supName,supContactNo,supMail,supAddress,repFirstName,repLastName,repNic,repGender,repContactNo,repMail) VALUES ('" + supId + "', '" + comName + "','" + supName + "','" + supContactNo + "','" + supMail + "', '" + supAddress + "','" + repFirstName + "','" + repLastName + "','" + repNic + "', '" + repSex + "','" + repContact + "','" + repMail + "');";

            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null, "Data Inserted Successfully...");

        } catch (SQLException ex) {
            Logger.getLogger(DashBoardForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        supId();
        showRecodsCompany();
        tblCountSupplier();
        txtSupName.setText("");
        txtCompanyName.setText("");
        txtSupContactNo.setText("");
        txtSupMail.setText("");
        txtSupAddress.setText("");
        txtRepFirstName.setText("");
        txtRepLastName.setText("");
        txtRepNice.setText("");
        txtRepContactNo.setText("");
        txtRepMail.setText("");

        rdoSupMale.setSelected(true);

    }//GEN-LAST:event_btnSupRegMouseClicked

    private void btnClear4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear4MouseClicked

    private void btnLblItemRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLblItemRegMouseClicked
        btnItemReg.setBackground(new Color(120, 168, 252));
        btnAddGrn.setBackground(new Color(68, 194, 137));
        btnShift.setBackground(new Color(68, 194, 137));
        btnViewInventory.setBackground(new Color(68, 194, 137));
        tabInventory.setSelectedIndex(0);
    }//GEN-LAST:event_btnLblItemRegMouseClicked

    private void btnLblAddGrnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLblAddGrnMouseClicked
        btnItemReg.setBackground(new Color(68, 194, 137));
        btnAddGrn.setBackground(new Color(120, 168, 252));
        btnShift.setBackground(new Color(68, 194, 137));
        btnViewInventory.setBackground(new Color(68, 194, 137));
        tabInventory.setSelectedIndex(1);
    }//GEN-LAST:event_btnLblAddGrnMouseClicked

    private void btnLblShiftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLblShiftMouseClicked
        btnItemReg.setBackground(new Color(68, 194, 137));
        btnAddGrn.setBackground(new Color(68, 194, 137));
        btnShift.setBackground(new Color(120, 168, 252));
        btnViewInventory.setBackground(new Color(68, 194, 137));
        tabInventory.setSelectedIndex(2);
    }//GEN-LAST:event_btnLblShiftMouseClicked

    private void btnLblViewInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLblViewInventoryMouseClicked
        btnItemReg.setBackground(new Color(68, 194, 137));
        btnAddGrn.setBackground(new Color(68, 194, 137));
        btnShift.setBackground(new Color(68, 194, 137));
        btnViewInventory.setBackground(new Color(120, 168, 252));
        //tabInventory.setSelectedIndex(3);
        
        ViewInventory vinventory = new ViewInventory(LoginUser);
        vinventory.setVisible(true);
        
    }//GEN-LAST:event_btnLblViewInventoryMouseClicked

    private void rdoSupMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSupMaleActionPerformed
        if (rdoSupMale.isSelected()) {
            rdoSupFemale.setSelected(false);
            supGender = rdoSupMale.getText();
        }
    }//GEN-LAST:event_rdoSupMaleActionPerformed

    private void rdoSupFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSupFemaleActionPerformed
        if (rdoSupFemale.isSelected()) {
            rdoSupMale.setSelected(false);
            supGender = rdoSupFemale.getText();
        }
    }//GEN-LAST:event_rdoSupFemaleActionPerformed

    private void lblCatagorySettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCatagorySettingsMouseClicked
        CatagoryManagement ctManagement = new CatagoryManagement();
        ctManagement.setVisible(true);
    }//GEN-LAST:event_lblCatagorySettingsMouseClicked

    private void lblCatagorySettingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCatagorySettingsMousePressed

        lblCatagorySettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/settings_icon_Red.png"))); // NOI18N

    }//GEN-LAST:event_lblCatagorySettingsMousePressed

    private void lblCatagorySettingsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCatagorySettingsMouseReleased
        lblCatagorySettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/settings_icon_White.png"))); // NOI18N
    }//GEN-LAST:event_lblCatagorySettingsMouseReleased

    private void lblUnitSettingsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUnitSettingsMousePressed
        lblUnitSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/settings_icon_Red.png"))); // NOI18N
    }//GEN-LAST:event_lblUnitSettingsMousePressed

    private void lblUnitSettingsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUnitSettingsMouseReleased
        lblUnitSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/settings_icon_White.png"))); // NOI18N
    }//GEN-LAST:event_lblUnitSettingsMouseReleased

    private void lblUnitSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUnitSettingsMouseClicked
        UnitManagement unitManagement = new UnitManagement();
        unitManagement.setVisible(true);
    }//GEN-LAST:event_lblUnitSettingsMouseClicked

    private void comboCompanyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCompanyMouseClicked

    }//GEN-LAST:event_comboCompanyMouseClicked

    private void comboCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCompanyActionPerformed

        String com = (String) comboCompany.getSelectedItem();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM company WHERE companyName = '" + com + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String companyId = rs.getString("companyId");

                lblSlectedCom.setText(companyId);
            }

        } catch (Exception e) {
        }


    }//GEN-LAST:event_comboCompanyActionPerformed

    private void btItemAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btItemAddMouseClicked

        String comId = lblSlectedCom.getText();
        String comName = (String) comboCompany.getSelectedItem();
        String itemId = lblProductId.getText();
        String itemName = txtProductName.getText();
        String cat = (String) comboCatagory.getSelectedItem();
        String unit = (String) comboUnit.getSelectedItem();
        String shopMax = txtShopMax.getText();
        String store1Max = txtStore1Max.getText();
        String reOrderLevel = txtReOrderLevel.getText();

        try {

            stmt = conn.createStatement();
            String sql = "INSERT INTO items (itemId,itemName,companyName,catagory,unit,shopMax,storeMax,reOrderLevel) VALUES ('" + itemId + "','" + itemName + "','" + comName + "','" + cat + "','" + unit + "','" + shopMax + "','" + store1Max + "','" + reOrderLevel + "');";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Item Registerd Successfully..");

            itemId();
            showRecodsItem();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

        comboCompany.setSelectedItem(null);
        txtProductName.setText("");
        comboCatagory.setSelectedItem(null);
        comboUnit.setSelectedItem(null);
        txtShopMax.setText("");
        txtStore1Max.setText("");
        txtReOrderLevel.setText("");


    }//GEN-LAST:event_btItemAddMouseClicked

    private void tableItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableItemMouseClicked

        btItemAdd.setVisible(false);
        btnItemUpdate.setVisible(true);
        btnItemRemove.setVisible(true);

        try {
            int rowIndex = tableItem.getSelectedRow();
            int colmnIndex = 0;

            String selectedItem = (String) tableItem.getValueAt(rowIndex, colmnIndex);

            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorymanagementsystem");
            conn.createStatement();
            String sql = "SELECT * FROM items WHERE itemId = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, selectedItem);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                lblProductId.setText(rs.getString("itemId"));
                txtProductName.setText(rs.getString("itemName"));
                txtShopMax.setText(rs.getString("shopMax"));
                txtStore1Max.setText(rs.getString("storeMax"));
                txtReOrderLevel.setText(rs.getString("reOrderLevel"));

                String cat = rs.getString("catagory");
                comboCatagory.setSelectedItem(cat);
                System.out.println(cat);

                String unit = rs.getString("unit");
                comboUnit.setSelectedItem(unit);
                System.out.println(unit);

                String company = rs.getString("companyName");
                comboCompany.setSelectedItem(company);
                System.out.println(company);
            }

            // Close the resources in reverse order of their creation
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }//GEN-LAST:event_tableItemMouseClicked

    private void txtItemSearchInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtItemSearchInputMethodTextChanged

        try {
            if (txtItemSearch.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "notext");
                showRecodsItem();
            } else {
                String item = txtItemSearch.getText();

                try {

                    //userName = txtSearch.getText();
                    stmt = conn.createStatement();
                    //WHERE userName = '" + userName + "';
                    String sql = "SELECT * FROM item ";

                    rs = stmt.executeQuery(sql);

                    if (rs.next()) {

                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM item WHERE itemId = '" + item + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tableItem.setModel(DbUtils.resultSetToTableModel(res));

                        //showRecods();
                    } else {
                        JOptionPane.showMessageDialog(null, "recode not found");
                        showRecodsItem();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any user name");
        }
    }//GEN-LAST:event_txtItemSearchInputMethodTextChanged

    private void txtItemSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemSearchKeyReleased

        String item = txtItemSearch.getText();
        String filterOption = (String) comboItemFilter.getSelectedItem();
        System.out.println(item);

        if (filterOption.equals("Item_ID")) {

            try {
                if (txtItemSearch.getText().isEmpty()) {
                    //JOptionPane.showMessageDialog(null, "notext");
                    showRecodsItem();
                } else {

                    try {

                        //userName = txtSearch.getText();
                        stmt = conn.createStatement();
                        //WHERE userName = '" + userName + "';
                        String sql = "SELECT * FROM items ";

                        rs = stmt.executeQuery(sql);

                        if (rs.next()) {

                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM items WHERE itemId LIKE '%" + item + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tableItem.setModel(DbUtils.resultSetToTableModel(res));

                            //showRecods();
                        } else {
                            //JOptionPane.showMessageDialog(null, "recode not found");
                            showRecodsItem();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Not found any items ");
            }

        } else if (filterOption.equals("Item_Name")) {

            try {
                if (txtItemSearch.getText().isEmpty()) {
                    //JOptionPane.showMessageDialog(null, "notext");
                    showRecodsItem();
                } else {

                    try {

                        //userName = txtSearch.getText();
                        stmt = conn.createStatement();
                        //WHERE userName = '" + userName + "';
                        String sql = "SELECT * FROM items ";

                        rs = stmt.executeQuery(sql);

                        if (rs.next()) {

                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM items WHERE itemName LIKE '%" + item + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tableItem.setModel(DbUtils.resultSetToTableModel(res));

                            //showRecods();
                        } else {
                            //JOptionPane.showMessageDialog(null, "recode not found");
                            showRecodsItem();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Not found any Item ");
            }

        } else if (filterOption.equals("Catagory")) {
            try {
                if (txtItemSearch.getText().isEmpty()) {
                    //JOptionPane.showMessageDialog(null, "notext");
                    showRecodsItem();
                } else {

                    try {

                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM items ";

                        rs = stmt.executeQuery(sql);

                        if (rs.next()) {

                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM items WHERE catagory LIKE '%" + item + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tableItem.setModel(DbUtils.resultSetToTableModel(res));
                        } else {
                            //JOptionPane.showMessageDialog(null, "recode not found");
                            showRecodsItem();
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Not found any Item");
            }
        } else if (filterOption.equals("Company")) {
            try {
                if (txtItemSearch.getText().isEmpty()) {
                    //JOptionPane.showMessageDialog(null, "notext");
                    showRecodsItem();
                } else {

                    try {

                        //userName = txtSearch.getText();
                        stmt = conn.createStatement();
                        //WHERE userName = '" + userName + "';
                        String sql = "SELECT * FROM items ";

                        rs = stmt.executeQuery(sql);

                        if (rs.next()) {

                            stmt = conn.createStatement();
                            String sqlSelect = "SELECT * FROM items WHERE companyName LIKE '%" + item + "%'";   //'" + userName + "';";
                            ResultSet res = stmt.executeQuery(sqlSelect);
                            tableItem.setModel(DbUtils.resultSetToTableModel(res));

                            //showRecods();
                        } else {
                            //JOptionPane.showMessageDialog(null, "recode not found");
                            showRecodsItem();
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Not found any Item");
            }
        }


    }//GEN-LAST:event_txtItemSearchKeyReleased

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        tableItem.clearSelection();
        lblSlectedCom.setText("");
        comboCompany.setSelectedItem(null);
        txtProductName.setText("");
        comboCatagory.setSelectedItem(null);
        comboUnit.setSelectedItem(null);
        txtShopMax.setText("");
        txtStore1Max.setText("");
        txtReOrderLevel.setText("");

        btItemAdd.setVisible(true);
        btnItemUpdate.setVisible(false);
        btnItemRemove.setVisible(false);

        itemId();
        supId();
        showRecodsItem();
        updateCatagoryCombo();
        updateUnitCombo();
        updateCompanyCombo();

    }//GEN-LAST:event_jLabel5MouseClicked

    private void btnItemUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnItemUpdateMouseClicked

        String comId = lblSlectedCom.getText();
        String comName = (String) comboCompany.getSelectedItem();
        String itemId = lblProductId.getText();
        String itemName = txtProductName.getText();
        String cat = (String) comboCatagory.getSelectedItem();
        String unit = (String) comboUnit.getSelectedItem();
        String shopMax = txtShopMax.getText();
        String store1Max = txtStore1Max.getText();
        String reOrderLevel = txtReOrderLevel.getText();

        try {

            stmt = conn.createStatement();
            String sql = "UPDATE items SET itemName = '" + itemName + "',companyName = '" + comName + "',catagory = '" + cat + "',unit = '" + unit + "',shopMax = '" + shopMax + "',storeMax = '" + store1Max + "',reOrderLevel = '" + reOrderLevel + "' WHERE itemId = '" + itemId + "' ;";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Item Updated Successfully..");

            showRecodsItem();
            itemId();

            lblSlectedCom.setText("");
            comboCompany.setSelectedItem(null);
            txtProductName.setText("");
            comboCatagory.setSelectedItem(null);
            comboUnit.setSelectedItem(null);
            txtShopMax.setText("");
            txtStore1Max.setText("");
            txtReOrderLevel.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }


    }//GEN-LAST:event_btnItemUpdateMouseClicked

    private void comboCatagoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCatagoryMouseClicked
        updateCatagoryCombo();
    }//GEN-LAST:event_comboCatagoryMouseClicked

    private void comboUnitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboUnitMouseClicked
        updateUnitCombo();
    }//GEN-LAST:event_comboUnitMouseClicked

    private void comboCatagoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCatagoryActionPerformed
        updateCatagoryCombo();
        updateCatagoryCombo();
    }//GEN-LAST:event_comboCatagoryActionPerformed

    private void btnBarcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarcodeMouseClicked
        
        String mfgg = ((JTextField) dteMfg.getDateEditor().getUiComponent()).getText();
        String expp = ((JTextField) dateExp.getDateEditor().getUiComponent()).getText();
        if (mfgg.equals("") || expp.equals("")) {
            JOptionPane.showMessageDialog(null, "Plese check the MFG Date and EXP Date");
        } else if (lblItemCode.isVisible() == false || comboItemName.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a item to genarate Qr");
        } else {

            String DateID = null;
            String dte = null;;
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select MAX(no) from expiredate");
                rs.next();
                rs.getString("MAX(no)");
                if (rs.getString("MAX(no)") == null) {
                    JOptionPane.showMessageDialog(null, "no id yet");
                    DateID = "0000000000000001";
                    System.out.println("date id null null");
                    JOptionPane.showMessageDialog(null, DateID);
                } else {
                    JOptionPane.showMessageDialog(null, "id is availbe ");
                    long id = Long.parseLong(rs.getString("MAX(no)").substring(1, rs.getString("MAX(no)").length()));
                    id++;
                    DateID = "0" + String.format("%015d", id);
                    System.out.println("id is 00001");
                    JOptionPane.showMessageDialog(null, DateID);

                }
            } catch (SQLException ex) {
                Logger.getLogger(DashBoardForm.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            String itmCode = lblItemCode.getText();
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMddyyyy");
            inputDateFormat.setLenient(false);
            outputDateFormat.setLenient(false);
            String mfg = ((JTextField) dteMfg.getDateEditor().getUiComponent()).getText();
            String exp = ((JTextField) dateExp.getDateEditor().getUiComponent()).getText();
            try {
                // Parsing input dates
                Date mfgDate = inputDateFormat.parse(mfg);
                Date expDate = inputDateFormat.parse(exp);
                // Formatting output dates
                String formattedMfg = outputDateFormat.format(mfgDate);
                String formattedExp = outputDateFormat.format(expDate);
                // Concatenating the formatted dates
                dte = formattedMfg + formattedExp;

                // System.out.println(result); // Output: 0701202307312023
            } catch (Exception e) {
                // Handle parsing exceptions
                e.printStackTrace();
            }
            String qr = itmCode + dte + DateID;

            try {
                String qrCodeData = qr;
                String filePath = "D:\\MLT Holdings QR\\'" + qr + "'.png";
                String charset = "UTF-8"; // or "ISO-8859-1"
                Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                BitMatrix matrix = new MultiFormatWriter().encode(
                        new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 200, 200, hintMap);
                MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                        .lastIndexOf('.') + 1), new File(filePath));
                JOptionPane.showMessageDialog(null, "QR code created Successfully.. " + qr);
                File imageFile = new File("D:\\MLT Holdings QR\\'" + qr + "'.png");
                if (imageFile.exists()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(imageFile);
                } else {
                    System.out.println("Image file not found.");
                }

            } catch (Exception e) {
                System.err.println(e);
            }

        }

    }//GEN-LAST:event_btnBarcodeMouseClicked

    private void btnBarcodeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarcodeMousePressed

        btnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_generate-barcode_blk.png"))); // NOI18N

    }//GEN-LAST:event_btnBarcodeMousePressed

    private void btnBarcodeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBarcodeMouseReleased

        btnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/button_generate-barcode.png"))); // NOI18N
    }//GEN-LAST:event_btnBarcodeMouseReleased

    private void txtItemSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemSearchActionPerformed

    private void btnItemRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnItemRemoveMouseClicked

        int rowIndex = tableItem.getSelectedRow();

        if (rowIndex < 0) {

            JOptionPane.showMessageDialog(null, "Select a Item before Remove..");

        } else {

            int yesOrNo = JOptionPane.showConfirmDialog(this, "Do You want to Remove this Item ?");

            if (yesOrNo == JOptionPane.YES_OPTION) {

                try {

                    rowIndex = tableItem.getSelectedRow();
                    int colmnIndex = 0;

                    String itemId = (String) tableItem.getModel().getValueAt(rowIndex, colmnIndex);

                    stmt = conn.createStatement();

                    String sql = "DELETE FROM items WHERE itemId = '" + itemId + "';";

                    stmt.executeUpdate(sql);

                    JOptionPane.showMessageDialog(null, "Item Removed Successfully..");
                    showRecodsItem();

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(this, e);
                }

            } else if (yesOrNo == JOptionPane.NO_OPTION) {

            } else {

            }

        }

    }//GEN-LAST:event_btnItemRemoveMouseClicked

    private void comboCompanyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCompanyNameActionPerformed

        comboItemCat();
        //cmbItem();
        //filterdItems();
        item();

        //item();
        String company = (String) comboCompanyName.getSelectedItem();
        //String cat = (String) comboItemCat.getSelectedItem();

        lblComName.setText(company);

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM company WHERE companyName = '" + company + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String supId = rs.getString("companyId");

                lblSupplierId.setText(supId);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM grn WHERE companyName='" + company + "';";
            rs = stmt.executeQuery(sql);

            //comboGRN.removeAllItems();
            while (rs.next()) {
                String grn = rs.getString("grnNo");

                // Check if the item already exists in the combo box
                boolean exists = false;
                for (int i = 0; i < comboGRN.getItemCount(); i++) {
                    if (grn.equals(comboGRN.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }

                // Add the item to the combo box only if it doesn't already exist
                if (!exists) {
                    comboGRN.addItem(grn); // Corrected line

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_comboCompanyNameActionPerformed
    
    private void comboItemNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboItemNameActionPerformed

        String item = (String) comboItemName.getSelectedItem();
        String cat = (String) comboItemCat.getSelectedItem();

        lblItemName.setText(item);
        lblCat.setText(cat);

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE itemName = '" + item + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String itemId = rs.getString("itemId");
                String shopMax = rs.getString("shopMax");
                String storeMax = rs.getString("storeMax");
                String unit = rs.getString("unit");
                lblItemCode.setText(itemId);
                lblShopMax.setText(shopMax);
                lblStore1Max.setText(storeMax);
                lblUnit.setText(unit);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

        String itmID = lblItemCode.getText();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemId = '" + itmID + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String shopAval = rs.getString("shopAvailable");
                String store1Aval = rs.getString("store1Available");
                String store2Aval = rs.getString("store2Available");
                String totStock = rs.getString("totalQty");
                String totcost = rs.getString("totalCost");
                String totValue = rs.getString("totalValue");
                String unitcost = rs.getString("unitCost");

                lblShopStock.setText(shopAval);
                lblStore1Stock.setText(store1Aval);
                lblStore2Stock.setText(store2Aval);
                lblTotalStock.setText(totStock);
                lblTotalCost.setText(totcost);
                lblTotalValue.setText(totValue);
                lblUnitCost.setText(unitcost);

            } else {
                lblShopStock.setText("00");
                lblStore1Stock.setText("00");
                lblStore2Stock.setText("00");
                lblTotalStock.setText("00");
                lblTotalCost.setText("00");
                lblTotalValue.setText("00");
                lblUnitCost.setText("00");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }


    }//GEN-LAST:event_comboItemNameActionPerformed

    private void comboItemCatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboItemCatFocusGained

    }//GEN-LAST:event_comboItemCatFocusGained

    private void rdoPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPercentActionPerformed
        rdoRupee.setSelected(false);
    }//GEN-LAST:event_rdoPercentActionPerformed

    private void rdoRupeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRupeeActionPerformed
        rdoPercent.setSelected(false);
    }//GEN-LAST:event_rdoRupeeActionPerformed

    private void txtDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyReleased


    }//GEN-LAST:event_txtDiscountKeyReleased

    private void tableInventoryItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInventoryItemMouseClicked


    }//GEN-LAST:event_tableInventoryItemMouseClicked

    private void btnGRNUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGRNUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGRNUpdateActionPerformed

    private void txtGrnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrnKeyReleased


    }//GEN-LAST:event_txtGrnKeyReleased


    private void comboGRNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboGRNActionPerformed

        grn();

        String grn = (String) comboGRN.getSelectedItem();
        txtGrn.setText(grn);

        //grn();
        //String grn = (String) comboGRN.getSelectedItem();
        //comboItemCat.removeAllItems();
        grnValueLoad();


    }//GEN-LAST:event_comboGRNActionPerformed

    private void btItemAdd3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btItemAdd3MouseClicked

        String froms = (String) comboWhere.getSelectedItem();
        String too = (String) comboTo.getSelectedItem();

        if (froms.equals(too)) {
            JOptionPane.showMessageDialog(null, "Item Shifting Location selected incorrect..");
        } else {

            if (comboCom.getSelectedItem().equals(null) || comboItm.getSelectedItem().equals(null) || txtQty.getText().equals(null)) {
                JOptionPane.showMessageDialog(null, "Enter all Data");

            } else {

                String com = (String) comboCom.getSelectedItem();
                String itm = (String) comboItm.getSelectedItem();
                String q = txtQty.getText();

                String from = (String) comboWhere.getSelectedItem();
                String to = (String) comboTo.getSelectedItem();

                int selectedWhere = comboWhere.getSelectedIndex();
                int selectedTo = comboTo.getSelectedIndex();

                if (selectedWhere == 0 && selectedTo == 1) {

                    String qty = txtQty.getText();
                    String shop = lblShopAvailble.getText();
                    String store1 = lblStore1Available.getText();
                    String store2 = lblStore2Availble.getText();

                    String shopMX = lblShopMaxLevel.getText();
                    String store1MX = lblStore1MaxLevel.getText();

                    if (shop.equals("")) {
                        shop = "0";
                    } else if (store2.equals("")) {
                        store1 = "0";
                    }

                    int quantity = Integer.parseInt(qty);

                    int shopAv = Integer.parseInt(shop);
                    int storeAv = Integer.parseInt(store1);
                    //int store2Av = Integer.parseInt(store2);

                    int shopMXLvl = Integer.parseInt(shopMX);
                    int store1MXLvl = Integer.parseInt(store1MX);

                    int shopNewStock = shopAv;
                    int store1NewStock = storeAv;

                    int canPut = store1MXLvl - storeAv;

                    String preFrom = String.valueOf(shopAv);
                    String preTo = String.valueOf(storeAv);

                    if (canPut <= store1MXLvl && canPut > 0 && quantity <= canPut && quantity <= shopAv) {

                        store1NewStock = store1NewStock + quantity;
                        shopNewStock = shopNewStock - quantity;

                        String nowFrom = String.valueOf(shopNewStock);
                        String nowTo = String.valueOf(store1NewStock);

                        //JOptionPane.showMessageDialog(null, "the shop pre :" + shopAv + "shop new :" + shopNewStock + "Store 1 pre:" + storeAv + "store 1 new : " + store1NewStock);
                        try {

                            stmt = conn.createStatement();
                            String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                            stmt.executeUpdate(sql);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        String data[] = {com, itm, from, to, preFrom, nowFrom, preTo, nowTo, q};

                        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();

                        tblModel.addRow(data);

                        JOptionPane.showMessageDialog(null, "Shifted Successfully..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Re check the stockLevels and Qnty...");
                    }

                } else if (selectedWhere == 0 && selectedTo == 2) {

                    String qty = txtQty.getText();
                    String shop = lblShopAvailble.getText();
                    String shopMX = lblShopMaxLevel.getText();
                    String store2 = lblStore2Availble.getText();

                    if (shop.equals("")) {
                        shop = "0";
                    } else if (store2.equals("")) {
                        store2 = "0";
                    }

                    int quantity1 = Integer.parseInt(qty);
                    int shopAv1 = Integer.parseInt(shop);
                    int shopMX1 = Integer.parseInt(shopMX);
                    //int store2Av11 = Integer.parseInt(store2);
                    int store2have = Integer.parseInt(store2);

                    int shopNewStock1 = shopAv1;
                    int store2NewStock1 = store2have;

                    String preFrom = String.valueOf(shopAv1);
                    String preTo = String.valueOf(store2have);

                    if (quantity1 > 0 && quantity1 <= shopAv1 && quantity1 <= shopMX1) {

                        store2NewStock1 = store2NewStock1 + quantity1;
                        shopNewStock1 = shopNewStock1 - quantity1;

                        String nowFrom = String.valueOf(shopNewStock1);
                        String nowTo = String.valueOf(store2NewStock1);

                        // JOptionPane.showMessageDialog(null, "the shop pre :" + shopAv1 + "shop new :" + shopNewStock1 + "Store 2 pre:" + store2have + "store 2 new : " + store2NewStock1);
                        try {

                            stmt = conn.createStatement();
                            String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store2Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                            stmt.executeUpdate(sql);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        String data[] = {com, itm, from, to, preFrom, nowFrom, preTo, nowTo, q};

                        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();

                        tblModel.addRow(data);

                        JOptionPane.showMessageDialog(null, "Shifted Successfully..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Re check the stockLevels and Qnty...");
                    }

                } else if (selectedWhere == 1 && selectedTo == 0) {

                    String qty = txtQty.getText();
                    String shop = lblShopAvailble.getText();
                    String shopMX = lblShopMaxLevel.getText();
                    String store1 = lblStore1Available.getText();
                    String store1MX = lblStore1MaxLevel.getText();

                    if (shop.equals("")) {
                        shop = "0";
                    } else if (store1.equals("")) {
                        store1 = "0";
                    }

                    int quantity = Integer.parseInt(qty);

                    int shopAv = Integer.parseInt(shop);
                    int storeAv = Integer.parseInt(store1);
                    //int store2Av = Integer.parseInt(store2);

                    int shopMXLvl = Integer.parseInt(shopMX);
                    int store1MXLvl = Integer.parseInt(store1MX);

                    int shopNewStock = shopAv;
                    int store1NewStock = storeAv;

                    int canPut = shopMXLvl - shopAv;

                    String preFrom = String.valueOf(storeAv);
                    String preTo = String.valueOf(shopAv);

                    if (canPut <= shopMXLvl && canPut > 0 && quantity <= canPut && quantity <= storeAv) {

                        shopNewStock = shopNewStock + quantity;
                        store1NewStock = store1NewStock - quantity;

                        String nowFrom = String.valueOf(store1NewStock);
                        String nowTo = String.valueOf(shopNewStock);

                        // JOptionPane.showMessageDialog(null, "the shop pre :" + shopAv + "shop new :" + shopNewStock + "Store 1 pre:" + storeAv + "store 1 new : " + store1NewStock);
                        try {

                            stmt = conn.createStatement();
                            String sql = "UPDATE maininventory SET store1Available = '" + nowFrom + "',shopAvailable = '" + nowTo + "' WHERE itemName='" + itm + "';";

                            stmt.executeUpdate(sql);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        String data[] = {com, itm, from, to, preFrom, nowFrom, preTo, nowTo, q};

                        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();

                        tblModel.addRow(data);

                        JOptionPane.showMessageDialog(null, "Shifted Successfully to Store1 to Shop..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Re check the stockLevels and Qnty...");
                    }

                } else if (selectedWhere == 1 && selectedTo == 2) {

                    String qty = txtQty.getText();
                    String shop = lblShopAvailble.getText();
                    String shopMX = lblShopMaxLevel.getText();
                    String store1 = lblStore1Available.getText();
                    String store1MX = lblStore1MaxLevel.getText();
                    String store2 = lblStore2Availble.getText();

                    if (shop.equals("")) {
                        store1 = "0";
                    } else if (store2.equals("")) {
                        store2 = "0";
                    }

                    int quantity = Integer.parseInt(qty);

                    //int shopAv = Integer.parseInt(shop);
                    int store1Av = Integer.parseInt(store1);
                    int store2Av = Integer.parseInt(store2);

                    //int shopMXLvl = Integer.parseInt(shopMX);
                    int store1MXLvl = Integer.parseInt(store1MX);
                    //int store2MXLvl = Integer.parseInt(store2MX);

                    //int shopNewStock = shopAv;
                    int store1NewStock = store1Av;
                    int store2NewStock = store2Av;

                    //int canPut = shopMXLvl - shopAv;
                    String preFrom = String.valueOf(store1Av);
                    String preTo = String.valueOf(store2Av);

                    if (quantity <= store1Av && quantity <= store1MXLvl && quantity > 0) {

                        store2NewStock = store2NewStock + quantity;
                        store1NewStock = store1NewStock - quantity;

                        String nowFrom = String.valueOf(store1NewStock);
                        String nowTo = String.valueOf(store2NewStock);

                        //JOptionPane.showMessageDialog(null, "the Store1 pre :" + store1Av + "Store 1 new :" + store1NewStock + "Store 2 pre:" + store2Av + "store 2 new : " + store2NewStock);
                        try {

                            stmt = conn.createStatement();
                            String sql = "UPDATE maininventory SET store1Available = '" + nowFrom + "',store2Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                            stmt.executeUpdate(sql);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        String data[] = {com, itm, from, to, preFrom, nowFrom, preTo, nowTo, q};

                        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();

                        tblModel.addRow(data);

                        JOptionPane.showMessageDialog(null, "Shifted Successfully to Store1 to Store2..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Re check the stockLevels and Qnty...");
                    }

                } else if (selectedWhere == 2 && selectedTo == 0) {

                    String qty = txtQty.getText();
                    String shop = lblShopAvailble.getText();
                    String shopMX = lblShopMaxLevel.getText();
                    String store1 = lblStore1Available.getText();
                    String store1MX = lblStore1MaxLevel.getText();
                    String store2 = lblStore2Availble.getText();

                    if (shop.equals("")) {
                        shop = "0";
                    } else if (store2.equals("")) {
                        store2 = "0";
                    }

                    int quantity = Integer.parseInt(qty);

                    int shopAv = Integer.parseInt(shop);
                    int store1Av = Integer.parseInt(store1);
                    int store2Av = Integer.parseInt(store2);

                    int shopMXLvl = Integer.parseInt(shopMX);
                    int store1MXLvl = Integer.parseInt(store1MX);
                    //int store2MXLvl = Integer.parseInt(store2MX);

                    int shopNewStock = shopAv;
                    int store1NewStock = store1Av;
                    int store2NewStock = store2Av;

                    int canPut = shopMXLvl - shopAv;

                    String preFrom = String.valueOf(store2Av);
                    String preTo = String.valueOf(shopAv);

                    if (quantity <= store2Av && quantity <= shopMXLvl && quantity > 0 && quantity <= canPut) {

                        shopNewStock = shopNewStock + quantity;
                        store2NewStock = store2NewStock - quantity;

                        String nowFrom = String.valueOf(store2NewStock);
                        String nowTo = String.valueOf(shopNewStock);

                        // JOptionPane.showMessageDialog(null, "the Store2 pre :" + store2Av + "Store2  new :" + store2NewStock + "shop pre:" + shopAv + "shop new : " + shopNewStock);
                        try {

                            stmt = conn.createStatement();
                            String sql = "UPDATE maininventory SET store2Available = '" + nowFrom + "',shopAvailable = '" + nowTo + "' WHERE itemName='" + itm + "';";

                            stmt.executeUpdate(sql);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        String data[] = {com, itm, from, to, preFrom, nowFrom, preTo, nowTo, q};

                        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();

                        tblModel.addRow(data);

                        JOptionPane.showMessageDialog(null, "Shifted  to Store2 to Shop Successfully..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Re check the stockLevels and Qnty...");
                    }

                } else if (selectedWhere == 2 && selectedTo == 1) {

                    String qty = txtQty.getText();
                    String shop = lblShopAvailble.getText();
                    String shopMX = lblShopMaxLevel.getText();
                    String store1 = lblStore1Available.getText();
                    String store1MX = lblStore1MaxLevel.getText();
                    String store2 = lblStore2Availble.getText();

                    if (store1.equals("")) {
                        store1 = "0";
                    } else if (store2.equals("")) {
                        store2 = "0";
                    }

                    int quantity = Integer.parseInt(qty);

                    int shopAv = Integer.parseInt(shop);
                    int store1Av = Integer.parseInt(store1);
                    int store2Av = Integer.parseInt(store2);

                    int shopMXLvl = Integer.parseInt(shopMX);
                    int store1MXLvl = Integer.parseInt(store1MX);
                    //int store2MXLvl = Integer.parseInt(store2MX);

                    int shopNewStock = shopAv;
                    int store1NewStock = store1Av;
                    int store2NewStock = store2Av;

                    int canPut = store1MXLvl - store1Av;

                    String preFrom = String.valueOf(store2Av);
                    String preTo = String.valueOf(store1Av);

                    if (quantity <= store2Av && quantity <= store1MXLvl && quantity > 0 && quantity <= canPut) {

                        store1NewStock = store1NewStock + quantity;
                        store2NewStock = store2NewStock - quantity;

                        String nowFrom = String.valueOf(store2NewStock);
                        String nowTo = String.valueOf(store1NewStock);

                        //  JOptionPane.showMessageDialog(null, "the Store2 pre :" + store2Av + "Store2  new :" + store2NewStock + "store1 pre:" + store1Av + "store 1 new : " + store1NewStock);
                        JOptionPane.showMessageDialog(null, "Shifted Store2 to store1 Successfully..");

                        try {

                            stmt = conn.createStatement();
                            String sql = "UPDATE maininventory SET store2Available = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                            stmt.executeUpdate(sql);

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }

                        String data[] = {com, itm, from, to, preFrom, nowFrom, preTo, nowTo, q};

                        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();

                        tblModel.addRow(data);

                    } else {
                        JOptionPane.showMessageDialog(null, "Re check the stockLevels and Qnty...");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Selection..");
                }

                comboItm.setSelectedItem(null);
                txtQty.setText(null);

            }

        }


    }//GEN-LAST:event_btItemAdd3MouseClicked

    private void btnItemRemove1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnItemRemove1MouseClicked

        int selectedRow = tableShift.getSelectedRow();

        DefaultTableModel tblModel = (DefaultTableModel) tableShift.getModel();
        String item = (String) tableShift.getValueAt(selectedRow, 1);
        String from = (String) tableShift.getValueAt(selectedRow, 2);
        String to = (String) tableShift.getValueAt(selectedRow, 3);
        String fromPreQty = (String) tableShift.getValueAt(selectedRow, 4);
        String toPreQty = (String) tableShift.getValueAt(selectedRow, 6);
        String qty = (String) tableShift.getValueAt(selectedRow, 8);

        try {
//                Shop
//                Store 1
//                Store 2

            if (from.equals("Shop") && to.equals("Store 1")) {
                String sql = "UPDATE maininventory SET shopAvailable = '" + fromPreQty + "',store1Available = '" + toPreQty + "' WHERE itemName='" + item + "';";

                stmt = conn.createStatement();
                //String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Shiftment deleted Successfully..");
            } else if (from.equals("Shop") && to.equals("Store 2")) {
                String sql = "UPDATE maininventory SET shopAvailable = '" + fromPreQty + "',store2Available = '" + toPreQty + "' WHERE itemName='" + item + "';";

                stmt = conn.createStatement();
                //String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                stmt.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Shiftment deleted Successfully..");
            } else if (from.equals("Store 1") && to.equals("Shop")) {
                String sql = "UPDATE maininventory SET store1Available = '" + fromPreQty + "',shopAvailable = '" + toPreQty + "' WHERE itemName='" + item + "';";

                stmt = conn.createStatement();
                //String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                stmt.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Shiftment deleted Successfully..");
            } else if (from.equals("Store 1") && to.equals("Store 2")) {
                String sql = "UPDATE maininventory SET store1Available = '" + fromPreQty + "',store2Available = '" + toPreQty + "' WHERE itemName='" + item + "';";

                stmt = conn.createStatement();
                //String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                stmt.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Shiftment deleted Successfully..");
            } else if (from.equals("Store 2") && to.equals("Shop")) {
                String sql = "UPDATE maininventory SET store2Available = '" + fromPreQty + "',shopAvailable = '" + toPreQty + "' WHERE itemName='" + item + "';";

                stmt = conn.createStatement();
                //String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                stmt.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Shiftment deleted Successfully..");
            } else if (from.equals("Store 2") && to.equals("Store 1")) {
                String sql = "UPDATE maininventory SET store2Available = '" + fromPreQty + "',store1Available = '" + toPreQty + "' WHERE itemName='" + item + "';";

                stmt = conn.createStatement();
                //String sql = "UPDATE maininventory SET shopAvailable = '" + nowFrom + "',store1Available = '" + nowTo + "' WHERE itemName='" + itm + "';";

                stmt.executeUpdate(sql);

                JOptionPane.showMessageDialog(null, "Shiftment deleted Successfully..");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        tblModel.removeRow(selectedRow);
        tableShift.clearSelection();

        lblShopAvailble.setText("00");
        lblStore1Available.setText("00");
        lblStore2Availble.setText("00");
        lblTotLvel.setText("00");
        lblShopMaxLevel.setText("00");
        lblStore1MaxLevel.setText("00");

    }//GEN-LAST:event_btnItemRemove1MouseClicked

    private void comboComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboComActionPerformed

    private void comboItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboItmActionPerformed
        item();
        String itm = (String) comboItm.getSelectedItem();
        try {

            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemName = '" + itm + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String shop = rs.getString("shopAvailable");
                String store1 = rs.getString("store1Available");
                String store2 = rs.getString("store2Available");
                String totq = rs.getString("totalQty");

                lblShopAvailble.setText(shop);
                lblStore1Available.setText(store1);
                lblStore2Availble.setText(store2);
                lblTotLvel.setText(totq);
            } else {
                lblShopAvailble.setText("00");
                lblStore1Available.setText("00");
                lblStore2Availble.setText("00");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO Any Data Found...");
        }

        try {

            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE itemName = '" + itm + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String shopMax = rs.getString("shopMax");
                String store1Max = rs.getString("storeMax");

                lblShopMaxLevel.setText(shopMax);
                lblStore1MaxLevel.setText(store1Max);

            } else {
                lblShopMaxLevel.setText("00");
                lblStore1MaxLevel.setText("00");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO Any Data Found...");
        }


    }//GEN-LAST:event_comboItmActionPerformed

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased

        String com = (String) comboCom.getSelectedItem();
        String itm = (String) comboItm.getSelectedItem();
        String qty = txtQty.getText();

        if (comboCom.getSelectedItem().equals(null) || comboItm.getSelectedItem().equals(null) || txtQty.getText().equals(null)) {
            JOptionPane.showMessageDialog(null, "Text feeilds are empty");
        } else {
            //JOptionPane.showMessageDialog(null,com+itm+qty);

            try {

                stmt = conn.createStatement();
                String sql = "SELECT * FROM maininventory WHERE itemName = '" + itm + "'";
                rs = stmt.executeQuery(sql);

                if (rs.next()) {

                    String shop = rs.getString("shopAvailable");
                    String store1 = rs.getString("store1Available");
                    String store2 = rs.getString("store2Available");

                    lblShopAvailble.setText(shop);
                    lblStore1Available.setText(store1);
                    lblStore2Availble.setText(store2);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "NO Any Data Found...");
            }

        }


    }//GEN-LAST:event_txtQtyKeyReleased

    private void tableShiftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShiftMouseClicked

        int selectedRow = tableShift.getSelectedRow();
        String itm = (String) tableShift.getValueAt(selectedRow, 1);
        String from = (String) tableShift.getValueAt(selectedRow, 2);
        String to = (String) tableShift.getValueAt(selectedRow, 3);
        if (from.equals("Shop") && to.equals("Store 1")) {
            comboWhere.setSelectedIndex(0);
            comboTo.setSelectedIndex(1);
        } else if (from.equals("Shop") && to.equals("Store 2")) {
            comboWhere.setSelectedIndex(0);
            comboTo.setSelectedIndex(2);
        } else if (from.equals("Store 1") && to.equals("Shop")) {
            comboWhere.setSelectedIndex(1);
            comboTo.setSelectedIndex(0);
        } else if (from.equals("Store 1") && to.equals("Store 2")) {
            comboWhere.setSelectedIndex(1);
            comboTo.setSelectedIndex(2);
        } else if (from.equals("Store 2") && to.equals("Shop")) {
            comboWhere.setSelectedIndex(2);
            comboTo.setSelectedIndex(0);
        } else if (from.equals("Store 2") && to.equals("Store 1")) {
            comboWhere.setSelectedIndex(2);
            comboTo.setSelectedIndex(1);
        }
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemName = '" + itm + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String shop = rs.getString("shopAvailable");
                String store1 = rs.getString("store1Available");
                String store2 = rs.getString("store2Available");
                String totq = rs.getString("totalQty");
                lblShopAvailble.setText(shop);
                lblStore1Available.setText(store1);
                lblStore2Availble.setText(store2);
                lblTotLvel.setText(totq);
            } else {
                lblShopAvailble.setText("00");
                lblStore1Available.setText("00");
                lblStore2Availble.setText("00");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO Any Data Found...");
        }
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE itemName = '" + itm + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String shopMax = rs.getString("shopMax");
                String store1Max = rs.getString("storeMax");
                lblShopMaxLevel.setText(shopMax);
                lblStore1MaxLevel.setText(store1Max);
            } else {
                lblShopMaxLevel.setText("00");
                lblStore1MaxLevel.setText("00");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO Any Data Found...");
        }
    }//GEN-LAST:event_tableShiftMouseClicked

    String cmName = null;
    String cmId = null;
    String itmId = null;
    String itmName = null;
    String ct = null;
    String qty = null;
    String[] orderData;

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        String cmId = lblComID.getText();
        String cmName = (String) comboCmNAme.getSelectedItem();
        String itmId = lblItmCode.getText();
        String itmName = (String) comboItmName.getSelectedItem();
        String ct = (String) comboCat.getSelectedItem();
        String qty = txtOrderQty.getText();

        orderData = new String[]{cmId, cmName, itmId, itmName, ct, qty};

        DefaultTableModel tblmod = (DefaultTableModel) tblViewOrder.getModel();
        tblmod.addRow(orderData);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnOrdrItemRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdrItemRemoveActionPerformed

        int selectedRow = tblViewOrder.getSelectedRow();

        if (selectedRow > 1) {
            JOptionPane.showMessageDialog(null, "Select a single row");
        } else {
            DefaultTableModel tblMod = (DefaultTableModel) tblViewOrder.getModel();
            tblMod.removeRow(selectedRow);

            tblViewOrder.clearSelection();
            btnOrdrItemRemove.setVisible(false);

        }


    }//GEN-LAST:event_btnOrdrItemRemoveActionPerformed

    private void tblViewOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblViewOrderMouseClicked

        btnOrdrItemRemove.setVisible(true);

    }//GEN-LAST:event_tblViewOrderMouseClicked

    private void comboCompanyReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCompanyReturnActionPerformed
        returnCompany();

        try {
            String com = (String) comboCompanyReturn.getSelectedItem();
            stmt = conn.createStatement();
            String sql = "SELECT companyId FROM company WHERE companyName = '" + com + "'";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String ComID = rs.getString("companyId");

                lblComIDReturn.setText(ComID);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant show company id..");
        }
    }//GEN-LAST:event_comboCompanyReturnActionPerformed

    private void comboItemReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboItemReturnActionPerformed
        ReturnItm();

        try {
            String itm = (String) comboItemReturn.getSelectedItem();

            stmt = conn.createStatement();
            String sql = "SELECT * FROM items WHERE itemName = '" + itm + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String itmID = rs.getString("itemId");
                lblItemCodeReturn.setText(itmID);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant show item id..");
        }

        String wh = null;

        try {
            String itm = (String) comboItemReturn.getSelectedItem();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM maininventory WHERE itemName = '" + itm + "' ";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String shopAV = rs.getString("shopAvailable");
                String store1AV = rs.getString("store1Available");
                String store2AV = rs.getString("store2Available");
                jLabel122.setText(shopAV);
                jLabel128.setText(store1AV);
                jLabel129.setText(store2AV);

            } else {
                jLabel122.setText("00");
                jLabel128.setText("00");
                jLabel129.setText("00");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cant show itEM qty..");
        }
    }//GEN-LAST:event_comboItemReturnActionPerformed

    private void txtReturnSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReturnSearchKeyReleased

        try {
            if (txtReturnSearch.getText().isEmpty()) {
                showrecodsReturn();
            } else {
                String item = txtReturnSearch.getText();
                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM returntable ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM returntable WHERE itemName LIKE '%" + item + "%'";   //'" + userName + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tblReturns.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        showrecodsReturn();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any Item..");
        }


    }//GEN-LAST:event_txtReturnSearchKeyReleased

    private void txtReturnSearchInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtReturnSearchInputMethodTextChanged

        try {
            if (txtReturnSearch.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "notext");
                showrecodsReturn();
            } else {
                String item = txtReturnSearch.getText();
                try {

                    stmt = conn.createStatement();
                    //WHERE userName = '" + userName + "';
                    String sql = "SELECT * FROM returntable ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM returntable WHERE itemName = '" + item + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tblReturns.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        JOptionPane.showMessageDialog(null, "recode not found");
                        showrecodsReturn();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any item");
        }

    }//GEN-LAST:event_txtReturnSearchInputMethodTextChanged

    private void txtReturnCompanyInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtReturnCompanyInputMethodTextChanged

        try {
            if (txtReturnCompany.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "notext");
                showrecodsReturn();
            } else {
                String com = txtReturnCompany.getText();
                try {

                    stmt = conn.createStatement();
                    //WHERE userName = '" + userName + "';
                    String sql = "SELECT * FROM returntable ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM returntable WHERE itemName = '" + com + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tblReturns.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        JOptionPane.showMessageDialog(null, "recode not found");
                        showrecodsReturn();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any cmpany");
        }

    }//GEN-LAST:event_txtReturnCompanyInputMethodTextChanged

    private void txtReturnCompanyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReturnCompanyKeyReleased

        try {
            if (txtReturnCompany.getText().isEmpty()) {
                showrecodsReturn();
            } else {
                String com = txtReturnCompany.getText();
                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM returntable ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM returntable WHERE itemName LIKE '%" + com + "%'";   //'" + userName + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tblReturns.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        showrecodsReturn();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any company..");
        }


    }//GEN-LAST:event_txtReturnCompanyKeyReleased

    private void txtreturnSerchCatInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtreturnSerchCatInputMethodTextChanged

        try {
            if (txtreturnSerchCat.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "notext");
                showrecodsReturn();
            } else {
                String cat = txtreturnSerchCat.getText();
                try {

                    stmt = conn.createStatement();
                    //WHERE userName = '" + userName + "';
                    String sql = "SELECT * FROM returntable ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM returntable WHERE catagory = '" + cat + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tblReturns.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        JOptionPane.showMessageDialog(null, "recode not found");
                        showrecodsReturn();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any catagory");
        }

    }//GEN-LAST:event_txtreturnSerchCatInputMethodTextChanged

    private void txtreturnSerchCatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtreturnSerchCatKeyReleased

        try {
            if (txtreturnSerchCat.getText().isEmpty()) {
                showrecodsReturn();
            } else {
                String cat = txtreturnSerchCat.getText();
                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM returntable ";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        stmt = conn.createStatement();
                        String sqlSelect = "SELECT * FROM returntable WHERE catagory LIKE '%" + cat + "%'";   //'" + userName + "';";
                        ResultSet res = stmt.executeQuery(sqlSelect);
                        tblReturns.setModel(DbUtils.resultSetToTableModel(res));
                    } else {
                        showrecodsReturn();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not found any catagory..");
        }

    }//GEN-LAST:event_txtreturnSerchCatKeyReleased

    private void comboUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUnitActionPerformed
        updateUnitCombo();
    }//GEN-LAST:event_comboUnitActionPerformed

    private void comboCatagoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCatagoryMouseEntered
        updateCatagoryCombo();
    }//GEN-LAST:event_comboCatagoryMouseEntered

    private void comboUnitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboUnitMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUnitMouseEntered

    private void jLabel131MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel131MouseClicked
        
        
        String mfggky = ((JTextField) dteMfg.getDateEditor().getUiComponent()).getText();
        String exppky = ((JTextField) dateExp.getDateEditor().getUiComponent()).getText();

        if (comboCompanyName.getSelectedItem() == null || comboItemName.getSelectedItem() == null || comboItemCat.getSelectedItem() == null || txtGrn.getText().equals("") || txtRetailPrice.getText().equals("") || txtCount.getText().equals("") || mfggky.equals("") || exppky.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter proper detaials to Add Items Correctly...");

        } else {

        }

        String mfggk = ((JTextField) dteMfg.getDateEditor().getUiComponent()).getText();
        String exppk = ((JTextField) dateExp.getDateEditor().getUiComponent()).getText();
        if (mfggk.equals("") || exppk.equals("")) {
            //JOptionPane.showMessageDialog(null, "Plese check the MFG Date and EXP Date");
        } else if (lblItemCode.isVisible() == false || comboItemName.getSelectedItem() == null) {
            //JOptionPane.showMessageDialog(null, "Please select a item to genarate Qr");
        } else {

            String DateIDe = null;
            String dtee = null;;
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select MAX(no) from expiredate");
                rs.next();
                rs.getString("MAX(no)");
                if (rs.getString("MAX(no)") == null) {

                    DateIDe = "0000000000000001";
                    System.out.println("date id null null");

                } else {

                    long id = Long.parseLong(rs.getString("MAX(no)").substring(1, rs.getString("MAX(no)").length()));
                    id++;
                    DateIDe = "0" + String.format("%015d", id);
                    System.out.println("id is 00001");

                }
            } catch (SQLException ex) {
                Logger.getLogger(DashBoardForm.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            String itmCode = lblItemCode.getText();
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("MMM d, yyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMddyyyy");
            inputDateFormat.setLenient(false);
            outputDateFormat.setLenient(false);
            String mfgs = ((JTextField) dteMfg.getDateEditor().getUiComponent()).getText();
            String exps = ((JTextField) dateExp.getDateEditor().getUiComponent()).getText();
            try {
                // Parsing input dates
                Date mfgDate = inputDateFormat.parse(mfgs);
                Date expDate = inputDateFormat.parse(exps);
                // Formatting output dates
                String formattedMfg = outputDateFormat.format(mfgDate);
                String formattedExp = outputDateFormat.format(expDate);
                // Concatenating the formatted dates
                dtee = formattedMfg + formattedExp;

                // System.out.println(result); // Output: 0701202307312023
            } catch (Exception e) {
                // Handle parsing exceptions
                e.printStackTrace();
            }
            String qr = itmCode + dtee + DateIDe;

            String fileP = "D:\\MLT Holdings QR\\'" + qr + "'.png";

            File file = new File(fileP);
            if (file.exists()) {

                String company = comboCompanyName.getSelectedItem().toString();
                String grn = txtGrn.getText();
                String cat = comboItemCat.getSelectedItem().toString();
                String itemId = lblItemCode.getText();
                String item = comboItemName.getSelectedItem().toString();
                String unit = lblUnit.getText();
                String retailPrice = txtRetailPrice.getText();
                String count = txtCount.getText();
                String freeIssue = txtFreeIssue.getText();
                String discountValue = txtDiscount.getText();
                //String unitCost = txtUnitCost.getText();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String mfg = ((JTextField) dteMfg.getDateEditor().getUiComponent()).getText();
                String exp = ((JTextField) dateExp.getDateEditor().getUiComponent()).getText();

                String batchNo = txtBatchNo.getText();

                //**************************************************************
                double retPrice = Double.parseDouble(retailPrice);
                int qty = Integer.parseInt(count);
                int freeItems = Integer.parseInt(freeIssue);
                double discount = Double.parseDouble(discountValue);
                //double costPerUnit = Double.parseDouble(unitCost);

                String unitCost;
                double costPerUnit = 0;

                int preQty = 0;
                int totQty = preQty;
                int totalItems = 0;
                int currentTotItems = 0;
                int shopMax = 0;
                int store1Max = 0;
                int shop = 0;
                int store1 = 0;
                int store2 = 0;

                int newTotQty = totalItems;
                int shopNewQty = shop;
                int store1NewQty = store1;
                int store2NewQty = store2;

                double value = 0;
                double netValue = 0;
                double disValue = 0;
                double preCost = 0;
                double totCost = 0;
                double netCost = 0;
                double correctNetCostEach = 0;
                double correctNetCost = 0;
                double currentTotCost = 0;
                double correctCostEach = 0;
                double currentTotValue = 0;

                // calculation part starting**********************************
                String DateID = null;
                try {
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("select MAX(no) from expiredate");
                    rs.next();
                    rs.getString("MAX(no)");
                    if (rs.getString("MAX(no)") == null) {

                        DateID = "0000000000000001";
                        System.out.println("date id null null");

                    } else {

                        long id = Long.parseLong(rs.getString("MAX(no)").substring(1, rs.getString("MAX(no)").length()));
                        id++;
                        DateID = "0" + String.format("%015d", id);
                        System.out.println("id is 00001");

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoardForm.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

//calculating part enough**********************************************************
                Boolean haveData;
                try {

                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM maininventory WHERE itemId = '" + itemId + "'";
                    rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        haveData = true;
                        String totalQty = rs.getString("totalQty");
                        String shopAvailable = rs.getString("shopAvailable");
                        String store1Available = rs.getString("store1Available");
                        String store2Available = rs.getString("store2Available");
                        String Cost = rs.getString("totalCost");

                        preQty = Integer.parseInt(totalQty);
                        shop = Integer.parseInt(shopAvailable);
                        store1 = Integer.parseInt(store1Available);
                        store2 = Integer.parseInt(store2Available);
                        preCost = Double.parseDouble(Cost);

                        JOptionPane.showMessageDialog(null, "Exixting Item Selected");

                    } else {

                        haveData = false;
                        JOptionPane.showMessageDialog(null, "No item found Redy to Insert");

                    }

                    if (haveData == false) {

                        //*************************************Calculation part calling*********************//
                        value = retPrice * qty;  //calculate the gross value of the items

                        //calculate the discount ammount
                        if (rdoPercent.isSelected()) {
                            disValue = value * discount / 100;
                        }
                        if (rdoRupee.isSelected()) {
                            disValue = value - discount;
                        }

                        totCost = value - disValue;  // calculate the gross cost of all

                        totalItems = qty + freeItems;
                        newTotQty = totalItems;
                        netCost = totCost / qty;//calculate the net cost of each
                        correctNetCostEach = totCost / totalItems;

                        txtUnitCost.setText(String.valueOf(correctNetCostEach));

                        correctNetCost = correctNetCostEach * totalItems;  //each cost with free issues

                        currentTotItems = totalItems + preQty;  //calculate all items have in Inventory
                        currentTotCost = correctNetCost + preCost;
                        correctCostEach = currentTotCost / currentTotItems; //calculate correct cost of each
                        currentTotValue = retPrice * currentTotItems;

                        shopMax = Integer.valueOf(lblShopMax.getText());
                        store1Max = Integer.valueOf(lblStore1Max.getText());

                        if (newTotQty > 0) {

                            for (int i = shop; i < shopMax; i++) {
                                if (shopNewQty == shopMax || newTotQty == 0) {
                                    break;
                                }

                                newTotQty = newTotQty - 1;
                                shopNewQty = shopNewQty + 1;

                            }
                        }

                        if (newTotQty > 0) {

                            for (int z = store1; z < store1Max; z++) {

                                if (store1NewQty == store1Max || newTotQty == 0) {
                                    break;
                                }

                                newTotQty = newTotQty - 1;
                                store1NewQty = store1NewQty + 1;

                            }
                        }

                        store2NewQty = newTotQty;

                        lblShopStock.setText(String.valueOf(shopNewQty));
                        lblStore1Stock.setText(String.valueOf(store1NewQty));
                        lblStore2Stock.setText(String.valueOf(store2NewQty));
                        lblTotalStock.setText(String.valueOf(currentTotItems));
                        lblUnitCost.setText(String.valueOf(correctNetCostEach));
                        lblNewUnitCost.setText(String.valueOf(correctCostEach));
                        lblTotalCost.setText(String.valueOf(currentTotCost));
                        lblTotalValue.setText(String.valueOf(currentTotValue));

///***********************calculation calling enough***********************
                        stmt = conn.createStatement();
                        String sqlAdd = "INSERT INTO mainInventory (itemId,grnNo,company,itemName,catagory,unit,freeIssue,discount,lastAddedQty,unitCost,unitRetail,totalCost,totalValue,totalQty,shopAvailable,store1Available,store2Available) VALUES('" + itemId + "','" + grn + "','" + company + "','" + item + "','" + cat + "','" + unit + "','" + freeIssue + "','" + discount + "','" + totalItems + "','" + correctCostEach + "','" + retailPrice + "','" + currentTotCost + "','" + currentTotValue + "','" + currentTotItems + "','" + shopNewQty + "','" + store1NewQty + "','" + store2NewQty + "');";
                        stmt.executeUpdate(sqlAdd);
                        JOptionPane.showMessageDialog(null, "Data inserted Successfully..");
                    }

                    if (haveData == true) {
                        System.out.println("Rady to update exsisting Data");

//**************************************************Starting Calculating************************************
                        value = retPrice * qty;  //calculate the gross value of the items

                        //calculate the discount ammount
                        if (rdoPercent.isSelected()) {
                            disValue = value * discount / 100;
                        }
                        if (rdoRupee.isSelected()) {
                            disValue = value - discount;
                        }

                        totCost = value - disValue;  // calculate the gross cost of all

                        totalItems = qty + freeItems;
                        newTotQty = totalItems;
                        netCost = totCost / qty;//calculate the net cost of each
                        correctNetCostEach = totCost / totalItems;

                        txtUnitCost.setText(String.valueOf(correctNetCostEach));

                        correctNetCost = correctNetCostEach * totalItems;  //each cost with free issues

                        currentTotItems = totalItems + preQty;  //calculate all items have in Inventory
                        currentTotCost = correctNetCost + preCost;
                        correctCostEach = currentTotCost / currentTotItems; //calculate correct cost of each
                        currentTotValue = retPrice * currentTotItems;

                        shopMax = Integer.valueOf(lblShopMax.getText());
                        store1Max = Integer.valueOf(lblStore1Max.getText());

                        shopNewQty = shop;
                        store1NewQty = store1;

                        if (newTotQty > 0) {

                            for (int i = shop; i < shopMax; i++) {
                                if (shopNewQty == shopMax || newTotQty == 0) {
                                    break;
                                }

                                newTotQty = newTotQty - 1;
                                shopNewQty = shopNewQty + 1;

                            }
                        }

                        if (newTotQty > 0) {

                            for (int z = store1; z < store1Max; z++) {

                                if (store1NewQty == store1Max || newTotQty == 0) {
                                    break;
                                }

                                newTotQty = newTotQty - 1;
                                store1NewQty = store1NewQty + 1;

                            }
                        }

                        store2NewQty = store2 + newTotQty;

                        lblShopStock.setText(String.valueOf(shopNewQty));
                        lblStore1Stock.setText(String.valueOf(store1NewQty));
                        lblStore2Stock.setText(String.valueOf(store2NewQty));
                        lblTotalStock.setText(String.valueOf(currentTotItems));
                        lblUnitCost.setText(String.valueOf(correctNetCostEach));
                        lblNewUnitCost.setText(String.valueOf(correctCostEach));
                        lblTotalCost.setText(String.valueOf(currentTotCost));
                        lblTotalValue.setText(String.valueOf(currentTotValue));

                        stmt = conn.createStatement();
                        //String sqlAdd = "INSERT INTO mainInventory (itemId,grnNo,company,itemName,catagory,unit,freeIssue,discount,lastAddedQty,unitCost,unitRetail,totalCost,totalValue,totalQty,shopAvailable,store1Available,store2Available) VALUES('" + itemId + "','" + grn + "','" + company + "','" + item + "','" + cat + "','" + unit + "','" + freeIssue + "','" + discount + "','" + totalItems + "','" + correctCostEach + "','" + retailPrice + "','" + currentTotCost + "','" + currentTotValue + "','" + currentTotItems + "','" + shopNewQty + "','" + store1NewQty + "','" + store2NewQty + "');";
                        String sqlupdate = "UPDATE mainInventory SET grnNo = '" + grn + "',company = '" + company + "',itemName = '" + item + "',catagory = '" + cat + "',unit = '" + unit + "',freeIssue = '" + freeIssue + "',discount = '" + discount + "',lastAddedQty = '" + totalItems + "',unitCost = '" + correctCostEach + "',unitRetail = '" + retailPrice + "',totalCost = '" + currentTotCost + "',totalValue = '" + currentTotValue + "',totalQty = '" + currentTotItems + "',shopAvailable = '" + shopNewQty + "',store1Available = '" + store1NewQty + "',store2Available = '" + store2NewQty + "' WHERE itemId = '" + itemId + "';";
                        stmt.executeUpdate(sqlupdate);
                        JOptionPane.showMessageDialog(null, "Data updated Successfully..");

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }

                try {

                    stmt = conn.createStatement();
                    String sql = "INSERT INTO expiredate (no,grnNo,itemId,mfg,exp,batchNo) VALUES ('" + DateID + "','" + grn + "','" + itemId + "','" + mfg + "','" + exp + "','" + batchNo + "');";

                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Item Inserted Successfully in to expire date table..");

                    //itemId();
                    //showRecodsItem();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);

                }

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
                String dd = sdf.format(d);
                //lblDateToday.setText(dd);

                boolean GRNhave;
                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM GRN WHERE grnNo='" + grn + "'";

                    rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        //GRNhave = true;

                        String grnValue = rs.getString("value");

                        double grnVal = Double.parseDouble(grnValue);

                        double newGRNval = grnVal + totCost;

                        stmt = conn.createStatement();
                        //String sqlAdd = "INSERT INTO mainInventory (itemId,grnNo,company,itemName,catagory,unit,freeIssue,discount,lastAddedQty,unitCost,unitRetail,totalCost,totalValue,totalQty,shopAvailable,store1Available,store2Available) VALUES('" + itemId + "','" + grn + "','" + company + "','" + item + "','" + cat + "','" + unit + "','" + freeIssue + "','" + discount + "','" + totalItems + "','" + correctCostEach + "','" + retailPrice + "','" + currentTotCost + "','" + currentTotValue + "','" + currentTotItems + "','" + shopNewQty + "','" + store1NewQty + "','" + store2NewQty + "');";
                        String sqlupdateGRN = "UPDATE GRN SET value = '" + newGRNval + "' ;";
                        stmt.executeUpdate(sqlupdateGRN);
                        JOptionPane.showMessageDialog(null, "GRN updated Successfully..");

                    } else {

                        try {

                            stmt = conn.createStatement();
                            String sqlADDGRN = "INSERT INTO GRN (grnNo,companyName,value,date) VALUES ('" + grn + "','" + company + "','" + totCost + "','" + dd + "');";

                            stmt.executeUpdate(sqlADDGRN);
                            JOptionPane.showMessageDialog(null, "GRN Inserted Successfully..");

                            //itemId();
                            //showRecodsItem();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, e);
                        }

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }

                showRecodsGRN();
                grn();
                grnValueLoad();
                grnNewValueLoad();
                lblItmCode.setText(null);
                lblItmCode.setVisible(false);

            } else {

                JOptionPane.showMessageDialog(null, "You should create a QR code before Adding items");

            }
        }
        
    }//GEN-LAST:event_jLabel131MouseClicked

    private void btnReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseClicked
        
        String com = (String) comboCompanyReturn.getSelectedItem();
        String cat = (String) comboCatagoryReturn.getSelectedItem();
        String itm = (String) comboItemReturn.getSelectedItem();
        String loc = (String) comboReturnLocation.getSelectedItem();
        String qty = txtReturnItemQty.getText();

        //String reson = txtReturnReson.getText();
        String comId = lblComIDReturn.getText();
        String itmId = lblItemCodeReturn.getText();
        String shopav = jLabel122.getText();
        String store1av = jLabel128.getText();
        String store2av = jLabel129.getText();

        if (shopav.equals("")) {
            shopav = "0";
        } else if (store2av.equals("")) {
            store2av = "0";
        } else if (store1av.equals("")) {
            store1av = "0";
        }

        boolean have;
        int retQty = 0;
        String location = null;
        have = false;

        if (comboReturnLocation.getSelectedIndex() == 0) {

            try {
                stmt = conn.createStatement();
                String sql = "SELECT * FROM returntable WHERE itemId='" + itmId + "' AND location = '" + loc + "'";

                rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    String retrnItmCount = rs.getString("qty");
                    location = rs.getString("location");
                    retQty = Integer.parseInt(retrnItmCount);
                    have = true;
                } else {
                    retQty = 0;
                    have = false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cant get retTable qty" + e);
            }
            int quant = Integer.parseInt(qty);
            int shop = Integer.parseInt(shopav);
            int store1 = Integer.parseInt(store1av);
            int store2 = Integer.parseInt(store2av);
            int shopNewQty = shop;
            int store1NewQty = store1;
            int store2NewQty = store2;
            int returnNewQty = retQty;
            if (quant <= shop && quant > 0 && shop > 0) {
                shopNewQty = shopNewQty - quant;
                returnNewQty = returnNewQty + quant;

                try {
                    stmt = conn.createStatement();
                    String sql = "UPDATE maininventory SET shopAvailable = '" + shopNewQty + "' WHERE itemId ='" + itmId + "'";

                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "cant update main inventory shop level " + e);
                }

                if (have == true) {
                    try {
                        stmt = conn.createStatement();
                        String sql = "UPDATE returntable SET qty = '" + returnNewQty + "', lastAddedQry='" + quant + "' WHERE itemId='" + itmId + "' AND location = '" + loc + "'";

                        stmt.executeUpdate(sql);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "CANT UPDATE RETURN TABLE QTY" + e);
                    }
                } else {
                    try {
                        stmt = conn.createStatement();
                        String sql = "INSERT INTO returntable (companyId, companyName, catagory, itemId, itemName,location ,lastAddedQry, qty) VALUES ('" + comId + "', '" + com + "', '" + cat + "' , '" + itmId + "' , '" + itm + "' ,'" + loc + "' ,'" + quant + "', '" + quant + "')";

                        stmt.executeUpdate(sql);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "CANT INSERT RETURN DATA FOR RETURN TABLE" + e);
                    }
                }

                JOptionPane.showMessageDialog(null, "Item Returned Successfully..");
                JOptionPane.showMessageDialog(null, "shopPre :" + shop + "shop new :" + shopNewQty + "return table pre :" + retQty + "retern tbl new : " + returnNewQty + "returnd qty :" + quant);
            } else {
                JOptionPane.showMessageDialog(null, "Some inserted data is Wrong..");
            }

        } else if (comboReturnLocation.getSelectedIndex() == 1) {

            try {
                stmt = conn.createStatement();
                String sql = "SELECT * FROM returntable WHERE itemId='" + itmId + "' AND location = '" + loc + "'";

                rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    String retrnItmCount = rs.getString("qty");
                    location = rs.getString("location");
                    retQty = Integer.parseInt(retrnItmCount);
                    have = true;
                } else {
                    retQty = 0;
                    have = false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cant get retTable qty" + e);
            }
            int quant = Integer.parseInt(qty);
            int shop = Integer.parseInt(shopav);
            int store1 = Integer.parseInt(store1av);
            int store2 = Integer.parseInt(store2av);
            int shopNewQty = shop;
            int store1NewQty = store1;
            int store2NewQty = store2;
            int returnNewQty = retQty;
            if (quant <= store1 && quant > 0 && store1 > 0) {
                store1NewQty = store1NewQty - quant;
                returnNewQty = returnNewQty + quant;

                try {
                    stmt = conn.createStatement();
                    String sql = "UPDATE maininventory SET store1Available = '" + store1NewQty + "' WHERE itemId ='" + itmId + "'";

                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "cant update main inventory Store1 level " + e);
                }

                if (have == true) {
                    try {
                        stmt = conn.createStatement();
                        String sql = "UPDATE returntable SET qty = '" + returnNewQty + "',lastAddedQry='" + quant + "' WHERE itemId='" + itmId + "' AND location = '" + loc + "'";

                        stmt.executeUpdate(sql);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "CANT UPDATE RETURN TABLE QTY" + e);
                    }
                } else {
                    try {
                        stmt = conn.createStatement();
                        String sql = "INSERT INTO returntable (companyId, companyName, catagory, itemId, itemName,location, lastAddedQry, qty) VALUES ('" + comId + "', '" + com + "', '" + cat + "' , '" + itmId + "' , '" + itm + "' ,'" + loc + "' , '" + quant + "','" + quant + "')";

                        stmt.executeUpdate(sql);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "CANT INSERT RETURN DATA FOR RETURN TABLE" + e);
                    }
                }

                JOptionPane.showMessageDialog(null, "Item Returned Successfully..");
                JOptionPane.showMessageDialog(null, "Store1 pre :" + store1 + "Store 1 new :" + store1NewQty + "return table pre :" + retQty + "retern tbl new : " + returnNewQty + "returned qty is:" + quant);
            } else {
                JOptionPane.showMessageDialog(null, "Some inserted data is Wrong..");
            }

        } else if (comboReturnLocation.getSelectedIndex() == 2) {

            try {
                stmt = conn.createStatement();
                String sql = "SELECT * FROM returntable WHERE itemId='" + itmId + "' AND location = '" + loc + "'";

                rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    String retrnItmCount = rs.getString("qty");
                    location = rs.getString("location");
                    retQty = Integer.parseInt(retrnItmCount);
                    have = true;
                } else {
                    retQty = 0;
                    have = false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cant get retTable qty" + e);
            }
            int quant = Integer.parseInt(qty);
            int shop = Integer.parseInt(shopav);
            int store1 = Integer.parseInt(store1av);
            int store2 = Integer.parseInt(store2av);
            int shopNewQty = shop;
            int store1NewQty = store1;
            int store2NewQty = store2;
            int returnNewQty = retQty;
            if (quant <= store2 && quant > 0 && store2 > 0) {
                store2NewQty = store2NewQty - quant;
                returnNewQty = returnNewQty + quant;

                try {
                    stmt = conn.createStatement();
                    String sql = "UPDATE maininventory SET store2Available = '" + store2NewQty + "' WHERE itemId ='" + itmId + "'";

                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "cant update main inventory shop level " + e);
                }

                if (have == true) {
                    try {
                        stmt = conn.createStatement();
                        String sql = "UPDATE returntable SET qty = '" + returnNewQty + "',lastAddedQry='" + quant + "' WHERE itemId='" + itmId + "' AND location = '" + loc + "'";

                        stmt.executeUpdate(sql);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "CANT UPDATE RETURN TABLE QTY" + e);
                    }
                } else {
                    try {
                        stmt = conn.createStatement();
                        String sql = "INSERT INTO returntable (companyId, companyName, catagory, itemId, itemName,location ,lastAddedQry, qty) VALUES ('" + comId + "', '" + com + "', '" + cat + "' , '" + itmId + "' , '" + itm + "' ,'" + loc + "' , '" + quant + "','" + quant + "')";

                        stmt.executeUpdate(sql);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "CANT INSERT RETURN DATA FOR RETURN TABLE" + e);
                    }
                }

                JOptionPane.showMessageDialog(null, "Item Returned Successfully..");
                JOptionPane.showMessageDialog(null, "store2 pre :" + store2 + "store2 new :" + store2NewQty + "return table pre :" + retQty + "retern tbl new : " + returnNewQty + "returned qty is :" + quant);
            } else {
                JOptionPane.showMessageDialog(null, "Some inserted data is Wrong..");
            }

        }

        txtReturnItemQty.setText("");
        comboCompanyReturn.setSelectedItem(null);
        comboItemReturn.setSelectedItem(null);
        comboCatagoryReturn.setSelectedItem(null);
        showrecodsReturn();

       
    }//GEN-LAST:event_btnReturnMouseClicked

    private void btnReturn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturn1MouseClicked
        
        try {
            tblReturns.print();
        } catch (PrinterException ex) {
            Logger.getLogger(DashBoardForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnReturn1MouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoardForm().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser CalanderDob;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel Inventory;
    private javax.swing.JPanel Management;
    private javax.swing.JPanel Order;
    private javax.swing.JPanel Return;
    public javax.swing.JTabbedPane TabedPanelMain;
    private javax.swing.JLabel btItemAdd;
    private javax.swing.JLabel btItemAdd3;
    private javax.swing.JPanel btnAddGrn;
    private javax.swing.JLabel btnAddUser;
    private javax.swing.JLabel btnAddUser2;
    private javax.swing.JLabel btnBarcode;
    private javax.swing.JLabel btnClear;
    private javax.swing.JLabel btnClear2;
    private javax.swing.JLabel btnClear4;
    private javax.swing.JLabel btnClear5;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnDelete2;
    private javax.swing.JButton btnGRNUpdate;
    private javax.swing.JPanel btnItemReg;
    private javax.swing.JLabel btnItemRemove;
    private javax.swing.JLabel btnItemRemove1;
    private javax.swing.JLabel btnItemUpdate;
    private javax.swing.JLabel btnLblAddGrn;
    private javax.swing.JLabel btnLblItemReg;
    private javax.swing.JLabel btnLblShift;
    private javax.swing.JLabel btnLblViewInventory;
    private javax.swing.JButton btnOrdrItemRemove;
    private javax.swing.JLabel btnRegister;
    private javax.swing.JLabel btnReturn;
    private javax.swing.JLabel btnReturn1;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JLabel btnSearch2;
    private javax.swing.JPanel btnShift;
    private javax.swing.JLabel btnSupReg;
    private javax.swing.JLabel btnSupUpdate;
    private javax.swing.JLabel btnSupUpdateOk;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JPanel btnViewInventory;
    private javax.swing.JComboBox<String> comboCat;
    private javax.swing.JComboBox<String> comboCatagory;
    private javax.swing.JComboBox<String> comboCatagoryReturn;
    private javax.swing.JComboBox<String> comboCmNAme;
    private javax.swing.JComboBox<String> comboCom;
    private javax.swing.JComboBox<String> comboCompany;
    private javax.swing.JComboBox<String> comboCompanyName;
    private javax.swing.JComboBox<String> comboCompanyReturn;
    private javax.swing.JComboBox<String> comboGRN;
    private javax.swing.JComboBox<String> comboItemCat;
    private javax.swing.JComboBox<String> comboItemFilter;
    private javax.swing.JComboBox<String> comboItemName;
    private javax.swing.JComboBox<String> comboItemReturn;
    private javax.swing.JComboBox<String> comboItm;
    private javax.swing.JComboBox<String> comboItmName;
    private javax.swing.JComboBox<String> comboReturnLocation;
    private javax.swing.JComboBox<String> comboSecQuestion;
    private javax.swing.JComboBox<String> comboTo;
    private javax.swing.JComboBox<String> comboUnit;
    private javax.swing.JComboBox<String> comboWhere;
    private com.toedter.calendar.JDateChooser dateExp;
    private com.toedter.calendar.JDateChooser dteMfg;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblAccountType;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBackgroundImage;
    private javax.swing.JLabel lblBackgroundImage1;
    private javax.swing.JLabel lblCat;
    private javax.swing.JLabel lblCatagorySettings;
    private javax.swing.JLabel lblComID;
    private javax.swing.JLabel lblComIDReturn;
    private javax.swing.JLabel lblComName;
    private javax.swing.JLabel lblCompanyCount;
    private javax.swing.JLabel lblCompanyName;
    private javax.swing.JLabel lblContactNo;
    private javax.swing.JLabel lblCurrentUser;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblCustomerCount;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblDob;
    private javax.swing.JLabel lblEmNumber;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblHide3;
    private javax.swing.JLabel lblHide4;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblInventory;
    private javax.swing.JLabel lblInvoice;
    private javax.swing.JLabel lblItemCode;
    private javax.swing.JLabel lblItemCodeReturn;
    private javax.swing.JLabel lblItemCount;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblItmCode;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblNewUnitCost;
    private javax.swing.JLabel lblNic;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblProductId;
    private javax.swing.JLabel lblRepassword;
    private javax.swing.JLabel lblReturn;
    private javax.swing.JLabel lblSecurityQuestion;
    private javax.swing.JLabel lblShopAvailble;
    private javax.swing.JLabel lblShopMax;
    private javax.swing.JLabel lblShopMaxLevel;
    private javax.swing.JLabel lblShopStock;
    private javax.swing.JLabel lblSlectedCom;
    private javax.swing.JLabel lblStore1Available;
    private javax.swing.JLabel lblStore1Max;
    private javax.swing.JLabel lblStore1MaxLevel;
    private javax.swing.JLabel lblStore1Stock;
    private javax.swing.JLabel lblStore2Availble;
    private javax.swing.JLabel lblStore2Stock;
    private javax.swing.JLabel lblSupId;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JLabel lblSupplierId;
    private javax.swing.JLabel lblSupplierImage;
    private javax.swing.JLabel lblTerms;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTodayValue;
    private javax.swing.JLabel lblTodayValue1;
    private javax.swing.JLabel lblTodayValue3;
    private javax.swing.JLabel lblTotLvel;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JLabel lblTotalInventoryCost;
    private javax.swing.JLabel lblTotalInventoryValue;
    private javax.swing.JLabel lblTotalStock;
    private javax.swing.JLabel lblTotalValue;
    private javax.swing.JLabel lblUnit;
    private javax.swing.JLabel lblUnitCost;
    private javax.swing.JLabel lblUnitSettings;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserCount;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblUserIcon1;
    private javax.swing.JLabel lblUserImage;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName1;
    private javax.swing.JLabel lblUserName10;
    private javax.swing.JLabel lblUserName11;
    private javax.swing.JLabel lblUserName12;
    private javax.swing.JLabel lblUserName13;
    private javax.swing.JLabel lblUserName14;
    private javax.swing.JLabel lblUserName2;
    private javax.swing.JLabel lblUserName3;
    private javax.swing.JLabel lblUserName4;
    private javax.swing.JLabel lblUserName5;
    private javax.swing.JLabel lblUserName6;
    private javax.swing.JLabel lblUserName7;
    private javax.swing.JLabel lblUserName8;
    private javax.swing.JLabel lblUserName9;
    private javax.swing.JLabel lblshow3;
    private javax.swing.JLabel lblshow4;
    private javax.swing.JPanel panalAddGRN;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelClear1;
    private javax.swing.JPanel panelClear2;
    private javax.swing.JPanel panelClear5;
    private javax.swing.JPanel panelClear6;
    private javax.swing.JPanel panelCurrentUser;
    private javax.swing.JPanel panelCustomer;
    private javax.swing.JPanel panelCustomerSelector;
    private javax.swing.JPanel panelDelete;
    private javax.swing.JPanel panelDelete2;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelHomeSelector;
    private javax.swing.JPanel panelInventory;
    private javax.swing.JPanel panelInventorySelector;
    private javax.swing.JPanel panelInvoice;
    private javax.swing.JPanel panelInvoiceSelector;
    private javax.swing.JPanel panelLeftNavigation;
    private javax.swing.JPanel panelMidNaviSupplier;
    private javax.swing.JPanel panelMidNaviUser;
    private javax.swing.JPanel panelMidNaviUser2;
    private javax.swing.JPanel panelMiddleNavigationCenter;
    private javax.swing.JPanel panelMiddleNavigationCenter2;
    private javax.swing.JPanel panelMiddleNavigationTop;
    private javax.swing.JPanel panelMiddleNavigationTop1;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JPanel panelOrderSelector;
    private javax.swing.JPanel panelQuestion1;
    private javax.swing.JPanel panelReg;
    private javax.swing.JPanel panelRegister1;
    private javax.swing.JPanel panelRegister2;
    private javax.swing.JPanel panelRegister5;
    private javax.swing.JPanel panelRegister6;
    private javax.swing.JPanel panelRegisterButtons;
    private javax.swing.JPanel panelReturnSelector;
    private javax.swing.JPanel panelReturns;
    private javax.swing.JPanel panelRightNaviSupplier;
    private javax.swing.JPanel panelRightNaviUser;
    private javax.swing.JPanel panelSerachBar;
    private javax.swing.JPanel panelSerachBar1;
    private javax.swing.JPanel panelShift;
    private javax.swing.JPanel panelShowIcon3;
    private javax.swing.JPanel panelShowIcon4;
    private javax.swing.JPanel panelSupButtons;
    private javax.swing.JPanel panelSupRegisterButtons;
    private javax.swing.JPanel panelSupUpdateButtons;
    private javax.swing.JPanel panelSupllierImage;
    private javax.swing.JPanel panelSupllierManagemet;
    private javax.swing.JPanel panelSupplier;
    private javax.swing.JPanel panelSupplierSelector;
    private javax.swing.JScrollPane panelTable;
    private javax.swing.JScrollPane panelTable2;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPanel panelUpdate;
    private javax.swing.JPanel panelUpdate2;
    private javax.swing.JPanel panelUpdateButtons;
    private javax.swing.JLabel panelUpdateOk;
    private javax.swing.JPanel panelUser;
    private javax.swing.JPanel panelUserAdd;
    private javax.swing.JPanel panelUserAdd2;
    private javax.swing.JPanel panelUserIcon;
    private javax.swing.JPanel panelUserIcon1;
    private javax.swing.JPanel panelUserImage1;
    private javax.swing.JPanel panelUserManagement;
    private javax.swing.JPanel panelUserSelector;
    private javax.swing.JPanel panelViewInventory;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JRadioButton rdoPercent;
    private javax.swing.JRadioButton rdoPermitedUser;
    private javax.swing.JRadioButton rdoRupee;
    private javax.swing.JRadioButton rdoSupFemale;
    private javax.swing.JRadioButton rdoSupMale;
    private javax.swing.JRadioButton rdoUser;
    private javax.swing.JScrollPane scollPanelWarmMessage1;
    private javax.swing.JScrollPane scollPanelWarmMessage2;
    private javax.swing.JScrollPane scollPanelWarmMessage5;
    private javax.swing.JScrollPane scrollPanelAdress1;
    private javax.swing.JTabbedPane tabInventory;
    private javax.swing.JTabbedPane tabMiddleNavigation;
    private javax.swing.JTabbedPane tabRightNavigation;
    private javax.swing.JTable tableInventoryItem;
    private javax.swing.JTable tableItem;
    private javax.swing.JTable tableShift;
    private javax.swing.JTable tableSupplier;
    private javax.swing.JTable tableUser;
    private javax.swing.JTable tblPutOrder;
    private javax.swing.JTable tblReturns;
    private javax.swing.JTable tblViewOrder;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtAnswer;
    private javax.swing.JTextField txtBatchNo;
    private javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtContactNo;
    private javax.swing.JTextField txtCount;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtEmNumber;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtFreeIssue;
    private javax.swing.JTextField txtGRNvalue;
    private javax.swing.JTextField txtGrn;
    private javax.swing.JTextField txtItemSearch;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtNic;
    private javax.swing.JTextField txtOrderQty;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtReOrderLevel;
    private javax.swing.JPasswordField txtRePassword;
    private javax.swing.JTextField txtRepContactNo;
    private javax.swing.JTextField txtRepFirstName;
    private javax.swing.JTextField txtRepLastName;
    private javax.swing.JTextField txtRepMail;
    private javax.swing.JTextField txtRepNice;
    private javax.swing.JTextField txtRetailPrice;
    private javax.swing.JTextField txtReturnCompany;
    private javax.swing.JTextField txtReturnItemQty;
    private javax.swing.JTextField txtReturnSearch;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearch2;
    private javax.swing.JTextField txtShopMax;
    private javax.swing.JTextField txtStore1Max;
    private javax.swing.JTextArea txtSupAddress;
    private javax.swing.JTextField txtSupContactNo;
    private javax.swing.JTextField txtSupMail;
    private javax.swing.JTextField txtSupName;
    private javax.swing.JTextField txtUnitCost;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextArea txtWarmMessage;
    private javax.swing.JTextArea txtWarmMessage3;
    private javax.swing.JTextField txtreturnSerchCat;
    // End of variables declaration//GEN-END:variables
}
