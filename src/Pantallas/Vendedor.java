package Pantallas;

import Vista.Maestros;
import Vista.Actividad;
import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import static Pantallas.principal.escritorio;
import static Vista.Login.Idioma;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import clases.SQLQuerys;
import clases.ValidaCodigo;
import clases.conexion;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import clases.conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Vendedor extends javax.swing.JInternalFrame {
    String ruta,nombre;
    public String activo=null;
    CargaTablas cargatabla = null;
    public conexion con;
    
    private conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    private Vector Msg, elementos, header_table, Menu;
    private String codigo="";
    public int fila, atras=-2, adelante=0, Reg_count, Reg_Tablas, nMenu;
    private boolean SinRegistros = false, lAgregar, lModificar, lPass=false;
    private ResultSet rs, rs_count, rs_codigo, rs_menu, rs_menu_count, rs_permisologias, rs_menu_id, rs_combos;

    public Vendedor() {
        initComponents();
        
        jPanel1.setBackground(Color.decode(colorForm));
        
        bt_agregar.setBackground(Color.decode(colorForm)); bt_Modificar.setBackground(Color.decode(colorForm));
        bt_save.setBackground(Color.decode(colorForm)); bt_Eliminar.setBackground(Color.decode(colorForm));
        bt_cancel.setBackground(Color.decode(colorForm)); bt_find.setBackground(Color.decode(colorForm));
        bt_Atras.setBackground(Color.decode(colorForm)); bt_Siguiente.setBackground(Color.decode(colorForm));
        bt_salir.setBackground(Color.decode(colorForm));
        
        bt_agregar.setForeground(Color.decode(colorText)); bt_Modificar.setForeground(Color.decode(colorText));
        bt_save.setForeground(Color.decode(colorText)); bt_Eliminar.setForeground(Color.decode(colorText));
        bt_cancel.setForeground(Color.decode(colorText)); bt_find.setForeground(Color.decode(colorText));
        bt_Atras.setForeground(Color.decode(colorText)); bt_Siguiente.setForeground(Color.decode(colorText));
        bt_salir.setForeground(Color.decode(colorText));
        
        bt_agregar.setHorizontalAlignment(2); bt_Eliminar.setHorizontalAlignment(2); bt_Modificar.setHorizontalAlignment(2);
        bt_Siguiente.setHorizontalAlignment(2); bt_Atras.setHorizontalAlignment(2); bt_cancel.setHorizontalAlignment(2);
        bt_find.setHorizontalAlignment(2); bt_salir.setHorizontalAlignment(2); bt_save.setHorizontalAlignment(2);
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        
        jCheckVen_Activo.setForeground(Color.decode(colorText));
        
        jTxtVen_Codigo.setForeground(Color.decode(colorText)); jTxtVen_Nombre.setForeground(Color.decode(colorText));
        jTxtVen_telefono.setForeground(Color.decode(colorText)); jTxtVen_Comision.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2)); jLabel4.setText((String) elementos.get(3));
        
        jCheckVen_Activo.setText((String) elementos.get(4));

