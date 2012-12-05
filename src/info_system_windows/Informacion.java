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

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    
     Font fuente;
    int tamañofuente;  
    int estilo;
    String nombrefuente;
    
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
        
                tamañofuente = 12;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
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
    
     public void configurarfirewall()
{
    String s = null;
    try {
        // Determinar en que SO estamos
        String so = System.getProperty("os.name");
        String comando = "cmd /c netsh advfirewall show currentprofile";
        // Ejcutamos el comando
        Process p = Runtime.getRuntime().exec(comando);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                p.getInputStream()));


        System.out.println("Esta es la salida standard del comando:\n");
        while ((s = stdInput.readLine()) != null) {
            jTextAreaInfoFirewall.setText(jTextAreaInfoFirewall.getText() + s + "\n");
        }
        // Leemos los errores si los hubiera                                       
    } catch (IOException e) {
        System.out.println("Excepcion: ");
        e.printStackTrace();

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
        jPanel12 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextAreaInfoFirewall = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu8 = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItemNegro1 = new javax.swing.JMenuItem();
        jMenuItemMocasin = new javax.swing.JMenuItem();
        jMenuItemRojoOscuro1 = new javax.swing.JMenuItem();
        jMenuItemCardo = new javax.swing.JMenuItem();
        jMenuItemBeige = new javax.swing.JMenuItem();
        jMenuItemAzulClaro1 = new javax.swing.JMenuItem();
        jMenuItemAqua = new javax.swing.JMenuItem();
        jMenuItemGris = new javax.swing.JMenuItem();
        jMenuItemVerdeOliva = new javax.swing.JMenuItem();
        jMenuItemColores2 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItemNegroText = new javax.swing.JMenuItem();
        jMenuItemRojo1 = new javax.swing.JMenuItem();
        jMenuItemAqua1 = new javax.swing.JMenuItem();
        jMenuItemNaranja1 = new javax.swing.JMenuItem();
        jMenuItemAmarillo1 = new javax.swing.JMenuItem();
        jMenuItemCafeClaro = new javax.swing.JMenuItem();
        jMenuItemMarronRosaseo = new javax.swing.JMenuItem();
        jMenuItemVerde1 = new javax.swing.JMenuItem();
        jMenuItemVerdeClaro1 = new javax.swing.JMenuItem();
        jMenuItemSalmon = new javax.swing.JMenuItem();
        jMenuItemMasColores1 = new javax.swing.JMenuItem();
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
                .addContainerGap(43, Short.MAX_VALUE))
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
                .addContainerGap(510, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(434, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
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
                                .addComponent(textNombreProceso, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonMatar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel3)
                .addGap(0, 704, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
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
                .addContainerGap(436, Short.MAX_VALUE))
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
                .addContainerGap(278, Short.MAX_VALUE))
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
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(57, Short.MAX_VALUE))
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
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Driver ", jPanel10);

        jTextAreaInfoFirewall.setColumns(20);
        jTextAreaInfoFirewall.setRows(5);
        jScrollPane13.setViewportView(jTextAreaInfoFirewall);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Configuracion del Firewall", jPanel12);

        jMenu4.setText("Archivo");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/save.png"))); // NOI18N
        jMenuItem1.setText("Guardar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/salir.png"))); // NOI18N
        jMenuItem2.setText("cerrar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Formato");
        jMenu5.add(jSeparator1);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/tema2.gif"))); // NOI18N
        jMenu8.setText("Tamaño");
        jMenu8.add(jSeparator2);

        jMenuItem10.setText("18");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem10);

        jMenuItem15.setText("20");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem15);

        jMenuItem16.setText("22");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem16);

        jMenuItem17.setText("24");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem17);

        jMenuItem18.setText("26");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem18);

        jMenuItem19.setText("28");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem19);

        jMenu5.add(jMenu8);

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/fontsize.png"))); // NOI18N
        jMenu9.setText("Fuentes");
        jMenu9.add(jSeparator3);

        jMenuItem12.setText("MONOSPACED");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem12);

        jMenuItem11.setText("SERIF");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem11);

        jMenuItem13.setText("DIALOG_INPUT");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem13);

        jMenuItem14.setText("SANS_SERIF");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem14);

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

        jMenuItemNegro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/negro.png"))); // NOI18N
        jMenuItemNegro1.setText("Negro");
        jMenuItemNegro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNegro1ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemNegro1);

        jMenuItemMocasin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/mocasin.png"))); // NOI18N
        jMenuItemMocasin.setText("Mocasin");
        jMenuItemMocasin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMocasinActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemMocasin);

        jMenuItemRojoOscuro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/rojo_os.png"))); // NOI18N
        jMenuItemRojoOscuro1.setText("Rojo Oscuro");
        jMenuItemRojoOscuro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRojoOscuro1ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemRojoOscuro1);

        jMenuItemCardo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/cardio.png"))); // NOI18N
        jMenuItemCardo.setText("Cardo");
        jMenuItemCardo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCardoActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemCardo);

        jMenuItemBeige.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/beige.png"))); // NOI18N
        jMenuItemBeige.setText("Beige");
        jMenuItemBeige.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBeigeActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemBeige);

        jMenuItemAzulClaro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/azul_cl.png"))); // NOI18N
        jMenuItemAzulClaro1.setText("Azul Claro");
        jMenuItemAzulClaro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAzulClaro1ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemAzulClaro1);

        jMenuItemAqua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/aqua.png"))); // NOI18N
        jMenuItemAqua.setText("Aqua");
        jMenuItemAqua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAquaActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemAqua);

        jMenuItemGris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/gris.png"))); // NOI18N
        jMenuItemGris.setText("Gris");
        jMenuItemGris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGrisActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemGris);

        jMenuItemVerdeOliva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/verde_oliva.png"))); // NOI18N
        jMenuItemVerdeOliva.setText("Verde Oliva");
        jMenuItemVerdeOliva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVerdeOlivaActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemVerdeOliva);

        jMenuItemColores2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/pick_color.png"))); // NOI18N
        jMenuItemColores2.setText("Mas Colores");
        jMenuItemColores2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemColores2ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItemColores2);

        jMenu6.add(jMenu11);

        jMenu13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenesbt/tipo de letra.jpg"))); // NOI18N
        jMenu13.setText("Color de texto");

        jMenuItemNegroText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/negro.png"))); // NOI18N
        jMenuItemNegroText.setText("Negro");
        jMenuItemNegroText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNegroTextActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemNegroText);

        jMenuItemRojo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/rojo.png"))); // NOI18N
        jMenuItemRojo1.setText("Rojo");
        jMenuItemRojo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRojo1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemRojo1);

        jMenuItemAqua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/aqua.png"))); // NOI18N
        jMenuItemAqua1.setText("Aqua");
        jMenuItemAqua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAqua1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemAqua1);

        jMenuItemNaranja1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/naranja.png"))); // NOI18N
        jMenuItemNaranja1.setText("Naranja");
        jMenuItemNaranja1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNaranja1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemNaranja1);

        jMenuItemAmarillo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/amarillo.png"))); // NOI18N
        jMenuItemAmarillo1.setText("Amarillo");
        jMenuItemAmarillo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAmarillo1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemAmarillo1);

        jMenuItemCafeClaro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/cafe.png"))); // NOI18N
        jMenuItemCafeClaro.setText("Cafe Claro");
        jMenuItemCafeClaro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCafeClaroActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemCafeClaro);

        jMenuItemMarronRosaseo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/marron_rosaseo.png"))); // NOI18N
        jMenuItemMarronRosaseo.setText("Marron Rosaseo");
        jMenuItemMarronRosaseo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMarronRosaseoActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemMarronRosaseo);

        jMenuItemVerde1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/verde.png"))); // NOI18N
        jMenuItemVerde1.setText("Verde");
        jMenuItemVerde1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVerde1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemVerde1);

        jMenuItemVerdeClaro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/verde_cl.png"))); // NOI18N
        jMenuItemVerdeClaro1.setText("Verde Claro");
        jMenuItemVerdeClaro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVerdeClaro1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemVerdeClaro1);

        jMenuItemSalmon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/salmon.png"))); // NOI18N
        jMenuItemSalmon.setText("Salmon");
        jMenuItemSalmon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalmonActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemSalmon);

        jMenuItemMasColores1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosColores/pick_color.png"))); // NOI18N
        jMenuItemMasColores1.setText("Mas Colores");
        jMenuItemMasColores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMasColores1ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItemMasColores1);

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
         configurarfirewall();
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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
System.exit(0);       // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
// TODO add your handling code here:
    
     nombrefuente  = Font.MONOSPACED;
        fuente = new Font(nombrefuente, estilo, tamañofuente);

      
             jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem12ActionPerformed

