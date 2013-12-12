package com.sissi.pipeline.out;

import io.netty.buffer.ByteBuf;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.Output.OutputBuilder;
import com.sissi.protocol.Element;
import com.sissi.write.Writer;
import com.sissi.write.Writer.Transfer;

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
			ByteBuf byteBuffer = this.transfer.allocBuffer();
			BufferedOutputStream output = new BufferedOutputStream(new ByteBufferOutputStream(byteBuffer));
			try {
				NetworkOutputBuilder.this.writer.write(context, node, output);
				output.flush();
				this.transfer.transfer(byteBuffer);
			} catch (IOException e) {
				NetworkOutputBuilder.this.log.error(e);
			} finally {
				IOUtils.closeQuietly(output);
			}
			return false;
		}

		public void close() {
			this.transfer.close();
		}

		private class ByteBufferOutputStream extends OutputStream {

			private final ByteBuf buffer;

			public ByteBufferOutputStream(ByteBuf buffer) {
				super();
				this.buffer = buffer;
			}

			@Override
			public void write(int b) throws IOException {
				this.buffer.writeByte(b);
			}
		}
	}
}
