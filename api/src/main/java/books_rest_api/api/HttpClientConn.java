package books_rest_api.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class HttpClientConn {

    public void connect() {

        try {

            HttpClient client = HttpClient.newBuilder()
                    .version(Version.HTTP_1_1)
                    .followRedirects(Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .build();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.googleapis.com/books/v1/volumes/volumeId"))
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .build();
            
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.statusCode());
            
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
