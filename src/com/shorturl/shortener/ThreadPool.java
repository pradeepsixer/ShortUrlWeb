/**
 * 
 */
package com.shorturl.shortener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Pradeep Kumar
 *
 */
public class ThreadPool {
	private static Executor exec = null;

	private ThreadPool() {
	}

	static {
		exec = Executors.newFixedThreadPool(1000);
	}

	public static void addTask(Runnable task) {
		exec.execute(task);
	}
}
