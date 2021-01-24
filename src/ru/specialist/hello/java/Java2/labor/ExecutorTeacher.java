package ru.specialist.hello.java.Java2.labor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Решение лабораторно преподавателем
 */
public class ExecutorTeacher {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long t1 = System.currentTimeMillis();
        long sum1 = sum (1, 100);
        long t2 = System.currentTimeMillis();
        System.out.println(sum1);
        System.out.printf("Completed one thread in %d ms\n", t2- t1);

        t1 = System.currentTimeMillis();
        long sum2 = sumMultiThread (100, 6);
        t2 = System.currentTimeMillis();
        System.out.println(sum2);
        System.out.printf("Completed multi in %d ms\n", t2- t1);

//        System.out.println(sumMultiThread(10,2));
    }

    /**
     * Метод многопоточного суммирования
     * @param from от какого числа суммировать
     * @param to до какого суммировать
     * @return сумма чисел
     */
    private static long sum (long from, long to){
        long sum = 0;
        for (long i = from; i <= to ; i++) {
            sum +=i ;
        } return sum;
    }

    /**
     * Метод описывающий мультитредное суммирование
     * С автоматическим выделением потоков смотри ExecutorLabVar2
     * @param to диапазон чисел
     * @param nTasks количество выделенных тредов на подсчет
     * @return сумму тредов
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static long sumMultiThread (long to, int nTasks) throws ExecutionException, InterruptedException {
        //Выделем количество потоков
        ExecutorService pool = Executors.newFixedThreadPool(nTasks);

        //Создаем массив для выделенных потоков, чтобы потом сложить их суммы
        List <Future<Long>> tasks = new ArrayList<>();
        //делим число на количество задач(тредов)
        //диапазон чисел в пределах которого будем считать
        long stepSize = to/nTasks;
        /**
         * чтобы обработать нечетное деление числа на треды (с остатком)
         * Обрабатываем следующим образом:
         * получаем остаток от деления (диапазона на треды)
         */
        long r = to % nTasks;

        /**
         * в цикле решаем остаток от деления
         * обрабатывается (i== nTasks -1 ? r : 0)
         */
        for (int i = 0; i < nTasks; i++) {
            long a = i * stepSize + 1;
            long b = i * stepSize + stepSize + (i== nTasks - 1 ? r : 0);
            //отправляем в пул сумму границ
            tasks.add(pool.submit(()-> sum(a, b)));
        }
        pool.shutdown();
        long sum = 0;
        for (Future<Long> task : tasks) {
            System.out.println (sum += task.get());
        } return sum;
    }
}
