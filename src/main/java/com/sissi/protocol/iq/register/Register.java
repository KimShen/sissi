package com.sissi.protocol.iq.register;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.register.simple.Password;
import com.sissi.protocol.iq.register.simple.Username;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = Register.XMLNS, localName = Register.NAME)
@XmlType(namespace = Register.XMLNS)
@XmlRootElement(name = Register.NAME)
public class Register extends Protocol implements Fields, Collector {

	public final static String XMLNS = "jabber:iq:register";

	public final static String NAME = "query";

	private final BeanFields fields;

	private String instructions;

	public Register() {
		this.fields = new BeanFields(true);
	}

	public Register(String instructions) {
		this();
		this.instructions = instructions;
	}

	@XmlElement
	public String getInstructions() {
		return instructions;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Register add(Field<?> field) {
		this.fields.add(field);
		return this;
	}

	public Register add(Fields fields) {
		this.fields.add(fields);
		return this;
	}

	@XmlElements({ @XmlElement(name = XData.NAME, type = XData.class), @XmlElement(name = Username.NAME, type = Username.class), @XmlElement(name = Password.NAME, type = Password.class) })
	public List<Field<?>> getFields() {
		return this.fields.getFields();
	}

	public Boolean isForm() {
		return this.fields.findField(XData.NAME, XData.class) != null;
	}

	public void set(String localName, Object ob) {
		this.fields.add(Field.class.cast(ob));
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.fields.iterator();
	}

	@Override
	public Boolean isEmbed() {
		return this.fields.isEmbed();
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.fields.findField(name, clazz);
	}
}
