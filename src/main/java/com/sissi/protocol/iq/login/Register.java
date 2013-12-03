package com.sissi.protocol.iq.login;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;

/**
 * @author kim 2013年12月3日
 */
@XmlRootElement(name = "query")
public class Register extends Protocol implements Collector {

	private final static String XMLNS = "jabber:iq:register";

	private List<Field> fields;

	@XmlElements({ @XmlElement(name = "username", type = Username.class), @XmlElement(name = "password", type = Password.class) })
	public List<Field> getFields() {
		return fields;
	}

	public Register add(Field field) {
		if (this.fields == null) {
			this.fields = new ArrayList<Field>();
		}
		fields.add(field);
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Register clear() {
		super.clear();
		this.fields = null;
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add((Field) ob);
	}

	public interface Field {

		public String getName();

		public String getText();
	}
}
