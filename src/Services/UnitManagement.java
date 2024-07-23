/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author mohom
 */
public class UnitManagement extends javax.swing.JFrame {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    
    public UnitManagement() {
        initComponents();
        conn = DatabaseConnection.connection();
        
        unitId();
        updateUnitCombo();
        comboUnitAutoComplete();
        txtUnit.setText("");
        comboUnit.setSelectedItem(null);
        lblSelectedId.setText("");
    }
    
    public void updateUnitCombo() {

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM unit";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String unitName = rs.getString("unitName");

                // Check if the item already exists in the combo box
                boolean exists = false;
                for (int i = 0; i < comboUnit.getItemCount(); i++) {
                    if (unitName.equals(comboUnit.getItemAt(i))) {
                        exists = true;
                        break;
                    }
                }

                // Add the item to the combo box only if it doesn't already exist
                if (!exists) {
                    comboUnit.addItem(unitName);
                }
            }
        } catch (Exception e) {
            // Exception handling code
        }

    }
    
    

    public void comboUnitAutoComplete() {
        AutoCompleteDecorator.decorate(comboUnit);
    }
    
    
   public void unitId() {

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select MAX(unitId) from unit");
            rs.next();

            rs.getString("MAX(unitId)");

            if (rs.getString("MAX(unitId)") == null) {

                lblUnitId.setText("Unit-0000001");
                System.out.println("unit null");
            } else {
                long id = Long.parseLong(rs.getString("MAX(unitId)").substring(5, rs.getString("MAX(unitId)").length()));
                id++;
                lblUnitId.setText("Unit-" + String.format("%07d", id));
                System.out.println("id is 00001");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        txtUnit = new javax.swing.JTextField();
        comboUnit = new javax.swing.JComboBox<>();
        lblUnitId = new javax.swing.JLabel();
        btnUnitAdd = new javax.swing.JButton();
        btnUnitUpdate = new javax.swing.JButton();
        btnUnitDel = new javax.swing.JButton();
        lblSelectedId = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel3.setBackground(new java.awt.Color(23, 35, 51));

        comboUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUnitActionPerformed(evt);
            }
        });

        lblUnitId.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblUnitId.setForeground(new java.awt.Color(255, 255, 255));

        btnUnitAdd.setText("Add");
        btnUnitAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnitAddActionPerformed(evt);
            }
        });

        btnUnitUpdate.setText("Update");
        btnUnitUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnitUpdateActionPerformed(evt);
            }
        });

        btnUnitDel.setText("Delete");
        btnUnitDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnitDelActionPerformed(evt);
            }
        });

        lblSelectedId.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSelectedId.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel3.setBackground(new java.awt.Color(255, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_delete_32px.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Units");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(158, 158, 158)
                .addComponent(jLabel3))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel3)
            .addComponent(jLabel4)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblUnitId, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(lblSelectedId, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(comboUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUnitAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUnitUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnUnitDel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUnitId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSelectedId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUnitAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnitUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnitDel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUnitActionPerformed

        updateUnitCombo();

        String unit = (String) comboUnit.getSelectedItem();
        txtUnit.setText(unit);

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM unit WHERE unitName = '" + unit + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String unitId = rs.getString("unitId");

                lblSelectedId.setText(unitId);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_comboUnitActionPerformed

    private void btnUnitAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnitAddActionPerformed

        String unitId = lblUnitId.getText();
        String unit = txtUnit.getText();

        try {

            stmt = conn.createStatement();
            String sql = "INSERT INTO unit (unitId,unitName) VALUES ('" + unitId + "','" + unit + "');";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Unit Inserted Successfully..");
            unitId();
            updateUnitCombo();

            this.dispose();
            UnitManagement utMnage = new UnitManagement();
            utMnage.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }//GEN-LAST:event_btnUnitAddActionPerformed

    private void btnUnitUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnitUpdateActionPerformed

        String unitId = lblSelectedId.getText();
        String newUnit = txtUnit.getText();

        try {
            stmt = conn.createStatement();

            //String sql = "UPDATE catagory SET catagoryName = '"+newCat+"' WHERE catagoryName = '"+cat+"';";
            String sql = "UPDATE unit SET unitName = '" + newUnit + "' WHERE unitId = '" + unitId + "'";

            stmt.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Unit Updated Successfully..");
            updateUnitCombo();
            
            this.dispose();
            UnitManagement utMnage = new UnitManagement();
            utMnage.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);

        }

    }//GEN-LAST:event_btnUnitUpdateActionPerformed

    private void btnUnitDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnitDelActionPerformed

        updateUnitCombo();

        String ut = lblSelectedId.getText();

        try {
            stmt = conn.createStatement();

            String sql = "DELETE FROM unit WHERE unitId = '" + ut + "';";

            stmt.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Deletion Successfull..");

            lblSelectedId.setText("");
            txtUnit.setText("");
            comboUnit.setSelectedItem(null);
            updateUnitCombo();
            
            this.dispose();
            UnitManagement utMnage = new UnitManagement();
            utMnage.setVisible(true);

        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnUnitDelActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.hide();
        updateUnitCombo();
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(UnitManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UnitManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UnitManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UnitManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UnitManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUnitAdd;
    private javax.swing.JButton btnUnitDel;
    private javax.swing.JButton btnUnitUpdate;
    private javax.swing.JComboBox<String> comboUnit;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblSelectedId;
    private javax.swing.JLabel lblUnitId;
    private javax.swing.JTextField txtUnit;
    // End of variables declaration//GEN-END:variables
}
