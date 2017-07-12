/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import clases.conexion;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.*;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author riky1_000
 */
public class CargaTablas extends conexion {
    private int i;
    public String [] columnas;
    public String [] filas;
    
    public void cargatablas(JTable tabla, String SQL, String[] column) {
        columnas = column;
        
        JTable tblEjemplo = new JTable();
        JScrollPane scpEjemplo= new JScrollPane();
 
        //Llenamos el modelo
/*
//        DefaultTableModel dtmEjemplo = new DefaultTableModel(getFilas(),
//                                                             getColumnas());
        DefaultTableModel dtm = new DefaultTableModel(null,columnas);
 
        tblEjemplo=new JTable(dtm){
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }}; //return false: Desabilitar edición de celdas.
*/        
        DefaultTableModel dtm = new DefaultTableModel(null,columnas);

        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;   
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"        
        String sql=(SQL);
        System.out.println(sql);
        
        try{
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(sql);
            rs = this.consulta.executeQuery();
//            this.consulta= (com.mysql.jdbc.Statement) this.conn.createStatement();
//            rs = this.consulta.executeQuery(sql);
            
            while( rs.next()) {
                //JOptionPane.showMessageDialog(null,rs.getMetaData().getColumnCount()); // Indica la cantidad de Columnas
                //    JOptionPane.showMessageDialog(null,rs.getMetaData().getColumnName(i+1)); // Indica el nombre del Campo
                
                //String [] filas={rs.getString("LIS_CODIGO"),rs.getString("LIS_DESCRIPCION")};

                if (rs.getMetaData().getColumnCount()==1){
                    String [] filas={rs.getString(1)};
                    dtm.addRow(filas);
                }
                if (rs.getMetaData().getColumnCount()==2){
                    String [] filas={rs.getString(1),rs.getString(2)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==3){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==4){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==5){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==6){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==7){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==8){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==9){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==10){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)};
                    dtm.addRow(filas);                    
                }
            }            
        }
           catch (ClassNotFoundException e){
	   }
	   catch (SQLException ex){
               JOptionPane.showMessageDialog(null, "Error SQL Exception");
	   }

        tabla.setModel(dtm);
    }
}