package com.sissi.pipeline.in.message;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年3月3日
 */
public class MessageReadedProcessor extends ProxyProcessor {

	private final PersistentElementBox persistentElementBox;

	public MessageReadedProcessor(PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Message.class).readed() ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		this.persistentElementBox.peek(Extracter.asMap(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_CLASS, Message.class.getSimpleName()).add(PersistentElementBox.fieldId, protocol.cast(Message.class).getReceived().getId()).get()), Extracter.asMap(BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(PersistentElementBox.fieldReaded, true).get()).get()));
		return true;
	}
}
