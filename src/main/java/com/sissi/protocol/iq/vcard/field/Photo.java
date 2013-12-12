package com.sissi.protocol.iq.vcard.field;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.vcard.ListVCardFields;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "vcard-temp", localName = "PHOTO")
@XmlRootElement(name = "PHOTO")
public class Photo implements Field<String>, Collector {

	public final static String NAME = Photo.class.getSimpleName().toLowerCase();

	private ListVCardFields vCardFields = new ListVCardFields(false);

	public Photo() {

	}

	public Photo(String type, String binval) {
		this.vCardFields.add(new Type(type)).add(new Binval(binval));
	}

	public void set(String localName, Object ob) {
		this.vCardFields.add(Field.class.cast(ob));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@XmlElements({ @XmlElement(name = "TYPE", type = Type.class), @XmlElement(name = "BINVAL", type = Binval.class) })
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
	public Boolean hasChild() {
		return true;
	}
}
