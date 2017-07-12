/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import Controlador.ControladorEmpresa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RaaG
 */
public class ColorApp {
    conexion conet = new conexion();
    private ResultSet rs;
    
    //public static String colorForm="";
    //public static String colorText="";
    public static String colorForm="#FFFFFF";
    public static String colorText="#0972ba";
    

    public void ColorApp(){
        try {
            String SQL = "SELECT * FROM dnpersonaliza_app";
            
            rs = conet.consultar(SQL);
            
            if (rs.getRow()>0 || String.valueOf(rs.getRow())!=null){
                colorForm = rs.getString("CONF_COLOR_FORM").toString();
                colorText = rs.getString("CONF_COLOR_TEXT").toString();
            }else{
                colorForm = "#FFFFFF";
                colorText = "#0972ba";
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ColorApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ColorApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
