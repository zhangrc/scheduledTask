package com.yinhai.sheduledTask.frame.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by zrc on 2016/11/28.
 */
public class SysLevelException extends RuntimeException {
    public SysLevelException(String var1, Throwable var2) {
        super(var1, var2);
    }

    public SysLevelException(String var1) {
        super(var1);
    }

    public SysLevelException(Throwable var1) {
        super(var1);
    }

    public void printStackTrace(PrintStream var1) {
        if(super.getCause() != null) {
            var1.print(this.getClass().getName() + " Caused by: ");
            super.getCause().printStackTrace(var1);
        } else {
            super.printStackTrace(var1);
        }

    }

    public void printStackTrace(PrintWriter var1) {
        if(super.getCause() != null) {
            var1.print(this.getClass().getName() + " Caused by: ");
            super.getCause().printStackTrace(var1);
        } else {
            super.printStackTrace(var1);
        }

    }
}
