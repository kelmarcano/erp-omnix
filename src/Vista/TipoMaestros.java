package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import Modelos.VariablesGlobales;
import clases.SQLSelect;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.conexion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.TableCellEditor;

public class TipoMaestros extends javax.swing.JInternalFrame  {
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

    public TipoMaestros() {
        initComponents();
        inicializaClase();
        
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        
        jCheckTma_Activo.setForeground(Color.decode(colorText));
        
        jTxtTip_Codigo.setForeground(Color.decode(colorText)); jTxtTip_Descripcion.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        
        jCheckTma_Activo.setText((String) elementos.get(2));
        
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
        
        this.setSize(600, 360);
        
        if (Tabla.getRowCount()==0){
            prueba.setAccion("Inicializa");
            prueba.habilitar();
            prueba.posicion_botones_2();
            prueba.setConsecutivo();
            jTxtTip_Descripcion.requestFocus();
            
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
        bt_Atras.addActionListener(new ListenerButton());
        bt_Siguiente.addActionListener(new ListenerButton());
        bt_salir.addActionListener(new ListenerButton());
    }
  
    public void Limitar(){
        jTxtTip_Codigo.setDocument(new LimitarCaracteres(jTxtTip_Codigo, 10));
        jTxtTip_Descripcion.setDocument(new LimitarCaracteres(jTxtTip_Descripcion, 80));
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
        jTxtTip_Codigo = new javax.swing.JTextField();
        jTxtTip_Descripcion = new javax.swing.JTextField();
        jCheckTma_Activo = new javax.swing.JCheckBox();
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
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel2.setText("Descripción:");

        jTxtTip_Codigo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtTip_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtTip_CodigoKeyPressed(evt);
            }
        });

        jTxtTip_Descripcion.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtTip_Descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtTip_DescripcionKeyPressed(evt);
            }
        });

        jCheckTma_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckTma_Activo.setText("Activo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTxtTip_Descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jTxtTip_Codigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckTma_Activo)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtTip_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCheckTma_Activo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTxtTip_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        bt_agregar.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setLabel("Grabar");
        bt_save.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_save);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_cancel);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bt_Atras.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setPreferredSize(new java.awt.Dimension(100, 45));
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 476, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(132, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtTip_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTip_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtTip_Descripcion.requestFocus();
        }
    }//GEN-LAST:event_jTxtTip_CodigoKeyPressed

    private void jTxtTip_DescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtTip_DescripcionKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_jTxtTip_DescripcionKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        prueba.actualizaJtable(Tabla.getSelectedRow());
        
        TableCellEditor celltable = Tabla.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla != null)
        {
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        String descrip = ""; String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            prueba.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

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
        System.out.println("Activado Form Tipo de Maestros");
        prueba.setClaseOrg(this.getClass().getName().toString());
        prueba.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        prueba.setClass(this);
        prueba.setJCheckBox(jCheckTma_Activo, null, null);
        prueba.setJTable(Tabla, null, null, null);
        prueba.setJTextField(jTxtTip_Codigo, jTxtTip_Descripcion, null, null, null, null, null, null);
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
    private javax.swing.JCheckBox jCheckTma_Activo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtTip_Codigo;
    private javax.swing.JTextField jTxtTip_Descripcion;
    // End of variables declaration//GEN-END:variables
}