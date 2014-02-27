package com.sissi.pipeline.in.iq.si;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.protocol.iq.si.Feature;
import com.sissi.protocol.iq.si.Si;
import com.sissi.server.exchange.ExchangerContext;
import com.sissi.write.TransferBuilder;
import com.sissi.write.TransferParam;

/**
 * @author kim 2014年2月24日
 */
public class Si2DelegationProcessor extends ProxyProcessor {

	private final Feature feature = new Feature().setX(new XData().setType(XDataType.SUBMIT).add(new XField().setVar("stream-method").add(new XValue(Bytestreams.XMLNS))));

	private final ExchangerContext exchangerContext;

	private final TransferBuilder transferBuilder;

	private final boolean bare;

	public Si2DelegationProcessor(ExchangerContext exchangerContext, TransferBuilder transferBuilder, boolean bare) {
		super();
		this.exchangerContext = exchangerContext;
		this.transferBuilder = transferBuilder;
		this.bare = bare;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Si si = protocol.cast(Si.class).setFeature(this.feature);
		JID to = super.build(si.parent().getTo());
		this.exchangerContext.join(si.host(context.jid().asString(this.bare), to.asString(this.bare)), this.transferBuilder.build(new SiTransferParam(si, this.bare)));
		context.write(si.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}

	private class SiTransferParam implements TransferParam {

		private final Si si;

		private final boolean bare;

		public SiTransferParam(Si si, boolean bare) {
			super();
			this.si = si;
			this.bare = bare;
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			switch (key) {
			case TransferParam.KEY_SI:
				return clazz.cast(this.si);
			case TransferParam.KEY_BARE:
				return clazz.cast(this.bare);
			}
			return null;
		}
	}
}
