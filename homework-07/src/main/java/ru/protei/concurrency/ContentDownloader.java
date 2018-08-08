package ru.protei.concurrency;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ContentDownloader {

    private final ExecutorService service;

    private static final class ContentFromUrlDownloader implements Callable<String> {
        private final String url;

        private ContentFromUrlDownloader(String url) {
            this.url = url;
        }

        public String call() {

            StringBuilder builder = new StringBuilder();

            try (Scanner scanner = new Scanner(new URL(url).openStream())) {
                while (scanner.hasNext()) {
                    builder.append(scanner.nextLine());
                }
            } catch (IOException e) {
                return null;
            }

            return builder.toString();
        }
    }

    public ContentDownloader(int poolSize) {
        service = Executors.newFixedThreadPool(poolSize);
    }

    public List<String> downloadContent(List<String> urls) throws ExecutionException, InterruptedException, TimeoutException {

        List<Future<String>> futures = new ArrayList<>();

        for (String url : urls) {
            futures.add(service.submit(new ContentFromUrlDownloader(url)));
        }
        List<String> result = new ArrayList<>();

        for (Future<String> future : futures) {
            result.add(future.get(60, TimeUnit.SECONDS));
        }
        return result;
    }
}
