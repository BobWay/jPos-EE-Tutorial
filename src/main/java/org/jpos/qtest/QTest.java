package org.jpos.qtest;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.Q2;
import org.jpos.q2.QBean;
import org.jpos.q2.QPersist;
import org.jpos.util.Log;
import org.jdom.Element;

public class QTest implements QTestMBean, Runnable, Configurable, QPersist {
    volatile int state;
    long tickInterval = 1000;
    Log log;
    boolean debug;
    boolean modified = false;
    Q2 server;
    Element e;

    public QTest () {
        super();
        state = -1;
        log = Log.getLog(Q2.LOGGER_NAME, "qtest"); log.info ("constructor");
    }
    public void init () {
        log.info("init");
        state = STARTING;
    }
    public void start() { log.info ("start"); state = STARTED;
        new Thread(this).start();
    }
    public void stop () {
        log.info ("stop");
        state = STOPPING;
    }
    public void destroy () { 
        log.info ("destroy"); 
        state = STOPPED;
    }
    public void setTickInterval (long tickInterval) {
        this.tickInterval = tickInterval;
    }
    public long getTickInterval () {
        return tickInterval;
    }
    public void run () {
        for (int tickCount=0; running (); tickCount++) { 
            log.info ("tick " + tickCount); ISOUtil.sleep (tickInterval);
        }
    }
    public int getState () {
        return state;
    }
    public void setConfiguration (Configuration cfg) {
        debug = cfg.getBoolean("debug", true);
    }
    public String getStateAsString () {
        return state >= 0 ? stateString[state] : "Unknown"; 
    }
    private boolean running() {
        return state == QBean.STARTING || state == QBean.STARTED;
    }
    private void log (String message) {
        if (debug)
            log (message);
    }
    public void setLogger (String loggerName) {
        log = Log.getLog (loggerName, getClass().getName());
        setModified (true);
    }
    public void setRealm (String realm) {
    if (log != null)
        log.setRealm (realm);
    }
    public void setModified (boolean modified) {
        this.modified = modified;
    }
    public void setServer (Q2 server) {
        this.server = server;
    }
    public boolean isModified () {
        return this.modified;
    }
    public void setPersist (Element e) {
        this.e = e;
    }
    public Element getPersist () {
        return this.e;
    }
}
