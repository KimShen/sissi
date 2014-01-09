package com.sissi.pipeline.in;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-21
 */
public class NothingProcessor implements Input {

	public final static Input NOTHING = new NothingProcessor();

	private final Log log = LogFactory.getLog(this.getClass());

	private NothingProcessor() {

	}

	@Override
	public Boolean input(JIDContext context, Protocol current) {
		this.log.warn("Nothing for " + current.getClass() + ", please check");
		return false;
	}
}