package clases;

import Modelos.VariablesGlobales;
import static Vista.Login.Idioma;
import Pantallas.principal;
import static Pantallas.principal.Maestro;
import static Pantallas.principal.Menus;
import static Pantallas.principal.SubMenus;
import static Pantallas.principal.SubMenus2;
import static Pantallas.principal.SubMenus3;
import static Pantallas.principal.barra;
import static Pantallas.principal.escritorio;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.plaf.basic.BasicMenuBarUI;

public class CargaMenu {
    private ResultSet rs_raiz=null, rs_hijos=null, rs_nietos=null, rs_bisnietos=null, rs_bisnietos1=null, rs_bisnietos2=null;
    private Vector Msg, elementos, header_table, Menu;
    private conexion conet = new conexion();
    private int veces=0, PosIniParamet=0, PosFinParamet=0, pos=0;
    
    private char _toCompare;
    private char [] caracteres;
    
    private String Evento, metodo = "", clase="", Paramet="", pr="", Dato="", Dato1="";;
    private String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    private String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public void CargaMenuPrincipal(boolean lMenu) throws SQLException{
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
        
//********************************* Menu Raiz *********************************
        try {
            String sql_raiz = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                              "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                              "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                              "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND "+
                              "SUB_MEN_ID=0 AND MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
            rs_raiz = conet.consultar(sql_raiz);
            
            rs_raiz.beforeFirst();
            while (rs_raiz.next()){
                String Menu_Raiz = rs_raiz.getString("MEN_NOMBRE").trim();
    
                String output_Menu_Raiz = Menu_Raiz;
                for (int i=0; i<original.length(); i++) {
                    // Reemplazamos los caracteres especiales.
                    output_Menu_Raiz = output_Menu_Raiz.replace(original.charAt(i), ascii.charAt(i));
                }
                //***************** Obtengo el Idioma del Menu *****************
                File xmlFileMenu = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                ReadFileXml xml_menu = new ReadFileXml();
                Menu = xml_menu.ReadFileXml(xmlFileMenu, "Menu", "form_principal_"+output_Menu_Raiz.replace(" ", "_"));
                //**************************************************************

                Menus = new JMenu((String) Menu.get(0));   // Agrego cada unos de los Menus Raiz
Menus.setOpaque(true);
Menus.setBackground(Color.decode(colorText));
Menus.setForeground(Color.decode(colorForm));

//********************************* Menu Hijos *********************************
                //Busca si el Menu Raiz posee 1er SubMenu
                String sql_hijo = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                  "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                  "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                                  "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_raiz.getString("MEN_ID")+" AND "+
                                  "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                rs_hijos = conet.consultar(sql_hijo);
            
                rs_hijos.beforeFirst();
                while (rs_hijos.next()){
                    final String Menu_Hijo = rs_hijos.getString("MEN_NOMBRE").trim();
                    final String Evento1 = rs_hijos.getString("MEN_URL");

                    metodo = Evento1; veces=0; PosIniParamet=0; PosFinParamet=0;
                    CargaDatos(rs_hijos);

                    final String Evt_Ext = rs_hijos.getString("MEN_EXTERNO");
                    final String Img_Menu = rs_hijos.getString("MEN_IMG");
                    final int NumParamet=veces;
                    final String Parametros=Paramet;
                    final String Metodo = metodo;
                    final String Clase = clase;

//********************************* Menu Nietos *********************************
                    //Busca si el 1er SubMenu del Menu Raiz tiene un 2do SubMenus
                    String sql_nietos = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                        "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_hijos.getString("MEN_ID")+" AND "+
                                        "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                    rs_nietos = conet.consultar(sql_nietos);
                    
                    String output_Menu_Hijo = Menu_Hijo;
                    for (int i=0; i<original.length(); i++) {
                        // Reemplazamos los caracteres especiales.
                        output_Menu_Hijo = output_Menu_Hijo.replace(original.charAt(i), ascii.charAt(i));
                    }
                    //***************** Obtengo el Idioma del Menu *****************
                    File xmlFileMenu1 = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                    ReadFileXml xml_menu1 = new ReadFileXml();
                    Menu = xml_menu1.ReadFileXml(xmlFileMenu1, "Menu", "form_principal_"+output_Menu_Hijo.replace(" ", "_"));
                    //**************************************************************

                    if (rs_nietos.getRow()>0){
                        // Si el 1er SubMenu posee un 2do SubMenu agrego los datos
                        SubMenus = new JMenu((String) Menu.get(0));   //Agrego el 2do SubMenus del Menu Raiz
SubMenus.setOpaque(true);
SubMenus.setBackground(Color.decode(colorText));
SubMenus.setForeground(Color.decode(colorForm));

                        if (rs_hijos.getString("MEN_ESTMENU").trim().equals("0")){
                            SubMenus.setEnabled(false);
                        }

                        Menus.add(SubMenus);   //Agrego las Opciones del 2do SubMenus del Menu Raiz correspondiente
Menus.addSeparator();
                        if (Img_Menu!=null){
                            SubMenus.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu.trim()));
                        }
                    }else{
                        // Si el 1er SubMenu no posee un 2do SubMenu le agrego a las Opciones el evento que realizara
                        SubMenus = new JMenuItem((String) Menu.get(0));   //Agrego la Opcion al 1er SubMenu del Menu Raiz
SubMenus.setOpaque(true);
SubMenus.setBackground(Color.decode(colorText));
SubMenus.setForeground(Color.decode(colorForm));

                        if (rs_hijos.getString("MEN_ESTMENU").trim().equals("0")){
                            SubMenus.setEnabled(false);
                        }

                        Menus.add(SubMenus);   //Agrego la Opcion al 1er SubMenu del Menu Raiz correspodiente
Menus.addSeparator();
                        if (Img_Menu!=null){
                            SubMenus.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu.trim()));
                        }
                            
                        SubMenus.addActionListener(new ActionListener() {  //Cargamos el Evento de la Opcion del Menu
                            public void actionPerformed( ActionEvent evento ){
                                CargarEventos(Evento1, Evt_Ext, Metodo, Clase, NumParamet, Parametros, Menu_Hijo);
                            }
                        });
                    }
            
