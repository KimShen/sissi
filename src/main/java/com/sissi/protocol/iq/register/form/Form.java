package com.sissi.protocol.iq.register.form;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.Field;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "jabber:x:data", localName = "x")
@XmlRootElement(name = "x")
public class Form implements Field, Collector {

	public static enum Type {

		TEXT_SINGLE, LIST_SINGLE, HIDDEN, FORM;

		private final static String TEXT_SINGLE_TEXT = "text-single";

		private final static String LIST_SINGLE_TEXT = "list-single";

		public String toString() {
			switch (this) {
			case TEXT_SINGLE:
				return TEXT_SINGLE_TEXT;
			case LIST_SINGLE:
				return LIST_SINGLE_TEXT;
			default:
				return super.toString().toLowerCase();
			}
		}
		
		public static Type parse(String type) {
			switch (type) {
			case TEXT_SINGLE_TEXT:
				return TEXT_SINGLE;
			case LIST_SINGLE_TEXT:
				return LIST_SINGLE;
			default:
				return Type.valueOf(type.toUpperCase());
			}
		}
	}

	private final static String XMLNS = "jabber:x:data";

	private String type;

	private String title;

	private String instructions;

	private List<Field> field;

	public Form() {

	}

	public Form(String title, String instructions) {
		super();
		this.type = Type.FORM.toString();
		this.title = title;
		this.instructions = instructions;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	@XmlElement(name = "title")
	public String getName() {
		return title;
	}

	public Form setType(String type) {
		this.type = type;
		return this;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	@Override
	@XmlElement(name = "instructions")
	public String getValue() {
		return instructions;
	}

	public Form add(Field field) {
		if (this.field == null) {
			this.field = new ArrayList<Field>();
		}
		this.field.add(field);
		return this;
	}

	@XmlElements({ @XmlElement(name = "field", type = Input.class), @XmlElement(name = "field", type = Select.class) })
	public List<Field> getField() {
		return field;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add((Field) ob);
	}

	@Override
	public Boolean isEmpty() {
		return this.getValue() == null;
	}
}
