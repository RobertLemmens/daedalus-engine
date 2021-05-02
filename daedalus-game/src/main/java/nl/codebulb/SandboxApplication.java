package nl.codebulb;

import nl.codebulb.engine.DaedalusApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SandboxApplication extends DaedalusApplication {

    private static final Logger logger = LoggerFactory.getLogger("Daedalus Application");

    public static void main(String[] args) {
        SandboxApplication sandboxApplication = new SandboxApplication();
        sandboxApplication.run(new SandboxLoop());
    }
}
