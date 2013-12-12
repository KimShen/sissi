package com.sissi.protocol.iq.register;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.register.form.Form;
import com.sissi.protocol.iq.register.simple.Password;
import com.sissi.protocol.iq.register.simple.Username;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Field.Fields;
import com.sissi.ucenter.vcard.ListVCardFields;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = "jabber:iq:register", localName = "query")
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement(name = "query")
public class Register extends Protocol implements Fields, Collector {

	private final static String XMLNS = "jabber:iq:register";
	
	private final ListVCardFields vCardFields = new ListVCardFields(true);

	private String instructions;

	public Register() {
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
		this.vCardFields.add(field);
		return this;
	}

	public Register add(List<Field<?>> fields) {
		for (Field<?> each : fields) {
			this.vCardFields.add(each);
		}
		return this;
	}

	@XmlElements({ @XmlElement(name = "username", type = Username.class), @XmlElement(name = "password", type = Password.class), @XmlElement(name = "x", type = Form.class) })
	public List<Field<?>> getFields() {
		return this.vCardFields.getFields();
	}

	public Boolean isForm() {
		return this.vCardFields.findField(Form.NAME, Field.class) != null;
	}

	@Override
	public void set(String localName, Object ob) {
		this.vCardFields.add(Field.class.cast(ob));
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
