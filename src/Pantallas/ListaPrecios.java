package Pantallas;

import Vista.Maestros;
import Vista.Actividad;
import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.SQLQuerys;
import clases.SQLSelect;
import clases.conexion;
import clases.BloqueaTablas;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import clases.LimitarCaracteres;
import clases.ValidaCodigo;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import clases.ReadFileXml;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author riky1_000
 */
public class ListaPrecios extends javax.swing.JInternalFrame {
    public int fila;
    private SQLSelect Registros;
    private boolean Bandera = false;
    private ResultSet rs; ResultSet rs_count;
    private int Reg_count;
    private Vector Msg, elementos, header_table;
    
    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public ListaPrecios() throws ClassNotFoundException, SQLException {
        initComponents();
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder((String) elementos.get(7)));
        
        bt_agregar.setText((String) elementos.get(8)); bt_Modificar.setText((String) elementos.get(9));
        bt_Eliminar.setText((String) elementos.get(10)); bt_Atras.setText((String) elementos.get(11)); 
        bt_Siguiente.setText((String) elementos.get(12)); bt_salir.setText((String) elementos.get(13)); 
        //bt_save.setText((String) elementos.get(14)); //bt_cancel.setText((String) elementos.get(11));
        bt_find.setText((String) elementos.get(14));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        Limitar();
        CodConsecutivo();
        Cargardatos();
        
        cargatabla = new CargaTablas();
        
        String SQL = "SELECT LIS_CODIGO,LIS_DESCRI FROM DNLISTPRE";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
        
        cargatabla.cargatablas(Tabla,SQL,columnas);
        jCheckLis_Activo.setSelected(true);
        this.setTitle("Tipos de Precios");
        
