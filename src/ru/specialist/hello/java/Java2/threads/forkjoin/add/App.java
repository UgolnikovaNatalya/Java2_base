package ru.specialist.hello.java.Java2.threads.forkjoin.add;

import ru.specialist.hello.java.Java2.threads.forkjoin.add.Adder;

import java.util.concurrent.ForkJoinPool;

/**
 * ForkJoinPool - набор классов, которые позволяют решать задачу рекурсивно
 * при помощи многопоточности
 * Класс позволяющий решать задачи рекурсивно используя пул потоков
 * new Adder - одинэкземпляр, который потом внутри себя создает рекурсивные подзадачи
 * commonPool - автоматически создает потоки по необходимости
 */
public class App {
    public static final long STEP = 200000000;
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        long sum = ForkJoinPool.commonPool().invoke(new Adder(1, 1000000000L));
        //то же самое (просто явный вызов)
//        long sum = new ForkJoinPool().invoke(new Adder (1, 1000000000L));

        long t2 = System.currentTimeMillis();
        System.out.println(sum);
        System.out.printf("Completed in %d ms", t2 -t1);
    }
}
