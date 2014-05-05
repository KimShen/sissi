package com.sissi.io.read.sax;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.sissi.commons.Trace;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Counter;
import com.sissi.io.read.Mapping;
import com.sissi.protocol.Stream;

/**
 * @author kim 2013-10-21
 */
public class SAXHandler extends DefaultHandler {

	private final static String text = "text";

	private final static Log log = LogFactory.getLog(SAXHandler.class);

	@SuppressWarnings("serial")
	private final static Set<String> reset = new HashSet<String>() {
		{
			add(Stream.NAME);
		}
	};

	private final static Map<Class<?>, MethodFinder> methods = new HashMap<Class<?>, MethodFinder>();

	private final Map<String, String> xmlns = new HashMap<String, String>();

	private final LinkedList<Object> stack = new LinkedList<Object>();

	private final SAXFuture future;

	private final Mapping mapping;

	private final Counter counter;

	private Object current;

	/**
	 * @param mapping
	 * @param future
	 * @param counter XMPP节长度校验器
	 */
	public SAXHandler(Mapping mapping, SAXFuture future, Counter counter) {
		super();
		this.future = future;
		this.counter = counter;
		this.mapping = mapping;
	}

	private SAXHandler xmlnCopy() {
		if (this.current != null) {
			for (String xmln : this.xmlns.keySet()) {
				this.propertyCopy(this.current, xmln, this.xmlns.get(xmln));
			}
		}
		return this;
	}

	private SAXHandler reset(String localName) {
		if (reset.contains(localName.intern().trim())) {
			this.stack.clear();
		}
		return this;
	}

	private MethodFinder find4Cached(Object ob) {
		MethodFinder finder = methods.get(ob.getClass());
		if (finder == null) {
			log.debug("Create finder for " + ob.getClass());
			methods.put(ob.getClass(), (finder = new MethodFinder()));
		}
		return finder;
	}

	private SAXHandler propertyCopy(Attributes attributes, Object element) {
		for (int index = 0; index < attributes.getLength(); index++) {
			this.propertyCopy(element, attributes.getLocalName(index), attributes.getValue(index));
		}
		return this;
	}

	private boolean propertyCopy(Object ob, String key, Object value) {
		try {
			return this.find4Cached(ob).invoke(ob, key, value);
		} catch (Exception e) {
			log.debug(e.toString());
			Trace.trace(log, e);
			return false;
		}
	}

	private SAXHandler newElement(String uri, String localName) {
		this.current = this.mapping.instance(uri, localName);
		return this;
	}

	private boolean generateNode(Attributes attributes, String uri, String localName) {
		this.reset(localName).newElement(uri, localName).xmlnCopy();
		if (this.current == null) {
			return false;
		}
		if (this.stack.isEmpty()) {
			if (!this.future.push(this.propertyCopy(attributes, this.current).current)) {
				log.warn("Queue none capacity");
			}
		} else {
			Object first = this.stack.getFirst();
			if (Collector.class.isAssignableFrom(first.getClass())) {
				Collector.class.cast(first).set(localName, this.current);
			}
		}
		this.stack.addFirst(this.current);
		return true;
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		if (prefix != null && !prefix.isEmpty()) {
			this.xmlns.put(prefix, uri);
		}
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.counter.recount();
		log.debug("Process uri: " + uri + " localName: " + localName);
		if (this.generateNode(attributes, uri, localName)) {
			this.propertyCopy(attributes, this.stack.getFirst());
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (this.mapping.cached(uri, localName)) {
			Object first = this.stack.size() == 1 ? this.stack.getFirst() : this.stack.removeFirst();
			log.debug(first.getClass() + " will be remove");
			if (this.stack.size() <= 1) {
				log.debug(first.getClass() + " will be feed");
				this.future.push(first);
			}
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		String text = new String(ch, start, length).trim();
		if (!text.isEmpty()) {
			if (this.current != null) {
				this.propertyCopy(this.stack.getFirst(), SAXHandler.text, text);
			}
		}
	}

	public void fatalError(SAXParseException e) throws SAXException {
		log.debug(e.toString());
		Trace.trace(log, e);
	}

	private static class MethodFinder extends HashMap<String, Method> {

		private final static long serialVersionUID = 1L;
		
		private final Set<String> ignores = new HashSet<String>();

		private final static Class<?>[] types = new Class[] { String.class };

		public boolean invoke(Object ob, String key, Object value) throws Exception {
			if (this.ignores.contains(key)) {
				return false;
			}
			this.cacheMethod(ob, key, this.get(key)).invoke(ob, value);
			log.trace("Copy " + key + " / " + value + " on " + ob.getClass());
			return true;
		}

		private Method cacheMethod(Object ob, String key, Method method) throws NoSuchMethodException {
			if (method == null) {
				try {
					this.put(key, (method = ob.getClass().getMethod("set" + StringUtils.capitalize(key), types)));
				} catch (NoSuchMethodException e) {
					this.ignores.add(key);
				}
			}
			return method;
		}
	}
}
