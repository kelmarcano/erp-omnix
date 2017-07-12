package Pantallas;

import Vista.Maestros;
import Vista.Actividad;
import static Vista.Login.Idioma;
import clases.SQLQuerys;
import clases.SQLSelect;
import clases.conexion;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import clases.LimitarCaracteres;
import clases.ValidaCodigo;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.StandardButtonShaper;
import static Pantallas.principal.escritorio;
import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import clases.CargaComboBoxs;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.ReadFileXml;
import java.awt.Color;
import java.io.File;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import org.jvnet.substance.SubstanceLookAndFeel;

public class Direcciones extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, SinRegistros = false, lAgregar, lModificar;
    private ResultSet rs ,rs_count, rs_codigo, rs_pais, rs_estado, rs_muni, rs_parro;
    private int Reg_count;
    public boolean cbo=false;
    public String combo, combo1, combo2;
    public String codigo=null, descri=null, pais=null, estado=null, muni=null, parro=null;
    private Vector Msg, elementos, header_table;
    
    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public Direcciones() {
        initComponents();

        jPanel1.setBackground(Color.decode(colorForm)); jPanel2.setBackground(Color.decode(colorForm));
        
        bt_agregar.setBackground(Color.decode(colorForm)); bt_Modificar.setBackground(Color.decode(colorForm));
        bt_save.setBackground(Color.decode(colorForm)); bt_Eliminar.setBackground(Color.decode(colorForm));
        bt_cancel.setBackground(Color.decode(colorForm)); bt_find.setBackground(Color.decode(colorForm));
        bt_Atras.setBackground(Color.decode(colorForm)); bt_Siguiente.setBackground(Color.decode(colorForm));
        bt_salir.setBackground(Color.decode(colorForm)); 
        
        bt_agregar.setForeground(Color.decode(colorText)); bt_Modificar.setForeground(Color.decode(colorText));
        bt_save.setForeground(Color.decode(colorText)); bt_Eliminar.setForeground(Color.decode(colorText));
        bt_cancel.setForeground(Color.decode(colorText)); bt_find.setForeground(Color.decode(colorText));
        bt_Atras.setForeground(Color.decode(colorText)); bt_Siguiente.setForeground(Color.decode(colorText));
        bt_salir.setForeground(Color.decode(colorText)); 
        
        bt_agregar.setHorizontalAlignment(2); bt_Eliminar.setHorizontalAlignment(2); bt_Modificar.setHorizontalAlignment(2);
        bt_Siguiente.setHorizontalAlignment(2); bt_Atras.setHorizontalAlignment(2); bt_cancel.setHorizontalAlignment(2);
        bt_find.setHorizontalAlignment(2); bt_salir.setHorizontalAlignment(2); bt_save.setHorizontalAlignment(2);
        
        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
        jLabel5.setForeground(Color.decode(colorText));
        
        jCheckDir_Activo.setForeground(Color.decode(colorText));
        
        jTxtDir_Codigo.setForeground(Color.decode(colorText)); jTxtDir_Descri.setForeground(Color.decode(colorText));
        
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

        jCheckDir_Activo.setText((String) elementos.get(6));
        
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
        
        cargatabla = new CargaTablas();
        String SQL = "SELECT DIR_CODIGO,DIR_DESCRI FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0), (String) header_table.get(1)};
        cargatabla.cargatablas(Tabla,SQL,columnas);
        
        this.setTitle("Direcciones");
        this.jCheckDir_Activo.setSelected(true);
        
        //this.setSize(540, 540);
        
        if (Tabla.getRowCount()==0){
            habilitar("Inicializa");
            posicion_botones_2();
            
            combopais();
            CodConsecutivo();
            
            jTxtDir_Codigo.setEnabled(false);
            jTxtDir_Descri.requestFocus();
            
            lAgregar=true;
        }else{
            posicion_botones_1();
            Deshabilitar();
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            
            Hilo_Direcciones Direcciones = new Hilo_Direcciones();
            Direcciones.start();
            
            Tabla.getSelectionModel().setSelectionInterval(Tabla.getRowCount()-1, Tabla.getRowCount()-1);
        }
    }
    
    public void Cargardatos() throws ClassNotFoundException, SQLException{
        String SQL = "SELECT * FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        Reg_count = conet.Count_Reg(SQL_Reg);
        
        if (Reg_count==1){
            rs.next();
        }else{
            rs.last();
        }
        
        Hilo_Direcciones Direcciones = new Hilo_Direcciones();
        Direcciones.start();
    }

    public void mostrar() throws SQLException{
        if (Reg_count > 0){
            jTxtDir_Codigo.setText(rs.getString("DIR_CODIGO").trim()); jTxtDir_Descri.setText(rs.getString("DIR_DESCRI").trim()); 
            
            combopais();
            
            try {
                String sql_pais = "SELECT PAI_NOMBRE FROM DNPAIS WHERE PAI_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PAI_ID="+rs.getInt("DIR_CODPAI");
                rs_pais = conet.consultar(sql_pais);
                pais=rs.getString("DIR_CODPAI").trim();
                comboestado();
                jCmbDir_CodPai.setSelectedItem(rs_pais.getString("PAI_NOMBRE").toString().trim());
                
                String sql_estado = "SELECT EST_NOMBRE FROM DNESTADOS WHERE EST_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND EST_ID="+rs.getInt("DIR_CODEST");
                rs_estado = conet.consultar(sql_estado);

                estado=rs.getString("DIR_CODEST").trim();
                combomunicipio();
                jCmbDir_CodEst.setSelectedItem(rs_estado.getString("EST_NOMBRE").toString().trim());
                
                String sql_munic = "SELECT MUN_NOMBRE FROM DNMUNICIPIOS WHERE MUN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MUN_ID="+rs.getInt("DIR_CODMUN");
                rs_muni = conet.consultar(sql_munic);
                muni=rs.getString("DIR_CODMUN").trim();
                comboparroquia();
                jCmbDir_CodMun.setSelectedItem(rs_muni.getString("MUN_NOMBRE").toString().trim());
                
                String sql_parroq = "SELECT PAR_NOMBRE FROM DNPARROQUIAS WHERE PAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PAR_ID="+rs.getInt("DIR_CODPAR");
                rs_parro = conet.consultar(sql_parroq);
                parro=rs.getString("DIR_CODPAR").trim(); 
                jCmbDir_CodPar.setSelectedItem(rs_parro.getString("PAR_NOMBRE").toString().trim());
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void Limitar(){
        jTxtDir_Codigo.setDocument(new LimitarCaracteres(jTxtDir_Codigo, 10));
    }
    
    public void CodConsecutivo(){
        String Consecutivo = null; 
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        //Consecutivo = codigo.CodigoConsecutivo("SELECT CONCAT(REPEAT('0',10-LENGTH(CONVERT(MAX(DIR_CODIGO)+1,CHAR(10)))),CONVERT(MAX(DIR_CODIGO)+1,CHAR(10))) AS CODIGO FROM DNDIRECCION");
        Consecutivo = codigo.CodigoConsecutivo("DIR_CODIGO","DNDIRECCION",10," WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        this.jTxtDir_Codigo.setText(Consecutivo);
        
        if (jTxtDir_Codigo.getText().trim().isEmpty()){
            this.jTxtDir_Codigo.setText("0000000001");
        }
    }
    
    public void Deshabilitar(){;
        jTxtDir_Codigo.setEnabled(false); jTxtDir_Descri.setEnabled(false);
        jCmbDir_CodPai.setEnabled(false); jCmbDir_CodEst.setEnabled(false);
        jCmbDir_CodMun.setEnabled(false); jCmbDir_CodPar.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            jTxtDir_Codigo.setEnabled(true); jTxtDir_Descri.setEnabled(true);
            jCmbDir_CodPai.setEnabled(true); jCmbDir_CodEst.setEnabled(true);
            jCmbDir_CodMun.setEnabled(true); jCmbDir_CodPar.setEnabled(true);
            
            jTxtDir_Descri.requestFocus();
        }else if (accion=="Modificar"){
            jTxtDir_Codigo.setEnabled(false); jTxtDir_Descri.setEnabled(true);
            jCmbDir_CodPai.setEnabled(true); jCmbDir_CodEst.setEnabled(true);
            jCmbDir_CodMun.setEnabled(true); jCmbDir_CodPar.setEnabled(true);
            
            jTxtDir_Descri.requestFocus();
        }
    }
    
    public void posicion_botones_1(){
        bt_agregar.setEnabled(true);
        
        bt_Modificar.setVisible(true); bt_find.setVisible(true); bt_Atras.setVisible(true);
        bt_Siguiente.setVisible(true); bt_salir.setVisible(true); bt_Eliminar.setVisible(true);
        
        bt_save.setVisible(false); bt_cancel.setVisible(false);
    }
    
    public void posicion_botones_2(){
        bt_agregar.setEnabled(false);
        
        bt_Modificar.setVisible(false); bt_find.setVisible(false); bt_Atras.setVisible(false);
        bt_Siguiente.setVisible(false); bt_salir.setVisible(false); bt_Eliminar.setVisible(false);
        
        bt_save.setVisible(true); bt_cancel.setVisible(true);
    }
        
    public void combopais(){
        String sql = "SELECT PAI_NOMBRE AS DATO1 FROM DNPAIS WHERE PAI_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCmbDir_CodPai.setModel(mdl); 
   }
    
    public void comboestado(){
        String sql = "SELECT EST_NOMBRE AS DATO1 FROM DNESTADOS WHERE EST_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND EST_CODPAI='"+pais+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCmbDir_CodEst.setModel(mdl);
    }
    
    public void combomunicipio(){
        String sql = "SELECT MUN_NOMBRE AS DATO1 FROM DNMUNICIPIOS WHERE MUN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MUN_CODEST='"+estado+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCmbDir_CodMun.setModel(mdl);
    }
    
    public void comboparroquia(){
        String sql = "SELECT PAR_NOMBRE AS DATO1 FROM DNPARROQUIAS WHERE PAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PAR_CODMUN='"+muni+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCmbDir_CodPar.setModel(mdl);      
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTxtDir_Codigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtDir_Descri = new javax.swing.JTextArea();
        jCmbDir_CodPai = new javax.swing.JComboBox();
        jCmbDir_CodEst = new javax.swing.JComboBox();
        jCmbDir_CodMun = new javax.swing.JComboBox();
        jCmbDir_CodPar = new javax.swing.JComboBox();
        jCheckDir_Activo = new javax.swing.JCheckBox();
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
        jMenu_VerFactura = new javax.swing.JMenuItem();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")), "Datos de Dirección", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jTxtDir_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 129, -1));

        jLabel1.setText("Codigo");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel2.setText("Direccion");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel3.setText("Pais");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel4.setText("Estado");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel5.setText("Municipio");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jLabel6.setText("Parroquia");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jTxtDir_Descri.setColumns(20);
        jTxtDir_Descri.setRows(5);
        jScrollPane2.setViewportView(jTxtDir_Descri);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 330, 70));

        jCmbDir_CodPai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCmbDir_CodPaiMouseClicked(evt);
            }
        });
        jCmbDir_CodPai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbDir_CodPaiActionPerformed(evt);
            }
        });
        jCmbDir_CodPai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbDir_CodPaiKeyPressed(evt);
            }
        });
        jPanel2.add(jCmbDir_CodPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 200, -1));

        jCmbDir_CodEst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCmbDir_CodEstMouseClicked(evt);
            }
        });
        jCmbDir_CodEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbDir_CodEstActionPerformed(evt);
            }
        });
        jCmbDir_CodEst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbDir_CodEstKeyPressed(evt);
            }
        });
        jPanel2.add(jCmbDir_CodEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 200, -1));

        jCmbDir_CodMun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCmbDir_CodMunMouseClicked(evt);
            }
        });
        jCmbDir_CodMun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbDir_CodMunActionPerformed(evt);
            }
        });
        jCmbDir_CodMun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbDir_CodMunKeyPressed(evt);
            }
        });
        jPanel2.add(jCmbDir_CodMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 200, -1));

        jCmbDir_CodPar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCmbDir_CodParMouseClicked(evt);
            }
        });
        jCmbDir_CodPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbDir_CodParActionPerformed(evt);
            }
        });
        jCmbDir_CodPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbDir_CodParKeyPressed(evt);
            }
        });
        jPanel2.add(jCmbDir_CodPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 200, -1));

        jCheckDir_Activo.setFont(new java.awt.Font("Roboto Light", 2, 14)); // NOI18N
        jCheckDir_Activo.setText("Activo");
        jCheckDir_Activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckDir_ActivoActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckDir_Activo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

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
        jScrollPane1.setViewportView(Tabla);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_agregarActionPerformed(evt);
            }
        });
        bt_agregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_agregarKeyPressed(evt);
            }
        });
        jToolBar1.add(bt_agregar);

        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ModificarActionPerformed(evt);
            }
        });
        bt_Modificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_ModificarKeyPressed(evt);
            }
        });
        jToolBar1.add(bt_Modificar);

        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setLabel("Grabar");
        bt_save.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });
        bt_save.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_saveKeyPressed(evt);
            }
        });
        jToolBar1.add(bt_save);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_EliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Eliminar);

        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancel.png"))); // NOI18N
        bt_cancel.setLabel("Cancelar");
        bt_cancel.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_cancel);

        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_find);

        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Anterior");
        bt_Atras.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AtrasActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Atras);

        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Adelante");
        bt_Siguiente.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SiguienteActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_Siguiente);

        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Salir");
        bt_salir.setPreferredSize(new java.awt.Dimension(115, 45));
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });
        jToolBar1.add(bt_salir);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu_VerFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_VerFacturaActionPerformed
       // Ver_Reporte(evt);
    }//GEN-LAST:event_jMenu_VerFacturaActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jCmbDir_CodPaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbDir_CodPaiActionPerformed
        //pais = jCmbDir_CodPai.getSelectedItem().toString().substring(0, jCmbDir_CodPai.getSelectedItem().toString().indexOf("-")).trim();
        
        try {
            String sql_pais = "SELECT PAI_ID FROM DNPAIS WHERE PAI_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                              "PAI_NOMBRE='"+jCmbDir_CodPai.getSelectedItem().toString().trim()+"'";
            rs_pais = conet.consultar(sql_pais);
            pais = rs_pais.getString("PAI_ID");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        comboestado();
    }//GEN-LAST:event_jCmbDir_CodPaiActionPerformed

    private void jCmbDir_CodMunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbDir_CodMunMouseClicked
        String estado = (String) jCmbDir_CodEst.getSelectedItem();
        String sql = "SELECT MUN_ID FROM DNMUNICIPIOS INNER JOIN DNESTADOS ON EST_ID=MUN_CODEST AND EST_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                     "WHERE MUN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MUN_CODEST='"+estado+"' GROUP BY MUN_ID";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.jCmbDir_CodMun.setModel(mdl);
    }//GEN-LAST:event_jCmbDir_CodMunMouseClicked

    private void jCmbDir_CodParMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbDir_CodParMouseClicked
        comboparroquia();
    }//GEN-LAST:event_jCmbDir_CodParMouseClicked

    private void jCmbDir_CodPaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbDir_CodPaiMouseClicked

    }//GEN-LAST:event_jCmbDir_CodPaiMouseClicked

    private void jCmbDir_CodEstMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbDir_CodEstMouseClicked

    }//GEN-LAST:event_jCmbDir_CodEstMouseClicked

    private void bt_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_agregarActionPerformed
        lAgregar=true;
        lModificar=false;

        habilitar("Inicializa");
        jTxtDir_Descri.setText("");
        
        CodConsecutivo();
        combopais();
        comboestado();
        combomunicipio();
        comboparroquia();
        
        posicion_botones_2();
    }//GEN-LAST:event_bt_agregarActionPerformed

    private void bt_agregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_agregarKeyPressed

    }//GEN-LAST:event_bt_agregarKeyPressed

    private void bt_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ModificarActionPerformed
        lAgregar=false;
        lModificar=true;

        habilitar("Modificar");

        posicion_botones_2();
    }//GEN-LAST:event_bt_ModificarActionPerformed

    private void bt_ModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_ModificarKeyPressed

    }//GEN-LAST:event_bt_ModificarKeyPressed

    private void bt_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_EliminarActionPerformed
        // TODO add your handling code here:
        String codigo = "";
        int eliminado;

        codigo = jTxtDir_Codigo.getText().trim();

        if(codigo.trim().isEmpty()){
            codigo = (String) Tabla.getValueAt(Tabla.getSelectedRow(),0).toString().trim();
        }

        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'",true,"");

            //---------- Refresca la Tabla para vizualizar los ajustes ----------
            String SQL = "SELECT DIR_CODIGO,DIR_DESCRI FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
            //-------------------------------------------------------------------

            cargatabla.cargatablas(Tabla,SQL,columnas);
        }

        Hilo_Direcciones Direccion = new Hilo_Direcciones();
        Direccion.start();
    }//GEN-LAST:event_bt_EliminarActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed

    }//GEN-LAST:event_bt_findActionPerformed

    private void bt_AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AtrasActionPerformed
        try {
            if(atras==-1){
                return;
            }

            if (atras==-2){
                atras=Tabla.getRowCount()-1;

                adelante=0;
            }

            atras=atras-1;
            adelante=adelante-1;
            System.out.println("Atras: "+atras);

            if (atras!=-2){
                ActualizaJtable(atras);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bt_AtrasActionPerformed

    private void bt_SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SiguienteActionPerformed
        int Reg=0;
        Reg=Tabla.getRowCount();

        if (atras==-2){
            adelante=Reg;
        }
        System.out.println("Atras Reg: "+atras);
        System.out.println("Reg: "+Reg);
        System.out.println("Adelante Reg: "+adelante);
        try {
            if (adelante==Reg){
                atras=Reg-1;
                ActualizaJtable(adelante-1);
                return;
            }

            if (atras==-1 || atras==-2){
                adelante=1;
                atras=1;
            }else{
                if (adelante<Reg){
                    adelante=atras+1;
                    atras=atras+1;
                }
            }

            System.out.println("Adelante: "+adelante);

            if (adelante<Reg){
                ActualizaJtable(adelante);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_bt_SiguienteActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        this.dispose();  //Codigo para Salir o Cerrar un Formulario
    }//GEN-LAST:event_bt_salirActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        action_bt_agregar();
    }//GEN-LAST:event_bt_saveActionPerformed

    private void bt_saveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_saveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_bt_agregar();
        }
    }//GEN-LAST:event_bt_saveKeyPressed

    private void bt_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelActionPerformed
        int eleccion = 0;

        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1));
        }else if (lModificar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(2));
        }

        if (eleccion == 0) {
            if (Tabla.getRowCount()==0){
                this.dispose();
                return;
            }

            Deshabilitar();
            posicion_botones_1();

            try {
                Cargardatos();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_cancelActionPerformed

    private void jCheckDir_ActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckDir_ActivoActionPerformed

    }//GEN-LAST:event_jCheckDir_ActivoActionPerformed

    private void jCmbDir_CodEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbDir_CodEstActionPerformed
        try {
            String sql_pais = "SELECT EST_ID FROM DNESTADOS WHERE EST_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND EST_NOMBRE='"+jCmbDir_CodEst.getSelectedItem().toString().trim()+"'";
            rs_estado = conet.consultar(sql_pais);
            estado = rs_estado.getString("EST_ID");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        combomunicipio();
    }//GEN-LAST:event_jCmbDir_CodEstActionPerformed

    private void jCmbDir_CodMunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbDir_CodMunActionPerformed
        try {
            String sql_pais = "SELECT MUN_ID FROM DNMUNICIPIOS WHERE MUN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MUN_NOMBRE='"+jCmbDir_CodMun.getSelectedItem().toString().trim()+"'";
            rs_muni = conet.consultar(sql_pais);
            muni = rs_muni.getString("MUN_ID");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        comboparroquia();
    }//GEN-LAST:event_jCmbDir_CodMunActionPerformed

    private void jCmbDir_CodParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbDir_CodParActionPerformed
        try {
            String sql_pais = "SELECT PAR_ID FROM DNPARROQUIAS WHERE PAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PAR_NOMBRE='"+jCmbDir_CodPar.getSelectedItem().toString().trim()+"'";
            rs_parro = conet.consultar(sql_pais);
            parro = rs_parro.getString("PAR_ID");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Direcciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jCmbDir_CodParActionPerformed

    private void jCmbDir_CodPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbDir_CodPaiKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCmbDir_CodEst.requestFocus();
        }
    }//GEN-LAST:event_jCmbDir_CodPaiKeyPressed

    private void jCmbDir_CodEstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbDir_CodEstKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCmbDir_CodMun.requestFocus();
        }
    }//GEN-LAST:event_jCmbDir_CodEstKeyPressed

    private void jCmbDir_CodMunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbDir_CodMunKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCmbDir_CodPar.requestFocus();
        }
    }//GEN-LAST:event_jCmbDir_CodMunKeyPressed

    private void jCmbDir_CodParKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbDir_CodParKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_jCmbDir_CodParKeyPressed

    private void action_bt_agregar(){
        String descrip = ""; String activo = "";

        if (jCheckDir_Activo.isSelected()==true){
            activo = "1";
        }else{
            activo = "0";
        }

        if ("".equals(jTxtDir_Codigo.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(3));
            jTxtDir_Codigo.requestFocus();
            return;
        }
        if ("".equals(jTxtDir_Descri.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(4));
            jTxtDir_Descri.requestFocus();
            return;
        }

        codigo = jTxtDir_Codigo.getText(); descrip = jTxtDir_Descri.getText();

        //jTxtDir_Codigo.setText(""); jTxtDir_Codigo.setText("");

        if (lAgregar==true){
            // Llama la Clase Insert para Guardar los Registros
            SQLQuerys insertar = new SQLQuerys();

            insertar.SqlInsert("INSERT INTO DNDIRECCION (DIR_EMPRESA,DIR_USER,DIR_MACPC,DIR_CODIGO,DIR_CODPAI,DIR_CODEST,DIR_CODMUN,DIR_CODPAR,DIR_DESCRI,DIR_ACTIVO) "+
                                                "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+codigo+"',"+
                                                        ""+pais+","+estado+","+muni+","+parro+","+
                                                        "'"+descrip+"','"+activo+"');");
            
            jTxtDir_Codigo.requestFocus();
            //CodConsecutivo();
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Direccion ''"+descrip+"'' con el Código #: "+codigo+" Correctamente");
        }else if (lModificar==true){
            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNDIRECCION SET DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"',DIR_CODIGO='"+codigo+"',"+
                                                       "DIR_DESCRI='"+descrip+"',DIR_ACTIVO='"+activo+"',"+
                                                       "DIR_CODPAI="+pais+",DIR_CODEST="+estado+",DIR_CODMUN="+muni+","+
                                                       "DIR_CODPAR="+parro+" "+
                                "WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'");
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la Direccion ''"+descrip+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT DIR_CODIGO,DIR_DESCRI FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
            
        cargatabla.cargatablas(Tabla,SQL,columnas);
        //-------------------------------------------------------------------
        
        try {
            posicion_botones_1();
            Deshabilitar();
            Cargardatos();
            
            lModificar=false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public class Hilo_Direcciones extends Thread{
        public void run(){
            try {
                String SQLCodTipMae="";
            
                SQLCodTipMae = "SELECT DIR_CODIGO FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY DIR_CODIGO DESC LIMIT 1 ";
                
                rs_codigo = conet.consultar(SQLCodTipMae);
                try {
                    if(lModificar==false){
                        codigo=rs_codigo.getString("DIR_CODIGO").trim();
                    }else{
                        codigo=jTxtDir_Codigo.getText().toString().trim();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println(codigo);
                String SQL = "SELECT * FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'";
                System.out.println(SQL);
                try {
                    rs = conet.consultar(SQL);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'";
                Reg_count = conet.Count_Reg(SQLReg);
                try {
                    mostrar();
                } catch (SQLException ex) {
                    Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    private void action_tablas(int Row){
        codigo = (String) Tabla.getValueAt(Row,0).toString().trim();
        
        String SQL = "SELECT * FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'";
        try {
            rs = conet.consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
        }

        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNDIRECCION WHERE DIR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DIR_CODIGO='"+codigo+"'";
        Reg_count = conet.Count_Reg(SQLReg);

        try {
            mostrar();
        } catch (SQLException ex) {
            Logger.getLogger(Maestros.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.Bandera=true;
    }
    
    private void ActualizaJtable(int item){
        action_tablas(item);
        Tabla.getSelectionModel().setSelectionInterval(item, item);
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
    private javax.swing.JCheckBox jCheckDir_Activo;
    public javax.swing.JComboBox jCmbDir_CodEst;
    public javax.swing.JComboBox jCmbDir_CodMun;
    public javax.swing.JComboBox jCmbDir_CodPai;
    public javax.swing.JComboBox jCmbDir_CodPar;
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
    private javax.swing.JMenuItem jMenu_VerFactura;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jTxtDir_Codigo;
    private javax.swing.JTextArea jTxtDir_Descri;
    // End of variables declaration//GEN-END:variables
}