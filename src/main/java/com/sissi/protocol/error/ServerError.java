package com.sissi.protocol.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error;
import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.detail.BadFormat;
import com.sissi.protocol.error.detail.BadNamespacePrefix;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.protocol.error.detail.FeatureNotImplemented;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.error.detail.Gone;
import com.sissi.protocol.error.detail.HostGone;
import com.sissi.protocol.error.detail.HostUnknown;
import com.sissi.protocol.error.detail.ImproperAddressing;
import com.sissi.protocol.error.detail.InternalServerError;
import com.sissi.protocol.error.detail.InvaildNamespace;
import com.sissi.protocol.error.detail.InvalidFrom;
import com.sissi.protocol.error.detail.InvalidXml;
import com.sissi.protocol.error.detail.ItemNotFound;
import com.sissi.protocol.error.detail.JIDMalformed;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.error.detail.NotAuthorized;
import com.sissi.protocol.error.detail.NotWellFormed;
import com.sissi.protocol.error.detail.PolicyViolation;
import com.sissi.protocol.error.detail.RecipientUnavaliable;
import com.sissi.protocol.error.detail.Redirect;
import com.sissi.protocol.error.detail.RegistrationRequired;
import com.sissi.protocol.error.detail.RemoteServerNotFound;
import com.sissi.protocol.error.detail.RemoteServerTimeout;
import com.sissi.protocol.error.detail.Reset;
import com.sissi.protocol.error.detail.RestrictedXml;
import com.sissi.protocol.error.detail.SeeOtherHost;
import com.sissi.protocol.error.detail.ServiceUnavailable;
import com.sissi.protocol.error.detail.SubscriptionRequired;
import com.sissi.protocol.error.detail.SystemShutdown;
import com.sissi.protocol.error.detail.UnExpectedRequest;
import com.sissi.protocol.error.detail.UnSupportedEncoding;
import com.sissi.protocol.error.detail.UnSupportedFeature;
import com.sissi.protocol.error.detail.UnSupportedStanzaType;
import com.sissi.protocol.error.detail.UnSupportedVersion;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = ServerError.NAME)
public class ServerError implements Error {

	public final static String NAME = "error";

	private String by;

	private String code;

	private String type;

	private ServerErrorText text;

	private List<ErrorDetail> details;

	public ServerError() {
		super();
	}

	public ServerError(Error error) {
		this.by = error.getBy();
		this.code = error.getCode();
		this.type = error.getType();
		this.details = error.getDetails();
		// 包装ServerErrorText
		this.text = error.getText() != null ? new ServerErrorText(error.getText().getLang(), error.getText().getText(), this.details != null && !this.details.isEmpty() ? this.details.get(0).getXmlns() : null) : null;
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

	@XmlAttribute
	public String getBy() {
		return by;
	}

	public ServerError setBy(String by) {
		this.by = by;
		return this;
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

	public ServerError type(ProtocolType type) {
		this.type = type.toString();
		return this;
	}

	public ServerError setType(String type) {
		this.type = type;
		return this;
	}

	public ServerError add(ErrorDetail detail) {
		if (this.details == null) {
			this.details = new ArrayList<ErrorDetail>();
		}
		this.details.add(detail);
		return this;
	}

	public ServerError add(ErrorDetail detail, String text) {
		return this.add(detail, null, text);
	}

	public ServerError add(ErrorDetail detail, String lang, String text) {
		this.add(detail);
		this.text = new ServerErrorText(lang, text, detail.getXmlns());
		return this;
	}

	@Override
	@XmlElement(name = ServerErrorText.NAME)
	public ServerErrorText getText() {
		return this.text;
	}

	@XmlElements({ @XmlElement(name = UnExpectedRequest.NAME, type = UnExpectedRequest.class), @XmlElement(name = SubscriptionRequired.NAME, type = SubscriptionRequired.class), @XmlElement(name = RemoteServerTimeout.NAME, type = RemoteServerTimeout.class), @XmlElement(name = RemoteServerNotFound.NAME, type = RemoteServerNotFound.class), @XmlElement(name = RegistrationRequired.NAME, type = RegistrationRequired.class), @XmlElement(name = Redirect.NAME, type = Redirect.class), @XmlElement(name = RecipientUnavaliable.NAME, type = RecipientUnavaliable.class), @XmlElement(name = JIDMalformed.NAME, type = JIDMalformed.class), @XmlElement(name = ServiceUnavailable.NAME, type = ServiceUnavailable.class), @XmlElement(name = ItemNotFound.NAME, type = ItemNotFound.class), @XmlElement(name = Gone.NAME, type = Gone.class), @XmlElement(name = Forbidden.NAME, type = Forbidden.class), @XmlElement(name = FeatureNotImplemented.NAME, type = FeatureNotImplemented.class), @XmlElement(name = BadRequest.NAME, type = BadRequest.class), @XmlElement(name = NotAllowed.NAME, type = NotAllowed.class), @XmlElement(name = NotAcceptable.NAME, type = NotAcceptable.class), @XmlElement(name = UnSupportedVersion.NAME, type = UnSupportedVersion.class), @XmlElement(name = UnSupportedStanzaType.NAME, type = UnSupportedStanzaType.class), @XmlElement(name = UnSupportedFeature.NAME, type = UnSupportedFeature.class), @XmlElement(name = UnSupportedEncoding.NAME, type = UnSupportedEncoding.class), @XmlElement(name = SystemShutdown.NAME, type = SystemShutdown.class), @XmlElement(name = SeeOtherHost.NAME, type = SeeOtherHost.class), @XmlElement(name = RestrictedXml.NAME, type = RestrictedXml.class), @XmlElement(name = com.sissi.protocol.error.detail.ResourceConstraint.NAME, type = com.sissi.protocol.error.detail.ResourceConstraint.class), @XmlElement(name = Reset.NAME, type = Reset.class), @XmlElement(name = PolicyViolation.NAME, type = PolicyViolation.class), @XmlElement(name = NotWellFormed.NAME, type = NotWellFormed.class), @XmlElement(name = InvalidXml.NAME, type = InvalidXml.class), @XmlElement(name = InvalidFrom.NAME, type = InvalidFrom.class), @XmlElement(name = InternalServerError.NAME, type = InternalServerError.class), @XmlElement(name = Conflict.NAME, type = Conflict.class), @XmlElement(name = BadNamespacePrefix.NAME, type = BadNamespacePrefix.class), @XmlElement(name = BadFormat.NAME, type = BadFormat.class), @XmlElement(name = HostGone.NAME, type = HostGone.class), @XmlElement(name = HostUnknown.NAME, type = HostUnknown.class), @XmlElement(name = InvaildNamespace.NAME, type = InvaildNamespace.class), @XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class), @XmlElement(name = ImproperAddressing.NAME, type = ImproperAddressing.class) })
	public List<ErrorDetail> getDetails() {
		return this.details;
	}
}
