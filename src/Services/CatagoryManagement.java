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
public class CatagoryManagement extends javax.swing.JFrame {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;



    public CatagoryManagement() {
        initComponents();
        conn = DatabaseConnection.connection();

        catId();
        updateCatagoryCombo();
        comboCatagoryAutoComplete();
        txtCatagory.setText("");
        comboCatagory.setSelectedItem(null);
        lblSelectedId.setText("");
    }

    public void catId() {

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select MAX(catagoryId) from catagory");
            rs.next();

            rs.getString("MAX(catagoryId)");

            if (rs.getString("MAX(catagoryId)") == null) {

                lblCatgoryId.setText("Cat-0000001");
                System.out.println("catgory null");
            } else {
                long id = Long.parseLong(rs.getString("MAX(catagoryId)").substring(4, rs.getString("MAX(catagoryId)").length()));
                id++;
                lblCatgoryId.setText("Cat-" + String.format("%07d", id));
                System.out.println("id is 00001");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void comboCatagoryAutoComplete() {
        AutoCompleteDecorator.decorate(comboCatagory);
    }

    public void updateCatagoryCombo() {

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

                // Add the item to the combo box only if it doesn't already exist
                if (!exists) {
                    comboCatagory.addItem(catagoryName);
                }
            }
        } catch (Exception e) {
            // Exception handling code
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtCatagory = new javax.swing.JTextField();
        comboCatagory = new javax.swing.JComboBox<>();
        lblCatgoryId = new javax.swing.JLabel();
        btnCatAdd = new javax.swing.JButton();
        btnCatUpdate = new javax.swing.JButton();
        btnCatDel = new javax.swing.JButton();
        lblSelectedId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(23, 35, 51));
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Services/Images/icons8_delete_32px.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Catagory");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(134, 134, 134)
                .addComponent(jLabel2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
            .addComponent(jLabel1)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBackground(new java.awt.Color(23, 35, 51));

        comboCatagory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCatagoryActionPerformed(evt);
            }
        });

        lblCatgoryId.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCatgoryId.setForeground(new java.awt.Color(255, 255, 255));

        btnCatAdd.setText("Add");
        btnCatAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatAddActionPerformed(evt);
            }
        });

        btnCatUpdate.setText("Update");
        btnCatUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatUpdateActionPerformed(evt);
            }
        });

        btnCatDel.setText("Delete");
        btnCatDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatDelActionPerformed(evt);
            }
        });

        lblSelectedId.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSelectedId.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectedId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 47, Short.MAX_VALUE)
                .addComponent(btnCatAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCatUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnCatDel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCatagory, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCatgoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSelectedId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboCatagory, 0, 171, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCatgoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSelectedId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCatagory, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCatagory, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCatAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCatUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCatDel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.hide();
        updateCatagoryCombo();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void btnCatAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatAddActionPerformed

        String catId = lblCatgoryId.getText();
        String cat = txtCatagory.getText();

        try {

            stmt = conn.createStatement();
            String sql = "INSERT INTO catagory (catagoryId,catagoryName) VALUES ('" + catId + "','" + cat + "');";

            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Catagory Inserted Successfully..");
            catId();
            updateCatagoryCombo();

            this.dispose();
            CatagoryManagement ctMnage = new CatagoryManagement();
            ctMnage.setVisible(true);
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }


    }//GEN-LAST:event_btnCatAddActionPerformed

    private void comboCatagoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCatagoryActionPerformed

        updateCatagoryCombo();

        String cat = (String) comboCatagory.getSelectedItem();
        txtCatagory.setText(cat);

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM catagory WHERE catagoryName = '" + cat + "'";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String catagoryId = rs.getString("catagoryId");

                lblSelectedId.setText(catagoryId);
            }

        } catch (Exception e) {
        }

    }//GEN-LAST:event_comboCatagoryActionPerformed

    private void btnCatUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatUpdateActionPerformed

        String catId = lblSelectedId.getText();
        String newCat = txtCatagory.getText();

        try {
            stmt = conn.createStatement();

            //String sql = "UPDATE catagory SET catagoryName = '"+newCat+"' WHERE catagoryName = '"+cat+"';";
            String sql = "UPDATE catagory SET catagoryName = '" + newCat + "' WHERE catagoryId = '" + catId + "'";

            stmt.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Catagory Updated Successfully..");
            updateCatagoryCombo();
            
            this.dispose();
            CatagoryManagement ctMnage = new CatagoryManagement();
            ctMnage.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);

        }


    }//GEN-LAST:event_btnCatUpdateActionPerformed

    private void btnCatDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatDelActionPerformed

        updateCatagoryCombo();

        String ct = lblSelectedId.getText();

        try {
            stmt = conn.createStatement();

            String sql = "DELETE FROM catagory WHERE catagoryId = '" + ct + "';";

            stmt.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Deletion Successfull..");

            lblSelectedId.setText("");
            txtCatagory.setText("");
            comboCatagory.setSelectedItem(null);
            updateCatagoryCombo();
            
            this.dispose();
            CatagoryManagement ctMnage = new CatagoryManagement();
            ctMnage.setVisible(true);

        } catch (Exception e) {
        }


    }//GEN-LAST:event_btnCatDelActionPerformed

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
            java.util.logging.Logger.getLogger(CatagoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatagoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatagoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatagoryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CatagoryManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCatAdd;
    private javax.swing.JButton btnCatDel;
    private javax.swing.JButton btnCatUpdate;
    private javax.swing.JComboBox<String> comboCatagory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCatgoryId;
    private javax.swing.JLabel lblSelectedId;
    private javax.swing.JTextField txtCatagory;
    // End of variables declaration//GEN-END:variables
}
