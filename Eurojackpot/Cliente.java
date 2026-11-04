package Eurojackpot;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        int puertoServidor = 1234;

        try {
            InetAddress destino = InetAddress.getLocalHost();
            DatagramSocket cliente = new DatagramSocket();
            Scanner sc = new Scanner(System.in);

            System.out.println("Cliente preparado para intentar ganar el bote de Eurojackpot");

            while (true) {
                //crea dos arrays vacios, 5 para numero y 2 para soles
                int[] numerosCliente = new int[5];
                int[] solesCliente = new int[2];

                do {
                    System.out.println("Introduce numeros entre 1-50 :");
                    for (int i = 0; i < numerosCliente.length; i++) {
                        //pide al usuario un numero entero por teclado a traves del metodo pedirSoloEntero de la clase ServicioCliente
                        numerosCliente[i] = ServicioCliente.pedirSoloEntero(sc);
                    }
                    //lo mismo para los soles, pero entre 1-12
                    System.out.println("Introduce soles entre 1-12 :");
                    for (int i = 0; i < solesCliente.length; i++) {
                        solesCliente[i] = ServicioCliente.pedirSoloEntero(sc);
                    }

                        //comprueba si hay duplicados o fuera de rango tanto en numero y soles,
                        //si hay error, vuelve a pedir los numeros y soles
                } while (ServicioCliente.tieneDuplicados(numerosCliente) == true
                        || ServicioCliente.tieneDuplicados(solesCliente) == true
                        || ServicioCliente.tieneFueraDeRango(numerosCliente, 1, 50)
                        || ServicioCliente.tieneFueraDeRango(solesCliente, 1, 12));

                //ordena de menor a mayor
                Arrays.sort(numerosCliente);

                //almacen donde se guardan los numero y soles
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                //conecta a bOut y permite transformar los datos en bytes
                DataOutputStream dos = new DataOutputStream(bOut);

                //guarda un numero entero que dice cauntos elementos hay
                dos.writeInt(numerosCliente.length);
                //toma el numero actual y lo guarda covertido en bytes
                for (int num : numerosCliente) {
                    dos.writeInt(num);
                }
                //lo mismo para los soles
                dos.writeInt(solesCliente.length);
                for (int sol : solesCliente) {
                    dos.writeInt(sol);
                }
                //guarda los datos
                dos.flush();

                //extrae los datos de bOut y los guarda en un array de bytes
                byte[] bufferEnvio = bOut.toByteArray();
                //prepara el paquete con los datos, la longitud, la direccion y el puerto del servidor
                DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, destino,
                        puertoServidor);
                //envia el paquete al servidor
                cliente.send(paqueteEnvio);

                //caja vacia con capcidad de 1024
                byte[] bufferRecibo = new byte[1024];
                //Prepara un paquete receptor vacío vinculado a la caja de 1024 bytes para recibir los datos entrantes
                DatagramPacket paqueteRecibo = new DatagramPacket(bufferRecibo, bufferRecibo.length);
                //pausa el programa y espera el mensaje
                cliente.receive(paqueteRecibo);

                //traduce los bytes recibidos a un String y lo imprime en pantalla
                String respuesta = new String(paqueteRecibo.getData(), 0, paqueteRecibo.getLength(),
                        StandardCharsets.UTF_8);
                System.out.println(respuesta);

                String decision = "";
                //salir si o no, si no es y/n vuelve a preguntar
                do {
                    System.out.println("DESEA SALIR ? (y / n)");

                    while (!sc.hasNextLine()) {
                        sc.next();
                    }
                    decision = sc.nextLine().trim();

                    if (!decision.equalsIgnoreCase("y") && !decision.equalsIgnoreCase("n")) {
                        System.out.println("Opción no válida. Introduce y/n");
                    }

                } while (!decision.equalsIgnoreCase("y") && !decision.equalsIgnoreCase("n"));

                //mandar la decision de si o no al servidor para que cierre el socket
                byte[] bufferDecision = decision.getBytes(StandardCharsets.UTF_8);
                DatagramPacket paqueteDecision = new DatagramPacket(bufferDecision, bufferDecision.length, destino,
                        puertoServidor);
                cliente.send(paqueteDecision);

                //si la decision es y, cierra el bucle y termina el programa
                if (decision.toLowerCase().equalsIgnoreCase("y")) {
                    System.out.println("Cerrando comunicacion");
                    break;
                }

            }

            cliente.close();
            sc.close();
        } catch (IOException e) {
            System.err.println("Error en el cliente UDP: " + e.getMessage());
        }
    }
}