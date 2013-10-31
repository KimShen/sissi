package com.sisi.process.stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.auth.Auth;
import com.sisi.protocol.auth.Mechanisms;
import com.sisi.protocol.core.Stream;
import com.sisi.protocol.iq.Bind;
import com.sisi.protocol.iq.Session;

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
