package com.sissi.protocol;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author kim 2014年1月2日
 */
@XmlType(namespace = Stream.XMLNS)
@XmlRootElement
public class Required {

	public final static Required REQUIRED = new Required();

	private Required() {

	}
}
