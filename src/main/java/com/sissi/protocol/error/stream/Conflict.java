package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = Conflict.NAME)
public class Conflict extends StreamErrorDetail {

	public final static Conflict DETAIL = new Conflict();

	public final static String NAME = "conflict";

	private Conflict() {

	}
}
