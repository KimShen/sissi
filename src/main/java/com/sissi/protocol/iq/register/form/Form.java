package com.sissi.protocol.iq.register.form;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
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
@MappingMetadata(uri = "jabber:x:data", localName = "x")
@XmlRootElement(name = "x")
public class Form extends ListVCardFields implements Field<List<Field<?>>>, Collector {

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

	public final static String NAME = "FORM";

	private final static String XMLNS = "jabber:x:data";

	private String type;

	private String title;

	private String instructions;

	public Form() {
		super(false);
	}

	public Form(String type, String title, String instructions, List<Field<?>> fields) {
		this();
		this.type = type;
		this.title = title;
		this.instructions = instructions;
		super.add(fields);
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}
	
	@XmlElement
	public String getTitle() {
		return this.title;
	}

	@XmlElement
	public String getInstructions(){
		return this.instructions;
	}
	
	@XmlElements({ @XmlElement(name = "field", type = Input.class), @XmlElement(name = "field", type = Select.class) })
	public List<Field<?>> getFields() {
		return super.getFields();
	}

	@Override
	public void set(String localName, Object ob) {
		super.add(Field.class.cast(ob));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Boolean isEmbed() {
		return false;
	}

	@Override
	public List<Field<?>> getValue() {
		return this.getFields();
	}

	@Override
	public Fields getChildren() {
		return this;
	}

	@Override
	public Boolean hasChild() {
		return !this.getFields().isEmpty();
	}
}
