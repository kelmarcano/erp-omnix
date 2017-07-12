package Controlador;

import Modelos.Modelo_Login;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class Controlador_Login {
    private Boolean result_acceso;
    Modelo_Login ModeloLogin = new Modelo_Login();
    
    public Boolean Login(String User, String Pass, String Empresa){
        ModeloLogin.setUsuario(User);
        ModeloLogin.setClave(Pass);
        ModeloLogin.setEmpresa(Empresa);
        
        result_acceso = ModeloLogin.getResultLogin();
        
        return result_acceso;
    }
    
    public DefaultComboBoxModel DatosCombo(String Sql){
        ModeloLogin.setSqlEmpresa(Sql);
        DefaultComboBoxModel mdl = ModeloLogin.getEmpresa();
        
        return mdl;
    }
    
    public Object[][] VariablesP(String Sql){
        ModeloLogin.setSqlVariables(Sql);
        Object element[][] = ModeloLogin.getCargaVariables();
        
        return element;
    }
    
    public ImageIcon[] BanderasComboIdiomas(){
        ImageIcon[] Banderas = ModeloLogin.getBanderas();
        
        return Banderas;
    }
}
