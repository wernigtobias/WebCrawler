package at.aau.app;

import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.WebCrawlerImpl;

import java.util.Scanner;
public class App {

    static Scanner scanner;
    public static int maxDepth;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        String url = readURL();
        maxDepth = readDepth();
        String[] domains = readDomains();
        scanner.close();
        initializeCrawlerAndRun(url, domains);
    }

    private static void initializeCrawlerAndRun(String url, String[] domains) {
        WebCrawlerConfig crawlerConfig = new WebCrawlerConfig(url, 0, domains);
        if(crawlerConfig.verifyConfig()){
            WebCrawlerImpl crawler = new WebCrawlerImpl(crawlerConfig);
            crawler.run();
        }
    }

    private static String readURL() {
        System.out.println("Enter URL:");
        return scanner.nextLine();
    }

    private static int readDepth() {
        System.out.println("Enter depth:");
        return scanner.nextInt();
    }

    private static String[] readDomains() {
        System.out.println("Enter domain(s) (comma-separated if multiple):");
        scanner.nextLine();
        return scanner.nextLine().replace(" ", "").split(",");
    }

}
