package ru.specialist.hello.java.Java2.threads.forkjoin.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Класс Node - узел дерева содержит определенные значения
 * и дочерние узлы(children). В этом классе не бинарное дерево, а с определенным
 * количеством "узлов"
 */
public class Node {
    //вес дерева
    private long value;
    //дочерние узлы
    private List<Node> children = Collections.emptyList();

    public Collection<Node> getChildren(){
        return children;
    }

    public Node (long value){
        this.value = value;
    }

    public long getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return toString(0);
    }


    /**
     * Метод для вывода на экран
     * @param level
     * @return
     */
    private String toString(int level){
        StringBuilder sb = new StringBuilder();

        sb = sb.append("\t".repeat(Math.max(0, level))).append(value).append("\n");

        for (Node n : children) {
            sb.append(n.toString(level + 1));
        }

        return sb.toString();
    }
}
