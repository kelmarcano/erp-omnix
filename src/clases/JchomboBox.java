/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.awt.Dimension;
import javax.swing.JComboBox;

/**
 *
 * @author luzmaria
 */
public class JchomboBox extends JComboBox {
    
    public JchomboBox(int num_items){
        Dimension d = new Dimension(206,26);
        this.setSize(d);
        this.setPreferredSize(d);
        
        for (int i=0; i<num_items; i++){
            this.addItem(i);
        }
        this.setVisible(true);
    }
}
