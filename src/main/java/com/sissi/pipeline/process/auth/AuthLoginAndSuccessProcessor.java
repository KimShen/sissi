package com.sissi.pipeline.process.auth;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.auth.Auth;
import com.sissi.protocol.auth.Success;

/**
 * @author kim 2013-10-24
 */
public class AuthLoginAndSuccessProcessor implements ProcessPipeline {

	private Log log = LogFactory.getLog(this.getClass());

	private List<AuthNormalization> authNormalizations;

	private JIDBuilder jidBuilder;

	private AuthAccessor authAccessor;

	public AuthLoginAndSuccessProcessor(List<AuthNormalization> authNormalizations, JIDBuilder jidBuilder, AuthAccessor authAccessor) {
		super();
		this.authNormalizations = authNormalizations;
		this.authAccessor = authAccessor;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Auth auth = Auth.class.cast(protocol);
		for (AuthNormalization un : this.authNormalizations) {
			if (un.isSupport(auth.getMechanism())) {
				this.log.info("Auth match for " + un.getClass());
				AuthCertificate certificate = un.normalize(auth);
				if (context.access(this.authAccessor.access(certificate))) {
					return this.writeSuccessProtocol(context, certificate);
				}
			}
		}
		return true;
	}

	private boolean writeSuccessProtocol(JIDContext context, AuthCertificate certificate) {
		JID myJID = this.jidBuilder.build(certificate.getUser(), null, null);
		this.log.info("JID " + myJID.asStringWithBare() + " can access");
		context.jid(myJID);
		context.write(Success.INSTANCE);
		return false;
	}
}
