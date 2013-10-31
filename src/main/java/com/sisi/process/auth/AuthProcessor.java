package com.sisi.process.auth;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.auth.Auth;
import com.sisi.protocol.auth.Failure;
import com.sisi.protocol.auth.Success;

/**
 * @author kim 2013-10-24
 */
public class AuthProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	private List<UserNormalization> userNormalizations;

	private Accessor accessor;

	public AuthProcessor(List<UserNormalization> userNormalizations, Accessor accessor) {
		super();
		this.userNormalizations = userNormalizations;
		this.accessor = accessor;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		// For iMessage repeat Auth
		if (context.access()) {
			return null;
		}
		Auth auth = Auth.class.cast(protocol);
		for (UserNormalization un : this.userNormalizations) {
			if (un.isSupport(auth.getMechanism())) {
				this.log.debug(un.getClass() + " will normalize " + auth.getMechanism());
				User user = un.normalize(auth);
				if (context.access(this.accessor.access(user))) {
					context.jid(new com.sisi.context.user.User(user.getUser(), null, null));
					return Success.INSTANCE;
				}
			}
		}
		return Failure.INSTANCE;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Auth.class.isAssignableFrom(protocol.getClass());
	}

}
