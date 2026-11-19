package TCP2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 6000;

        try {
            Socket cliente = new Socket(host, puerto);
            Scanner sc = new Scanner(System.in);

            DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
            DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
            System.out.println("Conectado al servidor de Números Perfectos.");

            while (true) {
                int[] numerosCliente = new int[7];
                do {
                    System.out.println("Introduce los numeros de tu primitiva entre 1-49");
                    for (int i = 0; i < numerosCliente.length; i++) {
                        numerosCliente[i] = ServicioCliente.pedirEnteroPositivo(sc);
                    }
                } while (ServicioCliente.tieneDuplicados(numerosCliente));

                flujoSalida.writeInt(numerosCliente.length);
                Arrays.sort(numerosCliente, 0, 6);

                System.out.println("introduce el septimo numero");
                numerosCliente[6] = ServicioCliente.pedirEnteroPositivo(sc);

                flujoSalida.writeInt(numerosCliente.length);
                for (int num : numerosCliente) {
                    flujoSalida.writeInt(num);
                }

                System.out.println("Número de reintegro generado:");
                int reintegro = ServicioCliente.generarReintegro();
                System.out.println(reintegro);
                flujoSalida.writeInt(reintegro);
                flujoSalida.flush();

                String respuesta = flujoEntrada.readUTF();
                System.out.println("Recibiendo un mensaje del servidor: " + respuesta);
                
                System.out.println("Si desea salir introduzca 0 (otro número para continuar): ");
                int decision = ServicioCliente.pedirEntero(sc);
                
                // Se envía la decisión al servidor para sincronizar la comunicación
                flujoSalida.writeInt(decision);
                flujoSalida.flush();

                if (decision == 0) {
                    System.out.println("Cerrando comunicacion");
                    break;
                }
            }

            flujoEntrada.close();
            flujoSalida.close();
            cliente.close();
            sc.close();
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}