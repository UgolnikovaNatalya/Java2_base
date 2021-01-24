package ru.specialist.hello.java.Java2.labor;

import java.util.Scanner;

/**
 * Пользователь вводит с клавиатуры 2 числа.
 * Создать 2 потока, которые будут выводить числа от 0 до введенного
 * t1 - поток выводящий первые числа
 * t2 - поток выводящий вторые числа
 */
//*********************************************************
//------------Решение преподавателя------------------------
//*********************************************************

public class ThreadFirstLab {

//    ***************Первый способ**********************

//    public static class Counter extends Thread {
//
//        private int number;
//
////-----------создаю конструктор для вводимого числа--------
//
//        public Counter(int number) {
//            this.number = number;
//        }
//
////        --------------переписываю метод запуска треда------------
//        @Override
//        public void run() {
//            for (int i = 0; i <= number; i++) {
//                System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
//            }
//        }
//
//        public static void main(String[] args) {
////------При использовании Scanner его обязательно нужно потом закрывать!
////      для этого можно использовать try или scanner.close
//            System.out.println("Решение от преподавателя");
//
//            int n1, n2;
//            try (Scanner num = new Scanner(System.in)) {
//                System.out.println("Enter first number");
//                n1 = num.nextInt();
//                System.out.println("Enter second number");
//                n2 = num.nextInt();
//            }//при окончании этого блока Scanner закроется автоматически
//
////            new Counter(n1).start();
////            new Counter(n2).start();
//
////***************Второй способ**********************
//            //            Первые числа
//            new Thread(() -> {
//                for (int i = 0; i < n1; i++) {
//                    System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
//
//                }
//            }).start();
//
//
////            Вторые числа
////            new Thread(() -> {
////                for (int i = 0; i < n2; i++) {
////                    System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
////
////                }
////            }).start();
//            new Thread(getRunnable(n2)).start();
//        }
//
////        Функциональное программирование. Возвращаем из метода
////        Вводимые числа.
//        private static Runnable getRunnable(int n) {
//            return () -> {
//                for (int i = 0; i < n; i++) {
//                    System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
//                }
//            };
//        }


/**
 * Мое решение
 */
//**********************************************************
//----------------------Мое решение-------------------------
//**********************************************************
    public static class FirstLabThread extends Thread {
    public static void main(String[] args) {
        //Вводим первое число
        System.out.println("Enter first number: ");
        Scanner n1 = new Scanner(System.in);
        int num1 = n1.nextInt();

        //Вводим второе число
        Scanner n2 = new Scanner(System.in);
        System.out.println("Enter second number: ");
        int num2 = n2.nextInt();

        //Создаю поток для первых чисел
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= num1; i++) {
                    System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
                }
            }
        });

        //Создаю поток для вторых чисел
        Thread t2 = new Thread(() -> {
            for (int i = 0; i <= num2; i++) {
                System.out.printf("%s %d\n", Thread.currentThread().getName(), i);
            }
        });

        //Запускаю потоки
        t1.start();
        t2.start();
//        -----------------------Конец моего решения--------------------
    }
    }
}



