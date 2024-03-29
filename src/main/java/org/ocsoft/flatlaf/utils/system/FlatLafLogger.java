/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ocsoft.flatlaf.utils.system;

import java.util.Map;
import java.util.WeakHashMap;

import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base logging class which can be used anywhere to log messages and exceptions.
 *
 * @author Mikle Garin
 */

public class FlatLafLogger {
    /**
     * todo 1. Add option to log within a separate thread to improve overall
     * performance
     */
    
    /**
     * Loggers cache.
     */
    protected static final Map<Class, Logger> loggers = new WeakHashMap<Class, Logger>();
    
    /**
     * Logging enabled/disabled conditions.
     */
    protected static final Map<Class, Boolean> loggingEnabled = new WeakHashMap<Class, Boolean>();
    
    /**
     * Logger synchronization lock object.
     */
    protected static final Object logLock = new Object();
    
    /**
     * Whether debug messages are enabled or not.
     */
    protected static boolean debugEnabled = false;
    
    /**
     * Whether Log is initialized or not.
     */
    protected static boolean initialized = false;
    
    /**
     * Initializes SettingsManager.
     */
    public static synchronized void initialize() {
        if (!initialized) {
            initialized = true;
            
            // Settings for SLF4J simple logger
            System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
            System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");
        }
    }
    
    /**
     * Returns whether debug messages are enabled or not.
     *
     * @return true if debug messages are enabled, false otherwise
     */
    public static boolean isDebugEnabled() {
        return debugEnabled;
    }
    
    /**
     * Sets whether debug messages are enabled or not.
     *
     * @param debugEnabled
     *            whether debug messages are enabled or not
     */
    public static void setDebugEnabled(final boolean debugEnabled) {
        synchronized (logLock) {
            FlatLafLogger.debugEnabled = debugEnabled;
        }
    }
    
    /**
     * Writes specified information message into log.
     *
     * @param message
     *            information message
     * @param data
     *            formatting data
     */
    public static void info(final String message, final Object... data) {
        info(ReflectUtils.getCallerClass(), message, data);
    }
    
    /**
     * Writes specified information message into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param message
     *            information message
     * @param data
     *            formatting data
     */
    public static void info(final Object logFor, final String message,
            final Object... data) {
        synchronized (logLock) {
            if (isLoggingEnabled(logFor)) {
                final String msg = data == null || data.length == 0 ? message
                        : String.format(message, data);
                getLogger(logFor).info(msg);
            }
        }
    }
    
    /**
     * Writes specified information message into log.
     *
     * @param message
     *            debugEnabled message
     * @param data
     *            formatting data
     */
    public static void debug(final String message, final Object... data) {
        debug(ReflectUtils.getCallerClass(), message, data);
    }
    
    /**
     * Writes specified information message into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param message
     *            debugEnabled message
     * @param data
     *            formatting data
     */
    public static void debug(final Object logFor, final String message,
            final Object... data) {
        synchronized (logLock) {
            if (debugEnabled) {
                if (isLoggingEnabled(logFor)) {
                    final String msg = data == null || data.length == 0 ? message
                            : String.format(message, data);
                    getLogger(logFor).debug(msg);
                }
            }
        }
    }
    
    /**
     * Writes specified warning message into log.
     *
     * @param message
     *            warning message
     */
    public static void warn(final String message) {
        warn(ReflectUtils.getCallerClass(), message);
    }
    
    /**
     * Writes specified warning message into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param message
     *            warning message
     */
    public static void warn(final Object logFor, final String message) {
        synchronized (logLock) {
            if (isLoggingEnabled(logFor)) {
                getLogger(logFor).warn(message);
            }
        }
    }
    
    /**
     * Writes specified warning message into log.
     *
     * @param message
     *            warning message
     * @param throwable
     *            exception
     */
    public static void warn(final String message, final Throwable throwable) {
        warn(ReflectUtils.getCallerClass(), message, throwable);
    }
    
    /**
     * Writes specified warning message into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param message
     *            warning message
     * @param throwable
     *            exception
     */
    public static void warn(final Object logFor, final String message,
            final Throwable throwable) {
        synchronized (logLock) {
            if (isLoggingEnabled(logFor)) {
                getLogger(logFor).warn(message, throwable);
            }
        }
    }
    
