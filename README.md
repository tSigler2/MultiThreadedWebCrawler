# MultiThreadedWebCrawler

## Multithreaded Web Crawler made in Java
## Uses JSoup for page requests
JSoup Can be Found at: https://jsoup.org/download

### Managed with Maven
Maven can be found at: https://maven.apache.org

### How to:
    - clean up:
        $mvn clean
    - compile:
        $mvn compile
    - run executable:
        $mvn exec:java -Dexec.mainClass="classes.Main"

input.txt: Found under the main folder in the resources folder. These are the seed links that will be checked at the beginning of the web crawl. This file will remember the last link crawled and so it can be used on successive crawls.

output.txt: This file will be found in your downloads folder after running the program. This is the output of all pages crawled during a web crawl and can contain the data of multiple crawls.


---About---

This project is intended to crawl multiple different webpages concurrently using multithreading. This is accomplished by recursive crawls that will crawl the web until a certain depth and using Jsoup to request pages. These files are then parsed for http and https links which are then subsequently crawled if the maximum depth has not been reached. The program allows the user to given any amount of seed pages for the webcrawler and will give a text document output for the user to use the indexing data without hassle.
