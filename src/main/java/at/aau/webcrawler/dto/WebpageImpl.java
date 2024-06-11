package at.aau.webcrawler.dto;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WebpageImpl implements Webpage {
  private final Document document;

  public WebpageImpl(Document document) {
    this.document = document;
  }

  public List<Heading> getHeadings() {
    return document.select("h1,h2,h3,h4,h5,h6").stream()
            .map(element -> new Heading(element.ownText(), Integer.parseInt(element.tagName().substring(1))))
            .collect(Collectors.toList());
  }

  public Set<String> getLinks() {
    Elements linkElements = document.select("a");
    return linkElements.stream().map(element -> element.attr("href")).collect(Collectors.toSet());
  }
}
