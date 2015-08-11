package org.jpos.qtest;

import org.jpos.iso.ISOUtil;
import org.jpos.q2.QBeanSupport;

public class QTest2 extends QBeanSupport implements Runnable {
    @Override
    protected void startService() {
        new Thread(this).start();
    }
    public void run () {
		for (int tickCount=0; running (); tickCount++) { 
			log.info ("tock " + tickCount);
			ISOUtil.sleep (cfg.getLong("tickInterval", 1000L));
		} 
	}
}
