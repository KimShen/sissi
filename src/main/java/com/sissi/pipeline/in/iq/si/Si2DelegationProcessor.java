package com.sissi.pipeline.in.iq.si;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.TransferBuilder;
import com.sissi.pipeline.TransferParam;
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

/**
 * 离线文件接收
 * 
 * @author kim 2014年2月24日
 */
public class Si2DelegationProcessor extends ProxyProcessor {

	private final Feature feature = new Feature().x(new XData().setType(XDataType.SUBMIT).add(new XField().setVar("stream-method").add(new XValue(Bytestreams.XMLNS))));

	private final ExchangerContext exchangerContext;

	private final TransferBuilder transferBuilder;

	private final boolean resource;

	public Si2DelegationProcessor(ExchangerContext exchangerContext, TransferBuilder transferBuilder, boolean resource) {
		super();
		this.exchangerContext = exchangerContext;
		this.transferBuilder = transferBuilder;
		this.resource = resource;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Si si = protocol.cast(Si.class).setFeature(this.feature);
		this.exchangerContext.wait(si.host(context.jid().asString(this.resource), super.build(si.parent().getTo()).asString(this.resource)), true, this.transferBuilder.build(new SiTransferParam(si)));
		context.write(si.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}

	private class SiTransferParam implements TransferParam {

		private final Si si;

		public SiTransferParam(Si si) {
			super();
			this.si = si;
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			switch (key) {
			case TransferParam.KEY_SI:
				return clazz.cast(this.si);
			}
			return null;
		}
	}
}
