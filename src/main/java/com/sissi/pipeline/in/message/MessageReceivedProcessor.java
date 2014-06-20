package com.sissi.pipeline.in.message;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.config.Dictionary;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * ACK更新
 * 
 * @author kim 2014年3月3日
 */
public class MessageReceivedProcessor extends ProxyProcessor {

	private final Persistent persistent;

	public MessageReceivedProcessor(Persistent persistent) {
		super();
		this.persistent = persistent;
	}

	/*
	 * 如果为ACK请求
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Message.class).received() ? this.writeAndReturn(context, protocol) : true;
	}

	/**
	 * {"pid":Xxx},{"$set":{"ack":false}}
	 * 
	 * @param context
	 * @param protocol
	 * @return
	 */
	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		this.persistent.peek(MongoUtils.asMap(BasicDBObjectBuilder.start(Dictionary.FIELD_PID, protocol.cast(Message.class).getReceived().id()).get()), MongoUtils.asMap(BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_ACK, true).get()).get()));
		return false;
	}
}