    /**
     * Writes specified exception into log.
     *
     * @param throwable
     *            exception
     */
    public static void error(final Throwable throwable) {
        error(ReflectUtils.getCallerClass(), throwable);
    }
    
    /**
     * Writes specified exception into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param throwable
     *            exception
     */
    public static void error(final Object logFor, final Throwable throwable) {
        synchronized (logLock) {
            if (isLoggingEnabled(logFor)) {
                getLogger(logFor).error(throwable.toString(), throwable);
            }
        }
    }
    
    /**
     * Writes specified exception message into log.
     *
     * @param message
     *            exception message
     * @param throwable
     *            exception
     */
    public static void error(final String message, final Throwable throwable) {
        error(ReflectUtils.getCallerClass(), message, throwable);
    }
    
    /**
     * Writes specified exception message into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param message
     *            exception message
     * @param throwable
     *            exception
     */
    public static void error(final Object logFor, final String message,
            final Throwable throwable) {
        synchronized (logLock) {
            if (isLoggingEnabled(logFor)) {
                getLogger(logFor).error(message, throwable);
            }
        }
    }
    
    /**
     * Writes specified exception message into log.
     *
     * @param message
     *            exception message
     */
    public static void error(final String message) {
        error(ReflectUtils.getCallerClass(), message);
    }
    
    /**
     * Writes specified exception message into log.
     *
     * @param logFor
     *            where to log message is attached
     * @param message
     *            exception message
     */
    public static void error(final Object logFor, final String message) {
        synchronized (logLock) {
            if (isLoggingEnabled(logFor)) {
                getLogger(logFor).error(message);
            }
        }
    }
    
    /**
     * Returns logger for the requesting class.
     *
     * @return logger for the requesting class
     */
    public static Logger get() {
        return getLogger(ReflectUtils.getCallerClass());
    }
    
    /**
     * Returns logger for the requesting class.
     *
     * @return logger for the requesting class
     */
    public static Logger getLogger() {
        return getLogger(ReflectUtils.getCallerClass());
    }
    
    /**
     * Returns logger for the specified class type.
     *
     * @param object
     *            class type or object type
     * @return logger for the specified class type
     */
    public static Logger getLogger(final Object object) {
        synchronized (logLock) {
            final Class type = object instanceof Class ? (Class) object
                    : object.getClass();
            Logger logger = loggers.get(type);
            if (logger == null) {
                logger = LoggerFactory.getLogger(type);
                loggers.put(type, logger);
            }
            return logger;
        }
    }
    
    /**
     * Disables logging for the specified class type.
     *
     * @param object
     *            class type or object type
     */
    public static void disableLogging(final Object object) {
        setLoggingEnabled(object, false);
    }
    
    /**
     * Enables logging for the specified class type.
     *
     * @param object
     *            class type or object type
     */
    public static void enableLogging(final Object object) {
        setLoggingEnabled(object, true);
    }
    
    /**
     * Sets whether logging for the specified class type is enabled or not.
     *
     * @param object
     *            class type or object type
     * @param enabled
     *            whether logging is enabled or not
     */
    public static void setLoggingEnabled(final Object object,
            final boolean enabled) {
        synchronized (logLock) {
            final Class type = object instanceof Class ? (Class) object
                    : object.getClass();
            if (!enabled) {
                loggingEnabled.put(type, enabled);
            } else {
                loggingEnabled.remove(type);
            }
        }
    }
    
    /**
     * Returns whether logging for the specified class type is enabled or not.
     *
     * @param object
     *            class type or object type
     * @return true if logging for the specified class type is enabled, false
     *         otherwise
     */
    public static boolean isLoggingEnabled(final Object object) {
        synchronized (logLock) {
            if (loggingEnabled.size() == 0) {
                return true;
            }
            final Class type = object instanceof Class ? (Class) object
                    : object.getClass();
            final Boolean enabled = loggingEnabled.get(type);
            return enabled == null || enabled;
        }
    }
}