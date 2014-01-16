package com.sissi.protocol.iq.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = XData.XMLNS, localName = XField.NAME)
@XmlRootElement(name = XField.NAME)
public class XField implements Field<String>, Collector {

	public final static String NAME = "field";

	private String var;

	private String type;

	private String value;

	private final BeanFields fields;

	public XField() {
		this.fields = new BeanFields(true);
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public XField setType(String type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return this.var;
	}

	@XmlAttribute
	public String getVar() {
		return this.var;
	}

	public XField setVar(String var) {
		this.var = var;
		return this;
	}

	@XmlElements({ @XmlElement(name = XOption.NAME, type = XOption.class), @XmlElement(name = XValue.NAME, type = XValue.class) })
	public List<Field<?>> getFields() {
		return this.fields.getFields();
	}

	@Override
	public Fields getChildren() {
		return this.fields;
	}

	@Override
	public Boolean hasChild() {
		return this.getValue() == null && (this.fields.getFields() != null && !this.fields.getFields().isEmpty());
	}

	public void set(String localName, Object ob) {
		this.fields.add(Field.class.cast(ob));
	}

	@Override
	public String getValue() {
		return this.value != null ? this.value : this.computeValue();
	}

	private String computeValue() {
		XValue value = this.fields.findField(XValue.NAME, XValue.class);
		return value != null ? (this.value = value.getValue()) : null;
	}
}
