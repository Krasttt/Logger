package com.nc.logger;

public class ClassB {
    private static final Logger log = new DBLogger(ClassB.class);

    public void methodClassB(){
        log.debug("ClassB");
        ClassC c = new ClassC();
        c.methodClassC();
        System.out.println("ClassB");
    }
}
