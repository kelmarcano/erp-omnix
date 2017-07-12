package clases;

import Modelos.VariablesGlobales;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.Vector;

public class CargaComboBoxs {
    static VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public static Vector Elementos(String Sql){
    //public Vector Elementos(String Sql){
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        int registros = 0;
    
        Vector elementos = new Vector();
        
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        try{
            switch (VarGlobales.getManejador()) {
                case "PostGreSQL":
                    Class.forName("org.postgresql.Driver");
                    Conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    //Conn = DriverManager.getConnection("jdbc:postgresql://"+ip+"/"+basedatos, user, passw);
                    consulta = Conn.createStatement();
                    rs = consulta.executeQuery(sql);
                    break;
                case "MySQL":
                    Class.forName("com.mysql.jdbc.Driver");
                    Conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos()+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    consulta = Conn.createStatement();
                    rs = consulta.executeQuery(sql);
                    break;
            }
            
            elementos.add("");
            
            while(rs.next()) {
                elementos.add(rs.getString("DATO1").trim());
            }
        }catch (ClassNotFoundException |SQLException e) {
            }
        
        return elementos;
    }
}