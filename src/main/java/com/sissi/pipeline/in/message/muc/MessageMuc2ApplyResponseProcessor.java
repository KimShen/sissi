package com.sissi.pipeline.in.message.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyResponseProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if ("http://jabber.org/protocol/muc#register".equals(protocol.cast(Message.class).getData().findField(XDataType.FORM_TYPE.toString(), XField.class).getValue())) {
			JID jid = super.build(protocol.cast(Message.class).getData().findField(MongoConfig.FIELD_JID.toString(), XField.class).getValue().toString());
			JID group = super.build(protocol.cast(Message.class).getData().findField(MongoConfig.FIELD_GROUP.toString(), XField.class).getValue().toString());
			boolean allowed = !"0".equals(protocol.cast(Message.class).getData().findField("muc#register_allow", XField.class).getValue().toString());
			//Update and notify and cancel TODO
			if (allowed && ItemAffiliation.MEMBER.contains(super.ourRelation(jid, group).cast(RelationMuc.class).affiliation())) {
				super.update(jid, group, ItemAffiliation.MEMBER.toString());
			}
		}
		return true;
	}
}
