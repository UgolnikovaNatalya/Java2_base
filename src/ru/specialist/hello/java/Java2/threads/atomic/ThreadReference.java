package ru.specialist.hello.java.Java2.threads.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/**
 * Reference - позволяет нам обернуть любой объект в атомарный и
 * к нему применять определенные операции.
 * Поскольку нет AtomicDouble, то мы берем Reference и передаем ему
 * тип <Double> и работать дальше
 */

public class ThreadReference {

    private static final AtomicReference<Double> val = new AtomicReference<>(1d);

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(() -> calc(n -> n * 2));
        Thread t1 = new Thread(() -> calc(n -> n / 2));

        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println(val);
    }
    private static void calc (UnaryOperator<Double> operator){
        for (int i = 0; i < 1000; i++) {
            val.updateAndGet(operator);
        }
    }
}
