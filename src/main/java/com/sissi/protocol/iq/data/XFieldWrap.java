package com.sissi.protocol.iq.data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.field.impl.BeanFields;
import com.sissi.protocol.iq.vcard.field.Nickname;

/**
 * @author kim 2014年2月8日
 */
abstract class XFieldWrap extends BeanFields {

	public XFieldWrap() {
		super(false);
	}

	public XFieldWrap(boolean isEmbed) {
		super(isEmbed);
	}

	@XmlElements({ @XmlElement(name = XField.NAME, type = XField.class), @XmlElement(name = XItem.NAME, type = XItem.class), @XmlElement(name = XReported.NAME, type = XReported.class), @XmlElement(name = XInput.NAME, type = XInput.class), @XmlElement(name = Select.NAME, type = Select.class), @XmlElement(name = Title.NAME, type = Title.class), @XmlElement(name = Instructions.NAME, type = Instructions.class) ,@XmlElement(name = Nickname.NAME, type = Nickname.class)})
	public List<Field<?>> getFields() {
		return super.getFields();
	}

	public Fields getChildren() {
		return this;
	}

	public boolean hasChild() {
		return super.getFields() != null && !super.getFields().isEmpty();
	}
}
