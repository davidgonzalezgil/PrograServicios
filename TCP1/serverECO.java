package TCP1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class serverECO {

    // LÓGICA DEL SERVICIO 
    public static String convertirAMayusculas(String cadena) {
        return cadena.toUpperCase();
    }

    public static void main(String[] args) {

        try {
            int port = 6000;
            ServerSocket server = new ServerSocket(port);
            System.out.println("Escuchando en " + server.getLocalPort());

            Socket cliente1 = server.accept(); // Esperando a un cliente
            System.out.println("Cliente conectado: " + cliente1.getRemoteSocketAddress());
            
            InputStream entrada = null;
            try {
                entrada = cliente1.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataInputStream dataInputStream = new DataInputStream(entrada);

            OutputStream salida = null;
            try {
                salida = cliente1.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(salida);

            boolean sigue = true; // false = cortar la comunicación

            while (sigue) {
                String mensaje = dataInputStream.readUTF();
                System.out.println("Mensaje recibido del cliente: " + mensaje);

                if (mensaje.equalsIgnoreCase("cortar")) {
                    sigue = false;
                } else {
                    String respuesta = convertirAMayusculas(mensaje); // llamada a la logica para convertir a mayusculas
                    dataOutputStream.writeUTF(respuesta);
                }
            }

            System.out.println("Comunicación cortada por el cliente");

            dataInputStream.close();
            dataOutputStream.close();
            cliente1.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}