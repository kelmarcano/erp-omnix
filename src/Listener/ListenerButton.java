package Listener;

import Vista.Maestros;
import Modelos.ModelActionListener;
import Pantallas.Empresas;
import Vista.Productos;
import Vista.TipDocumentos;
import Vista.Moneda;
import Vista.UnidadMedida;
import Vista.Precio;
import Vista.GruposPermisos;
import Vista.CrearUsuarios;
import Vista.Actividad;
import Vista.CentroCosto;
import Vista.ComprobanteDif;
import Vista.PlanCuentas;
import Vista.EstructuraCtas;
import Vista.TipoContacto;
import Vista.TipoMaestros;
import java.awt.event.ActionEvent;

public class ListenerButton implements java.awt.event.ActionListener {
    ModelActionListener prueba = ModelActionListener.getPrueba();
    private static int atras=-2, adelante=0, tabs;
    private static boolean Bandera = false, lAgregar=true;
    private static String tabla = null;
    
    //Clases
    private Actividad act = null;
    private TipoMaestros tm = null;
    private TipoContacto tc = null;
    private CrearUsuarios cu = null;
    private GruposPermisos gp = null;
    private Moneda md = null;
    private Precio pc = null;
    private UnidadMedida um = null;
    private TipDocumentos td = null;
    private Productos pd = null;
    private Maestros mt = null;
    private PlanCuentas plc = null;
    private EstructuraCtas ec = null;
    private Empresas emp = null;
    private CentroCosto cencos = null;
    private ComprobanteDif comdif = null;

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            case "Agregar":
                lAgregar=true;

                prueba.setAccion("Inicializa");
                prueba.habilitar();
                prueba.posicion_botones_2();
                prueba.action_bt_agregar();
                prueba.setConsecutivo();
            
                break;
            case "Modificar":
                lAgregar=false;

                prueba.setAccion("Modificar");
                prueba.habilitar();
                prueba.posicion_botones_2();
                prueba.action_bt_modificar();
            
                break;
            case "Grabar":
                prueba.action_bt_grabar(lAgregar);
                
                break;
            case "Eliminar":
                prueba.action_bt_eliminar();

                break;
            case "Cancelar":
                int eleccion = 0;

                if (lAgregar==true){
                    prueba.action_bt_cancelar(lAgregar);
                }else if (lAgregar==false){
                    prueba.action_bt_cancelar(lAgregar);
                }
                
                break;
            case "Anterior":
                if (prueba.getTabbedPane()!=null){
                    tabs = prueba.getTabbedPane().getTabCount();
                }
                
                try {
                    if(atras==-1){
                        return;
                    }

                    if (atras==-2){
                        if (prueba.getTabbedPane()==null){
                            atras=prueba.getTabla().getRowCount()-1;
                        }else{
                            if (tabs==2){
                                if (prueba.getTabbedPane().getSelectedIndex()==0){
                                    atras=prueba.getTabla().getRowCount()-1;
                                    tabla = "Ventas";
                                }else if (prueba.getTabbedPane().getSelectedIndex()==1){
                                    atras=prueba.getTabla2().getRowCount()-1;
                                    tabla = "Compras";
                                }
                            }else if (tabs==4){
                                if (prueba.getTabbedPane().getSelectedIndex()==0){
                                    atras=prueba.getTabla().getRowCount()-1;
                                    tabla = "Clientes";
                                }else if (prueba.getTabbedPane().getSelectedIndex()==1){
                                    atras=prueba.getTabla2().getRowCount()-1;
                                    tabla = "Proveedores";
                                }else if (prueba.getTabbedPane().getSelectedIndex()==2){
                                    atras=prueba.getTabla3().getRowCount()-1;
                                    tabla = "Otros";
                                }else if (prueba.getTabbedPane().getSelectedIndex()==3){
                                    atras=prueba.getTabla4().getRowCount()-1;
                                    tabla = "Todos";
                                }
                            }
                        }
                        adelante=0;
                    }

                    atras=atras-1;
                    adelante=adelante-1;
                    System.out.println("Atras: "+atras);

                    if (atras!=-2){
                        if (tabs>=2){
                            prueba.actualizaJtableTipDoc(tabla, atras);
                        }else{
                            prueba.actualizaJtable(atras);
                        }
                    }
                } catch (Exception e) {
                }
                
                break;
            case "Adelante":
                int Reg=0;
                if (prueba.getTabbedPane()!=null){
                    tabs = prueba.getTabbedPane().getTabCount();
                }
                
