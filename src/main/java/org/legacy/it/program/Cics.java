package org.legacy.it.program;

import lombok.extern.slf4j.Slf4j;
import org.legacy.it.request.ServiceTask;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class Cics implements Runnable {

    private BlockingQueue responseQueue;
    private BlockingQueue requestQueue;

    public Cics(BlockingQueue requestQueue, BlockingQueue responseQueue) {
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
    }

    public void runProgram() {
        try{
            log.info("Calling the RUN method for thread id: {}", Thread.currentThread().getId());
            synchronized (requestQueue) {
                processServiceTask1();
                processServiceTask2();
                processServiceTask3();
            }
        } finally {
            requestQueue = null;
            responseQueue = null;
            Thread.currentThread().interrupt();
        }
    }

    public void processServiceTask1() {
        log.info("In processServiceTask1 : {}" , Instant.now().toString());
        synchronized (requestQueue) {
            try{
                Object take = requestQueue.take();
                log.info("Get processServiceTask1: {} ; Time: {} " , take, Instant.now().toString());
                Thread.sleep(2000);
                final ServiceTask responseServiceTask = ServiceTask.builder().name("screen2").build();
                responseQueue.put(responseServiceTask);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void processServiceTask2() {
        log.info("In processServiceTask2 : {}" , Instant.now().toString());
        synchronized (requestQueue) {
            try{
                Object take = requestQueue.take();
                log.info("Get processServiceTask2: {} ; Time: {} " , take, Instant.now().toString());
                Thread.sleep(2000);
                final ServiceTask responseServiceTask = ServiceTask.builder().name("screen3").build();
                responseQueue.put(responseServiceTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void processServiceTask3() {
        log.info("In processServiceTask3 : {}" , Instant.now().toString());
        synchronized (requestQueue) {
            try{
                Object take = requestQueue.take();
                log.info("Get processServiceTask3: {} ; Time: {} " , take, Instant.now().toString());
                Thread.sleep(2000);
                final ServiceTask responseServiceTask = ServiceTask.builder().name("end").build();
                responseQueue.put(responseServiceTask);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        runProgram();
    }
}