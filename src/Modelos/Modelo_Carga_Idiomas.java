package Modelos;

import clases.ReadFileXml;
import java.io.File;
import java.util.Vector;

public class Modelo_Carga_Idiomas {
    private File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
    private File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+"Idiomas_Trl.xml");
    private Vector listaidiomas, formidioma, msgidioma, tablaidioma;
    private String Formulario, Componete, Idioma;
    
    public void setFormulario(String form){
        Formulario = form;
    }
    
    public void setComponente(String componen){
        Componete = componen;
    }
    
    public void setIdioma(String idioma){
        Idioma = idioma;
    }
    
    public Vector getListaIdioma(){
        ReadFileXml xml = new ReadFileXml();
        listaidiomas = xml.ReadFileXml(xmlFile, "Combos", "row");
        
        return listaidiomas;
    }
    
    public Vector getFormIdioma(){
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");
        ReadFileXml xml = new ReadFileXml();
        formidioma = xml.ReadFileXml(xmlFile, Componete, Formulario);
        
        return formidioma;
    }
    
    public Vector getMsgIdioma(){
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml = new ReadFileXml();
        msgidioma = xml.ReadFileXml(xmlFile, Componete, Formulario);
        
        return msgidioma;
    }
    
    public Vector getTablasIdioma(){
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Header_Table.xml");
        ReadFileXml xml = new ReadFileXml();
        tablaidioma = xml.ReadFileXml(xmlFile, Componete, Formulario);
        
        return tablaidioma;
    }
}