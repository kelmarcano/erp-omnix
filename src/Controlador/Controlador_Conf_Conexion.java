package Controlador;

import Modelos.Modelo_Conf_Conexion;

public class Controlador_Conf_Conexion {
    private String ip, user, passw, basedatos, manejador;
    Modelo_Conf_Conexion ModeloConexion = new Modelo_Conf_Conexion();

    public void setDatosConex(String Ip, String User, String Pass, String Bd, String Manejador, String MacPc, String IpPc){
        ModeloConexion.setIpPuertoBd(Ip);
        ModeloConexion.setUserBd(User);
        ModeloConexion.setPassBd(Pass);
        ModeloConexion.setBd(Bd);
        ModeloConexion.setManejador(Manejador);
        
        ModeloConexion.setMacPc(MacPc);
        ModeloConexion.setIpPc(IpPc);
    }
    
    public void DatosConex(){
        ModeloConexion.getConsultaConex();
    }
    
    public int ExisteConex(){
        int result = ModeloConexion.getExisteConexion();

        return result;
    }
    
    public int ExisteConexLocal(String MacPc){
        ModeloConexion.setMacPc(MacPc);
        int result = ModeloConexion.getExisteConexion();

        return result;
    }
    
    public void GuardaConexion(){
        ModeloConexion.setGuardaConex();
    }
}
