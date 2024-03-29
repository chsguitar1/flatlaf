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

package org.ocsoft.flatlaf.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.ocsoft.flatlaf.utils.TimeUtils;
import org.ocsoft.flatlaf.utils.collection.CollectionUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

/**
 * This timer is a small extension for standart javax.swing.Timer. Instead of
 * running in a single queue it creates separate Threads for each timer and does
 * not affect event-dispatching thread, until events are dispatched. This
 * basically means that you can use any number of Timer instances and you can
 * run them alltogether without having any issues.
 * <p/>
 * Also this Timer implementation offers a variety of additional features and
 * improvements which standard timer doesn't have (for example you can dispatch
 * events in a separate non-EDT thread and as a result avoid using EDT at all
 * where it is not necessary).
 *
 * @author Mikle Garin
 * @see javax.swing.Timer
 * @see org.ocsoft.flatlaf.utils.swing.TimerActionListener
 */
public class WebTimer {
    /**
     * Timer constants for calculations.
     */
    public static final int msInDay = 86400000;
    public static final int msInHour = 3600000;
    public static final int msInMinute = 60000;
    public static final int msInSecond = 1000;
    
    /**
     * Default name for timer thread.
     */
    public static String defaultThreadName = "WebTimer";
    
    /**
     * Default cycles number limit.
     */
    public static int defaultCyclesLimit = 0;
    
    /**
     * Whether EDT should be used as the default timer action execution thread.
     */
    public static boolean useEdtByDefault = true;
    
    /**
     * Timer event listeners list.
     */
    protected final List<ActionListener> listeners = new ArrayList<ActionListener>(
            1);
    
    /**
     * Unique (within one timer instance) ID of currently running thread.
     */
    protected int id = 0;
    
    /**
     * ID of previously executed thread.
     */
    protected int lastId;
    
    /**
     * Map of marks for currently active threads.
     */
    protected final Map<Integer, Boolean> running = new Hashtable<Integer, Boolean>();
    
    /**
     * Last timer cycle start time.
     */
    protected long sleepStart = 0;
    
    /**
     * Last timer cycle delay time.
     */
    protected long sleepTime = 0;
    
    /**
     * Number of executed cycles;
     */
    protected int cycleCount = 0;
    
    /**
     * Last timer thread.
     */
    protected Thread exec = null;
    
    /**
     * Delay between timer cycles in milliseconds.
     */
    protected long delay;
    
    /**
     * Delay before the first timer cycle run in milliseconds.
     */
    protected long initialDelay;
    
    /**
     * Whether timer repeat its cycles or not.
     */
    protected boolean repeats = true;
    
    /**
     * Whether each action should be fired from a separate invoke and wait call
     * or not. This might be useful if you are going to use multiply action
     * listeners and make some interface changes on each action.
     */
    protected boolean coalesce = true;
    
    /**
     * Whether actions should be fired from Event Dispatch Thread or not. This
     * might be useful if operations you want to perform within timer cycles
     * have nothing to do with Event Dispatch Thread.
     */
    protected boolean useEventDispatchThread = true;
    
    /**
     * Whether should use daemon thread instead of user one or not. Daemon
     * thread will allow JVM to shutdown even if timer is still running. This
     * option should be set before starting timer to have any effect.
     */
    protected boolean useDaemonThread = false;
    
    /**
     * Action command for fired events.
     */
    protected String actionCommand = "";
    
    /**
     * Internal timer thread name.
     */
    protected String name = null;
    
    /**
     * Timer cycles execution limit. Zero and less = unlimited amount of
     * execution cycles.
     */
    protected int cyclesLimit = 0;
    
    /**
     * Constructs timer with specified delay.
     *
     * @param delay
     *            delay between timer cycles
     * @see #parseDelay(String)
     */
    public WebTimer(final String delay) {
        this(parseDelay(delay));
    }
    
    /**
     * Constructs timer with specified delay.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     */
    public WebTimer(final long delay) {
        this(defaultThreadName, delay);
    }
    
    /**
     * Constructs timer with specified internal thread name and delay.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles
     * @see #parseDelay(String)
     */
    public WebTimer(final String name, final String delay) {
        this(name, parseDelay(delay));
    }
    
    /**
     * Constructs timer with specified internal thread name and delay.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     */
    public WebTimer(final String name, final long delay) {
        this(name, delay, null);
    }
    
