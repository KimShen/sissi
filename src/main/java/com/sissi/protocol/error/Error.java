package com.sissi.protocol.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Failed;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.detail.Blocked;
import com.sissi.protocol.error.detail.NotAcceptable;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = "error")
public class Error extends Protocol implements Failed {

	private String code;

	private List<Detail> details;

	public Error() {
		super();
	}
	
	public Error(Failed failed) {
		this.code = failed.getCode();
		this.details = failed.getDetails();
		super.setFrom(failed.getFrom());
		super.setTo(failed.getTo());
		super.setType(failed.getType());
	}

	public Error(String code, Type type) {
		super();
		super.setType(type);
		this.code = code;
	}

	public Error setType(Type type) {
		super.setType(type);
		return this;
	}

	@XmlAttribute
	public String getCode() {
		return code;
	}

	public Error add(Detail detail) {
		if (this.details == null) {
			this.details = new ArrayList<Detail>();
		}
		this.details.add(detail);
		return this;
	}

	@XmlElements({ @XmlElement(name = "not-acceptable", type = NotAcceptable.class), @XmlElement(name = "blocked", type = Blocked.class) })
	public List<Detail> getDetails() {
		return details;
	}
}
