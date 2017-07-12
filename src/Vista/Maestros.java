package Vista;

import Listener.ListenerButton;
import static Vista.Login.Idioma;
import static Pantallas.principal.Maestro;
import static Pantallas.principal.escritorio;
import Modelos.VariablesGlobales;
import Pantallas.Listas;
import Modelos.ModelActionListener;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.CreaValidaRif;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.ValidaCodigo;
import clases.conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
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

public class Maestros extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0, tab;
    private SQLSelect Registros;
    private boolean Bandera = false, SinRegistros = false, lAgregar, lModificar, lEnter;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count, regTablas=0;    
    private String codigo = "", nombre = "", tipmae = "", valor_cli = "", valor_pro = "";
    private String limite = "0.00", dcto = "0", condic = "", dias = "0", observ = "0", rif = "";
    private String nit = "", contrib = "", activi = "", reside = "", zonacom = "", codmon = "";
    private String mtoven = "0.00", diaven = "0", contriesp = "", coddir = "", otromon = "", activo = "";
    private String fecha = ""; String retiva = "0"; String foto = "", ruta, Ultimo_Reg="";
    private int MAX_WIDTH_IMG=150; //Ancho máximo
    private int MAX_HEIGHT_IMG=150; //Alto máximo
    private Vector Msg, elementos, header_table;
    
    CargaTablas cargatabla = null;
    clases.conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();

    public Maestros() throws SQLException {
        initComponents();
        //inicializaClase();
        
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        jPanel3.setBackground(Color.decode(colorForm)); jPanel4.setBackground(Color.decode(colorForm));
        jPanel8.setBackground(Color.decode(colorForm)); jPanel10.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel4.setForeground(Color.decode(colorText)); jLabel5.setForeground(Color.decode(colorText)); 
        jLabel6.setForeground(Color.decode(colorText)); jLabel8.setForeground(Color.decode(colorText));
        jLabel9.setForeground(Color.decode(colorText)); jLabel10.setForeground(Color.decode(colorText)); 
        jLabel11.setForeground(Color.decode(colorText)); jLabel12.setForeground(Color.decode(colorText)); 
        jLabel13.setForeground(Color.decode(colorText)); jLabel14.setForeground(Color.decode(colorText)); 
        jLabel15.setForeground(Color.decode(colorText)); jLabel16.setForeground(Color.decode(colorText)); 
        jLabel17.setForeground(Color.decode(colorText)); jLabel18.setForeground(Color.decode(colorText)); 
        jLabel19.setForeground(Color.decode(colorText)); jLabel20.setForeground(Color.decode(colorText)); 
        jLabel21.setForeground(Color.decode(colorText));
        
        jCheckMae_Activo.setForeground(Color.decode(colorText)); jCheckMae_Cliente.setForeground(Color.decode(colorText));
        jCheckMae_Proveed.setForeground(Color.decode(colorText));
        
        jTxtMAE_Observ.setForeground(Color.decode(colorText)); jTxtMae_Activi.setForeground(Color.decode(colorText));
        jTxtMae_Codigo.setForeground(Color.decode(colorText)); jTxtMae_Codmon.setForeground(Color.decode(colorText));
        jTxtMae_Codtip.setForeground(Color.decode(colorText)); jTxtMae_Dcto.setForeground(Color.decode(colorText));
        jTxtMae_Dias.setForeground(Color.decode(colorText)); jTxtMae_Diaven.setForeground(Color.decode(colorText));
        jTxtMae_Limite.setForeground(Color.decode(colorText)); jTxtMae_Mtoven.setForeground(Color.decode(colorText));
        jTxtMae_Nit.setForeground(Color.decode(colorText)); jTxtMae_Nombre.setForeground(Color.decode(colorText));
        jTxtMae_Retiva.setForeground(Color.decode(colorText)); jTxtMae_Rif.setForeground(Color.decode(colorText));
        
        cargatabla = new CargaTablas();
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);
            
        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLabel4.setText((String) elementos.get(3));
        jLabel5.setText((String) elementos.get(4)); jLabel6.setText((String) elementos.get(5));
        jLabel8.setText((String) elementos.get(7));
        jLabel9.setText((String) elementos.get(8)); jLabel10.setText((String) elementos.get(9));
        jLabel11.setText((String) elementos.get(10)); jLabel12.setText((String) elementos.get(11));
        jLabel13.setText((String) elementos.get(12)); jLabel14.setText((String) elementos.get(13));
        jLabel15.setText((String) elementos.get(14)); jLabel16.setText((String) elementos.get(15));
        jLabel17.setText((String) elementos.get(16)); jLabel18.setText((String) elementos.get(17));
        jLabel19.setText((String) elementos.get(18)); jLabel20.setText((String) elementos.get(19));
        jLabel21.setText((String) elementos.get(20));
        
        jCheckMae_Activo.setText((String) elementos.get(21)); jCheckMae_Cliente.setText((String) elementos.get(22));
        jCheckMae_Proveed.setText((String) elementos.get(23));
        
        bt_agregar.setText((String) elementos.get(24)); bt_Modificar.setText((String) elementos.get(25));
        bt_Eliminar.setText((String) elementos.get(26)); bt_find.setText((String) elementos.get(27));
        bt_Atras.setText((String) elementos.get(28)); bt_Siguiente.setText((String) elementos.get(29));
        bt_salir.setText((String) elementos.get(30)); bt_save.setText((String) elementos.get(31));
        bt_cancel.setText((String) elementos.get(32));
        
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(34), fila, fila, null, Color.decode(colorText)));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(35), fila, fila, null, Color.decode(colorText)));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(36), fila, fila, null, Color.decode(colorText)));
        
        Tab.setTitleAt(0, (String) elementos.get(37)); Tab.setTitleAt(1, (String) elementos.get(38));
        Tab.setTitleAt(2, (String) elementos.get(39)); Tab.setTitleAt(3, (String) elementos.get(40));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------

        Numeros(jTxtMae_Limite); Numeros(jTxtMae_Dcto); Numeros(jTxtMae_Mtoven);
        Numeros(jTxtMae_Dias); Numeros(jTxtMae_Diaven);
        jCheckMae_Activo.setSelected(true); jTxtMae_Codtip.setEditable(false);
        jTxtMae_Activi.setEditable(false); jTxtMae_Codmon.setEditable(false);
  
        this.setTitle((String) elementos.get(41));
        this.jTxtMae_Nombre.requestFocus();

        if (Maestro.equals("Clie")){
            Tab.setSelectedIndex(0);
            regTablas = Tabla_Cliente.getRowCount();
        }else if (Maestro.equals("Prov")){
            Tab.setSelectedIndex(1);
            regTablas = Tabla_Proveedor.getRowCount();
        }
        
        //valida_reg_tablas();
        
        bt_agregar.addActionListener(new ListenerButton());
        bt_Modificar.addActionListener(new ListenerButton());
        bt_save.addActionListener(new ListenerButton());
        bt_Eliminar.addActionListener(new ListenerButton());
        bt_cancel.addActionListener(new ListenerButton());
        bt_Atras.addActionListener(new ListenerButton());
        bt_Siguiente.addActionListener(new ListenerButton());
        bt_salir.addActionListener(new ListenerButton());
    }
