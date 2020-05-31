package org.legacy.it.program;

import org.legacy.it.request.ServiceTask;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Cics implements Runnable {

    private BlockingQueue responseQueue;
    private final BlockingQueue queue = new LinkedBlockingDeque();
    private String name;
    private ServiceTask serviceTask;

    public Cics(String name, ServiceTask serviceTask, BlockingQueue responseQueue) {
        this.name = name;
        this.serviceTask = serviceTask;
        this.responseQueue = responseQueue;
    }

    public void run(final ServiceTask serviceTask) {
        System.out.println("Calling the RUN method");
        if(serviceTask.getName().equals("screen1")) {
            System.out.println("Service Task for Screen 1");
            synchronized (queue) {
                try{
                    System.out.println("Is it coming here ???");
                    queue.put(serviceTask);
                    System.out.println("Queue Put: " + Instant.now().toString());
                    queue.notifyAll();
                    System.out.println("Queue Notify: " + Instant.now().toString());
                    processServiceTask(serviceTask);
                }catch (Exception e){
                }
            }
        }
        else if(serviceTask.getName().equals("screen2")) {
            System.out.println("Service Task for Screen 2");
            synchronized (queue) {
                try{
                    queue.put(serviceTask);
                    System.out.println("Queue Put: " + Instant.now().toString());
                    queue.notifyAll();
                    System.out.println("Queue Notify: " + Instant.now().toString());
                    processServiceTask(serviceTask);
                }catch (Exception e){
                }
            }
        }
        else if(serviceTask.getName().equals("screen3")) {
            System.out.println("Service Task for Screen 3");
            synchronized (queue) {
                try{
                    queue.put(serviceTask);
                    System.out.println("Queue Put: " + Instant.now().toString());
                    queue.notifyAll();
                    System.out.println("Queue Notify: " + Instant.now().toString());
                    processServiceTask(serviceTask);
                }catch (Exception e){
                }
            }
        }
    }

//    public void run(final ServiceTask serviceTask) {
//        System.out.println("Calling the RUN method");
//        synchronized (queue) {
//            try{
//                System.out.println("Is it coming here ???");
//                queue.put(serviceTask);
//                System.out.println("Queue Put: " + Instant.now().toString());
//                queue.notifyAll();
//                System.out.println("Queue Notify: " + Instant.now().toString());
//                processServiceTask(serviceTask);
//            }catch (Exception e){
//            }
//        }
//    }

    public void processServiceTask(ServiceTask serviceTask) {
        System.out.println("In processServiceTask: " + Instant.now().toString());
        synchronized (queue) {
                try{
                    Object take = queue.take();
                    System.out.println("Get: " + take + " ; Time: " + Instant.now().toString());
                    Thread.sleep(2000);
                    String name = serviceTask.getName().equals("screen1") ? "screen2" : serviceTask.getName().equals("screen2") ? "screen3": "end";
                    final ServiceTask responseServiceTask = ServiceTask.builder().name(name).build();
                    responseQueue.put(responseServiceTask);
                    responseQueue.notifyAll();
                    queue.notifyAll();
                }catch (Exception e){
                }
            }
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        run(serviceTask);
    }
}