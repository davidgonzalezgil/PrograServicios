package MulticastSocket.Esqueletos;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;

public class servicio {

    public static void main(String[] args) throws Exception {

        // DEFINICION DE DATOS
        // ip grupal
        String ipGrupo = "230.0.0.1";
        int puerto = 12345;

        // CREAR Y CONFIGURAR EL SOCKET
        // crear el scoket multicast, como solo envía nno lleva puerto
        MulticastSocket ms = new MulticastSocket();

        // activar el loopback para que si pruebas cliente y servidor funcione
        ms.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, true);

        // transformar el text de la ip en un objeto de direccion red
        InetAddress grupo = InetAddress.getByName(ipGrupo);

        // PREPARAR Y ENVIAR EL MENSAJE (ne bucle si envia varias veces)
        String mensaje = "Hola";

        // los sockets solo mandan bytes
        byte[] buff = mensaje.getBytes();

        // crear el sobre
        DatagramPacket paquete = new DatagramPacket(buff, buff.length, grupo, puerto);
        // mandar
        ms.send(paquete);

    }
}
