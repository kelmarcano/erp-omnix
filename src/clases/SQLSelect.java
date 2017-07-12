package clases;

import clases.conexion;
import com.sun.org.apache.regexp.internal.recompile;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.*;
import java.util.Vector;
import javax.swing.JFrame;

public class SQLSelect extends conexion{

    public  Object[][] SQLSelect (String Sql) {    
        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        Object elementos[][]=null;
        
        try{
            this.creaConexion();
            consulta= conn.createStatement();
            rs = consulta.executeQuery(sql);

//            rs.last();
    
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numCols = rsmd.getColumnCount();
            int numFils =rs.getRow();
            if (numFils==0){
                numFils=1;
            }

            elementos=new Object[numFils][numCols];
            int j = 0;

//            rs.beforeFirst();

            while(rs.next()) { 
                for (int i=0;i<numCols;i++){
                    //JOptionPane.showMessageDialog(null, rs.getObject(i+1));
                    elementos[j][i]=rs.getObject(i+1);
                }
                j++;
            }
        }catch (ClassNotFoundException |SQLException e) {
            //JOptionPane.showMessageDialog(null, e);
        }
        return elementos;
    }
}
