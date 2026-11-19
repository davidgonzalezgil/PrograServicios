import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ejemplo2URLCon {
    
    public static void main(String[] args) {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL("http://localhost/2014/vernombre.php");
            URLConnection conexion = url.openConnection();
            conexion.setDoOutput(true);

            //crea un scanner que pida el nombre y el apellido 
           Scanner sc = new Scanner(System.in);
            System.out.println("dame tu nombre");
            String nombre = sc.nextLine();
            System.out.println("damee los apellidos");
            String apellido = sc.nextLine();

            // Construcción de la cadena con StringBuilder y codificación para evitar errores con espacios/tildes
            StringBuilder sb = new StringBuilder();
            sb.append("nombre=").append(URLEncoder.encode(nombre, StandardCharsets.UTF_8))
            .append("&apellidos=").append(URLEncoder.encode(apellido, StandardCharsets.UTF_8));

            String cadena = sb.toString();

            // ESCRIBIR EN LA URL (reemplaza 'conexion' por el nombre de tu variable URLConnection)
            PrintWriter output = new PrintWriter(conexion.getOutputStream());
            

            //LEER DE LA URL
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conexion.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }

        } catch (MalformedURLException me) {
            System.err.println("MalformedURLException: " + me);
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe);
        }
    }//main
}
