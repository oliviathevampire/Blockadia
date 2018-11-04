package team.hdt.blockadia.old_engine_code_1.core_rewrite.util.thread;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A thread that is run in a thread pool.
 * 
 * @author Ocelot5836
 * @since 1.5.4
 */
class PooledThread extends Thread {

	private static int threadId = 1;

	private ThreadPool pool;

	public PooledThread(ThreadPool pool) {
		super(pool, "PooledThread-" + threadId++);
		this.pool = pool;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			Runnable task = null;
			try {
				task = pool.getTask();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (task == null)
				return;

			try {
				task.run();
			} catch (Throwable t) {
				pool.uncaughtException(this, t);
			}
		}
	}
}