package com.sissi.protocol.iq.auth;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.auth.error.Aborted;
import com.sissi.protocol.iq.auth.error.NotAuthorized;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Failure extends Protocol {

	public final static Failure INSTANCE_NOTAUTHORIZED = new Failure().add(NotAuthorized.DETAIL);
	
	public final static Failure INSTANCE_ABORTED = new Failure().add(Aborted.DETAIL);

	private List<ErrorDetail> details;

	private Failure() {

	}

	public Failure add(ErrorDetail detail) {
		if (this.details == null) {
			this.details = new ArrayList<ErrorDetail>();
		}
		this.details.add(detail);
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}

	@XmlElements({ @XmlElement(name = Aborted.NAME, type = Aborted.class),@XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class) })
	public List<ErrorDetail> getDetails() {
		return details;
	}

}
