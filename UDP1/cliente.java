package UDP1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class cliente {

	public static void main(String[] args) {
		int puertoServidor = 12345;
		byte[] buffer = new byte[1024];
		boolean activo = true;

		try (DatagramSocket socket = new DatagramSocket();
				Scanner scanner = new Scanner(System.in)) {

			InetAddress destino = InetAddress.getLocalHost();
			System.out.println("cliente iniciado, quit para salir");

			while (activo) {
				// envio
				System.out.print("cliente ");
				String mensajeEnviar = scanner.nextLine();

				byte[] bytesEnviar = mensajeEnviar.getBytes(StandardCharsets.UTF_8);
				DatagramPacket envio = new DatagramPacket(bytesEnviar, bytesEnviar.length, destino, puertoServidor);
				socket.send(envio);

				// recibir respuesta
				DatagramPacket recibo = new DatagramPacket(buffer, buffer.length);
				socket.receive(recibo); // espera respuesta servidor

				String mensajeRecibido = new String(recibo.getData(), 0, recibo.getLength(), StandardCharsets.UTF_8);
				System.out.println("servidor: " + mensajeRecibido);

				//salir
				if(mensajeRecibido.equals("quit")) {
					System.out.println("saliendo");
				}

			}

		} catch (IOException e) {
			System.err.println("Error en el cliente UDP: " + e.getMessage());
			e.printStackTrace();
		}
	}
}