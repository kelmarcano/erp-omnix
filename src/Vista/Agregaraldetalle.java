package Vista;

import Pantallas.Documentos;
import static Pantallas.Documentos.bt_detalle;
import static Pantallas.Documentos.cTipoPre;
import static Pantallas.Documentos.jCboInv_Precio;
import static Pantallas.Documentos.jTextPro_Descri;
import static Pantallas.Documentos.jTextInv_Precio;
import static Pantallas.Documentos.jTextInv_Cantid;
import static Pantallas.Documentos.nValorIva;
import static Pantallas.Documentos.cTipIva;
import static Pantallas.Documentos.jCboInv_Unidad;
import static Pantallas.Documentos.jTextInv_CodPro;
import static Pantallas.Documentos.lSinUnd;
import static Vista.Login.Idioma;
import clases.CargaComboBoxs;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.conexion;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kelvin Marcano
 */
public class Agregaraldetalle extends javax.swing.JInternalFrame {
    private SQLSelect Registros;
    CargaTablas cargatabla = null;
    public Object [] opciones={"Aceptar","Cancelar"};
    public boolean lSave=false;
    public DefaultTableModel m;
    static double total1;
    double descuento, subtotal, iva;
    boolean cCxC, cCxP;
    private ResultSet rs_BuscaPro,rs_undmed;
    private conexion conet = new conexion();
    private Vector Msg, elementos, header_table;

    /**
     * Creates new form Agregaraldetalle
     */
    public Agregaraldetalle(boolean Ventas, boolean Compras) {
        initComponents();

        jPanel1.setBackground(Color.decode(colorForm));
        
        bt_enviar.setBackground(Color.decode(colorForm)); bt_enviar.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLeyenda1.setForeground(Color.decode(colorText));
        
        txt_producto.setForeground(Color.decode(colorText)); txt_canti.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLeyenda1.setText((String) elementos.get(2));

        bt_enviar.setText((String) elementos.get(3));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        tabla();

        cCxC=Ventas; cCxP=Compras;
        
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
                txt_producto.requestFocus(); txt_producto.setText("");
            }
        });
