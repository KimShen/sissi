package com.sissi.netty.impl;

import java.io.IOException;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDContextBuilder;
import com.sissi.feed.FeederBuilder;
import com.sissi.looper.LooperBuilder;
import com.sissi.netty.ServerCloser;
import com.sissi.pipeline.InputCondition.InputFinder;
import com.sissi.read.Reader;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-30
 */
public class MyServerHandlerBuilder {

	private final Writer writer;

	private final Reader reader;

	private final InputFinder finder;

	private final Addressing addressing;

	private final ServerCloser serverCloser;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder looperBuilder;

	private final JIDContextBuilder jidContextBuilder;

	public MyServerHandlerBuilder(Writer writer, Reader reader, InputFinder finder, Addressing addressing, ServerCloser serverCloser, FeederBuilder feederBuilder, LooperBuilder looperBuilder, JIDContextBuilder jidContextBuilder) {
		super();
		this.writer = writer;
		this.reader = reader;
		this.finder = finder;
		this.addressing = addressing;
		this.serverCloser = serverCloser;
		this.feederBuilder = feederBuilder;
		this.looperBuilder = looperBuilder;
		this.jidContextBuilder = jidContextBuilder;
	}

	public MyServerHandler builder() throws IOException {
		return new MyServerHandler(this.reader, this.writer, this.finder, this.addressing, this.serverCloser, this.feederBuilder, this.looperBuilder, this.jidContextBuilder);
	}
}
