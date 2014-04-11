package com.sissi.pipeline.in.message;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.offline.Delay;

/**
 * @author kim 2014年4月9日
 */
public class MessageTimeDelayProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Message.class).setDelay(new Delay().setStamp(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date())));
		return true;
	}
}
