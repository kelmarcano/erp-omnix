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
import javax.swing.DefaultComboBoxModel;
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

public class ControladorMaestros {
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

    private JTextField tfCodigo, tfNombre, tfTipoMae, tfActivEco, tfTipoMoneda;
    private JFormattedTextField ftfLimitCred, ftfLimitCredVenc, ftfDiasPlazo, ftfRif, ftfNit, ftfDcto, ftfDiasVen, ftfretIva;
    private JTextArea taObserva;
    private JCheckBox chkActivo, chkClient, chkProveed;
    private JComboBox cbResidPais, cbZonaComer, cbCondicPago, cbContrIva, cbContrEsp;
    private JDateChooser dcFecha;
    private JButton btBuscaTipMae, btBuscaActEco, btBuscaMon;
    private JTabbedPane tpTab;
    
    public ControladorMaestros(){
        
    }

    public void setComponentes(JTextField codigo, JTextField nombre, JTextField tipoMaestro, JTextField activEco, 
                               JTextField tipoMoneda, JFormattedTextField limitCred, JFormattedTextField limitCredVenc, 
                               JFormattedTextField diasPlazo, JFormattedTextField rif, JFormattedTextField nit,
                               JFormattedTextField descuento, JFormattedTextField diasVen, JFormattedTextField retIva,
                               JTextArea observac, JComboBox residPais, JComboBox zonaComer, JComboBox condicPago, 
                               JComboBox contrIva, JComboBox contrEsp, JCheckBox activo, JCheckBox cliente, 
                               JCheckBox proveedor, JButton buscaTipMae, JButton buscaActEco, JButton buscaMon,
                               JTabbedPane tab, JDateChooser fecha){
        this.tfCodigo = codigo;
        this.tfNombre = nombre;
        this.tfTipoMae = tipoMaestro;
        this.tfActivEco = activEco;
        this.tfTipoMoneda = tipoMoneda;
        this.ftfLimitCred = limitCred;
        this.ftfLimitCredVenc = limitCredVenc;
        this.ftfDiasPlazo = diasPlazo;
        this.ftfRif = rif;
        this.ftfNit = nit;
        this.ftfDcto = descuento;
        this.ftfDiasVen = diasVen;
        this.ftfretIva = retIva;
        this.taObserva = observac;
        this.cbResidPais = residPais;
        this.cbZonaComer = zonaComer;
        this.cbCondicPago = condicPago;
        this.cbContrIva = contrIva;
        this.cbContrEsp = contrEsp;
        this.chkActivo = activo;
        this.chkClient = cliente;
        this.chkProveed = proveedor;
        this.btBuscaTipMae = buscaTipMae;
        this.btBuscaActEco = buscaActEco;
        this.btBuscaMon = buscaMon;
        this.tpTab = tab;
        this.dcFecha = fecha;
        
        comboforma();
    }
    
