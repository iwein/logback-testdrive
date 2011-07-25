package iwein.samples;

import org.junit.Test;
import iwein.samples.foo.SomeLoggingThing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iwein
 */
public class LoggingConfigurationPlayground {
    private final Logger logger = LoggerFactory.getLogger(LoggingConfigurationPlayground.class);
    @Test
    public void shouldCopyContextBetweenOtherThreads() throws Exception {
        SomeLoggingThing.saySomething();
        logger.debug("DEBUG"+this);
        logger.trace("TRACE"+this);
    }
}
