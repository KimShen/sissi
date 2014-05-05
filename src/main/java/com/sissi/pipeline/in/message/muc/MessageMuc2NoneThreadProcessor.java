package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2NoneThreadProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Message.class).noneThread();
		return true;
	}
}
