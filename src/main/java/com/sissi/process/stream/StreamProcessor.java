package com.sissi.process.stream;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.auth.Auth;
import com.sissi.protocol.auth.Mechanisms;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.iq.session.Session;
import com.sissi.protocol.stream.Stream;

/**
 * @author kim 2013-10-24
 */
public class StreamProcessor implements Processor {

	@Override
	public void process(Context context, Protocol protocol) {
		Stream stream = Stream.generate(context);
		this.rewriteStreamByAccess(context, stream);
		context.write(stream);
	}

	private void rewriteStreamByAccess(Context context, Stream stream) {
		if (context.access()) {
			this.buildBindingFeature(stream);
		} else {
			this.buildLoginMethod(stream);
		}
	}

	private void buildLoginMethod(Stream stream) {
		stream.addFeature(Mechanisms.PLAIN);
		stream.addFeature(new Auth());
	}

	private void buildBindingFeature(Stream stream) {
		stream.addFeature(Session.INSTANCE);
		stream.addFeature(new Bind());
	}
}