    public void save(String cod, String nomb, String activo, JTable tablaClien, JTable tablaProv, JTable tablaOtros, JTable tablaTodos, Boolean lAgregar, String clase){
        Limpiar_Variables();
        xmlFile(clase);

        //if (this.Bandera==true){
        //    JOptionPane.showMessageDialog(null, (String) Msg.get(0));
        //    return;
        //}
        if ("".equals(tfCodigo.getText())){
            JOptionPane.showMessageDialog(null, (String) Msg.get(1));
            tfCodigo.requestFocus();
            return;
        }
        if ("".equals(tfNombre.getText())){
            JOptionPane.showMessageDialog(null, (String) Msg.get(2));
            tfNombre.requestFocus();
            return;
        }
        if ("".equals(tfActivEco.getText())){
            JOptionPane.showMessageDialog(null, (String) Msg.get(3));
            tfActivEco.requestFocus();
            return;
        }

        //******************* Validacion de Rif *******************
        if (lAgregar==true){
            int registros;

            ValidaCodigo consulta = new ValidaCodigo();
            registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_RIF='"+ftfRif.getText().trim()+"'",false,"RIF");

            if (registros==1){
                ftfRif.requestFocus();
                ftfRif.setText("");
                return;
            }
        }
        //*********************************************************
        if (chkClient.isSelected()==true & chkProveed.isSelected()==false) {
            DefaultTableModel model = (DefaultTableModel) tablaClien.getModel();
            String [] filas={tfCodigo.getText(),tfNombre.getText(),tfTipoMae.getText(),tfActivEco.getText()};
            model.addRow(filas);

            valor_cli="1";
            valor_pro="0";
//            tpTab.setSelectedComponent(jPanel2);
        }
        if (chkProveed.isSelected()==true & chkClient.isSelected()==false) {
            DefaultTableModel model = (DefaultTableModel) tablaProv.getModel();
            String [] filas={tfCodigo.getText(),tfNombre.getText(),tfTipoMae.getText(),tfActivEco.getText()};
            model.addRow(filas);

            valor_pro="1";
            valor_cli="0";
//            tpTab.setSelectedComponent(jPanel3);
        }
        if (chkProveed.isSelected()==true & chkClient.isSelected()==true) {
            DefaultTableModel model = (DefaultTableModel) tablaTodos.getModel();
            String [] filas={tfCodigo.getText(),tfNombre.getText(),tfTipoMae.getText(),tfActivEco.getText()};
            model.addRow(filas);

            valor_cli="1";
            valor_pro="1";
//            tpTab.setSelectedComponent(jPanel5);
        }
        if (chkProveed.isSelected()==false & chkClient.isSelected()==false) {
            DefaultTableModel model = (DefaultTableModel) tablaOtros.getModel();
            String [] filas={tfCodigo.getText(),tfNombre.getText(),tfTipoMae.getText(),tfActivEco.getText()};
            model.addRow(filas);

            valor_cli="0";
            valor_pro="0";
//            tpTab.setSelectedComponent(jPanel6);
        }
        
        codigo = tfCodigo.getText(); nombre = tfNombre.getText().toUpperCase();
        
        tipmae = tfTipoMae.getText();
        if (tipmae.isEmpty()){
            tipmae=null;
        }else{
            tipmae="'"+tipmae+"'";
        }

        condic = cbCondicPago.getSelectedItem().toString();
        limite = ftfLimitCred.getText(); limite = limite.replace(".", "");
        limite = limite.replace(",", ".");
        mtoven = ftfLimitCredVenc.getText(); mtoven = mtoven.replace(".", "");
        mtoven = mtoven.replace(",", ".");
        dcto = ftfDcto.getText(); dias = ftfDiasPlazo.getText(); diaven = ftfDiasVen.getText();
        
        Date date = dcFecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha = sdf.format(date);
        
        retiva = ftfretIva.getText();
        observ = taObserva.getText(); rif = ftfRif.getText(); nit = ftfNit.getText();
        contrib = (String) cbContrIva.getSelectedItem(); activi = tfActivEco.getText();
        reside = (String) cbResidPais.getSelectedItem(); zonacom = (String) cbZonaComer.getSelectedItem();

        codmon = tfTipoMoneda.getText();
        if (codmon.isEmpty()){
            codmon=null;
        }else{
            codmon="'"+codmon+"'";
        }
        contriesp = (String) cbContrEsp.getSelectedItem();
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            String sqlInsert = "INSERT INTO DNMAESTRO (MAE_EMPRESA,MAE_USER,MAE_MACPC,MAE_CODIGO,MAE_NOMBRE,MAE_CODTIPM,MAE_LIMITE,"+
                                                "MAE_DCTO,MAE_CONDIC,MAE_ACTIVO,MAE_DIAS,MAE_OBSERV,MAE_RIF,"+
                                                "MAE_NIT,MAE_CONTRI,MAE_CODACT,MAE_RESIDE,MAE_ZONCOM,MAE_CODMON,"+
                                                "MAE_CONTESP,MAE_MTOVEN,MAE_DIAVEN,MAE_CLIENTE,MAE_PROVEED,MAE_RETIVA) "+
                                       "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+codigo+"','"+nombre+"',"+
                                               ""+tipmae+","+limite+","+dcto+",'"+condic+"','"+activo+"',"+dias+","+
                                               "'"+observ+"','"+rif+"','"+nit+"','"+contrib+"','"+activi+"',"+
                                               "'"+reside+"','"+zonacom+"',"+codmon+",'"+contriesp+"',"+mtoven+","+diaven+","+
                                               "'"+valor_cli+"','"+valor_pro+"',"+retiva+");";

            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Maestro ''"+nombre+"'' con el RIF #: "+rif+" Correctamente");
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE DNMAESTRO SET MAE_CODIGO='"+codigo+"',MAE_NOMBRE='"+nombre+"',"+
                                              "MAE_CODTIPM="+tipmae+",MAE_LIMITE="+limite+",MAE_DCTO="+dcto+","+
                                              "MAE_CONDIC='"+condic+"',MAE_ACTIVO='"+activo+"',MAE_DIAS="+dias+","+
                                              "MAE_OBSERV='"+observ+"',MAE_RIF='"+rif+"',"+
                                              "MAE_NIT='"+nit+"',MAE_CONTRI='"+contrib+"',MAE_CODACT='"+activi+"',"+
                                              "MAE_RESIDE='"+reside+"',MAE_ZONCOM='"+zonacom+"',MAE_CODMON="+codmon+","+
                                              "MAE_CONTESP='"+contriesp+"',MAE_MTOVEN="+mtoven+",MAE_DIAVEN="+diaven+","+
                                              "MAE_CLIENTE='"+valor_cli+"',MAE_PROVEED='"+valor_pro+"',MAE_RETIVA="+retiva+" "+
                         "WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CODIGO='"+codigo+"';";
            
            modificar.SqlUpdate(sql);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                                 "Modificación de Registro", "Se Modifico el Maestro ''"+nombre+"'' con RIF: "+rif+" Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tablaClien, tablaProv, tablaOtros, tablaTodos, clase);
        //-------------------------------------------------------------------
    }
    
