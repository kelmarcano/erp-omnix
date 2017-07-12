/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import static Vista.Login.elementos;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class jComboRenderer  extends JLabel implements ListCellRenderer{
    private ImageIcon[] items;
    
    /** Constrcutor de clase */
    public jComboRenderer(ImageIcon[] items ){
        setOpaque(true);
        this.items = items;            
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        int selectedIndex = ((Integer)value).intValue();

        if (isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        ImageIcon icon = this.items[selectedIndex];
        setIcon( icon );

        File f = new File( items[selectedIndex].toString());
        setText((String) elementos.get(selectedIndex));

        return this;
    }
}