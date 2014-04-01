package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年4月1日
 */
@Metadata(uri = Ack.XMLNS, localName = AckReaded.NAME)
@XmlRootElement
public class AckReaded extends Ack {

	public final static String NAME = "readed";

	private String id;

	public AckReaded setId(String id) {
		this.id = id;
		return this;
	}

	public boolean id() {
		return this.getId() != null;
	}

	public String getId() {
		return this.id;
	}
}
