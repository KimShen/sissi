package com.sissi.process.stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.auth.Auth;
import com.sissi.protocol.auth.Mechanisms;
import com.sissi.protocol.core.Stream;
import com.sissi.protocol.iq.Bind;
import com.sissi.protocol.iq.Session;

/**
 * @author kim 2013-10-24
 */
public class StreamProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Stream stream = Stream.generate(context);
		this.log.info("Before Stream is " + stream);
		if (context.access()) {
			this.buildBindingFeature(stream);
		} else {
			this.buildLoginMethod(stream);
		}
		this.log.info("After Stream is " + stream);
		return stream;
	}

	private void buildLoginMethod(Stream stream) {
		stream.addFeature(Mechanisms.PLAIN);
		stream.addFeature(new Auth());
	}

	private void buildBindingFeature(Stream stream) {
		stream.addFeature(Session.INSTANCE);
		stream.addFeature(new Bind());
	}

	public Boolean isSupport(Protocol protocol) {
		return Stream.class.isAssignableFrom(protocol.getClass());
	}
}
