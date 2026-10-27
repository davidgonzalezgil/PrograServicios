package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class cliente0 {

    public static void main(String[] args) throws Exception {
        int port = 12345; // puerto al que envío
        InetAddress destino = InetAddress.getLocalHost(); // IP a la que envío
        
        String saludo = "Enviando Saludos !!";
        byte[] mensaje = new byte[1024]; // codificarlo a bytes para enviarlo

        mensaje = saludo.getBytes();

        // construyo el datagrama a enviar
        DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, destino, port);

        // envío del datagrama a través del socket
        DatagramSocket socket = new DatagramSocket();
        socket.send(envio);
        socket.close();
    }
}