package Controlador;

import Modelos.Modelo_Carga_Idiomas;
import java.util.Vector;

public class Controlador_Carga_Idiomas {
    Vector ListaIdioma, FormIdioma, MsgIdioma, TablaIdioma;
    Modelo_Carga_Idiomas ModeloIdiomas = new Modelo_Carga_Idiomas();
    
    public Vector ListaIdioma(){
        ListaIdioma = ModeloIdiomas.getListaIdioma();
        
        return ListaIdioma;
    }
    
    public Vector FormIdioma(String form, String componente, String idioma){
        ModeloIdiomas.setFormulario(form);
        ModeloIdiomas.setComponente(componente);
        ModeloIdiomas.setIdioma(idioma);
        
        FormIdioma = ModeloIdiomas.getFormIdioma();
        
        return FormIdioma;
    }
    
    public Vector MsgIdioma(String form, String componente, String idioma){
        ModeloIdiomas.setFormulario(form);
        ModeloIdiomas.setComponente(componente);
        ModeloIdiomas.setIdioma(idioma);
        
        MsgIdioma = ModeloIdiomas.getMsgIdioma();
        
        return MsgIdioma;
    }
    
    public Vector TablaIdioma(String form, String componente, String idioma){
        ModeloIdiomas.setFormulario(form);
        ModeloIdiomas.setComponente(componente);
        ModeloIdiomas.setIdioma(idioma);
        
        TablaIdioma = ModeloIdiomas.getMsgIdioma();
        
        return TablaIdioma;
    }
}