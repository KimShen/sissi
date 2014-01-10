package com.sissi.write.jaxb;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
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
import com.sissi.write.Writer;
import com.sissi.write.WriterFragement;
import com.sissi.write.WriterPart;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final String PACKAGE = "com.sissi.protocol";

	private final String MAPPING_PROPERTY = "com.sun.xml.bind.namespacePrefixMapper";

	private final Map<Class<? extends Element>, WriterPart> parts = new HashMap<Class<? extends Element>, WriterPart>();

	private final Log log = LogFactory.getLog(this.getClass());

	private final JAXBContext context;

	private final NamespacePrefixMapper mapper;

	public JAXBWriter() {
		this(new JAXBNamespaceMapping());
	}

	public JAXBWriter(NamespacePrefixMapper mapper) {
		super();
		this.mapper = mapper;
		try {
			List<Class<?>> clazz = new ArrayList<Class<?>>();
			for (Class<?> each : ScanUtil.getClasses(PACKAGE)) {
				if (each.getAnnotation(XmlRootElement.class) != null) {
					clazz.add(each);
				}
			}
			this.log.info("All classes in JAXB Context: " + clazz);
			context = JAXBContext.newInstance(clazz.toArray(new Class[] {}));
		} catch (Exception e) {
			this.log.error(e.toString());
			throw new RuntimeException("Can't init JAXB context", e);
		}
	}

	public Element write(JIDContext context, Element element, OutputStream output) throws IOException {
		BufferedOutputStream bufferOut = new BufferedOutputStream(output);
		try {
			try {
				switch (this.getPart(element.getClass())) {
				case NONE:
					this.marshallerAll(context, element, bufferOut);
					break;
				case WITH_LAST:
					this.marshallerPart(context, WriterPart.WITH_LAST, element, output);
					break;
				case WITHOUT_FIRST:
					this.marshallerPart(context, WriterPart.WITHOUT_FIRST, element, output);
					break;
				case WITHOUT_LAST:
					this.marshallerPart(context, WriterPart.WITHOUT_LAST, element, output);
					break;
				}
				return element;
			} catch (Exception e) {
				if (this.log.isErrorEnabled()) {
					this.log.error(e);
					e.printStackTrace();
				}
				return null;
			}
		} finally {
			bufferOut.flush();
			bufferOut.close();
		}
	}

	public Element marshallerAll(JIDContext context, Element element, OutputStream output) throws Exception {
		Marshaller marshaller = this.generateMarshaller(false, true);
		if (this.log.isInfoEnabled()) {
			StringBufferWriter bufferTemp = new StringBufferWriter(new StringWriter());
			marshaller.marshal(element, bufferTemp);
			bufferTemp.flush();
			String content = bufferTemp.toString();
			this.log.info("Write on " + context.getJid().asString() + " " + content);
			output.write(content.getBytes("UTF-8"));
		} else {
			marshaller.marshal(element, output);
		}
		return element;
	}

	private WriterPart getPart(Class<? extends Element> element) {
		WriterPart part = this.parts.get(element);
		if (part == null) {
			WriterFragement fragement = element.getAnnotation(WriterFragement.class);
			this.parts.put(element, (part = fragement != null ? fragement.part() : WriterPart.NONE));
		}
		return part;
	}

	private Element marshallerPart(JIDContext context, WriterPart part, Element element, OutputStream output) throws Exception {
		Marshaller marshaller = this.generateMarshaller(true, part == WriterPart.WITHOUT_FIRST ? true : false);
		String content = this.getPart(this.prepareToLines(element, marshaller), part);
		this.log.info("Write on " + context.getJid().asString() + " " + content);
		output.write(content.getBytes("UTF-8"));
		return element;
	}

	private LinkedList<String> prepareToLines(Element node, Marshaller marshaller) throws JAXBException, IOException {
		ByteArrayOutputStream prepare = new ByteArrayOutputStream();
		marshaller.marshal(node, prepare);
		LineIterator iterator = IOUtils.lineIterator(new ByteArrayInputStream(prepare.toByteArray()), "UTF-8");
		LinkedList<String> contents = new LinkedList<String>();
		while (iterator.hasNext()) {
			String each = iterator.next().trim();
			if (!each.isEmpty()) {
				contents.add(each);
			}
		}
		return contents;
	}

	private Marshaller generateMarshaller(Boolean format, Boolean fragement) throws JAXBException, PropertyException {
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragement);
		marshaller.setProperty(MAPPING_PROPERTY, mapper);
		if (format) {
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		return marshaller;
	}

	public String getPart(LinkedList<String> contents, WriterPart part) {
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

	private void add(LinkedList<String> contents, StringBuffer parts) {
		for (String content : contents) {
			parts.append(content);
		}
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
}
