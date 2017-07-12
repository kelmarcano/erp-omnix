package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import static Pantallas.principal.escritorio;
import Modelos.VariablesGlobales;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.SQLQuerys;
import clases.SQLSelect;
import clases.ValidaCodigo;
import clases.conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JInternalFrame;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class TipDocumentos extends javax.swing.JInternalFrame {
    public int fila, tab, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean SinRegistros = false, lAgregar=false, lModificar=false;
    public static boolean Bandera = false;
    private ResultSet rs, rs_count, rs_codigo, rs_menu, rs_menu_count, rs_permisologias, rs_menu_id;
    private int Reg_count, Reg_Tablas, nMenu;
    public static String origen;
    private String codigo="";
    private Vector Msg, elementos, header_table, Menu;
    private int ancho=0, posx=0;

    private String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    private String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    private static final String xmlFilePath ="E:\\NetBeansProjects\\Administrativo-Nube\\Localizaciones\\Español (es_VE)_Menu.xml";
    
//    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();
    
    public TipDocumentos() throws ClassNotFoundException, SQLException {
        initComponents();
  
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        jPanel3.setBackground(Color.decode(colorForm)); jPanel4.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText));
        
        jRadDoc_Libcom.setForeground(Color.decode(colorText)); jRadDoc_Libvta.setForeground(Color.decode(colorText));
        jRadDoc_cxc.setForeground(Color.decode(colorText)); jRadDoc_cxp.setForeground(Color.decode(colorText));
        
        jCheckDoc_Activo.setForeground(Color.decode(colorText)); jCheckDoc_Fisico.setForeground(Color.decode(colorText));
        jCheckDoc_Iva.setForeground(Color.decode(colorText)); jCheckDoc_Logico.setForeground(Color.decode(colorText));
        jCheckDoc_RetIslr.setForeground(Color.decode(colorText)); jCheckDoc_RetIva.setForeground(Color.decode(colorText));
        jCheckMenu.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2));
        
        jCheckDoc_Activo.setText((String) elementos.get(3)); jCheckDoc_Iva.setText((String) elementos.get(4));
        jCheckDoc_RetIslr.setText((String) elementos.get(5)); jCheckDoc_RetIva.setText((String) elementos.get(6));
        jCheckDoc_Fisico.setText((String) elementos.get(7)); jCheckDoc_Logico.setText((String) elementos.get(8));
        
        jRadDoc_Libcom.setText((String) elementos.get(9)); jRadDoc_Libvta.setText((String) elementos.get(10));
        jRadDoc_cxc.setText((String) elementos.get(11)); jRadDoc_cxp.setText((String) elementos.get(12));
        
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(13), fila, fila, null, Color.decode(colorText)));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(14), fila, fila, null, Color.decode(colorText)));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(15), fila, fila, null, Color.decode(colorText)));
        
        Tab.setTitleAt(0, (String) elementos.get(16)); Tab.setTitleAt(1, (String) elementos.get(17));
        
        bt_agregar.setText((String) elementos.get(18)); bt_Modificar.setText((String) elementos.get(19));
        bt_Eliminar.setText((String) elementos.get(20)); bt_Atras.setText((String) elementos.get(21)); 
        bt_Siguiente.setText((String) elementos.get(22)); bt_salir.setText((String) elementos.get(23)); 
        bt_save.setText((String) elementos.get(24)); bt_cancel.setText((String) elementos.get(25));
        bt_find.setText((String) elementos.get(26));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        Limitar();
        
        this.jCheckDoc_Activo.setSelected(true);
        this.setTitle("Documentos");
        
        if (Idioma.substring(0, Idioma.indexOf(" ")).equals("Español")){
            ancho=620; posx=480;
            //this.setSize(ancho, 470);
        }else if (Idioma.substring(0, Idioma.indexOf(" ")).equals("English")){
            ancho=650; posx=520;
            this.setSize(ancho, 470);
        }
        
        valida_reg_tablas();
        
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
        jTextDoc_Codigo.setDocument(new LimitarCaracteres(jTextDoc_Codigo, 3));
        jTextDoc_Descri.setDocument(new LimitarCaracteres(jTextDoc_Descri, 80));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextDoc_Codigo = new javax.swing.JTextField();
        jTextDoc_Descri = new javax.swing.JTextField();
        jCheckDoc_Activo = new javax.swing.JCheckBox();
        jRadDoc_cxc = new javax.swing.JRadioButton();
        jRadDoc_cxp = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jRadDoc_Libcom = new javax.swing.JRadioButton();
        jRadDoc_Libvta = new javax.swing.JRadioButton();
        jCheckDoc_Iva = new javax.swing.JCheckBox();
        jCheckDoc_RetIslr = new javax.swing.JCheckBox();
        jCheckDoc_RetIva = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jCheckDoc_Fisico = new javax.swing.JCheckBox();
        jCheckDoc_Logico = new javax.swing.JCheckBox();
        jComboDoc_InvAct = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jCheckMenu = new javax.swing.JCheckBox();
        Tab = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_Ventas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla_Compras = new javax.swing.JTable();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel1.setText("Codigo");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel2.setText("Descripcion");

        jTextDoc_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextDoc_CodigoFocusLost(evt);
            }
        });
        jTextDoc_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDoc_CodigoKeyPressed(evt);
            }
        });

        jCheckDoc_Activo.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jCheckDoc_Activo.setSelected(true);
        jCheckDoc_Activo.setText("Activo");
        jCheckDoc_Activo.setToolTipText("");

        jRadDoc_cxc.setText("Doc Ventas");
        jRadDoc_cxc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadDoc_cxcActionPerformed(evt);
            }
        });

        jRadDoc_cxp.setText("Doc Compras");
        jRadDoc_cxp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadDoc_cxpActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Impuestos"));

        jRadDoc_Libcom.setText("Libro de Compras");

        jRadDoc_Libvta.setText("Libro de Ventas");

        jCheckDoc_Iva.setText("Calcula I.V.A");

        jCheckDoc_RetIslr.setText("Retención de ISLR");

        jCheckDoc_RetIva.setText("Retención de I.V.A");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadDoc_Libvta)
                    .addComponent(jRadDoc_Libcom)
                    .addComponent(jCheckDoc_RetIva)
                    .addComponent(jCheckDoc_RetIslr)
                    .addComponent(jCheckDoc_Iva))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadDoc_Libcom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadDoc_Libvta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckDoc_Iva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckDoc_RetIslr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckDoc_RetIva)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Inventario"));

        jCheckDoc_Fisico.setText("Fisico");

        jCheckDoc_Logico.setText("Lógico");

        jComboDoc_InvAct.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suma", "Resta" }));

        jLabel3.setText("Existencia");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboDoc_InvAct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCheckDoc_Logico, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                .addComponent(jCheckDoc_Fisico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jCheckDoc_Fisico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckDoc_Logico)
                .addGap(7, 7, 7)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboDoc_InvAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jCheckMenu.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jCheckMenu.setSelected(true);
        jCheckMenu.setText("Agregar al Menu");
        jCheckMenu.setToolTipText("");
        jCheckMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckMenuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(jTextDoc_Codigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDoc_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadDoc_cxc)
                            .addComponent(jCheckDoc_Activo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jRadDoc_cxp)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckMenu)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jCheckDoc_Activo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextDoc_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextDoc_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadDoc_cxc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadDoc_cxp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckMenu))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        Tab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TabStateChanged(evt);
            }
        });

        Tabla_Ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Activo"
            }
        ));
        Tabla_Ventas.setShowHorizontalLines(false);
        Tabla_Ventas.setShowVerticalLines(false);
        Tabla_Ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_VentasMouseClicked(evt);
            }
        });
        Tabla_Ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_VentasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_VentasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla_Ventas);

        Tab.addTab("Documentos de Ventas", jScrollPane2);

        Tabla_Compras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Activo"
            }
        ));
        Tabla_Compras.setShowHorizontalLines(false);
        Tabla_Compras.setShowVerticalLines(false);
        Tabla_Compras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_ComprasMouseClicked(evt);
            }
        });
        Tabla_Compras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla_ComprasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla_ComprasKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla_Compras);

        Tab.addTab("Documentos de Compras", jScrollPane3);

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

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_cancel);

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tab, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        Tab.getAccessibleContext().setAccessibleName("Documentos de Ventas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadDoc_cxcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadDoc_cxcActionPerformed
        modelActionListener.actionRadioButton("Ventas");
        Tab.setSelectedIndex(0);
    }//GEN-LAST:event_jRadDoc_cxcActionPerformed

    private void jRadDoc_cxpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadDoc_cxpActionPerformed
        modelActionListener.actionRadioButton("Compras");
        Tab.setSelectedIndex(1);
    }//GEN-LAST:event_jRadDoc_cxpActionPerformed

    private void Tabla_VentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_VentasKeyPressed
        String descrip = ""; String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtableTipDoc("Ventas", Tabla_Ventas.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_VentasKeyPressed

    private void jTextDoc_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextDoc_CodigoFocusLost
        int registros;
        
        if(this.Bandera==false && (lAgregar==true || lModificar==true)){
            System.out.println("Paso para validar Codigo");
            ValidaCodigo consulta = new ValidaCodigo();
            registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNDOCUMENTOS WHERE DOC_CODIGO='"+jTextDoc_Codigo.getText().trim()+"'",false,"");

            if (registros==1){
                jTextDoc_Codigo.requestFocus();
                jTextDoc_Codigo.setText("");
            }
        }    
    }//GEN-LAST:event_jTextDoc_CodigoFocusLost

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        Pantallas.Buscar BuscaDoc = new Pantallas.Buscar();

        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = BuscaDoc.getSize();
        BuscaDoc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
                
        escritorio.add(BuscaDoc);
        BuscaDoc.show();
        BuscaDoc.setVisible(true);
    }//GEN-LAST:event_bt_findActionPerformed

    private void Tabla_ComprasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ComprasKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtableTipDoc("Ventas", Tabla_Compras.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ComprasKeyPressed

    private void TabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TabStateChanged
        inicializaClase();
        modelActionListener.cargaTabla();
        
        if (Tab.getSelectedIndex() == 0){
            tab = Tab.getSelectedIndex();
            jRadDoc_cxc.setSelected(true);
            jRadDoc_cxp.setSelected(false);

            valida_reg_tablas();
            modelActionListener.actionRadioButton("Ventas");
            
            modelActionListener.ejecutaHilo();
            
            atras=-2; adelante=0; lAgregar=false; lModificar=false;
        }
        if (Tab.getSelectedIndex() == 1){
            tab = Tab.getSelectedIndex();
            jRadDoc_cxp.setSelected(true);
            jRadDoc_cxc.setSelected(false);

            valida_reg_tablas();
            modelActionListener.actionRadioButton("Compras");
            modelActionListener.ejecutaHilo();
            
            atras=-2; adelante=0; lAgregar=false; lModificar=false;
        }
    }//GEN-LAST:event_TabStateChanged

    private void jTextDoc_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDoc_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTextDoc_Descri.requestFocus();
        }
    }//GEN-LAST:event_jTextDoc_CodigoKeyPressed

    private void Tabla_VentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_VentasKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtableTipDoc("Ventas", Tabla_Ventas.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtableTipDoc("Ventas", Tabla_Ventas.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_VentasKeyReleased

    private void Tabla_VentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_VentasMouseClicked
        modelActionListener.actualizaJtableTipDoc("Ventas", Tabla_Ventas.getSelectedRow());
    }//GEN-LAST:event_Tabla_VentasMouseClicked

    private void Tabla_ComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_ComprasMouseClicked
        modelActionListener.actualizaJtableTipDoc("Compras", Tabla_Compras.getSelectedRow());
    }//GEN-LAST:event_Tabla_ComprasMouseClicked

    private void Tabla_ComprasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla_ComprasKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtableTipDoc("Compras", Tabla_Compras.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtableTipDoc("Compras", Tabla_Compras.getSelectedRow());
        }
    }//GEN-LAST:event_Tabla_ComprasKeyReleased

    private void jCheckMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckMenuMouseClicked
        String sql, Menu_Id = null;
        
        String Id_Menu = "SELECT * FROM DNMENU WHERE MEN_NOMBRE='"+jTextDoc_Descri.getText().toString()+"' AND MEN_TIPO=2";
        try {
            rs_menu_id = conet.consultar(Id_Menu);
            Menu_Id = String.format("%06d", rs_menu_id.getInt("MEN_ID"));

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            if (rs_menu_id.getRow()>0){
                if (jCheckMenu.isSelected()==true){
                    String Permis = "SELECT * FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            
                    try {
                        rs_permisologias = conet.consultar(Permis);

                        if (rs_permisologias.getRow()>0){
                            rs_permisologias.first();
                    
                            for (int i=0; i<rs_permisologias.getRow(); i++){
                                SQLQuerys modificar = new SQLQuerys();
                                modificar.SqlUpdate("UPDATE dnpermisologia SET MIS_ACTIVO=1 "+
                                                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_MENU="+rs_menu_id.getInt("MEN_ID")+" AND "+
                                                    "MIS_TIPOMENU=2");
                        
                                rs_permisologias.next();
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    String Permis = "SELECT * FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            
                    try {
                        rs_permisologias = conet.consultar(Permis);

                        if (rs_permisologias.getRow()>0){
                            rs_permisologias.first();
                    
                            for (int i=0; i<rs_permisologias.getRow(); i++){
                                SQLQuerys modificar = new SQLQuerys();
                                modificar.SqlUpdate("UPDATE dnpermisologia SET MIS_ACTIVO=0 "+
                                                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_MENU="+rs_menu_id.getInt("MEN_ID")+" AND "+
                                                    "MIS_TIPOMENU=2");
                        
                                rs_permisologias.next();
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
//                barra.removeAll();
//                barra.revalidate();
 
//                CargaMenu MenuPrincipal = new CargaMenu();
//                try {
//                    MenuPrincipal.CargaMenuPrincipal(false);
//                } catch (SQLException ex) {
//                    Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
                Class Formulario;
                Formulario = Class.forName("Pantallas.ProgressBarRefrescaMenu");
                JInternalFrame Form = (JInternalFrame) Formulario.newInstance();
                                        
                Dimension desktopSize = escritorio.getSize();
                Dimension jInternalFrameSize = Form.getSize();
                Form.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
                
                escritorio.add(Form);
                Form.show();
            }else{
                if (jCheckMenu.isSelected()==true){
                    SQLQuerys insertar = new SQLQuerys();
                    if (tab==0){
                        sql = "INSERT INTO DNMENU (MEN_EMPRESA,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,"+
                                                  "MEN_TIPO,MEN_EXTERNO) "+
                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"',12,'"+jTextDoc_Descri.getText().toString()+"',"+
                                                  "'"+jTextDoc_Descri.getText().toString()+"',"+
                                                  "'clases.LlamaFormParamt;Form(Documentos,Internal,"+jTextDoc_Codigo.getText().toString()+","+jTextDoc_Descri.getText().toString()+")',"+
                                                  ""+(nMenu)+",2,0);";
                    }else{
                        sql = "INSERT INTO DNMENU (MEN_EMPRESA,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,"+
                                                  "MEN_TIPO,MEN_EXTERNO) "+
                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"',13,'"+jTextDoc_Descri.getText().toString()+"',"+
                                                  "'"+jTextDoc_Descri.getText().toString()+"',"+
                                                  "'clases.LlamaFormParamt;Form(Documentos,Internal,"+jTextDoc_Codigo.getText().toString()+","+jTextDoc_Descri.getText().toString()+")',"+
                                                  ""+(nMenu)+",2,0);";
                    }

                    insertar.SqlInsert(sql);

                    String Id_Menu2 = "SELECT * FROM DNMENU WHERE MEN_NOMBRE='"+jTextDoc_Descri.getText().toString()+"' AND MEN_TIPO=2";
                    try {
                        rs_menu_id = conet.consultar(Id_Menu2);
                        Menu_Id = String.format("%06d", rs_menu_id.getInt("MEN_ID"));

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
                    String Permis = "SELECT * FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
                    try {
                        rs_permisologias = conet.consultar(Permis);

                        if (rs_permisologias.getRow()>0){
                            rs_permisologias.first();
                    
                            for (int i=0; i<rs_permisologias.getRow(); i++){
                            //while(rs_permisologias.next()) {
                                sql = "INSERT INTO DNPERMISOLOGIA (MIS_EMPRESA,MIS_ID,MIS_PERMISO,MIS_MENU,MIS_TIPOMENU,MIS_TIPOPER,"+
                                                                  "MIS_ACTIVO) "+
                                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+Menu_Id+"_"+rs_permisologias.getString("PER_ID")+"_2_0',"+
                                                                   rs_permisologias.getInt("PER_ID")+","+
                                                                   ""+rs_menu_id.getInt("MEN_ID")+","+
                                                                   "2,0,1);";

                                insertar.SqlInsert(sql);
                                rs_permisologias.next();
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
//**************************************************************************************************************************
                    try {
                        SAXBuilder builder = new SAXBuilder();
                        File xmlFile = new File(xmlFilePath);
 
                        Document doc = (Document) builder.build(xmlFile);
                        Element rootNode = doc.getRootElement();
 
                        String output_Menu = jTextDoc_Descri.getText().toString();
                        for (int i=0; i<original.length(); i++) {
                            // Reemplazamos los caracteres especiales.
                            output_Menu = output_Menu.replace(original.charAt(i), ascii.charAt(i));
                        }
                        Element form = new Element("form_principal_"+output_Menu.replace(" ", "_"));
                        form.setAttribute(new Attribute("id", rs_menu_id.getString("MEN_ID")));
                        
                        Element values = new Element("value");
                
                        Element menu = new Element("Menu");
                        menu.setText(jTextDoc_Descri.getText().toString());
                
                        values.addContent(menu);
                        form.addContent(values);
                        rootNode.addContent(form);
 
                        XMLOutputter xmlOutput = new XMLOutputter();
                        xmlOutput.setFormat(Format.getPrettyFormat());
                        xmlOutput.output(doc, new FileWriter(xmlFilePath));
 
                        System.out.println("XML File updated successfully!");
                    } catch (IOException io) {
                        io.printStackTrace();
                    } catch (JDOMException e) {
                        e.printStackTrace();
                    }

 //                   barra.removeAll();
 //                   barra.revalidate();
 
 //                   CargaMenu MenuPrincipal = new CargaMenu();
 //                   try {
 //                       MenuPrincipal.CargaMenuPrincipal(false);
 //                   } catch (SQLException ex) {
 //                       Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
 //                   }
                    Class Formulario;
                    Formulario = Class.forName("Pantallas.ProgressBarRefrescaMenu");
                    JInternalFrame Form = (JInternalFrame) Formulario.newInstance();
                                        
                    Dimension desktopSize = escritorio.getSize();
                    Dimension jInternalFrameSize = Form.getSize();
                    Form.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
                
                    escritorio.add(Form);
                    Form.show();
//**************************************************************************************************************************
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TipDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jCheckMenuMouseClicked

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated
       
    private void valida_reg_tablas(){
        tab=Tab.getSelectedIndex();
        
        if (tab==0){
            Reg_Tablas=Tabla_Ventas.getRowCount();
        }
        if (tab==1){
            Reg_Tablas=Tabla_Compras.getRowCount();
        }

        if (Reg_Tablas==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();

            jTextDoc_Codigo.requestFocus();
            
            lAgregar=true;
        }else{
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();
            
            if (tab==0){
                Tabla_Ventas.getSelectionModel().setSelectionInterval(Tabla_Ventas.getRowCount()-1, Tabla_Ventas.getRowCount()-1);
            }
            if (tab==1){
                Tabla_Compras.getSelectionModel().setSelectionInterval(Tabla_Compras.getRowCount()-1, Tabla_Compras.getRowCount()-1);
            }
        }
    }
    
    private void inicializaClase(){
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setClass(this);
        modelActionListener.setJCheckBoxTipDoc(jCheckDoc_Activo, jCheckDoc_Iva, jCheckDoc_RetIslr, jCheckDoc_RetIva, jCheckDoc_Fisico, jCheckDoc_Logico, jCheckMenu);
        modelActionListener.setJRadioButton(jRadDoc_Libcom, jRadDoc_Libvta, jRadDoc_cxc, jRadDoc_cxp);
        modelActionListener.setJTable(Tabla_Ventas, Tabla_Compras, null, null);
        modelActionListener.setJComboBox(jComboDoc_InvAct, null, null, null, null);
        modelActionListener.setJTabbedPane(Tab);
        modelActionListener.setJTextField(jTextDoc_Codigo, jTextDoc_Descri, null, null, null, null, null, null);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tab;
    private javax.swing.JTable Tabla_Compras;
    private javax.swing.JTable Tabla_Ventas;
    private javax.swing.JButton bt_Atras;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Modificar;
    private javax.swing.JButton bt_Siguiente;
    private javax.swing.JButton bt_agregar;
    private javax.swing.JButton bt_cancel;
    private javax.swing.JButton bt_find;
    private javax.swing.JButton bt_salir;
    private javax.swing.JButton bt_save;
    public static javax.swing.JCheckBox jCheckDoc_Activo;
    public static javax.swing.JCheckBox jCheckDoc_Fisico;
    public static javax.swing.JCheckBox jCheckDoc_Iva;
    public static javax.swing.JCheckBox jCheckDoc_Logico;
    public static javax.swing.JCheckBox jCheckDoc_RetIslr;
    public static javax.swing.JCheckBox jCheckDoc_RetIva;
    public static javax.swing.JCheckBox jCheckMenu;
    private javax.swing.JComboBox jComboDoc_InvAct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JRadioButton jRadDoc_Libcom;
    public static javax.swing.JRadioButton jRadDoc_Libvta;
    public static javax.swing.JRadioButton jRadDoc_cxc;
    public static javax.swing.JRadioButton jRadDoc_cxp;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextField jTextDoc_Codigo;
    public static javax.swing.JTextField jTextDoc_Descri;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}