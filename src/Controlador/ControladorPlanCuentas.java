package Controlador;

import Modelos.Bitacora;
import Modelos.ModeloPlanCuentas;
import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.CargaComboBoxs;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ControladorPlanCuentas {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ModeloPlanCuentas modeloPlanCuentas = ModeloPlanCuentas.getDatosPlanCuentas();
    conexion conet = new conexion();
    
    CargaTablas cargatabla = new CargaTablas();
    private Vector Msg, elementos, header_table;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count, nivel;
    private String codigo, descrip, activo, tabla, clase;
    private String ctaPrevia, ctaPadre="0", nivelMax;
    private Boolean lAgregar, lMsg=false;
    
    public ControladorPlanCuentas(){
        
    }
    
    public Boolean save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar, String clase, String ctaPadre, String nivelCrea, String clasifCta, String naturaleza){
        Boolean lSave = true;
        xmlFile(clase);
        
        if ("".equals(modeloPlanCuentas.getCodigoCta().getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(3));
            modeloPlanCuentas.getCodigoCta().requestFocus();
            return
            lSave = false;
        }
        
        if ("".equals(modeloPlanCuentas.getTfNombre().getText())){
            JOptionPane.showMessageDialog(null,(String) Msg.get(4));
            modeloPlanCuentas.getTfNombre().requestFocus();
            return
            lSave = false;
        }
        
        lSave = insert(codigo, descrip, activo, lAgregar, ctaPadre, nivelCrea, naturaleza);
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla, clase);
        //-------------------------------------------------------------------
            
        return lSave;
    }
    
    public int existeCod(String codigo){
        int existe;

        ValidaCodigo consulta = new ValidaCodigo();
        existe = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CTA_CODIGO='"+codigo+"'",true,"");
        
        return existe;
    }
    
    public int existeCod(String codigo, String nivelP, String nivel){
        int existe;
        
        String punto = codigo.substring(codigo.length()-1);
        
        if (punto.equals(".")){
            codigo = codigo.substring(0, codigo.length()-1);
        }

        ValidaCodigo consulta = new ValidaCodigo();
        String sqlCta = "SELECT COUNT(*) AS REGISTROS FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                        "CTA_PADRE="+nivelP+" AND CTA_NIVEL="+nivel+" AND CTA_CODIGO='"+codigo+"'";
        existe = consulta.ValidaCodigo(sqlCta,true,"");
        
        return existe;
    }
    
    public Object[][] cargaEstructuraCta(String ctaPadre){
        Object structCta[][]=null;
        structCta=new Object[1][5];
        String SQLstructCta="";
            
        try {
            SQLstructCta = "SELECT * FROM DNESTRUCTURA_CTA WHERE STR_PADRE="+ctaPadre;
            
            rs_codigo = conet.consultar(SQLstructCta);
            
            if (rs_codigo.getRow()>0){
                structCta[0][0] = rs_codigo.getString("STR_PADRE");
                structCta[0][1] = rs_codigo.getString("STR_NIVEL_MAX");
                structCta[0][2] = rs_codigo.getString("STR_NIVEL_PREFIJO");
                structCta[0][3] = rs_codigo.getString("STR_PREFIJO");
                structCta[0][4] = rs_codigo.getString("STR_LONG_PREF");
            }else{
                structCta=new Object[1][1];
                structCta[0][0] = " ";
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPlanCuentas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPlanCuentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return structCta;
    }
    
    public Boolean insert(String codigo, String descrip, String activo, Boolean lAgregar, String ctaPadre, String nivelCrea, String naturaleza){
        Boolean lInsert = null;
        
        String debe = "", haber = "";
                
        if (naturaleza.equals("Deudor")){
            debe = "+";
            haber = "-";
        }else if (naturaleza.equals("Acreedor")){
            debe = "-";
            haber = "+";
        }
                
        if (lAgregar==true){
            if (existeCod(codigo)==0){
                SQLQuerys insertar = new SQLQuerys();
                String sql = "INSERT INTO DNCTA (CTA_EMPRESA,CTA_PADRE,CTA_NIVEL,CTA_USER,CTA_MACPC,CTA_CODIGO,CTA_NOMBRE,"+
                                                "CTA_ACTIVO,CTA_NATURALEZA,CTA_DEBE,CTA_HABER,CTA_NIVEL_2,"+
                                                "CTA_NIVEL_3,CTA_NIVEL_4,CTA_NIVEL_5,CTA_NIVEL_6,CTA_NIVEL_7,"+
                                                "CTA_NIVEL_8) "+
                                        "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+ctaPadre+"', '"+nivelCrea+"', "+
                                                "'"+VarGlobales.getIdUser()+"','"+
                                                    VarGlobales.getMacPc()+"','"+codigo+"',"+
                                                "'"+descrip+"','"+activo+"','"+naturaleza+"',"+
                                                "'"+debe+"','"+haber+"','"+modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString()+"',"+
                                                "'"+modeloPlanCuentas.getCbNivelPrev2().getSelectedItem().toString()+"',"+
                                                "'"+modeloPlanCuentas.getCbNivelPrev3().getSelectedItem().toString()+"',"+
                                                "'"+modeloPlanCuentas.getCbNivelPrev4().getSelectedItem().toString()+"',"+
                                                "'"+modeloPlanCuentas.getCbNivelPrev5().getSelectedItem().toString()+"',"+
                                                "'"+modeloPlanCuentas.getCbNivelPrev6().getSelectedItem().toString()+"',"+
                                                "'"+modeloPlanCuentas.getCbNivelPrev7().getSelectedItem().toString()+"');";
            
                insertar.SqlInsert(sql);
            
                lInsert = true;
                
                Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                     VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                     "Inserción de Nuevo Registro", "Se creo la Cuenta ''"+descrip+"'' con el Código #: "+codigo+" Correctamente");
            }else{
                JOptionPane.showMessageDialog(null,"Este numero de cuenta ''"+codigo+"'' ya existe", "Notificación", JOptionPane.WARNING_MESSAGE);
                lInsert = false;
            }
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            modificar.SqlUpdate("UPDATE DNCTA SET CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"',CTA_CODIGO='"+codigo+"',CTA_NOMBRE='"+descrip+"',"+
                                                 "CTA_ACTIVO="+activo+",CTA_NATURALEZA='"+naturaleza+"',"+
                                                 "CTA_DEBE='"+debe+"',CTA_HABER='"+haber+"' "+
                                "WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CTA_CODIGO='"+codigo+"'");
            
            lInsert = true;
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la Cuenta ''"+descrip+"'' Correctamente");
        }
        
        return lInsert;
    }
    
    public void delete(String codigo, JTable tabla, String clase){
        int eliminado;
        xmlFile(clase);
        
        int salir = JOptionPane.showConfirmDialog(null,(String) Msg.get(0)+codigo+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CTA_CODIGO='"+codigo+"'");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CTA_CODIGO='"+codigo+"'",true,"");
            
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
        
//        cbPadre.setSelectedIndex(0);
//        if (eleccion==0){
//            reset(2);
//        }

        return eleccion;
    }
    
    public void cargaTabla(JTable tabla, String clase){
        xmlFile(clase);
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT CTA_CODIGO,CTA_NOMBRE FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY CTA_CODIGO";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla,SQL,columnas);        
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT CTA_CODIGO,CTA_NOMBRE FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY CTA_CODIGO";
        rs = conet.consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
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
            
            SQLCodTipMae = "SELECT CTA_CODIGO FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY CTA_CODIGO DESC LIMIT 1 ";
                
            rs_codigo = conet.consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("CTA_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPlanCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CTA_CODIGO='"+codigo+"'";

            try {
                rs = conet.consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorPlanCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNCTA WHERE CTA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CTA_CODIGO='"+codigo+"'";
            Reg_count = conet.Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPlanCuentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void mostrar(){
        if (Reg_count > 0){
            try {
                if (rs.getBoolean("CTA_ACTIVO")==true){
                    modeloPlanCuentas.getChkActivo().setSelected(true);
                }else{
                    modeloPlanCuentas.getChkActivo().setSelected(false);
                }
                
                modeloPlanCuentas.getCodigoCta().setText(rs.getString("CTA_CODIGO").trim()); 
                String prueba = modeloPlanCuentas.getCodigoCta().getText();
                modeloPlanCuentas.getTfNombre().setText(rs.getString("CTA_NOMBRE").trim());
                
                modeloPlanCuentas.getCbCtaPadre().setSelectedItem(rs.getString("CTA_PADRE").trim());
                modeloPlanCuentas.getCbCtaNivelCrea().setSelectedItem(rs.getString("CTA_NIVEL").trim());

                modeloPlanCuentas.getCbNivelPrev1().setSelectedItem(rs.getString("CTA_NIVEL_2").trim());
                modeloPlanCuentas.getCbNivelPrev2().setSelectedItem(rs.getString("CTA_NIVEL_3").trim());
                modeloPlanCuentas.getCbNivelPrev3().setSelectedItem(rs.getString("CTA_NIVEL_4").trim());
                modeloPlanCuentas.getCbNivelPrev4().setSelectedItem(rs.getString("CTA_NIVEL_5").trim());
                modeloPlanCuentas.getCbNivelPrev5().setSelectedItem(rs.getString("CTA_NIVEL_6").trim());
                modeloPlanCuentas.getCbNivelPrev6().setSelectedItem(rs.getString("CTA_NIVEL_7").trim());
                modeloPlanCuentas.getCbNivelPrev7().setSelectedItem(rs.getString("CTA_NIVEL_8").trim());
                
//                if (rs.getString("CTA_CLASIFICACION").equals("")){
//                    cbClasifCta.setSelectedItem(" ");
//                }else{
//                    cbClasifCta.setSelectedItem(rs.getString("CTA_CLASIFICACION"));
//                }
//                if (rs.getString("CTA_NATURALEZA").equals("")){
//                    cbNaturaleza.setSelectedItem(" ");
//                }else{
//                    cbNaturaleza.setSelectedItem(rs.getString("CTA_NATURALEZA"));
//                }
                deshabilitar();
                
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPlanCuentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void comboNivel(JComboBox combo, String padre, int nivelPrev, String nivelCrea, String ctaPrev){
        nivel = nivelPrev;
        
        String sql = "SELECT CONCAT(CTA_CODIGO,' - ', CTA_NOMBRE) AS DATO1 FROM DNCTA " +
                     " WHERE CTA_PADRE="+padre+" AND "+
                     "CTA_NIVEL="+nivel+" AND CTA_CODIGO LIKE '%"+ctaPrev+"%'";

        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));
        combo.setModel(mdl);
        
        if (combo.getItemCount()>1 && nivel!=(Integer.valueOf(nivelCrea))){
            combo.setEnabled(true);
        }else{
            combo.setEnabled(false);
            
            if (nivel<Integer.valueOf(nivelCrea)){
                JOptionPane.showMessageDialog(null,"No puede continuar con la creación de la cta de Nivel "+nivelCrea+
                                                   " porque aun no existen las cuenta posteriores a la cuenta "+ctaPrev, "Notificación", JOptionPane.WARNING_MESSAGE);

//                lMsg=true;
                reset(nivel);
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
        //tfCodigo.setEnabled(false); 
        modeloPlanCuentas.getTfNombre().setEnabled(false); modeloPlanCuentas.getChkActivo().setEnabled(false);
        modeloPlanCuentas.getCbCtaPadre().setEnabled(false); modeloPlanCuentas.getCbCtaNivelCrea().setEnabled(false);
        modeloPlanCuentas.getCbNivelPrev1().setEnabled(false); modeloPlanCuentas.getCbNivelPrev2().setEnabled(false);
        modeloPlanCuentas.getCbNivelPrev3().setEnabled(false); modeloPlanCuentas.getCbNivelPrev4().setEnabled(false);
        modeloPlanCuentas.getCbNivelPrev5().setEnabled(false); modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
        modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
        
        reset(2);
        //cbPadre.setSelectedIndex(0);
        
//        cbNivelCrea.setSelectedIndex(0);
//        cbNivelIncre.setSelectedIndex(0);
    }

    public void habilitar(String accion){
        switch (accion) {
            case "Inicializa":
                //tfCodigo.setEnabled(true);
                modeloPlanCuentas.getTfNombre().setEnabled(true);
                modeloPlanCuentas.getChkActivo().setEnabled(true);
                modeloPlanCuentas.getCbCtaPadre().setEnabled(true);
                modeloPlanCuentas.getCbCtaNivelCrea().setEnabled(true);
//                cbNivelPrev1.setEnabled(true); cbNivelPrev2.setEnabled(true);
//                cbNivelPrev3.setEnabled(true); cbNivelPrev4.setEnabled(true);
//               cbNivelPrev5.setEnabled(true); cbNivelPrev6.setEnabled(true);
//                cbNivelPrev7.setEnabled(true);
                
                limpiar();
                modeloPlanCuentas.getTfNombre().requestFocus();
                
                break;
            case "Modificar":
                modeloPlanCuentas.getTfNombre().setEnabled(true);
                modeloPlanCuentas.getChkActivo().setEnabled(true);
//                cbNivelPrev1.setEnabled(true); cbNivelPrev2.setEnabled(true);
//                cbNivelPrev3.setEnabled(true); cbNivelPrev4.setEnabled(true);
//                cbNivelPrev5.setEnabled(true); cbNivelPrev6.setEnabled(true);
//                cbNivelPrev7.setEnabled(true);
                
                reset(2);
                
                modeloPlanCuentas.getTfNombre().requestFocus();
                break;
        }
    }
    
    private void limpiar(){
        modeloPlanCuentas.getCodigoCta().setText(""); 
        modeloPlanCuentas.getTfNombre().setText(""); 
        modeloPlanCuentas.getChkActivo().setSelected(true);
        modeloPlanCuentas.getCbCtaPadre().setSelectedIndex(0);
//        cbNivelCrea.setSelectedIndex(0);
//        cbNivelIncre.setSelectedIndex(0);
    }
    
    public void reset(int desde){
        switch (desde) {
            case 2:
                if(modeloPlanCuentas.getCbNivelPrev1().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev1(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev1().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev2().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev2(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev2().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev3().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev3(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev3().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev4().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev4(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev4().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev5().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev5(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev5().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev6().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev6(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
            case 3:
                if(modeloPlanCuentas.getCbNivelPrev2().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev2(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev2().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev3().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev3(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev3().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev4().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev4(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev4().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev5().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev5(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev5().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev6().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev6(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
            case 4:
                if(modeloPlanCuentas.getCbNivelPrev3().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev3(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev3().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev4().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev4(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev4().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev5().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev5(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev5().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev6().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev6(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
            case 5:
                if(modeloPlanCuentas.getCbNivelPrev4().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev4(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev4().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev5().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev5(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev5().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev6().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev6(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
            case 6:
                if(modeloPlanCuentas.getCbNivelPrev5().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev5(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev5().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev6().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev6(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
            case 7:
                if(modeloPlanCuentas.getCbNivelPrev6().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev6(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev6().setEnabled(false);
                }
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
            case 8:
                if(modeloPlanCuentas.getCbNivelPrev7().isEnabled()==true){
                    comboNivel(modeloPlanCuentas.getCbNivelPrev7(), modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), desde, "", "");
                    modeloPlanCuentas.getCbNivelPrev7().setEnabled(false);
                }
        
                break;
        }
    }
    
    public String[] cargaCombos(int nivel, Boolean incrementa){
        int maxNiveles = nivel;
        int niveles = 0;
        
        String[] arregloNiveles = new String[maxNiveles];
        
        for (int i=0; i<maxNiveles; i++){
            niveles++;
            
            if (niveles==1 && incrementa==true){
                arregloNiveles[i] = "";
            }else{
                arregloNiveles[i] = String.valueOf(niveles);
            }
        }
        
        return arregloNiveles;
    }
    
    public void ctaPrevia(JComboBox combo, Boolean lIncrement){
        String prefijo = null;
        String prevCta = modeloPlanCuentas.getCodigoCta().getText();
        int pos = combo.getSelectedItem().toString().indexOf(" ", 1);
        String cta = combo.getSelectedItem().toString().substring(0, pos);
        String newCta = cta;
        String nivelCta = String.valueOf(Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1);

//        if (modeloPlanCuentas.getlAgregar()==true){
        if (lIncrement==true){
            if (existeCod(newCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), nivelCta)>0){
                int posicion = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1);
            
                if (posicion>0){
                    prevCta = combo.getSelectedItem().toString().substring(0, pos)+".";
                    
                    if (Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>=Integer.valueOf(modeloPlanCuentas.getNivelPrefijo())){
                        prefijo = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", 1);
                    }else{
                        prefijo = "1";
                    }
            
                    newCta = prevCta+prefijo;
                    modeloPlanCuentas.getCodigoCta().setText(newCta);
                    
                    if (existeCod(newCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>0){
                        incrementaCta();
                    }
                }
            }
        }else{
            if (nivel==1){
                prefijo = ".1";
                
                newCta = newCta+prefijo;
                modeloPlanCuentas.getCodigoCta().setText(newCta);
                
                if (existeCod(newCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>0){
                    incrementaCta();
                }
            }else{
                if (newCta.equals(modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString())){
                    modeloPlanCuentas.getCodigoCta().setText(prevCta);
                }else{
                    modeloPlanCuentas.getCodigoCta().setText(newCta);
                }
            }
        }
    }
    
    public void vizualizaCta(){
        String prevCta="";
        
        if (modeloPlanCuentas.getNivelPrefijo().equals("1")){
            prevCta = modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()+".";
        }else{
            prevCta = modeloPlanCuentas.getCodigoCta().getText();
            ctaPrevia = modeloPlanCuentas.getCodigoCta().getText();
        }

        prevCta = prevCta.replace("0", "");
        
        int niveles = Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1;
        prevCta = modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()+".";
            
        for (int i=0; i<niveles; i++){
             prevCta = prevCta+modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()+".";
        }

        int nivel = (Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString()))-(Integer.valueOf(modeloPlanCuentas.getNivelPrefijo()))+1;
        int posicion = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getNivelPrefijo())-1);

        if (modeloPlanCuentas.getPrefijo()!=" "){
            prevCta = prevCta.substring(0, posicion+1);
            
            if (posicion==0){
                prevCta = "";
            }
            
            String prefijo = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", Integer.valueOf(modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()));
            
            for (int i=0; i<nivel; i++){
                prevCta = prevCta+prefijo+".";
            }
        }
        
        prevCta = limpiaPunto(prevCta);

        modeloPlanCuentas.getCodigoCta().setText(prevCta);
    }
    
    public void incrementaCta(){
        String prevCta = modeloPlanCuentas.getCodigoCta().getText();
        String prefijo = null;
        
        if (existeCod(prevCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>0){
            int posicion = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1);
            
            if (posicion>0){
                prevCta = prevCta.substring(posicion+1, prevCta.length());
            
                int ctaIncremeta = Integer.valueOf(prevCta);
                ctaIncremeta++;
            
                prevCta = modeloPlanCuentas.getCodigoCta().getText();
                prevCta = prevCta.substring(0, posicion+1);
            
                if (Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>=Integer.valueOf(modeloPlanCuentas.getNivelPrefijo())){
                    if (prefijo==" "){
                        prefijo = String.valueOf(ctaIncremeta);
                    }else{
                        prefijo = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", ctaIncremeta);
                    }
                }else{
                    prefijo = String.valueOf(ctaIncremeta);
                }
            
                prevCta = prevCta+prefijo;
                modeloPlanCuentas.getCodigoCta().setText(prevCta);
        
                if (existeCod(prevCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>0){
                    incrementaCta();
                }
            }
        }else{
            int posicion = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1);
            
            prevCta = modeloPlanCuentas.getCodigoCta().getText();
            prevCta = prevCta.substring(0, posicion+1);
            
            prevCta = limpiaPunto(prevCta);
            String nivelCrea = String.valueOf(Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1);
            
            if (existeCod(prevCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), nivelCrea)==0 && lMsg==false && !modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString().equals("1")){
                JOptionPane.showMessageDialog(null,"No puede crear la cta de Nivel "+modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString()+" porque la cuenta "+prevCta+" que precede no ha sido creada aun", "Notificación", JOptionPane.WARNING_MESSAGE);
                
                lMsg=true;
                modeloPlanCuentas.getCodigoCta().setText(ctaPrevia);

                String[] arregloNiveles = cargaCombos(Integer.valueOf(nivelMax), false);
                modeloPlanCuentas.getCbCtaNivelCrea().setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles));
            }
        }
    }
    
    public void vizualizaCtaPrevia(){
        String prevCta="";
        
        if (modeloPlanCuentas.getNivelPrefijo().equals("1")){
            prevCta = modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()+".";
        }else{
            prevCta = modeloPlanCuentas.getCodigoCta().getText();
            ctaPrevia = modeloPlanCuentas.getCodigoCta().getText();
        }

        prevCta = prevCta.replace("0", "");
        
        int nivel = (Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString()))-(Integer.valueOf(modeloPlanCuentas.getNivelPrefijo()))+1;
        if (nivel==0){
            nivel = 1;
        }
        int posicion = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString())-1);

        if (modeloPlanCuentas.getPrefijo()!=" "){
            prevCta = prevCta.substring(0, posicion+1);
            
            if (posicion==0){
                prevCta = "";
            }
            
            String prefijo = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", Integer.valueOf(modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()));
            
            for (int i=0; i<nivel; i++){
                prevCta = prevCta+prefijo+".";
            }
        }else{
            int niveles = Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1;
            prevCta = modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()+".";
            
            if (modeloPlanCuentas.getPrefijo()==" "){
                for (int i=0; i<niveles; i++){
                    prevCta = prevCta+modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()+".";
                }
            }
        }
        
        prevCta = limpiaPunto(prevCta);
        modeloPlanCuentas.getCodigoCta().setText(prevCta);
    }
    
    public void incrementaCtaPrevia(){
        String prevCta = modeloPlanCuentas.getCodigoCta().getText();
        String prefijo = null;
        
        prevCta = prevCta.replace("0", "");
        
        if (existeCod(prevCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString())>0){
            int posicion = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1);
            prevCta = prevCta.substring(posicion+1, prevCta.length());
            
            int ctaIncremeta = Integer.valueOf(prevCta);
            ctaIncremeta++;
            
            prevCta = modeloPlanCuentas.getCodigoCta().getText();
            prevCta = prevCta.substring(0, posicion+1);
            
            if (modeloPlanCuentas.getNivelPrefijo()!=modeloPlanCuentas.getCbNivelPrev1().getSelectedItem()){
                prefijo = String.valueOf(ctaIncremeta);
            }else{
                prefijo = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", ctaIncremeta);
            }
            
            prevCta = prevCta+prefijo+".";
            
            if (existeCod(prevCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString())==0){
                JOptionPane.showMessageDialog(null,"No puede incrementar la cta de Nivel "+modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString()+" porque la cuenta "+prevCta+" que precede no ha sido creada aun", "Notificación", JOptionPane.WARNING_MESSAGE);
                
                lMsg=true;
                modeloPlanCuentas.getCodigoCta().setText(ctaPrevia);

                String[] arregloNiveles2 = cargaCombos(Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1, true);
                modeloPlanCuentas.getCbNivelPrev1().setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles2));
        
                return;
            }
            
            modeloPlanCuentas.getCodigoCta().setText(prevCta);

            int nivel = (Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString()))-(Integer.valueOf(modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString()));
            String prefijo2 = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", Integer.valueOf(modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()));
            
            for (int i=0; i<nivel; i++){
                prevCta = prevCta+prefijo2+".";
            }
            
//****************************************************************************************************************
            prevCta = prevCta.replace("0", "");
        
            int nivel1 = (Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString()))-(Integer.valueOf(modeloPlanCuentas.getNivelPrefijo()))+1;
            int posicion1 = countOccurrences(prevCta, '.', Integer.valueOf(modeloPlanCuentas.getNivelPrefijo())-1);

            if (prefijo!=" "){
                prevCta = prevCta.substring(0, posicion1+1);
            
                if (posicion1==0){
                    prevCta = "";
                }
            
                String prefijo1 = String.format("%0"+modeloPlanCuentas.getLongPrefijo()+"d", Integer.valueOf(modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString()));
            
                for (int i=0; i<nivel1; i++){
                    prevCta = prevCta+prefijo1+".";
                }
            }
//****************************************************************************************************************
            
            prevCta = limpiaPunto(prevCta);
        
            modeloPlanCuentas.getCodigoCta().setText(prevCta);
            
            if (existeCod(prevCta, modeloPlanCuentas.getCbCtaPadre().getSelectedItem().toString(), modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())>0){
                incrementaCta();
            }
        }else{
            JOptionPane.showMessageDialog(null,"No puede incrementar la cta de Nivel "+modeloPlanCuentas.getCbNivelPrev1().getSelectedItem().toString()+" porque la cuenta "+prevCta+" que precede no ha sido creada aun", "Notificación", JOptionPane.WARNING_MESSAGE);

            lMsg=true;
            modeloPlanCuentas.getCodigoCta().setText(ctaPrevia);
            
            String[] arregloNiveles2 = cargaCombos(Integer.valueOf(modeloPlanCuentas.getCbCtaNivelCrea().getSelectedItem().toString())-1, true);
            modeloPlanCuentas.getCbNivelPrev1().setModel(new javax.swing.DefaultComboBoxModel(arregloNiveles2));
        }
    }
 
    public String limpiaPunto(String prevCta){
        String punto = prevCta.substring(prevCta.length()-1);
        
        if (punto.equals(".")){
            prevCta = prevCta.substring(0, prevCta.length()-1);
        }
        
        return prevCta;
    }
    
    public static int countOccurrences(String haystack, char needle, int nivel) { 
        int count = 0; 
        int posicion = 0; 
        
        for (int i=0; i < haystack.length(); i++) { 
            if (haystack.charAt(i) == needle) { 
                if (nivel!=count){
                    count++;
                    posicion = i;
                }
            } 
        } 
        
        return posicion; 
    }
}