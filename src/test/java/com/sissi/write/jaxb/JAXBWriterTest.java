package com.sissi.write.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.sissi.protocol.auth.Auth;
import com.sissi.protocol.stream.Stream;
import com.sissi.protocol.stream.Stream.StreamOpen;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriterTest {

	@Test
	public void testWrite() throws IOException {
		InputStream except = Thread.currentThread().getContextClassLoader().getResourceAsStream("jaxb-default.xml");
		Stream stream = new Stream();
		stream.addFeature(new Auth());
		JAXBWriter writer = new JAXBWriter();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		writer.write(stream, output);
		ByteArrayInputStream actual = new ByteArrayInputStream(output.toByteArray());
		Assert.assertTrue(IOUtils.contentEquals(except, actual));
	}
	
	
	@Test
	public void testWriteWithOutClose() throws IOException {
		InputStream except = Thread.currentThread().getContextClassLoader().getResourceAsStream("jaxb-withoutClose.xml");
		Stream stream = new StreamOpen();
		stream.addFeature(new Auth());
		JAXBWriter writer = new JAXBWriter();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		writer.write(stream, output);
		ByteArrayInputStream actual = new ByteArrayInputStream(output.toByteArray());
		Assert.assertTrue(IOUtils.contentEquals(except, actual));
	}
}
