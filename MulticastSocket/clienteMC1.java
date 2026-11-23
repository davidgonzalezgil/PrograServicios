package MulticastSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class clienteMC1 {

    public static void main(String args[]) throws Exception {
        // Se crea el socket multicast
        String IpGrupo = "230.0.0.1";
        int Puerto = 12345;// Puerto multicast
        MulticastSocket ms = new MulticastSocket(Puerto);
        InetAddress grupo = InetAddress.getByName(IpGrupo);// Grupo
        InetSocketAddress direccionGrupo = new InetSocketAddress(grupo, Puerto);
        NetworkInterface interfaz = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        // Nos unimos al grupo
        ms.joinGroup(direccionGrupo, interfaz);
        String msg = "";
        byte[] buf = new byte[1000];
        //
        while (!msg.trim().equals("*")) {
            // Recibe el paquete del servidor multicast
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);
            ms.receive(paquete);
            msg = new String(paquete.getData(), 0, paquete.getLength());
            System.out.println("Recibo: " + msg.trim());
        }
        ms.leaveGroup(direccionGrupo, interfaz); // abandonamos grupo
        ms.close(); // cierra socket
        System.out.println("Socket cerrado...");
    }
}