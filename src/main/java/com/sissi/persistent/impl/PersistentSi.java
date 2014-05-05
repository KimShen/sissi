package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XOption;
import com.sissi.protocol.iq.si.Feature;
import com.sissi.protocol.iq.si.File;
import com.sissi.protocol.iq.si.Si;

/**
 * Si</p>索引策略:{"sid":1}
 * 
 * @author kim 2014年2月25日
 */
public class PersistentSi extends PersistentProtocol {

	private final String profile = "http://jabber.org/protocol/si/profile/file-transfer";

	private final Feature feature = new Feature().setX(new XData().setType(XDataType.FORM).add(new XField().setType(XFieldType.LIST_SINGLE).setVar("stream-method").add(new XOption(Bytestreams.XMLNS))));

	private final String delegation;

	/**
	 * @param jidBuilder
	 * @param tip
	 * @param delegation 代理域
	 */
	public PersistentSi(JIDBuilder jidBuilder, String tip, String delegation) {
		super(Si.class, jidBuilder, tip, true);
		this.delegation = delegation;
	}

	@Override
	public Map<String, Object> write(Element element) {
		Si si = Si.class.cast(element);
		Map<String, Object> entity = super.write(si.parent().reply().setType(ProtocolType.SET.toString()));
		entity.put(Dictionary.FIELD_SID, si.getId());
		entity.put(Dictionary.FIELD_SIZE, si.getFile().getSize());
		entity.put(Dictionary.FIELD_NAME, si.getFile().getName());
		entity.put(Dictionary.FIELD_CLASS, element.getClass().getSimpleName());
		entity.put(Dictionary.FIELD_HOST, new String[] { si.host(this.delegation, super.jidBuilder.build(si.parent().getTo()).asStringWithBare()) });
		if (si.delay()) {
			entity.put(Dictionary.FIELD_DELAY, si.getDelay().getStamp());
		}
		return entity;
	}

	/*
	 * IQ.id.from.add(new Si().id.source.profile.feature.file(new File().name.size))
	 * 
	 * @see com.sissi.persistent.PersistentElement#read(java.util.Map)
	 */
	@Override
	public Element read(Map<String, Object> element) {
		return IQ.class.cast(super.read(element, new IQ())).setId(element.get(Dictionary.FIELD_SID).toString()).setFrom(this.delegation).add(new Si().setId(element.get(Dictionary.FIELD_SID).toString()).setSource(element.get(Dictionary.FIELD_FROM).toString()).setProfile(this.profile).setFeature(this.feature).setFile(new File().setName(element.get(Dictionary.FIELD_NAME).toString()).setSize(element.get(Dictionary.FIELD_SIZE).toString())).delay(super.toString(element, Dictionary.FIELD_DELAY)));
	}

	public Class<? extends Element> support() {
		return Si.class;
	}
}
