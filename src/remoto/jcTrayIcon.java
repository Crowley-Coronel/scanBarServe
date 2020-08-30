package remoto;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class jcTrayIcon {

    private JFrame miframe;
    private PopupMenu popup = new PopupMenu();
    
   private Image image =new ImageIcon(getClass().getResource("scancode.png")).getImage() ;
  //String icoPath = "D:/mira1.png";
   
   
    //private final TrayIcon trayIcon = new TrayIcon(new ImageIcon(icoPath, "omt").getImage(), "Mi Aplicación", popup);
    private final TrayIcon trayIcon = new TrayIcon(image, "Control Remoto", popup);
    //para el Timer
    private Timer timer;    
    private boolean band;
   private String ipe;
 public jcTrayIcon( JFrame frame)
 {
        try {
            this.ipe = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(jcTrayIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
    this.miframe = frame;
    //comprueba si SystemTray es soportado en el sistema
    if (SystemTray.isSupported())
    {
      //obtiene instancia SystemTray
      SystemTray systemtray = SystemTray.getSystemTray();
      //acciones del raton sobre el icono en la barra de tareas
      MouseListener mouseListener = new MouseListener() {

        public void mouseClicked(MouseEvent evt) {            
            //Si se presiono el boton izquierdo y la aplicacion esta minimizada
            if( evt.getButton() == MouseEvent.BUTTON1 && miframe.getExtendedState()==JFrame.ICONIFIED )
                MensajeTrayIcon("La ip es: "+ipe, MessageType.INFO);
        }

        public void mouseEntered(MouseEvent evt) {}

        public void mouseExited(MouseEvent evt) {}

        public void mousePressed(MouseEvent evt) {}

        public void mouseReleased(MouseEvent evt) {}
    };

    //ACCIONES DEL MENU POPUP
    //Salir de aplicacion
    ActionListener exitListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {            
            System.exit(0);
        }
    };
    //Restaurar aplicacion
    ActionListener RestaurarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {            
            miframe.setVisible(true);
            
            miframe.setExtendedState( JFrame.NORMAL );
            miframe.repaint();
            band = true;
        }
    };
    //Se crean los Items del menu PopUp y se añaden
    MenuItem SalirItem = new MenuItem("Salir");
    SalirItem.addActionListener(exitListener);
    popup.add(SalirItem);

    MenuItem ItemRestaurar = new MenuItem("Restaurar");
    ItemRestaurar.addActionListener(RestaurarListener);
    popup.add(ItemRestaurar);
    trayIcon.setImageAutoSize(true);
    trayIcon.addMouseListener(mouseListener);
    

    //Añade el TrayIcon al SystemTray
    try {
        systemtray.add(trayIcon);
    } catch (AWTException e) {
        System.err.println( "Error:" + e.getMessage() );
    }
  } else {
     System.err.println( "Error: SystemTray no es soportado" );
  }

    //Cuando se minimiza JFrame, se oculta para que no aparesca en la barra de tareas
     miframe.addWindowListener(new WindowAdapter(){
        @Override
        public void windowIconified(WindowEvent e){
           miframe.setVisible(false);//Se oculta JFrame
           //Se inicia una tarea cuando se minimiza
           band = false;
         //  timer = new Timer();           
          // timer.schedule(new MyTimerTask(),0, 10000 );//Se ejecuta cada 10 segundos
           MensajeTrayIcon("Servidor minimizado", MessageType.INFO);
           
        }
    });

    }

    //Muestra una burbuja con la accion que se realiza
    public void MensajeTrayIcon(String texto, MessageType tipo)
    {
        trayIcon.displayMessage("Corriendo Servidor:", texto, tipo);
    }

    //clase interna que manejara una accion en segundo plano
    class MyTimerTask extends TimerTask {
        public void run() {
            if(band)//Termina Timer
                timer.cancel();
          
             
        }
        //Una accion a realiza cuando la aplicacion a sido minimizada
      

    }

}

