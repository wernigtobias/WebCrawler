package at.aau.webcrawler;

import at.aau.app.App;
import at.aau.webcrawler.dto.Heading;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class WebCrawlerReportBuilder {

    private static void appendLine(StringBuilder report, String... parts) {
        for (String part : parts) {
            report.append(part);
        }
        report.append("\n");
    }

    private static void appendArrayItems(StringBuilder report, String[] items) {
        for (String item : items) {
            appendLine(report, "<br>" + item);
        }
    }

    private static void appendHeadings(StringBuilder report, WebCrawlerPageResult webCrawlerResult) {
        List<Heading> headings = webCrawlerResult.getHeadings();
        for (Heading heading : headings) {
            report.append(formatHeading(heading, webCrawlerResult.getWebCrawlerConfiguration().getDepth()));
        }
        report.append("\n");
    }

    public static String getBaseReport(WebCrawlerPageResult webCrawlerResult) {
        WebCrawlerConfig webCrawlerConfiguration = webCrawlerResult.getWebCrawlerConfiguration();

        StringBuilder report = new StringBuilder();

        appendLine(report, "input: <a>" + webCrawlerConfiguration.getUrl() + " </a>\n");
        appendLine(report,"depth:", String.valueOf(App.getMaxDepth()));
        appendLine(report,"crawled domains:");
        appendArrayItems(report, webCrawlerConfiguration.getDomains());
        appendLine(report, "summary:");
        appendHeadings(report, webCrawlerResult);

        return report.toString();
    }

    public static String getNestedReport(WebCrawlerPageResult nestedWebCrawlerResult) {
        WebCrawlerConfig webCrawlerConfiguration = nestedWebCrawlerResult.getWebCrawlerConfiguration();

        StringBuilder report = new StringBuilder();

        String crawledUrl = webCrawlerConfiguration.getUrl();
        appendLine(report, createDepthIndent(webCrawlerConfiguration.getDepth()) + " link to <a>" + crawledUrl + " </a>");
        appendHeadings(report, nestedWebCrawlerResult);

        return report.toString();
    }

    public static String getBrokenLinkReport(WebCrawlerConfig configuration, String errorMessage) {
        String brokenLink = createDepthIndent(configuration.getDepth()) + " broken link<a>" + configuration.getUrl() + " </a> ("+ errorMessage + ")\n";
        return brokenLink;
    }

    private static String createDepthIndent(int depth) {
        StringBuilder indent = new StringBuilder();
        indent.append("-".repeat(Math.max(0, 2 * depth)));
        if (depth > 0) {
            indent.append(">");
        }
        return indent.toString();
    }

    private static String formatHeading(Heading heading, int depth) {
        StringBuilder markdownHeading = new StringBuilder();
        int headingLevel = heading.getOrder();
        markdownHeading.append("\n");
        markdownHeading.append("#".repeat(Math.max(0, headingLevel)));
        markdownHeading.append(" ");
        markdownHeading.append(createDepthIndent(depth));
        markdownHeading.append(" ");
        markdownHeading.append(heading.getText());
        markdownHeading.append("\n");
        return markdownHeading.toString();
    }

}
