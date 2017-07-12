package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import Modelos.VariablesGlobales;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.conexion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.TableCellEditor;

public class GruposPermisos extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo="", tipomenu="";
    private Vector Msg, elementos, header_table;
    
    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();

    public GruposPermisos() {
        initComponents();
        inicializaClase();
        
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        
        jCheckPer_Activo.setBackground(Color.decode(colorForm)); jCheckPer_Activo.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        
        jOpcAppAndroid.setForeground(Color.decode(colorText)); jOpcAppDesktop.setForeground(Color.decode(colorText));
        jOpcAppWeb.setForeground(Color.decode(colorText));
        
        jTxtPer_Id.setForeground(Color.decode(colorText)); jTxtPer_Nombre.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);
            
        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        
        jCheckPer_Activo.setText((String) elementos.get(2));
        
        jOpcAppWeb.setText((String) elementos.get(3)); jOpcAppWeb.setText((String) elementos.get(4));
        jOpcAppWeb.setText((String) elementos.get(5));
        
        bt_agregar.setText((String) elementos.get(6)); bt_Modificar.setText((String) elementos.get(7));
        bt_Eliminar.setText((String) elementos.get(8)); bt_find.setText((String) elementos.get(9));
        bt_Atras.setText((String) elementos.get(10)); bt_Siguiente.setText((String) elementos.get(11));
        bt_salir.setText((String) elementos.get(12)); bt_save.setText((String) elementos.get(13));
        bt_cancel.setText((String) elementos.get(14));
        
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(15), fila, fila, null, Color.decode(colorText)));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------

        this.setTitle("Grupos de Permiso de Usuarios");
        
        modelActionListener.cargaTabla();
        
        if (Tabla.getRowCount()==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();

            jTxtPer_Id.setEnabled(false);
            jTxtPer_Nombre.requestFocus();
            
            lAgregar=true;
        }else{
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();
            
            Tabla.getSelectionModel().setSelectionInterval(Tabla.getRowCount()-1, Tabla.getRowCount()-1);
        }
        
        bt_agregar.addActionListener(new ListenerButton());
        bt_Modificar.addActionListener(new ListenerButton());
        bt_save.addActionListener(new ListenerButton());
        bt_Eliminar.addActionListener(new ListenerButton());
        bt_cancel.addActionListener(new ListenerButton());
        bt_Atras.addActionListener(new ListenerButton());
        bt_Siguiente.addActionListener(new ListenerButton());
        bt_salir.addActionListener(new ListenerButton());
    }

    public void Limitar(){
        jTxtPer_Nombre.setDocument(new LimitarCaracteres(jTxtPer_Nombre, 100));
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtPer_Id = new javax.swing.JTextField();
        jTxtPer_Nombre = new javax.swing.JTextField();
        jCheckPer_Activo = new javax.swing.JCheckBox();
        jOpcAppWeb = new javax.swing.JRadioButton();
        jOpcAppDesktop = new javax.swing.JRadioButton();
        jOpcAppAndroid = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        bt_agregar = new javax.swing.JButton();
        bt_Modificar = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        bt_Eliminar = new javax.swing.JButton();
        bt_cancel = new javax.swing.JButton();
        bt_find = new javax.swing.JButton();
        bt_Atras = new javax.swing.JButton();
        bt_Siguiente = new javax.swing.JButton();
        bt_salir = new javax.swing.JButton();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel2.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel1.setText("ID Permiso:");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jTxtPer_Id.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPer_Id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtPer_IdActionPerformed(evt);
            }
        });
        jTxtPer_Id.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtPer_IdFocusLost(evt);
            }
        });
        jTxtPer_Id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPer_IdKeyPressed(evt);
            }
        });

        jTxtPer_Nombre.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPer_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPer_NombreKeyPressed(evt);
            }
        });

        jCheckPer_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckPer_Activo.setText("Activo");
        jCheckPer_Activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckPer_ActivoActionPerformed(evt);
            }
        });

        jOpcAppWeb.setText("App Web");
        jOpcAppWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpcAppWebActionPerformed(evt);
            }
        });

        jOpcAppDesktop.setText("App Desktop");
        jOpcAppDesktop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpcAppDesktopActionPerformed(evt);
            }
        });

        jOpcAppAndroid.setText("App Android");
        jOpcAppAndroid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpcAppAndroidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jOpcAppWeb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jOpcAppDesktop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jOpcAppAndroid)
                        .addGap(0, 104, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTxtPer_Id, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(jTxtPer_Nombre, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jCheckPer_Activo))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtPer_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckPer_Activo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtPer_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOpcAppWeb)
                    .addComponent(jOpcAppDesktop)
                    .addComponent(jOpcAppAndroid))
                .addContainerGap())
        );

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
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

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setLabel("Grabar");
        bt_save.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_save);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_cancel);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 476, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(148, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(24, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtPer_IdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtPer_IdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPer_IdActionPerformed

    private void jTxtPer_IdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPer_IdFocusLost
        // TODO add your handling code here:
        //        int registros;

        //        ValidaCodigo consulta = new ValidaCodigo();
        //        registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNACTIVIDAD WHERE ACT_CODIGO='"+jTxtAct_Codigo.getText().trim()+"'",false,"");

        //        if (registros==1){
            //            jTxtAct_Codigo.requestFocus();
            //            jTxtAct_Codigo.setText("");
            //        }
    }//GEN-LAST:event_jTxtPer_IdFocusLost

    private void jTxtPer_IdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPer_IdKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtPer_Nombre.requestFocus();
        }
    }//GEN-LAST:event_jTxtPer_IdKeyPressed

    private void jTxtPer_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPer_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_jTxtPer_NombreKeyPressed

    private void jCheckPer_ActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckPer_ActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckPer_ActivoActionPerformed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        modelActionListener.actualizaJtable(Tabla.getSelectedRow());

        TableCellEditor celltable = Tabla.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla != null)
        {
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed

    }//GEN-LAST:event_bt_findActionPerformed

    private void jOpcAppAndroidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpcAppAndroidActionPerformed
        jOpcAppWeb.setSelected(false);
        jOpcAppDesktop.setSelected(false);
        jOpcAppAndroid.setSelected(true);
    }//GEN-LAST:event_jOpcAppAndroidActionPerformed

    private void jOpcAppWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpcAppWebActionPerformed
        jOpcAppWeb.setSelected(true);
        jOpcAppDesktop.setSelected(false);
        jOpcAppAndroid.setSelected(false);
    }//GEN-LAST:event_jOpcAppWebActionPerformed

    private void jOpcAppDesktopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpcAppDesktopActionPerformed
        jOpcAppWeb.setSelected(false);
        jOpcAppDesktop.setSelected(true);
        jOpcAppAndroid.setSelected(false);
    }//GEN-LAST:event_jOpcAppDesktopActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void inicializaClase(){
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setClass(this);
        modelActionListener.setJCheckBox(jCheckPer_Activo, null, null);
        modelActionListener.setJRadioButton(jOpcAppWeb, jOpcAppDesktop, jOpcAppAndroid, null);
        modelActionListener.setJTable(Tabla, null, null, null);
        modelActionListener.setJTextField(jTxtPer_Id, jTxtPer_Nombre, null, null, null, null, null, null);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    private javax.swing.JCheckBox jCheckPer_Activo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jOpcAppAndroid;
    private javax.swing.JRadioButton jOpcAppDesktop;
    private javax.swing.JRadioButton jOpcAppWeb;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtPer_Id;
    private javax.swing.JTextField jTxtPer_Nombre;
    // End of variables declaration//GEN-END:variables
}