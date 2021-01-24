package ru.specialist.hello.java.Java2.threads.atomic;

import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * Накопительны класс.
 * mult - то что накапливает
 * n -  значение которое добавлятеся к mult
 * 1 - то, с чего начиается отсчет
 */

public class ThreadDoubleAccumulator {
    private static final DoubleAccumulator val = new DoubleAccumulator((mult, n) -> mult = mult*n, 1);

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 1; i < 5; i++) {
                val.accumulate(i);
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
