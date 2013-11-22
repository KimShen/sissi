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

/**
 * @author Kim.shen 2013-10-16
 */
public class SAXReader implements Reader {

	private final static SAXParserFactory FACTORY;

	static {
		FACTORY = SAXParserFactory.newInstance();
		FACTORY.setNamespaceAware(true);
	}

	private Log log = LogFactory.getLog(SAXReader.class);

	private Executor executor;

	private Mapping mapping;

	public SAXReader() {
		this(new XMLMapping(), Executors.newSingleThreadExecutor());
	}

	public SAXReader(Mapping mapping) {
		this(mapping, Executors.newSingleThreadExecutor());
	}

	public SAXReader(Executor executor) {
		this(new XMLMapping(), executor);
	}

	public SAXReader(Mapping mapping, Executor executor) {
		super();
		this.mapping = mapping;
		this.executor = executor;
		this.log.debug("Executor is: " + this.executor.getClass());
	}

	public Future<Object> future(InputStream stream) throws IOException {
		try {
			SAXFuture future = new SAXFuture();
			this.executor.execute(new ParseRunnable(stream, FACTORY.newSAXParser(), new SAXHandler(this.mapping, future)));
			return future;
		} catch (Exception e) {
			this.log.error(e);
			throw new RuntimeException(e);
		}
	}

	private static class ParseRunnable implements Runnable {

		private final static Log LOG = LogFactory.getLog(ParseRunnable.class);

		private SAXParser parser;

		private SAXHandler handler;

		private InputStream stream;

		public ParseRunnable(InputStream stream, SAXParser parser, SAXHandler handler) {
			super();
			this.parser = parser;
			this.handler = handler;
			this.stream = stream;
		}

		public void run() {
			try {
				this.parser.parse(this.stream, this.handler);
			} catch (Exception e) {
				LOG.debug(e);
			}
		}
	}
}