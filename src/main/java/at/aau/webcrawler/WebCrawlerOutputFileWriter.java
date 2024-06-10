package at.aau.webcrawler;

import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;

/**
 * Interface for writing web crawler output to a file.
 */
public interface WebCrawlerOutputFileWriter {

  /**
   * Sets the base report for the web crawler results.
   *
   * @param webCrawlerResult The web crawler results to set as the base report.
   */
  void setBaseReport(WebCrawlerPageResult webCrawlerResult);

  /**
   * Adds a nested report for the web crawler results at a specified depth.
   *
   * @param nestedWebCrawlerResult The nested web crawler results to add.
   */
  void addNestedReport(WebCrawlerPageResult nestedWebCrawlerResult);

  /**
   * Adds a report for a broken link encountered during crawling at a specified depth.
   *
   * @param configuration The configuration associated with the broken link.
   * @param depth         The depth at which the broken link was encountered.
   */
  void addBrokenLinkReport(WebCrawlerConfig configuration, int depth);

  /**
   * Writes the output content to the output file.
   */
  void writeToOutputFile();

  /**
   * Retrieves the content that is to be written to the output file.
   *
   * @return The content to be written to the output file.
   */
  StringBuilder getOutputFileContent();

}
