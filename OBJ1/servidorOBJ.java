package OBJ1;

import java.io.*;
import java.net.*;

public class servidorOBJ {

    public static void main(String[] args) {
        int numeroPuerto = 6000;

        try (ServerSocket servidor = new ServerSocket(numeroPuerto)) {
            System.out.println("Esperando al cliente en el puerto " + numeroPuerto + "...");

            try (Socket cliente = servidor.accept();
                    ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
                    ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream())) {

                outObjeto.flush();

                // recibir objeto Persona enviado por el cliente
                Persona dato = (Persona) inObjeto.readObject();
                System.out.println("Recibo: " + dato);

                // modificar el objeto
                dato.setNombre(dato.getNombre().toUpperCase());
                System.out.println("recibo: " + dato);

                dato.setEdad(dato.getEdad() + 10);

                // devolver el objeto modificado al cliente
                outObjeto.writeObject(dato);
                outObjeto.flush();
                System.out.println("Envio: " + dato);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
