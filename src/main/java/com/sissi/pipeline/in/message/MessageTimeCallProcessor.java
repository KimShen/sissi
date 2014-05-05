package com.sissi.pipeline.in.message;

import java.util.UUID;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.ack.ServerTime;

/**
 * 自定义服务器时间反馈
 * 
 * @author kim 2014年3月6日
 */
public class MessageTimeCallProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(new IQ().add(ServerTime.now().setId(protocol.parent().getId())).setId(UUID.randomUUID().toString()).setFrom(protocol.getTo()).setType(ProtocolType.SET));
		return true;
	}
}
