package org.legacy.it.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCache {
    private ExecutorService executorService;
    private BlockingQueue requestQueue;
    private BlockingQueue responseQueue;
    private boolean isRunning;
}


