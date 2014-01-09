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
import com.sissi.write.WithFull;
import com.sissi.write.WithOnlyLast;
import com.sissi.write.WithOutFirst;
import com.sissi.write.WithOutLast;
import com.sissi.write.Writer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final String PACKAGE = "com.sissi.protocol";

	private final String MAPPING_PROPERTY = "com.sun.xml.bind.namespacePrefixMapper";

	private final Map<Class<? extends Element>, Boolean> isFragment = new HashMap<Class<? extends Element>, Boolean>();

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
				return this.isFragment(element.getClass()) ? this.writeWithFragement(context, element, bufferOut) : this.writeWithFull(context, element, bufferOut);
			} catch (Exception e) {
				if (this.log.isErrorEnabled()) {
					this.log.error(e);
					e.printStackTrace();
				}
				return null;
			}
		} finally {
			bufferOut.close();
		}
	}

	public Element writeWithFull(JIDContext context, Element element, OutputStream output) throws Exception {
		Marshaller marshaller = this.generateMarshaller(false, true);
		if (this.log.isInfoEnabled()) {
			StringBufferWriter bufferTemp = new StringBufferWriter(new StringWriter());
			marshaller.marshal(element, bufferTemp);
			bufferTemp.flush();
			String content = bufferTemp.toString();
			this.log.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + content);
			output.write(content.getBytes("UTF-8"));
		} else {
			marshaller.marshal(element, output);
		}
		output.flush();
		return element;
	}

	private Boolean isFragment(Class<? extends Element> element) {
		Boolean isFragment = this.isFragment.get(element);
		if (isFragment == null) {
			isFragment = !WithFull.class.isAssignableFrom(element) && (WithOnlyLast.class.isAssignableFrom(element) || this.isWithOut(element));
			this.isFragment.put(element, isFragment);
		}
		return isFragment;
	}

	private Boolean isWithOut(Class<? extends Element> element) {
		return WithOutFirst.class.isAssignableFrom(element) || WithOutLast.class.isAssignableFrom(element);
	}

	private Element writeWithFragement(JIDContext context, Element element, OutputStream output) throws Exception {
		return WithOnlyLast.class.isAssignableFrom(element.getClass()) ? this.writeWithOnlyLast(context, element, output) : this.writeWithOut(context, element, output);
	}

	private Element writeWithOut(JIDContext context, Element element, OutputStream output) throws Exception {
		return WithOutFirst.class.isAssignableFrom(element.getClass()) ? this.writeWithOutFirst(context, element, output) : this.writeWithOutLast(context, element, output);
	}

	private Element writeWithOnlyLast(JIDContext context, Element element, OutputStream output) throws Exception {
		Marshaller marshaller = this.generateMarshaller(true, false);
		LinkedList<String> contents = this.prepareToLines(element, marshaller);
		String content = contents.getLast();
		this.log.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + content);
		output.write(content.getBytes("UTF-8"));
		output.flush();
		return element;
	}

	private Element writeWithOutFirst(JIDContext context, Element element, OutputStream output) throws Exception {
		Marshaller marshaller = this.generateMarshaller(true, true);
		LinkedList<String> contents = this.prepareToLines(element, marshaller);
		contents.removeFirst();
		StringBuffer sb = new StringBuffer();
		for (String each : contents) {
			sb.append(each);
		}
		this.log.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + sb.toString());
		output.write(sb.toString().getBytes("UTF-8"));
		output.flush();
		return element;
	}

	private Element writeWithOutLast(JIDContext context, Element element, OutputStream output) throws Exception {
		Marshaller marshaller = this.generateMarshaller(true, true);
		LinkedList<String> contents = this.prepareToLines(element, marshaller);
		contents.removeLast();
		StringBuffer sb = new StringBuffer();
		for (String each : contents) {
			sb.append(each);
		}
		this.log.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + sb.toString());
		output.write(sb.toString().getBytes("UTF-8"));
		output.flush();
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

	private Marshaller generateMarshaller(Boolean withOutClose, Boolean isFragement) throws JAXBException, PropertyException {
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragement);
		marshaller.setProperty(MAPPING_PROPERTY, mapper);
		if (withOutClose) {
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		return marshaller;
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
