/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import Vista.Login;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author luzmaria
 */
public class ReadFileXml {
    SAXBuilder builder = new SAXBuilder();
    private String nombre = "";
    private Vector elementos = new Vector();
    
    public Vector ReadFileXml(File xmlFile, String OpcCarga, String Elemt) {
        try{
            elementos.clear();
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build(xmlFile);

            //Se obtiene la raiz 'omnixsolutionsTrl'
            Element rootNode = document.getRootElement();
System.out.println(xmlFile);
System.out.println(rootNode.getName());
System.out.println(Elemt);
            //Se obtiene la lista de hijos de la raiz 'omnixsolutionsTrl'
            List list = rootNode.getChildren(Elemt);
System.out.println("Hijos: "+list.size());

            //Se recorre la lista de hijos de 'tables'
            for ( int i = 0; i < list.size(); i++ ){
                //Se obtiene el elemento 'tabla'
                Element tabla = (Element) list.get(i);
 
                //Se obtiene el atributo 'nombre' que esta en el tag 'tabla'
                String nombreTabla = tabla.getAttributeValue("id");
System.out.println( tabla.getName());
//System.out.println("Valor id: "+nombreTabla);
 
                //Se obtiene la lista de hijos del tag 'tabla'
                List lista_campos = tabla.getChildren();

                //Se recorre la lista de campos
                for ( int j = 0; j < lista_campos.size(); j++ )
                {
                    //Se obtiene el elemento 'campo'
                    Element campo = (Element)lista_campos.get( j );
         
                    //Se obtienen los valores que estan entre los tags '<campo></campo>'
                    //Se obtiene el valor que esta entre los tags '<nombre></nombre>'                    
                    if (OpcCarga.equals("Combos")){
                        nombre = campo.getChildTextTrim("Idioma");

                        elementos.add(nombre);
                        System.out.println("Combos: "+nombre);
                    }
                    
                    if (OpcCarga.equals("Msg")){
                        //nombre = campo.getChildTextTrim("Message");
                        nombre = campo.getChildText("Message");

                        elementos.add(nombre);
                        System.out.println("Msg: "+nombre);
                    }
                    
                    if (OpcCarga.equals("Label")){
                        nombre = campo.getChildTextTrim("Componet");

                        elementos.add(nombre);
                        System.out.println("Label: "+nombre);
                    }
                    
                    if (OpcCarga.equals("Header")){
                        nombre = campo.getChildTextTrim("Header_Table");

                        elementos.add(nombre);
                        System.out.println("Label: "+nombre);
                    }
                    
                    if (OpcCarga.equals("Menu")){
                        nombre = campo.getChildTextTrim("Menu");

                        elementos.add(nombre);
                        System.out.println("Menu: "+nombre);
                    }
                    //System.out.println( "\t"+nombre);
                }
            }
        }catch ( IOException io ) {
            System.out.println( io.getMessage() );
        }catch ( JDOMException jdomex ) {
            System.out.println( jdomex.getMessage() );
        }

        return elementos;
    }
}
