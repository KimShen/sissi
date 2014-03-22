package com.sissi.pipeline.in.message.muc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ChainedFinder;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XValue;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2AllProcessor extends ProxyProcessor implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (protocol.cast(Message.class).getBody().getText().startsWith("setRole")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo())).setType(ProtocolType.SET);
			XMucAdmin xmuc = new XMucAdmin();
			for (char each : params[1].toCharArray()) {
				xmuc.set(null, new Item().nick(each + "").role(params[2]).reason(params[3]));
			}
			iq.set(null, xmuc);
			this.applicationContext.getBean("global.finder", ChainedFinder.class).find(iq).input(context, iq);
		}
		if (protocol.cast(Message.class).getBody().getText().startsWith("getRole")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo())).setType(ProtocolType.GET);
			XMucAdmin xmuc = new XMucAdmin();
			xmuc.set(null, new Item().role(params[1]));
			iq.set(null, xmuc);
			this.applicationContext.getBean("global.finder", ChainedFinder.class).find(iq).input(context, iq);
		}
		if (protocol.cast(Message.class).getBody().getText().startsWith("apply")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			Message message = new Message();
			message.setFrom(context.jid()).setTo("abcd@group.sissi.pw");
			message.set(XData.NAME, new XData().setType(XDataType.SUBMIT).add(new XField().setVar("FORM_TYPE").add(new XValue("http://jabber.org/protocol/muc#request"))).add(new XField().setVar("muc#jid").add(new XValue(params[1]))).add(new XField().setVar("muc#role").setType("text-single").setLabel("Requested role").add(new XValue(params[2]))).add(new XField().setVar("muc#roomnick").add(new XValue(params[3]))).add(new XField().setVar("muc#request_allow").add(new XValue(params[4]))));
			this.applicationContext.getBean("global.finder", ChainedFinder.class).find(message).input(context, message);
		}
		if (protocol.cast(Message.class).getBody().getText().startsWith("setAff")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo())).setType(ProtocolType.SET);
			XMucAdmin xmuc = new XMucAdmin();
			for (char each : params[1].toCharArray()) {
				xmuc.set(null, new Item().jid(super.build(each + "@sissi.pw")).affiliation(params[2]).reason(params[3]));
			}
			iq.set(null, xmuc);
			this.applicationContext.getBean("global.finder", ChainedFinder.class).find(iq).input(context, iq);
		}
		if (protocol.cast(Message.class).getBody().getText().startsWith("getAff")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo())).setType(ProtocolType.GET);
			XMucAdmin xmuc = new XMucAdmin();
			xmuc.set(null, new Item().affiliation(params[1]));
			iq.set(null, xmuc);
			this.applicationContext.getBean("global.finder", ChainedFinder.class).find(iq).input(context, iq);
		}

		JID group = super.build(protocol.getTo());
		protocol.setFrom(group.resource(super.ourRelation(context.jid(), group).name()));
		for (JID jid : super.whoSubscribedMe(group)) {
			super.findOne(jid, true).write(protocol);
		}
		return true;
	}
}
