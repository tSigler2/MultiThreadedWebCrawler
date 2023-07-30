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

output.txt: Also found under the main folder in the resources folder. This is the output of all pages crawled during a web crawl and can contain the data of multiple crawls.
