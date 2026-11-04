package Eurojackpot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ServerService {
    //Guarda los números ganadores del servidor: las posiciones 0-4 son los 5 números 
    // y las 5-6 son los 2 soles
    private static int[] arrayServidor = new int[7];

    public static void rellenarArrayNummerosServidor() {
        boolean esValido;
        //array numeros
        int[] arraySinRepeticiones = new int[5];
        do {
            //crea 5 numeros aleatorios entre 1 y 50 y los guarda en el array
            esValido = true;
            for (int i = 0; i < arraySinRepeticiones.length; i++) {
                arraySinRepeticiones[i] = (int) (Math.random() * 50) + 1;
            }
            //si hay repetidos, vuelve a generar los numeros
            if (hayRepetidos(arraySinRepeticiones)) {
                esValido = false;
            }
        } while (!esValido);

        for (int i = 0; i < 5; i++) {
            arrayServidor[i] = arraySinRepeticiones[i];
        }
    }

    //Igual que el anterior, pero con 2 números entre 1 y 12, 
    // que guarda en las posiciones 5 y 6.
    public static void rellenarSolesServidor() {
        boolean esValido;
        int[] arraySinRepeticiones = new int[2];
        do {
            esValido = true;
            for (int i = 0; i < arraySinRepeticiones.length; i++) {
                arraySinRepeticiones[i] = (int) (Math.random() * 12) + 1;
            }
            if (hayRepetidos(arraySinRepeticiones)) {
                esValido = false;
            }
        } while (!esValido);

        for (int i = 0; i < 2; i++) {
            arrayServidor[5 + i] = arraySinRepeticiones[i];
        }
    }

    //toma cada uno de los 5 numero del servidor 
    //y los copara con los 5 del cliente 
    public static int coincidenciasNumeros(int[] arrayCliente) {
        int contadorIguales = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //si lo encuentra, suma 1 al contador y sale del bucle interior
                if (arrayServidor[i] == arrayCliente[j]) {
                    contadorIguales++;
                    break;
                }
            }
        }
        //devuelve cuantos coinciden
        return contadorIguales;
    }

    //Lo mismo, pero con los 2 soles.
    public static int coincidenciaSoles(int[] solesCliente) {
        int contadorIguales = 0;
        for (int i = 5; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                if (arrayServidor[i] == solesCliente[j]) {
                    contadorIguales++;
                    break;
                }
            }
        }
        return contadorIguales;
    }

    // MÉTODO AÑADIDO: Permite procesar el texto recibido por el socket UDP y devolver la respuesta
    public static String devolucionAlCliente(int numsCliente[], int[] solesCliente) {
        int numCoincidencias = coincidenciasNumeros(numsCliente);
        int solesCoincidencias = coincidenciaSoles(solesCliente);
        return "Aciertos -> Números: " + numCoincidencias + "/5 | Soles: " + solesCoincidencias + "/2";
    }

    public static void mostrarArrayServidor() {
        System.out.println("Array servidor (5 números + 2 soles): " + Arrays.toString(arrayServidor));
    }

    public static boolean hayRepetidos(int[] array) {
        Set<Integer> vistos = new HashSet<>();
        for (int i : array) {
            if (!vistos.add(i)) {
                return true;
            }
        }
        return false;
    }
}