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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControladorUnidadMedida {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre, tfCantUnd, tfPeso, tfVolumen;
    private JCheckBox chkActivo, chMultxu;
    private JComboBox cbSigno;
    
    public ControladorUnidadMedida(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JTextField cantUnd, JTextField peso, 
                               JTextField volumen, JCheckBox activo, JCheckBox multxu, JComboBox signo){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;
        this.tfCantUnd = cantUnd;
        this.tfPeso = peso;
        this.tfVolumen = volumen;
        this.chkActivo = activo;
        this.chMultxu = multxu;
        this.cbSigno = signo;
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase){
        String mulcxu = ""; String signo = "";
        
        xmlFile(clase);
        
        if (chMultxu.isSelected()==true){
            mulcxu = "1";
        } else{
            mulcxu = "0";
        }
        
        if (chkActivo.isSelected()==true){
            activo = "1";
        } else{
            activo = "0";
        }

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

        signo = cbSigno.getSelectedItem().toString();
        signo = signo.substring(0,1).toString();
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sqlInsert = "INSERT INTO DNUNDMEDIDA (MED_EMPRESA,MED_USER,MED_MACPC,MED_CODIGO,MED_DESCRI,MED_CANUND,MED_PESO,MED_VOLUME,"+
                                                  "MED_SIGNO,MED_MULCXU,MED_ACTIVO) "+
                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+
                                                  ""+codigo.toUpperCase()+"','"+descrip+"',"+
                                                  ""+tfCantUnd.getText().toString().replace(",", ".")+","+
                                                  ""+tfPeso.getText().toString().replace(",", ".")+","+
                                                  ""+tfVolumen.getText().toString().replace(",", ".")+",'"+signo+"','"+mulcxu+"','"+activo+"')";

            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Unidad de Medida ''"+tfCodigo.getText().toString()+
                                                 " Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNUNDMEDIDA SET MED_CODIGO='"+codigo.toUpperCase()+"',MED_DESCRI='"+descrip+"',"+
                                                "MED_CANUND="+tfCantUnd.getText().toString().replace(",", ".")+","+
                                                "MED_PESO="+tfPeso.getText().toString().replace(",", ".")+","+
                                                "MED_VOLUME="+tfVolumen.getText().toString().replace(",", ".")+",MED_SIGNO='"+signo+"',"+
                                                "MED_MULCXU='"+mulcxu+"',MED_ACTIVO='"+activo+"' "+
                         "WHERE MED_CODIGO='"+codigo+"'";
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la Unidad de Medida ''"+tfCodigo.getText().toString()+
                                                 " Correctamente");
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
        }
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                                              "MED_CODIGO='"+codigo+"'",true,"");
            
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
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT MED_CODIGO,MED_DESCRI,MED_CANUND FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("MED_CODIGO","DNUNDMEDIDA",8,"WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="00000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT * FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT MED_CODIGO FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MED_ID DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("MED_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorUnidadMedida.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorUnidadMedida.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MED_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorUnidadMedida.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("MED_CODIGO").trim()); tfNombre.setText(rs.getString("MED_DESCRI").trim());
                tfCantUnd.setText(rs.getString("MED_CANUND").trim()); tfPeso.setText(rs.getString("MED_PESO"));
                tfVolumen.setText(rs.getString("MED_VOLUME"));
                
                String signo=rs.getString("MED_SIGNO").trim();
                if (signo.equals("*")){
                    cbSigno.setSelectedItem("* Multiplica");
                }else if (signo.equals("/")){
                    cbSigno.setSelectedItem("/ Divide");
                }
                
                if (rs.getBoolean("MED_MULCXU")==true){
                    chMultxu.setSelected(true);
                }else{
                    chMultxu.setSelected(false);
                }
                
                if (rs.getBoolean("MED_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorUnidadMedida.class.getName()).log(Level.SEVERE, null, ex);
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
        tfCodigo.setEnabled(false); tfNombre.setEnabled(false); tfCantUnd.setEnabled(false);
        tfPeso.setEnabled(false); tfVolumen.setEnabled(false); chkActivo.setEnabled(false); 
        chMultxu.setEnabled(false); cbSigno.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true); tfCantUnd.setEnabled(true);
            tfPeso.setEnabled(true); tfVolumen.setEnabled(true); chkActivo.setEnabled(true);
            chMultxu.setEnabled(true); cbSigno.setEnabled(true);
            
            limpiar();
            
            tfCodigo.requestFocus();
        }else if (accion=="Modificar"){
            tfNombre.setEnabled(true); tfCantUnd.setEnabled(true); tfPeso.setEnabled(true); 
            tfVolumen.setEnabled(true); chkActivo.setEnabled(true); chMultxu.setEnabled(true);
            cbSigno.setEnabled(true);
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){
        tfCodigo.setText(""); tfNombre.setText(""); tfCantUnd.setText("1.0000");
        tfPeso.setText("0.000"); tfVolumen.setText("0.000"); cbSigno.setSelectedIndex(0);
        chkActivo.setSelected(true);
    }
}