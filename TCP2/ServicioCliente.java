package TCP2;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ServicioCliente {

    public static int pedirEnteroPositivo(Scanner sc) {
        int numero;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("error, no va");
                sc.next();
            }
            numero = sc.nextInt();
            if (numero < 1 || numero > 49) {
                System.out.println("el numerotiene que estar entre 1 y 49");
            }
        } while (numero < 1 || numero > 49);
        return numero;
    }

    public static int pedirEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("error, mete numero valido");
            sc.next();
        }
        return sc.nextInt();
    }

    public static boolean tieneDuplicados(int[] numeros) {
        Set<Integer> set = new HashSet<>();
        for (int numero : numeros) {
            if (!set.add(numero)) {
                System.out.println("ERROR: no puede haber números duplicados");
                return true;
            }
        }
        return false;
    }

    public static int generarReintegro() {
        return (int) (Math.random() * 10);
    }
}