package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Feature;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;

/**
 * @author kim 2013-10-24
 */
public class StreamFeatureAuthProcessor implements Input {

	private final Feature[] features = new Feature[] { Mechanisms.FEATURE, Register.FEATURE };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !context.auth() ? this.writeFeature(Stream.class.cast(protocol)) : true;
	}

	private Boolean writeFeature(Stream stream) {
		stream.addFeature(this.features);
		return true;
	}

}
