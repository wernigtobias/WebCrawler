package at.aau.app;

import at.aau.webcrawler.WebCrawlerConfig;
import at.aau.webcrawler.WebCrawler;

import java.util.Scanner;

/**
 * WebCrawler
 *
 */
public class App {

    public static void main(String[] args) {
        //Read input values for WebCrawler-Config
        Scanner scanner = new Scanner(System.in);
        String url = readURL(scanner);
        int depth = readDepth(scanner);
        String[] domains = readDomains(scanner);
        scanner.close();

        initializeCrawlerAndRun(url, depth, domains);
    }

    private static void initializeCrawlerAndRun(String url, int depth, String[] domains) {
        WebCrawlerConfig crawlerConfig = new WebCrawlerConfig(url, depth, domains);
        if(crawlerConfig.verifyConfig()){
            WebCrawler crawler = new WebCrawler(crawlerConfig);
            crawler.run();
        }
    }

    private static String readURL(Scanner scanner) {
        System.out.println("Enter URL:");
        return scanner.nextLine();
    }

    private static int readDepth(Scanner scanner) {
        System.out.println("Enter depth:");
        return scanner.nextInt();
    }

    private static String[] readDomains(Scanner scanner) {
        System.out.println("Enter domain(s) (comma-separated if multiple):");
        scanner.nextLine();
        return scanner.nextLine().split(",");
    }

}
