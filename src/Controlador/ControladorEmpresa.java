package Controlador;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.CargaTablas;
import clases.CodigoConsecutivo;
import clases.ReadFileXml;
import clases.SQLQuerys;
import clases.ValidaCodigo;
import clases.conexion;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ControladorEmpresa {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre;
    private JFormattedTextField ftfRif;
    private JTextArea taDireccion;
    private JCheckBox chkActivo;
    
    public ControladorEmpresa(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JFormattedTextField rif, JTextArea direccion, JCheckBox activo){
        this.tfCodigo = codigo;       tfCodigo.setFont(new java.awt.Font("Monospaced", 0, 12));
        this.tfNombre = nombre;       tfNombre.setFont(new java.awt.Font("Monospaced", 0, 12));
        this.ftfRif = rif;            ftfRif.setFont(new java.awt.Font("Monospaced", 0, 12));
        this.taDireccion = direccion; taDireccion.setFont(new java.awt.Font("Monospaced", 0, 12));
        this.chkActivo = activo;      chkActivo.setFont(new java.awt.Font("Monospaced", 0, 12));
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        String valor = ""; String fechavigen = "";
        
        xmlFile(clase);
        
        if ("".equals(tfCodigo.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(3));
            tfCodigo.requestFocus();
            return;
        }
        if ("".equals(tfNombre.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(4));
            tfNombre.requestFocus();
            return;
        }
        if ("".equals(ftfRif.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(6));
            ftfRif.requestFocus();
            return;
        }
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sql = "INSERT INTO dnempresas (EMP_USER,EMP_MACPC,EMP_CODIGO,EMP_NOMBRE,EMP_RIF,EMP_DIRECCION,EMP_ACTIVO) "+
                                                "VALUES ('"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+codigo+"',"+
                                                        "'"+descrip+"','"+ftfRif.getText().toString()+"',"+
                                                        "'"+taDireccion.getText().toString()+"','"+activo+"');";

            insertar.SqlInsert(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Empresa ''"+tfNombre.getText().toString()+
                                                 "'' con el RIF : "+ftfRif.getText().toString()+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE dnempresas SET EMP_CODIGO='"+codigo+"',EMP_NOMBRE='"+descrip+"',"+
                                               "EMP_RIF='"+ftfRif.getText().toString()+"',EMP_DIRECCION='"+taDireccion.getText().toString()+"',"+
                                               "EMP_ACTIVO="+activo+" "+
                                "WHERE EMP_CODIGO='"+codigo+"'";
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                                 "Modificación de Registro", "Se Modifico la Empresa ''"+
                                                  tfNombre.getText().toString()+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla, clase);
        //-------------------------------------------------------------------
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        String nombre = null;
        int eliminado;
        xmlFile(clase);

        if(codigo.trim().isEmpty()){
            codigo = (String) tabla.getValueAt(tabla.getSelectedRow(),0).toString().trim();
            nombre = (String) tabla.getValueAt(tabla.getSelectedRow(),1).toString().trim();
        }
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM dnempresas WHERE EMP_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM dnempresas WHERE EMP_CODIGO='"+codigo+"'",true,"");
            
            cargaTabla(tabla, clase);
        }
    }
    
    public int cancelar(Boolean lAgregar, String clase){
        int eleccion = 0;
        xmlFile(clase);
        
        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1));
        }else if (lAgregar==false){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(2));
        }
        
        if (eleccion==1){
            ftfRif.requestFocus();
        }
        
        return eleccion;
    }
    
    public void cargaTabla(JTable tabla, String clase){
        xmlFile(clase);
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT EMP_CODIGO,EMP_NOMBRE FROM dnempresas";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("EMP_CODIGO","dnempresas",6,"");
        
        if (consecutivo==null){
            consecutivo="000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT * FROM dnempresas";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM dnempresas";
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
        try {
            String SQLCodTipMae="";
            
            SQLCodTipMae = "SELECT EMP_CODIGO FROM dnempresas ORDER BY EMP_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("EMP_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM dnempresas WHERE EMP_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM dnempresas WHERE EMP_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("EMP_CODIGO").trim()); tfNombre.setText(rs.getString("EMP_NOMBRE").trim());
                ftfRif.setText(rs.getString("EMP_RIF")); 
                taDireccion.setText(rs.getString("EMP_DIRECCION").trim());
                
                if (rs.getBoolean("EMP_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorEmpresa.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void deshabilitar(){
        tfCodigo.setEnabled(false); tfNombre.setEnabled(false);
        ftfRif.setEnabled(false); taDireccion.setEnabled(false);
        chkActivo.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfNombre.setEnabled(true);
            ftfRif.setEnabled(true); taDireccion.setEnabled(true);
            chkActivo.setEnabled(true);
            
            limpiar();
            
            ftfRif.requestFocus();
            lAgregar = true;
        }else if (accion=="Modificar"){
            tfNombre.setEnabled(true); ftfRif.setEnabled(true); taDireccion.setEnabled(true);
            chkActivo.setEnabled(true);
            
            ftfRif.requestFocus();
            lAgregar = false;
        }
    }
    
    private void limpiar(){
        tfNombre.setText(""); ftfRif.setText(""); taDireccion.setText("");
        chkActivo.setSelected(true);
    }
    
    public void validarRif(Vector DatosFiscales){
        if (lAgregar==true){
            if (DatosFiscales.elementAt(0)=="El RIF ingresado es invalido...!"){
                ftfRif.setText("");
                ftfRif.requestFocus();
            } else{
                ftfRif.setText((String) DatosFiscales.elementAt(0));
                tfNombre.setText((String) DatosFiscales.elementAt(1));
            }
        }

        if (lAgregar==true){
            ValidaCodigo consulta = new ValidaCodigo();
            int registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM dnempresas WHERE EMP_RIF='"+ftfRif.getText().trim()+"'",false,"RIF");

            if (registros==1){
                ftfRif.requestFocus();
                ftfRif.setText("");
                tfNombre.setText("");
            }
        }   
    }
}