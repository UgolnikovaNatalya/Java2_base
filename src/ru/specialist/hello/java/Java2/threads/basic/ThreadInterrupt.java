package ru.specialist.hello.java.Java2.threads.basic;

/**
 * Класс описывающий прерывание потоков
 */
public class ThreadInterrupt {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i <= 10; i++) {
                System.out.printf("%s: %d\n", Thread.currentThread().getName(), i);
            }
            /**
             * это своего рода флаг. Это место, где остановится поток
             * когда вызовем метод t.interrupt()
             * Такой флаг можно устанавливать в нужных местах
             */
//             if (Thread.interrupted()){
//                 return; //прервет выполнение потока
//             }
            /**
             * Либо мы его останавливаем из вне, пока он находится в
             * спящем режиме
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            return;
            }
            for (int i = 11; i <= 20; i++) {
                System.out.printf("%s: %d\n", Thread.currentThread().getName(), i);
            }
        });
        t.start();
        t.interrupt();

    }
}
