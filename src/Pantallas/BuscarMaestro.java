/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Pantallas;

import static Pantallas.Documentos.jCboEnc_Condic;
import static Pantallas.Documentos.jTextEnc_CodMae;
import static Pantallas.Documentos.jText_Mae_Nombre;
import static Pantallas.Documentos.jTextInv_CodPro;
import static Pantallas.Documentos.nDcto;
import static Pantallas.Documentos.nDiasVen;
import static Pantallas.Documentos.nlimit_cre;
import static Pantallas.Documentos.cTipoPre;
import static Vista.Login.Idioma;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import clases.SQLSelect;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BuscarMaestro extends javax.swing.JInternalFrame {
    public DefaultTableModel m;
    CargaTablas cargatabla = null;
    public Object [] opciones={"Aceptar","Cancelar"};
    public boolean lSave=false;
    private String condic=null;
    private int fsel=-1;
    private Vector Msg, elementos, header_table;

    /**
     * Creates new form BuscarMaestro2
     */
    public BuscarMaestro(boolean Ventas, boolean Compras) {
        initComponents();

        jPanel1.setBackground(Color.decode(colorForm));
        
        jButton1.setBackground(Color.decode(colorForm)); Btn_cancelar.setBackground(Color.decode(colorForm));
        jButton1.setForeground(Color.decode(colorText)); Btn_cancelar.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLeyenda1.setForeground(Color.decode(colorText)); jLeyenda2.setForeground(Color.decode(colorText));
        
        txt_nombre.setForeground(Color.decode(colorText)); txt_rif.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLeyenda1.setText((String) elementos.get(2)); jLeyenda2.setText((String) elementos.get(3));

        jButton1.setText((String) elementos.get(4)); Btn_cancelar.setText((String) elementos.get(5));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        txt_rif.setText(""); 
        txt_nombre.setText("");
        
        tabla(Ventas, Compras);
        
//******************** Codigo para Cerrar el Formulario con la tecla ESC ********************
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");
        
        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                close();
            }
        });
//*******************************************************************************************
        
//******************** Codigo para Teclas Rapidas ********************
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0), "Codigo");
        
        getRootPane().getActionMap().put("Codigo", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                txt_rif.requestFocus();
                txt_rif.setText(""); txt_nombre.setText("");
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), "Nombre");
        
        getRootPane().getActionMap().put("Nombre", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                txt_nombre.requestFocus();
                txt_rif.setText(""); txt_nombre.setText("");
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0), "Seleccion");
        
        getRootPane().getActionMap().put("Seleccion", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                action_select_maestro();
            }
        });
