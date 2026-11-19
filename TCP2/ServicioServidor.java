package TCP2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ServicioServidor {
    private static int[] arrayServidor = new int[6];
    private static int complementarioServidor;
    private static int reintegroServidor;

    public static void rellenarArrayServidor() {
        boolean esValido;
        int[] arraySinRepeticiones = new int[6];
        do {
            for (int i = 0; i < arrayServidor.length; i++) {
                arraySinRepeticiones[i] = (int) (Math.random() * 49) + 1;
            }
            // Comprobación correcta de validez del array aleatorio
            esValido = !hayRepetidos(arraySinRepeticiones);
        } while (!esValido);

        for (int i = 0; i < arrayServidor.length; i++) {
            arrayServidor[i] = arraySinRepeticiones[i];
        }
        Arrays.sort(arrayServidor);

        complementarioServidor = (int) (Math.random() * 49) + 1;
        reintegroServidor = (int) (Math.random() * 10);
    }

    public static int coincidencias(int[] arrayCliente) {
        int contadorIguales = 0;
        for (int i = 0; i < arrayServidor.length; i++) {
            for (int j = 0; j < arrayCliente.length; j++) {
                if (arrayServidor[i] == arrayCliente[j]) {
                    contadorIguales++;
                    break;
                }
            }
        }
        return contadorIguales;
    }

    public static boolean coincidenReintegro(int reintegro) {
        return reintegro == reintegroServidor;
    }

    public static boolean coincidenciaComplementario(int[] arrayCliente) {
        for (int i = 0; i < arrayCliente.length; i++) {
            if (arrayCliente[i] == complementarioServidor) {
                return true;
            }
        }
        return false;
    }

    public static String devolucionAlCliente(int[] arrayCliente, int reintegroCliente) {
        int numCoincidencias = coincidencias(arrayCliente);
        boolean esReintegro = coincidenReintegro(reintegroCliente);
        boolean esComplementario = coincidenciaComplementario(arrayCliente);

        if (numCoincidencias == 6 && esReintegro) {
            return "Has acertado los 6 numeros mas el reintegro eres el ganador de la categoria especial";
        } else if (numCoincidencias == 6) {
            return "Has acertado los seis numeros eres el ganador del primer premio";
        } else if (numCoincidencias == 5 && esComplementario) {
            return "Has acertado cinco numeros mas el complementario eres el ganador del segundo premio";
        } else if (numCoincidencias == 5) {
            return "Has acertado cinco numeros eres el ganador del tercer premio";
        } else if (numCoincidencias == 4) {
            return "Has acertado 4 numeros eres el ganador del cuarto premio";
        } else if (numCoincidencias == 3) {
            return "Has acertado 3 numeros eres el ganador del quinto premio";
        } else if (esReintegro) {
            return "Has acertado el numero del reintegro se te devolvera el importe de la operacion";
        } else {
            return "No has ganado nada lo siento";
        }
    }

    public static void mostrarArrayServidor() {
        System.out.println("Array servidor: " + Arrays.toString(arrayServidor));
        System.out.println("Complementario servidor: " + complementarioServidor);
        System.out.println("Reintegro servidor: " + reintegroServidor);
    }

    public static boolean hayRepetidos(int[] arrayServidor) {
        Set<Integer> vistos = new HashSet<>();
        for (int i : arrayServidor) {
            if (!vistos.add(i)) {
                return true;
            }
        }
        return false;
    }
}