package ru.specialist.hello.java.Java2.labor;
// Лекция №1 Лабораторная работа 3

import java.util.function.Function;

/**
 * Два потока поочереди должны считать функцию возведения в степень
 * и кваратный корень.
 * ********************************************************************
 * Отличие моего решения от вашего в том, что я цикл for сделала внутри
 * synchronized, у вас в лекции был вынесен и для выхода из цикла
 * вы использовали return;
 */
public class ThreadWaitNotifyLab {
    /**
     * @param n - число, которое возводим в степень и извлекаем корень
     * @param m - переменная, в которую записываем занчение n после извлечения корня
     * для передачи значения в if для Thread exp
     * @param count - количество циклов for
     */
    private static double n = 9;
    private static final int count = 3;
    private static final Object number = new Object();

    public static void main(String[] args) throws InterruptedException {

/**
 * Треды через метод не работают.
 */
        Thread sqrt = new Thread(getRunnable(Math::sqrt));
        Thread exp = new Thread(getRunnable(n -> n * n));
        /**
         * А через лямбду работают нормально.
         */

//        Thread sqrt = new Thread(()-> {
//            synchronized (number){
//                for (int i = 0; i < count; i++) {
//                    if (n == n){
//                        n = Math.sqrt(n);
//                        System.out.printf("%s: %.2f\n", Thread.currentThread().getName(), n );
//                        try {
//                            number.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        number.notify();
//                    }
//                }
//            }
//        });

//        Thread exp = new Thread(()-> {
//            synchronized (number){
//                for (int i = 0; i < count; i++) {
//                    if (n == n){
//                        n *= n;
//                        System.out.printf("%s: %.2f\n", Thread.currentThread().getName(), n );
//                        number.notify();
//                    }
//                   try {
//                       number.wait();
//                   } catch (InterruptedException e) {
//                       e.printStackTrace();
//                   }
//               }
//           }
//        });

        sqrt.start();
        exp.start();
    }

//
        private static Runnable getRunnable (Function <Double, Double> function) {
        return () -> {
            synchronized (number){
                for (int i = 0; i < count; i++) {
                    if (n == n){
                        n = function.apply(n);
                        System.out.printf("%s: %.2f\n", Thread.currentThread().getName(), n );

                        number.notify();
                        if (i == count -1) return;
                        try {
                            number.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

        };
//
    }
}