package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		//Getting all needed information from user
		Scanner console = new Scanner(System.in);
		System.out.print("Number of threads you'd like to use: ");
		ArrayList<WebCrawler> bots = new ArrayList<>();
		int k = 0, n = console.nextInt();
		System.out.print("Depth for each thread to crawl: ");
		int d = console.nextInt();
		
		//Create WebCrawler bots from list of urls
		try {
			BufferedReader r = new BufferedReader(new FileReader("txtFiles/input.txt"));
			String line;
			while(k < n && (line = r.readLine()) != null) {
				bots.add(new WebCrawler(line, k+1, d));
				k++;
			}
			if(k < n) {
				System.out.println("Not enough Input URLs to create all instances of Web Crawler");
				System.out.println("Only created " + k + " instances of Web Crawler.");
			}
			r.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//Start Crawling Threads
		for(WebCrawler w : bots) {
			try {
				w.getThread().join();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
