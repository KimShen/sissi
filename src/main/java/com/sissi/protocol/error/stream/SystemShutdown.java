package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = SystemShutdown.NAME)
public class SystemShutdown extends StreamErrorDetail {

	public final static SystemShutdown DETAIL = new SystemShutdown();

	public final static String NAME = "system-shutdown";

	private SystemShutdown() {

	}
}
