package ru.protei.concurrency;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class FileChecker {
    private static final Logger log = Logger.getLogger(FileChecker.class);

    private static final int QUEUE_SIZE = 5;

    private static final String DEFAULT_FILE_0 = "input.txt";
    private static final String DEFAULT_FILE_1 = "output.txt";
    private static final int DEFAULT_CHECK_TREAD_COUNT = 4;
    private static final String DEFAULT_REG_EXP = ".*";

    private final BlockingQueue<String> checkQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private final BlockingQueue<String> writeQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

    private final String file0;
    private final String file1;
    private final int checkTreadCount;
    private final String regExp;

    private ExecutorService service;

    public FileChecker() {
        this(DEFAULT_FILE_0,
                DEFAULT_FILE_1,
                DEFAULT_CHECK_TREAD_COUNT,
                DEFAULT_REG_EXP);
    }

    public FileChecker(String file0, String file1, int checkTreadCount, String regExp) {
        this.file0 = file0;
        this.file1 = file1;
        this.checkTreadCount = checkTreadCount;
        this.regExp = regExp;
    }

    public void run() {
        BasicConfigurator.configure();

        service = Executors.newFixedThreadPool(checkTreadCount + 2);
        service.submit(new Reader(file0));
        service.submit(new Writer(file1));
        for (int i = 0; i < checkTreadCount; i++) {
            service.submit(new Checker(regExp));
        }
        service.shutdown();
        try {
            service.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
    }

    private class Checker implements Runnable {

        private final Pattern pattern;

        Checker(String str) {
            pattern = Pattern.compile(str);
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = checkQueue.poll(10, TimeUnit.SECONDS)) != null) {
                    if (pattern.matcher(line).matches()) {
                        writeQueue.add(line);
                    }
                }
            } catch (InterruptedException e) {
                log.fatal("Checker cannot check!", e);
            }
        }
    }

    private class Reader implements Runnable {

        private final String inputFile;

        Reader(String inputFile) {
            this.inputFile = inputFile;
        }

        @Override
        public void run() {
            try (Scanner scanner = new Scanner(Paths.get(inputFile))) {

                String line;
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    checkQueue.add(line);
                }
            } catch (IOException e) {
                log.fatal("Reader cannot read!", e);
            }
        }
    }

    private class Writer implements Runnable {

        private final String outputFile;

        Writer(String outputFile) {
            this.outputFile = outputFile;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(outputFile)) {
                String line;
                while ((line = writeQueue.poll(10, TimeUnit.SECONDS)) != null) {
                    out.println(line);
                }
            } catch (Exception e) {
                log.fatal("Writer cannot write!", e);
            }
        }
    }
}
