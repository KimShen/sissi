package com.sissi.protocol.iq.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.iq.register.form.Input;
import com.sissi.protocol.iq.register.form.Instructions;
import com.sissi.protocol.iq.register.form.Select;
import com.sissi.protocol.iq.register.form.Title;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = XData.XMLNS, localName = XData.NAME)
@XmlType(namespace = XData.XMLNS)
@XmlRootElement(name = XData.NAME)
public class XData extends BeanFields implements Field<Object>, Collector {

	public final static String NAME = "x";

	public final static String XMLNS = "jabber:x:data";

	public final static String TYPE_FROM = "from";

	private String type;

	public XData() {
		super(false);
	}

	public XData(Boolean isEmbed) {
		super(isEmbed);
	}

	public XData(Boolean isEmbed, List<Field<?>> fields) {
		super(isEmbed);
		super.add(fields);
		this.type = TYPE_FROM;
	}

	public XData(Boolean isEmbed, String type, List<Field<?>> fields) {
		super(isEmbed);
		super.add(fields);
		this.type = type;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public XData setType(String type) {
		this.type = type;
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlElements({ @XmlElement(name = XField.NAME, type = XField.class), @XmlElement(name = Input.NAME, type = Input.class), @XmlElement(name = Select.NAME, type = Select.class), @XmlElement(name = Title.NAME, type = Title.class), @XmlElement(name = Instructions.NAME, type = Instructions.class) })
	public List<Field<?>> getFields() {
		return super.getFields();
	}

	@Override
	public void set(String localName, Object ob) {
		super.add(Field.class.cast(ob));
	}

	@Override
	public Boolean isEmbed() {
		return super.isEmbed();
	}

	@Override
	public List<Field<?>> getValue() {
		return super.getFields();
	}

	@Override
	public Fields getChildren() {
		return this;
	}

	@Override
	public Boolean hasChild() {
		return super.getFields() != null && !super.getFields().isEmpty();
	}

	@Override
	public String getName() {
		return NAME;
	}
}
