
package classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebCrawler implements Runnable {
    public static final List<String> visitedLinks = Collections.synchronizedList(new ArrayList<>());
    private static final String INPUT_FILE_PATH = "/input.txt";

    public int MAX_DEPTH;
    private Thread thread;
    private String fLink;
    private int ID;
    private ConcurrentHashMap<Integer, String> activeUrls;

    public WebCrawler(String link, int n, int depth, ConcurrentHashMap<Integer, String> activeUrls) {
        this.activeUrls = activeUrls;
        System.out.println("New Web Crawler");
        fLink = link;
        ID = n;
        MAX_DEPTH = depth;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        activeUrls.put(ID, fLink);
        crawl(1, fLink);
        activeUrls.remove(ID);
    }

    private void crawl(int level, String url) {
        if(level <= MAX_DEPTH) {
            Document doc = request(url);

            if(doc != null) {
                for(Element links : doc.select("a[href]")) {
                    String next = links.absUrl("href");

                    if(!visitedLinks.contains(next) && (next.contains("https://") || next.contains("http://"))) {
                        visitedLinks.add(next);
                        crawl(level + 1, next);
                    }
                }

                try{
                    String homeDir = System.getProperty("user.home");
                    File outFile = new File(homeDir + "/Downloads/output.txt");
                    if (!outFile.exists()) {
                        outFile.createNewFile();
                    }
                    PrintWriter out = new PrintWriter(new FileWriter(outFile, true));
                    PrintWriter in = new PrintWriter(new FileWriter(new File(Main.class.getResource(INPUT_FILE_PATH).getPath()), true));
                    in.println(url);
                    for(String link : visitedLinks){
                        out.println(link);                    
                    }
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode() == 200) {
                System.out.println(url + " Bot: " + ID);

                String title = doc.title();
                System.out.println("Crawling: " + title);
                return doc;
            }
            return null;
        }
        catch(IOException e) {
            return null;
        }
    }

    public Thread getThread() {
        return thread;
    }
}

