package MulticastSocket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class dameTiempo {

    public static String obtenerHoraActual(LocalDate fecha) {
        // Obtenemos la hora actual
        LocalTime horaActual = LocalTime.now();

        // Formateamos la hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = horaActual.format(formatter);

        // Devolvemos una cadena con la fecha y la hora
        return "Fecha: " + fecha + ", Hora actual: " + horaFormateada;
    }
}
