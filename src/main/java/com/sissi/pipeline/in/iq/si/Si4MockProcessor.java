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

/**
 * @author kim 2014年2月25日
 */
public class Si4MockProcessor extends ProxyProcessor {

	private final String delegation;

	private final Streamhost streamhost;

	private final PersistentElementBox persistentElementBox;

	public Si4MockProcessor(String delegation, Streamhost streamhost, PersistentElementBox persistentElementBox) {
		super();
		this.delegation = delegation;
		this.streamhost = streamhost;
		this.persistentElementBox = persistentElementBox;
	}

	@SuppressWarnings("unchecked")
	public boolean input(JIDContext context, Protocol protocol) {
		Map<String, Object> element = this.persistentElementBox.peek(BasicDBObjectBuilder.start(PersistentElementBox.fieldHost, protocol.getParent().getId()).get().toMap());
		String sid = element.get(PersistentElementBox.fieldSid).toString();
		context.write(new IQ().add(new Bytestreams().setSid(sid).add(this.streamhost, true)).setFrom(this.delegation).setId(element.get(PersistentElementBox.fieldHost).toString()).setType(ProtocolType.SET), false, true);
		return false;
	}
}
