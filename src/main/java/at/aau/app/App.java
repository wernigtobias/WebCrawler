package at.aau.app;

import at.aau.webcrawler.WebCrawlerExecutor;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.WebCrawlerImpl;

import java.util.Scanner;
public class App {

    static Scanner scanner;
    public static int maxDepth;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        //String[] urls = readURLs();
        String[] urls = new String[]{"https://ecockpit.at", "https://softwaregutachten.at/"};
        //maxDepth = readDepth();
        maxDepth = 3;
        //String[] domains = readDomains();
        String[] domains = new String[]{"ecockpit.at", "softwaregutachten.at", "de.wikipedia.org"};

        scanner.close();

        initializeCrawlerAndRun(urls, domains);
    }

    private static void initializeCrawlerAndRun(String[] urls, String[] domains) {
        WebCrawlerExecutor.initializeThreadPoolWithThreadCount(300);
        WebCrawlerImpl crawler = new WebCrawlerImpl();
        crawler.run(urls, domains);
    }

    private static String[] readURLs() {
        System.out.println("Enter URL(s) (;-separated if multiple):");
        return scanner.nextLine().replace(" ", "").split(";");
    }

    private static int readDepth() {
        System.out.println("Enter depth:");
        return scanner.nextInt();
    }

    private static String[] readDomains() {
        System.out.println("Enter domain(s) (;-separated if multiple):");
        scanner.nextLine();
        return scanner.nextLine().replace(" ", "").split(";");
    }

}
