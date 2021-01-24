package ru.specialist.hello.java.Java2.labor;

import java.util.Scanner;

/**
 * Вводим 2 числа. Создаем 2 треда. Оба считают до нужного числа
 * Второй тред ждет пока первый посчитает и только потом стартует
 * Усложненная задача: 3 потока, первые 2 выполняются,
 * потом выполняется main
 */
public class ThreadJoinLab extends Thread {
    private int numbers;

    public ThreadJoinLab(int numbers) {
        this.numbers = numbers;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("*****My example*****");
        int num1, num2, num3;

        System.out.printf ("Thread - %s: \n", Thread.currentThread().getName());

        try (Scanner number = new Scanner(System.in)) {
            System.out.print("First number: ");
            num1 = number.nextInt();
            System.out.print("Second number: ");
            num2 = number.nextInt();
            System.out.print("Third number: ");
            num3 = number.nextInt();
        }

        Thread first = new Thread(() -> loop(num1));

        Thread second = new Thread(() -> {
            try {
                first.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loop(num2);});


        first.start();
        Thread.sleep(1L);
        second.start();

//        mainThread ждет пока выполнится second
        second.join();
        loop (num3);


    }
    private static void loop (int n){
        for (int i = 0; i <= n; i++) {
            System.out.printf("%s: %d\n", Thread.currentThread().getName(), i);
        }
    }
}
