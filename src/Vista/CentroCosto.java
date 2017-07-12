/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Listener.ListenerButton;
import Modelos.ModelActionListener;
import Modelos.VariablesGlobales;
import static Pantallas.principal.escritorio;
import static Vista.Login.Idioma;
import static Vista.Moneda.jCheckMon_Activo;
import static Vista.Moneda.jDateMon_Fechavig;
import static Vista.Moneda.jTxtMon_Codigo;
import static Vista.Moneda.jTxtMon_Nombre;
import static Vista.Moneda.jTxtMon_Nomenclatura;
import static Vista.Moneda.jTxtMon_Valor;
import clases.CargaTablas;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.LimitarCaracteres;
import clases.ReadFileXml;
import clases.SQLSelect;
import clases.ValidaCodigo;
import clases.conexion;
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
import javax.swing.JTextField;

/**
 *
 * @author Kelvin Marcano
 */
public class CentroCosto extends javax.swing.JInternalFrame {

    public int fila, atras = -2, adelante = 0;
    private SQLSelect Registros;
    private boolean Bandera = false, lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo = "";
    private Vector Msg, elementos, header_table;

    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModelActionListener modelActionListener = ModelActionListener.getPrueba();

    /**
     * Creates new form CentroCosto
     */
    public CentroCosto() throws ClassNotFoundException, SQLException {
        initComponents();
        inicializaClase();
        
        jPanel1.setBackground(Color.decode(colorForm));
        jPanel2.setBackground(Color.decode(colorForm));
        jLabel1.setForeground(Color.decode(colorText));
        jLabel2.setForeground(Color.decode(colorText));
        jTxtCen_Codigo.setForeground(Color.decode(colorText));
        jTxtCen_Descri.setForeground(Color.decode(colorText));
        jDateCen_Fechavig.setForeground(Color.decode(colorText));
        Tabla.setForeground(Color.decode(colorText));
        
        //------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);

        jLabel1.setText((String) elementos.get(0)); jLabel2.setText((String) elementos.get(1));
        jLabel3.setText((String) elementos.get(2)); 
                     
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, (String) elementos.get(3), fila, fila, null, Color.decode(colorText)));
        
        bt_agregar.setText((String) elementos.get(4)); 
        bt_Modificar.setText((String) elementos.get(5));
        bt_Eliminar.setText((String) elementos.get(6)); 
        bt_Atras.setText((String) elementos.get(7)); 
        bt_Siguiente.setText((String) elementos.get(8)); 
        bt_salir.setText((String) elementos.get(9)); 
        bt_save.setText((String) elementos.get(10)); 
        bt_cancel.setText((String) elementos.get(11));
        bt_find.setText((String) elementos.get(12));
        
         jCheckCen_Activo.setText((String) elementos.get(13));
        /*
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        */
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
        
        limitar();
        modelActionListener.cargaTabla();
        this.setTitle("Centro de Costo");

        if (Tabla.getRowCount() == 0) {
            modelActionListener.setAccion("Inicializa");
            modelActionListener.habilitar();
            modelActionListener.posicion_botones_2();
            modelActionListener.setConsecutivo();
            jTxtCen_Descri.requestFocus();
            lAgregar = true;
        } else {
            modelActionListener.posicion_botones_1();
            modelActionListener.Deshabilitar();
            bt_save.setVisible(false);
            bt_cancel.setVisible(false);
            modelActionListener.ejecutaHilo();

            Tabla.getSelectionModel().setSelectionInterval(Tabla.getRowCount() - 1, Tabla.getRowCount() - 1);
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

    public void limitar() {
        /**
         * *****
         * Este Metodo se realizó con la finalidad de limitar la cantidad de
         * caracteres a ingresar en el formulario
         */
        jTxtCen_Codigo.setDocument(new LimitarCaracteres(jTxtCen_Codigo, 6));
        jTxtCen_Descri.setDocument(new LimitarCaracteres(jTxtCen_Descri, 40));
    }

    public void Mascara_Campos_Num(){
       // jTxtMon_Valor.setText("0,00");

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
        jDateCen_Fechavig.setDate(fch);
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
        jTxtCen_Codigo = new javax.swing.JTextField();
        jTxtCen_Descri = new javax.swing.JTextField();
        jDateCen_Fechavig = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jCheckCen_Activo = new javax.swing.JCheckBox();
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
        salirfactura = new javax.swing.JMenu();
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        jPanel2.setPreferredSize(new java.awt.Dimension(515, 155));

        jLabel1.setText("Codigo:");

        jLabel2.setText("Descripción:");

        jTxtCen_Codigo.setPreferredSize(new java.awt.Dimension(6, 25));
        jTxtCen_Codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtCen_CodigoFocusLost(evt);
            }
        });
        jTxtCen_Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCen_CodigoKeyPressed(evt);
            }
        });

        jTxtCen_Descri.setPreferredSize(new java.awt.Dimension(6, 25));
        jTxtCen_Descri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtCen_DescriKeyPressed(evt);
            }
        });

        jLabel3.setText("Fecha:");

        jCheckCen_Activo.setText("Activo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckCen_Activo))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25)
                        .addComponent(jTxtCen_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateCen_Fechavig, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtCen_Descri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jDateCen_Fechavig, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTxtCen_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtCen_Descri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckCen_Activo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Còdigo", "Descripciòn"
            }
        ));
        Tabla.setPreferredSize(new java.awt.Dimension(150, 0));
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
        jToolBar1.setAlignmentY(0.0F);

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar    ");
        bt_agregar.setToolTipText("");
        bt_agregar.setBorder(null);
        bt_agregar.setFocusable(false);
        bt_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar   ");
        bt_Modificar.setBorder(null);
        bt_Modificar.setFocusable(false);
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ModificarActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setText("Grabar      ");
        bt_save.setBorder(null);
        bt_save.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bt_save.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_save);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar     ");
        bt_Eliminar.setBorder(null);
        bt_Eliminar.setFocusable(false);
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setText("Cancelar   ");
        bt_cancel.setBorder(null);
        bt_cancel.setFocusable(false);
        bt_cancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_cancel);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar      ");
        bt_find.setBorder(null);
        bt_find.setFocusable(false);
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior    ");
        bt_Atras.setBorder(null);
        bt_Atras.setFocusable(false);
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante   ");
        bt_Siguiente.setBorder(null);
        bt_Siguiente.setFocusable(false);
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir          ");
        bt_salir.setBorder(null);
        bt_salir.setFocusable(false);
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        salirfactura.setText("Sistema");
        salirfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfacturaActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        salirfactura.add(jMenuItem1);

        jMenuBar1.add(salirfactura);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void salirfacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfacturaActionPerformed
        this.dispose();
        //this.setVisible(false);
    }//GEN-LAST:event_salirfacturaActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        Pantallas.Buscar BuscaCen = new Pantallas.Buscar();
        escritorio.add(BuscaCen);
        BuscaCen.show();
        BuscaCen.setVisible(true);    }//GEN-LAST:event_bt_findActionPerformed

    private void jTxtCen_CodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCen_CodigoFocusLost
        // TODO add your handling code here:
        int registros;

        ValidaCodigo consulta = new ValidaCodigo();
        registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNCENTRO WHERE CEN_CODIGO='" + jTxtCen_Codigo.getText() + "'", false, "");

        if (registros == 1) {
            jTxtCen_Codigo.requestFocus();
            jTxtCen_Codigo.setText("");
        }
    }//GEN-LAST:event_jTxtCen_CodigoFocusLost

    private void jTxtCen_DescriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCen_DescriKeyPressed
        // TODO add your handling code here:        
    }//GEN-LAST:event_jTxtCen_DescriKeyPressed

    private void jTxtCen_CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtCen_CodigoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jTxtCen_Descri.requestFocus();
        }
    }//GEN-LAST:event_jTxtCen_CodigoKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        // TODO add your handling code here:
        modelActionListener.actualizaJtable(Tabla.getSelectedRow());
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        // TODO add your handling code here:
        String nombre = ""; 
        String fecha = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void bt_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_ModificarActionPerformed

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        // TODO add your handling code here:
         if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelActionListener.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void inicializaClase() {
        modelActionListener.setClaseOrg(this.getClass().getName().toString());
        modelActionListener.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelActionListener.setClass(this);
        modelActionListener.setJTable(Tabla, null, null, null);
        modelActionListener.setFecha(jDateCen_Fechavig);
        modelActionListener.setJCheckBox(jCheckCen_Activo, null, null);
        modelActionListener.setJTextField(jTxtCen_Codigo, jTxtCen_Descri,null,null,null,null,null,null);
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
    private javax.swing.JCheckBox jCheckCen_Activo;
    public static com.toedter.calendar.JDateChooser jDateCen_Fechavig;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JTextField jTxtCen_Codigo;
    public static javax.swing.JTextField jTxtCen_Descri;
    private javax.swing.JMenu salirfactura;
    // End of variables declaration//GEN-END:variables
}
