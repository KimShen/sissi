package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.protocol.iq.disco.DiscoFeature;
import com.sissi.protocol.iq.disco.DiscoInfo;
import com.sissi.protocol.iq.disco.Identity;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2014年3月12日
 */
public class DiscoInfoMucProcessor extends ProxyProcessor {

	private final XInput input = new XInput(XFieldType.HIDDEN.toString(), null, XDataType.FORM_TYPE.toString(), "http://jabber.org/protocol/muc#roominfo");

	private final DiscoFeature feature = new DiscoFeature("http://jabber.org/protocol/muc");

	private final Identity identity;

	private final VCardContext vcardContext;

	public DiscoInfoMucProcessor(Identity identity, VCardContext vcardContext) {
		super();
		this.identity = identity;
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		protocol.cast(DiscoInfo.class).setData(this.vcardContext.get(group, new XData().setType(XDataType.RESULT).add(this.input))).add(this.identity.clone().setName(group.user())).add(this.feature);
		context.write(protocol.parent().setType(ProtocolType.RESULT).reply());
		return true;
	}
}
