package ru.specialist.hello.java.Java2.threads.executor;

import java.util.concurrent.*;

/**
 * Executors -  это объекты, которые позволяют выполнять одну задачу в несколько
 * потоков. При этом мы не создаем явные потоки вручную, они создаются JVM в
 * зависимости от того, какой Executor мы выберем
 */
public class Executor {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //узнаем количество ядер, чтобы скорректировать количество потоков
        //иногда помогает соптимизировать
        int n = Runtime.getRuntime().availableProcessors();
        System.out.println(n);

        //пул из одного потока
        ExecutorService pool =
//                медленный
//                Executors.newSingleThreadExecutor();
                /* После того, как узнали сколько ядер оптимизируем
                * все с количеством потоков (автоматически)*/
                Executors.newFixedThreadPool(n);
                /*Отличается тем, что он создает нужно количество потоков
                * и в случае, если они не используются, убирает их
                * Самый разумный вариант*/
//                Executors.newCachedThreadPool();

        Future<Integer> result1 = pool.submit(new Sum());
        Future<Integer> result2 = pool.submit(() -> {
            System.out.printf("%s: Calculating (5+5)\n", Thread.currentThread().getName());
            Thread.sleep(1000);
            return 5 + 5;
        });

        Future<Integer> result3 = pool.submit(() -> {
            System.out.printf("%s: Calculating (10+10)\n", Thread.currentThread().getName());
            Thread.sleep(1000);
            return 10 + 10;
        });
        //закрытие потока. Закроется, только после выполнения задачи
        pool.shutdown();

        //отмена задачи
//        result1.cancel(true);

        /*Можно использовать для того, чтобы главный тред просто
        * так не спал, занять его чем-нибудь, для этого пишем это условие*/
        while (!result1.isDone() || !result2.isDone()|| !result3.isDone()){
            System.out.println("Waiting for result...");
            Thread.sleep(100);
        }
        //Тут выпадет ошибка, когда захотим получить результат
        //поэтому ставим флаг на проверку отмены
        if (!result1.isCancelled())
        System.out.println("Result: " + result2.get());
        if (!result2.isCancelled())
        System.out.println("Result: " + result3.get());
        if (!result3.isCancelled())
            System.out.println("Result: " + result1.get());

        System.out.println("End");

    }
}
//1.59.46