private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
// TODO add your handling code here:
    
          nombrefuente  = Font.SERIF;
      fuente = new Font(nombrefuente, estilo, tamañofuente);
      
             jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
      
}//GEN-LAST:event_jMenuItem11ActionPerformed

private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
// TODO add your handling code here:
    
         nombrefuente  = Font.DIALOG_INPUT;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
             jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem13ActionPerformed

private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
// TODO add your handling code here:
    
            nombrefuente  = Font.SANS_SERIF;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
            jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem14ActionPerformed

private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
// TODO add your handling code here:
    
            tamañofuente  = 18;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
        jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem10ActionPerformed

private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
// TODO add your handling code here:
    
    tamañofuente  = 20;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
        jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem15ActionPerformed

private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
// TODO add your handling code here:
    
    tamañofuente  = 22;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
        jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem16ActionPerformed

private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
// TODO add your handling code here:
    tamañofuente  = 24;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
        jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem17ActionPerformed

private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
// TODO add your handling code here:
    
    tamañofuente  = 26;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
        jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem18ActionPerformed

private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
// TODO add your handling code here:
    
    tamañofuente  = 28;
        fuente = new Font(nombrefuente, estilo, tamañofuente);
        
        jTextAreaInfoGeneral.setFont(fuente);
             jTextAreaInfoCPU.setFont(fuente);
             jTextAreaInfoRAM.setFont(fuente);
             jTextAreaParticiones.setFont(fuente);
             jTextAreaProcesos.setFont(fuente);
             jTextArea1.setFont(fuente);
             jTextAreaConexionesEntrantes.setFont(fuente);
             jTextAreaInfoDriver.setFont(fuente);
             jTextAreaInformacionDeRed.setFont(fuente);
             jTextAreaInicio.setFont(fuente);
             jTextAreaProto.setFont(fuente);
}//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItemMocasinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMocasinActionPerformed
        // TODO add your handling code here:
        Color mocasin = new Color(255, 228, 181);
        jTextAreaInfoGeneral.setBackground(mocasin);
        jTextArea1.setBackground(mocasin);
        jTextAreaConexionesEntrantes.setBackground(mocasin);
        jTextAreaInfoCPU.setBackground(mocasin);
        jTextAreaInfoDriver.setBackground(mocasin);
        jTextAreaInfoFirewall.setBackground(mocasin);
        jTextAreaInfoRAM.setBackground(mocasin);
        jTextAreaInformacionDeRed.setBackground(mocasin);
        jTextAreaInicio.setBackground(mocasin);
        jTextAreaParticiones.setBackground(mocasin);
        jTextAreaProcesos.setBackground(mocasin);
        jTextAreaProto.setBackground(mocasin);
        jTextField1.setBackground(mocasin);
        textNombreProceso.setBackground(mocasin);
        
       
        
        
    }//GEN-LAST:event_jMenuItemMocasinActionPerformed

    private void jMenuItemCardoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCardoActionPerformed
        // TODO add your handling code here:
          Color cardo = new Color(216, 191, 216);
       jTextAreaInfoGeneral.setBackground(cardo);
       jTextArea1.setBackground(cardo);
       jTextAreaConexionesEntrantes.setBackground(cardo);
       jTextAreaInfoCPU.setBackground(cardo);
       jTextAreaInfoDriver.setBackground(cardo);
       jTextAreaInfoFirewall.setBackground(cardo);
       jTextAreaInfoRAM.setBackground(cardo);
       jTextAreaInformacionDeRed.setBackground(cardo);
       jTextAreaInicio.setBackground(cardo);
       jTextAreaParticiones.setBackground(cardo);
       jTextAreaProcesos.setBackground(cardo);
       jTextAreaProto.setBackground(cardo);
       jTextField1.setBackground(cardo);
       textNombreProceso.setBackground(cardo);
       
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemCardoActionPerformed

    private void jMenuItemNegro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNegro1ActionPerformed
        // TODO add your handling code here:
        jTextAreaInfoGeneral.setBackground(Color.decode("000000"));
        jTextArea1.setBackground(Color.decode("000000"));
        jTextAreaConexionesEntrantes.setBackground(Color.decode("000000"));
        jTextAreaInfoCPU.setBackground(Color.decode("000000"));
        jTextAreaInfoDriver.setBackground(Color.decode("000000"));
        jTextAreaInfoFirewall.setBackground(Color.decode("000000"));
        jTextAreaInfoRAM.setBackground(Color.decode("000000"));
        jTextAreaInformacionDeRed.setBackground(Color.decode("000000"));
        jTextAreaInicio.setBackground(Color.decode("000000"));
        jTextAreaParticiones.setBackground(Color.decode("000000"));
        jTextAreaProcesos.setBackground(Color.decode("000000"));
        jTextAreaProto.setBackground(Color.decode("000000"));
        jTextField1.setBackground(Color.decode("000000"));
        textNombreProceso.setBackground(Color.decode("000000"));
       
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemNegro1ActionPerformed

    private void jMenuItemRojoOscuro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRojoOscuro1ActionPerformed
        // TODO add your handling code here:
         Color rojo_obs = new Color(0xc00000);
        jTextAreaInfoGeneral.setBackground(rojo_obs);
        jTextArea1.setBackground(rojo_obs);
        jTextAreaConexionesEntrantes.setBackground(rojo_obs);
        jTextAreaInfoCPU.setBackground(rojo_obs);
        jTextAreaInfoDriver.setBackground(rojo_obs);
        jTextAreaInfoFirewall.setBackground(rojo_obs);
        jTextAreaInfoRAM.setBackground(rojo_obs);
        jTextAreaInformacionDeRed.setBackground(rojo_obs);
        jTextAreaInicio.setBackground(rojo_obs);
        jTextAreaParticiones.setBackground(rojo_obs);
        jTextAreaProcesos.setBackground(rojo_obs);
        jTextAreaProto.setBackground(rojo_obs);
        jTextField1.setBackground(rojo_obs);
        textNombreProceso.setBackground(rojo_obs);
        
        
        
        
        
        
       
    }//GEN-LAST:event_jMenuItemRojoOscuro1ActionPerformed

    private void jMenuItemBeigeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBeigeActionPerformed
        // TODO add your handling code here:
          Color beige = new Color(255, 255, 204);
       jTextAreaInfoGeneral.setBackground(beige);
       jTextArea1.setBackground(beige);
       jTextAreaConexionesEntrantes.setBackground(beige);
       jTextAreaInfoCPU.setBackground(beige);
       jTextAreaInfoDriver.setBackground(beige);
       jTextAreaInfoFirewall.setBackground(beige);
       jTextAreaInfoRAM.setBackground(beige);
       jTextAreaInformacionDeRed.setBackground(beige);
       jTextAreaInicio.setBackground(beige);
       jTextAreaParticiones.setBackground(beige);
       jTextAreaProcesos.setBackground(beige);
       jTextAreaProto.setBackground(beige);
       jTextField1.setBackground(beige);
       textNombreProceso.setBackground(beige);
        
        
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemBeigeActionPerformed

    private void jMenuItemAzulClaro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAzulClaro1ActionPerformed
        // TODO add your handling code here:
       Color azul = new Color(0x0070c0);
            jTextAreaInfoGeneral.setBackground(azul); 
            jTextArea1.setBackground(azul); 
        jTextAreaConexionesEntrantes.setBackground(azul); 
        jTextAreaInfoCPU.setBackground(azul); 
        jTextAreaInfoDriver.setBackground(azul); 
        jTextAreaInfoFirewall.setBackground(azul); 
        jTextAreaInfoRAM.setBackground(azul); 
        jTextAreaInformacionDeRed.setBackground(azul); 
        jTextAreaInicio.setBackground(azul); 
        jTextAreaParticiones.setBackground(azul); 
        jTextAreaProcesos.setBackground(azul); 
        jTextAreaProto.setBackground(azul); 
        jTextField1.setBackground(azul); 
        textNombreProceso.setBackground(azul); 
        
        
      
       
      
        
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemAzulClaro1ActionPerformed

    private void jMenuItemAquaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAquaActionPerformed
        // TODO add your handling code here:
        Color aqua = new Color(0, 255, 255);
        jTextAreaInfoGeneral.setBackground(aqua);
        jTextArea1.setBackground(aqua);
        jTextAreaConexionesEntrantes.setBackground(aqua);
        jTextAreaInfoCPU.setBackground(aqua);
        jTextAreaInfoDriver.setBackground(aqua);
        jTextAreaInfoFirewall.setBackground(aqua);
        jTextAreaInfoRAM.setBackground(aqua);
        jTextAreaInformacionDeRed.setBackground(aqua);
        jTextAreaInicio.setBackground(aqua);
        jTextAreaParticiones.setBackground(aqua);
        jTextAreaProcesos.setBackground(aqua);
        jTextAreaProto.setBackground(aqua);
        jTextField1.setBackground(aqua);
        textNombreProceso.setBackground(aqua);
        
        
        
    }//GEN-LAST:event_jMenuItemAquaActionPerformed

    private void jMenuItemGrisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGrisActionPerformed
        // TODO add your handling code here:
        Color gris = new Color(217, 217, 217);
        jTextAreaInfoGeneral.setBackground(gris);
        jTextArea1.setBackground(gris);
        jTextAreaConexionesEntrantes.setBackground(gris);
        jTextAreaInfoCPU.setBackground(gris);
        jTextAreaInfoDriver.setBackground(gris);
        jTextAreaInfoFirewall.setBackground(gris);
        jTextAreaInfoRAM.setBackground(gris);
        jTextAreaInformacionDeRed.setBackground(gris);
        jTextAreaInicio.setBackground(gris);
        jTextAreaParticiones.setBackground(gris);
        jTextAreaProcesos.setBackground(gris);
        jTextAreaProto.setBackground(gris);
        jTextField1.setBackground(gris);
        textNombreProceso.setBackground(gris);
        
        
        
    }//GEN-LAST:event_jMenuItemGrisActionPerformed

    private void jMenuItemVerdeOlivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVerdeOlivaActionPerformed
        // TODO add your handling code here:
        Color verdeOliva = new Color(202, 255, 112);
        jTextAreaInfoGeneral.setBackground(verdeOliva);
        jTextArea1.setBackground(verdeOliva);
        jTextAreaConexionesEntrantes.setBackground(verdeOliva);
        jTextAreaInfoCPU.setBackground(verdeOliva);
        jTextAreaInfoDriver.setBackground(verdeOliva);
        jTextAreaInfoFirewall.setBackground(verdeOliva);
        jTextAreaInfoRAM.setBackground(verdeOliva);
        jTextAreaInformacionDeRed.setBackground(verdeOliva);
        jTextAreaInicio.setBackground(verdeOliva);
        jTextAreaParticiones.setBackground(verdeOliva);
        jTextAreaProcesos.setBackground(verdeOliva);
        jTextAreaProto.setBackground(verdeOliva);
        jTextField1.setBackground(verdeOliva);
        textNombreProceso.setBackground(verdeOliva);
       
        
        
        
    }//GEN-LAST:event_jMenuItemVerdeOlivaActionPerformed

    private void jMenuItemColores2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemColores2ActionPerformed
        // TODO add your handling code here:
        Color newColor = JColorChooser.showDialog(this, "Choose Background Color", jTextAreaInfoGeneral.getBackground());

        if (newColor != null) {
           jTextAreaInfoGeneral.setBackground(newColor);
           jTextArea1.setBackground(newColor);
           jTextAreaConexionesEntrantes.setBackground(newColor);
           jTextAreaInfoCPU.setBackground(newColor);
           jTextAreaInfoDriver.setBackground(newColor);
           jTextAreaInfoFirewall.setBackground(newColor);
           jTextAreaInfoRAM.setBackground(newColor);
           jTextAreaInformacionDeRed.setBackground(newColor);
           jTextAreaInicio.setBackground(newColor);
           jTextAreaParticiones.setBackground(newColor);
           jTextAreaProcesos.setBackground(newColor);
           jTextAreaProto.setBackground(newColor);
           jTextField1.setBackground(newColor);
           textNombreProceso.setBackground(newColor);
      
           
           
           
           
        }
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemColores2ActionPerformed

    private void jMenuItemNegroTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNegroTextActionPerformed
        // TODO add your handling code here:
             jTextAreaInfoGeneral.setForeground(Color.decode("000000"));
             jTextArea1.setForeground(Color.decode("000000"));
             jTextAreaConexionesEntrantes.setForeground(Color.decode("000000"));
             jTextAreaInfoCPU.setForeground(Color.decode("000000"));
             jTextAreaInfoDriver.setForeground(Color.decode("000000"));
             jTextAreaInfoFirewall.setForeground(Color.decode("000000"));
             jTextAreaInfoRAM.setForeground(Color.decode("000000"));
             jTextAreaInformacionDeRed.setForeground(Color.decode("000000"));
             jTextAreaInicio.setForeground(Color.decode("000000"));
             jTextAreaParticiones.setForeground(Color.decode("000000"));
             jTextAreaProcesos.setForeground(Color.decode("000000"));
             jTextAreaProto.setForeground(Color.decode("000000"));
             jTextField1.setForeground(Color.decode("000000"));
             textNombreProceso.setForeground(Color.decode("000000"));
      
        
        
        
        
    }//GEN-LAST:event_jMenuItemNegroTextActionPerformed

    private void jMenuItemRojo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRojo1ActionPerformed
       // TODO add your handling code here:
         Color rojo = new Color(0xff0000);
        jTextAreaInfoGeneral.setForeground(rojo);  
        jTextArea1.setForeground(rojo);  
        jTextAreaConexionesEntrantes.setForeground(rojo);  
        jTextAreaInfoCPU.setForeground(rojo);  
        jTextAreaInfoDriver.setForeground(rojo);  
        jTextAreaInfoFirewall.setForeground(rojo);  
        jTextAreaInfoRAM.setForeground(rojo);   
        jTextAreaInformacionDeRed.setForeground(rojo);  
        jTextAreaInicio.setForeground(rojo);  
        jTextAreaParticiones.setForeground(rojo);  
        jTextAreaProcesos.setForeground(rojo);  
        jTextAreaProto.setForeground(rojo);  
        jTextField1.setForeground(rojo);  
        textNombreProceso.setForeground(rojo);  
       
        
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemRojo1ActionPerformed

    private void jMenuItemAqua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAqua1ActionPerformed
        // TODO add your handling code here:
        Color aqua = new Color(0, 255, 255);
          jTextAreaInfoGeneral.setForeground(aqua);
          jTextArea1.setForeground(aqua);
          jTextAreaConexionesEntrantes.setForeground(aqua);
          jTextAreaInfoCPU.setForeground(aqua);
          jTextAreaInfoDriver.setForeground(aqua);
          jTextAreaInfoFirewall.setForeground(aqua);
          jTextAreaInfoRAM.setForeground(aqua);
          jTextAreaInformacionDeRed.setForeground(aqua);
          jTextAreaInicio.setForeground(aqua);
          jTextAreaParticiones.setForeground(aqua);
          jTextAreaProcesos.setForeground(aqua);
          jTextAreaProto.setForeground(aqua);
          jTextField1.setForeground(aqua);
          textNombreProceso.setForeground(aqua);
       
        
        
    }//GEN-LAST:event_jMenuItemAqua1ActionPerformed

    private void jMenuItemNaranja1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNaranja1ActionPerformed
        // TODO add your handling code here:
         Color naranja = new Color(0xffc000);
           jTextAreaInfoGeneral.setForeground(naranja);
           jTextArea1.setForeground(naranja);
           jTextAreaConexionesEntrantes.setForeground(naranja);
           jTextAreaInfoCPU.setForeground(naranja);
           jTextAreaInfoDriver.setForeground(naranja);
           jTextAreaInfoFirewall.setForeground(naranja);
           jTextAreaInfoRAM.setForeground(naranja);
           jTextAreaInformacionDeRed.setForeground(naranja);
           jTextAreaInicio.setForeground(naranja);
           jTextAreaParticiones.setForeground(naranja);
           jTextAreaProcesos.setForeground(naranja);
           jTextAreaProto.setForeground(naranja);
           jTextField1.setForeground(naranja);
           textNombreProceso.setForeground(naranja);
          
        
        
    }//GEN-LAST:event_jMenuItemNaranja1ActionPerformed

    private void jMenuItemAmarillo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAmarillo1ActionPerformed
        // TODO add your handling code here:
         jTextAreaInfoGeneral.setForeground(Color.YELLOW);
         jTextArea1.setForeground(Color.YELLOW);
       jTextAreaConexionesEntrantes.setForeground(Color.YELLOW);
       jTextAreaInfoCPU.setForeground(Color.YELLOW);
       jTextAreaInfoDriver.setForeground(Color.YELLOW);
       jTextAreaInfoFirewall.setForeground(Color.YELLOW);
       jTextAreaInfoRAM.setForeground(Color.YELLOW);
       jTextAreaInformacionDeRed.setForeground(Color.YELLOW);
       jTextAreaInicio.setForeground(Color.YELLOW);
       jTextAreaParticiones.setForeground(Color.YELLOW);
       jTextAreaProcesos.setForeground(Color.YELLOW);
       jTextAreaProto.setForeground(Color.YELLOW);
       jTextField1.setForeground(Color.YELLOW);
       textNombreProceso.setForeground(Color.YELLOW);
       
      
      
       
      
      
       
        
        
        
        
    }//GEN-LAST:event_jMenuItemAmarillo1ActionPerformed

    private void jMenuItemCafeClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCafeClaroActionPerformed
        // TODO add your handling code here:
        Color cafeClaro = new Color(205, 102, 29);
           jTextAreaInfoGeneral.setForeground(cafeClaro);
           jTextArea1.setForeground(cafeClaro);
           jTextAreaConexionesEntrantes.setForeground(cafeClaro);
           jTextAreaInfoCPU.setForeground(cafeClaro);
           jTextAreaInfoDriver.setForeground(cafeClaro);
           jTextAreaInfoFirewall.setForeground(cafeClaro);
           jTextAreaInfoRAM.setForeground(cafeClaro);
           jTextAreaInformacionDeRed.setForeground(cafeClaro);
           jTextAreaInicio.setForeground(cafeClaro);
           jTextAreaParticiones.setForeground(cafeClaro);
           jTextAreaProcesos.setForeground(cafeClaro);
           jTextAreaProto.setForeground(cafeClaro);
           jTextField1.setForeground(cafeClaro);
           textNombreProceso.setForeground(cafeClaro);
       
        
        
        
    }//GEN-LAST:event_jMenuItemCafeClaroActionPerformed

    private void jMenuItemMarronRosaseoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMarronRosaseoActionPerformed
        // TODO add your handling code here:
        Color marronRosaseo = new Color(255, 193, 193);
        jTextAreaInfoGeneral.setForeground(marronRosaseo);
        jTextArea1.setForeground(marronRosaseo);
        jTextAreaConexionesEntrantes.setForeground(marronRosaseo);
        jTextAreaInfoCPU.setForeground(marronRosaseo);
        jTextAreaInfoDriver.setForeground(marronRosaseo);
        jTextAreaInfoFirewall.setForeground(marronRosaseo);
        jTextAreaInfoRAM.setForeground(marronRosaseo);
        jTextAreaInformacionDeRed.setForeground(marronRosaseo);
        jTextAreaInicio.setForeground(marronRosaseo);
        jTextAreaParticiones.setForeground(marronRosaseo);
        jTextAreaProcesos.setForeground(marronRosaseo);
        jTextAreaProto.setForeground(marronRosaseo);
        jTextField1.setForeground(marronRosaseo);
        textNombreProceso.setForeground(marronRosaseo);
        
        
        
    }//GEN-LAST:event_jMenuItemMarronRosaseoActionPerformed

    private void jMenuItemVerde1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVerde1ActionPerformed
        // TODO add your handling code here:
         Color verde = new Color(0x00b050);
        jTextAreaInfoGeneral.setForeground(verde);
        jTextArea1.setForeground(verde);
        jTextAreaConexionesEntrantes.setForeground(verde);
        jTextAreaInfoCPU.setForeground(verde);
        jTextAreaInfoDriver.setForeground(verde);
        jTextAreaInfoFirewall.setForeground(verde);
        jTextAreaInfoRAM.setForeground(verde);
        jTextAreaInformacionDeRed.setForeground(verde);
        jTextAreaInicio.setForeground(verde);
        jTextAreaParticiones.setForeground(verde);
        jTextAreaProcesos.setForeground(verde);
        jTextAreaProto.setForeground(verde);
        jTextField1.setForeground(verde);
        textNombreProceso.setForeground(verde);
        
        
        
        
        
    }//GEN-LAST:event_jMenuItemVerde1ActionPerformed

    private void jMenuItemVerdeClaro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVerdeClaro1ActionPerformed
        // TODO add your handling code here:
         Color verde_c = new Color(0xc92d050);
           jTextAreaInfoGeneral.setForeground(verde_c);
           jTextArea1.setForeground(verde_c);
           jTextAreaConexionesEntrantes.setForeground(verde_c);
           jTextAreaInfoCPU.setForeground(verde_c);
           jTextAreaInfoDriver.setForeground(verde_c);
           jTextAreaInfoFirewall.setForeground(verde_c);
           jTextAreaInfoRAM.setForeground(verde_c);
           jTextAreaInformacionDeRed.setForeground(verde_c);
           jTextAreaInicio.setForeground(verde_c);
           jTextAreaParticiones.setForeground(verde_c);
           jTextAreaProcesos.setForeground(verde_c);
           jTextAreaProto.setForeground(verde_c);
           jTextField1.setForeground(verde_c);
           textNombreProceso.setForeground(verde_c);
       
        
        
        
    }//GEN-LAST:event_jMenuItemVerdeClaro1ActionPerformed

    private void jMenuItemSalmonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalmonActionPerformed
        // TODO add your handling code here:
        Color salmon = new Color(255, 160, 122);
        jTextAreaInfoGeneral.setForeground(salmon);
        jTextArea1.setForeground(salmon);
        jTextAreaConexionesEntrantes.setForeground(salmon);
        jTextAreaInfoCPU.setForeground(salmon);
        jTextAreaInfoDriver.setForeground(salmon);
        jTextAreaInfoFirewall.setForeground(salmon);
        jTextAreaInfoRAM.setForeground(salmon);
        jTextAreaInformacionDeRed.setForeground(salmon);
        jTextAreaInicio.setForeground(salmon);
        jTextAreaParticiones.setForeground(salmon);
        jTextAreaProcesos.setForeground(salmon);
        jTextAreaProto.setForeground(salmon);
        jTextField1.setForeground(salmon);
        textNombreProceso.setForeground(salmon);
        
        
        
       
        
        
        
        
    }//GEN-LAST:event_jMenuItemSalmonActionPerformed

    private void jMenuItemMasColores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMasColores1ActionPerformed
        // TODO add your handling code here:
         Color newColor = JColorChooser.showDialog(this, "Choose Background Color", jTextAreaInfoGeneral.getForeground());

        if (newColor != null) {
        jTextAreaInfoGeneral.setForeground(newColor);
        jTextArea1.setForeground(newColor);
        jTextAreaConexionesEntrantes.setForeground(newColor);
        jTextAreaInfoCPU.setForeground(newColor);
        jTextAreaInfoDriver.setForeground(newColor);
        jTextAreaInfoFirewall.setForeground(newColor);
        jTextAreaInfoRAM.setForeground(newColor);
        jTextAreaInformacionDeRed.setForeground(newColor);
        jTextAreaInicio.setForeground(newColor);
        jTextAreaParticiones.setForeground(newColor);
        jTextAreaProcesos.setForeground(newColor);
        jTextAreaProto.setForeground(newColor);
        jTextField1.setForeground(newColor);
        textNombreProceso.setForeground(newColor);
       
        
        }
        
    }//GEN-LAST:event_jMenuItemMasColores1ActionPerformed

    private void guardar() {
        String title = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar como...");
            fileChooser.setSelectedFile(new File("*.txt"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Texto Plano", "txt"));
            fileChooser.showSaveDialog(this);
            File Guardar = fileChooser.getSelectedFile();
            if (Guardar != null) {
                PrintWriter Guard = new PrintWriter(Guardar + ".txt");
                elegirJText(title, Guard);
                Guard.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void elegirJText(String title, PrintWriter Guard) throws IOException {
        if (title.equals("Informacion General")) {
            Guard.print(jTextAreaInfoCPU.getText());
        } else if (title.equals("RAM")) {
           Guard.print(jTextAreaInfoRAM.getText());
        } else if (title.equals("CPU")) {
            Guard.print(jTextAreaInfoCPU.getText());
        } else if (title.equals("Red")) {
            Guard.print(jTextAreaInformacionDeRed.getText());
        }else if (title.equals ("Procesos")){
            Guard.print(jTextAreaProcesos.getText());
        }
        else if (title.equals ("Particiones")){
            Guard.print(jTextAreaParticiones.getText());
        }else if (title.equals ("Entrantes")){
            Guard.print(jTextAreaConexionesEntrantes.getText());
        }else if (title.equals ("Driver")){
            Guard.print(jTextAreaInfoDriver.getText());
        }else if (title.equals ("Protocolo")){
            Guard.print(jTextAreaProto.getText());
        }
    }

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
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemAmarillo1;
    private javax.swing.JMenuItem jMenuItemAqua;
    private javax.swing.JMenuItem jMenuItemAqua1;
    private javax.swing.JMenuItem jMenuItemAzulClaro1;
    private javax.swing.JMenuItem jMenuItemBeige;
    private javax.swing.JMenuItem jMenuItemCafeClaro;
    private javax.swing.JMenuItem jMenuItemCardo;
    private javax.swing.JMenuItem jMenuItemColores2;
    private javax.swing.JMenuItem jMenuItemGris;
    private javax.swing.JMenuItem jMenuItemMarronRosaseo;
    private javax.swing.JMenuItem jMenuItemMasColores1;
    private javax.swing.JMenuItem jMenuItemMocasin;
    private javax.swing.JMenuItem jMenuItemNaranja1;
    private javax.swing.JMenuItem jMenuItemNegro1;
    private javax.swing.JMenuItem jMenuItemNegroText;
    private javax.swing.JMenuItem jMenuItemRojo1;
    private javax.swing.JMenuItem jMenuItemRojoOscuro1;
    private javax.swing.JMenuItem jMenuItemSalmon;
    private javax.swing.JMenuItem jMenuItemVerde1;
    private javax.swing.JMenuItem jMenuItemVerdeClaro1;
    private javax.swing.JMenuItem jMenuItemVerdeOliva;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JScrollPane jScrollPane13;
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
    private javax.swing.JTextArea jTextAreaInfoFirewall;
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
