package iwein.samples;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author iwein
 */
public class MDCTestDrive {

    private static final Logger logger = LoggerFactory.getLogger(MDCTestDrive.class);

    @Test
    public void shouldCopyContextFromOtherThread() throws Exception {
        final CountDownLatch taskExecuted = new CountDownLatch(1);
        final AtomicReference<Map> contextMap = new AtomicReference<Map>();

        //some context is set in a different thread
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                MDC.put("first", "Dorothy");
                MDC.put("last", "Parker");
                logger.info("Check enclosed.");
                contextMap.set(MDC.getCopyOfContextMap());
                taskExecuted.countDown();
            }
        });

        taskExecuted.await();

        //if you don't do this you don't have the context because it was populated in a child thread
        MDC.setContextMap(contextMap.get());
        logger.debug("The most beautiful two words in English.");
    }

    @Test
    public void shouldCopyContextToOtherThread() throws Exception {
        final AtomicReference<Map> contextMap = new AtomicReference<Map>();

        MDC.put("first", "Dorothy");
        MDC.put("last", "Parker");

        contextMap.set(MDC.getCopyOfContextMap());

        //some context is set in a different thread
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                //not needed, but shouldn't break anything in the parent right?
                MDC.setContextMap(contextMap.get());
                logger.info("Check enclosed.");
            }
        });

        //If I don't do this I have lost my context. That seems wrong..
        //MDC.setContextMap(contextMap.get());

        logger.debug("The most beautiful two words in English.");
    }
}
