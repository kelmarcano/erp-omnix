package Controlador;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.CargaTablas;
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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ControladorEstructuraCuentas {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ControladorPlanCuentas planCuentas = new ControladorPlanCuentas();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase;
    private Boolean lAgregar;

    private JComboBox cbPadre, cbNivelMax, cbNivelPref, cbPrefijo, cbLongPref;
    
    public ControladorEstructuraCuentas(){
        
    }

    public void setComponentes(JComboBox ctaPadre, JComboBox ctaNivelPref, JComboBox prefijo, JComboBox longPref, JComboBox ctaNivelMax){
        this.cbPadre = ctaPadre;
        this.cbNivelMax = ctaNivelMax;;
        this.cbNivelPref = ctaNivelPref;
        this.cbPrefijo = prefijo;
        this.cbLongPref = longPref;
    }
    
    public Boolean save(String ctaPadre, String ctaNivelPref, String prefijo, String longPref, String ctaNivelMax, JTable tabla, String activo, Boolean lAgregar, String clase){
        Boolean lSave = true;
        xmlFile(clase);
        
//        if ("".equals(tfCodigo.getText())){
//            JOptionPane.showMessageDialog(null,(String) Msg.get(3));
//            tfCodigo.requestFocus();
//            return
//            lSave = false;
//        }
        
//        if ("".equals(tfNombre.getText())){
//            JOptionPane.showMessageDialog(null,(String) Msg.get(4));
//            tfNombre.requestFocus();
//            return
//            lSave = false;
//        }
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sql = "INSERT INTO DNESTRUCTURA_CTA (STR_EMPRESA,STR_USER,STR_MACPC,STR_PADRE,STR_NIVEL_MAX,"
                                                     + "STR_NIVEL_PREFIJO,STR_PREFIJO,STR_LONG_PREF,STR_ACTIVO) "+
                                    "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+VarGlobales.getIdUser()+"','"+
                                                VarGlobales.getMacPc()+"','"+ctaPadre+"',"+
                                            "'"+ctaNivelMax+"','"+ctaNivelPref+"','"+prefijo+"','"+longPref+"',"+
                                            "'"+activo+"');";

            insertar.SqlInsert(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Configuracion de la Cuenta Principal ''"+ctaPadre+"'' Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNESTRUCTURA_CTA SET STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"',STR_PADRE='"+ctaPadre+"',STR_NIVEL_MAX='"+ctaNivelMax+"',"+
                                                 "STR_NIVEL_PREFIJO='"+ctaNivelPref+"',STR_PREFIJO='"+prefijo+"',"+
                                                 "STR_LONG_PREF='"+longPref+"',STR_ACTIVO="+activo+" "+
                                "WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND STR_PADRE='"+ctaPadre+"'");
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la Cuenta ''"+descrip+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla, clase);
        //-------------------------------------------------------------------
            
        return lSave;
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            if (planCuentas.existeCod(codigo)==0){
                SQLQuerys eliminar = new SQLQuerys();
                eliminar.SqlDelete("DELETE FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND STR_PADRE='"+codigo+"'");

                ValidaCodigo consulta = new ValidaCodigo();
                eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND STR_PADRE='"+codigo+"'",true,"");
            
                cargaTabla(tabla, clase);
            }else{
                JOptionPane.showMessageDialog(null,(String) Msg.get(3), "", JOptionPane.ERROR_MESSAGE);
            }
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
        String SQL = "SELECT STR_PADRE, STR_NIVEL_MAX, STR_NIVEL_PREFIJO, STR_PREFIJO, STR_LONG_PREF FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY STR_PADRE";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1), (String) header_table.get(2), (String) header_table.get(3), (String) header_table.get(4)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT STR_PADRE,STR_NIVEL_MAX,STR_NIVEL_PREFIJO,STR_PREFIJO,STR_LONG_PREF FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY STR_PADRE";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT STR_PADRE FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY STR_PADRE DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("STR_PADRE").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorEstructuraCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND STR_PADRE='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorEstructuraCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNESTRUCTURA_CTA WHERE STR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND STR_PADRE='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorEstructuraCuentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                cbPadre.setSelectedItem(rs.getString("STR_PADRE").trim()); 
                cbNivelMax.setSelectedItem(rs.getString("STR_NIVEL_MAX").trim()); 
                cbNivelPref.setSelectedItem(rs.getString("STR_NIVEL_PREFIJO").trim());
                cbPrefijo.setSelectedItem(rs.getString("STR_PREFIJO").trim());
                cbLongPref.setSelectedItem(rs.getString("STR_LONG_PREF").trim());
            } catch (SQLException ex) {
                Logger.getLogger(ControladorEstructuraCuentas.class.getName()).log(Level.SEVERE, null, ex);
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
        cbPadre.setEnabled(false); cbNivelMax.setEnabled(false);
        cbNivelPref.setEnabled(false); cbPrefijo.setEnabled(false);
        cbLongPref.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            cbPadre.setEnabled(true); cbNivelMax.setEnabled(true);
            cbNivelPref.setEnabled(true); cbPrefijo.setEnabled(true);
            cbLongPref.setEnabled(true);
            
            limpiar();
        }else if (accion=="Modificar"){
            //tfCodigo.setEnabled(false); 
            cbNivelMax.setEnabled(true);
            cbNivelPref.setEnabled(true); cbPrefijo.setEnabled(true);
            cbLongPref.setEnabled(true);
        }
    }
    
    private void limpiar(){
        cbNivelPref.setSelectedIndex(0);
        cbNivelMax.setSelectedIndex(0);
        cbPrefijo.setSelectedIndex(0);
        cbLongPref.setSelectedIndex(0);
        cbPadre.setSelectedIndex(0);
    }
}