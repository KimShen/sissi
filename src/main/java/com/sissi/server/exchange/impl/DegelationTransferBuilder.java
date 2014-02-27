package com.sissi.server.exchange.impl;

import io.netty.buffer.ByteBuf;

import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.context.JID;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.iq.si.Si;
import com.sissi.server.exchange.Delegation;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;
import com.sissi.write.TransferBuilder;
import com.sissi.write.TransferParam;

/**
 * @author kim 2014年2月25日
 */
public class DegelationTransferBuilder implements TransferBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final PersistentElementBox persistentElementBox;

	private final Delegation delegation;

	public DegelationTransferBuilder(PersistentElementBox persistentElementBox, Delegation delegation) {
		super();
		this.persistentElementBox = persistentElementBox;
		this.delegation = delegation;
	}

	@Override
	public Transfer build(TransferParam param) {
		return new DelegationTransfer(param.find(TransferParam.KEY_JID, JID.class), param.find(TransferParam.KEY_SI, Si.class), param.find(TransferParam.KEY_FROM, String.class), param.find(TransferParam.KEY_TO, String.class));
	}

	private class DelegationTransfer implements Transfer {

		private final AtomicLong current = new AtomicLong();

		private final Si si;

		private final OutputStream output;

		public DelegationTransfer(JID jid, Si si, String from, String to) {
			super();
			this.si = si;
			this.output = DegelationTransferBuilder.this.delegation.write(si.getId());
		}

		private Transfer shouldClose() {
			if (this.si.getFile().size(this.current.get())) {
				this.close();
			}
			return this;
		}

		@Override
		public Transfer transfer(TransferBuffer buffer) {
			try {
				ByteBuf buf = ByteBuf.class.cast(buffer.getBuffer());
				this.current.addAndGet(buf.readableBytes());
				byte[] bytes = new byte[buf.readableBytes()];
				buf.readBytes(bytes);
				this.output.write(bytes);
				return this.shouldClose();
			} catch (Exception e) {
				IOUtils.closeQuietly(this.output);
				DegelationTransferBuilder.this.log.error(e);
				Trace.trace(DegelationTransferBuilder.this.log, e);
				throw new RuntimeException(e);
			} finally {
				buffer.release();
			}
		}

		@Override
		public void close() {
			IOUtils.closeQuietly(this.output);
			DegelationTransferBuilder.this.persistentElementBox.push(this.si);
		}
	}
}
