package books_rest_api.api;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HttpClientConn conn = new HttpClientConn();
        String query = "Harry Potter";

        conn.connect(query);

        /*
         * String query = "";
         * 
         * Scanner sc = new Scanner(System.in); System.out.println("Enter the query"); String input
         * = sc.nextLine();
         * 
         * query = input;
         * 
         * try { BooksCredentials.queryForBooks(query); } catch (IOException e) {
         * 
         * } catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
         * 
         * sc.close();
         */
    }
}
