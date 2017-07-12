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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControladorTipoDocumentos {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus, rs_menu, rs_menu_count;
    private static int Reg_count, tab, nMenu;
    private String codigo, descrip, activo, tabla, clase, tipomenu="";;
    private Boolean lAgregar;

    private JTextField tfCodigo, tfNombre;
    private JCheckBox chkActivo, chkCalcIva, chkRetIslr, chkRetIva, chkInvFis, chkInvLog, chkAddMenu;
    private JRadioButton rbLibComp, rbLibVent, rbDocCxC, rbDocCxP;
    private JComboBox cbOperacion;
    private JTabbedPane tpTab;
    
    public ControladorTipoDocumentos(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JRadioButton libComp, JRadioButton libVent, 
                               JRadioButton docCxC, JRadioButton docCxP, JCheckBox activo, JCheckBox calcIva, 
                               JCheckBox retIslr, JCheckBox retIva, JCheckBox invFis, JCheckBox invLog, 
                               JCheckBox addMenu, JComboBox combo, JTabbedPane tab){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;
        this.rbLibComp = libComp;
        this.rbLibVent = libVent;
        this.rbDocCxC = docCxC;
        this.rbDocCxP = docCxP;
        this.chkActivo = activo;
        this.chkCalcIva = calcIva;
        this.chkRetIslr = retIslr;
        this.chkRetIva = retIva;
        this.chkInvFis = invFis;
        this.chkInvLog = invLog;
        this.chkAddMenu = addMenu;
        this.cbOperacion = combo;
        this.tpTab = tab;
    }
    
    public void save(String codigo, String descrip, String activo, JTable tablaVentas, JTable tablaCompras, Boolean lAgregar, String clase){
        String cxc=""; String cxp=""; String libcom="";
        String libvta=""; String fisico=""; String logico=""; String caliva=""; String retiva="";
        String retislr=""; String invact="";
        
        xmlFile(clase);
        
        if (rbDocCxC.isSelected()==true){
            cxc="1";
        }else{
            cxc="0";
        }
        if (rbDocCxP.isSelected()==true){
            cxp="1";
        }else{
            cxp="0";
        }
        if (rbLibComp.isSelected()==true){
            libcom="1";
        }else{
            libcom="0";
        }
        if (rbLibVent.isSelected()==true){
            libvta="1";
        }else{
            libvta="0";
        }
        if (chkInvFis.isSelected()==true){
            fisico="1";
        }else{
            fisico="0";
        }
        if (chkInvLog.isSelected()==true){
            logico="1";
        }else{
            logico="0";
        }
        if (chkCalcIva.isSelected()==true){
            caliva="1";
        }else{
            caliva="0";
        }
        if (chkRetIslr.isSelected()==true){
            retislr="1";
        }else{
            retislr="0";
        }       
        if (chkRetIva.isSelected()==true){
            retiva="1";
        }else{
            retiva="0";
        }
        if (cbOperacion.getSelectedItem().equals("Suma")){
            invact="1";
        }else if (cbOperacion.getSelectedItem().equals("Resta")){
            invact="-1";
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
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sqlInsert = "INSERT INTO DNDOCUMENTOS (DOC_EMPRESA,DOC_USER,DOC_MACPC,DOC_CODIGO,DOC_DESCRI,DOC_CXC,DOC_CXP,DOC_INVACT,"+
                                                         "DOC_LIBVTA,DOC_LIBCOM,DOC_PAGOS,DOC_IVA,DOC_RETISLR,DOC_RETIVA,"+
                                                         "DOC_FISICO,DOC_LOGICO,DOC_ACTIVO) "+
                                                 "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+codigo.toUpperCase()+"','"+descrip+"',"+
                                                         "'"+cxc+"','"+cxp+"','"+invact+"','"+libvta+"','"+libcom+"','1',"+
                                                         "'"+caliva+"','"+retislr+"','"+retiva+"','"+fisico+"','"+logico+"',"+
                                                         "'"+activo+"');";

            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Tipo de Documento ''"+codigo.toUpperCase()+"''"+
                                                 " Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNDOCUMENTOS SET DOC_CODIGO='"+codigo.toUpperCase()+"', DOC_DESCRI='"+descrip+"',"+
                                                 "DOC_CXC='"+cxc+"',DOC_CXP='"+cxp+"',DOC_INVACT='"+invact+"',DOC_LIBVTA='"+libvta+"',"+
                                                 "DOC_LIBCOM='"+libcom+"',DOC_IVA='"+caliva+"',DOC_RETISLR='"+retislr+"',"+
                                                 "DOC_RETIVA='"+retiva+"',DOC_FISICO='"+fisico+"',"+
                                                 "DOC_LOGICO='"+logico+"',DOC_ACTIVO='"+activo+"' "+
                         "WHERE DOC_CODIGO='"+codigo+"'";
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Tipo de Documento ''"+codigo.toUpperCase()+"''"+
                                                 " Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tablaVentas, tablaCompras, clase);
        //-------------------------------------------------------------------
    }
    
    public void delete(String codigo, JTable tablaVentas, JTable tablaCompras, String clase){
        String nombre = null;
        int eliminado;
        xmlFile(clase);
        
        String descrip = tfNombre.getText().toString().trim();
        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(0)+codigo+" - "+descrip+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                                              "DOC_CODIGO='"+codigo+"'",true,"");
            
            ejecutaHilo("", false);
            cargaTabla(tablaVentas, tablaCompras, clase);
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
        
//        if (tab==0){
//            if (tablaVentas.getRowCount()==0){
//                this.dispose();
//                return;
//            }
//        }
//        if (tab==1){
//            if (tablaCompras.getRowCount()==0){
//                this.dispose();
//                return;
//            }
//        }
            
        return eleccion;
    }
    
    public void cargaTabla(JTable tablaVentas, JTable tablaCompras, String clase){
        xmlFile(clase);
        
        String SQL_VEN = "SELECT DOC_CODIGO,DOC_DESCRI,DOC_ACTIVO FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXC='1'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};
        
        cargatabla.cargatablas(tablaVentas,SQL_VEN,columnas);
        tablaVentas.getSelectionModel().setSelectionInterval(tablaVentas.getRowCount()-1, tablaVentas.getRowCount()-1);
        
        String SQL_COM = "SELECT DOC_CODIGO,DOC_DESCRI,DOC_ACTIVO FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXP='1'";
        
        cargatabla.cargatablas(tablaCompras,SQL_COM,columnas);
        tablaCompras.getSelectionModel().setSelectionInterval(tablaCompras.getRowCount()-1, tablaCompras.getRowCount()-1);
        
        tab=tpTab.getSelectedIndex();
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        if (tab == 0 ){
            String SQL = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXC='1'";
            rs = conet.consultar(SQL);

            String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXC='1'";
            Reg_count = conet.Count_Reg(SQL_Reg);
        
            if (Reg_count==1){
                rs.next();
            }else{
                rs.last();
            }
        }
        if (tab == 1){
            String SQL = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXP='1'";
            rs = conet.consultar(SQL);

            String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNUNDMEDIDA WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXP='1'";
            Reg_count = conet.Count_Reg(SQL_Reg);
        
            if (Reg_count==1){
                rs.next();
            }else{
                rs.last();
            }
        }
                
        ResultSet resultSet = ejecutaHilo(codigo, lAgregar);
        return resultSet;
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar){
        try {
            String SQLCodDoc="";
            
            if (tpTab.getSelectedIndex()==0){
                SQLCodDoc = "SELECT DOC_CODIGO FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXC='1' ORDER BY DOC_ID DESC LIMIT 1 ";
            }
            if (tpTab.getSelectedIndex()==1){
                SQLCodDoc = "SELECT DOC_CODIGO FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CXP='1' ORDER BY DOC_ID DESC LIMIT 1 ";
            }
                
            rs_codigo = conet.consultar(SQLCodDoc);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("DOC_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNDOCUMENTOS WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND DOC_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("DOC_CODIGO").trim()); tfNombre.setText(rs.getString("DOC_DESCRI").trim()); 
                    
                if (rs.getBoolean("DOC_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                if (rs.getBoolean("DOC_CXC")==true){
                    rbDocCxC.setSelected(true);
                    rbDocCxP.setSelected(false);
                    rbDocCxP.setEnabled(false);
                    rbLibComp.setSelected(false);
                    rbLibComp.setEnabled(false);
                }else{
                    rbDocCxC.setSelected(false);
                }
                if (rs.getBoolean("DOC_CXP")==true){
                    rbDocCxP.setSelected(true);
                    rbDocCxC.setSelected(false);
                    rbDocCxC.setEnabled(false);
                    rbLibVent.setSelected(false);
                    rbLibVent.setEnabled(false);
                }else{
                    rbDocCxP.setSelected(false);
                }
                if (rs.getBoolean("DOC_LIBCOM")==true){
                    rbLibComp.setSelected(true);
                    rbDocCxP.setSelected(true);
                    rbLibVent.setSelected(false);
                    rbLibVent.setEnabled(false);
                    rbDocCxC.setSelected(false);
                    rbDocCxC.setEnabled(false);
                }else{
                    rbLibComp.setSelected(false);
                }
                if (rs.getBoolean("DOC_LIBVTA")==true){
                    rbLibVent.setSelected(true);
                    rbDocCxC.setSelected(true);
                    rbLibComp.setSelected(false);
                    rbLibComp.setEnabled(false);
                    rbDocCxP.setSelected(false);
                    rbDocCxP.setEnabled(false);
                }else{
                    rbLibVent.setSelected(false);
                }

                if (rs.getString("DOC_INVACT").trim().equals("1")){
                    cbOperacion.setSelectedIndex(0);
                }else if (rs.getString("DOC_INVACT").trim().equals("-1")){
                    cbOperacion.setSelectedIndex(1);
                }
                if (rs.getBoolean("DOC_FISICO")==true){
                    chkInvFis.setSelected(true);
                }else{
                    chkInvFis.setSelected(false);
                }
                if (rs.getBoolean("DOC_LOGICO")==true){
                    chkInvLog.setSelected(true);
                }else{
                    chkInvLog.setSelected(false);
                }
                if (rs.getBoolean("DOC_IVA")==true){
                    chkCalcIva.setSelected(true);
                }else{
                    chkCalcIva.setSelected(false);
                }
                if (rs.getBoolean("DOC_RETIVA")==true){
                    chkRetIva.setSelected(true);
                }else{
                    chkRetIva.setSelected(false);
                }
                if (rs.getBoolean("DOC_RETISLR")==true){
                    chkRetIslr.setSelected(true);
                }else{
                    chkRetIslr.setSelected(false);
                }
                
                if (tab==0){
                    String SQL_Count = "SELECT * FROM DNMENU WHERE SUB_MEN_ID=12 AND MEN_TIPO=2";

                    try {
                        rs_menu_count = conet.consultar(SQL_Count);
                        nMenu=rs_menu_count.getRow();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
       
                    String SQL = "SELECT * FROM DNMENU INNER JOIN DNPERMISOLOGIA ON MIS_ID=MEN_ID AND MIS_ACTIVO=1 "+
                                 "WHERE SUB_MEN_ID=12 AND MEN_URL LIKE '%"+rs.getString("DOC_CODIGO").trim()+"%' AND MEN_TIPO=2";

                    try {
                        rs_menu = conet.consultar(SQL);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    String SQL_Count = "SELECT * FROM DNMENU WHERE SUB_MEN_ID=13 AND MEN_TIPO=2";

                    try {
                        rs_menu_count = conet.consultar(SQL_Count);
                        nMenu=rs_menu_count.getRow();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
       
                    String SQL = "SELECT * FROM DNMENU INNER JOIN DNPERMISOLOGIA ON MIS_ID=MEN_ID AND MIS_ACTIVO=1 "+
                                 "WHERE SUB_MEN_ID=13 AND MEN_URL LIKE '%"+rs.getString("DOC_CODIGO").trim()+"%' AND MEN_TIPO=2";

                    try {
                        rs_menu = conet.consultar(SQL);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if (rs_menu.getRow()>0){
                    chkAddMenu.setSelected(true);
                }else{
                    chkAddMenu.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorTipoDocumentos.class.getName()).log(Level.SEVERE, null, ex);
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
        chkActivo.setEnabled(false); chkInvFis.setEnabled(false); chkInvLog.setEnabled(false);
        chkCalcIva.setEnabled(false); chkRetIslr.setEnabled(false); chkRetIva.setEnabled(false);
        rbLibComp.setEnabled(false); rbLibVent.setEnabled(false); rbDocCxC.setEnabled(false);
        rbDocCxP.setEnabled(false); cbOperacion.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(true); tfNombre.setEnabled(true); 
            chkActivo.setEnabled(true); chkInvFis.setEnabled(true); chkInvLog.setEnabled(true);
            chkCalcIva.setEnabled(true); chkRetIslr.setEnabled(true); chkRetIva.setEnabled(true);
            rbLibComp.setEnabled(true); rbLibVent.setEnabled(true); rbDocCxC.setEnabled(true);
            rbDocCxP.setEnabled(true); cbOperacion.setEnabled(true);
            
            limpiar();
            
            if (tab==0){
                actionRadioButtom("Ventas");
            }
            if (tab==1){
                actionRadioButtom("Compras");
            }
            
            tfCodigo.requestFocus();
        }else if (accion=="Modificar"){
            tfNombre.setEnabled(true); cbOperacion.setEnabled(true);
            chkActivo.setEnabled(true); chkInvFis.setEnabled(true); chkInvLog.setEnabled(true);
            chkCalcIva.setEnabled(true); chkRetIslr.setEnabled(true); chkRetIva.setEnabled(true);
            
            if (tab==0){
                rbLibVent.setEnabled(true); 
            }
            if (tab==1){
                rbLibComp.setEnabled(true);
            }
            
            tfNombre.requestFocus();
        }
    }
    
    private void limpiar(){        
        tfCodigo.setText(""); tfNombre.setText(""); 
        chkInvFis.setSelected(false); chkInvLog.setSelected(false); chkCalcIva.setSelected(false); 
        chkRetIslr.setSelected(false); chkRetIva.setSelected(false); rbLibComp.setSelected(false); 
        rbLibVent.setSelected(false); 
        
        chkActivo.setSelected(true);
    }
    
    public void actionRadioButtom(String accion){
        if (accion.equals("Ventas")){
            if(rbDocCxC.isSelected()==true){
                rbLibComp.setSelected(false);
                rbLibComp.setEnabled(false);
                rbDocCxP.setSelected(false);
                rbDocCxP.setEnabled(false);
                
                cbOperacion.setSelectedIndex(1);
                
                //if (tab==0 && lAgregar==true){
                if (tab==0){
                    rbLibVent.setEnabled(true);
                    rbDocCxC.setEnabled(true);
                    rbLibComp.setSelected(false);
                }
            }else{
                this.rbLibComp.setEnabled(true);
                this.rbDocCxP.setEnabled(true);
                this.rbLibVent.setSelected(false);
            }
        }
        
        if (accion.equals("Compras")){
            if(rbDocCxP.isSelected()==true){
                this.rbLibVent.setSelected(false);
                this.rbLibVent.setEnabled(false);
                this.rbDocCxC.setSelected(false);
                this.rbDocCxC.setEnabled(false);
                
                cbOperacion.setSelectedIndex(0);
                
                if (tab==1 && lAgregar==true){
                    this.rbLibComp.setEnabled(true);
                    this.rbDocCxP.setEnabled(true);
                    this.rbLibVent.setSelected(false);
                }
            }else{
                this.rbLibVent.setEnabled(true);
                this.rbDocCxC.setEnabled(true);
                this.rbLibComp.setSelected(false);
            }
        }
    }
}