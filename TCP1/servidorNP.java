package TCP1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidorNP {

    public static void main(String[] args) {
        int puerto = 6000;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("conectado");

                try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
                        DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

                    while (true) {
                        int numero = entrada.readInt();

                        // fin conexion
                        if (numero == 0) {
                            System.out.println("fin conexion");
                            break;
                        }

                        boolean resultado = esPerfecto(numero);
                        salida.writeBoolean(resultado);
                    }

                } catch (IOException e) {
                    System.out.println("error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // numero pefecto
    public static boolean esPerfecto(int numero) {
        if (numero <= 1) {
            return false;
        }

        int sumadivisores = 1;
        for (int i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                sumadivisores += i;
                if (i != numero / i) {
                    sumadivisores += numero / i;
                }
            }
        }
        return sumadivisores == numero;
    }
}