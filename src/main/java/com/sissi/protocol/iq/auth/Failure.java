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
import com.sissi.protocol.error.element.Aborted;
import com.sissi.protocol.error.element.AccountDisabled;
import com.sissi.protocol.error.element.CredentialsExpired;
import com.sissi.protocol.error.element.EncryptionRequired;
import com.sissi.protocol.error.element.IncorrectEncoding;
import com.sissi.protocol.error.element.InvalidMechanism;
import com.sissi.protocol.error.element.MalformedRequest;
import com.sissi.protocol.error.element.MechanismTooWeak;
import com.sissi.protocol.error.element.NotAuthorized;
import com.sissi.protocol.error.element.TemporaryAuthFailure;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Failure extends Protocol implements Error {

	public final static Failure INSTANCE_INVALIDMECHANISM = new Failure().add(InvalidMechanism.DETAIL);

	public final static Failure INSTANCE_NOTAUTHORIZED = new Failure().add(NotAuthorized.DETAIL);

	public final static Failure INSTANCE_ABORTED = new Failure().add(Aborted.DETAIL);

	private List<ErrorDetail> details;

	private ServerErrorText text;

	private String code;

	private String by;

	public Failure() {
	}

	public Failure(String code, String lang, String text) {
		this.code = code;
		this.text = new ServerErrorText(lang, text);
	}

	public Failure(String by, String code, String lang, String text) {
		this(code, lang, text);
		this.by = by;
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
	@XmlAttribute
	public String getCode() {
		return this.code;
	}

	@Override
	@XmlAttribute
	public String getBy() {
		return this.by;
	}

	@Override
	@XmlElement
	public ServerErrorText getText() {
		return this.text;
	}

	@XmlElements({ @XmlElement(name = TemporaryAuthFailure.NAME, type = TemporaryAuthFailure.class), @XmlElement(name = MechanismTooWeak.NAME, type = MechanismTooWeak.class), @XmlElement(name = MalformedRequest.NAME, type = MalformedRequest.class), @XmlElement(name = InvalidMechanism.NAME, type = InvalidMechanism.class), @XmlElement(name = IncorrectEncoding.NAME, type = IncorrectEncoding.class), @XmlElement(name = EncryptionRequired.NAME, type = EncryptionRequired.class), @XmlElement(name = CredentialsExpired.NAME, type = CredentialsExpired.class), @XmlElement(name = AccountDisabled.NAME, type = AccountDisabled.class), @XmlElement(name = Aborted.NAME, type = Aborted.class), @XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class) })
	public List<ErrorDetail> getDetails() {
		return details;
	}
}
