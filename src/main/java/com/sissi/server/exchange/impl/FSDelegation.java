package com.sissi.server.exchange.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.Exchanger;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2014年2月26日
 */
public class FSDelegation implements Delegation {

	private final String resoure = this.getClass().getSimpleName();

	private final Log log = LogFactory.getLog(this.getClass());

	private final File dir;

	private final int buffer;

	private final ResourceCounter resourceCounter;

	private final PersistentElementBox persistentElementBox;

	public FSDelegation(File dir, int buffer, ResourceCounter resourceCounter, PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
		this.resourceCounter = resourceCounter;
		this.buffer = buffer;
		this.dir = this.mkdir(dir);
	}

	private File mkdir(File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	@Override
	public OutputStream allocate(String host) {
		try {
			return new BufferedOutputStream(new FileOutputStream(new File(this.dir, host)));
		} catch (Exception e) {
			this.log.equals(e.toString());
			Trace.trace(this.log, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Delegation recover(Exchanger exchanger) {
		Map<String, Object> peek = this.persistentElementBox.peek(BasicDBObjectBuilder.start(PersistentElementBox.fieldHost, exchanger.host()).get().toMap());
		ByteTransferBuffer buffer = null;
		try {
			buffer = new ByteTransferBuffer(new BufferedInputStream(new FileInputStream(new File(FSDelegation.this.dir, peek.get(PersistentElementBox.fieldSid).toString()))), Long.valueOf(peek.get(PersistentElementBox.fieldSize).toString()));
			while (buffer.hasNext()) {
				exchanger.write(buffer.next());
			}
			return this;
		} catch (Exception e) {
			this.log.equals(e.toString());
			Trace.trace(this.log, e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(buffer);
		}
	}

	private class ByteTransferBuffer implements TransferBuffer, Closeable, Iterator<TransferBuffer> {

		private byte[] buffer = new byte[FSDelegation.this.buffer];

		private final AtomicLong current = new AtomicLong();

		private final InputStream input;

		private ByteBuf byteBuf;

		private final long total;

		public ByteTransferBuffer(InputStream input, long total) {
			super();
			this.input = input;
			this.total = total;
			FSDelegation.this.resourceCounter.increment(FSDelegation.this.resoure);
		}

		@Override
		public Object getBuffer() {
			return this.byteBuf;
		}

		@Override
		public boolean hasNext() {
			return this.current.get() < this.total;
		}

		@Override
		public TransferBuffer next() {
			try {
				this.byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
				int readable = this.input.read(this.buffer);
				if (readable != -1) {
					this.byteBuf.writeBytes(this.buffer, 0, readable);
					this.current.addAndGet(readable);
				}
				return this;
			} catch (IOException e) {
				FSDelegation.this.log.error(e.toString());
				Trace.trace(FSDelegation.this.log, e);
				throw new RuntimeException(e);
			}
		}

		@Override
		public void remove() {
		}

		@Override
		public TransferBuffer release() {
			if (this.byteBuf.refCnt() > 0) {
				ReferenceCountUtil.release(this.buffer);
			}
			return this;
		}

		@Override
		public void close() throws IOException {
			this.release();
			this.buffer = null;
			IOUtils.closeQuietly(this.input);
			FSDelegation.this.resourceCounter.decrement(FSDelegation.this.resoure);
		}
	}
}
