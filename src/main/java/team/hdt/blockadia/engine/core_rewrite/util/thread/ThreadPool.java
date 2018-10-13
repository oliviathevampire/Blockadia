package team.hdt.blockadia.engine.core_rewrite.util.thread;

import java.util.LinkedList;
import java.util.List;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * Allows the ability to have multi-threading. Runs multiple tasks at the same time.
 * 
 * @author Ocelot5836
 * @since 1.5.4
 */
public class ThreadPool extends ThreadGroup {

	private static int threadId = 1;

	private List<Runnable> taskQueue;
	private boolean alive;

	/**
	 * @param numThreads
	 *            The number of tasks that can be ran at a single time
	 */
	public ThreadPool(int numThreads) {
		super("ThreadPool-" + threadId++);
		this.setDaemon(true);
		this.taskQueue = new LinkedList<Runnable>();
		this.alive = true;
		for (int i = 0; i < numThreads; i++) {
			new PooledThread(this).start();
		}
	}

	/**
	 * Adds the specified task to the list of tasks to be run.
	 * 
	 * @param task
	 *            The task to run
	 */
	public synchronized void addScheduledTask(Runnable task) {
		if (!alive)
			throw new IllegalStateException(getName() + " is dead");
		if (task != null) {
			taskQueue.add(task);
			notify();
		}
	}

	/**
	 * Closes all threads urgently and discards all thread progress.
	 */
	public synchronized void close() {
		if (!alive)
			return;
		alive = false;
		taskQueue.clear();
		interrupt();
	}
	/**
	 * Speeds all threads up then closes all threads.
	 */
	public void join() {
		synchronized (this) {
			alive = false;
			notifyAll();
		}

		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads);

		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected synchronized Runnable getTask() throws InterruptedException {
		while (taskQueue.size() == 0) {
			if (!alive)
				return null;
			wait();
		}
		return taskQueue.remove(0);
	}
}