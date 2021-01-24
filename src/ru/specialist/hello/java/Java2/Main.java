package ru.specialist.hello.java.Java2;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        // узнаем количество доступных ядер процессора (потоков)
        System.out.println(Runtime.getRuntime().availableProcessors());

//        запуск проводника
//	Runtime.getRuntime().exec("explorer.exe");

//        вызываем сборщик мусора. На практике не используется.
        Runtime.getRuntime().gc();

    }
}