                if (prueba.getTabbedPane()==null){
                    Reg=prueba.getTabla().getRowCount();
                }else{
                    if (tabs==2){
                        if (prueba.getTabbedPane().getSelectedIndex()==0){
                            Reg=prueba.getTabla().getRowCount();
                            tabla = "Ventas";
                        }else if (prueba.getTabbedPane().getSelectedIndex()==1){
                            Reg=prueba.getTabla2().getRowCount();
                            tabla = "Compras";
                        }
                    }else if (tabs==4){
                        if (prueba.getTabbedPane().getSelectedIndex()==0){
                            Reg=prueba.getTabla().getRowCount();
                            tabla = "Clientes";
                        }else if (prueba.getTabbedPane().getSelectedIndex()==1){
                            Reg=prueba.getTabla2().getRowCount();
                            tabla = "Proveedores";
                        }else if (prueba.getTabbedPane().getSelectedIndex()==2){
                            Reg=prueba.getTabla3().getRowCount();
                            tabla = "Otros";
                        }else if (prueba.getTabbedPane().getSelectedIndex()==3){
                            Reg=prueba.getTabla4().getRowCount();
                            tabla = "Todos";
                        }
                    }
                }

                if (atras==-2){
                    adelante=Reg;
                }

                try {
                    if (adelante==Reg){
                        atras=Reg-1;
                        if (tabs>=2){
                            //prueba.actualizaJtableTipDoc(tabla, Reg);
                            prueba.actualizaJtableTipDoc(tabla, adelante-1);
                        }else{
                            prueba.actualizaJtable(adelante-1);
                        }
                        
                        return;
                    }

                    if (atras==-1 || atras==-2){
                        adelante=1;
                        atras=1;
                    }else{
                        if (adelante<Reg){
                            adelante=atras+1;
                            atras=atras+1;
                        }
                    }

                    System.out.println("Adelante: "+adelante);
                
                    if (adelante<Reg){
                        if (tabs>=2){
                            //prueba.actualizaJtableTipDoc(tabla, Reg);
                            prueba.actualizaJtableTipDoc(tabla, adelante);
                        }else{
                            prueba.actualizaJtable(adelante);
                        }
                    }
                } catch (Exception e) {
                }
            
                break;
            case "Salir":
                if (prueba.getaThis() instanceof Actividad) {
                    act = (Actividad) prueba.getaThis(); 
                    act.dispose();
                }else if (prueba.getaThis() instanceof TipoMaestros) {
                    tm = (TipoMaestros) prueba.getaThis(); 
                    tm.dispose();
                }else if (prueba.getaThis() instanceof TipoContacto) {
                    tc = (TipoContacto) prueba.getaThis(); 
                    tc.dispose();
                }else if (prueba.getaThis() instanceof CrearUsuarios) {
                    cu = (CrearUsuarios) prueba.getaThis(); 
                    cu.dispose();
                }else if (prueba.getaThis() instanceof GruposPermisos) {
                    gp = (GruposPermisos) prueba.getaThis(); 
                    gp.dispose();
                }else if (prueba.getaThis() instanceof Moneda) {
                    md = (Moneda) prueba.getaThis(); 
                    md.dispose();
                }else if (prueba.getaThis() instanceof Precio) {
                    pc = (Precio) prueba.getaThis(); 
                    pc.dispose();
                }else if (prueba.getaThis() instanceof UnidadMedida) {
                    um = (UnidadMedida) prueba.getaThis(); 
                    um.dispose();
                }else if (prueba.getaThis() instanceof TipDocumentos) {
                    td = (TipDocumentos) prueba.getaThis(); 
                    td.dispose();
                }else if (prueba.getaThis() instanceof Productos) {
                    pd = (Productos) prueba.getaThis(); 
                    pd.dispose();
                }else if (prueba.getaThis() instanceof Maestros) {
                    mt = (Maestros) prueba.getaThis(); 
                    mt.dispose();
                }else if (prueba.getaThis() instanceof PlanCuentas) {
                    plc = (PlanCuentas) prueba.getaThis(); 
                    plc.dispose();
                }else if (prueba.getaThis() instanceof EstructuraCtas) {
                    ec = (EstructuraCtas) prueba.getaThis(); 
                    ec.dispose();
                }else if (prueba.getaThis() instanceof Empresas) {
                    emp = (Empresas) prueba.getaThis(); 
                    emp.dispose();
                }else if(prueba.getaThis() instanceof CentroCosto){
                    cencos = (CentroCosto) prueba.getaThis();
                    cencos.dispose();
                }else if(prueba.getaThis() instanceof ComprobanteDif){
                    comdif = (ComprobanteDif) prueba.getaThis();
                    comdif.dispose();
                }
                
                break;
        }
    }
}
