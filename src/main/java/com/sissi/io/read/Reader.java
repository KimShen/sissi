package com.sissi.io.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * @author Kim.shen 2013-10-16
 */
public interface Reader {

	public Future<?> future(InputStream stream) throws IOException;
}
