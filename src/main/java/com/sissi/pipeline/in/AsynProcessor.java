package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;
import com.sissi.thread.Runner;

/**
 * @author kim 2013年12月2日
 */
public abstract class AsynProcessor extends UtilProcessor {

	private final static Integer THREAD_NUM = 1;

	private Runner runner;

	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.runner.executor(THREAD_NUM, this.doInput(context, protocol));
		return true;
	}

	abstract protected AsynRunnable doInput(JIDContext context, Protocol protocol);

	abstract protected class AsynRunnable implements Runnable {

		protected JIDContext context;

		protected Protocol protocol;

		public AsynRunnable(JIDContext context) {
			super();
			this.context = context;
		}

		public AsynRunnable(JIDContext context, Protocol protocol) {
			this(context);
			this.protocol = protocol;
		}
	}
}
