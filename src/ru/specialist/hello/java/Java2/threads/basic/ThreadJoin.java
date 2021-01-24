package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий Join
 * Поток t1 ждет пока выполнится t0 и только потом завершается
 * Влияет на поток из которого он вызывается. Один из способов
 * синхронизации потоков. Важно, чтобы Join был вызван после того,
 * как стартанет поток t0
 */

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
            }
        });

        Thread t1 = new Thread(()->{
            try{
                t0.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.printf("%s: Completed\n", Thread.currentThread().getName());

        });


        t0.start();
        Thread.sleep(1);
        t1.start();
    }
}
