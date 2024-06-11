package at.aau.webcrawler;

import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebCrawlerOutputFileWriterTest {

  private WebCrawlerOutputFileWriterImpl writer;
  @BeforeEach
  void init() {
    writer = new WebCrawlerOutputFileWriterImpl(new File("test.txt"));
  }

  @Test
  public void testSetBaseReport() {
    //WebCrawlerConfig config = new WebCrawlerConfig("http://example.com", 2, new String[]{"example.com"});
    //WebCrawlerPageResult results = new WebCrawlerPageResult(config);
    //results.setHeadings(new Elements('1'));
    //results.setLinks(new HashSet<>());
    //writer.getBaseReport(results);
    //assertEquals("input: <a>http://example.com</a>\ndepth:2\ncrawled domains:\n<br>example.com\nsummary:\n\n", writer.getOutputFileContent().toString());
  }

  @Test
  public void testAddNestedReport() {
    /*WebCrawlerConfig config = new WebCrawlerConfig("http://example.com/nested", 3, new String[]{"example.com"});
    WebCrawlerPageResult results = new WebCrawlerPageResult(config);
    results.setHeadings(new Elements('1'));
    results.setLinks(new HashSet<>());
    writer.addNestedReport(results);
    assertEquals("--> link to <a>http://example.com/nested</a>\n\n", writer.getOutputFileContent().toString());
  */
  }


  @Test
  public void testAddBrokenLinkReport() {
    /*WebCrawlerConfig config = new WebCrawlerConfig("http://brokenlink.com", 1, new String[]{"brokenlink.com"});
    writer.addBrokenLinkReport(config, 2);
    assertEquals("----> broken link<a>http://brokenlink.com</a>\n", writer.getOutputFileContent().toString());

     */
  }
}
