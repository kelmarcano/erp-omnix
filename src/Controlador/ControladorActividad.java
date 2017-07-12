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

public class ControladorActividad {
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
    
    public ControladorActividad(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JCheckBox activo){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;;
        this.chkActivo = activo;
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        xmlFile(clase);
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        String [] filas={codigo,descrip};
        model.addRow(filas);
        
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
            String sql = "INSERT INTO DNACTIVIDAD_ECO (ACT_EMPRESA,ACT_USER,ACT_MACPC,ACT_CODIGO,ACT_NOMBRE,ACT_ACTIVO) "+
                                              "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+VarGlobales.getIdUser()+"','"+
                                                          VarGlobales.getMacPc()+"','"+codigo+"',"+
                                                       "'"+descrip+"','"+activo+"');";
            insertar.SqlInsert(sql);
            
//            jTxtAct_Codigo.requestFocus();
            //CodConsecutivo();
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Actividad ''"+descrip+"'' con el Código #: "+codigo+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNACTIVIDAD_ECO SET ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"',ACT_CODIGO='"+codigo+"',ACT_NOMBRE='"+descrip+"',"+
                                                       "ACT_ACTIVO="+activo+" "+
                                "WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_CODIGO='"+codigo+"'");

            //---------- Refresca la Tabla para vizualizar los ajustes ----------
            String SQL = "SELECT ACT_CODIGO,ACT_NOMBRE FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
            
            cargatabla.cargatablas(tabla,SQL,columnas);
            //-------------------------------------------------------------------
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la Actividad ''"+descrip+"'' Correctamente");
        }
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_CODIGO='"+codigo+"'",true,"");
            
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
        
        return eleccion;
    }
    
    public void cargaTabla(JTable tabla, String clase){
        xmlFile(clase);
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT ACT_CODIGO,ACT_NOMBRE FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("ACT_CODIGO","DNACTIVIDAD_ECO",6," WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="000001";
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
        try {
            String SQLCodTipMae="";
            
            SQLCodTipMae = "SELECT ACT_CODIGO FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY ACT_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("ACT_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND ACT_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("ACT_CODIGO").trim()); tfNombre.setText(rs.getString("ACT_NOMBRE").trim());
                
                if (rs.getBoolean("ACT_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorActividad.class.getName()).log(Level.SEVERE, null, ex);
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