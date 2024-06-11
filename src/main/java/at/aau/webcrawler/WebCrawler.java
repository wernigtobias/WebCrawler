package at.aau.webcrawler;

import at.aau.app.App;
import at.aau.services.WebService;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;
import at.aau.webcrawler.dto.Webpage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class WebCrawler {

    private final Set<String> crawledLinks;

    public WebCrawler() {
        this.crawledLinks = new HashSet<>();
    }

    public void run(String[] urls, String[] domains) {
        WebCrawlerExecutor webCrawlerExecutor = new WebCrawlerExecutor();

        for (String url : urls) {
            WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(url, 0, url, domains);
            webCrawlerExecutor.submit(() -> crawl(webCrawlerConfig), url);
        }

        WebCrawlerOutputFileWriterImpl.writeToOutputFile(webCrawlerExecutor.getResult(), new File("output.md"));
        webCrawlerExecutor.shutdown();
    }

    public String crawl(WebCrawlerConfig configuration) {
        System.out.println("[Crawler-" + Thread.currentThread().getName() + "-] URL: " + configuration.getUrl() + " Depth: " + configuration.getDepth());

        Webpage webpage = loadWebpage(configuration.getUrl());
        if (webpage != null) {
            StringBuilder report = new StringBuilder();

            report.append(getResult(processWebpage(webpage, configuration), configuration));
            report.append(processLinks(webpage.getLinks(), configuration));

            return report.toString();
        } else {
            return WebCrawlerReportBuilder.getBrokenLinkReport(configuration);
        }
    }

    public Webpage loadWebpage(String url) {
        return WebService.loadWebpage(url);
    }

    public WebCrawlerPageResult processWebpage(Webpage webpage, WebCrawlerConfig configuration) {
        WebCrawlerPageResult result = new WebCrawlerPageResult(configuration);
        result.setHeadings(webpage.getHeadings());
        result.setLinks(webpage.getLinks());
        return result;
    }

    public String getResult(WebCrawlerPageResult result, WebCrawlerConfig configuration) {
        if (configuration.getDepth() == 0) {
            return WebCrawlerReportBuilder.getBaseReport(result);
        } else {
            return WebCrawlerReportBuilder.getNestedReport(result);
        }
    }

    public String processLinks(Set<String> links, WebCrawlerConfig configuration) {
        if (configuration.getDepth() >= App.maxDepth) {
            return "";
        }

        WebCrawlerExecutor webCrawlerExecutor = new WebCrawlerExecutor();

        for (String link : links) {
            webCrawlerExecutor.submit(() -> processLink(link, configuration), link);
        }

        StringBuilder report = new StringBuilder();
        String resultOfChildren = webCrawlerExecutor.getResult();
        report.append(resultOfChildren);

        return report.toString();
    }

    public String processLink(String link, WebCrawlerConfig configuration) {
        int newDepth = configuration.getDepth() + 1;

        WebCrawlerConfig nestedConfiguration = new WebCrawlerConfig(link, newDepth, configuration.getRootUrl(), configuration.getDomains());

        if (!checkIfLinkAlreadyCrawled(link) && nestedConfiguration.verifyConfig()) {
            return crawl(nestedConfiguration);
        }

        return "";
    }

    public synchronized boolean checkIfLinkAlreadyCrawled(String url){
        if(!crawledLinks.contains(url)) {
            crawledLinks.add(url);
            return false;
        }

        return true;
    }
}
