package Vista;

import Controlador.ControladorPlanCuentas;
import Listener.ListenerButton;
import Modelos.ModelActionListener;
import Modelos.ModeloPlanCuentas;
import clases.SQLSelect;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JOptionPane;

public class PlanCuentas extends javax.swing.JInternalFrame {
    CargaTablas cargatabla = null;
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();
    ControladorPlanCuentas planCuentas = new ControladorPlanCuentas();
    ModeloPlanCuentas modeloPlanCuentas = ModeloPlanCuentas.getDatosPlanCuentas();

    private SQLSelect Registros;
    private Vector Msg, elementos, header_table;
    private String ctaPrevia, ctaPadre="0", nivelMax;
    private Boolean lMsg=false, lEstadoForm=false;
    private int selectComboP=0, selectComboI=0, nivel=0;
    
    public PlanCuentas() {
        initComponents();
        inicializaClase();
//        limpiar();
        
        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        jPanel_Estructura.setBackground(Color.decode(colorForm));
        
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) "Estructura de la Cta", 0, 0, null, Color.decode(colorText)));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) "Cuenta Contable", 0, 0, null, Color.decode(colorText)));
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel12.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText)); jLabel7.setForeground(Color.decode(colorText));
        jLabel10.setForeground(Color.decode(colorText)); jLabel11.setForeground(Color.decode(colorText));
        jLabel13.setForeground(Color.decode(colorText)); jLabel14.setForeground(Color.decode(colorText));
        jLabel15.setForeground(Color.decode(colorText)); jLabel16.setForeground(Color.decode(colorText));
        jLabel17.setForeground(Color.decode(colorText)); jLabel18.setForeground(Color.decode(colorText));
        jLabel19.setForeground(Color.decode(colorText));
        
        jTxtAct_Nombre.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
/*
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2)); jLabel4.setText((String) elementos.get(3));
        jLabel5.setText((String) elementos.get(4)); jLabel6.setText((String) elementos.get(5));
        jLabel7.setText((String) elementos.get(6));
        
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder((String) elementos.get(7)));
        
//        bt_agregar.setText((String) elementos.get(8)); bt_Modificar.setText((String) elementos.get(9));
//        bt_Eliminar.setText((String) elementos.get(10)); bt_Atras.setText((String) elementos.get(11)); 
//        bt_Siguiente.setText((String) elementos.get(12)); bt_salir.setText((String) elementos.get(13)); 
//        bt_save.setText((String) elementos.get(14)); //bt_cancel.setText((String) elementos.get(11));
//        bt_find.setText((String) elementos.get(15));
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
*/
//-----------------------------------------------------------------------------------------------------------------------

        modelActionListener.cargaTabla();

        this.jCheckAct_Activo.setSelected(true);
        this.setTitle("Plan de Cuentas");
        
        if (Tabla.getRowCount()==0){
            modeloPlanCuentas.setlAgregar(true);
            
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
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
        bt_salir.addActionListener(new ListenerButton());
        bt_Atras.addActionListener(new ListenerButton());
        bt_Siguiente.addActionListener(new ListenerButton());
    }

//    public void Numeros(JTextField num){
//        num.addKeyListener(new KeyAdapter() {
//            public void keyTyped(KeyEvent evt){
//                char let=evt.getKeyChar();

