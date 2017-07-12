package Vista;

import Modelos.VariablesGlobales;
import clases.Encrip_Desencrip;
import static Vista.ConfigurarConexion.Tab;
import static Vista.ConfigurarConexion.clave;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.SQLQuerys;
import static clases.conexion.lBdExist;
import static clases.conexion.lCreaBd;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Painter;
import javax.swing.SwingWorker;
import javax.swing.UIDefaults;
import javax.swing.WindowConstants;

public class ProgressBarCreaBd extends javax.swing.JFrame implements PropertyChangeListener {
    private TaskMySQL task_mysql;
    private TaskPostGreSQL task_postgresql;
    private int num_crea = 0, num_insert = 0;
    private JScrollPane scrollPane;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public ProgressBarCreaBd() {
        initComponents();

        jPanel1.setBackground(Color.decode(colorForm));
        
        jLbl_DescriProgre.setForeground(Color.decode(colorText)); jLbl_DescriProgre1.setForeground(Color.decode(colorText));
        
        jAreaDetalle.setForeground(Color.decode(colorText));
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
        
//******************* Validacion sobre el Boton Cerrar del la Barra de Titulo *******************
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);

	addWindowListener(new WindowAdapter(){
	    public void windowClosing(WindowEvent we){
                if (num_crea<1110){
                    JOptionPane.showMessageDialog(null, "No se puede cerrar el formulario porque el proceso de contrucción \n"+
                                                        "de la base de datos no ha concluido aun...!!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
	    }
	});
//***********************************************************************************************
        
        jLbl_DescriProgre.setText("Proceso: ");
        jLbl_DescriProgre1.setText("Registros Iniciales: ");
        jAreaDetalle.setEditable(false);
        setLocationRelativeTo(null);
        
        if (VarGlobales.getManejador().equals("MySQL")){
            jProgressBar1.setMaximum(1650);
            jProgressBar2.setMaximum(1594);

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task_mysql = new TaskMySQL();
            task_mysql.addPropertyChangeListener(this);
            task_mysql.execute();
        }else if (VarGlobales.getManejador().equals("PostGreSQL")){
            jProgressBar1.setMaximum(1168);
            jProgressBar2.setMaximum(1626);
            
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task_postgresql = new TaskPostGreSQL();
            task_postgresql.addPropertyChangeListener(this);
            task_postgresql.execute();
        }
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        jProgressBar2.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar2.putClientProperty("Nimbus.Overrides", defaults);
    }
    
    class TaskMySQL extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
//*********************************************************************************
            File carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            File Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\BD-MySQL.sql");
            File Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+"temp");

//********** Codigo para Desencriptar el Archivo SQL de la Base de Datos **********
            String ScriptSQL="";
            try { 
                ScriptSQL = Encrip_Desencrip.readEncrypted(Archivo_SQL, 12345);
            
                //Creo el archivo desencriptado temporal
                if (Archivo_SQL_Temp.createNewFile()){    
                    FileWriter Arc = new FileWriter(Archivo_SQL_Temp,true);
                    BufferedWriter escribir = new BufferedWriter(Arc);

                    escribir.write(ScriptSQL);

                    escribir.close();
                }           
            } catch (IOException ioe) {
            }
//*********************************************************************************
            
            BufferedReader pr;
            try {
                pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
            
                String Read2="", Crea_Tabla="", Create_Unique="", Create_Index = "", Alter_Table = "";
                String AreaDetalle=""; String Trigger=""; 
                int Linea_Declare=0; int Linea_Set=0; int Linea_If=0;

                SQLQuerys insertarconex = new SQLQuerys();
                
                String DropDataBase = "drop database if exists `"+VarGlobales.getBaseDatos()+"`;";
                jLbl_DescriProgre.setText("Proceso: "+DropDataBase);
                jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                insertarconex.SqlInsert(DropDataBase);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                }

