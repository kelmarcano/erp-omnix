package clases;

import Modelos.VariablesGlobales;
import Pantallas.principal;
import Vista.SeleccionApps;
import static clases.Main.idioma;
import java.awt.*;
import java.awt.SplashScreen;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class ProgressBar {
    //public static String ip=null, user=null, passw=null, basedatos=null, manejador=null;
    VariablesGlobales VarGlobal = VariablesGlobales.getDatosGlobales();
    
    public ResultSet rs;
    private conexion conet = new conexion();
    public static boolean Conexion;
    final SplashScreen splash ;
    //texto que se muestra a medida que se va cargando el progressbar
    final String[] texto = {"Imagenes" ,"Configuraciones", "Librerias",
                            "Formularios","Base de Datos","Iconos","Propiedades",
                            "Conexiones",""};
    private File carpeta, archivo, archivo_temp;
    
    private File file_exe;
    private Boolean Aplicacion;

    public ProgressBar() {
         splash = SplashScreen.getSplashScreen();
         VarGlobal.setPos(false);
         VarGlobal.setErp(false);
         VarGlobal.setHcm(false);
         VarGlobal.setHcs(false);
         VarGlobal.setFuerzadVentas(false);
         VarGlobal.setEcommers(false);
         VarGlobal.setGestoProyect(false);
    }

    public void animar(){
        if (splash != null){
            Graphics2D g = splash.createGraphics();
            
            for(int i=1; i<texto.length; i++){
                //se pinta texto del array
                g.setColor( new Color(4,52,101));//color de fondo
	        g.fillRect(203, 328,280,12);//para tapar texto anterior
                g.setColor(Color.white);//color de texto	        
                g.drawString("Cargando "+texto[i-1]+"...", 203, 338);                
                g.setColor(Color.green);//color de barra de progeso
                g.fillRect(204, 308,(i*307/texto.length), 12);//la barra de progreso
                //se pinta una linea segmentada encima de la barra verde
                float dash1[] = {2.0f};
                BasicStroke dashed = new BasicStroke(9.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,5.0f, dash1, 0.0f);
                g.setStroke(dashed);
                g.setColor(Color.GREEN);//color de barra de progeso
                g.setColor( new Color(4,52,101));
                g.drawLine(205,314, 510, 314);                
                //se actualiza todo
                
                splash.update();
                
		try {
                    Thread.sleep(600);
		} catch(InterruptedException e) { }
            }
            
	    splash.close();
	}
        //una vez terminada la animación se muestra la aplicación principal
        carpeta = new File(System.getProperty("user.dir")+"\\"+"Configuracion");
        carpeta.mkdirs();
        archivo = new File(carpeta.getAbsolutePath()+"\\"+"conf_conexion.txt");
        archivo_temp = new File(carpeta.getAbsolutePath()+"\\"+"temp");
        
//********** Codigo para Encriptar el Archivo SQL de la Base de Datos **********
//        File ArchivoSQL = new File(carpeta.getAbsolutePath()+"\\"+"BD_MySQL - Original1.sql");
//        File Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\BD-MySQL.sql");
//        try {
//            Encrip_Desencrip.encrypt(ArchivoSQL, Archivo_SQL, 12345);
//        } catch (IOException ex) {
//            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ArchivoSQL.delete();
        
//        File ArchivoSQL1 = new File(carpeta.getAbsolutePath()+"\\"+"Datos_Iniciales1.sql");
//        File Archivo_SQL1 = new File(carpeta.getAbsolutePath()+"\\Datos_Iniciales.sql");
//        try {
//            Encrip_Desencrip.encrypt(ArchivoSQL1, Archivo_SQL1, 12345);
//        } catch (IOException ex) {
//            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ArchivoSQL1.delete();
//******************************************************************************
                
        String txtTexto="";
        try { 
            txtTexto = Encrip_Desencrip.readEncrypted(archivo, 12345);
            System.out.println(txtTexto);
            
            //Creo el archivo desencriptado temporal
            if (archivo_temp.createNewFile()){    
                FileWriter Arc = new FileWriter(archivo_temp,true);
                BufferedWriter escribir = new BufferedWriter(Arc);

                escribir.write(txtTexto);

                escribir.close();
            }
            
            //Leo el archivo desencriptado temporal
            BufferedReader bf = new BufferedReader(new FileReader(archivo_temp));
            String Read;
            int linea=0;
            
            while((Read = bf.readLine()) != null){
                linea++;
                    
                if (linea==1){
//                    ip = Read.trim();
                    VarGlobal.setIpServer(Read.trim());
                }else if (linea==2){
//                    user = Read.trim();
                    VarGlobal.setUserServer(Read.trim());
                }else if (linea==3){
//                    passw = Read.trim();
                    VarGlobal.setPasswServer(Read.trim());
                }else if (linea==4){
//                    basedatos = Read.trim();
                    VarGlobal.setBaseDatos(Read.trim());
                }else if (linea==5){
//                    manejador = Read.trim();
                    VarGlobal.setManejador(Read.trim());
                }
            }
            
            //ColorApp color = new ColorApp();
            //color.ColorApp();
        
            //Cierro y elimino el archivo desencriptado temporar
            bf.close();
            archivo_temp.delete();
            idioma="";
            
            String[] nombre = new String[7];
            nombre[0] = "C:\\OmnixSolutions\\ERP\\OmnixERP.exe";
            nombre[1] = "C:\\OmnixSolutions\\POS\\OmnixPOS.exe";
            nombre[2] = "";
            nombre[3] = "";
            nombre[4] = "";
            nombre[5] = "";
            nombre[6] = "";
            int NumApp = 0;
            
            for (int i=0; i<nombre.length; i++){
                System.out.println(nombre[i]);
                file_exe = new File(nombre[i]);
                Aplicacion = file_exe.isFile();
                
                if (Aplicacion==true){
                    NumApp++;
                    System.out.println(Aplicacion);
                    
                    switch(i){
                        case 0:
                            VarGlobal.setErp(Aplicacion);
                            break;
                        case 1:
                            VarGlobal.setPos(Aplicacion);
                            break;
                        case 2:
                            VarGlobal.setHcm(Aplicacion);
                            break;
                        case 3:
                            VarGlobal.setHcs(Aplicacion);
                            break;
                        case 4:
                            VarGlobal.setFuerzadVentas(Aplicacion);
                            break;
                        case 5:
                            VarGlobal.setEcommers(Aplicacion);
                            break;
                        case 6:
                            VarGlobal.setGestoProyect(Aplicacion);
                            break;
                    }
                }
            }
            
            Vista.Login.Idioma="Español (es_VE)";
            //VarGlobal.setCodEmpresa("000001");
            //VarGlobal.setIdUser("1");
            //VarGlobal.setUserName("OMNIX");
            //VarGlobal.setGrupPermiso("1028");
            
            if(VarGlobal.getUserName()!=null){
                principal prin = new principal();
                prin.show();
                prin.setExtendedState(principal.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
                prin.setVisible(true);
            }else{
            
            if (NumApp==1){
                new Vista.Login().setVisible(true);
            }else{
                SeleccionApps Apps = new SeleccionApps();
                Apps.show();
                Apps.setExtendedState(SeleccionApps.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
                Apps.setVisible(true);
            }
            }
        } catch (IOException ioe) {
            String err = "No se ha Configurado la Conexion al Sistema";
            JOptionPane.showMessageDialog(null, err, "Error De Conexion", JOptionPane.ERROR_MESSAGE);
                
            new Vista.ConfigurarConexion().setVisible(true);
       }   
    }
}