package com.sissi.pipeline.in.iq.si;

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
import com.sissi.server.ExchangerContext;
import com.sissi.write.TransferBuilder;
import com.sissi.write.TransferParam;

/**
 * @author kim 2014年2月24日
 */
public class Si2MockProcessor extends ProxyProcessor {

	private final Feature feature = new Feature().setX(new XData().setType(XDataType.SUBMIT));

	private final ExchangerContext exchangerContext;

	private final TransferBuilder transferBuilder;

	public Si2MockProcessor(ExchangerContext exchangerContext, TransferBuilder transferBuilder) {
		super();
		this.exchangerContext = exchangerContext;
		this.transferBuilder = transferBuilder;
		XField xField = new XField().setVar("stream-method");
		xField.set(XValue.NAME, new XValue(Bytestreams.XMLNS));
		this.feature.getX().add(xField);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.getParent().setFrom(context.jid());
		Si si = protocol.cast(Si.class);
		exchangerContext.join(si.host(), this.transferBuilder.build(new SiTransferParam(si, "delegation@sissi.pw/delegation", super.build(si.getParent().getTo()).asStringWithBare())));
		protocol.cast(Si.class).set(Feature.NAME, this.feature);
		context.write(protocol.getParent().reply().setType(ProtocolType.RESULT));
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
