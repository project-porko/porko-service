package io.porko.config.utils;

import java.util.stream.IntStream;

public class TestUtils {
    public static void repeat(int count, Runnable runnable) {
        IntStream.range(0, count).forEach((i) -> {
            runnable.run();
        });
    }
}
