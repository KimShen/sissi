package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.DiscoFeature;
import com.sissi.protocol.iq.disco.DiscoInfo;
import com.sissi.protocol.iq.disco.feature.Blocking;
import com.sissi.protocol.iq.disco.feature.Bytestreams;
import com.sissi.protocol.iq.disco.feature.Identity;
import com.sissi.protocol.iq.disco.feature.Last;
import com.sissi.protocol.iq.disco.feature.Si;
import com.sissi.protocol.iq.disco.feature.SiFileTransfer;
import com.sissi.protocol.iq.disco.feature.Time;
import com.sissi.protocol.iq.disco.feature.VCard;

/**
 * @author kim 2013年12月18日
 */
public class DiscoInfo2RobotProcessor implements Input {

	private final DiscoFeature[] features = new DiscoFeature[] { Identity.FEATURE_PROXY, Bytestreams.FEATURE, SiFileTransfer.FEATURE, Si.FEATURE, VCard.FEATURE, Blocking.FEATURE, Last.FEATURE, Time.FEATURE };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(DiscoInfo.class.cast(protocol).add(this.features).getParent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
