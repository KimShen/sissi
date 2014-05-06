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
import com.sissi.protocol.muc.FormType;
import com.sissi.protocol.muc.XMuc;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * <iq type='get' to='MUC JID'><query xmlns='http://jabber.org/protocol/disco#info'/></iq>
 * 
 * @author kim 2014年3月12日
 */
public class DiscoInfoProcessor extends ProxyProcessor {

	private final XInput form = new XInput(XFieldType.HIDDEN.toString(), null, XDataType.FORM_TYPE.toString(), FormType.MUC_ROOMINFO.toString());

	private final DiscoFeature feature = new DiscoFeature(XMuc.XMLNS);
	
	private final VCardContext vcardContext;

	private final Identity identity;

	public DiscoInfoProcessor(Identity identity, VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
		this.identity = identity;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		protocol.cast(DiscoInfo.class).data(this.vcardContext.pull(group, new XData().setType(XDataType.RESULT).add(this.form))).add(this.identity.clone().setName(group.user())).add(this.feature);
		context.write(protocol.parent().setType(ProtocolType.RESULT).reply());
		return true;
	}
}
