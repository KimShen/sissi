package com.sissi.commons;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author kim 2013年12月18日
 */
public class IOUtils {

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
		}
	}

	public static LineIterator lineIterator(InputStream input, String encoding) throws IOException {
		return lineIterator(input, Charsets.toCharset(encoding));
	}

	public static LineIterator lineIterator(InputStream input, Charset encoding) throws IOException {
		return new LineIterator(new InputStreamReader(input, Charsets.toCharset(encoding)));
	}
}
