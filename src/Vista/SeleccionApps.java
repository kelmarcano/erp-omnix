package Vista;

import Modelos.VariablesGlobales;
import static clases.ColorApp.colorForm;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeleccionApps extends javax.swing.JFrame {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public SeleccionApps() {
        initComponents();
        
        jPanel1.setBackground(Color.decode(colorForm));
     
        if (VarGlobales.getPos()==false){
            jBtnPOS.setEnabled(false);
        }
        if (VarGlobales.getErp()==false){
            jBtnERP.setEnabled(false);
        }
        if (VarGlobales.getHcm()==false){
            jBtnHCM.setEnabled(false);
        }
        if (VarGlobales.getHcs()==false){
            jBtnHCS.setEnabled(false);
        }
        if (VarGlobales.getEcommers()==false){
            jBtnEcomerce.setEnabled(false);
        }
        if (VarGlobales.getFuerzadVentas()==false){
            jBtnFdV.setEnabled(false);
        }
        if (VarGlobales.getGestoProyect()==false){
            jBtnGestor.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBtnPOS = new javax.swing.JButton();
        jBtnERP = new javax.swing.JButton();
        jBtnHCM = new javax.swing.JButton();
        jBtnHCS = new javax.swing.JButton();
        jBtnFdV = new javax.swing.JButton();
        jBtnEcomerce = new javax.swing.JButton();
        jBtnGestor = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jBtnPOS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnPOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_POS.jpg"))); // NOI18N
        jBtnPOS.setText("POS");
        jBtnPOS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnPOS.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnPOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPOSActionPerformed(evt);
            }
        });

        jBtnERP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnERP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_ERP.jpg"))); // NOI18N
        jBtnERP.setText("ERP");
        jBtnERP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnERP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnERP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnERPActionPerformed(evt);
            }
        });

        jBtnHCM.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnHCM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_HCM.jpg"))); // NOI18N
        jBtnHCM.setText("HCM");
        jBtnHCM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnHCM.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnHCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnHCMActionPerformed(evt);
            }
        });

        jBtnHCS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnHCS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_HCS.jpg"))); // NOI18N
        jBtnHCS.setText("HCS");
        jBtnHCS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnHCS.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnHCS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnHCSActionPerformed(evt);
            }
        });

        jBtnFdV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnFdV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_fuerza_de_ventas.jpg"))); // NOI18N
        jBtnFdV.setText("Fuerza de Ventas");
        jBtnFdV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnFdV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnFdV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnFdVActionPerformed(evt);
            }
        });

        jBtnEcomerce.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnEcomerce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_ecommerce.jpg"))); // NOI18N
        jBtnEcomerce.setText("Ecommerce");
        jBtnEcomerce.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnEcomerce.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnEcomerce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEcomerceActionPerformed(evt);
            }
        });

        jBtnGestor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtnGestor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/App_gestor_de_proyectos.jpg"))); // NOI18N
        jBtnGestor.setText("Gestor de Proyectos");
        jBtnGestor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnGestor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnGestor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGestorActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sistema.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jBtnPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnERP, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnHCM, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnHCS, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnFdV, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEcomerce, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnGestor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jBtnGestor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnERP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnFdV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnHCS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnHCM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnEcomerce, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jBtnPOS))
                .addContainerGap(580, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnPOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPOSActionPerformed
        try {
            Runtime obj = Runtime.getRuntime();  
            obj.exec("C:\\OmnixSolutions\\POS\\OmnixPOS.exe");
        } catch (IOException ex) {
            Logger.getLogger(SeleccionApps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnPOSActionPerformed

    private void jBtnERPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnERPActionPerformed
        dispose();
        new Vista.Login().setVisible(true);
//        try {
//            Runtime obj = Runtime.getRuntime();  
//            obj.exec("C:\\OmnixErp\\OmnixERP.exe");
//        } catch (IOException ex) {
//            Logger.getLogger(SeleccionApps.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jBtnERPActionPerformed

    private void jBtnHCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHCMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnHCMActionPerformed

    private void jBtnHCSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHCSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnHCSActionPerformed

    private void jBtnFdVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnFdVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnFdVActionPerformed

    private void jBtnEcomerceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEcomerceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnEcomerceActionPerformed

    private void jBtnGestorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGestorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnGestorActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeleccionApps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SeleccionApps().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnERP;
    private javax.swing.JButton jBtnEcomerce;
    private javax.swing.JButton jBtnFdV;
    private javax.swing.JButton jBtnGestor;
    private javax.swing.JButton jBtnHCM;
    private javax.swing.JButton jBtnHCS;
    private javax.swing.JButton jBtnPOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
