package iwein.samples.foo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iwein
 */
public class SomeLoggingThing {
    private static final Logger logger = LoggerFactory.getLogger(SomeLoggingThing.class);

    public static void saySomething(){
        logger.info("INFO");
        logger.debug("DEBUG");
        logger.trace("TRACE");
    }
}
