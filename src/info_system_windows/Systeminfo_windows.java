/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info_system_windows;

import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author Ab
 */
public class Systeminfo_windows {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic her
        Sigar sigar = new Sigar();
        OperatingSystem sys = OperatingSystem.getInstance();
        System.out.println("Descripcion del SO\t" + sys.getDescription());
        System.out.println("Nombre del SO\t\t" + sys.getName());
        System.out.println("Arquitectura del SO\t" + sys.getArch());
        System.out.println("Version del SO\t\t" + sys.getVersion());
        System.out.println("Nivel de parches\t" + sys.getPatchLevel());
        System.out.println("Fabricante\t\t" + sys.getVendor());
        System.out.println("Version SO\t\t" + sys.getVendorVersion());
        
        System.out.println(" ..........");
    
    }    
    }

