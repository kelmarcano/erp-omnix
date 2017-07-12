package clases;

import Modelos.VariablesGlobales;
import static Vista.ConfigurarConexion.lPassPostGre;
import static Vista.ConfigurarConexion.Clave;
import static clases.Main.idioma;
import static Vista.Login.Idioma;
import static Vista.Login.NumEmp;
import java.awt.GridLayout;
import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class conexion {
    static VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public static String bd = VarGlobales.getBaseDatos(), login = VarGlobales.getUserServer(), password = VarGlobales.getPasswServer();
    public static String url = "jdbc:mysql://"+VarGlobales.getIpServer()+"/" + VarGlobales.getBaseDatos();
    public java.sql.Statement stmt;//consultas
    public static java.sql.Connection con;
    public ResultSet rs, rs_mysql;
    public ResultSetMetaData rsMeta;
    protected java.sql.Connection conn;
    public PreparedStatement consulta;
    protected ResultSet resultado;
    protected java.sql.Statement stSentenciciasSQL;
    protected int registros;
    public static boolean login_acceso = false, lBdExist = false, lCreaBd = false;
    private Vector Msg, elementos;
    private String FormClass="";
    
    public static void Conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, login, password);
            System.out.println("CONEXION EXITOSA");
        }catch(Exception e){
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
        }
    }
    
    public static boolean Conectar(String Driver, String User, String Pass){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(Driver, User, Pass);
            System.out.println("CONEXION EXITOSA");
            
            return true;
        }catch(Exception e){
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
            return false;
        }
    }
    
    public void creaConexion() throws ClassNotFoundException, SQLException{
        try {
            if (VarGlobales.getManejador().equals("PostGreSQL")){
                Class.forName("org.postgresql.Driver");
                //this.conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.103:5432/dnpos","dnet","123");
                this.conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }else if (VarGlobales.getManejador().equals("MySQL")){
                Class.forName("com.mysql.jdbc.Driver");
                //this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/dnpos","root","");
                System.out.println("ip: "+VarGlobales.getIpServer()+"  user: "+VarGlobales.getUserServer()+" password:"+VarGlobales.getPasswServer()+" Base de datos: "+VarGlobales.getBaseDatos());
                this.conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos()+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }

            lCreaBd=false; lBdExist = true;
        } catch (Exception e) {
//********** Cargar archivo XML con los Mensajes en el Idioma Seleccionado **********
            FormClass = this.getClass().getName().toString();
            FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());

            File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
            File xmlFile = null;
            File xmlFileMsg = null;

            if (!idioma.isEmpty()){
                xmlFile = new File(carpeta.getAbsolutePath()+"\\"+idioma+".xml");
                xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+idioma+"_Msg.xml");
            }else if (!Idioma.isEmpty()){
                xmlFile = new File(carpeta.getAbsolutePath()+"\\"+Idioma+".xml");
                xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
            }

            ReadFileXml xml_label = new ReadFileXml();
            elementos = xml_label.ReadFileXml(xmlFile, "Label", "clase_"+FormClass);

            ReadFileXml xml_msg = new ReadFileXml();
            Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "clase_"+FormClass);
