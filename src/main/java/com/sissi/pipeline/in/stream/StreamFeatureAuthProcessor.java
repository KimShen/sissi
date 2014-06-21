package com.sissi.pipeline.in.stream;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Feature;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;
import com.sissi.ucenter.access.AuthMechanism;

/**
 * @author kim 2013-10-24
 */
public class StreamFeatureAuthProcessor implements Input {

	private final Feature[] features;

	public StreamFeatureAuthProcessor(Set<AuthMechanism> mechanisms) {
		super();
		Mechanisms feature = new Mechanisms();
		for (AuthMechanism mechanism : mechanisms) {
			feature.add(mechanism.support());
		}
		this.features = new Feature[] { feature, Register.FEATURE };
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !context.auth() ? this.writeFeature(protocol.cast(Stream.class)) : true;
	}

	private boolean writeFeature(Stream stream) {
		stream.addFeature(this.features);
		return true;
	}

}
