package at.aau.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.stream.Collectors;

public class Webpage {
  private final Document document;

  public Webpage(Document document) {
    this.document = document;
  }

  public Elements getHeadings() {
    return document.select("h1,h2,h3,h4,h5,h6");
  }

  public Set<String> getLinks() {
    Elements linkElements = document.select("a");
    Set<String> links = linkElements.stream().map(element -> element.attr("href")).collect(Collectors.toSet());
    return links;
  }
}