    /**
     * Constructs timer with specified delay and initial delay.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     */
    public WebTimer(final long delay, final long initialDelay) {
        this(defaultThreadName, delay, initialDelay);
    }
    
    /**
     * Constructs timer with specified internal thread name, delay and initial
     * delay.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     */
    public WebTimer(final String name, final long delay, final long initialDelay) {
        this(name, delay, initialDelay, null);
    }
    
    /**
     * Constructs timer with specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param listener
     *            action listener
     * @see #parseDelay(String)
     */
    public WebTimer(final String delay, final ActionListener listener) {
        this(parseDelay(delay), listener);
    }
    
    /**
     * Constructs timer with specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     */
    public WebTimer(final long delay, final ActionListener listener) {
        this(defaultThreadName, delay, listener);
    }
    
    /**
     * Constructs timer with specified internal thread name, delay and action
     * listener.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles
     * @param listener
     *            action listener
     * @see #parseDelay(String)
     */
    public WebTimer(final String name, final String delay,
            final ActionListener listener) {
        this(name, parseDelay(delay), listener);
    }
    
    /**
     * Constructs timer with specified internal thread name, delay and action
     * listener.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     */
    public WebTimer(final String name, final long delay,
            final ActionListener listener) {
        this(name, delay, -1, listener);
    }
    
    /**
     * Constructs timer with specified delay, initial delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param listener
     *            action listener
     */
    public WebTimer(final long delay, final long initialDelay,
            final ActionListener listener) {
        this(defaultThreadName, delay, initialDelay, listener);
    }
    
    /**
     * Constructs timer with specified internal thread name, delay, initial
     * delay and action listener.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles
     * @param initialDelay
     *            delay before the first timer cycle run
     * @param listener
     *            action listener
     * @see #parseDelay(String)
     */
    public WebTimer(final String name, final String delay,
            final String initialDelay, final ActionListener listener) {
        this(name, parseDelay(delay), parseDelay(initialDelay), listener);
    }
    
    /**
     * Constructs timer with specified internal thread name, delay, initial
     * delay and action listener.
     *
     * @param name
     *            internal thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param listener
     *            action listener
     */
    public WebTimer(final String name, final long delay,
            final long initialDelay, final ActionListener listener) {
        super();
        setName(name);
        setDelay(delay);
        setInitialDelay(initialDelay);
        addActionListener(listener);
    }
    
    /**
     * Returns delay before the first timer cycle run in milliseconds.
     *
     * @return delay before the first timer cycle run in milliseconds
     */
    public long getInitialDelay() {
        return initialDelay;
    }
    
    /**
     * Returns delay before the first timer cycle run.
     *
     * @return delay before the first timer cycle run
     */
    public String getInitialStringDelay() {
        return toStringDelay(initialDelay);
    }
    
    /**
     * Sets delay before the first timer cycle run.
     *
     * @param initialDelay
     *            delay before the first timer cycle run
     * @return this timer
     */
    public WebTimer setInitialDelay(final String initialDelay) {
        setInitialDelay(parseDelay(initialDelay));
        return this;
    }
    
    /**
     * Sets delay before the first timer cycle run in milliseconds.
     *
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @return this timer
     */
    public WebTimer setInitialDelay(final long initialDelay) {
        if (initialDelay != -1 && initialDelay < 0) {
            throw new IllegalArgumentException("Invalid initial delay: "
                    + initialDelay);
        } else {
            this.initialDelay = initialDelay;
            return this;
        }
    }
    
    /**
     * Returns delay between timer cycles in milliseconds.
     *
     * @return delay between timer cycles in milliseconds
     */
    public long getDelay() {
        return delay;
    }
    
    /**
     * Returns delay between timer cycles.
     *
     * @return delay between timer cycles
     */
    public String getStringDelay() {
        return toStringDelay(delay);
    }
    
    /**
     * Sets delay between timer cycles.
     *
     * @param delay
     *            delay between timer cycles
     * @return this timer
     */
    public WebTimer setDelay(final String delay) {
        setDelay(parseDelay(delay));
        return this;
    }
    
