package com.sissi.read.sax;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.sissi.commons.Trace;
import com.sissi.read.Counter;
import com.sissi.read.Mapping;
import com.sissi.read.Reader;
import com.sissi.resource.ResourceCounter;

/**
 * @author Kim.shen 2013-10-16
 */
public class SAXReader implements Reader {

	private final Log log = LogFactory.getLog(this.getClass());

	private final NoneCounter noneCounter = new NoneCounter();

	private final String resource = ParseRunnable.class.getSimpleName();

	private final ResourceCounter resourceCounter;

	private final SAXParserFactory factory;

	private final Executor executor;

	private final Mapping mapping;

	private final long limit;

	public SAXReader(long limit, Executor executor, ResourceCounter resourceCounter) throws Exception {
		this(limit, new XMLMapping(), executor, resourceCounter);
	}

	public SAXReader(long limit, Mapping mapping, Executor executor, ResourceCounter resourceCounter) throws Exception {
		super();
		this.limit = limit;
		this.mapping = mapping;
		this.executor = executor;
		this.resourceCounter = resourceCounter;
		this.factory = SAXParserFactory.newInstance();
		this.factory.setNamespaceAware(true);
		this.factory.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);
	}

	public Future<Object> future(InputStream stream) throws IOException {
		try {
			SAXFuture future = new SAXFuture();
			return this.limit == 0 ? this.noneffectiveCount(stream, future) : this.effectiveCount(stream, future);
		} catch (Exception e) {
			this.log.error(e);
			Trace.trace(log, e);
			throw new RuntimeException(e);
		}
	}

	private SAXFuture effectiveCount(InputStream stream, SAXFuture future) throws ParserConfigurationException, SAXException {
		SAXSecurityInputStream input = new SAXSecurityInputStream(stream, this.limit);
		this.executor.execute(new ParseRunnable(input, this.factory.newSAXParser(), new SAXHandler(this.mapping, future, input)));
		return future;
	}

	private SAXFuture noneffectiveCount(InputStream stream, SAXFuture future) throws ParserConfigurationException, SAXException {
		this.executor.execute(new ParseRunnable(stream, this.factory.newSAXParser(), new SAXHandler(this.mapping, future, this.noneCounter)));
		return future;
	}

	private class ParseRunnable implements Runnable {

		private final SAXParser parser;

		private final SAXHandler handler;

		private final InputStream stream;

		public ParseRunnable(InputStream stream, SAXParser parser, SAXHandler handler) {
			super();
			this.parser = parser;
			this.stream = stream;
			this.handler = handler;
		}

		public void run() {
			try {
				SAXReader.this.resourceCounter.increment(SAXReader.this.resource);
				this.parser.parse(this.stream, this.handler);
			} catch (Exception e) {
				SAXReader.this.log.debug(e.toString());
				Trace.trace(SAXReader.this.log, e);
			} finally {
				SAXReader.this.resourceCounter.decrement(SAXReader.this.resource);
			}
		}
	}

	private class NoneCounter implements Counter {

		@Override
		public Counter recount() {
			return this;
		}
	}

	private class SAXSecurityInputStream extends FilterInputStream implements Counter {

		private final AtomicLong counter = new AtomicLong();

		private final long limit;

		private SAXSecurityInputStream(InputStream proxy, long limit) {
			super(proxy);
			this.limit = limit;
		}

		private SAXSecurityInputStream incr(long incr) throws IOException {
			if (this.counter.addAndGet(incr) > this.limit) {
				IOException exception = new IOException("Aggregate leak: " + this.counter.get() + " / " + this.limit);
				SAXReader.this.log.error(exception.getMessage());
				throw exception;
			}
			return this;
		}

		@Override
		public int read() throws IOException {
			this.incr(1);
			return super.read();
		}

		public int read(byte b[]) throws IOException {
			this.incr(b.length);
			return super.read(b, 0, b.length);
		}

		public int read(byte b[], int off, int len) throws IOException {
			this.incr(len);
			return super.read(b, off, len);
		}

		public SAXSecurityInputStream recount() {
			this.counter.set(0);
			return this;
		}
	}
}