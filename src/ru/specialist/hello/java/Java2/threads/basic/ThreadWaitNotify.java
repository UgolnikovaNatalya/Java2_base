package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий работу Wait & Notify
 * Оба метода вызываются у объекта класса
 * Wait - освобождает монитор и переводит вызывающий поток в состояние ожидания
 * до тех пор, пока другой поток не вызовет метод notify()
 * Вызывается только внутри блока Synchronized!!!
 * Notify - продолжает работу потока, у которого ранее был вызван метод wait()
 * Так же вызывается в synchronized блоке!!!
 */
public class ThreadWaitNotify {

    private static final Object monitor = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            synchronized (monitor){
                try {
                    System.out.printf("%s: Waiting\n", Thread.currentThread().getName());
                    //тред t1 засыпает и ждет пока t0 его разбудит по notify();
                    monitor.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.printf("%s: Completed\n", Thread.currentThread().getName());
                // будит поток t0 для продолжения счета
                monitor.notify();
            }
        });

        Thread t0 = new Thread(()->{
            for (int i = 1; i < 100 ; i++) {
                System.out.println(i);
                if (i==50){
                    synchronized (monitor){
                        //будит тред t1
                        monitor.notify();
                        try{
                            //усыпляет поток t0
                            monitor.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t1.start();
        Thread.sleep(100);
        t0.start();
    }
}
