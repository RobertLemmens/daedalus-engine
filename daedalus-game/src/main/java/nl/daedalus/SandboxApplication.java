package nl.daedalus;

import nl.daedalus.engine.core.DaedalusApplication;

public class SandboxApplication extends DaedalusApplication {

    public static void main(String[] args) {
        SandboxApplication sandboxApplication = new SandboxApplication();
        sandboxApplication.run(new SandboxLoop());
    }

}