//***********************************************************************************
        
            if (VarGlobales.getManejador().equals("PostGreSQL")){
//******************** Codigo para solicitar la clave de Superusuario de PostGreSQL ********************
                if (lPassPostGre==true){
                    JPanel PassPanel = new JPanel();
                    PassPanel.setLayout(new GridLayout(2, 2));

                    JLabel titulo = new JLabel((String) elementos.get(0));
                    JPasswordField pf = new JPasswordField();
                
                    PassPanel.add(titulo);
                    PassPanel.add(pf);
                
                    int okCxl = JOptionPane.showConfirmDialog(null, PassPanel, (String) Msg.get(0), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
                    if (okCxl== JOptionPane.OK_OPTION){
                        Clave = new String(pf.getPassword());
                    }
                }
                
                System.out.println("Tu clave es: "+Clave);
//******************************************************************************************************
                
                this.conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/postgres", "postgres", Clave);
                stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                String sql = "SELECT datname AS DataBase FROM pg_database WHERE datistemplate = false;";
                this.consulta = this.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                resultado = this.consulta.executeQuery();
                resultado.last();
            }else if (VarGlobales.getManejador().equals("MySQL")){
                this.conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/mysql"+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                String sql = "SHOW DATABASES;";
                this.consulta = this.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                resultado = this.consulta.executeQuery();
                resultado.last();
            }

            resultado.first();
            for (int i=0; i<resultado.getRow(); i++){
                if (VarGlobales.getBaseDatos().equals(resultado.getString("DataBase"))){
                    lBdExist = true;
                }
                resultado.next();
            }
            
            if (lBdExist==false && lCreaBd==false){
                int eleccion = JOptionPane.showConfirmDialog(null, (String) Msg.get(1), (String) Msg.get(2), JOptionPane.ERROR_MESSAGE);
                
	        if ( eleccion == 0) {
                    new Vista.ProgressBarCreaBd().setVisible(true);
                    lCreaBd=true;
	        }
            }
        }
    }
    
    public java.sql.Connection conectado(){
        return con;
    }
    
    /**
     * @param sql sentencia sql como insertar, actualizar y eliminar
     * @return 0 si no hubo ningun cambio, devuelve 1 si hubo algun cambio 
     * en los registros
     */
    public static int guardarRegistro(String sql){
        conexion.Conectar();
        
        try{
            java.sql.Statement st = conexion.con.createStatement();
            int eu = st.executeUpdate(sql);
            st.close();
            conexion.con.close();
            
            return eu;    
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return 0;
    }
    
    public static ResultSet getRegistros(String sql){
        conexion.Conectar();
        
        try{
            java.sql.Statement st = conexion.con.createStatement();
            ResultSet eq = st.executeQuery(sql);            
            return eq;
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public boolean access (String txt_user, String txt_pass, String cod_empresa) {
//********** Cargar archivo XML con los Mensajes en el Idioma Seleccionado **********
        FormClass = this.getClass().getName().toString();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());

        File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
        File xmlFileMsg = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Msg.xml");
        
        ReadFileXml xml_msg = new ReadFileXml();
        Msg = xml_msg.ReadFileXml(xmlFileMsg, "Msg", "clase_"+FormClass);
//***********************************************************************************
        
        //***** Se declaran las variables de conexion en null
        java.sql.Connection Conn = null;
        ResultSet rsExiste = null;
        ResultSet rs = null;
        java.sql.Statement consulta = null;   
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        //String sql=("SELECT OPE_NOMBRE, OPE_CLAVE FROM "+basedatos+".dnusuarios WHERE OPE_USUARIO='"+txt_user+"' AND OPE_CLAVE='"+txt_pass+"'");
        String sql=("SELECT OPE_NOMBRE,OPE_CLAVE FROM dnusuarios WHERE OPE_EMPRESA='"+cod_empresa+"' AND "+
                    "OPE_USUARIO='"+txt_user+"' AND OPE_CLAVE='"+txt_pass+"' AND OPE_ACTIVO=1");
        System.out.println(sql);
        
        try{
            if (VarGlobales.getManejador().equals("PostGreSQL")){
                Class.forName("org.postgresql.Driver");
                //Conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.103:5432/dnpos","dnet","123");
                Conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta=(java.sql.Statement) Conn.createStatement();
            }else if (VarGlobales.getManejador().equals("MySQL")){
                Class.forName("com.mysql.jdbc.Driver");
                //Conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/dnpos","root","");
                Conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta=(java.sql.Statement) Conn.createStatement();
            }

            String sqlExiste=("SELECT COUNT(*) AS REGISTROS FROM dnusuarios WHERE OPE_EMPRESA='"+cod_empresa+"' AND "+
                              "OPE_USUARIO='"+txt_user+"' AND OPE_CLAVE='"+txt_pass+"'");
            int userExist = Count_Reg(sqlExiste);
            rs = consulta.executeQuery(sql);
            
            if(rs.next()){
                login_acceso = true;
                System.out.println("Login Correcto");
            }else{ 
                if (NumEmp>2){
                    JOptionPane.showMessageDialog(null, (String) Msg.get(3));
                }else{
                    if (userExist>0){
                        JOptionPane.showMessageDialog(null, (String) Msg.get(5));
                    }else{
                        JOptionPane.showMessageDialog(null, (String) Msg.get(6));
                    }
                }
                login_acceso = false;
            }

            while(rs.next()){ 
                System.out.println(rs.getString(1));
            }
        }catch (ClassNotFoundException |SQLException e) {
            JOptionPane.showMessageDialog(null, (String) Msg.get(4));
        }
        
        return(login_acceso);
    }    
    
    public ResultSet consultar(String sql) throws ClassNotFoundException{
        java.sql.Connection Conn = null;
        java.sql.Statement consulta = null;   
        String Sql=(sql);
        System.out.println(Sql);
        
        try{
            this.creaConexion();
            this.consulta = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultado = this.consulta.executeQuery();
//            System.out.println("Consulta: "+Sql);
            resultado.last();
        }catch (ClassNotFoundException |SQLException e){
        }

        return resultado;
    }
    
    public int Count_Reg (String Sql) {
        try {
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(Sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = this.consulta.executeQuery();
            System.out.println(Sql);

            while(resultado.next()) {
                if (resultado.getInt("REGISTROS") > 0){
                    registros = resultado.getInt("REGISTROS");
                }else{
                    registros = 0;
                }
            }
        } catch (Exception e) {
        }
        
        return registros;
    }   
}