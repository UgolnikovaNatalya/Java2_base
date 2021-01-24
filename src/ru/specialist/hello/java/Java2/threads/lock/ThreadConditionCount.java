package ru.specialist.hello.java.Java2.threads.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadConditionCount {

    private static final Lock lock = new ReentrantLock();

    private static final Condition cond1 = lock.newCondition();
    private static final Condition cond2 = lock.newCondition();

    private static int n;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            for (int i = 1; i <= 500 ; i++) {
                lock.lock();
                n++;
                System.out.printf("%s: %d\n", Thread.currentThread().getName(), n);
                //запуск второго треда
                if (i == 100) cond1.signal();
                //запуск 3 треда
                if (i == 200) cond2.signal();
                // в конце отпускает блокировку
                lock.unlock();
            }
        });

        Thread t2 = new Thread(()->{

            lock.lock();

            try {
                //ждет пока в первом потоке досчитается до 100
                cond1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Выполняется в любом случае, не важно был выброшен try/catch или нет
            //делается для гарантии, что точно выполнится
            finally {
                // открывается чтобы выполнить дальнейшее условие
                lock.unlock();
            }
            //условие, которое выполняется, когда произойдет unlock
            for (int i = 1; i <= 400 ; i++) {
                lock.lock();
                n--;
                System.out.printf("%s: %d\n", Thread.currentThread().getName(), n);
                lock.unlock();
            }
        });

        Thread t3 = new Thread(()->{
            lock.lock();
            try {
                cond2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            for (int i = 1; i <= 100 ; i++) {
                lock.lock();
                n--;
                System.out.printf("%s: %d\n", Thread.currentThread().getName(), n);
                lock.unlock();
            }
        });

        t2.start();
        Thread.sleep(100);

        t3.start();
        Thread.sleep(100);

        t1.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println(n);

    }
}
