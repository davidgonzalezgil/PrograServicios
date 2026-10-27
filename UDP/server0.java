package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class server0 {

    public static void main(String[] args) throws Exception {

        byte[] buffer = new byte[1024];
        DatagramPacket recibo = new DatagramPacket(buffer, buffer.length);

        DatagramSocket socket = new DatagramSocket(12345);
        socket.receive(recibo);

        int bytesRec = recibo.getLength();
        String llegada = new String(recibo.getData(), 0, bytesRec);

        System.out.println("puerto origen del mensaje: " + recibo.getPort());
        System.out.println("ip de origen:" + recibo.getAddress().getHostAddress());

        socket.close();
    }
}