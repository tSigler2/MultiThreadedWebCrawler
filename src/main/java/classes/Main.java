package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URISyntaxException;
import java.io.InputStreamReader;
import java.io.InputStream;

public class Main {
    public static ConcurrentHashMap<Integer, String> activeUrls = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (Scanner console = new Scanner(System.in)) {
            System.out.print("Number of threads you'd like to use: ");
            ArrayList<WebCrawler> bots = new ArrayList<>();
            int k = 0, n = getInt(console);
            System.out.print("Depth for each thread to crawl: ");
            int d = getInt(console);

            try (BufferedReader r = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/input.txt")))) {
                String line;
                while (k < n && (line = r.readLine()) != null) {
                    bots.add(new WebCrawler(line, k + 1, d, activeUrls));
                    k++;
                }
                if (k < n) {
                    System.out.println("Not enough Input URLs to create all instances of Web Crawler");
                    System.out.println("Only created " + k + " instances of Web Crawler.");
                }
            } catch (IOException e) {
                System.err.println("Failed to read from the input file. Please make sure the file exists and is readable.");
                e.printStackTrace();
            }

            for (WebCrawler w : bots) {
                try {
                    w.getThread().join();
                } catch (InterruptedException e) {
                    System.err.println("A thread was interrupted unexpectedly.");
                    e.printStackTrace();
                }
            }
        }
    }

    private static int getInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }
}

