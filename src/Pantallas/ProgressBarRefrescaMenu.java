package Pantallas;

import static Pantallas.principal.barra;
import clases.CargaMenu;
import static clases.ColorApp.colorText;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Painter;
import javax.swing.SwingWorker;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class ProgressBarRefrescaMenu extends javax.swing.JInternalFrame {
    private TaskMySQL task_mysql;
    
    public ProgressBarRefrescaMenu() {
        initComponents();
        
        jProgressBar1.setMaximum(99);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
                
        task_mysql = new TaskMySQL();
        task_mysql.execute();
    }

    class TaskMySQL extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            for (int i = 0; i<100; i++){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e){
                }
                
                jProgressBar1.setValue(i);
                jProgressBar1.setStringPainted(true);
            }
//*********************************************************************************
            return null;
        }
 
        @Override
        public void done() {
            barra.removeAll();
            barra.revalidate();
                
            CargaMenu MenuPrincipal = new CargaMenu();
            try {
                MenuPrincipal.CargaMenuPrincipal(false);
            } catch (SQLException ex) {
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
            }

            dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();

        jProgressBar1.setMaximum(983);
        jProgressBar1.setAutoscrolls(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}

class MyPainter implements Painter<JProgressBar> {

    private final Color color;

    public MyPainter(Color c1) {
        this.color = c1;
    }
    @Override
    public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
        gd.setColor(color);
        gd.fillRect(0, 0, width, height);
    }
}