package com.sisi.process.stream;

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

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Stream stream = Stream.generate(context);
		if (context.access()) {
			this.buildBindingFeature(stream);
		} else {
			this.buildLoginMethod(stream);
		}
		return stream;
	}

	private void buildLoginMethod(Stream stream) {
		stream.addFeature(Auth.INSTANCE);
		stream.addFeature(new Mechanisms("PLAIN"));
	}

	private void buildBindingFeature(Stream stream) {
		stream.addFeature(new Bind());
		stream.addFeature(new Session());
	}

	public Boolean isSupport(Protocol protocol) {
		return Stream.class.isAssignableFrom(protocol.getClass());
	}
}
