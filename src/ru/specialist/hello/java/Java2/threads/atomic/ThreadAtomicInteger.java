package ru.specialist.hello.java.Java2.threads.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Atomic - это классы, которые предоставляют атомарные операции для
 * числовых значений, ссылок и тп. ОТлично подходит для подсчета циклов
 */

public class ThreadAtomicInteger {
    private static final AtomicInteger val = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        Thread t0 = new Thread(()->{
//            for (int i = 0; i < 100000; i++) {
//                val.incrementAndGet();
//            }
//        });

//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 100000; i++) {
//                val.decrementAndGet();
//            }
//        });

        Thread t0 = new Thread(() -> calc(n -> n++));
        Thread t1 = new Thread(() -> calc(n -> n--));


        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println(val);
    }
    private static void calc (IntUnaryOperator operator){
        for (int i = 0; i < 100000; i++) {
            val.updateAndGet(operator);
        }
    }
}
