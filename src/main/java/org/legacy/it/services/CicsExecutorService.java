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
    private BlockingQueue queue;
//    private String programName;

    @Autowired
    public CicsExecutorService(){
//        this.programName = programName;
        this.executorService = getExecutorService();
        this.queue = getBlockingQueue();
    }

    public ServiceTask nextStage(ServiceTask serviceTask) {
        ServiceTask responseServiceTask;
        synchronized (queue) {
            try{
                final Cics cics = new Cics("Cics-Thread-1", serviceTask, queue);
                executorService.submit(cics, ServiceTask.class);
                responseServiceTask = ( ServiceTask) queue.poll(5000, TimeUnit.SECONDS);
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

    @Cacheable(value = "blockingQueues", key = "'cics1'")
    public BlockingQueue getBlockingQueue() {
        return new SynchronousQueue();
    }

//    @Cacheable(value = "program", key = "#programName")
//    public Class<?> getProgram() {
//        try{
//            Class<?> aClass = Class.forName(programName);
//            aClass
//        } catch (Exception e){}
//        return null;
//    }

}
