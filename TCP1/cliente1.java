package TCP1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class cliente1 {

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

            InputStream entrada = null;
            try {
                entrada = cliente.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataInputStream dataInputStream = new DataInputStream(entrada);

            OutputStream salida = null;
            try {
                salida = cliente.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(salida);

            dataOutputStream.writeUTF("Hola servidor, soy el cliente");

            String respuestaServidor = dataInputStream.readUTF();
            System.out.println("Respuesta recibida del servidor: " + respuestaServidor);

            Scanner sc = new Scanner(System.in);

            boolean sigue = true; 

            while(sigue) {
                System.out.println("Ingrese un mensaje para enviar al servidor (\"quite\" para terminar): ");
                String mensaje = sc.nextLine();
                dataOutputStream.writeUTF(mensaje);

                if (mensaje.equalsIgnoreCase("quite")) {
                    sigue = false;
                } else {
                    String mensaje2 = dataInputStream.readUTF();
                    System.out.println("Mensaje recibido del servidor: " + mensaje2);
                }
            }

            dataInputStream.close();
            dataOutputStream.close();
            cliente.close(); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}