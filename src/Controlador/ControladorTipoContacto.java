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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControladorTipoContacto {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre;
    private JCheckBox chkActivo;
    
    public ControladorTipoContacto(){
        
    }
    
    public void setComponentes(JTextField codigo, JTextField nombre, JCheckBox activo){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;;
        this.chkActivo = activo;
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        xmlFile(clase);
        
        if ("".equals(tfCodigo.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(4));
            tfCodigo.requestFocus();
            return;
        }
        if ("".equals(tfNombre.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(5));
            tfNombre.requestFocus();
            return;
        }
        
        if (lAgregar==true){
            // Llama la Clase Insert para Guardar los Registros
            SQLQuerys insertar = new SQLQuerys();
            String sql = "INSERT INTO DNTIPCONTACTO (TCON_EMPRESA,TCON_USER,TCON_MACPC,TCON_CODIGO,TCON_NOMBRE,TCON_ACTIVO) "+
                                            "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+VarGlobales.getIdUser()+"','"+
                                                        VarGlobales.getMacPc()+"','"+codigo+"',"+
                                                    "'"+descrip+"','"+activo+"');";
            insertar.SqlInsert(sql);
            
//            jTxtAct_Codigo.requestFocus();
            //CodConsecutivo();
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Tipo de Maestro ''"+descrip+"'' con el Código #: "+codigo+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNTIPCONTACTO SET TCON_USER='"+VarGlobales.getIdUser()+"',TCON_MACPC='"+VarGlobales.getMacPc()+"',"+
                                                         "TCON_CODIGO='"+codigo+"',TCON_NOMBRE='"+descrip+"',"+
                                                         "TCON_ACTIVO='"+activo+"' "+
                                                         "WHERE TCON_CODIGO='"+codigo+"'");

            //---------- Refresca la Tabla para vizualizar los ajustes ----------
            String SQL = "SELECT TCON_CODIGO,TCON_NOMBRE FROM DNTIPCONTACTO";
            String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
            
            cargatabla.cargatablas(tabla,SQL,columnas);
            //-------------------------------------------------------------------
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Tipo de Maestro ''"+descrip+"'' Correctamente");
        }
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(2)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+codigo+"'",true,"");
            
            cargaTabla(tabla, clase);
        }
    }
    
    public int cancelar(Boolean lAgregar, String clase){
        int eleccion = 0;
        xmlFile(clase);
        
        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(0));
        }else if (lAgregar==false){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1));
        }
        
        return eleccion;
    }
    
    public void cargaTabla(JTable tabla, String clase){
        xmlFile(clase);
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT TCON_CODIGO,TCON_NOMBRE FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("TCON_CODIGO","DNTIPCONTACTO",10," WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="0000000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT TCON_CODIGO,TCON_NOMBRE FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT TCON_CODIGO FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY TCON_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("TCON_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorTipoContacto.class.getName()).log(Level.SEVERE, null, ex);
            }

            String SQL = "SELECT * FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorTipoContacto.class.getName()).log(Level.SEVERE, null, ex);
            }

            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNTIPCONTACTO WHERE TCON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TCON_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorTipoContacto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
 
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("TCON_CODIGO").trim()); tfNombre.setText(rs.getString("TCON_NOMBRE").trim());
                
                if (rs.getBoolean("TCON_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
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
        chkActivo.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true);
            chkActivo.setEnabled(true);
            
            limpiar();
            
            tfNombre.requestFocus();
        }else if (accion=="Modificar"){
            tfCodigo.setEnabled(false); tfNombre.setEnabled(true);
            chkActivo.setEnabled(true);
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){
        tfNombre.setText(""); 
        chkActivo.setSelected(true);
    }
}