    public int delete(String codigo, JTable tablaClien, JTable tablaProv, JTable tablaOtros, JTable tablaTodos, String clase){
        String nombre = null;
        int eliminado;
        xmlFile(clase);
        
        String descrip = tfNombre.getText().toString().trim();
        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(4)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                                              "MAE_CODIGO='"+codigo+"'",true,"");
            
            ejecutaHilo("", false);
            cargaTabla(tablaClien, tablaProv, tablaOtros, tablaTodos, clase);
        }
        return Reg_count;
    }
    
    public int cancelar(Boolean lAgregar, String clase){
        int eleccion = 0;
        xmlFile(clase);
        
        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(5));
        }else if (lAgregar==false){
            eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(6));
        }

        return eleccion;
    }
    
    public void cargaTabla(JTable tablaCliente, JTable tablaProveedor, JTable tablaTodos, JTable tablaOtros, String clase){
        xmlFile(clase);
        
        String SQL_Cli = "SELECT MAE_CODIGO,MAE_NOMBRE,MAE_RIF,MAE_CODTIPM,MAE_CODACT FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND (MAE_PROVEED='0' OR MAE_PROVEED IS NULL)";
        String[] columnas_Cli = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2),(String) header_table.get(3),(String) header_table.get(4)};
        
        cargatabla.cargatablas(tablaCliente,SQL_Cli,columnas_Cli);
        tablaCliente.getSelectionModel().setSelectionInterval(tablaCliente.getRowCount()-1, tablaCliente.getRowCount()-1);

        String SQL_Pro = "SELECT MAE_CODIGO,MAE_NOMBRE,MAE_RIF,MAE_CODTIPM,MAE_CODACT FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR MAE_CLIENTE IS NULL) AND MAE_PROVEED='1'";
        String[] columnas_Pro = {(String) header_table.get(0),(String) header_table.get(5),(String) header_table.get(6),(String) header_table.get(3),(String) header_table.get(4)};
        
        cargatabla.cargatablas(tablaProveedor,SQL_Pro,columnas_Pro);
        tablaProveedor.getSelectionModel().setSelectionInterval(tablaProveedor.getRowCount()-1, tablaProveedor.getRowCount()-1);

        String SQL_Todos = "SELECT MAE_CODIGO,MAE_NOMBRE,MAE_RIF,MAE_CODTIPM,MAE_CODACT FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND MAE_PROVEED='1'";
        String[] columnas_Todos = {(String) header_table.get(0),(String) header_table.get(7),(String) header_table.get(8),(String) header_table.get(3),(String) header_table.get(4)};
        
        cargatabla.cargatablas(tablaTodos,SQL_Todos,columnas_Todos);
        tablaTodos.getSelectionModel().setSelectionInterval(tablaTodos.getRowCount()-1, tablaTodos.getRowCount()-1);

        String SQL_Otros = "SELECT MAE_CODIGO,MAE_NOMBRE,MAE_RIF,MAE_CODTIPM,MAE_CODACT FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR MAE_CLIENTE IS NULL) AND (MAE_PROVEED='0' OR MAE_PROVEED IS NULL)";
        String[] columnas_Otros = {(String) header_table.get(0),(String) header_table.get(7),(String) header_table.get(8),(String) header_table.get(3),(String) header_table.get(4)};
        
        cargatabla.cargatablas(tablaOtros,SQL_Otros,columnas_Otros);
        tablaOtros.getSelectionModel().setSelectionInterval(tablaOtros.getRowCount()-1, tablaOtros.getRowCount()-1);
        
        tab=tpTab.getSelectedIndex();
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("MAE_CODIGO","DNMAESTRO",10,"WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        
        if (consecutivo==null){
            consecutivo="0000000001";
        }
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;        
        
        if (tab == 0 ){
            String SQLCli = "SELECT * FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND "+
                            "(MAE_PROVEED='0' OR MAE_PROVEED IS NULL)";
            rs = conet.consultar(SQLCli);
            
            String SQLCli_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND "+
                                "(MAE_PROVEED='0' OR MAE_PROVEED IS NULL)";            
            Reg_count = conet.Count_Reg(SQLCli_Reg);

            if (Reg_count==1){
                rs.next();
            }else{
                rs.last();
            }
        }
        if (tab == 1 ){
            String SQLPro = "SELECT * FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR "+
                            "MAE_CLIENTE IS NULL) AND MAE_PROVEED='1'";
            rs = conet.consultar(SQLPro);
            
            String SQLPro_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR "+
                                "MAE_CLIENTE IS NULL) AND MAE_PROVEED='1'";
            Reg_count = conet.Count_Reg(SQLPro_Reg);

            if (Reg_count==1){
                rs.next();
            }else{
                rs.last();
            }
        }
        if (tab == 2 ){
            String SQLTodo = "SELECT * FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND MAE_PROVEED='1'";
            rs = conet.consultar(SQLTodo);
            
            String SQLTodo_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND MAE_PROVEED='1'";
            Reg_count = conet.Count_Reg(SQLTodo_Reg);

            if (Reg_count==1){
                rs.next();
            }else{
                rs.last();
            }
        }
        if (tab == 3 ){
            String SQLOtro = "SELECT * FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR "+
                             "MAE_CLIENTE IS NULL) AND (MAE_PROVEED='0' OR "+
                             "MAE_PROVEED IS NULL);";
            rs = conet.consultar(SQLOtro);
            
            String SQLOtro_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR "+
                                 "MAE_CLIENTE IS NULL) AND (MAE_PROVEED='0' OR "+
                                 "MAE_PROVEED IS NULL);";
            Reg_count = conet.Count_Reg(SQLOtro_Reg);

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
        comboforma();
        
        try {
            String SQLCodDoc="";
            
            if (tab == 0 ){
                SQLCodDoc = "SELECT MAE_CODIGO FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND " +
                            "(MAE_PROVEED='0' OR MAE_PROVEED IS NULL) ORDER BY MAE_CODIGO DESC LIMIT 1 ";
            }
            if (tab == 1 ){
                SQLCodDoc = "SELECT MAE_CODIGO FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR "+
                            "MAE_CLIENTE IS NULL) AND MAE_PROVEED='1' ORDER BY MAE_CODIGO DESC LIMIT 1 ";
            }
            if (tab == 2){
                SQLCodDoc = "SELECT MAE_CODIGO FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CLIENTE='1' AND MAE_PROVEED='1' "+
                            "ORDER BY MAE_CODIGO DESC LIMIT 1 ";
            }
            if (tab == 3){
                SQLCodDoc = "SELECT MAE_CODIGO FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND (MAE_CLIENTE='0' OR "+
                            "MAE_CLIENTE IS NULL) AND (MAE_PROVEED='0' OR "+
                            "MAE_PROVEED IS NULL) ORDER BY MAE_CODIGO DESC LIMIT 1";
            }
                
            rs_codigo = conet.consultar(SQLCodDoc);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("MAE_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMaestros.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorMaestros.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNMAESTRO WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MAE_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorMaestros.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        String sql_cmb="";

        if (Reg_count > 0){
            try {
                tfCodigo.setText(rs.getString("MAE_CODIGO").trim()); tfNombre.setText(rs.getString("MAE_NOMBRE").trim());
                ftfLimitCred.setText(rs.getString("MAE_LIMITE").replace(".", ",")); 
                ftfDcto.setText(rs.getString("MAE_DCTO"));

                if (rs.getBoolean("MAE_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                ftfDiasPlazo.setText(rs.getString("MAE_DIAS")); 
            
                //--------- Covierte el String de la Fecha en Date ----------
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fch = null;

                try {
                    if (rs.getString("MAE_FECHA")==null ){
                        Date fecha = new Date();
                        String Fch = sdf.format(fecha);
                        fch = sdf.parse(Fch);
                    }else{
                        fch = sdf.parse(rs.getString("MAE_FECHA"));
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            
                dcFecha.setDate(fch);
                //-----------------------------------------------------------

                if (rs.getString("MAE_OBSERV")==null){
                    taObserva.setText("");
                }else{
                    taObserva.setText(rs.getString("MAE_OBSERV"));
                }
                if (rs.getString("MAE_CODTIPM")==null){
                    tfTipoMae.setText("");
                }else{
                    tfTipoMae.setText(rs.getString("MAE_CODTIPM").trim());
                }       
                if (rs.getString("MAE_CODACT")==null){
                    tfActivEco.setText("");
                }else{
                    tfActivEco.setText(rs.getString("MAE_CODACT"));
                }

                ftfRif.setText(rs.getString("MAE_RIF").trim()); ftfNit.setText(rs.getString("MAE_NIT").trim());
//String condicP = rs.getString("MAE_CONDIC").trim();
//                cbCondicPago.setSelectedItem(condicP);
                cbCondicPago.setSelectedItem(rs.getString("MAE_CONDIC").trim());
                cbContrIva.setSelectedItem(rs.getString("MAE_CONTRI").trim()); cbResidPais.setSelectedItem(rs.getString("MAE_RESIDE").trim());
                cbZonaComer.setSelectedItem(rs.getString("MAE_ZONCOM").trim()); tfTipoMoneda.setText(rs.getString("MAE_CODMON").trim());
                cbContrEsp.setSelectedItem(rs.getString("MAE_CONTESP").trim()); ftfretIva.setText(rs.getString("MAE_RETIVA").trim());
                ftfLimitCredVenc.setText(rs.getString("MAE_MTOVEN").replace(".", ",")); 
                ftfDiasVen.setText(rs.getString("MAE_DIAVEN"));

                if (rs.getBoolean("MAE_CLIENTE")==true){
                    chkClient.setSelected(true);
                }else{
                    chkClient.setSelected(false);
                }

                if (rs.getBoolean("MAE_PROVEED")==true){
                    chkProveed.setSelected(true);
                }else{
                    chkProveed.setSelected(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMaestros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            habilitar("Inicializa");
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
        ftfRif.setEnabled(false); ftfNit.setEnabled(false); tfTipoMae.setEnabled(false);
        ftfLimitCred.setEnabled(false); ftfDcto.setEnabled(false); cbCondicPago.setEnabled(false); 
        ftfLimitCredVenc.setEnabled(false); ftfDiasPlazo.setEnabled(false); ftfDiasVen.setEnabled(false);
        tfActivEco.setEnabled(false); tfTipoMoneda.setEnabled(false); cbResidPais.setEnabled(false); 
        cbZonaComer.setEnabled(false); cbContrEsp.setEnabled(false); cbContrIva.setEnabled(false);
        taObserva.setEnabled(false); chkActivo.setEnabled(false); chkProveed.setEnabled(false); 
        chkClient.setEnabled(false); ftfretIva.setEnabled(false); 
        dcFecha.setEnabled(false);
        
        btBuscaTipMae.setEnabled(false); btBuscaActEco.setEnabled(false);
        btBuscaMon.setEnabled(false);
    }

    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            tfCodigo.setEnabled(false); tfNombre.setEnabled(true);
            ftfRif.setEnabled(true); ftfNit.setEnabled(true); tfTipoMae.setEnabled(true);
            ftfLimitCred.setEnabled(true); ftfDcto.setEnabled(true); cbCondicPago.setEnabled(true); 
            ftfLimitCredVenc.setEnabled(true); ftfDiasPlazo.setEnabled(true); ftfDiasVen.setEnabled(true);
            tfActivEco.setEnabled(true); tfTipoMoneda.setEnabled(true); cbResidPais.setEnabled(true); 
            cbZonaComer.setEnabled(true); cbContrEsp.setEnabled(true); cbContrIva.setEnabled(true);
            taObserva.setEnabled(true); chkActivo.setEnabled(true); chkProveed.setEnabled(true); 
            chkClient.setEnabled(true); ftfretIva.setEnabled(true); 
            dcFecha.setEnabled(true);
        
            btBuscaTipMae.setEnabled(true); btBuscaActEco.setEnabled(true);
            btBuscaMon.setEnabled(true);
            
            limpiar();
            
            ftfRif.requestFocus();
            
            if (tab==0){
                chkClient.setSelected(true);
            }else if (tab==1){
                chkProveed.setSelected(true);
            }
        }else if (accion=="Modificar"){
            tfTipoMae.setEnabled(true); ftfRif.setEnabled(true);
            ftfLimitCred.setEnabled(true); ftfDcto.setEnabled(true); cbCondicPago.setEnabled(true); 
            ftfLimitCredVenc.setEnabled(true); ftfDiasPlazo.setEnabled(true); ftfDiasVen.setEnabled(true);
            tfActivEco.setEnabled(true); tfTipoMoneda.setEnabled(true); cbResidPais.setEnabled(true); 
            cbZonaComer.setEnabled(true); cbContrEsp.setEnabled(true); cbContrIva.setEnabled(true);
            taObserva.setEnabled(true); chkActivo.setEnabled(true); chkProveed.setEnabled(true); 
            chkClient.setEnabled(true); ftfretIva.setEnabled(true);
        
            btBuscaTipMae.setEnabled(true); btBuscaActEco.setEnabled(true);
            btBuscaMon.setEnabled(true);
            
            ftfRif.requestFocus();
        }
    }
    
    private void limpiar(){        
        ftfRif.setText("");

        tfCodigo.setText(""); tfNombre.setText("");
        ftfRif.setText(" -        - "); ftfNit.setText(""); tfTipoMae.setText("");
        ftfLimitCred.setText("0.00"); ftfDcto.setText("0"); 
        ftfLimitCredVenc.setText("0.00"); ftfDiasPlazo.setText("0"); ftfDiasVen.setText("0");
        tfActivEco.setText(""); tfTipoMoneda.setText(""); taObserva.setText(""); ftfretIva.setText("0");

        cbCondicPago.setSelectedItem(""); cbResidPais.setSelectedItem(""); cbZonaComer.setSelectedItem(""); 
        cbContrEsp.setSelectedItem(""); cbContrIva.setSelectedItem("");

        //--------- Covierte el String de la Fecha en Date ----------
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = new Date();

        Date fch = null;
        String Fch = sdf.format(fecha);
        try {
            fch = sdf.parse(Fch);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorMaestros.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        dcFecha.setDate(fch);
             
        chkActivo.setSelected(false); chkProveed.setSelected(false); chkClient.setSelected(false);
    }
    
    private void comboforma() {
        String sql = "SELECT CDI_DESCRI AS DATO1 FROM dncondipago WHERE CDI_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        cbCondicPago.setModel(mdl);
        
        int prueba = cbCondicPago.getItemCount();
    }
    
    private void Limpiar_Variables(){
        codigo = ""; nombre = ""; tipmae = ""; valor_cli = ""; valor_pro = "";
        limite = "0.00"; dcto = "0"; condic = ""; dias = "0"; observ = "0"; rif = "";
        nit = ""; contrib = ""; activi = ""; reside = ""; zonacom = ""; codmon = "";
        mtoven = "0.00"; diaven = "0"; contriesp = ""; coddir = ""; otromon = ""; activo = "";
        fecha = ""; retiva = "0"; foto = "";
    }
}