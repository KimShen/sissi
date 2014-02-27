package com.sissi.pipeline.in.iq.si;

import com.sissi.pipeline.in.iq.ToProxyMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.si.Si;

/**
 * @author kim 2014年2月25日
 */
public class Si4DelegationMatcher extends ToProxyMatcher {

	public Si4DelegationMatcher(String delegation) {
		super(Si.class, delegation);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Si.class).getFeature().getX().type(XDataType.SUBMIT);
	}
}
