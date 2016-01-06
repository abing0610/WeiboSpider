package com.inga.utils.log;


import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 * Date:15/4/24
 * Time:12:11
 * Author Mr.Object
 */
public class PlatformLogger {

    private static final String FQCN = PlatformLogger.class.getName();

    private final static String MDC_ADDRESS = "address";
    private final static String MDC_HOSTNAME = "hostname";

    private final static String LOG_NAME_MONITOR = "PLATFORM_LOGGER.MONITOR";
    private final static String LOGGER_NAME_MESSAGE = "PLATFORM_LOGGER.MESSAGE";
    private final static String LOGGER_NAME_BUSINESS = "PLATFORM_LOGGER.BUSINESS";
    private final static String LOGGER_NAME_ERROR = "PLATFORM_LOGGER.ERROR";
    private final static String LOGGER_NAME_PERFORMANCE = "PLATFORM_LOGGER.PERFORMANCE";

    protected final static Logger MONITOR_LOG = Logger.getLogger(LOG_NAME_MONITOR);
    protected final static Logger MESSAGE_LOG = Logger.getLogger(LOGGER_NAME_MESSAGE);
    protected final static Logger BUSINESS_LOG = Logger.getLogger(LOGGER_NAME_BUSINESS);
    protected final static Logger ERROR_LOG = Logger.getLogger(LOGGER_NAME_ERROR);
    protected final static Logger PERFORMANCE_LOG = Logger.getLogger(LOGGER_NAME_PERFORMANCE);

    /**
     * 控制台日志
     *
     * @param message log this message
     */
    public static void message(Object message) {
        if (MESSAGE_LOG.isDebugEnabled()) {
            MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
            MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
            MESSAGE_LOG.log(FQCN, LogLevel.MESSAGE, message, null);
        }
    }

    /**
     * 控制台日志
     *
     * @param message log this message
     * @param t       log this cause
     */
    public static void message(Object message, Throwable t) {
        if (MESSAGE_LOG.isDebugEnabled()) {
            MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
            MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
            MESSAGE_LOG.log(FQCN, LogLevel.MESSAGE, message, t);
        }
    }

    /**
     * 监控日志
     *
     * @param message log this message
     */
    public static void monitor(Object message) {
        if (MESSAGE_LOG.isDebugEnabled()) {
            MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
            MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
            MONITOR_LOG.log(FQCN, LogLevel.MONITOR, message, null);
        }
    }

    /**
     * 监控日志
     *
     * @param message log this message
     * @param t       log this cause
     */
    public static void monitor(Object message, Throwable t) {
        if (MESSAGE_LOG.isDebugEnabled()) {
            MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
            MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
            MONITOR_LOG.log(FQCN, LogLevel.MONITOR, message, t);
        }
    }

    /**
     * 错误日志
     *
     * @param message log this message
     */
    public static void error(Object message) {
        MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
        MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
        ERROR_LOG.log(FQCN, LogLevel.ERROR, message, null);
    }

    /**
     * 错误日志
     *
     * @param message log this message
     * @param t       log this cause
     */
    public static void error(Object message, Throwable t) {
        MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
        MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
        ERROR_LOG.log(FQCN, LogLevel.ERROR, message, t);
    }


    /**
     * 性能日志
     *
     * @param message log this message
     */
    public static void performance(Object message) {
        MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
        MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
        PERFORMANCE_LOG.log(FQCN, LogLevel.PERFORMANCE, message, null);
    }

    /**
     * 性能日志
     *
     * @param message log this message
     * @param t       log this cause
     */
    public static void performance(Object message, Throwable t) {
        MDC.put(MDC_ADDRESS, LocalHost.getLocalIP());
        MDC.put(MDC_HOSTNAME, LocalHost.getMachineName());
        PERFORMANCE_LOG.log(FQCN, LogLevel.PERFORMANCE, message, t);
    }



}
