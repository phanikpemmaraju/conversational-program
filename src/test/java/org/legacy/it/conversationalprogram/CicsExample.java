package org.legacy.it.conversationalprogram;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class CicsExample {

    public static void main(String... args) {
        final BlockingQueue queue = new SynchronousQueue();
        new Producer("Producer", queue);
        new Consumer("Consumer", queue);
    }

    public void run() {
    }
}

class Producer implements Runnable {
    protected BlockingQueue queue;

    public Producer(String name, BlockingQueue queue){
        this.queue = queue;
        Thread t = new Thread(this,name);
        t.start();
    }

    public void run() {
        int i = 0;
        while(true) {
            try{
                queue.put(i++);
                System.out.println("Put: " + i);
                notify();
                wait();
            }catch (Exception e){}
        }

    }
}

class Consumer implements Runnable {
    protected BlockingQueue queue;

    public Consumer(String name, BlockingQueue queue){
        this.queue = queue;
        Thread t = new Thread(this,name);
        t.start();
    }

    public void run() {
        while(true) {
            try{
                Object take = queue.take();
                System.out.println("Get: " + take);
                Thread.sleep(2000);
                notifyAll();
            }catch (Exception e){}
        }

    }
}
