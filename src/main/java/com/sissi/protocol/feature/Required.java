package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Stream;


/**
 * @author kim 2014年1月2日
 */
@XmlType(namespace = Stream.XMLNS)
public class Required {

	public final static Required REQUIRED = new Required();

	private Required() {

	}
}
