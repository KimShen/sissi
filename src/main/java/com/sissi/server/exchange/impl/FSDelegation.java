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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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

	private final String resoureTransfer = this.getClass().getSimpleName() + ".Transfer";

	private final String resoureChunk = this.getClass().getSimpleName() + ".Chunk";

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
			this.log.warn(e.toString());
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
			this.log.warn(e.toString());
			Trace.trace(this.log, e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(buffer);
		}
	}

	private class ByteTransferBuffer implements TransferBuffer, Closeable, Iterator<ByteTransferBuffer> {

		private final BlockingQueue<ByteBuf> queue = new LinkedBlockingQueue<ByteBuf>();

		private final AtomicLong current = new AtomicLong();

		private final AtomicLong readable = new AtomicLong();

		private final InputStream input;

		private final long total;

		private ByteBuf byteBuf;

		public ByteTransferBuffer(InputStream input, long total) {
			super();
			this.input = input;
			this.total = total;
			FSDelegation.this.resourceCounter.increment(FSDelegation.this.resoureTransfer);
		}

		@Override
		public Object getBuffer() {
			return this.byteBuf;
		}

		@Override
		public boolean hasNext() {
			return this.readable.get() != -1 && this.current.get() < this.total;
		}

		@Override
		public ByteTransferBuffer next() {
			try {
				ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer(FSDelegation.this.buffer);
				readable.set(byteBuf.writeBytes(this.input, byteBuf.capacity()));
				if (readable.get() > 0) {
					this.current.addAndGet(readable.get());
				}
				this.byteBuf = byteBuf;
				this.queue.add(this.byteBuf);
				FSDelegation.this.resourceCounter.increment(FSDelegation.this.resoureChunk);
				return this;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void remove() {
		}

		@Override
		public TransferBuffer release() {
			try {
				ByteBuf byteBuf = this.queue.take();
				if (byteBuf.refCnt() > 0) {
					ReferenceCountUtil.release(byteBuf);
				}
			} catch (Exception e) {
				FSDelegation.this.log.warn("Find leak bytebuf");
				Trace.trace(FSDelegation.this.log, e);
			} finally {
				FSDelegation.this.resourceCounter.decrement(FSDelegation.this.resoureChunk);
			}
			return this;
		}

		@Override
		public void close() throws IOException {
			this.readable.set(-1);
			IOUtils.closeQuietly(this.input);
			FSDelegation.this.resourceCounter.decrement(FSDelegation.this.resoureTransfer);
		}
	}
}
