package com.inga.utils.log;

import org.apache.log4j.Level;

import java.io.Serializable;

/**
 * Date:15/5/5
 * Time:16:56
 * Author Mr.Object
 */
public class LogLevel extends Level implements Serializable {

    // 10000
    public final static int MESSAGE_INT = DEBUG_INT;
    // 40000
    public final static int BUSINESS_INT = ERROR_INT;
    // 40000
    public final static int PERFORMANCE_INT = ERROR_INT;
    //10000
    public final static int MONITOR_INT = DEBUG_INT;

    public final static LogLevel MONITOR = new LogLevel(MONITOR_INT, "MONITOR", 7);
    public final static LogLevel MESSAGE = new LogLevel(MESSAGE_INT, "MESSAGE", 7);
    public final static LogLevel BUSINESS = new LogLevel(BUSINESS_INT, "BUSINESS", 3);
    public final static LogLevel PERFORMANCE = new LogLevel(PERFORMANCE_INT, "PERFORMANCE", 3);

    protected LogLevel(int level, String levelStr, int syslogEquivalent) {
        super(level, levelStr, syslogEquivalent);
    }
}
