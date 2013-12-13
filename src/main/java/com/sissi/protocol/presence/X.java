package com.sissi.protocol.presence;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.presence.x.XVCardPhoto;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.vcard.ListVCardFields;
import com.sissi.ucenter.vcard.ListVCardFields.Xmlns;

/**
 * @author kim 2013年12月13日
 */
@MappingMetadata(uri = "vcard-temp:x:update", localName = "x")
@XmlRootElement(name = "x")
public class X implements Field<String>, Collector {

	public final static String NAME = X.class.getSimpleName();

	private String xmlns;

	private final ListVCardFields vCardFields = new ListVCardFields(false);

	@XmlAttribute
	public String getXmlns() {
		return xmlns;
	}

	@Override
	public void set(String localName, Object ob) {
		this.vCardFields.add(Field.class.cast(ob));
		this.xmlns = Xmlns.class.isAssignableFrom(ob.getClass()) ? Xmlns.class.cast(ob).getXmlns() : null;
	}

	@XmlElements({ @XmlElement(name = "photo", type = XVCardPhoto.class) })
	public List<Field<?>> getFields() {
		return this.vCardFields.getFields();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Fields getChildren() {
		return this.vCardFields;
	}

	@Override
	public Boolean hasChild() {
		return true;
	}
}
