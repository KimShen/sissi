package com.sissi.read.sax;

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

import com.sissi.read.Collector;
import com.sissi.read.Mapping;

/**
 * @author kim 2013-10-21
 */
public class SAXHandler extends DefaultHandler {

	private final static String field = "text";

	private final static Map<Class<?>, MethodFinder> cachedMethods = new HashMap<Class<?>, MethodFinder>();

	private final static Log log = LogFactory.getLog(SAXHandler.class);

	@SuppressWarnings("serial")
	private final Set<String> reset = new HashSet<String>() {
		{
			add("stream");
		}
	};

	private final Map<String, String> xmlns = new HashMap<String, String>();

	private final LinkedList<Object> stack = new LinkedList<Object>();

	private final SAXFuture future;

	private final Mapping mapping;

	private Object current;

	public SAXHandler(Mapping mapping, SAXFuture future) {
		super();
		this.future = future;
		this.mapping = mapping;
	}

	private MethodFinder find4Cached(Object ob) {
		MethodFinder finder = cachedMethods.get(ob.getClass());
		if (finder == null) {
			log.debug("Create MethodFinder for " + ob.getClass());
			cachedMethods.put(ob.getClass(), (finder = new MethodFinder()));
		}
		return finder;
	}

	private boolean isCollector() {
		return Collector.class.isAssignableFrom(this.stack.getFirst().getClass());
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
			if (log.isDebugEnabled()) {
				log.debug(e.toString());
				e.printStackTrace();
			}
			return false;
		}
	}

	private SAXHandler resetIfNecessary(String localName) {
		if (this.reset.contains(localName.intern().trim())) {
			this.stack.clear();
		}
		return this;
	}

	private SAXHandler newElement(String uri, String localName) {
		this.current = this.mapping.instance(uri, localName);
		return this;
	}

	private SAXHandler xmlnCopy() {
		if (this.current != null) {
			for (String xmln : this.xmlns.keySet()) {
				this.propertyCopy(this.current, xmln, this.xmlns.get(xmln));
			}
		}
		return this;
	}

	private boolean generateNode(Attributes attributes, String uri, String localName) {
		this.resetIfNecessary(localName).newElement(uri, localName).xmlnCopy();
		if (this.current != null) {
			if (this.stack.isEmpty()) {
				this.propertyCopy(attributes, this.current);
				this.future.push(this.current);
			} else {
				if (!this.propertyCopy(this.stack.getFirst(), localName, this.current) && this.isCollector()) {
					Collector.class.cast(this.stack.getFirst()).set(localName, this.current);
				}
			}
			this.stack.addFirst(this.current);
		}
		return this.current != null;
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		if (prefix != null && !prefix.isEmpty()) {
			this.xmlns.put(prefix, uri);
		}
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		log.debug("Process uri: " + uri + " localName: " + localName);
		if (this.generateNode(attributes, uri, localName)) {
			this.propertyCopy(attributes, this.stack.getFirst());
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Can not use simple boolean flag, because node may be nest
		if (this.mapping.exists(uri, localName)) {
			Object firstNode = this.stack.removeFirst();
			log.debug(firstNode.getClass() + " will be remove");
			if (this.stack.size() <= 1) {
				log.debug(firstNode.getClass() + " will be feed");
				this.future.push(firstNode);
			}
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		String text = new String(ch, start, length).trim();
		if (!text.isEmpty()) {
			if (this.current != null) {
				this.propertyCopy(this.stack.getFirst(), field, text);
			}
		}
	}

	public void fatalError(SAXParseException e) throws SAXException {
		if (log.isDebugEnabled()) {
			log.debug(e.toString());
			e.printStackTrace();
		}
	}

	private static class MethodFinder extends HashMap<String, Method> {

		private final static long serialVersionUID = 1L;

		private final static Class<?>[] types = new Class[] { String.class };

		private final Set<String> ignores = new HashSet<String>();

		public boolean invoke(Object ob, String key, Object value) throws Exception {
			if (this.ignores.contains(key)) {
				return false;
			}
			this.cacheMethod(ob, key, this.get(key)).invoke(ob, value);
			log.debug("Copy " + key + " / " + value + " on " + ob.getClass());
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
