package clases;

import Modelos.VariablesGlobales;
import clases.conexion;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.*;

public class CodigoConsecutivo extends conexion{
    private String Sql="";
    clases.conexion conet = new conexion();
    
    public String CodigoConsecutivo(String Campo, String Tabla, int Longitud, String Filtro){
        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        String registros = null;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        if (VarGlobales.getManejador().equals("MySQL")){
            Sql = "SELECT CONCAT(REPEAT('0',"+Longitud+"-LENGTH(CONVERT(MAX("+Campo+")+1,CHAR("+Longitud+")))),CONVERT(MAX("+Campo+")+1,CHAR("+Longitud+"))) AS CODIGO FROM "+Tabla+" "+Filtro;
        }else if (VarGlobales.getManejador().equals("PostGreSQL")){
            Sql = "SELECT CONCAT(REPEAT('0',"+Longitud+"-LENGTH((CAST(MAX("+Campo+") AS NUMERIC)+1)::TEXT)),(CAST(MAX("+Campo+") AS NUMERIC)+1)::TEXT) AS CODIGO FROM "+Tabla+" "+Filtro;
        }
        
        String sql=(Sql);
        System.out.println(sql);

        try{
            rs = conet.consultar(sql);

            if (rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()) {
                    if (rs.getString("CODIGO")!=null){
                        registros = rs.getString("CODIGO").trim();
                    }else{
                        registros=null;
                    }
                }
            }else{
                registros=null;
            }
        }catch (ClassNotFoundException |SQLException e) {
        }

        return registros;
    }
}