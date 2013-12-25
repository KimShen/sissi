package com.sissi.read.sax;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.ScanUtil;
import com.sissi.read.Mapping;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-10-25
 */
public class XMLMapping implements Mapping {

	private final String PACKAGE = "com.sissi.protocol";

	private final Log log = LogFactory.getLog(this.getClass());

	private final FinderSelector selector = new FinderSelector();

	private final Map<String, Class<?>> cached = new HashMap<String, Class<?>>();

	public XMLMapping() {
		super();
		Finder finder = new Finder();
		for (Class<?> each : ScanUtil.getClasses(PACKAGE)) {
			MappingMetadata metadata = each.getAnnotation(MappingMetadata.class);
			if (metadata != null) {
				this.selector.addFinder(finder.cover(metadata.uri(), metadata.localName(), each));
			}
		}
	}

	public Object newInstance(String uri, String localName) {
		String securityURI = uri != null ? uri : "";
		Class<?> clazz = this.newInstanceAndGetCached(localName, securityURI);
		this.log.debug("Request uri: " + securityURI + " / localName: " + localName + " and found: " + clazz);
		try {
			return clazz != null ? this.newInstanceAndPutCached(localName, securityURI, clazz) : null;
		} catch (Exception e) {
			this.log.error(e.toString());
			return null;
		}
	}

	private Object newInstanceAndPutCached(String localName, String securityURI, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		this.cached.put(securityURI + localName, clazz);
		return clazz.newInstance();
	}

	private Class<?> newInstanceAndGetCached(String localName, String securityURI) {
		Class<?> clazz = this.cached.get(securityURI + localName);
		if (clazz == null) {
			clazz = this.selector.find(securityURI, localName);
		}
		return clazz;
	}

	public Boolean hasCached(String uri, String localName) {
		String securityURI = uri != null ? uri : "";
		boolean isCached = this.cached.containsKey(securityURI + localName);
		if (isCached) {
			this.log.debug("URI: " + uri + " / LocalName: " + localName + " can be reused");
		}
		return isCached;
	}

	private class FinderSelector {

		private final Log log = LogFactory.getLog(this.getClass());

		private final Map<String, Class<?>> mapping = new HashMap<String, Class<?>>();

		public void addFinder(Finder finder) {
			this.doEachFinder(finder);
		}

		private void doEachFinder(Finder finder) {
			for (String uri : finder.getUris()) {
				this.mapping.put((uri != null ? uri : "") + finder.getLocalName(), finder.getClazz());
				this.log.debug("Insert Mapping: " + (uri != null ? uri : "") + " / " + finder.getLocalName() + " / " + finder.getClazz());
			}
		}

		public Class<?> find(String uri, String localName) {
			return this.mapping.get((uri + localName).intern());
		}
	}

	private class Finder {

		private String[] uris;

		private String localName;

		private Class<?> clazz;

		public String[] getUris() {
			return uris;
		}

		public String getLocalName() {
			return localName;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public Finder cover(String[] uris, String localName, Class<?> clazz) {
			this.uris = uris;
			this.localName = localName;
			this.clazz = clazz;
			return this;
		}
	}
}
