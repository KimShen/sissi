package com.sissi.server.exchange.impl;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtil;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.Transfer;
import com.sissi.pipeline.TransferBuffer;
import com.sissi.pipeline.TransferBuilder;
import com.sissi.pipeline.TransferParam;
import com.sissi.protocol.iq.si.Si;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.Recall;

/**
 * 离线代理传输
 * 
 * @author kim 2014年2月25日
 */
public class DegelationTransferBuilder implements TransferBuilder {

	private final String resoure = this.getClass().getSimpleName();

	private final Log log = LogFactory.getLog(this.getClass());

	private final ResourceCounter resourceCounter;

	private final Persistent persistent;

	private final JIDBuilder jidBuilder;

	private final Delegation delegation;

	private final Recall recall;

	public DegelationTransferBuilder(Persistent persistent, ResourceCounter resourceCounter, Delegation delegation, JIDBuilder jidBuilder, Recall recall) {
		super();
		this.resourceCounter = resourceCounter;
		this.persistent = persistent;
		this.delegation = delegation;
		this.jidBuilder = jidBuilder;
		this.recall = recall;
	}

	@Override
	public DelegationTransfer build(TransferParam param) {
		return new DelegationTransfer(param.find(TransferParam.KEY_SI, Si.class));
	}

	private class DelegationTransfer implements Transfer {

		/**
		 * 已读字节
		 */
		private final AtomicLong readable = new AtomicLong();

		/**
		 * 有序锁
		 */
		private final Lock lock = new ReentrantLock(true);

		private final OutputStream output;

		private final Si si;

		public DelegationTransfer(Si si) {
			super();
			this.si = si;
			this.output = DegelationTransferBuilder.this.delegation.allocate(si.getId());
			DegelationTransferBuilder.this.resourceCounter.increment(DegelationTransferBuilder.this.resoure);
		}

		@Override
		public DelegationTransfer transfer(TransferBuffer buffer) {
			ByteBuf buf = ByteBuf.class.cast(buffer.getBuffer());
			try {
				this.lock.lock();
				int readable = buf.readableBytes();
				buf.readBytes(this.output, readable);
				this.readable.addAndGet(readable);
				return this;
			} catch (Exception e) {
				DegelationTransferBuilder.this.log.error(e);
				Trace.trace(DegelationTransferBuilder.this.log, e);
				throw new RuntimeException(e);
			} finally {
				this.lock.unlock();
				if (buf.refCnt() > 0) {
					ReferenceCountUtil.release(buf);
				}
			}
		}

		@Override
		public void close() {
			IOUtil.closeQuietly(this.output);
			DegelationTransferBuilder.this.persistent.push(this.si);
			DegelationTransferBuilder.this.resourceCounter.decrement(DegelationTransferBuilder.this.resoure);
			DegelationTransferBuilder.this.recall.call(DegelationTransferBuilder.this.jidBuilder.build(si.parent().getTo()).asStringWithBare());
		}
	}
}
