package com.sissi.pipeline.out;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.apache.IOUtils;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.OutputBuilder;
import com.sissi.protocol.Element;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;
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
			ByteBufOutputTransferBuffer output = new ByteBufOutputTransferBuffer();
			BufferedOutputStream buf = new BufferedOutputStream(output);
			try {
				NetworkOutputBuilder.this.writer.write(context, node, buf);
				this.transfer.transfer(output);
			} catch (IOException e) {
				NetworkOutputBuilder.this.log.error(e.toString());
			} finally {
				IOUtils.closeQuietly(buf);
				IOUtils.closeQuietly(output);
			}
			return false;
		}

		public Output close() {
			this.transfer.close();
			return this;
		}

		private class ByteBufOutputTransferBuffer extends OutputStream implements TransferBuffer {

			private final ByteBuf buffer;

			public ByteBufOutputTransferBuffer() {
				super();
				this.buffer = PooledByteBufAllocator.DEFAULT.buffer();
			}

			@Override
			public void write(int b) throws IOException {
				this.buffer.writeByte(b);
			}

			public ByteBuf getBuffer() {
				return this.buffer;
			}

			@Override
			public TransferBuffer release() {
				if (this.buffer.refCnt() > 0) {
					ReferenceCountUtil.release(this.buffer);
				}
				return this;
			}
		}
	}
}
