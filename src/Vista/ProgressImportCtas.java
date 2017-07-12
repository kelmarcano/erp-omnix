package Vista;

import Controlador.ControladorPlanCuentas;
import Modelos.VariablesGlobales;
import static clases.ColorApp.colorForm;
import static clases.ColorApp.colorText;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Painter;
import javax.swing.SwingWorker;
import javax.swing.UIDefaults;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProgressImportCtas extends javax.swing.JInternalFrame implements PropertyChangeListener {
    private File carpeta, archivoExcel, archivoTxt;
    private TaskImportCtas taskImportCtas;
    private int num_crea = 0, num_insert = 0, numCtasExist = 0;
    private JScrollPane scrollPane;
    private Boolean lRead = true;
    private Vector ctasDuplicadas = new Vector();
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    ControladorPlanCuentas planCuentas = new ControladorPlanCuentas();

    public ProgressImportCtas() {
        initComponents();

        jPanel1.setBackground(Color.decode(colorForm));
        jLbl_DescriProgre.setForeground(Color.decode(colorText));
        jAreaDetalle.setForeground(Color.decode(colorText));
        jBtnProcesar.setBackground(Color.decode(colorForm)); jBtnProcesar.setForeground(Color.decode(colorText));
        jBtnSalir.setBackground(Color.decode(colorForm)); jBtnSalir.setForeground(Color.decode(colorText));
        jBtnBuscarArchivo.setBackground(Color.decode(colorForm)); jBtnBuscarArchivo.setForeground(Color.decode(colorText));
        jProgressBar1.setForeground(Color.decode(colorForm));
        
        carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
        archivoExcel = new File(carpeta.getAbsolutePath()+"\\PLAN.xls");
        
        String rutaArchivo = archivoExcel.toString();
        jTxtRutaArch.setText(rutaArchivo);
        jTxtRutaArch.setForeground(Color.decode(colorText));
        
        jLbl_DescriProgre.setText("Proceso: ");
        jAreaDetalle.setEditable(false);

        //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter2(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter2(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
    }
    
    class TaskImportCtas extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() throws BiffException {
            num_crea = 0; num_insert = 0;
            ctasDuplicadas.clear();
            jAreaDetalle.setText("");
                                
            jBtnProcesar.setEnabled(false);
            jBtnSalir.setEnabled(false);
            
            archivoExcel = new File(jTxtRutaArch.getText().toString());
            
            try {
                Workbook workbook = Workbook.getWorkbook(archivoExcel); //Pasamos el excel que vamos a leer
                Sheet sheet = workbook.getSheet(0); //Seleccionamos la hoja que vamos a leer
                String nombre;
                String cta;
                String descrip;
                
                jProgressBar1.setMaximum(sheet.getRows());
            
                for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas
                    num_crea++;
                
                    jProgressBar1.setValue(num_crea);
                    jProgressBar1.setStringPainted(true);
                    
                    cta = sheet.getCell(0, fila).getContents(); //setear la celda leida a nombre
                    descrip = sheet.getCell(2, fila).getContents(); //setear la celda leida a nombre
                    
                    if (planCuentas.existeCod(cta)==0 && descrip!=""){
                        num_insert++;
                        
//                        String original = descrip;
//                        byte[] utf8Bytes = original.getBytes("UTF8");
//System.out.println("Reg 1 "+utf8Bytes);
                        String out = new String(descrip.getBytes("ISO-8859-1"), "UTF-8");
                        out = new String(descrip.getBytes("UTF-8"), "ISO-8859-1");
                        out = new String(descrip.getBytes("UTF-8"), "UTF-8");
//                        out = new String(descrip.getBytes("ISO-8859-1"), "ISO-8859-1");
                        //String prueb = Charset.forName("UTF-8").encode(descrip);
                        planCuentas.insert(cta, out, "1", true, "", "", "");
                    }else{
                        numCtasExist++;
                        
                        if (descrip==""){
                            ctasDuplicadas.add(cta+" "+descrip+"     (Cta sin Descripción)");
                        }else{
                            ctasDuplicadas.add(cta+" "+descrip+"     (Cta Duplicada)");
                        }
                    }
                    
                    jLbl_DescriProgre.setText("Proceso: "+cta+" "+descrip);
                    jAreaDetalle.append(jLbl_DescriProgre.getText()+"\n");
                    //for (int columna = 0; columna < sheet.getColumns(); columna++) { //recorremos las columnas
                    //    nombre = sheet.getCell(columna, fila).getContents(); //setear la celda leida a nombre
                        //System.out.print(nombre + ' '); // imprimir nombre
                    //}
                
                    int len = jAreaDetalle.getDocument().getLength();
                    jAreaDetalle.setCaretPosition(len);
                    jAreaDetalle.requestFocusInWindow();
                }
                
                jProgressBar1.setValue(num_crea+1);
                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "El Archivo o la Ruta indicado no son correctos", "Notificación", JOptionPane.ERROR_MESSAGE);
                
                lRead = false;
                Logger.getLogger(ProgressImportCtas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BiffException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error en el proceso de Letura del archivo", "Notificación", JOptionPane.ERROR_MESSAGE);
                
                lRead = false;
                
                Logger.getLogger(ProgressImportCtas.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
 
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            
            if (lRead==true){
                jAreaDetalle.append("Done!\n");
                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
            
                if (num_insert>0){
                    JOptionPane.showMessageDialog(null, "El proceso de Importacion del Plan de Cuentas finalizo correctamente \n"+
                                                        String.valueOf(num_insert)+" Cuentas Contables importadas", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                    
                    if (numCtasExist>0){
                        creaTxt();
                        
                        JOptionPane.showMessageDialog(null, "Se creo un archivo txt con las "+String.valueOf(numCtasExist)+" cuentas contables que no se pudieron importar en la base de datos\n"+
                                                            "en la siguiente direccion: \n\n"+archivoTxt.toString(), "Notificación", JOptionPane.INFORMATION_MESSAGE);
                        
                        try {
                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+archivoTxt.toString());
                        } catch (IOException ex) {
                            Logger.getLogger(ProgressImportCtas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Este Plan de Ctas ya fue Importado \n"+
                                                        "\n"+String.valueOf(num_insert)+" Cuentas Contables importadas", "Notificación", JOptionPane.INFORMATION_MESSAGE);            
                }
            }else{
                jAreaDetalle.append("Error..!!\n");
                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
                
                lRead = true;
            }
            
            jBtnProcesar.setEnabled(true);
            jBtnSalir.setEnabled(true);
        }
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBar1.setValue(progress);
            jProgressBar1.setStringPainted(true);
            jAreaDetalle.append(String.format("Completed %d%% of task.\n", taskImportCtas.getProgress()));
        } 
    }
    
    private void buscarArchivo(){
        JFileChooser fc = new JFileChooser();
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            File s = fc.getSelectedFile();
            String l = s.getAbsoluteFile().toString();
                
            jTxtRutaArch.setText(l);
                
            System.out.println(s);
        }
    }
    
    private void creaTxt(){
        try {
            carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            archivoTxt = new File(carpeta.getAbsolutePath()+"\\Cuentas no Importadas.txt");
        
            File file = new File(archivoTxt.toString());
            Boolean existe = file.isFile();
            
            if (existe==true){
                archivoTxt.delete();
            }
                
            //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
            FileWriter Arc = new FileWriter(archivoTxt, true);
            BufferedWriter escribir = new BufferedWriter(Arc);
            
            //Escribimos en el archivo con el metodo write
            for (int i=0; i<ctasDuplicadas.size(); i++){
                System.out.println(ctasDuplicadas.get(i));
                escribir.write((String) ctasDuplicadas.get(i)); escribir.newLine();
            }

            //Cerramos la conexion
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(ProgressImportCtas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLbl_DescriProgre = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jAreaDetalle = new javax.swing.JTextArea();
        jBtnBuscarArchivo = new javax.swing.JButton();
        jTxtRutaArch = new javax.swing.JTextField();
        jBtnProcesar = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jProgressBar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jProgressBar1.setMaximum(983);
        jProgressBar1.setAutoscrolls(true);

        jLbl_DescriProgre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLbl_DescriProgre.setText("jLabel1");

        jAreaDetalle.setColumns(20);
        jAreaDetalle.setFont(new java.awt.Font("Monospaced", 1, 10)); // NOI18N
        jAreaDetalle.setRows(5);
        jScrollPane1.setViewportView(jAreaDetalle);

        jBtnBuscarArchivo.setBackground(new java.awt.Color(255, 255, 255));
        jBtnBuscarArchivo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnBuscarArchivo.setText("[...]");
        jBtnBuscarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarArchivoActionPerformed(evt);
            }
        });

        jBtnProcesar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnProcesar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnProcesar.setText("Importar");
        jBtnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnProcesarActionPerformed(evt);
            }
        });

        jBtnSalir.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSalir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnSalir.setText("Salir");
        jBtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jTxtRutaArch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnBuscarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLbl_DescriProgre, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnProcesar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtRutaArch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBuscarArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbl_DescriProgre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnBuscarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarArchivoActionPerformed
        buscarArchivo();
    }//GEN-LAST:event_jBtnBuscarArchivoActionPerformed

    private void jBtnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnProcesarActionPerformed
        taskImportCtas = new TaskImportCtas();
        taskImportCtas.addPropertyChangeListener(this);
        taskImportCtas.execute();
    }//GEN-LAST:event_jBtnProcesarActionPerformed

    private void jBtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_jBtnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jAreaDetalle;
    private javax.swing.JButton jBtnBuscarArchivo;
    private javax.swing.JButton jBtnProcesar;
    private javax.swing.JButton jBtnSalir;
    private javax.swing.JLabel jLbl_DescriProgre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTxtRutaArch;
    // End of variables declaration//GEN-END:variables
}

class MyPainter2 implements Painter<JProgressBar> {
    private final Color color;

    public MyPainter2(Color c1) {
        this.color = c1;
    }
    @Override
    public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
        gd.setColor(color);
        gd.fillRect(0, 0, width, height);
    }
}