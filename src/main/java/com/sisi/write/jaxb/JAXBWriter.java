package com.sisi.write.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.protocol.Protocol;
import com.sisi.protocol.auth.Failure;
import com.sisi.protocol.auth.Success;
import com.sisi.protocol.core.IQ;
import com.sisi.protocol.core.Message;
import com.sisi.protocol.core.Stream;
import com.sisi.protocol.core.Stream.StreamOpen;
import com.sisi.protocol.iq.Bind;
import com.sisi.protocol.iq.Session;
import com.sisi.write.WithOutClose;
import com.sisi.write.Writer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final static String MAPPING_PROPERTY = "com.sun.xml.bind.namespacePrefixMapper";

	private final static JAXBContext CONTEXT;

	static {
		try {
			CONTEXT = JAXBContext.newInstance(Stream.class, StreamOpen.class, Failure.class, Success.class, IQ.class, Bind.class, Session.class, Message.class);
		} catch (JAXBException e) {
			throw new RuntimeException("Can't init JAXB context", e);
		}
	}

	private Log log = LogFactory.getLog(this.getClass());

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
			if (this.log.isInfoEnabled()) {
				StringWriter writer = new StringWriter();
				marshaller.marshal(protocol, writer);
				String content = writer.toString().replaceAll(" xmlns:stream=\"http://etherx.jabber.org/streams\"", "");
				this.log.info("Write: " + content);
				output.write(content.getBytes("UTF-8"));

			} else {
				marshaller.marshal(protocol, output);
			}
		} catch (JAXBException e) {
			this.log.error(e);
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
			this.log.debug("Line XML: " + contents);
			contents.removeLast();
			StringBuffer sb = new StringBuffer();
			for (String each : contents) {
				sb.append(each);
			}
			this.log.info("Write: " + sb.toString());
			output.write(sb.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			this.log.error(e);
			throw new IOException(e);
		}
	}
}
