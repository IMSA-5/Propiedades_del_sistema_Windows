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
import java.util.ArrayList;
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
      public ArrayList<String> listConexionesEntrantes;
      public ArrayList<String> listproto;
    /** Creates new form ver */
    
     private void apagado(String procesocmd){
           
        try {
            Process cmd = Runtime.getRuntime().exec(procesocmd);
            cmd.waitFor();
                               
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e, "Excepcion", javax.swing.JOptionPane.PLAIN_MESSAGE);
        }
    }
    
     private void comandoconsolaConexionesEntrantes(String procesocmd){
           listConexionesEntrantes = new ArrayList<String>();
        try {
            Process cmd = Runtime.getRuntime().exec(procesocmd);
            cmd.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
            String linea = "";
            while ((linea = buf.readLine()) != null) {
                listConexionesEntrantes.add(linea.toString());
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e, "Excepcion", javax.swing.JOptionPane.PLAIN_MESSAGE);
        }
    }
       private void comandoconsolaprotocolo(String pcmd){
           listproto = new ArrayList<String>();
        try {
            Process cmd = Runtime.getRuntime().exec(pcmd);
            cmd.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
            String linea = "";
            while ((linea = buf.readLine()) != null) {
                listproto.add(linea.toString());
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e, "Excepcion", javax.swing.JOptionPane.PLAIN_MESSAGE);
        }
    }
      public ArrayList<String> getListConexionesEntrantes() {
        return listConexionesEntrantes;       
    }
 private void mostrarmConexionesEntrantes() {
        ArrayList<String> listConexiones=listConexionesEntrantes;
        for (int i = 0; i < listConexiones.size(); i++) {
           jTextAreaConexionesEntrantes.append(listConexiones.get(i) + '\n');  
          // listas.getListSockets().get(4).charAt(3);
           
           
            
           
        }
    }
 public ArrayList<String> getListSockets() {
        return listproto;       
    }
 private void mostrarmeprotoarp() {
        ArrayList<String> listSockets=listproto;
        for (int i = 0; i < listSockets.size(); i++) {
           jTextAreaProto.append(listSockets.get(i) + '\n');  
          // listas.getListSockets().get(4).charAt(3);      
        }
    }
 
    
    
    
    public Informacion() {
        try {
            initComponents();
            imprimirInfoCPU();
            imprimirInfoRAM();
            InformacionParticiones();
            informaciongeneralwindows();
            inicio();
            imprimirtiempo();
            verInformacionRed();
            verProcesos();
        } catch (SigarException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Sigar sigar = new Sigar();
    
     private void inicio() throws SigarException{
        Mem memoria = sigar.getMem();
        Swap intercambio = sigar.getSwap();
        
    OperatingSystem sys = OperatingSystem.getInstance();
    
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
    jTextAreaInicio.append("Version SO:\t\t" + sys.getName()+"\n");
    jTextAreaInicio.append("Nombre del S.O:\t" + sys.getVendorVersion()+"\n");
    jTextAreaInicio.append("CPUs fisica\t\t" + info.getTotalSockets()+"\n");
    jTextAreaInicio.append("Nucleos por CPU\t" + info.getCoresPerSocket()+ "\n");
    jTextAreaInicio.append("Cantidad de memoria RAM:\t"+ memoria.getRam() + " MB"+"\n");
    
    jLabel5.setText("Nombre de Usuario: "+System.getProperty("user.name"));
   
    }
    
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
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextAreaInicio = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
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
        jPanel7 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextAreaConexionesEntrantes = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextAreaProto = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextAreaInfoDriver = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu8 = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        jTextAreaInfoGeneral.setColumns(20);
        jTextAreaInfoGeneral.setRows(5);
        jScrollPane1.setViewportView(jTextAreaInfoGeneral);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 204, 204));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ItsePortada.jpg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel6.setText("INFORMACIÓN  DEL  SISTEMA");

        jTextAreaInicio.setColumns(20);
        jTextAreaInicio.setRows(5);
        jScrollPane12.setViewportView(jTextAreaInicio);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel5))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("inicio", jPanel11);

        jLabel1.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel1.setText("Información General:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(452, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(438, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
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
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
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
                                .addComponent(textNombreProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonMatar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel3)
                .addGap(0, 646, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informacion de Red", jPanel6);

        jTextAreaConexionesEntrantes.setColumns(20);
        jTextAreaConexionesEntrantes.setRows(5);
        jScrollPane7.setViewportView(jTextAreaConexionesEntrantes);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Conexiones Entrantes", jPanel7);

        jLabel4.setText("Tiempo");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(jLabel4))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(48, 48, 48)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(376, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(282, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Programar Apagado", jPanel8);

        jTextAreaProto.setColumns(20);
        jTextAreaProto.setRows(5);
        jScrollPane9.setViewportView(jTextAreaProto);

        jButton4.setText("Definición Protocolo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane10.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
        );

        jScrollPane8.setViewportView(jPanel9);

        jTabbedPane1.addTab("Protocolo ARP", jScrollPane8);

        jTextAreaInfoDriver.setColumns(20);
        jTextAreaInfoDriver.setRows(5);
        jScrollPane11.setViewportView(jTextAreaInfoDriver);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Driver ", jPanel10);

        jMenu4.setText("Archivo");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/save.png"))); // NOI18N
        jMenuItem1.setText("Guardar");
        jMenu4.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/salir.png"))); // NOI18N
        jMenuItem2.setText("cerrar");
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Formato");
        jMenu5.add(jSeparator1);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/tema2.gif"))); // NOI18N
        jMenu8.setText("Tamaño");
        jMenu8.add(jSeparator2);

        jMenuItem10.setText("sdfghjkl");
        jMenu8.add(jMenuItem10);

        jMenu5.add(jMenu8);

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/fontsize.png"))); // NOI18N
        jMenu9.setText("Fuentes");
        jMenu9.add(jSeparator3);

        jMenuItem12.setText("asdfghjkl");
        jMenu9.add(jMenuItem12);

        jMenu5.add(jMenu9);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Personalizar");

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/gtk2.gif"))); // NOI18N
        jMenu10.setText("Temas");

        jMenuItem3.setText("jMenuItem3");
        jMenu10.add(jMenuItem3);

        jMenu6.add(jMenu10);

        jMenu11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/tipo de color.jpg"))); // NOI18N
        jMenu11.setText("Color de fondos");

        jMenuItem4.setText("jMenuItem4");
        jMenu11.add(jMenuItem4);

        jMenuItem5.setText("jMenuItem5");
        jMenu11.add(jMenuItem5);

        jMenu6.add(jMenu11);

        jMenu13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/tipo de letra.jpg"))); // NOI18N
        jMenu13.setText("Color de texto");

        jMenuItem6.setText("jMenuItem6");
        jMenu13.add(jMenuItem6);

        jMenuItem7.setText("jMenuItem7");
        jMenu13.add(jMenuItem7);

        jMenu6.add(jMenu13);

        jMenuBar1.add(jMenu6);

        jMenu7.setText("Ayuda");

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/informacion2.gif"))); // NOI18N
        jMenuItem8.setText("Acerca de ....");
        jMenu7.add(jMenuItem8);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/Imagen11.gif"))); // NOI18N
        jMenuItem9.setText("Manual");
        jMenu7.add(jMenuItem9);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
 jTextAreaProto.setText("");
        comandoconsolaprotocolo("arp -a");
         mostrarmeprotoarp();
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

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
         jTextAreaConexionesEntrantes.setText("");
        comandoconsolaConexionesEntrantes("netstat");
         mostrarmConexionesEntrantes(); 
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         String tiempo = jTextField1.getText();
    apagado("shutdown -s -t "+tiempo);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        apagado("shutdown -a");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      String A="Muestra y permite modificar las tablas del protocolo ARP,"+ "\n" +  "encargado de convertir las direcciones IP de cada ordenador" +"\n"+ "en direcciones MAC (dirección física única de cada tarjeta de red).";  
        jTextArea1.setText(A);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
           String s = null;
                try {
                       // Determinar en que SO estamos
                        String so = System.getProperty("os.name");
                      String  comando = "cmd /c driverquery";                    
                                      
                       // Ejcutamos el comando
                        Process p = Runtime.getRuntime().exec(comando);

                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                                        p.getInputStream()));

                        BufferedReader stdError = new BufferedReader(new InputStreamReader(
                                        p.getErrorStream()));

                        // Leemos la salida del comando
                        System.out.println("Esta es la salida standard del comando:\n");
                        while ((s = stdInput.readLine()) != null) {
                          jTextAreaInfoDriver.setText(jTextAreaInfoDriver.getText()+s+"\n");
                                                 }                        
                        // Leemos los errores si los hubiera
                       
                        
                } catch (IOException e) {
                        System.out.println("Excepcion: ");
                        e.printStackTrace();
                       
                }  
    }//GEN-LAST:event_formWindowOpened


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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaConexionesEntrantes;
    private javax.swing.JTextArea jTextAreaInfoCPU;
    private javax.swing.JTextArea jTextAreaInfoDriver;
    private javax.swing.JTextArea jTextAreaInfoGeneral;
    private javax.swing.JTextArea jTextAreaInfoRAM;
    private javax.swing.JTextArea jTextAreaInformacionDeRed;
    private javax.swing.JTextArea jTextAreaInicio;
    private javax.swing.JTextArea jTextAreaParticiones;
    private javax.swing.JTextArea jTextAreaProcesos;
    private javax.swing.JTextArea jTextAreaProto;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelNombreProceso;
    private javax.swing.JTextField textNombreProceso;
    // End of variables declaration//GEN-END:variables
}
