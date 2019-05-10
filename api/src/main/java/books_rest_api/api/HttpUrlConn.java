package books_rest_api.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpUrlConn {
    
    private String urlString = "https://raw.githubusercontent.com/Adelaice7/filereader/master/doc/valami.txt";
    
    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }



    public void connectGet() {
        URL urlObj = null;
        HttpURLConnection con = null;
        BufferedReader in = null;
        try {
            urlObj = new URL(urlString);

            /*
            Scanner s = new Scanner(urlObj.openStream());
            
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }
            */
            
            con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            
            con.setRequestProperty("Content-Type", "application/json");
            String contentType = con.getHeaderField("Content-Type");
            
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            
            int status = con.getResponseCode();
            
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                
                response.append("\n");
            }
            
            System.out.println(response.toString());
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            con.disconnect();
        }

    }

}
