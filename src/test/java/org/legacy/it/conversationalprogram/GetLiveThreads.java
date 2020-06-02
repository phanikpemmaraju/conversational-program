package org.legacy.it.conversationalprogram;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.stream.LongStream;

public class GetLiveThreads {

    @Test
    public void getCurrentThreads() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

        String jvmName = runtimeBean.getName();
        System.out.println("JVM Name = " + jvmName);
        long pid = Long.valueOf(jvmName.split("@")[0]);
        System.out.println("JVM PID  = " + pid);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        int peakThreadCount = bean.getPeakThreadCount();
        System.out.println("Thread Count = " + peakThreadCount);

        ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
        System.out.println(thbean.getThreadInfo(3));

        // get the IDs of all live threads.
        long[] threadIDs = thbean.getAllThreadIds();
        LongStream.of(threadIDs).forEach(System.out::println);
    }
}
