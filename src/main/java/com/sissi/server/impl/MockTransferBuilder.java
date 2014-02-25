package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import com.sissi.context.JID;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.iq.si.Si;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;
import com.sissi.write.TransferBuilder;
import com.sissi.write.TransferParam;

/**
 * @author kim 2014年2月25日
 */
public class MockTransferBuilder implements TransferBuilder {

	private final PersistentElementBox persistentElementBox;

	public MockTransferBuilder(PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public Transfer build(TransferParam param) {
		return new MockTransfer(param.find(TransferParam.KEY_JID, JID.class), param.find(TransferParam.KEY_SI, Si.class), param.find(TransferParam.KEY_FROM, String.class), param.find(TransferParam.KEY_TO, String.class));
	}

	private class MockTransfer implements Transfer {

		private final AtomicLong current = new AtomicLong();

		private final Si si;

		private final long total;

		private final OutputStream output;

		public MockTransfer(JID jid, Si si, String from, String to) {
			super();
			this.si = si;
			this.total = Long.valueOf(si.getFile().getSize());
			try {
				this.output = new FileOutputStream(si.host(from, to));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public Transfer transfer(TransferBuffer buffer) {
			ByteBuf buf = ByteBuf.class.cast(buffer.getBuffer());
			try {
				current.addAndGet(buf.readableBytes());
				byte[] bytes = new byte[buf.readableBytes()];
				buf.readBytes(bytes);
				output.write(bytes);
				if (this.current.get() == total) {
					this.close();
				}
				return this;
			} catch (Exception e) {
				e.printStackTrace();
				return this;
			} finally {
				buffer.release();
			}
		}

		@Override
		public void close() {
			try {
				output.close();
				MockTransferBuilder.this.persistentElementBox.push(this.si);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