        if (rs.getRow()==Reg_count){
            this.bt_Siguiente.setEnabled(false);
        }
        if (Reg_count==1){
            this.bt_Siguiente.setEnabled(false);
        }
    }
    
    public void Cargardatos() throws ClassNotFoundException, SQLException{
        String SQL = "SELECT LIS_CODIGO,LIS_DESCRI FROM DNLISTPRE";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNLISTPRE";            
        Reg_count = conet.Count_Reg(SQL_Reg);

        if (Reg_count==1){
            rs.next();
        }else{
            rs.last();
        }
    }
    
    public void mostrar() throws SQLException{
        if (Reg_count==1){
            this.bt_Siguiente.setEnabled(false);
        }else{
            if (rs.getRow()==Reg_count){
                this.bt_Siguiente.setEnabled(false);
            }
            if (rs.getRow()<Reg_count){
                this.bt_Siguiente.setEnabled(true);
            }
            if (rs.getRow()==1){
                this.bt_Atras.setEnabled(false);
            }
            if (rs.getRow()>1){
                this.bt_Atras.setEnabled(true);
            }
        }
        
        if (Reg_count > 0){
            jTxtLis_Codigo.setText(rs.getString("LIS_CODIGO")); jTxtLis_Descripcion.setText(rs.getString("LIS_DESCRI")); 
            if (rs.getInt("LIS_ACTIVO")==1){
                jCheckLis_Activo.setSelected(true);
            }else{
                jCheckLis_Activo.setSelected(false);
            }
        }
    }

    public void Limitar(){
        jTxtLis_Codigo.setDocument(new LimitarCaracteres(jTxtLis_Codigo, 8));
        jTxtLis_Descripcion.setDocument(new LimitarCaracteres(jTxtLis_Descripcion, 40));
    }
    
    public void CodConsecutivo(){
        String Consecutivo = null;

        CodigoConsecutivo codigo = new CodigoConsecutivo();
        //Consecutivo = codigo.CodigoConsecutivo("SELECT CONCAT(REPEAT('0',8-LENGTH(CONVERT(MAX(LIS_CODIGO)+1,CHAR(10)))),CONVERT(MAX(LIS_CODIGO)+1,CHAR(10))) AS CODIGO FROM DNLISTPRE");
        Consecutivo = codigo.CodigoConsecutivo("LIS_CODIGO","DNLISTPRE",8,"");
        this.jTxtLis_Codigo.setText(Consecutivo);

        if (jTxtLis_Codigo.getText().trim().isEmpty()){
            this.jTxtLis_Codigo.setText("00000001");
        }
        
        if (!jTxtLis_Codigo.getText().trim().isEmpty()){
            this.jTxtLis_Descripcion.requestFocus();
            this.jTxtLis_Codigo.disable();
        }
        else{
            this.jTxtLis_Codigo.requestFocus();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtLis_Codigo = new javax.swing.JTextField();
        jTxtLis_Descripcion = new javax.swing.JTextField();
        jCheckLis_Activo = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        bt_agregar = new javax.swing.JButton();
        bt_Modificar = new javax.swing.JButton();
        bt_Eliminar = new javax.swing.JButton();
        bt_Atras = new javax.swing.JButton();
        bt_Siguiente = new javax.swing.JButton();
        bt_salir = new javax.swing.JButton();
        bt_find = new javax.swing.JButton();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel2.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel2.setText("Descripción:");

        jTxtLis_Codigo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtLis_Codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtLis_CodigoActionPerformed(evt);
            }
        });
        jTxtLis_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtLis_CodigoFocusLost(evt);
            }
        });
        jTxtLis_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtLis_CodigoKeyPressed(evt);
            }
        });

        jTxtLis_Descripcion.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtLis_Descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtLis_DescripcionKeyPressed(evt);
            }
        });

        jCheckLis_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckLis_Activo.setText("Activo");
        jCheckLis_Activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckLis_ActivoActionPerformed(evt);
            }
        });

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
                    .addComponent(jTxtLis_Codigo)
                    .addComponent(jTxtLis_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jCheckLis_Activo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckLis_Activo)
                    .addComponent(jLabel1)
                    .addComponent(jTxtLis_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtLis_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        });
        jScrollPane1.setViewportView(Tabla);

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964960_Add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_agregarActionPerformed(evt);
            }
        });
        bt_agregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_agregarKeyPressed(evt);
            }
        });

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964940_Pencil.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ModificarActionPerformed(evt);
            }
        });
        bt_Modificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_ModificarKeyPressed(evt);
            }
        });

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964909_Erase.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_EliminarActionPerformed(evt);
            }
        });
        bt_Eliminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_EliminarKeyPressed(evt);
            }
        });

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964894_Back.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AtrasActionPerformed(evt);
            }
        });

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964902_Forward.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SiguienteActionPerformed(evt);
            }
        });

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964916_Exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1409964921_Find.png"))); // NOI18N
        bt_find.setText("Buscar");

        jMenu3.setText("Sistema");

        jMenuItem3.setText("Salir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar3.add(jMenu3);

        jMenu4.setText("Reportes");
        jMenuBar3.add(jMenu4);

        setJMenuBar(jMenuBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(bt_Eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_Atras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_Siguiente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addComponent(bt_find, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(bt_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_agregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Modificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_find)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Atras, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_Siguiente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_salir)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtLis_CodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtLis_CodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtLis_CodigoActionPerformed

    private void jTxtLis_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtLis_CodigoFocusLost
        // TODO add your handling code here:
        int registros;

        ValidaCodigo consulta = new ValidaCodigo();
        registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNLISTPRE WHERE LIS_CODIGO='"+jTxtLis_Codigo.getText()+"'",false,"");

        if (registros==1){
            jTxtLis_Codigo.requestFocus();
            jTxtLis_Codigo.setText("");
        }
    }//GEN-LAST:event_jTxtLis_CodigoFocusLost

    private void jTxtLis_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtLis_CodigoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtLis_Descripcion.requestFocus();
        }
    }//GEN-LAST:event_jTxtLis_CodigoKeyPressed

    private void jTxtLis_DescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtLis_DescripcionKeyPressed
        // TODO add your handling code here:
        if (this.Bandera==true){
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                bt_Modificar.requestFocus();
            }
        }
        else{
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                bt_agregar.requestFocus();
            }
        }
    }//GEN-LAST:event_jTxtLis_DescripcionKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        // TODO add your handling code here
        TableCellEditor celltable = Tabla.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla != null)
        {
            //JOptionPane.showMessageDialog(null, Tabla.getCellEditor());
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        // TODO add your handling code here:
        String codigo = ""; String descrip = ""; String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            codigo = (String) Tabla.getValueAt(Tabla.getSelectedRow(),0);
            
            String SQL = "SELECT * FROM DNLISTPRE WHERE LIS_CODIGO='"+codigo+"'";
            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListaPrecios.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNLISTPRE WHERE LIS_CODIGO='"+codigo+"'";            
            Reg_count = conet.Count_Reg(SQLReg);
            
            try {
                mostrar();
            } catch (SQLException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.jTxtLis_Codigo.setEnabled(false);
            this.bt_agregar.setEnabled(false);
            this.jTxtLis_Descripcion.requestFocus();
            this.Bandera=true;
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void bt_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_agregarActionPerformed
        // TODO add your handling code here:
        String codigo = null; String descrip = null; String activo = "";
        
        if (jCheckLis_Activo.isSelected()==true){
            activo = "1";
        }
        else{
            activo = "0";
        }

        if (this.Bandera==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(0));
            return;
        }
        if ("".equals(jTxtLis_Codigo.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(1));
            jTxtLis_Codigo.requestFocus();
            return;
        }
        if ("".equals(jTxtLis_Descripcion.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(2));
            jTxtLis_Descripcion.requestFocus();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) this.Tabla.getModel();
        String [] filas={jTxtLis_Codigo.getText(),jTxtLis_Descripcion.getText()};
        model.addRow(filas);

        codigo = jTxtLis_Codigo.getText();
        descrip = jTxtLis_Descripcion.getText();

        jTxtLis_Codigo.setText("");
        jTxtLis_Descripcion.setText("");

        // Llama la Clase Insert para Guardar los Registros
        SQLQuerys insertar = new SQLQuerys();
        insertar.SqlInsert("INSERT INTO DNLISTPRE SET LIS_USER='"+VarGlobales.getIdUser()+"',"+
                           "LIS_MACPC='"+VarGlobales.getMacPc()+"',"+
                           "LIS_CODIGO='"+codigo+"', LIS_DESCRI='"+descrip+"',"+
                           "LIS_ACTIVO="+activo+";");

        jTxtLis_Codigo.requestFocus();
        CodConsecutivo();
        try {
            Cargardatos();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_agregarActionPerformed

    private void bt_agregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_agregarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String codigo = null; String descrip = null; String activo = "";
        
            if (jCheckLis_Activo.isSelected()==true){
                activo = "1";
            }
            else{
                activo = "0";
            }

            if (this.Bandera==true){
                JOptionPane.showMessageDialog(null,(String) Msg.get(0));
                return;
            }
            if ("".equals(jTxtLis_Codigo.getText())){
                JOptionPane.showMessageDialog(null,(String) Msg.get(1));
                jTxtLis_Codigo.requestFocus();
                return;
            }
            if ("".equals(jTxtLis_Descripcion.getText())){
                JOptionPane.showMessageDialog(null,(String) Msg.get(2));
                jTxtLis_Descripcion.requestFocus();
                return;
            }

            DefaultTableModel model = (DefaultTableModel) this.Tabla.getModel();
            String [] filas={jTxtLis_Codigo.getText(),jTxtLis_Descripcion.getText()};
            model.addRow(filas);

            codigo = jTxtLis_Codigo.getText();
            descrip = jTxtLis_Descripcion.getText();

            jTxtLis_Codigo.setText("");
            jTxtLis_Descripcion.setText("");

            // Llama la Clase Insert para Guardar los Registros
            SQLQuerys insertar = new SQLQuerys();
            insertar.SqlInsert("INSERT INTO DNLISTPRE SET LIS_USER='"+VarGlobales.getIdUser()+"',"+
                               "LIS_MACPC='"+VarGlobales.getMacPc()+"',"+
                               "LIS_CODIGO='"+codigo+"', LIS_DESCRI='"+descrip+"',"+
                               "LIS_ACTIVO="+activo+";");

            jTxtLis_Codigo.requestFocus();
            CodConsecutivo();
            try {
                Cargardatos();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_agregarKeyPressed

    private void bt_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ModificarActionPerformed
        // TODO add your handling code here:
        String codigo = null; String descrip = null; String activo = "";
        
        if (jCheckLis_Activo.isSelected()==true){
            activo = "1";
        }
        else{
            activo = "0";
        }

        codigo = jTxtLis_Codigo.getText();
        descrip = jTxtLis_Descripcion.getText();

        jTxtLis_Codigo.setText("");
        jTxtLis_Descripcion.setText("");

        SQLQuerys modificar = new SQLQuerys();
        modificar.SqlUpdate("UPDATE DNLISTPRE SET LIS_USER='"+VarGlobales.getIdUser()+"',"+
                            "LIS_MACPC='"+VarGlobales.getMacPc()+"',"+
                            "LIS_CODIGO='"+codigo+"',LIS_DESCRI='"+descrip+"',"+
                            "LIS_ACTIVO="+activo+" "+
                            "WHERE LIS_CODIGO='"+codigo+"';");

        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT LIS_CODIGO,LIS_DESCRI FROM DNLISTPRE";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(Tabla,SQL,columnas);

        this.jTxtLis_Codigo.setEnabled(true);
        this.bt_agregar.setEnabled(true);
        this.jTxtLis_Codigo.requestFocus();
        this.Bandera=false;
        CodConsecutivo();
    }//GEN-LAST:event_bt_ModificarActionPerformed

    private void bt_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_EliminarActionPerformed
        // TODO add your handling code here:
        String codigo = null;
        int eliminado;

        codigo = jTxtLis_Codigo.getText();
        codigo = (String) Tabla.getValueAt(Tabla.getSelectedRow(),0);

        jTxtLis_Codigo.setText("");
        jTxtLis_Descripcion.setText("");

        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(3)+codigo+"?");
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNLISTPRE WHERE LIS_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNLISTPRE WHERE LIS_CODIGO='"+codigo+"'",true,"");

            //--------------- Remueve la fila de la Tabla ---------------
            if (eliminado == 0){
                DefaultTableModel model = (DefaultTableModel) this.Tabla.getModel();
                int[] rows = Tabla.getSelectedRows();

                for(int i=0;i<rows.length;i++){
                    model.removeRow(rows[i]-i);
                }
            }
            //-----------------------------------------------------------
        }

        this.jTxtLis_Codigo.requestFocus();
        CodConsecutivo();
    }//GEN-LAST:event_bt_EliminarActionPerformed

    private void bt_AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AtrasActionPerformed
        // TODO add your handling code here:
        try {
            rs.previous();
            mostrar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bt_AtrasActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        // TODO add your handling code here:
        this.dispose();  //Codigo para Salir o Cerrar un Formulario
    }//GEN-LAST:event_bt_salirActionPerformed

    private void jCheckLis_ActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckLis_ActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckLis_ActivoActionPerformed

    private void bt_ModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_ModificarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String codigo = null; String descrip = null; String activo = "";
        
            if (jCheckLis_Activo.isSelected()==true){
                activo = "1";
            }
            else{
                activo = "0";
            }

            codigo = jTxtLis_Codigo.getText();
            descrip = jTxtLis_Descripcion.getText();

            jTxtLis_Codigo.setText("");
            jTxtLis_Descripcion.setText("");

            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNLISTPRE SET LIS_USER='"+VarGlobales.getIdUser()+"',"+
                                "LIS_MACPC='"+VarGlobales.getMacPc()+"',"+
                                "LIS_CODIGO='"+codigo+"',LIS_DESCRI='"+descrip+"',"+
                                "LIS_ACTIVO="+activo+" "+
                                "WHERE LIS_CODIGO='"+codigo+"';");

            //---------- Refresca la Tabla para vizualizar los ajustes ----------
            String SQL = "SELECT LIS_CODIGO,LIS_DESCRI FROM DNLISTPRE";
            String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
            //-------------------------------------------------------------------

            cargatabla.cargatablas(Tabla,SQL,columnas);

            this.jTxtLis_Codigo.setEnabled(true);
            this.bt_agregar.setEnabled(true);
            this.jTxtLis_Codigo.requestFocus();
            this.Bandera=false;
            CodConsecutivo();
        }
    }//GEN-LAST:event_bt_ModificarKeyPressed

    private void bt_EliminarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_EliminarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String codigo = null;
            int eliminado;

            codigo = jTxtLis_Codigo.getText();
            codigo = (String) Tabla.getValueAt(Tabla.getSelectedRow(),0);

            jTxtLis_Codigo.setText("");
            jTxtLis_Descripcion.setText("");

            int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(3)+codigo+"?");
            if ( salir == 0) {
                SQLQuerys eliminar = new SQLQuerys();
                eliminar.SqlDelete("DELETE FROM DNLISTPRE WHERE LIS_CODIGO='"+codigo+"'");

                ValidaCodigo consulta = new ValidaCodigo();
                eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNLISTPRE WHERE LIS_CODIGO='"+codigo+"'",true,"");

                //--------------- Remueve la fila de la Tabla ---------------
                if (eliminado == 0){
                    DefaultTableModel model = (DefaultTableModel) this.Tabla.getModel();
                    int[] rows = Tabla.getSelectedRows();

                    for(int i=0;i<rows.length;i++){
                        model.removeRow(rows[i]-i);
                    }
                }
                //-----------------------------------------------------------
            }

            this.jTxtLis_Codigo.requestFocus();
            CodConsecutivo();
        }
    }//GEN-LAST:event_bt_EliminarKeyPressed

    private void bt_SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SiguienteActionPerformed
        // TODO add your handling code here:
        try {
            rs.next();
            mostrar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bt_SiguienteActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_salir;
    private javax.swing.JCheckBox jCheckLis_Activo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTxtLis_Codigo;
    private javax.swing.JTextField jTxtLis_Descripcion;
    // End of variables declaration//GEN-END:variables
}
