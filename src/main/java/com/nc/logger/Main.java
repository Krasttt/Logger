package com.nc.logger;

import java.io.IOException;

public class Main {
    private static final Logger log = MyLoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws IOException {
        ClassB b = new ClassB();
        b.methodClassB();
        someMethod1();
    }

    static void someMethod1() {
        log.info("info");
        log.debug("debug");
    }
}
