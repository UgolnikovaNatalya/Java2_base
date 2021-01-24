package ru.specialist.hello.java.Java2.threads.executor;

import java.util.concurrent.Callable;

class Sum implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.printf("%s: Calculating (2+2)\n", Thread.currentThread().getName());
        Thread.sleep(1000);
        return 2 + 2;
    }
}
