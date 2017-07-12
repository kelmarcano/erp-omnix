package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import Modelos.VariablesGlobales;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.conexion;
import java.awt.Color;
import java.sql.ResultSet;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CrearUsuarios extends javax.swing.JInternalFrame {
    public String combo;
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, SinRegistros = false, lAgregar, lModificar, lPass=false;
    private ResultSet rs, rs_count, rs_codigo, rs_permiso;
    private int Reg_count;
    public boolean cbo=false;
    private String codigo="", activo = null, Clave="";
    private Vector Msg, elementos, header_table;
    
    private conexion conet = new conexion();
    private ImageIcon img=null;
    private JPanel panelprincipal;
    private String extension="JPG";
    private JLabel limg;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener prueba = ModelActionListener.getPrueba();

    public CrearUsuarios() {
        initComponents();
        inicializaClase();

        jPanel1.setBackground(Color.decode(colorForm));
        
        jChkOpe_AccAndroid.setBackground(Color.decode(colorForm)); jChkOpe_Accweb.setBackground(Color.decode(colorForm));
        jChkOpe_Activo.setBackground(Color.decode(colorForm));
        jChkOpe_AccAndroid.setForeground(Color.decode(colorText)); jChkOpe_Accweb.setForeground(Color.decode(colorText));
        jChkOpe_Activo.setForeground(Color.decode(colorText));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText)); jLabel6.setForeground(Color.decode(colorText));
        jLabel7.setForeground(Color.decode(colorText)); jLabel8.setForeground(Color.decode(colorText));
        jLabel9.setForeground(Color.decode(colorText));
        
        jTxtOpe_Numero.setForeground(Color.decode(colorText)); jTxtOpe_Nombre.setForeground(Color.decode(colorText));
        jTxtOpe_Clave.setForeground(Color.decode(colorText)); jTxtOpe_Clave1.setForeground(Color.decode(colorText));
        jTxtOpe_Usuario.setForeground(Color.decode(colorText)); jTxtOpe_Correo.setForeground(Color.decode(colorText));
        jTxtOpe_RutaImg.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);
            
        jLabel1.setText((String) elementos.get(0));
        jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2));
        jLabel4.setText((String) elementos.get(3));
        jLabel5.setText((String) elementos.get(4));
        jLabel6.setText((String) elementos.get(5));
        jLabel7.setText((String) elementos.get(6));
        jLabel8.setText((String) elementos.get(7));
        jLabel9.setText((String) elementos.get(8));
        
        jChkOpe_Activo.setText((String) elementos.get(9));
        jChkOpe_Accweb.setText((String) elementos.get(10));
        jChkOpe_AccAndroid.setText((String) elementos.get(11));
        
        bt_agregar.setText((String) elementos.get(12));
        bt_Modificar.setText((String) elementos.get(13));
        bt_Eliminar.setText((String) elementos.get(14));
        bt_find.setText((String) elementos.get(15));
        bt_Atras.setText((String) elementos.get(16));
        bt_Siguiente.setText((String) elementos.get(17));
        bt_salir.setText((String) elementos.get(18));
        bt_save.setText((String) elementos.get(19));
        bt_cancel.setText((String) elementos.get(20));
        btn_buscarimg.setText((String) elementos.get(15));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------

        prueba.cargaTabla();
        
        if (Tabla.getRowCount()==0){
            prueba.setAccion("Inicializa");
            prueba.habilitar();
            prueba.posicion_botones_2();
            jTxtOpe_Nombre.requestFocus();
            
            lAgregar=true;
        }else{
            prueba.posicion_botones_1();
            prueba.Deshabilitar();
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            
            prueba.ejecutaHilo();
            Clave = prueba.getClave().toString();

            CargaImagen imagen = new CargaImagen();
            imagen.start();
            
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTxtOpe_Numero = new javax.swing.JTextField();
        jTxtOpe_Nombre = new javax.swing.JTextField();
        jTxtOpe_Clave = new javax.swing.JTextField();
        jTxtOpe_RutaImg = new javax.swing.JTextField();
        btn_buscarimg = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jCboOpe_tipo = new javax.swing.JComboBox();
        jCboOpe_Cargo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jChkOpe_Activo = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTxtOpe_Usuario = new javax.swing.JTextField();
        jFondo_Preview = new javax.swing.JLabel();
        jTxtOpe_Clave1 = new javax.swing.JPasswordField();
        jChkOpe_Accweb = new javax.swing.JCheckBox();
        jChkOpe_AccAndroid = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jTxtOpe_Correo = new javax.swing.JTextField();
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

        jTextField1.setText("jTextField1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 590, 130));

        jLabel1.setText("Codigo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel2.setText("Nombre Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel3.setText("Contraseña");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel8.setText("Ruta de Imagen");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));
        jPanel1.add(jTxtOpe_Numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 132, -1));

        jTxtOpe_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_NombreKeyPressed(evt);
            }
        });
        jPanel1.add(jTxtOpe_Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 170, -1));

        jTxtOpe_Clave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtOpe_ClaveFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtOpe_ClaveFocusLost(evt);
            }
        });
        jTxtOpe_Clave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_ClaveKeyPressed(evt);
            }
        });
        jPanel1.add(jTxtOpe_Clave, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 170, -1));
        jPanel1.add(jTxtOpe_RutaImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 320, -1));

        btn_buscarimg.setBackground(new java.awt.Color(255, 255, 255));
        btn_buscarimg.setText("Buscar");
        btn_buscarimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarimgActionPerformed(evt);
            }
        });
        jPanel1.add(btn_buscarimg, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, -1, 30));

        jLabel7.setText("Grupo de Permisologias de Usuario");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, -1));

        jCboOpe_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboOpe_tipoKeyPressed(evt);
            }
        });
        jPanel1.add(jCboOpe_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 180, -1));

        jCboOpe_Cargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboOpe_CargoKeyPressed(evt);
            }
        });
        jPanel1.add(jCboOpe_Cargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 170, -1));

        jLabel5.setText("Cargo");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jChkOpe_Activo.setBackground(new java.awt.Color(255, 255, 255));
        jChkOpe_Activo.setText("Activo");
        jPanel1.add(jChkOpe_Activo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        jLabel9.setText("Buscar:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
        });
        jPanel1.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 60, -1));

        jLabel4.setText("Usuario del Sistema");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jTxtOpe_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_UsuarioKeyPressed(evt);
            }
        });
        jPanel1.add(jTxtOpe_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 170, -1));

        jFondo_Preview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jFondo_Preview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/omnix_escritorio.png"))); // NOI18N
        jFondo_Preview.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jFondo_Preview, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 400, 160));

        jTxtOpe_Clave1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtOpe_Clave1FocusLost(evt);
            }
        });
        jTxtOpe_Clave1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_Clave1KeyPressed(evt);
            }
        });
        jPanel1.add(jTxtOpe_Clave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 170, -1));

        jChkOpe_Accweb.setBackground(new java.awt.Color(255, 255, 255));
        jChkOpe_Accweb.setText("Acceso Web");
        jPanel1.add(jChkOpe_Accweb, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, -1, -1));

        jChkOpe_AccAndroid.setBackground(new java.awt.Color(255, 255, 255));
        jChkOpe_AccAndroid.setText("Acceso Android");
        jPanel1.add(jChkOpe_AccAndroid, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, -1));

        jLabel6.setText("Correo Electronico");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jTxtOpe_Correo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_CorreoKeyPressed(evt);
            }
        });
        jPanel1.add(jTxtOpe_Correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 590, -1));

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        bt_agregar.setBackground(new java.awt.Color(255, 255, 255));
        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_agregar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_agregar);

        bt_Modificar.setBackground(new java.awt.Color(255, 255, 255));
        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_Modificar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Modificar);

        bt_save.setBackground(new java.awt.Color(255, 255, 255));
        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setText("Grabar");
        bt_save.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_save.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_save);

        bt_cancel.setBackground(new java.awt.Color(255, 255, 255));
        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setText("Cancelar");
        bt_cancel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_cancel.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_cancel);

        bt_Eliminar.setBackground(new java.awt.Color(255, 255, 255));
        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Eliminar);

        bt_find.setBackground(new java.awt.Color(255, 255, 255));
        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_find.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setBackground(new java.awt.Color(255, 255, 255));
        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Atras.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setBackground(new java.awt.Color(255, 255, 255));
        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Siguiente);

        bt_salir.setBackground(new java.awt.Color(255, 255, 255));
        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        bt_salir.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_salir);

        jPanel1.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 110, 450));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class CargaImagen extends Thread{
        public void run(){
            try {
                String SQL = "SELECT * FROM DNUSUARIOS WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+jTxtOpe_Numero.getText()+"'";

                rs = conet.consultar(SQL);
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNUSUARIOS WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+jTxtOpe_Numero.getText().toString()+"'";
                Reg_count = conet.Count_Reg(SQLReg);
                mostrarImagen();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(CrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
    }
    
    public void mostrarImagen() throws SQLException{
        if (Reg_count > 0){
            String ruta_img_fondo = rs.getString("OPE_RUTAIMG"); 
            jTxtOpe_RutaImg.setText(ruta_img_fondo);
            try{
                if (rs.getString("OPE_RUTAIMG")==null || ruta_img_fondo.trim().isEmpty()){
                    Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_AREA_AVERAGING));
                    jFondo_Preview.setIcon(icon);
                }else{
                    Image preview = Toolkit.getDefaultToolkit().getImage(rs.getString("OPE_RUTAIMG").trim());
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_AREA_AVERAGING));
                    jFondo_Preview.setIcon(icon);
                }
            }catch (Exception ex){
                Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_AREA_AVERAGING));
                jFondo_Preview.setIcon(icon);
            }
        }
    }
    
    public void buscarimagen(){
        JFileChooser fc = new JFileChooser();
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            File s = fc.getSelectedFile();
            String l = s.getAbsoluteFile().toString();
                
            jTxtOpe_RutaImg.setText(l);
            
            Image preview = Toolkit.getDefaultToolkit().getImage(jTxtOpe_RutaImg.getText());
            ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_DEFAULT));
            jFondo_Preview.setIcon(icon);
                
            System.out.println(s);
        }
    }

    private void btn_buscarimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarimgActionPerformed
        buscarimagen();        
    }//GEN-LAST:event_btn_buscarimgActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        jLabel9.setVisible(true); txt_buscar.setVisible(true);
        txt_buscar.setText(""); txt_buscar.requestFocus();
    }//GEN-LAST:event_bt_findActionPerformed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        prueba.actualizaJtable(Tabla.getSelectedRow());
    }//GEN-LAST:event_TablaMouseClicked

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //ActualizaJtable(Integer.valueOf(txt_buscar.getText()), true);
        }
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        String nombre = ""; String nomenclatura = ""; String valor = "";
        String fecha = ""; String activo = "";

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

    private void jTxtOpe_ClaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtOpe_ClaveFocusGained
        if (VarGlobales.getIdUser().equals(jTxtOpe_Numero.getText().toString())){
            jTxtOpe_Clave.setText(Clave);
        }
    }//GEN-LAST:event_jTxtOpe_ClaveFocusGained

    private void jTxtOpe_ClaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtOpe_ClaveFocusLost
        Clave = jTxtOpe_Clave.getText().toString();
                
        int LongClave = Clave.length();
        String clave_codif = "";
            
        for (int i=0; i<LongClave; i++){
            clave_codif= clave_codif+"*";
        }
        
        jTxtOpe_Clave.setText(clave_codif);
    }//GEN-LAST:event_jTxtOpe_ClaveFocusLost

    private void jTxtOpe_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if (lPass==true){
                jTxtOpe_Clave1.requestFocus();
            }else{
                jTxtOpe_Clave.requestFocus();
            }
        }
    }//GEN-LAST:event_jTxtOpe_NombreKeyPressed

    private void jTxtOpe_ClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_ClaveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtOpe_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_ClaveKeyPressed

    private void jTxtOpe_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_UsuarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCboOpe_Cargo.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_UsuarioKeyPressed

    private void jCboOpe_CargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboOpe_CargoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtOpe_Correo.requestFocus();
        }
    }//GEN-LAST:event_jCboOpe_CargoKeyPressed

    private void jCboOpe_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboOpe_tipoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btn_buscarimg.requestFocus();
        }
    }//GEN-LAST:event_jCboOpe_tipoKeyPressed

    private void jTxtOpe_Clave1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_Clave1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtOpe_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_Clave1KeyPressed

    private void jTxtOpe_Clave1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtOpe_Clave1FocusLost
        Clave = jTxtOpe_Clave1.getText().toString();
    }//GEN-LAST:event_jTxtOpe_Clave1FocusLost

    private void jTxtOpe_CorreoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_CorreoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtOpe_Correo.requestFocus();
            jCboOpe_tipo.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_CorreoKeyPressed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void inicializaClase(){
        prueba.setClaseOrg(this.getClass().getName().toString());
        prueba.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        prueba.setButtonOther(btn_buscarimg, null, null);
        prueba.setClass(this);
        prueba.setJCheckBox(jChkOpe_Activo, jChkOpe_AccAndroid, jChkOpe_Accweb);
        prueba.setJTable(Tabla, null, null, null);
        prueba.setImgDesktop(jFondo_Preview);
        prueba.setJTextField(jTxtOpe_Numero, jTxtOpe_Nombre, jTxtOpe_Clave, jTxtOpe_Clave1, jTxtOpe_Usuario, jTxtOpe_Correo, jTxtOpe_RutaImg, null);
        prueba.setJComboBox(jCboOpe_Cargo, jCboOpe_tipo, null, null, null);
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
    private javax.swing.JButton btn_buscarimg;
    private javax.swing.JComboBox jCboOpe_Cargo;
    private javax.swing.JComboBox jCboOpe_tipo;
    private javax.swing.JCheckBox jChkOpe_AccAndroid;
    private javax.swing.JCheckBox jChkOpe_Accweb;
    private javax.swing.JCheckBox jChkOpe_Activo;
    private javax.swing.JComboBox jComboBox1;
    public static javax.swing.JLabel jFondo_Preview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtOpe_Clave;
    private javax.swing.JPasswordField jTxtOpe_Clave1;
    private javax.swing.JTextField jTxtOpe_Correo;
    private javax.swing.JTextField jTxtOpe_Nombre;
    private javax.swing.JTextField jTxtOpe_Numero;
    private javax.swing.JTextField jTxtOpe_RutaImg;
    private javax.swing.JTextField jTxtOpe_Usuario;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}