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
@XmlRootElement(name = ServerTime.NAME)
public class ServerTime extends Protocol {

	public final static String NAME = "server";

	private final static FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZ");

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
		return new ServerTime(format.format(new Date()));
	}
}
