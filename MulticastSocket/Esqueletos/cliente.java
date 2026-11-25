package MulticastSocket.Esqueletos;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class cliente {

    public static void main(String[] args) throws Exception {

        // DEFINICIÓN DE DATOS DE RED (Iguales a los del servidor)
        String ipGrupo = "230.0.0.1";
        int puerto = 12345;

        // CREACIÓN DEL SOCKET Y SUSCRIPCIÓN AL GRUPO
        // Creamos el socket INDICANDO EL PUERTO, porque este programa sí va a escuchar
        MulticastSocket ms = new MulticastSocket(puerto);
        // Convertimos el texto de la IP en un objeto InetAddress
        InetAddress grupo = InetAddress.getByName(ipGrupo);
        // Juntamos la IP del grupo y el puerto en un único objeto de dirección de
        // socket
        InetSocketAddress direccionGrupo = new InetSocketAddress(grupo, puerto);

        // nos suscribimos al grupo para empezar a recibir lo que envie el servidor
        // el segundo parametro es la tarjeta de red: 'null' usa la que tengas por
        // defecto (o llamas a obtenerInterfaz())
        ms.joinGroup(direccionGrupo, null);

        // RECEPCIÓN DE DATOS
        // creamos un array de bytes vacio que hara de buzon donde guardar lo que
        // llegue
        byte[] buf = new byte[1000];
        String msg = "";

        // bucle para seguir escuchando hasta que llegue la palabra clave de salida
        while (!msg.trim().equals("vete de aqui")) {
            // creamos un paquete vacío (solo 2 datos: el array donde guardar y su tamaño)
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);

            // el programa se queda PAUSADO aquí esperando hasta que llegue un paquete
            ms.receive(paquete);

            // extraemos los bytes recibidos desde la posición 0 hasta la longitud real del
            // mensaje (getLength)
            // y los convertimos a String. .trim() limpia los espacios sobrantes del buffer
            msg = new String(paquete.getData(), 0, paquete.getLength()).trim();

            // si el mensaje no es la orden de cerrar, lo mostramos por pantalla
            if (!msg.equals("vete de aqui")) {
                System.out.println("Recibido: " + msg);
            }
        }

        // ABANDONAR EL GRUPO Y CERRAR
        // nos damos de baja del grupo multicast antes de irnos
        ms.leaveGroup(direccionGrupo, null);
        // cerramos el socket para liberar el puerto

    }

    // MÉTODO EXTRA
    // joinGroup/leaveGroup)
    public static NetworkInterface obtenerInterfaz() throws Exception {
        // Obtiene una lista con todas las tarjetas de red del ordenador (Wi-Fi,
        // Ethernet, virtuales...)
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        // Recorre la lista una por una
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            // Comprueba 3 cosas: que esté encendida (isUp), que no sea interna del PC
            // (!isLoopback)
            // y que permita tráfico multicast (supportsMulticast)
            if (ni.isUp() && !ni.isLoopback() && ni.supportsMulticast()) {
                // Devuelve la primera tarjeta válida que encuentre
                return ni;
            }
        }
        // Si no encuentra ninguna válida, devuelve null
        return null;
    }
}
