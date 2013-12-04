package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;
import com.sissi.protocol.feature.Session;

/**
 * @author kim 2013-10-24
 */
public class StreamOpenProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.rewriteStreamByAccess(context, new Stream(protocol.getId())));
		return true;
	}

	private Stream rewriteStreamByAccess(JIDContext context, Stream stream) {
		return context.isAuth() ? this.buildBindingFeature(stream) : this.buildLoginMethod(stream);
	}

	private Stream buildLoginMethod(Stream stream) {
		stream.addFeature(Mechanisms.MECHANISMS).addFeature(Register.REGISTER);
		return stream;
	}

	private Stream buildBindingFeature(Stream stream) {
		stream.addFeature(Session.INSTANCE).addFeature(new Bind());
		return stream;
	}
}