    /**
     * Sets delay between timer cycles in milliseconds.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @return this timer
     */
    public WebTimer setDelay(final long delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("Invalid delay: " + delay);
        } else {
            this.delay = delay;
            return this;
        }
    }
    
    /**
     * Returns whether timer repeat its cycles or not.
     *
     * @return true if timer repeat its cycles, false otherwise
     */
    public boolean isRepeats() {
        return repeats;
    }
    
    /**
     * Sets whether timer should repeat its cycles or not.
     *
     * @param repeats
     *            whether timer should repeat its cycles or not
     * @return this timer
     */
    public WebTimer setRepeats(final boolean repeats) {
        this.repeats = repeats;
        return this;
    }
    
    /**
     * Returns whether each action should be fired from a separate invoke and
     * wait call or not.
     *
     * @return true if each action should be fired from a separate invoke and
     *         wait call, false otherwise
     */
    public boolean isCoalesce() {
        return coalesce;
    }
    
    /**
     * Sets whether each action should be fired from a separate invoke and wait
     * call or not.
     *
     * @param coalesce
     *            whether each action should be fired from a separate invoke and
     *            wait call or not
     * @return this timer
     */
    public WebTimer setCoalesce(final boolean coalesce) {
        this.coalesce = coalesce;
        return this;
    }
    
    /**
     * Returns whether actions should be fired from Event Dispatch Thread or
     * not.
     *
     * @return true if actions should be fired from Event Dispatch Thread, false
     *         otherwise
     */
    public boolean isUseEventDispatchThread() {
        return useEventDispatchThread;
    }
    
    /**
     * Sets whether actions should be fired from Event Dispatch Thread or not.
     *
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @return this timer
     */
    public WebTimer setUseEventDispatchThread(
            final boolean useEventDispatchThread) {
        this.useEventDispatchThread = useEventDispatchThread;
        return this;
    }
    
    /**
     * Returns whether should use daemon thread instead of user one or not.
     *
     * @return true if should use daemon thread instead of user one, false
     *         otherwise
     */
    public boolean isUseDaemonThread() {
        return useDaemonThread;
    }
    
    /**
     * Returns whether should use daemon thread instead of user one or not. If
     * set to true it will allow JVM to shutdown even if timer is still running.
     * This option should be set before starting timer to have any effect.
     *
     * @param useDaemonThread
     *            whether should use daemon thread instead of user one or not
     */
    public WebTimer setUseDaemonThread(final boolean useDaemonThread) {
        this.useDaemonThread = useDaemonThread;
        return this;
    }
    
    /**
     * Returns action command for fired events.
     *
     * @return action command for fired events
     */
    public String getActionCommand() {
        return actionCommand;
    }
    
    /**
     * Sets action command for fired events.
     *
     * @param actionCommand
     *            action command for fired events
     * @return this timer
     */
    public WebTimer setActionCommand(final String actionCommand) {
        this.actionCommand = actionCommand;
        return this;
    }
    
    /**
     * Returns timer cycles execution limit.
     *
     * @return timer cycles execution limit
     */
    public int getCyclesLimit() {
        return cyclesLimit;
    }
    
    /**
     * Sets timer cycles execution limit. Zero and less = unlimited amount of
     * execution cycles.
     *
     * @param cyclesLimit
     *            timer cycles execution limit
     * @return this timer
     */
    public WebTimer setCyclesLimit(final int cyclesLimit) {
        this.cyclesLimit = cyclesLimit;
        return this;
    }
    
    /**
     * Returns internal timer thread name.
     *
     * @return internal timer thread name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets internal timer thread name.
     *
     * @param name
     *            internal timer thread name
     * @return this timer
     */
    public WebTimer setName(final String name) {
        this.name = name;
        if (exec != null) {
            exec.setName(name);
        }
        return this;
    }
    
    /**
     * Returns time passed in milliseconds since curent cycle start. Cycle
     * includes its delay time and execution time.
     *
     * @return time passed in milliseconds since curent cycle start
     */
    public long getCycleTimePassed() {
        return System.currentTimeMillis() - sleepStart;
    }
    
    /**
     * Returns time left in milliseconds until current cycle action exection.
     *
     * @return time left in milliseconds until current cycle action exection
     */
    public long getCycleTimeLeft() {
        return sleepTime - getCycleTimePassed();
    }
    
    /**
     * Returns executed cycles count. This number changes only after cycle
     * execution (including action execution).
     *
     * @return executed cycles count
     */
    public int getCycleCount() {
        return cycleCount;
    }
    
    /**
     * Returns current cycle number. This number changes only after cycle
     * execution (including action execution).
     *
     * @return current cycle number
     */
    public int getCycleNumber() {
        return cycleCount + 1;
    }
    
    /**
     * Return whether last cycle exection is ongoing or not.
     *
     * @return true if last cycle exection is ongoing, false otherwise
     */
    public boolean isLastCycle() {
        return getCyclesLimit() > 0 && getCycleNumber() == getCyclesLimit();
    }
    
    /**
     * Starts timer execution.
     *
     * @return this timer
     */
    public WebTimer start() {
        startExec();
        return this;
    }
    
    /**
     * Stops timer execution.
     *
     * @return this timer
     */
    public WebTimer stop() {
        stopExec();
        return this;
    }
    
    /**
     * Restarts timer execution.
     *
     * @return this timer
     */
    public WebTimer restart() {
        stopExec();
        startExec();
        return this;
    }
    
    /**
     * Restarts timer execution and modifies timer delay.
     *
     * @param delay
     *            delay between timer cycles
     * @return this timer
     */
    public WebTimer restart(final long delay) {
        stopExec();
        setInitialDelay(delay);
        setDelay(delay);
        startExec();
        return this;
    }
    
    /**
     * Restarts timer execution and modifies timer delays.
     *
     * @param initialDelay
     *            delay before the first timer cycle run
     * @param delay
     *            delay between timer cycles
     * @return this timer
     */
    public WebTimer restart(final long initialDelay, final long delay) {
        stopExec();
        setInitialDelay(initialDelay);
        setDelay(delay);
        startExec();
        return this;
    }
    
    /**
     * Restarts timer execution and modifies timer delay.
     *
     * @param delay
     *            delay between timer cycles
     * @return this timer
     */
    public WebTimer restart(final String delay) {
        stopExec();
        setInitialDelay(delay);
        setDelay(delay);
        startExec();
        return this;
    }
    
    /**
     * Restarts timer execution and modifies timer delays.
     *
     * @param initialDelay
     *            delay before the first timer cycle run
     * @param delay
     *            delay between timer cycles
     * @return this timer
     */
    public WebTimer restart(final String initialDelay, final String delay) {
        stopExec();
        setInitialDelay(initialDelay);
        setDelay(delay);
        startExec();
        return this;
    }
    
    /**
     * Returns whether this timer is running or not.
     *
     * @return true if this timer is running, false otherwise
     */
    public synchronized boolean isRunning() {
        return exec != null && exec.isAlive();
    }
    
    /**
     * Starts timer execution thread.
     */
    protected synchronized void startExec() {
        // Ignore if timer is already running
        if (isRunning()) {
            return;
        }
        
        // Saving current thread unique id
        lastId = id;
        id++;
        
        // Starting new cycling thread
        final int currentId = lastId;
        exec = new Thread(new Runnable() {
            @Override
            public void run() {
                // Adding a live thread into map
                setAlive(currentId, true);
                
                try {
                    // Initial delay
                    final long actualInitialDelay = getInitialDelay() < 0 ? getDelay()
                            : getInitialDelay();
                    if (actualInitialDelay > 0) {
                        sleepStart = System.currentTimeMillis();
                        sleepTime = actualInitialDelay;
                        Thread.sleep(actualInitialDelay);
                    }
                    
                    // Checking if we sould stop execution
                    if (shouldContinue(-1, currentId)) {
                        // Clearing cycles count
                        cycleCount = 0;
                        
                        // Starting cycles execution
                        if (repeats) {
                            // Repeated events
                            while (shouldContinue(cycleCount, currentId)) {
                                // Firing events
                                fireActionPerformed();
                                
                                // Incrementing cycles count
                                cycleCount++;
                                
                                // Checking if we sould stop execution due to
                                // changes through events
                                if (!shouldContinue(cycleCount, currentId)) {
                                    break;
                                }
                                
                                // Waiting for next execution
                                if (getDelay() > 0) {
                                    final long currentDelay = getDelay();
                                    sleepStart = System.currentTimeMillis();
                                    sleepTime = currentDelay;
                                    Thread.sleep(currentDelay);
                                }
                            }
                        } else {
                            // Single event
                            fireActionPerformed();
                            
                            // Incrementing cycles count
                            cycleCount++;
                        }
                    }
                } catch (final InterruptedException e) {
                    // Execution interrupted
                }
                
                // Removing finished thread from map
                cleanUp(currentId);
            }
        }, name);
        exec.setDaemon(useDaemonThread);
        exec.start();
    }
    
    /**
     * Returns whether thread with specified ID should continue execution or
     * not.
     *
     * @param cycle
     *            cycle number
     * @param id
     *            thread ID
     * @return true if thread with specified ID should continue execution, false
     *         otherwise
     */
    protected boolean shouldContinue(final int cycle, final int id) {
        return running.get(id) && !Thread.currentThread().isInterrupted()
                && (cyclesLimit <= 0 || cyclesLimit > cycle);
    }
    
    /**
     * Sets whether thread under specified ID is alive or not.
     *
     * @param id
     *            thread ID
     * @param alive
     *            whether thread is alive or not
     */
    protected void setAlive(final int id, final boolean alive) {
        running.put(id, alive);
    }
    
    /**
     * Cleans thread ID cache.
     *
     * @param id
     *            thread ID
     */
    protected void cleanUp(final int id) {
        running.remove(id);
    }
    
    /**
     * Stops timer execution.
     */
    protected synchronized void stopExec() {
        if (exec != null) {
            // Interrupt thread
            exec.interrupt();
            
            // Stop execution from inside
            setAlive(lastId, false);
            
            // Wait for execution to stop
            try {
                exec.join();
            } catch (final InterruptedException e) {
                FlatLafLogger.error(this, e);
            }
        }
    }
    
    /**
     * Adds new action listener. You can use TimerActionListener instead of
     * simple ActionListener to simplify interaction with timer.
     *
     * @param listener
     *            new action listener
     * @return this timer
     */
    public WebTimer addActionListener(final ActionListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
        return this;
    }
    
    /**
     * Removes an action listener.
     *
     * @param listener
     *            action listener
     * @return this timer
     */
    public WebTimer removeActionListener(final ActionListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
        return this;
    }
    
    /**
     * Returns available action listeners list.
     *
     * @return available action listeners list
     */
    public List<ActionListener> getListeners() {
        return listeners;
    }
    
    /**
     * Fires action events.
     */
    public void fireActionPerformed() {
        if (listeners.size() > 0) {
            // Event
            final ActionEvent actionEvent = createActionEvent();
            
            // Working with local array
            final List<ActionListener> listenerList = CollectionUtils
                    .copy(listeners);
            
            // Dispatch event in chosen way
            if (useEventDispatchThread) {
                if (coalesce) {
                    // Merge all events into single call to event dispatch
                    // thread
                    invokeAndWaitSafely(new Runnable() {
                        @Override
                        public void run() {
                            for (final ActionListener listener : listenerList) {
                                listener.actionPerformed(actionEvent);
                            }
                        }
                    });
                } else {
                    // Make separate event calls to event dispatch thread
                    for (final ActionListener listener : listenerList) {
                        invokeAndWaitSafely(new Runnable() {
                            @Override
                            public void run() {
                                listener.actionPerformed(actionEvent);
                            }
                        });
                    }
                }
            } else {
                // Execute events in the same thread with timer
                for (final ActionListener listener : listenerList) {
                    listener.actionPerformed(actionEvent);
                }
            }
        }
    }
    
    /**
     * Returns action event.
     *
     * @return action event
     */
    protected ActionEvent createActionEvent() {
        return new ActionEvent(WebTimer.this, 0, actionCommand,
                TimeUtils.currentTime(), 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name + ", delay (" + getStringDelay() + "), initialDelay ("
                + getInitialStringDelay() + ")";
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer delay(final String delay,
            final ActionListener listener) {
        return delay(parseDelay(delay), listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer delay(final long delay, final ActionListener listener) {
        return delay(defaultThreadName, delay, listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer delay(final String name, final String delay,
            final ActionListener listener) {
        return delay(name, parseDelay(delay), listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer delay(final String name, final long delay,
            final ActionListener listener) {
        return delay(name, delay, true, listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer delay(final String delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return delay(parseDelay(delay), useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer delay(final long delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return delay(defaultThreadName, delay, useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer delay(final String name, final String delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return delay(name, parseDelay(delay), useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that doesn't repeat and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer delay(final String name, final long delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        final WebTimer once = new WebTimer(name, delay, listener);
        once.setRepeats(false);
        once.setUseEventDispatchThread(useEventDispatchThread);
        once.start();
        return once;
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String delay,
            final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(defaultThreadName, pd, pd, defaultCyclesLimit,
                useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String delay, final int cyclesLimit,
            final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(defaultThreadName, pd, pd, cyclesLimit, useEdtByDefault,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay,
            final ActionListener listener) {
        return repeat(defaultThreadName, delay, delay, defaultCyclesLimit,
                useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay, final int cyclesLimit,
            final ActionListener listener) {
        return repeat(defaultThreadName, delay, delay, cyclesLimit,
                useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String name, final String delay,
            final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(name, pd, pd, defaultCyclesLimit, useEdtByDefault,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String name, final String delay,
            final int cyclesLimit, final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(name, pd, pd, cyclesLimit, useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final ActionListener listener) {
        return repeat(name, delay, delay, defaultCyclesLimit, useEdtByDefault,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final int cyclesLimit, final ActionListener listener) {
        return repeat(name, delay, delay, cyclesLimit, useEdtByDefault,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(defaultThreadName, pd, pd, defaultCyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String delay, final int cyclesLimit,
            final boolean useEventDispatchThread, final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(defaultThreadName, pd, pd, cyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return repeat(defaultThreadName, delay, delay, defaultCyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay, final int cyclesLimit,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return repeat(defaultThreadName, delay, delay, cyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String name, final String delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(name, pd, pd, defaultCyclesLimit, useEventDispatchThread,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     * @see #parseDelay(String)
     */
    public static WebTimer repeat(final String name, final String delay,
            final int cyclesLimit, final boolean useEventDispatchThread,
            final ActionListener listener) {
        final long pd = parseDelay(delay);
        return repeat(name, pd, pd, cyclesLimit, useEventDispatchThread,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return repeat(name, delay, delay, defaultCyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final int cyclesLimit, final boolean useEventDispatchThread,
            final ActionListener listener) {
        return repeat(name, delay, delay, cyclesLimit, useEventDispatchThread,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay, final long initialDelay,
            final ActionListener listener) {
        return repeat(defaultThreadName, delay, initialDelay,
                defaultCyclesLimit, useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay, final long initialDelay,
            final int cyclesLimit, final ActionListener listener) {
        return repeat(defaultThreadName, delay, initialDelay, cyclesLimit,
                useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final long initialDelay, final ActionListener listener) {
        return repeat(name, delay, initialDelay, defaultCyclesLimit,
                useEdtByDefault, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final long initialDelay, final int cyclesLimit,
            final ActionListener listener) {
        return repeat(name, delay, initialDelay, cyclesLimit, useEdtByDefault,
                listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay, final long initialDelay,
            final boolean useEventDispatchThread, final ActionListener listener) {
        return repeat(defaultThreadName, delay, initialDelay,
                defaultCyclesLimit, useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final long delay, final long initialDelay,
            final int cyclesLimit, final boolean useEventDispatchThread,
            final ActionListener listener) {
        return repeat(defaultThreadName, delay, initialDelay, cyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final long initialDelay, final boolean useEventDispatchThread,
            final ActionListener listener) {
        return repeat(name, delay, initialDelay, defaultCyclesLimit,
                useEventDispatchThread, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param useEventDispatchThread
     *            whether actions should be fired from Event Dispatch Thread or
     *            not
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final String name, final long delay,
            final long initialDelay, final int cyclesLimit,
            final boolean useEventDispatchThread, final ActionListener listener) {
        final WebTimer repeat = new WebTimer(name, delay, initialDelay,
                listener);
        repeat.setRepeats(true);
        repeat.setUseEventDispatchThread(useEventDispatchThread);
        repeat.setCyclesLimit(cyclesLimit);
        repeat.start();
        return repeat;
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param useDaemonThread
     *            whether should use daemon thread instead of user one or not
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final boolean useDaemonThread,
            final String name, final long delay, final ActionListener listener) {
        return repeat(useDaemonThread, name, delay, delay, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param useDaemonThread
     *            whether should use daemon thread instead of user one or not
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final boolean useDaemonThread,
            final String name, final long delay, final long initialDelay,
            final ActionListener listener) {
        return repeat(useDaemonThread, name, delay, initialDelay,
                defaultCyclesLimit, listener);
    }
    
    /**
     * Returns newly created and started timer that repeats and has the
     * specified delay, initial delay and action listener.
     *
     * @param useDaemonThread
     *            whether should use daemon thread instead of user one or not
     * @param name
     *            thread name
     * @param delay
     *            delay between timer cycles in milliseconds
     * @param initialDelay
     *            delay before the first timer cycle run in milliseconds
     * @param cyclesLimit
     *            timer cycles execution limit
     * @param listener
     *            action listener
     * @return newly created and started timer
     */
    public static WebTimer repeat(final boolean useDaemonThread,
            final String name, final long delay, final long initialDelay,
            final int cyclesLimit, final ActionListener listener) {
        final WebTimer repeat = new WebTimer(name, delay, initialDelay,
                listener);
        repeat.setRepeats(true);
        repeat.setUseDaemonThread(useDaemonThread);
        repeat.setCyclesLimit(cyclesLimit);
        repeat.start();
        return repeat;
    }
    
    /**
     * Either returns delay retrieved from string or throws an exception if it
     * cannot be parsed. Full string format is "Xd Yh Zm s ms" but you can skip
     * any part of it. Yet you must specify atleast one value. For example
     * string "2h 5s" will be a valid delay declaration and will be converted
     * into (2*60*60*1000+5*1000) long value.
     *
     * @param delay
     *            string delay
     * @return delay retrieved from string
     */
    public static long parseDelay(final String delay)
            throws DelayFormatException {
        try {
            long summ = 0;
            final String[] parts = delay.split(" ");
            for (final String part : parts) {
                for (int i = 0; i < part.length(); i++) {
                    if (!Character.isDigit(part.charAt(i))) {
                        final int time = Integer.parseInt(part.substring(0, i));
                        final PartType type = PartType.valueOf(part
                                .substring(i));
                        switch (type) {
                        case d:
                            summ += time * msInDay;
                            break;
                        case h:
                            summ += time * msInHour;
                            break;
                        case m:
                            summ += time * msInMinute;
                            break;
                        case s:
                            summ += time * msInSecond;
                            break;
                        case ms:
                            summ += time;
                            break;
                        }
                        break;
                    }
                }
            }
            return summ;
        } catch (final Throwable e) {
            throw new DelayFormatException(e);
        }
    }
    
    /**
     * Returns delay string representation.
     *
     * @param delay
     *            delay to process
     * @return delay string representation
     */
    public static String toStringDelay(final long delay) {
        if (delay <= 0) {
            throw new IllegalArgumentException("Invalid delay: " + delay);
        }
        
        long time = delay;
        
        final long d = time / msInDay;
        time = time - d * msInDay;
        
        final long h = time / msInHour;
        time = time - h * msInHour;
        
        final long m = time / msInMinute;
        time = time - m * msInMinute;
        
        final long s = time / msInSecond;
        time = time - s * msInSecond;
        
        final long ms = time;
        
        final String stringDelay = (d > 0 ? d + "d " : "")
                + (h > 0 ? h + "h " : "") + (m > 0 ? m + "m " : "")
                + (s > 0 ? s + "s " : "") + (ms > 0 ? ms + "ms " : "");
        
        return stringDelay.trim();
    }
    
    /**
     * Time part type enumeration used to parse string delay.
     */
    protected static enum PartType {
        d, h, m, s, ms
    }
    
    /**
     * Will invoke the specified action in EDT in case it is called from non-EDT
     * thread. It will also block any exceptions thrown by "invokeAndWait"
     * method.
     *
     * @param runnable
     *            runnable
     */
    private static void invokeAndWaitSafely(final Runnable runnable) {
        try {
            invokeAndWait(runnable);
        } catch (final Throwable e) {
            //
        }
    }
    
    /**
     * Will invoke the specified action in EDT in case it is called from non-EDT
     * thread.
     *
     * @param runnable
     *            runnable
     * @throws InterruptedException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static void invokeAndWait(final Runnable runnable)
            throws InterruptedException, InvocationTargetException {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeAndWait(runnable);
        }
    }
}
