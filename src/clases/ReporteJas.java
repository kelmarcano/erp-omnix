package clases;

import Modelos.VariablesGlobales;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteJas {
    static VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public static final String DRIVER="com.mysql.jdbc.Driver";
    public static final String RUTA="jdbc:mysql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos();
    public static final String USER=VarGlobales.getUserServer();
    public static final String PASSWORD=VarGlobales.getPasswServer();
    public static Connection CONEXION;
        
    public void startReportProve(){
        try{
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA,USER,PASSWORD);
            javax.swing.JOptionPane.showMessageDialog(null,"´Procesando Reporte......");
            
            String template="\\informes\\Factura.jasper";
            JasperReport reporte=null;
            reporte=(JasperReport) JRLoader.loadObjectFromLocation(template);
           
            Map param=new HashMap();
            
            JasperPrint jasperprint= JasperFillManager.fillReport(reporte,param,CONEXION);
            JasperViewer visor=new JasperViewer(jasperprint,false);
            visor.setTitle("Factura");
            visor.setVisible(true);
            visor.show();
        }catch(Exception e){
            System.out.println("hola2");
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }
}
