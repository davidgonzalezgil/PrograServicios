package MulticastSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;
import java.time.LocalDate;

public class servidorHora {

    public static void main(String[] args) throws Exception {
        String ipGrupo = "230.0.0.1";
        int puerto = 12345;
        MulticastSocket ms = new MulticastSocket();
        ms.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, true);
        InetAddress grupo = InetAddress.getByName(ipGrupo);

        System.out.println("servidor iiniciado");

        // envia la hora 10 veces cada 15 segundos
        for (int i = 1; i <= 10; i++) {
            String horaExacta = dameTiempo.obtenerHoraActual(LocalDate.now());
            byte[] buf = horaExacta.getBytes();

            DatagramPacket paquete = new DatagramPacket(buf, buf.length, grupo, puerto);
            ms.send(paquete);

            System.out.println("Enviado: " + horaExacta);
            Thread.sleep(15000); // espera 15 segundos
        }

        // envia "vete de aqui" para avisar a los clientes de que termina
        byte[] fin = "vete de aqui".getBytes();
        DatagramPacket paqueteFin = new DatagramPacket(fin, fin.length, grupo, puerto);
        ms.send(paqueteFin);

        ms.close();
        System.out.println("Servidor cerrado");
    }
}