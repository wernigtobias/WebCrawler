package at.aau.app;

import at.aau.translator.TranslatorService;
import at.aau.translator.TranslatorServiceImpl;
import at.aau.webcrawler.WebCrawlerExecutor;
import at.aau.webcrawler.WebCrawler;

import java.util.Scanner;
public class App {

    static Scanner scanner;
    private static int maxDepth;
    private static String[] urls;
    private static String[] domains;
    private static String targetLanguage;

    public static void main(String[] args) {
        TranslatorService translatorService = new TranslatorServiceImpl();

        readUserInput(translatorService);

        WebCrawlerExecutor.initializeThreadPoolWithThreadCount(1000);
        WebCrawler crawler = new WebCrawler(translatorService);
        crawler.run(urls, domains);
    }

    private static void readUserInput(TranslatorService translatorService){
        scanner = new Scanner(System.in);

        urls = readURLs();
        maxDepth = readDepth();
        domains = readDomains();
        targetLanguage = readLanguage(translatorService);

        scanner.close();
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

    private static String readLanguage(TranslatorService translatorService) {
        System.out.println("Enter language:");
        String language = scanner.nextLine();

        if(translatorService.validateLanguage(language)){
            return language;
        }

        System.out.println("Wrong input. Please enter the wanted language in english.");
        return readLanguage(translatorService);
    }

    public static int getMaxDepth() {
        return maxDepth;
    }

    public static void setMaxDepth(int maxDepth) {
        App.maxDepth = maxDepth;
    }

    public static String getTargetLanguage() {
        return targetLanguage;
    }
}