//********************************************************************
    }
    
    public void tabla(){
       cargatabla = new CargaTablas();
       String SQL = "";
       if (cTipoPre.isEmpty() || cTipoPre.equals(null)){
           SQL = "SELECT PRO_CODIGO, PRO_DESCRI,(CASE WHEN PRE_MONTO IS NULL THEN 0 ELSE PRE_MONTO END) AS PRE_MONTO FROM DNPRODUCTO "+
                 "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO "+
                 "WHERE PRO_ACTIVO='1' GROUP BY PRO_CODIGO";
       }else{
           SQL = "SELECT PRO_CODIGO, PRO_DESCRI,(CASE WHEN PRE_MONTO IS NULL THEN 0 ELSE PRE_MONTO END) AS PRE_MONTO FROM DNPRODUCTO "+
                 "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_CODLIS='"+cTipoPre+"'"+
                 "WHERE PRO_ACTIVO='1' GROUP BY PRO_CODIGO";
       }

       System.out.println(SQL);
       String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};
        
       cargatabla.cargatablas(tbl_producto,SQL,columnas); 
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
        txt_producto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_canti = new javax.swing.JTextField();
        bt_enviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_producto = new javax.swing.JTable();
        jLeyenda1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Producto");

        txt_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_productoKeyPressed(evt);
            }
        });

        jLabel2.setText("Cantidad");

        txt_canti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cantiActionPerformed(evt);
            }
        });
        txt_canti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantiKeyPressed(evt);
            }
        });

        bt_enviar.setText("Enviar");
        bt_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_enviarActionPerformed(evt);
            }
        });

        tbl_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_productoMouseClicked(evt);
            }
        });
        tbl_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_productoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_producto);

        jLeyenda1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda1.setText("Leyenda 1 de teclas Rapidas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLeyenda1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_canti, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(bt_enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(118, 118, 118)))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txt_canti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_enviar))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLeyenda1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jMenu1.setText("Sistema");

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

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

    private void bt_enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_enviarActionPerformed
        action_bt_enviar();
    }//GEN-LAST:event_bt_enviarActionPerformed

    private void txt_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_productoKeyPressed
        String sql="";
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                if (Integer.valueOf(txt_producto.getText().toString())>0){
                    ActualizaJtable(txt_producto.getText().toString());
                }
            } catch (HeadlessException | NumberFormatException e) {
                String codigo=null, descri=null, rif=null;
                double limit_cre=0, Dcto=0, DiasVen=0;

                descri = txt_producto.getText().toString();

                SQLSelect elemen = new SQLSelect();
                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO, PRO_DESCRI,(CASE WHEN PRE_MONTO IS NULL THEN 0 ELSE PRE_MONTO END) AS PRE_MONTO FROM DNPRODUCTO " +
                                                        "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO " +
                                                        "WHERE PRO_DESCRI LIKE '%"+descri+"%'");

                codigo     = elementos[0][0].toString();
                txt_producto.setText(codigo);
                
                ActualizaJtable(txt_producto.getText().toString());
            }
        }
    }//GEN-LAST:event_txt_productoKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void tbl_productoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_productoMouseClicked
        if (evt.getButton()==1){
            action_tablas(tbl_producto.getSelectedRow());
        }
    }//GEN-LAST:event_tbl_productoMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_formKeyPressed

    private void txt_cantiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantiKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_bt_enviar();
        }
    }//GEN-LAST:event_txt_cantiKeyPressed

    private void txt_cantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cantiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cantiActionPerformed

    private void tbl_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_productoKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            action_tablas(tbl_producto.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            action_tablas(tbl_producto.getSelectedRow());
        }
    }//GEN-LAST:event_tbl_productoKeyReleased

    private void action_bt_enviar(){
        int fsel=-1;
        fsel = tbl_producto.getSelectedRow();
        System.out.println("Pruducto Seleccionado #: "+fsel);

        try {
            System.out.println(fsel);
            String codigo, descri, cant, precio = null;
           
            double calcula=0.0, x=0.0, desc=0.0;
            int canti = 0;

            if(fsel<0) {
                JOptionPane.showMessageDialog(null,(String) Msg.get(0), (String) Msg.get(2), JOptionPane.WARNING_MESSAGE);
            }else if(txt_canti.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(null,(String) Msg.get(1), (String) Msg.get(2), JOptionPane.WARNING_MESSAGE);
                txt_canti.requestFocus();
            }else {
                m = (DefaultTableModel) tbl_producto.getModel();
                codigo = tbl_producto.getValueAt(fsel, 0).toString().trim();
                descri = tbl_producto.getValueAt(fsel, 1).toString().trim();
                
                String sql="";
                System.out.println("Tipo de Precio Filtra: "+cTipoPre);
                if (cTipoPre.isEmpty() || cTipoPre.equals(null)){
                    sql = "SELECT CONCAT('Precio ',PRE_CODLIS,': ',PRE_MONTO) AS DATO1 FROM dnprecio "+
                          "WHERE PRE_CODPRO='"+codigo+"' AND PRE_ACTIVO=1";
                }
                
                DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
                jCboInv_Precio.setModel(mdl);
                System.out.println("Items: "+jCboInv_Precio.getItemCount());
            
                if (jCboInv_Precio.getItemCount()>2){
                    System.out.println("Paso por aqui");
                    jCboInv_Precio.setVisible(true);
                    jCboInv_Precio.setSelectedIndex(1);
                            
                    jTextInv_Precio.setVisible(false);
                    Documentos.lListaP=true;
                }else{
                    precio = tbl_producto.getValueAt(fsel, 2).toString();

                    if (precio.isEmpty() || precio.equals(null)){
                        precio="0.00";
                    }
                }
                
                sql="";
                sql = "SELECT CONCAT(BAR_CODUND,' - ',MED_DESCRI) AS DATO1 FROM DNBARUNDMED "+
                      "INNER JOIN DNUNDMEDIDA ON MED_CODIGO=BAR_CODUND "+
                      "WHERE BAR_CODPRO='"+codigo+"'";
                
                DefaultComboBoxModel und = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
                jCboInv_Unidad.setModel(und);
                
                if (jCboInv_Unidad.getItemCount()==1){
                    und.addElement("UND - Unidad de Medida");
                    jCboInv_Unidad.setModel(und);
                    lSinUnd=false;
                }else{
                    lSinUnd=true;
                }
                jCboInv_Unidad.setSelectedIndex(0);
                
                cant = txt_canti.getText();
                
                jTextInv_CodPro.setText(""+codigo);
                jTextPro_Descri.setText(""+descri);
                jTextInv_Cantid.setText(""+cant);
                jTextInv_Precio.setText(""+precio);
                if (precio.equals("0.00")){
                    jTextInv_Precio.setText(""); jTextInv_Precio.requestFocus();
                }else{
                    bt_detalle.requestFocus();
                }
                
                this.txt_producto.setText(codigo);
                System.out.println("Paso por aqui");
                System.out.println("cTipoPre: "+cTipoPre);
                if (cTipoPre.isEmpty() || cTipoPre.equals(null)){
                    if (cCxC==true){
                        sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                              "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                              "(CASE WHEN TIVA_VALVEN IS NULL THEN '' ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                              "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO "+
                              "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA "+
                              "WHERE PRO_CODIGO ='"+codigo+"'";
                    }else if (cCxP==true){
                        sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                              "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                              "(CASE WHEN TIVA_VALVEN IS NULL THEN '' ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                              "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO "+
                              "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA "+
                              "WHERE PRO_CODIGO ='"+codigo+"'";
                    }
                }else{
                    System.out.println("Paso 2");
                    if (cCxC==true){
                        sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                              "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                              "(CASE WHEN TIVA_VALVEN IS NULL THEN '' ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                              "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_CODLIS='"+cTipoPre+"' "+
                              "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA "+
                              "WHERE PRO_CODIGO ='"+codigo+"'";
                    }else if (cCxP==true){
                        sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                              "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                              "(CASE WHEN TIVA_VALVEN IS NULL THEN '' ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                              "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_CODLIS='"+cTipoPre+"' "+
                              "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA "+
                              "WHERE PRO_CODIGO ='"+codigo+"'";
                    }
                }
                System.out.println(sql);
                try {
                    rs_BuscaPro = conet.consultar(sql);
                    try {
                        cTipIva   = rs_BuscaPro.getString("PRO_TIPIVA").toString().trim();
                        nValorIva = rs_BuscaPro.getInt("VALORIVA");
                    } catch (SQLException ex) {
                        Logger.getLogger(Agregaraldetalle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Agregaraldetalle.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                this.dispose();
            }
        } catch (HeadlessException | NumberFormatException e) {

        }
    }
    
    private void close(){
        java.awt.Toolkit.getDefaultToolkit().beep();
        jTextInv_CodPro.requestFocus();
        dispose();
    }
 
    private void ActualizaJtable(String Codigo){
        int item=0;
        String Ultimo_Codigo = "";
        Ultimo_Codigo= String.format("%010d", Integer.valueOf(Codigo));
        
        for (int i = 0; i < tbl_producto.getRowCount(); i++){
            if(tbl_producto.getValueAt(i, 0).toString().trim().equals(Ultimo_Codigo)){
                item = i;
                txt_producto.setText((String) tbl_producto.getValueAt(item,0).toString().trim());
            }
        }

        tbl_producto.getSelectionModel().setSelectionInterval(item, item);
        txt_canti.requestFocus();
    }
    
    private void action_tablas(int Row){
        String codigo = null;
        String descri = null;

        codigo = (String) tbl_producto.getValueAt(tbl_producto.getSelectedRow(),0).toString().trim();

        txt_producto.setText(codigo);
        txt_canti.requestFocus();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_enviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLeyenda1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_producto;
    private javax.swing.JTextField txt_canti;
    private javax.swing.JTextField txt_producto;
    // End of variables declaration//GEN-END:variables
}