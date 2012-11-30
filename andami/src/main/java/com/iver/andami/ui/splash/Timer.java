package com.iver.andami.ui.splash;

/**
 * This class implements an interval timer. It calls the tick method in the
 * callback interface after a fixed number of milliseconds (indicated by the
 * interval variable). It measures the amount of time spent in the tick method
 * and adjusts for it. To start up a timer with this class, create it with a
 * callback and the number of milliseconds in the interval and then call the
 * start method:
 * 
 * <PRE>
 * 
 * Timer timer = new Timer(this, 2000);
 * // 2 second interval
 * timer.start();
 * 
 * </PRE>
 * 
 * @author Mark Wutka
 */

public class Timer extends Object implements Runnable {

	protected Thread timerThread;

	/** The number of milliseconds in the interval */
	protected long interval;

	/** The callback interface containing the tick method */
	protected TimerCallBack callback;

	public Timer() {
	}

	public Timer(TimerCallBack callback) {
		this.callback = callback;
	}

	public Timer(long interval) {
		this.interval = interval;
	}

	public Timer(TimerCallBack callback, long interval) {
		this.callback = callback;
		this.interval = interval;
	}

	/** returns the number of milliseconds in the interval */
	public long getInterval() {
		return interval;
	}

	/**
	 * sets the number of milliseconds in the interval
	 * 
	 * @param newInterval
	 *            the new number of milliseconds
	 */
	public void setInterval(long newInterval) {
		interval = newInterval;
	}

	/** returns the callback interface */
	public TimerCallBack getCallback() {
		return callback;
	}

	/**
	 * changes the callback interface
	 * 
	 * @param callback
	 *            the new callback
	 */
	public void setCallback(TimerCallBack callback) {
		this.callback = callback;
	}

	/** starts the timer */
	public void start() {
		timerThread = new Thread(this);
		timerThread.start();
	}

	/** stops the timer */
	public void stop() {
		timerThread.interrupt();
		timerThread = null;
	}

	public void run() {
		while (true) {
			// Check the current time
			long startTime = System.currentTimeMillis();

			// If there is a callback, call it
			if (callback != null) {
				callback.tick();
			}

			// Check the time again
			long endTime = System.currentTimeMillis();

			// The amount of time to sleep is the interval minus the time spent
			// in the tick routine
			long sleepTime = interval - (endTime - startTime);

			// If you've passed the next interval, hurry up and call the next
			// tick
			if (sleepTime <= 0)
				continue;

			try {
				Thread.sleep(sleepTime);
			} catch (Exception insomnia) {
			} catch (Throwable insomnia) {
			}
		}
	}
}
