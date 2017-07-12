package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import static Pantallas.principal.escritorio;
import Modelos.VariablesGlobales;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.ValidaCodigo;
import clases.conexion;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.table.TableCellEditor;

public class UnidadMedida extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    public static boolean Bandera = false;
    private boolean lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo="";
    private Vector Msg, elementos, header_table;

    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();
    
    public UnidadMedida() throws ClassNotFoundException, SQLException{
        initComponents();
        inicializaClase();

        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText));
        
        jChkMed_Activo.setForeground(Color.decode(colorText)); jChkMed_Mulcxu.setForeground(Color.decode(colorText));
        
        jTxtMed_CanUnd.setForeground(Color.decode(colorText)); jTxtMed_Codigo.setForeground(Color.decode(colorText));
        jTxtMed_Descri.setForeground(Color.decode(colorText)); jTxtMed_Peso.setForeground(Color.decode(colorText));
        jTxtMed_Volume.setForeground(Color.decode(colorText));
        
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
        
        jChkMed_Activo.setText((String) elementos.get(6)); jChkMed_Mulcxu.setText((String) elementos.get(7));
        
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(8), fila, fila, null, Color.decode(colorText)));
        
        bt_agregar.setText((String) elementos.get(9)); bt_Modificar.setText((String) elementos.get(10));
        bt_Eliminar.setText((String) elementos.get(11)); bt_Atras.setText((String) elementos.get(12)); 
        bt_Siguiente.setText((String) elementos.get(13)); bt_salir.setText((String) elementos.get(14)); 
        bt_save.setText((String) elementos.get(15)); bt_cancel.setText((String) elementos.get(16));
        bt_find.setText((String) elementos.get(17));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        Limitar();
        modelActionListener.cargaTabla();
        
        this.jChkMed_Activo.setSelected(true);
        this.setTitle("Unidad de Medida");
        
        if (Tabla.getRowCount()==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();
            jTxtMed_Codigo.requestFocus();
            
            lAgregar=true;
        }else{
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();
            
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
    
    public void Limitar(){
        jTxtMed_Codigo.setDocument(new LimitarCaracteres(jTxtMed_Codigo, 3));
        jTxtMed_Descri.setDocument(new LimitarCaracteres(jTxtMed_Descri, 60));
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTxtMed_Codigo = new javax.swing.JTextField();
        jTxtMed_Descri = new javax.swing.JTextField();
        jChkMed_Activo = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jTxtMed_CanUnd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTxtMed_Peso = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTxtMed_Volume = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jChkMed_Mulcxu = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setBackground(new java.awt.Color(255, 255, 255));
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel2.setText("Descripción:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel1.setText("Codigo:");

        jTxtMed_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMed_CodigoFocusLost(evt);
            }
        });
        jTxtMed_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMed_CodigoKeyPressed(evt);
            }
        });

        jTxtMed_Descri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMed_DescriKeyPressed(evt);
            }
        });

        jChkMed_Activo.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jChkMed_Activo.setSelected(true);
        jChkMed_Activo.setText("Activo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel3.setText("Cantidad de Unidades:");

        jTxtMed_CanUnd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMed_CanUnd.setText("1.0000");
        jTxtMed_CanUnd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMed_CanUndFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMed_CanUndFocusLost(evt);
            }
        });
        jTxtMed_CanUnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMed_CanUndKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel4.setText("Signo:");

        jCboMen_Signo.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jCboMen_Signo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "/ Divide", "* Multiplica" }));

        jTxtMed_Peso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMed_Peso.setText("0.000");
        jTxtMed_Peso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMed_PesoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMed_PesoFocusLost(evt);
            }
        });
        jTxtMed_Peso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMed_PesoKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel5.setText("Peso:");

        jTxtMed_Volume.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtMed_Volume.setText("0.000");
        jTxtMed_Volume.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtMed_VolumeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtMed_VolumeFocusLost(evt);
            }
        });
        jTxtMed_Volume.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtMed_VolumeKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel6.setText("Volumen Metros Cúbicos:");

        jChkMed_Mulcxu.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jChkMed_Mulcxu.setText("Multiplica el resultado por Unidades");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTxtMed_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTxtMed_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jChkMed_Activo)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtMed_CanUnd, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jCboMen_Signo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtMed_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jTxtMed_Volume, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jChkMed_Mulcxu))
                        .addGap(0, 117, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jChkMed_Activo))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtMed_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtMed_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtMed_CanUnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCboMen_Signo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtMed_Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtMed_Volume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jChkMed_Mulcxu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción"
            }
        ));
        Tabla.setShowHorizontalLines(false);
        Tabla.setShowVerticalLines(false);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 487, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(146, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jMenu2.setText("Reportes");
        jMenuBar1.add(jMenu2);

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

    private void jTxtMed_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_CodigoFocusLost
        int registros;

        if (this.Bandera==false){
            ValidaCodigo consulta = new ValidaCodigo();
            registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE MED_CODIGO='"+jTxtMed_Codigo.getText().toUpperCase()+"'",false,"");

            if (registros==1){
                jTxtMed_Codigo.requestFocus();
                jTxtMed_Codigo.setText("");
            }
        }    
    }//GEN-LAST:event_jTxtMed_CodigoFocusLost

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        String descrip = ""; String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        
        TableCellEditor celltable = Tabla.getCellEditor();  //--> Trae la celda que se esta editando

        if(Tabla != null)
        {
            celltable.stopCellEditing(); //--> Detiene la edicion de la celda
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        Pantallas.Buscar BuscaUnd = new Pantallas.Buscar();
        
        escritorio.add(BuscaUnd);
        BuscaUnd.show();
        BuscaUnd.setVisible(true);
    }//GEN-LAST:event_bt_findActionPerformed

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void jTxtMed_DescriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMed_DescriKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMed_CanUnd.requestFocus();
        }
    }//GEN-LAST:event_jTxtMed_DescriKeyPressed

    private void jTxtMed_CanUndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMed_CanUndKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMed_Peso.requestFocus();
        }
    }//GEN-LAST:event_jTxtMed_CanUndKeyPressed

    private void jTxtMed_PesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMed_PesoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtMed_Volume.requestFocus();
        }
    }//GEN-LAST:event_jTxtMed_PesoKeyPressed

    private void jTxtMed_VolumeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMed_VolumeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_jTxtMed_VolumeKeyPressed

    private void jTxtMed_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtMed_CodigoKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            jTxtMed_Descri.requestFocus();
        }
    }//GEN-LAST:event_jTxtMed_CodigoKeyPressed

    private void jTxtMed_CanUndFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_CanUndFocusGained
        jTxtMed_CanUnd.setText("");
    }//GEN-LAST:event_jTxtMed_CanUndFocusGained

    private void jTxtMed_PesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_PesoFocusGained
        jTxtMed_Peso.setText("");
    }//GEN-LAST:event_jTxtMed_PesoFocusGained

    private void jTxtMed_VolumeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_VolumeFocusGained
        jTxtMed_Volume.setText("");
    }//GEN-LAST:event_jTxtMed_VolumeFocusGained

    private void jTxtMed_CanUndFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_CanUndFocusLost
        DecimalFormat formato = new DecimalFormat("#.0000");

        if (jTxtMed_CanUnd.getText().isEmpty() || Double.valueOf(jTxtMed_CanUnd.getText())<=0){
            jTxtMed_CanUnd.setText("1.0000");
        }else{
            jTxtMed_CanUnd.setText(formato.format(Double.valueOf(jTxtMed_CanUnd.getText().replace(",", "."))));
        }
    }//GEN-LAST:event_jTxtMed_CanUndFocusLost

    private void jTxtMed_PesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_PesoFocusLost
        DecimalFormat formato = new DecimalFormat("#.000");
        
        if (jTxtMed_Peso.getText().isEmpty()){
            jTxtMed_Peso.setText("0.000");
        }else{
            jTxtMed_Peso.setText(formato.format(Double.valueOf(jTxtMed_Peso.getText().replace(",", "."))));
        }
    }//GEN-LAST:event_jTxtMed_PesoFocusLost

    private void jTxtMed_VolumeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtMed_VolumeFocusLost
        DecimalFormat formato = new DecimalFormat("#.0000");
        
        if (jTxtMed_Volume.getText().isEmpty()){
            jTxtMed_Volume.setText("0.000");
        }else{
            String cant = jTxtMed_Volume.getText().toString();
            cant = cant.replace(",", ".");
            jTxtMed_Volume.setText(formato.format(Double.valueOf(cant)));
        }
    }//GEN-LAST:event_jTxtMed_VolumeFocusLost

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated
    
    private void inicializaClase(){
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setClass(this);
        modelActionListener.setJCheckBox(jChkMed_Activo, jChkMed_Mulcxu, null);
        modelActionListener.setJTable(Tabla, null, null, null);
        modelActionListener.setJComboBox(jCboMen_Signo, null, null, null, null);
        modelActionListener.setJTextFieldUnidadM(jTxtMed_Codigo, jTxtMed_Descri, jTxtMed_CanUnd, jTxtMed_Peso, jTxtMed_Volume);
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
    public static final javax.swing.JComboBox jCboMen_Signo = new javax.swing.JComboBox();
    public static javax.swing.JCheckBox jChkMed_Activo;
    public static javax.swing.JCheckBox jChkMed_Mulcxu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JTextField jTxtMed_CanUnd;
    public static javax.swing.JTextField jTxtMed_Codigo;
    public static javax.swing.JTextField jTxtMed_Descri;
    public static javax.swing.JTextField jTxtMed_Peso;
    public static javax.swing.JTextField jTxtMed_Volume;
    // End of variables declaration//GEN-END:variables
}