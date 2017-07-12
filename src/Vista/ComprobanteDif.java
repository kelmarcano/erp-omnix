/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Listener.ListenerButton;
import Modelos.ModelActionListener;
import Modelos.VariablesGlobales;
import Pantallas.Documentos;
import static Pantallas.Documentos.detalle_factura;
import static Pantallas.Documentos.jCboEnc_Condic;
import static Pantallas.Documentos.jCboInv_Precio;
import static Pantallas.Documentos.jCboInv_Unidad;
import static Pantallas.Documentos.jTextEnc_CodMae;
import static Pantallas.Documentos.jTextInv_Cantid;
import static Pantallas.Documentos.jTextInv_CodPro;
import static Pantallas.Documentos.jTextInv_Precio;
import static Pantallas.Documentos.jTextPro_Descri;
import static Pantallas.Documentos.jText_Mae_Nombre;
import static Pantallas.Documentos.lListaP;
import static Pantallas.Documentos.txt_stock;
import static Pantallas.principal.escritorio;
import static Vista.CrearUsuarios.jFondo_Preview;
import static Vista.Login.Idioma;
import static Vista.Maestros.jTxtMae_Limite;
import static Vista.Moneda.jTxtMon_Codigo;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.ValidaCodigo;
import clases.conexion;
import clases.control_existencias;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kelvin
 */
public class ComprobanteDif extends javax.swing.JInternalFrame {
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
    ModelActionListener prueba = ModelActionListener.getPrueba();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();
    
    /**
     * Creates new form ComprobanteDif
     */
    public ComprobanteDif() {
        initComponents();
        inicializaClase();
        
        jTxtCta_Debe.setEditable(false);
        jTxtCta_Haber.setEditable(false);
        jTxtCta_Debe.setEnabled(false);
        jTxtCta_Haber.setEnabled(false);
        detalle_contable.setBackground(Color.decode(colorForm));
        jPanel1.setBackground(Color.decode(colorForm));
        jPanel2.setBackground(Color.decode(colorForm));
        jPanel3.setBackground(Color.decode(colorForm));
        jLabel1.setForeground(Color.decode(colorText));
        jLabel2.setForeground(Color.decode(colorText));
        jLabel8.setForeground(Color.decode(colorText));
        jLabel10.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText));
        jLabel6.setForeground(Color.decode(colorText));
        jLabel7.setForeground(Color.decode(colorText));
        jLabel11.setForeground(Color.decode(colorText));
        jLabel12.setForeground(Color.decode(colorText));
        jLabel13.setForeground(Color.decode(colorText));
        jTxtCom_Codigo.setForeground(Color.decode(colorText));
        jTxtDescrip_Asi.setForeground(Color.decode(colorText));
        jTxtNum_Doc.setForeground(Color.decode(colorText));
        jTxtCta_Debe.setForeground(Color.decode(colorText));
        jTxtCta_Haber.setForeground(Color.decode(colorText));
        jTxtCta_Descri.setForeground(Color.decode(colorText));
        jTxtCta_Monto.setForeground(Color.decode(colorText));
        jTxtCta_Num.setForeground(Color.decode(colorText));
        jTxtDoc_Monto.setForeground(Color.decode(colorText));
        btncomp_agregar.setForeground(Color.decode(colorText));
        btncomp_eliminar.setForeground(Color.decode(colorText));
        jDateCom_Fechavig.setForeground(Color.decode(colorText));
        
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);
        
        /*
        jLabel1.setText((String) elementos.get(0));
        jLabel5.setText((String) elementos.get(2));
        jLabel6.setText((String) elementos.get(3));
        jLabel7.setText((String) elementos.get(4));
        jLabel8.setText((String) elementos.get(5));
        jLabel10.setText((String) elementos.get(6));
        jLabel11.setText((String) elementos.get(7));
        jLabel12.setText((String) elementos.get(8));
        jLabel13.setText((String) elementos.get(9));
        
         */
        Limitar();
        modelActionListener.cargaTabla();
        this.setTitle("Comprobante Contable Diferido");
      
        
        if (detalle_contable.getRowCount()==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();
            //jTxtMon_Nombre.requestFocus();
            
            lAgregar=true;
        }else{
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            bt_save.setVisible(false); 
            bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();
            
            detalle_contable.getSelectionModel().setSelectionInterval(detalle_contable.getRowCount()-1, detalle_contable.getRowCount()-1);
        }
        
        bt_agregar.addActionListener(new ListenerButton());
        bt_Modificar.addActionListener(new ListenerButton());
        bt_save.addActionListener(new ListenerButton());
        bt_Eliminar.addActionListener(new ListenerButton());
        bt_cancel.addActionListener(new ListenerButton());
        bt_Atras.addActionListener(new ListenerButton());
        bt_Siguiente.addActionListener(new ListenerButton());
        bt_salir.addActionListener(new ListenerButton());
        
        //******************** Codigo para Teclas Rapidas ********************
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0), "Maestros");
        
        getRootPane().getActionMap().put("Maestros", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                if (lOpc==true){
                    //BuscaMaestros();
                }else{
                   // action_bt_agregar();
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
                    //BuscaProductos();
                }else{
                   // String numfact=(jTextCom_Codigo.getText().toString());
                    //startReport(numfact);
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
                   // action_bt_modificar();
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
                   // action_bt_save();
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
                    //action_bt_cancel();
                }else{
                    dispose();
                }
            }
        });
