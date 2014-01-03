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
import com.sissi.protocol.error.stream.BadFormat;
import com.sissi.protocol.error.stream.BadNamespacePrefix;
import com.sissi.protocol.error.stream.Conflict;
import com.sissi.protocol.error.stream.HostUnknown;
import com.sissi.protocol.error.stream.InvaildNamespace;
import com.sissi.protocol.error.stream.NotAuthorized;

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

	@XmlElements({ @XmlElement(name = Conflict.NAME, type = Conflict.class), @XmlElement(name = BadNamespacePrefix.NAME, type = BadNamespacePrefix.class), @XmlElement(name = BadFormat.NAME, type = BadFormat.class), @XmlElement(name = HostUnknown.NAME, type = HostUnknown.class), @XmlElement(name = InvaildNamespace.NAME, type = InvaildNamespace.class), @XmlElement(name = NotAuthorized.NAME, type = NotAuthorized.class), @XmlElement(name = NotAcceptable.NAME, type = NotAcceptable.class) })
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
