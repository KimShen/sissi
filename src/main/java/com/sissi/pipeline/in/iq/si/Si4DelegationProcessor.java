package com.sissi.pipeline.in.iq.si;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.config.Dictionary;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.Streamhost;
import com.sissi.protocol.iq.si.Si;

/**
 * 离线文件推送
 * 
 * @author kim 2014年2月25日
 */
public class Si4DelegationProcessor extends ProxyProcessor {

	private final boolean resource;

	private final String delegation;

	private final Streamhost streamhost;

	private final Persistent persistent;

	public Si4DelegationProcessor(boolean resource, String delegation, Streamhost streamhost, Persistent persistent) {
		super();
		this.resource = resource;
		this.delegation = delegation;
		this.streamhost = streamhost;
		this.persistent = persistent;
	}

	/*
	 * Query: {"sid":Xxx"}</p>Entity: {"$addToSet":{"host":Xxx}}
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	public boolean input(JIDContext context, Protocol protocol) {
		String host = protocol.cast(Si.class).setId(protocol.parent().getId()).host(super.build(protocol.parent().getTo()).asString(this.resource), context.jid().asString(this.resource));
		context.write(new IQ().add(new Bytestreams().setSid(this.persistent.peek(MongoUtils.asMap(BasicDBObjectBuilder.start(Dictionary.FIELD_SID, protocol.parent().getId()).get()), MongoUtils.asMap(BasicDBObjectBuilder.start("$addToSet", BasicDBObjectBuilder.start(Dictionary.FIELD_HOST, host).get()).get())).get(Dictionary.FIELD_SID).toString()).add(this.streamhost, true)).setFrom(this.delegation).setId(host).setType(ProtocolType.SET));
		return true;
	}
}
