package com.sissi.protocol.iq.vcard.field;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = VCard.XMLNS, localName = Photo.NAME)
@XmlRootElement(name = Photo.NAME)
public class Photo implements Field<String>, Collector {

	public final static String NAME = "PHOTO";

	private final BeanFields fields;

	public Photo() {
		this.fields = new BeanFields(false);
	}

	public Photo(String type, String binval) {
		this();
		this.fields.add(new Type(type)).add(new Binval(binval));
	}

	public void set(String localName, Object ob) {
		this.fields.add(Field.class.cast(ob));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@XmlElements({ @XmlElement(name = Type.NAME, type = Type.class), @XmlElement(name = Binval.NAME, type = Binval.class) })
	public List<Field<?>> getFields() {
		return this.fields.getFields();
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Fields getChildren() {
		return this.fields;
	}

	@Override
	public Boolean hasChild() {
		return true;
	}
}
