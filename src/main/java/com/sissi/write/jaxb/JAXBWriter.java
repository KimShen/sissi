package com.sissi.write.jaxb;

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
<<<<<<< HEAD
import com.sissi.protocol.Element;
=======
import com.sissi.protocol.Node;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
import com.sissi.write.Writer;
import com.sissi.write.WriteWithOutClose;
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

	private NamespacePrefixMapper mapper;

	public JAXBWriter() {
		super();
		this.mapper = new JAXBNamespaceMapping();
	}

	public JAXBWriter(NamespacePrefixMapper mapper) {
		super();
		this.mapper = mapper;
	}

<<<<<<< HEAD
	public void write(JIDContext context, Element node, OutputStream output) throws IOException {
		if (WriteWithOutClose.class.isAssignableFrom(node.getClass())) {
=======
	public void write(JIDContext context, Node node, OutputStream output) throws IOException {
		if (WriterWithOutClose.class.isAssignableFrom(node.getClass())) {
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
			this.writeWithOutClose(context, node, output);
		} else {
			this.writeWithFull(context, node, output);
		}
	}

<<<<<<< HEAD
	public void writeWithFull(JIDContext context, Element node, OutputStream output) throws IOException {
=======
	public void writeWithFull(JIDContext context, Node node, OutputStream output) throws IOException {
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		try {
			Marshaller marshaller = this.generateMarshaller(false);
			if (LOG.isInfoEnabled()) {
				StringWriter writer = new StringWriter();
				marshaller.marshal(node, writer);
				String content = writer.toString();
				LOG.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + content);
				output.write(content.getBytes("UTF-8"));
			} else {
				marshaller.marshal(node, output);
			}
		} catch (JAXBException e) {
			LOG.error(e);
			throw new IOException(e);
		}
	}

<<<<<<< HEAD
	private void writeWithOutClose(JIDContext context, Element node, OutputStream output) throws IOException {
=======
	private void writeWithOutClose(JIDContext context, Node node, OutputStream output) throws IOException {
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		try {
			Marshaller marshaller = generateMarshaller(true);
			LinkedList<String> contents = this.prepareToLines(node, marshaller);
			LOG.debug("Line XML: " + contents);
			contents.removeLast();
			StringBuffer sb = new StringBuffer();
			for (String each : contents) {
				sb.append(each);
			}
			LOG.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + sb.toString());
			output.write(sb.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			LOG.error(e);
			throw new IOException(e);
		}
	}

<<<<<<< HEAD
	private LinkedList<String> prepareToLines(Element node, Marshaller marshaller) throws JAXBException, IOException {
=======
	private LinkedList<String> prepareToLines(Node node, Marshaller marshaller) throws JAXBException, IOException {
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		ByteArrayOutputStream prepare = new ByteArrayOutputStream();
		marshaller.marshal(node, prepare);
		LineIterator iterator = IOUtils.lineIterator(new ByteArrayInputStream(prepare.toByteArray()), "UTF-8");
		LinkedList<String> contents = new LinkedList<String>();
		while (iterator.hasNext()) {
			this.addEachLine(iterator, contents);
		}
		return contents;
	}

	private void addEachLine(LineIterator iterator, LinkedList<String> contents) {
		String eachLine = iterator.next().trim();
		if (!eachLine.isEmpty()) {
			contents.add(eachLine);
		}
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

}
