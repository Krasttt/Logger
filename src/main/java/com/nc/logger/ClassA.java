package com.nc.logger;
public class ClassA {
    private static final Logger log = new DBLogger(ClassA.class);

    public void methodClassA(){
        log.debug("ClassA");
        System.out.println("ClassA");
    }
}
