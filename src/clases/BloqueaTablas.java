/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author riky1_000
 */
public class BloqueaTablas {

    public void bloquea(JTable tabla2, String SQL, String[] column){
        class ModeloDeTabla extends AbstractTableModel {
            // Datos iniciales   
            private String columns[][] ={{"Datos"}};
            private String rows[][] = {{"0"},{"2"},{"4"},{"8"}};
  
            /** Metodo definido como abstract en AbstractTableModel
            * @return el numero de columnas
            */
            public int getColumnCount() {
                return columns.length;
            }
   
            /** Metodo definido como abstract en AbstractTableModel
            * @return el numero de filas
            */
            public int getRowCount() {
                return rows.length;
            }
   
            /** Metodo definido como abstract en AbstractTableModel
            * @return el contenido de una celda en la celda especificada
            */
            public Object getValueAt(int row, int column) {
                return rows[row][column];
            }

            /** Sobreescribimos el metodo setValueAt de AbstractTableModel
            * para que se pueda modificar el valor en una celda
            */
            public void setValueAt(Object aValue, int row, int column) {
                rows[row][column] = aValue.toString();   
                // Notificamos a los posibles oyentes que los datos en la tabla han cambiado      
                fireTableDataChanged();
            }
   
            /** Sobreescribimos el metodo isCellEditable de AbstractTableModel
            * para indicar que todas las celdas son editables
            */
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            /** Metodo nuevo que ofrecemos para añadir una fila a la tabla
            * @param el valor a introducir
            */
            public void nuevaFila(double dx){
                String[][] datos = new String[getRowCount()+1][getColumnCount()];
                for (int i=0;i<getRowCount();i++)
                    datos[i] = rows[i];  
                    datos[getRowCount()][0] = dx+"";
                    
                    rows = datos;
   	
                    // Notificamos que los datos en la tabla han cambiado
                    fireTableDataChanged();
            }
        }
    }
    
/*
    public void bloquea(JTable tabla2, String SQL, String[] column){
        String [] columnas = column;
        //DefaultTableModel dtm = new DefaultTableModel(null,columnas);
        JOptionPane.showMessageDialog(null, "entro");
        
        AbstractTableModel dtm2 = new AbstractTableModel() {

            @Override
            public int getRowCount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getColumnCount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            public boolean isCellEditable(int row, int column) {
                JOptionPane.showMessageDialog(null, "entro");
                return false;
            }
        };
    }
*/
}
