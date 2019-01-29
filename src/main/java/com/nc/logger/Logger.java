package com.nc.logger;

import java.util.Date;

public interface Logger {

    public void debug(String msg);
    public void info(String msg);
    public void error(String msg);
    public void warn(String msg);
    public void fatal(String msg);
}
