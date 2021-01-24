package ru.specialist.hello.java.Java2.threads.forkjoin.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class App {
    private static int total = 0;

    public static void main(String[] args) {
        Node root = generateRandomTree(20);
//        System.out.println(root);

        //создаем поток с такой
        //в таске у нас nodeSummer(root)
        /**
         * Задача: запустить рекурсивно эту же задачу для других улов
         * результат выполнения их сложить с весом текущего узла и его вернуть
         * Здесь вызывается для корня
         */
        long sum = ForkJoinPool.commonPool().invoke(new NodeSummer(root));

        System.out.println(total);
        System.out.println(sum);

    }

    private static long sumRecursive(Node node){
        long sum = 0;
        for (Node n: node.getChildren()) {
            sum += sumRecursive(n);
        }
        return sum + node.getValue();
    }

    /**
     * Метод генерации рандомного дерева
     * @param level уровень вложенности. На каком уровне будут вложены узлы
     * @return
     */
    private static Node generateRandomTree(int level){
        Random rnd = new Random();
        //вес дерева(кол-во узлов)
        long weight = rnd.nextInt(100);
        //записываем вес дерева, для сверки с результатом подсчитанном программой
        total += weight;
        Node root = new Node(weight);
        //создание корня
        if (level == 0)
            return root;

        //генерация дочерних узлов 1-4 шт
        int nChildren = rnd.nextInt(3) + 1;
        List<Node> children = new ArrayList<>();
        //рекурсивный метод генерации дерева
        for (int i = 0; i < nChildren; i++) {
            children.add(generateRandomTree(level - 1));
        }

        root.setChildren(children);
        return root;
    }
}