//********************************************************************
    }
    
    public void tabla(boolean Ventas, boolean Compras){
        cargatabla = new CargaTablas();
        
        String SQL="";
        if (Ventas==true){
            SQL = "SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,MAE_CODLIS,MAE_DIAS FROM DNMAESTRO "+
                  "WHERE MAE_CLIENTE='1'";
        }else if (Compras==true){
            SQL = "SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,MAE_CODLIS,MAE_DIAS FROM DNMAESTRO "+
                  "WHERE MAE_PROVEED='1'";
        }
        
        System.out.println(SQL);
        //String[] columnas = {"Rif","Nombre"};
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};
        
        cargatabla.cargatablas(tbl_maestros,SQL,columnas); 
    }
    
    public void tablaBrow(){
        cargatabla = new CargaTablas();
        String SQL = "SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,MAE_CODLIS,MAE_DIAS FROM dnmaestro "+
                     " WHERE MAE_RIF LIKE '%"+txt_rif.getText()+"%' AND MAE_NOMBRE LIKE '%"+txt_nombre.getText()+"%' ";
        
        System.out.println(SQL);
        //String[] columnas = {"Rif","Nombre"};
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};

        cargatabla.cargatablas(tbl_maestros,SQL,columnas); 
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_maestros = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        Btn_cancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLeyenda1 = new javax.swing.JLabel();
        jLeyenda2 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_rif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        salirfactura = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbl_maestros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_maestros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_maestrosMouseClicked(evt);
            }
        });
        tbl_maestros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_maestrosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_maestros);

        jButton1.setText("Agregar Maestro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Btn_cancelar.setText("Cancelar");
        Btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_cancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Rif: ");

        jLeyenda1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda1.setText("Leyenda 1 de teclas Rapidas");

        jLeyenda2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda2.setText("Leyanda 2 de Teclas Rapidas");

        txt_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombreKeyPressed(evt);
            }
        });

        txt_rif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rifKeyPressed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(411, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(5, 5, 5)
                .addComponent(Btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLeyenda2)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(25, 25, 25)
                                    .addComponent(txt_rif, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLeyenda1))
                            .addGap(0, 144, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(275, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(Btn_cancelar))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_rif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLeyenda1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLeyenda2)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        salirfactura.setText("Sistema");
        salirfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfacturaActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        salirfactura.add(jMenuItem1);

        jMenuBar1.add(salirfactura);

        setJMenuBar(jMenuBar1);

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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void salirfacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfacturaActionPerformed
        this.dispose();
        //this.setVisible(false);
    }//GEN-LAST:event_salirfacturaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        action_select_maestro();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            String codigo=null, descri=null, rif=null;
            double limit_cre=0, Dcto=0, DiasVen=0;

            descri = txt_nombre.getText().toString().toUpperCase();

            SQLSelect elemen = new SQLSelect();
            Object elementos[][] = elemen.SQLSelect("SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,"+
                                                    "(CASE WHEN MAE_CODLIS IS NULL THEN '' ELSE MAE_CODLIS END) AS MAE_CODLIS,MAE_DIAS FROM DNMAESTRO "+
                                                    "WHERE MAE_NOMBRE LIKE '%"+descri+"%'");

            codigo     = elementos[0][0].toString().trim();
            rif        = elementos[0][1].toString().trim();
            descri     = elementos[0][2].toString().trim();
            nlimit_cre = elementos[0][3].toString();
            nDcto      = elementos[0][4].toString();
            condic     = elementos[0][5].toString().trim();
            cTipoPre   = elementos[0][6].toString();
            nDiasVen   = elementos[0][7].toString();

            this.txt_rif.setText(codigo.trim());
            this.txt_nombre.setText(descri.trim());

            ActualizaJtable(txt_rif.getText().toString().trim());
        }
    }//GEN-LAST:event_txt_nombreKeyPressed

    private void txt_rifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rifKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            ActualizaJtable(txt_rif.getText().toString().trim());
        }
    }//GEN-LAST:event_txt_rifKeyPressed

    private void Btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_cancelarActionPerformed
        jTextEnc_CodMae.requestFocus();
        this.dispose();
    }//GEN-LAST:event_Btn_cancelarActionPerformed

    private void tbl_maestrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_maestrosMouseClicked
        if (evt.getButton()==1){
            action_tablas(tbl_maestros.getSelectedRow());
        }
    }//GEN-LAST:event_tbl_maestrosMouseClicked

    private void tbl_maestrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_maestrosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            action_tablas(tbl_maestros.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            action_tablas(tbl_maestros.getSelectedRow());
        }
    }//GEN-LAST:event_tbl_maestrosKeyReleased

    private void close(){
        java.awt.Toolkit.getDefaultToolkit().beep();
        jTextEnc_CodMae.requestFocus();
        txt_rif.setText(""); txt_nombre.setText("");
        dispose();
    }
    
    private void ActualizaJtable(String Codigo){
        //String Ultimo_Codigo = String.format("%010d", item);
        int item=0;
        String Ultimo_Codigo = "";
        
        if (txt_rif.getText().length()>=6 && !txt_rif.getText().substring(0, 2).equals("00")){
            if (txt_rif.getText().length()==6 || txt_rif.getText().length()==7){
                Ultimo_Codigo= String.format("%08d", Integer.valueOf(txt_rif.getText().trim()));
            }else if (txt_rif.getText().length()==8){
                Ultimo_Codigo= txt_rif.getText().toString().trim();
            }else if (txt_rif.getText().length()==9){
                Ultimo_Codigo= txt_rif.getText().toString().trim().substring(0, 8)+"-"+txt_rif.getText().toString().trim().substring(8, 9);
            }
        
            for (int i = 0; i < tbl_maestros.getRowCount(); i++){
                if (Ultimo_Codigo.length()==10){
                    if(tbl_maestros.getValueAt(i, 1).toString().trim().substring(2, tbl_maestros.getValueAt(i, 1).toString().trim().length()).equals(Ultimo_Codigo)){
                        item = i;
                        txt_rif.setText((String) tbl_maestros.getValueAt(item,0).toString().trim());
                        txt_nombre.setText((String) tbl_maestros.getValueAt(item,2).toString().trim());
                    }
                }else{
                    if(tbl_maestros.getValueAt(i, 1).toString().trim().substring(2, 10).equals(Ultimo_Codigo)){
                        item = i;
                        txt_rif.setText((String) tbl_maestros.getValueAt(item,0).toString().trim());
                        txt_nombre.setText((String) tbl_maestros.getValueAt(item,2).toString().trim());
                    }
                }
            }
        }else{
            Ultimo_Codigo= String.format("%010d", Integer.valueOf(txt_rif.getText().trim()));

            for (int i = 0; i < tbl_maestros.getRowCount(); i++){
                if(tbl_maestros.getValueAt(i, 0).toString().trim().equals(Ultimo_Codigo)){
                    item = i;
                    txt_rif.setText((String) tbl_maestros.getValueAt(item,0).toString().trim());
                    txt_nombre.setText((String) tbl_maestros.getValueAt(item,2).toString().trim());
                }
            }
        }

        tbl_maestros.getSelectionModel().setSelectionInterval(item, item);
        action_tablas(item);
        tbl_maestros.requestFocus();
        fsel=item;
    }

    private void action_tablas(int Row){
        String codigo=null, descri=null, rif=null;
        double limit_cre=0, Dcto=0, DiasVen=0;

        codigo = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),1).toString().trim();

        SQLSelect elemen = new SQLSelect();
        Object elementos[][] = elemen.SQLSelect("SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,"+
                                                "(CASE WHEN MAE_CODLIS IS NULL THEN '' ELSE MAE_CODLIS END) AS MAE_CODLIS,MAE_DIAS FROM DNMAESTRO "+
                                                "WHERE MAE_RIF='"+codigo+"' OR MAE_NOMBRE='"+descri+"'");

        codigo     = elementos[0][0].toString();
        rif        = elementos[0][1].toString();
        descri     = elementos[0][2].toString();
        nlimit_cre = elementos[0][3].toString();
        nDcto      = elementos[0][4].toString();
        condic     = elementos[0][5].toString();
        cTipoPre   = elementos[0][6].toString();
        nDiasVen   = elementos[0][7].toString();

        this.txt_rif.setText(codigo);
        this.txt_nombre.setText(descri);
