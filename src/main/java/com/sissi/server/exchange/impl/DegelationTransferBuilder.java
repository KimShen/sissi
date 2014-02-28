package com.sissi.server.exchange.impl;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.iq.si.Si;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.DelegationCallback;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;
import com.sissi.write.TransferBuilder;
import com.sissi.write.TransferParam;

/**
 * @author kim 2014年2月25日
 */
public class DegelationTransferBuilder implements TransferBuilder {

	private final String resoure = this.getClass().getSimpleName();

	private final Log log = LogFactory.getLog(this.getClass());

	private final PersistentElementBox persistentElementBox;

	private final DelegationCallback delegationCallback;

	private final ResourceCounter resourceCounter;

	private final JIDBuilder jidBuilder;

	private final Delegation delegation;

	private final int buffer;

	public DegelationTransferBuilder(PersistentElementBox persistentElementBox, DelegationCallback delegationCallback, ResourceCounter resourceCounter, Delegation delegation, JIDBuilder jidBuilder, int buffer) {
		super();
		this.persistentElementBox = persistentElementBox;
		this.delegationCallback = delegationCallback;
		this.resourceCounter = resourceCounter;
		this.delegation = delegation;
		this.jidBuilder = jidBuilder;
		this.buffer = buffer;
	}

	@Override
	public Transfer build(TransferParam param) {
		return new DelegationTransfer(param.find(TransferParam.KEY_SI, Si.class));
	}

	private class DelegationTransfer implements Transfer {

		private final AtomicLong current = new AtomicLong();

		private final ReentrantLock lock = new ReentrantLock();

		private final Si si;

		private final OutputStream output;

		private byte[] buffer = new byte[DegelationTransferBuilder.this.buffer];

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
				if (this.buffer.length < readable) {
					this.buffer = new byte[(int) (readable * 1.5)];
				}
				buf.readBytes(this.buffer, 0, readable);
				this.output.write(this.buffer, 0, readable);
				this.current.addAndGet(readable);
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
			IOUtils.closeQuietly(this.output);
			DegelationTransferBuilder.this.resourceCounter.decrement(DegelationTransferBuilder.this.resoure);
			DegelationTransferBuilder.this.persistentElementBox.push(this.si);
			DegelationTransferBuilder.this.delegationCallback.callback(DegelationTransferBuilder.this.jidBuilder.build(si.parent().getTo()).asStringWithBare());
		}
	}
}
