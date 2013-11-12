package com.sissi.process.iq.result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class IQResultMatcher implements Matcher {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public Boolean match(Protocol protocol) {
		this.log.info("IQResultMatcher will match all Protocol");
		return true;
	}
}
