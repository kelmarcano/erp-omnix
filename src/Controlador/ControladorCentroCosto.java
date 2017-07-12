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
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControladorCentroCosto {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo, descrip, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre;
    private JDateChooser fecha;
    
    public ControladorCentroCosto(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JDateChooser fecha){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;;
        this.fecha = fecha;
    }
    
    public void save(String codigo, String descrip, JTable tabla, Boolean lAgregar, String clase){
        String fechavigen = "";
        
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
      
        if (fecha.getDate()==null){
            JOptionPane.showMessageDialog(null,(String) Msg.get(6));
            fecha.requestFocus();
            return;
        }
        
        //--------- Captura de la Fecha del jCalendar ----------
        Date date = fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fch = sdf.format(date);
        fechavigen = fch;
        //------------------------------------------------------
       
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sql = "INSERT INTO DNCENTRO (CEN_EMPRESA,CEN_USER,CEN_MACPC,CEN_CODIGO,CEN_DESCRI,CEN_FCHVIG) "+
                                      "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+
                                               "','"+tfCodigo.getText().toString()+
                                               "','"+tfNombre.getText().toString()+"','"+fechavigen+"');";

            insertar.SqlInsert(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Centro de Costo ''"+tfNombre.getText().toString()+
                                                 "'' con el Valor :  Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNCENTRO SET CEN_CODIGO='"+tfCodigo.getText().toString()+"',CEN_DESCRI='"+tfNombre.getText().toString()+"',"+
                                             "CEN_FCHVIG='"+fechavigen+"'"+
                         "WHERE CEN_CODIGO='"+codigo+"'";
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                                 "Modificación de Registro", "Se Modifico el Centro de Costo ''"+
                                                  tfNombre.getText().toString()+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para visualizar los ajustes ----------
        String SQL = "SELECT CEN_CODIGO,CEN_DESCRI FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) "Código",(String) "Descripción"};
            
        cargatabla.cargatablas(tabla,SQL,columnas);
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
            eliminar.SqlDelete("DELETE FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CEN_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CEN_CODIGO='"+codigo+"'",true,"");
            
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
        String SQL = "SELECT CEN_CODIGO,CEN_DESCRI FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        //String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
       String[] columnas = {(String) "Código",(String) "Descripción"};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);  
        tabla.getSelectionModel().setSelectionInterval(tabla.getRowCount()-1, tabla.getRowCount()-1);
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("CEN_CODIGO","DNCENTRO",6,"WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT CEN_CODIGO,CEN_DESCRI FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT CEN_CODIGO FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY CEN_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("CEN_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCentroCosto.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CEN_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorCentroCosto.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNCENTRO WHERE CEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CEN_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCentroCosto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("CEN_CODIGO").trim()); tfNombre.setText(rs.getString("CEN_DESCRI").trim());
                                
                             
                //--------- Covierte el String de la Fecha en Date ----------
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fch = null;

                try {
                    fch = sdf.parse(rs.getString("CEN_FCHVIG"));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                fecha.setDate(fch);
            //-----------------------------------------------------------
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCentroCosto.class.getName()).log(Level.SEVERE, null, ex);
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
        fecha.setEnabled(false); 
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true);
            fecha.setEnabled(true); 
                        
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            Date fch = null;
            String Fch = sdf.format(date);
            try {
                fch = sdf.parse(Fch);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorCentroCosto.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fecha.setDate(fch);
            //-----------------------------------------------------------
            
            limpiar();
            
            tfNombre.requestFocus();
        }else if (accion=="Modificar"){
            tfNombre.setEnabled(true);
            fecha.setEnabled(true); 
                       
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){
        tfNombre.setText(""); 
    }
}