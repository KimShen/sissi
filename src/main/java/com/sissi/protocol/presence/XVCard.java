package com.sissi.protocol.presence;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = XVCard.XMLNS, localName = X.NAME)
@XmlType(namespace = XVCard.XMLNS)
@XmlRootElement
public class XVCard extends X implements Fields, Field<String>, Collector {

	public final static String XMLNS = "vcard-temp:x:update";

	private final BeanFields vCardFields = new BeanFields(false);

	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add(Field.class.cast(ob));
	}

	@XmlElements({ @XmlElement(name = XVCardPhoto.NAME, type = XVCardPhoto.class) })
	public List<Field<?>> getFields() {
		return this.vCardFields.getFields();
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
	public boolean hasChild() {
		return true;
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.vCardFields.iterator();
	}

	@Override
	public boolean isEmbed() {
		return this.vCardFields.isEmbed();
	}

	@Override
	public XVCard add(Field<?> field) {
		this.vCardFields.add(field);
		return this;
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.vCardFields.findField(name, clazz);
	}
}
