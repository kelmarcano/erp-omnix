package clases;

import Modelos.VariablesGlobales;
import clases.conexion;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class SQLQuerys extends conexion{
    private static PreparedStatement ps = null;    
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public void SqlInsert(String Sql){
        try {
            creaConexion();
            consulta = this.conn.prepareStatement(Sql);
            consulta.executeUpdate();
        } catch (ClassNotFoundException e){
	  }
	   catch (SQLException ex){
               if (lBdExist==false){
                   
               }else{
                   JOptionPane.showMessageDialog(null, ex, "Error SQL", JOptionPane.ERROR_MESSAGE);
               }
               
               String Error = ex.toString();
               System.out.println(Error);
               // Verifica el error de Conexion en la IP y Puerto del Servidor
               if (Error.endsWith("from the server.")==true){
                   JOptionPane.showMessageDialog(null, "Acceso denegado , por favor verifique la direccion IP y Puerto del "+
                                                       "servidor al que desea realizar la Conexión...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
               }
               // Verifica el error de Conexion en el Usuario(s) de MySQL
               if (Error.endsWith("to database 'mysql'")==true){
                   JOptionPane.showMessageDialog(null, "Acceso denegado para el Usuario ''"+VarGlobales.getUserServer()+"'', por favor verifique si "+
                                                       "el usuario indicado existe...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
               }
               // Verifica el error de Conexion en la Clave del Usuario(s) de MySQL
               if (Error.endsWith("(using password: YES)")==true){
                   JOptionPane.showMessageDialog(null, "Acceso denegado para el Usuario ''"+VarGlobales.getUserServer()+"'', por favor verifique si es "+
                                                       "correcta la Contraseña o si el usuario posee una...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
               }
               
//****************************** PostGreSQL ******************************
               // Verifica el error de Conexion en la Clave del Usuario(s) de MySQL
               if (Error.endsWith("contraseña.")==true){
                   JOptionPane.showMessageDialog(null, "El servidor requiere autenticación basada en contraseña, para el Superusuario ''postgres'' "+
                                                       "pero no se ha provisto ninguna contraseña...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
               }
               
               if (Error.endsWith("conexiones TCP/IP.")==true){
                   JOptionPane.showMessageDialog(null, "Conexión rechazada. Verifique que el nombre del Host y el puerto sean correctos "+
                                                       "y que postmaster este aceptando conexiones TCP/IP...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
               }
               
               if (Error.endsWith("usuario �postgres�")==true){
                   JOptionPane.showMessageDialog(null, "la autentificación del password fallo para el usuario ''postgres'' ", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
               }
	  }
    }
    
    public void SqlUpdate(String Sql){
        try {
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(Sql);
            this.consulta.executeUpdate();
        } catch (ClassNotFoundException e){
	  }
	   catch (SQLException ex){
               JOptionPane.showMessageDialog(null, "Error SQL Exception");
	  }
    }
    
    public void SqlDelete(String Sql){
        try {
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(Sql);
            this.consulta.executeUpdate();
        } catch (ClassNotFoundException e){
	  }
	   catch (SQLException ex){
               JOptionPane.showMessageDialog(null, "Error al Eliminar Registro, debe tener una llave foranea asociada");
	  }
    }
    
    public void SqlInsertImagen(String Sql,String ruta){
        FileInputStream fis;

        try {
            //conn.setAutoCommit(false);
            this.creaConexion();
            
            File file = new File(ruta);
            fis = new FileInputStream(file);
            
            this.consulta = this.conn.prepareStatement(Sql);

            //this.consulta.setBinaryStream(1,fis,fis.available());
            this.consulta.setBlob(1, fis);
            this.consulta.executeUpdate();

            //conexion.commit();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}