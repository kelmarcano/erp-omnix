/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

/**
 *
 * @author Kelvin Marcano
 */
public class control_existencias {
     private Sentencias_sql sen;
     private String Documento;
     private  String numero_factura="";

     public Object[] combox(String tabla, String campo)
     {
        return sen.poblar_combox(tabla, campo, "select "+campo+" from "+tabla+";");
     }
     
     public boolean registrar_producto(String Nnm_factura,String codigo,String descripcion,String cantidad, String total)
        {
        String[] datos = {Nnm_factura,codigo, descripcion, cantidad,total};
        
        //String[] datosP = {cantidad, id_articulo};
        
        sen.insertar(datos, "insert into dnfactura(codigo_factura,codigo_producto,descripion,cantidad,total) values(?,?,?,?,?);");
        /*
        if(sen.insertar(datosP, "update articulo set stock=stock-? where id_articulo=?;"))
        {
            return sen.insertar(datos, "insert into detalle_factura(cod_factura,cod_articulo,cantidad,total) values(?,?,?,?);");
        }
        else
        {
            return false;
        }
                */
         return false;
      }
     
     public Double total_factura(String numfact)
        {
        return sen.datos_totalfactura("total", "select round( sum( fac_total ) , 2 ) as total from dnmovfac where fac_numero='"+numfact+"';");
    }
     
           
      public void registrar_factura(String Nnm_factura, String Nombre_empleado, String fecha_facturacion, String cod_formapago)
     {
        String[] datos = {Nnm_factura, Documento, Nombre_empleado,fecha_facturacion,cod_formapago};
        sen.insertar(datos, "insert into factura(Nnm_factura,cod_cliente,Nombre_empleado,fecha_facturacion, cod_formapago) values(?,?,?,?,?);");
    }
      
      public Object[][] datos_producto(String id_articulo)
     {
        String[] columnas={"descripcion","precio_venta","stock"};
        Object[][] resultado = sen.GetTabla(columnas, "articulo", "select descripcion, precio_venta, stock from articulo where id_articulo='"+id_articulo+"';");
        return resultado;
      }
      public Object[][] datos_detallefactura(String numero_factura, String codigo, String descripcion, String cantidad, String total)
     {
        String[] columnas={"codigo_factura","codigo_producto","descripcion","cantidad","total"};
       // Object[][] resultado = sen.GetTabla(columnas, "");
       // return resultado;
         return null;
        
      }

}
