package com.sisi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.context.JID;
import com.sisi.netty.io.ByteBufferOutputStream;
import com.sisi.protocol.Protocol;
import com.sisi.write.Writer;

/**
 * @author kim 2013-10-27
 */
public class UserContext implements Context {

	private final static Log LOG = LogFactory.getLog(UserContext.class);

	private enum AuthState {

		ACCESS, REFUSE;

		public static AuthState valueOf(Boolean canAccess) {
			return canAccess ? ACCESS : REFUSE;
		}
	}

	private Writer writer;

	private ChannelHandlerContext netty;

	private AuthState state;

	private JID jid;

	public UserContext(Writer writer, ChannelHandlerContext netty) {
		super();
		this.writer = writer;
		this.netty = netty;
		this.state = AuthState.REFUSE;
	}

	@Override
	public Boolean access() {
		return this.state == AuthState.ACCESS;
	}

	@Override
	public Boolean access(Boolean canAccess) {
		this.state = AuthState.valueOf(canAccess);
		return this.access();
	}

	public JID jid(JID jid) {
		this.jid = jid;
		return this.jid();
	}

	public JID jid() {
		return this.jid;
	}

	public void write(Protocol protocol) {
		ByteBuf byteBuffer = this.allocBuffer();
		try {
			this.writer.write(protocol, new ByteBufferOutputStream(byteBuffer));
			this.netty.writeAndFlush(byteBuffer).addListener(FailLogGenericFutureListener.INSTANCE);
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	private ByteBuf allocBuffer() {
		return this.netty.alloc().buffer();
	}

}
