package org.jpos.qtest;

import org.jpos.q2.QBean;

public interface QTestMBean extends QBean {
    public void setTickInterval(long tickInterval);
    public long getTickInterval();
}
