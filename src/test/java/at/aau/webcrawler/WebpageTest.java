package at.aau.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebpageTest {
  private Webpage webpage;

  @BeforeEach
  public void setUp() {
    String html = "<html><head><title>Test Page</title></head><body>" +
        "<h1>Heading 1</h1><h2>Heading 2</h2>" +
        "<a href=\"http://example.com\">Link 1</a>" +
        "<a href=\"http://example.org\">Link 2</a>" +
        "</body></html>";
    Document document = Jsoup.parse(html, "", Parser.xmlParser());
    webpage = new Webpage(document);
  }

  @Test
  public void testGetHeadings() {
    Elements headings = webpage.getHeadings();
    assertEquals(2, headings.size());
    assertEquals("h1", headings.get(0).tagName());
    assertEquals("h2", headings.get(1).tagName());
  }

  @Test
  public void testGetLinks() {
    Set<String> links = webpage.getLinks();
    assertEquals(2, links.size());
    assertTrue(links.contains("http://example.com"));
    assertTrue(links.contains("http://example.org"));
  }
}
