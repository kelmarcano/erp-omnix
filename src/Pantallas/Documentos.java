package Pantallas;

import Vista.Agregaraldetalle;
import Vista.Maestros;
import static Vista.Login.Idioma;
import static Pantallas.principal.escritorio;
import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import clases.CargaComboBoxs;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import static clases.ReporteJas.CONEXION;
import static clases.ReporteJas.DRIVER;
import static clases.ReporteJas.PASSWORD;
import static clases.ReporteJas.RUTA;
import static clases.ReporteJas.USER;
import clases.SQLQuerys;
import clases.conexion;
import clases.control_existencias;
import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Documentos extends javax.swing.JInternalFrame {
    private Object [][] datostabla;
    control_existencias con;
    static double total1=0;
    private double descuento=0, subtotal=0, calculariva=0, iva1=0;
    private DefaultTableModel m;
    private String cTipDoc="", cTitulo="", cNumero="", ultimo_reg="", UltimoRegBusq="", lInvAct="";
    private boolean cCxC, cCxP, lLibVta, lLibCom, lCalIva, lRetIva, lRetIslr, lFisico, lLogico;
    private boolean lExitDoc, lTipDoc, lnext, lforward, lAgregar, lModificar, lBuscaDoc=false, lCantida=false, lOpc=false;
    private ResultSet rs_doc, rs_encab, rs_detalle, rs_BuscaMae, rs_BuscaPro, rs_undmed;
    private conexion conet = new conexion();
    private CargaTablas cargatabla = null;
    private int nItems, nDoc=0;
    private Vector Msg, elementos, header_table;
    
    public static String nlimit_cre="", nDcto="", nDiasVen="", cTipIva="", cTipoPre="",SigUnd="";
    public static int nValorIva=0;
    public static boolean lListaP=false, lSinUnd=true;
    public static double CanUnd = 0, PesUnd=0, VolUnd=0;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public Documentos(String TipDoc, String Titulo) {
    //public Documentos() {
        initComponents();
        validar_nroControl(TipDoc);
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        jPanel3.setBackground(Color.decode(colorForm)); jPanel4.setBackground(Color.decode(colorForm));
        
        bt_agregar.setBackground(Color.decode(colorForm)); bt_Modificar.setBackground(Color.decode(colorForm));
        bt_save.setBackground(Color.decode(colorForm)); bt_detalle.setBackground(Color.decode(colorForm));
        bt_cancel.setBackground(Color.decode(colorForm)); bt_find.setBackground(Color.decode(colorForm));
        bt_Atras.setBackground(Color.decode(colorForm)); bt_Siguiente.setBackground(Color.decode(colorForm));
        bt_salir.setBackground(Color.decode(colorForm)); bt_elimina_detalle.setBackground(Color.decode(colorForm));
        bt_agregarproducto.setBackground(Color.decode(colorForm)); bt_agregarproveedor.setBackground(Color.decode(colorForm));
        
        bt_agregar.setForeground(Color.decode(colorText)); bt_Modificar.setForeground(Color.decode(colorText));
        bt_save.setForeground(Color.decode(colorText)); bt_detalle.setForeground(Color.decode(colorText));
        bt_cancel.setForeground(Color.decode(colorText)); bt_find.setForeground(Color.decode(colorText));
        bt_Atras.setForeground(Color.decode(colorText)); bt_Siguiente.setForeground(Color.decode(colorText));
        bt_salir.setForeground(Color.decode(colorText)); bt_elimina_detalle.setForeground(Color.decode(colorText));
        bt_agregarproducto.setForeground(Color.decode(colorText)); bt_agregarproveedor.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText)); 
        jLabel5.setForeground(Color.decode(colorText)); jLabel6.setForeground(Color.decode(colorText));
        jLabel7.setForeground(Color.decode(colorText)); jLabel8.setForeground(Color.decode(colorText)); 
        jLabel9.setForeground(Color.decode(colorText)); jLabel10.setForeground(Color.decode(colorText)); 
        jLabel11.setForeground(Color.decode(colorText)); jLabel12.setForeground(Color.decode(colorText)); 
        jLabel13.setForeground(Color.decode(colorText)); jLabel14.setForeground(Color.decode(colorText));
        jLabel15.setForeground(Color.decode(colorText)); jLabel16.setForeground(Color.decode(colorText));
        jLeyenda1.setForeground(Color.decode(colorText)); jLeyenda2.setForeground(Color.decode(colorText));
        lbl_nroControl.setForeground(Color.decode(colorText));
        
        
        jCboEnc_Codven.setForeground(Color.decode(colorText)); jCboEnc_Condic.setForeground(Color.decode(colorText));
        jCboInv_Precio.setForeground(Color.decode(colorText)); jCboInv_Unidad.setForeground(Color.decode(colorText));
        
        jTextEnc_Base.setForeground(Color.decode(colorText)); jTextEnc_CodMae.setForeground(Color.decode(colorText));
        jTextEnc_Codigo.setForeground(Color.decode(colorText)); jTextEnc_Iva.setForeground(Color.decode(colorText));
        jTextEnc_Monto.setForeground(Color.decode(colorText)); jTextInv_Cantid.setForeground(Color.decode(colorText));
        jTextInv_CodPro.setForeground(Color.decode(colorText)); jTextInv_Precio.setForeground(Color.decode(colorText));
        jTextInv_Total.setForeground(Color.decode(colorText)); jTextPro_Descri.setForeground(Color.decode(colorText));
        jText_Mae_Nombre.setForeground(Color.decode(colorText));
        txt_stock.setForeground(Color.decode(colorText)); txt_buscar.setForeground(Color.decode(colorText));
        txt_descuento.setForeground(Color.decode(colorText));
        jDateEnc_Fecha.setForeground(Color.decode(colorText));
        jTextEnc_CodigoCtrl.setForeground(Color.decode(colorText));
        
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
        jLabel7.setText((String) elementos.get(7)); jLabel8.setText((String) elementos.get(8));
        jLabel9.setText((String) elementos.get(9)); jLabel10.setText((String) elementos.get(10));
        jLabel11.setText((String) elementos.get(11)); jLabel12.setText((String) elementos.get(12));
        jLabel13.setText((String) elementos.get(13)); jLabel14.setText((String) elementos.get(14));
        jLabel15.setText((String) elementos.get(15)); jLabel16.setText((String) elementos.get(16));

        bt_agregarproducto.setText((String) elementos.get(28));
        bt_detalle.setText((String) elementos.get(29)); bt_elimina_detalle.setText((String) elementos.get(30));
        
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder((String) elementos.get(31)));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        this.cTipDoc=TipDoc; this.cTitulo=Titulo;
        this.setTitle(cTitulo);
        
        this.lTipDoc=validad_documento(this.cTipDoc);
        if (this.lTipDoc==false){
            JOptionPane.showMessageDialog(null, (String) Msg.get(0)+cTipDoc+(String) Msg.get(1), (String) Msg.get(2), JOptionPane.WARNING_MESSAGE);
        }

	if (cCxC==true){
            this.jLabel6.setText((String) elementos.get(5));
        }else if (cCxP==true){
            this.jLabel6.setText((String) elementos.get(6));
        }

//******************** Codigo para Teclas Rapidas ********************
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0), "Maestros");
        
        getRootPane().getActionMap().put("Maestros", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                if (lOpc==true){
                    BuscaMaestros();
                }else{
                    action_bt_agregar();
                }
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0), "Productos");
        
        getRootPane().getActionMap().put("Productos", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                if (lOpc==true){
                    BuscaProductos();
                }else{
                    String numfact=(jTextEnc_Codigo.getText().toString());
                    startReport(numfact);
                }
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0), "Modificar");
        
        getRootPane().getActionMap().put("Modificar", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                if (lOpc==true){

                }else{
                    action_bt_modificar();
                }
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), "Grabar");
        
        getRootPane().getActionMap().put("Grabar", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                if (lOpc==true){
                    action_bt_save();
                }
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancelar");
        
        getRootPane().getActionMap().put("Cancelar", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                if (lOpc==true){
                    action_bt_cancel();
                }else{
                    dispose();
                }
            }
        });