                String CreateDataBase = "create database `"+VarGlobales.getBaseDatos()+"`;";
                jLbl_DescriProgre.setText("Proceso: "+CreateDataBase);
                jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                insertarconex.SqlInsert(CreateDataBase);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                }
                
                String UseDataBase = "use `"+VarGlobales.getBaseDatos()+"`;";
                jLbl_DescriProgre.setText("Proceso: "+UseDataBase);
                jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                insertarconex.SqlInsert(UseDataBase);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                }
                
                lBdExist = true;
                //jAreaDetalle.setLineWrap(true);   //Ajusta las lineas al Ancho del jTextArea

                while((Read2 = pr.readLine()) != null){
                    num_crea++;
                
                    jProgressBar1.setValue(num_crea);
                    jProgressBar1.setStringPainted(true);

                    if (Read2.startsWith("drop")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Drop = Read2;
                        insertarconex.SqlInsert(Drop);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create table")==true){
                        Crea_Tabla = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Crea_Tabla = Read2+"(";
                    }
                    if (Read2.startsWith("   ")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                        }
                        if (Create_Unique.length()>0){
                            Create_Unique = Create_Unique+Read2.trim();
                        }
                        if (Create_Index.length()>0){
                            Create_Index = Create_Index+Read2.trim();
                        }
                    }
                    if (Read2.startsWith(");")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                            System.out.println(Crea_Tabla);
                            jLbl_DescriProgre.setText("Proceso: "+Crea_Tabla);
                            jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                            
                            String CreaTabla = Crea_Tabla;
                            insertarconex.SqlInsert(CreaTabla);
                        
                            Crea_Tabla = "";

                            try {
                              Thread.sleep(100);
                            } catch (InterruptedException e){
                            }
                        }
                        if (Create_Unique.length()>0){
                            Create_Unique = Create_Unique+Read2.trim();
                            System.out.println(Create_Unique);
                            jLbl_DescriProgre.setText("Proceso: "+Create_Unique);
                            jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");

                            String CreaUnico = Create_Unique;
                            insertarconex.SqlInsert(CreaUnico);
                            
                            Create_Unique = "";
                        
                            try {
                              Thread.sleep(100);
                            } catch (InterruptedException e){
                            }
                        }
                        if (Create_Index.length()>0){
                            Create_Index = Create_Index+Read2.trim();
                            System.out.println(Create_Index);
                            jLbl_DescriProgre.setText("Proceso: "+Create_Index);
                            jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                            String CreaIndice = Create_Index;
                            insertarconex.SqlInsert(CreaIndice);
                            
                            Create_Index = "";
                        
                            try {
                               Thread.sleep(100);
                            } catch (InterruptedException e){
                            }
                        }
                    }
                    if (Read2.startsWith("alter")==true && Read2.endsWith(";")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Alter = Read2;
                        insertarconex.SqlInsert(Alter);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create unique")==true){
                        Create_Unique = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Create_Unique = Read2+"(";
                    }
                    if (Read2.startsWith("create index")==true){
                        Create_Index = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Create_Index = Read2+"(";
                    }
                    if (Read2.startsWith("alter")==true && Read2.endsWith(")")==true){
                        Alter_Table = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Alter_Table = Read2;
                    }
                    if (Read2.startsWith("      ")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Alter_Table = Alter_Table+" "+Read2.trim();
                        System.out.println(Alter_Table);
                        jLbl_DescriProgre.setText("Proceso: "+Alter_Table);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String AlterTabla = Alter_Table;
                        insertarconex.SqlInsert(AlterTabla);
                    
                        Alter_Table = "";
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("CREATE TRIGGER")==true){
                        Trigger = Read2+"\n";
                    }
                    if (Read2.startsWith("  DECLARE")==true){
                        Trigger = Trigger+Read2+"\n";
                    }
                    if (Read2.startsWith("  SET")==true){
                        Linea_Set++;
                        
                        if (Linea_Set==1){
                            Trigger = Trigger+"\n"+Read2+"\n";
                        }else{
                            Trigger = Trigger+Read2+"\n";
                        }
                    }
                    if (Read2.startsWith("  IF")==true){
                        Linea_If++;
                        
                        if (Linea_If==1){
                            Trigger = Trigger+"\n"+Read2+"\n";
                        }else{
                            Trigger = Trigger+Read2+"\n";
                        }
                    }
                    if (Read2.startsWith("  ELSE")==true){
                        Trigger = Trigger+Read2+"\n";
                    }
                    if (Read2.startsWith("     SET")==true){
                        Trigger = Trigger+Read2+"\n";
                    }                    
                    if (Read2.startsWith("  END IF;")==true){
                        Trigger = Trigger+Read2+"\n";
                    }    
                    if (Read2.startsWith("END;;")==true){
                        Trigger = Trigger+Read2;
                        System.out.println(Trigger);
                        jLbl_DescriProgre.setText("Proceso: "+Trigger);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String CreaTriger = Trigger;
                        insertarconex.SqlInsert(CreaTriger);
                    
                        Trigger = ""; Linea_Set=0; Linea_If=0;
                    
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    
                    //Para Desplázar hasta el final del JTextArea
                    int len = jAreaDetalle.getDocument().getLength();
                    jAreaDetalle.setCaretPosition(len);
                    jAreaDetalle.requestFocusInWindow();
                }

                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
                
                Cargado_Datos_Iniciales();

                System.out.println(num_crea);

                //Cierro y elimino el archivo desencriptado temporar
                pr.close();
                Archivo_SQL_Temp.delete();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 1", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 2", JOptionPane.INFORMATION_MESSAGE);
            }
