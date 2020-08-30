package remoto;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Genaro Coronel
 */

public class Remoto extends javax.swing.JFrame {

    /**
     * 
     * 
     * Creates new form Remoto
     */
      jcTrayIcon jct = new jcTrayIcon(this);
      ShowIp ipe = new ShowIp();
    
    public Remoto() throws SocketException {
        initComponents();
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.getContentPane().setForeground(Color.cyan);
        
       Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("scancode.png"));
       setIconImage(icon);
       setVisible(true);
    }
    
    public void escribirCodigo(String keys){
          Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Remoto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (char c : keys.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new RuntimeException(
                    "Key code not found for character '" + c + "'");
            }
            robot.keyPress(keyCode);
            robot.delay(2);
            robot.keyRelease(keyCode);
            robot.delay(2);
        }
        
        Boolean saltoLinea = true;
        
        if(saltoLinea){
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(2);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(2);
        }
    }
    
    public void recibirMensajesServidor() throws IOException{
        // Obtiene el flujo de entrada del socket
                // Declaracion de strings de comandos
        DatagramSocket serverSocket;
        serverSocket = new DatagramSocket(9000);
        System.out.println("Ejecutando Servidor");   

//        String sSistemaOperativo = System.getProperty("os.name");
        while (true) {
           
          //  System.out.println("esta ejecutnandose el while");
            byte[] receiveData = new byte[1024];
            
//            byte[] sendData = new byte[1024];
            
            //String ipss = ipe.ipGetWindows();
            String mensaje_enviar;
            byte[] sendData ;
            DatagramPacket packet;
            
            InetAddress receiverAddress = InetAddress.getLocalHost();
            
            String clnsentence = " ";
                          
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            clnsentence = sentence.trim();

            System.out.println("RECIVIDO: " + clnsentence);
              
         
              
            String OS = "Windows 10";
            
            switch (OS) {
             case "Windows 10":
             case "Linux":
                 switch(clnsentence){
                    case "scanner_up":
                        System.out.println("SERVER UP");
                        lblStatus.setText("Scanner Activado");
                        lblStatus.setForeground(Color.orange);
                        break;
                    case "scanner_down":
                        System.out.println("Server Down");
                        lblStatus.setText("Scanner Pausado");
                        lblStatus.setForeground(Color.orange);
                        break;
                    case "discover":
                        mensaje_enviar = "play_audio";
                        sendData = mensaje_enviar.getBytes();
                        packet = new DatagramPacket(sendData, sendData.length, receiverAddress, 9000);
                        serverSocket.send(packet);
                        break;
                    default:
                        escribirCodigo(clnsentence);
                //        mensaje_enviar = "play_audio";
                  //      sendData = mensaje_enviar.getBytes();
                   //     packet = new DatagramPacket(sendData, sendData.length, 9000);
                    //    serverSocket.send(packet);
                 }
    
            }
        }
    }
    
     public void mostrarIp() throws UnknownHostException, IOException{
        jComboBox1.setVisible(false);
        String sSistemaOperativo = System.getProperty("os.name");
        System.out.println("Sistema operativo: ___ " + sSistemaOperativo);
        switch(sSistemaOperativo){
            case "Windows 10":
                String ipeW = ipe.ipGetWindows();
                labelip.setText("Direccion IP: " + ipeW);
                labelip.setForeground(Color.orange);
                break;
            case "Linux":
                String ipString = ipe.ipGetLinux();
                labelip.setText("Direccion ip: "+ ipString);
                labelip.setForeground(Color.orange);
                break;
            case "Mac OS X":
                String ipe = InetAddress.getLocalHost().getHostAddress();
                labelip.setText("Direccion IP: " + ipe);
                labelip.setForeground(Color.orange);
                break;
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

        jComboBox1 = new javax.swing.JComboBox();
        labelip = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ScanBar Servidor");
        setBackground(new java.awt.Color(204, 153, 0));
        setForeground(new java.awt.Color(0, 204, 204));
        setIconImage(getIconImage());
        setIconImages(getIconImages());

        labelip.setFont(new java.awt.Font("Airstream", 1, 24)); // NOI18N
        labelip.setText("Direccion IP");

        lblStatus.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelip, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(labelip)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException  {
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
            java.util.logging.Logger.getLogger(Remoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Remoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Remoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Remoto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        Remoto remote = new Remoto();
//        remote.setVisible(true);
        remote.mostrarIp();
        remote.recibirMensajesServidor();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel labelip;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
