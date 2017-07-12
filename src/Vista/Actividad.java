package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import clases.SQLSelect;
import clases.conexion;
import clases.CargaTablas;
import clases.LimitarCaracteres;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableCellEditor;
import Modelos.VariablesGlobales;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import java.awt.Color;
import java.io.File;
import java.util.Vector;

public class Actividad extends javax.swing.JInternalFrame{
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo="";
    private Vector Msg, elementos, header_table;

    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener prueba = ModelActionListener.getPrueba();
    
    public Actividad() throws ClassNotFoundException, SQLException {
        initComponents();
        inicializaClase();

        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
                
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        
        jCheckAct_Activo.setForeground(Color.decode(colorText));
        
        jTxtAct_Codigo.setForeground(Color.decode(colorText)); jTxtAct_Nombre.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jCheckAct_Activo.setText((String) elementos.get(2));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(3), fila, fila, null, Color.decode(colorText)));
        
        bt_agregar.setText((String) elementos.get(4)); bt_Modificar.setText((String) elementos.get(5));
        bt_Eliminar.setText((String) elementos.get(6)); bt_Atras.setText((String) elementos.get(7)); 
        bt_Siguiente.setText((String) elementos.get(8)); bt_salir.setText((String) elementos.get(9)); 
        bt_save.setText((String) elementos.get(10)); bt_cancel.setText((String) elementos.get(11));
        bt_find.setText((String) elementos.get(12));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        Limitar();
        prueba.cargaTabla();

        this.jCheckAct_Activo.setSelected(true);
        this.setTitle("Actividad");
        
        if (Tabla.getRowCount()==0){
            prueba.setAccion("Inicializa");
            prueba.habilitar();
            //habilitar("Inicializa");
            prueba.posicion_botones_2();
            prueba.setConsecutivo();
            jTxtAct_Codigo.setEnabled(false);
            jTxtAct_Nombre.requestFocus();
            
            lAgregar=true;
        }else{
            prueba.posicion_botones_1();
            prueba.Deshabilitar();
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            
            prueba.ejecutaHilo();
            
            Tabla.getSelectionModel().setSelectionInterval(Tabla.getRowCount()-1, Tabla.getRowCount()-1);
        }
        
        bt_agregar.addActionListener(new ListenerButton());
        bt_Modificar.addActionListener(new ListenerButton());
        bt_save.addActionListener(new ListenerButton());
        bt_Eliminar.addActionListener(new ListenerButton());
        bt_cancel.addActionListener(new ListenerButton());
        bt_salir.addActionListener(new ListenerButton());
        bt_Atras.addActionListener(new ListenerButton());
        bt_Siguiente.addActionListener(new ListenerButton());
    }
    
    public void Limitar(){
        jTxtAct_Codigo.setDocument(new LimitarCaracteres(jTxtAct_Codigo, 6));
        jTxtAct_Nombre.setDocument(new LimitarCaracteres(jTxtAct_Nombre, 80));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtAct_Codigo = new javax.swing.JTextField();
        jTxtAct_Nombre = new javax.swing.JTextField();
        jCheckAct_Activo = new javax.swing.JCheckBox();
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
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        jMenu1.setText("Sistema");

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Informes");
        jMenuBar1.add(jMenu2);

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel2.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel2.setText("Descripción:");

        jTxtAct_Codigo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtAct_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtAct_CodigoKeyPressed(evt);
            }
        });

        jTxtAct_Nombre.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtAct_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtAct_NombreKeyPressed(evt);
            }
        });

        jCheckAct_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckAct_Activo.setText("Activo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTxtAct_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtAct_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addComponent(jCheckAct_Activo))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtAct_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckAct_Activo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtAct_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

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
                .addGap(0, 451, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(134, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(22, Short.MAX_VALUE)))
        );

        jMenu3.setText("Sistema");

        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Informes");
        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

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

    private void jTxtAct_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtAct_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtAct_Nombre.requestFocus();
        }
    }//GEN-LAST:event_jTxtAct_CodigoKeyPressed

    private void jTxtAct_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtAct_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_jTxtAct_NombreKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        prueba.actualizaJtable(Tabla.getSelectedRow());
        
        TableCellEditor celltable = Tabla.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla != null){
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            prueba.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            prueba.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            prueba.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated
 
    private void inicializaClase(){
        System.out.println("Activado Form Actividad");
        prueba.setClaseOrg(this.getClass().getName().toString());
        prueba.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        prueba.setClass(this);
        prueba.setJCheckBox(jCheckAct_Activo, null, null);
        prueba.setJTable(Tabla, null, null, null);
        prueba.setJTextField(jTxtAct_Codigo, jTxtAct_Nombre, null, null, null, null, null, null);
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
    private javax.swing.JCheckBox jCheckAct_Activo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtAct_Codigo;
    private javax.swing.JTextField jTxtAct_Nombre;
    // End of variables declaration//GEN-END:variables
}