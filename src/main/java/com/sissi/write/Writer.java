package com.sissi.write;

import java.io.IOException;
import java.io.OutputStream;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Node;

/**
 * @author kim 2013-10-24
 */
public interface Writer {

	public void write(JIDContext context, Node node, OutputStream outputStream) throws IOException;
}
