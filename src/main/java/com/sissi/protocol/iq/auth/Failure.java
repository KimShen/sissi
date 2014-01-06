package com.sissi.protocol.iq.auth;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error;
import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerErrorText;
import com.sissi.protocol.iq.auth.error.Aborted;
import com.sissi.protocol.iq.auth.error.AccountDisabled;
import com.sissi.protocol.iq.auth.error.CredentialsExpired;
import com.sissi.protocol.iq.auth.error.EncryptionRequired;
import com.sissi.protocol.iq.auth.error.NotAuthorized;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Failure extends Protocol implements Error {
	
	public final static Failure INSTANCE_ENCRYPTIONREQUIRED = new Failure().add(EncryptionRequired.DETAIL);

	public final static Failure INSTANCE_NOTAUTHORIZED = new Failure().add(NotAuthorized.DETAIL);

	public final static Failure INSTANCE_ABORTED = new Failure().add(Aborted.DETAIL);

	private List<ErrorDetail> details;

	private ServerErrorText text;

	private String code;

	public Failure() {
	}

	public Failure(String code, String lang, String text) {
		this.code = code;
		this.text = new ServerErrorText(lang, text);
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

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	@XmlElement
	public ServerErrorText getText() {
		return this.text;
	}

	@XmlElements({ @XmlElement(name = EncryptionRequired.NAME, type = EncryptionRequired.class), @XmlElement(name = CredentialsExpired.NAME, type = CredentialsExpired.class), @XmlElement(name = AccountDisabled.NAME, type = AccountDisabled.class), @XmlElement(name = Aborted.NAME, type = Aborted.class), @XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class) })
	public List<ErrorDetail> getDetails() {
		return details;
	}
}
