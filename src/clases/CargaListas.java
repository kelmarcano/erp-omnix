/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import clases.conexion;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JFrame;
import java.util.Vector;

/**
 *
 * @author riky1_000
 */
public class CargaListas extends conexion{
    
    //public static Vector Elementos(String Sql){
    public Vector Elementos(String Sql){
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        int registros = 0;
    
        Vector elementos = new Vector();
        
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        try{
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(sql);
            rs = this.consulta.executeQuery();
//            this.consulta= (com.mysql.jdbc.Statement) this.conn.createStatement();
//            rs = this.consulta.executeQuery(sql);

            while(rs.next()) {
                elementos.add(rs.getString("DATO1"));
            }
        }catch (ClassNotFoundException |SQLException e) {
            }
        
        return elementos;
    }
}
