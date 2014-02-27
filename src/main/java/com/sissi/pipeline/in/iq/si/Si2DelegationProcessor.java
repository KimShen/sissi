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

	private final String delegation;

	public Si2DelegationProcessor(ExchangerContext exchangerContext, TransferBuilder transferBuilder, String delegation) {
		super();
		this.exchangerContext = exchangerContext;
		this.transferBuilder = transferBuilder;
		this.delegation = delegation;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Si si = protocol.cast(Si.class);
		JID to = super.build(si.parent().getTo());
		//TODO
		this.exchangerContext.join(si.host(context.jid().asStringWithBare(), to.asStringWithBare()), this.transferBuilder.build(new SiTransferParam(si, this.delegation, to.asStringWithBare())));
		protocol.cast(Si.class).set(Feature.NAME, this.feature);
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return false;
	}

	private class SiTransferParam implements TransferParam {

		private final Si si;

		private final String from;

		private final String to;

		public SiTransferParam(Si si, String from, String to) {
			super();
			this.si = si;
			this.from = from;
			this.to = to;
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			switch (key) {
			case TransferParam.KEY_SI:
				return clazz.cast(this.si);
			case TransferParam.KEY_FROM:
				return clazz.cast(this.from);
			case TransferParam.KEY_TO:
				return clazz.cast(this.to);
			}
			return null;
		}
	}
}
