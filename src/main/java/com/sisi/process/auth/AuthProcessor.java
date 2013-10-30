package com.sisi.process.auth;

import java.util.List;

import com.sisi.context.Context;
import com.sisi.netty.UID;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.auth.Auth;
import com.sisi.protocol.auth.Failure;
import com.sisi.protocol.auth.Success;

/**
 * @author kim 2013-10-24
 */
public class AuthProcessor implements Processor {

	private List<UserNormalization> userNormalizations;

	private Accessor accessor;

	public AuthProcessor(List<UserNormalization> userNormalizations, Accessor accessor) {
		super();
		this.userNormalizations = userNormalizations;
		this.accessor = accessor;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		if (context.access()) {
			return null;
		}

		Auth auth = Auth.class.cast(protocol);
		for (UserNormalization un : this.userNormalizations) {
			if (un.isSupport(auth.getMechanism())) {
				User user = un.normalize(auth);
				if (context.access(this.accessor.access(user))) {
					context.jid(new UID(user.getUser(), "", ""));
					return Success.INSTANCE;
				}
			}
		}
		return new Failure();
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Auth.class.isAssignableFrom(protocol.getClass());
	}

}
