package com.nc.logger;

public class Main {
    private static final Logger log = MyLoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ClassB b = new ClassB();
        b.methodClassB();
        someMethod1();
    }

    static void someMethod1() {
        log.info("info");
        log.debug("debug");
    }
}