//        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(11), fila, fila, null, Color.decode(colorText)));
        
        bt_agregar.setText((String) elementos.get(5)); bt_Modificar.setText((String) elementos.get(6));
        bt_Eliminar.setText((String) elementos.get(7)); bt_Atras.setText((String) elementos.get(8)); 
        bt_Siguiente.setText((String) elementos.get(9)); bt_salir.setText((String) elementos.get(10)); 
        bt_save.setText((String) elementos.get(11)); bt_cancel.setText((String) elementos.get(12));
        bt_find.setText((String) elementos.get(13));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        cargatabla = new CargaTablas();
    
        //**************** Carga las diferentes Tablas ****************
        carga_tablas();
        //*************************************************************
        
        this.jCheckVen_Activo.setSelected(true);
        this.setTitle("Vendedores");

        valida_reg_tablas();
        
        jTxtVen_Nombre.requestFocus();        
    }

    private void valida_reg_tablas(){
        Reg_Tablas=tbl_vendedor.getRowCount();

        if (Reg_Tablas==0){
            Limpiar();
            habilitar("Inicializa");
            posicion_botones_2();
            CodConsecutivo();
            
            lAgregar=true;
        }else{
            posicion_botones_1();
            Deshabilitar();
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            
            Hilo_Vendedor Vendedor = new Hilo_Vendedor();
            Vendedor.start();
            
            tbl_vendedor.getSelectionModel().setSelectionInterval(tbl_vendedor.getRowCount()-1, tbl_vendedor.getRowCount()-1);
        }
    }
    
    public void tablaBrow(){
        cargatabla = new CargaTablas();
         String SQL = "SELECT VEN_CODIGO,VEN_NOMBRE,VEN_TELEFONO,VEN_COMISION FROM DNVENDEDOR ";
                
        System.out.println(SQL);
        String[] columnas = {"Codigo","Nombre","Telefono","Comision"};

        cargatabla.cargatablas(tbl_vendedor,SQL,columnas); 
    }
    
    public void CodConsecutivo(){
        String Consecutivo = null;
        
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        //Consecutivo = codigo.CodigoConsecutivo("SELECT CONCAT(REPEAT('0',6-LENGTH(CONVERT(MAX(VEN_CODIGO)+1,CHAR(6)))),CONVERT(MAX(VEN_CODIGO)+1,CHAR(6))) "
        //        + "AS CODIGO FROM DNVENDEDOR");
        Consecutivo = codigo.CodigoConsecutivo("VEN_CODIGO","DNVENDEDOR",6,"WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");

        if (Consecutivo==null) {
            Consecutivo="000001";
        }       
        this.jTxtVen_Codigo.setText(Consecutivo);
    }
    
    private void Limpiar(){
        
    }
    
    public void Deshabilitar(){;
        jTxtVen_Codigo.setEnabled(false); jTxtVen_Nombre.setEnabled(false); jTxtVen_telefono.setEnabled(false);
        jTxtVen_Comision.setEnabled(false);
        jCheckVen_Activo.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            jTxtVen_Codigo.setEnabled(true); jTxtVen_Nombre.setEnabled(true); jTxtVen_telefono.setEnabled(true);
            jTxtVen_Comision.setEnabled(true);
            jCheckVen_Activo.setEnabled(true);
        
            //jTxtPro_Codigo.requestFocus();
        }else if (accion=="Modificar"){
            jTxtVen_Codigo.setEnabled(true); jTxtVen_Nombre.setEnabled(true); jTxtVen_telefono.setEnabled(true);
            jTxtVen_Comision.setEnabled(true);
            jCheckVen_Activo.setEnabled(true);
            
            jTxtVen_Nombre.requestFocus();
        }
    }
    
    public void posicion_botones_1(){
        bt_agregar.setEnabled(true);
        
        bt_Modificar.setVisible(true); bt_find.setVisible(true); bt_Atras.setVisible(true);
        bt_Siguiente.setVisible(true); bt_salir.setVisible(true); bt_Eliminar.setVisible(true);
        
        bt_save.setVisible(false); bt_cancel.setVisible(false);
    }
    
    public void posicion_botones_2(){
        bt_agregar.setEnabled(false);
        
        bt_Modificar.setVisible(false); bt_find.setVisible(false); bt_Atras.setVisible(false);
        bt_Siguiente.setVisible(false); bt_salir.setVisible(false); bt_Eliminar.setVisible(false);
        
        bt_save.setVisible(true); bt_cancel.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtVen_Codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTxtVen_Nombre = new javax.swing.JTextField();
        jTxtVen_telefono = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jCheckVen_Activo = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_vendedor = new javax.swing.JTable();
        foto_vendedor = new javax.swing.JLabel();
        bt_buscarimagen = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTxtVen_Comision = new javax.swing.JTextField();
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Codigo");

        jLabel2.setText("Nombre");

        jLabel3.setText("Telefono");

        jCheckVen_Activo.setText("Activo");

        tbl_vendedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Telefono", "Comision", "Activo"
            }
        ));
        jScrollPane1.setViewportView(tbl_vendedor);

        foto_vendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        foto_vendedor.setText("Preview");
        foto_vendedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        foto_vendedor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        bt_buscarimagen.setText("Abrir");
        bt_buscarimagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarimagenActionPerformed(evt);
            }
        });

        jLabel4.setText("Comisión");

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_agregar.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_agregarActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Modificar.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ModificarActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setLabel("Grabar");
        bt_save.setPreferredSize(new java.awt.Dimension(100, 39));
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
        jToolBar1.add(bt_save);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_EliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_cancel);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_find.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Atras.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AtrasActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SiguienteActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_salir.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTxtVen_Codigo)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTxtVen_telefono)
                                                    .addComponent(jTxtVen_Comision, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 51, Short.MAX_VALUE)))
                                        .addGap(108, 108, 108)
                                        .addComponent(jCheckVen_Activo))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTxtVen_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(foto_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(bt_buscarimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTxtVen_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckVen_Activo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxtVen_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtVen_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtVen_Comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(foto_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_buscarimagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void bt_buscarimagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarimagenActionPerformed
        final JFileChooser elegirImagen = new JFileChooser();

        elegirImagen.setMultiSelectionEnabled(false);

        int o = elegirImagen.showOpenDialog(this);

        if(o == JFileChooser.APPROVE_OPTION){
            ruta = elegirImagen.getSelectedFile().getAbsolutePath();
            nombre = elegirImagen.getSelectedFile().getName();

            //jTextField1.setText(ruta);
            Image preview = Toolkit.getDefaultToolkit().getImage(ruta);

            if(preview != null){
                foto_vendedor.setText("");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(foto_vendedor.getWidth(), foto_vendedor.getHeight(), Image.SCALE_DEFAULT));
                foto_vendedor.setIcon(icon);
            }
        }
    }//GEN-LAST:event_bt_buscarimagenActionPerformed

    private void bt_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_agregarActionPerformed
        lAgregar=true;
        lModificar=false;

        habilitar("Inicializa");
        Limpiar();
        posicion_botones_2();
        CodConsecutivo();
    }//GEN-LAST:event_bt_agregarActionPerformed

    private void bt_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ModificarActionPerformed
        lAgregar=false;
        lModificar=true;

        habilitar("Modificar");
        posicion_botones_2();
    }//GEN-LAST:event_bt_ModificarActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        action_bt_agregar();
    }//GEN-LAST:event_bt_saveActionPerformed

    private void bt_saveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_saveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_bt_agregar();
        }
    }//GEN-LAST:event_bt_saveKeyPressed

    private void bt_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_EliminarActionPerformed
        int eliminado;

        codigo = jTxtVen_Codigo.getText().trim();
        String descrip = jTxtVen_Nombre.getText().toString().trim();

        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(0)+codigo+" - "+descrip+"?");
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'",true,"");

            //---------- Refresca la Tabla para vizualizar los ajustes ----------
            carga_tablas();
            //-------------------------------------------------------------------
        }

        Hilo_Vendedor Vendedor = new Hilo_Vendedor();
        Vendedor.start();
    }//GEN-LAST:event_bt_EliminarActionPerformed

    private void bt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelActionPerformed
        int eleccion = 0;

        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1));
        }else if (lModificar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(2));
        }

        if (eleccion == 0) {
            if (tbl_vendedor.getRowCount()==0){
                this.dispose();
                return;
            }

            Deshabilitar();
            posicion_botones_1();

            try {
                Cargardatos();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_cancelActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        Pantallas.Buscar BuscaDoc = new Pantallas.Buscar();

        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = BuscaDoc.getSize();
        BuscaDoc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorio.add(BuscaDoc);
        BuscaDoc.show();
        BuscaDoc.setVisible(true);
    }//GEN-LAST:event_bt_findActionPerformed

    private void bt_AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AtrasActionPerformed
        try {
            if(atras==-1){
                return;
            }

            if (atras==-2){
                atras=tbl_vendedor.getRowCount()-1;

                adelante=0;
            }

            atras=atras-1;
            adelante=adelante-1;
            System.out.println("Atras: "+atras);

            if (atras!=-2){
                ActualizaJtable(atras);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bt_AtrasActionPerformed

    private void bt_SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SiguienteActionPerformed
        int Reg=0;
        Reg=tbl_vendedor.getRowCount();

        if (atras==-2){
            adelante=Reg;
        }

        try {
            if (adelante==Reg){
                atras=Reg-1;
                ActualizaJtable(adelante-1);
                return;
            }

            if (atras==-1 || atras==-2){
                adelante=1;
                atras=1;
            }else{
                if (adelante<Reg){
                    adelante=atras+1;
                    atras=atras+1;
                }
            }

            System.out.println("Adelante: "+adelante);

            if (adelante<Reg){
                ActualizaJtable(adelante);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_bt_SiguienteActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_bt_salirActionPerformed

    private void action_bt_agregar(){
        String imagen = ruta;
        String codigo = this.jTxtVen_Codigo.getText();
        String vendedor = this.jTxtVen_Nombre.getText();
        String telefono = this.jTxtVen_telefono.getText();
        String comision = this.jTxtVen_Comision.getText();
        
        if (jCheckVen_Activo.isSelected()== true ){
            activo = "1";
            
        }
        
        if (jCheckVen_Activo.isSelected()== false ){
            activo="0";
        }
        
        if ("".equals(jTxtVen_Nombre.getText())){
            JOptionPane.showMessageDialog(null,"El Nombre del Vendedor no puede estar vacío");
            jTxtVen_Nombre.requestFocus();
            return;
        }
        
        if (Reg_Tablas==0){
            lAgregar=true;
        }
        
        if (lAgregar==true){
            //---------- Llama la Clase Insert para Guardar los Registros ----------
            SQLQuerys insertar = new SQLQuerys();
            String SQL = "INSERT INTO DNVENDEDOR (VEN_EMPRESA,VEN_CODIGO,VEN_NOMBRE,VEN_TELEFONO,VEN_COMISION,VEN_ACTIVO) "+
                         "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+codigo+"','"+vendedor+"','"+telefono+"','"+comision+"','"+activo+"');";
            
            System.out.println(SQL);
            insertar.SqlInsert(SQL);
            //----------------------------------------------------------------------
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se registro el Vendedor ''"+codigo.toUpperCase()+"'' Correctamente");
        }else if (lModificar==true){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNVENDEDOR SET VEN_CODIGO='"+codigo.toUpperCase()+"', VEN_NOMBRE='"+nombre+"',"+
                                                      "VEN_TELEFONO='"+telefono+"', VEN_COMISION='"+comision+"',"+
                                                      "VEN_ACTIVO='"+activo+"' "+
                                "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'";
            
            System.out.println(sql);
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Producto ''"+codigo.toUpperCase()+"'' Correctamente");
        }
        
        //**************** Carga las diferentes Tablas ****************
        carga_tablas();
        //*************************************************************
        
        try {
            posicion_botones_1();
            Deshabilitar();
            Cargardatos();
            
            lModificar=false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void carga_tablas(){
        String SQL_VEN = "SELECT VEN_CODIGO,VEN_NOMBRE,VEN_TELEFONO,VEN_COMISION FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3)};
        
        cargatabla.cargatablas(tbl_vendedor,SQL_VEN,columnas);
        tbl_vendedor.getSelectionModel().setSelectionInterval(tbl_vendedor.getRowCount()-1, tbl_vendedor.getRowCount()-1);
    }
    
    private void action_tablas(String Tabla, int Row){
        codigo = (String) tbl_vendedor.getValueAt(Row,0).toString().trim();
        
        String SQL = "SELECT * FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'";
        try {
            rs = conet.consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
        }

        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'";
        Reg_count = conet.Count_Reg(SQLReg);

        try {
            mostrar();
        } catch (SQLException ex) {
            Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ActualizaJtable(int item){
        action_tablas("Ventas", item);
        tbl_vendedor.getSelectionModel().setSelectionInterval(item, item);
    }
    
    public void Cargardatos() throws ClassNotFoundException, SQLException{
        String SQL = "SELECT * FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        Reg_count = conet.Count_Reg(SQL_Reg);
        
        if (Reg_count==1){
            rs.next();
        }else{
            rs.last();
        }
        
        Hilo_Vendedor Vendedor = new Hilo_Vendedor();
        Vendedor.start();
    }
    
    public void mostrar() throws SQLException{
        String sql_cmb="";
        
        if (Reg_count > 0){
            jTxtVen_Codigo.setText(rs.getString("VEN_CODIGO").trim());  jTxtVen_Nombre.setText(rs.getString("VEN_NOMBRE").trim()); 
            jTxtVen_telefono.setText(rs.getString("VEN_TELEFONO").trim());  jTxtVen_Comision.setText(rs.getString("VEN_COMISION").trim());
                    
            if (rs.getBoolean("VEN_ACTIVO")==true){
                jCheckVen_Activo.setSelected(true);
            }else{
                jCheckVen_Activo.setSelected(false);
            }
        }
    }

    public class Hilo_Vendedor extends Thread{
        public void run(){
            try {
                String SQLCodDoc="";
            
                SQLCodDoc = "SELECT VEN_CODIGO FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY VEN_ID DESC LIMIT 1 ";
                
                rs_codigo = conet.consultar(SQLCodDoc);
                try {
                    if(lModificar==false){
                        codigo=rs_codigo.getString("VEN_CODIGO").trim();
                    }else{
                        codigo=jTxtVen_Codigo.getText().toString().trim();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (Reg_Tablas==0){
                    codigo="";
                }
                
                String SQL = "SELECT * FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'";
                System.out.println(SQL);
                try {
                    rs = conet.consultar(SQL);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNVENDEDOR WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND VEN_CODIGO='"+codigo+"'";
                Reg_count = conet.Count_Reg(SQLReg);
                try {
                    mostrar();
                } catch (SQLException ex) {
                    Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_buscarimagen;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    public static javax.swing.JLabel foto_vendedor;
    private javax.swing.JCheckBox jCheckVen_Activo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtVen_Codigo;
    private javax.swing.JTextField jTxtVen_Comision;
    private javax.swing.JTextField jTxtVen_Nombre;
    private javax.swing.JTextField jTxtVen_telefono;
    private javax.swing.JTable tbl_vendedor;
    // End of variables declaration//GEN-END:variables
}