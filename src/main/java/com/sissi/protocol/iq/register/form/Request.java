package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "jabber:x:data", localName = "field")
@XmlRootElement
public class Request implements Field<String>, Collector {

	private String var;

	private Value value;

	public Request() {
	}

	public Request setVar(String var) {
		this.var = var;
		return this;
	}

	@Override
	public String getValue() {
		return this.value != null ? this.value.getText() : null;
	}

	@Override
	public String getName() {
		return this.var;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}

	public void set(String localName, Object ob) {
		this.value = Value.class.cast(ob);
	}

	@MappingMetadata(uri = "jabber:x:data", localName = "value")
	public static class Value {

		private String text;

		public Value setText(String text) {
			this.text = text;
			return this;
		}

		public String getText() {
			return text;
		}
	}
}
