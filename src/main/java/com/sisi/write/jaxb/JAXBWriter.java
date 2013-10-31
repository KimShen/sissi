package com.sisi.write.jaxb;

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

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.protocol.Protocol;
import com.sisi.write.WithOutClose;
import com.sisi.write.Writer;
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
			CONTEXT = JAXBContext.newInstance(clazz.toArray(new Class[]{}));
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

	public void write(Protocol protocol, OutputStream output) throws IOException {
		if (WithOutClose.class.isAssignableFrom(protocol.getClass())) {
			this.writeWithOutClose(protocol, output);
		} else {
			this.writeWithFull(protocol, output);
		}
	}

	public void writeWithFull(Protocol protocol, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = CONTEXT.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.setProperty(MAPPING_PROPERTY, mapper);
			if (LOG.isInfoEnabled()) {
				StringWriter writer = new StringWriter();
				marshaller.marshal(protocol, writer);
				String content = writer.toString().replaceAll(" xmlns:stream=\"http://etherx.jabber.org/streams\"", "");
				LOG.info("Write: " + content);
				output.write(content.getBytes("UTF-8"));

			} else {
				marshaller.marshal(protocol, output);
			}
		} catch (JAXBException e) {
			LOG.error(e);
			throw new IOException(e);
		}
	}

	private void writeWithOutClose(Protocol protocol, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = CONTEXT.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(MAPPING_PROPERTY, mapper);
			ByteArrayOutputStream prepare = new ByteArrayOutputStream();
			marshaller.marshal(protocol, prepare);
			LineIterator iterator = IOUtils.lineIterator(new ByteArrayInputStream(prepare.toByteArray()), "UTF-8");
			LinkedList<String> contents = new LinkedList<String>();
			while (iterator.hasNext()) {
				String eachLine = iterator.next().trim();
				if (!eachLine.isEmpty()) {
					contents.add(eachLine);
				}
			}
			LOG.debug("Line XML: " + contents);
			contents.removeLast();
			StringBuffer sb = new StringBuffer();
			for (String each : contents) {
				sb.append(each);
			}
			LOG.info("Write: " + sb.toString());
			output.write(sb.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			LOG.error(e);
			throw new IOException(e);
		}
	}
}
