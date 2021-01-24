package ru.specialist.hello.java.Java2.threads.forkjoin.add;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
/**
 * Класс описывающий логику вычислений (сложения)
 */

import static ru.specialist.hello.java.Java2.threads.forkjoin.add.App.STEP;

/**
 * Наследуемся от Recursiv, который наследуется от Fork
 */
public class Adder extends RecursiveTask<Long> {
    private long from;
    private long to;

    /**
     * Конструктор. Позаывающий диапазон в котором надо будет делать расчеты
     * @param from мин число
     * @param to мах число
     */
    public Adder (long from, long to){
        this.from = from;
        this.to = to;
    }

    /**
     * Расчет диапазона
     * @return сумму диапазона
     */
    @Override
    protected Long compute() {
        long sum = 0;
        long range = to - from + 1;

        /**
         * Если он меньше мин значения шага, то просто будет вызываться
         * суммирование в одном потоке в цикле. И решается как одна зачада
         * Если это значение больше чем шаг см. ниже
         */
        if (range <= STEP){
            return sum(from,to);
        }

        List<Adder> subTasks = new LinkedList<>();

        /**
         * При значении большем чем шаг, мы его делим на разные подзадачи
         * Создаем в цикле подзадачи, через объект Adder (Adder task) и у него вызываем
         * метод Fork. И этот метод говорит, что объект task это подзадача и
         * будет выделяться доп. поток. После чего мы сможем суммировать все
         * подзадачи и вывести результат
         */
        long nTasks = range/STEP;
        long r = range % nTasks;
        for (int i = 0; i < nTasks; i++) {
            long a = i * STEP + 1;
            long b = i * STEP + STEP + (i == nTasks - 1 ? r : 0);
            //это объекст класса Adder
            Adder task = new Adder(a,b);
            //и вызываем у него создание подзадачи(ответвление)(отд треды)
            task.fork();
            subTasks.add(task);
        }
        //получение результата
        for (Adder task:subTasks) {
            //присоединение
            sum += task.join();
        } return sum;
    }

    /**
     * Метод сложения значений в цикле
     * @param from мин число
     * @param to мах число
     * @return сумма чисел
     */
    private Long sum(long from, long to) {
        long sum = 0;
        for (long i = from; i < to; i++) {
            sum += i;
        }return sum;
    }

}