//        txt_rif.requestFocus();
    }
    
    private void action_select_maestro(){
        System.out.println("fsel: "+fsel);
        fsel = tbl_maestros.getSelectedRow();
        System.out.println("fsel: "+fsel);
        try {
            System.out.println(fsel);
            String codigo, rif, descri;

            if(fsel<0){
                JOptionPane.showMessageDialog(null,(String) Msg.get(0), (String) Msg.get(1), JOptionPane.WARNING_MESSAGE);
            }else{
                m = (DefaultTableModel) tbl_maestros.getModel();
                codigo = tbl_maestros.getValueAt(fsel, 0).toString().trim();
                rif    = tbl_maestros.getValueAt(fsel, 1).toString().trim();
                descri = tbl_maestros.getValueAt(fsel, 2).toString().trim();
                System.out.println("Condicion: "+condic);

                jTextEnc_CodMae.setText(codigo);
                jText_Mae_Nombre.setText(""+descri);
                jCboEnc_Condic.setSelectedItem(condic);
                jTextInv_CodPro.requestFocus();

                close();
            }
        } catch (HeadlessException | NumberFormatException e) {
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_cancelar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLeyenda1;
    private javax.swing.JLabel jLeyenda2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu salirfactura;
    private javax.swing.JTable tbl_maestros;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_rif;
    // End of variables declaration//GEN-END:variables
}