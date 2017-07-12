package Controlador;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.CargaComboBoxs;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ControladorProductos {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus, rs_combos, rs_menu_count;
    private static int Reg_count, nMenu;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre, tfCodPro, tfRutaImg;
    private JTextArea taDescrip;
    private JCheckBox chkActivo;
    private JComboBox cbMarca, cbTipIva, cbTipPrec, cbUnd;
    private JButton btBuscaProd;
    
    public ControladorProductos(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JTextField codPro, JTextField rutaImg,
                               JTextArea descrip, JCheckBox activo, JComboBox cbMarca, JComboBox cbTipIva,
                               JComboBox cbTipPrec, JComboBox cbUnd, JButton buscaProd){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;
        this.tfCodPro = codPro;
        this.tfRutaImg = rutaImg;
        this.taDescrip = descrip;
        this.chkActivo = activo;
        this.cbMarca = cbMarca;
        this.cbTipIva = cbTipIva;
        this.cbTipPrec = cbTipPrec;
        this.cbUnd = cbUnd;
        this.btBuscaProd = buscaProd;
    }
    
    public void save(String codigo, String nombre, String activo, JTable tabla, Boolean lAgregar, String clase){
        String codmae=""; String tipprec=""; String und=""; String tipimpuesto="";
        
        xmlFile(clase);
        
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

        codmae = tfCodPro.getText();
        if (codmae.isEmpty()){
            codmae = null;
        }
        
        tipimpuesto = (String) cbTipIva.getSelectedItem();
        if (tipimpuesto.isEmpty()){
            tipimpuesto = null;
        }else{
            tipimpuesto = tipimpuesto.substring(0, 2);
        }
        
        tipprec = (String) cbTipPrec.getSelectedItem();
        if (tipprec.isEmpty()){
            tipprec = null;
        }else{
            tipprec = tipprec.substring(0, 1);
        }
        
        und = (String) cbUnd.getSelectedItem();
        if (und.isEmpty()){
            und = null;
        }else{
            und = und.substring(0, 3);
        }
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sqlInsert = "INSERT INTO DNPRODUCTO (PRO_EMPRESA,PRO_USER,PRO_MACPC,PRO_CODIGO,PRO_CODMAE,PRO_NOMBRE,PRO_DESCRI,"+
                                                       "PRO_TIPPREC,PRO_CODUND,PRO_UTILIZ,PRO_TIPIVA,PRO_RUTAIMG,PRO_ACTIVO) "+
                                               "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+codigo.toUpperCase()+"',"+
                                                       "'"+codmae+"','"+nombre+"','"+taDescrip.getText().toString()+"','"+tipprec+"',"+
                                                       "'"+und+"','','"+tipimpuesto+"',"+"'"+tfRutaImg.getText().toString()+"','"+activo+"');";
System.out.println(sqlInsert);
            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Producto ''"+codigo.toUpperCase()+"'' Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNPRODUCTO SET PRO_CODIGO='"+codigo.toUpperCase()+"', PRO_NOMBRE='"+nombre+"',"+
                                                      "PRO_DESCRI='"+taDescrip.getText().toString()+"', PRO_CODMAE='"+codmae+"',"+
                                                      "PRO_TIPPREC='"+tipprec+"',PRO_CODUND='"+und+"',"+
                                                      "PRO_TIPIVA='"+tipimpuesto+"',PRO_RUTAIMG='"+tfRutaImg.getText().toString()+"',"+
                                                      "PRO_ACTIVO='"+activo+"' "+
                         "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+codigo+"'";
System.out.println(sql);
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Producto ''"+codigo.toUpperCase()+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla, clase);
        //-------------------------------------------------------------------
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        String nombre = null;
        int eliminado;
        xmlFile(clase);
        
        String descrip = tfNombre.getText().toString().trim();
        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(0)+codigo+" - "+descrip+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                                              "PRO_CODIGO='"+codigo+"'",true,"");
            
            ejecutaHilo("", false);
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
        
        String SQL_VEN = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_ACTIVO FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};
        
        cargatabla.cargatablas(tabla,SQL_VEN,columnas);
        tabla.getSelectionModel().setSelectionInterval(tabla.getRowCount()-1, tabla.getRowCount()-1);
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("PRO_CODIGO","DNPRODUCTO",10,"WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="0000000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;        
        
        String SQL = "SELECT * FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
        comboTipIva();
        comboUnd();
        comboTipPrec();
        
        try {
            String SQLCodDoc="";
            
            SQLCodDoc = "SELECT PRO_CODIGO FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_ID DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodDoc);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("PRO_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPRODUCTO WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        String sql_cmb="";
        
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("PRO_CODIGO").trim());  tfNombre.setText(rs.getString("PRO_NOMBRE").trim());
                taDescrip.setText(rs.getString("PRO_DESCRI").trim());  tfCodPro.setText(rs.getString("PRO_CODMAE").trim());
                
                if (rs.getBoolean("PRO_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                
                sql_cmb = "SELECT CONCAT(TIVA_CODIGO,' - ', TIVA_DESCRI) AS DATO1 FROM dntipiva WHERE TIVA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TIVA_CODIGO='"+rs.getString("PRO_TIPIVA").trim()+"'";
                try {
                    rs_combos = conet.consultar(sql_cmb);
                    cbTipIva.setSelectedItem(rs_combos.getString("DATO1").trim());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                sql_cmb = "SELECT CONCAT(LIS_CODIGO,' - ', LIS_DESCRI) AS DATO1 FROM dnlistpre WHERE LIS_CODIGO='"+rs.getString("PRO_TIPPREC").trim()+"'";
                try {
                    rs_combos = conet.consultar(sql_cmb);
                    cbTipPrec.setSelectedItem(rs_combos.getString("DATO1").trim());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                sql_cmb = "SELECT CONCAT(MED_CODIGO,' - ', MED_DESCRI) AS DATO1 FROM dnundmedida WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+rs.getString("PRO_CODUND").trim()+"'";
                try {
                    rs_combos = conet.consultar(sql_cmb);
                    cbUnd.setSelectedItem(rs_combos.getString("DATO1").trim().trim());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
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
        tfCodigo.setEnabled(false); tfNombre.setEnabled(false); tfCodPro.setEnabled(false);
        tfRutaImg.setEnabled(false); taDescrip.setEnabled(false);
        cbMarca.setEnabled(false); cbTipIva.setEnabled(false); cbTipPrec.setEnabled(false);
        cbUnd.setEnabled(false);
        chkActivo.setEnabled(false); btBuscaProd.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfNombre.setEnabled(true); tfCodPro.setEnabled(true);
            tfRutaImg.setEnabled(true); taDescrip.setEnabled(true);
            cbMarca.setEnabled(true); cbTipIva.setEnabled(true); cbTipPrec.setEnabled(true);
            cbUnd.setEnabled(true);
            chkActivo.setEnabled(true); btBuscaProd.setEnabled(true);
            
            limpiar();
            
            tfNombre.requestFocus();
        }else if (accion=="Modificar"){
            tfNombre.setEnabled(true); tfCodPro.setEnabled(true); tfRutaImg.setEnabled(true);
            taDescrip.setEnabled(true);
            cbMarca.setEnabled(true); cbTipIva.setEnabled(true); cbTipPrec.setEnabled(true);
            cbUnd.setEnabled(true);
            chkActivo.setEnabled(true); btBuscaProd.setEnabled(true);
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){        
        tfCodigo.setText(""); tfNombre.setText(""); tfCodPro.setText(""); 
        tfRutaImg.setText(""); taDescrip.setText("");
        
        cbMarca.setSelectedItem(""); cbTipIva.setSelectedItem("");
        cbTipPrec.setSelectedItem(""); cbUnd.setSelectedItem("");
        
        chkActivo.setSelected(true);
    }
    
    private void comboTipIva(){
        String sql = "SELECT CONCAT(TIVA_CODIGO,' - ', TIVA_DESCRI) AS DATO1 FROM DNTIPIVA WHERE TIVA_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        cbTipIva.setModel(mdl); 
    }
    
    private void comboUnd() {
        String sql = "SELECT CONCAT(MED_CODIGO,' - ', MED_DESCRI) AS DATO1 FROM dnundmedida WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";

        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        cbUnd.setModel(mdl);
    } 
    
    private void comboTipPrec() {
        String sql = "SELECT CONCAT(LIS_CODIGO,' - ', LIS_DESCRI) AS DATO1 FROM dnlistpre";

        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        cbTipPrec.setModel(mdl);
    }
}