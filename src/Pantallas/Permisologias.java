package Pantallas;

import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.CargaComboBoxs;
import clases.CargaTablas;
import clases.Celda_CheckBox;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import clases.Render_CheckBox;
import clases.SQLQuerys;
import clases.SQLSelect;
import clases.conexion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class Permisologias extends javax.swing.JInternalFrame {
    //public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus, rs_hijos;
    private int Reg_count, SelectCombo=0;
    private String codigo="", tipomenu="",sql_tabla="";
    private DefaultTableModel modelo;
    private Vector Msg, elementos, header_table;
    
    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public Permisologias() {
        initComponents();
        combotipusuario();

        jPanel1.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText));
        
        bt_salir.setBackground(Color.decode(colorForm)); bt_salir.setForeground(Color.decode(colorText));
        bt_salir.setHorizontalAlignment(2);
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);
            
        jLabel1.setText((String) elementos.get(0));
        jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2));
        jLabel4.setText((String) elementos.get(3));
        jLabel5.setText((String) elementos.get(4));
        
        bt_salir.setText((String) elementos.get(5));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        String[] columnas = {(String) header_table.get(0), (String) header_table.get(1), (String) header_table.get(2)};
        DefaultTableModel dtm = new DefaultTableModel(null,columnas);
        Tabla.setModel(dtm);

        this.setTitle("Permisologias");
        OcultaCombos();
    }
    
    private void OcultaCombos(){
        jLabel2.setVisible(false); jCboMen_Raiz.setVisible(false);
        jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
        jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
        jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
    }
    
    public void combotipusuario(){
        String sql = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ACTIVO=1 AND PER_TIPOMENU='2'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCboMis_Permisos.setModel(mdl); 
    }

   private void combomenuraiz(){  
       jLabel2.setVisible(true); jCboMen_Raiz.setVisible(true);
       
       String sql = "SELECT CONCAT(MEN_ID,' - ', MEN_NOMBRE) AS DATO1 FROM DNPERMISOLOGIA " +
                    "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' " +
                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND " +
                    "MIS_TIPOMENU=2 AND SUB_MEN_ID=0 " +
                    "ORDER BY MIS_PERMISO,MIS_MENU";
       DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
       this.jCboMen_Raiz.setModel(mdl); 
   }

   private void combosubmenuraiz(){ 
       //jLabel8.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
       String ID_Sub_Menu = buscasubmenus(jCboMis_Permisos.getSelectedItem().toString().substring(0, 4), jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim());

       if (ID_Sub_Menu.length()>0){
           jLabel3.setVisible(true); jCboSub_Men_Raiz.setVisible(true);
           
           String sql = "SELECT CONCAT(MEN_ID,' - ', MEN_NOMBRE) AS DATO1 FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' " +
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND " +
                        "MIS_TIPOMENU=2 AND MEN_ID IN("+ID_Sub_Menu.substring(0, ID_Sub_Menu.length()-1)+") "+
                        "ORDER BY MIS_PERMISO,MIS_MENU";
           DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
           this.jCboSub_Men_Raiz.setModel(mdl); 
       }else{
           //jCboSub_Men_Raiz.removeAllItems();
           jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
           jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
           jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
       }
   }
   
   private void combosubmenuhijos(){      
       String ID_Sub_Menu = buscasubmenus(jCboMis_Permisos.getSelectedItem().toString().substring(0, 4), jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim());

       if (ID_Sub_Menu.length()>0){
           jLabel4.setVisible(true); jCboSub_Men_Hijos.setVisible(true);
           
           String sql = "SELECT CONCAT(MEN_ID,' - ', MEN_NOMBRE) AS DATO1 FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' " +
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND " +
                        "MIS_TIPOMENU=2 AND MEN_ID IN("+ID_Sub_Menu.substring(0, ID_Sub_Menu.length()-1)+") "+
                        "ORDER BY MIS_PERMISO,MIS_MENU";
           DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
           this.jCboSub_Men_Hijos.setModel(mdl);
       }else{
           if (jCboSub_Men_Hijos.getItemCount()>0){
               //jCboSub_Men_Hijos.removeAllItems();
               jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
               jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
           }
       }
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
        jLabel1 = new javax.swing.JLabel();
        jCboMis_Permisos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jCboMen_Raiz = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jCboSub_Men_Raiz = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jCboSub_Men_Hijos = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jCboSub_Men_Nietos = new javax.swing.JComboBox();
        bt_salir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();

        jLabel1.setText("Grupo de Permisologias de Usuario");

        jCboMis_Permisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboMis_PermisosActionPerformed(evt);
            }
        });
        jCboMis_Permisos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboMis_PermisosKeyPressed(evt);
            }
        });

        jLabel2.setText("Menus Raiz Padre");

        jCboMen_Raiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboMen_RaizActionPerformed(evt);
            }
        });
        jCboMen_Raiz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboMen_RaizKeyPressed(evt);
            }
        });

        jLabel3.setText("Sub-Menus Padre");

        jCboSub_Men_Raiz.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboSub_Men_RaizItemStateChanged(evt);
            }
        });
        jCboSub_Men_Raiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboSub_Men_RaizActionPerformed(evt);
            }
        });
        jCboSub_Men_Raiz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboSub_Men_RaizKeyPressed(evt);
            }
        });

        jLabel4.setText("Sub-Menus Hijos");

        jCboSub_Men_Hijos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboSub_Men_HijosActionPerformed(evt);
            }
        });
        jCboSub_Men_Hijos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboSub_Men_HijosKeyPressed(evt);
            }
        });

        jLabel5.setText("Sub-Menus Nietos");

        jCboSub_Men_Nietos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboSub_Men_NietosActionPerformed(evt);
            }
        });
        jCboSub_Men_Nietos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboSub_Men_NietosKeyPressed(evt);
            }
        });

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Menu", "Nombre", "Selección"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaMouseClicked(evt);
            }
        });
        Tabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(237, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jCboMen_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jCboSub_Men_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jCboSub_Men_Hijos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jCboSub_Men_Nietos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jCboMis_Permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(461, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addGap(6, 6, 6)
                    .addComponent(jCboMis_Permisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel2)
                    .addGap(6, 6, 6)
                    .addComponent(jCboMen_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel3)
                    .addGap(6, 6, 6)
                    .addComponent(jCboSub_Men_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel4)
                    .addGap(6, 6, 6)
                    .addComponent(jCboSub_Men_Hijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel5)
                    .addGap(6, 6, 6)
                    .addComponent(jCboSub_Men_Nietos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                    .addComponent(bt_salir)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            action_tablas(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            action_tablas(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_tablas(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        action_tablas(Tabla.getSelectedRow());

        TableCellEditor celltable = Tabla.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla != null)
        {
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void jCboMis_PermisosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboMis_PermisosKeyPressed

    }//GEN-LAST:event_jCboMis_PermisosKeyPressed

    private void jCboMis_PermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboMis_PermisosActionPerformed
        if (!jCboMis_Permisos.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 ORDER BY MIS_PERMISO,MIS_MENU;";
                    
            CargaTablaPermisos(sql_tabla, 1);
        
            combomenuraiz();
        }else{
            int rows_cli = Tabla.getRowCount();

            for(int i=0;i<rows_cli;i++){
                modelo.removeRow(0);
            }
        
            OcultaCombos();
        }
    }//GEN-LAST:event_jCboMis_PermisosActionPerformed

    private void jCboMen_RaizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboMen_RaizActionPerformed
        if (!jCboMen_Raiz.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
        
            CargaTablaPermisos(sql_tabla, 2);
        
            combosubmenuraiz();
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 ORDER BY MIS_PERMISO,MIS_MENU;";
                    
            CargaTablaPermisos(sql_tabla, 1);
            
            jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
            jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboMen_RaizActionPerformed

    private void jCboMen_RaizKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboMen_RaizKeyPressed

    }//GEN-LAST:event_jCboMen_RaizKeyPressed

    private void jCboSub_Men_RaizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboSub_Men_RaizActionPerformed
        if (!jCboSub_Men_Raiz.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 3);

            combosubmenuhijos();
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'"+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
        
            CargaTablaPermisos(sql_tabla, 2);
            
            jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboSub_Men_RaizActionPerformed

    private void jCboSub_Men_RaizKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboSub_Men_RaizKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCboSub_Men_RaizKeyPressed

    private void jCboSub_Men_HijosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboSub_Men_HijosActionPerformed
        if(!jCboSub_Men_Hijos.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Hijos.getSelectedItem().toString().substring(0, jCboSub_Men_Hijos.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 4);
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 3);
            
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboSub_Men_HijosActionPerformed

    private void jCboSub_Men_HijosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboSub_Men_HijosKeyPressed
        
    }//GEN-LAST:event_jCboSub_Men_HijosKeyPressed

    private void jCboSub_Men_NietosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboSub_Men_NietosActionPerformed
        
    }//GEN-LAST:event_jCboSub_Men_NietosActionPerformed

    private void jCboSub_Men_NietosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboSub_Men_NietosKeyPressed
        
    }//GEN-LAST:event_jCboSub_Men_NietosKeyPressed

    private void jCboSub_Men_RaizItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboSub_Men_RaizItemStateChanged

    }//GEN-LAST:event_jCboSub_Men_RaizItemStateChanged

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        this.dispose();  //Codigo para Salir o Cerrar un Formulario
    }//GEN-LAST:event_bt_salirActionPerformed

    private void action_tablas(int Row){
        codigo = (String) Tabla.getValueAt(Row,0).toString().trim();
        boolean select = (boolean) Tabla.getValueAt(Row,2);
        
        String SQL = "SELECT * FROM DNPERMISOLOGIA WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_MENU='"+codigo+"'";
        try {
            rs = conet.consultar(SQL);
            
            SQLQuerys modificar = new SQLQuerys();
            if (rs.getBoolean("MIS_ACTIVO")==false){
                modificar.SqlUpdate("UPDATE DNPERMISOLOGIA SET MIS_ACTIVO='1' WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+
                                    " AND MIS_MENU='"+codigo+"'");
            }else if (rs.getBoolean("MIS_ACTIVO")==true){
                modificar.SqlUpdate("UPDATE DNPERMISOLOGIA SET MIS_ACTIVO='0' WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+
                                    " AND MIS_MENU='"+codigo+"'");
            }
            
            CargaTablaPermisos(sql_tabla, 0);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        }

//        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPERMISO_GRUPAL WHERE PER_ID='"+codigo+"'";
//        Reg_count = conet.Count_Reg(SQLReg);

//        try {
//            mostrar();
//        } catch (SQLException ex) {
//            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        this.Bandera=true;
    }
    
    private void ActualizaJtable(int item){
        action_tablas(item);
        Tabla.getSelectionModel().setSelectionInterval(item, item);
    }
    
    private void CargaTablaPermisos(String Sql, int Combo){
        try {
            modelo = new DefaultTableModel();
            Tabla.setModel(modelo);
            
            rs = conet.consultar(Sql);
            
            ResultSetMetaData rsMd = rs.getMetaData();
            
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn((String) header_table.get(0));
            modelo.addColumn((String) header_table.get(1));
            modelo.addColumn((String) header_table.get(2));
            //for (int i=1; i <= cantidadColumnas; i++){
            //    modelo.addColumn(rsMd.getColumnLabel(i));
            //}
            
            int Item1=0;
                    
            rs.beforeFirst();
            while (rs.next()){
                Object[] fila = new Object[cantidadColumnas];

                for (int i=0; i<cantidadColumnas; i++){
                    fila[i]=rs.getObject(i+1);
                }
                
                modelo.addRow(fila);
            }
            
            SelectCombo=0;
            Item1=0;
            
            Tabla.getColumnModel().getColumn(2).setCellEditor(new Celda_CheckBox());
            Tabla.getColumnModel().getColumn(2).setCellRenderer(new Render_CheckBox());
            
            Tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            Tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            Tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String buscasubmenus(String Permiso, String Id){
        String ID_Sub_Menu="";
        
        try {
            String sql = "SELECT * FROM DNPERMISOLOGIA " +
                         "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+Permiso+" AND " +
                         "MIS_TIPOMENU=2 AND SUB_MEN_ID="+Id+" ORDER BY MIS_PERMISO,MIS_MENU";
            
            ResultSet rs_sub_menu = conet.consultar(sql);
            
            rs_sub_menu.beforeFirst();
            while (rs_sub_menu.next()){
                String sql_sub_Menu = "SELECT * FROM DNPERMISOLOGIA " +
                                      "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                                      "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+Permiso+" AND " +
                                      "MIS_TIPOMENU=2 AND SUB_MEN_ID="+rs_sub_menu.getString("MEN_ID")+" "+
                                      "ORDER BY MIS_PERMISO,MIS_MENU";

                ResultSet sub_menu = conet.consultar(sql_sub_Menu);
                
                if (sub_menu.getRow()>0){
                    ID_Sub_Menu = ID_Sub_Menu+"'"+sub_menu.getString("SUB_MEN_ID")+"',";
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("ID_Sub_Menu: "+ID_Sub_Menu);
        return ID_Sub_Menu;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private javax.swing.JButton bt_salir;
    private javax.swing.JComboBox jCboMen_Raiz;
    private javax.swing.JComboBox jCboMis_Permisos;
    private javax.swing.JComboBox jCboSub_Men_Hijos;
    private javax.swing.JComboBox jCboSub_Men_Nietos;
    private javax.swing.JComboBox jCboSub_Men_Raiz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}