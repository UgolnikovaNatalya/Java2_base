package ru.specialist.hello.java.Java2.threads.basic;

public class ThreadWaitNotifyAll {

    public static void main(String[] args) {
        Object monitor = new Object();
/**
 * Создаем лямбду, для создания 3 одинаковых потоков
 */
        Runnable runnable = () -> {
            synchronized (monitor){
                System.out.printf("%s: Waiting\n", Thread.currentThread().getName());
                try {
                    //отпускает монитор на 3 сек
                    monitor.wait(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.printf("%s: Completed\n", Thread.currentThread().getName());
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();

        /**
         * Чтобы не ждать 3 сек, мы в треде main ждем одну секунду
         * вызовем notify(); Он разбудит один из этих потоков
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        synchronized (monitor){
            System.out.printf("%s: Notifying\n", Thread.currentThread().getName());
            //будит только один поток
//            monitor.notify();
            //будит все потоки сразу
            monitor.notifyAll();
        }
    }
}
