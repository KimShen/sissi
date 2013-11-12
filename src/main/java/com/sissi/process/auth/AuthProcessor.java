package com.sissi.process.auth;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.auth.Auth;
import com.sissi.protocol.auth.Failure;
import com.sissi.protocol.auth.Success;

/**
 * @author kim 2013-10-24
 */
public class AuthProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	private List<Normalization> normalizations;

	private Accessor accessor;

	public AuthProcessor(List<Normalization> normalizations, Accessor accessor) {
		super();
		this.normalizations = normalizations;
		this.accessor = accessor;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		// For iMessage repeat Auth
		if (context.access()) {
			this.log.warn("Duplice access for " + context.jid().asStringWithNaked());
			return;
		}
		Auth auth = Auth.class.cast(protocol);
		for (Normalization un : this.normalizations) {
			if (un.isSupport(auth.getMechanism())) {
				this.log.info("Auth match for " + un.getClass());
				User user = un.normalize(auth);
				if (this.accessOK(context, user)) {
					this.prepareSUCCESS(context, user);
					return;
				}
			}
		}
		context.write(Failure.INSTANCE);
	}

	private void prepareSUCCESS(Context context, User user) {
		com.sissi.context.user.User myJID = new com.sissi.context.user.User(user.getUser(), null, null);
		this.log.info("JID " + myJID.asStringWithNaked() + " can access");
		context.jid(myJID);
		context.write(Success.INSTANCE);
	}

	private Boolean accessOK(Context context, User user) {
		return context.access(this.accessor.access(user));
	}
}
