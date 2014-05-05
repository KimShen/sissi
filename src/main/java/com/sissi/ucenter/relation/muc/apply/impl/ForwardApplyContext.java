package com.sissi.ucenter.relation.muc.apply.impl;

import com.sissi.context.JID;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.apply.ApplySupport;
import com.sissi.ucenter.relation.muc.apply.RequestConfig;

/**
 * 请求转发, Support永远返回True</p>Sample: <message to="last@conference.sissi.pw" xmlns:stream="http://etherx.jabber.org/streams"><x type="submit" xmlns="jabber:x:data"><field type="hidden" var="FORM_TYPE"><value>http://jabber.org/protocol/muc#request</value></field><field label="Requested role" type="text-single" var="muc#role"><value>participant</value></field></x></message>
 * 
 * @author kim 2014年5月5日
 */
public class ForwardApplyContext implements ApplySupport {

	private final XInput jid = new XInput(XFieldType.TEXT_SINGLE.toString(), "JID", RequestConfig.JID.toString());

	private final XInput nick = new XInput(XFieldType.TEXT_SINGLE.toString(), "Nickname", RequestConfig.ROOMNICK.toString());

	private final XInput allow = new XInput(XFieldType.BOOLEAN.toString(), "Allow", RequestConfig.REQUEST_ALLOW.toString(), Boolean.FALSE.toString());

	private final MucRelationContext relationContext;

	public ForwardApplyContext(MucRelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public boolean apply(JID invoker, JID group, Fields fields) {
		fields.add(this.jid.clone().value(invoker.asString())).add(this.nick.clone().value(this.relationContext.ourRelation(invoker, group).name())).add(this.allow);
		fields.findField(XDataType.FORM_TYPE.toString(), XField.class).type(XFieldType.HIDDEN);
		return true;
	}

	/*
	 * Support永远返回True
	 * 
	 * @see com.sissi.ucenter.relation.muc.request.RequestSupport#support(com.sissi.field.Fields)
	 */
	@Override
	public boolean support(Fields fields) {
		return true;
	}
}
