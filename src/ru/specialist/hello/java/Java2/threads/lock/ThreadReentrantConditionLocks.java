package ru.specialist.hello.java.Java2.threads.lock;
/**
 * Условие блокировки представляет собой объект интерфейска Condition
 * интерфейска
 * signal == notify
 * await == wait
 */

import java.io.PrintWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class ThreadReentrantConditionLocks {

    private static double n = 9;

    private static final Lock lock = new ReentrantLock();

    private static final Condition cond = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        Thread t0 = new Thread(() -> calc (Math::sqrt));
        Thread t1 = new Thread(() -> calc (x -> x * x));

        t0.start();
        Thread.sleep(100);
        t1.start();

    }

    private static void calc (Function <Double, Double> f) {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            n = f.apply(n);
            System.out.println(n);
            cond.signal ();

            try {
                cond.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cond.signalAll();
            lock.unlock();
        }
    }
}
//
