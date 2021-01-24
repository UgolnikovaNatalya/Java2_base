package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий методы потока
 */
public class ThreadMethods {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("**********Методы потока**********");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getId());
        System.out.println(Thread.currentThread().getState());

        System.out.println("Sleep");
        Thread.sleep(1000);
        System.out.println("Continue");

        /**
         * DaemonThread - поток, завершение которого не обязательно
         * для завершения основного потока
         */
        System.out.println("**********DaemonThread**********");
        Thread t = new Thread(()->{
            for (int i = 0; i <= 100; i++) {
                System.out.println(i);
            }
        });
        //делаем поток незавершенным
        t.setDaemon(true);
        t.start();

        Thread.sleep(1);
    }
}