                    rs_nietos.beforeFirst();
                    while (rs_nietos.next()){
                        final String Menu_Nietos = rs_nietos.getString("MEN_NOMBRE").trim();
                        final String Evento2 = rs_nietos.getString("MEN_URL");

                        metodo = Evento2; veces=0; PosIniParamet=0; PosFinParamet=0;
                        CargaDatos(rs_nietos);

                        final String Evt_Ext1 = rs_nietos.getString("MEN_EXTERNO");
                        final String Img_Menu1 = rs_nietos.getString("MEN_IMG");
                        final int NumParamet1=veces;
                        final String Parametros1=Paramet;
                        final String Metodo1 = metodo;
                        final String Clase1 = clase;

//********************************* Menu Bisnietos *********************************
                        //Busca si el 2do SubMenu del Menu Raiz tiene un 3er SubMenus
                        String sql_bisnietos = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                               "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                               "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                                               "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_nietos.getString("MEN_ID")+" AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND "+
                                               "MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                        rs_bisnietos = conet.consultar(sql_bisnietos);

                        String output_Menu_Nietos = Menu_Nietos;
                        for (int i=0; i<original.length(); i++) {
                            // Reemplazamos los caracteres especiales.
                            output_Menu_Nietos = output_Menu_Nietos.replace(original.charAt(i), ascii.charAt(i));
                        }
                        //***************** Obtengo el Idioma del Menu *****************
                        File xmlFileMenu2 = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                        ReadFileXml xml_menu2 = new ReadFileXml();
                        Menu = xml_menu2.ReadFileXml(xmlFileMenu2, "Menu", "form_principal_"+output_Menu_Nietos.replace(" ", "_"));
                        //**************************************************************

                        if (rs_bisnietos.getRow()>0){
                            // Si el 2do SubMenu posee un 3er SubMenu agrego los datos
                            SubMenus2 = new JMenu((String) Menu.get(0));   //Agrego el 3er SubMenus del Menu Raiz
SubMenus2.setOpaque(true);
SubMenus2.setBackground(Color.decode(colorText));
SubMenus2.setForeground(Color.decode(colorForm));

                            if (rs_nietos.getString("MEN_ESTMENU").trim().equals("0")){
                                SubMenus2.setEnabled(false);
                            }
                        
                            SubMenus.add(SubMenus2);   //Agrego las Opciones del 3er SubMenus del Menu Raiz correspondiente
SubMenus.add(new JSeparator());
                            if (Img_Menu1!=null){
                                SubMenus2.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu1.trim()));
                            }
                        }else{
                            // Si el 2do SubMenu no posee un 3er SubMenu le agrego a las Opciones el evento que realizara
                            SubMenus2 = new JMenuItem((String) Menu.get(0));   //Agrego la Opcion al 2do SubMenu del Menu Raiz
SubMenus2.setOpaque(true);
SubMenus2.setBackground(Color.decode(colorText));
SubMenus2.setForeground(Color.decode(colorForm));

                            if (rs_nietos.getString("MEN_ESTMENU").trim().equals("0")){
                                SubMenus2.setEnabled(false);
                            }
                            
                            SubMenus.add(SubMenus2);   //Agrego la Opcion al 2do SubMenu del Menu Raiz correspodiente
SubMenus.add(new JSeparator());
                            if (Img_Menu1!=null){
                                SubMenus2.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu1.trim()));
                            }
                            
                            SubMenus2.addActionListener(new ActionListener() {  // clase interna anónima
                                public void actionPerformed( ActionEvent evento ){
                                    CargarEventos(Evento2, Evt_Ext1, Metodo1, Clase1, NumParamet1, Parametros1, Menu_Nietos);
                                }
                            });
                        }
                        
                        rs_bisnietos.beforeFirst();
                        while (rs_bisnietos.next()){
                            final String Menu_Bisnietos = rs_bisnietos.getString("MEN_NOMBRE").trim();
                            final String Evento3 = rs_bisnietos.getString("MEN_URL");

                            metodo = Evento3; veces=0; PosIniParamet=0; PosFinParamet=0;
                            CargaDatos(rs_bisnietos);

                            final String Evt_Ext2 = rs_bisnietos.getString("MEN_EXTERNO");
                            final String Img_Menu2 = rs_bisnietos.getString("MEN_IMG");
                            final int NumParamet2=veces;
                            final String Parametros2=Paramet;
                            final String Metodo2 = metodo;
                            final String Clase2 = clase;

//********************************* Menu Bisnietos1 *********************************
                            //Busca si el 3er SubMenu del Menu Raiz tiene un 4to SubMenus
                            String sql_bisnietos2 = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                                    "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                                    "INNER JOIN DNMENU ON MIS_MENU=MEN_ID AND MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"'"+
                                                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_bisnietos.getString("MEN_ID")+" AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND "+
                                                    "MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                            rs_bisnietos1 = conet.consultar(sql_bisnietos2);
                            
                            String output_Menu_Bisnietos = Menu_Bisnietos;
                            for (int i=0; i<original.length(); i++) {
                                // Reemplazamos los caracteres especiales.
                                output_Menu_Bisnietos = output_Menu_Bisnietos.replace(original.charAt(i), ascii.charAt(i));
                            }

                            //***************** Obtengo el Idioma del Menu *****************
                            File xmlFileMenu3 = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                            ReadFileXml xml_menu3 = new ReadFileXml();
                            Menu = xml_menu3.ReadFileXml(xmlFileMenu3, "Menu", "form_principal_"+output_Menu_Bisnietos.replace(" ", "_"));
                            //**************************************************************
                        
                            if (rs_bisnietos1.getRow()>0){
                                // Si el 3er SubMenu posee un 4to SubMenu agrego los datos
                                SubMenus3 = new JMenu((String) Menu.get(0));   //Agrego el 4to SubMenus del Menu Raiz
SubMenus3.setOpaque(true);
SubMenus3.setBackground(Color.decode(colorText));
SubMenus3.setForeground(Color.decode(colorForm));

                                if (rs_bisnietos.getString("MEN_ESTMENU").trim().equals("0")){
                                    SubMenus3.setEnabled(false);
                                }
                            
                                SubMenus2.add(SubMenus3);   //Agrego las Opciones del 4to SubMenus del Menu Raiz correspondiente
SubMenus2.add(new JSeparator());
                                if (Img_Menu2!=null){
                                    SubMenus3.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu2.trim()));
                                }
                            }else{
                                // Si el 3er SubMenu no posee un 4to SubMenu le agrego a las Opciones el evento que realizara
                                SubMenus3 = new JMenuItem((String) Menu.get(0));   //Agrego la Opcion al 3er SubMenu del Menu Raiz
SubMenus3.setOpaque(true);
SubMenus3.setBackground(Color.decode(colorText));
SubMenus3.setForeground(Color.decode(colorForm));

                                if (rs_bisnietos.getString("MEN_ESTMENU").trim().equals("0")){
                                    SubMenus3.setEnabled(false);
                                }
                                
                                SubMenus2.add(SubMenus3);   //Agrego la Opcion al 3er SubMenu del Menu Raiz correspodiente
SubMenus2.add(new JSeparator());
                                if (Img_Menu2!=null){
                                    SubMenus3.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu2.trim()));
                                }
                                                        
                                SubMenus3.addActionListener(new ActionListener() {  // clase interna anónima
                                    public void actionPerformed( ActionEvent evento ){
                                        CargarEventos(Evento3, Evt_Ext2, Metodo2, Clase2, NumParamet2, Parametros2, Menu_Bisnietos);
                                    }
                                });
                            }
