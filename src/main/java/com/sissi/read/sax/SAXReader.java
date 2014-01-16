package com.sissi.read.sax;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.read.Mapping;
import com.sissi.read.Reader;
import com.sissi.resource.ResourceMonitor;

/**
 * @author Kim.shen 2013-10-16
 */
public class SAXReader implements Reader {

	private final Log log = LogFactory.getLog(this.getClass());

	private final ResourceMonitor resourceMonitor;

	private final SAXParserFactory factory;

	private final Executor executor;

	private final Mapping mapping;

	public SAXReader(ResourceMonitor resourceMonitor) throws Exception {
		this(new XMLMapping(), Executors.newSingleThreadExecutor(), resourceMonitor);
	}

	public SAXReader(Mapping mapping, ResourceMonitor resourceMonitor) throws Exception {
		this(mapping, Executors.newSingleThreadExecutor(), resourceMonitor);
	}

	public SAXReader(Executor executor, ResourceMonitor resourceMonitor) throws Exception {
		this(new XMLMapping(), executor, resourceMonitor);
	}

	public SAXReader(Mapping mapping, Executor executor, ResourceMonitor resourceMonitor) throws Exception {
		super();
		this.mapping = mapping;
		this.executor = executor;
		this.resourceMonitor = resourceMonitor;
		this.factory = SAXParserFactory.newInstance();
		this.factory.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
		this.factory.setNamespaceAware(true);
	}

	public Future<Object> future(InputStream stream) throws IOException {
		try {
			SAXFuture future = new SAXFuture();
			this.executor.execute(new ParseRunnable(stream, this.factory.newSAXParser(), new SAXHandler(this.mapping, future)));
			return future;
		} catch (Exception e) {
			this.log.error(e);
			throw new RuntimeException(e);
		}
	}

	private class ParseRunnable implements Runnable {

		private final SAXParser parser;

		private final SAXHandler handler;

		private final InputStream stream;

		public ParseRunnable(InputStream stream, SAXParser parser, SAXHandler handler) {
			super();
			this.parser = parser;
			this.handler = handler;
			this.stream = stream;
		}

		public void run() {
			try {
				SAXReader.this.resourceMonitor.increment();
				this.parser.parse(this.stream, this.handler);
			} catch (Exception e) {
				if (SAXReader.this.log.isDebugEnabled()) {
					SAXReader.this.log.debug(e.toString());
					e.printStackTrace();
				}
			} finally {
				SAXReader.this.resourceMonitor.decrement();
			}
		}
	}
}