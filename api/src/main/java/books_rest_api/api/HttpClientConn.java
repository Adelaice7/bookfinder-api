package books_rest_api.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HttpClientConn {

    private static final String API_KEY = "";

    public void connect(String query) {

        try {

            HttpClient client = HttpClient.newBuilder()
                    .version(Version.HTTP_1_1)
                    .followRedirects(Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.googleapis.com/books/v1/volumes?q="
                            + URLEncoder.encode(query, "UTF-8") + "&key=" + API_KEY
                            + "&langRestrict=en" + "&filter=ebooks"))
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            System.out.println(response.statusCode());
            System.out.println(response.body());

            // Parse output into JSONObject ?
            // Tie to Books Java Client Library?

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
