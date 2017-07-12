package Vista;

import Controlador.Controlador_Carga_Idiomas;
import Controlador.Controlador_Login;
import Modelos.Modelo_Login;
import Pantallas.principal;
import Modelos.VariablesGlobales;
import Modelos.Bitacora;
import clases.CambiarColorJpanel;
import clases.CargaComboBoxs;
import clases.ControldeInicio;
import clases.GetMacIp;
import clases.JchomboBox;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.conexion;
import clases.jComboRenderer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    public static String Idioma="";
    public static JchomboBox jComboIdioma;
    public static int NumEmp = 0;
    public static Vector elementos;
    private Vector Msg, Menu;
    private String FormClass="", IdiomaSelect="";;
    private String colorForm="#FFFFFF", colorText="#0972ba";
    
    Controlador_Login ControlLogin = new Controlador_Login();
    Controlador_Carga_Idiomas ControlIdioma = new Controlador_Carga_Idiomas();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public Login() {
        initComponents();
        
        jPanel1.setBackground(Color.decode(colorForm));
        
        jButton1.setBackground(Color.decode(colorForm)); jButton2.setBackground(Color.decode(colorForm));
        jButton1.setForeground(Color.decode(colorText)); jButton2.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText));
        
        txt_user.setForeground(Color.decode(colorText)); txt_pass.setForeground(Color.decode(colorText));
        
        jCmbIdioma.setVisible(false);
        
        final Properties p = System.getProperties();
        FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
        
        elementos = ControlIdioma.ListaIdioma();
        
        jComboIdioma = new JchomboBox(ControlLogin.BanderasComboIdiomas().length);
        jComboRenderer render = new jComboRenderer(ControlLogin.BanderasComboIdiomas());
        jComboIdioma.setRenderer(render);
        
        comboEmpresa();
        NumEmp = jCmbEmpresa.getItemCount();
        
        if (jCmbEmpresa.getItemCount()<=2){
            jCmbEmpresa.setVisible(false);
            jLabel5.setVisible(false);

//****** Agregando los componenten con un GroupLayout ******
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(60, 60, 60)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_pass, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_user, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboIdioma, javax.swing.GroupLayout.Alignment.LEADING, 0, 157, Short.MAX_VALUE))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addContainerGap())
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(71, 71, 71)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGap(21, 21, 21)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGap(26, 26, 26)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
//**********************************************************
        }else{
            //               x    y    h   w
            jLabel1.reshape(270, 45, 100, 20); jPanel1.add(jLabel1);
            txt_user.reshape(370, 40, 168, 30); jPanel1.add(txt_user);
            jLabel2.reshape(270, 85, 100, 20); jPanel1.add(jLabel2);
            txt_pass.reshape(370, 80, 168, 30); jPanel1.add(txt_pass);
            jLabel4.reshape(270, 125, 100, 20); jPanel1.add(jLabel4);
            jComboIdioma.reshape(370, 120, 168, 30); jPanel1.add(jComboIdioma);
            jLabel5.reshape(270, 165, 100, 20); jPanel1.add(jLabel5);
            jCmbEmpresa.reshape(370, 160, 168, 30); jPanel1.add(jCmbEmpresa);
            
            jButton1.reshape(330, 210, 100, 45); jPanel1.add(jButton1);
            jButton2.reshape(440, 210, 100, 45); jPanel1.add(jButton2);
        }
        
        jComboIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Item = (int) jComboIdioma.getSelectedItem();
                
                IdiomaSelect=(String) elementos.get(Item);
                CargarIdioma(IdiomaSelect);
            }
        });
        
        jComboIdioma.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode()==KeyEvent.VK_ENTER){
                    if (NumEmp>2){
                       jCmbEmpresa.requestFocus();
                    }else{
                        jButton1.requestFocus();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        if (p.get("user.language").equals("es")){
            for (int i=0; i<elementos.size(); i++){
                if (elementos.get(i).equals("Español ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")")){
                    IdiomaSelect=(String) elementos.get(i);
                    //jCmbIdioma.setSelectedItem("Español ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")");
                    jComboIdioma.setSelectedItem(i);
                }
            }
            CargarIdioma(IdiomaSelect);
        }
        if (p.get("user.language").equals("en")){
            for (int i=0; i<elementos.size(); i++){
                if (elementos.get(i).equals("English ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")")){
                    IdiomaSelect=(String) elementos.get(i);
                    //jComboIdioma.setSelectedItem("English ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")");
                    jComboIdioma.setSelectedItem(i);
                }
            }
            CargarIdioma(IdiomaSelect);
        }
    }
    
    public void comboEmpresa() {
        String sql = "SELECT CONCAT(EMP_CODIGO,' - ',EMP_NOMBRE) AS DATO1 FROM dnempresas WHERE EMP_ACTIVO=1";

        DefaultComboBoxModel mdl = ControlLogin.DatosCombo(sql);
        
        this.jCmbEmpresa.setModel(mdl);
        jCmbEmpresa.setSelectedIndex(1);
        
        VarGlobales.setCodEmpresa(jCmbEmpresa.getSelectedItem().toString().substring(0, 6));
        VarGlobales.setNombEmpresa(jCmbEmpresa.getSelectedItem().toString().substring(9, jCmbEmpresa.getSelectedItem().toString().length()));
    }
    
    public void colorpanel(){
        CambiarColorJpanel cp = new CambiarColorJpanel();
        cp.seleccionarColorForm();
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
        jLabel3 = new javax.swing.JLabel();
        txt_user = new javax.swing.JTextField();
        txt_pass = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCmbIdioma = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCmbEmpresa = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LoginInicio.png"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_user.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        txt_user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_userKeyPressed(evt);
            }
        });

        txt_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passActionPerformed(evt);
            }
        });
        txt_pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/entrar.png"))); // NOI18N
        jButton1.setText("Entrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCmbIdioma.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbIdiomaItemStateChanged(evt);
            }
        });
        jCmbIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbIdiomaActionPerformed(evt);
            }
        });
        jCmbIdioma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbIdiomaKeyPressed(evt);
            }
        });

        jLabel1.setText("Usuario:");

        jLabel2.setText("Contraseña:");

        jLabel4.setText("Lenguaje:");

        jCmbEmpresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbEmpresaItemStateChanged(evt);
            }
        });
        jCmbEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbEmpresaActionPerformed(evt);
            }
        });
        jCmbEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbEmpresaKeyPressed(evt);
            }
        });

        jLabel5.setText("Empresa:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_pass, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_user, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCmbIdioma, javax.swing.GroupLayout.Alignment.LEADING, 0, 157, Short.MAX_VALUE)
                            .addComponent(jCmbEmpresa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_userKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            txt_pass.requestFocus();
        }
    }//GEN-LAST:event_txt_userKeyPressed

    private void txt_passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            //acion_login();
            jComboIdioma.requestFocus();
        }
    }//GEN-LAST:event_txt_passKeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            acion_login();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        acion_login();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(0));

        if (salir == 0){
            new ControldeInicio().cerrarApp();  
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passActionPerformed
        
    }//GEN-LAST:event_txt_passActionPerformed

    private void jCmbIdiomaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbIdiomaKeyPressed

    }//GEN-LAST:event_jCmbIdiomaKeyPressed

    private void jCmbIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbIdiomaActionPerformed
        //CargarIdioma();
    }//GEN-LAST:event_jCmbIdiomaActionPerformed

    private void jCmbIdiomaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbIdiomaItemStateChanged
        
    }//GEN-LAST:event_jCmbIdiomaItemStateChanged

    private void jCmbEmpresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbEmpresaItemStateChanged
        
    }//GEN-LAST:event_jCmbEmpresaItemStateChanged

    private void jCmbEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbEmpresaActionPerformed
        
    }//GEN-LAST:event_jCmbEmpresaActionPerformed

    private void jCmbEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbEmpresaKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButton1.requestFocus();
        }        
    }//GEN-LAST:event_jCmbEmpresaKeyPressed

    private void acion_login(){
        boolean login = false;
        
        int Item = (int) jComboIdioma.getSelectedItem();
        Idioma=(String) elementos.get(Item);
        VarGlobales.setCodEmpresa(jCmbEmpresa.getSelectedItem().toString().substring(0, 6));
        VarGlobales.setNombEmpresa(jCmbEmpresa.getSelectedItem().toString().substring(9, jCmbEmpresa.getSelectedItem().toString().length()));
        
        login = ControlLogin.Login(txt_user.getText().toUpperCase(), txt_pass.getText().toUpperCase(), VarGlobales.getCodEmpresa());

        if (login == true){
//**************************** Asignacion de Variables publica ****************************
            String sql = "SELECT OPE_EMPRESA,OPE_NUMERO,OPE_NOMBRE,OPE_USUARIO,OPE_TIPO_DESKTOP FROM dnusuarios " +
                         "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND " +
                         "OPE_USUARIO='"+txt_user.getText().toUpperCase()+"' AND OPE_ACTIVO=1";
            Object element[][] = ControlLogin.VariablesP(sql);

            VarGlobales.setCodEmpresa(element[0][0].toString());
            VarGlobales.setIdUser(element[0][1].toString());
            VarGlobales.setUserName(element[0][3].toString());
            VarGlobales.setGrupPermiso(element[0][4].toString());

            GetMacIp macip = new GetMacIp();
            Object MacIp[][] = macip.GetMacIp();
               
            VarGlobales.setMacPc(MacIp[0][0].toString());
            VarGlobales.setIpPc(MacIp[0][1].toString());
//*****************************************************************************************

            JOptionPane.showMessageDialog(null, (String) Msg.get(1));

            principal prin = new principal();
            prin.show();
            prin.setExtendedState(principal.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
            prin.setVisible(true);
            
            //Bitacora insertar = new Bitacora(Mac_Pc, ID_User, User_Nombre, "inicio sesión", "Usuario "+User_Nombre.trim()+" ha iniciado sesión");
            Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), VarGlobales.getIdUser(),
                                             VarGlobales.getUserName(), "inicio sesión", 
                                             "Usuario "+VarGlobales.getUserName().trim()+" ha iniciado sesión");
            txt_user.setText(""); txt_pass.setText("");

            dispose();
        }else{
            txt_user.setText(""); txt_pass.setText("");
        }
    }

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    private void CargarIdioma(String idioma){
        try{
            Vector elementos = ControlIdioma.FormIdioma("form_"+FormClass, "Label", idioma.trim());
        
            jLabel1.setText((String) elementos.get(0));
            jLabel2.setText((String) elementos.get(1));
            jLabel4.setText((String) elementos.get(2));
            jLabel5.setText((String) elementos.get(3));
            jButton1.setText((String) elementos.get(4));
            jButton2.setText((String) elementos.get(5));
            
            Msg = ControlIdioma.MsgIdioma("form_"+FormClass, "Msg", idioma.trim());
        
            txt_user.requestFocus();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, (String) Msg.get(2),(String) Msg.get(3),JOptionPane.INFORMATION_MESSAGE);
            
            Properties p = System.getProperties();
            if (p.get("user.language").equals("es")){
                for (int i=0; i<elementos.size(); i++){
                    if (elementos.get(i).equals("Español ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")")){
                        IdiomaSelect=(String) elementos.get(i);
                        jComboIdioma.setSelectedItem(i);
                    }
                }
                CargarIdioma(IdiomaSelect);
            }
            if (p.get("user.language").equals("en")){
                for (int i=0; i<elementos.size(); i++){
                    if (elementos.get(i).equals("English ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")")){
                        IdiomaSelect=(String) elementos.get(i);
                        jComboIdioma.setSelectedItem(i);
                    }
                }
                CargarIdioma(IdiomaSelect);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public static javax.swing.JComboBox jCmbEmpresa;
    public static javax.swing.JComboBox jCmbIdioma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_pass;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}