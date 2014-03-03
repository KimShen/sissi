package com.sissi.pipeline.in.message;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年3月3日
 */
public class MessageReceivedProcessor extends ProxyProcessor {

	private final PersistentElementBox persistentElementBox;

	public MessageReceivedProcessor(PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Message.class).received() ? this.writeAndReturn(context, protocol) : true;
	}

	@SuppressWarnings("unchecked")
	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		this.persistentElementBox.peek(BasicDBObjectBuilder.start().add(PersistentElementBox.fieldClass, Message.class.getSimpleName()).add(PersistentElementBox.fieldId, protocol.cast(Message.class).getReceived().getId()).get().toMap(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(PersistentElementBox.fieldAck, false).get()).get().toMap());
		return true;
	}
}
