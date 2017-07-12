package Pantallas;

import Vista.Productos;
import Vista.TipDocumentos;
import Vista.UnidadMedida;
import Vista.Precio;
import Modelos.VariablesGlobales;
import clases.CargaListas;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import Vista.Maestros;
import static Vista.Moneda.jCheckMon_Activo;
import static Vista.Moneda.jDateMon_Fechavig;
import static Vista.Moneda.jTxtMon_Codigo;
import static Vista.Moneda.jTxtMon_Nombre;
import static Vista.Moneda.jTxtMon_Nomenclatura;
import static Vista.Moneda.jTxtMon_Valor;
import static Vista.TipDocumentos.jTextDoc_Codigo;
import clases.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Vista.TipDocumentos.jTextDoc_Descri;
import static Vista.TipDocumentos.jCheckDoc_Activo;
import static Vista.TipDocumentos.jCheckDoc_Logico;
import static Vista.TipDocumentos.jCheckDoc_Fisico;
import static Vista.TipDocumentos.jRadDoc_cxc;
import static Vista.TipDocumentos.jRadDoc_cxp;
import static Vista.TipDocumentos.jRadDoc_Libcom;
import static Vista.TipDocumentos.jRadDoc_Libvta;
import static Vista.TipoContacto.jCheckTco_Activo;
import static Vista.TipoContacto.jTxtTcon_Codigo;
import static Vista.TipoContacto.jTxtTcon_Descripcion;
import static Vista.Productos.jTxtPro_Codigo;
import static Vista.Productos.jTxtPro_Nombre;
import static Vista.Productos.jCheckPro_Activo;
import static Vista.UnidadMedida.jTxtMed_Codigo;
import static Vista.UnidadMedida.jTxtMed_Descri;
import static Vista.UnidadMedida.jChkMed_Activo;
import static clases.ColorApp.colorForm;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author riky1_000
 */
public class Listas extends javax.swing.JFrame {
    private boolean Bandera;
    public static String variable1;
    public static String Tabla;
    private ResultSet rs; ResultSet rs_count;
    private int Reg_count;
    
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public Listas() {
        initComponents();
        
        jPanel1.setBackground(Color.decode(colorForm));
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
         ////****** Crea de Fondo de Pantalla *******/////     
        /* 
        this.setSize(1024, 695);   
        POSPanelFormularios F = new POSPanelFormularios(); // Creamos el objeto F de tipo PanelFondo
        this.add(F,BorderLayout.CENTER);
        this.pack();
        setLocationRelativeTo(null);
        */
        
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
    }

    public String titulo(String TituloForm, String Titulo){
        this.jLabTitulo.setText(Titulo);
        this.setTitle(TituloForm);

        return Titulo;
    }
    
