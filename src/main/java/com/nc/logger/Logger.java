package com.nc.logger;

public interface Logger {
    public void debug(String msg);
    public void info(String msg);
    public void error(String msg);
    public void warn(String msg);
    public void fatal(String msg);
}
