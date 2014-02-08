package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.field.impl.BeanField;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = XRequired.NAME)
public class XRequired extends BeanField<String> {

	public final static XRequired FIELD = new XRequired();

	public final static String NAME = "required";

	private XRequired() {
	}
}