/*
                            SubMenus4 = new JMenuItem[rs_bisnietos1.getRow()];
                            int ind_bisnietos1 = 0;
                        
                            rs_bisnietos1.beforeFirst();
                            while (rs_bisnietos1.next()){
                                String Menu_Bisnietos1 = rs_bisnietos1.getString("MEN_NOMBRE").trim();
                                final String Evento4 = rs_bisnietos1.getString("MEN_URL");
                                ind_bisnietos1++;

                                metodo = Evento4; veces=0; PosIniParamet=0; PosFinParamet=0;
                                CargaDatos(rs_bisnietos1);

                                final String Evt_Ext3 = rs_bisnietos1.getString("MEN_EXTERNO");
                                final int NumParamet3=veces;
                                final String Parametros3=Paramet;
                                final String Metodo3 = metodo;
                                final String Clase3 = clase;

//********************************* Menu Bisnietos2 *********************************
                                //Busca si el 4to SubMenu del Menu Raiz tiene un 5to SubMenus
                                //String sql_bisnietos3 = "SELECT * FROM DNMENU WHERE SUB_MEN_ID="+rs_bisnietos2.getString("MEN_ID")+" AND MEN_TIPO=2 ORDER BY MEN_ID ASC;";
                                String sql_bisnietos3 = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                                        "MEN_IMG,MEN_EXTERNO FROM DNPERMISOLOGIA "+
                                                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                                        "WHERE SUB_MEN_ID="+rs_bisnietos2.getString("MEN_ID")+" AND MIS_PERMISO="+Grupo_Permisos+" AND "+
                                                        "MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                                rs_bisnietos2 = conet.consultar(sql_bisnietos2);
                            
                                if (rs_bisnietos2.getRow()>0){
                                    // Si el 4to SubMenu posee un 5to SubMenu agrego los datos
                                    this.SubMenus4[ind_bisnietos1-1] = new JMenu(Menu_Bisnietos1);   //Agrego el 5to SubMenus del Menu Raiz
                                    this.SubMenus3[ind_bisnietos-1].add(this.SubMenus4[ind_bisnietos1-1]);   //Agrego las Opciones del 5to SubMenus del Menu Raiz correspondiente
                                }else{
                                    // Si el 4to SubMenu no posee un 5to SubMenu le agrego a las Opciones el evento que realizara
                                    this.SubMenus4[ind_bisnietos1-1] = new JMenuItem(Menu_Bisnietos1);   //Agrego la Opcion al 4to SubMenu del Menu Raiz
                                    this.SubMenus3[ind_bisnietos-1].add(this.SubMenus4[ind_bisnietos1-1]);   //Agrego la Opcion al 4to SubMenu del Menu Raiz correspodiente
                                
                                    this.SubMenus4[ind_bisnietos1-1].addActionListener(new ActionListener() {  // clase interna anónima
                                        public void actionPerformed( ActionEvent evento ){
                                            CargarEventos(Evento4, Evt_Ext3, Metodo3, Clase3, NumParamet3, Parametros3);
                                        }
                                    });
                                }
                            }
*/                            
                        }
                    }
