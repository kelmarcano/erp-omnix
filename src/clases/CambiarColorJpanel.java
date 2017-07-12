package clases;

import static Pantallas.principal.escritorio;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JPanel;


public class CambiarColorJpanel {
    JColorChooser jcc;
    Object x;
    
    private JPanel panelForm, panelFormPreview, panelTextPreview;
    
    public void setPanel(JPanel panelForm, JPanel panelFormPreview, JPanel panelTextPreview){
        this.panelForm = panelForm;
        this.panelFormPreview = panelFormPreview;
        this.panelTextPreview = panelTextPreview;
    }
    
    public void seleccionarColorForm(){
        if(seleccionarColor() != null){
            //escritorio.setBackground(c);
            panelForm.setBackground(seleccionarColor());
            panelFormPreview.setBackground(seleccionarColor());
        }  
    }
    
    public void seleccionarColorText(){
        if(seleccionarColor() != null){
            panelTextPreview.setBackground(seleccionarColor());
        }  
    }
    
    public Color seleccionarColor(){
        Color c = JColorChooser.showDialog(null, "Seleccion color" , Color.white);
        
        String[] prueba = c.toString().trim().split(",");
        int colorR = Integer.valueOf(prueba[0].subSequence(17, prueba[0].length()).toString());
        int colorG = Integer.valueOf(prueba[1].subSequence(2, prueba[1].length()).toString());
        int colorB = Integer.valueOf(prueba[2].subSequence(2, prueba[2].length()-1).toString());
        
        String colorHex = toHex(colorR, colorG, colorB);
        System.out.println(colorHex);
        return c;
    }
    
    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));

        while (builder.length() < 2) {
            builder.append("0");
        }

        return builder.toString().toUpperCase();
    }
}
