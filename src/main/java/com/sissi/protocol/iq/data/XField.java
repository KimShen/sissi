package com.sissi.protocol.iq.data;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

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

	private Object value;

	@XmlElement
	public String getDesc() {
		return desc;
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

	public XField setType(XFieldType type) {
		this.type = type.toString();
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
	public boolean hasChild() {
		return this.getValue() == null && (this.fields.getFields() != null && !this.fields.getFields().isEmpty());
	}

	public void set(String localName, Object ob) {
		this.fields.add(Field.class.cast(ob));
	}

	@Override
	public Object getValue() {
		return this.value != null ? this.value : this.computeValue();
	}

	private Object computeValue() {
		LinkedList<String> fields = new LinkedList<String>();
		for (Field<?> field : this.fields.findField(XValue.NAME)) {
			fields.add(field.getValue().toString());
		}
		return fields.size() == 1 ? fields.getFirst() : fields.toArray(new String[] {});
	}
}