//*******************************************************************************************
        
        total1=0; descuento=0; subtotal=0.0; iva1=0;
        /*
        correlativo();
        UltimoRegBusq= String.format("%010d", Integer.valueOf(this.jTxtCom_Codigo.getText())-1);

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
            
            lAgregar=true;
            this.jTxtCom_Codigo.requestFocus();
        }else{
            
            posicion_botones_1();
        }
        */
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
    
    public void Limitar(){
        this.jTxtCom_Codigo.setDocument(new LimitarCaracteres(jTxtCom_Codigo, 6));
        this.jTxtNum_Doc.setDocument(new LimitarCaracteres(jTxtNum_Doc, 10));
        this.jTxtDoc_Monto.setDocument(new LimitarCaracteres(jTxtDoc_Monto, 10));
        this.jTxtCta_Descri.setDocument(new LimitarCaracteres(jTxtCta_Descri, 50));
        this.jTxtDescrip_Asi.setDocument(new LimitarCaracteres(jTxtDescrip_Asi, 50));
        this.jTxtCta_Num.setDocument(new LimitarCaracteres(jTxtCta_Num, 12));
        this.jTxtCta_Haber.setDocument(new LimitarCaracteres(jTxtCta_Haber, 10));
        this.jTxtCta_Debe.setDocument(new LimitarCaracteres(jTxtCta_Debe, 10));
    }
    
     public void Mascara_Campos_Num(){
        jTxtCta_Haber.setText("0,00"); 
        jTxtCta_Debe.setText("0,00");
        jTxtDoc_Monto.setText("0,00");
        jTxtCta_Monto.setText("0,00");

//--------- Covierte el String de la Fecha en Date ----------
        Calendar c1 = Calendar.getInstance();

        String Dia = Integer.toString(c1.get(Calendar.DATE));
        String Mes = Integer.toString(c1.get(Calendar.MONTH)+1);
        String Ano = Integer.toString(c1.get(Calendar.YEAR));

        if (c1.get(Calendar.MONTH)+1<9){
            Mes = "0"+Integer.toString(c1.get(Calendar.MONTH)+1);
        }
        String fecha = Dia+"/"+Mes+"/"+Ano;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fch = null;

        try {
            fch = sdf.parse(fecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        jDateCom_Fechavig.setDate(fch);
//-----------------------------------------------------------
    }
     
    public void Numeros(JTextField num){
        num.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt){
                char let=evt.getKeyChar();

                if(Character.isLetter(let)){
                    evt.consume();
                }
            }
        });
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

        this.jTxtCom_Codigo.setText(Consecutivo);
    } 
    
    public void detalle_tabla(){
        int fsel = detalle_contable.getSelectedRow();
        String numcom = this.jTxtCom_Codigo.getText();
        String numdoc = this.jTxtNum_Doc.getText();
        String docmon = this.jTxtDoc_Monto.getText();
        String ctanum = this.jTxtCta_Num.getText();
        String descri = this.jTxtDescrip_Asi.getText();
        String descricta = this.jTxtCta_Descri.getText();
        String ctamon = this.jTxtCta_Monto.getText();
        String debe = this.jTxtCta_Debe.getText();
        String haber = this.jTxtCta_Haber.getText();
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        detalle_contable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtCom_Codigo = new javax.swing.JTextField();
        jDateCom_Fechavig = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jTxtDoc_Monto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTxtDescrip_Asi = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTxtNum_Doc = new javax.swing.JTextField();
        txt_buscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTxtCta_Num = new javax.swing.JTextField();
        btncomp_buscar = new javax.swing.JButton();
        jTxtCta_Descri = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTxtCta_Monto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTxtCta_Debe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTxtCta_Haber = new javax.swing.JTextField();
        btncomp_eliminar = new javax.swing.JButton();
        btncomp_agregar = new javax.swing.JButton();
        bt_agregar = new javax.swing.JButton();
        bt_Modificar = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        bt_Eliminar = new javax.swing.JButton();
        bt_cancel = new javax.swing.JButton();
        bt_find = new javax.swing.JButton();
        bt_Atras = new javax.swing.JButton();
        jLeyenda1 = new javax.swing.JLabel();
        jLeyenda2 = new javax.swing.JLabel();
        bt_Siguiente = new javax.swing.JButton();
        bt_salir = new javax.swing.JButton();
        bt_imprimir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

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

        detalle_contable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cod. Cuenta", "Descripción", "Debe/Haber", "Monto"
            }
        ));
        detalle_contable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detalle_contableMouseClicked(evt);
            }
        });
        detalle_contable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                detalle_contableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                detalle_contableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(detalle_contable);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comprobante Contable", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setText("Nº Comprobante:");

        jLabel2.setText("Fecha:");

        jTxtCom_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtCom_CodigoFocusLost(evt);
            }
        });

        jLabel11.setText("Monto");

        jTxtDoc_Monto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtDoc_MontoFocusLost(evt);
            }
        });

        jLabel12.setText("Descripción del Asiento");

        jLabel13.setText("Nº Documento:");

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

        jLabel9.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel9.setText("Buscar:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateCom_Fechavig, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jTxtNum_Doc, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtDoc_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTxtDescrip_Asi, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtCom_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171)
                        .addComponent(jLabel9)
                        .addGap(1, 1, 1)
                        .addComponent(txt_buscar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtCom_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateCom_Fechavig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTxtDescrip_Asi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTxtDoc_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(6, 6, 6)
                        .addComponent(jTxtNum_Doc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información del Asiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel7.setText("Nº Cuenta");

        jTxtCta_Num.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCta_NumKeyPressed(evt);
            }
        });

        btncomp_buscar.setText("Buscar");

        jLabel8.setText("Descripción Cuenta");

        jTxtCta_Monto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtCta_MontoFocusLost(evt);
            }
        });

        jLabel10.setText("Monto en Cuenta");

        jLabel6.setText("Debe:");

        jLabel5.setText("Haber:");

        btncomp_eliminar.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btncomp_eliminar.setText("Eliminar Asiento del Detalle");

        btncomp_agregar.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btncomp_agregar.setText("Agregar Asiento al Detalle");
        btncomp_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7))
                            .addComponent(jTxtCta_Num, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addComponent(btncomp_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jTxtCta_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtCta_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtCta_Debe, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTxtCta_Haber, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btncomp_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncomp_eliminar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtCta_Num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncomp_buscar)
                    .addComponent(jTxtCta_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btncomp_agregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncomp_eliminar))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(6, 6, 6)
                            .addComponent(jTxtCta_Haber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(6, 6, 6)
                            .addComponent(jTxtCta_Debe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTxtCta_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setToolTipText("");
        bt_agregar.setFocusable(false);
        bt_agregar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bt_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setFocusable(false);
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setFocusable(false);
        bt_save.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setFocusable(false);
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setFocusable(false);
        bt_cancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setFocusable(false);
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setFocusable(false);
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLeyenda1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda1.setText("Leyenda 1 de teclas Rapidas");

        jLeyenda2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda2.setText("Leyenda 2 de Teclas Rapidas");

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setFocusable(false);
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SiguienteActionPerformed(evt);
            }
        });

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setFocusable(false);
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLeyenda2)
                            .addComponent(jLeyenda1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bt_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_save)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(bt_imprimir)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bt_Eliminar))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(bt_Atras)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bt_Siguiente)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(bt_cancel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bt_find)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bt_salir))
                                    .addComponent(bt_Modificar))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_agregar)
                                    .addComponent(bt_save))
                                .addComponent(bt_imprimir)
                                .addComponent(bt_Eliminar)
                                .addComponent(bt_cancel))
                            .addComponent(bt_salir, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(bt_find))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_Atras)
                    .addComponent(bt_Siguiente)
                    .addComponent(bt_Modificar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLeyenda1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLeyenda2)
                .addContainerGap(22, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here: 
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_bt_salirActionPerformed

    private void jTxtCta_NumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCta_NumKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //aGREGAR cODIGO DE BUSQUEDA DE CUENTA CONTABLE
        }

    }//GEN-LAST:event_jTxtCta_NumKeyPressed

    private void jTxtCom_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCom_CodigoFocusLost
        // TODO add your handling code here:
        int registros;

        ValidaCodigo consulta = new ValidaCodigo();
        registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNCOMPROBANTE WHERE COM_CODIGO='"+jTxtMon_Codigo.getText()+"'",false,"");

        if (registros==1){
            jTxtCom_Codigo.requestFocus();
            jTxtCom_Codigo.setText("");
        }
    }//GEN-LAST:event_jTxtCom_CodigoFocusLost

    private void detalle_contableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detalle_contableMouseClicked
        // TODO add your handling code here:
        modelActionListener.actualizaJtable(detalle_contable.getSelectedRow());
    }//GEN-LAST:event_detalle_contableMouseClicked

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        // TODO add your handling code here:
        Pantallas.Buscar BuscaMon = new Pantallas.Buscar();
        escritorio.add(BuscaMon);
        BuscaMon.show();
        BuscaMon.setVisible(true);
    }//GEN-LAST:event_bt_findActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void detalle_contableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detalle_contableKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(detalle_contable.getSelectedRow());
        }
    }//GEN-LAST:event_detalle_contableKeyPressed

    private void jTxtCta_MontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCta_MontoFocusLost
        // TODO add your handling code here:
         String monto2="";
        
        if ("".equals(jTxtCta_Monto.getText())){
            jTxtCta_Monto.setText("0,00");
        }else{
            monto2=jTxtCta_Monto.getText();
            monto2=monto2.replace(".",",");
           jTxtCta_Monto.setText(monto2);
        }
    }//GEN-LAST:event_jTxtCta_MontoFocusLost

    private void jTxtDoc_MontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtDoc_MontoFocusLost
        // TODO add your handling code here:

         String monto="";
        
        if ("".equals(jTxtDoc_Monto.getText())){
            jTxtDoc_Monto.setText("0,00");
        }else{
            monto=jTxtDoc_Monto.getText();
            monto=monto.replace(".",",");
           jTxtDoc_Monto.setText(monto);
        }
        
    }//GEN-LAST:event_jTxtDoc_MontoFocusLost

    private void detalle_contableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detalle_contableKeyReleased
        // TODO add your handling code here:
          if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(detalle_contable.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(detalle_contable.getSelectedRow());
        }
    }//GEN-LAST:event_detalle_contableKeyReleased

    private void bt_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_imprimirActionPerformed
        //Ver_Reporte(evt);
    }//GEN-LAST:event_bt_imprimirActionPerformed

    private void bt_SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SiguienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_SiguienteActionPerformed

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

    public class Hilo_Encabezado extends Thread{
        public void run(){
            if (lforward==true){
                cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())-1);
                
                if (Integer.valueOf(cNumero)<1){
                    return;
                }
            }else if (lnext==true){
                cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())+1);
                
                if (Integer.valueOf(cNumero)>Integer.valueOf(ultimo_reg)){
                    return;
                }
            }else{  
                if (UltimoRegBusq.equals("0000000000")){
                    cNumero= "0000000001";
                    jTxtCom_Codigo.setText(cNumero);
                    UltimoRegBusq=cNumero;
                }else{
                    cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())-1);
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
                        cNumero=jTxtCom_Codigo.getText().toString();
                        lBuscaDoc=false;
                        txt_buscar.setText("");
                    }
                }
            }
            System.out.println("Encabezado: "+jTxtCom_Codigo.getText());
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
                        jTxtCom_Codigo.setText(rs_encab.getString("ENC_CODIGO").toString().trim());
                        jTextEnc_CodMae.setText(rs_encab.getString("ENC_CODMAE").toString().trim());
                        jText_Mae_Nombre.setText(rs_encab.getString("MAE_NOMBRE").toString().trim());
                        //jTextEnc_Base.setText(rs_encab.getString("ENC_BASE"));
                        //jTextEnc_Iva.setText(rs_encab.getString("ENC_IVA"));
                        //jTextEnc_Monto.setText(rs_encab.getString("ENC_MONTO"));
                        jCboEnc_Condic.setSelectedItem(rs_encab.getString("ENC_CONDIC").trim());
                        //jCboEnc_Codven.setSelectedItem(rs_encab.getString("ENC_CODVEN").trim());
                        
                        //--------- Covierte el String de la Fecha en Date ----------
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fch = null;

                        try {
                            fch = sdf.parse(rs_encab.getString("ENC_FECHA"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        
                        jDateCom_Fechavig.setDate(fch);
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
                cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())-1);
                
                if (Integer.valueOf(cNumero)<1){
                    return;
                }
            }else if (lnext==true){
                cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())+1);
                
                if (Integer.valueOf(cNumero)>Integer.valueOf(ultimo_reg)){
                    return;
                }
            }else{
                cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())-1);
                //ultimo_reg=cNumero;
                if (cNumero.equals("0000000000")){
                    cNumero= "0000000001";
                    UltimoRegBusq=cNumero;
                    jTxtCom_Codigo.setText(cNumero);
                }else{
                    cNumero= String.format("%010d", Integer.valueOf(jTxtCom_Codigo.getText())-1);
                }
                ultimo_reg=UltimoRegBusq;
                
                if (lBuscaDoc==true){
                    if(Integer.valueOf(txt_buscar.getText().toString())<=Integer.valueOf(UltimoRegBusq)){
                        cNumero=String.format("%010d", Integer.valueOf(txt_buscar.getText().toString()));
                        lBuscaDoc=false;
                    }else{
                        cNumero=jTxtCom_Codigo.getText().toString();
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
    
    private void inicializaClase(){
        
        
        prueba.setClaseOrg(this.getClass().getName().toString());
        prueba.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        prueba.setClass(this);
        prueba.setJTable(detalle_contable, null, null, null);
        modelActionListener.setFecha(jDateCom_Fechavig);
        prueba.setJTextFieldComprobanteDif(jTxtCom_Codigo,jTxtNum_Doc, jTxtDoc_Monto, jTxtDescrip_Asi,jTxtCta_Num, jTxtCta_Descri, jTxtCta_Monto, jTxtCta_Debe, jTxtCta_Haber);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_imprimir;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton btncomp_agregar;
    private javax.swing.JButton btncomp_buscar;
    private javax.swing.JButton btncomp_eliminar;
    private javax.swing.JTable detalle_contable;
    public static com.toedter.calendar.JDateChooser jDateCom_Fechavig;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLeyenda1;
    private javax.swing.JLabel jLeyenda2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTxtCom_Codigo;
    private javax.swing.JTextField jTxtCta_Debe;
    private javax.swing.JTextField jTxtCta_Descri;
    private javax.swing.JTextField jTxtCta_Haber;
    private javax.swing.JTextField jTxtCta_Monto;
    private javax.swing.JTextField jTxtCta_Num;
    private javax.swing.JTextField jTxtDescrip_Asi;
    private javax.swing.JTextField jTxtDoc_Monto;
    private javax.swing.JTextField jTxtNum_Doc;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