//*******************************************************************************************
        
        total1=0; descuento=0; subtotal=0.0; iva1=0;
        
        correlativo();
        UltimoRegBusq= String.format("%010d", Integer.valueOf(this.jTextEnc_Codigo.getText())-1);

        Hilo_Encabezado Encabezado = new Hilo_Encabezado();
        Encabezado.start();
        Hilo_Detalle Detalle = new Hilo_Detalle();
        Detalle.start();
        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (detalle_factura.getRowCount()==0){
            comboforma();
            combovendedor();
            activar("Inicializa");

            lAgregar=true;
            this.jTextEnc_CodMae.requestFocus();
        }else{
            inicio();
            comboforma();
            combovendedor();
            posicion_botones_1();
        }
        
        
    }
    
    public void validar_nroControl(String TipDoc){
        /**Metodo prara verificar el numero de control motivado a que hay un solo 
         * formulario para ambos tipos de documentos compras y ventas */
        boolean lDoc=false;
        jTextEnc_CodigoCtrl.setVisible(false);
        lbl_nroControl.setVisible(false);
        
        String sql = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+TipDoc+"'";
        
        System.out.println("Verificar nro control"+sql);
        try{
            rs_doc = conet.consultar(sql);
            
            if(rs_doc.getBoolean("DOC_CXC")){
                
                jTextEnc_CodigoCtrl.setVisible(true);
                lbl_nroControl.setVisible(true);
                System.out.println("text true");
        
               lDoc=true;
            }   else{
                jTextEnc_CodigoCtrl.setVisible(false);
                lbl_nroControl.setVisible(false);
                System.out.println("text false");
        
                lDoc=false;
            }
            
        }catch (Exception e){
            System.out.println("Problemas para verificar el numero de control");
        }
        
        
    }

    public void inicio(){
        lOpc=false;
        
        jTextEnc_Codigo.setEnabled(false); jCboEnc_Condic.setEnabled(false); jCboEnc_Codven.setEnabled(false);
        jCboInv_Precio.setVisible(false); jDateEnc_Fecha.setEnabled(false); jCboInv_Unidad.setEnabled(false);
        
        jText_Mae_Nombre.setEnabled(false); jText_Mae_Nombre.setText("");
        jTextEnc_CodMae.setEnabled(false); jTextEnc_CodMae.setText("");
        jTextInv_CodPro.setEnabled(false); jTextInv_CodPro.setText("");
        jTextPro_Descri.setEnabled(false); jTextPro_Descri.setText("");
        txt_stock.setEnabled(false); txt_stock.setText("");
        jTextInv_Precio.setEnabled(false); jTextInv_Precio.setText("0.00");
        jTextEnc_Base.setText("0.00"); jTextEnc_Iva.setText("0.00"); jTextEnc_Monto.setText("0.00");
        
        jTextInv_Cantid.setEnabled(false); txt_descuento.setEnabled(false); jTextInv_Total.setEnabled(false);
        jTextEnc_Base.setEnabled(false); jTextEnc_Iva.setEnabled(false); jTextEnc_Monto.setEnabled(false);
        txt_buscar.setEnabled(true);
        
        if (lTipDoc==false){
            bt_agregar.setEnabled(false);
        }else{
            bt_agregar.setEnabled(true);
        }
        
        bt_save.setEnabled(false); bt_save.setVisible(false); bt_cancel.setVisible(false);
        bt_agregarproveedor.setEnabled(false); bt_agregarproducto.setEnabled(false); bt_imprimir.setVisible(true);
        bt_imprimir.setEnabled(true);
        
        correlativo();
        UltimoRegBusq= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())-1);
        
        Hilo_Encabezado Encabezado = new Hilo_Encabezado();
        Encabezado.start();
        Hilo_Detalle Detalle = new Hilo_Detalle();
        Detalle.start();
    }
    
    public void activar(String accion){
        if (accion=="Inicializa"){
            lOpc=true;
            
            bt_agregar.setEnabled(false); bt_save.setEnabled(true); bt_save.setVisible(true);
            bt_cancel.setVisible(true); bt_agregarproveedor.setEnabled(true); bt_agregarproducto.setEnabled(true);
        
            jTextEnc_Codigo.setEnabled(true); jCboEnc_Condic.setEnabled(true); jCboEnc_Condic.setSelectedIndex(0);
            jCboEnc_Codven.setEnabled(true); jCboEnc_Codven.setSelectedIndex(0); jCboInv_Unidad.setEnabled(true);
            
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = new Date();

            Date fch = null;
            String Fch = sdf.format(fecha);
            try {
                fch = sdf.parse(Fch);
            } catch (ParseException ex) {
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            jDateEnc_Fecha.setDate(fch);
            jDateEnc_Fecha.setEnabled(true);
            //-----------------------------------------------------------
        
            jTextEnc_CodMae.setEnabled(true); jTextEnc_CodMae.requestFocus(); //jText_Mae_Nombre.setEnabled(true);
            jTextInv_CodPro.setEnabled(true); jTextPro_Descri.setEnabled(true); jTextInv_Precio.setEnabled(true);
            jTextInv_Cantid.setEnabled(true); txt_descuento.setEnabled(true); jTextInv_Total.setEnabled(true);
            jTextEnc_Base.setEnabled(false); jTextEnc_Iva.setEnabled(false); jTextEnc_Monto.setEnabled(false);
            txt_buscar.setEnabled(false);
        
            jTextInv_Precio.setText("0.00"); jTextInv_Cantid.setText("0"); txt_descuento.setText("0");
            jTextInv_Total.setText("0"); txt_stock.setText("0"); jTextEnc_CodMae.setText("");
            jText_Mae_Nombre.setText(""); jTextEnc_Base.setText("0.00"); jTextEnc_Iva.setText("0.00");
            jTextEnc_Monto.setText("0.00");
            
            this.jTextEnc_CodMae.requestFocus();
            posicion_botones_2();
        }else if (accion=="Limpiar"){
            jTextInv_CodPro.setText(""); jTextPro_Descri.setText(""); txt_stock.setText("0");
            jTextInv_Precio.setText("0.00"); jTextInv_Cantid.setText("0"); txt_descuento.setText("0");
            jTextInv_Total.setText("0.00");
            
            jCboInv_Precio.setVisible(false);
            jTextInv_Precio.setVisible(true);
            
            lListaP=false;

            if (detalle_factura.getRowCount()>0){
                jTextEnc_CodMae.setEnabled(false);
                bt_agregarproveedor.setEnabled(false);
            }
            
            jTextInv_CodPro.requestFocus();
        }else if (accion=="Modificar"){
            lOpc=true;
            
            jTextInv_Precio.setText("0.00"); jTextInv_Cantid.setText("0"); txt_descuento.setText("0");
            jTextInv_Total.setText("0.00"); txt_stock.setText("0"); jCboInv_Unidad.setEnabled(true);
            
            bt_agregar.setEnabled(false); bt_save.setEnabled(true); bt_save.setVisible(true);
            bt_cancel.setVisible(true);
            
            posicion_botones_2();
            jTextInv_CodPro.requestFocus();
        }
    }
    
    private void Limpiar(){
        jTextEnc_Base.setText("0.00"); jTextEnc_CodMae.setText(""); jTextEnc_Codigo.setText("");
        jTextEnc_Iva.setText("0.00"); jTextEnc_Monto.setText("0.00"); jText_Mae_Nombre.setText("");
        
        jTextInv_CodPro.setText(""); jTextPro_Descri.setText(""); txt_stock.setText("0");
        jTextInv_Precio.setText("0.00"); jTextInv_Cantid.setText("0"); txt_descuento.setText("0");
        jTextInv_Total.setText("0.00");
    }
    
    public void posicion_botones_1(){
        bt_Atras.setVisible(true); bt_Siguiente.setVisible(true); bt_salir.setVisible(true);
        bt_imprimir.setVisible(true); bt_find.setVisible(true); bt_Modificar.setVisible(true);

        bt_Atras.reshape(75, 430, 70, 44); jPanel1.add(bt_Atras);
        bt_Siguiente.reshape(145, 430, 70, 44); jPanel1.add(bt_Siguiente);
        bt_Modificar.reshape(215, 430, 70, 44); jPanel1.add(bt_Modificar);
        bt_imprimir.reshape(285, 430, 70, 44); jPanel1.add(bt_imprimir);
        bt_find.reshape(355, 430, 70, 44); jPanel1.add(bt_find);
        bt_salir.reshape(425, 430, 70, 44); jPanel1.add(bt_salir);
        
        //jLeyenda1.setText("F2: Incluir; F3: Imprimir; F4: Modificar; F5: Buscar; ESC: Salir");
        jLeyenda1.setText((String) elementos.get(19));
        
        txt_buscar.setEnabled(true);
    }
    
    public void posicion_botones_2(){
        bt_Atras.setVisible(false); bt_Siguiente.setVisible(false); bt_salir.setVisible(false);
        bt_imprimir.setVisible(false); bt_find.setVisible(false); bt_Modificar.setVisible(false);
        
        bt_cancel.reshape(145, 430, 70, 44); jPanel1.add(bt_cancel);
        
        //jLeyenda1.setText("F2: Busqueda de Maestros; F3: Busqueda de Productos; F5: Guardar el Documento"); jLeyenda1.setVisible(true); 
        //jLeyenda2.setText("ESC: Cancelar");
        jLeyenda1.setText((String) elementos.get(17));
        jLeyenda2.setText((String) elementos.get(18)); jLeyenda2.setVisible(true);
        
        txt_buscar.setEnabled(false);
    }

    public boolean validad_documento(String TipoDoc){
        boolean lDoc=false;
        String sql = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+TipoDoc+"'";
        
        try {
            rs_doc = conet.consultar(sql);
            try {
                if(rs_doc.getRow()>0){
                    cCxC=rs_doc.getBoolean("DOC_CXC");
                    cCxP=rs_doc.getBoolean("DOC_CXP");
                    lInvAct=rs_doc.getString("DOC_INVACT");
                    lLibVta=rs_doc.getBoolean("DOC_LIBVTA");
                    lLibCom=rs_doc.getBoolean("DOC_LIBCOM");
                    lCalIva=rs_doc.getBoolean("DOC_IVA");
                    lRetIva=rs_doc.getBoolean("DOC_RETIVA");
                    lRetIslr=rs_doc.getBoolean("DOC_RETISLR");
                    lFisico=rs_doc.getBoolean("DOC_FISICO");
                    lLogico=rs_doc.getBoolean("DOC_LOGICO");
                    
                    lDoc = true;
                }else{
                    lDoc = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lDoc;
    }
    
    public void comboforma() {
        String sql = "SELECT CDI_DESCRI AS DATO1 FROM dncondipago WHERE CDI_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCboEnc_Condic.setModel(mdl);
    } 
    
    public void combovendedor() {
        String sql = "SELECT CONCAT(VEN_CODIGO,' - ',VEN_NOMBRE) AS DATO1 FROM dnvendedor WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCboEnc_Codven.setModel(mdl);
    } 
    
    public void correlativo(){
        String Consecutivo = null;
        
        CodigoConsecutivo codigo = new CodigoConsecutivo();
//        Consecutivo = codigo.CodigoConsecutivo("SELECT CONCAT(REPEAT('0',10-LENGTH(CONVERT(MAX(ENC_CODIGO)+1,CHAR(10)))),CONVERT(MAX(ENC_CODIGO)+1,CHAR(10))) "+
//                                               "AS CODIGO FROM DNENCABEZADO WHERE ENC_CODDOC='"+cTipDoc+"'");
        Consecutivo = codigo.CodigoConsecutivo("ENC_CODIGO","DNENCABEZADO",10,"WHERE ENC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ENC_CODDOC='"+cTipDoc+"'");
        
        if (Consecutivo==null || Consecutivo.isEmpty()) {
            Consecutivo="0000000001";
        }

        this.jTextEnc_Codigo.setText(Consecutivo);
    }  
    
    public void limpiartabla(){
        DefaultTableModel model_cli = (DefaultTableModel) this.detalle_factura.getModel();
        int rows_cli = detalle_factura.getRowCount();
        System.out.println("Registros en la Tabla: "+detalle_factura.getRowCount());

        for(int i=0;i<rows_cli;i++){
            model_cli.removeRow(0);
        }
    }
    
    public void Modificar(){
        jTextInv_CodPro.setEnabled(true);
        jTextPro_Descri.setEnabled(true);
        jTextInv_Cantid.setEnabled(true);
        jTextInv_Precio.setEnabled(true);
        jTextInv_Total.setEnabled(true);
        
        bt_agregarproducto.setEnabled(true);
    }
    
    public void startReport(String numfact){
        try{
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA,USER,PASSWORD);
            System.out.println("Conexion al Reporte: "+CONEXION);
            
            //javax.swing.JOptionPane.showMessageDialog(null,"Procesando Reporte......");
            
            File jasper = new File(System.getProperty("user.dir")+"\\src\\informes\\"+"Factura.jasper");
            System.out.println("Jasper: "+jasper);
            
            JasperReport reporte=null;
//            reporte=(JasperReport) JRLoader.loadObjectFromLocation(template);
            reporte=(JasperReport) JRLoader.loadObject(jasper);
            
            //Declaración de Parametros para consulta en IReport
            Map param=new HashMap();
            param.put("numfact", numfact);
            
            System.out.println("Numero de Documento:"+numfact);
            JasperPrint jasperprinter = JasperFillManager.fillReport(reporte,param,CONEXION);
            System.out.println(jasperprinter);
            JasperViewer vista = new JasperViewer(jasperprinter,false);
            vista.setTitle("Factura de Venta");
            vista.setExtendedState(MAXIMIZED_BOTH);
            vista.setVisible(true);
        }catch(Exception e){
            System.out.println("hola2");
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Ver_Reporte(java.awt.event.ActionEvent evt) {
        String numfact=(jTextEnc_Codigo.getText().toString());
        System.out.println("Numero de Factura: "+numfact);
        startReport(numfact);
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
        bt_find = new javax.swing.JButton();
        bt_salir = new javax.swing.JButton();
        bt_cancel = new javax.swing.JButton();
        bt_Modificar = new javax.swing.JButton();
        bt_Siguiente = new javax.swing.JButton();
        bt_imprimir = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        bt_Atras = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextPro_Descri = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_stock = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextInv_Cantid = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_descuento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        bt_agregarproducto = new javax.swing.JButton();
        jTextInv_Total = new javax.swing.JTextField();
        bt_detalle = new javax.swing.JButton();
        jTextInv_CodPro = new javax.swing.JTextField();
        bt_elimina_detalle = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextEnc_Iva = new javax.swing.JTextField();
        jTextEnc_Monto = new javax.swing.JTextField();
        jTextEnc_Base = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jText_Mae_Nombre = new javax.swing.JTextField();
        jTextEnc_Codigo = new javax.swing.JTextField();
        jDateEnc_Fecha = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCboEnc_Codven = new javax.swing.JComboBox();
        bt_agregarproveedor = new javax.swing.JButton();
        jTextEnc_CodMae = new javax.swing.JTextField();
        txt_buscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextEnc_CodigoCtrl = new javax.swing.JTextField();
        lbl_nroControl = new javax.swing.JLabel();
        bt_agregar = new javax.swing.JButton();
        jLeyenda1 = new javax.swing.JLabel();
        jLeyenda2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu_VerFactura = new javax.swing.JMenuItem();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_find.setMaximumSize(new java.awt.Dimension(83, 77));
        bt_find.setMinimumSize(new java.awt.Dimension(83, 77));
        bt_find.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_salir.setMaximumSize(new java.awt.Dimension(83, 77));
        bt_salir.setMinimumSize(new java.awt.Dimension(83, 77));
        bt_salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setFocusable(false);
        bt_cancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_cancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelActionPerformed(evt);
            }
        });

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Modificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ModificarActionPerformed(evt);
            }
        });

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Siguiente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SiguienteActionPerformed(evt);
            }
        });

        bt_imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/print.png"))); // NOI18N
        bt_imprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_imprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_imprimirActionPerformed(evt);
            }
        });

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Atras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AtrasActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jTextPro_Descri, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 280, -1));

        jLabel7.setText("Codigo Producto y Descripción");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, -1, -1));

        jLabel8.setText("Stock");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));
        jPanel3.add(txt_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 64, -1));

        jLabel9.setText("Precio Unidad");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, -1, -1));

        jTextInv_Precio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextInv_PrecioFocusLost(evt);
            }
        });
        jTextInv_Precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextInv_PrecioKeyPressed(evt);
            }
        });
        jPanel3.add(jTextInv_Precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 135, -1));

        jLabel10.setText("Cantidad");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 78, -1, -1));

        jTextInv_Cantid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextInv_CantidActionPerformed(evt);
            }
        });
        jTextInv_Cantid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextInv_CantidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextInv_CantidFocusLost(evt);
            }
        });
        jTextInv_Cantid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextInv_CantidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextInv_CantidKeyReleased(evt);
            }
        });
        jPanel3.add(jTextInv_Cantid, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 100, 120, -1));

        jLabel12.setText("Unidad de Medida");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, -1, -1));
        jPanel3.add(txt_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 120, -1));

        jLabel13.setText("Total Productos");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        bt_agregarproducto.setText("Buscar");
        bt_agregarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_agregarproductoActionPerformed(evt);
            }
        });
        jPanel3.add(bt_agregarproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));
        jPanel3.add(jTextInv_Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 160, -1));

        bt_detalle.setText("Agregar al detalle");
        bt_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_detalleActionPerformed(evt);
            }
        });
        bt_detalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_detalleKeyPressed(evt);
            }
        });
        jPanel3.add(bt_detalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 140, -1));

        jTextInv_CodPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextInv_CodProActionPerformed(evt);
            }
        });
        jTextInv_CodPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextInv_CodProFocusGained(evt);
            }
        });
        jTextInv_CodPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextInv_CodProKeyPressed(evt);
            }
        });
        jPanel3.add(jTextInv_CodPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, -1));

        bt_elimina_detalle.setText("Eliminar del detalle");
        bt_elimina_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_elimina_detalleActionPerformed(evt);
            }
        });
        jPanel3.add(bt_elimina_detalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 140, -1));

        jPanel3.add(jCboInv_Precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 130, -1));

        jCboInv_Unidad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboInv_UnidadItemStateChanged(evt);
            }
        });
        jCboInv_Unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboInv_UnidadKeyPressed(evt);
            }
        });
        jPanel3.add(jCboInv_Unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, 160, -1));

        jLabel11.setText("Descuento");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, -1, -1));

        detalle_factura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Factura", "Codigo Producto", "Descripción", "Und. Medida", "Cantidad", "Precio Unitario", "Total Producto"
            }
        ));
        detalle_factura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                detalle_facturaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(detalle_factura);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Totalizador"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setText("Total Base:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jLabel16.setText("Total Factura:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        jTextEnc_Iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextEnc_Iva.setText("0.00");
        jPanel4.add(jTextEnc_Iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 150, -1));

        jTextEnc_Monto.setEditable(false);
        jTextEnc_Monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextEnc_Monto.setText("0.00");
        jTextEnc_Monto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEnc_MontoActionPerformed(evt);
            }
        });
        jPanel4.add(jTextEnc_Monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 150, -1));

        jTextEnc_Base.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextEnc_Base.setText("0.00");
        jTextEnc_Base.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEnc_BaseActionPerformed(evt);
            }
        });
        jPanel4.add(jTextEnc_Base, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 150, -1));

        jLabel15.setText("Total Iva:");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("N° Factura");

        jLabel6.setText("Proveedor");

        jLabel4.setText("Fecha");

        jLabel2.setText("Condición de Pago");

        jCboEnc_Condic.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Vendedor");

        jCboEnc_Codven.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        bt_agregarproveedor.setText("Buscar");
        bt_agregarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_agregarproveedorActionPerformed(evt);
            }
        });

        jTextEnc_CodMae.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextEnc_CodMaeKeyPressed(evt);
            }
        });

        txt_buscar.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        txt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscarActionPerformed(evt);
            }
        });
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel5.setText("Buscar:");

        lbl_nroControl.setText("Nº Control");
        lbl_nroControl.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                lbl_nroControlComponentHidden(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextEnc_CodMae)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextEnc_Codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(bt_agregarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jText_Mae_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextEnc_CodigoCtrl, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_nroControl))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCboEnc_Condic, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jCboEnc_Codven, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jDateEnc_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 7, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextEnc_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_nroControl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextEnc_CodigoCtrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateEnc_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCboEnc_Codven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCboEnc_Condic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jText_Mae_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextEnc_CodMae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_agregarproveedor))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_agregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_agregarActionPerformed(evt);
            }
        });

        jLeyenda1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda1.setText("Leyenda 1 de teclas Rapidas");

        jLeyenda2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda2.setText("Leyanda 2 de Teclas Rapidas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLeyenda2)
                    .addComponent(jLeyenda1))
                .addContainerGap(650, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(bt_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bt_Atras, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bt_Siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bt_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bt_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bt_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bt_find, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(533, Short.MAX_VALUE)
                .addComponent(jLeyenda1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLeyenda2)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(3, 3, 3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_agregar)
                        .addComponent(bt_save)
                        .addComponent(bt_imprimir)
                        .addComponent(bt_cancel)
                        .addComponent(bt_find, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bt_Atras)
                                .addComponent(bt_Siguiente)
                                .addComponent(bt_Modificar))))
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

        jMenu2.setText("Informes");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenu_VerFactura.setText("Ver Factura");
        jMenu_VerFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu_VerFacturaActionPerformed(evt);
            }
        });
        jMenu2.add(jMenu_VerFactura);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_agregarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_agregarproveedorActionPerformed
        BuscaMaestros();
    }//GEN-LAST:event_bt_agregarproveedorActionPerformed

    private void jTextEnc_CodMaeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextEnc_CodMaeKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            Hilo_BuscaMaestro BuscaMaestros = new Hilo_BuscaMaestro();
            BuscaMaestros.start();
        }
    }//GEN-LAST:event_jTextEnc_CodMaeKeyPressed

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarActionPerformed

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            lforward=false;
            lnext=false;
            lBuscaDoc=true;

            Hilo_Encabezado Encabezado = new Hilo_Encabezado();
            Encabezado.start();
            Hilo_Detalle Detalle = new Hilo_Detalle();
            Detalle.start();
        }
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void jTextInv_PrecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextInv_PrecioFocusLost
        if (jTextInv_Precio.getText().equals("") || jTextInv_Precio.getText().equals("0")){
            jTextInv_Precio.setText("0.00");
        }
    }//GEN-LAST:event_jTextInv_PrecioFocusLost

    private void jTextInv_PrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInv_PrecioKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            if (jTextInv_Cantid.getText().equals("0")){
                jTextInv_Cantid.setText(""); jTextInv_Cantid.requestFocus();
            }else{
                bt_detalle.requestFocus();
            }
        }
    }//GEN-LAST:event_jTextInv_PrecioKeyPressed

    private void jTextInv_CantidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextInv_CantidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextInv_CantidActionPerformed

    private void jTextInv_CantidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextInv_CantidFocusLost
        if (jTextInv_Cantid.getText().equals("") || jTextInv_Cantid.getText().equals("0")){
            jTextInv_Cantid.setText("0");
        }
    }//GEN-LAST:event_jTextInv_CantidFocusLost

    private void jTextInv_CantidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInv_CantidKeyPressed
        // TODO add your handling code here:
        int c = Integer.parseInt(txt_stock.getText());
        int b = Integer.parseInt(jTextInv_Cantid.getText());

        if (c>b){
            double suma;
            double descuento = Double.parseDouble(txt_descuento.getText());
            double precio = Double.parseDouble(jTextInv_Precio.getText());
            int cant = Integer.parseInt( jTextInv_Cantid.getText() );
            double desc = descuento/100;
            double porcent;

            if (descuento == 0){
                suma = precio*cant ;
            }else{
                porcent = precio*desc;
                suma = (precio-porcent)*cant ;
                System.out.println(suma);
            }

            jTextInv_Total.setText(Double.toString(suma));
            bt_detalle.requestFocus();
        }else {
            String Cant = JOptionPane.showInputDialog((String) Msg.get(3));
            jTextInv_Cantid.setText(Cant);
            bt_detalle.requestFocus();
        }
    }//GEN-LAST:event_jTextInv_CantidKeyPressed

    private void jTextInv_CantidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInv_CantidKeyReleased

    }//GEN-LAST:event_jTextInv_CantidKeyReleased

    private void bt_agregarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_agregarproductoActionPerformed
        BuscaProductos();
    }//GEN-LAST:event_bt_agregarproductoActionPerformed

    private void bt_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_detalleActionPerformed
        action_bt_detalle();
    }//GEN-LAST:event_bt_detalleActionPerformed

    private void bt_detalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_detalleKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            action_bt_detalle();
        }
    }//GEN-LAST:event_bt_detalleKeyPressed

    private void jTextInv_CodProKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextInv_CodProKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            Hilo_BuscaProductos BuscaProductos = new Hilo_BuscaProductos();
            BuscaProductos.start();
        }
    }//GEN-LAST:event_jTextInv_CodProKeyPressed

    private void bt_elimina_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_elimina_detalleActionPerformed
        action_elimina_detalle();
    }//GEN-LAST:event_bt_elimina_detalleActionPerformed

    private void detalle_facturaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detalle_facturaMousePressed
        action_elimina_detalle();
    }//GEN-LAST:event_detalle_facturaMousePressed

    private void jTextEnc_MontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEnc_MontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEnc_MontoActionPerformed

    private void jTextEnc_BaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEnc_BaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEnc_BaseActionPerformed

    private void bt_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_agregarActionPerformed
        action_bt_agregar();
    }//GEN-LAST:event_bt_agregarActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        action_bt_save();
    }//GEN-LAST:event_bt_saveActionPerformed

    private void bt_AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AtrasActionPerformed
        lforward=true;
        lnext=false;
        lBuscaDoc=false;

        Hilo_Encabezado Encabezado = new Hilo_Encabezado();
        Encabezado.start();
        Hilo_Detalle Detalle = new Hilo_Detalle();
        Detalle.start();
    }//GEN-LAST:event_bt_AtrasActionPerformed

    private void bt_SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SiguienteActionPerformed
        lnext=true;
        lforward=false;
        lBuscaDoc=false;

        Hilo_Encabezado Encabezado = new Hilo_Encabezado();
        Encabezado.start();
        Hilo_Detalle Detalle = new Hilo_Detalle();
        Detalle.start();
    }//GEN-LAST:event_bt_SiguienteActionPerformed

    private void bt_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_imprimirActionPerformed
        Ver_Reporte(evt);
    }//GEN-LAST:event_bt_imprimirActionPerformed

    private void bt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelActionPerformed
        action_bt_cancel();
    }//GEN-LAST:event_bt_cancelActionPerformed

    private void bt_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ModificarActionPerformed
        action_bt_modificar();
    }//GEN-LAST:event_bt_ModificarActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        this.jLabel5.setVisible(true);
        this.txt_buscar.setVisible(true);
        this.txt_buscar.setEnabled(true);
        this.txt_buscar.requestFocus();
    }//GEN-LAST:event_bt_findActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        System.out.println("Saliendo del formulario");

        inicio();
        limpiartabla();
        posicion_botones_1();
        subtotal=0; calculariva=0; total1=0;
        Limpiar();
        this.dispose();
    }//GEN-LAST:event_bt_salirActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu_VerFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_VerFacturaActionPerformed
        Ver_Reporte(evt);
    }//GEN-LAST:event_jMenu_VerFacturaActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jTextInv_CantidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextInv_CantidFocusGained
        if (lCantida==false){
            String Cant = JOptionPane.showInputDialog((String) Msg.get(4));
            jTextInv_Cantid.setText(Cant);
            
            double total =Double.valueOf(jTextInv_Precio.getText().toString())*(CanUnd*Integer.valueOf(Cant));
            jTextInv_Total.setText(String.valueOf(total));
            jCboInv_Unidad.requestFocus();
            
            if (Integer.valueOf(Cant)>0){
                lCantida=true;
            }
        }
        
    }//GEN-LAST:event_jTextInv_CantidFocusGained

    private void jTextInv_CodProFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextInv_CodProFocusGained
        lCantida=false;
        
        Hilo_BuscaMaestro BuscaMaestros = new Hilo_BuscaMaestro();
        BuscaMaestros.start();
    }//GEN-LAST:event_jTextInv_CodProFocusGained

    private void jTextInv_CodProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextInv_CodProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextInv_CodProActionPerformed

    private void jCboInv_UnidadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboInv_UnidadItemStateChanged
        UnidaMedida();
        
        double total =Double.valueOf(jTextInv_Precio.getText().toString())*(CanUnd*Integer.valueOf(jTextInv_Cantid.getText().toString()));
        jTextInv_Total.setText(String.valueOf(total));
    }//GEN-LAST:event_jCboInv_UnidadItemStateChanged

    private void jCboInv_UnidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboInv_UnidadKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_detalle.requestFocus();
        }
    }//GEN-LAST:event_jCboInv_UnidadKeyPressed

    private void lbl_nroControlComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_lbl_nroControlComponentHidden
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lbl_nroControlComponentHidden

    public class Hilo_BuscaMaestro extends Thread{
        public void run(){
            String sql = "", Codigo="";
            if (jTextEnc_CodMae.getText().length()>=6){
                if (jTextEnc_CodMae.getText().length()==9){
                    Codigo= jTextEnc_CodMae.getText().toString().substring(0, 8)+"-"+jTextEnc_CodMae.getText().toString().substring(8, 9);
                }else{
                    Codigo= jTextEnc_CodMae.getText().toString();
                }
            }else{
                Codigo= String.format("%010d", Integer.valueOf(jTextEnc_CodMae.getText()));
            }
            System.out.println("Codigo: "+Codigo);
            jTextEnc_CodMae.setText(Codigo);
 
            if (cCxC==true){
                sql = "SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,"+
                      "(CASE WHEN MAE_CODLIS IS NULL THEN '' ELSE MAE_CODLIS END) AS MAE_CODLIS,MAE_DIAS FROM DNMAESTRO "+
                      "WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CODIGO='"+Codigo+"' OR MAE_RIF LIKE '%"+Codigo+"%') AND MAE_CLIENTE='1'";
            }else if (cCxP==true){
                sql = "SELECT MAE_CODIGO,MAE_RIF,MAE_NOMBRE,MAE_LIMITE,MAE_DCTO,MAE_CONDIC,"+
                      "(CASE WHEN MAE_CODLIS IS NULL THEN '' ELSE MAE_CODLIS END) AS MAE_CODLIS,MAE_DIAS FROM DNMAESTRO "+
                      "WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CODIGO='"+Codigo+"' OR MAE_RIF LIKE '%"+Codigo+"%') AND MAE_PROVEED='1'";
            }

            System.out.println("sql");
        
            try {
                rs_BuscaMae = conet.consultar(sql);
                try {
                    if(rs_BuscaMae.getRow()>0){
                        jTextEnc_CodMae.setText(rs_BuscaMae.getString("MAE_CODIGO").toString().trim());
                        jText_Mae_Nombre.setText(rs_BuscaMae.getString("MAE_NOMBRE").toString().trim());
                        jCboEnc_Condic.setSelectedItem(rs_BuscaMae.getString("MAE_CONDIC").trim());

                        nlimit_cre = rs_BuscaMae.getString("MAE_LIMITE").toString();
                        nDcto      = rs_BuscaMae.getString("MAE_DCTO").toString();
                        cTipoPre   = rs_BuscaMae.getString("MAE_CODLIS").toString();
                        nDiasVen   = rs_BuscaMae.getString("MAE_DIAS").toString();
                        
                        jTextInv_CodPro.requestFocus();
                    }else{
                        if (cCxC==true){
                            JOptionPane.showMessageDialog(null,(String) Msg.get(5), (String) Msg.get(6), JOptionPane.ERROR_MESSAGE);
                            jTextEnc_CodMae.setText("");
                            jText_Mae_Nombre.setText("");
                        }else if (cCxP==true){
                            JOptionPane.showMessageDialog(null,(String) Msg.get(7), (String) Msg.get(6), JOptionPane.ERROR_MESSAGE);
                            jTextEnc_CodMae.setText("");
                            jText_Mae_Nombre.setText("");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public class Hilo_BuscaProductos extends Thread{
        public void run(){
            String sql = "";
            String Codigo= String.format("%010d", Integer.valueOf(jTextInv_CodPro.getText()));
            jTextInv_CodPro.setText(Codigo);

            System.out.println("cTipoPre: "+cTipoPre);
            if (cTipoPre.isEmpty() || cTipoPre.equals(null)){
                sql = "SELECT CONCAT('Precio ',PRE_CODLIS,': ',PRE_MONTO) AS DATO1 FROM dnprecio "+
                      "WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRE_CODPRO='"+Codigo+"' AND PRE_ACTIVO='1'";
                
                DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
                jCboInv_Precio.setModel(mdl);
                System.out.println("Items: "+jCboInv_Precio.getItemCount());
                
                if (cCxC==true){
                    sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                          "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                          "(CASE WHEN TIVA_VALVEN IS NULL THEN 0 ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                          "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                          "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA AND TIVA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                          "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO ='"+Codigo+"'";
                }else if (cCxP==true){
                    sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                          "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                          "(CASE WHEN TIVA_VALVEN IS NULL THEN 0 ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                          "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                          "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA AND TIVA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                          "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO ='"+Codigo+"'";
                }
            }else{
                if (cCxC==true){
                    sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                          "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                          "(CASE WHEN TIVA_VALVEN IS NULL THEN 0 ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                          "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRE_CODLIS='"+cTipoPre+"' "+
                          "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA AND TIVA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                          "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO ='"+Codigo+"'";
                }else if (cCxP==true){
                    sql = "SELECT PRO_CODIGO, PRO_DESCRI,IF(PRE_MONTO IS NULL,0,PRE_MONTO) AS PRE_MONTO,"+
                          "(CASE WHEN PRO_TIPIVA IS NULL THEN '' ELSE PRO_TIPIVA END) AS PRO_TIPIVA,"+
                          "(CASE WHEN TIVA_VALVEN IS NULL THEN 0 ELSE TIVA_VALVEN END) AS VALORIVA  FROM DNPRODUCTO "+
                          "LEFT JOIN DNPRECIO ON PRE_CODPRO=PRO_CODIGO AND PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRE_CODLIS='"+cTipoPre+"' "+
                          "LEFT JOIN DNTIPIVA ON TIVA_CODIGO=PRO_TIPIVA AND TIVA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                          "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO ='"+Codigo+"'";
                }
            }

            System.out.println("sql");
        
            try {
                rs_BuscaPro = conet.consultar(sql);
                try {
                    if(rs_BuscaPro.getRow()>0){
                        jTextInv_CodPro.setText(rs_BuscaPro.getString("PRO_CODIGO").toString().trim());
                        jTextPro_Descri.setText(rs_BuscaPro.getString("PRO_DESCRI").toString().trim());
                        if (jCboInv_Precio.getItemCount()>2){
                            jCboInv_Precio.setVisible(true);
                            jCboInv_Precio.setSelectedIndex(1);
                            
                            jTextInv_Precio.setVisible(false);
                            Documentos.lListaP=true;
                        }else{
                            jTextInv_Precio.setText(rs_BuscaPro.getString("PRE_MONTO").toString());
                        }

                        cTipIva   = rs_BuscaPro.getString("PRO_TIPIVA").toString();
                        nValorIva = rs_BuscaPro.getInt("VALORIVA");

                        if(rs_BuscaPro.getString("PRE_MONTO").toString().equals("0")){
                            jTextInv_Precio.setText(""); jTextInv_Precio.requestFocus();
			}else{
                            jTextInv_Cantid.setText(""); jTextInv_Cantid.requestFocus();
                        }
                        
                        sql="";
                        sql = "SELECT CONCAT(BAR_CODUND,' - ',MED_DESCRI) AS DATO1 FROM DNBARUNDMED "+
                              "INNER JOIN DNUNDMEDIDA ON MED_CODIGO=BAR_CODUND AND MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"'  "+
                              "WHERE BAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND BAR_CODPRO='"+Codigo+"'";
                
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
                    }else{
                        JOptionPane.showMessageDialog(null,(String) Msg.get(8), (String) Msg.get(6), JOptionPane.ERROR_MESSAGE);
                        jTextInv_CodPro.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public class Hilo_Encabezado extends Thread{
        public void run(){
            if (lforward==true){
                cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())-1);
                
                if (Integer.valueOf(cNumero)<1){
                    return;
                }
            }else if (lnext==true){
                cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())+1);
                
                if (Integer.valueOf(cNumero)>Integer.valueOf(ultimo_reg)){
                    return;
                }
            }else{  
                if (UltimoRegBusq.equals("0000000000")){
                    cNumero= "0000000001";
                    jTextEnc_Codigo.setText(cNumero);
                    UltimoRegBusq=cNumero;
                }else{
                    cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())-1);
                }
                System.out.println("Numeroooooo Encabezado: "+cNumero);
                //ultimo_reg=cNumero;
                ultimo_reg=UltimoRegBusq;
                
                if (lBuscaDoc==true){
                    if(Integer.valueOf(txt_buscar.getText().toString())<=Integer.valueOf(UltimoRegBusq)){
                        cNumero=String.format("%010d", Integer.valueOf(txt_buscar.getText().toString()));
                        lBuscaDoc=false;
                        txt_buscar.setText(cNumero);
                    }else{
                        JOptionPane.showMessageDialog(null,(String) Msg.get(9), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
                        cNumero=jTextEnc_Codigo.getText().toString();
                        lBuscaDoc=false;
                        txt_buscar.setText("");
                    }
                }
            }
            System.out.println("Encabezado: "+jTextEnc_Codigo.getText());
            System.out.println("Encabezado: "+cNumero);
            
            String sql = "SELECT ENC_CODIGO,ENC_CODDOC,ENC_CODMAE,MAE_NOMBRE,ENC_BASE,ENC_IVA,ENC_MONTO,ENC_DCTO,ENC_CONDIC,"+
                         "CONCAT(VEN_CODIGO,' - ',VEN_NOMBRE) AS ENC_CODVEN,ENC_FECHA,ENC_FCHDEC FROM DNENCABEZADO "+
                         "INNER JOIN DNMAESTRO ON ENC_CODMAE=MAE_CODIGO AND MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                         "INNER JOIN DNVENDEDOR ON ENC_CODVEN=VEN_CODIGO AND VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                         "WHERE ENC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ENC_CODIGO='"+cNumero+"' AND ENC_CODDOC='"+cTipDoc+"' ORDER BY ENC_ID DESC LIMIT 1";
            System.out.println(sql);
        
            try {
                rs_encab = conet.consultar(sql);
                try {
                    if(rs_encab.getRow()>0){
                        nDoc=rs_encab.getRow();
                        jTextEnc_Codigo.setText(rs_encab.getString("ENC_CODIGO").toString().trim());
                        jTextEnc_CodMae.setText(rs_encab.getString("ENC_CODMAE").toString().trim());
                        jText_Mae_Nombre.setText(rs_encab.getString("MAE_NOMBRE").toString().trim());
                        jTextEnc_Base.setText(rs_encab.getString("ENC_BASE"));
                        jTextEnc_Iva.setText(rs_encab.getString("ENC_IVA"));
                        jTextEnc_Monto.setText(rs_encab.getString("ENC_MONTO"));
                        jCboEnc_Condic.setSelectedItem(rs_encab.getString("ENC_CONDIC").trim());
                        jCboEnc_Codven.setSelectedItem(rs_encab.getString("ENC_CODVEN").trim());
                        
                        //--------- Covierte el String de la Fecha en Date ----------
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fch = null;

                        try {
                            fch = sdf.parse(rs_encab.getString("ENC_FECHA"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        
                        jDateEnc_Fecha.setDate(fch);
                        //-----------------------------------------------------------
                    }else{
                        System.out.println("Sin Registros");
                        nDoc=rs_encab.getRow();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public class Hilo_Detalle extends Thread{
        public void run(){
            if (lforward==true){
                cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())-1);
                
                if (Integer.valueOf(cNumero)<1){
                    return;
                }
            }else if (lnext==true){
                cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())+1);
                
                if (Integer.valueOf(cNumero)>Integer.valueOf(ultimo_reg)){
                    return;
                }
            }else{
                cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())-1);
                //ultimo_reg=cNumero;
                if (cNumero.equals("0000000000")){
                    cNumero= "0000000001";
                    UltimoRegBusq=cNumero;
                    jTextEnc_Codigo.setText(cNumero);
                }else{
                    cNumero= String.format("%010d", Integer.valueOf(jTextEnc_Codigo.getText())-1);
                }
                ultimo_reg=UltimoRegBusq;
                
                if (lBuscaDoc==true){
                    if(Integer.valueOf(txt_buscar.getText().toString())<=Integer.valueOf(UltimoRegBusq)){
                        cNumero=String.format("%010d", Integer.valueOf(txt_buscar.getText().toString()));
                        lBuscaDoc=false;
                    }else{
                        cNumero=jTextEnc_Codigo.getText().toString();
                        lBuscaDoc=false;
                        txt_buscar.setText("");
                    }
                }
            }
            System.out.println("Nueva Numero del Doc: "+cNumero);
            
            cargatabla = new CargaTablas();
            String sql = "SELECT INV_CODENC,INV_CODPRO,PRO_DESCRI,INV_UNIDAD,INV_CANUND,INV_CANTID,INV_PRECIO,INV_TOTAL FROM DNINVENTARIO "+
                         "INNER JOIN DNPRODUCTO ON INV_CODPRO=PRO_CODIGO AND PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                         "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_CODDOC='"+cTipDoc+"' AND INV_CODENC='"+cNumero+"' ";

            //String[] columnas = {"N° Factura","Código Producto","Descripción","Und. Medida","Cant. x Und","Cantidad","Precio Unitario","Total Producto"};
            String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3),(String) header_table.get(4),(String) header_table.get(5),(String) header_table.get(6),(String) header_table.get(7)};
            
            cargatabla.cargatablas(detalle_factura,sql,columnas);
            txt_buscar.setText("");
            
            System.out.println("Registros Tabla: "+detalle_factura.getRowCount());
            System.out.println("Tipo de Documento: "+cTipDoc);
        }
    }

    private void action_bt_agregar(){
        lAgregar=true;
        lModificar=false;

        activar("Inicializa");
        limpiartabla();
        correlativo();
        validad_documento(cTipDoc);
    }
    
    private void action_bt_modificar(){
        lAgregar=false;
        lModificar=true;

        nItems=detalle_factura.getRowCount();

        Modificar();
        activar("Modificar");
    }
    
    private void action_bt_save(){
        validad_documento(cTipDoc);
        
        if (jTextEnc_CodMae.getText().equals("") && cCxC==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(10), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jTextEnc_CodMae.requestFocus();
            return;
        }
        
        if (jTextEnc_CodMae.getText().equals("") && cCxP==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(11), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jTextEnc_CodMae.requestFocus();
            return;
        }
        
        if (jCboEnc_Codven.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null,(String) Msg.get(12), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jCboEnc_Codven.requestFocus();
            return;
        }

        if(detalle_factura.getRowCount()<=0){
            JOptionPane.showMessageDialog(null,(String) Msg.get(13), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            return;
        }else{
            SQLQuerys insertar = new SQLQuerys();

            if (lAgregar==true){
                Date date = jDateEnc_Fecha.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = sdf.format(date);
                fecha = fecha;

                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(date.getTime());
                cal.add(Calendar.DATE, Integer.valueOf(nDiasVen));
                String fecha_ven = sdf.format(cal.getTime());
                System.out.println("Fecha: "+cal.getTime());

                //String fecha_Dec = sdf.format("0000-00-00");
                String fecha_Dec = "0000-00-00";
                String ConDic = (String) jCboEnc_Condic.getSelectedItem();
                if (ConDic.isEmpty()){
                    ConDic=null;
                }else{
                    ConDic="'"+ConDic+"'";
                }
                String CodVen = (String) jCboEnc_Codven.getSelectedItem();
                CodVen = CodVen.substring(0, 6).toString();

                String sql = "INSERT INTO dnencabezado (ENC_EMPRESA,ENC_USER,ENC_MACPC,ENC_CODIGO,ENC_CODDOC,ENC_CODMAE,ENC_ACTIVO,"+
                                                       "ENC_BASE,ENC_IVA,ENC_MONTO,ENC_CONDIC,ENC_CODVEN,ENC_FECHA,"+
                                                       "ENC_FCHDEC,ENC_FCHVEN) "+
                                              "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"',"+
                                                      "'"+jTextEnc_Codigo.getText().toString()+"','"+cTipDoc+"',"+
                                                      "'"+jTextEnc_CodMae.getText().toString()+"','1',"+
                                                       ""+jTextEnc_Base.getText().toString()+","+
                                                       ""+jTextEnc_Iva.getText().toString()+","+
                                                       ""+jTextEnc_Monto.getText().toString()+","+
                                                       ""+ConDic+",'"+CodVen+"',"+
                                                      "'"+fecha+"','"+fecha_Dec+"','"+fecha_ven+"'); ";

                System.out.println(sql);
                insertar.SqlInsert(sql);
                
                Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                     VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                     "Inserción de Nuevo Registro", "Se creo el Documento ''"+cTipDoc+": "+
                                                              jTextEnc_Codigo.getText().toString()+"'' del Maestro: "+
                                                              jTextEnc_CodMae.getText().toString()+" Correctamente");

                for (int i=0; i<detalle_factura.getRowCount(); i++){
                    String NumDoc    = detalle_factura.getValueAt(i,0).toString();
                    String CodMae    = jTextEnc_CodMae.getText().toString();
                    String CodPro    = detalle_factura.getValueAt(i,1).toString();
                    String UndPro    = detalle_factura.getValueAt(i,3).toString();
                    String CanUnd    = detalle_factura.getValueAt(i,4).toString();
                    String Cantid    = detalle_factura.getValueAt(i,5).toString();
                    String Precio    = detalle_factura.getValueAt(i,6).toString();
                    String TotalProd = detalle_factura.getValueAt(i,7).toString();
                    String Item = String.valueOf(i+1);
                    String Item_pro=""; String InvFis = "0"; String InvLog = "0";

                    if (lInvAct.equals("1")){
                        if (lFisico==true){
                            InvFis = "1";
                        }
                        if (lLogico==true){
                            InvLog = "1";
                        }
                    }else if (lInvAct.equals("-1")){
                        if (lFisico==true){
                            InvFis = "-1";
                        }
                        if (lLogico==true){
                            InvLog = "-1";
                        }
                    }

                    if (Item.length()==1){
                        Item_pro = "00000"+Item;
                    }else{
                        Item_pro = "0000"+Item;
                    }

                    String SQL_Insert = "INSERT INTO dninventario (INV_EMPRESA,INV_USER,INV_MACPC,INV_CODDOC,INV_CODPRO,INV_CODENC,INV_CODMAE,INV_UNIDAD,INV_CANUND,"+
                                                                  "INV_LOGICO,INV_FISICO,INV_ITEM,INV_CANTID,INV_PRECIO,INV_TOTAL,INV_ACTIVO) "+
                                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+cTipDoc+"',"+
                                                                  "'"+CodPro+"','"+NumDoc+"','"+CodMae+"','"+UndPro+"','"+CanUnd+"',"+
                                                                  "'"+InvLog+"','"+InvFis+"','"+Item_pro+"',"+Cantid+","+Precio+","+TotalProd+",'1');";

                    insertar.SqlInsert(SQL_Insert);
                    
                    Bitacora registo_detalle = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                            VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                            "Inserción de Nuevo Registro", "Se ingreso el Item ''"+Item_pro+
                                                            " para el Documento "+cTipDoc+": "+NumDoc+"'' del Maestro: "+CodMae+" Correctamente");
                }
            }else if (lModificar==true){
                SQLQuerys actualizar = new SQLQuerys();

                String SQL_Update = "UPDATE dnencabezado SET ENC_BASE="+jTextEnc_Base.getText().toString()+","+
                                                            "ENC_IVA="+jTextEnc_Iva.getText().toString()+","+
                                                            "ENC_MONTO="+jTextEnc_Monto.getText().toString()+" "+
                                    "WHERE ENC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ENC_CODIGO='"+jTextEnc_Codigo.getText().toString()+"' AND "+
                                          "ENC_CODDOC='"+cTipDoc+"';";

                actualizar.SqlUpdate(SQL_Update);
                
                Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                     VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                     "Modificación de Registro", "Se Modifico el Documento ''"+cTipDoc+": "+
                                                     jTextEnc_Codigo.getText().toString()+"'' del Maestro: "+jTextEnc_CodMae.getText().toString()+" Correctamente");

                for (int i=nItems; i<detalle_factura.getRowCount(); i++){
                    String NumDoc    = detalle_factura.getValueAt(i,0).toString();
                    String CodMae    = jTextEnc_CodMae.getText().toString();
                    String CodPro    = detalle_factura.getValueAt(i,1).toString();
                    String UndPro    = detalle_factura.getValueAt(i,3).toString();
                    String CanUnd    = detalle_factura.getValueAt(i,4).toString();
                    String Cantid    = detalle_factura.getValueAt(i,5).toString();
                    String Precio    = detalle_factura.getValueAt(i,6).toString();
                    String TotalProd = detalle_factura.getValueAt(i,7).toString();
                    String Item = String.valueOf(i+1);
                    String Item_pro=""; String InvFis = "0"; String InvLog = "0";

                    if (lInvAct.equals("1")){
                        if (lFisico==true){
                            InvFis = "1";
                        }
                        if (lLogico==true){
                            InvLog = "1";
                        }
                    }else if (lInvAct.equals("-1")){
                        if (lFisico==true){
                            InvFis = "-1";
                        }
                        if (lLogico==true){
                            InvLog = "-1";
                        }
                    }

                    if (Item.length()==1){
                        Item_pro = "00000"+Item;
                    }else{
                        Item_pro = "0000"+Item;
                    }

                    String SQL_Insert = "INSERT INTO dninventario (INV_EMPRESA,INV_USER,INV_MACPC,INV_CODDOC,INV_CODPRO,INV_CODENC,INV_CODMAE,INV_UNIDAD,INV_CANUND,"+
                                                                  "INV_LOGICO,INV_FISICO,INV_ITEM,INV_CANTID,INV_PRECIO,INV_TOTAL,INV_ACTIVO) "+
                                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+cTipDoc+"',"+
                                                                  "'"+CodPro+"','"+NumDoc+"','"+CodMae+"','"+UndPro+"','"+CanUnd+"',"+
                                                                  "'"+InvLog+"','"+InvFis+"','"+Item_pro+"',"+Cantid+","+Precio+","+TotalProd+",'1');";

                    insertar.SqlInsert(SQL_Insert);
                    
                    Bitacora registo_detalle = new Bitacora(VarGlobales.getCodEmpresa() , VarGlobales.getMacPc(), 
                                                            VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                            "Modificación de Registro", "Se Modifico el Item ''"+
                                                             Item_pro+" para el Documento "+cTipDoc+": "+NumDoc+"'' del Maestro: "+CodMae+" Correctamente");
                }
            }
        }

        if (lAgregar==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(14)+jTextEnc_Codigo.getText().toString()+
                (String) Msg.get(15), (String) Msg.get(16), JOptionPane.INFORMATION_MESSAGE);
        }else if (lModificar==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(14)+jTextEnc_Codigo.getText().toString()+
                (String) Msg.get(17), (String) Msg.get(16), JOptionPane.INFORMATION_MESSAGE);
        }

        inicio();
        limpiartabla();
        correlativo();
        posicion_botones_1();

        Hilo_Encabezado Encabezado = new Hilo_Encabezado();
        Encabezado.start();
        Hilo_Detalle Detalle = new Hilo_Detalle();
        Detalle.start();        
    }
    
    private void action_bt_cancel(){
        int eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(18)+this.getTitle().toString()+"?");

        if ( eleccion == 0) {
            if (nDoc==0){
                lOpc=false;
                this.dispose();
            }else{
                inicio();
                correlativo();
                limpiartabla();
                posicion_botones_1();
                subtotal=0; calculariva=0; total1=0;

                Hilo_Encabezado Encabezado = new Hilo_Encabezado();
                Encabezado.start();
                Hilo_Detalle Detalle = new Hilo_Detalle();
                Detalle.start();
            }
        }
    }
    
    private void action_bt_detalle(){
        int fsel = detalle_factura.getSelectedRow();
        int c = Integer.parseInt(txt_stock.getText());
        int b = Integer.parseInt(jTextInv_Cantid.getText());
        double x,calcula = 0;
        String num_factura = jTextEnc_Codigo.getText();
        String codigoproducto = jTextInv_CodPro.getText();
        String descripcion = jTextPro_Descri.getText();
        String precio="0.00";
        if (lListaP==false){
            precio = jTextInv_Precio.getText();
        }else if (lListaP==true){
            precio = (String) jCboInv_Precio.getSelectedItem();
            precio = precio.substring(10,precio.length()).toString();
        }
        String canti = jTextInv_Cantid.getText();
        String totalproducto = jTextInv_Total.getText();
        String totalfactura = jTextEnc_Monto.getText();
        String total;
    
        validad_documento(cTipDoc);
        System.out.println("Precio00: "+precio);
                
        if (nValorIva<=0 && lCalIva==true){ // Si el documento calcula IVA y el prodcto no tiene configurado IVA
            JOptionPane.showMessageDialog(null,(String) Msg.get(19), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jTextInv_Cantid.setText("0"); jTextInv_Precio.setText("0.00");
            jTextInv_CodPro.requestFocus();
            return;
        }

        if (lListaP==false){
            if(jTextInv_Precio.getText().toString().equals("0") || jTextInv_Precio.getText().toString().equals("0.00") || jTextInv_Precio.getText().toString().equals("")){
                JOptionPane.showMessageDialog(null,(String) Msg.get(20), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
                jTextInv_Precio.setText(""); jTextInv_Precio.requestFocus();
                return;
            }
        }else if (lListaP==true){
            if(jCboInv_Precio.getSelectedItem().toString().equals(null)){
                JOptionPane.showMessageDialog(null,(String) Msg.get(21), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
                jTextInv_Precio.requestFocus();
                return;
            }
        }
        
        if (jCboInv_Unidad.getSelectedItem().toString().isEmpty()){
            JOptionPane.showMessageDialog(null,(String) Msg.get(22), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jCboInv_Unidad.requestFocus();
            return;
        }
        
        if(jTextInv_CodPro.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,(String) Msg.get(23), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            bt_agregarproducto.requestFocus();
            return;
        }

        if(jTextInv_Cantid.getText().trim().isEmpty() || jTextInv_Cantid.getText().equals("0")){
            JOptionPane.showMessageDialog(null,(String) Msg.get(24), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jTextInv_Cantid.requestFocus();
            return;
        }

        if(fsel==1){
            JOptionPane.showMessageDialog(null,(String) Msg.get(25), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jTextInv_CodPro.requestFocus();
        }else if(jText_Mae_Nombre.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,(String) Msg.get(26), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
            jTextEnc_CodMae.requestFocus();
        }else {
            UnidaMedida();
                    
            x = (Double.parseDouble(precio) * (Integer.parseInt(canti)*CanUnd));
            total = String.valueOf(x);
            m = (DefaultTableModel) detalle_factura.getModel();
            String filaelemento[] = {num_factura,codigoproducto,descripcion,jCboInv_Unidad.getSelectedItem().toString().substring(0, 3),String.valueOf(CanUnd),canti,precio,total};
            m.addRow(filaelemento);

            if (lModificar==true){
                subtotal= Double.valueOf(jTextEnc_Base.getText());
            }

            calcula = (Double.parseDouble(precio) * (Integer.parseInt(jTextInv_Cantid.getText())*CanUnd));
            subtotal = subtotal + calcula;

            if (lCalIva==true){
                calculariva = (subtotal*nValorIva/100);
            }
            //total1 = total1 + calcula + calculariva;
            total1 = subtotal + calculariva;

            jTextEnc_Base.setText(""+subtotal);
            jTextEnc_Iva.setText(""+calculariva);
            jTextEnc_Monto.setText(""+total1);
            
            activar("Limpiar");
        }
    }
    
    private void action_elimina_detalle(){
        int fsel=-1;
        fsel = detalle_factura.getSelectedRow();
        int c = Integer.parseInt(txt_stock.getText());
        int b = Integer.parseInt(jTextInv_Cantid.getText());
        double x, calcula, restaiva=0;
        String num_factura = jTextEnc_Codigo.getText();
        String codigoproducto = jTextEnc_CodMae.getText();
        String descripcion = jTextPro_Descri.getText();
        String precio = jTextInv_Precio.getText();
        String canti = jTextInv_Cantid.getText();
        String totalproducto = jTextInv_Total.getText();
        String totalfactura = jTextEnc_Monto.getText();

        int resp = 0;

        try{
            if (lAgregar==true){
                fsel = detalle_factura.getSelectedRow();
                if (fsel<0){
                    JOptionPane.showMessageDialog(null, (String) Msg.get(27), (String) Msg.get(6), JOptionPane.WARNING_MESSAGE);
                }else{
                    resp = JOptionPane.showConfirmDialog(null, (String) Msg.get(28), (String) Msg.get(29),JOptionPane.YES_NO_OPTION);
                    if(resp == JOptionPane.YES_OPTION){
                        totalproducto = detalle_factura.getValueAt(fsel,7).toString();
                        System.out.println("Posicion: "+fsel);
                        System.out.println("totalproducto: "+totalproducto);

                        if (lCalIva==true){
                            restaiva = (Double.valueOf(totalproducto)*nValorIva/100);
                        }

                        double base = Double.valueOf(jTextEnc_Base.getText())-Double.valueOf(totalproducto);
                        double iva = Double.valueOf(jTextEnc_Iva.getText())-restaiva;
                        double total = Double.valueOf(jTextEnc_Monto.getText())-(Double.valueOf(totalproducto)+restaiva);
                        
                        jTextEnc_Base.setText(String.valueOf(base));
                        jTextEnc_Iva.setText(String.valueOf(iva));
                        jTextEnc_Monto.setText(String.valueOf(total));
            
                        m = (DefaultTableModel)detalle_factura.getModel();
                        m.removeRow(fsel);
                    }
                }
            }
        }catch (Exception e){

        }
        
        if (detalle_factura.getRowCount()==0){
            jTextEnc_CodMae.setEnabled(true);
            bt_agregarproveedor.setEnabled(true);
        }
    }
    
    private void BuscaMaestros(){
        BuscarMaestro bm = new BuscarMaestro(cCxC, cCxP);

        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = bm.getSize();
        bm.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        principal.escritorio.add(bm);
        bm.toFront();
        bm.setVisible(true);
    }
    
    private void BuscaProductos(){
        lCantida=false;
        Agregaraldetalle ad = new Agregaraldetalle(cCxC, cCxP);

        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = ad.getSize();
        ad.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        principal.escritorio.add(ad);
        ad.toFront();
        ad.setVisible(true);      
    }
    
    private void UnidaMedida(){
        String sql = "";

        if (lSinUnd==true){
            sql = "SELECT BAR_CODUND,BAR_CODPRO,BAR_BARRA,MED_DESCRI,MED_CANUND,MED_PESO,MED_VOLUME,"+
                  "MED_SIGNO,MED_MULCXU FROM DNBARUNDMED " +
                  "INNER JOIN DNUNDMEDIDA ON MED_CODIGO=BAR_CODUND AND MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' " +
                  "WHERE BAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND BAR_CODPRO='"+jTextInv_CodPro.getText().toString()+"' AND "+
                  "BAR_CODUND='"+jCboInv_Unidad.getSelectedItem().toString().substring(0, 3)+"'";
            
            try {
                rs_undmed = conet.consultar(sql);
                try {
                    CanUnd = rs_undmed.getInt("MED_CANUND");
                    PesUnd = rs_undmed.getInt("MED_PESO");
                    VolUnd = rs_undmed.getInt("MED_VOLUME");
                    SigUnd = rs_undmed.getString("MED_SIGNO").toString();
                } catch (SQLException ex) {                    
                    Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Documentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            CanUnd = 1;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_agregarproducto;
    private javax.swing.JButton bt_agregarproveedor;
    private javax.swing.JButton bt_cancel;
    public static javax.swing.JButton bt_detalle;
    private javax.swing.JButton bt_elimina_detalle;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_imprimir;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    public static final javax.swing.JTable detalle_factura = new javax.swing.JTable();
    private javax.swing.JComboBox jCboEnc_Codven;
    public static final javax.swing.JComboBox jCboEnc_Condic = new javax.swing.JComboBox();
    public static final javax.swing.JComboBox jCboInv_Precio = new javax.swing.JComboBox();
    public static final javax.swing.JComboBox jCboInv_Unidad = new javax.swing.JComboBox();
    private com.toedter.calendar.JDateChooser jDateEnc_Fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLeyenda1;
    private javax.swing.JLabel jLeyenda2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenu_VerFactura;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextEnc_Base;
    public static javax.swing.JTextField jTextEnc_CodMae;
    private javax.swing.JTextField jTextEnc_Codigo;
    private javax.swing.JTextField jTextEnc_CodigoCtrl;
    private javax.swing.JTextField jTextEnc_Iva;
    private javax.swing.JTextField jTextEnc_Monto;
    public static javax.swing.JTextField jTextInv_Cantid;
    public static javax.swing.JTextField jTextInv_CodPro;
    public static final javax.swing.JTextField jTextInv_Precio = new javax.swing.JTextField();
    private javax.swing.JTextField jTextInv_Total;
    public static javax.swing.JTextField jTextPro_Descri;
    public static javax.swing.JTextField jText_Mae_Nombre;
    private javax.swing.JLabel lbl_nroControl;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_descuento;
    public static javax.swing.JTextField txt_stock;
    // End of variables declaration//GEN-END:variables
}