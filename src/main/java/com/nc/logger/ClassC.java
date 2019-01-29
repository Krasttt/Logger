package com.nc.logger;

public class ClassC {
    private static final Logger log = new DBLogger(ClassC.class);

    public void methodClassC(){
        log.debug("ClassC");
        ClassA a = new ClassA();
        a.methodClassA();
        System.out.println("ClassC");
    }
}
