/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kel
 */
public class ControladorComprobanteDif {
     VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus, rs_combos, rs_menu_count;
    private static int Reg_count, nMenu, tab;
    private String codigo = "", nombre = "", tipmae = "", valor_cli = "", valor_pro = "";
    private String limite = "0.00", dcto = "0", condic = "", dias = "0", observ = "0", rif = "";
    private String nit = "", contrib = "", activi = "", reside = "", zonacom = "", codmon = "";
    private String mtoven = "0.00", diaven = "0", contriesp = "", coddir = "", otromon = "", activo = "";
    private String fecha = ""; String retiva = "0"; String foto = "", ruta, Ultimo_Reg="";
    private Boolean lAgregar;

    private JTextField tfnumcodigo, tfnumdoc, tfdocmonto, tfdescripasiento, tfnumcta, tfctadescri, tfctamonto, tfctadebe, tfctahaber;
    private JFormattedTextField ftfLimitCred, ftfLimitCredVenc, ftfDiasPlazo, ftfRif, ftfNit, ftfDcto, ftfDiasVen, ftfretIva;   
    private JDateChooser dcFecha;
    private JButton btncomp_agregar, btncomp_eliminar, btncomp_buscar;
        
    public ControladorComprobanteDif(){
    
    }
    
    public void setComponentes(JTextField numcodigo, JTextField numdoc, JTextField docmonto, JTextField descripasiento, JTextField numcta, 
                                JTextField ctadescri, JTextField ctamonto, JTextField ctadebe, JTextField ctahaber, JDateChooser dcFecha, 
                                JButton btncomp_agregar, JButton btncomp_eliminar, JButton btncomp_buscar){ 
        
        this.tfnumcodigo = numcodigo;
        this.tfnumdoc = numdoc;
        this.tfdocmonto = docmonto;
        this.tfdescripasiento = descripasiento;
        this.tfnumcta = numcta;
        this.tfctadescri = ctadescri;
        this.tfctamonto = ctamonto;
        this.tfctadebe =ctadebe;
        this.tfctahaber = ctahaber; 
        this.btncomp_agregar = btncomp_agregar;
        this.btncomp_buscar= btncomp_buscar;
        this.btncomp_eliminar = btncomp_eliminar;
        this.dcFecha= dcFecha;

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
    
    
     public void save(String numcodigo, String descripasiento, JTable tabla, Boolean lAgregar, String clase){
        String valor = ""; String fechavigen = "";
        xmlFile(clase);
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        String [] filas={numcodigo,descripasiento};
        model.addRow(filas);

        /*
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
        */
        if (dcFecha.getDate()==null){
            JOptionPane.showMessageDialog(null,(String) Msg.get(8));
            dcFecha.requestFocus();
            return;
        }
        
        //--------- Captura de la Fecha del jCalendar ----------
        Date date = dcFecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fch = sdf.format(date);
        fechavigen = fch;
        //------------------------------------------------------
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sql = "INSERT INTO DNCOMPROBANTE (COMP_EMPRESA, COMP_ID, COMP_DATTIM,COMP_NUMERO, COMP_DOCNUM, COMP_FECHA, COMP_CTACODIGO,COMP_DOCMONTO, COMP_DESCRIPASIENTO, COMP_DESCRI, COMP_CTAMONTO, COMP_DEBE,COMP_HABER, COMP_STATUS) "+
                     " VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+tfnumcodigo.getText().toString()+
                            "','"+tfnumdoc.getText().toString()+"','"+fechavigen+"','"+tfnumcta.getText().toString()+"','"+tfdocmonto.getText().toString()+"','"+tfdescripasiento.getText().toString()+
                            "'','"+tfctadescri.getText().toString()+"','"+tfctamonto.getText().toString()+"','"+tfctadebe.getText().toString()+"','"+tfctahaber.getText().toString()+"');";

            insertar.SqlInsert(sql);
            
            System.out.println(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el comprobante Número ''"+tfnumcodigo.getText().toString()+
                                                 "'' con el Valor : "+valor+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
           String sql = "UPDATE DNCOMPROBANTE SET COMP_NUMERO='"+tfnumcodigo.getText().toString()+"',COMP_DOCNUM='"+tfnumdoc.getText().toString()+"',"+
                                                        "COMP_FECHA'"+fechavigen+"',COMP_CTACODIGO='"+tfnumcta.getText().toString()+"',COMP_DOCMONTO='"+tfdocmonto.getText().toString()+"',"+
                                                        "COMP_DESCRIPASIENTO='"+tfdescripasiento.getText().toString()+"',"+
                                                        "COMP_DESCRI='"+tfctadescri.getText().toString()+"',"+
                                                        "COMP_CTAMONTO='"+tfctamonto.getText().toString()+"',COMP_DEBE='"+tfctadebe.getText().toString()+"',"+
                                                        "COMP_HABER='"+tfctahaber.getText().toString()+"',COMP_STATUS'"+activo+"'"+
                         "WHERE COMP_NUMERO='"+numcodigo+"'";
            modificar.SqlUpdate(sql);
            
            System.out.println(sql);
              
                      
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                                 "Modificación de Registro", "Se Modifico el Comprobante ''"+
                                                  tfnumcodigo.getText().toString()+"'' Correctamente");
          
        }
       
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT comp_ctacodigo,comp_descripasiento,comp_debe comp_ctamonto FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        //String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3)};
        String[] columnas = {"Cod.Cta","Descripción","Debe/Haber","Monto"};
            
        cargatabla.cargatablas(tabla,SQL,columnas);
        //-------------------------------------------------------------------
    }
     
