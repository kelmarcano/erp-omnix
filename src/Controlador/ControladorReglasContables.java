/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import clases.CargaTablas;
import clases.ReadFileXml;
import clases.conexion;
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Kel
 */
public class ControladorReglasContables {
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
    
    public ControladorReglasContables(){
        
    }
    
     public void cargaTabla(JTable tabla1, JTable tabla2, String clase){
        xmlFile(clase);
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        String SQL = "SELECT REG_DESCRI, REG_DEBE, REG_HABER FROM DNREGLAS WHERE REG_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String[] columnas = {(String) header_table.get(0),(String) header_table.get(1),(String) header_table.get(2)};
        //-------------------------------------------------------------------

        cargatabla.cargatablas(tabla2,SQL,columnas);     
        
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
    
    
}
