package Vista;

import Modelos.ModelActionListener;
import Listener.ListenerButton;
import static Vista.Login.Idioma;
import static Pantallas.principal.escritorio;
import Modelos.VariablesGlobales;
import Pantallas.Listas;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Precio extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, lAgregar, lModificar;
    private boolean SinRegistros = false;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo="";
    private Vector Msg, elementos, header_table;
    
    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();
    
    public Precio() throws SQLException, ClassNotFoundException {
        initComponents();
        inicializaClase();
        
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText));
        
        jCheckPre_Activo.setForeground(Color.decode(colorText));
        
        jTxtPre_CodProduc.setForeground(Color.decode(colorText)); jTxtPre_Codigo.setForeground(Color.decode(colorText));
        jTxtPre_Codlis.setForeground(Color.decode(colorText)); jTxtPre_Coduni.setForeground(Color.decode(colorText));
        jTxtPre_Monto.setForeground(Color.decode(colorText));
        jDatePre_Fechaven.setForeground(Color.decode(colorText));
        
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
        
        jCheckPre_Activo.setText((String) elementos.get(6));
        
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(7), fila, fila, null, Color.decode(colorText)));
        
        bt_agregar.setText((String) elementos.get(8)); bt_Modificar.setText((String) elementos.get(9));
        bt_Eliminar.setText((String) elementos.get(10)); bt_Atras.setText((String) elementos.get(11)); 
        bt_Siguiente.setText((String) elementos.get(12)); bt_salir.setText((String) elementos.get(13)); 
        bt_save.setText((String) elementos.get(14)); bt_cancel.setText((String) elementos.get(15));
        bt_find.setText((String) elementos.get(16));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        Limitar();
        Mascara_Campos_Num();
        modelActionListener.cargaTabla();
        
        Numeros(jTxtPre_Monto);
        this.setTitle("Precios");
        
        if (Tabla.getRowCount()==0){
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();
            jTxtPre_CodProduc.requestFocus();
            
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
        jTxtPre_Codigo.setDocument(new LimitarCaracteres(jTxtPre_Codigo, 10));
        jTxtPre_CodProduc.setDocument(new LimitarCaracteres(jTxtPre_CodProduc, 15));
        jTxtPre_Codlis.setDocument(new LimitarCaracteres(jTxtPre_Codlis, 8));
        jTxtPre_Coduni.setDocument(new LimitarCaracteres(jTxtPre_Coduni, 8));
        jTxtPre_Monto.setDocument(new LimitarCaracteres(jTxtPre_Monto, 15));
    }
    
    public void Mascara_Campos_Num(){
       jTxtPre_Monto.setText("0,00");

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
        jDatePre_Fechaven.setDate(fch);
//-----------------------------------------------------------
    }
    
    public void CargaDatosLista(String Codigo){
        jTxtPre_CodProduc.setText(Codigo);
        jTxtPre_CodProduc.requestFocus();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtPre_Codigo = new javax.swing.JTextField();
        jTxtPre_CodProduc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTxtPre_Codlis = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTxtPre_Coduni = new javax.swing.JTextField();
        jDatePre_Fechaven = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jTxtPre_Monto = new javax.swing.JFormattedTextField();
        jBtnBusca_Productos = new javax.swing.JButton();
        jBtnBusca_TipPrecio = new javax.swing.JButton();
        jBtnBusca_UniMed = new javax.swing.JButton();
        jCheckPre_Activo = new javax.swing.JCheckBox();
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
        jPanel2.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel1.setText("Código:");

        jLabel2.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel2.setText("Código Producto:");

        jTxtPre_Codigo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPre_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtPre_CodigoFocusLost(evt);
            }
        });
        jTxtPre_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPre_CodigoKeyPressed(evt);
            }
        });

        jTxtPre_CodProduc.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPre_CodProduc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPre_CodProducKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel3.setText("Tipo de Precio:");

        jLabel4.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel4.setText("Precio:");

        jTxtPre_Codlis.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPre_Codlis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPre_CodlisKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel5.setText("Unidad de Medida:");

        jTxtPre_Coduni.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPre_Coduni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPre_CoduniKeyPressed(evt);
            }
        });

        jDatePre_Fechaven.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jDatePre_Fechaven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDatePre_FechavenKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel6.setText("Fecha:");

        jTxtPre_Monto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jTxtPre_Monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtPre_Monto.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtPre_Monto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPre_MontoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtPre_MontoFocusLost(evt);
            }
        });
        jTxtPre_Monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtPre_MontoKeyPressed(evt);
            }
        });

        jBtnBusca_Productos.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_Productos.setText("[...]");
        jBtnBusca_Productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_ProductosActionPerformed(evt);
            }
        });

        jBtnBusca_TipPrecio.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_TipPrecio.setText("[...]");
        jBtnBusca_TipPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_TipPrecioActionPerformed(evt);
            }
        });

        jBtnBusca_UniMed.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        jBtnBusca_UniMed.setText("[...]");
        jBtnBusca_UniMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBusca_UniMedActionPerformed(evt);
            }
        });

        jCheckPre_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckPre_Activo.setText("Activo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtPre_Codigo)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTxtPre_Coduni, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnBusca_UniMed))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtPre_CodProduc)
                                    .addComponent(jTxtPre_Codlis))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBtnBusca_TipPrecio)
                                    .addComponent(jBtnBusca_Productos))))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDatePre_Fechaven, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckPre_Activo)))
                    .addComponent(jTxtPre_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtPre_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckPre_Activo))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtPre_CodProduc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnBusca_Productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6)))
                    .addComponent(jDatePre_Fechaven, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jTxtPre_Codlis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBusca_TipPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtPre_Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtPre_Coduni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jBtnBusca_UniMed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        bt_agregar.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Modificar.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setLabel("Grabar");
        bt_save.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_save);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_cancel);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_find.setPreferredSize(new java.awt.Dimension(100, 39));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Atras.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_salir.setPreferredSize(new java.awt.Dimension(100, 39));
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtPre_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPre_CodigoFocusLost
        int registros;

        ValidaCodigo consulta = new ValidaCodigo();
        registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNPRECIO WHERE PRE_CODIGO='"+jTxtPre_Codigo.getText().trim()+"'",false,"");

        if (registros==1){
            jTxtPre_Codigo.requestFocus();
            jTxtPre_Codigo.setText("");
        }
    }//GEN-LAST:event_jTxtPre_CodigoFocusLost

    private void jTxtPre_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPre_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtPre_CodProduc.requestFocus();
        }
    }//GEN-LAST:event_jTxtPre_CodigoKeyPressed

    private void jTxtPre_CodProducKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPre_CodProducKeyPressed
        if (this.Bandera==true){
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                bt_Modificar.requestFocus();
            }
        }
        else{
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                jTxtPre_Codlis.requestFocus();
            }
        }
    }//GEN-LAST:event_jTxtPre_CodProducKeyPressed

    private void jTxtPre_CodlisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPre_CodlisKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtPre_Monto.requestFocus();
        }
    }//GEN-LAST:event_jTxtPre_CodlisKeyPressed

    private void jTxtPre_CoduniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPre_CoduniKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jDatePre_Fechaven.requestFocus();
        }
    }//GEN-LAST:event_jTxtPre_CoduniKeyPressed

    private void jTxtPre_MontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPre_MontoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jBtnBusca_UniMed.requestFocus();
        }
    }//GEN-LAST:event_jTxtPre_MontoKeyPressed

    private void jBtnBusca_ProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_ProductosActionPerformed
        Pantallas.Listas ListasProd = new Pantallas.Listas();
        ListasProd.titulo("Lista de Productos","Lista de Productos");

        String sqlCodigo = "SELECT PRO_CODIGO AS DATO1 FROM DNPRODUCTO ORDER BY PRO_CODIGO";
        String sqlDescrip = "SELECT PRO_DESCRI AS DATO1 FROM DNPRODUCTO ORDER BY PRO_CODIGO";

        this.SinRegistros = ListasProd.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(0));
            
            int eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1));

	    if (eleccion == 0){
                Vista.Productos produ = null;
                produ = new Vista.Productos();
                
                Dimension desktopSize = escritorio.getSize();
                Dimension jInternalFrameSize = produ.getSize();
                produ.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        
                escritorio.add(produ);
                produ.show(); 
                produ.setVisible(true);
               
	    }
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListasProd.getSize();
            ListasProd.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            
            ListasProd.show();
            Listas.Tabla="DNPRODUCTO";
            ListasProd.setVisible(true);
        }
    }//GEN-LAST:event_jBtnBusca_ProductosActionPerformed

    private void jBtnBusca_TipPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_TipPrecioActionPerformed
        // TODO add your handling code here:
        Pantallas.Listas ListasTipPre = new Pantallas.Listas ();
        ListasTipPre.titulo("Lista de Tipos de Precios","Lista de Tipos de Precios");

        String sqlCodigo = "SELECT LIS_CODIGO AS DATO1 FROM DNLISTPRE";
        String sqlDescrip = "SELECT LIS_DESCRI AS DATO1 FROM DNLISTPRE";

        this.SinRegistros = ListasTipPre.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(2));
            
            int eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(3));

	    if (eleccion == 0){
                Pantallas.ListaPrecios TipPrecios = null;
                try {
                    TipPrecios = new Pantallas.ListaPrecios();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Precio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Precio.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                Dimension desktopSize = escritorio.getSize();
                Dimension jInternalFrameSize = TipPrecios.getSize();
                TipPrecios.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        
                escritorio.add(TipPrecios);
                TipPrecios.show(); 
                TipPrecios.setVisible(true);
	    }
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListasTipPre.getSize();
            ListasTipPre.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            
            ListasTipPre.show();
            Listas.Tabla="DNLISTPRE";
            ListasTipPre.setVisible(true);
        }
    }//GEN-LAST:event_jBtnBusca_TipPrecioActionPerformed

    private void jBtnBusca_UniMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBusca_UniMedActionPerformed
       Pantallas.Listas ListasUnidad = new Pantallas.Listas();
        ListasUnidad.titulo("Lista de Unidades de Medida","Lista de Unidades de Medida");

        String sqlCodigo = "SELECT MED_CODIGO AS DATO1 FROM DNUNDMEDIDA ORDER BY MED_ID";
        String sqlDescrip = "SELECT MED_DESCRI AS DATO1 FROM DNUNDMEDIDA ORDER BY MED_ID";

        this.SinRegistros = ListasUnidad.CargaListas(sqlCodigo,sqlDescrip);

        if (this.SinRegistros==true){
            JOptionPane.showMessageDialog(null,(String) Msg.get(4));
            
            int eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(5));

	    if (eleccion == 0){
                Vista.UnidadMedida UndMed = null;
                try {
                    UndMed = new Vista.UnidadMedida();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Precio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Precio.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Dimension desktopSize = escritorio.getSize();
                Dimension jInternalFrameSize = UndMed.getSize();
                UndMed.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        
                escritorio.add(UndMed);
                UndMed.show(); 
                UndMed.setVisible(true);
	    }
        }
        else{
            Dimension desktopSize = escritorio.getSize();
            Dimension jInternalFrameSize = ListasUnidad.getSize();
            ListasUnidad.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
            
            ListasUnidad.show();
            Listas.Tabla="DNUNDMEDIDA";
            ListasUnidad.setVisible(true);
        }
    }//GEN-LAST:event_jBtnBusca_UniMedActionPerformed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        modelActionListener.actualizaJtable(Tabla.getSelectedRow());
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        String codproduc = ""; String tipPre = "";
        String precio = ""; String codunidad = ""; String fecha = "";
        String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void jDatePre_FechavenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDatePre_FechavenKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.jBtnBusca_Productos.requestFocus();
        }
    }//GEN-LAST:event_jDatePre_FechavenKeyPressed

    private void jTxtPre_MontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPre_MontoFocusGained
        if (this.Bandera==false){
            jTxtPre_Monto.setText("");
        }
    }//GEN-LAST:event_jTxtPre_MontoFocusGained

    private void jTxtPre_MontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPre_MontoFocusLost
        if ("".equals(jTxtPre_Monto.getText())){
            jTxtPre_Monto.setText("0,00");
        }
    }//GEN-LAST:event_jTxtPre_MontoFocusLost

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        Pantallas.Buscar BuscaDoc = new Pantallas.Buscar();

        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = BuscaDoc.getSize();
        BuscaDoc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorio.add(BuscaDoc);
        BuscaDoc.show();
        BuscaDoc.setVisible(true);
    }//GEN-LAST:event_bt_findActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated
    
    private void inicializaClase(){
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setButtonOther(jBtnBusca_Productos, jBtnBusca_TipPrecio, jBtnBusca_UniMed);
        modelActionListener.setClass(this);
        modelActionListener.setJCheckBox(jCheckPre_Activo, null, null);
        modelActionListener.setJTable(Tabla, null, null, null);
        modelActionListener.setFecha(jDatePre_Fechaven);
        modelActionListener.setValor(jTxtPre_Monto);
        modelActionListener.setJTextFieldPrecio(jTxtPre_Codigo, jTxtPre_CodProduc, jTxtPre_Codlis, jTxtPre_Coduni);
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
    private javax.swing.JButton jBtnBusca_Productos;
    public static javax.swing.JButton jBtnBusca_TipPrecio;
    public static javax.swing.JButton jBtnBusca_UniMed;
    private javax.swing.JCheckBox jCheckPre_Activo;
    private com.toedter.calendar.JDateChooser jDatePre_Fechaven;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JTextField jTxtPre_CodProduc;
    private javax.swing.JTextField jTxtPre_Codigo;
    public static javax.swing.JTextField jTxtPre_Codlis;
    public static javax.swing.JTextField jTxtPre_Coduni;
    public static javax.swing.JFormattedTextField jTxtPre_Monto;
    // End of variables declaration//GEN-END:variables
}