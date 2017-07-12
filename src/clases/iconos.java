package clases;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.net.*;
import javax.swing.JButton;

public class iconos {
    
    public int iconos (JButton botn, String direccion) {
        
        String path = direccion;
        URL url = this.getClass().getResource(path);
        
        ImageIcon fot = new ImageIcon(url);
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(botn.getWidth(), botn.getHeight(), Image.SCALE_DEFAULT));
        botn.setIcon(icono);
        this.repaint();
        return 0;
 
    }

    private void repaint() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
