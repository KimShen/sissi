package com.sisi.write;

import java.io.IOException;
import java.io.OutputStream;

import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Writer {

	public void write(Protocol protocol, OutputStream outputStream) throws IOException;
}
