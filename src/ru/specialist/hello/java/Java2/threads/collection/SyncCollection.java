package ru.specialist.hello.java.Java2.threads.collection;

import ru.specialist.hello.java.Java2.threads.lock.ThreadReadWriteList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SyncCollection {
    /**
     * Коллекция - некая обертка над массивом, где можем хранить несколько
     * значений Integer в расширяемой коллекции
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
//        Тут всегда будет выводиться 2000
//        List <Integer> list = Collections.synchronizedList(new ArrayList<>());
        //а тут всегда разные числа
//        List <Integer> list = new ArrayList<>();
        //Java 7-8++ new class
        /**
         * Если мы хотим добавить новый элемент, соз копия списка,
         * вставляется новый эл-т и меняется ссылка со станого списска на новый
         */
        List <Integer> list = new CopyOnWriteArrayList<>();
        /**
         * Stream - используется для обработки потока данных, для применения
         * операции для всех данных этого потока, сделано для того, чтобы не
         * блокировать операции на чтение
         */

        List<Integer> subList = IntStream.
                rangeClosed(1,100).
                boxed().
                collect(Collectors.toList());
        /**
         * Runnable - то, что передается непосредственно в поток
         */
        Runnable listAdd = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        Thread t1 = new Thread(listAdd);
        Thread t2 = new Thread(listAdd);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(list.size());

    }

}
