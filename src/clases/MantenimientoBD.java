package clases;

import Modelos.VariablesGlobales;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import clases.conexion;
import static Pantallas.principal.escritorio;

/**
 *
 * @author sicvispacemparabellu
 */
public class MantenimientoBD {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
        
    public void respaldar(){
        JFileChooser selectorDeArchivos = new JFileChooser();        
        int showSaveDialog = selectorDeArchivos.showSaveDialog(escritorio);
        if(showSaveDialog == JFileChooser.APPROVE_OPTION)
        {
            File archivo = selectorDeArchivos.getSelectedFile();
            
            /*NOTE: Used to create a cmd command*/
            String pasword = (VarGlobales.getPasswServer()== null || VarGlobales.getPasswServer().isEmpty())?"":" -p " + VarGlobales.getPasswServer();
            String datosComando = VarGlobales.getUserServer()+ pasword + " --database " + VarGlobales.getBaseDatos() + " -r " + archivo.getAbsoluteFile()+".sql";
            String executeCmd = "mysqldump -u "+datosComando;
            System.out.println(executeCmd);
             /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
              
            int processComplete = respaldarBd(executeCmd);
            if (processComplete == 0) {
                    System.out.println("Backup Complete");
                } else {
                    JFileChooser seleccionarExe = new JFileChooser();  
                    seleccionarExe.setFileFilter(new MantenimientoBD2());
                    int showOpenDialog = seleccionarExe.showOpenDialog(escritorio);
                    if(showOpenDialog == JFileChooser.APPROVE_OPTION)
                    {                        
                       executeCmd = seleccionarExe.getSelectedFile().getAbsolutePath()+" -u "+datosComando;
                       System.out.println(executeCmd);
                        int respaldarBd = respaldarBd(executeCmd);
                        if(processComplete == 0)
                        {
                           JOptionPane.showMessageDialog(escritorio, "Respaldo de base de datos se realizo correctamente.\n"
                                   + "en "+seleccionarExe.getSelectedFile().getAbsolutePath(), "El Proceso se realizo correctammente", JOptionPane.PLAIN_MESSAGE);                 
                        }
                    }
                }
        }
    }
    
    public void restaurar(){
        JFileChooser selectorDeArchivos = new JFileChooser();        
        int showSaveDialog = selectorDeArchivos.showOpenDialog(escritorio);
        if(showSaveDialog == JFileChooser.APPROVE_OPTION)
        {
            try {
                FileReader archivo = new FileReader(selectorDeArchivos.getSelectedFile());
                conexion.Conectar();
                ScriptsBD sr = new ScriptsBD(conexion.con,false,true);
                sr.runScript(archivo);                
                conexion.con.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(escritorio, "Archivo no soportado");                
            }
            catch(IOException ex)
            {
                JOptionPane.showMessageDialog(escritorio, "Archivo no soportado");
            }
            catch(SQLException ex){JOptionPane.showMessageDialog(escritorio, "Archivo no soportado");}
            JOptionPane.showMessageDialog(escritorio, "Base de datos Importado correctamente");
        }
    }
    
    public int respaldarBd(String executeCmd){
           int processComplete = -1;
            /*NOTE: Executing the command here*/
            Process runtimeProcess;
            try {
                runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                processComplete = runtimeProcess.waitFor();               
                return processComplete;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(escritorio, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);                
            }
            catch(InterruptedException ex){ex.printStackTrace();}
            return processComplete;
    }
}
