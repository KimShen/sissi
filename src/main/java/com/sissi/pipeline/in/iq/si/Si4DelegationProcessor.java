package com.sissi.pipeline.in.iq.si;

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

	private final boolean bare;

	private final String delegation;

	private final Streamhost streamhost;

	private final PersistentElementBox persistentElementBox;

	public Si4DelegationProcessor(boolean bare, String delegation, Streamhost streamhost, PersistentElementBox persistentElementBox) {
		super();
		this.bare = bare;
		this.delegation = delegation;
		this.streamhost = streamhost;
		this.persistentElementBox = persistentElementBox;
	}

	@SuppressWarnings("unchecked")
	public boolean input(JIDContext context, Protocol protocol) {
		String host = protocol.cast(Si.class).setId(protocol.parent().getId()).host(super.build(protocol.parent().getTo()).asString(this.bare), context.jid().asString(this.bare));
		context.write(new IQ().add(new Bytestreams().setSid(this.persistentElementBox.peek(BasicDBObjectBuilder.start(PersistentElementBox.fieldSid, protocol.parent().getId()).get().toMap(), BasicDBObjectBuilder.start("$addToSet", BasicDBObjectBuilder.start(PersistentElementBox.fieldHost, host).get()).get().toMap()).get(PersistentElementBox.fieldSid).toString()).add(this.streamhost, true)).setFrom(this.delegation).setId(host).setType(ProtocolType.SET));
		return true;
	}
}
