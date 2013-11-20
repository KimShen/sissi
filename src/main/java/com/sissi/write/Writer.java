package com.sissi.write;

import java.io.IOException;
import java.io.OutputStream;

<<<<<<< HEAD
import com.sissi.context.JIDContext;
=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Writer {

<<<<<<< HEAD
	public void write(JIDContext context, Protocol protocol, OutputStream outputStream) throws IOException;
=======
	public void write(Protocol protocol, OutputStream outputStream) throws IOException;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
}
