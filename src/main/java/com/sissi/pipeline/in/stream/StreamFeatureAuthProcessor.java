package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;

/**
 * @author kim 2013-10-24
 */
public class StreamFeatureAuthProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !context.isAuth() ? this.buildLoginFeature(context, Stream.class.cast(protocol)) : true;
	}

	private Boolean buildLoginFeature(JIDContext context, Stream stream) {
		stream.addFeature(Mechanisms.FEATURE).addFeature(Register.FEATURE);
		return true;
	}

}
