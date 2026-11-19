package UDP1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class server {

    public static void main(String[] args) {
        int puertoEscucha = 12345;
        byte[] buffer = new byte[1024];
        boolean activo = true;

        try (DatagramSocket socket = new DatagramSocket(puertoEscucha);
                Scanner scanner = new Scanner(System.in)) {

            System.out.println("servidor activo" + puertoEscucha);

            while (activo) {
                // llegada
                DatagramPacket recibo = new DatagramPacket(buffer, buffer.length);
                socket.receive(recibo); // espera mensaje cliente

                String mensajeRecibido = new String(recibo.getData(), 0, recibo.getLength(), StandardCharsets.UTF_8)
                        .trim();
                System.out.println("cliente: " + mensajeRecibido);
                // envio respuesta
                System.out.print("srvidor");
                String mensajeEnviar = scanner.nextLine();

                byte[] bytesEnviar = mensajeEnviar.getBytes(StandardCharsets.UTF_8);
                // responder a la direccion y puerto
                DatagramPacket envio = new DatagramPacket(
                        bytesEnviar,
                        bytesEnviar.length,
                        recibo.getAddress(),
                        recibo.getPort());
                socket.send(envio);

                //salir
				if(mensajeRecibido.equals("quit")) {
					System.out.println("saliendo");
				}

            }

        } catch (IOException e) {
            System.err.println("Error en el servidor UDP: " + e.getMessage());
            e.printStackTrace();
        }
    }
}