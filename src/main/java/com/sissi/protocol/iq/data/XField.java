package com.sissi.protocol.iq.data;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.field.impl.BeanFields;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = XData.XMLNS, localName = XField.NAME)
@XmlRootElement(name = XField.NAME)
public class XField implements Field<Object>, Collector {

	public final static String NAME = "field";

	private final BeanFields fields = new BeanFields(true);

	private String var;

	private String desc;

	private String type;

	private String label;

	private Object value;

	public XField add(Field<?> field) {
		this.fields.add(field);
		return this;
	}

	@XmlElement
	public String getDesc() {
		return this.desc;
	}

	public XField setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public XField setType(String type) {
		this.type = type;
		return this;
	}

	public XField type(XFieldType type) {
		this.type = type.toString();
		return this;
	}

	public String getName() {
		return this.var;
	}

	public XField setLabel(String label) {
		this.label = label;
		return this;
	}

	@XmlAttribute
	public String getLabel() {
		return this.label;
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
	public boolean hasChild() {
		return this.getValue() == null && (this.fields.getFields() != null && !this.fields.getFields().isEmpty());
	}

	@Override
	public Object getValue() {
		return this.value != null ? this.value : this.computeValue();
	}

	private Object computeValue() {
		LinkedList<String> fields = new LinkedList<String>();
		for (Field<?> field : this.fields.findFields(XValue.NAME)) {
			if (field.getValue() != null) {
				fields.add(field.getValue().toString());
			}
		}
		return fields.isEmpty() ? null : fields.size() == 1 ? fields.getFirst() : fields.toArray(new String[] {});
	}

	public void set(String localName, Object ob) {
		this.fields.add(Field.class.cast(ob));
	}
}
