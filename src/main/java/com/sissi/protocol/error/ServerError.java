package com.sissi.protocol.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error;
import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.element.NotAcceptable;
import com.sissi.protocol.error.element.NotAllowed;
import com.sissi.protocol.error.stream.BadFormat;
import com.sissi.protocol.error.stream.BadNamespacePrefix;
import com.sissi.protocol.error.stream.Conflict;
import com.sissi.protocol.error.stream.HostGone;
import com.sissi.protocol.error.stream.HostUnknown;
import com.sissi.protocol.error.stream.InternalServerError;
import com.sissi.protocol.error.stream.InvaildNamespace;
import com.sissi.protocol.error.stream.InvalidFrom;
import com.sissi.protocol.error.stream.InvalidXml;
import com.sissi.protocol.error.stream.NotAuthorized;
import com.sissi.protocol.error.stream.NotWellFormed;
import com.sissi.protocol.error.stream.PolicyViolation;
import com.sissi.protocol.error.stream.Reset;
import com.sissi.protocol.error.stream.RestrictedXml;
import com.sissi.protocol.error.stream.SeeOtherHost;
import com.sissi.protocol.error.stream.SystemShutdown;
import com.sissi.protocol.error.stream.UnSupportedEncoding;
import com.sissi.protocol.error.stream.UnSupportedFeature;
import com.sissi.protocol.error.stream.UnSupportedStanzaType;
import com.sissi.protocol.error.stream.UnSupportedVersion;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = ServerError.NAME)
public class ServerError implements Error {

	public final static String NAME = "error";

	private String code;

	private String type;

	private ServerErrorText text;

	private List<ErrorDetail> details;

	public ServerError() {
		super();
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

	public ServerError add(ErrorDetail detail, String lang, String text) {
		this.add(detail);
		this.text = new ServerErrorText(lang, text);
		return this;
	}

	@Override
	@XmlElement(name = ServerErrorText.NAME)
	public ServerErrorText getText() {
		return this.text;
	}

	@XmlElements({ @XmlElement(name = NotAllowed.NAME, type=NotAllowed.class), @XmlElement(name = com.sissi.protocol.error.element.ResourceConstraint.NAME, type = com.sissi.protocol.error.element.ResourceConstraint.class), @XmlElement(name = NotAcceptable.NAME, type = NotAcceptable.class), @XmlElement(name = UnSupportedVersion.NAME, type = UnSupportedVersion.class), @XmlElement(name = UnSupportedStanzaType.NAME, type = UnSupportedStanzaType.class), @XmlElement(name = UnSupportedFeature.NAME, type = UnSupportedFeature.class), @XmlElement(name = UnSupportedEncoding.NAME, type = UnSupportedEncoding.class), @XmlElement(name = SystemShutdown.NAME, type = SystemShutdown.class), @XmlElement(name = SeeOtherHost.NAME, type = SeeOtherHost.class), @XmlElement(name = RestrictedXml.NAME, type = RestrictedXml.class), @XmlElement(name = com.sissi.protocol.error.stream.ResourceConstraint.NAME, type = com.sissi.protocol.error.stream.ResourceConstraint.class), @XmlElement(name = Reset.NAME, type = Reset.class), @XmlElement(name = PolicyViolation.NAME, type = PolicyViolation.class), @XmlElement(name = NotWellFormed.NAME, type = NotWellFormed.class), @XmlElement(name = InvalidXml.NAME, type = InvalidXml.class), @XmlElement(name = InvalidFrom.NAME, type = InvalidFrom.class), @XmlElement(name = InternalServerError.NAME, type = InternalServerError.class), @XmlElement(name = Conflict.NAME, type = Conflict.class), @XmlElement(name = BadNamespacePrefix.NAME, type = BadNamespacePrefix.class), @XmlElement(name = BadFormat.NAME, type = BadFormat.class), @XmlElement(name = HostGone.NAME, type = HostGone.class), @XmlElement(name = HostUnknown.NAME, type = HostUnknown.class), @XmlElement(name = InvaildNamespace.NAME, type = InvaildNamespace.class), @XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class) })
	public List<ErrorDetail> getDetails() {
		return details;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public ServerError setId(String id) {
		return this;
	}

	@Override
	public String getFrom() {
		return null;
	}

	@Override
	public ServerError setFrom(String from) {
		return this;
	}

	@Override
	public String getTo() {
		return null;
	}

	@Override
	public ServerError setTo(String to) {
		return this;
	}

	@Override
	public ServerError getError() {
		return null;
	}

	@Override
	public ServerError setError(Error error) {
		return this;
	}
}
