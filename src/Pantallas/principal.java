package Pantallas;

import static Vista.Login.Idioma;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import clases.CargaMenu;
import clases.ImagenFondo;
import clases.ReadFileXml;
import clases.conexion;
import clases.control_existencias;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 *
 * @author Kelvin Marcano
 */
public class principal extends javax.swing.JFrame implements Runnable {
    public static final int salir=1;
    public static String Maestro="";
    public static JMenu Menus;
    public static JMenuItem SubMenus, SubMenus2, SubMenus3, SubMenus4, SubMenus5;
    public static JMenuBar barra = new JMenuBar();//LA DECLARAMOS E INSTANCIAMOS.
    
    control_existencias ctrl = new control_existencias();
    String hora, minutos, segundos, ampm, Form_Carga, TipDoc, Titulo;
    Calendar calendario;
    Thread h1;
    
    private boolean lSubMenu=false;
    private String Evento, metodo = "", clase="", Paramet="", pr="", Dato="", Dato1="";;
    private int veces=0, PosIniParamet=0, PosFinParamet=0, pos=0;
    private char _toCompare;
    private char [] caracteres;
    private ResultSet rs=null, rs_raiz=null, rs_hijos=null, rs_nietos=null, rs_bisnietos=null, rs_bisnietos1=null, rs_bisnietos2=null;
    private boolean lMetedo=false;
    private Vector Msg, elementos, Menu;
    private String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    private String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    
    clases.conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public principal() {
        initComponents();

        lbHora.setVisible(false); jLogoCli.setVisible(false); jLogoEmp.setVisible(false);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());

//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");

        String FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        ReadFileXml xml_label = new ReadFileXml();
        elementos = xml_label.ReadFileXml(xmlFile, "Label", "form_"+FormClass);
        
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "form_"+FormClass);
//-----------------------------------------------------------------------------------------------------------------------
        
        CargaMenu MenuPrincipal = new CargaMenu();
        try {
            MenuPrincipal.CargaMenuPrincipal(false);
        } catch (SQLException ex) {
            Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        setJMenuBar(barra);

        escritorio.setBorder(new ImagenFondo(VarGlobales.getIdUser()));
        
        escritorio.setBackground(java.awt.Color.WHITE);
        escritorio.setBackground(Color.white);
        iniciarreloj();

//******************* Validacion sobre el Boton Cerrar del la Barra de Titulo *******************
        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);

	addWindowListener(new WindowAdapter(){
	    public void windowClosing(WindowEvent we){
                JInternalFrame[] array = escritorio.getAllFrames();
                int FormActivos = array.length;

                if (FormActivos>0){
                    JOptionPane.showMessageDialog(null,(String) Msg.get(0), (String) Msg.get(1), JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
	        int eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(2));

	        if ( eleccion == 0) {
                    Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                     VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                     "cerro sesión", "Usuario "+VarGlobales.getUserName().trim()+" ha cerrado sesión");
                    
                    System.exit(0);
	        }  
	    }
	});
//***********************************************************************************************    

        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("+text+"))); // NOI18N
        
        Dimension desktopSize = escritorio.getSize();
        Dimension jInternalFrameSize = escritorio.getSize();
        escritorio.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        principal.setDefaultLookAndFeelDecorated(true);
        
        this.setTitle("Bienvenidos al Sistema de Gestión Administrativa ["+VarGlobales.getNombEmpresa()+"]");
    }
    
    public void iniciarreloj(){
        h1 = new Thread(this);
        h1.start();
    }
        
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcula();
            lbHora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        escritorio = new javax.swing.JDesktopPane();
        jLogoCli = new javax.swing.JLabel();
        lbHora = new javax.swing.JLabel();
        jLogoEmp = new javax.swing.JLabel();
        jLogoBD = new javax.swing.JLabel();
        BarraMenu = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(java.awt.Color.white);

        escritorio.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        escritorio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        escritorio.setMaximumSize(new java.awt.Dimension(1341, 941));

        jLogoCli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLogoCli.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbHora.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lbHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHora.setText("jLabel1");
        lbHora.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLogoEmp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLogoEmp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLogoBD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createSequentialGroup()
                        .addComponent(jLogoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 707, Short.MAX_VALUE)
                        .addComponent(jLogoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLogoBD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLogoEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLogoCli, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbHora, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                .addComponent(jLogoBD, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        escritorio.setLayer(jLogoCli, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lbHora, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jLogoEmp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jLogoBD, javax.swing.JLayeredPane.DEFAULT_LAYER);

        setJMenuBar(BarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    //public static class Variables {
    //    public static String ID_User = "";
    //    public static String User_Nombre = "";
    //    public static String IP_Pc = "";
    //    public static String Mac_Pc = "";
    //    public static String Cod_Empresa = "";
    //    public static int Grupo_Permisos = 0;
    //}
    
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
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new RelojFrame().setVisible(true); 
                principal.setDefaultLookAndFeelDecorated(true);
                new principal().setVisible(true);
                SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");  

            }
        });
    }
    
    public void salir(){
        int salir = JOptionPane.showConfirmDialog(null, (String) Msg.get(2));
        
        if (salir == 0) {
            Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                             VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                             "cerro sesión", "Usuario "+VarGlobales.getUserName().trim()+" ha cerrado sesión");
            
            System.exit(0);
        }
    }
    
    public void CambioUsuario(){
        dispose();
        Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(),
                                         VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                         "cerro sesión", "Usuario "+VarGlobales.getUserName().trim()+" ha cerrado sesión para cambio de Usuario");

        new Vista.Login().setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar BarraMenu;
    public static javax.swing.JDesktopPane escritorio;
    public static javax.swing.JLabel jLogoBD;
    public static javax.swing.JLabel jLogoCli;
    public static javax.swing.JLabel jLogoEmp;
    private javax.swing.JLabel lbHora;
    // End of variables declaration//GEN-END:variables
}