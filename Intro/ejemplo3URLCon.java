import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ejemplo3URLCon {

    public static void main(String[] args) throws Exception {
        String cadena;
        // Uso de URI para evitar advertencias de deprecación de new URL() en Java moderno
        URL url = URI.create("http://localhost/2014/vernombre.html").toURL();
        URLConnection conexion = url.openConnection();

        System.out.println("Direccion [getURL()]: " + conexion.getURL());

        Date fecha = new Date(conexion.getLastModified());
        System.out.println("Fecha ultima modificacion [getLastModified()]: " + fecha);
        System.out.println("Tipo de Contenido [getContentType()]: " + conexion.getContentType());

        System.out.println("=================================================");
        System.out.println("TODOS LOS CAMPOS DE CABECERA CON getHeaderFields(): ");

        // Uso de genéricos explícitos para evitar advertencias (rawtypes)
        Map<String, List<String>> camposcabecera = conexion.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : camposcabecera.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("=================================================");
        System.out.println("CAMPOS 1 Y 4 DE CABECERA:");
        System.out.println("getHeaderField(1)=> " + conexion.getHeaderField(1));
        System.out.println("getHeaderField(4)=> " + conexion.getHeaderField(4));
        System.out.println("=================================================");

        System.out.println("CONTENIDO DE [url.getFile()]:" + url.getFile());

        // try-with-resources para asegurar el cierre del flujo de entrada
        try (BufferedReader pagina = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((cadena = pagina.readLine()) != null) {
                System.out.println(cadena);
            }
        }
    }
}