/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import clases.conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//import ceval.Maestros;

/**
 *
 * @author riky1_000
 */
public class GetImagen extends conexion {
    ResultSet rsFotos;
    conexion conn;
    private Image image;
    
//    public void GetImagen(String Sql) throws ClassNotFoundException, SQLException, IOException{
    public Image GetImagen(String Sql) throws ClassNotFoundException, SQLException, IOException{
        try {
            String sentenciaSQL = Sql;
            
            rsFotos = consultar(sentenciaSQL);
            rsFotos.last();

            ByteArrayOutputStream Ouput = new ByteArrayOutputStream();
            InputStream datos =  rsFotos.getBinaryStream(1);
            int temp=datos.read();
        
            while (temp>=0){
                Ouput.write((char)temp);
                temp=datos.read();
            }
            
            //Image image = Toolkit.getDefaultToolkit().createImage(Ouput.toByteArray());
            image = Toolkit.getDefaultToolkit().createImage(Ouput.toByteArray());

//            if(image != null){
//                POSIntMaestros.jLabMae_Foto.setText("");
//                ImageIcon icon = new ImageIcon(image.getScaledInstance(POSIntMaestros.jLabMae_Foto.getWidth(), POSIntMaestros.jLabMae_Foto.getHeight(), Image.SCALE_DEFAULT));
//                POSIntMaestros.jLabMae_Foto.setIcon(new ImageIcon(image));
//            }
        } catch (Exception e) {
        }
        return image;
    }
}
