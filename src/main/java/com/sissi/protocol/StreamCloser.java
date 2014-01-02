package com.sissi.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.write.WithJustClose;

/**
 * @author kim 2014年1月2日
 */
@XmlType(namespace = Stream.XMLNS)
@XmlRootElement(name = Stream.NAME)
public class StreamCloser extends Stream implements WithJustClose {

	public final static StreamCloser CLOSER = new StreamCloser();

	private StreamCloser() {

	}

	@XmlElement
	public String getPlaceholder() {
		return "";
	}
}
