package com.sissi.pipeline.in.iq.si;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.si.Si;

/**
 * @author kim 2013年12月13日
 */
public class SiTimeDelayProcessor extends ProxyProcessor {

	private final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZ");

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Si.class).delay(this.format.format(new Date()));
		return true;
	}
}
