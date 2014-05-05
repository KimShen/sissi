package com.sissi.pipeline.in.presence.status;

import org.apache.commons.lang.time.FastDateFormat;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.offline.History;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * MUC房间推送
 * 
 * @author kim 2014年2月18日
 */
public class PresenceMucRecoverProcessor extends ProxyProcessor {

	private final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZ");

	private final Input proxy;

	private final VCardContext vcardContext;

	public PresenceMucRecoverProcessor(Input proxy, VCardContext vcardContext) {
		super();
		this.proxy = proxy;
		this.vcardContext = vcardContext;
	}

	/*
	 * 推送当前JID存在岗位的房间,并自动指定History
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (!context.onlined()) {
			Presence presence = Presence.muc();
			presence.findField(XMuc.NAME, XMuc.class).history(new History().setSince(this.format.format(Long.valueOf(this.vcardContext.get(context.jid(), VCardContext.FIELD_LOGOUT, String.valueOf(System.currentTimeMillis())).getValue()))));
			for (JID jid : super.iSubscribedWho(context.jid())) {
				this.proxy.input(context, presence.setTo(jid));
			}
		}
		return true;
	}
}
