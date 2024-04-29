# WebCrawler

This Java project is a web crawler designed to crawl through web pages up to a certain depth and extract information from specified domains.

## Installation

1. Clone this repository to your local machine:

    ```bash
    git clone https://github.com/wernigtobias/WebCrawler
    ```

2. Navigate to the project directory:

    ```bash
    cd WebCrawler
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

## Usage

To use the web crawler, follow these steps:

1. Start the crawler by running the main method in the `App` class.
2. When prompted, provide the following inputs:
    - URL: The starting URL from which the crawler will begin crawling.
    - Depth: The depth to which the crawler will explore links recursively.
    - To-crawl-domains: Comma-separated list of domains that the crawler should crawl.
3. Have a look at the results in the generated `output.md` file.