/*    
    public void mostrar() throws SQLException{
        if (Reg_count > 0){
            jTxtMae_Codigo.setText(rs.getString("MAE_CODIGO").trim()); jTxtMae_Nombre.setText(rs.getString("MAE_NOMBRE").trim());
            jTxtMae_Limite.setText(rs.getString("MAE_LIMITE").replace(".", ",")); 
            jTxtMae_Dcto.setText(rs.getString("MAE_DCTO")); jCboMae_Condic.setSelectedItem(rs.getString("MAE_CONDIC").trim()); 

            if (rs.getBoolean("MAE_ACTIVO")==true){
                jCheckMae_Activo.setSelected(true);
            }else{
                jCheckMae_Activo.setSelected(false);
            }
            jTxtMae_Dias.setText(rs.getString("MAE_DIAS")); 
            
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fch = null;

            System.out.println("Fechas: "+rs.getString("MAE_FECHA"));
            try {
                if (rs.getString("MAE_FECHA")==null ){
                    Date fecha = new Date();
                    String Fch = sdf.format(fecha);
                    fch = sdf.parse(Fch);

                    System.out.println("Entro Fechas vacia: "+rs.getString("MAE_FECHA"));
                }else{
                    fch = sdf.parse(rs.getString("MAE_FECHA"));
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            
            jDateMae_Fecha.setDate(fch);
            //-----------------------------------------------------------

            if (rs.getString("MAE_OBSERV")==null){
                jTxtMAE_Observ.setText("");
            }else{
                jTxtMAE_Observ.setText(rs.getString("MAE_OBSERV"));
            }
            if (rs.getString("MAE_CODTIPM")==null){
                jTxtMae_Codtip.setText("");
            }else{
                jTxtMae_Codtip.setText(rs.getString("MAE_CODTIPM").trim());
            }       
            if (rs.getString("MAE_CODACT")==null){
                jTxtMae_Activi.setText("");
            }else{
                jTxtMae_Activi.setText(rs.getString("MAE_CODACT"));
            }

            jTxtMae_Rif.setText(rs.getString("MAE_RIF").trim()); jTxtMae_Nit.setText(rs.getString("MAE_NIT").trim());
            jCmbMae_Contri.setSelectedItem(rs.getString("MAE_CONTRI").trim()); jCmbMae_Reside.setSelectedItem(rs.getString("MAE_RESIDE").trim());
            jCmbMae_Zoncom.setSelectedItem(rs.getString("MAE_ZONCOM").trim()); jTxtMae_Codmon.setText(rs.getString("MAE_CODMON").trim());
            jCmbMae_Contesp.setSelectedItem(rs.getString("MAE_CONTESP").trim()); jTxtMae_Retiva.setText(rs.getString("MAE_RETIVA").trim());
            jTxtMae_Mtoven.setText(rs.getString("MAE_MTOVEN").replace(".", ",")); 
            jTxtMae_Diaven.setText(rs.getString("MAE_DIAVEN"));

            if (rs.getBoolean("MAE_CLIENTE")==true){
                jCheckMae_Cliente.setSelected(true);
            }else{
                jCheckMae_Cliente.setSelected(false);
            }

            if (rs.getBoolean("MAE_PROVEED")==true){
                jCheckMae_Proveed.setSelected(true);
            }else{
                jCheckMae_Proveed.setSelected(false);
            }
            
            Deshabilitar();
        }else{
            Limpiar();
            Mascara_Campos_Num();
            habilitar("Inicializa");
            CodConsecutivo();
        }
    }
*/
    public void Limitar(){
        this.jTxtMae_Codigo.setDocument(new LimitarCaracteres(jTxtMae_Codigo, 10));
        this.jTxtMae_Nombre.setDocument(new LimitarCaracteres(jTxtMae_Nombre, 120));
        this.jTxtMae_Rif.setDocument(new LimitarCaracteres(jTxtMae_Rif, 12));
        this.jTxtMae_Nit.setDocument(new LimitarCaracteres(jTxtMae_Nit, 12));
        this.jTxtMae_Codtip.setDocument(new LimitarCaracteres(jTxtMae_Codtip, 10));
        this.jTxtMae_Limite.setDocument(new LimitarCaracteres(jTxtMae_Limite, 15));
        this.jTxtMae_Dcto.setDocument(new LimitarCaracteres(jTxtMae_Dcto, 3));
        this.jTxtMae_Mtoven.setDocument(new LimitarCaracteres(jTxtMae_Mtoven, 15));
        this.jTxtMae_Dias.setDocument(new LimitarCaracteres(jTxtMae_Dias, 3));
        this.jTxtMae_Diaven.setDocument(new LimitarCaracteres(jTxtMae_Diaven, 3));
        this.jTxtMae_Activi.setDocument(new LimitarCaracteres(jTxtMae_Activi, 6));
        this.jTxtMae_Codmon.setDocument(new LimitarCaracteres(jTxtMae_Codmon, 6));
        this.jTxtMae_Retiva.setDocument(new LimitarCaracteres(jTxtMae_Retiva, 3));
    }

    public void Mascara_Campos_Num(){
        jTxtMae_Limite.setText("0,00"); jTxtMae_Dcto.setText("0");
        jTxtMae_Mtoven.setText("0,00"); jTxtMae_Dias.setText("0");
        jTxtMae_Diaven.setText("0"); jTxtMae_Retiva.setText("0");

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
        jDateMae_Fecha.setDate(fch);
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

    public void validarRif(String sRif) throws IOException{
        CreaValidaRif datosfiscales = new CreaValidaRif();
        Vector DatosFiscales = datosfiscales.DatosFiscales(sRif);
     
        if (DatosFiscales.elementAt(0)=="El RIF ingresado es invalido...!"){
            jTxtMae_Rif.setText("");
            jTxtMae_Rif.requestFocus();
        } else{
            jTxtMae_Rif.setText((String) DatosFiscales.elementAt(0));
            jTxtMae_Nombre.setText((String) DatosFiscales.elementAt(1));        
            jCmbMae_Contri.setSelectedItem(DatosFiscales.elementAt(2));
            jCmbMae_Contesp.setSelectedItem(DatosFiscales.elementAt(3));
            jTxtMae_Retiva.setText((String) DatosFiscales.elementAt(4));
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

        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtMae_Codigo = new javax.swing.JTextField();
        jTxtMae_Nombre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTxtMae_Codtip = new javax.swing.JTextField();
        jBtnBusca_TipoMae = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jCheckMae_Activo = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtMAE_Observ = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTxtMae_Activi = new javax.swing.JTextField();
        jBtnBusca_Actividad = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jCmbMae_Reside = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jCmbMae_Zoncom = new javax.swing.JComboBox();
        jBtnBusca_CondMoneda = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTxtMae_Codmon = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTxtMae_Dias = new javax.swing.JFormattedTextField();
        jTxtMae_Mtoven = new javax.swing.JFormattedTextField();
        jTxtMae_Diaven = new javax.swing.JFormattedTextField();
        jTxtMae_Rif = new javax.swing.JFormattedTextField();
        jTxtMae_Nit = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jCmbMae_Contri = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jCmbMae_Contesp = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jTxtMae_Retiva = new javax.swing.JFormattedTextField();
        jTxtMae_Limite = new javax.swing.JFormattedTextField();
        jTxtMae_Dcto = new javax.swing.JFormattedTextField();
        jDateMae_Fecha = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jCheckMae_Cliente = new javax.swing.JCheckBox();
        jCheckMae_Proveed = new javax.swing.JCheckBox();
        Tab = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        Tabla_Cliente = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Tabla_Proveedor = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        Tabla_Todos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        Tabla_Otros = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        bt_agregar = new javax.swing.JButton();
        bt_Modificar = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        bt_Eliminar = new javax.swing.JButton();
        bt_cancel = new javax.swing.JButton();
        bt_Atras = new javax.swing.JButton();
        bt_Siguiente = new javax.swing.JButton();
        bt_find = new javax.swing.JButton();
        bt_salir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel4.setToolTipText("");
        jPanel4.setFont(new java.awt.Font("Roboto Light", 3, 14)); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel1.setText("Código:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel2.setText("Nombre:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jTxtMae_Codigo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtMae_CodigoActionPerformed(evt);
            }
        });
        jTxtMae_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_CodigoFocusLost(evt);
            }
        });
        jTxtMae_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_CodigoKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 190, -1));

        jTxtMae_Nombre.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_NombreKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 421, -1));

        jLabel16.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel16.setText("R.I.F.:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel17.setText("N.I.T.:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel4.setText("Tipo de Maestro:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jTxtMae_Codtip.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jPanel4.add(jTxtMae_Codtip, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 190, -1));

        jBtnBusca_TipoMae.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_TipoMae.setText("[...]");
        jBtnBusca_TipoMae.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_TipoMaeActionPerformed(evt);
            }
        });
        jBtnBusca_TipoMae.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBtnBusca_TipoMaeKeyPressed(evt);
            }
        });
        jPanel4.add(jBtnBusca_TipoMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel5.setText("Limite de Credito:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel12.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel12.setText("Descuento:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, -1, -1));

        jLabel13.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel13.setText("Condición:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, -1, -1));

        jCheckMae_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckMae_Activo.setText("Activo");
        jCheckMae_Activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckMae_ActivoActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckMae_Activo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel8.setText("Dias de Plazo:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jTxtMAE_Observ.setColumns(20);
        jTxtMAE_Observ.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMAE_Observ.setRows(5);
        jScrollPane2.setViewportView(jTxtMAE_Observ);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 260, 470, 120));

        jLabel21.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel21.setText("Observaciones:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, -1, -1));

        jLabel14.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel14.setText("Dias de Vencimiento:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, -1, -1));

        jLabel9.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel9.setText("Actividad Economica:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jTxtMae_Activi.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Activi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_ActiviKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Activi, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 70, -1));

        jBtnBusca_Actividad.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_Actividad.setText("[...]");
        jBtnBusca_Actividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_ActividadActionPerformed(evt);
            }
        });
        jBtnBusca_Actividad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBtnBusca_ActividadKeyPressed(evt);
            }
        });
        jPanel4.add(jBtnBusca_Actividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, -1, 30));

        jLabel10.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel10.setText("Recide en el Pais:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jCmbMae_Reside.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCmbMae_Reside.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Si", "No" }));
        jCmbMae_Reside.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbMae_ResideKeyPressed(evt);
            }
        });
        jPanel4.add(jCmbMae_Reside, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 66, 27));

        jLabel15.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel15.setText("Zona Comercial:");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jCmbMae_Zoncom.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCmbMae_Zoncom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Nacional", "Libre" }));
        jCmbMae_Zoncom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbMae_ZoncomKeyPressed(evt);
            }
        });
        jPanel4.add(jCmbMae_Zoncom, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, 100, 27));

        jBtnBusca_CondMoneda.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_CondMoneda.setText("[...]");
        jBtnBusca_CondMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_CondMonedaActionPerformed(evt);
            }
        });
        jBtnBusca_CondMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBtnBusca_CondMonedaKeyPressed(evt);
            }
        });
        jPanel4.add(jBtnBusca_CondMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, -1, -1));

        jLabel11.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel11.setText("Tipo de Moneda:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jTxtMae_Codmon.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jPanel4.add(jTxtMae_Codmon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 100, -1));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel6.setText("Limite de Monto en Vencimiendo:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 220, -1));

        jTxtMae_Dias.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jTxtMae_Dias.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMae_Dias.setText("0");
        jTxtMae_Dias.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Dias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMae_DiasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_DiasFocusLost(evt);
            }
        });
        jTxtMae_Dias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_DiasKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Dias, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 70, -1));

        jTxtMae_Mtoven.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jTxtMae_Mtoven.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMae_Mtoven.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Mtoven.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMae_MtovenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_MtovenFocusLost(evt);
            }
        });
        jTxtMae_Mtoven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_MtovenKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Mtoven, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 117, -1));

        jTxtMae_Diaven.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jTxtMae_Diaven.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMae_Diaven.setText("0");
        jTxtMae_Diaven.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Diaven.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMae_DiavenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_DiavenFocusLost(evt);
            }
        });
        jTxtMae_Diaven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_DiavenKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Diaven, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 190, 80, -1));

        try {
            jTxtMae_Rif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-########-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jTxtMae_Rif.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Rif.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_RifFocusLost(evt);
            }
        });
        jTxtMae_Rif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_RifKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtMae_RifKeyTyped(evt);
            }
        });
        jPanel4.add(jTxtMae_Rif, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 150, -1));

        try {
            jTxtMae_Nit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jTxtMae_Nit.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Nit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_NitKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Nit, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 150, -1));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Fiscales"));

        jLabel18.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel18.setText("Contribuyente de I.V.A:");

        jCmbMae_Contri.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCmbMae_Contri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Si", "No" }));
        jCmbMae_Contri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbMae_ContriKeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel19.setText("Contribuyente Especial:");

        jCmbMae_Contesp.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCmbMae_Contesp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Si", "No" }));
        jCmbMae_Contesp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbMae_ContespKeyPressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel20.setText("% de Retencion:");

        jTxtMae_Retiva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jTxtMae_Retiva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMae_Retiva.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Retiva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMae_RetivaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_RetivaFocusLost(evt);
            }
        });
        jTxtMae_Retiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_RetivaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(21, 21, 21)
                        .addComponent(jCmbMae_Contri, 0, 61, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jCmbMae_Contesp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtMae_Retiva)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jCmbMae_Contri, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbMae_Contesp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtMae_Retiva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, -1, 140));

        jTxtMae_Limite.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jTxtMae_Limite.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMae_Limite.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Limite.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMae_LimiteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_LimiteFocusLost(evt);
            }
        });
        jTxtMae_Limite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_LimiteKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Limite, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 117, -1));

        jTxtMae_Dcto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jTxtMae_Dcto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMae_Dcto.setText("0");
        jTxtMae_Dcto.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtMae_Dcto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMae_DctoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMae_DctoFocusLost(evt);
            }
        });
        jTxtMae_Dcto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMae_DctoKeyPressed(evt);
            }
        });
        jPanel4.add(jTxtMae_Dcto, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 140, -1));
        jPanel4.add(jDateMae_Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 150, -1));

        jCboMae_Condic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboMae_CondicKeyPressed(evt);
            }
        });
        jPanel4.add(jCboMae_Condic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 140, 30));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Maestro"));
        jPanel1.setFont(new java.awt.Font("Roboto Light", 3, 14)); // NOI18N

        jCheckMae_Cliente.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckMae_Cliente.setText("Cliente");
        jCheckMae_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckMae_ClienteActionPerformed(evt);
            }
        });

        jCheckMae_Proveed.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckMae_Proveed.setText("Proveedor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckMae_Proveed)
                    .addComponent(jCheckMae_Cliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jCheckMae_Cliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckMae_Proveed))
        );

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        Tab.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        Tab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TabStateChanged(evt);
            }
        });

        Tabla_Cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_ClienteMouseClicked(evt);
            }
        });
        Tabla_Cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_ClienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_ClienteKeyReleased(evt);
            }
        });
        jScrollPane12.setViewportView(Tabla_Cliente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Tab.addTab("Clientes", jPanel2);

        Tabla_Proveedor.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Proveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_ProveedorMouseClicked(evt);
            }
        });
        Tabla_Proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_ProveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_ProveedorKeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(Tabla_Proveedor);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        Tab.addTab("Proveedores", jPanel3);

        Tabla_Todos.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Todos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_TodosMouseClicked(evt);
            }
        });
        Tabla_Todos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_TodosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_TodosKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(Tabla_Todos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        Tab.addTab("Todos", jPanel5);

        Tabla_Otros.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabla_Otros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_OtrosMouseClicked(evt);
            }
        });
        Tabla_Otros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_OtrosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_OtrosKeyReleased(evt);
            }
        });
        jScrollPane11.setViewportView(Tabla_Otros);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        Tab.addTab("Otros", jPanel6);

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

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Siguiente);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tab)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tab, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 600));

        jMenu1.setText("Sistema");

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reportes");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    private void jTxtMae_CodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtMae_CodigoActionPerformed
    }//GEN-LAST:event_jTxtMae_CodigoActionPerformed

    private void jTxtMae_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_CodigoFocusLost
    }//GEN-LAST:event_jTxtMae_CodigoFocusLost

    private void jTxtMae_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Nombre.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_CodigoKeyPressed

    private void jTxtMae_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jBtnBusca_TipoMae.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_NombreKeyPressed

    private void jBtnBusca_TipoMaeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_TipoMaeActionPerformed
        action_busca_tipmae();
    }//GEN-LAST:event_jBtnBusca_TipoMaeActionPerformed

    private void jCheckMae_ActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckMae_ActivoActionPerformed
    }//GEN-LAST:event_jCheckMae_ActivoActionPerformed

    private void jTxtMae_ActiviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_ActiviKeyPressed
    }//GEN-LAST:event_jTxtMae_ActiviKeyPressed

    private void jBtnBusca_ActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_ActividadActionPerformed
        action_busca_actividad();
    }//GEN-LAST:event_jBtnBusca_ActividadActionPerformed

    private void jCmbMae_ResideKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbMae_ResideKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCmbMae_Zoncom.requestFocus();
        }
    }//GEN-LAST:event_jCmbMae_ResideKeyPressed

    private void jCmbMae_ZoncomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbMae_ZoncomKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jBtnBusca_CondMoneda.requestFocus();
        }
    }//GEN-LAST:event_jCmbMae_ZoncomKeyPressed

    private void jBtnBusca_CondMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_CondMonedaActionPerformed
        action_busca_moneda();
    }//GEN-LAST:event_jBtnBusca_CondMonedaActionPerformed

    private void jTxtMae_DiasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_DiasFocusGained
        if (this.Bandera==false){
            dias = jTxtMae_Dias.getText().toString();
            jTxtMae_Dias.setText("");
        }
    }//GEN-LAST:event_jTxtMae_DiasFocusGained

    private void jTxtMae_DiasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_DiasFocusLost
        if ("".equals(jTxtMae_Dias.getText())){
            jTxtMae_Dias.setText("0");
            
            if(jTxtMae_Dias.getText().toString().equals("0")){
                jTxtMae_Dias.setText(dias);
            }
        }
    }//GEN-LAST:event_jTxtMae_DiasFocusLost

    private void jTxtMae_DiasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_DiasKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jBtnBusca_Actividad.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_DiasKeyPressed

    private void jTxtMae_MtovenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_MtovenFocusGained
        if (this.Bandera==false){
            mtoven = jTxtMae_Mtoven.getText().toString();
            jTxtMae_Mtoven.setText("");
        }
    }//GEN-LAST:event_jTxtMae_MtovenFocusGained

    private void jTxtMae_MtovenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_MtovenFocusLost
        if ("".equals(jTxtMae_Mtoven.getText())){
            jTxtMae_Mtoven.setText("0,00");
            if(jTxtMae_Mtoven.getText().toString().equals("0,00")){
                jTxtMae_Mtoven.setText(mtoven);
            }
        }
    }//GEN-LAST:event_jTxtMae_MtovenFocusLost

    private void jTxtMae_MtovenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_MtovenKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCboMae_Condic.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_MtovenKeyPressed

    private void jTxtMae_DiavenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_DiavenFocusGained
        if (this.Bandera==false){
            diaven = jTxtMae_Diaven.getText().toString();
            jTxtMae_Diaven.setText("");
        }
    }//GEN-LAST:event_jTxtMae_DiavenFocusGained

    private void jTxtMae_DiavenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_DiavenFocusLost
        if ("".equals(jTxtMae_Diaven.getText())){
            jTxtMae_Diaven.setText("0");
            
            if(jTxtMae_Diaven.getText().toString().equals("0")){
                jTxtMae_Diaven.setText(diaven);
            }
        }
    }//GEN-LAST:event_jTxtMae_DiavenFocusLost

    private void jTxtMae_DiavenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_DiavenKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Dias.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_DiavenKeyPressed

    private void jTxtMae_RifFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_RifFocusLost
        int registros;

        if ("-        -".equals(jTxtMae_Rif.getText().trim())){
            jTxtMae_Rif.setText("");

            return;
        }

        if (lAgregar==true || lModificar==true){
            try {
                validarRif(jTxtMae_Rif.getText());
            } catch (IOException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (lAgregar==true){
            ValidaCodigo consulta = new ValidaCodigo();
            registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_RIF='"+jTxtMae_Rif.getText().trim()+"'",false,"RIF");

            if (registros==1){
                jTxtMae_Rif.requestFocus();
                jTxtMae_Rif.setText("");
                jTxtMae_Nombre.setText("");
                jCmbMae_Contri.setSelectedItem("");
                jCmbMae_Contesp.setSelectedItem("");
            }
        }
    }//GEN-LAST:event_jTxtMae_RifFocusLost

    private void jTxtMae_RifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_RifKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Nit.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_RifKeyPressed

    private void jTxtMae_RifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_RifKeyTyped
        char caracter = evt.getKeyChar();

        // Verificar si la tecla pulsada no es un digito
        if(((caracter < '0') || (caracter > '9')) &&
            ((caracter != 'v') & (caracter != 'm') & (caracter != 'p') & (caracter != 'r') &
                (caracter != 'e') & (caracter != 'j') & (caracter != 'i') & (caracter != 'e')))
        {
            evt.consume();  // ignorar el evento de teclado
        }
    }//GEN-LAST:event_jTxtMae_RifKeyTyped

    private void jTxtMae_NitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_NitKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jBtnBusca_TipoMae.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_NitKeyPressed

    private void jCmbMae_ContriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbMae_ContriKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCmbMae_Contesp.requestFocus();
        }
    }//GEN-LAST:event_jCmbMae_ContriKeyPressed

    private void jCmbMae_ContespKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbMae_ContespKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Retiva.requestFocus();
        }
    }//GEN-LAST:event_jCmbMae_ContespKeyPressed

    private void jTxtMae_RetivaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_RetivaFocusGained
        if (this.Bandera==false){
            if (Integer.parseInt(jTxtMae_Retiva.getText())==0){
                jTxtMae_Retiva.setText("");
            }
        }
    }//GEN-LAST:event_jTxtMae_RetivaFocusGained

    private void jTxtMae_RetivaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_RetivaFocusLost
        if ("".equals(jTxtMae_Retiva.getText())){
            jTxtMae_Retiva.setText("0");
        }
    }//GEN-LAST:event_jTxtMae_RetivaFocusLost

    private void jTxtMae_RetivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_RetivaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jBtnBusca_Actividad.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_RetivaKeyPressed

    private void jTxtMae_LimiteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_LimiteFocusGained
        if (this.Bandera==false){
            limite = jTxtMae_Limite.getText().toString();
            jTxtMae_Limite.setText("");
        }
    }//GEN-LAST:event_jTxtMae_LimiteFocusGained

    private void jTxtMae_LimiteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_LimiteFocusLost
        if ("".equals(jTxtMae_Limite.getText())){
            jTxtMae_Limite.setText("0,00");
            if(jTxtMae_Limite.getText().toString().equals("0,00")){
                jTxtMae_Limite.setText(limite);
            }
        }
    }//GEN-LAST:event_jTxtMae_LimiteFocusLost

    private void jTxtMae_LimiteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_LimiteKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Dcto.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_LimiteKeyPressed

    private void jTxtMae_DctoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_DctoFocusGained
        if (this.Bandera==false){
            if(Double.valueOf(jTxtMae_Dcto.getText().toString())<100){
                dcto = jTxtMae_Dcto.getText().toString();
            }
            
            jTxtMae_Dcto.setText("");
        }
    }//GEN-LAST:event_jTxtMae_DctoFocusGained

    private void jTxtMae_DctoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMae_DctoFocusLost
        if ("".equals(jTxtMae_Dcto.getText())){
            jTxtMae_Dcto.setText("0");
            if(jTxtMae_Dcto.getText().toString().equals("0")){
                jTxtMae_Dcto.setText(dcto);
            }
        }else{
            if(Double.valueOf(jTxtMae_Dcto.getText().toString())>100){
                JOptionPane.showMessageDialog(null, "El Descuento no puede ser mayor al 100 %");
                jTxtMae_Dcto.requestFocus();
            }
        }
    }//GEN-LAST:event_jTxtMae_DctoFocusLost

    private void jTxtMae_DctoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMae_DctoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Mtoven.requestFocus();
        }
    }//GEN-LAST:event_jTxtMae_DctoKeyPressed

    private void jCheckMae_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckMae_ClienteActionPerformed
    }//GEN-LAST:event_jCheckMae_ClienteActionPerformed

    private void Tabla_ClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ClienteMouseClicked
        modelActionListener.actualizaJtableTipDoc("Clientes", Tabla_Cliente.getSelectedRow());
    }//GEN-LAST:event_Tabla_ClienteMouseClicked

    private void Tabla_ClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ClienteKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtableTipDoc("Clientes", Tabla_Cliente.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ClienteKeyPressed

    private void Tabla_ClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ClienteKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtableTipDoc("Clientes", Tabla_Cliente.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtableTipDoc("Clientes", Tabla_Cliente.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ClienteKeyReleased

    private void Tabla_ProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ProveedorMouseClicked
        modelActionListener.actualizaJtableTipDoc("Proveedores", Tabla_Proveedor.getSelectedRow());
    }//GEN-LAST:event_Tabla_ProveedorMouseClicked

    private void Tabla_ProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ProveedorKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtableTipDoc("Proveedores", Tabla_Proveedor.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ProveedorKeyPressed

    private void Tabla_TodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_TodosMouseClicked
        modelActionListener.actualizaJtableTipDoc("Todos", Tabla_Todos.getSelectedRow());
    }//GEN-LAST:event_Tabla_TodosMouseClicked

    private void Tabla_TodosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_TodosKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtableTipDoc("Todos", Tabla_Todos.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_TodosKeyPressed

    private void Tabla_OtrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_OtrosMouseClicked
        modelActionListener.actualizaJtableTipDoc("Otros", Tabla_Otros.getSelectedRow());
    }//GEN-LAST:event_Tabla_OtrosMouseClicked

    private void Tabla_OtrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_OtrosKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtableTipDoc("Otros", Tabla_Otros.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_OtrosKeyPressed
    
    private void TabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TabStateChanged
        inicializaClase();
        modelActionListener.cargaTabla();
        
        if (Tab.getSelectedIndex() == 0){
            tab = Tab.getSelectedIndex();
            jCheckMae_Cliente.setSelected(true);
            
            valida_reg_tablas();
            
            atras=-2; adelante  =0;
        }
        if (Tab.getSelectedIndex() == 1){
            tab = Tab.getSelectedIndex();
            jCheckMae_Proveed.setSelected(true);
            
            valida_reg_tablas();
            
            atras=-2; adelante=0;
        }
        if (Tab.getSelectedIndex() == 2){
            tab = Tab.getSelectedIndex();
            
            valida_reg_tablas();
            
            atras=-2; adelante=0;
        }
        if (Tab.getSelectedIndex() == 3){
            tab = Tab.getSelectedIndex();
            
            valida_reg_tablas();
            
            atras=-2; adelante=0;
        }
    }//GEN-LAST:event_TabStateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
    }//GEN-LAST:event_bt_findActionPerformed

    private void Tabla_ProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ProveedorKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtableTipDoc("Proveedores", Tabla_Proveedor.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtableTipDoc("Proveedores", Tabla_Proveedor.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ProveedorKeyReleased

    private void Tabla_TodosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_TodosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtableTipDoc("Todos", Tabla_Todos.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtableTipDoc("Todos", Tabla_Todos.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_TodosKeyReleased

    private void Tabla_OtrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_OtrosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtableTipDoc("Otros", Tabla_Otros.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtableTipDoc("Otros", Tabla_Otros.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_OtrosKeyReleased

    private void jBtnBusca_TipoMaeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBtnBusca_TipoMaeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_busca_tipmae();
        }
    }//GEN-LAST:event_jBtnBusca_TipoMaeKeyPressed

    private void jCboMae_CondicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboMae_CondicKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMae_Diaven.requestFocus();
        }
    }//GEN-LAST:event_jCboMae_CondicKeyPressed

    private void jBtnBusca_ActividadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBtnBusca_ActividadKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_busca_actividad();
        }
    }//GEN-LAST:event_jBtnBusca_ActividadKeyPressed

    private void jBtnBusca_CondMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBtnBusca_CondMonedaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_busca_moneda();
        }
    }//GEN-LAST:event_jBtnBusca_CondMonedaKeyPressed
    
    private void action_busca_tipmae(){
        Pantallas.Listas ListasTipoMae = new Pantallas.Listas();
        ListasTipoMae.titulo("Tipos de Maestros","Tipos de Maestros");

        String sqlCodigo = "SELECT TMA_CODIGO AS DATO1 FROM DNTIPOMAESTRO WHERE TMA_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String sqlDescrip = "SELECT TMA_NOMBRE AS DATO1 FROM DNTIPOMAESTRO WHERE TMA_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";

        this.SinRegistros = ListasTipoMae.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(7));
            jTxtMae_Limite.requestFocus();
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListasTipoMae.getSize();
            ListasTipoMae.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            
            ListasTipoMae.show();
            Listas.Tabla="DNTIPOMAESTRO";
            ListasTipoMae.setVisible(true);
        }
    }
    
    private void action_busca_actividad(){
        Pantallas.Listas ListsActiv = new Pantallas.Listas();
        ListsActiv.titulo("Actividad Economica","Actividad Economica");

        String sqlCodigo = "SELECT ACT_CODIGO AS DATO1 FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_ACTIVO='1'";
        String sqlDescrip = "SELECT ACT_NOMBRE AS DATO1 FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_ACTIVO='1'";

        this.SinRegistros = ListsActiv.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null, (String) Msg.get(8));
            jCmbMae_Reside.requestFocus();
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListsActiv.getSize();
            ListsActiv.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
                    
            ListsActiv.show();
            Listas.Tabla="DNACTIVIDAD_ECO";
            ListsActiv.setVisible(true);
        }
    }
    
    private void action_busca_moneda(){
        Pantallas.Listas ListasMoneda = new Pantallas.Listas();
        ListasMoneda.titulo("Tipo de Moneda","Tipo de Moneda");

        String sqlCodigo = "SELECT MON_CODIGO AS DATO1 FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String sqlDescrip = "SELECT MON_NOMBRE AS DATO1 FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";

        this.SinRegistros = ListasMoneda.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null, (String) Msg.get(9));
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListasMoneda.getSize();
            ListasMoneda.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            
            ListasMoneda.show();
            Listas.Tabla="DNMONEDA";
            ListasMoneda.setVisible(true);
        }
    }
    
    private void valida_reg_tablas(){
        tab=Tab.getSelectedIndex();
        
        if (tab==0){
            regTablas=Tabla_Cliente.getRowCount();
        }
        if (tab==1){
            regTablas=Tabla_Proveedor.getRowCount();
        }
        if (tab==2){
            regTablas=Tabla_Otros.getRowCount();
        }
        if (tab==3){
            regTablas=Tabla_Todos.getRowCount();
        }

        if (regTablas==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();

            jTxtMae_Rif.requestFocus();
            jCheckMae_Cliente.setSelected(true);
            
            lAgregar=true;
        }else{
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();
            
            if (tab==0){
                Tabla_Cliente.getSelectionModel().setSelectionInterval(Tabla_Cliente.getRowCount()-1, Tabla_Cliente.getRowCount()-1);
            }
            if (tab==1){
                Tabla_Proveedor.getSelectionModel().setSelectionInterval(Tabla_Cliente.getRowCount()-1, Tabla_Cliente.getRowCount()-1);
            }
        }
    }
    
    private void inicializaClase(){
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setButtonOther(jBtnBusca_TipoMae, jBtnBusca_Actividad, jBtnBusca_CondMoneda);
        modelActionListener.setClass(this);
        modelActionListener.setJTabbedPane(Tab);
        modelActionListener.setFecha(jDateMae_Fecha);
        modelActionListener.setJCheckBox(jCheckMae_Activo, jCheckMae_Cliente, jCheckMae_Proveed);
        modelActionListener.setJTable(Tabla_Cliente, Tabla_Proveedor, Tabla_Otros, Tabla_Todos);
        modelActionListener.setJComboBox(jCmbMae_Reside, jCmbMae_Zoncom, jCboMae_Condic, jCmbMae_Contri, jCmbMae_Contesp);
        modelActionListener.setJTextFieldMaestros(jTxtMae_Codigo, jTxtMae_Nombre, jTxtMae_Codtip, jTxtMae_Activi, jTxtMae_Codmon);
        modelActionListener.setJFormattedTextField(jTxtMae_Limite, jTxtMae_Mtoven, jTxtMae_Dias, jTxtMae_Rif, jTxtMae_Nit, jTxtMae_Dcto, jTxtMae_Diaven, jTxtMae_Retiva);
        modelActionListener.setValor(jTxtMae_Nit);
        modelActionListener.setJTextArea(jTxtMAE_Observ);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tab;
    private javax.swing.JTable Tabla_Cliente;
    private javax.swing.JTable Tabla_Otros;
    private javax.swing.JTable Tabla_Proveedor;
    private javax.swing.JTable Tabla_Todos;
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton jBtnBusca_Actividad;
    private javax.swing.JButton jBtnBusca_CondMoneda;
    private javax.swing.JButton jBtnBusca_TipoMae;
    public static final javax.swing.JComboBox jCboMae_Condic = new javax.swing.JComboBox();
    private javax.swing.JCheckBox jCheckMae_Activo;
    private javax.swing.JCheckBox jCheckMae_Cliente;
    private javax.swing.JCheckBox jCheckMae_Proveed;
    private javax.swing.JComboBox jCmbMae_Contesp;
    private javax.swing.JComboBox jCmbMae_Contri;
    public static javax.swing.JComboBox jCmbMae_Reside;
    private javax.swing.JComboBox jCmbMae_Zoncom;
    private com.toedter.calendar.JDateChooser jDateMae_Fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea jTxtMAE_Observ;
    public static javax.swing.JTextField jTxtMae_Activi;
    private javax.swing.JTextField jTxtMae_Codigo;
    public static javax.swing.JTextField jTxtMae_Codmon;
    public static javax.swing.JTextField jTxtMae_Codtip;
    private javax.swing.JFormattedTextField jTxtMae_Dcto;
    private javax.swing.JFormattedTextField jTxtMae_Dias;
    private javax.swing.JFormattedTextField jTxtMae_Diaven;
    public static javax.swing.JFormattedTextField jTxtMae_Limite;
    private javax.swing.JFormattedTextField jTxtMae_Mtoven;
    private javax.swing.JFormattedTextField jTxtMae_Nit;
    private javax.swing.JTextField jTxtMae_Nombre;
    private javax.swing.JFormattedTextField jTxtMae_Retiva;
    private javax.swing.JFormattedTextField jTxtMae_Rif;
    // End of variables declaration//GEN-END:variables
}