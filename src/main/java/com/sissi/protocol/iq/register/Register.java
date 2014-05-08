package com.sissi.protocol.iq.register;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.field.impl.BeanFields;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.register.simple.Password;
import com.sissi.protocol.iq.register.simple.Registered;
import com.sissi.protocol.iq.register.simple.Username;

/**
 * @author kim 2013年12月3日
 */
@Metadata(uri = Register.XMLNS, localName = Register.NAME)
@XmlType(namespace = Register.XMLNS)
@XmlRootElement(name = Register.NAME)
public class Register extends Protocol implements Fields, Collector {

	public final static String XMLNS = "jabber:iq:register";

	public final static String NAME = "query";

	private final BeanFields fields = new BeanFields(true);

	private String instructions;

	public Register() {
	}

	public Register(String instructions) {
		this();
		this.instructions = instructions;
	}

	public boolean form(boolean needForm) {
		return (this.fields.findField(XData.NAME, XData.class) != null) == needForm;
	}

	@XmlElement
	public String getInstructions() {
		return this.instructions;
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

	@XmlElements({ @XmlElement(name = XData.NAME, type = XData.class), @XmlElement(name = Username.NAME, type = Username.class), @XmlElement(name = Password.NAME, type = Password.class), @XmlElement(name = Registered.NAME, type = Registered.class) })
	public List<Field<?>> getFields() {
		return this.fields.getFields();
	}

	public void set(String localName, Object ob) {
		this.fields.add(Field.class.cast(ob));
	}

	public Register clear() {
		this.fields.reset();
		return this;
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.fields.iterator();
	}

	@Override
	public boolean isEmbed() {
		return this.fields.isEmbed();
	}

	public boolean isEmpty() {
		return this.fields.isEmpty();
	}

	public Fields findFields(String name) {
		return this.fields.findFields(name);
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.fields.findField(name, clazz);
	}
}
