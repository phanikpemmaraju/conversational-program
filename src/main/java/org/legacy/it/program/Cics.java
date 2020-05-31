package org.legacy.it.program;

import org.legacy.it.request.ServiceTask;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;

public class Cics implements Runnable {

    private BlockingQueue responseQueue;
    private BlockingQueue requestQueue;
    private String name;

    public Cics(String name, BlockingQueue requestQueue, BlockingQueue responseQueue) {
        this.name = name;
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
    }

    public void runProgram() {
        System.out.println("Calling the RUN method");
        synchronized (requestQueue) {
            processServiceTask1();
            processServiceTask2();
            processServiceTask3();
        }
    }

    public void processServiceTask1() {
        System.out.println("In processServiceTask1 : " + Instant.now().toString());
        synchronized (requestQueue) {
            try{
                Object take = requestQueue.take();
                System.out.println("Get processServiceTask1: " + take + " ; Time: " + Instant.now().toString());
                Thread.sleep(2000);
                final ServiceTask responseServiceTask = ServiceTask.builder().name("screen2").build();
                responseQueue.put(responseServiceTask);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void processServiceTask2() {
        System.out.println("In processServiceTask2: " + Instant.now().toString());
        synchronized (requestQueue) {
            try{
                Object take = requestQueue.take();
                System.out.println("Get processServiceTask2: " + take + " ; Time: " + Instant.now().toString());
                Thread.sleep(2000);
                final ServiceTask responseServiceTask = ServiceTask.builder().name("screen3").build();
                responseQueue.put(responseServiceTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void processServiceTask3() {
        System.out.println("In processServiceTask3: " + Instant.now().toString());
        synchronized (requestQueue) {
            try{
                Object take = requestQueue.take();
                System.out.println("Get processServiceTask3: " + take + " ; Time: " + Instant.now().toString());
                Thread.sleep(2000);
                final ServiceTask responseServiceTask = ServiceTask.builder().name("end").build();
                responseQueue.put(responseServiceTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void processServiceTask(ServiceTask serviceTask) {
        System.out.println("In processServiceTask: " + Instant.now().toString());
        synchronized (requestQueue) {
                try{
                    Object take = requestQueue.take();
                    System.out.println("Get: " + take + " ; Time: " + Instant.now().toString());
                    Thread.sleep(2000);
                    String name = serviceTask.getName().equals("screen1") ? "screen2" : serviceTask.getName().equals("screen2") ? "screen3": "end";
                    final ServiceTask responseServiceTask = ServiceTask.builder().name(name).build();
                    responseQueue.put(responseServiceTask);
                    responseQueue.notifyAll();
                    requestQueue.notifyAll();
                }catch (Exception e){
                }
            }
    }

    @Override
    public void run() {
        Thread.currentThread().setName(name);
        runProgram();
    }

}