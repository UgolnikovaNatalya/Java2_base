package ru.specialist.hello.java.Java2.threads.lock;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReedWriteLock - отдельный интерфейс локов
 * есть отдельно блок на чтение и на запись
 * При чтении: когда один поток читает, то потоки, которые пишут - блокируются
 * потоки которые читают - не блокируются
 * При записи: кодга один поток пишет все остальные потоки блокируются!
 */

public class ThreadReadWriteList<E> {
    private List <E> list = new ArrayList<>();
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    public ThreadReadWriteList (E...initialElements){
        list.addAll(Arrays.asList(initialElements));
    }

    //запись
    public void add (E element){
        Lock writeLock = rwLock.writeLock();
        //получаем блокировку на чтение при записи
        writeLock.lock();

        try {
            //запись
            list.add(element);
        } finally {
            writeLock.unlock();
        }
    }

    //чтение. При попытке чтения во время записи, лок сразу отпустится
    //тк try не сможет выполниться, тк идет запись
    public E get(int index){
        Lock readLock = rwLock.readLock();
        //блокировка при чтении от записи
        readLock.lock();
        try{
            //читаем
            return list.get(index);
        }finally {
            //блокировку отпускаем
            readLock.unlock();
        }
    }

    //считываем информацию о размере
    public int size(){
        Lock readLock = rwLock.readLock();

        try {
            return list.size();
        }finally {
            readLock.unlock();
        }
    }
}
