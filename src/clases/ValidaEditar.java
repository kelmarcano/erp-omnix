package clases;

import clases.conexion;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JFrame;
//import pos.POSIntSubGrupo;

public class ValidaEditar extends conexion {
    
    public int ValidaEditar (String Sql, boolean elmin) throws ClassNotFoundException {
        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        int registros = 0;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        try{
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(sql);
            rs = this.consulta.executeQuery();
//            this.consulta= (com.mysql.jdbc.Statement) conn.createStatement();
//            rs = this.consulta.executeQuery(sql);
  
            while(rs.next()) {
                if (rs.getInt("REGISTROS") > 0){
                    if (elmin == false){
                        JOptionPane.showMessageDialog(null, "Este Numero de Código no Existe");
                    }
                    registros =rs.getInt("REGISTROS");
                }
                else{
                    registros = 0;
                }
            }
        }catch (SQLException e) {
            }
        return registros;
    }
}
