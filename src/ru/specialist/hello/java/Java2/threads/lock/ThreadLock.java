package ru.specialist.hello.java.Java2.threads.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Более высокоуровневый способ блокировки, чем synchronized
 * В ситуации с sync, если мы не можем передать монитор треду, то он будет
 * простаивать и ничего не делать. Получается доп. расход, а это нехорошо.
 * В случае с Lock мы можем назначить треду выполнение какой-либо задачи, если
 * он не может захватить монитор
 */
public class ThreadLock {
    private static int count;
    /**
     * ReentrantLock - простейший из замков, который выступает в роли монитора
     * но с доп. условиями
     * Один из них try.lock, если свободный тред(который в ожидании)
     * захватывает его, то начинает выполнять условия. Чтобы не простаивать просто так
     */
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(()->{
            for (int i = 1; i < 100000000; i++) {
                while (!lock.tryLock()){
                    try {
                        System.out.printf("%s: Waiting for lock...\n", Thread.currentThread().getName());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count++;

                lock.unlock();
            }
        });

        Thread t1 = new Thread(()-> {
            for (int i = 1; i <100000000 ; i++) {
                while (!lock.tryLock()){
                    try {
                        //тут мы пишем то, что поток должен выполнить
                        //пока не может захватить лок
                        System.out.printf("%s: Waiting for lock...\n", Thread.currentThread().getName());
                        // Это время для захвата лока, если не получается,
                        //тред продолжает выполнения try
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //когда тред захватил лок, то выполняет:
                count --;

                lock.unlock();
            }
        });

        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println(count);
    }

    /*
    * Вопрос1: Данный лок можно было бы использовать в нашем случае, когда решали
    * задачу wait/notify пока один поток возводит в степень, другой считает,
    * сколько раз захватил лок и наоборот
    *
    * */
}
