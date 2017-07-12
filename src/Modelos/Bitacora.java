package Modelos;

import clases.SQLQuerys;

public class Bitacora {
    SQLQuerys insertar = new SQLQuerys();
    
    public Bitacora(String CodEmp, String MacPc, String IdUser, String NomUser, String accion, String detalle){
        String sql = "INSERT INTO DNBITACORA (BIT_EMPRESA,BIT_MACPC,BIT_CODUSER,BIT_USUARIO,BIT_ACCION,BIT_DETALLES,BIT_APPORG) "+
                                     "VALUES ('"+CodEmp+"','"+MacPc+"',"+IdUser+",'"+NomUser+"','"+accion+"','"+detalle+"','App Desktop')";
        
        System.out.println(sql);
        insertar.SqlInsert(sql);
    }
}