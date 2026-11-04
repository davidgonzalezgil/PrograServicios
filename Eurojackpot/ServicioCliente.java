package Eurojackpot;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ServicioCliente {

    public static int pedirSoloEntero(Scanner sc) {
       //si pones un numero que no sea un entero, error
        while (!sc.hasNextInt()) {
            System.out.println("Error: Debes introducir un número entero válido.");
            sc.next();
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    public static boolean tieneFueraDeRango(int[] numeros, int min, int max) {
       //recorre el array
        for (int numero : numeros) {
            //si numero menor que min o numero mayor que max, error
            if (numero < min || numero > max) {
                System.out.println("ERROR: Hay números fuera del rango permitido (" + min + " - " + max + ").");
                return true;
            }
        }
        return false;
    }

    public static boolean tieneDuplicados(int[] numeros) {
        //crea un set para almacenar los numeros
        Set<Integer> set = new HashSet<>();
        for (int numero : numeros) {
            //si el numero ya esta en el set, error
            if (!set.add(numero)) {
                System.out.println("ERROR: No puede haber números duplicados en el boleto.");
                return true;
            }
        }
        return false;
    }
}