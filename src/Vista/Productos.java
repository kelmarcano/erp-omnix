package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import static Pantallas.principal.escritorio;
import Modelos.VariablesGlobales;
import Pantallas.Listas;
import clases.CargaComboBoxs;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;

public class Productos extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0, Reg_count, Reg_Tablas, nMenu;
    public static boolean Bandera = false;
    private boolean SinRegistros = false, lAgregar, lModificar, lPass=false;
    private ResultSet rs, rs_count, rs_codigo, rs_menu, rs_menu_count, rs_permisologias, rs_menu_id, rs_combos;
    
    private Vector Msg, elementos, header_table, Menu;
    
    private String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    private String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    private String codigo="";

    private conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();

    public Productos() {
        initComponents();
        inicializaClase();
        comboMarca();

        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText)); jLabel6.setForeground(Color.decode(colorText));
        jLabel7.setForeground(Color.decode(colorText)); jLabel8.setForeground(Color.decode(colorText));
        jLabel10.setForeground(Color.decode(colorText));
        
        jCheckPro_Activo.setForeground(Color.decode(colorText));
        
        jTxtPro_CodMae.setForeground(Color.decode(colorText)); jTxtPro_Codigo.setForeground(Color.decode(colorText));
        jTxtPro_Descri.setForeground(Color.decode(colorText)); jTxtPro_Nombre.setForeground(Color.decode(colorText));
        jTxtPro_RutaImg.setForeground(Color.decode(colorText)); jTxt_Buscar.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2)); jLabel4.setText((String) elementos.get(3));
        jLabel5.setText((String) elementos.get(4)); jLabel6.setText((String) elementos.get(5));
        jLabel7.setText((String) elementos.get(6)); jLabel8.setText((String) elementos.get(7));
        jLabel10.setText((String) elementos.get(8));
        
        jCheckPro_Activo.setText((String) elementos.get(9));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(11), fila, fila, null, Color.decode(colorText)));
        
        bt_agregar.setText((String) elementos.get(12)); bt_Modificar.setText((String) elementos.get(13));
        bt_Eliminar.setText((String) elementos.get(14)); bt_Atras.setText((String) elementos.get(15)); 
        bt_Siguiente.setText((String) elementos.get(16)); bt_salir.setText((String) elementos.get(17)); 
        bt_save.setText((String) elementos.get(18)); bt_cancel.setText((String) elementos.get(19));
        bt_find.setText((String) elementos.get(20));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        Limitar();
        modelActionListener.cargaTabla();
        
        lbl_buscar.setVisible(false); jTxt_Buscar.setVisible(false);
        jTxtPro_CodMae.setEditable(false);
        
        this.jCheckPro_Activo.setSelected(true);
        this.setTitle("Productos");
        
        valida_reg_tablas();
        
        jTxtPro_Nombre.requestFocus();
        
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
        jTxtPro_Codigo.setDocument(new LimitarCaracteres(jTxtPro_Codigo, 10));
        jTxtPro_Nombre.setDocument(new LimitarCaracteres(jTxtPro_Nombre, 80));
    }
    
    public void buscarimagen(){
        JFileChooser fc = new JFileChooser();
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            File s = fc.getSelectedFile();
            String l = s.getAbsoluteFile().toString();
                
            jTxtPro_RutaImg.setText(l);
            
            Image preview = Toolkit.getDefaultToolkit().getImage(jTxtPro_RutaImg.getText());
            ImageIcon icon = new ImageIcon(preview.getScaledInstance(jProduct_Preview.getWidth(), jProduct_Preview.getHeight(), Image.SCALE_DEFAULT));
            jProduct_Preview.setIcon(icon);
                
            System.out.println(s);
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

        jPanel2 = new javax.swing.JPanel();
        lbl_buscar = new javax.swing.JLabel();
        jTxt_Buscar = new javax.swing.JTextField();
        jProduct_Preview = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTxtPro_RutaImg = new javax.swing.JTextField();
        jBtn_Buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Productos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtPro_Codigo = new javax.swing.JTextField();
        jTxtPro_Nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboPro_Marca = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboPro_TipIva = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jTxtPro_CodMae = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtPro_Descri = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboPro_Und = new javax.swing.JComboBox();
        jComboPro_Tipprec = new javax.swing.JComboBox();
        jCheckPro_Activo = new javax.swing.JCheckBox();
        jBtnBusca_Maestro = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        bt_agregar = new javax.swing.JButton();
        bt_Modificar = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        bt_cancel = new javax.swing.JButton();
        bt_Eliminar = new javax.swing.JButton();
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

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_buscar.setText("Buscar");
        jPanel2.add(lbl_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, -1));
        jPanel2.add(jTxt_Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 126, -1));

        jProduct_Preview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jProduct_Preview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LoginInicio.png"))); // NOI18N
        jProduct_Preview.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jProduct_Preview.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.add(jProduct_Preview, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 230, 220));

        jLabel10.setText("Ruta de lmagen");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));
        jPanel2.add(jTxtPro_RutaImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 470, -1));

        jBtn_Buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        jBtn_Buscar.setText("Buscar");
        jBtn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_BuscarActionPerformed(evt);
            }
        });
        jPanel2.add(jBtn_Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, -1, -1));

        Tabla_Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Tabla_Productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_ProductosMouseClicked(evt);
            }
        });
        Tabla_Productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_ProductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_ProductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla_Productos);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 295, 470, 190));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Codigo Producto o Referencia");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel2.setText("Nombre");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));
        jPanel1.add(jTxtPro_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 126, -1));
        jPanel1.add(jTxtPro_Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 206, -1));

        jLabel3.setText("Marca");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jPanel1.add(jComboPro_Marca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 206, -1));

        jLabel4.setText("Tipo de Impuesto");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jPanel1.add(jComboPro_TipIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 206, -1));

        jLabel5.setText("Proveedor");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));
        jPanel1.add(jTxtPro_CodMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 210, -1));

        jLabel6.setText("Descripción");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jTxtPro_Descri.setColumns(20);
        jTxtPro_Descri.setRows(5);
        jScrollPane2.setViewportView(jTxtPro_Descri);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 360, 68));

        jLabel7.setText("Precio");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        jLabel8.setText("Unidad de Medida");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        jPanel1.add(jComboPro_Und, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 210, -1));

        jPanel1.add(jComboPro_Tipprec, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 210, -1));

        jCheckPro_Activo.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jCheckPro_Activo.setSelected(true);
        jCheckPro_Activo.setText("Activo");
        jCheckPro_Activo.setToolTipText("");
        jPanel1.add(jCheckPro_Activo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        jBtnBusca_Maestro.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_Maestro.setText("[...]");
        jBtnBusca_Maestro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_MaestroActionPerformed(evt);
            }
        });
        jBtnBusca_Maestro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBtnBusca_MaestroKeyPressed(evt);
            }
        });
        jPanel1.add(jBtnBusca_Maestro, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 246, -1, -1));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 380, 490));

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_agregar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Modificar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setLabel("Grabar");
        bt_save.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_save);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_cancel);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Eliminar);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_find.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Atras.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_salir.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_salir);

        jPanel2.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 120, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        Pantallas.Buscar BuscaDoc = new Pantallas.Buscar();

        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = BuscaDoc.getSize();
        BuscaDoc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorio.add(BuscaDoc);
        BuscaDoc.show();
        BuscaDoc.setVisible(true);
    }//GEN-LAST:event_bt_findActionPerformed

    private void jBtn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_BuscarActionPerformed
        buscarimagen();
    }//GEN-LAST:event_jBtn_BuscarActionPerformed

    private void jBtnBusca_MaestroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_MaestroActionPerformed
        action_busca_maestros();
    }//GEN-LAST:event_jBtnBusca_MaestroActionPerformed

    private void jBtnBusca_MaestroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBtnBusca_MaestroKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_busca_maestros();
        }
    }//GEN-LAST:event_jBtnBusca_MaestroKeyPressed

    private void Tabla_ProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ProductosKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(Tabla_Productos.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ProductosKeyPressed

    private void Tabla_ProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ProductosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(Tabla_Productos.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(Tabla_Productos.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ProductosKeyReleased

    private void Tabla_ProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ProductosMouseClicked
        modelActionListener.actualizaJtable(Tabla_Productos.getSelectedRow());
        
        TableCellEditor celltable = Tabla_Productos.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla_Productos != null){
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_Tabla_ProductosMouseClicked

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void action_busca_maestros(){
        Pantallas.Listas ListasTipoMae = new Pantallas.Listas();
        ListasTipoMae.titulo("Proveedores","Proveedores");

        String sqlCodigo = "SELECT MAE_CODIGO AS DATO1 FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String sqlDescrip = "SELECT MAE_NOMBRE AS DATO1 FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";

        this.SinRegistros = ListasTipoMae.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(6));
//            jTxtMae_Limite.requestFocus();
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListasTipoMae.getSize();
            ListasTipoMae.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            
            ListasTipoMae.show();
            Listas.Tabla="DNMAESTRO";
            ListasTipoMae.setVisible(true);
        }
    }
    
    private void valida_reg_tablas(){
        Reg_Tablas=Tabla_Productos.getRowCount();

        if (Reg_Tablas==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();
            
            lAgregar=true;
        }else{
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();
            
            Tabla_Productos.getSelectionModel().setSelectionInterval(Tabla_Productos.getRowCount()-1, Tabla_Productos.getRowCount()-1);
        }
    }
 
    private void inicializaClase(){
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setButtonOther(jBtnBusca_Maestro, null, null);
        modelActionListener.setClass(this);
        modelActionListener.setJCheckBox(jCheckPro_Activo, null, null);
        modelActionListener.setJTable(Tabla_Productos, null, null, null);
        modelActionListener.setJComboBox(jComboPro_Marca, jComboPro_TipIva, jComboPro_Tipprec, jComboPro_Und, null);
        modelActionListener.setJTextFieldProductos(jTxtPro_Codigo, jTxtPro_Nombre, jTxtPro_CodMae, jTxtPro_RutaImg);
        modelActionListener.setJTextArea(jTxtPro_Descri);
    }
    
     public void comboMarca() {
        String sql = "SELECT MAR_CODIGO, MAR_DESCRI AS DATO1 FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jComboPro_Marca.setModel(mdl);
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_Productos;
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton jBtnBusca_Maestro;
    private javax.swing.JButton jBtn_Buscar;
    public static javax.swing.JCheckBox jCheckPro_Activo;
    private javax.swing.JComboBox jComboPro_Marca;
    private javax.swing.JComboBox jComboPro_TipIva;
    private javax.swing.JComboBox jComboPro_Tipprec;
    private javax.swing.JComboBox jComboPro_Und;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jProduct_Preview;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JTextField jTxtPro_CodMae;
    public static javax.swing.JTextField jTxtPro_Codigo;
    public static javax.swing.JTextArea jTxtPro_Descri;
    public static javax.swing.JTextField jTxtPro_Nombre;
    private javax.swing.JTextField jTxtPro_RutaImg;
    private javax.swing.JTextField jTxt_Buscar;
    private javax.swing.JLabel lbl_buscar;
    // End of variables declaration//GEN-END:variables
}