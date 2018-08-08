package ru.protei.concurrency;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileCheckerTest {

    @Test
    public void run() {
        FileChecker fileChecker = new FileChecker();
        fileChecker.run();
    }
}