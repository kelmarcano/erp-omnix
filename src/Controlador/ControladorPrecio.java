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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControladorPrecio {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfCodPro, tfTipoPrecio, tfCodUnd;
    private JFormattedTextField ftfPrecio;
    private JCheckBox chkActivo;
    private JDateChooser fecha;
    private JButton btnProducto, btnTipProduct, btnUniMed; 
    
    public ControladorPrecio(){
        
    }

    public void setComponentes(JTextField codigo, JTextField codProd, JTextField tipoPrecio, JTextField codUnd, 
                               JFormattedTextField precio, JDateChooser fecha, JCheckBox activo, JButton btnProducto, 
                               JButton btnTipProduct, JButton btnUniMed){
        this.tfCodigo = codigo;
        this.tfCodPro = codProd;
        this.chkActivo = activo;
        this.tfTipoPrecio = tipoPrecio;
        this.ftfPrecio = precio;
        this.fecha = fecha;
        this.tfCodUnd = codUnd;
        this.btnProducto = btnProducto;
        this.btnTipProduct = btnTipProduct;
        this.btnUniMed = btnUniMed;
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        String valor = ""; String fechavenc = ""; String codunidad = "";
        
        xmlFile(clase);
        
        
        if (chkActivo.isSelected()==true){
            activo = "1";
        }else{
            activo = "0";
        }
        
//        if (this.Bandera==true){
//            JOptionPane.showMessageDialog(null,(String) Msg.get(9));
//            return;
//        }
        if ("".equals(tfCodigo.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(10));
            tfCodigo.requestFocus();
            return;
        }
        if ("".equals(tfCodPro.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(11));
            btnProducto.requestFocus();
            return;
        }
        if ("".equals(tfTipoPrecio.getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(12));
            btnTipProduct.requestFocus();
            return;
        }
        
        //------------- Sustitucion de Caraterers --------------
        String precio = ftfPrecio.getText();
        precio = precio.replace(".", "");
        precio = precio.replace(",", ".");
        //------------------------------------------------------
        if (Double.valueOf(precio)==0){
            JOptionPane.showMessageDialog(null,(String) Msg.get(13));
            ftfPrecio.requestFocus();
            return;
        }
        if (fecha.getDate()==null){
            JOptionPane.showMessageDialog(null,(String) Msg.get(14));
            fecha.requestFocus();
            return;
        }
        
        codunidad = tfCodUnd.getText();
        //--------- Captura de la Fecha del jCalendar ----------
        Date date = fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(date);
        fechavenc = fecha;
        //------------------------------------------------------
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sqlInsert = "INSERT INTO DNPRECIO (PRE_EMPRESA,PRE_USER,PRE_MACPC,PRE_CODIGO,PRE_CODPRO,PRE_CODLIS,"+
                                                     "PRE_MONTO,PRE_CODUNA,PRE_FCHVEN,PRE_ACTIVO) "+
                                             "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"',"+
                                                     "'"+VarGlobales.getMacPc()+"','"+codigo+"','"+tfCodPro.getText().toString()+"',"+
                                                     "'"+tfTipoPrecio.getText().toString()+"',"+precio+",'"+codunidad+"','"+fechavenc+"','"+activo+"');";
            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Precio ''"+tfTipoPrecio.getText().toString()+
                                                 " - "+precio+"'' para el producto: "+tfCodPro.getText().toString()+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNPRECIO SET PRE_CODIGO='"+codigo+"', PRE_CODPRO='"+tfCodPro.getText().toString()+"',"+
                                                    "PRE_CODLIS='"+tfTipoPrecio.getText().toString()+"',PRE_MONTO='"+precio+"',PRE_CODUNA='"+codunidad+"',"+""+
                                                    "PRE_FCHVEN='"+fechavenc+"',PRE_ACTIVO='"+activo+"' "+
                         "WHERE PRE_CODIGO='"+codigo+"'";
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Precio ''"+tfTipoPrecio.getText().toString()+
                                                 "'' del Producto: "+tfCodPro.getText().toString()+" Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT PRE_CODIGO,PRE_CODPRO,PRE_CODLIS,PRE_MONTO FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
        }
        
        String codpro = (String) tabla.getValueAt(tabla.getSelectedRow(),1).toString().trim();
        String LisPre = (String) tabla.getValueAt(tabla.getSelectedRow(),2).toString().trim();
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(6)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRE_CODIGO='"+codigo+"' AND "+
                               "PRE_CODPRO='"+codpro+"' AND PRE_CODLIS='"+LisPre+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                                              "PRE_CODIGO='"+codigo+"' AND PRE_CODPRO='"+codpro+"' AND "+
                                              "PRE_CODLIS='"+LisPre+"'",true,"");
            
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
        String SQL = "SELECT PRE_CODIGO,PRE_CODPRO,PRE_CODLIS,PRE_MONTO FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("PRE_CODIGO","DNPRECIO",5,"WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="00001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT * FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT PRE_CODIGO FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRE_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("PRE_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPrecio.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRE_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorPrecio.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPRECIO WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRE_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPrecio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("PRE_CODIGO").trim()); tfCodPro.setText(rs.getString("PRE_CODPRO").trim());
                tfTipoPrecio.setText(rs.getString("PRE_CODLIS").trim());
                ftfPrecio.setText(rs.getString("PRE_MONTO").replace(".", ","));
                tfCodUnd.setText(rs.getString("PRE_CODUNA").trim());
                //--------- Covierte el String de la Fecha en Date ----------
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fch = null;

                try {
                    fch = sdf.parse(rs.getString("PRE_FCHVEN"));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                fecha.setDate(fch);
                //-----------------------------------------------------------
                if (rs.getBoolean("PRE_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPrecio.class.getName()).log(Level.SEVERE, null, ex);
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
        tfCodigo.setEnabled(false); tfCodPro.setEnabled(false);
        tfTipoPrecio.setEnabled(false); ftfPrecio.setEnabled(false); 
        tfCodUnd.setEnabled(false); fecha.setEnabled(false); 
        chkActivo.setEnabled(false);
        
        btnProducto.setEnabled(false); btnTipProduct.setEnabled(false);
        btnUniMed.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodPro.setEnabled(true); tfCodPro.setEnabled(true); tfCodPro.setEditable(false);
            tfTipoPrecio.setEnabled(true); tfTipoPrecio.setEditable(false); ftfPrecio.setEnabled(true); 
            tfCodUnd.setEnabled(true); tfCodUnd.setEditable(false); fecha.setEnabled(true); 
            chkActivo.setEnabled(true);
            
            tfCodPro.requestFocus();
            
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            Date fch = null;
            String Fch = sdf.format(date);
            try {
                fch = sdf.parse(Fch);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorPrecio.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fecha.setDate(fch);
            //-----------------------------------------------------------
            
            limpiar();
            
            btnProducto.setEnabled(true); btnTipProduct.setEnabled(true); btnUniMed.setEnabled(true);
        }else if (accion=="Modificar"){
            tfCodPro.setEnabled(true); tfCodPro.setEditable(false); tfTipoPrecio.setEnabled(true); 
            ftfPrecio.setEnabled(true); tfCodUnd.setEnabled(true); tfCodUnd.setEditable(false);
            fecha.setEnabled(true); chkActivo.setEnabled(true); tfTipoPrecio.setEditable(false);
            
            tfCodPro.requestFocus();
            
            btnProducto.setEnabled(true); btnTipProduct.setEnabled(true); btnUniMed.setEnabled(true);
        }
    }
    
    private void limpiar(){
        tfCodPro.setText(""); tfTipoPrecio.setText(""); ftfPrecio.setText("0.00");
        tfCodUnd.setText("");
        chkActivo.setSelected(true);
    }
}