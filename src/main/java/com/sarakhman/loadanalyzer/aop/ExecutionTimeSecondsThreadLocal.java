package com.sarakhman.loadanalyzer.aop;

public class ExecutionTimeSecondsThreadLocal {
    private static final ThreadLocal<Long> EXECUTION_TIME_SECONDS = new ThreadLocal<>();

    public static void set(Long seconds) {
        EXECUTION_TIME_SECONDS.set(seconds);
    }

    public static Long get() {
        return EXECUTION_TIME_SECONDS.get();
    }
}
