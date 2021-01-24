package ru.specialist.hello.java.Java2.threads.collection;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Map - хранит данные парами: ключ/значение
 * В данном примере второй тред будет перезаписывать значения первого треда
 * или наоборот, тк мы не знаем, как будут подключаться треды
 */
public class SyncMap {

    public static void main(String[] args) throws InterruptedException {

        Map<Integer, String> map =
                //несинхрон. При многопоточности не использовать!!!
                new HashMap<>();

                //синхрон
//                Collections.synchronizedMap(new HashMap<>());

                /*синхрон. Но отличается тем, что блокируется не вся таблица
                * а только то, что меняется в ней
                * Данный вариант предпочтительнее других, для нескольких тредов*/
//                new ConcurrentHashMap<>();

        Runnable mapAdd = () -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(mapAdd);
        Thread t2 = new Thread(mapAdd);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

//        List<Integer> keys = map.keySet().stream().sorted().collect(Collectors.toList());
        System.out.println(map.size());
        //количество ключей больше, чем значений в цикле. Проверяем
        //Создаем массив, чтобы туда все сохранить и приводим его к строке
        //чтобы вывести значения и посмотреть, почему значений больше
        //значений больше, тк создаются null ячейки
        Object [] array = map.keySet().toArray();
//        System.out.println(Arrays.toString(array));
        //проверяем на null
        int [] vals = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) System.out.println("Null");
            else vals [i] = (Integer) array[i];
        }
    }
}
