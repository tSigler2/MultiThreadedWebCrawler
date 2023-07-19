package classes;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import java.io.*;

public class WebCrawler implements Runnable{
	public int MAX_DEPTH;
	private Thread thread;
	private String fLink;
	private ArrayList<String> visitedLinks = new ArrayList<String>();
	private int ID;
	
	//Constructor
	public WebCrawler(String link, int n, int depth) {
		System.out.println("New Web Crawler");
		fLink = link;
		ID = n;
		MAX_DEPTH = depth;
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		crawl(1, fLink);
	}
	
	private void crawl(int l, String url) {
		if(l <= MAX_DEPTH) {
			Document doc = request(url);
			
			if(doc != null) {
				for(Element links : doc.select("a[href]")) {
					String next = links.absUrl("href");
					
					if(visitedLinks.contains(next) == false && (next.contains("https://") || next.contains("http://"))) {
						crawl(l++, next);
					}
					
					//Writing out urls to output of crawled pages and remembering most recently crawled page
					try {
						File f = new File("txtFiles/output.txt");
						BufferedReader r = new BufferedReader(new FileReader(f));
						r.close();
						File i = new File("txtFiles/input.txt");
						PrintWriter flush = new PrintWriter(i);
						flush.print("");
						flush.close();
						PrintWriter out = new PrintWriter(new FileWriter(f, true));
						PrintWriter in = new PrintWriter(new FileWriter(i, true));
						out.write(next + "\n");
						in.write(url + "\n");
						out.close();
						in.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//Using JSoup to access web pages via http:// or https:// urls
	private Document request(String url) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			if(con.response().statusCode() == 200) {
				System.out.println(url + " Bot: " + ID);
				
				String t = doc.title();
				System.out.println("Crawling: " + t);
				visitedLinks.add(url);
				
				return doc;
			}
			return null;
		}
		catch(IOException e) {
			return null;
		}
	}
	
	//Returning threads
	public Thread getThread() {
		return thread;
	}
}