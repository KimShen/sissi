package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Feature;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Session;

/**
 * @author kim 2014年1月4日
 */
public class StreamFeatureBindingProcessor implements Input {

	private final Feature[] features = new Feature[] { Session.FEATURE, Bind.FEATURE };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.auth() ? this.writeFeature(Stream.class.cast(protocol)) : true;
	}

	private Boolean writeFeature(Stream stream) {
		stream.addFeature(this.features);
		return true;
	}
}