//*********************************************************************************
            return null;
        }
 
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            jAreaDetalle.append("Done!\n");
            jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
            lCreaBd=false; lBdExist = true;
            
            JOptionPane.showMessageDialog(null, "El proceso de Creacion de la Base de datos: "+VarGlobales.getBaseDatos()+" finalizo correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            dispose();

            ConfigurarConexion prueba_metodo = new ConfigurarConexion();
            Tab=0;
            clave=VarGlobales.getPasswServer();
            prueba_metodo.action_bt_save(true);
        }
    }

    class TaskPostGreSQL extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            File carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            File Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\BD-PostGreSQL.sql");
            File Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+"temp");
            
            String ScriptSQL="";
            try { 
                ScriptSQL = Encrip_Desencrip.readEncrypted(Archivo_SQL, 12345);
            
                //Creo el archivo desencriptado temporal
                if (Archivo_SQL_Temp.createNewFile()){    
                    FileWriter Arc = new FileWriter(Archivo_SQL_Temp,true);
                    BufferedWriter escribir = new BufferedWriter(Arc);

                    escribir.write(ScriptSQL);

                    escribir.close();
                }           
            } catch (IOException ioe) {
            }
//*********************************************************************************
            
            BufferedReader pr;
            try {
                pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
            
                String Read2="", Crea_Tabla="", Create_Unique="", Create_Index = "", Alter_Table = "";
                String AreaDetalle=""; String Trigger=""; String comment=""; String Funcion="";
                int Linea_Declare=0; int Linea_Set=0; int Linea_If=0;

                SQLQuerys insertarconex = new SQLQuerys();
            
                String DropDataBase = "drop database if exists "+VarGlobales.getBaseDatos()+";";
                jLbl_DescriProgre.setText("Proceso: "+DropDataBase);
                jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                insertarconex.SqlInsert(DropDataBase);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                }

                String CreateDataBase = "create database "+VarGlobales.getBaseDatos()+" with encoding='UTF8' owner="+VarGlobales.getUserServer()+";";
                jLbl_DescriProgre.setText("Proceso: "+CreateDataBase);
                jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                insertarconex.SqlInsert(CreateDataBase);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                }
                
                lBdExist = true;
                //jAreaDetalle.setLineWrap(true);   //Ajusta las lineas al Ancho del jTextArea

                while((Read2 = pr.readLine()) != null){
                    num_crea++;
                
                    jProgressBar1.setValue(num_crea);
                    jProgressBar1.setStringPainted(true);

                    if (Read2.startsWith("drop")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Drop = Read2;
                        insertarconex.SqlInsert(Drop);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create table")==true){
                        Crea_Tabla = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        //Crea_Tabla = Read2+"(";
                        Crea_Tabla = Read2;
                    }
                    if (Read2.startsWith("   ")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                        }
                        if (Create_Unique.length()>0){
                            Create_Unique = Create_Unique+Read2.trim();
                        }
                        if (Create_Index.length()>0){
                            Create_Index = Create_Index+Read2.trim();
                        }
                    }
                    if (Read2.startsWith(");")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                            System.out.println(Crea_Tabla);
                            jLbl_DescriProgre.setText("Proceso: "+Crea_Tabla);
                            jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                            
                            String CreaTabla = Crea_Tabla;
                            insertarconex.SqlInsert(CreaTabla);
                        
                            Crea_Tabla = "";

                            try {
                              Thread.sleep(100);
                            } catch (InterruptedException e){
                            }
                        }
                    }
                    if (Read2.startsWith("comment")==true){                        
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Comment = Read2;
                        insertarconex.SqlInsert(Comment);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("alter")==true && Read2.endsWith(";")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Alter = Read2;
                        insertarconex.SqlInsert(Alter);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create unique")==true){
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Unique = Read2;
                        insertarconex.SqlInsert(Unique);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create  index")==true){
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String Create_Indice = Read2;
                        insertarconex.SqlInsert(Create_Indice);
                    
                        try {
                           Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
//*************************** Creacion de Funciones ****************************
                    if (Read2.startsWith("create or replace function")==true){
                        Funcion = Read2+"\n";
                    }
                    if (Read2.startsWith("  DECLARE")==true){
                        Funcion = Funcion+Read2;
                    }
                    if (Read2.startsWith("       ")==true && Read2.endsWith(";")==true){
                        Linea_Set++;
                        
                        if (Linea_Set==1){
                            Funcion = Funcion+"\n"+Read2+"\n";
                        }else{
                            if (Read2.startsWith("       RETURN NEW;")==true){
                                Funcion = Funcion+"\n"+Read2+"\n";
                            }else{
                                Funcion = Funcion+Read2+"\n";
                            }
                        }
                    }
                    if (Read2.startsWith("  BEGIN")==true){
                        Funcion = Funcion+Read2+"\n";
                    }
                    if (Read2.startsWith("       IF")==true){
                        Funcion = Funcion+"\n"+Read2+"\n";
                    }
                    if (Read2.startsWith("       ELSE")==true){
                        Funcion = Funcion+Read2+"\n";
                    }
                    if (Read2.startsWith("  END IF;")==true){
                        Funcion = Funcion+Read2+"\n";
                    }    
                    if (Read2.startsWith("  END;")==true){
                        Funcion = Funcion+Read2+"\n";
                    }
                    if (Read2.startsWith("$")==true){
                        Funcion = Funcion+Read2;
                        System.out.println(Funcion);
                        jLbl_DescriProgre.setText("Proceso: "+Funcion);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String CreaFuncion = Funcion;
                        insertarconex.SqlInsert(CreaFuncion);
                    
                        Funcion = ""; Linea_Set=0; Linea_If=0;
                    
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }
//******************************************************************************
//**************************** Creacion de Trigger *****************************
                    if (Read2.startsWith("CREATE TRIGGER")==true){
                        Trigger = Read2+"\n";
                    }
                    if (Read2.startsWith("    ON")==true){
                        Trigger = Trigger+Read2+"\n";
                    }
                    if (Read2.startsWith("    EXECUTE PROCEDURE")==true){
                        Trigger = Trigger+Read2;
                        System.out.println(Trigger);
                        jLbl_DescriProgre.setText("Proceso: "+Trigger);
                        jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                        
                        String CreaTriger = Trigger;
                        insertarconex.SqlInsert(CreaTriger);
                    
                        Trigger = "";
                    
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e){
                        }
                    }                                        
//******************************************************************************
                    //Para Desplázar hasta el final del JTextArea
                    int len = jAreaDetalle.getDocument().getLength();
                    jAreaDetalle.setCaretPosition(len);
                    jAreaDetalle.requestFocusInWindow();
                }
                
                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");

                Cargado_Datos_Iniciales();

                System.out.println(num_crea);

                //Cierro y elimino el archivo desencriptado temporar
                pr.close();
                Archivo_SQL_Temp.delete();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 1", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 2", JOptionPane.INFORMATION_MESSAGE);
            }
                
            return null;    
        }
        
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            jAreaDetalle.append("Done!\n");
            jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
            lCreaBd=false; lBdExist = true;
            
            JOptionPane.showMessageDialog(null, "El proceso de Creacion de la Base de datos: "+VarGlobales.getBaseDatos()+" finalizo correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            dispose();

            ConfigurarConexion prueba_metodo = new ConfigurarConexion();
            Tab=1;
            clave=VarGlobales.getPasswServer();
            prueba_metodo.action_bt_save(true);
        }
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBar1.setValue(progress);
            jProgressBar1.setStringPainted(true);
            jAreaDetalle.append(String.format("Completed %d%% of task.\n", task_mysql.getProgress()));
        } 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLbl_DescriProgre = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLbl_DescriProgre1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jAreaDetalle = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jProgressBar1.setMaximum(983);
        jProgressBar1.setAutoscrolls(true);

        jLbl_DescriProgre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLbl_DescriProgre.setText("jLabel1");

        jProgressBar2.setMaximum(983);
        jProgressBar2.setAutoscrolls(true);

        jLbl_DescriProgre1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLbl_DescriProgre1.setText("jLabel1");

        jAreaDetalle.setColumns(20);
        jAreaDetalle.setFont(new java.awt.Font("Monospaced", 1, 10)); // NOI18N
        jAreaDetalle.setRows(5);
        jScrollPane1.setViewportView(jAreaDetalle);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLbl_DescriProgre1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLbl_DescriProgre, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLbl_DescriProgre)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbl_DescriProgre1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProgressBarCreaBd().setVisible(true);
            }
        });
    }
    
    private void Cargado_Datos_Iniciales(){
        File carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
        File Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\Datos_Iniciales.sql");
        File Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+"temp2");
            
        SQLQuerys querys = new SQLQuerys();
        querys.SqlInsert("alter table dnmenu charset = utf8;");
            
        String ScriptSQL="";
        try { 
            ScriptSQL = Encrip_Desencrip.readEncrypted(Archivo_SQL, 12345);
           
            //Creo el archivo desencriptado temporal
            if (Archivo_SQL_Temp.createNewFile()){    
                FileWriter Arc = new FileWriter(Archivo_SQL_Temp,true);
                BufferedWriter escribir = new BufferedWriter(Arc);

                escribir.write(ScriptSQL);

                escribir.close();
            }           
        } catch (IOException ioe) {
        }
