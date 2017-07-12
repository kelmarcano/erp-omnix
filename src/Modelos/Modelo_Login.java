package Modelos;

import clases.CargaComboBoxs;
import clases.SQLSelect;
import clases.conexion;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class Modelo_Login {
    private String Usuario, Clave, Empresa;
    private String CosultaEmpresa, CosultaVariables;
    private Vector Empresas;
    private Boolean result_acceso;
    
    public void setUsuario(String usuario){
        Usuario = usuario;
    }
    
    public void setClave(String clave){
        Clave = clave;
    }
    
    public void setEmpresa(String empresa){
        Empresa = empresa;
    }
    
    public void setSqlEmpresa(String sql){
        CosultaEmpresa = sql;
    }
    
    public void setSqlVariables(String sql){
        CosultaVariables = sql;
    }
    
    public DefaultComboBoxModel getEmpresa(){
        String sql = CosultaEmpresa;
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBoxs.Elementos(sql));    
        
        return mdl;
    }
    
    public Boolean getResultLogin(){
        conexion acceso = new conexion();
        result_acceso = acceso.access(Usuario, Clave, Empresa);

        return result_acceso;
    }
    
    public Object[][] getCargaVariables(){
        SQLSelect elemen = new SQLSelect();
        Object element[][] = elemen.SQLSelect(CosultaVariables);
        
        return element;
    }
    
    public ImageIcon[] getBanderas(){
        ImageIcon[] Banderas = { new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/argentina_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/brasil_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/chile_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/china_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/colombia_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/japon_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/mexico_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/united_states_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/venezuela_flag.png")};
                     
        return Banderas;
    }
}
