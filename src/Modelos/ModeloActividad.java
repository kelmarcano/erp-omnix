package Modelos;

import Controlador.ControladorActividad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

public class ModeloActividad {
    private String codigo, descrip, claseOrg, activo;
    private Boolean operacion;
    private JTable tabla;
    
    ControladorActividad actividad = new ControladorActividad();

    public ModeloActividad() {
    }

    public JTable getTabla() {
        return tabla;
    }

    public Boolean isOperacion() {
        return operacion;
    }

    public void setOperacion(Boolean operacion) {
        this.operacion = operacion;
    }
    
    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public String getClaseOrg() {
        return claseOrg;
    }

    public void setClaseOrg(String claseOrg) {
        this.claseOrg = claseOrg;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
    public void setSave(){
        actividad.save(codigo, descrip, activo, tabla, operacion, claseOrg);
    }
    
    public void setDelete(){
        actividad.delete(codigo, tabla, claseOrg);
    }
    
    public void setCargaTabla(){
        actividad.cargaTabla(tabla, claseOrg);
    }
    
    public String getConsecutivo(){
        String consecutivo = actividad.codConsecutivo();
        
        return consecutivo;
    }
    
    public void setCargaDatos() throws ClassNotFoundException, SQLException{
        ResultSet rs = actividad.Cargardatos(codigo, operacion);
        
        setCodigo(rs.getString("ACT_CODIGO").trim());
        setDescrip(rs.getString("ACT_NOMBRE").trim()); 
        if (rs.getBoolean("ACT_ACTIVO")==true){
            setActivo("1");
        }else{
            setActivo("0");
        }
    }
    
    public void setHilo(){
        try {
            ResultSet rs = actividad.ejecutaHilo(codigo, operacion);
            
//        if (Reg_count > 0){
            setCodigo(rs.getString("ACT_CODIGO").trim());
            setDescrip(rs.getString("ACT_NOMBRE").trim()); 
            if (rs.getBoolean("ACT_ACTIVO")==true){
                setActivo("1");
            }else{
                setActivo("0");
            }
//        }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
