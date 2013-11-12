package com.sissi.process.message;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-11-4
 */
public class MessageMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return Message.class.isAssignableFrom(protocol.getClass());
	}
}
