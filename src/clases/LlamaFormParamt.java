/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import Vista.ConfigurarConexion;
import Pantallas.Documentos;
import static Pantallas.principal.escritorio;
import java.awt.Dimension;

/**
 *
 * @author luzmaria
 */
public class LlamaFormParamt {
    
    public void Form(String form, String TipForm, String paramet1, String paramet2){
        if (TipForm.trim().equals("Internal")){
            if (form.trim().equals("Documentos")){
                Documentos fact = new Documentos(paramet1, paramet2);
        
                //fact = new Documentos();
                Dimension desktopSize = escritorio.getSize();
                Dimension jInternalFrameSize = fact.getSize();
                fact.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        
                escritorio.add(fact);
                fact.show(); 
            }
        }
        
        if (TipForm.trim().equals("External")){
            if (form.trim().equals("ConfigurarConexion")){
                boolean lDatosConex;
                
                if (paramet1.trim().equals("1")){
                    lDatosConex=true;
                }else{
                    lDatosConex=false;
                }
                
                ConfigurarConexion config = new ConfigurarConexion(lDatosConex);
                config.show();
            }            
        }
    }
}