     public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("COMP_NUMERO","DNCOMPROBANTE",10,"WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="0000000001";
        }
        
        return consecutivo;
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
        String SQL = "SELECT comp_ctacodigo,comp_descripasiento,comp_debe,comp_ctamonto FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        System.out.println("IMPRESION CONSULTA CARGA TABLA "+ SQL);
        //String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3)};
        String[] columnas = {"Cod.Cta","Descripción","Debe/Haber","Monto"};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
     
    public void deshabilitar(){
        tfnumcodigo.setEnabled(false);
        tfnumdoc.setEnabled(false);
        tfdocmonto.setEnabled(false);
        tfdescripasiento.setEnabled(false);
        tfnumcta.setEnabled(false);
        tfctadescri.setEnabled(false);
        tfctamonto.setEnabled(false);
        tfctadebe.setEnabled(false);
        tfctahaber.setEnabled(false);
        btncomp_agregar.setEnabled(false);
        btncomp_buscar.setEnabled(false);
        btncomp_eliminar.setEnabled(false);
    }
    
    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfnumcodigo.setEnabled(true);
            tfnumdoc.setEnabled(true);
            tfdocmonto.setEnabled(true);
            tfdescripasiento.setEnabled(true);
            tfnumcta.setEnabled(true);
            tfctadescri.setEnabled(true);
            tfctamonto.setEnabled(true);
            tfctadebe.setEnabled(true);
            tfctahaber.setEnabled(true);
            btncomp_agregar.setEnabled(true);
            btncomp_buscar.setEnabled(true);
            btncomp_eliminar.setEnabled(true);
            dcFecha.setEnabled(true); 
                        
            //--------- Covierte el String de la Fecha en Date ----------
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            Date fch = null;
            String Fch = sdf.format(date);
            try {
                fch = sdf.parse(Fch);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorComprobanteDif.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            dcFecha.setDate(fch);
            //-----------------------------------------------------------
            
            limpiar();
            
            tfnumcodigo.requestFocus();
        }else if (accion=="Modificar"){
            tfnumcodigo.setEnabled(true);
            tfnumdoc.setEnabled(true);
            tfdocmonto.setEnabled(true);
            tfdescripasiento.setEnabled(true);
            tfnumcta.setEnabled(true);
            tfctadescri.setEnabled(true);
            tfctamonto.setEnabled(true);
            tfctadebe.setEnabled(true);
            tfctahaber.setEnabled(true);
            btncomp_agregar.setEnabled(true);
            btncomp_buscar.setEnabled(true);
            btncomp_eliminar.setEnabled(true);
            dcFecha.setEnabled(true);         
            tfnumcodigo.requestFocus();
        }
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND COMP_NUMERO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND COMP_NUMERO='"+codigo+"'",true,"");
            
            cargaTabla(tabla, clase);
        }
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar){
        try {
            String SQLCodComprobante="";
            
            SQLCodComprobante = "SELECT COMP_NUMERO FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY COMP_NUMERO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodComprobante);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("COMP_NUMERO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorComprobanteDif.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND COMP_NUMERO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorComprobanteDif.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND COMP_NUMERO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorComprobanteDif.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
     public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT COMP_NUMERO,COMP_CTADESCRI FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNCOMPROBANTE WHERE COMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        Reg_count = conet.Count_Reg(SQL_Reg);
        
        if (Reg_count==1){
            rs.next();
        }else{
            rs.last();
        }
        
        ResultSet resultSet = ejecutaHilo(codigo, lAgregar);
        return resultSet;
    }
     
     public void mostrar(){
        if (Reg_count > 0){
            try {
                tfnumcodigo.setText(rs.getString("COMP_NUMERO").trim()); tfdescripasiento.setText(rs.getString("COMP_DESCRIPASIENTO").trim());
                
                
            } catch (SQLException ex) {
                Logger.getLogger(ControladorComprobanteDif.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void limpiar(){
        
    }
     
     
    
}
