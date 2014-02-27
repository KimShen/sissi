package com.sissi.server.exchange.impl;

import io.netty.buffer.Unpooled;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.commons.Trace;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.Exchanger;
import com.sissi.server.exchange.ExchangerTerminal;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2014年2月26日
 */
public class FSDelegation implements Delegation {

	private final Log log = LogFactory.getLog(this.getClass());

	private final File dir;

	private final PersistentElementBox persistentElementBox;

	public FSDelegation(File dir, PersistentElementBox persistentElementBox) {
		super();
		this.dir = dir;
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public Delegation read(Exchanger exchanger) {
		@SuppressWarnings("unchecked")
		String sid = this.persistentElementBox.peek(BasicDBObjectBuilder.start(PersistentElementBox.fieldHost, exchanger.host()).get().toMap(), BasicDBObjectBuilder.start(PersistentElementBox.fieldActivate, false).get().toMap()).get(PersistentElementBox.fieldSid).toString();
		ByteBufTransferBuffer bbt = new ByteBufTransferBuffer(exchanger, sid);
		return this;
	}

	@Override
	public OutputStream write(String host) {
		try {
			return new BufferedOutputStream(new FileOutputStream(new File(this.dir, host)));
		} catch (FileNotFoundException e) {
			this.log.equals(e.toString());
			Trace.trace(this.log, e);
			throw new RuntimeException(e);
		}
	}

	private class ByteBufTransferBuffer implements TransferBuffer {

		private final InputStream input;

		private Exchanger exchanger;

		private byte[] bytes = new byte[100];

		private int offset;

		public ByteBufTransferBuffer(Exchanger exchanger, String sid) {
			super();
			this.exchanger = exchanger;
			try {
				this.input = new FileInputStream(new File(FSDelegation.this.dir, sid));
				do {
					offset = this.input.read(this.bytes);
					if (offset != -1) {
						this.exchanger.write(this);
					}
				} while (offset != -1);
				this.input.close();
			} catch (IOException e) {
				FSDelegation.this.log.equals(e.toString());
				Trace.trace(FSDelegation.this.log, e);
				throw new RuntimeException(e);
			}
		}

		@Override
		public Object getBuffer() {
			return Unpooled.wrappedBuffer(this.bytes, 0, this.offset);
		}

		@Override
		public TransferBuffer release() {
			System.out.println("realease");
			// IOUtils.closeQuietly(this.input);
			// exchanger.close(ExchangerTerminal.TARGET);
			return this;
		}
	}
}
