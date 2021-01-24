package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Инкремент/Декремент - не являются атомарными операциями
 * Атомарная операция - операция, которая выполняется либо целиком,
 * либо не выполняется вообще
 * ++/-- - выполняются в 3 этапа: берем нач.значение, увеличиваем/уменьшаем
 * на 1 и кладем обратно.
 * ***************************************************************************
 * Синхронизировать можно 3 способами:
 * 1. Создать монитор: synchronized (monitor){ код }
 * 2. Создать синхронизированные методы по классу:
 *    private synchronized static void inc (); Вызов: inc()l;
 * 3. Создать метод синхронизированный по объекту в классе
 *    практически то же самое, что и 1 только тут вызываем у объекта:
 *    private synchronized void inc () Вызвать: obj.inc();
 */
public class ThreadSynchronized {

    /**
     * Создаем монитор, который может быть занят, только одним потоком
     * Монитор является точкой для синхронизации
     */
    private static final Object monitor = new Object();

    /**
     * общий ресурс
     */
    private static int count =0;

    public static void main(String[] args) throws InterruptedException {

        Thread t0 = new Thread(()->{
            for (int i = 0; i < 100000; i++) {
                //monitor lock
                synchronized (monitor) {
                    count++;
//                    inc();
                }//monitor unlock
            }
        });

        Thread t1 = new Thread(()->{
            for (int i = 0; i < 100000; i++) {
                //monitor lock
                synchronized (monitor) {
                    count--;
//                    dec();
                }//monitor unlock
            }
        });
        t0.start();
        t1.start();

        t0.join();
        t1.join();
        System.out.println("Count: " + count);
    }

    /**
     * Методы, которые синхронизируются на одном объекте КЛАССА!
     * Класс ThreadSynchronized является ОБЪЕКТОМ!!
     */
    private synchronized static void inc () {
        count++;
    }
    private synchronized static void dec () {
        count--;
    }
}
//Lecture 3.28.29