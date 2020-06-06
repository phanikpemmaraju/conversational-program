package org.legacy.it.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

// NOTE: For every Session id, the application will have an instance of Program Cache.

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCache {
    private ExecutorService executorService;
    private BlockingQueue requestQueue;
    private BlockingQueue responseQueue;
}


