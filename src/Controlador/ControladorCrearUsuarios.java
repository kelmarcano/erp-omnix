package Controlador;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import static Pantallas.principal.escritorio;
import static Vista.Login.Idioma;
import clases.CargaComboBoxs;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import clases.ImagenFondo;
import clases.ReadFileXml;
import clases.SQLQuerys;
import clases.ValidaCodigo;
import clases.conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControladorCrearUsuarios {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_permiso;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, Clave="";
    private Boolean lAgregar, lPass=false;

    private JLabel imgDesktUser;
    private JTextField tfCodigo, tfNombre, tfClave, tfUsuario, tfCorreo, tfRutaImg;
    private JPasswordField pfClave1;
    private JCheckBox chkActivo, accAndroid, accWeb;
    private JComboBox cbCargo, cbTipo;

    public ControladorCrearUsuarios(){
        
    }
    
    public void setComponentes(JTextField codigo, JTextField nombre, JTextField clave, JPasswordField clave1, 
                               JTextField usuario, JTextField correo, JTextField rutaImg, JCheckBox activo, 
                               JCheckBox accAndroid, JCheckBox accWeb, JComboBox combo1, JComboBox combo2, JLabel imgDesktUser){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;
        this.tfClave = clave;
        this.pfClave1 = clave1;
        this.tfUsuario = usuario;
        this.tfCorreo = correo;
        this.tfRutaImg = rutaImg;
        this.chkActivo = activo;
        this.accAndroid = accAndroid;
        this.accWeb = accWeb;
        this.cbCargo = combo1;
        this.cbTipo = combo2;
        this.imgDesktUser = imgDesktUser;
    }
    
    public void setPass(Boolean lPass){
        if (!VarGlobales.getIdUser().equals(tfCodigo.getText().toString())){
            pfClave1.setVisible(true); pfClave1.setText(Clave);
            tfClave.setVisible(false);
            this.lPass = lPass;
        }
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        String accandroid = ""; String accweb = ""; 
        xmlFile(clase);
        
        if (chkActivo.isSelected()==true){
            activo = "1";
        }else{
            activo = "0";
        }
        
        if (accWeb.isSelected()==true){
            accweb = "1";
        }else{
            accweb = "0";
        }
        
        if (accAndroid.isSelected()==true){
            accandroid = "1";
        }else{
            accandroid = "0";
        }

//        if (this.Bandera==true){
//            JOptionPane.showMessageDialog(null, (String) Msg.get(3));
//            return;
//        }
        if ("".equals(tfCodigo.getText())){
            JOptionPane.showMessageDialog(null, (String) Msg.get(4));
            tfCodigo.requestFocus();
            return;
        }
        if ("".equals(tfNombre.getText())){
            JOptionPane.showMessageDialog(null, (String) Msg.get(5));
            tfNombre.requestFocus();
            return;
        }
        if ("".equals(tfUsuario.getText())){
            JOptionPane.showMessageDialog(null, (String) Msg.get(6));
            tfUsuario.requestFocus();
            return;
        }
        
        if (lPass==true){
            if ("".equals(pfClave1.getText())){
                JOptionPane.showMessageDialog(null, (String) Msg.get(7));
                pfClave1.requestFocus();
                return;
            }
        }else{
            if ("".equals(tfClave.getText())){
                JOptionPane.showMessageDialog(null, (String) Msg.get(7));
                tfClave.requestFocus();
                return;
            }
        }
        
        if (lAgregar==true){
            // Llama la Clase Insert para Guardar los Registros
            SQLQuerys insertar = new SQLQuerys();
            
            String sql = "INSERT INTO dnusuarios (OPE_EMPRESA,OPE_NOMBRE,OPE_CARGO,OPE_CLAVE,OPE_MAPTAB,OPE_MAPMNU,OPE_LUNES,OPE_MARTES,"+
                                                 "OPE_MIERCOLES,OPE_JUEVES,OPE_VIRNES,OPE_SABADO,OPE_DOMINGO,OPE_LUNAIN,"+
                                                 "OPE_LUNAFI,OPE_LUNPIN,OPE_LUNPFIN,OPE_MARAIN,OPE_MARAFI,OPE_MARPIN,OPE_MARPFI,"+
                                                 "OPE_MIEAIN,OPE_MIEAFI,OPE_MIEPIN,OPE_MIEPFI,OPE_JUEAIN,OPE_JUEAFI,OPE_JUEPIN,"+
                                                 "OPE_JUEPFI,OPE_VIEAIN,OPE_VIEAFI,OPE_VIEPIN,OPE_VIEPFI,OPE_SABAIN,OPE_SABAFI,"+
                                                 "OPE_SABPIN,OPE_SABPFI,OPE_DOMAIN,OPE_DOMAFI,OPE_DOMPIN,OPE_DOMPFI,OPE_ACCWEB,"+
                                                 "OPE_ACCANDROID,OPE_ACTIVO,OPE_TIPO_DESKTOP,OPE_STATUS,OPE_CODVEN,OPE_CODZON,OPE_USUARIO,"+
                                                 "OPE_CORREO,OPE_RUTAIMG,OPE_IMGPERFIL,OPE_USUDAT) "+
                                       "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+descrip.toUpperCase()+"','"+cbCargo.getSelectedItem().toString()+"','"+Clave+"',"+
                                               "'','','0','0','0','0','0','0','0', '', '', '', '', '', '', '', '',"+
                                               "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '',"+
                                               "'"+accweb+"','"+accandroid+"','"+activo+"',"+cbTipo.getSelectedItem().toString().substring(0, 4)+","+
                                               "'Activo', '', '','"+tfUsuario.getText().toUpperCase()+"','"+tfCorreo.getText().toString()+"',"+
                                               "'"+tfRutaImg.getText().toString().replace("\\", "/").trim()+"', '', '');";

            insertar.SqlInsert(sql);
            
//            jTxtAct_Codigo.requestFocus();
            //CodConsecutivo();
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Usuario", "Usuario "+tfUsuario.getText()+" Insertado Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE dnusuarios SET OPE_NOMBRE='"+descrip.toUpperCase()+"',OPE_CARGO='"+cbCargo.getSelectedItem().toString()+"',"+
                                               "OPE_CLAVE='"+Clave+"',OPE_ACCWEB='"+accweb+"',OPE_ACCANDROID='"+accandroid+"',OPE_ACTIVO='"+activo+"',"+
                                               "OPE_TIPO_DESKTOP="+cbTipo.getSelectedItem().toString().substring(0, 4)+",OPE_STATUS='Activo',"+
                                               "OPE_USUARIO='"+tfUsuario.getText().toUpperCase()+"',OPE_CORREO='"+tfCorreo.getText().toString()+"',"+
                                               "OPE_RUTAIMG='"+tfRutaImg.getText().toString().replace("\\", "/").trim()+"' "+
                         "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+codigo+"';";
            
            modificar.SqlUpdate(sql);
            
            Bitacora modifica_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                  VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                  "Modificación Usuario", "Usuario "+tfUsuario.getText()+" Modificado Correctamente");
            
            if (VarGlobales.getIdUser().equals(tfCodigo.getText().toString())){
                escritorio.setBorder(new ImagenFondo(VarGlobales.getIdUser()));
            }
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT OPE_NUMERO,OPE_USUARIO,REPEAT('*',LENGTH(OPE_CLAVE)) AS OPE_CLAVE,OPE_CARGO,OPE_TIPO_DESKTOP"+
                 " FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3),(String) header_table.get(4)};
            
        cargatabla.cargatablas(tabla,SQL,columnas);
        //-------------------------------------------------------------------
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            String sql = "DELETE FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+codigo+"'";
            eliminar.SqlDelete(sql);

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+codigo+"'",true,"");
            
            cargaTabla(tabla, clase);
        }
    }
    
    public int cancelar(Boolean lAgregar, String clase){
        int eleccion = 0;
        xmlFile(clase);
        
        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(0));
            tfClave.setVisible(true);
        }else if (lAgregar==false){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1));
        }
        
        return eleccion;
    }
    
    public void cargaTabla(JTable tabla, String clase){
        xmlFile(clase);
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT OPE_NUMERO,OPE_USUARIO,REPEAT('*',LENGTH(OPE_CLAVE)) AS OPE_CLAVE,OPE_CARGO,OPE_TIPO_DESKTOP FROM dnusuarios "+
                     "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY OPE_NUMERO ASC";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3),(String) header_table.get(4)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("OPE_NUMERO","DNUSUARIOS",1,"WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="1";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT ACT_CODIGO,ACT_NOMBRE FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        Reg_count = conet.Count_Reg(SQL_Reg);
        
        if (Reg_count==1){
            rs.next();
        }else{
            rs.last();
        }
        
        ResultSet resultSet = ejecutaHilo(codigo, lAgregar);
        return resultSet;
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar){
        comboCargo();
        comboTipusuario();
        
        try {
            String SQLCodTipMae="";
            
            SQLCodTipMae = "SELECT OPE_NUMERO FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY OPE_NUMERO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("OPE_NUMERO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }

    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("OPE_NUMERO").trim()); tfNombre.setText(rs.getString("OPE_NOMBRE").trim());
                
                if (rs.getBoolean("OPE_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                
                tfUsuario.setText(rs.getString("OPE_USUARIO").trim());
                cbCargo.setSelectedItem(rs.getString("OPE_CARGO").trim()); tfCorreo.setText(rs.getString("OPE_CORREO").trim());
                
                if (rs.getBoolean("OPE_ACCWEB")==true){
                    accWeb.setSelected(true);
                }else{
                    accWeb.setSelected(false);
                }
                if (rs.getBoolean("OPE_ACCANDROID")==true){
                    accAndroid.setSelected(true);
                }else{
                    accAndroid.setSelected(false);
                }
                
                if (rs.getString("OPE_TIPO_DESKTOP")==null || rs.getString("OPE_TIPO_DESKTOP").trim().isEmpty()){
                }else{
                    String SQL = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS OPE_TIPO_DESKTOP FROM DNPERMISO_GRUPAL "+
                            "WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ID="+rs.getString("OPE_TIPO_DESKTOP").trim()+"";
                    
                    try {
                        rs_permiso = conet.consultar(SQL);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    cbTipo.setSelectedItem(rs_permiso.getString("OPE_TIPO_DESKTOP").trim());
                }
                
                Clave = rs.getString("OPE_CLAVE").trim();
                
                int LongClave = Clave.length();
                String clave_codif = "";
                
                for (int i=0; i<LongClave; i++){
                    clave_codif= clave_codif+"*";
                }
                
                tfClave.setText(clave_codif);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void mostrarImagen(String rutaImg) throws SQLException{
        if (Reg_count > 0){
            String ruta_img_fondo = rutaImg; 
            
            try{
                if (rutaImg==null || ruta_img_fondo.trim().isEmpty()){
                    Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(imgDesktUser.getWidth(), imgDesktUser.getHeight(), Image.SCALE_AREA_AVERAGING));
                    imgDesktUser.setIcon(icon);
                }else{
                    Image preview = Toolkit.getDefaultToolkit().getImage(rs.getString("OPE_RUTAIMG").trim());
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(imgDesktUser.getWidth(), imgDesktUser.getHeight(), Image.SCALE_AREA_AVERAGING));
                    imgDesktUser.setIcon(icon);
                }
            }catch (Exception ex){
                Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(imgDesktUser.getWidth(), imgDesktUser.getHeight(), Image.SCALE_AREA_AVERAGING));
                imgDesktUser.setIcon(icon);
            }
        }
    }
    
    private void xmlFile(String clase){
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = clase.toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
        
        File xmlFileHeader = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml_header = new ReadFileXml();
        header_table = xml_header.ReadFileXml(xmlFileHeader, "Header", "form_"+FormClass);
    }
    
    private void comboCargo(){
        String sql = "SELECT PER_NOMBRE AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.cbCargo.setModel(mdl); 
    }
    
    public void comboTipusuario(){
        String sql = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        this.cbTipo.setModel(mdl); 
    }
    
    public void deshabilitar(){
        tfCodigo.setEnabled(false); tfNombre.setEnabled(false); tfUsuario.setEnabled(false); tfCorreo.setEnabled(false);
        tfRutaImg.setEnabled(false); tfClave.setEnabled(false); pfClave1.setVisible(false);
            
        cbCargo.setEnabled(false); cbTipo.setEnabled(false);
        
        chkActivo.setEnabled(false); accAndroid.setEnabled(false); accWeb.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true); tfUsuario.setEnabled(true); tfCorreo.setEnabled(true);
            tfRutaImg.setEnabled(true); tfClave.setEnabled(true); pfClave1.setVisible(true);
            
            cbCargo.setEnabled(true); cbTipo.setEnabled(true);
        
            chkActivo.setEnabled(true); accAndroid.setEnabled(true); accWeb.setEnabled(true);
            
            limpiar();
            
            tfNombre.requestFocus();
        }else if (accion=="Modificar"){
            tfCodigo.setEnabled(false); tfNombre.setEnabled(true); tfUsuario.setEnabled(true); tfCorreo.setEnabled(true);
            tfRutaImg.setEnabled(true); tfClave.setEnabled(true); pfClave1.setVisible(true);
            
            cbCargo.setEnabled(true); cbTipo.setEnabled(true);
        
            chkActivo.setEnabled(true); accAndroid.setEnabled(true); accWeb.setEnabled(true);
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){
        tfNombre.setText(""); tfUsuario.setText("");  tfCorreo.setText("");  tfRutaImg.setText(""); 
        tfClave.setText("");  pfClave1.setText(""); 
        chkActivo.setSelected(true); accAndroid.setEnabled(false); accWeb.setEnabled(false);
    }
}