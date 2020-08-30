package remoto;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class ShowIp {
String ip;
    public String ipGetLinux() throws SocketException {
        NetworkInterface ni = NetworkInterface.getByName("wlan0");
        Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
        while(inetAddresses.hasMoreElements()) {
            InetAddress ia = inetAddresses.nextElement();
            if(!ia.isLinkLocalAddress()) {
                 ip=ia.toString();
            }
        }
        return ip.replace("/","");
    }
    
    public String ipGetWindows() throws SocketException, UnknownHostException{
        
        try(final DatagramSocket socket = new DatagramSocket()){
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        ip = socket.getLocalAddress().getHostAddress();
      }
         return ip;
    }
}