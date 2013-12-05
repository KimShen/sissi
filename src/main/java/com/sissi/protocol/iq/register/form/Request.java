package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.RegisterContext.Field;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "jabber:x:data", localName = "field")
@XmlRootElement
public class Request implements Field, Collector {

	private String var;

	private Value value;

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public String getName() {
		return this.var;
	}

	@Override
	public String getText() {
		return this.value != null ? this.value.getText() : null;
	}

	public void set(String localName, Object ob) {
		this.value = Value.class.cast(ob);
	}

	@MappingMetadata(uri = "jabber:x:data", localName = "value")
	public static class Value {

		private String text;

		public void setText(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}
}
