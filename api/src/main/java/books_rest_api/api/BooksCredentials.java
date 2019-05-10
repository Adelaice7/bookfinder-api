package books_rest_api.api;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.BooksRequestInitializer;

public class BooksCredentials {
    
    final static String API_KEY = "";
    
    private final static String APPLICATION_NAME = "Google Books Search";
    
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.FRANCE);
    
    public static void queryForBooks(String query) throws Exception {
        
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                .build();
        
        System.out.println("Query: [" + query + "]" );
        com.google.api.services.books.Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setFilter("ebooks");
        
        Volumes volumes = volumesList.execute();
        
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            System.out.println("No results");
            return;
        }
        
        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            Volume.SaleInfo saleInfo = volume.getSaleInfo();
            
            System.out.println("--------------------");
            
            // Title
            System.out.println("Title: " + volumeInfo.getTitle());
            
            //Authors
            List<String> authors = volumeInfo.getAuthors();
            System.out.print("Author(s): ");
            
            for (int i = 0; i < authors.size(); i++) {
                System.out.print(authors.get(i));
                if (i < authors.size() - 1) {
                    System.out.print(", ");
                }
            }
            
            System.out.println();
            
            //Description
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                System.out.println("Description: ");
                System.out.println(volumeInfo.getDescription());
            }
            
            // Ratings
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                int fullRating = (int) volumeInfo.getAverageRating().doubleValue();
                System.out.println("User rating: ");
                
                for (int i = 0; i < fullRating; i++) {
                    System.out.print("*");
                }
                System.out.println(" (" + volumeInfo.getRatingsCount() + " rating(s))");
            }
            
            // PRICE
            if (saleInfo != null && saleInfo.getSaleability().equals("FOR_SALE")) {
                double save = saleInfo.getListPrice().getAmount() - saleInfo.getRetailPrice().getAmount();
                if (save > 0.0) {
                    System.out.print("List price: " + CURRENCY_FORMATTER.format(saleInfo.getListPrice().getAmount()) + " | ");
                }
                System.out.print("Google eBooks price: " + CURRENCY_FORMATTER.format(saleInfo.getRetailPrice().getAmount()));
                if (save > 0.0) {
                    System.out.print(" You save: " + CURRENCY_FORMATTER.format(save) + 
                            " (" + save/saleInfo.getListPrice().getAmount() + ")");
                }
                System.out.println();
            }

            System.out.println("--------------------");
            System.out.println(
                volumes.getTotalItems() + " total results at http://books.google.com/ebooks?q="
                + URLEncoder.encode(query, "UTF-8"));
        }
    }

}
