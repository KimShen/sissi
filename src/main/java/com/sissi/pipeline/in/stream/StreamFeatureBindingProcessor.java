package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Session;

/**
 * @author kim 2014年1月4日
 */
public class StreamFeatureBindingProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isAuth() ? this.buildBindingFeature(Stream.class.cast(protocol)) : true;
	}

	private Boolean buildBindingFeature(Stream stream) {
		stream.addFeature(Session.FEATURE).addFeature(Bind.FEATURE);
		return true;
	}
}
