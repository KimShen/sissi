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

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.write.WriteWithOutClose;
import com.sissi.write.Writer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final static Log LOG = LogFactory.getLog(JAXBWriter.class);

	private final static String MAPPING_PROPERTY = "com.sun.xml.bind.namespacePrefixMapper";

	private final static JAXBContext CONTEXT;

	static {
		try {
			LineIterator classes = IOUtils.lineIterator(Thread.currentThread().getContextClassLoader().getResourceAsStream("jaxb.properties"), "UTF-8");
			List<Class<?>> clazz = new ArrayList<Class<?>>();
			while (classes.hasNext()) {
				clazz.add(Class.forName(classes.next().trim()));
			}
			LOG.info("All classes in JAXB Context: " + clazz);
			CONTEXT = JAXBContext.newInstance(clazz.toArray(new Class[] {}));
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException("Can't init JAXB context", e);
		}
	}

	private final NamespacePrefixMapper mapper;

	public JAXBWriter() {
		super();
		this.mapper = new JAXBNamespaceMapping();
	}

	public JAXBWriter(NamespacePrefixMapper mapper) {
		super();
		this.mapper = mapper;
	}

	public void write(JIDContext context, Element node, OutputStream output) throws IOException {
		BufferedOutputStream bufferOut = new BufferedOutputStream(output);
		try {
			if (WriteWithOutClose.class.isAssignableFrom(node.getClass())) {
				this.writeWithOutClose(context, node, bufferOut);
			} else {
				this.writeWithFull(context, node, bufferOut);
			}
		} finally {
			bufferOut.close();
		}
	}

	public void writeWithFull(JIDContext context, Element node, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = this.generateMarshaller(false);
			if (LOG.isInfoEnabled()) {
				StringBufferWriter bufferTemp = new StringBufferWriter(new StringWriter());
				marshaller.marshal(node, bufferTemp);
				bufferTemp.flush();
				String content = bufferTemp.toString();
				LOG.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + content);
				output.write(content.getBytes("UTF-8"));
			} else {
				marshaller.marshal(node, output);
			}
			output.flush();
		} catch (JAXBException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e);
				e.printStackTrace();
			}
		}
	}

	private void writeWithOutClose(JIDContext context, Element node, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = generateMarshaller(true);
			LinkedList<String> contents = this.prepareToLines(node, marshaller);
			contents.removeLast();
			StringBuffer sb = new StringBuffer();
			for (String each : contents) {
				sb.append(each);
			}
			LOG.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + sb.toString());
			output.write(sb.toString().getBytes("UTF-8"));
			output.flush();
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e);
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
		Marshaller marshaller = CONTEXT.createMarshaller();
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
