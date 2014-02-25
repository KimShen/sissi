package com.sissi.pipeline.in.iq.bytestreams;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.StreamhostUsed;
import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;
import com.sissi.server.ExchangerPoint;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2014年2月24日
 */
public class Bytestreams2MockProcessor extends ProxyProcessor {

	private final StreamhostUsed streamhostUsed = new StreamhostUsed();

	private final ExchangerContext exchangerContext;

	public Bytestreams2MockProcessor(String jid, ExchangerContext exchangerContex) {
		super();
		this.streamhostUsed.setJid(jid);
		this.exchangerContext = exchangerContex;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Bytestreams.class).mock(this.streamhostUsed).getParent().reply().setType(ProtocolType.RESULT), false, true);
		Exchanger exchanger = this.exchangerContext.leave(protocol.getParent().getId());
		
		try {
			exchanger.write(new InputStreamTransferBuffer(Unpooled.wrappedBuffer(IOUtils.toByteArray(new FileInputStream(protocol.getParent().getId())))));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exchanger.close(ExchangerPoint.TARGET);
		return false;
	}

	private class InputStreamTransferBuffer implements TransferBuffer {

		private ByteBuf buf;

		public InputStreamTransferBuffer(ByteBuf buf) {
			this.buf = buf;
		}

		@Override
		public Object getBuffer() {
			return this.buf;
		}

		@Override
		public TransferBuffer release() {
			return null;
		}
	}
}