//******************************************************************************                
                }
//******************************************************************************

                barra.setUI ( new BasicMenuBarUI (){
                    public void paint ( Graphics g, JComponent c ){
                        g.setColor(Color.decode(colorText));
                        g.fillRect( 0, 0, c.getWidth (), c.getHeight () );
                    }
                } );
                
                barra.add(Menus);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargaMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CargaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
//*****************************************************************************
    }
    
    private void CargaDatos(ResultSet rs_datos) throws SQLException{
        if (metodo==null || metodo.isEmpty()){
            metodo="";
        }else{
            pr=metodo.trim();
            PosIniParamet = pr.indexOf("(");
            PosFinParamet = pr.indexOf(")");
                       
            _toCompare=',';
            caracteres=pr.toCharArray();
            for (int i=0; i<=caracteres.length-1;i++){
                if(_toCompare == caracteres[i]){
                    veces++;
                }
            }
                        
            if (PosIniParamet>0 && PosFinParamet>0){
                Paramet=pr.substring(PosIniParamet+1, PosFinParamet);
            }
                        
            if (veces>0){
                veces=veces+1;
            }
        }

        pos = 0;
        pos = metodo.indexOf(";");

        if (pos>0){
            clase = rs_datos.getString("MEN_URL").substring(0,(rs_datos.getString("MEN_URL").indexOf(";"))).trim();

            if (PosIniParamet>0){
                metodo = rs_datos.getString("MEN_URL").substring((rs_datos.getString("MEN_URL").indexOf(";")+1),PosIniParamet).trim();
            }else{
                metodo = rs_datos.getString("MEN_URL").substring((rs_datos.getString("MEN_URL").indexOf(";")+1),rs_datos.getString("MEN_URL").trim().length()).trim();
            }
        }else{
            metodo=null;
        }
    }
    
    private void CargarEventos(String Evento, String Evt_Ext, String Metodo, String Clase, int NumParamet, String Parametros, String Nombre_Menu){
        if (Evento!=null || !Evento.isEmpty()){
            Class Formulario;
            try {
                if (Evt_Ext.trim().equals("1")){
                    Formulario = Class.forName(Evento.trim());
                    JFrame frame = (JFrame) Formulario.newInstance();
                    frame.setVisible(true);
                }else{
                    if (Metodo!=null){
                        try{
                            Object tempClass = Class.forName(Clase.trim()).newInstance();
                                        
                            Class claseCargada = tempClass.getClass();
                                                            
                            Method meth;
                            if (NumParamet>0){
                                Class Param[] = new Class[NumParamet];
                                                                
                                for (int i=0; i<=NumParamet-1;i++){
                                    Param[i]= String.class;
                                }
                                
                                meth = claseCargada.getDeclaredMethod(Metodo.trim(), Param);
                                Object arglist[] = new Object[NumParamet];
                                                                
                                Dato=""; Dato1="";
                                if (Parametros.length()>0){
                                    for (int i=0; i<=NumParamet-1;i++){
                                        if (i==0){
                                            Dato=Parametros.substring(0,Parametros.indexOf(","));
                                            Dato1=Parametros.substring(Parametros.indexOf(",")+1,Parametros.length());
                                        }else{
                                            if (Dato1.indexOf(",")>0){
                                                Dato=Dato1.substring(0,Dato1.indexOf(","));
                                                Dato1=Dato1.substring(Dato1.indexOf(",")+1,Dato1.length());
                                            }else{
                                                Dato=Dato1.substring(0,Dato1.length());
                                            }
                                        }
                                        arglist[i] = new String(Dato);
                                    }
                                }
                                                                
                                meth.invoke(tempClass, arglist);
                            }else{
                                meth = claseCargada.getDeclaredMethod(Metodo.trim(), null);
                                meth.invoke(tempClass, null);
                            }
                        }catch (Throwable e){
                            System.err.println(e);
                        }
                    }else{
                        if (Nombre_Menu.equals("Clientes")){
                            Maestro="Clie";
                        }
                        if (Nombre_Menu.equals("Proveedores")){
                            Maestro="Prov";
                        }
System.out.println(Evento.trim());
                        Formulario = Class.forName(Evento.trim());
                        JInternalFrame Form = (JInternalFrame) Formulario.newInstance();
                                        
                        Dimension desktopSize = escritorio.getSize();
                        Dimension jInternalFrameSize = Form.getSize();
                        Form.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
                
                        escritorio.add(Form);
                        Form.show();
                        
                        if(Evento.trim().equals("POS.POS")){
                            try { 
                                Form.setMaximum(true);
                            } catch (PropertyVetoException ex) {
                                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
