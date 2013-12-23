package com.sissi.write.jaxb;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.IOUtils;
import com.sissi.commons.LineIterator;
import com.sissi.commons.ScanUtil;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.write.WithOutClose;
import com.sissi.write.Writer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final String PACKAGE = "com.sissi.protocol";

	private final String MAPPING_PROPERTY = "com.sun.xml.bind.namespacePrefixMapper";

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
			this.log.error(e);
			throw new RuntimeException("Can't init JAXB context", e);
		}
	}

	public Element write(JIDContext context, Element element, OutputStream output) throws IOException {
		BufferedOutputStream bufferOut = new BufferedOutputStream(output);
		try {
			if (WithOutClose.class.isAssignableFrom(element.getClass())) {
				this.writeWithOutClose(context, element, bufferOut);
			} else {
				this.writeWithFull(context, element, bufferOut);
			}
		} finally {
			bufferOut.close();
		}
		return element;
	}

	public void writeWithFull(JIDContext context, Element element, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = this.generateMarshaller(false);
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
		} catch (JAXBException e) {
			if (this.log.isErrorEnabled()) {
				this.log.error(e);
				e.printStackTrace();
			}
		}
	}

	private void writeWithOutClose(JIDContext context, Element element, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = generateMarshaller(true);
			LinkedList<String> contents = this.prepareToLines(element, marshaller);
			contents.removeLast();
			StringBuffer sb = new StringBuffer();
			for (String each : contents) {
				sb.append(each);
			}
			this.log.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + sb.toString());
			output.write(sb.toString().getBytes("UTF-8"));
			output.flush();
		} catch (Exception e) {
			if (this.log.isErrorEnabled()) {
				this.log.error(e);
				e.printStackTrace();
			}
		}
	}

	private LinkedList<String> prepareToLines(Element node, Marshaller marshaller) throws JAXBException, IOException {
		ByteArrayOutputStream prepare = new ByteArrayOutputStream();
		marshaller.marshal(node, prepare);
		LineIterator iterator = IOUtils.lineIterator(new ByteArrayInputStream(prepare.toByteArray()), "UTF-8");
		LinkedList<String> contents = new LinkedList<String>();
		while (iterator.hasNext()) {
			contents.add(iterator.next().trim());
		}
		return contents;
	}

	private Marshaller generateMarshaller(Boolean withOutClose) throws JAXBException, PropertyException {
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
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
