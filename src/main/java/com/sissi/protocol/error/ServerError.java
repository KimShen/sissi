package com.sissi.protocol.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error;
import com.sissi.protocol.error.detail.NotAcceptable;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = ServerError.NAME)
public class ServerError implements Error {

	public final static String NAME = "error";

	private String code;

	private String type;

	private List<Detail> details;

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

	public ServerError add(Detail detail) {
		if (this.details == null) {
			this.details = new ArrayList<Detail>();
		}
		this.details.add(detail);
		return this;
	}

	@XmlElements({ @XmlElement(name = "not-acceptable", type = NotAcceptable.class) })
	public List<Detail> getDetails() {
		return details;
	}
}
