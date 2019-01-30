package com.nc.logger;

public class ClassC {
    private static final Logger log = MyLoggerFactory.getLogger(ClassC.class);

    public void methodClassC(){
        log.info("ClassC");
        ClassA a = new ClassA();
        a.methodClassA();
    }
}
