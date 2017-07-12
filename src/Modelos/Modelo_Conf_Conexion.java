package Modelos;

import clases.SQLQuerys;
import clases.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Modelo_Conf_Conexion {
    private String IpPuertoBd, UserBd, PassBd, Bd, Manejador, MacPc, IpPc;
    private int TabManejador;
    private ResultSet rs;
    
    private conexion conet = new conexion();
    
    public void setIpPuertoBd(String ipPuertoBd){
        IpPuertoBd = ipPuertoBd;
    }
    
    public void setUserBd(String userbd){
        UserBd = userbd;
    }
    
    public void setPassBd(String passbd){
        PassBd = passbd;
    }
    
    public void setBd(String bd){
        Bd = bd;
    }
    
    public void setManejador(String manejador){
        Manejador = manejador;
    }
    
    public void setTabManejador(int Tab){
        TabManejador = Tab;
    }
    
    public void setMacPc(String macpc){
        MacPc = macpc;
    }
    
    public void setIpPc(String ippc){
        IpPc = ippc;
    }
    
    public String getIpPuertoBd(){
        return IpPuertoBd;
    }
    
    public String getUserBd(){
        return UserBd;
    }
    
    public String getPassBd(){
        return PassBd;
    }
    
    public String getBd(){
        return Bd;
    }
    
    public String getManejador(){
        return Manejador;
    }
    
    public void getConsultaConex(){
        String sql = ("SELECT * FROM DNCONEXION");
        try {
            rs = conet.consultar(sql);
            
            if (rs.getRow()>0){
                IpPuertoBd = rs.getString("CONF_IP").trim();
                UserBd = rs.getString("CONF_USER").trim(); 
                PassBd = rs.getString("CONF_PASS").trim();
                Bd = rs.getString("CONF_BD").trim();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo_Conf_Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Modelo_Conf_Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getExisteConexion(){
        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNCONEXION WHERE CONF_MACPC='"+IpPuertoBd+"' AND "+
                        "CONF_BD='"+Bd+"' AND CONF_MANEJADOR='"+Manejador+"'";

        int Reg_count = conet.Count_Reg(SQLReg);

        return Reg_count;
    }
    
    public int getExisteConexionLocal(){
        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNCONEXION WHERE CONF_MACPC='"+MacPc+"' AND CONF_IP='"+IpPuertoBd+"' AND "+
                        "CONF_USER='"+UserBd+"' AND CONF_PASS='"+PassBd+"' AND CONF_BD='"+Bd+"' AND CONF_MANEJADOR='"+Manejador+"'";
        int Reg_count = conet.Count_Reg(SQLReg);

        return Reg_count;
    }
    
    public void setGuardaConex(){
        SQLQuerys insertarconex = new SQLQuerys();
        String SQL = "INSERT INTO DNCONEXION (CONF_IPPC,CONF_MACPC,CONF_IP,CONF_USER,CONF_PASS,CONF_BD,CONF_MANEJADOR) "+
                                     "VALUES ('"+IpPc+"','"+MacPc+"','"+IpPuertoBd+"','"+UserBd+"','"+PassBd+"','"+Bd+"','"+Manejador+"')";

        insertarconex.SqlInsert(SQL);
    }
}