//                if(Character.isLetter(let)){
//                    evt.consume();
//                }
//            }
//        });
//    }
    
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
        jCmbCta_Padre = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jCmbCta_Nivel = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabCta_Tipsal = new javax.swing.JLabel();
        jCmbCta_DebeHaber = new javax.swing.JComboBox();
        jCmbCta_Clasificacion = new javax.swing.JComboBox();
        jLabCta_Perten = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox();
        jComboBox9 = new javax.swing.JComboBox();
        jComboBox10 = new javax.swing.JComboBox();
        jComboBox11 = new javax.swing.JComboBox();
        jComboBox12 = new javax.swing.JComboBox();
        jComboBox13 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTxtAct_Nombre = new javax.swing.JTextField();
        jCheckAct_Activo = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel_Estructura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Estructura de la Cta"));
        jPanel1.setToolTipText("");
        jPanel1.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel1.setText("Cuentas Principales:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jCmbCta_Padre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCmbCta_Padre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        jCmbCta_Padre.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbCta_PadreItemStateChanged(evt);
            }
        });
        jPanel1.add(jCmbCta_Padre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 60, 25));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("DEFINICION DE CUENTAS");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 360, -1));

        jLabel12.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel12.setText("Nivel 8:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 120, -1));

        jCmbCta_Nivel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCmbCta_Nivel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbCta_NivelItemStateChanged(evt);
            }
        });
        jPanel1.add(jCmbCta_Nivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 60, 25));

        jComboBox7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 270, 25));

        jLabel13.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel13.setText("Niveles a crear:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 90, -1));

        jLabCta_Tipsal.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabCta_Tipsal.setText("Saldo:");
        jPanel1.add(jLabCta_Tipsal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));

        jCmbCta_DebeHaber.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jCmbCta_DebeHaber.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Deudor", "Acreedor" }));
        jCmbCta_DebeHaber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbCta_DebeHaberActionPerformed(evt);
            }
        });
        jPanel1.add(jCmbCta_DebeHaber, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 75, 25));

        jCmbCta_Clasificacion.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jCmbCta_Clasificacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Real", "Nominal" }));
        jCmbCta_Clasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbCta_ClasificacionActionPerformed(evt);
            }
        });
        jPanel1.add(jCmbCta_Clasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 75, 25));

        jLabCta_Perten.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabCta_Perten.setText("Clasificación:");
        jPanel1.add(jLabCta_Perten, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        jComboBox8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 270, 25));

        jComboBox9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox9ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 270, 25));

        jComboBox10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox10ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 270, 25));

        jComboBox11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox11ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 270, 25));

        jComboBox12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox12ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 270, 25));

        jComboBox13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox13ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 270, 25));

        jLabel14.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel14.setText("Nivel 2:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 120, -1));

        jLabel15.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel15.setText("Nivel 3:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 120, -1));

        jLabel16.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel16.setText("Nivel 4:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 120, -1));

        jLabel17.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel17.setText("Nivel 5:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 120, -1));

        jLabel18.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel18.setText("Nivel 6:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 120, -1));

        jLabel19.setFont(new java.awt.Font("Roboto Light", 0, 12)); // NOI18N
        jLabel19.setText("Nivel 7:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 120, -1));

        jPanel_Estructura.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 330));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuenta Contable"));
        jPanel2.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel10.setText("Código Cta:");

        jLabel11.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jLabel11.setText("Descripción:");

        jTxtAct_Nombre.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jTxtAct_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtAct_NombreKeyPressed(evt);
            }
        });

        jCheckAct_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckAct_Activo.setText("Activo");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("jLabel7");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtAct_Nombre)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckAct_Activo))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jCheckAct_Activo)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTxtAct_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel_Estructura.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 500, -1));

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

        jPanel_Estructura.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 500, 190));

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

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setPreferredSize(new java.awt.Dimension(115, 45));
        jToolBar1.add(bt_salir);

        jPanel_Estructura.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 110, 650));

        getContentPane().add(jPanel_Estructura, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCmbCta_PadreItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbCta_PadreItemStateChanged
        String newPadre=jCmbCta_Padre.getSelectedItem().toString();
        
        if ((!ctaPadre.equals(newPadre)) && (selectComboP>0)){
            selectComboP=0;
        }
        
        if (selectComboP==0 && !newPadre.equals(" ")){
            selectComboP++;
            jLabel7.setText(jCmbCta_Padre.getSelectedItem().toString()+".");
        
            Object datosEstrucutaCta[][] = planCuentas.cargaEstructuraCta(jCmbCta_Padre.getSelectedItem().toString());

            ctaPadre = datosEstrucutaCta[0][0].toString();
        
            if (!ctaPadre.equals(" ")){
                nivelMax = datosEstrucutaCta[0][1].toString();

                String[] arregloNiveles = planCuentas.cargaCombos(Integer.valueOf(nivelMax), false);
                jCmbCta_Nivel.setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles));
                jCmbCta_Nivel.setSelectedIndex(0);
        
                modeloPlanCuentas.setNivelPrefijo(datosEstrucutaCta[0][2].toString());
                modeloPlanCuentas.setPrefijo(datosEstrucutaCta[0][3].toString());
                modeloPlanCuentas.setLongPrefijo(datosEstrucutaCta[0][4].toString());

//                planCuentas.vizualizaCta();
//                planCuentas.incrementaCta();
            }else{
                if (!jCmbCta_Padre.getSelectedItem().equals("") && lMsg==false){
                    JOptionPane.showMessageDialog(null,"No ha realizado la configuracion de la Cta Principal ''"+jCmbCta_Padre.getSelectedItem().toString()+"''", "Notificación", JOptionPane.WARNING_MESSAGE);
                
                    lMsg=true;
                    String[] arregloNiveles = planCuentas.cargaCombos(0, false);
                    jCmbCta_Nivel.setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles));
                    
                    String[] arregloNivelesIncr = planCuentas.cargaCombos(0, false);
                    jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(arregloNivelesIncr));
                    
                    jCmbCta_Padre.setSelectedItem(" ");
                }
            }
        }else{
            if (newPadre.equals(" ")){
                String[] arregloNiveles = planCuentas.cargaCombos(0, false);
                jCmbCta_Nivel.setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles));

                String[] arregloNivelesIncr = planCuentas.cargaCombos(0, false);
                jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(arregloNivelesIncr));
            }  
            
            lMsg=false;
        }
    }//GEN-LAST:event_jCmbCta_PadreItemStateChanged

    private void jTxtAct_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtAct_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_jTxtAct_NombreKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        modelActionListener.actualizaJtable(Tabla.getSelectedRow());
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void jCmbCta_NivelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbCta_NivelItemStateChanged
        if(jCmbCta_Nivel.getSelectedItem().toString().equals("2")){
            nivel = 1;
        }else{
            nivel = 2;
        }
        
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            planCuentas.comboNivel(jComboBox7, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), "");
//            vizualizaCta();
//            incrementaCta();
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jCmbCta_NivelItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        if (modeloPlanCuentas.getlAgregar()==true){
        planCuentas.ctaPrevia(jComboBox7, false);
        }
        if(jCmbCta_Nivel.getSelectedItem().toString().equals("2")){
            nivel = 1;
        }else{
            nivel = 3;
        }
        
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            int pos = jComboBox7.getSelectedItem().toString().indexOf(" ", 1);
            String cta = jComboBox7.getSelectedItem().toString().substring(0, pos);
            planCuentas.comboNivel(jComboBox8, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), cta);
        
            if ((nivel-(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1))==1){
                planCuentas.ctaPrevia(jComboBox7, true);
            }
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jCmbCta_DebeHaberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbCta_DebeHaberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbCta_DebeHaberActionPerformed

    private void jCmbCta_ClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbCta_ClasificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbCta_ClasificacionActionPerformed

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        if (modeloPlanCuentas.getlAgregar()==true){
        planCuentas.ctaPrevia(jComboBox8, false);
        }
        nivel = 4;
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            int pos = jComboBox8.getSelectedItem().toString().indexOf(" ", 1);
            String cta = jComboBox8.getSelectedItem().toString().substring(0, pos);
            planCuentas.comboNivel(jComboBox9, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), cta);
        
            if ((nivel-(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1))==1){
                planCuentas.ctaPrevia(jComboBox8, true);
            }
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jComboBox9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox9ItemStateChanged
        if (modeloPlanCuentas.getlAgregar()==true){
        planCuentas.ctaPrevia(jComboBox9, false);
        }
        nivel = 5;
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            int pos = jComboBox9.getSelectedItem().toString().indexOf(" ", 1);
            String cta = jComboBox9.getSelectedItem().toString().substring(0, pos);
            planCuentas.comboNivel(jComboBox10, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), cta);
        
            if ((nivel-(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1))==1){
                planCuentas.ctaPrevia(jComboBox9, true);
            }
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jComboBox9ItemStateChanged

    private void jComboBox10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox10ItemStateChanged
        if (modeloPlanCuentas.getlAgregar()==true){
        planCuentas.ctaPrevia(jComboBox10, false);
        }
        nivel = 6;
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            int pos = jComboBox10.getSelectedItem().toString().indexOf(" ", 1);
            String cta = jComboBox10.getSelectedItem().toString().substring(0, pos);
            planCuentas.comboNivel(jComboBox11, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), cta);
        
            if ((nivel-(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1))==1){
                planCuentas.ctaPrevia(jComboBox10, true);
            }
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jComboBox10ItemStateChanged

    private void jComboBox11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox11ItemStateChanged
        if (modeloPlanCuentas.getlAgregar()==true){
        planCuentas.ctaPrevia(jComboBox11, false);
        }
        nivel = 7;
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            int pos = jComboBox11.getSelectedItem().toString().indexOf(" ", 1);
            String cta = jComboBox11.getSelectedItem().toString().substring(0, pos);
            planCuentas.comboNivel(jComboBox12, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), cta);
        
            if ((nivel-(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1))==1){
                planCuentas.ctaPrevia(jComboBox11, true);
            }
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jComboBox11ItemStateChanged

    private void jComboBox12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox12ItemStateChanged
        planCuentas.ctaPrevia(jComboBox12, false);

        nivel = 8;