    public boolean CargaListas(String SqlCod,String SqlDescri){
//---------- Carga Lista Codigo ----------
        String sqlCodigo = SqlCod;
        System.out.println(sqlCodigo);
        
        CargaListas codigo = new CargaListas();
        Vector Codigo = codigo.Elementos(sqlCodigo);
        //Vector Codigo = CargaListas.Elementos(sqlCodigo);
        DefaultListModel listCod = new DefaultListModel();

        if (Codigo.isEmpty()==true){
            Bandera=Codigo.isEmpty();
        }
        else{
            for(int i=0;i<Codigo.size();i++){
                listCod.addElement(Codigo.elementAt(i));
            }
        }
        
        this.jListCodigo.setModel(listCod);
//----------------------------------------
        
//---------- Carga Lista Descripcion ----------
        String sqlDescrip = SqlDescri;
        System.out.println(sqlDescrip);

        CargaListas descrip = new CargaListas();
        Vector Descrip = descrip.Elementos(sqlDescrip);
        //Vector Descrip = CargaListas.Elementos(sqlDescrip);
        DefaultListModel listDescri = new DefaultListModel();

        if (Descrip.isEmpty()==true){
            Bandera=Codigo.isEmpty();
            this.dispose();
        }
        else{
            for(int i=0;i<Descrip.size();i++){
                listDescri.addElement(Descrip.elementAt(i));
            }   
        }
        
        this.jListDescrip.setModel(listDescri);
//---------------------------------------------
        return Bandera;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListDescrip = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListCodigo = new javax.swing.JList();
        jLabBarra = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabTitulo.setFont(new java.awt.Font("Roboto Light", 2, 16)); // NOI18N
        jLabTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jListDescrip.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jListDescrip.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListDescrip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListDescripMouseClicked(evt);
            }
        });
        jListDescrip.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListDescripValueChanged(evt);
            }
        });
        jListDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jListDescripKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jListDescrip);

        jListCodigo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jListCodigo.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListCodigoMouseClicked(evt);
            }
        });
        jListCodigo.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListCodigoValueChanged(evt);
            }
        });
        jListCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jListCodigoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jListCodigo);

        jLabBarra.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabBarra.setText("Código                              Descripción");
        jLabBarra.setToolTipText("");
        jLabBarra.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabBarra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabBarra.getAccessibleContext().setAccessibleName(" Código                              Descripción");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabTitulo)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListCodigoValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListCodigoValueChanged
        // TODO add your handling code here:
        int x = jListCodigo.getSelectedIndex();
        
        jListDescrip.setSelectedIndex(x);
    }//GEN-LAST:event_jListCodigoValueChanged

    private void jListDescripValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListDescripValueChanged
        // TODO add your handling code here:
        int x = jListDescrip.getSelectedIndex();
        
        jListCodigo.setSelectedIndex(x);
    }//GEN-LAST:event_jListDescripValueChanged

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_formKeyPressed

    private void jListCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCodigoMouseClicked
        if (evt.getClickCount() == 2){
            variable1 = jListCodigo.getSelectedValue().toString();
            
            if (Tabla=="DNMAESTRO"){
                Productos.jTxtPro_CodMae.setText(variable1);
                Productos.jTxtPro_Descri.requestFocus();
            }
            if (Tabla=="DNPRODUCTO"){
                Precio.jTxtPre_CodProduc.setText(variable1);
                Precio.jBtnBusca_TipPrecio.requestFocus();
            }
            if (Tabla=="DNLISTPRE"){
                Precio.jTxtPre_Codlis.setText(variable1);
                Precio.jTxtPre_Monto.requestFocus();
            }
            if (Tabla=="DNUNDMEDIDA"){
                Precio.jTxtPre_Coduni.setText(variable1);
                Precio.jBtnBusca_UniMed.requestFocus();
            }
            if (Tabla=="DNTIPOMAESTRO"){
                Maestros.jTxtMae_Codtip.setText(variable1);
                Maestros.jTxtMae_Limite.requestFocus();
            }
            if (Tabla=="DNACTIVIDAD_ECO"){
                Maestros.jTxtMae_Activi.setText(variable1);
                Maestros.jCmbMae_Reside.requestFocus();
            }
            if (Tabla=="DNMONEDA"){
                Maestros.jTxtMae_Codmon.setText(variable1);
            }
            if (Tabla=="DNDOCUMENTOS"){
                String SQL = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+variable1+"'";
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarDoc();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
            
            if (Tabla=="DNTIPCONTACTO"){
                String SQL = "SELECT * FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarContacto();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Tabla=="DNPRODUCT"){
                String SQL = "SELECT * FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarProducto();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Tabla=="DNUNDMED"){
                String SQL = "SELECT * FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarUndMedida();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Tabla=="DNMONE"){
                String SQL = "SELECT * FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarMoneda();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.dispose();
        }
    }//GEN-LAST:event_jListCodigoMouseClicked

    private void jListDescripMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListDescripMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2){
            variable1 = jListCodigo.getSelectedValue().toString();
            
            if (Tabla=="DNMAESTRO"){
                Productos.jTxtPro_CodMae.setText(variable1);
                Productos.jTxtPro_Descri.requestFocus();
            }
            if (Tabla=="DNPRODUCTO"){
                Precio.jTxtPre_CodProduc.setText(variable1);
                Precio.jBtnBusca_TipPrecio.requestFocus();
            }
            if (Tabla=="DNLISTPRE"){
                Precio.jTxtPre_Codlis.setText(variable1);
                Precio.jTxtPre_Monto.requestFocus();
            }
            if (Tabla=="DNUNDMEDIDA"){
                Precio.jTxtPre_Coduni.setText(variable1);
                Precio.jBtnBusca_UniMed.requestFocus();
            }
            if (Tabla=="DNTIPOMAESTRO"){
                Maestros.jTxtMae_Codtip.setText(variable1);
                Maestros.jTxtMae_Limite.requestFocus();
            }
            if (Tabla=="DNACTIVIDAD_ECO"){
                Maestros.jTxtMae_Activi.setText(variable1);
                Maestros.jCmbMae_Reside.requestFocus();
            }
            if (Tabla=="DNMONEDA"){
                Maestros.jTxtMae_Codmon.setText(variable1);
            }
            if (Tabla=="DNDOCUMENTOS"){
                String SQL = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarDoc();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            if (Tabla=="DNTIPCONTACTO"){
                String SQL = "SELECT * FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarContacto();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Tabla=="DNPRODUCT"){
                String SQL = "SELECT * FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarProducto();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Tabla=="DNUNDMED"){
                String SQL = "SELECT * FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarUndMedida();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Tabla=="DNMONE"){
                String SQL = "SELECT * FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+variable1+"'";
                
                try{
                    rs = conet.consultar(SQL);
                }catch (ClassNotFoundException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }  
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+variable1+"'";            
                Reg_count = conet.Count_Reg(SQLReg);
                
                try {
                    CargarMoneda();
                } catch (SQLException ex) {
                    Logger.getLogger(Listas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            this.dispose();
        }
    }//GEN-LAST:event_jListDescripMouseClicked

    private void jListCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListCodigoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            variable1 = jListCodigo.getSelectedValue().toString();
            
            if (Tabla=="DNMAESTRO"){
                Productos.jTxtPro_CodMae.setText(variable1);
                Productos.jTxtPro_Descri.requestFocus();
            }
            if (Tabla=="DNPRODUCTO"){
                Precio.jTxtPre_CodProduc.setText(variable1);
                Precio.jBtnBusca_TipPrecio.requestFocus();
            }
            if (Tabla=="DNLISTPRE"){
                Precio.jTxtPre_Codlis.setText(variable1);
                Precio.jTxtPre_Monto.requestFocus();
            }
            if (Tabla=="DNUNDMEDIDA"){
                Precio.jTxtPre_Coduni.setText(variable1);
                Precio.jBtnBusca_UniMed.requestFocus();
            }
            if (Tabla=="DNTIPOMAESTRO"){
                Maestros.jTxtMae_Codtip.setText(variable1);
                Maestros.jTxtMae_Limite.requestFocus();
            }
            if (Tabla=="DNACTIVIDAD_ECO"){
                Maestros.jTxtMae_Activi.setText(variable1);
                Maestros.jCmbMae_Reside.requestFocus();
            }
            if (Tabla=="DNMONEDA"){
                Maestros.jTxtMae_Codmon.setText(variable1);
            }
            
            this.dispose();
        }
    }//GEN-LAST:event_jListCodigoKeyPressed

    private void jListDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListDescripKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            variable1 = jListCodigo.getSelectedValue().toString();
            
            if (Tabla=="DNMAESTRO"){
                Productos.jTxtPro_CodMae.setText(variable1);
                Productos.jTxtPro_Descri.requestFocus();
            }
            if (Tabla=="DNPRODUCTO"){
                Precio.jTxtPre_CodProduc.setText(variable1);
                Precio.jBtnBusca_TipPrecio.requestFocus();
            }
            if (Tabla=="DNLISTPRE"){
                Precio.jTxtPre_Codlis.setText(variable1);
                Precio.jTxtPre_Monto.requestFocus();
            }
            if (Tabla=="DNUNDMEDIDA"){
                Precio.jTxtPre_Coduni.setText(variable1);
                Precio.jBtnBusca_UniMed.requestFocus();
            }
            if (Tabla=="DNTIPOMAESTRO"){
                Maestros.jTxtMae_Codtip.setText(variable1);
                Maestros.jTxtMae_Limite.requestFocus();
            }
            if (Tabla=="DNACTIVIDAD_ECO"){
                Maestros.jTxtMae_Activi.setText(variable1);
                Maestros.jCmbMae_Reside.requestFocus();
            }
            if (Tabla=="DNMONEDA"){
                Maestros.jTxtMae_Codmon.setText(variable1);
            }
            
            this.dispose();
        }
    }//GEN-LAST:event_jListDescripKeyPressed

    private void CargarDoc() throws SQLException{
        if (Reg_count > 0){
            jTextDoc_Codigo.setText(rs.getString("DOC_CODIGO")); 
            jTextDoc_Descri.setText(rs.getString("DOC_DESCRI")); 
            TipDocumentos.Bandera=true;
                    
            if (rs.getInt("DOC_ACTIVO")==1){
               jCheckDoc_Activo.setSelected(true);
            }else{
               jCheckDoc_Activo.setSelected(false);
            }
            if (rs.getInt("DOC_CXC")==1){
               jRadDoc_cxc.setSelected(true);
               jRadDoc_cxp.setSelected(false);
               jRadDoc_cxp.setEnabled(false);
               jRadDoc_Libcom.setSelected(false);
               jRadDoc_Libcom.setEnabled(false);
            }else{
               jRadDoc_cxc.setSelected(false);
            }
            if (rs.getInt("DOC_CXP")==1){
               jRadDoc_cxp.setSelected(true);
               jRadDoc_cxc.setSelected(false);
               jRadDoc_cxc.setEnabled(false);
               jRadDoc_Libvta.setSelected(false);
               jRadDoc_Libvta.setEnabled(false);
            }else{
               jRadDoc_cxp.setSelected(false);
            }
            if (rs.getInt("DOC_LIBCOM")==1){
               jRadDoc_Libcom.setSelected(true);
               jRadDoc_cxp.setSelected(true);
               jRadDoc_Libvta.setSelected(false);
               jRadDoc_Libvta.setEnabled(false);
               jRadDoc_cxc.setSelected(false);
               jRadDoc_cxc.setEnabled(false);
            }else{
               jRadDoc_Libcom.setSelected(false);
            }
            if (rs.getInt("DOC_LIBVTA")==1){
               jRadDoc_Libvta.setSelected(true);
               jRadDoc_cxc.setSelected(true);
               jRadDoc_Libcom.setSelected(false);
               jRadDoc_Libcom.setEnabled(false);
               jRadDoc_cxp.setSelected(false);
               jRadDoc_cxp.setEnabled(false);
            }else{
               jRadDoc_Libvta.setSelected(false);
            }
            if (rs.getInt("DOC_FISICO")==1){
               jCheckDoc_Fisico.setSelected(true);
            }else{
               jCheckDoc_Fisico.setSelected(false);
            }
            if (rs.getInt("DOC_LOGICO")==1){
               jCheckDoc_Logico.setSelected(true);
            }else{
               jCheckDoc_Logico.setSelected(false);
            }
        }
    }
    
    private void CargarContacto() throws SQLException{
        if (Reg_count>0){
           jTxtTcon_Codigo.setText(rs.getString("TCON_CODIGO")); 
           jTxtTcon_Descripcion.setText(rs.getString("TCON_DESCRI")); 
           
           if (rs.getInt("TCON_ACTIVO")==1){
              jCheckTco_Activo.setSelected(true);
           }else{
              jCheckTco_Activo.setSelected(false);
           } 
        }
    }
    
    private void CargarProducto() throws SQLException{
        if (Reg_count>0){
           jTxtPro_Codigo.setText(rs.getString("PRO_CODIGO")); 
           jTxtPro_Nombre.setText(rs.getString("PRO_DESCRI")); 
           Productos.Bandera=true;
           
           if (rs.getInt("PRO_ACTIVO")==1){
              jCheckPro_Activo.setSelected(true);
           }else{
              jCheckPro_Activo.setSelected(false);
           } 
        }
    }
    
    private void CargarUndMedida() throws SQLException{
        if (Reg_count>0){
           jTxtMed_Codigo.setText(rs.getString("MED_CODIGO")); 
           jTxtMed_Descri.setText(rs.getString("MED_DESCRI")); 
           UnidadMedida.Bandera=true;
           
           if (rs.getInt("MED_ACTIVO")==1){
              jChkMed_Activo.setSelected(true);
           }else{
              jChkMed_Activo.setSelected(false);
           } 
        }
    }
    
    private void CargarMoneda() throws SQLException{
        if (Reg_count > 0){
            jTxtMon_Codigo.setText(rs.getString("MON_CODIGO")); 
            jTxtMon_Nombre.setText(rs.getString("MON_NOMBRE"));
            
            if (rs.getInt("MON_ACTIVO")==1){
                jCheckMon_Activo.setSelected(true);
            }else{
                jCheckMon_Activo.setSelected(false);
            }
            
            jTxtMon_Nomenclatura.setText(rs.getString("MON_NOMENC"));
            jTxtMon_Valor.setText(rs.getString("MON_VALOR").replace(".", ","));
            
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fch = null;

            try {
                fch = sdf.parse(rs.getString("MON_FCHVIG"));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            jDateMon_Fechavig.setDate(fch);
            //-----------------------------------------------------------
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
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Listas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Listas().setVisible(true);
            }
        });
    }
    
    private void close(){
        java.awt.Toolkit.getDefaultToolkit().beep();
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabBarra;
    private javax.swing.JLabel jLabTitulo;
    private javax.swing.JList jListCodigo;
    private javax.swing.JList jListDescrip;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
