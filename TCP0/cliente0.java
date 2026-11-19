package TCP0;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class cliente0 {

    public static void main(String[] args) {

        try {
            String host = "localhost";
            int port = 6000; // puerto remoto
            // ABRIR SOCKET Y CONECTAR AL SERVIDOR
            Socket cliente = new Socket(host, port);

            InetAddress inetAddress = cliente.getInetAddress();
            System.out.println("Puerto local: " + cliente.getLocalPort());
            System.out.println("Puerto Remoto: " + cliente.getPort());
            System.out.println("Host Remoto: " + inetAddress.getHostName());
            System.out.println("IP Host Remoto: " + inetAddress.getHostAddress());

            cliente.close(); // Cierra el socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //crear input y output en el cliente y servidor para enviar y recibir mensajes
}