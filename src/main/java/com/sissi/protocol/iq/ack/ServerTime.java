package com.sissi.protocol.iq.ack;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Sissi;

/**
 * @author kim 2014年2月17日
 */
@XmlRootElement(name = ServerTime.NAME)
public class ServerTime extends Protocol {

	public final static String NAME = "server";

	private String time;

	private ServerTime() {
		super();
	}

	private ServerTime(String time) {
		super();
		this.time = time;
	}

	@XmlAttribute
	public String getTime() {
		return time;
	}

	@XmlAttribute
	public String getXmlns() {
		return Sissi.XMLNS;
	}

	public static ServerTime now() {
		return new ServerTime(String.valueOf(System.currentTimeMillis()));
	}
}
