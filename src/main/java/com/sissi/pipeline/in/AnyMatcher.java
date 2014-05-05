package com.sissi.pipeline.in;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.pipeline.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * 匹配所有
 * 
 * @author kim 2013-11-4
 */
public class AnyMatcher implements InputMatcher {

	public final static InputMatcher INSTANCE = new AnyMatcher();

	private final Log log = LogFactory.getLog(this.getClass());

	private AnyMatcher() {

	}

	@Override
	public boolean match(Protocol protocol) {
		this.log.debug("Match anyone for " + protocol.getClass());
		return true;
	}
}
