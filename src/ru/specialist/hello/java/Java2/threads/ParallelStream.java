package ru.specialist.hello.java.Java2.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ParallelStream {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(3);
        list.add(4);
        list.add(7);

        System.out.println("Custom list");
        System.out.println(list);


        List<Integer> filteredList = new ArrayList<>();

        for (Integer i : list) {
            if (i > 4){
                filteredList.add(i);
            }
        }
        System.out.println("New list");
        System.out.println(filteredList);

        System.out.println("New sorted list");
        Collections.sort(filteredList);
        System.out.println(filteredList);
//----------------------------------------------------
        System.out.println("Stream list");
        /**
         * Приведение к строке.
         * Фильтруем, сортируем, приводим значения i
         * к строке и выводим (будет выводиться без скобок)
         */
        String s = list.stream()
                .filter(i -> i > 4)
                .sorted()
                .map(i -> Integer.toString(i))
                .collect(Collectors.joining(", "));

        System.out.println(s);
        /**
         * Stream у коллекции создает стрим для ссылочных типов,
         * (Integer, Long etc), т.е. для объектов
         * тк обрабатывает коллекцию.
         */
        List<Integer> sortedList = list.stream()
                .filter(i -> i > 4)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(sortedList);

        /**
         * LongStream, IntStream - для работы с примитивными данными
         * тк не обрабатывает коллекцию. Будет работать как с массивом
         * (int, long etc)
         */
        long t1 = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 1000000000).
                sum();
        long t2 = System.currentTimeMillis();

        System.out.println(sum);
        System.out.printf("Time: %d ms\n", t2-t1);

        /**
         * способ распараллеливания для решения задачи
         */
        t1 = System.currentTimeMillis();
        long parallel = LongStream.rangeClosed(0, 1000000000)
                //должно стоять первым, чтобы все, что после него идет
                //распараллеливалось
                .parallel()
                .sum();
        t2 = System.currentTimeMillis();

        System.out.println(sum);
        System.out.printf("Time (parallel): %d ms\n", t2-t1);
    }
}
