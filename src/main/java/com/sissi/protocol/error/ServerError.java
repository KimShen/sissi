package com.sissi.protocol.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.sissi.protocol.Error;
import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.detail.InvaildNamespace;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.protocol.error.detail.NotAuthorized;

/**
 * @author kim 2014年1月3日
 */
abstract public class ServerError implements Error {

	public final static String NAME = "error";

	private String code;

	private String type;

	private List<ErrorDetail> details;

	public ServerError() {
		super();
	}

	public ServerError(Error error) {
		this.type = error.getType();
		this.code = error.getCode();
		this.details = error.getDetails();
	}

	@XmlAttribute
	public String getCode() {
		return this.code;
	}

	public ServerError setCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public ServerError setType(String type) {
		this.type = type;
		return this;
	}

	public ServerError setType(Type type) {
		this.type = type.toString();
		return this;
	}

	public ServerError add(ErrorDetail detail) {
		if (this.details == null) {
			this.details = new ArrayList<ErrorDetail>();
		}
		this.details.add(detail);
		return this;
	}

	@XmlElements({ @XmlElement(name = InvaildNamespace.NAME, type = InvaildNamespace.class), @XmlElement(name = NotAcceptable.NAME, type = NotAcceptable.class), @XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class) })
	public List<ErrorDetail> getDetails() {
		return details;
	}
}
