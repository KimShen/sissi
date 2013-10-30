package com.sisi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.context.Group;
import com.sisi.feed.Connector;
import com.sisi.feed.ConnectorBuilder;
import com.sisi.feed.FeederBuilder;
import com.sisi.process.Processor;
import com.sisi.read.sax.SAXReader;
import com.sisi.write.jaxb.JAXBWriter;

/**
 * @author kim 2013-10-26
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	private final static Log LOG = LogFactory.getLog(ServerHandler.class);

	private final static AttributeKey<Context> CONTEXT = new AttributeKey<Context>("CONTEXT");

	private final PipedInputStream reader = new PipedInputStream();

	private final PipedOutputStream writer;

	private final Group group;

	private final List<Processor> processors;

	private final FeederBuilder feederBuilder;

	private final ConnectorBuilder connectorBuilder;

	public ServerHandler(Group group, List<Processor> processors, FeederBuilder feederBuilder, ConnectorBuilder connectorBuilder) throws IOException {
		super();
		this.writer = new PipedOutputStream(reader);
		this.group = group;
		this.processors = processors;
		this.feederBuilder = feederBuilder;
		this.connectorBuilder = connectorBuilder;
	}

	public void channelActive(final ChannelHandlerContext ctx) {
		ctx.attr(CONTEXT).set(new UserContext(new JAXBWriter(), ctx));
		this.group.add(ctx.attr(CONTEXT).get());
		SAXReader sax = new SAXReader();
		try {
			Connector connector = this.connectorBuilder.builder(sax.future(reader), this.feederBuilder.builder(ctx.attr(CONTEXT).get(), this.processors));
			connector.start();
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	public void channelInactive(ChannelHandlerContext ctx) {
		this.group.remove(ctx.attr(CONTEXT).get());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf byteBuf = (ByteBuf) msg;
			this.logIfNecessary(byteBuf);
			byte[] data = new byte[byteBuf.readableBytes()];
			byteBuf.readBytes(data);
			this.writer.write(data);
		} catch (Exception e) {
			LOG.fatal(e);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOG.error(cause);
		ctx.close();
	}

	private void logIfNecessary(ByteBuf byteBuf) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Read: " + byteBuf.toString(Charset.forName("UTF-8")));
			byteBuf.readerIndex(0);
		}
	}
}