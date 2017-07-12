package clases;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Kelvin Marcano
 */
public class MantenimientoBD2 extends FileFilter{

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().startsWith("mysqldump");
    }

    @Override
    public String getDescription() {
        return "Buscar Archivo: mysqldump.exe";
    }
    
}
