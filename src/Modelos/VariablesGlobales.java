package Modelos;

public class VariablesGlobales {
    private static VariablesGlobales variablesGlobales;
    private String idUser, userNombre, ipPc, macPc, codEmpresa, grupoPermisos;
    private String ipServer, userServer, passwServer, basedatos, manejador, nombEmpresa;
    private Boolean lPos, lErp, lHcm, lHcs, lFdv, lEcom, lGp;

    private VariablesGlobales() {
    }
    
    public static VariablesGlobales getDatosGlobales(){
        if (variablesGlobales == null){
            variablesGlobales = new VariablesGlobales();
        }

        return variablesGlobales;
    }
    
/*
    Implementancion de Metos Setter y Getter
*/
    public void setIdUser(String idUser){
        this.idUser = idUser;
    }
    
    public String getIdUser(){
        return idUser;
    }
    
    public void setUserName(String userName){
        this.userNombre = userName;
    }
    
    public String getUserName(){
        return userNombre;
    }
    
    public void setIpPc(String ipPc){
        this.ipPc = ipPc;
    }
    
    public String getIpPc(){
        return ipPc;
    }
    
    public void setMacPc(String macPc){
        this.macPc = macPc;
    }
    
    public String getMacPc(){
        return macPc;
    }
    
    public void setCodEmpresa(String codEmpresa){
        this.codEmpresa = codEmpresa;
    }
    
    public String getCodEmpresa(){
        return codEmpresa;
    }
    
    public void setNombEmpresa(String nombEmpresa){
        this.nombEmpresa = nombEmpresa;
    }
    
    public String getNombEmpresa(){
        return nombEmpresa;
    }
    
    public void setGrupPermiso(String grupoPermiso){
        this.grupoPermisos = grupoPermiso;
    }
    
    public String getGrupPermiso(){
        return grupoPermisos;
    }
    
    public void setIpServer(String ipConex){
        this.ipServer = ipConex;
    }
    
    public String getIpServer(){
        return ipServer;
    }
    
    public void setUserServer(String userConex){
        this.userServer = userConex;
    }
    
    public String getUserServer(){
        return userServer;
    }
    
    public void setPasswServer(String passwConex){
        this.passwServer = passwConex;
    }
    
    public String getPasswServer(){
        return passwServer;
    }
    
    public void setBaseDatos(String bdConex){
        this.basedatos = bdConex;
    }
    
    public String getBaseDatos(){
        return basedatos;
    }
    
    public void setManejador(String manejadorConex){
        this.manejador = manejadorConex;
    }
    
    public String getManejador(){
        return manejador;
    }
    
    public void setPos(Boolean lPos){
        this.lPos = lPos;
    }
    
    public Boolean getPos(){
        return lPos;
    }
    
    public void setErp(Boolean lErp){
        this.lErp = lErp;
    }
    
    public Boolean getErp(){
        return lErp;
    }
    
    public void setHcm(Boolean lHcm){
        this.lHcm = lHcm;
    }
    
    public Boolean getHcm(){
        return lHcm;
    }
    
    public void setHcs(Boolean lHcs){
        this.lHcs = lHcs;
    }
    
    public Boolean getHcs(){
        return lHcs;
    }
    
    public void setFuerzadVentas(Boolean lFdv){
        this.lFdv = lFdv;
    }
    
    public Boolean getFuerzadVentas(){
        return lFdv;
    }
    
    public void setEcommers(Boolean lEcom){
        this.lEcom = lEcom;
    }
    
    public Boolean getEcommers(){
        return lEcom;
    }
    
    public void setGestoProyect(Boolean lGep){
        this.lGp = lGep;
    }
    
    public Boolean getGestoProyect(){
        return lGp;
    }
}