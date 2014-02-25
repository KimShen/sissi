package com.sissi.pipeline.in.iq.si;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.si.Si;

/**
 * @author kim 2014年2月25日
 */
public class Si4MockMatcher extends ClassMatcher {

	private String delegation;

	public Si4MockMatcher(String delegation) {
		super(Si.class);
		this.delegation = delegation;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.getParent().to(this.delegation) && protocol.cast(Si.class).getFeature().getX().type(XDataType.SUBMIT);
	}
}
