package com.sissi.write.jaxb;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.ScanUtil;
import com.sissi.commons.apache.IOUtils;
import com.sissi.commons.apache.LineIterator;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.protocol.Stream;
import com.sissi.write.Writer;
import com.sissi.write.WriterFragement;
import com.sissi.write.WriterPart;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final Map<Class<? extends Element>, WriterPart> parts = new HashMap<Class<? extends Element>, WriterPart>();

	private final Log log = LogFactory.getLog(this.getClass());

	private final String mapperProperty = "com.sun.xml.bind.namespacePrefixMapper";

	private final String protocol = "com.sissi.protocol";

	private final JAXBContext context;

	private final NamespacePrefixMapper mapper;

	public JAXBWriter() {
		this(XmppPrefixMapper.MAPPER);
	}

	public JAXBWriter(NamespacePrefixMapper mapper) {
		super();
		this.mapper = mapper;
		try {
			List<Class<?>> clazz = new ArrayList<Class<?>>();
			for (Class<?> each : ScanUtil.getClasses(protocol)) {
				if (each.getAnnotation(XmlRootElement.class) != null) {
					clazz.add(each);
				}
			}
			this.context = JAXBContext.newInstance(clazz.toArray(new Class[] {}));
			this.log.info("All classes in JAXB Context: " + clazz);
		} catch (Exception e) {
			this.log.error(e.toString());
			throw new RuntimeException("Can't init JAXB context", e);
		}
	}

	public Element write(JIDContext context, OutputStream output, Element element) throws IOException {
		try {
			try {
				switch (this.getPart(element.getClass())) {
				case NONE:
					this.marshaller(context, output, element);
					break;
				case WITH_LAST:
					this.marshallerPart(context, WriterPart.WITH_LAST, output, element);
					break;
				case WITHOUT_FIRST:
					this.marshallerPart(context, WriterPart.WITHOUT_FIRST, output, element);
					break;
				case WITHOUT_LAST:
					this.marshallerPart(context, WriterPart.WITHOUT_LAST, output, element);
					break;
				}
				output.flush();
				return element;
			} catch (Exception e) {
				if (this.log.isErrorEnabled()) {
					this.log.error(e);
					e.printStackTrace();
				}
				return null;
			}
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	private WriterPart getPart(Class<? extends Element> element) {
		WriterPart part = this.parts.get(element);
		if (part == null) {
			WriterFragement fragement = element.getAnnotation(WriterFragement.class);
			this.parts.put(element, (part = fragement != null ? fragement.part() : WriterPart.NONE));
		}
		return part;
	}

	public Element marshaller(JIDContext context, OutputStream output, Element element) throws Exception {
		Marshaller marshaller = this.generateMarshaller(false, true);
		if (this.log.isInfoEnabled()) {
			StringBufferWriter bufferTemp = new StringBufferWriter(new StringWriter());
			marshaller.marshal(element, bufferTemp);
			bufferTemp.flush();
			String content = bufferTemp.toString();
			this.log.info("Write on " + context.jid().asString() + " " + content);
			output.write(content.getBytes("UTF-8"));
		} else {
			marshaller.marshal(element, output);
		}
		return element;
	}

	private Element marshallerPart(JIDContext context, WriterPart part, OutputStream output, Element element) throws Exception {
		Marshaller marshaller = this.generateMarshaller(true, part == WriterPart.WITHOUT_FIRST ? true : false);
		String content = this.getPart(this.prepareToLines(marshaller, element), part);
		this.log.info("Write on " + context.jid().asString() + " " + content);
		output.write(content.getBytes("UTF-8"));
		return element;
	}

	private LinkedList<String> prepareToLines(Marshaller marshaller, Element node) throws JAXBException, IOException {
		ByteArrayOutputStream prepare = new ByteArrayOutputStream();
		BufferedOutputStream buffer = new BufferedOutputStream(prepare);
		try {
			marshaller.marshal(node, buffer);
			buffer.flush();
			LineIterator iterator = IOUtils.lineIterator(new ByteArrayInputStream(prepare.toByteArray()), "UTF-8");
			LinkedList<String> contents = new LinkedList<String>();
			while (iterator.hasNext()) {
				String each = iterator.next().trim();
				if (!each.isEmpty()) {
					contents.add(each);
				}
			}
			return contents;
		} finally {
			IOUtils.closeQuietly(buffer);
			IOUtils.closeQuietly(prepare);
		}
	}

	private void add(LinkedList<String> contents, StringBuffer parts) {
		for (String content : contents) {
			parts.append(content);
		}
	}

	private Marshaller generateMarshaller(boolean format, boolean fragement) throws JAXBException, PropertyException {
		Marshaller marshaller = this.context.createMarshaller();
		marshaller.setProperty(this.mapperProperty, this.mapper);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragement);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
		return marshaller;
	}

	private String getPart(LinkedList<String> contents, WriterPart part) {
		StringBuffer parts = new StringBuffer();
		switch (part) {
		case WITH_LAST:
			parts.append(contents.getLast());
			break;
		case WITHOUT_FIRST:
			contents.removeFirst();
			this.add(contents, parts);
			break;
		case WITHOUT_LAST:
			contents.removeLast();
			this.add(contents, parts);
			break;
		default:
		}
		return parts.toString();
	}

	private class StringBufferWriter extends BufferedWriter {

		private StringWriter out;

		public StringBufferWriter(StringWriter out) {
			super(out);
			this.out = out;
		}

		public String toString() {
			return this.out.toString();
		}
	}

	private static class XmppPrefixMapper extends NamespacePrefixMapper {

		public final static NamespacePrefixMapper MAPPER = new XmppPrefixMapper();

		private final Map<String, String> mapping;

		private XmppPrefixMapper() {
			super();
			Map<String, String> mapping = new HashMap<String, String>();
			mapping.put(Stream.XMLNS, Stream.NAME);
			this.mapping = Collections.unmodifiableMap(mapping);
		}

		public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			return this.mapping.get(namespaceUri);
		}
	}
}
