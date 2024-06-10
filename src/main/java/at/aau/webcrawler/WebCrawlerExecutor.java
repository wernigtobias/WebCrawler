package at.aau.webcrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class WebCrawlerExecutor {
    public static int timeoutForChildThreadsInSeconds = 60;
    public static int timeoutForShutdownInSeconds = 60;

    private static ExecutorService executorService;

    private final List<Future<String>> futures = new ArrayList<>();
    private final Map<Future<String>, String> futuresWithUrls = new HashMap<>();
    private final StringBuilder result = new StringBuilder();

    public static void initializeThreadPoolWithThreadCount(int threadCount) {
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public void submit(Callable<String> task, String url) {
        Future<String> future = executorService.submit(task);
        futures.add(future);
        futuresWithUrls.put(future, url);
    }

    public String getResult() {
        for (Future<String> future : futures) {
            try {
                result.append(future.get(timeoutForChildThreadsInSeconds, TimeUnit.MINUTES));
            } catch (Exception e) {
                String url = futuresWithUrls.get(future);
                result.append(url).append("\n").append(getErrorAsReportMessage(e));
            }
        }
        return result.toString();
    }

    private String getErrorAsReportMessage(Exception e) {
        if (e instanceof TimeoutException) {
            return "TimeoutException when waiting on child crawling url";
        } else if (e instanceof ExecutionException) {
            return "ExecutionException when waiting on child crawling url";
        } else if (e instanceof InterruptedException) {
            return "InterruptedException when waiting on child crawling url";
        }
        return "Exception when waiting on child crawling url";
    }

    public boolean shutdown() {
        executorService.shutdown();
        try {
            return executorService.awaitTermination(timeoutForShutdownInSeconds, TimeUnit.MINUTES);
        } catch (InterruptedException interruptedException) {
            System.out.println("InterruptedException when waiting for thread pool termination");
            interruptedException.printStackTrace();
        }
        return false;
    }
}
