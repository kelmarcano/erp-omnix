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

public class ControladorMoneda {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre, tfNomenclatura;
    private JFormattedTextField ftfValor;
    private JCheckBox chkActivo;
    private JDateChooser fecha;
    
    public ControladorMoneda(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JTextField nomenclatura, JFormattedTextField valor, JDateChooser fecha, JCheckBox activo){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;;
        this.chkActivo = activo;
        this.tfNomenclatura = nomenclatura;
        this.ftfValor = valor;
        this.fecha = fecha;
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        String valor = ""; String fechavigen = "";
        
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
        if ("".equals(tfNomenclatura.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(6));
            tfNomenclatura.requestFocus();
            return;
        }
        
        //------------- Sustitucion de Caraterers --------------
        valor = ftfValor.getText().toString();
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        //------------------------------------------------------
        if (Double.valueOf(valor)==0){
            JOptionPane.showMessageDialog(null,(String) Msg.get(7));
            ftfValor.requestFocus();
            return;
        }
        
        if (fecha.getDate()==null){
            JOptionPane.showMessageDialog(null,(String) Msg.get(8));
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
            String sql = "INSERT INTO DNMONEDA (MON_EMPRESA,MON_USER,MON_MACPC,MON_CODIGO,MON_NOMBRE,MON_NOMENC,MON_VALOR,"+
                                               "MON_FCHVIG,MON_ACTIVO) "+
                                      "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+
                                               "','"+tfCodigo.getText().toString()+
                                               "','"+tfNombre.getText().toString()+"','"+tfNomenclatura.getText().toString()+
                                               "',"+valor+","+
                                               "'"+fechavigen+"','"+activo+"');";

            insertar.SqlInsert(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Moneda ''"+tfNombre.getText().toString()+
                                                 "'' con el Valor : "+valor+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNMONEDA SET MON_CODIGO='"+tfCodigo.getText().toString()+"',MON_NOMBRE='"+tfNombre.getText().toString()+"',"+
                                             "MON_NOMENC='"+tfNomenclatura.getText().toString()+"',MON_VALOR="+valor+","+
                                             "MON_FCHVIG='"+fechavigen+"',MON_ACTIVO='"+activo+"' "+
                         "WHERE MON_CODIGO='"+codigo+"'";
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                                 "Modificación de Registro", "Se Modifico la Moneda ''"+
                                                  tfNombre.getText().toString()+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT MON_CODIGO,MON_NOMBRE,MON_NOMENC,MON_VALOR FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3)};
            
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
            eliminar.SqlDelete("DELETE FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'",true,"");
            
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
        String SQL = "SELECT MON_CODIGO,MON_NOMBRE,MON_NOMENC,MON_VALOR FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("MON_CODIGO","DNMONEDA",6,"WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT MON_CODIGO,MON_NOMBRE,MON_NOMENC,MON_VALOR FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT MON_CODIGO FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MON_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("MON_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("MON_CODIGO").trim()); tfNombre.setText(rs.getString("MON_NOMBRE").trim());
                tfNomenclatura.setText(rs.getString("MON_NOMENC").trim());
                ftfValor.setText(rs.getString("MON_VALOR").replace(".", ","));
                
                if (rs.getBoolean("MON_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                
                //--------- Covierte el String de la Fecha en Date ----------
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fch = null;

                try {
                    fch = sdf.parse(rs.getString("MON_FCHVIG"));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                fecha.setDate(fch);
            //-----------------------------------------------------------
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
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
        tfNomenclatura.setEnabled(false); ftfValor.setEnabled(false); 
        fecha.setEnabled(false); 
        chkActivo.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true);
            tfNomenclatura.setEnabled(true); ftfValor.setEnabled(true); 
            fecha.setEnabled(true); 
            chkActivo.setEnabled(true);
            
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            Date fch = null;
            String Fch = sdf.format(date);
            try {
                fch = sdf.parse(Fch);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fecha.setDate(fch);
            //-----------------------------------------------------------
            
            limpiar();
            
            tfNombre.requestFocus();
        }else if (accion=="Modificar"){
            tfNombre.setEnabled(true); tfNomenclatura.setEnabled(true); ftfValor.setEnabled(true); 
            fecha.setEnabled(true); 
            chkActivo.setEnabled(true);
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){
        tfNombre.setText(""); tfNomenclatura.setText(""); ftfValor.setText("0.00");
        chkActivo.setSelected(true);
    }
}