package com.sissi.write;

import java.io.IOException;
import java.io.OutputStream;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-24
 */
public interface Writer {

	public Element write(JIDContext context, OutputStream outputStream, Element element) throws IOException;
}
