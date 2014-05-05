package com.sissi.io.read.sax;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.ScanUtil;
import com.sissi.io.read.Mapping;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2013-10-25
 */
public class XMLMapping implements Mapping {

	private final String loading = "com.sissi.protocol";

	private final Log log = LogFactory.getLog(this.getClass());

	private final Selector selector = new Selector();

	private final Map<String, Class<?>> cached = new HashMap<String, Class<?>>();

	public XMLMapping() {
		super();
		for (Class<?> each : ScanUtil.getClasses(this.loading)) {
			Metadata metadata = each.getAnnotation(Metadata.class);
			if (metadata != null) {
				this.selector.install(metadata.uri(), metadata.localName(), each);
			}
		}
	}

	public Object instance(String uri, String localName) {
		String uriTrim = uri != null ? uri : "";
		Class<?> clazz = this.instance4Cached(localName, uriTrim);
		this.log.debug("Request uri: " + uriTrim + " / localName: " + localName + " and found: " + clazz);
		try {
			return clazz != null ? this.instance2Cached(localName, uriTrim, clazz) : null;
		} catch (Exception e) {
			this.log.error(e.toString());
			return null;
		}
	}

	private Object instance2Cached(String localName, String uriTrim, Class<?> clazz) throws Exception {
		this.cached.put(uriTrim + localName, clazz);
		return clazz.newInstance();
	}

	private Class<?> instance4Cached(String localName, String uriTrim) {
		Class<?> clazz = this.cached.get(uriTrim + localName);
		if (clazz == null) {
			clazz = this.selector.find(uriTrim, localName);
		}
		return clazz;
	}

	public boolean cached(String uri, String localName) {
		String uriTrim = uri != null ? uri : "";
		boolean isCached = this.cached.containsKey(uriTrim + localName);
		if (isCached) {
			this.log.debug("URI: " + uri + " / LocalName: " + localName + " is exists");
		}
		return isCached;
	}

	private class Selector {

		private final Map<String, Class<?>> mapping = new HashMap<String, Class<?>>();

		public void install(String[] uris, String localName, Class<?> clazz) {
			for (String uri : uris) {
				this.mapping.put((uri != null ? uri : "") + localName, clazz);
				XMLMapping.this.log.debug("Insert Mapping: " + (uri != null ? uri : "") + " / " + localName + " / " + clazz);
			}
		}

		public Class<?> find(String uri, String localName) {
			return this.mapping.get((uri + localName).intern());
		}
	}
}
