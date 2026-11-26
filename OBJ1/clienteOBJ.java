package OBJ1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clienteOBJ {

        public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);
                String host = "localhost";
                int puerto = 6000;

                System.out.println("PROGRAMA CLIENTE INICIADO....");

                try (Socket cliente = new Socket(host, puerto);
                                ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());
                                ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream())) {

                        System.out.println("dame el nombre ");
                        String nombre = sc.nextLine();
                        System.out.println("dame la edad");
                        int edad = sc.nextInt();

                        // crear el objeto Persona y enviarlo al servidor
                        Persona per = new Persona(nombre, edad);
                        perSal.writeObject(per);
                        perSal.flush();
                        System.out.println("envio: " + per);

                        // recibir el objeto Persona modificado desde el servidor
                        Persona datoModificado = (Persona) perEnt.readObject();
                        System.out.println("recibo: " + datoModificado);

                } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Error en el cliente: " + e.getMessage());
                        e.printStackTrace();
                }
        }
}