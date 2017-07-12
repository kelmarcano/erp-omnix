/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author riky1_000
 */
public class CreaValidaRif {
    
    public Vector DatosFiscales(String sRif) throws MalformedURLException, IOException{
        boolean bResultado = false;
        int iFactor = 0;
        String temp = ""; String sRif2 = "";
        
        Vector datosfiscales = new Vector();
        
        sRif = sRif.replace("-", "");

        if (sRif.length() < 10)
            sRif = sRif.substring(0, 1).toUpperCase();
            sRif2 = sRif.substring(1, 9);

        int y=9-sRif2.trim().length();
        for(int i=0;i<y;i++){
            temp=temp+"0";
        }

        sRif2 = sRif+temp;
        sRif2 = sRif2.replace(" ", "");
        //sRif2 = sRif2.toUpperCase().substring(0, 1) + sRif2;
        String sPrimerCaracter = sRif2.substring(0, 1).toUpperCase();
            
        switch (sPrimerCaracter)
        {
            case "V": iFactor = 1; break;
            case "E": iFactor = 2; break;
            case "J": iFactor = 3; break;
            case "P": iFactor = 4; break;
            case "G": iFactor = 5; break;
        }
        
        if (iFactor==0){
            JOptionPane.showMessageDialog(null,"El RIF ingresado es invalido...!", 
                                               "Falla en Consulta de RIF!", 
                                               JOptionPane.WARNING_MESSAGE);
            datosfiscales.add("El RIF ingresado es invalido...!");
        
            return datosfiscales;
            //return null;
        }
            
        if (iFactor > 0){
            int suma = ((Integer.parseInt(sRif2.substring(1, 2))) * 3)
                     + ((Integer.parseInt(sRif2.substring(2, 3))) * 2)
                     + ((Integer.parseInt(sRif2.substring(3, 4))) * 7)
                     + ((Integer.parseInt(sRif2.substring(4, 5))) * 6)
                     + ((Integer.parseInt(sRif2.substring(5, 6))) * 5)
                     + ((Integer.parseInt(sRif2.substring(6, 7))) * 4)
                     + ((Integer.parseInt(sRif2.substring(7, 8))) * 3)
                     + ((Integer.parseInt(sRif2.substring(8, 9))) * 2)
                     + (iFactor * 4);

            float dividendo = suma / 11;
            int DividendoEntero = (int)dividendo;
            int resto = 11 - (suma - DividendoEntero * 11);

            if (resto >= 10 || resto < 1){
                resto = 0;
            }
                
            sRif2 = sRif2.substring(0, 9)+resto;
        }

        URL url = null;
        try {
            url = new URL("http://contribuyente.seniat.gob.ve/getContribuyente/getrif?rif="+sRif2);
System.out.println("http://contribuyente.seniat.gob.ve/getContribuyente/getrif?rif="+sRif2);
            BufferedReader bs = new BufferedReader(new InputStreamReader(url.openStream()));

            String txt; String fuente_html = null;
            String Nombre = null; String AgenteRetencionIVA = null;
            String ContribuyenteIVA = null; String Tasa = null;
        
            while ((txt = bs.readLine()) != null)
                fuente_html = txt;
            
                Nombre = fuente_html.substring(118, fuente_html.length());
                int inicioN = Nombre.indexOf("<");
                Nombre = Nombre.substring(0, inicioN);
            
                AgenteRetencionIVA = fuente_html.substring(118+inicioN+37, fuente_html.length());
                int inicioR = AgenteRetencionIVA.indexOf("<");
                AgenteRetencionIVA = AgenteRetencionIVA.substring(0, inicioR);
            
                ContribuyenteIVA = fuente_html.substring(118+inicioN+37+inicioR+47, fuente_html.length());
                int inicioC = ContribuyenteIVA.indexOf("<");
                ContribuyenteIVA = ContribuyenteIVA.substring(0, inicioC);
            
                Tasa = fuente_html.substring(118+inicioN+37+inicioR+47+inicioC+33, fuente_html.length());
                int inicioT = Tasa.indexOf("<");
                Tasa = Tasa.substring(0, inicioT);
        
                ContribuyenteIVA=ContribuyenteIVA.replace("I", "i");
                ContribuyenteIVA=ContribuyenteIVA.replace("O", "o");

                AgenteRetencionIVA=AgenteRetencionIVA.replace("I", "i");
                AgenteRetencionIVA=AgenteRetencionIVA.replace("O", "o");

                datosfiscales.add(sRif2);
                datosfiscales.add(Nombre);
                datosfiscales.add(ContribuyenteIVA);
                datosfiscales.add(AgenteRetencionIVA);
                datosfiscales.add(Tasa);
        }catch (Exception e){
            String Error = e.toString();
            
            if (Error.endsWith("Connection reset")==true){
                JOptionPane.showMessageDialog(null,"Problema en la Conexion a la Pagina de Consulta del RIF \n"+
                                                   "\n"+url,"Falla en Consulta de RIF!",JOptionPane.WARNING_MESSAGE);
                //JOptionPane.showMessageDialog(null,e,"Falla en Consulta de RIF!",JOptionPane.WARNING_MESSAGE);
                datosfiscales.add(sRif2);
            }
            System.out.println(e);
        }
        
        return datosfiscales;
    }    
}