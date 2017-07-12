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
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControladorGrupoPermisos {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre;
    private JCheckBox chkActivo;
    private JRadioButton rbAppWeb, rbAppDesktop, rbAppAndroid;
    
    public ControladorGrupoPermisos(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JCheckBox activo, JRadioButton rbAppWeb, JRadioButton rbAppDesktop, JRadioButton rbAppAndroid){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;;
        this.chkActivo = activo;
        this.rbAppWeb = rbAppWeb;
        this.rbAppDesktop = rbAppDesktop;
        this.rbAppAndroid = rbAppAndroid;
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
        
        if (rbAppWeb.isSelected()==false && rbAppDesktop.isSelected()==false && rbAppAndroid.isSelected()==false){
            JOptionPane.showMessageDialog(null, (String) Msg.get(5));
            return;
        }
        
        if (rbAppWeb.isSelected()==true){
            tipomenu="1";
        }else if (rbAppDesktop.isSelected()==true){
            tipomenu="2";    
        }else if (rbAppAndroid.isSelected()==true){
            tipomenu="3";    
        }
        
        if (lAgregar==true){
            // Llama la Clase Insert para Guardar los Registros
            SQLQuerys insertar = new SQLQuerys();
            String sql = "INSERT INTO DNPERMISO_GRUPAL (PER_EMPRESA,PER_ID,PER_NOMBRE,PER_DESCRIPCION,PER_TIPO,PER_ACTIVO,PER_TIPOMENU) "+
                         "VALUES ('"+VarGlobales.getCodEmpresa()+"',"+codigo+",'"+descrip+"','"+descrip+"','0','"+activo+"','"+tipomenu+"');";

            insertar.SqlInsert(sql);
            try {
                CreaPermisologias();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorGrupoPermisos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorGrupoPermisos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Permiso Grupal ''"+descrip+"'' con el Código #: "+codigo+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNPERMISO_GRUPAL SET PER_NOMBRE='"+descrip+"',PER_ACTIVO='"+activo+"',"+
                                                            "PER_TIPOMENU='"+tipomenu+"' "+
                                "WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ID='"+codigo+"'");
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa() ,VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Permiso Grupal ''"+descrip+"'' con el Código #: "+codigo+" Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT PER_ID,PER_NOMBRE,PER_TIPOMENU FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0), (String) header_table.get(1), (String) header_table.get(2)};
            
        cargatabla.cargatablas(tabla,SQL,columnas);
        //-------------------------------------------------------------------
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);

        if(codigo.trim().isEmpty()){
            codigo = (String) tabla.getValueAt(tabla.getSelectedRow(),0).toString().trim();
        }
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ID='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ID='"+codigo+"'",true,"");
            
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
        String SQL = "SELECT PER_ID,PER_NOMBRE,PER_TIPOMENU FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0), (String) header_table.get(1), (String) header_table.get(2)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("PER_ID","DNPERMISO_GRUPAL",4,"WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="1028";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT PER_ID,PER_NOMBRE,PER_TIPOMENU FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT PER_ID FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PER_ID DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("PER_ID").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorGrupoPermisos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ID='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorGrupoPermisos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ID='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorGrupoPermisos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("PER_ID").trim()); tfNombre.setText(rs.getString("PER_NOMBRE").trim());
                
                if (rs.getBoolean("PER_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                
                if (rs.getString("PER_TIPOMENU").equals("1")){
                   rbAppWeb.setSelected(true); rbAppDesktop.setSelected(false); rbAppAndroid.setSelected(false);
                }else if (rs.getString("PER_TIPOMENU").equals("2")){
                    rbAppWeb.setSelected(false); rbAppDesktop.setSelected(true); rbAppAndroid.setSelected(false);
                }else if (rs.getString("PER_TIPOMENU").equals("3")){
                    rbAppWeb.setSelected(false); rbAppDesktop.setSelected(false); rbAppAndroid.setSelected(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorGrupoPermisos.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void CreaPermisologias() throws ClassNotFoundException, SQLException{
        String sql_menus = "SELECT * FROM DNMENU WHERE MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MEN_TIPO="+tipomenu+" ORDER BY MEN_ID ASC;";
        String NumMenu="";

        SQLQuerys permisologias = new SQLQuerys();
            
        rs_opc_menus = conet.consultar(sql_menus);
            
        rs_opc_menus.beforeFirst();
        while (rs_opc_menus.next()){
            NumMenu=String.format("%06d", rs_opc_menus.getInt("MEN_ID"));

            permisologias.SqlInsert("INSERT INTO DNPERMISOLOGIA (MIS_EMPRESA,MIS_ID,MIS_PERMISO,MIS_MENU,MIS_TIPOMENU,MIS_TIPOPER,"+
                                                                "MIS_ACTIVO) "+
                                                        "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+NumMenu+"_"+tfCodigo.getText().toString()+"_"+tipomenu+"_"+"0',"+
                                                                 tfCodigo.getText().toString()+","+
                                                                 rs_opc_menus.getInt("MEN_ID")+","+tipomenu+",0,'0');");
        }
    }
    
    public void deshabilitar(){
        tfCodigo.setEnabled(false); tfNombre.setEnabled(false);
        chkActivo.setEnabled(false);
        rbAppWeb.setEnabled(false); rbAppDesktop.setEnabled(false); rbAppAndroid.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true);
            chkActivo.setEnabled(true);
            rbAppWeb.setEnabled(true); rbAppDesktop.setEnabled(true); rbAppAndroid.setEnabled(true);
            
            limpiar();
            
            tfNombre.requestFocus();
        }else if (accion=="Modificar"){
            tfCodigo.setEnabled(false); tfNombre.setEnabled(true);
            chkActivo.setEnabled(true);
            rbAppWeb.setEnabled(true); rbAppDesktop.setEnabled(true); rbAppAndroid.setEnabled(true);
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){
        tfNombre.setText(""); 
        chkActivo.setSelected(true);
    }
}