//        planCuentas.reset(nivel);
        
        if (lMsg==false){
            int pos = jComboBox12.getSelectedItem().toString().indexOf(" ", 1);
            String cta = jComboBox12.getSelectedItem().toString().substring(0, pos);
            planCuentas.comboNivel(jComboBox13, jCmbCta_Padre.getSelectedItem().toString(), nivel, jCmbCta_Nivel.getSelectedItem().toString(), cta);
        
            if ((nivel-(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1))==1){
                planCuentas.ctaPrevia(jComboBox12, true);
            }
        }else{
            lMsg=false;
        }
    }//GEN-LAST:event_jComboBox12ItemStateChanged

    private void jComboBox13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox13ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox13ItemStateChanged
/*
    private String[] cargaCombos(int nivel, Boolean incrementa){
        int maxNiveles = nivel;
        int niveles = 0;
        
        String[] arregloNiveles = new String[maxNiveles];
        
        for (int i=0; i<maxNiveles; i++){
            niveles++;
            
            if (niveles==1 && incrementa==true){
                arregloNiveles[i] = "";
            }else{
                arregloNiveles[i] = String.valueOf(niveles);
            }
        }
        
        return arregloNiveles;
    }
    
    private void ctaPrevia(JComboBox combo, Boolean lIncrement){
        String prefijo = null;
        String prevCta = jLabel7.getText();
        int pos = combo.getSelectedItem().toString().indexOf(" ", 1);
        String cta = combo.getSelectedItem().toString().substring(0, pos);
        String newCta = cta;
        String nivelCta = String.valueOf(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1);
        
        if (lIncrement==true){
            if (planCuentas.existeCod(newCta, jCmbCta_Padre.getSelectedItem().toString(), nivelCta)>0){
                int posicion = countOccurrences(prevCta, '.', Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1);
            
                if (posicion>0){
                    prevCta = combo.getSelectedItem().toString().substring(0, pos)+".";
                    
                    if (Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())>=Integer.valueOf(nivelPrefijo)){
                        prefijo = String.format("%0"+longPrefijo+"d", 1);
                    }else{
                        prefijo = "1";
                    }
            
                    newCta = prevCta+prefijo;
                    jLabel7.setText(newCta);
                    
                    if (planCuentas.existeCod(newCta, jCmbCta_Padre.getSelectedItem().toString(), jCmbCta_Nivel.getSelectedItem().toString())>0){
                        incrementaCta();
                    }
                }
            }
        }else{
            if (nivel==1){
                prefijo = ".1";
                
                newCta = newCta+prefijo;
                jLabel7.setText(newCta);
                
                if (planCuentas.existeCod(newCta, jCmbCta_Padre.getSelectedItem().toString(), jCmbCta_Nivel.getSelectedItem().toString())>0){
                    incrementaCta();
                }
            }else{
                if (newCta.equals(jCmbCta_Padre.getSelectedItem().toString())){
                    jLabel7.setText(prevCta);
                }else{
                    jLabel7.setText(newCta);
                }
            }
        }
    }
    
    private void vizualizaCta(){
        String prevCta="";
        
        if (nivelPrefijo.equals("1")){
            prevCta = jCmbCta_Padre.getSelectedItem().toString()+".";
        }else{
            prevCta = jLabel7.getText();
            ctaPrevia = jLabel7.getText();
        }

        prevCta = prevCta.replace("0", "");
        
        int niveles = Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1;
        prevCta = jCmbCta_Padre.getSelectedItem().toString()+".";
            
        for (int i=0; i<niveles; i++){
             prevCta = prevCta+jCmbCta_Padre.getSelectedItem().toString()+".";
        }

        int nivel = (Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString()))-(Integer.valueOf(nivelPrefijo))+1;
        int posicion = countOccurrences(prevCta, '.', Integer.valueOf(nivelPrefijo)-1);

        if (prefijo!=" "){
            prevCta = prevCta.substring(0, posicion+1);
            
            if (posicion==0){
                prevCta = "";
            }
            
            String prefijo = String.format("%0"+longPrefijo+"d", Integer.valueOf(jCmbCta_Padre.getSelectedItem().toString()));
            
            for (int i=0; i<nivel; i++){
                prevCta = prevCta+prefijo+".";
            }
        }
        
        prevCta = limpiaPunto(prevCta);

        jLabel7.setText(prevCta);
    }
    
    private void incrementaCta(){
        String prevCta = jLabel7.getText();
        String prefijo = null;
        
        if (planCuentas.existeCod(prevCta, jCmbCta_Padre.getSelectedItem().toString(), jCmbCta_Nivel.getSelectedItem().toString())>0){
            int posicion = countOccurrences(prevCta, '.', Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1);
            
            if (posicion>0){
                prevCta = prevCta.substring(posicion+1, prevCta.length());
            
                int ctaIncremeta = Integer.valueOf(prevCta);
                ctaIncremeta++;
            
                prevCta = jLabel7.getText();
                prevCta = prevCta.substring(0, posicion+1);
            
                if (Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())>=Integer.valueOf(nivelPrefijo)){
                    if (prefijo==" "){
                        prefijo = String.valueOf(ctaIncremeta);
                    }else{
                        prefijo = String.format("%0"+longPrefijo+"d", ctaIncremeta);
                    }
                }else{
                    prefijo = String.valueOf(ctaIncremeta);
                }
            
                prevCta = prevCta+prefijo;
                jLabel7.setText(prevCta);
        
                if (planCuentas.existeCod(prevCta, jCmbCta_Padre.getSelectedItem().toString(), jCmbCta_Nivel.getSelectedItem().toString())>0){
                    incrementaCta();
                }
            }
        }else{
            int posicion = countOccurrences(prevCta, '.', Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1);
            
            prevCta = jLabel7.getText();
            prevCta = prevCta.substring(0, posicion+1);
            
            prevCta = limpiaPunto(prevCta);
            String nivelCrea = String.valueOf(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1);
            
            if (planCuentas.existeCod(prevCta, jCmbCta_Padre.getSelectedItem().toString(), nivelCrea)==0 && lMsg==false && !jCmbCta_Nivel.getSelectedItem().toString().equals("1")){
                JOptionPane.showMessageDialog(null,"No puede crear la cta de Nivel "+jCmbCta_Nivel.getSelectedItem().toString()+" porque la cuenta "+prevCta+" que precede no ha sido creada aun", "Notificación", JOptionPane.WARNING_MESSAGE);
                
                lMsg=true;
                jLabel7.setText(ctaPrevia);

                String[] arregloNiveles = cargaCombos(Integer.valueOf(nivelMax), false);
                jCmbCta_Nivel.setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles));
            }
        }
    }
    
    private void vizualizaCtaPrevia(){
        String prevCta="";
        
        if (nivelPrefijo.equals("1")){
            prevCta = jCmbCta_Padre.getSelectedItem().toString()+".";
        }else{
            prevCta = jLabel7.getText();
            ctaPrevia = jLabel7.getText();
        }

        prevCta = prevCta.replace("0", "");
        
        int nivel = (Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString()))-(Integer.valueOf(nivelPrefijo))+1;
        if (nivel==0){
            nivel = 1;
        }
        int posicion = countOccurrences(prevCta, '.', Integer.valueOf(jComboBox7.getSelectedItem().toString())-1);

        if (prefijo!=" "){
            prevCta = prevCta.substring(0, posicion+1);
            
            if (posicion==0){
                prevCta = "";
            }
            
            String prefijo = String.format("%0"+longPrefijo+"d", Integer.valueOf(jCmbCta_Padre.getSelectedItem().toString()));
            
            for (int i=0; i<nivel; i++){
                prevCta = prevCta+prefijo+".";
            }
        }else{
            int niveles = Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1;
            prevCta = jCmbCta_Padre.getSelectedItem().toString()+".";
            
            if (prefijo==" "){
                for (int i=0; i<niveles; i++){
                    prevCta = prevCta+jCmbCta_Padre.getSelectedItem().toString()+".";
                }
            }
        }
        
        prevCta = limpiaPunto(prevCta);
        jLabel7.setText(prevCta);
    }
    
    private void incrementaCtaPrevia(){
        String prevCta = jLabel7.getText();
        String prefijo = null;
        
        prevCta = prevCta.replace("0", "");
        
        if (planCuentas.existeCod(prevCta, jCmbCta_Padre.getSelectedItem().toString(), jComboBox7.getSelectedItem().toString())>0){
            int posicion = countOccurrences(prevCta, '.', Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1);
            prevCta = prevCta.substring(posicion+1, prevCta.length());
            
            int ctaIncremeta = Integer.valueOf(prevCta);
            ctaIncremeta++;
            
            prevCta = jLabel7.getText();
            prevCta = prevCta.substring(0, posicion+1);
            
            if (nivelPrefijo!=jComboBox7.getSelectedItem()){
                prefijo = String.valueOf(ctaIncremeta);
            }else{
                prefijo = String.format("%0"+longPrefijo+"d", ctaIncremeta);
            }
            
            prevCta = prevCta+prefijo+".";
            
            if (planCuentas.existeCod(prevCta, jCmbCta_Padre.getSelectedItem().toString(), jComboBox7.getSelectedItem().toString())==0){
                JOptionPane.showMessageDialog(null,"No puede incrementar la cta de Nivel "+jComboBox7.getSelectedItem().toString()+" porque la cuenta "+prevCta+" que precede no ha sido creada aun", "Notificación", JOptionPane.WARNING_MESSAGE);
                
                lMsg=true;
                jLabel7.setText(ctaPrevia);

                String[] arregloNiveles2 = cargaCombos(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1, true);
                jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles2));
        
                return;
            }
            
            jLabel7.setText(prevCta);

            int nivel = (Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString()))-(Integer.valueOf(jComboBox7.getSelectedItem().toString()));
            String prefijo2 = String.format("%0"+longPrefijo+"d", Integer.valueOf(jCmbCta_Padre.getSelectedItem().toString()));
            
            for (int i=0; i<nivel; i++){
                prevCta = prevCta+prefijo2+".";
            }
            
//****************************************************************************************************************
            prevCta = prevCta.replace("0", "");
        
            int nivel1 = (Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString()))-(Integer.valueOf(nivelPrefijo))+1;
            int posicion1 = countOccurrences(prevCta, '.', Integer.valueOf(nivelPrefijo)-1);

            if (prefijo!=" "){
                prevCta = prevCta.substring(0, posicion1+1);
            
                if (posicion1==0){
                    prevCta = "";
                }
            
                String prefijo1 = String.format("%0"+longPrefijo+"d", Integer.valueOf(jCmbCta_Padre.getSelectedItem().toString()));
            
                for (int i=0; i<nivel1; i++){
                    prevCta = prevCta+prefijo1+".";
                }
            }
//****************************************************************************************************************
            
            prevCta = limpiaPunto(prevCta);
        
            jLabel7.setText(prevCta);
            
            if (planCuentas.existeCod(prevCta, jCmbCta_Padre.getSelectedItem().toString(), jCmbCta_Nivel.getSelectedItem().toString())>0){
                incrementaCta();
            }
        }else{
            JOptionPane.showMessageDialog(null,"No puede incrementar la cta de Nivel "+jComboBox7.getSelectedItem().toString()+" porque la cuenta "+prevCta+" que precede no ha sido creada aun", "Notificación", JOptionPane.WARNING_MESSAGE);

            lMsg=true;
            jLabel7.setText(ctaPrevia);
            
            String[] arregloNiveles2 = cargaCombos(Integer.valueOf(jCmbCta_Nivel.getSelectedItem().toString())-1, true);
            jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles2));
        }
    }
 
    private String limpiaPunto(String prevCta){
        String punto = prevCta.substring(prevCta.length()-1);
        
        if (punto.equals(".")){
            prevCta = prevCta.substring(0, prevCta.length()-1);
        }
        
        return prevCta;
    }
    
    private static int countOccurrences(String haystack, char needle, int nivel) { 
        int count = 0; 
        int posicion = 0; 
        
        for (int i=0; i < haystack.length(); i++) { 
            if (haystack.charAt(i) == needle) { 
                if (nivel!=count){
                    count++;
                    posicion = i;
                }
            } 
        } 
        
        return posicion; 
    }
 */   
    private void inicializaClase(){
        System.out.println("Activado Form Estructura y Plan de Ctas");
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setClass(this);

        modeloPlanCuentas.setlAgregar(lEstadoForm);
        modeloPlanCuentas.setChkActivo(jCheckAct_Activo);
        modeloPlanCuentas.setCodigoCta(jLabel7);
        modeloPlanCuentas.setTfNombre(jTxtAct_Nombre);
        modeloPlanCuentas.setCbCtaPadre(jCmbCta_Padre);
        modeloPlanCuentas.setCbCtaNivelCrea(jCmbCta_Nivel);
        modeloPlanCuentas.setCbNivelPrev1(jComboBox7); modeloPlanCuentas.setCbNivelPrev2(jComboBox8);
        modeloPlanCuentas.setCbNivelPrev3(jComboBox9); modeloPlanCuentas.setCbNivelPrev4(jComboBox10);
        modeloPlanCuentas.setCbNivelPrev5(jComboBox11); modeloPlanCuentas.setCbNivelPrev6(jComboBox12);
        modeloPlanCuentas.setCbNivelPrev7(jComboBox13);
        
        modelActionListener.setJTable(Tabla, null, null, null);
        modelActionListener.setJTextField(null, jTxtAct_Nombre, null, null, null, null, null, null);
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
    private javax.swing.JCheckBox jCheckAct_Activo;
    private javax.swing.JComboBox jCmbCta_Clasificacion;
    private javax.swing.JComboBox jCmbCta_DebeHaber;
    private javax.swing.JComboBox jCmbCta_Nivel;
    private javax.swing.JComboBox jCmbCta_Padre;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JLabel jLabCta_Perten;
    private javax.swing.JLabel jLabCta_Tipsal;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static final javax.swing.JPanel jPanel_Estructura = new javax.swing.JPanel();
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtAct_Nombre;
    // End of variables declaration//GEN-END:variables
}