package nl.codebulb.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DaedalusLogger {

    private static final Logger LOG = LoggerFactory.getLogger("Daedalus Engine");

    public static void info(String msg) {
         LOG.info(msg);
    }

    public static void warn(String msg) {
         LOG.warn(msg);
    }

    public static void error(String msg) {
         LOG.error(msg);
    }

}
