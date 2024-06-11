package at.aau.webcrawler.dto;

import org.jsoup.select.Elements;

import java.util.List;
import java.util.Set;

public interface Webpage {
    List<Heading> getHeadings();
    Set<String> getLinks();
}
