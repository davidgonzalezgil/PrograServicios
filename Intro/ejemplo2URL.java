import java.io.*;
import java.net.*;

public class ejemplo2URL {

    public static void main(String[] args) {
        URL url;
        URLConnection urlCon;
        try {
            url = URI.create("http://www.elaltozano.es").toURL();
            urlCon= url.openConnection();

            BufferedReader in;
            InputStream inputStream = urlCon.getInputStream();
            in = new BufferedReader(new 
                    InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

            in.close();
        }
        catch (MalformedURLException e) {
            System.err.println("URL no válida: " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }//
}// ejemplo2URL