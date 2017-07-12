package Vista;

import Controlador.Controlador_Carga_Idiomas;
import Controlador.Controlador_Conf_Conexion;
import Modelos.VariablesGlobales;
import Pantallas.Documentos;
import static Vista.Login.Idioma;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.Encrip_Desencrip;
import clases.GetMacIp;
import static clases.Main.idioma;
import clases.ReadFileXml;
import clases.SQLQuerys;
import clases.conexion;
import static clases.conexion.lBdExist;
import static clases.conexion.lCreaBd;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ConfigurarConexion extends javax.swing.JFrame {
    public ResultSet rs;
    public static int Tab=0;
    public static String Clave="", clave="";
    public static boolean lPassPostGre;
    private conexion conet = new conexion();
    private File carpeta, archivo, archivo2;
    private boolean lConex=false;
    private Vector Msg, elementos;
    
    Controlador_Carga_Idiomas ControlIdioma = new Controlador_Carga_Idiomas();
    Controlador_Conf_Conexion ControlConex = new Controlador_Conf_Conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public ConfigurarConexion() {
        initComponents();

        estiloform();
        cargaidioma();
        TabConexBd.requestFocus();
        txt_ipconfig.requestFocus();

        TabConexBd.setIconAt(0, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_mysql.png"));
        TabConexBd.setIconAt(1, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_postgresql.png"));
        
        setLocationRelativeTo(null);
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
        //this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_omnix.png").getImage());
    }
    
    public ConfigurarConexion(boolean Conexion) {
        initComponents();
        
        estiloform();
        cargaidioma();
        
        lConex=Conexion;
        TabConexBd.requestFocus();

        TabConexBd.setIconAt(0, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_mysql.png"));
        TabConexBd.setIconAt(1, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_postgresql.png"));
        
        if (Conexion==true){
            cargartxt();
        }
        
        setLocationRelativeTo(null);
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
    }
    
    public void cargartxt() {
        String sql = ("SELECT * FROM DNCONEXION");
            
        try {
            rs = conet.consultar(sql);
            try {
                if (VarGlobales.getManejador().equals("mySQL")){
                    TabConexBd.setSelectedIndex(0);
                    TabConexBd.setEnabledAt(1, false);
                    
                    VarGlobales.setIpServer(rs.getString("CONF_IP").trim()); txt_ipconfig.setText(VarGlobales.getIpServer());
                    VarGlobales.setUserServer(rs.getString("CONF_USER").trim()); txt_confuser.setText(VarGlobales.getUserServer());
                    VarGlobales.setPasswServer(rs.getString("CONF_PASS").trim()); txt_confpass.setText(VarGlobales.getPasswServer());
                    VarGlobales.setBaseDatos(rs.getString("CONF_BD").trim()); txt_confbd.setText(VarGlobales.getBaseDatos());
                }else if (VarGlobales.getManejador().equals("PostGreSQL")){
                    TabConexBd.setSelectedIndex(1);
                    TabConexBd.setEnabledAt(0, false);
                    
                    VarGlobales.setIpServer(rs.getString("CONF_IP").trim()); txt_ipconfig1.setText(VarGlobales.getIpServer());
                    VarGlobales.setUserServer(rs.getString("CONF_USER").trim()); txt_confuser1.setText(VarGlobales.getUserServer());
                    VarGlobales.setPasswServer(rs.getString("CONF_PASS").trim()); txt_confpass1.setText(VarGlobales.getPasswServer());
                    VarGlobales.setBaseDatos(rs.getString("CONF_BD").trim()); txt_confbd1.setText(VarGlobales.getBaseDatos());
                }
            } catch (SQLException ex) {                    
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void estiloform(){
        jPanel1.setBackground(Color.decode(colorForm)); jPanel3.setBackground(Color.decode(colorForm)); 
        jPanel4.setBackground(Color.decode(colorForm));
        
        bt_save.setBackground(Color.decode(colorForm)); bt_save1.setBackground(Color.decode(colorForm));
        
        bt_save.setForeground(Color.decode(colorText)); bt_save1.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText)); 
        jLabel5.setForeground(Color.decode(colorText)); jLabel6.setForeground(Color.decode(colorText));
        jLabel7.setForeground(Color.decode(colorText)); jLabel8.setForeground(Color.decode(colorText)); 
        
        txt_confbd.setForeground(Color.decode(colorText)); txt_confbd1.setForeground(Color.decode(colorText));
        txt_confpass.setForeground(Color.decode(colorText)); txt_confpass1.setForeground(Color.decode(colorText));
        txt_confuser.setForeground(Color.decode(colorText)); txt_confuser1.setForeground(Color.decode(colorText));
        txt_ipconfig.setForeground(Color.decode(colorText)); txt_ipconfig1.setForeground(Color.decode(colorText));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TabConexBd = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_confpass = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_confpass1 = new javax.swing.JPasswordField();
        bt_save = new javax.swing.JButton();
        bt_save1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        TabConexBd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TabConexBd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TabConexBdStateChanged(evt);
            }
        });

        jLabel1.setText("Localhost/IP-SERVIDOR+Puerto");

        txt_ipconfig.setToolTipText("127.0.0.1:3306 o el puerto que usted selecciono");
        txt_ipconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ipconfigKeyPressed(evt);
            }
        });

        jLabel2.setText("Usuario");

        txt_confuser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confuserKeyPressed(evt);
            }
        });

        jLabel3.setText("Contraseña");

        jLabel4.setText("Base de Datos");

        txt_confbd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confbdKeyPressed(evt);
            }
        });

        txt_confpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confpassKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(txt_ipconfig, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(txt_confuser, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txt_confbd, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(txt_confpass))
                .addGap(45, 45, 45))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(txt_ipconfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txt_confuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_confpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(txt_confbd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        TabConexBd.addTab("Conexión MySQL ", jPanel3);

        txt_confbd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confbd1KeyPressed(evt);
            }
        });

        jLabel8.setText("Base de Datos");

        jLabel7.setText("Contraseña");

        txt_confuser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confuser1KeyPressed(evt);
            }
        });

        jLabel6.setText("Usuario");

        txt_ipconfig1.setToolTipText("127.0.0.1:3306 o el puerto que usted selecciono");
        txt_ipconfig1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ipconfig1KeyPressed(evt);
            }
        });

        jLabel5.setText("Localhost/IP-SERVIDOR+Puerto");

        txt_confpass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confpass1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_confpass1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_ipconfig1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_confuser1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_confbd1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(txt_ipconfig1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addGap(6, 6, 6)
                .addComponent(txt_confuser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_confpass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addGap(6, 6, 6)
                .addComponent(txt_confbd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        TabConexBd.addTab("Conexión PostGreSQL ", jPanel4);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setText("Grabar");
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });
        bt_save.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_saveKeyPressed(evt);
            }
        });

        bt_save1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_save1.setText("Cancelar");
        bt_save1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_save1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TabConexBd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_save1)
                    .addComponent(bt_save))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(TabConexBd, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(58, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        action_bt_save(false);
    }//GEN-LAST:event_bt_saveActionPerformed

    private void bt_save1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save1ActionPerformed
        dispose();
    }//GEN-LAST:event_bt_save1ActionPerformed

    private void TabConexBdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TabConexBdStateChanged
        Tab = TabConexBd.getSelectedIndex();
    }//GEN-LAST:event_TabConexBdStateChanged

    private void txt_ipconfigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ipconfigKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confuser.requestFocus();
        }
    }//GEN-LAST:event_txt_ipconfigKeyPressed

    private void txt_confuserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confuserKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confpass.requestFocus();
        }
    }//GEN-LAST:event_txt_confuserKeyPressed

    private void txt_confbdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confbdKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_txt_confbdKeyPressed

    private void txt_confbd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confbd1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_txt_confbd1KeyPressed

    private void txt_confuser1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confuser1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confpass1.requestFocus();
        }
    }//GEN-LAST:event_txt_confuser1KeyPressed

    private void txt_ipconfig1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ipconfig1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confuser1.requestFocus();
        }
    }//GEN-LAST:event_txt_ipconfig1KeyPressed

    private void bt_saveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_saveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_bt_save(false);
        }
    }//GEN-LAST:event_bt_saveKeyPressed

    private void txt_confpassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confpassKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confbd.requestFocus();
        }
    }//GEN-LAST:event_txt_confpassKeyPressed

    private void txt_confpass1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confpass1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confbd1.requestFocus();
        }
    }//GEN-LAST:event_txt_confpass1KeyPressed

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
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigurarConexion().setVisible(true);
            }
        });
    }
    
    public void action_bt_save(boolean lPass){
        //Se le asignan a los text una variable publica
        if (Tab==0){
            if (txt_ipconfig.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, (String )Msg.get(0));
                txt_ipconfig.requestFocus();
                return;
            }

            if (txt_confuser.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, (String )Msg.get(1));
                txt_confuser.requestFocus();
                return;
            }

            if (txt_confbd.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, (String )Msg.get(2));
                txt_confbd.requestFocus();
                return;
            }
        }else if (Tab==1){
            if (txt_ipconfig1.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, (String )Msg.get(0));
                txt_ipconfig1.requestFocus();
                return;
            }

            if (txt_confuser1.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, (String )Msg.get(3));
                txt_confuser1.requestFocus();
                return;
            }

            if (txt_confbd1.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, (String )Msg.get(2));
                txt_confbd1.requestFocus();
                return;
            }
        }

        GetMacIp macip = new GetMacIp();
        Object MacIp[][] = macip.GetMacIp();

        VarGlobales.setMacPc(MacIp[0][0].toString());
        VarGlobales.setIpPc(MacIp[0][1].toString());
        
        if (Tab==0){
            VarGlobales.setIpServer(txt_ipconfig.getText());
            VarGlobales.setUserServer(txt_confuser.getText());
            VarGlobales.setPasswServer(txt_confpass.getText());
            VarGlobales.setBaseDatos(txt_confbd.getText());
            VarGlobales.setManejador("MySQL");
            
            ControlConex.setDatosConex(VarGlobales.getIpServer(), VarGlobales.getUserServer(), VarGlobales.getPasswServer(), VarGlobales.getBaseDatos(), "MySQL", VarGlobales.getMacPc(), VarGlobales.getIpPc());
        }else if (Tab==1){
            VarGlobales.setIpServer(txt_ipconfig1.getText());
            VarGlobales.setUserServer(txt_confuser1.getText());
            VarGlobales.setPasswServer(txt_confpass1.getText());
            VarGlobales.setBaseDatos(txt_confbd1.getText());
            VarGlobales.setManejador("PostGreSQL");
            
            ControlConex.setDatosConex(VarGlobales.getIpServer(), VarGlobales.getUserServer(), VarGlobales.getPasswServer(), VarGlobales.getBaseDatos(), "PostGreSQL", VarGlobales.getMacPc(), VarGlobales.getIpPc());
        }
        
        if (lConex==true){
            int Reg_count = ControlConex.ExisteConex();

            if (Reg_count>0){
                JOptionPane.showMessageDialog(null, (String )Msg.get(4));
                return;
            }
        }

        if (lPass==true){
            VarGlobales.setPasswServer(clave);
        }

        lPassPostGre=true;
        int Reg_count = ControlConex.ExisteConexLocal(VarGlobales.getMacPc());

        if (Reg_count==0){
            lPassPostGre=false;
            
            ControlConex.GuardaConexion();
        }

        if (lBdExist==false){
            if (lCreaBd==true){
                dispose();
            }
            return;
        }

        carpeta = new File(System.getProperty("user.dir")+"\\"+"Configuracion");
        carpeta.mkdirs();
        archivo = new File(carpeta.getAbsolutePath()+"\\"+"conf.txt");
        archivo2 = new File(carpeta.getAbsolutePath()+"\\"+"conf_conexion.txt");

        try {
            if (archivo.createNewFile()){
                System.out.println("Archivo creado");

                //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
                FileWriter Arc = new FileWriter(archivo,true);
                BufferedWriter escribir = new BufferedWriter(Arc);

                //Escribimos en el archivo con el metodo write
                escribir.write(VarGlobales.getIpServer()); escribir.newLine();
                escribir.write(VarGlobales.getUserServer()); escribir.newLine();
                escribir.write(VarGlobales.getPasswServer()); escribir.newLine();
                escribir.write(VarGlobales.getBaseDatos()); escribir.newLine();
                escribir.write(VarGlobales.getManejador());

                //Cerramos la conexion
                escribir.close();
            }

            try {
                Encrip_Desencrip.encrypt(archivo, archivo2, 12345);
            } catch (IOException ioe) {
                String err = "Error De Lectura\n" +
                             "Probablemente el disco esta lleno o protegido contra escritura";
                JOptionPane.showMessageDialog(null, err, (String )Msg.get(5), JOptionPane.ERROR_MESSAGE);
            }

            archivo.delete();
        } catch (IOException ex) {
            Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        dispose();
        if (lConex==false){
            new Vista.Login().setVisible(true);
        }        
    }
    
    private void cargaidioma(){
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = null, xmlFileMsg = null;
        
        if (!idioma.isEmpty()){
            xmlFile = new File(carpeta.getAbsolutePath()+"\\"+idioma+".xml");
            xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+idioma+"_Msg.xml");
        }else if (!Idioma.isEmpty()){
            xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");
            xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        }

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());

        elementos = ControlIdioma.FormIdioma("form_"+FormClass, "Label", idioma);
        
        TabConexBd.setTitleAt(0, (String) elementos.get(0));
        TabConexBd.setTitleAt(1, (String) elementos.get(1));
        
        jLabel1.setText((String) elementos.get(2));
        jLabel2.setText((String) elementos.get(3));
        jLabel3.setText((String) elementos.get(4));
        jLabel4.setText((String) elementos.get(5));
        jLabel5.setText((String) elementos.get(2));
        jLabel6.setText((String) elementos.get(3));
        jLabel7.setText((String) elementos.get(4));
        jLabel8.setText((String) elementos.get(5));

        bt_save.setText((String) elementos.get(6));
        bt_save1.setText((String) elementos.get(7));
        
        Msg = ControlIdioma.MsgIdioma("form_"+FormClass, "Msg", idioma);
//-----------------------------------------------------------------------------------------------------------------------
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabConexBd;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_save1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public static final javax.swing.JTextField txt_confbd = new javax.swing.JTextField();
    public static final javax.swing.JTextField txt_confbd1 = new javax.swing.JTextField();
    private javax.swing.JPasswordField txt_confpass;
    private javax.swing.JPasswordField txt_confpass1;
    public static final javax.swing.JTextField txt_confuser = new javax.swing.JTextField();
    public static final javax.swing.JTextField txt_confuser1 = new javax.swing.JTextField();
    public static final javax.swing.JTextField txt_ipconfig = new javax.swing.JTextField();
    public static final javax.swing.JTextField txt_ipconfig1 = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}