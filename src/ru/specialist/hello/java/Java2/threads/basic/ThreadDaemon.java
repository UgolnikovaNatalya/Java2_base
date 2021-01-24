package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий работу потоков Daemon
 * Main Thread завершает свою работу не дожидаясь окончания работы
 * потоков Daemon, в то время как обычные потоки, основной поток дожидается
 */
public class ThreadDaemon {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                System.out.println(i);
            }
        });
        t.setDaemon(true);
        t.start();

        Thread.sleep(1);
    }
}
