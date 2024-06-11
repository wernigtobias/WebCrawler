package at.aau.webcrawler;

import at.aau.webcrawler.dto.Heading;
import at.aau.webcrawler.dto.WebpageImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebpageTest {
  private WebpageImpl webpage;

  @BeforeEach
  public void setUp() {
    String html = "<html><head><title>Test Page</title></head><body>" +
        "<h1>Heading 1</h1><h2>Heading 2</h2>" +
        "<a href=\"http://example.com\">Link 1</a>" +
        "<a href=\"http://example.org\">Link 2</a>" +
        "</body></html>";

    Document document = Jsoup.parse(html, "", Parser.xmlParser());

    webpage = new WebpageImpl(document);
  }

  @Test
  public void testGetHeadings() {
    List<Heading> headings = webpage.getHeadings();
    assertEquals(2, headings.size());

    assertEquals(1, headings.get(0).getOrder());
    assertEquals("Heading 1", headings.get(0).getText());

    assertEquals(2, headings.get(1).getOrder());
    assertEquals("Heading 2", headings.get(1).getText());
  }

  @Test
  public void testGetLinks() {
    Set<String> links = webpage.getLinks();
    assertEquals(2, links.size());
    assertTrue(links.contains("http://example.com"));
    assertTrue(links.contains("http://example.org"));
  }
}
