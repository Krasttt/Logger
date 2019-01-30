package com.nc.logger;

public class ClassB {
    private static final Logger log = MyLoggerFactory.getLogger(ClassB.class);

    public void methodClassB(){
        log.fatal("ClassB");
        ClassC c = new ClassC();
        c.methodClassC();
    }
}
