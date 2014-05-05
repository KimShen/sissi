package com.sissi.pipeline.in.message.apply;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * 取消表单
 * 
 * @author kim 2014年3月8日
 */
public class MessageApplyCancelProcessor extends ProxyProcessor {

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.log.warn("Cancel in " + this.getClass());
		return true;
	}
}
