package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;
import com.sissi.protocol.feature.Session;
import com.sissi.protocol.feature.Starttls;

/**
 * @author kim 2013-10-24
 */
public class StreamOpenProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(context.isAuth() ? this.buildBindingFeature(Stream.class.cast(protocol)) : this.buildLoginMethod(context, Stream.class.cast(protocol)));
		return true;
	}

	private Protocol buildLoginMethod(JIDContext context, Stream stream) {
		stream.addFeature(Mechanisms.FEATURE).addFeature(Register.FEATURE);
		if(!context.isStarttls()){
			stream.addFeature(Starttls.FEATURE);
		}
		return stream.reply();
	}

	private Protocol buildBindingFeature(Stream stream) {
		stream.addFeature(Session.FEATURE).addFeature(Bind.FEATURE);
		return stream.reply();
	}
}
