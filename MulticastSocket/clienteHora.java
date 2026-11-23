package MulticastSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class clienteHora {

    public static void main(String[] args) throws Exception {

        String ipGrupo = "230.0.0.1";
        int puerto = 12345;
        MulticastSocket ms = new MulticastSocket(puerto);
        InetAddress grupo = InetAddress.getByName(ipGrupo);
        InetSocketAddress direccionGrupo = new InetSocketAddress(grupo, puerto);

        ms.joinGroup(direccionGrupo, null);

        String msg = "";
        byte[] buf = new byte[1000];

        while (!msg.trim().equals("vete de aqui")) {
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);
            ms.receive(paquete);
            msg = new String(paquete.getData(), 0, paquete.getLength()).trim();

            if (!msg.equals("vete de aqui")) {
                System.out.println("hora: " + msg);
            }
        }
        ms.leaveGroup(direccionGrupo, null);
        ms.close();
        System.out.println("Socket cerrado...");
    }

    // quizas examen
    public static NetworkInterface obtenerInterfaz() throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            if (ni.isUp() && !ni.isLoopback() && ni.supportsMulticast()) {
                return ni;
            }
        }
        return null;
    }
}