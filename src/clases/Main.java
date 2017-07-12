/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.util.Properties;
import org.jvnet.substance.SubstanceLookAndFeel;
import javax.swing.*;

/**
 *
 * @author Kelvin Marcano
 */
public class Main {
    public static String idioma="";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (Exception e) {
            e.printStackTrace();
	}

        System.out.println("Nombre del Sistema Operativo: "+System.getProperty("os.name"));
        System.out.println("Version del Sistema Operativo: "+System.getProperty("os.version"));
        Properties p = System.getProperties();
        System.out.println("Idioma del Sistema Operativo: "+p.get("user.language"));
        System.out.println("Pais del Sistema Operativo: "+p.get("user.country"));
        
        if (p.get("user.language").equals("es")){
            idioma="Español ("+p.get("user.language")+"_"+p.get("user.country")+")";
            //idioma="English (UK)";
        }
        System.out.println("Idioma a cargar: "+"Español ("+p.get("user.language")+"_"+p.get("user.country")+")");
        
        System.out.println("Version de Java #: "+System.getProperty("java.version"));
        System.out.println("Directorio de Instalacion de Java: "+System.getProperty("java.home"));
        System.out.println("Nombre del Distribuidor de Java: "+System.getProperty("java.vendor"));
        System.out.println("Pagina Web del Distribuidor de Java: "+System.getProperty("java.vendor.url"));
        
        new ProgressBar().animar();   
         //SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");  
    }   
}