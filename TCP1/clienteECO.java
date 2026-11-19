package TCP1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class clienteECO {

    public static void main(String[] args) {

        try {
            String host = "10.101.21.127";
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

            Scanner sc = new Scanner(System.in);

            boolean sigue = true; // false = cortar la comunicación

            while(sigue) {
                System.out.println("Escriba una cadena (\"cortar\" para terminar): ");
                String mensaje = sc.nextLine();
                dataOutputStream.writeUTF(mensaje);

                if (mensaje.equalsIgnoreCase("cortar")) {
                    sigue = false;
                } else {
                    String respuesta = dataInputStream.readUTF();
                    System.out.println("Respuesta del servidor: " + respuesta);
                }
            }

            sc.close();
            dataInputStream.close();
            dataOutputStream.close();
            cliente.close(); 

        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
}

