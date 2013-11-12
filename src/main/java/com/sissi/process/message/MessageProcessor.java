package com.sissi.process.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.context.Context;
import com.sissi.context.JID;
import com.sissi.context.user.User;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-10-24
 */
public class MessageProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	private Addressing addressing;

	private RelationContext relationContext;

	public MessageProcessor(Addressing addressing, RelationContext relationContext) {
		super();
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		if (!this.relationContext.weAreFriends(new User(protocol.getTo()), context.jid())) {
			this.log.warn("We are not friends: " + context.jid().asStringWithNaked() + " --> " + protocol.getTo());
			return;
		}
		Message message = Message.class.cast(protocol);
		if (message.hasContent()) {
			this.processTextMessage(context, message);
		}
	}

	private void processTextMessage(Context context, Message message) {
		message.setFrom(context.jid().asString());
		JID to = new User(message.getTo());
		if (this.addressing.online(to)) {
			this.addressing.find(to).write(message);
		}
	}
}
