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
import com.sissi.config.Dictionary;
import com.sissi.config.impl.MongoUtils;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.TransferBuffer;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.Exchanger;

/**
 * 基于文件系统的离线文件代理. 索引策略:{"host":1}
 * 
 * @author kim 2014年2月26日
 */
public class FSDelegation implements Delegation {

	private final String transfer = this.getClass().getSimpleName() + "_transfer";

	private final String chunk = this.getClass().getSimpleName() + "_chunk";

	private final Log log = LogFactory.getLog(this.getClass());

	private final File dir;

	private final int buffer;

	private final Persistent persistent;

	private final ResourceCounter resourceCounter;

	/**
	 * @param dir
	 * @param buffer Push缓冲区大小
	 * @param resourceCounter
	 * @param persistent
	 */
	public FSDelegation(File dir, int buffer, ResourceCounter resourceCounter, Persistent persistent) {
		super();
		this.resourceCounter = resourceCounter;
		this.persistent = persistent;
		this.buffer = buffer;
		this.dir = this.mkdir(dir);
	}

	private File mkdir(File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/*
	 * BufferedOutputStream
	 * 
	 * @see com.sissi.server.exchange.Delegation#allocate(java.lang.String)
	 */
	@Override
	public OutputStream allocate(String sid) {
		try {
			return new BufferedOutputStream(new FileOutputStream(new File(this.dir, sid)));
		} catch (Exception e) {
			this.log.warn(e.toString());
			Trace.trace(this.log, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Delegation push(Exchanger exchanger) {
		// {"host":Xxx}
		Map<String, Object> peek = this.persistent.peek(MongoUtils.asMap(BasicDBObjectBuilder.start(Dictionary.FIELD_HOST, exchanger.host()).get()));
		ByteTransferBuffer buffer = null;
		try {
			buffer = new ByteTransferBuffer(new BufferedInputStream(new FileInputStream(new File(FSDelegation.this.dir, peek.get(Dictionary.FIELD_SID).toString()))), Long.valueOf(peek.get(Dictionary.FIELD_SIZE).toString())).write(exchanger);
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

		/**
		 * 已经读取字节
		 */
		private final AtomicLong readable = new AtomicLong();

		/**
		 * 本次读取字节
		 */
		private final AtomicLong current = new AtomicLong();

		private final InputStream input;

		private final long total;

		private ByteBuf byteBuf;

		/**
		 * @param input
		 * @param total Si长度
		 */
		public ByteTransferBuffer(InputStream input, long total) {
			super();
			this.input = input;
			this.total = total;
			FSDelegation.this.resourceCounter.increment(FSDelegation.this.transfer);
		}

		/**
		 * 写入Exchanger
		 * 
		 * @param exchanger
		 * @return
		 */
		public ByteTransferBuffer write(Exchanger exchanger) {
			while (this.hasNext()) {
				exchanger.write(this.next());
			}
			return this;
		}

		@Override
		public Object getBuffer() {
			return this.byteBuf;
		}

		/**
		 * 流读至末尾或已读取超出Si长度
		 * 
		 * @return
		 */
		@Override
		public boolean hasNext() {
			return this.current.get() != -1 && this.readable.get() < this.total;
		}

		@Override
		public ByteTransferBuffer next() {
			try {
				ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer(FSDelegation.this.buffer);
				if (this.set(byteBuf.writeBytes(this.input, byteBuf.capacity())).get() > 0) {
					this.readable.addAndGet(current.get());
				}
				this.queue.add(this.byteBuf = byteBuf);
				FSDelegation.this.resourceCounter.increment(FSDelegation.this.chunk);
				return this;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private AtomicLong set(long current) {
			this.current.set(current);
			return this.current;
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
				FSDelegation.this.log.warn(e.toString());
				Trace.trace(FSDelegation.this.log, e);
			} finally {
				this.notify();
				FSDelegation.this.resourceCounter.decrement(FSDelegation.this.chunk);
			}
			return this;
		}

		@Override
		public void close() throws IOException {
			this.current.set(-1);
			IOUtils.closeQuietly(this.input);
			FSDelegation.this.resourceCounter.decrement(FSDelegation.this.transfer);
		}
	}
}
