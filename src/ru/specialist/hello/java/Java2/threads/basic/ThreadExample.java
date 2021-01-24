package ru.specialist.hello.java.Java2.threads.basic;

public class ThreadExample {

    /**
     * Класс представляющий собой поток
     * Объекты t0 & t1 & t2 выполняют его самостоятельно
     * t0 & t1 & t2- ссылки на поток, который является экземпляром класса Counter
     */
    public static class Counter extends Thread{
        /**
         * Переписываем метода Thread
         * %s - выводим строку с названием потока
         * %d - число i
         * смотрим на то, как процессор выполняет мультипоточность
         * переключаясь между потоками.
         */
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
            }
        }
        public static void main(String[] args) {
            // Thread is super - значит что мы из него можем ссылаться
            // на подкласс, а не наоборот

            Thread t0 = new Counter();
//            Counter t1 = new Counter();
            /**
             * Создаем анонимный класс
             * Анонимный класс - объявляется без указания в коде имени класса
             * new Runnable - это интерфейс и мы не знаем, как он исполняется
             * поэтому мы сразу задаем для него реализацию.
             * Runnable - явзяется функциональным интерфейсом
             * Функциональный интерфейс - класс у которого один метод без реализации
             * T1 & t2 - реализуют одно и то же, только в t2 мы убрали имя
             * и реализовали через лямбда-выражение
             */

            Thread t1= new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= 10; i++) {
                        System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
                    }
                }
            });

            int num2 = 15;
            Thread t2= new Thread(()-> {

                    for (int i = 1; i <= num2; i++) {
                        System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
                    }
            });

            t0.start();
            t1.start();
            t2.start();
        }
    }
}