//*********************************************************************************
            
        BufferedReader pr;
        try {
            pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
            
            String Read2=""; num_insert=0;
            
            SQLQuerys insertarconex = new SQLQuerys();
            
            while((Read2 = pr.readLine()) != null){
                num_insert++;
                
                jProgressBar2.setValue(num_insert);
                jProgressBar2.setStringPainted(true);

                if (Read2.startsWith("INSERT INTO")==true){
                    System.out.println(Read2);
                    jLbl_DescriProgre1.setText("Registros Iniciales: "+Read2);
                    jAreaDetalle.append(jLbl_DescriProgre1.getText()+"\n");
                        
                    String InsertReg = Read2;
                    insertarconex.SqlInsert(InsertReg);
                    
                    try {
                       Thread.sleep(50);
                    } catch (InterruptedException e){
                    }
                }

                //Para Desplázar hasta el final del JTextArea
                int len = jAreaDetalle.getDocument().getLength();
                jAreaDetalle.setCaretPosition(len);
                jAreaDetalle.requestFocusInWindow();
            }
            
            SQLQuerys actualizar = new SQLQuerys();

            actualizar.SqlUpdate("UPDATE DNEMPRESAS SET EMP_USER='1',EMP_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNCONDIPAGO SET CDI_USER='1',CDI_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNDOCUMENTOS SET DOC_USER='1',DOC_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNPAIS SET PAI_USER='1',PAI_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNESTADOS SET EST_USER='1',EST_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNMUNICIPIOS SET MUN_USER='1',MUN_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNPARROQUIAS SET PAR_USER='1',PAR_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNLISTPRE SET LIS_USER='1',LIS_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNMONEDA SET MON_USER='1',MON_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNTIPIVA SET TIVA_USER='1',TIVA_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNTIPOMAESTRO SET TMA_USER='1',TMA_MACPC='"+VarGlobales.getMacPc()+"';");
            actualizar.SqlUpdate("UPDATE DNUNDMEDIDA SET MED_USER='1',MED_MACPC='"+VarGlobales.getMacPc()+"';");
            
            jLbl_DescriProgre1.setText("Registros Iniciales: Cargados Correctamente...!!");
            
            System.out.println(num_insert);

            //Cierro y elimino el archivo desencriptado temporar
            pr.close();
            Archivo_SQL_Temp.delete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jAreaDetalle;
    private javax.swing.JLabel jLbl_DescriProgre;
    private javax.swing.JLabel jLbl_DescriProgre1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

class MyPainter implements Painter<JProgressBar> {
    private final Color color;

    public MyPainter(Color c1) {
        this.color = c1;
    }
    @Override
    public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
        gd.setColor(color);
        gd.fillRect(0, 0, width, height);
    }
}