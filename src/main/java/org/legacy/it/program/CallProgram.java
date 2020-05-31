package org.legacy.it.program;

import org.legacy.it.request.ServiceTask;

import java.util.Objects;
import java.util.concurrent.*;

public class CallProgram {

    public static void main(String... args) {
        ServiceTask serviceTask = ServiceTask.builder().name("screen1").build();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final BlockingQueue queue = new SynchronousQueue();
        Cics cics = new Cics("Cics-Thread-1", serviceTask, queue);

        synchronized (queue) {
            try{
                executorService.submit(cics, ServiceTask.class);
                ServiceTask response = ( ServiceTask) queue.poll(5000, TimeUnit.SECONDS);
                if(Objects.nonNull(response)) {
                    System.out.println("Response Service Task Screen Name: {} " + response.getName());
                }

            } catch (Exception e){}
        }

    }
}
