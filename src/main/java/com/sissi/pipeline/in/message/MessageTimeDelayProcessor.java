package com.sissi.pipeline.in.message;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.offline.Delay;

/**
 * 强制追加Delay
 * 
 * @author kim 2014年4月9日
 */
public class MessageTimeDelayProcessor extends ProxyProcessor {

	private final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZ");
	
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Message.class).delay(new Delay().setStamp(this.format.format(new Date())));
		return true;
	}
}
