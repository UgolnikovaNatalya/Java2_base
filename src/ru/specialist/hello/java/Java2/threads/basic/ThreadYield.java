package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий вытесняющи/не вытесняющий многозадачность
 * Вытесняющая многозадачность - это то, как работает наша ОС
 * Планировщик решает кому сколько времени выделить для выполнения процесса
 * Невытесняющая многозадачность - когда поток сам решает, сколько ему выполняться
 */
public class ThreadYield {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
            if (i == 500){
                Thread.yield();
            }
            }
        });

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
            }
        });
        t1.start();
        t2.start();
    }
}
