package com.sissi.protocol.iq.vcard;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.vcard.field.Photo;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Field.Fields;
import com.sissi.ucenter.vcard.ListVCardFields;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "vcard-temp", localName = "vCard")
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement(name = "vCard")
public class VCard extends Protocol implements Fields, Collector {

	private final static String XMLNS = "vcard-temp";

	private final ListVCardFields vCardFields = new ListVCardFields(false);

	public VCard() {
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlElements({ @XmlElement(name = "PHOTO", type = Photo.class) })
	public List<Field<?>> getFields() {
		return this.vCardFields.getFields();
	}

	public VCard add(Field<?> field) {
		this.vCardFields.add(field);
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.vCardFields.add(Field.class.cast(ob));
	}

	public Fields addField(Field<?> field) {
		this.vCardFields.add(field);
		return this;
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.vCardFields.iterator();
	}

	@Override
	public Boolean isEmbed() {
		return this.vCardFields.isEmbed();
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.vCardFields.findField(name, clazz);
	}
}
