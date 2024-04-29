package at.aau.webcrawler;

import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerResults;
import at.aau.webcrawler.dto.Webpage;

import java.util.Set;

/**
 * Interface representing a web crawler.
 */
public interface WebCrawler {

  /**
   * Starts the web crawler.
   */
  void run();

  /**
   * Crawls the web with the given configuration.
   *
   * @param configuration The configuration for the web crawling.
   */
  void crawl(WebCrawlerConfig configuration);

  /**
   * Processes a single link found during web crawling.
   *
   * @param link          The URL of the link to be processed.
   * @param configuration The configuration for the web crawling.
   */
  void processLink(String link, WebCrawlerConfig configuration);

  /**
   * Loads a webpage from the given URL.
   *
   * @param url The URL of the webpage to load.
   * @return The loaded webpage.
   */
  Webpage loadWebpage(String url);

  /**
   * Processes a webpage and generates results based on the configuration.
   *
   * @param webpage       The webpage to process.
   * @param configuration The configuration for processing the webpage.
   * @return The results generated from processing the webpage.
   */
  WebCrawlerResults processWebpage(Webpage webpage, WebCrawlerConfig configuration);

  /**
   * Saves the results of processing a webpage.
   *
   * @param result        The results to save.
   * @param configuration The configuration for the web crawling.
   */
  void saveResult(WebCrawlerResults result, WebCrawlerConfig configuration);

  /**
   * Processes all links found on a webpage.
   *
   * @param links         The set of links to process.
   * @param configuration The configuration for the web crawling.
   */
  void processLinks(Set<String> links, WebCrawlerConfig configuration);
}
