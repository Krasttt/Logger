package com.nc.logger;
public class ClassA {
    private static final Logger log = MyLoggerFactory.getLogger(ClassA.class);

    public void methodClassA(){
        log.debug("ClassA");
    }
}
