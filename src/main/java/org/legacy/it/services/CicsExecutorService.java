package org.legacy.it.services;

import org.legacy.it.program.Cics;
import org.legacy.it.request.ServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.*;

@Service
public class CicsExecutorService {

    private ExecutorService executorService;
    private BlockingQueue requestQueue;
    private BlockingQueue responseQueue;
//    private String programName;

    @Autowired
    public CicsExecutorService(){
//        this.programName = programName;
        this.executorService = getExecutorService();
        this.requestQueue = getBlockingRequestQueue();
        this.responseQueue = getBlockingResponseQueue();
    }

    public ServiceTask nextStage(ServiceTask serviceTask) {
        ServiceTask responseServiceTask;
        System.out.println("Next Stage comes here: {} ");
        if (serviceTask.getName().equalsIgnoreCase("screen1")) {
            Cics cics = new Cics("Cics-Thread-1", requestQueue, responseQueue);
            executorService.submit(cics, ServiceTask.class);
        }

        try{
            System.out.println("Is it coming here in try block: {} ");
            requestQueue.put(serviceTask);
        } catch (Exception e){e.printStackTrace();}

        synchronized (responseQueue) {
            try{
                responseServiceTask = ( ServiceTask) responseQueue.poll(5000, TimeUnit.SECONDS);
                if (Objects.nonNull(responseServiceTask)) {
                    System.out.println("Response Service Task Screen Name: {} " + responseServiceTask.getName());
                }
            } catch (Exception e){return null;}
        }
        return responseServiceTask;
    }

    @Cacheable(value = "executorServices", key = "'cics1'" )
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(1);
    }

    @Cacheable(value = "blockingRequestQueues", key = "'cics1-request'")
    public BlockingQueue getBlockingRequestQueue() {
        return new LinkedBlockingDeque();
    }

    @Cacheable(value = "blockingResponseQueues", key = "'cics1-response'")
    public BlockingQueue getBlockingResponseQueue() {
        return new SynchronousQueue();
    }

    // Need this at a later point in time.
//    @Cacheable(value = "program", key = "#programName")
//    public Class<?> getProgram() {
//        try{
//            Class<?> aClass = Class.forName(programName);
//            aClass
//        } catch (Exception e){}
//        return null;
//    }

}
