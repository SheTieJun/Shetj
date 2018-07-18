package com.shetj.diyalbume.executors;
import com.shetj.diyalbume.executors.ExecutorsPool.Priority;

/**
	 * 带有优先级的Runnable类型
	 */

	public class PriorityRunnable implements Runnable {
		/*
		 *  任务优先级
		 */
		public final  Priority priority;
		/*
		 *  任务真正执行者
		 */
		private final Runnable runnable;
		/*
		 * 任务唯一标示
		 */
		long SEQ;


		public PriorityRunnable(Priority priority, Runnable runnable) {
			this.priority = priority == null ? Priority.NORMAL : priority;
			this.runnable = runnable;
		}

		@Override
		public final void run() {
			this.runnable.run();
		}
	}