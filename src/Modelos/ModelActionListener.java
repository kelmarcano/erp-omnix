package Modelos;

import Controlador.ControladorActividad;
import Controlador.ControladorCentroCosto;
import Controlador.ControladorComprobanteDif;
import Controlador.ControladorCrearUsuarios;
import Controlador.ControladorEmpresa;
import Controlador.ControladorEstructuraCuentas;
import Controlador.ControladorGrupoPermisos;
import Controlador.ControladorMaestros;
import Controlador.ControladorMoneda;
import Controlador.ControladorPlanCuentas;
import Controlador.ControladorPrecio;
import Controlador.ControladorProductos;
import Controlador.ControladorReglasContables;
import Controlador.ControladorTipoContacto;
import Controlador.ControladorTipoDocumentos;
import Controlador.ControladorTipoMaestros;
import Controlador.ControladorUnidadMedida;
import Pantallas.Empresas;
import Vista.Actividad;
import Vista.CentroCosto;
import Vista.ComprobanteDif;
import Vista.CrearUsuarios;
import Vista.EstructuraCtas;
import Vista.GruposPermisos;
import Vista.Maestros;
import Vista.Moneda;
import Vista.Precio;
import Vista.Productos;
import Vista.TipDocumentos;
import Vista.TipoContacto;
import Vista.TipoMaestros;
import Vista.UnidadMedida;
import Vista.PlanCuentas;
import Vista.ReglasContabilidad;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import clases.CreaValidaRif;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ModelActionListener {
    private static ModelActionListener prueba;
    private String accion, claseOrg, claveUser;
    private JLabel imgDesktUser, codigoCta;
    private JCheckBox activo, accAndroid, accWeb, calcIva, retIslr, retIva, invFis, invLog, addMenu;
    private JTable tabla1, tabla2, tabla3, tabla4;
    private JTextField codigo, nombre, clave, usuario, correo, rutaImg, nomenclatura, codProd, codUnd, tipoPrecio;
    private JTextField cantdUnd, peso, volumen, tipoMaestro, activEco, tipoMoneda;
    private JTextField numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber;
    private JTextArea descripcion;
    private JPasswordField clave1;
    private JComboBox combo1, combo2, combo3, combo4, combo5, combo6, combo7, combo8, combo9;
    private JDateChooser fecha;
    private JTabbedPane tabbedPane=null;
    private JFormattedTextField valor1, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva;
    private JRadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private JButton agregar, modificar, grabar, eliminar, cancelar, buscar, atras, adelante, salir;
    private JButton boton1, boton2, boton3;
    
    private Boolean lAddEdit=false;

    // Clases
    private Object aThis;
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
    private CentroCosto cen = null;
    private ComprobanteDif comp= null;
    private ReglasContabilidad reg = null;

    private Vector Msg, elementos, header_table;
    
    // Controladores
    ControladorActividad actividad = new ControladorActividad();
    ControladorTipoMaestros tipoMaestros = new ControladorTipoMaestros();
    ControladorTipoContacto tipoContacto = new ControladorTipoContacto();
    ControladorCrearUsuarios crearUsuarios = new ControladorCrearUsuarios();
    ControladorGrupoPermisos grupoPermisos =new ControladorGrupoPermisos();
    ControladorMoneda moneda = new ControladorMoneda();
    ControladorPrecio precio = new ControladorPrecio();
    ControladorUnidadMedida unidadMedida = new ControladorUnidadMedida();
    ControladorTipoDocumentos tipoDocumentos = new ControladorTipoDocumentos();
    ControladorProductos productos = new ControladorProductos();
    ControladorMaestros maestros = new ControladorMaestros();
    ControladorPlanCuentas planCuentas = new ControladorPlanCuentas();
    ControladorEstructuraCuentas estructuraCuentas = new ControladorEstructuraCuentas();
    ControladorEmpresa empresa = new ControladorEmpresa();
    ControladorCentroCosto centro = new ControladorCentroCosto();
    ControladorComprobanteDif comproba = new ControladorComprobanteDif();
    ControladorReglasContables reglas = new ControladorReglasContables();
    
    private ModelActionListener() {
    }
    
    public static ModelActionListener getPrueba(){
        if (prueba == null){
            prueba = new ModelActionListener();
        }

        return prueba;
    }
    
    public void setClass(Object aThis){
        this.aThis = aThis;
    }
    
    public void setJTextArea(JTextArea descripcion){
        this.descripcion = descripcion;
    }
    
    public void setJTextField(JTextField codigo, JTextField nombre, JTextField clave, JPasswordField clave1, 
                              JTextField usuario, JTextField correo, JTextField rutaImg, JTextField nomenclatura) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.clave = clave;
        this.clave1 = clave1;
        this.usuario = usuario;
        this.correo = correo;
        this.rutaImg = rutaImg;
        this.nomenclatura = nomenclatura;
    }
    
    public void setJTextFieldMaestros(JTextField codigo, JTextField nombre, JTextField tipoMaestro, JTextField activEco, 
                                      JTextField tipoMoneda) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoMaestro = tipoMaestro; 
        this.activEco = activEco;
        this.tipoMoneda = tipoMoneda;
    }
    
    public void setJTextFieldPrecio(JTextField codigo, JTextField codProd, JTextField tipoPrecio, 
                                    JTextField codUnd) {
        this.codigo = codigo;
        this.codProd = codProd;
        this.tipoPrecio = tipoPrecio;
        this.codUnd = codUnd;
    }
    
    public void setJTextFieldProductos(JTextField codigo, JTextField nombre, JTextField codProd, 
                                       JTextField rutaImg) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codProd = codProd;
        this.rutaImg = rutaImg;
    }
    
    public void setJTextFieldCentroCosto(JTextField codigo, JTextField nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    public void setJTextFieldUnidadM(JTextField codigo, JTextField nombre, JTextField cantUnid, 
                                    JTextField peso, JTextField volumen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantdUnd = cantUnid;
        this.peso = peso;
        this.volumen = volumen;
    }
    
    public void setJTextFieldComprobanteDif(JTextField numComp, JTextField numDoc, JTextField docMonto, JTextField docDescri, 
                                            JTextField ctaNum,JTextField ctaDescri, JTextField ctaMonto, JTextField debe, 
                                            JTextField haber){
        
        this.numComp = numComp;
        this.numDoc = numDoc;
        this.docMonto = docMonto;
        this.docDescri = docDescri;
        this.ctaNum = ctaNum;
        this.ctaDescri = ctaDescri;
        this.ctaMonto = ctaMonto;
        this.debe = debe;
        this.haber = haber;
        
    }

    public void setJFormattedTextField(JFormattedTextField limitCred, JFormattedTextField limitCredVenc, 
                                       JFormattedTextField diasPlazo, JFormattedTextField rif, JFormattedTextField nit,
                                       JFormattedTextField descuento, JFormattedTextField diasVen, JFormattedTextField retInva){
        
        this.limitCred = limitCred;
        this.limitCredVenc = limitCredVenc;
        this.diasPlazo = diasPlazo;
        this.rif = rif;
        this.nit = nit;
        this.descuento = descuento;
        this.diasVen = diasVen;
        this.retencIva = retInva;
    }
    
        
    public void setValor(JFormattedTextField valor1) {
        this.valor1 = valor1;
    }

    public void setFecha(JDateChooser fecha) {
        this.fecha = fecha;
    }
    
    public void setJComboBox(JComboBox combo1, JComboBox combo2, JComboBox combo3, JComboBox combo4, JComboBox combo5){
        this.combo1 = combo1;
        this.combo2 = combo2;
        this.combo3 = combo3;
        this.combo4 = combo4;
        this.combo5 = combo5;
    }
    
     public void setJComboBoxReglas(JComboBox combo1, JComboBox combo2, JComboBox combo3){
        this.combo1 = combo1;
        this.combo2 = combo2;
        this.combo3 = combo3;
            }
    
    public void setJComboBoxCta(JComboBox combo1, JComboBox combo2, JComboBox combo3, JComboBox combo4, JComboBox combo5,
                                JComboBox combo6, JComboBox combo7, JComboBox combo8, JComboBox combo9){
        this.combo1 = combo1;
        this.combo2 = combo2;
        this.combo3 = combo3;
        this.combo4 = combo4;
        this.combo5 = combo5;
        this.combo6 = combo6;
        this.combo7 = combo7;
        this.combo8 = combo8;
        this.combo9 = combo9;
    }
    
    public void setJRadioButton(JRadioButton radioButton1, JRadioButton radioButton2, JRadioButton radioButton3,
                                JRadioButton radioButton4){
        this.radioButton1 = radioButton1;
        this.radioButton2 = radioButton2;
        this.radioButton3 = radioButton3;
        this.radioButton4 = radioButton4;
    }
    
    public void setImgDesktop(JLabel imgDesktUser){
        this.imgDesktUser = imgDesktUser;
    }

    public void setCodigoCta(JLabel codigoCta){
        this.codigoCta = codigoCta;
    }

    public void setJCheckBox(JCheckBox activo, JCheckBox accAndroid, JCheckBox accWeb) {
        this.activo = activo;
        this.accAndroid = accAndroid;
        this.accWeb = accWeb;
    }
    
    public void setJCheckBoxTipDoc(JCheckBox activo, JCheckBox calcIva, JCheckBox retIslr, JCheckBox retIva, 
                                   JCheckBox invFis, JCheckBox invLog, JCheckBox addMenu) {
        this.activo = activo;
        this.calcIva = calcIva;
        this.retIslr = retIslr;
        this.retIva = retIva;
        this.invFis = invFis;
        this.invLog = invLog;
        this.addMenu = addMenu;
    }
    
    public void setJTabbedPane(JTabbedPane tabbedPane){
        this.tabbedPane = tabbedPane;
    }
    
    public void setJTable(JTable tabla1, JTable tabla2, JTable tabla3, JTable tabla4){
        this.tabla1 = tabla1;
        this.tabla2 = tabla2;
        this.tabla3 = tabla3;
        this.tabla4 = tabla4;
    }
    
    public void setButton(JButton agregar, JButton modificar, JButton grabar, JButton eliminar, JButton cancelar,
                          JButton buscar, JButton atras, JButton adelante, JButton salir){
        
        this.agregar = agregar;
        this.modificar = modificar;
        this.grabar = grabar;
        this.eliminar = eliminar;
        this.cancelar = cancelar;
        this.buscar = buscar;
        this.atras = atras;
        this.adelante = adelante;
        this.salir = salir;
        
        colorButton();
    }
    
    public void setButtonOther(JButton boton1, JButton boton2, JButton boton3){
        this.boton1 = boton1;
        this.boton2 = boton2;
        this.boton3 = boton3;
        
        colorButtonOther();
    }

    public String getClaseOrg() {
        return claseOrg;
    }

    public void setClaseOrg(String claseOrg) {
        this.claseOrg = claseOrg;
    }
    
    public void setAccion(String accion){
        this.accion = accion;
    }

    public Object getaThis() {
        return aThis;
    }

    public void setaThis(Object aThis) {
        this.aThis = aThis;
    }
    
    public JTextField getCodigo(){
        return codigo;
    }
    
    public JTextField getNombre(){
        return nombre;
    }

    public JCheckBox getActivo() {
        return activo;
    }

    public JTable getTabla() {
        return tabla1;
    }
    
    public JTable getTabla2() {
        return tabla2;
    }

    public JTable getTabla3() {
        return tabla3;
    }
    
    public JTable getTabla4() {
        return tabla4;
    }
    
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
    
    public String getClave() {
        return claveUser;
    }

    public void Deshabilitar(){;
        if (aThis instanceof Actividad) {
            actividad.setComponentes(codigo, nombre, activo);
            actividad.deshabilitar();
        }else if (aThis instanceof TipoMaestros) {
            tipoMaestros.setComponentes(codigo, nombre, activo);
            tipoMaestros.deshabilitar();
        }else if (aThis instanceof TipoContacto) {
            tipoContacto.setComponentes(codigo, nombre, activo);
            tipoContacto.deshabilitar();
        }else if (aThis instanceof CrearUsuarios) {
            crearUsuarios.setComponentes(codigo, nombre, clave, clave1, usuario, correo, rutaImg, activo, accAndroid, accWeb, combo1, combo2, imgDesktUser);
            crearUsuarios.deshabilitar();
        }else if (aThis instanceof GruposPermisos) {
            grupoPermisos.setComponentes(codigo, nombre, activo, radioButton1, radioButton2, radioButton3);
            grupoPermisos.deshabilitar();
        }else if (aThis instanceof Moneda) {
            moneda.setComponentes(codigo, nombre, nomenclatura, valor1, fecha, activo);
            moneda.deshabilitar();
        }else if (aThis instanceof Precio) {
            precio.setComponentes(codigo, codProd, tipoPrecio, codUnd, valor1, fecha, activo, boton1, boton2, boton3);
            precio.deshabilitar();
        }else if (aThis instanceof UnidadMedida) {
            unidadMedida.setComponentes(codigo, nombre, cantdUnd, peso, volumen, activo, accAndroid, combo1);
            unidadMedida.deshabilitar();
        }else if (aThis instanceof TipDocumentos) {
            tipoDocumentos.setComponentes(codigo, nombre, radioButton1, radioButton2, radioButton3, radioButton4, activo, calcIva, retIslr, retIva, invFis, invLog, addMenu, combo1, tabbedPane);
            tipoDocumentos.deshabilitar();
        }else if (aThis instanceof Productos) {
            productos.setComponentes(codigo, nombre, codProd, rutaImg, descripcion, activo, combo1, combo2, combo3, combo4, boton1);
            productos.deshabilitar();
        }else if (aThis instanceof Maestros) {
            maestros.setComponentes(codigo, nombre, tipoMaestro, activEco, tipoMoneda, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva, descripcion, combo1, combo2, combo3, combo4, combo5, activo, accAndroid, accWeb, boton1, boton2, boton3, tabbedPane, fecha);
            maestros.deshabilitar();
        }else if (aThis instanceof PlanCuentas) {
            planCuentas.deshabilitar();
        }else if (aThis instanceof EstructuraCtas) {
            estructuraCuentas.setComponentes(combo1, combo2, combo3, combo4, combo5);
            estructuraCuentas.deshabilitar();
        }else if (aThis instanceof Empresas) {
            empresa.setComponentes(codigo, nombre, rif, descripcion, activo);
            empresa.deshabilitar();
        }else if (aThis instanceof CentroCosto) {
            centro.setComponentes(codigo, nombre, fecha);
            centro.deshabilitar();
        }else if (aThis instanceof ComprobanteDif) {
            comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            comproba.deshabilitar();
        }else if (aThis instanceof ReglasContabilidad) {
            //comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            //comproba.deshabilitar();
        }
        
    }
     
    public void habilitar(){
        if (aThis instanceof Actividad) {
            actividad.setComponentes(codigo, nombre, activo);
            actividad.habilitar(accion);
        }else if (aThis instanceof TipoMaestros) {
            tipoMaestros.setComponentes(codigo, nombre, activo);
            tipoMaestros.habilitar(accion);
        }else if (aThis instanceof TipoContacto) {
            tipoContacto.setComponentes(codigo, nombre, activo);
            tipoContacto.habilitar(accion);
        }else if (aThis instanceof CrearUsuarios) {
            crearUsuarios.setComponentes(codigo, nombre, clave, clave1, usuario, correo, rutaImg, activo, accAndroid, accWeb, combo1, combo2, imgDesktUser);
            crearUsuarios.habilitar(accion);
        }else if (aThis instanceof GruposPermisos) {
            grupoPermisos.setComponentes(codigo, nombre, activo, radioButton1, radioButton2, radioButton3);
            grupoPermisos.habilitar(accion);
        }else if (aThis instanceof Moneda) {
            moneda.setComponentes(codigo, nombre, nomenclatura, valor1, fecha, activo);
            moneda.habilitar(accion);
        }else if (aThis instanceof Precio) {
            precio.setComponentes(codigo, codProd, tipoPrecio, codUnd, valor1, fecha, activo, boton1, boton2, boton3);
            precio.habilitar(accion);
        }else if (aThis instanceof UnidadMedida) {
            unidadMedida.setComponentes(codigo, nombre, cantdUnd, peso, volumen, activo, accAndroid, combo1);
            unidadMedida.habilitar(accion);
        }else if (aThis instanceof TipDocumentos) {
            tipoDocumentos.setComponentes(codigo, nombre, radioButton1, radioButton2, radioButton3, radioButton4, activo, calcIva, retIslr, retIva, invFis, invLog, addMenu, combo1, tabbedPane);
            tipoDocumentos.habilitar(accion);
        }else if (aThis instanceof Productos) {
            productos.setComponentes(codigo, nombre, codProd, rutaImg, descripcion, activo, combo1, combo2, combo3, combo4, boton1);
            productos.habilitar(accion);
        }else if (aThis instanceof Maestros) {
            //maestros.setComponentes(codigo, nombre, tipoMaestro, activEco, tipoMoneda, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva, descripcion, combo1, combo2, combo3, combo4, combo5, activo, accAndroid, accWeb, boton1, boton2, boton3, tabbedPane, fecha);
            maestros.habilitar(accion);
        }else if (aThis instanceof PlanCuentas) {
            planCuentas.habilitar(accion);
        }else if (aThis instanceof EstructuraCtas) {
            estructuraCuentas.setComponentes(combo1, combo2, combo3, combo4, combo5);
            estructuraCuentas.habilitar(accion);
        }else if (aThis instanceof Empresas) {
            empresa.setComponentes(codigo, nombre, rif, descripcion, activo);
            empresa.habilitar(accion);
        }else if (aThis instanceof CentroCosto) {
            centro.setComponentes(codigo, nombre, fecha);
            centro.habilitar(accion);
        }else if (aThis instanceof ComprobanteDif) {
            comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            comproba.habilitar(accion);
        }else if (aThis instanceof ReglasContabilidad) {
            //comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            //comproba.habilitar(accion);
        }
        
    }
    
    public void setConsecutivo(){
        String consecutivo = null;
        
        if (aThis instanceof Actividad) {
            consecutivo = actividad.codConsecutivo();
        }else if (aThis instanceof TipoMaestros) {
            consecutivo = tipoMaestros.codConsecutivo();
        }else if (aThis instanceof TipoContacto) {
            consecutivo = tipoContacto.codConsecutivo();
        }else if (aThis instanceof CrearUsuarios) {
            try {
                crearUsuarios.setComponentes(codigo, nombre, clave, clave1, usuario, correo, rutaImg, activo, accAndroid, accWeb, combo1, combo2, imgDesktUser);
                crearUsuarios.mostrarImagen(null);
                consecutivo = crearUsuarios.codConsecutivo();
            } catch (SQLException ex) {
                Logger.getLogger(ModelActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (aThis instanceof GruposPermisos) {
            consecutivo = grupoPermisos.codConsecutivo();
        }else if (aThis instanceof Moneda) {
            consecutivo = moneda.codConsecutivo();
        }else if (aThis instanceof Precio) {
            consecutivo = precio.codConsecutivo();
        }else if (aThis instanceof UnidadMedida) {
            consecutivo = unidadMedida.codConsecutivo();
        }else if (aThis instanceof Productos) {
            consecutivo = productos.codConsecutivo();
        }else if (aThis instanceof Maestros) {
            consecutivo = maestros.codConsecutivo();
        }else if (aThis instanceof Empresas) {
            consecutivo = empresa.codConsecutivo();
        }else if (aThis instanceof CentroCosto) {
            consecutivo = centro.codConsecutivo();
        }else if (aThis instanceof ComprobanteDif) {
            consecutivo = centro.codConsecutivo();
        }
        
        codigo.setText(consecutivo);
//        nombre.requestFocus();
    }
    
    public void posicion_botones_1(){
        agregar.setEnabled(true);
        
        modificar.setVisible(true); buscar.setVisible(true); atras.setVisible(true);
        adelante.setVisible(true); salir.setVisible(true); eliminar.setVisible(true);
        
        grabar.setVisible(false); cancelar.setVisible(false);
    }
    
    public void posicion_botones_2(){
        agregar.setEnabled(false);
        
        modificar.setVisible(false); buscar.setVisible(false); atras.setVisible(false);
        adelante.setVisible(false); salir.setVisible(false); eliminar.setVisible(false);
        
        grabar.setVisible(true); cancelar.setVisible(true);
    }
    
    public void cargaTabla(){
        if (aThis instanceof Actividad) {
            actividad.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof TipoMaestros) {
            tipoMaestros.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof TipoContacto) {
            tipoContacto.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof CrearUsuarios) {
            crearUsuarios.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof GruposPermisos) {
            grupoPermisos.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof Moneda) {
            moneda.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof Precio) {
            precio.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof UnidadMedida) {
            unidadMedida.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof TipDocumentos) {
            tipoDocumentos.setComponentes(codigo, nombre, radioButton1, radioButton2, radioButton3, radioButton4, activo, calcIva, retIslr, retIva, invFis, invLog, addMenu, combo1, tabbedPane);
            tipoDocumentos.cargaTabla(tabla1, tabla2, claseOrg);
        }else if (aThis instanceof Productos) {
            productos.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof Maestros) {
            maestros.setComponentes(codigo, nombre, tipoMaestro, activEco, tipoMoneda, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva, descripcion, combo1, combo2, combo3, combo4, combo5, activo, accAndroid, accWeb, boton1, boton2, boton3, tabbedPane, fecha);
            maestros.cargaTabla(tabla1, tabla2, tabla3, tabla4, claseOrg);
        }else if (aThis instanceof PlanCuentas) {
            planCuentas.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof EstructuraCtas) {
            estructuraCuentas.cargaTabla(tabla1, claseOrg);
        }else if (aThis instanceof Empresas) {
            empresa.cargaTabla(tabla1, claseOrg);
        }else if(aThis instanceof CentroCosto){
            centro.cargaTabla(tabla1, claseOrg);
        }else if(aThis instanceof ComprobanteDif){
            comproba.cargaTabla(tabla1, claseOrg);
        }else if(aThis instanceof ReglasContabilidad){
            reglas.cargaTabla(tabla1, tabla2, claseOrg);
        }
    }
    
    public void ejecutaHilo(){
        if (tabbedPane!=null){
            cargaDatos("",-1);
        }else{
            cargaDatos(-1);
        }
    }
    
    public void actualizaJtable(int item){
        cargaDatos(item);
        tabla1.getSelectionModel().setSelectionInterval(item, item);
        
    }
    
    public void actualizaJtableTipDoc(String tabla, int item){
        cargaDatos(item);
        
        if (tabbedPane.getSelectedIndex()==0){
            cargaDatos(tabla, item);
            tabla1.getSelectionModel().setSelectionInterval(item, item);
        }else if (tabbedPane.getSelectedIndex()==1){
            cargaDatos(tabla, item);
            tabla2.getSelectionModel().setSelectionInterval(item, item);
        }else if (tabbedPane.getSelectedIndex()==2){
            cargaDatos(tabla, item);
            tabla3.getSelectionModel().setSelectionInterval(item, item);
        }else if (tabbedPane.getSelectedIndex()==3){
            cargaDatos(tabla, item);
            tabla4.getSelectionModel().setSelectionInterval(item, item);
        }
    }
    
    private void cargaDatos(int Row){
        Boolean lOperacion =false;
        ResultSet rs = null;
        String idetificador = null, code = null;
        
        if (Row>=0){
            code = (String) tabla1.getValueAt(Row,0).toString().trim();
            lOperacion = true;
        }
        
        if (aThis instanceof Actividad) {
            actividad.setComponentes(codigo, nombre, activo);
            actividad.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof TipoMaestros) {
            tipoMaestros.setComponentes(codigo, nombre, activo);
            tipoMaestros.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof TipoContacto) {
            tipoContacto.setComponentes(codigo, nombre, activo);
            tipoContacto.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof CrearUsuarios) {
            crearUsuarios.setComponentes(codigo, nombre, clave, clave1, usuario, correo, rutaImg, activo, accAndroid, accWeb, combo1, combo2, imgDesktUser);
            rs = crearUsuarios.ejecutaHilo(code, lOperacion);
            
            try {
                claveUser = rs.getString("OPE_CLAVE").trim();
        
                if (Row>=0 || lAddEdit==true){
                    crearUsuarios.mostrarImagen(rs.getString("OPE_RUTAIMG"));
                    lAddEdit=false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (aThis instanceof GruposPermisos) {
            grupoPermisos.setComponentes(codigo, nombre, activo, radioButton1, radioButton2, radioButton3);
            grupoPermisos.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof Moneda) {
            moneda.setComponentes(codigo, nombre, nomenclatura, valor1, fecha, activo);
            moneda.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof Precio) {
            precio.setComponentes(codigo, codProd, tipoPrecio, codUnd, valor1, fecha, activo, boton1, boton2, boton3);
            precio.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof UnidadMedida) {
            unidadMedida.setComponentes(codigo, nombre, cantdUnd, peso, volumen, activo, accAndroid, combo1);
            unidadMedida.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof Productos) {
            productos.setComponentes(codigo, nombre, codProd, rutaImg, descripcion, activo, combo1, combo2, combo3, combo4, boton1);
            productos.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof PlanCuentas) {
            planCuentas.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof EstructuraCtas) {
            estructuraCuentas.setComponentes(combo1, combo2, combo3, combo4, combo5);
            estructuraCuentas.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof Empresas) {
            empresa.setComponentes(codigo, nombre, rif, descripcion, activo);
            empresa.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof CentroCosto) {
            centro.setComponentes(codigo, nombre,fecha);
            centro.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof ComprobanteDif) {
            comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            comproba.ejecutaHilo(code, lOperacion);
        }
    }
    
    private void cargaDatos(String tabla, int Row){
        Boolean lOperacion =false;
        String idetificador = null, code = null;
        
        if (Row>=0){
            if (tabla.equals("Ventas") || tabla.equals("Clientes")){
                code = (String) tabla1.getValueAt(Row,0).toString().trim();
            }else if (tabla.equals("Compras") || tabla.equals("Proveedores")){
                code = (String) tabla2.getValueAt(Row,0).toString().trim();
            }else if (tabla.equals("Otros")){
                code = (String) tabla3.getValueAt(Row,0).toString().trim();
            }else if (tabla.equals("Todos")){
                code = (String) tabla4.getValueAt(Row,0).toString().trim();
            }
            
            lOperacion = true;
        }
        
        if (aThis instanceof TipDocumentos) {
            tipoDocumentos.setComponentes(codigo, nombre, radioButton1, radioButton2, radioButton3, radioButton4, activo, calcIva, retIslr, retIva, invFis, invLog, addMenu, combo1, tabbedPane);
            tipoDocumentos.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof Maestros) {
            //maestros.setComponentes(codigo, nombre, tipoMaestro, activEco, tipoMoneda, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva, descripcion, combo1, combo2, combo3, combo4, combo5, activo, accAndroid, accWeb, boton1, boton2, boton3, tabbedPane, fecha);
            maestros.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof CentroCosto) {
            centro.setComponentes(codigo, nombre, fecha);//maestros.setComponentes(codigo, nombre, tipoMaestro, activEco, tipoMoneda, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva, descripcion, combo1, combo2, combo3, combo4, combo5, activo, accAndroid, accWeb, boton1, boton2, boton3, tabbedPane, fecha);
            centro.ejecutaHilo(code, lOperacion);
        }else if (aThis instanceof ComprobanteDif) {
            comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            comproba.ejecutaHilo(code, lOperacion);
        }
    }
    
    public void action_bt_modificar(){
        if (aThis instanceof CrearUsuarios){
            crearUsuarios.setPass(true);
        }
    }
    
    public void action_bt_agregar(){
        if (aThis instanceof CrearUsuarios){
            crearUsuarios.setPass(true);
        }
    }
    
    public void actionRadioButton(String accion){
        tipoDocumentos.setComponentes(codigo, nombre, radioButton1, radioButton2, radioButton3, radioButton4, activo, calcIva, retIslr, retIva, invFis, invLog, addMenu, combo1, tabbedPane);
        tipoDocumentos.actionRadioButtom(accion);
    }
    
    public void action_bt_grabar(Boolean lAgregar){
//        xmlFile(aThis.getClass().getName().toString());
        Boolean lSave = true;
        
        String chkActivo;
        if (activo.isSelected()==true){
            chkActivo="1";
        }else{
            chkActivo="0";
        }

        if (aThis instanceof Actividad) {
            actividad.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof TipoMaestros) {
            tipoMaestros.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof TipoContacto) {
            tipoContacto.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof CrearUsuarios) {
            crearUsuarios.setComponentes(codigo, nombre, clave, clave1, usuario, correo, rutaImg, activo, accAndroid, accWeb, combo1, combo2, imgDesktUser);
            crearUsuarios.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof GruposPermisos) {
            grupoPermisos.setComponentes(codigo, nombre, activo, radioButton1, radioButton2, radioButton3);
            grupoPermisos.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof Moneda) {
            moneda.setComponentes(codigo, nombre, nomenclatura, valor1, fecha, activo);
            moneda.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof Precio) {
            precio.setComponentes(codigo, codProd, tipoPrecio, codUnd, valor1, fecha, activo, boton1, boton2, boton3);
            precio.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof UnidadMedida) {
            unidadMedida.setComponentes(codigo, nombre, cantdUnd, peso, volumen, activo, accAndroid, combo1);
            unidadMedida.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof TipDocumentos) {
            tipoDocumentos.setComponentes(codigo, nombre, radioButton1, radioButton2, radioButton3, radioButton4, activo, calcIva, retIslr, retIva, invFis, invLog, addMenu, combo1, tabbedPane);
            tipoDocumentos.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, tabla2, lAgregar, claseOrg);
        }else if (aThis instanceof Productos) {
            productos.setComponentes(codigo, nombre, codProd, rutaImg, descripcion, activo, combo1, combo2, combo3, combo4, boton1);
            productos.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof Maestros) {
            //maestros.setComponentes(codigo, nombre, tipoMaestro, activEco, tipoMoneda, limitCred, limitCredVenc, diasPlazo, rif, nit, descuento, diasVen, retencIva, descripcion, combo1, combo2, combo3, combo4, combo5, activo, accAndroid, accWeb, boton1, boton2, boton3, tabbedPane, fecha);
            maestros.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, tabla2, tabla3, tabla4, lAgregar, claseOrg);
        }else if (aThis instanceof PlanCuentas) {
            lSave = planCuentas.save(codigoCta.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg, combo1.getSelectedItem().toString(), combo2.getSelectedItem().toString(), "", "");
        }else if (aThis instanceof EstructuraCtas) {
            estructuraCuentas.setComponentes(combo1, combo2, combo3, combo4, combo5);
            lSave = estructuraCuentas.save(combo1.getSelectedItem().toString(), combo2.getSelectedItem().toString(), combo3.getSelectedItem().toString(), combo4.getSelectedItem().toString(), combo5.getSelectedItem().toString(), tabla1, chkActivo, lAgregar, claseOrg);
        }else if (aThis instanceof Empresas) {
            empresa.setComponentes(codigo, nombre, rif, descripcion, activo);
            empresa.save(codigo.getText().toString(), nombre.getText().toString(), chkActivo, tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof CentroCosto) {
            centro.setComponentes(codigo, nombre, fecha);
            centro.save(codigo.getText().toString(), nombre.getText().toString(), tabla1, lAgregar, claseOrg);
        }else if (aThis instanceof ComprobanteDif) {
            comproba.setComponentes(numComp,numDoc,docMonto,docDescri,ctaNum,ctaDescri,ctaMonto,debe,haber, fecha, agregar, eliminar, buscar);
            //comproba.save(numComp.getText().toString(), docMonto.getText().toString(), docDescri.getText().toString(), ctaNum.getText().toString(), ctaDescri.getText().toString(), ctaMonto.getText().toString(), debe.getText().toString(), haber.getText().toString(), tabla1, lAgregar, claseOrg);
        }

        if (lSave==true){
            posicion_botones_1();
            Deshabilitar();
        }
    }
    
    public void action_bt_eliminar(){
        int eliminado;

        if (aThis instanceof Actividad) {
            actividad.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof TipoMaestros) {
            tipoMaestros.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof TipoContacto) {
            tipoContacto.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof CrearUsuarios) {
            crearUsuarios.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof GruposPermisos) {
            grupoPermisos.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof Moneda) {
            moneda.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof Precio) {
            precio.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof UnidadMedida) {
            unidadMedida.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof TipDocumentos) {
            tipoDocumentos.delete(codigo.getText().toString(), tabla1, tabla2, claseOrg);
        }else if (aThis instanceof Productos) {
            productos.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof Maestros) {
            int regis = maestros.delete(codigo.getText().toString(), tabla1, tabla2, tabla3, tabla4, claseOrg);
            
            if (regis==0){
                setConsecutivo();
                posicion_botones_2();
            }
        }else if (aThis instanceof PlanCuentas) {
            planCuentas.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof EstructuraCtas) {
            estructuraCuentas.delete(combo1.getSelectedItem().toString(), tabla1, claseOrg);
        }else if (aThis instanceof Empresas) {
            empresa.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof CentroCosto) {
            centro.delete(codigo.getText().toString(), tabla1, claseOrg);
        }else if (aThis instanceof ComprobanteDif) {
            comproba.delete(numComp.getText().toString(), tabla1, claseOrg);
        }
        
    }
    
    public void action_bt_cancelar(Boolean lAgregar){
        this.lAddEdit = lAgregar;
//        xmlFile(aThis.getClass().getName().toString());
        int eleccion = 0;
        
        if (aThis instanceof Actividad) {
            eleccion = actividad.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof TipoMaestros) {
            eleccion = tipoMaestros.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof TipoContacto) {
            eleccion = tipoContacto.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof CrearUsuarios) {
            eleccion = crearUsuarios.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof GruposPermisos) {
            eleccion = grupoPermisos.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof Moneda) {
            eleccion = moneda.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof Precio) {
            eleccion = precio.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof UnidadMedida) {
            eleccion = unidadMedida.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof TipDocumentos) {
            eleccion = tipoDocumentos.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof Productos) {
            eleccion = tipoDocumentos.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof Maestros) {
            eleccion = maestros.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof PlanCuentas) {
            eleccion = planCuentas.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof EstructuraCtas) {
            eleccion = estructuraCuentas.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof Empresas) {
            eleccion = empresa.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof CentroCosto) {
            eleccion = centro.cancelar(lAgregar, claseOrg);
        }else if (aThis instanceof ComprobanteDif) {
            eleccion = comproba.cancelar(lAgregar, claseOrg);
        }

        if (eleccion == 0) {
            if (tabla1.getRowCount()==0){
                if (aThis instanceof Actividad) {
                    act = (Actividad) prueba.getaThis(); 
                    act.dispose();
                }else if (aThis instanceof TipoMaestros) {
                    tm = (TipoMaestros) prueba.getaThis(); 
                    tm.dispose();
                }else if (aThis instanceof TipoContacto) {
                    tc = (TipoContacto) prueba.getaThis(); 
                    tc.dispose();
                }else if (aThis instanceof CrearUsuarios) {
                    cu = (CrearUsuarios) prueba.getaThis(); 
                    cu.dispose();
                }else if (aThis instanceof GruposPermisos) {
                    gp = (GruposPermisos) prueba.getaThis(); 
                    gp.dispose();
                }else if (aThis instanceof Moneda) {
                    md = (Moneda) prueba.getaThis(); 
                    md.dispose();
                }else if (aThis instanceof Precio) {
                    pc = (Precio) prueba.getaThis(); 
                    pc.dispose();
                }else if (aThis instanceof UnidadMedida) {
                    um = (UnidadMedida) prueba.getaThis();
                    um.dispose();
                }else if (aThis instanceof TipDocumentos) {
                    td = (TipDocumentos) prueba.getaThis();
                    td.dispose();
                }else if (aThis instanceof Productos) {
                    pd = (Productos) prueba.getaThis();
                    pd.dispose();
                }else if (aThis instanceof Maestros) {
                    mt = (Maestros) prueba.getaThis();
                    mt.dispose();
                }else if (aThis instanceof PlanCuentas) {
                    plc = (PlanCuentas) prueba.getaThis();
                    plc.dispose();
                }else if (aThis instanceof EstructuraCtas){
                    ec = (EstructuraCtas) prueba.getaThis();
                    ec.dispose();
                }else if (aThis instanceof Empresas){
                    emp = (Empresas) prueba.getaThis();
                    emp.dispose();
                }else if (aThis instanceof CentroCosto){
                    cen = (CentroCosto) prueba.getaThis();
                    cen.dispose();
                }else if (aThis instanceof ComprobanteDif){
                    comp = (ComprobanteDif) prueba.getaThis();
                    comp.dispose();
                }

                return;
            }
            
            Deshabilitar();
            posicion_botones_1();
            
            if (tabbedPane!=null){
                cargaDatos("",-1);
            }else{
                cargaDatos(-1);
            }
        }
    }
    
    public void validarRif(String sRif) throws IOException{
        CreaValidaRif datosfiscales = new CreaValidaRif();
        Vector DatosFiscales = datosfiscales.DatosFiscales(sRif);
     
        if (aThis instanceof Empresas) {
            empresa.setComponentes(codigo, nombre, rif, descripcion, activo);
            empresa.validarRif(DatosFiscales);
        }
    }
    
    private void colorButton(){
        agregar.setBackground(Color.decode(colorForm)); modificar.setBackground(Color.decode(colorForm));
        grabar.setBackground(Color.decode(colorForm)); eliminar.setBackground(Color.decode(colorForm));
        cancelar.setBackground(Color.decode(colorForm)); buscar.setBackground(Color.decode(colorForm));
        atras.setBackground(Color.decode(colorForm)); adelante.setBackground(Color.decode(colorForm));
        salir.setBackground(Color.decode(colorForm)); 
        
        agregar.setForeground(Color.decode(colorText)); modificar.setForeground(Color.decode(colorText));
        grabar.setForeground(Color.decode(colorText)); eliminar.setForeground(Color.decode(colorText));
        cancelar.setForeground(Color.decode(colorText)); buscar.setForeground(Color.decode(colorText));
        atras.setForeground(Color.decode(colorText)); adelante.setForeground(Color.decode(colorText));
        salir.setForeground(Color.decode(colorText)); 
        
        agregar.setHorizontalAlignment(2); eliminar.setHorizontalAlignment(2); modificar.setHorizontalAlignment(2);
        adelante.setHorizontalAlignment(2); atras.setHorizontalAlignment(2); cancelar.setHorizontalAlignment(2);
        buscar.setHorizontalAlignment(2); salir.setHorizontalAlignment(2); grabar.setHorizontalAlignment(2);
    }
    
    private void colorButtonOther(){
        if (boton1!=null){
            boton1.setBackground(Color.decode(colorForm));
            boton1.setForeground(Color.decode(colorText));
        }
        if (boton2!=null){
            boton2.setBackground(Color.decode(colorForm));
            boton2.setForeground(Color.decode(colorText));
        }
        if (boton3!=null){
            boton3.setBackground(Color.decode(colorForm));
            boton3.setForeground(Color.decode(colorText));
        }
    }
}