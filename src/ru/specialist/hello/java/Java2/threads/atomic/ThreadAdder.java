package ru.specialist.hello.java.Java2.threads.atomic;

import java.util.concurrent.atomic.DoubleAdder;

/**
 * Последовательное сложение чисел
 */

public class ThreadAdder {
    private static final DoubleAdder val = new DoubleAdder();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                val.add(0.5);
            }
        };

        Thread t0 = new Thread(task);
        Thread t1 = new Thread(task);
        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println(val);
    }
}
