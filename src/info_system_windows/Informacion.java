/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * form.java
 *
 * Created on 20/11/2012, 03:14:32 PM
 */
package info_system_windows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
 *
 * @author Ab
 */
public class Informacion extends javax.swing.JFrame {

    /** Creates new form form */
    public Informacion() {
        try {
            initComponents();
            imprimirInfoCPU();
            imprimirInfoRAM();
            InformacionParticiones();
            informaciongeneralwindows();
            imprimirtiempo();
            verInformacionRed();
            verProcesos();
        } catch (SigarException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Sigar sigar = new Sigar();
    
    private void informaciongeneralwindows(){
    OperatingSystem sys = OperatingSystem.getInstance();
    jTextAreaInfoGeneral.append("Descripcion del SO:\t" + sys.getDescription()+"\n");
    jTextAreaInfoGeneral.append("Nombre del SO:\t" + sys.getName()+"\n");
    jTextAreaInfoGeneral.append("Arquitectura del SO:\t" + sys.getArch()+"\n");
    jTextAreaInfoGeneral.append("Version del SO:\t\t" + sys.getVersion()+"\n");
    jTextAreaInfoGeneral.append("Fabricante:\t\t" + sys.getVendor()+"\n");
    jTextAreaInfoGeneral.append("Version SO:\t\t" + sys.getVendorVersion()+"\n");
    jTextAreaInfoGeneral.append("Version JDK:\t\t" + JDK()+"\n\n");
    jLabel1.setText("Nombre de Usuario: "+System.getProperty("user.name"));
    
    }
    public static String JDK()
    {
         return System.getProperty("java.version");      
    }
    
    public void imprimirtiempo() throws SigarException {
        double uptime = sigar.getUptime().getUptime();
        String resultado = "";
        int dias = (int) uptime / (60 * 60 * 24);
        int minutos, horas;
        if (dias != 0)
            resultado += dias + " " + ((dias > 1) ? "dias" : "dia") + ", ";
        minutos = (int) uptime / 60;
        horas = minutos / 60;
        horas %= 24;
        minutos %= 60;
        if (horas != 0)
            resultado += horas + ":" + (minutos < 10 ? "0" + minutos : minutos)+ " horas";
        else
            resultado += minutos + " min";
        jTextAreaInfoGeneral.append("Encendido durante:\t" + resultado);
    } 
 
     private void verInformacionRed(){
        try {
           
            String red = System.getenv("windir")+"\\System32\\"+"IPCONFIG.exe";
          
            Process infored=Runtime.getRuntime().exec(red);
          
            BufferedReader entrada = new BufferedReader(new InputStreamReader(infored.getInputStream()));
            String tmp;
            while((tmp=entrada.readLine())!=null){
                    jTextAreaInformacionDeRed.append(tmp + "\n");
                    
            }
            entrada.close();
        } catch (IOException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    public void imprimirInfoCPU() {
        sigar = new Sigar();
        CpuInfo[] infos = null;
        CpuPerc[] cpus = null;
        try {
            infos = sigar.getCpuInfoList();
            cpus = sigar.getCpuPercList();
        } catch (SigarException e) {
         e.printStackTrace();
    }
 
        CpuInfo info = infos[0];
        //long tamañoCache = info.getCacheSize();
        jTextAreaInfoCPU.append("Fabricante:\t\t" + info.getVendor()+"\n");
        jTextAreaInfoCPU.append("Modelo\t\t" + info.getModel()+"\n");
        jTextAreaInfoCPU.append("Mhz\t\t" + info.getMhz()+"\n");
        jTextAreaInfoCPU.append("Total CPUs\t\t" + info.getTotalCores()+ "  "+ info.getModel()+"\n");
        jTextAreaInfoCPU.append("CPUs fisiscas\t\t" + info.getTotalSockets()+"\n");
        jTextAreaInfoCPU.append("Nucleos por CPU\t" + info.getCoresPerSocket()+ "\n");
       /* if ((info.getTotalCores() != info.getTotalSockets())
                || (info.getCoresPerSocket() > info.getTotalCores())) {
            jTextAreaInfoCPU.append("CPUs físicas\t\t" + info.getTotalSockets());
            jTextAreaInfoCPU.append("Nucleos por CPU\t\t" + info.getCoresPerSocket());
        }*/
 
        /*if (tamanioCache != Sigar.FIELD_NOTIMPL)
            jTextAreaInfoCPU.append("Tamanio cache\t\t" + tamanioCache +"\n");
        jTextAreaInfoCPU.append(""+"\n");*/
 
        for (int i = 0; i < cpus.length; i++)         
            jTextAreaInfoCPU.append("Consumo de CPU " + "  " + i + "\t"+ CpuPerc.format(cpus[i].getUser())+ "\n");
        try {
          jTextAreaInfoCPU.append("Consumo total del CPU:\t"+ CpuPerc.format(sigar.getCpuPerc().getUser()));  
       } catch (SigarException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void imprimirInfoRAM() throws SigarException {
        Mem memoria = sigar.getMem();
        Swap intercambio = sigar.getSwap();
        
        jTextAreaInfoRAM.append("Cantidad de memoria RAM:\t"+ memoria.getRam() + " MB"+"\n");
        //jTextAreaInfoRAM.append("Total:\t\t "+enBytes(memoria.getTotal())+" MB"+"\n");
        jTextAreaInfoRAM.append("Usada:\t\t"+enBytes(memoria.getUsed())+" MB"+"\n");
        jTextAreaInfoRAM.append("Disponible:\t\t "+enBytes(memoria.getFree())+" MB"+"\n");
        jTextAreaInfoRAM.append("Memoria SWAP total:\t"+enBytes(intercambio.getTotal())+" MB"+"\n");
        jTextAreaInfoRAM.append("Memoria SWAP usada:\t"+enBytes(intercambio.getUsed())+" MB"+"\n");
        jTextAreaInfoRAM.append("Memoria SWAP libre:\t"+enBytes(intercambio.getFree())+" MB"+"\n");
    }
    private Long enBytes(long valor) {
        return new Long((valor / 1024)/1024);
    }
    
    public void InformacionParticiones(){
        for(File root : File.listRoots())
{
if(root.canWrite())
{
jTextAreaParticiones.append("root: " + root.getAbsolutePath()+"\n");
}
}
    }
    
//muestra los procesos en ejecucion    
private void verProcesos(){
        try {
            // LLAMAMOS LA VARIABLE DE ENTORNO WINDOWS Y EL PROGRAMA QUE GESTION LOS PROCESOS
            String consola = System.getenv("windir")+"\\System32\\"+"tasklist.exe";
            // Ejecutamos el comando
            Process proceso=Runtime.getRuntime().exec(consola);
            //OBTENEMOS EL BUFFER DE SALIDA
            BufferedReader entrada = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String tmp;
            while((tmp=entrada.readLine())!=null){
                    jTextAreaProcesos.append(tmp + "\n");
                    
            }
            entrada.close();
        } catch (IOException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInfoGeneral = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaInfoCPU = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaInfoRAM = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaParticiones = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaProcesos = new javax.swing.JTextArea();
        labelNombreProceso = new javax.swing.JLabel();
        textNombreProceso = new javax.swing.JTextField();
        buttonMatar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaInformacionDeRed = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        jTextAreaInfoGeneral.setColumns(20);
        jTextAreaInfoGeneral.setRows(5);
        jScrollPane1.setViewportView(jTextAreaInfoGeneral);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel1.setText("Información General:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(429, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información General", jPanel1);

        jTextAreaInfoCPU.setColumns(20);
        jTextAreaInfoCPU.setRows(5);
        jScrollPane2.setViewportView(jTextAreaInfoCPU);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Información del CPU", jPanel2);

        jTextAreaInfoRAM.setColumns(20);
        jTextAreaInfoRAM.setRows(5);
        jScrollPane3.setViewportView(jTextAreaInfoRAM);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Información de Memoria RAM", jPanel3);

        jTextAreaParticiones.setColumns(20);
        jTextAreaParticiones.setRows(5);
        jScrollPane4.setViewportView(jTextAreaParticiones);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Particiones", jPanel4);

        jTextAreaProcesos.setColumns(20);
        jTextAreaProcesos.setRows(5);
        jScrollPane5.setViewportView(jTextAreaProcesos);

        labelNombreProceso.setText("Nombre del proceso:");

        buttonMatar.setText("Matar");
        buttonMatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMatarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Matar Proceso");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(labelNombreProceso)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textNombreProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonMatar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textNombreProceso)
                    .addComponent(buttonMatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNombreProceso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Procesos", jPanel5);

        jTextAreaInformacionDeRed.setColumns(20);
        jTextAreaInformacionDeRed.setRows(5);
        jScrollPane6.setViewportView(jTextAreaInformacionDeRed);

        jLabel3.setText("C");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informacion de Red", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
 
}//GEN-LAST:event_formWindowActivated

private void buttonMatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMatarActionPerformed

        String osName = System.getProperty("os.name");
        String system =  "";
        if(osName.toUpperCase().contains("WIN")){ 
            system+="taskkill.exe /PID " + textNombreProceso.getText();
        }
        Process hijo;
        try {
            hijo = Runtime.getRuntime().exec(system);
            hijo.waitFor();
            if ( hijo.exitValue()==0){
                JOptionPane.showMessageDialog(rootPane, "Se ha terminado el proceso: " +"\t"+ textNombreProceso.getText());
            }else{
                JOptionPane.showMessageDialog(rootPane,"No se pudo terminar el proceso: "+"\t" + textNombreProceso.getText() + ". Exit code: " + hijo.exitValue());
            }
        } catch (IOException e) {
            System.out.println("Incapaz de matar soffice. " + e.getMessage() + " " + system);
        } catch (InterruptedException e) {
            System.out.println("Incapaz de matar soffice.");
        }      
}//GEN-LAST:event_buttonMatarActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Informacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Informacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Informacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Informacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Informacion().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonMatar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaInfoCPU;
    private javax.swing.JTextArea jTextAreaInfoGeneral;
    private javax.swing.JTextArea jTextAreaInfoRAM;
    private javax.swing.JTextArea jTextAreaInformacionDeRed;
    private javax.swing.JTextArea jTextAreaParticiones;
    private javax.swing.JTextArea jTextAreaProcesos;
    private javax.swing.JLabel labelNombreProceso;
    private javax.swing.JTextField textNombreProceso;
    // End of variables declaration//GEN-END:variables
}
