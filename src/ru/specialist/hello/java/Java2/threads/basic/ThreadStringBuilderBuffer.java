package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий построение строк (склеивание)
 * При сложении двух строк, образуется новый класс
 * sout:(hello + " " + world) - образуется новый класс из 5 объектов!
 * String hello = "Hello"; & String world = "World! - неизменные объекты
 */

public class ThreadStringBuilderBuffer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Builder - строит определенный класс за счет добавления к нему
         * определенных элементов
         * Factory (фабрика) - генерирующий шаблон, который позволяет
         * создавать объект за счет вызова в нем определенных методов
         * (не через new а берем фабрику, берем объект передаем в него параметры
         * и потом вызываем метод и потом собирается нужный объект)
         */
        
        StringBuilder builder = new StringBuilder();

        /**
         * Buffer отличается от Builder - тем что он синхронизирован
         * Buffer - используется только при многопоточности!
         */
        StringBuffer buffer = new StringBuffer();
        builder.append("Hello");
        builder.append(" ");
        builder.append("World!");
        System.out.println(builder); // создается только один объект! вместо 5

        Thread t0 = new Thread(()->{
            for (int i = 1; i < 100; i++) {
//                builder.append(i + ", ");
                buffer.append(i + ", ");
            }
        });

        Thread t1 = new Thread(()->{
            for (int i = 101; i < 200; i++) {
//                builder.append(i + ", ");
                buffer.append(i + ", ");
            }
        });
        t0.start();
        t1.start();

        t0.join();
        t1.join();
//        System.out.println(builder);
        System.out.println(buffer);

    }
}
