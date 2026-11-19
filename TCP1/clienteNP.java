package TCP1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class clienteNP {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = "localhost";
        int puerto = 6000;

        try (Socket socket = new Socket(host, puerto);
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("conectado al servidor.");
            System.out.println("Introduce números enteros positivos");

            while (true) {
                System.out.print("numero: ");

                //validar
                if (!sc.hasNextInt()) {
                    System.out.println("pon un numero valido.");
                    sc.next(); 
                    continue;
                }

                int numero = sc.nextInt();

                //corte
                if (numero == 0) {
                    salida.writeInt(0);
                    System.out.println("desconectado.");
                    break;
                }

                //solo enteros positivos
                if (numero < 0) {
                    System.out.println("el numero tiene que aser psitivo");
                    continue;
                }

                salida.writeInt(numero);

                boolean esPerfecto = entrada.readBoolean();
                if (esPerfecto) {
                    System.out.println("Eel numero " + numero + " ES perfecto.");
                } else {
                    System.out.println("el numero " + numero + " NO es perfecto.");
                }
            }

        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }
    }
}