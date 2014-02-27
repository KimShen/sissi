package com.sissi.pipeline.in.iq.si;

import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.Streamhost;
import com.sissi.protocol.iq.si.Si;

/**
 * @author kim 2014年2月25日
 */
public class Si4DelegationProcessor extends ProxyProcessor {

	private final String delegation;

	private final Streamhost streamhost;

	private final PersistentElementBox persistentElementBox;

	public Si4DelegationProcessor(String delegation, Streamhost streamhost, PersistentElementBox persistentElementBox) {
		super();
		this.delegation = delegation;
		this.streamhost = streamhost;
		this.persistentElementBox = persistentElementBox;
	}

	@SuppressWarnings("unchecked")
	public boolean input(JIDContext context, Protocol protocol) {
		String host = protocol.cast(Si.class).setId(protocol.parent().getId()).host(protocol.parent().getTo(), context.jid().asString());
		Map<String, Object> element = this.persistentElementBox.peek(BasicDBObjectBuilder.start(PersistentElementBox.fieldSid, protocol.parent().getId()).get().toMap(), BasicDBObjectBuilder.start("$addToSet", BasicDBObjectBuilder.start(PersistentElementBox.fieldHost, host).get()).get().toMap());
		context.write(new IQ().add(new Bytestreams().setSid(element.get(PersistentElementBox.fieldSid).toString()).add(this.streamhost, true)).setFrom(this.delegation).setId(host).setType(ProtocolType.SET));
		return false;
	}
}
