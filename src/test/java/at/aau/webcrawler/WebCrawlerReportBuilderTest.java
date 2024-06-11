package at.aau.webcrawler;

import at.aau.app.App;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebCrawlerReportBuilderTest {

    @BeforeEach
    void init() {
        App.setMaxDepth(2);
    }

    @Test
    public void testSetBaseReport() {
        WebCrawlerPageResult mockedWebCrawlerPageResult = Mockito.mock(WebCrawlerPageResult.class);
        WebCrawlerConfig mockedWebCrawlerConfig = Mockito.mock(WebCrawlerConfig.class);

        Mockito.when(mockedWebCrawlerConfig.getUrl()).thenReturn("http://example.com");
        Mockito.when(mockedWebCrawlerConfig.getDomains()).thenReturn(new String[]{"example.com"});

        Mockito.when(mockedWebCrawlerPageResult.getWebCrawlerConfiguration()).thenReturn(mockedWebCrawlerConfig);
        Mockito.when(mockedWebCrawlerPageResult.getHeadings()).thenReturn(new ArrayList<>());

        String baseReport = WebCrawlerReportBuilder.getBaseReport(mockedWebCrawlerPageResult);
        assertEquals("input: <a>http://example.com </a>\n\ndepth:2\ncrawled domains:\n<br>example.com\nsummary:\n\n", baseReport);
    }

    @Test
    public void testAddNestedReport() {
        WebCrawlerConfig mockedWebCrawlerConfig = Mockito.mock(WebCrawlerConfig.class);
        Mockito.when(mockedWebCrawlerConfig.getUrl()).thenReturn("http://example.com/nested");
        Mockito.when(mockedWebCrawlerConfig.getDepth()).thenReturn(1);

        WebCrawlerPageResult mockedWebCrawlerPageResult = Mockito.mock(WebCrawlerPageResult.class);
        Mockito.when(mockedWebCrawlerPageResult.getWebCrawlerConfiguration()).thenReturn(mockedWebCrawlerConfig);

        String nestedReport = WebCrawlerReportBuilder.getNestedReport(mockedWebCrawlerPageResult);
        assertEquals("--> link to <a>http://example.com/nested </a>\n\n", nestedReport);
    }

    @Test
    public void testAddBrokenLinkReport() {
        WebCrawlerConfig mockedWebCrawlerConfig = Mockito.mock(WebCrawlerConfig.class);
        Mockito.when(mockedWebCrawlerConfig.getDepth()).thenReturn(2);
        Mockito.when(mockedWebCrawlerConfig.getUrl()).thenReturn("http://brokenlink.com");

        String brokenLink = WebCrawlerReportBuilder.getBrokenLinkReport(mockedWebCrawlerConfig, "ErrorMessage");
        assertEquals("----> broken link<a>http://brokenlink.com </a> (ErrorMessage)\n", brokenLink);
    }
}
