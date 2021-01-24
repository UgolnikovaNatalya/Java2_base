package ru.specialist.hello.java.Java2.threads.basic;

public class ThreadPriority {
    static int n1 = 0;
    static int n2 = 0;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Thread: " + Thread.currentThread().getName());

        Thread t1 = new Thread(()->{
            System.out.println("Thread: " + Thread.currentThread().getName());
            for (int i = 0; i < 1000000000; i++) {
                n1++;
            }
        });

        Thread t2 = new Thread(()->{
            System.out.println("Thread: " + Thread.currentThread().getName());
            for (int i = 0; i < 1000000000; i++) {
                n2++;
            }
        });

        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();

        /**
         * Пока n2 не станет больше 1000000000
         * ему будет выделяться больше времени, чем первому
         */
        while (n2 < 1000000000){
            Thread.sleep(1L);
        }
        System.out.println("n1 " + n1);
        System.out.println("n2 " + n2);
    }
}
