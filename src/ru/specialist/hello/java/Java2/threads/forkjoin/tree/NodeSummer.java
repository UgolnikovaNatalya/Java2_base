package ru.specialist.hello.java.Java2.threads.forkjoin.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class NodeSummer extends RecursiveTask<Long> {
    private final Node node;

    public NodeSummer(Node node) {
        this.node = node;
    }
    /**
     * здесь вызов для улов этого корня и в них создает рекурсивно таски
     * для этих узлов до тех пор, пока дерево не закончится
     */
    @Override
    protected Long compute() {
        long sum = node.getValue();
        List<NodeSummer> subTasks = new LinkedList<>();
        /**
         * Для каждого узла создается подзадача
         */
        for(Node child : node.getChildren()) {
            //передача дочернего узла
            NodeSummer task = new NodeSummer(child);
            //с момента запуска форк, будут созданы новые подзадачи
            //и будут созданы другими потоками
            //ответвление
            task.fork();
            subTasks.add(task);
        }

        for(NodeSummer task : subTasks) {
            //присоединение и передача значений и суммирование
            sum += task.join();
        }

        return sum;
    }
}
