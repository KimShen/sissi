package com.sisi.process.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.addressing.Addressing;
import com.sisi.context.Context;
import com.sisi.context.JID;
import com.sisi.context.user.User;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.core.Message;

/**
 * @author kim 2013-10-24
 */
public class MessageProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	private Addressing addressing;

	public MessageProcessor(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Message message = Message.class.cast(protocol);
		this.log.debug("Message before send: " + message);
		if (message.hasContent()) {
			message.setFrom(context.jid().asString());
			JID to = new User(message.getTo());
			this.log.info("Message will be send to: " + to.asStringWithLoop());
			if (this.addressing.isLogin(to)) {
				Context toContext = this.addressing.find(to);
				message.setTo(null);
				this.log.info("Message ( " + message + " ) would be send to: " + toContext.jid().asStringWithLoop());
				toContext.write(message);
			}
		}
		return null;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Message.class.isAssignableFrom(protocol.getClass());
	}

}
