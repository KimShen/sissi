package com.sissi.protocol.iq.ack;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.time.FastDateFormat;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Sissi;

/**
 * @author kim 2014年2月17日
 */
@XmlRootElement(name = ServerAck.NAME)
public class ServerAck extends Protocol {

	public final static String NAME = "server";

	private final static FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZ");

	private String time;

	private ServerAck() {
		super();
	}

	private ServerAck(String time) {
		super();
		this.time = time;
	}

	@XmlAttribute
	public String getTime() {
		return this.time;
	}

	@XmlAttribute
	public String getXmlns() {
		return Sissi.XMLNS;
	}

	public static ServerAck now() {
		return new ServerAck(format.format(new Date()));
	}
}
