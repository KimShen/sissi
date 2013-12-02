package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.login.Auth;
import com.sissi.protocol.iq.login.Bind;
import com.sissi.protocol.iq.login.Mechanisms;
import com.sissi.protocol.iq.login.Register;
import com.sissi.protocol.iq.login.Session;

/**
 * @author kim 2013-10-24
 */
public class StreamProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.rewriteStreamByAccess(context, Stream.generate(protocol)));
		return false;
	}

	private Stream rewriteStreamByAccess(JIDContext context, Stream stream) {
		return context.isAuth() ? this.buildBindingFeature(stream) : this.buildLoginMethod(stream);
	}

	private Stream buildLoginMethod(Stream stream) {
<<<<<<< HEAD
		stream.addFeature(Mechanisms.MECHANISMS).addFeature(new Auth()).addFeature(new Register());
=======
		stream.addFeature(Mechanisms.MECHANISMS).addFeature(new Auth());
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		return stream;
	}

	private Stream buildBindingFeature(Stream stream) {
		stream.addFeature(Session.INSTANCE).addFeature(new Bind());
		return stream;
	}
}
