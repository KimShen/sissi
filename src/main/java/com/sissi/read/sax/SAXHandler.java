package com.sissi.read.sax;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.sissi.read.Collector;
import com.sissi.read.Mapping;

/**
 * @author kim 2013-10-21
 */
public class SAXHandler extends DefaultHandler {

	private final static Integer ONLY_ROOT = 1;

	private final static Log LOG = LogFactory.getLog(SAXHandler.class);

	@SuppressWarnings("serial")
	private final static Set<String> ROOT_NODE = new HashSet<String>(){{add("stream");}};


	private Mapping mapping;

	private LinkedList<Object> stack;

	private SAXFuture future;

	private Object current;

	public SAXHandler(Mapping mapping, SAXFuture future) {
		super();
		this.mapping = mapping;
		this.future = future;
		this.stack = new LinkedList<Object>();
	}

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
	}

	private boolean propertyCopy(Object ob, String key, Object value) {
		try {
			LOG.debug("Copy " + key + " / " + value + " on " + ob.getClass());
			PropertyUtils.setProperty(ob, key, value);
			return true;
		} catch (Exception e) {
			LOG.debug(e);
			return false;
		}
	}

	private boolean isCollector() {
		return Collector.class.isAssignableFrom(this.stack.getFirst().getClass());
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		LOG.debug("Process uri: " + uri + " localName: " + localName);
		if (this.generateNode(uri != null ? uri : null, localName)) {
			for (int index = 0; index < attributes.getLength(); index++) {
				this.propertyCopy(this.stack.getFirst(), attributes.getLocalName(index), attributes.getValue(index));
			}
		}
	}

	private boolean generateNode(String uri, String localName) {
		if (ROOT_NODE.contains(localName.intern().trim())) {
			this.stack.clear();
		}
		this.current = this.mapping.newInstance(uri, localName);
		if (this.current != null) {
			if (this.stack.isEmpty()) {
				this.future.set(this.current);
			} else {
				this.propertyCopy(this.stack.getFirst(), localName, this.current);
				if (this.isCollector()) {
					Collector collector = Collector.class.cast(this.stack.getFirst());
					collector.set(localName, this.current);
				}
			}
			this.stack.addFirst(this.current);
		}
		return this.current != null;
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (this.mapping.hasCached(uri, localName)) {
			Object firstNode = this.stack.removeFirst();
			LOG.debug(firstNode.getClass() + " will be remove");
			if (this.stack.size() <= ONLY_ROOT) {
				LOG.debug(firstNode.getClass() + " will be feed");
				this.future.set(firstNode);
			}
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		String text = new String(ch, start, length).trim();
		if (!text.isEmpty()) {
			if (this.current != null) {
				this.propertyCopy(this.stack.getFirst(), "text", text);
			}
		}
	}

	public void fatalError(SAXParseException e) throws SAXException {
		LOG.warn(e);
	}
}
