package com.sissi.pipeline.out;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.IOUtils;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.OutputBuilder;
import com.sissi.protocol.Element;
import com.sissi.write.Transfer;
import com.sissi.write.Writer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkOutputBuilder implements OutputBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final Writer writer;

	public NetworkOutputBuilder(Writer writer) {
		super();
		this.writer = writer;
	}

	@Override
	public Output build(Transfer transfer) {
		return new NetworkOutut(transfer);
	}

	private class NetworkOutut implements Output {

		private final Transfer transfer;

		public NetworkOutut(Transfer transfer) {
			super();
			this.transfer = transfer;
		}

		@Override
		public Boolean output(JIDContext context, Element node) {
			ByteBufferOutputStream output = new ByteBufferOutputStream();
			try {
				BufferedOutputStream buf = new BufferedOutputStream(output);
				NetworkOutputBuilder.this.writer.write(context, node, new BufferedOutputStream(output));
				buf.flush();
				this.transfer.transfer(output.getBuffer().nioBuffer());
			} catch (IOException e) {
				NetworkOutputBuilder.this.log.error(e.toString());
			} finally {
				IOUtils.closeQuietly(output);
			}
			return false;
		}

		public Output close() {
			this.transfer.close();
			return this;
		}

		private class ByteBufferOutputStream extends OutputStream {

			private ByteBuf buffer;

			public ByteBufferOutputStream() {
				super();
				this.buffer = Unpooled.buffer();
			}

			@Override
			public void write(int b) throws IOException {
				this.buffer.writeByte(b);
			}

			public ByteBuf getBuffer() {
				return buffer;
			}
		}
	}
}
