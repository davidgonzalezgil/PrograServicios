package TCP0;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server0 {
    
    public static void main(String[] args) {
        
        try {
            int port = 6000;
            ServerSocket server = new ServerSocket(port); // Se añadió el ';' faltante
            System.out.println("Escuchando en " + server.getLocalPort());
            
            Socket cliente1 = server.accept(); // Esperando a un cliente
            System.out.println("Cliente conectado: " + cliente1.getRemoteSocketAddress());
            cliente1.close(); // Cierre del socket cliente
            
            Socket cliente2 = server.accept(); // Esperando a otro cliente
            System.out.println("Cliente conectado: " + cliente2.getRemoteSocketAddress());
            cliente2.close(); // Cierre del socket cliente

            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}