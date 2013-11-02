package com.sissi.read.sax;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.read.Mapping;

/**
 * @author kim 2013-10-25
 */
public class XMLMapping implements Mapping {

	private Log log = LogFactory.getLog(this.getClass());

	private FinderSelector selector;

	private Map<String, Class<?>> cached = new HashMap<String, Class<?>>();

	public XMLMapping() {
		this(Thread.currentThread().getContextClassLoader().getResourceAsStream("mapping.xml"));
	}

	public XMLMapping(InputStream input) {
		super();
		try {
			JAXBContext context = JAXBContext.newInstance(FinderSelector.class);
			this.selector = (FinderSelector) context.createUnmarshaller().unmarshal(input);
		} catch (JAXBException e) {
			this.log.fatal(e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public Object newInstance(String uri, String localName) {
		String securityURI = uri != null ? uri : "";
		Class<?> clazz = this.cached.get(securityURI + localName);
		if (clazz == null) {
			clazz = this.selector.find(securityURI, localName);
		}
		this.log.debug("Request uri: " + securityURI + " / localName: " + localName + " and found: " + clazz);
		try {
			if (clazz != null) {
				this.cached.put(securityURI + localName, clazz);
				return clazz.newInstance();
			} else {
				return null;
			}
		} catch (Exception e) {
			this.log.error(e);
			return null;
		}
	}

	public Boolean hasCached(String uri, String localName) {
		String securityURI = uri != null ? uri : "";
		boolean isCached = this.cached.containsKey(securityURI + localName);
		if (isCached) {
			this.log.debug("URI: " + uri + " / LocalName: " + localName + " would be reused");
		}
		return isCached;
	}

	@XmlRootElement(name = "finders")
	public static class FinderSelector {

		private Log log = LogFactory.getLog(this.getClass());

		private Map<String, Class<?>> mapping = new HashMap<String, Class<?>>();

		private List<Finder> finders;

		public void setFinders(List<Finder> finders) {
			this.finders = finders;
			for (Finder finder : finders) {
				try {
					String uri = finder.getUri();
					this.mapping.put((uri != null ? uri : "") + finder.getLocalName(), Class.forName(finder.getClazz()));
					this.log.debug("Insert Mapping: " + (uri != null ? uri : "") + " / " + finder.getLocalName() + " / " + finder.getClazz());
				} catch (Exception e) {
					this.log.warn(e);
				}
			}
		}

		@XmlElements({ @XmlElement(name = "finder", type = Finder.class) })
		public List<Finder> getFinders() {
			return finders;
		}

		public Class<?> find(String uri, String localName) {
			return this.mapping.get((uri + localName).intern());
		}
	}

	@XmlRootElement
	public static class Finder {

		private String uri;

		private String localName;

		private String clazz;

		@XmlAttribute
		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		@XmlAttribute
		public String getLocalName() {
			return localName;
		}

		public void setLocalName(String localName) {
			this.localName = localName;
		}

		public String getClazz() {
			return clazz;
		}

		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
	}

}
